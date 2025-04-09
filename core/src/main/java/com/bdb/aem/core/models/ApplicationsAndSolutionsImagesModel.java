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
 * The Class ApplicationsAndSolutionsImagesModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ApplicationsAndSolutionsImagesModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(ApplicationsAndSolutionsImagesModel.class);
    
    /** The alt image. */
    @Inject
    private String altImage;
    
    /** The thumb image path. */
    @Inject
    private String thumbImagePath;
    
    /** The image path. */
    @Inject
    private String imagePath;

	/** TheenlargedImagePath. */
	@Inject
	private String enlargedImagePath;


	/** The image path. */
	@Inject
	private String enlargeSize;
    
    /** The image title. */
    @Inject
    private String imageTitle;
    
    /** The image description. */
    @Inject
    private String imageDescription;
    
    /** The border enable multiple. */
    @Inject
    private String borderEnableMultiple;

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
        	thumbImagePath = externalizerService.getFormattedUrl(thumbImagePath, resourceResolver);
        	imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
        	enlargedImagePath = externalizerService.getFormattedUrl(enlargedImagePath, resourceResolver);
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
	 * Gets the thumb image path.
	 *
	 * @return the thumb image path
	 */
	public String getThumbImagePath() {
		return thumbImagePath;
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
	 * Gets the enlarged image path.
	 *
	 * @return the enlarged image path
	 */
	public String getEnlargedImagePath() {
		return enlargedImagePath;
	}

	/**
	 * Gets the enlarged image size.
	 *
	 * @return the enlarged image size
	 */
	public String getEnlargeSize() {
		return enlargeSize;
	}

	/**
	 * Gets the image title.
	 *
	 * @return the image title
	 */
	public String getImageTitle() {
		return imageTitle;
	}

	/**
	 * Gets the image description.
	 *
	 * @return the image description
	 */
	public String getImageDescription() {
		return imageDescription;
	}

	/**
	 * Gets the border enable multiple.
	 *
	 * @return the border enable multiple
	 */
	public String getBorderEnableMultiple() {
		return borderEnableMultiple;
	}

	

}
