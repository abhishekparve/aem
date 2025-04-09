package com.bdb.aem.core.servlets.solr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
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
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.EligibleProductsApiService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * The Servlet punchout service.
 */
@Component(service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Punch Out Auth Servlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + PunchOutAuthServlet.RESOURCE_TYPE
})
public class PunchOutAuthServlet extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PunchOutAuthServlet.class);
	
	public static final String RESOURCE_TYPE = "bdb/punch-out-authentication";

	@Reference
	transient EligibleProductsApiService eligibleProductsApiService;

	@Reference
	transient BDBApiEndpointService bdbApiEndpointService;

	@Reference
	transient RestClient restClient;
	
	@Reference
	transient ExternalizerService externalizerService;
	
	@Reference
	transient ResourceResolverFactory resolverFactory;

	transient JsonObject queryResponseJson = new JsonObject();
	transient JsonObject finalResponseJson = new JsonObject();
	String formattedCartPage = StringUtils.EMPTY;
	String formattedlandingPage = StringUtils.EMPTY;
	String redirectedPage = StringUtils.EMPTY;
	
	public static final String SPECIAL_CHAR = "(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)" ;

	/**
	 * doGet method which gets the request from hybris and redirects the user to required page.
	 *
	 * @param request  param
	 * @param response response param
	 * @throws IOException
	 */
	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		String apiResponse = StringUtils.EMPTY;
		JsonObject requestObject = new JsonObject();

		Map<String, String[]> parameterMap = request.getParameterMap();
		
		ResourceResolver resolver = null;
		try {		
			resolver = request.getResourceResolver();
			String regionCountryLocal = formRegionCountryLocalePath(parameterMap);
			Resource resource = resolver.getResource(CommonConstants.CONTENT+ "/bdb" + regionCountryLocal);
			if(null != resource) {
				Page page = resource.adaptTo(Page.class);
				String cartPage = CommonHelper.getLabel("mycartPagePath", page);
				String homePath = CommonHelper.getLabel("homePagePath", page);
				
				if (null != parameterMap) {
					constructResuestObject(requestObject, parameterMap);
					if (StringUtils.isNotBlank(cartPage) && StringUtils.isNotBlank(homePath)) {
						formattedCartPage = externalizerService.getFormattedUrl(cartPage, resolver);
						formattedlandingPage = externalizerService.getFormattedUrl(homePath, resolver);
					}
	
					if (!requestObject.isJsonNull()) {
						apiResponse = commonEndPointCall(request, requestObject, response, parameterMap,resolver);
					}
				}
			}
		} catch (AemInternalServerErrorException | IllegalArgumentException e) {
			apiResponse = e.getMessage();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			LOGGER.error("Exception Occurred during rest client execution {}", e);
		}finally {
			response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(apiResponse);
			CommonHelper.closeResourceResolver(resolver);
			if(StringUtils.isNotEmpty(redirectedPage)) {
				response.sendRedirect(redirectedPage);
			}
		}
	}
	
	public String formRegionCountryLocalePath(Map<String, String[]> parameterMap) {
		String path= StringUtils.EMPTY;
		String country= StringUtils.EMPTY;
		if(parameterMap.containsKey(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.REGION)) || parameterMap.containsKey(CommonConstants.REGION)) {
			String region;
			if(null != parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.REGION))) {
				region = parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.REGION))[0].toLowerCase();
			}else {
				region = parameterMap.get(CommonConstants.REGION)[0].toLowerCase();
			}
			path = path + CommonConstants.SINGLE_SLASH +region;
		}
		if(parameterMap.containsKey(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.COUNTRY)) || parameterMap.containsKey(CommonConstants.COUNTRY)) {
			if(null != parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.COUNTRY))) {
				country = parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.COUNTRY))[0].toLowerCase();
			}else {
				country = parameterMap.get(CommonConstants.COUNTRY)[0].toLowerCase();
			}
			path = path + CommonConstants.SINGLE_SLASH +country;
		}
		
		if(parameterMap.containsKey(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.LANGUAGE)) || parameterMap.containsKey(CommonConstants.LANGUAGE)) {
			String language;
			if(null != parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.LANGUAGE))) {
				language = parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.LANGUAGE))[0].toLowerCase();
			}else {
				language = parameterMap.get(CommonConstants.LANGUAGE)[0].toLowerCase();
			}
			path = path + CommonConstants.SINGLE_SLASH + language + CommonConstants.HYPHEN + country;
		}
		
		return path;
	}

	/**
	 * @param requestObject
	 * @param parameterMap
	 * @throws UnsupportedEncodingException
	 */
	public void constructResuestObject(JsonObject requestObject, Map<String, String[]> parameterMap)
			throws UnsupportedEncodingException {
		if (parameterMap.containsKey(CommonConstants.KEY)) {
			addPropertyToObject(CommonConstants.AUTH_KEY,CommonConstants.KEY,requestObject, parameterMap);
		}

		if(parameterMap.containsKey(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.SID))) {
			addPropertyToObject(CommonConstants.SID,CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.SID),requestObject, parameterMap);
		}else if(parameterMap.containsKey(CommonConstants.SID)){
			addPropertyToObject(CommonConstants.SID,CommonConstants.SID,requestObject, parameterMap);
		}
		
		if(parameterMap.containsKey(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.CART_ID))) {
			addPropertyToObject("cartID",CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.CART_ID),requestObject, parameterMap);
		}else if(parameterMap.containsKey(CommonConstants.CART_ID)){
			addPropertyToObject("cartID",CommonConstants.CART_ID,requestObject, parameterMap);
		}
		
		if(parameterMap.containsKey(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.OPERATION))) {
			addPropertyToObject(CommonConstants.OPERATION,CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.OPERATION),requestObject, parameterMap);
		}else if(parameterMap.containsKey(CommonConstants.OPERATION)){
			addPropertyToObject(CommonConstants.OPERATION,CommonConstants.OPERATION,requestObject, parameterMap);
		}
		
		
		if(parameterMap.containsKey(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.PRODUCT_CODE_KEYWORD))) {
			addPropertyToObject(CommonConstants.PRODUCT_CODE_KEYWORD,CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.PRODUCT_CODE_KEYWORD),requestObject, parameterMap);
		}else if(parameterMap.containsKey(CommonConstants.PRODUCT_CODE_KEYWORD)) {
			addPropertyToObject(CommonConstants.PRODUCT_CODE_KEYWORD,CommonConstants.PRODUCT_CODE_KEYWORD,requestObject, parameterMap);
		}
		
		if(parameterMap.containsKey(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.DISPLAY_SHIPTO))) {
			addPropertyToObject(CommonConstants.DISPLAY_SHIPTO,CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.DISPLAY_SHIPTO),requestObject, parameterMap);
		}else if(parameterMap.containsKey(CommonConstants.DISPLAY_SHIPTO)) {
			addPropertyToObject(CommonConstants.DISPLAY_SHIPTO,CommonConstants.DISPLAY_SHIPTO,requestObject, parameterMap);
		}
		
		if(parameterMap.containsKey(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.ADDRESS_ID))) {
			addPropertyToObject(CommonConstants.ADDRESS_ID,CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.ADDRESS_ID),requestObject, parameterMap);
		}else if(parameterMap.containsKey(CommonConstants.ADDRESS_ID)) {
			addPropertyToObject(CommonConstants.ADDRESS_ID,CommonConstants.ADDRESS_ID,requestObject, parameterMap);
		}
		
		if(parameterMap.containsKey(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.DISPLAY_MY_ORDERS))) {
			addPropertyToObject(CommonConstants.DISPLAY_MY_ORDERS,CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.DISPLAY_MY_ORDERS),requestObject, parameterMap);
		}else if(parameterMap.containsKey(CommonConstants.DISPLAY_MY_ORDERS)) {
			addPropertyToObject(CommonConstants.DISPLAY_MY_ORDERS,CommonConstants.DISPLAY_MY_ORDERS,requestObject, parameterMap);
		}
		
		
		
	}



	private void addPropertyToObject(String propertyValue, String valueInMap,JsonObject requestObject, Map<String, String[]> parameterMap) throws UnsupportedEncodingException {
		
		if(StringUtils.equals(propertyValue, "authKey")) {
		String encodeValue = URLEncoder.encode(parameterMap.get(valueInMap)[0], StandardCharsets.UTF_8.toString());
		
		String decodeValue = URLDecoder.decode(encodeValue, StandardCharsets.UTF_8.toString());
		
		requestObject.addProperty(propertyValue,decodeValue);
		LOGGER.debug("authKey value {}", decodeValue);
		}else {
			requestObject.addProperty(propertyValue,parameterMap.get(valueInMap)[0]);
			LOGGER.debug("parameter {} and its value {}", propertyValue,parameterMap.get(valueInMap)[0]);
		}
			
		
		
		
	}
	
	/**
	 * Making the endpoint and setting the cookie
	 * 
	 * @param request
	 * @param requestObject
	 * @param response
	 * @param parameterMap 
	 * @param resolver 
	 * @return
	 * @throws AemInternalServerErrorException
	 * @throws IOException 
	 */
	public String commonEndPointCall(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response, Map<String, String[]> parameterMap, ResourceResolver resolver) throws AemInternalServerErrorException, IOException {
		LOGGER.debug("Entry commonEndPointCall of PunchOut Servlet");
		String responseString;
		String endPoint = createEndPointUrl(request);
		if (StringUtils.isNotEmpty(endPoint)) {
			final Map<String, String> requestHeaderMap = new HashMap<>();
			 requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE,
			CommonConstants.APPLICATION_JSON);
			
			final BaseRequest baseRequest = new BaseRequestImpl(endPoint, HttpMethodType.POST,
					requestObject.toString());
			BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);

			if (!baseResponse.hasError()) {
				response.setStatus(baseResponse.getResponseData().getStatusCode());
				responseString = baseResponse.getResponseData().getData();

				setCookieAndRedirect(response, responseString, parameterMap, resolver);
				LOGGER.debug("response has no error and cookie was set");
			} else {
				response.setStatus(baseResponse.getError().getErrorCode());
				responseString = baseResponse.getError().getMessage();
				
				LOGGER.debug("response has error");
			}

		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			responseString = CommonConstants.ACCESS_TOKEN_RETRIEVAL_ERROR;
			LOGGER.error("End point {}", endPoint);
		}
		LOGGER.debug("Exit commonEndPointCall of punchOut servlet");
		return responseString;
	}

	/**
	 * @param response
	 * @param responseString
	 * @param parameterMap 
	 * @param resolver 
	 * @throws IOException 
	 */
	public void setCookieAndRedirect(SlingHttpServletResponse response, String responseString, Map<String, String[]> parameterMap, ResourceResolver resolver)
			throws IOException {
		
		JsonObject responseJson = new Gson().fromJson(responseString, JsonObject.class);
		
		String pdpUrl = responseJson.get("pdpURL")!=null?responseJson.get("pdpURL").getAsString():StringUtils.EMPTY;
		String language;
		String country;
		String region;
		if(StringUtils.isNotBlank(pdpUrl)) {
			
			if(null != parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.LANGUAGE))) {
				language = parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.LANGUAGE))[0].toLowerCase();
			}else {
				language = parameterMap.get(CommonConstants.LANGUAGE)[0].toLowerCase();
			}
			if(null != parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.COUNTRY))) {
				country = parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.COUNTRY))[0].toLowerCase();
			}else {
				country = parameterMap.get(CommonConstants.COUNTRY)[0].toLowerCase();
			}
			if(null != parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.REGION))) {
				region = parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.REGION))[0].toLowerCase();
			}else {
				region = parameterMap.get(CommonConstants.REGION)[0].toLowerCase();
			}
		redirectedPage = CommonConstants.CONTENT+ CommonConstants.SINGLE_SLASH + CommonConstants.BDB + CommonConstants.SINGLE_SLASH + region + CommonConstants.SINGLE_SLASH + country + CommonConstants.SINGLE_SLASH+ language + CommonConstants.HYPHEN + country + pdpUrl;
		redirectedPage = externalizerService.getFormattedUrl(redirectedPage, resolver);
		}else {
			String operation = responseJson.get("operation")!=null?responseJson.get("operation").getAsString():StringUtils.EMPTY;
			if(operation.equalsIgnoreCase("create")) {
				redirectedPage = formattedlandingPage;
			}else if(operation.equalsIgnoreCase("edit") || operation.equalsIgnoreCase("inspect"))  {
				redirectedPage = formattedCartPage;
			}
		}
		
		int cookieExpiryTime = bdbApiEndpointService.getCookieExpiryTime();
		setPunchOutCookie(response, "punchOut", responseString.replaceAll(SPECIAL_CHAR, ""), cookieExpiryTime,
				CommonConstants.BOOLEAN_TRUE);
		
		setCookie(response, "auth_token", responseJson.get("hybrisToken").getAsString(), cookieExpiryTime,
				CommonConstants.BOOLEAN_TRUE);
		
		setCookie(response, "user_details_uid", responseJson.get("uid").getAsString(), cookieExpiryTime,
				CommonConstants.BOOLEAN_TRUE);
		
		setCookie(response, "user_details_cartid", responseJson.get("cartID").getAsString(), cookieExpiryTime,
				CommonConstants.BOOLEAN_TRUE);
		
		setCookie(response, "sso_token", responseJson.get("ssoToken").getAsString(), cookieExpiryTime,
				CommonConstants.BOOLEAN_TRUE);
		
		setCookie(response, "user_details_grantEnabled", String.valueOf(responseJson.get("grantEnabled").getAsBoolean()), cookieExpiryTime,
				CommonConstants.BOOLEAN_TRUE);
		
		setCookie(response, "user_details_isSmartCartUser", String.valueOf(responseJson.get("isSmartCartUser").getAsBoolean()), cookieExpiryTime,
				CommonConstants.BOOLEAN_TRUE);
		
		setCookie(response, "site_locale", fetchSiteLocalevalue(parameterMap), -1,
				CommonConstants.BOOLEAN_TRUE);
		
	}
	
	


	/**
	 * setCookie method which set the cookie for punchout request and as sameSite=none.
	 * This is added as user were having issue in diff. browser due to sameSite going as default.
	 */
	private void setCookie(SlingHttpServletResponse response, String cookieName, String cookieValue, int timeout,
			boolean isSecure) throws UnsupportedEncodingException {
		Cookie cookie = new Cookie(cookieName, URLEncoder.encode(cookieValue, CommonConstants.UTF_ENCODING));
		cookie.setPath(CommonConstants.SINGLE_SLASH);
		cookie.setMaxAge(timeout);
		cookie.setSecure(isSecure);
		cookie.setComment(org.eclipse.jetty.http.HttpCookie.SAME_SITE_NONE_COMMENT);
		response.addCookie(cookie);
		
	}

	private String fetchSiteLocalevalue(Map<String, String[]> parameterMap) {
		String language;
		String country;
		if(null != parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.LANGUAGE))) {
			language = parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.LANGUAGE))[0].toLowerCase();
		}else {
			language = parameterMap.get(CommonConstants.LANGUAGE)[0].toLowerCase();
		}
		if(null != parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.COUNTRY))) {
			country = parameterMap.get(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.COUNTRY))[0].toLowerCase();
		}else {
			country = parameterMap.get(CommonConstants.COUNTRY)[0].toLowerCase();
		}
		return language+CommonConstants.HYPHEN+country;
	}

	/**
	 * Creating the endpoint URL
	 * 
	 * @param request
	 * @return
	 */
	public String createEndPointUrl(SlingHttpServletRequest request) {
		LOGGER.debug("Entry createEndPointUrl of punchOut servlet");
		String endpoint = StringUtils.EMPTY;
		String siteId = CommonHelper.getSiteIdHeader(request);
		if (StringUtils.isBlank(siteId)) {
			siteId = "bdbUS";
		}
		LOGGER.debug("Site id : {}", siteId);
		StringBuilder endPointUrl = new StringBuilder();
		if (null != bdbApiEndpointService) {
			endPointUrl.append(bdbApiEndpointService.getBDBHybrisDomain().trim());

			endPointUrl.append(bdbApiEndpointService.getPunchOutEndpoint()); 

			endpoint = endPointUrl.toString();

			endpoint = StringUtils.replace(endpoint, CommonConstants.BASE_SITE_ID, siteId);
		} else {
			LOGGER.error("Could not get BdbEndpointService reference in punchOut servlet");
		}
		LOGGER.debug("Exit createEndPointUrl of punchOut servlet");
		return endpoint;
	}
	
	/**
	 * sets the cookie for space issue in puchout.
	 *
	 * @param response    the response
	 * @param cookieName  the cookie name
	 * @param cookieValue the cookie value
	 * @param timeout     the timeout
	 * @param isSecure    the is secure
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static void setPunchOutCookie(SlingHttpServletResponse response, String cookieName, String cookieValue, int timeout,
			boolean isSecure) throws UnsupportedEncodingException {

		Cookie cookie = new Cookie(cookieName, URLEncoder.encode(cookieValue, CommonConstants.UTF_ENCODING).replace("+", "%20"));
		cookie.setPath(CommonConstants.SINGLE_SLASH);
		cookie.setMaxAge(-1);
		cookie.setSecure(isSecure);
		cookie.setComment(org.eclipse.jetty.http.HttpCookie.SAME_SITE_NONE_COMMENT);
		response.addCookie(cookie);

	}

}
