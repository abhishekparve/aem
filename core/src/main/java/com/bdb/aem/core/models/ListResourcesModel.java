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
 * The Class ListResourcesModel.
 */
@Model( adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class ListResourcesModel {
	
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(ListResourcesModel.class);

	/** The row link. */
	@Inject
	private String rowLink;
	
	/** The row title. */
	@Inject
	private String rowTitle;

	/** The row description. */
	@Inject
	private String rowDescription;
	
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
    	logger.debug("ListResourcesModel Initiated");
    	rowLink = externalizerService.getFormattedUrl(rowLink, resourceResolver);
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
}
