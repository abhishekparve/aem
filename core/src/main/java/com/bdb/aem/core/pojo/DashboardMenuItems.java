package com.bdb.aem.core.pojo;

import org.apache.sling.api.SlingConstants;

import com.google.gson.annotations.SerializedName;

/**
 * The Class DashboardMenuItems.
 */
public class DashboardMenuItems {
    /** The label. */
    @SerializedName("label")
    private String label;

    /** The icon. */
    @SerializedName("icon")
    private String icon;

    /** The altText */
    @SerializedName("altText")
    private String altText;
    
    /** The altText */
    @SerializedName("adaLabel")
    private String adaLabel;

    /** The path */
    @SerializedName(SlingConstants.PROPERTY_PATH)
    private String path;

    /**
     * Constructor sets DashboardMenuItems  .
     *
     * @param label - label
     * @param icon - icon
     * @param path - path
     *
     */
    public DashboardMenuItems(String label, String icon, String altText, String adaLabel, String path ) {
        this.label = label;
        this.icon = icon;
        this.altText = altText;
        this.adaLabel = adaLabel;
        this.path = path;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }

    public String getAltText() {
        return altText;
    }
    
    public String getAdaLabel() {
        return adaLabel;
    }

    public String getPath() {
        return path;
    }
}
