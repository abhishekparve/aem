package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class DistributorInfoLabels.
 */
public class DistributorInfoLabels {

    /** the title */
    @SerializedName("title")
    private String title;

    /** the nameLabel */
    @SerializedName("nameLabel")
    private String nameLabel;

    /** the emailLabel */
    @SerializedName("emailLabel")
    private String emailLabel;

    /** the addressLabels */
    @SerializedName("addressLabels")
    private String addressLabels;

    /**
     * Instantiates a new PersonalInfoLabels.
     *
     * @param  title
     * @param  nameLabel
     * @param  emailLabel
     * @param  addressLabels
     */
    public DistributorInfoLabels(String title, String nameLabel, String emailLabel, String addressLabels) {
        this.title = title;
        this.nameLabel = nameLabel;
        this.emailLabel = emailLabel;
        this.addressLabels = addressLabels;
    }
}
