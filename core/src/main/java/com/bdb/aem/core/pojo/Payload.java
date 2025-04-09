package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

public class Payload {

    /** the Payload Url */
    @SerializedName("url")
    private String url;

    /** the Payload Method */
    @SerializedName("method")
    private String method;

    public Payload(String url, String method) {
        this.url = url;
        this.method = method;
    }

    /**
     * Gets Payload Url.
     *
     * @return the Payload Url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets Payload Method.
     *
     * @return the Payload Method
     */

    public String getMethod() {
        return method;
    }
}
