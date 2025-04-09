package com.bdb.aem.core.api.response.impl;

import com.bdb.aem.core.api.response.DataResponse;

/**
 * The Class DataResponseImpl.
 */
public class DataResponseImpl implements DataResponse {

	/** The data. */
	private String data;

	/** The data. */
	private int statusCode;

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(final String data) {
		this.data = data;
	}

	/**
	 * Gets the status code.
	 *
	 * @return the status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode the new status code
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
