package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
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
public class BoostrulesModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(BoostrulesModel.class);
	
	/** The boost rules */
	@Inject
	@ChildResource
	private Resource boostRules;
	
	@Inject
	private String facetAttributes;
	
	@Inject
	@Default(intValues=0)
	private int preferenceOrder;
	
	
	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("BoostrulesModel - Init method started");
	}


	public Resource getBoostRules() {
		return boostRules;
	}


	public String getFacetAttributes() {
		return facetAttributes;
	}


	public int getPreferenceOrder() {
		return preferenceOrder;
	}


	
	
}
