package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class BDBCarouselModel.
 */
@Model(	adaptables = Resource.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BDBCarouselModel {
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(BDBCarouselModel.class);
	
	/** The current resource. */
	@Inject
	Resource currentResource;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		log.debug("Inside BDB Teaser Model Init");
		getArticleId();
	}
	
	/**
     * Gets the article id.
     *
     * @return the article id
     */
    public String getArticleId() {
    	return currentResource.getParent().getName() + "-" + currentResource.getName();
	}
}
