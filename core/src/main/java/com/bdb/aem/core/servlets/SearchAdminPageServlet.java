package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.impl.BaseRequestImpl;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SearchConstants;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet to send the Search Admin page API Call
 */

@Component(name = "SearchAdminPageServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "SearchAdminPageServlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + SearchAdminPageServlet.RESOURCE_TYPE})
public class SearchAdminPageServlet extends BaseServlet {
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/get-search-endpoints";

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchAdminPageServlet.class);
	
	private static final String NO_REQUEST_OBJECT_ERROR_MESSAGE = "No request object {}";

	/** The bdb search endpoint service. */
	@Reference
	private transient BDBSearchEndpointService bdbSearchEndpointService;

	@Reference
	private transient SolrSearchService solrSearchService;

	/** The rest client. */
	@Reference
	private transient RestClient restClient;

	@Reference
	private transient ResourceResolverFactory resolverFactory;

	/**
	 * Post method.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		doGet(request, response);
	}

	/**
	 * Get Method.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		String getResponse = StringUtils.EMPTY;
		ResourceResolver resourceResolver = null;
		String getParameters = request.getRequestParameter(SearchConstants.SEARCH_PARAM) != null
				? request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString()
				: StringUtils.EMPTY;
		
		try {
			resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
			if (getParameters.equalsIgnoreCase(SearchConstants.RELOAD_SOLR_SERVER)) {
				reloadSolrServer(request, resourceResolver);
			} else if (getParameters.equalsIgnoreCase(SearchConstants.SEARCH_SYNONYM_RESULTS)) {
				getResponse = getSynonymResultsEndpoint(request);
			} else if (getParameters.equalsIgnoreCase(SearchConstants.SEARCH_DELETE_RESULTS)) {
				getResponse = getDeleteSynonymEndpoint(request, resourceResolver);
			} else if (getParameters.equalsIgnoreCase(SearchConstants.SEARCH_CREATE_SYNONYM_RESULTS)) {
				getResponse = getCreateSynonymEndpoint(request, resourceResolver);

			} else if (getParameters.equalsIgnoreCase(SearchConstants.SEARCH_STOPWORD_RESULTS)) {
				getResponse = getStopWordResultsEndpoint(request);
			} else if (getParameters.equalsIgnoreCase(SearchConstants.SEARCH_CREATE_STOPWORD_RESULTS)) {
				getResponse = getCreateStopWordEndpoint(request, resourceResolver);
			} else if (getParameters.equalsIgnoreCase(SearchConstants.SEARCH_REMOVE_STOPWORD_RESULT)) {
				getResponse = getRemoveSelectedStopWordEndpoint(request, resourceResolver);
			}  
		} catch (LoginException e) {
			LOGGER.error("Exception is Stopwords and Synonyms {}", e.getMessage());
		}
		finally {
			CommonHelper.closeResourceResolver(resourceResolver);
			response.setContentType(CommonConstants.APPLICATION_JSON);
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(getResponse);
			writer.flush();
		}
	}


	/**
	 * Below method to Create Synonym words
	 * 
	 * @return
	 * @throws LoginException
	 */
	private String getCreateSynonymEndpoint(SlingHttpServletRequest request, ResourceResolver resourceResolver)
			throws LoginException {

		String languageKey = null != request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD)
				? request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD).getString()
				: StringUtils.EMPTY;
		String synonymRequestValue = request.getParameter(SearchConstants.SYNONYM);
				
		ArrayList<String> countriesList = (ArrayList<String>) solrSearchService.getAllCountries(resourceResolver);
		JsonObject response = new JsonObject();
		JsonObject requestObject = null;
		JsonElement synonym = null;
		JsonArray trimSynonymArr = new JsonArray();
		boolean isSynonym = StringUtils.equalsIgnoreCase(synonymRequestValue, "true");
		try {
			requestObject = getRequestObject(request);
			if(isSynonym) {
				synonym = requestObject.get(SearchConstants.SYNONYM);
				/* Trimming all trailing and leading white spaces from synonym object
				 * GE-20848
				 */
				JsonArray synonymArr = synonym.getAsJsonArray();
				for (int i = 0; i < synonymArr.size(); i++) {
					JsonElement synonymObj = synonymArr.get(i);
					String trimSynonymObj = synonymObj.getAsString().trim();
					trimSynonymArr.add(trimSynonymObj);
				}
			}
		} catch (IOException e) {
			LOGGER.error(NO_REQUEST_OBJECT_ERROR_MESSAGE, e.getMessage());
		}
		for (String country : countriesList) {
			try {
				final Map<String, String> requestHeaderMap = new HashMap<>();
				StringBuilder requestUrl = new StringBuilder();
				requestUrl = requestUrl.append(bdbSearchEndpointService.getSolrUrl())
						.append(bdbSearchEndpointService.getCreateSynonym()
								.replace(SolrSearchConstants.COUNTRY_VARIABLE, country.toLowerCase())
								.replace(SearchConstants.SEARCH_LANGUAGE_KEY_SELECTOR, languageKey));
				LOGGER.debug("getCreateSynonymEndpoint {} ", requestUrl);
				LOGGER.debug("getCreateSynonymEndpoint- requestObject {} ", requestObject);
				BaseRequest baseRequest = new BaseRequestImpl(requestUrl.toString(), HttpMethodType.PUT,
						isSynonym && null != trimSynonymArr ? trimSynonymArr.toString() : requestObject.toString());
				requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
				BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
				
				
				response = fetchResponseJson(response, baseResponse, country, "getCreateSynonymEndpoint");
				
			} catch (AemInternalServerErrorException | HttpSolrClient.RemoteSolrException e) {
				LOGGER.error("Exception in AemInternalServerErrorException in getCreateSynonymEndpoint {}",
						e.getMessage());
			}
		}
		
		String name = isSynonym && null != trimSynonymArr ? trimSynonymArr.toString() : requestObject.toString();
		//Logging an entry to replication core.
		addRecordToReplication(name);
			

		return response.toString();
	}

	private void addRecordToReplication(String name) {
		Map<String, String> requestHeader = new HashMap<>();
		requestHeader.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		SolrInputDocument inputDoc = new SolrInputDocument(
				"id", currentDateTime.toString(),
				"uniqueIdentifier", currentDateTime.toString(),
				"name_t", name);
		String coreName = bdbSearchEndpointService.getContentPageCollectionName() + "-" + "replication";
		String solrUrl = bdbSearchEndpointService.getSolrUrl() + SolrSearchConstants.SLASH_SOLR_SLASH + coreName;
		try {
			HttpSolrClient server = new HttpSolrClient.Builder(solrUrl).withHttpClient(restClient.getHttpClient()).build();
			server.add(inputDoc);
			server.commit();
			server.close();
			LOGGER.debug("Finished Indexing for core : {}", coreName);
		} 
		catch (SolrServerException | IOException | HttpSolrClient.RemoteSolrException e) {
			LOGGER.error("Exception while indexing to replication core {}", e);
		}
	}

	/**
	 * Below Method to Remove the Stopwords
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws LoginException
	 */
	private String getRemoveSelectedStopWordEndpoint(SlingHttpServletRequest request, ResourceResolver resourceResolver)
			throws UnsupportedEncodingException, LoginException {
		String removeStopWord = null != request.getRequestParameter(SearchConstants.SEARCH_REMOVE_KEY_WORD)
				? request.getRequestParameter(SearchConstants.SEARCH_REMOVE_KEY_WORD).getString()
				: StringUtils.EMPTY;
		String languageKey = null != request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD)
				? request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD).getString()
				: StringUtils.EMPTY;
				
		JsonObject response = new JsonObject();
		final Map<String, String> requestHeaderMap = new HashMap<>();
		removeStopWord = !removeStopWord.isEmpty()
				? URLEncoder.encode(removeStopWord, StandardCharsets.UTF_8.toString())
				: StringUtils.EMPTY;
		ArrayList<String> countriesList = (ArrayList<String>) solrSearchService.getAllCountries(resourceResolver);
		JsonObject requestObject = null;
		try {
			requestObject = getRequestObject(request);
		} catch (IOException e) {
			LOGGER.error(NO_REQUEST_OBJECT_ERROR_MESSAGE, e.getMessage());
		}
		for (String country : countriesList) {
			try {
				StringBuilder requestUrl = new StringBuilder();
				requestUrl = requestUrl.append(bdbSearchEndpointService.getSolrUrl())
						.append(bdbSearchEndpointService.getRemoveSelectedStopWord()
								.replace(SolrSearchConstants.COUNTRY_VARIABLE, country.toLowerCase())
								.replace(SearchConstants.SEARCH_KEY_WORD, removeStopWord)
								.replace(SearchConstants.SEARCH_LANGUAGE_KEY_SELECTOR, languageKey));
				LOGGER.debug("getRemoveSelectedStopWordEndpoint {}  ", requestUrl);
				LOGGER.debug("getRemoveSelectedStopWordEndpoint- requestObject {} ", requestObject);
				if(null!=requestObject) {
					BaseRequest baseRequest = new BaseRequestImpl(requestUrl.toString(), HttpMethodType.DELETE,
							requestObject.toString());
					requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
					BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
					response = fetchResponseJson(response, baseResponse, country, "getRemoveSelectedStopWordEndpoint");
				}
			} catch (AemInternalServerErrorException | HttpSolrClient.RemoteSolrException e) {
				LOGGER.error("Exception in AemInternalServerErrorException in getRemoveSelectedStopWordEndpoint {}",
						e.getMessage());
			}
		}
		
		//Logging an entry to replication core.
		addRecordToReplication(removeStopWord);

		return response.toString();
	}

	/**
	 * Method to Create new StopWord
	 * 
	 * @return
	 * @throws LoginException
	 */

	private String getCreateStopWordEndpoint(SlingHttpServletRequest request, ResourceResolver resourceResolver)
			throws LoginException {
		String languageKey = null != request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD)
				? request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD).getString()
				: StringUtils.EMPTY;

		JsonObject response = new JsonObject();
		ArrayList<String> countriesList = (ArrayList<String>) solrSearchService.getAllCountries(resourceResolver);
		JsonArray requestObject = null;
		try {
			requestObject = getRequestObjectArray(request);
		} catch (IOException e) {
			LOGGER.error(NO_REQUEST_OBJECT_ERROR_MESSAGE, e.getMessage());
		}
		for (String country : countriesList) {
			try {
				final Map<String, String> requestHeaderMap = new HashMap<>();
				StringBuilder requestUrl = new StringBuilder();

				requestUrl = requestUrl.append(bdbSearchEndpointService.getSolrUrl())
						.append(bdbSearchEndpointService.getCreateStopWord()
								.replace(SolrSearchConstants.COUNTRY_VARIABLE, country.toLowerCase())
								.replace(SearchConstants.SEARCH_LANGUAGE_KEY_SELECTOR, languageKey));
				LOGGER.debug("getCreateStopWordEndpoint {} ", requestUrl);
				LOGGER.debug("getCreateStopWordEndpoint- requestObject {} ", requestObject);
				if(null!=requestObject) {
					BaseRequest baseRequest = new BaseRequestImpl(requestUrl.toString(), HttpMethodType.PUT,
							requestObject.toString());
					requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
					BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
					response = fetchResponseJson(response, baseResponse, country, "getCreateStopWordEndpoint");
				}
			} catch (AemInternalServerErrorException | HttpSolrClient.RemoteSolrException e) {
				LOGGER.error("Exception in AemInternalServerErrorException in getCreateStopWordEndpoint {}",
						e.getMessage());
			}
		}
		
		//Logging an entry to replication core.
		if(null!=requestObject) {
			addRecordToReplication(requestObject.toString());
		}

		return response.toString();
	}

	/**
	 * Method to delete the Synonym word
	 * 
	 * @param request
	 * @return
	 * @throws LoginException
	 * @throws UnsupportedEncodingException
	 */

	private String getDeleteSynonymEndpoint(SlingHttpServletRequest request, ResourceResolver resourceResolver)
			throws LoginException, UnsupportedEncodingException {
		String deleteWord = null != request.getRequestParameter(SearchConstants.SEARCH_DELETE_KEY_WORD)
				? request.getRequestParameter(SearchConstants.SEARCH_DELETE_KEY_WORD).getString()
				: StringUtils.EMPTY;
		String languageKey = null != request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD)
				? request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD).getString()
				: StringUtils.EMPTY;
		JsonObject response = new JsonObject();
		deleteWord = !deleteWord.isEmpty() ? URLEncoder.encode(deleteWord, StandardCharsets.UTF_8.toString())
				: StringUtils.EMPTY;
		ArrayList<String> countriesList = (ArrayList<String>) solrSearchService.getAllCountries(resourceResolver);
		JsonObject requestObject = null;
		try {
			requestObject = getRequestObject(request);
		} catch (IOException e) {
			LOGGER.error(NO_REQUEST_OBJECT_ERROR_MESSAGE, e.getMessage());
		}
		for (String country : countriesList) {
			try {
				final Map<String, String> requestHeaderMap = new HashMap<>();
				StringBuilder requestUrl = new StringBuilder();
				requestUrl = requestUrl.append(bdbSearchEndpointService.getSolrUrl())
						.append(bdbSearchEndpointService.getDeleteSynonym()
								.replace(SolrSearchConstants.COUNTRY_VARIABLE, country.toLowerCase())
								.replace(SearchConstants.SEARCH_KEY_WORD, deleteWord)
								.replace(SearchConstants.SEARCH_LANGUAGE_KEY_SELECTOR, languageKey));
				LOGGER.debug("getDeleteSynonymEndpoint {} ", requestUrl);
				LOGGER.debug("getDeleteSynonymEndpoint- requestObject {} ", requestObject);
				if(null!=requestObject) {
					BaseRequest baseRequest = new BaseRequestImpl(requestUrl.toString(), HttpMethodType.DELETE,
							requestObject.toString());
					requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
					BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
					response = fetchResponseJson(response, baseResponse, country, "getDeleteSynonymEndpoint");
				}
			} catch (AemInternalServerErrorException | HttpSolrClient.RemoteSolrException e) {
				LOGGER.error("Exception in AemInternalServerErrorException in getDeleteSynonymEndpoint {}",
						e.getMessage());
			}
		}
		
		//Logging an entry to replication core.
		addRecordToReplication(deleteWord);

		return response.toString();
	}

	/**
	 * Below method to Get the List of StopWords
	 * 
	 * @return
	 * @param request
	 */
	private String getStopWordResultsEndpoint(SlingHttpServletRequest request) {
		String languageKey = null != request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD)
				? request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD).getString()
				: StringUtils.EMPTY;
		try {
			JsonObject requestObject = new JsonObject();
			StringBuilder requestUrl = new StringBuilder();
			final Map<String, String> requestHeaderMap = new HashMap<>();
			requestUrl = requestUrl.append(bdbSearchEndpointService.getSolrUrl())
					.append(bdbSearchEndpointService.getStopWordResults()
							.replace(SolrSearchConstants.COUNTRY_VARIABLE, "us")
							.replace(SearchConstants.SEARCH_LANGUAGE_KEY_SELECTOR, languageKey));
			LOGGER.debug("getStopWordResultsEndpoint {} ", requestUrl);
			LOGGER.debug("getStopWordResultsEndpoint- requestObject {} ", requestObject);
			BaseRequest baseRequest = new BaseRequestImpl(requestUrl.toString(), HttpMethodType.GET,
					requestObject.toString());
			requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
			BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
			if (null != baseResponse.getError()) {
				LOGGER.error("Error in getStopWordResultsEndpoint {}", baseResponse.getError().getMessage());
				return baseResponse.getError().getMessage();
			} else if (null != baseResponse.getResponseData()) {
				return baseResponse.getResponseData().getData();
			} else {
				LOGGER.error("Blank Response in getStopWordResultsEndpoint");
				return CommonConstants.BLANK;
			}
		} catch (AemInternalServerErrorException | HttpSolrClient.RemoteSolrException e) {
			LOGGER.error("Exception in AemInternalServerErrorException in getStopWordResultsEndpoint {}",
					e.getMessage());
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Below method is to get all Synonym Results from Solr
	 * 
	 * @return
	 * @param request
	 */
	private String getSynonymResultsEndpoint(SlingHttpServletRequest request) {
		String languageKey = null != request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD)
				? request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD).getString()
				: StringUtils.EMPTY;

		try {
			JsonObject requestObject = new JsonObject();
			final Map<String, String> requestHeaderMap = new HashMap<>();
			StringBuilder requestUrl = new StringBuilder();
			requestUrl = requestUrl.append(bdbSearchEndpointService.getSolrUrl())
					.append(bdbSearchEndpointService.getSynonymResults()
							.replace(SolrSearchConstants.COUNTRY_VARIABLE, "us")
							.replace(SearchConstants.SEARCH_LANGUAGE_KEY_SELECTOR, languageKey));
			LOGGER.debug("getSynonymResultsEndpoint {} ", requestUrl);
			LOGGER.debug("getSynonymResultsEndpoint- requestObject {} ", requestObject);
			BaseRequest baseRequest = new BaseRequestImpl(requestUrl.toString(), HttpMethodType.GET,
					requestObject.toString());
			requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
			BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
			if (null != baseResponse.getError()) {
				LOGGER.error("Error in getSynonymResultsEndpoint {}", baseResponse.getError().getMessage());
				return baseResponse.getError().getMessage();
			} else if (null != baseResponse.getResponseData()) {
				return baseResponse.getResponseData().getData();
			} else {
				LOGGER.error("Blank Response in getSynonymResultsEndpoint");
				return CommonConstants.BLANK;
			}
		} catch (AemInternalServerErrorException | HttpSolrClient.RemoteSolrException e) {
			LOGGER.error("Exception in AemInternalServerErrorException in getSynonymResultsEndpoint {}",
					e.getMessage());
		}
		return StringUtils.EMPTY;
	}
	
	private JsonObject fetchResponseJson(JsonObject response, BaseResponse baseResponse, String country, String methodName) {
		String message = StringUtils.EMPTY;
		if (null != baseResponse.getError()) {
			LOGGER.error("Error in {} {}", methodName, baseResponse.getError().getMessage());
			message = baseResponse.getError().getMessage();
		} else if (null != baseResponse.getResponseData()) {
			message = baseResponse.getResponseData().getData();
		} else {
			LOGGER.error("Blank Response in {}", methodName);
			message = CommonConstants.BLANK;
		}
		
		if (!message.equals(CommonConstants.BLANK)) {
			JsonObject messageJson = new JsonParser().parse(message).getAsJsonObject();
			response.add(country, messageJson);
		} else {
			response.addProperty(country, "Failed to delete Synonyms");
		}
		return response;
	}
	
	/**
	 * Refreshing the Master Solr Data for all available cores
	 * 
	 * @return baseResponse
	 */
	private BaseResponse refreshSolrData(String country) {
		StringBuilder requestUrl = new StringBuilder();
		JsonObject requestObject = new JsonObject();
		BaseResponse baseResponse = null;
		final Map<String, String> requestHeaderMap = new HashMap<>();
		try {
			requestUrl = requestUrl.append(bdbSearchEndpointService.getSolrUrl())
					.append(bdbSearchEndpointService.getRefreshSolrCollection())
					.append(bdbSearchEndpointService.getContentPageCollectionName()).append(CommonConstants.HYPHEN)
					.append(country.toLowerCase());
			LOGGER.debug("Refresh master solr endpoint for request url: {} for country {}", requestUrl, country);
			BaseRequest baseRequest = new BaseRequestImpl(requestUrl.toString(), HttpMethodType.GET,
					requestObject.toString());
			requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
			baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
		} catch (AemInternalServerErrorException e) {
			LOGGER.error("Error Refresh Solr Domain Data ", e);
		}
		return baseResponse;
	}
	
	/**
	 * Refreshing the Slave Solr Data for all available cores.
	 * 
	 * @return base response for all slave server
	 */
	private ArrayList<BaseResponse>refreshSlaveSolrData(String country) {
		StringBuilder requestUrl;
		JsonObject requestObject = new JsonObject();
		BaseResponse baseResponse = null;
		final Map<String, String> requestHeaderMap = new HashMap<>();
		ArrayList<BaseResponse>slaveUrlResponse = new ArrayList<BaseResponse>();
		String slaveSolr = bdbSearchEndpointService.getSlaveSolrUrl();
		if(null != slaveSolr) {
			String[] slaveSolrServer = bdbSearchEndpointService.getSlaveSolrUrl().split(",");
			if (null != slaveSolrServer && slaveSolrServer.length > 0) {
				for (String slaveServerUrl : slaveSolrServer) {
					try {
						requestUrl = new StringBuilder();
						requestUrl = requestUrl.append(slaveServerUrl)
								.append(bdbSearchEndpointService.getRefreshSolrCollection())
								.append(bdbSearchEndpointService.getContentPageCollectionName()).append(CommonConstants.HYPHEN)
								.append(country.toLowerCase());
						LOGGER.debug("Refresh slave solr endpoint for request url: {} for country {}", requestUrl, country);
						BaseRequest baseRequest = new BaseRequestImpl(requestUrl.toString(), HttpMethodType.GET,
								requestObject.toString());
						requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
						baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
					} catch (AemInternalServerErrorException | HttpSolrClient.RemoteSolrException rse) {
						LOGGER.error("Error while Connecting to Slave Server with url: {} for country {} throwing exception {}", slaveServerUrl, country, rse);
					}
					slaveUrlResponse.add(baseResponse);
				}
			}
		}
		return slaveUrlResponse;
	}
	
	/**
	 * method to reload master and slave solr server.
	 * 
	 * @throws LoginException
	 */
	private void reloadSolrServer(SlingHttpServletRequest request, ResourceResolver resourceResolver) throws LoginException {
		ArrayList<String> countriesList = (ArrayList<String>) solrSearchService.getAllCountries(resourceResolver);
		for (String country : countriesList) {
			try {
				String message = StringUtils.EMPTY;
				BaseResponse refreshResponse = refreshSolrData(country);
				if (null != refreshResponse && null != refreshResponse.getResponseData()) {
					message = refreshResponse.getResponseData().getData();
					LOGGER.debug("Response for master solr: {} for country: {}", message, country);
				}
				ArrayList<BaseResponse>refreshSlaveResponse = refreshSlaveSolrData(country);
				if(refreshSlaveResponse.size()>0) {
					for(BaseResponse slaveResponse:refreshSlaveResponse) {
						if (null != slaveResponse && null != slaveResponse.getResponseData()) {
							message = slaveResponse.getResponseData().getData();
							LOGGER.debug("Response for slave solr: {} for country: {}", message, country);
						}
					}
				}
			} catch (HttpSolrClient.RemoteSolrException e) {
				LOGGER.error("Exception in reloading solr server in reloadSolrServer {}",
						e.getMessage());
			}
		}
	}
}