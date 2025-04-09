package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;


/**
 * The Class CountryMultifieldModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CountryMultifieldModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(CountryMultifieldModel.class);

    
    /** The image path. */
    @Inject
    private String country;
    
    /** The alt image. */
    @Inject
    private String urlCountry;
    
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

    private String locale;


    /**
     * Inits the.
     */
	@PostConstruct
	protected void init() {
		logger.debug("CountryMultifieldModel Initialized");
		String[] splitUrl = urlCountry.split("/");
		locale = splitUrl.length >=5 ? splitUrl[5] : "en-us";
		urlCountry = externalizerService.getFormattedUrl(urlCountry, resourceResolver);
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Gets the url country.
	 *
	 * @return the url country
	 */
	public String getUrlCountry() {
		return urlCountry;
	}

	public String getLocale() {
		return locale;
	}
    
}
