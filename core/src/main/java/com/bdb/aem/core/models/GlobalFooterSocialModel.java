package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class GlobalFooterSocialModel.
 */
@Model( adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class GlobalFooterSocialModel {

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(GlobalFooterSocialModel.class);
	
	/** The social alt. */
	@Inject
	private String socialAlt;
	
	/** The social id. */
	@Inject
	private String socialId;
	
	/** The social URL. */
	@Inject
	private String socialURL; 
	
	/** The social icon. */
	@Inject
	private String socialIcon;

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
    	logger.debug("GlobalFooterSocialModel Initialized");
    	socialURL = externalizerService.getFormattedUrl(socialURL, resourceResolver);
    	socialIcon = externalizerService.getFormattedUrl(socialIcon, resourceResolver);
    }
	
	/**
	 * Gets the social alt.
	 *
	 * @return the social alt
	 */
	public String getSocialAlt() {
		if(StringUtils.isNotEmpty(socialAlt)) {
			return socialAlt;
		}
		else {
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * Gets the social id.
	 *
	 * @return the social id
	 */
	public String getSocialId() {
		if(StringUtils.isNotEmpty(socialId)) {
			return socialId;
		}
		else {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * Gets the social URL.
	 *
	 * @return the social URL
	 */
	public String getSocialURL() {
		if(StringUtils.isNotEmpty(socialURL)) {
			return socialURL;	
		}
		else {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * Gets the social icon.
	 *
	 * @return the social icon
	 */
	public String getSocialIcon() {
		if(StringUtils.isNotEmpty(socialIcon)) {
			return socialIcon;
		}
		else {
			return StringUtils.EMPTY;
		}
	} 

}