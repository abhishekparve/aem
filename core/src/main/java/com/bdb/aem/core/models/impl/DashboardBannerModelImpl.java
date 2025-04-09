package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.DashboardBannerModel;
import com.bdb.aem.core.util.ExcludeUtil;
import com.bdb.aem.core.util.UrlHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The Class DashboardBannerModelImpl.
 */
@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = {DashboardBannerModel.class},
        resourceType = {DashboardBannerModelImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class DashboardBannerModelImpl implements DashboardBannerModel {

    /**
     * The Constant RESOURCE_TYPE.
     */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/dashboardbanner/v1/dashboardbanner";


    /**
     * The Constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(DashboardBannerModelImpl.class);

    private String textImageBannerLabels;

    /**
     * The bannerImage.
     */
    @Inject
    @Via("resource")
    @SerializedName("image")
    private String bannerImage;
    
    /**
     * The bannerImageAltText
     */
    @Inject
    @Via("resource")
    @SerializedName("altText")
    private String bannerImageAltText;

    /**
     * The bannerTitle
     */
    @Inject
    @Via("resource")
    @SerializedName("title")
    private String bannerTitle;

    /**
     * The bannerText
     */
    @Inject
    @Via("resource")
    @SerializedName("text")
    private String bannerText;

    /**
     * The bannerCtaLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("ctaLabel")
    private String bannerCtaLabel;

    /**
     * The bannerCtaLink.
     */
    @Inject
    @Via("resource")
    @SerializedName("ctaLink")
    private String bannerCtaLink;

    /**
     * The resource resolver.
     */
    @SlingObject
    private ResourceResolver resourceResolver;


    /**
     * Populates the textImageBannerLabels.
     */
    @PostConstruct
    protected void init() {
        setTextImageBannerLabels();
    }

    /**
     * get the textImageBannerLabels.
     */
    @Override
    public String getTextImageBannerLabels() {
        return textImageBannerLabels;
    }

    private void setTextImageBannerLabels()
    {
        bannerCtaLink = UrlHandler.getModifiedUrl(bannerCtaLink,resourceResolver);
        ExcludeUtil excludeUtilObject = new ExcludeUtil();
        Gson dashboardConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        textImageBannerLabels = dashboardConfigGson.toJson(this);
    }
}