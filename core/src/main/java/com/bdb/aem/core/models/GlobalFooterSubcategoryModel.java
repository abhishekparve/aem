package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;


/**
 * The Class GlobalFooterSubcategoryModel.
 */
@Model(
		adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class GlobalFooterSubcategoryModel {

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(GlobalFooterSubcategoryModel.class);

	/** The title. */
	@Inject
	private String title;

	/** The url. */
	@Inject
	private String url;
	
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resolver factory. */
    @Inject
    ResourceResolverFactory resolverFactory;

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        	logger.debug("GlobalFooterSubcategoryModel Initialized");
        	if(StringUtils.isEmpty(url)) {	
        		url = StringUtils.EMPTY;
    		}   				     
    }

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		if(StringUtils.isNotEmpty(title)) {
			return title.trim();
		}else {
			return StringUtils.EMPTY;
		}	
			
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}