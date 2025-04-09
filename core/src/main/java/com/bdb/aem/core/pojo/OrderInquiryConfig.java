package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;
/**
 * The Class OrderInquiryConfig.
 */
public class OrderInquiryConfig {

    /** the getOrderInquiryDetail */
    @SerializedName("getOrderInquiryDetail")
    private PayloadConfig getOrderInquiryDetail;

    /** the postOrderInquiryDetails */
    @SerializedName("postOrderInquiryDetails")
    private PayloadConfig postOrderInquiryDetails;


    public OrderInquiryConfig(PayloadConfig getOrderInquiryDetail, PayloadConfig postOrderInquiryDetails) {
        this.getOrderInquiryDetail = getOrderInquiryDetail;
        this.postOrderInquiryDetails = postOrderInquiryDetails;
    }


}
