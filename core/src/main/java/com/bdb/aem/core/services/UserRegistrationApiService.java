package com.bdb.aem.core.services;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.google.gson.JsonObject;

/**
 * The Interface UserRegistrationApiService.
 */
public interface UserRegistrationApiService {

	/**
	 * Fetch sign up details.
	 *
	 * @param request       the request
	 * @param requestObject the request object
	 * @param response      the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	public String fetchSignUpDetails(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response) throws AemInternalServerErrorException;

	/**
	 * Reset password.
	 *
	 * @param request       the request
	 * @param requestObject the request object
	 * @param response      the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	public String resetPassword(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response) throws AemInternalServerErrorException;

	/**
	 * Area of focus.
	 *
	 * @param request       the request
	 * @param requestObject the request object
	 * @param response      the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	public String areaOfFocus(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response) throws AemInternalServerErrorException;

	/**
	 * Purchase account.
	 *
	 * @param request       the request
	 * @param requestObject the request object
	 * @param response      the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	public String purchaseAccount(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response) throws AemInternalServerErrorException;

	/**
	 * Purchase account.
	 *
	 * @param request       the request
	 * @param requestObject the request object
	 * @param apiType       the api type
	 * @param response      the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	public String commonEndPointCall(SlingHttpServletRequest request, JsonObject requestObject, String apiType,
			SlingHttpServletResponse response) throws AemInternalServerErrorException;

	/**
	 * Fetch auth token.
	 *
	 * @param request the request
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	public String fetchAuthToken(SlingHttpServletRequest request) throws AemInternalServerErrorException;

	/**
	 * Gets the access token.
	 *
	 * @param baseResponse the base response
	 * @return the access token
	 */
	public String getAccessToken(BaseResponse baseResponse);

	/**
	 * Creates the end point url.
	 *
	 * @param serviceName the service name
	 * @param request     the request
	 * @return the string
	 */
	public String createEndPointUrl(String serviceName, SlingHttpServletRequest request);

	/**
	 * Gets the auth token api endpoint.
	 *
	 * @param request the request
	 * @return the auth token api endpoint
	 */
	public String getAuthTokenApiEndpoint(SlingHttpServletRequest request);

}
