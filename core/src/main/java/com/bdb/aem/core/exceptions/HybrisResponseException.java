package com.bdb.aem.core.exceptions;

/**
 * The Class HybrisResponseException.
 */
public class HybrisResponseException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new hybris response exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public HybrisResponseException(String message, Throwable cause) {
		super(message, cause);
	}
}
