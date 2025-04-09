package com.bdb.aem.core.api.response;

/**
 * The Interface ErrorResponse.
 */
public interface ErrorResponse {

    /**
     * Gets the error code.
     *
     * @return the error code
     */
    int getErrorCode();

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    String getMessage();

}
