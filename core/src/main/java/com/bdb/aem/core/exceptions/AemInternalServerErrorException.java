package com.bdb.aem.core.exceptions;


/**
 * The Class AemInternalServerErrorException.
 */
public class AemInternalServerErrorException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Instantiates a new aem internal server error exception.
	 *
	 * @param message the message
	 */
	public AemInternalServerErrorException(String message) {
		// Call constructor of parent Exception
		super(message);
	}
	
	/**
	 * Instantiates a new aem internal server error exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public AemInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
