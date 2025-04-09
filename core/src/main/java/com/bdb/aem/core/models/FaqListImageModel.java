package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The class Faq list image model.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FaqListImageModel {

	/** The image url */
	@Inject
	private String url;
	
	/** The image Link Url */
	@Inject
	private String imageLinkUrl;
	
	/** The openNewImageLinkTab. */
	@Inject
	private String openNewImageLinkTab;

	/** The image alt text */
	@Inject
	private String altText;
	
	/** The caption. */
	@Inject
	private String caption;
	
	/** The enable border. */
	@Inject
	private String enableBorder;
	
	/** The url large. */
	@Inject
	private String urlLarge;
	
	/** The Image Link Url large. */
	@Inject
	private String imageLinkUrlLarge;
	
	/** The openNewImageLinkTab. */
	@Inject
	private String openNewImageLinkTabLarge;
	
	/** The alt text large. */
	@Inject
	private String altTextLarge;
	
	/** The caption large. */
	@Inject
	private String captionLarge;
	
	/** The enable border large. */
	@Inject
	private String enableBorderLarge;
	
	 /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
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
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Gets the enable border.
	 *
	 * @return the enable border
	 */
	public String getEnableBorder() {
		return enableBorder;
	}

	/**
	 * Gets the url for large image
	 * 
	 * @return the url for large image 
	 */
	public String getUrlLarge() {
		return urlLarge;
	}
	

	/**
	 * @return the imageLinkUrlLarge
	 */
	public String getImageLinkUrlLarge() {
		return externalizerService.getFormattedUrl(imageLinkUrlLarge, resourceResolver);
	}
	
	/**
	 * @return the openNewImageLinkTabLarge
	 */
	public String getOpenNewImageLinkTabLarge() {
		return openNewImageLinkTabLarge;
	}

	/**
	 * Gets the alt text for large image
	 * 
	 * @return the alt text for large image 
	 */
	public String getAltTextLarge() {
		return altTextLarge;
	}

	/**
	 * Gets the caption large.
	 *
	 * @return the caption large
	 */
	public String getCaptionLarge() {
		return captionLarge;
	}

	/**
	 * Gets the enable border large.
	 *
	 * @return the enable border large
	 */
	public String getEnableBorderLarge() {
		return enableBorderLarge;
	}
}
