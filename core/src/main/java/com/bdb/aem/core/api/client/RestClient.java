package com.bdb.aem.core.api.client;

import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;

/**
 * The Interface RestClient.
 */
public interface RestClient {

	/**
	 * Send request with body string.
	 *
	 * @param request   the request
	 * @param headerMap the header map
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	BaseResponse sendRequest(final BaseRequest request, final Map<String, String> headerMap) throws AemInternalServerErrorException;
	
	/**
	 * Gets the http client.
	 *
	 * @return the http client
	 */
	CloseableHttpClient getHttpClient();
	
	/**
	 * Sets the BDB Api end point service.
	 *
	 * @param bDBApiEndpointService the new BDB Api end point service
	 */
	public void setBDBApiEndpointService(BDBApiEndpointService bDBApiEndpointService);

	/**
	 * Send request upload.
	 *
	 * @param postMethod the post method
	 * @param headerMap the header map
	 * @return the base response
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	BaseResponse sendRequestUpload(HttpPost postMethod, Map<String, String> headerMap)
			throws AemInternalServerErrorException;

}
