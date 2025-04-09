package com.bdb.aem.core.api.request.impl;

import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.util.HttpMethodType;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class BaseRequestImpl.
 */
public class BaseRequestImpl implements BaseRequest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseRequestImpl.class);

	/** The url. */
	private String url;
	
	/** The methodtype. */
	private HttpMethodType methodtype;
	
	/** The data. */
	private String data;
	
	/** The name value pairs. */
	private List<NameValuePair> nameValuePairs;

	/**
	 * Instantiates a new base request impl.
	 *
	 * @param url the url
	 * @param method the method
	 * @param data the data
	 */
	public BaseRequestImpl(final String url, final HttpMethodType method, final String data) {
		this.url = url;
		this.methodtype = method;
		this.data = data;
	}

	/**
	 * Instantiates a new base request impl.
	 *
	 * @param url the url
	 * @param method the method
	 * @param nameValuePairs the name value pairs
	 */
	public BaseRequestImpl(final String url, final HttpMethodType method, final List<NameValuePair> nameValuePairs) {
		this.url = url;
		this.methodtype = method;
		this.nameValuePairs = new ArrayList<>(nameValuePairs);
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * Gets the name value pairs.
	 *
	 * @return the name value pairs
	 */
	public List<NameValuePair> getNameValuePairs() {
		return new ArrayList<>(nameValuePairs);
	}

	/**
	 * Gets the http method type.
	 *
	 * @return the http method type
	 */
	public HttpMethodType gethttpMethodType() {
		return methodtype;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public HttpMethod getMethod() {
		switch (methodtype) {
		case GET:
			return caseGet();
		case PUT:
			return new PutMethod(url);
		case POST:
			return new PostMethod(url);
		case DELETE:
			return new DeleteMethod(url);
		default:
			return new GetMethod(url);
		}
	}

	/**
	 * Case get.
	 *
	 * @return the http method
	 */
	private HttpMethod caseGet() {
		try {
			return new GetMethod(new URI(url, false).toString());
		} catch (URIException e) {
			LOGGER.error("URIException", e);
		}
		return null;
	}

}
