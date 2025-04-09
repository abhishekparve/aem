package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class ListSupportModel.
 */
@Model( adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class ListSupportModel {
	
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(ListSupportModel.class);

    /** The list row link. */
    @Inject
    @Named("rowLink")
	private String listRowLink;
	
	/** The list row title. */
	@Inject
	@Named("rowTitle")
	private String listRowTitle;

	/** The list row description. */
	@Inject
	@Named("rowDescription")
	private String listRowDescription;
	
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
    	logger.debug("ListSupportModel Initiated");
    	listRowLink = externalizerService.getFormattedUrl(listRowLink, resourceResolver);
    }
    

	/**
	 * Gets the list row link.
	 *
	 * @return the list row link
	 */
	public String getListRowLink() {
		return listRowLink;
	}

	/**
	 * Gets the list row title.
	 *
	 * @return the list row title
	 */
	public String getListRowTitle() {
		return listRowTitle;
	}

	/**
	 * Gets the list row description.
	 *
	 * @return the list row description
	 */
	public String getListRowDescription() {
		return listRowDescription;
	}	
	
}
