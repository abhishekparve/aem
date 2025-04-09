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
 * The Class ImageTextModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageTextModel {
    
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(ImageTextModel.class);

    /** The image path. */
    @Inject
    private String imagePath;
    
    /** The Image Link url */
    @Inject
    private String imageLinkUrl;
    
    /** The label cta. */
    @Inject
    private String labelcta;

    /** The cta url. */
    @Inject
    private String ctaUrl;

    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The background color */
    @Inject
	private String backgroundColor;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
    	logger.debug("ImageTextModel Initiated");
    	imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
    	imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
    	ctaUrl = externalizerService.getFormattedUrl(ctaUrl, resourceResolver);
    }
    
    /**
     * Gets the image path.
     *
     * @return the image path
     */
    public String getImagePath() {
    	return imagePath;

    }
    
    /**
	 * Gets the Image link url.
	 *
	 * @return the Image link url
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}
	
	/**
     * Gets the label cta.
     *
     * @return the label cta
     */
    public String getLabelcta() {
       return labelcta;

    }
    
    /**
     * Gets the cta url.
     *
     * @return the cta url
     */
    public String getCtaUrl() {
       return ctaUrl;

    }
  

    /**
	 * @return the backgroundColor
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

}
