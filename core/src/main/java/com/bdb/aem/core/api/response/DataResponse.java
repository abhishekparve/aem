package com.bdb.aem.core.api.response;

/**
 * The Interface DataResponse.
 */
public interface DataResponse {

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	String getData();

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	void setData(String data);

	/**
	 * Gets the status code.
	 *
	 * @return the status code
	 */
	public int getStatusCode();

	/**
	 * Sets the status code.
	 *
	 * @param statusCode the new status code
	 */
	public void setStatusCode(int statusCode);
}
