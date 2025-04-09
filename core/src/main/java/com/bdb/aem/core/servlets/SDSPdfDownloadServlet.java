package com.bdb.aem.core.servlets;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.exceptions.InvalidRequestException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.SDSDocumentSearchService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * The Class SDSPdfDownloadServlet.
 */
@Component(name = "SDSPdfDownload", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "SDSPdfDownloadServlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + SDSPdfDownloadServlet.RESOURCE_TYPE
})
public class SDSPdfDownloadServlet extends SlingAllMethodsServlet {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/download-sds-pdf";
	
	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SDSPdfDownloadServlet.class);

	/** The bdb api endpoint service. */
	@Reference
	private transient BDBApiEndpointService bdbApiEndpointService;

	/** The sds document service. */
	@Reference
	private transient SDSDocumentSearchService sdsDocumentService;

	/** The http client. */
	private transient CloseableHttpClient httpClient;

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
		LOGGER.debug("Entry doGet of SDSPdfDownloadServlet");
		int scketTimeoutConfig = bdbApiEndpointService.getSocketTimeoutConfig();
		int requestTimeoutConfig = bdbApiEndpointService.getRequestTimeoutConfig();
		int connectTimeoutConfig = bdbApiEndpointService.getConnectTimeoutConfig();

		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
				.setSocketTimeout(scketTimeoutConfig * 1000).setConnectTimeout(connectTimeoutConfig * 1000)
				.setConnectionRequestTimeout(requestTimeoutConfig * 1000).build();

		String fileID = StringUtils.EMPTY;
		String language = StringUtils.EMPTY;
		String country = StringUtils.EMPTY;
		String materialNumber = StringUtils.EMPTY;
		RequestPathInfo requestPath = request.getRequestPathInfo();
		LOGGER.debug("SDSPdfDownloadServlet requestPath {}", requestPath);
		if(requestPath.getSelectors().length != 0) {
			String[] reqSelectors = requestPath.getSelectors();
			LOGGER.debug("SDSPdfDownloadServlet Request Selectors {}", Arrays.toString(reqSelectors));
			if (reqSelectors.length == 1) {
				fileID = reqSelectors[0];
				LOGGER.debug("Field ID {}", fileID);
			} else if (reqSelectors.length >= 3) {
				country = reqSelectors[0];
				language = reqSelectors[1];
				materialNumber = reqSelectors[2];
				country = CommonHelper.isCountryEU(country);
				LOGGER.debug("SDS Search Input Country {}", country);
				LOGGER.debug("SDS Search Input materialNumber {}", materialNumber);
			}
		}

		HttpResponse conn = null;
		try {
			if (!StringUtils.isEmpty(fileID)) {
				conn = sdsDocumentService.downloadSDSDocument(fileID, requestConfig);
			} else {
				JsonArray sdsArray = sdsDocumentService.searchSDSDocument(country, materialNumber);
				LOGGER.debug("SDS Search Response {}", sdsArray);
				conn = getStandaloneResponse(requestConfig, language, conn, sdsArray);
			}
			if (null != conn && conn.getStatusLine().getStatusCode() == 200) {
				LOGGER.debug("Connection Scuccessfull Status Code {}", conn.getStatusLine().getStatusCode());
				InputStream is = conn.getEntity().getContent();
				IOUtils.copy(is, pdfStream);
			} else {
				LOGGER.error("DataSheet PDF Download Read Request Timed Out");
				if(null != conn && null != conn.getStatusLine()) {
					LOGGER.error("Connection Failed Status Code {}", conn.getStatusLine().getStatusCode());
				}
				LOGGER.error("until-successful' retries exhausted. Last exception message was: HTTP POST on resource 'https://qaregdocs.bdx.com:443/regulatory/api/viewPDF' failed: not acceptable (406)");
				response.sendError(404);
			}

		} catch (AemInternalServerErrorException e) {
			response.sendError(404);
			LOGGER.error("AemInternalServerErrorException {0}", e);
		} finally {
			if(response.getStatus() != 404) {
			response.setContentType(CommonConstants.APPLICATION_PDF);
			response.setCharacterEncoding("UTF-8");
			response.getOutputStream().write(response.getStatus());
			response.getOutputStream().write(pdfStream.toByteArray());
			response.getOutputStream().write(response.getStatus());
			}
		}
		LOGGER.error("Exit DataSheet PDF Download Servlet Activate");
	}

	/**
	 * Gets the standalone response.
	 *
	 * @param requestConfig the request config
	 * @param language      the language
	 * @param conn          the conn
	 * @param sdsArray      the sds array
	 * @return the standalone response
	 * @throws AemInternalServerErrorException 
	 */
	public HttpResponse getStandaloneResponse(RequestConfig requestConfig, String language, HttpResponse conn,
											  JsonArray sdsArray) throws AemInternalServerErrorException {
		LOGGER.debug("SDS Seaarch Array {}", sdsArray);
		for (JsonElement arrayItem : sdsArray) {
			JsonObject searchObj = arrayItem.getAsJsonObject();
			String languageCodeValue = getJsonProperty(searchObj, "language");
			if (language.equalsIgnoreCase(languageCodeValue)) {
				String id = getJsonProperty(searchObj, CommonConstants.ID);
				if (null != id) {
					conn = sdsDocumentService.downloadSDSDocument(id, requestConfig);
				}
			}
		}
		return conn;
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
	 * Deactivate.
	 */
	@Deactivate
	public void deactivate() {
		LOGGER.debug("DataSheet PDF download servlet Deactivated");
		try {
			if (null != httpClient) {
				httpClient.close();
			}
		} catch (IOException e) {
			LOGGER.error("IOException during closing of httpClient", e);
		}
	}
}
