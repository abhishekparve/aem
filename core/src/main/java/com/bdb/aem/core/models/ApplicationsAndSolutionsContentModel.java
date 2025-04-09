package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * The Class ApplicationsAndSolutionsContentModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ApplicationsAndSolutionsContentModel {
    
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(ApplicationsAndSolutionsContentModel.class);
    
    /** The content title. */
    @Inject
    private String contentTitle;
    	
    /** The content description. */
    @Inject
    private String contentDescription;

	/** The selection. */
	@Inject
	private String selection;
    
    /** The images. */
    @Inject
    private List<ApplicationsAndSolutionsImagesModel> images;
    
    /** The instruction text. */
    @Inject
    private String instructionText;	
    
    /** The cta label. */
    @Inject
    private String ctaLabel;
    
    /** The cta url. */
    @Inject
    private String ctaUrl;

	/** The single image path. */
	@Inject
	private String singleImagePath;

	/** The single enlarged image path. */
	@Inject
	private String singleEnlargedImagePath;

	/** The single alt text. */
	@Inject
	private String singleAltText;

	/** The single image enlarge size. */
	@Inject
	private String singleImageEnlargeSize;

	/** The image caption. */
	@Inject
	private String imageCaption;

	/** The single image title. */
	@Inject
	private String singleImageTitle;

	/** The single image description. */
	@Inject
	private String singleImageDescription;
	
	/** The modal img flag. */
	@Inject
	private String modalImgFlag;
	
	/** The border enable single. */
	@Inject
	private String borderEnableSingle;

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
        	ctaUrl = externalizerService.getFormattedUrl(ctaUrl, resourceResolver);
        	if(StringUtils.isNotEmpty(contentDescription)) {
        		contentDescription = CommonHelper.HandleRTEAnchorLink(contentDescription, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
        	if(StringUtils.isNotEmpty(instructionText)) {
        		instructionText = CommonHelper.HandleRTEAnchorLink(instructionText, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
        } catch (IOException e) {
            logger.error("LoginException {}", e.getMessage());
        }
    }

	/**
	 * Gets the content title.
	 *
	 * @return the content title
	 */
	public String getContentTitle() {
		return contentTitle;
	}

	/**
	 * Gets the content description.
	 *
	 * @return the content description
	 */
	public String getContentDescription() {
		return contentDescription;
	}

	/**
	 * Gets the images.
	 *
	 * @return the images
	 */
	public List<ApplicationsAndSolutionsImagesModel> getImages() {
		return new ArrayList<>(images);
	}

	/**
	 * Gets the cta label.
	 *
	 * @return the cta label
	 */
	public String getCtaLabel() {
		return ctaLabel;
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
	 * Gets the instruction text.
	 *
	 * @return the instruction text
	 */
	public String getInstructionText() {
		return instructionText;
	}


	/**
	 * Gets the selection.
	 *
	 * @return the selection
	 */
	public String getSelection() {
		return selection;
	}

	/**
	 * Gets the single image path.
	 *
	 * @return the single image path
	 */
	public String getSingleImagePath() {
		return singleImagePath;
	}

	/**
	 * Gets the single enlarged image path.
	 *
	 * @return the single enlarged image path
	 */
	public String getSingleEnlargedImagePath() {
		return singleEnlargedImagePath;
	}

	/**
	 * Gets the single alt text.
	 *
	 * @return the single alt text
	 */
	public String getSingleAltText() {
		return singleAltText;
	}

	/**
	 * Gets the single image enlarge size.
	 *
	 * @return the single image enlarge size
	 */
	public String getSingleImageEnlargeSize() {
		return singleImageEnlargeSize;
	}

	/**
	 * Gets the image caption.
	 *
	 * @return the image caption
	 */
	public String getImageCaption() {
		return imageCaption;
	}

	/**
	 * Gets the single image title.
	 *
	 * @return the single image title
	 */
	public String getSingleImageTitle() {
		return singleImageTitle;
	}

	/**
	 * Gets the single image description.
	 *
	 * @return the single image description
	 */
	public String getSingleImageDescription() {
		return singleImageDescription;
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
	 * Gets the border enable single.
	 *
	 * @return the border enable single
	 */
	public String getBorderEnableSingle() {
		return borderEnableSingle;
	}
}
