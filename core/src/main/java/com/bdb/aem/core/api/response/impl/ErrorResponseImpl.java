package com.bdb.aem.core.api.response.impl;

import com.bdb.aem.core.api.response.ErrorResponse;

/**
 * The Class ErrorResponseImpl.
 */
public class ErrorResponseImpl implements ErrorResponse {

	/** The error code. */
	private int errorCode;

	/** The error message. */
	private String message;

	/**
	 * Instantiates a new error response impl.
	 *
	 * @param code    the code
	 * @param key     the key
	 * @param message the message
	 */
	public ErrorResponseImpl(final int code, final String message) {
		super();
		this.errorCode = code;
		this.message = message;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getMessage() {
		return message;
	}

}
