package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class PayloadConfig.
 */
public class PayloadConfig {

    /**  the requestPayload. */
    @SerializedName("requestPayload")
    private Payload requestPayload;

    /**
     * Instantiates a new sign up config.
     *
     * @param  requestPayload
     */
    public PayloadConfig(Payload requestPayload){

        this.requestPayload = requestPayload;
    }

    /**
     * Gets the requestPayload .
     *
     * @return the requestPayload
     */
    public Payload getRequestPayload() {
        return requestPayload;
    }



}
