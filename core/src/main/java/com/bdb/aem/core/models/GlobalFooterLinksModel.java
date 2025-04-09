package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class GlobalFooterLinksModel.
 */
@Model(
		adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class GlobalFooterLinksModel {

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(GlobalFooterLinksModel.class);
	
	/** The link label. */
	@Inject
	private String linkLabel;

	
	/** The link URL. */
	@Inject
	private String linkURL;

	/** The isCookieSettings. */
	@Inject
	private String isCookieSettings;

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
    	logger.debug("GlobalFooterLinksModel Initialized");
    }

    
	/**
	 * Gets the link label.
	 *
	 * @return the link label
	 */
	public String getLinkLabel() {
		if(StringUtils.isNotEmpty(linkLabel)) {
			return linkLabel;
		}else{
			return StringUtils.EMPTY;
		}
		
	}


	/**
	 * Gets the link URL.
	 *
	 * @return the link URL
	 */
	public String getLinkURL() {
		if(StringUtils.isNotEmpty(linkURL)) {
			return linkURL;
		}else{
			return StringUtils.EMPTY;
		}
		
	}

	/**
	 * Sets the link URL.
	 *
	 * @param linkURL the new link URL
	 */
	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	/**
	 * Sets the isCookieSettings.
	 *
	 */
	public String getIsCookieSettings() {
		return isCookieSettings;
	}
}