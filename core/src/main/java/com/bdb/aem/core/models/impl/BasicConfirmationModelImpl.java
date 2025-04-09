package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.AccessLinksModel;
import com.bdb.aem.core.models.BasicConfirmationModel;
import com.bdb.aem.core.pojo.ConfirmationModelConfig;
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
import java.util.ArrayList;
import java.util.List;

/**
 * The Class BasicConfirmationModelImpl.
 */
@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = {BasicConfirmationModel.class},
        resourceType = {BasicConfirmationModelImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class BasicConfirmationModelImpl implements BasicConfirmationModel {

    /**
     * The Constant RESOURCE_TYPE.
     */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/basicconfirmation/v1/basicconfirmation";


    /**
     * The Constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(BasicConfirmationModelImpl.class);

    private String confirmationLabels;

    /**
     * The image.
     */
    @Inject
    @Via("resource")
    private String image;

    /**
     * The imageAltText.
     */
    @Inject
    @Via("resource")
    private String imageAltText;
    /**
     * The confirmation title.
     */
    @Inject
    @Via("resource")
    private String confirmationTitle;
    /**
     * The content.
     */
    @Inject
    @Via("resource")
    private String content;

    /**
     * The access text.
     */
    @Inject
    @Via("resource")
    private String accessText;


    /**
     * The linksTitle.
     */
    @Inject
    @Via("resource")
    private String linksTitle;

    /**
     * The links multifield.
     */
    @Inject
    @Via("resource")
    private List<Resource> linksMultifield;

    /**
     * The role list.
     */
    @SerializedName("links")
    private List<AccessLinksModel> accessLinks = new ArrayList<>();

    /**
     * The purchasing account label.
     */
    @Inject
    @Via("resource")
    private String purchasingAccountLabel;

    /**
     * The skip and browse label.
     */
    @Inject
    @Via("resource")
    private String skipAndBrowseLabel;

    /**
     * The skip and browse url.
     */
    @Inject
    @Via("resource")
    private String skipAndBrowseUrl;

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

        populateAccessLinks();
        setConfirmationLabels();
    }

    /**
     * get the textImageBannerLabels.
     */
    @Override
    public String getConfirmationLabels() {
        return confirmationLabels;
    }

    private void setConfirmationLabels() {
        ExcludeUtil excludeUtilObject = new ExcludeUtil();
        Gson basicConfirmationConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        ConfirmationModelConfig confirmationModelConfig = createConfirmationModelConfig();
        confirmationLabels = basicConfirmationConfigGson.toJson(confirmationModelConfig);
    }

    /**
     * Populate access links.
     */
    private void populateAccessLinks() {

        if (linksMultifield != null && !linksMultifield.isEmpty()) {

            for (Resource resource : linksMultifield) {
                AccessLinksModel accessLink = resource.adaptTo(AccessLinksModel.class);
                accessLinks.add(accessLink);
            }
        }
    }

    /**
     * Creates and returns an instance of ConfirmationModelConfig.
     *
     * @return - Instance of ConfirmationModelConfig
     */
    private ConfirmationModelConfig createConfirmationModelConfig() {
        return new ConfirmationModelConfig(
                image,
                imageAltText,
                confirmationTitle,
                content,
                accessText,
                linksTitle,
                accessLinks,
                purchasingAccountLabel,
                skipAndBrowseLabel,
                UrlHandler.getModifiedUrl(skipAndBrowseUrl, resourceResolver));
    }
}