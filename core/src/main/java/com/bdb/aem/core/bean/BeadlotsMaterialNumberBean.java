package com.bdb.aem.core.bean;

import com.google.gson.annotations.SerializedName;

/**
 * The Class BeadlotsMaterialNumbersModel.
 */

public class BeadlotsMaterialNumberBean {

    @SerializedName("materialNumber")
    private String materialNumber;

    @SerializedName("badge")
    private String badge;

    public BeadlotsMaterialNumberBean(String materialNumber, String badge) {
        this.materialNumber = materialNumber;
        this.badge = badge;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public String getBadge() {
        return badge;
    }
}
