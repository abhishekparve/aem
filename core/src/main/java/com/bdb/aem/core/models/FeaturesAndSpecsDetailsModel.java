package com.bdb.aem.core.models;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;


/**
 * The Class FeaturesAndSpecsDetailsModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FeaturesAndSpecsDetailsModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(FeaturesAndSpecsDetailsModel.class);

    /** The image path. */
    @Inject
    private String imagePath;
    
    /** The image Link Url. */
    @Inject
    private String imageLinkUrl;
    
    /** The open new image Link tab. */
    @Inject
    private String openNewImageLinkTab;
    
    /** The image path mobile. */
    @Inject
    private String imagePathMobile;
    
    /** The section title. */
    @Inject
    private String sectionTitle;
    
    /** The sub title. */
    @Inject
    private String subTitle;
    
    /** The slide title. */
    @Inject
    private String slideTitle;
    
    /** The description. */
    @Inject
    private String description;
    
    /** The alt text. */
    @Inject
    private String altText;
    
    /** The border enable. */
    @Inject
    private String borderEnable;
    
    /** The background color image. */
    @Inject
    private String backgroundColorImage;
    
    /** The is large image. */
    @Inject
    private String isLargeImage;
    
    /** The Model Image Flag */
    @Inject
    private String modalImgFlag;
    
    /** The Image Enlarge Size. */
    @Inject
    private String imageEnlargeSize;
    
    /** The is Enlarged Image Path. */
    @Inject
    private String enlargedImagePath;
    
    /** The Enlarged Image Alt Text */
    @Inject
    private String enlargedImageAltText;
    
    /** The Image Title */
    @Inject
    private String imageTitle;
    
    /** The Image Title */
    @Inject
    private String imageDescription;

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
        try {
        	logger.debug("FeaturesAndSpecsDetailsModel Initialized");
        	if(StringUtils.isNotEmpty(description)) {
        		description = CommonHelper.HandleRTEAnchorLink(description, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
        	imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
        	imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
        	imagePathMobile = externalizerService.getFormattedUrl(imagePathMobile, resourceResolver);

        } catch (IOException e) {
            logger.error("LoginException {}", e.getMessage());
        }
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
	 * Gets the section name.
	 *
	 * @return the section name
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}

	/**
	 * Gets the sub title.
	 *
	 * @return the sub title
	 */
	public String getSubTitle() {
		return subTitle;
	}

	/**
	 * Gets the slide title.
	 *
	 * @return the slide title
	 */
	public String getSlideTitle() {
		return slideTitle;
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
	 * Gets the alt text.
	 *
	 * @return the alt text
	 */
	public String getAltText() {
		return altText;
	}

	/**
	 * Gets the image path mobile.
	 *
	 * @return the image path mobile
	 */
	public String getImagePathMobile() {
		return imagePathMobile;
	}

	/**
	 * Gets the border enable.
	 *
	 * @return the border enable
	 */
	public String getBorderEnable() {
		return borderEnable;
	}

	/**
	 * Gets the background color image.
	 *
	 * @return the background color image
	 */
	public String getBackgroundColorImage() {
		return backgroundColorImage;
	}

	/**
	 * Gets the checks if is large image.
	 *
	 * @return the checks if is large image
	 */
	public String getIsLargeImage() {
		return isLargeImage;
	}

	/**
	 * Gets the modal img flag.
	 *
	 * @return the modal img flag
	 */
	public String getModalImgFlag() {
		return modalImgFlag;
	}

	/**
	 * Gets the image enlarge size.
	 *
	 * @return the image enlarge size
	 */
	public String getImageEnlargeSize() {
		return imageEnlargeSize;
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
	 * Gets the enlarged image alt text.
	 *
	 * @return the enlarged image alt text
	 */
	public String getEnlargedImageAltText() {
		return enlargedImageAltText;
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
	
}
