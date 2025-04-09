package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The class Contact Card Model.
 */

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactCardModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactCardModel.class);

	/** The page title */
	@ValueMapValue
	private String pageTitle;
	
	/** The page sub title */
	@ValueMapValue
	private String pageSubTitle;

	/** The contact cards */
	@ChildResource
	private List<ContactCardListModel> contactCards;
	
	
	   /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
	/**
	 * The init
	 */
	protected void init() {
		LOGGER.debug("Into Contact Card Model");
	}

	/**
	 * Gets the page title.
	 * 
	 * @return the page title
	 */
	public String getPageTitle() {
		return pageTitle;
	}

	/**
	 * Gets the page sub title.
	 * 
	 * @return the page sub title
	 */
	public String getPageSubTitle() {
		return pageSubTitle;
	}

	/**
	 * Gets the contact cards.
	 * 
	 * @return the contact cards
	 */
	public List<ContactCardListModel> getContactCards() {
		return Optional.ofNullable(contactCards).filter(c -> !c.isEmpty()).orElseGet(ArrayList::new);
	}
	
}
