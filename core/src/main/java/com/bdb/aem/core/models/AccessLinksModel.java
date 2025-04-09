package com.bdb.aem.core.models;

import com.google.gson.annotations.SerializedName;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Class AccessLinksModel.
 */
@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AccessLinksModel {

    /**
     * The Constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(AccessLinksModel.class);
    /** The label. */
    @Inject
    @Named("linkLabel")
    @SerializedName("label")
    private String linkLabel;

    /** The linkIcon. */
    @Inject
    @Named("linkIcon")
    @SerializedName("icon")
    private String linkIcon;

    /** The linkAltText. */
    @Inject
    @Named("linkAltText")
    @SerializedName("altText")
    private String linkAltText;

    public AccessLinksModel(String linkLabel, String linkIcon, String linkAltText) {
        this.linkLabel = linkLabel;
        this.linkIcon = linkIcon;
        this.linkAltText = linkAltText;
    }

    public AccessLinksModel(){}

    public String getLinkLabel() {
        return linkLabel;
    }

    public String getLinkIcon() {
        return linkIcon;
    }

    public String getLinkAltText() {
        return linkAltText;
    }
}