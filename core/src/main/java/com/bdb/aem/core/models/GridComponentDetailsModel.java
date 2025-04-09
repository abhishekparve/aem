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
 * The Class GridComponentDetailsModel.
 */
@Model( adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class GridComponentDetailsModel {
	
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(GridComponentDetailsModel.class);

	/** The product image. */
	@Inject
	private String productImage;
	
	/** The image Link Url. */
	@Inject
	private String imageLinkUrl;
	
	/** The open New Image Link Tab */
	@Inject
	private String openNewImageLinkTab;
	
	/** The alt image. */
	@Inject
	private String altImage;
	
	/** The product title. */
	@Inject
	private String productTitle;
	
	/** The product description. */
	@Inject
	private String productDescription;
	
	/** The button URL. */
	@Inject
	private String buttonURL;
	
	/** The button label. */
	@Inject
	private String buttonLabel;
	
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
    	logger.debug("GridComponentDetailsModel Initiated");
    	productImage = externalizerService.getFormattedUrl(productImage, resourceResolver);
    	imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
    	buttonURL = externalizerService.getFormattedUrl(buttonURL, resourceResolver);
    }
    
	/**
	 * Gets the product image.
	 *
	 * @return the product image
	 */
	public String getProductImage() {
		return productImage;
	}
	
	/**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}

	/**
	 * @return the open New Image Link Tab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}

	/**
	 * Gets the button URL.
	 *
	 * @return the button URL
	 */
	public String getButtonURL() {
		return buttonURL;
	}

	/**
	 * Gets the alt image.
	 *
	 * @return the alt image
	 */
	public String getAltImage() {
		return altImage;
	}

	/**
	 * Gets the product title.
	 *
	 * @return the product title
	 */
	public String getProductTitle() {
		return productTitle;
	}

	/**
	 * Gets the product description.
	 *
	 * @return the product description
	 */
	public String getProductDescription() {
		return productDescription;
	}

	/**
	 * Gets the button label.
	 *
	 * @return the button label
	 */
	public String getButtonLabel() {
		return buttonLabel;
	}
	
}
