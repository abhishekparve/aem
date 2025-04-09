package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class DashboardConfig.
 */
public class DashboardConfig {

    /** the getMessages */
    @SerializedName("getMessages")
    private PayloadConfig getMessages;

    /** the readMessage */
    @SerializedName("readMessage")
    private PayloadConfig readMessage;

    /** the getOrders */
    @SerializedName("getRecentOrders")
    private PayloadConfig getOrders;

    /** the getQuotes */
    @SerializedName("getRecentQuotes")
    private PayloadConfig getQuotes;


    public DashboardConfig(PayloadConfig getMessages,
                           PayloadConfig readMessage,
                           PayloadConfig getOrders,
                           PayloadConfig getQuotes) {
        this.getMessages = getMessages;
        this.readMessage = readMessage;
        this.getOrders = getOrders;
        this.getQuotes = getQuotes;
    }
}
