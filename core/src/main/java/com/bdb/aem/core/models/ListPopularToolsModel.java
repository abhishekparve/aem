package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class ListPopularToolsModel.
 */
@Model( adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class ListPopularToolsModel {
	
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(ListPopularToolsModel.class);

    /** The row link. */
    @Inject
	private String rowLink;
    
    /** The row image. */
    @Inject
	private String rowImage;
	
	/** The row title. */
	@Inject
	private String rowTitle;

	/** The row description. */
	@Inject
	private String rowDescription;
	
	/** The row download label. */
	@Inject
	private String rowDownloadLabel;
	
	/** The row download path. */
	@Inject 
	private String rowDownloadPath;
	
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
    	logger.debug("ListPopularToolsModel Initiated");
    	rowLink = externalizerService.getFormattedUrl(rowLink, resourceResolver);
    	rowImage = externalizerService.getFormattedUrl(rowImage, resourceResolver);
    	rowDownloadPath = externalizerService.getFormattedUrl(rowDownloadPath, resourceResolver);
    }
    

	/**
	 * Gets the row link.
	 *
	 * @return the row link
	 */
	public String getRowLink() {
		return rowLink;
	}
	
	/**
	 * Gets the row image.
	 *
	 * @return the row image
	 */
	public String getRowImage() {
		return rowImage;
	}

	/**
	 * Gets the row title.
	 *
	 * @return the row title
	 */
	public String getRowTitle() {
		return rowTitle;
	}

	/**
	 * Gets the row description.
	 *
	 * @return the row description
	 */
	public String getRowDescription() {
		return rowDescription;
	}

	/**
	 * Gets the row download label.
	 *
	 * @return the row download label
	 */
	public String getRowDownloadLabel() {
		return rowDownloadLabel;
	}

	/**
	 * Gets the row download path.
	 *
	 * @return the row download path
	 */
	public String getRowDownloadPath() {
		return rowDownloadPath;
	}	
}
