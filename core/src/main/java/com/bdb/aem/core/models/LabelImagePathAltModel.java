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
 * The Class LabelImagePathAltModel.
 */
@Model(	adaptables = Resource.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LabelImagePathAltModel {
	
/** The Constant log. */
protected static final Logger log = LoggerFactory.getLogger(LabelImagePathAltModel.class);
	
	/** The label. */
	@Inject
    private String label;

    /** The path. */
    @Inject
    private String path;
    
    /** The alt text. */
    @Inject
    private String altText;
    
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
    	log.debug("Inside LabelImagePathAltModel Init");
		path =externalizerService.getFormattedUrl(path, resourceResolver);
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets the alt text.
	 *
	 * @return the alt text
	 */
	public String getAltText() {
		return altText;
	}
}
