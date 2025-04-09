package com.bdb.aem.core.api.response;


/**
 * The Interface BaseResponse.
 */
public interface BaseResponse {

    /**
     * Checks for error.
     *
     * @return true, if successful
     */
    boolean hasError();

    /**
     * Gets the error.
     *
     * @return the error
     */
    ErrorResponse getError();

    /**
     * Sets the error.
     *
     * @param error the new error
     */
    void setError(ErrorResponse error);

    /**
     * Gets the response data.
     *
     * @return the response data
     */
    DataResponse getResponseData();

    /**
     * Sets the response data.
     *
     * @param data the new response data
     */
    void setResponseData(DataResponse data);
}
