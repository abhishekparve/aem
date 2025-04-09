package com.bdb.aem.core.services;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.google.gson.JsonObject;

/**
 * The Interface SSOLoginService.
 */
public interface SSOLoginService {

	/**
	 * Fetch user details.
	 *
	 * @param request    the request
	 * @param response   the response
	 * @param queryParam the query param
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	public String fetchUserDetails(SlingHttpServletRequest request, SlingHttpServletResponse response,
			Map<String, String> queryParam) throws AemInternalServerErrorException;

	/**
	 * Sets the failure response cookies.
	 *
	 * @param response  the response
	 * @param errorCode the error code
	 * @throws UnsupportedEncodingException 
	 */
	public void setFailureResponseCookies(SlingHttpServletResponse response, String errorCode) throws UnsupportedEncodingException;

	/**
	 * Checks if is required query param available.
	 *
	 * @param queryParams the query params
	 * @return true, if is required query param available
	 */
	public boolean isRequiredQueryParamAvailable(Map<String, String> queryParams);

	/**
	 * Removes the failure cookies.
	 *
	 * @param response the response
	 * @param isSecure the is secure
	 */
	public void removeFailureCookies(SlingHttpServletResponse response, Boolean isSecure);

	/**
	 * Refresh tokens.
	 *
	 * @param request the request
	 * @param response the response
	 * @param jsonObject the json object
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	public void refreshTokens(SlingHttpServletRequest request, SlingHttpServletResponse response, JsonObject jsonObject)
			throws AemInternalServerErrorException;
			
		/**
	 * Checks if is redirect url valid.
	 *
	 * @param resourceResolver the resource resolver
	 * @param queryParams the query params
	 * @return true, if is redirect url valid
	 */
	String getRedirectUrl(ResourceResolver resourceResolver, Map<String, String> queryParams);
	
	/**
	 * Update redirect url as per country.
	 *
	 * @param resourceResolver the resource resolver
	 * @param queryParams the query params
	 * @return true, if is redirect url valid
	 */
	String getRegionRedirectUrl(String redirectUrl, String coutnryCode);

	/**
	 * Checks if is valid url.
	 *
	 * @param redirectUrl the redirect url
	 * @param queryParams the query params
	 * @return true, if is valid url
	 */
	boolean isValidUrl(String redirectUrl, Map<String, String> queryParams);

}
