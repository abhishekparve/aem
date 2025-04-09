package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sling Model to fetch values from dialog and product structure in AEM, and
 * return respective JSONs to the FE.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class KeywordsModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(KeywordsModel.class);
	
	/** The Keyword Options */
	@Inject
	@ChildResource
	private Resource keywordOptions;
	
	@Inject
	private String keyword;
	
	@Inject
	private String urlRedirect;
	
	
	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("KeywordsModel - Init method started");
	}

	public Resource getKeywordOptions() {
		return keywordOptions;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getUrlRedirect() {
		return urlRedirect;
	}
	
	
}
