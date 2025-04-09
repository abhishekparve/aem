package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.google.gson.annotations.SerializedName;

/**
 * The Helping model for Social Media MultiField.
 */
@Model(
		adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SocialMediaMultiFieldModel {

	 /** The logger. */
    Logger logger = LoggerFactory.getLogger(SocialMediaMultiFieldModel.class);
    
    /** Social Media Icon Path */
	@Inject
	@Named("socialMediaIcon")
	@SerializedName("icon")
	String icon;
	
	 /** Social Media URL */
	@Inject
	@Named("socialMediaURL")
	@SerializedName("url")
	String url;
	
	 /** Social Media Icon Alt text */
	@Inject
	@Named("socialMediaAlt")
	@SerializedName("alt")
	String alt;
	
	 /** Social Media open link in new tab */
	@Inject
	@Named("openNewImageLinkTab")
	@SerializedName("openNewImageLinkTab")
	String openNewImageLinkTab;
	
	 /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        	logger.debug("SocialMediaMultiFieldModel Initiated");
        	url = externalizerService.getFormattedUrl(url, resourceResolver);
    }
	
   /**
     * Getter method for Social Media Icon
     * @return social Media Icon Path
     */
	 public String getIcon() {
		return icon;
	}

	/**
     * Getter method for Social Media URL
     * @return social Media URL
     */
	public String getUrl() {
		return url;
	}
	/**
     * Getter method for Social Media Alt Text
     * @return social Media Alt Text
     */
	public String getAlt() {
		return alt;
	}

	/**
	 * @return the openNewImageLinkTab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}
}
