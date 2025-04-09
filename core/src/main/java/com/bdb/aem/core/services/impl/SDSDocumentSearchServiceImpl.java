package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.exceptions.InvalidRequestException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.SDSDocumentSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BoundedInputStream;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * The Class SDSDocumentSearchServiceImpl.
 */
@Component(service = SDSDocumentSearchService.class, immediate = true)
public class SDSDocumentSearchServiceImpl implements SDSDocumentSearchService {

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SDSDocumentSearchServiceImpl.class);
	
	/** The Constant BASIC_REST_PARAM_ERROR. */
	private static final String BASIC_REST_PARAM_ERROR = "{\r\n" + 
			"    \"message\": \"Error while processing the request\",\r\n" + 
			"    \"errorDetails\": \"'until-successful' retries exhausted. Last exception message was: HTTP POST on resource 'https://regdocs.bd.com:443/regulatory/api/viewPDF' failed: not acceptable (406).\"\r\n" + 
			"}";

	/** The externalizer service. */
	@Reference
	private ExternalizerService externalizerService;

	/** The bdb api endpoint service. */
	@Reference
	private BDBApiEndpointService bdbApiEndpointService;

	/** The resolver factory. */
	@Reference
	ResourceResolverFactory resolverFactory;

	/** The http client. */
	private CloseableHttpClient httpClient;

	/**
	 * Search SDS document.
	 *
	 * @param countryCode the country code
	 * @param materialNumber the material number
	 * @return the json array
	 */
	@Override
	public JsonArray searchSDSDocument(String countryCode, String materialNumber) {
		LOGGER.debug("SDS Document Search Service");
		JsonArray dataSearch = new JsonArray();
		StringBuilder responseBuilder = new StringBuilder();
		JsonObject inputJson = new JsonObject();
		int scketTimeoutConfig = bdbApiEndpointService.getSocketTimeoutConfig();
		int requestTimeoutConfig = bdbApiEndpointService.getRequestTimeoutConfig();
		int connectTimeoutConfig = bdbApiEndpointService.getConnectTimeoutConfig();

		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
				.setSocketTimeout(scketTimeoutConfig * 1000).setConnectTimeout(connectTimeoutConfig * 1000)
				.setConnectionRequestTimeout(requestTimeoutConfig * 1000).build();
		countryCode = CommonHelper.isCountryEU(countryCode);
		inputJson.addProperty(CommonConstants.COUNTRY_CODE, countryCode.toUpperCase());
		inputJson.addProperty(CommonConstants.MATERIAL_NUMBER, materialNumber);
		inputJson.addProperty(CommonConstants.SOURCE, CommonConstants.BDB_LABEL);

		String userCredentials = bdbApiEndpointService.getSdsEndpointUser() + CommonConstants.COLON
				+ bdbApiEndpointService.getSdsEndpointPassword();
		String basicAuth = CommonConstants.BASIC + CommonConstants.SPACE
				+ new String(Base64.getEncoder().encode(userCredentials.getBytes()));
		String sdsSearchEndpoint = bdbApiEndpointService.getSdsPdfSearchEndpoint();
		LOGGER.debug("SDS Search Endpoint {}",sdsSearchEndpoint);
		/* To get dynamic sds */
		try (ResourceResolver resourceResolver =  CommonHelper.getReadServiceResolver(resolverFactory)) {
			HttpResponse response = getHttpConnection(inputJson, basicAuth, sdsSearchEndpoint, requestConfig);
			if (null != response && response.getStatusLine().getStatusCode() == 200) {
				LOGGER.debug("SDS Search Successfull {}",response.getStatusLine().getStatusCode());
				responseBuilder = getHttpResponse(responseBuilder, response);
				dataSearch = searchArrayJson(responseBuilder, resourceResolver);
			}else if(null != response){
				LOGGER.error("SDS Search Response Status {}", response.getStatusLine().getStatusCode());
				response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
	            dataSearch.add(BASIC_REST_PARAM_ERROR);
	        }
		} catch (IOException e) {
			LOGGER.error("Search IOException", e);
		} catch (LoginException e) {
			LOGGER.error("LoginException", e);
		}
		return dataSearch;
	}

	/**
	 * Gets the http connection.
	 *
	 * @param inputJson the input json
	 * @param basicAuth the basic auth
	 * @param sdsEndpoint the sds endpoint
	 * @param requestConfig the request config
	 * @return the http connection
	 * @throws ClientProtocolException 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpResponse getHttpConnection(JsonObject inputJson, String basicAuth, String sdsEndpoint,RequestConfig requestConfig) throws ClientProtocolException, IOException
			 {
		LOGGER.debug("Http Connection Establish");
		String inputString = inputJson.toString();
		StringEntity entity = new StringEntity(inputString);
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		HttpPost httpRequest = new HttpPost(sdsEndpoint);
		httpRequest.addHeader(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
		httpRequest.setEntity(entity);
		httpRequest.setHeader(CommonConstants.AUTHORIZATION, basicAuth);

		return httpClient.execute(httpRequest);
	}

	/**
	 * Gets the http response.
	 *
	 * @param responseBuilder the response builder
	 * @param response the response
	 * @return the http response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public StringBuilder getHttpResponse(StringBuilder responseBuilder, HttpResponse response) throws IOException {
		LOGGER.debug("Http Response");
		BoundedInputStream boundedInput = new BoundedInputStream(response.getEntity().getContent(),
				CommonConstants.SIZE_4B);
		BufferedReader reader = new BufferedReader(new InputStreamReader(boundedInput), CommonConstants.SIZE_2B);
	try(StringBuilderWriter writer = new StringBuilderWriter(responseBuilder)){	
		IOUtils.copy(reader, writer);
		responseBuilder = writer.getBuilder();
		return responseBuilder;
	}   catch(IOException ex) {
        LOGGER.error("Exception occurred",ex);
    }
	return responseBuilder; 
	}

	/**
	 * Search array json.
	 *
	 * @param responseBuilder the response builder
	 * @param resourceResolver the resource resolver
	 * @return the json array
	 */
	public JsonArray searchArrayJson(StringBuilder responseBuilder,ResourceResolver resourceResolver) {
		LOGGER.debug("Inside :: searchArrayJson");
		JsonArray dataJsonArray = new JsonArray();
		Resource languageResource = resourceResolver.getResource(CommonConstants.LANGUAGE_GENERIC_LIST_PATH);
		ArrayList<Resource> resourceList = new ArrayList<>();
		getLanguageResourceList(languageResource, resourceList);
		JsonElement jsonElement = new JsonParser().parse(responseBuilder.toString());
		if (null != jsonElement.getAsJsonArray()) {
			JsonArray searchArray = jsonElement.getAsJsonArray();
			LOGGER.debug("Response String {} ", responseBuilder);
			LOGGER.debug("searchArray Response String {}", searchArray);
			for (JsonElement searchList : searchArray) {
				JsonObject searchAttributes = new JsonObject();
				JsonObject searchObj = searchList.getAsJsonObject();
				String id = getJsonProperty(searchObj, CommonConstants.ID);
				String country = getJsonProperty(searchObj, CommonConstants.COUNTRY);
				String languageCode = getJsonProperty(searchObj, CommonConstants.LANGUAGE_CODE);
				searchAttributes.addProperty(CommonConstants.ID, id);
				searchAttributes.addProperty(CommonConstants.COUNTRY, country);
				searchAttributes.addProperty("language", languageCode);
				getSearchAttributes(resourceList, languageCode,searchAttributes);
				dataJsonArray.add(searchAttributes);

			}
		}

		return dataJsonArray;
	}

	/**
	 * Gets the json property.
	 *
	 * @param assetObj the asset obj
	 * @param property the property
	 * @return the json property
	 */
	private String getJsonProperty(JsonObject assetObj, String property) {
		if (null != assetObj && null != assetObj.get(property)) {
			property = assetObj.get(property).getAsString();
		}
		return property;
	}

	/**
	 * Gets the language resource list.
	 *
	 * @param languageResource the language resource
	 * @param resourceList the resource list
	 * @return the language resource list
	 */
	public void getLanguageResourceList(Resource languageResource, List<Resource> resourceList) {
		LOGGER.debug("Inside :: getLanguageResourceList");
		if (languageResource.hasChildren()) {
			Iterator<Resource> languageList = languageResource.listChildren();
			while (languageList.hasNext()) {
				Resource languageItem = languageList.next();
				resourceList.add(languageItem);
			}
		}
	}

	/**
	 * Gets the search attributes.
	 *
	 * @param resourceList the resource list
	 * @param languageCode the language code
	 * @param searchAttributes the search attributes
	 * @return the search attributes
	 */
	public void getSearchAttributes(List<Resource> resourceList,
									String languageCode,JsonObject searchAttributes) {
		LOGGER.debug("Inside :: getSearchAttributes");
		int lisCount = 0;
		for (Resource resource : resourceList) {
			ValueMap valueMap = resource.adaptTo(ValueMap.class);
			String langCode = valueMap.get(CommonConstants.DATA_VALUE, String.class);
			boolean booleanCheck = StringUtils.equalsIgnoreCase(langCode, languageCode);
			if (booleanCheck) {
				lisCount = lisCount + 1;
				String langLable = valueMap.get(CommonConstants.JCR_TITLE, String.class);
				searchAttributes.addProperty(CommonConstants.LANGUAGE_CODE, langLable);
			}
		}
		if (lisCount == 0) {
			searchAttributes.addProperty(CommonConstants.LANGUAGE_CODE, languageCode);
		}
	}


	/**
	 * Download SDS document.
	 *
	 * @param fileID the file ID
	 * @param requestConfig the request config
	 * @return the http response
	 * @throws AemInternalServerErrorException 
	 */
	@Override
	public HttpResponse downloadSDSDocument(String fileID, RequestConfig requestConfig) throws AemInternalServerErrorException {
		LOGGER.debug("Download SDSDocument Service");
		HttpResponse response = null;
		String userCredentials = bdbApiEndpointService.getSdsEndpointUser() + CommonConstants.COLON
				+ bdbApiEndpointService.getSdsEndpointPassword();
		String basicAuth = CommonConstants.BASIC + CommonConstants.SPACE
				+ new String(Base64.getEncoder().encode(userCredentials.getBytes()));
		JsonObject inputJson = new JsonObject();
		inputJson.addProperty(CommonConstants.FILE_ID, fileID);
		String sdsDownloadEndpoint = bdbApiEndpointService.getSdsPdfDownloadEndpoint();
		LOGGER.debug("SDS DownloadEndpoint Path {}", sdsDownloadEndpoint);
		try {
			response = getHttpConnection(inputJson, basicAuth, sdsDownloadEndpoint, requestConfig);
			LOGGER.debug("Download Endpoint Response {}", response);
		} catch (IOException e) {
			throw new AemInternalServerErrorException(CommonConstants.AEM_SSO_ERROR_CODE,
					new InvalidRequestException(BASIC_REST_PARAM_ERROR));
		}
		return response;

	}

	/**
	 * Deactivate.
	 */
	@Deactivate
	public void deactivate() {
		LOGGER.debug("DataSheet Model Deactivated");
		try {
			if (null != httpClient) {
				httpClient.close();
			}
		} catch (IOException e) {
			LOGGER.error("IOException during closing of httpClient", e);
		}
	}

}
