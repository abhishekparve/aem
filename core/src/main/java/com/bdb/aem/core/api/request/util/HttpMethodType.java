package com.bdb.aem.core.api.request.util;

/**
 * The Enum HttpMethodType.
 */
public enum HttpMethodType {

	/** select. */
	GET,

	/** edit. */
	POST,

	/** add. */
	PUT,

	/** DELETE. */
	DELETE;

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return name();
	}
}
