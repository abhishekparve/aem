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
 * The Class ContentCardMultifieldModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentCardMultifieldModel {
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ContentCardMultifieldModel.class);

	/** The icon image path. */
	@Inject
	private String iconImagePath;
	
	/** The imageLinkUrl. */
	@Inject
	private String imageLinkUrl;
	
	/** The openNewImageLinkTab. */
	@Inject
	private String openNewImageLinkTab;

	/** The title. */
	@Inject
	private String title;
	
	/** The title link. */
	@Inject
	private String titleLink;

	/** The alt text. */
	@Inject
	private String altText;

	/** The description. */
	@Inject
	private String description;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/**
	 * Gets the icon image path.
	 *
	 * @return the icon image path
	 */
	public String getIconImagePath() {
		return iconImagePath;
	}
	
	/**
	 * Gets the icon image path.
	 *
	 * @return the icon image path
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}
	

	/**
	 * @return the openNewImageLinkTab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
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
	 * Gets the title link.
	 *
	 * @return the title link
	 */
	public String getTitleLink() {
		return titleLink;
	}

	/**
	 * Gets the alt text.
	 *
	 * @return the alt text
	 */
	public String getAltText() {
		return altText;
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
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		iconImagePath = externalizerService.getFormattedUrl(iconImagePath, resourceResolver);
		imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
		titleLink = externalizerService.getFormattedUrl(titleLink, resourceResolver);
	}

}