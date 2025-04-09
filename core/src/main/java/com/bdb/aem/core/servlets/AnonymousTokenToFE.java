package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.impl.BaseRequestImpl;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.FetchBearerTokenService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;

@Component(service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "AnonymousTokenToFE",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + AnonymousTokenToFE.RESOURCE_TYPE
})
public class AnonymousTokenToFE extends SlingAllMethodsServlet{
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/bdb-anonymous-token";
	
	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AnonymousTokenToFE.class);

	/**
	 * The bdb api endpoint service.
	 */
	@Reference
	private transient BDBApiEndpointService bdbApiEndpointService;

	/** The rest client. */
	@Reference
	private transient RestClient restClient;
	
	@Reference
	private transient FetchBearerTokenService fetchAnonymousToken;
	

	/**
     * Get Method.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
    	
		/**
		* JSON Declarations in use
		*/
    	JsonObject requestObject = new JsonObject();
		JsonObject accessToken = new JsonObject();
		JsonObject responseData = new JsonObject();
		
		PrintWriter writer;
		String endPoint = fetchAnonymousToken.getAuthTokenApiEndpoint(request);
		LOGGER.debug("end point : "+endPoint);
		
		if (StringUtils.isNotBlank(endPoint)) {
			
			final Map<String, String> requestHeaderMap = new HashMap<>();
			final BaseRequest baseRequest = new BaseRequestImpl(endPoint, HttpMethodType.POST,requestObject.toString());
			
			try {
					accessToken = getAccessToken(restClient.sendRequest(baseRequest, requestHeaderMap));
					LOGGER.debug("Token data as a JSON : "+accessToken);
					if (null != accessToken) {
						JsonElement tokenElement = accessToken.get(CommonConstants.ACCESS_TOKEN);
						JsonElement expire_timeElement = accessToken.get(CommonConstants.ACCESS_TOKEN_EXPIRY);
						if(null!=tokenElement)responseData.add(CommonConstants.ACCESS_TOKEN, tokenElement);
						if(null!=expire_timeElement)responseData.add(CommonConstants.ACCESS_TOKEN_EXPIRY, expire_timeElement);
					}
				} 
			/**
			 * @returns Bad Response with the Error Message 
			 * */
			catch (AemInternalServerErrorException e) {
					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
					response.setHeader("Expires", "0"); // Proxies.
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().write(e.getMessage());
					LOGGER.error("Exception {}", e.getMessage());
				}
			/**
			 * Posts the Data as a JSON format 
			 * Token and Expire Time.
			 * */
			finally {
					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
					response.setHeader("Expires", "0"); // Proxies.
					response.setContentType(CommonConstants.APPLICATION_JSON);
					response.setCharacterEncoding("UTF-8");
					writer = response.getWriter();
					if (null != writer) {
						writer.write(responseData.toString());
						writer.flush();
						writer.close();
					}
				}
		}
		/**
		 * @References from common constants 
		 * CommonConstants.ACCESS_TOKEN = access_token
		 * CommonConstants.ACCESS_TOKEN_EXPIRY = expires_in
		 */
    }
	/**
	 * Gets the access token JSON data.
	 *
	 * @param baseResponse the base response
	 * @return the access token JSON Data 
	 */
	public JsonObject getAccessToken(BaseResponse baseResponse) {
		LOGGER.debug("Entry getAccessToken of FetchBearerTokenServiceImpl");
		JsonObject convertedObject= new JsonObject();
		if (!baseResponse.hasError()) {
			String jsonResponse = baseResponse.getResponseData().getData();
			convertedObject = new Gson().fromJson(jsonResponse, JsonObject.class);
		}
		LOGGER.debug("Exit getAccessToken of FetchBearerTokenServiceImpl");
		return convertedObject;
	}

}
