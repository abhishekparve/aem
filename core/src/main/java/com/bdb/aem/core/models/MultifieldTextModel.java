package com.bdb.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * The Class MultifieldTextModel.
 */
@Model( adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class MultifieldTextModel {

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(MultifieldTextModel.class);

	/** The title. */
	@Inject
	private String title;
	
	/** The description. */
	@Inject
	private String description;
	
	
    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
		logger.debug("MultifieldTextModel Initialized");
    }
    
    
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
}