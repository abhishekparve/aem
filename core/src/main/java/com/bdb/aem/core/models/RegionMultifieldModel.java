package com.bdb.aem.core.models;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonHelper;
 	

/**
 * Sling Model to expose countrySelector component's CountryMultifield values to FE.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RegionMultifieldModel {		

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(RegionMultifieldModel.class);
	
	/** Country Multifield. */
    @Inject
    private List<CountryMultifieldModel> countrymultifield;
    
    /** The Region. */
    @Inject
    private String region;
    
    /** The compare country. */
    private String compareCountry;
    
    /**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("Initialize method for RegionMultifieldModel");
	}
    
    
    /**
     * Gets the Region.
     *
     * @return - Region as String
     */
    public String getRegion() {
		return region;
	}
    
    
    /**
     * Gets the countrymultifield.
     *
     * @return the countrymultifield
     */
    public List<CountryMultifieldModel> getCountrymultifield() {
		return new ArrayList<>(countrymultifield);
	}

	/**
	 * Gets the compare country.
	 *
	 * @return the compare country
	 */
	public String getCompareCountry() {
		return compareCountry;
	}
}
