package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class PaymentsConfig.
 */
public class PaymentsConfig {

    /**  the getPayments. */
    @SerializedName("getPayments")
    private PayloadConfig getPayments;

    /**  the addCreditCard. */
    @SerializedName("addCreditCard")
    private PayloadConfig addCreditCard;

    /**  the removeCreditCard. */
    @SerializedName("removeCreditCard")
    private PayloadConfig removeCreditCard;

    /**  the updateCreditCard. */
    @SerializedName("updateCreditCard")
    private PayloadConfig updateCreditCard;
    
    /** The get paymetric token. */
    @SerializedName("getPaymetricToken")
    private PayloadConfig getPaymetricToken;
    
    /** The iframe url. */
    @SerializedName("iframeUrl")
    private String iframeUrl;

    /**
     * Instantiates a new payments config.
     *
     * @param getPayments the get payments
     * @param addCreditCard the add credit card
     * @param removeCreditCard the remove credit card
     * @param updateCreditCard the update credit card
     * @param getPaymetricToken the get paymetric token
     * @param iframeUrl the iframe url
     */
    public PaymentsConfig(PayloadConfig getPayments,
                          PayloadConfig addCreditCard,
                          PayloadConfig removeCreditCard,
                          PayloadConfig updateCreditCard,
                          PayloadConfig getPaymetricToken,
                          String iframeUrl) {
        this.getPayments = getPayments;
        this.addCreditCard = addCreditCard;
        this.removeCreditCard = removeCreditCard;
        this.updateCreditCard = updateCreditCard;
        this.getPaymetricToken = getPaymetricToken;
        this.iframeUrl = iframeUrl;
    }
}
