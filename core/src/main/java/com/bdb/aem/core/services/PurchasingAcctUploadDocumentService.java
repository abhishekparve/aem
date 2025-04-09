package com.bdb.aem.core.services;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;

/**
 * The Interface PurchasingAcctUploadDocumentService.
 */
public interface PurchasingAcctUploadDocumentService {

	/**
	 * Fetch user details.
	 *
	 * @param request    the request
	 * @param response   the response
	 * @param queryParam the query param
	 * @return 
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	public String uploadDocument(SlingHttpServletRequest request, SlingHttpServletResponse response) throws AemInternalServerErrorException;

}
