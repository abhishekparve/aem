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
 * The Class InteractiveCarouselIconModel.
 */
@Model(	adaptables = Resource.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InteractiveCarouselIconModel {
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(InteractiveCarouselIconModel.class);

    /** The icon path. */
    @Inject
    private String iconPath;
    
	/** The image Link Url. */
	@Inject
	private String imageLinkUrl;
	
	/** The open New Image Link Tab */
	@Inject
	private String openNewImageLinkTab;
    
    /** The alt text. */
    @Inject
    private String altText;
    
    /** The sub heading. */
    @Inject
    private String subHeading;
	
	/** The sub description. */
	@Inject
	private String subDescription;
    
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
    	log.debug("Inside InteractiveCarouselIconModel Init Level-3");
		iconPath = externalizerService.getFormattedUrl(iconPath, resourceResolver);
		imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
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
	 * @return the imageLinkUrl
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
	 * Gets the alt text.
	 *
	 * @return the alt text
	 */
	public String getAltText() {
		return altText;
	}

	/**
	 * Gets the sub heading.
	 *
	 * @return the sub heading
	 */
	public String getSubHeading() {
		return subHeading;
	}

	/**
	 * Gets the sub description.
	 *
	 * @return the sub description
	 */
	public String getSubDescription() {
		return subDescription;
	}
}
