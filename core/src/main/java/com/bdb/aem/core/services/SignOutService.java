package com.bdb.aem.core.services;

import java.io.UnsupportedEncodingException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.google.gson.JsonObject;


/**
 * The Interface SignOutService.
 */
public interface SignOutService {


	/**
	 * Fetch sign out details.
	 *
	 * @param request the request
	 * @param requestObject the request object
	 * @param response the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public Boolean fetchSignOutDetails(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response)  throws AemInternalServerErrorException ;

	/**
	 * Creates the end point url.
	 *
	 * @param request the request
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public String createEndPointUrl(SlingHttpServletRequest request) ;
}
