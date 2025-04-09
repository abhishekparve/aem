package com.bdb.aem.core.services;

import org.apache.sling.api.SlingHttpServletRequest;

import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;

/**
 * The Interface FetchBearerTokenService.
 */
public interface FetchBearerTokenService {

	/**
	 * Fetch auth token.
	 *
	 * @param request the request
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
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
	 * Gets the auth token api endpoint.
	 *
	 * @param request the request
	 * @return the auth token api endpoint
	 */
	public String getAuthTokenApiEndpoint(SlingHttpServletRequest request);

}
