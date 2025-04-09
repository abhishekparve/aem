package com.bdb.aem.core.api.response.impl;


import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.api.response.DataResponse;
import com.bdb.aem.core.api.response.ErrorResponse;

/**
 * The Class BaseResponseImpl.
 */
public class BaseResponseImpl implements BaseResponse {

    /** The error. */
    private ErrorResponse error;
    
    /** The data. */
    private DataResponse data;

    /**
     * Checks for error.
     *
     * @return true, if successful
     */
    public boolean hasError() {
        return error != null;
    }

    /**
     * Gets the error.
     *
     * @return the error
     */
    public ErrorResponse getError() {
        return error;
    }

    /**
     * Sets the error.
     *
     * @param error the new error
     */
    public void setError(final ErrorResponse error) {
        this.error = error;
    }

    /**
     * Gets the response data.
     *
     * @return the response data
     */
    public DataResponse getResponseData() {
        return data;
    }

    /**
     * Sets the response data.
     *
     * @param data the new response data
     */
    public void setResponseData(final DataResponse data) {
        this.data = data;
    }

}
