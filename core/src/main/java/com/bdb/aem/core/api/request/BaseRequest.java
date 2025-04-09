package com.bdb.aem.core.api.request;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.http.NameValuePair;

import com.bdb.aem.core.api.request.util.HttpMethodType;

import java.util.List;

/**
 * The Interface BaseRequest.
 */
public interface BaseRequest {

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	String getUrl();

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	HttpMethod getMethod();

	/**
	 * Gets the http method type.
	 *
	 * @return the http method type
	 */
	HttpMethodType gethttpMethodType();

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	String getData();

	/**
	 * Gets the name value pairs.
	 *
	 * @return the name value pairs
	 */
	List<NameValuePair> getNameValuePairs();

}
