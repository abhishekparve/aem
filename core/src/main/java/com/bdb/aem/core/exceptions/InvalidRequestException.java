package com.bdb.aem.core.exceptions;

/**
 * The Class InvalidRequestException.
 */
public class InvalidRequestException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new invalid request exception.
	 *
	 * @param message the message
	 */
	public InvalidRequestException(String message) {
		// Call constructor of parent Exception
		super(message);
	}
}
