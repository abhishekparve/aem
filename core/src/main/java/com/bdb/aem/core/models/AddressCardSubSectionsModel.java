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
 * The Address Card sub Sections Model.
 *
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AddressCardSubSectionsModel {

	protected static final Logger log = LoggerFactory.getLogger(AddressCardSubSectionsModel.class);
	
	/** The section sub title */
	@Inject
	private String sectionSubTitle;

	/** The description */
	@Inject
	private String description;

	/** The link url */
	@Inject
	private String linkUrl;
	
	/** The link url */
	@Inject
	private String eventName;

	/** The link title */
	@Inject
	private String linkTitle;

	/** The icon path */
	@Inject
	private String iconPath;

	/** The icon alt text */
	@Inject
	private String iconAltText;
	
	@Inject
    ExternalizerService externalizerService;
    
    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;


    /**
	 * Inits the model.
	 */
	@PostConstruct
	protected void init() {
		log.debug("Inside Model Init");
		linkUrl = externalizerService.getFormattedUrl(linkUrl, resourceResolver);
		iconPath = externalizerService.getFormattedUrl(iconPath, resourceResolver);
	}
	
	/**
	 * Gets the section sub title.
	 * 
	 * @return the section sub title
	 */
	public String getSectionSubTitle() {
		return sectionSubTitle;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the link url.
	 * 
	 * @return the link url
	 */
	public String getLinkUrl() {
		return linkUrl;
	}

	/**
	 * Gets the link title.
	 * 
	 * @return the link title
	 */
	public String getLinkTitle() {
		return linkTitle;
	}

	/**
	 * Gets the icon path.
	 * 
	 * @return the icon path
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * Gets the icon alt text.
	 * 
	 * @return the icon alt text
	 */
	public String getIconAltText() {
		return iconAltText;
	}
	
	/**
	 * Gets the event name.
	 * 
	 * @return the event name
	 */
	public String getEventName() {
		return eventName;
	}
}
