package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.ApiErrorMessagesModel;
import com.bdb.aem.core.models.TrainingDetailsCardModel;
import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class TrainingDetailsCardModelImpl.
 */
@Model( adaptables = { Resource.class }, 
		adapters = { TrainingDetailsCardModel.class }, 
		resourceType = { TrainingDetailsCardModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TrainingDetailsCardModelImpl implements TrainingDetailsCardModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/trainingDetailsCard/v1/trainingDetailsCard";
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(TrainingDetailsCardModelImpl.class);
	
	/** The Constant FEATURED_TRAINING_RESOURCE_TYPE. */
	protected static final String FEATURED_TRAINING_RESOURCE_TYPE = "bdb-aem/proxy/components/content/featuredTrainingCarousel";
	
	/** The title. */
	@Inject
	private String title;
	
	/** The description multi field. */
	@Inject
    private List<Resource> descriptionMultiField;
	
	/** The video enabled. */
	@Inject
	private boolean videoEnabled;

	/** The brightcove video id. */
	@Inject
	private String brightcoveVideoId;
	
	/** The image path. */
	@Inject
	private String imagePath;
	
	/** The image video alt text. */
	@Inject
	private String imageVideoAltText;
	
	/** The image link url. */
	@Inject
	private String imageLinkUrl;
	
	/** The open image link url new tab. */
	@Inject
	private String openNewImageLinkTab;
	
	/** The caption under image. */
	@Inject
	private String captionUnderImage;
	
	/** The primary cta label. */
	@Inject
	private String primaryCtaLabel;
	
	/** The primary cta path. */
	@Inject
	private String primaryCtaPath;
	
	/** The optional cta label. */
	@Inject
	private String optionalCtaLabel;
	
	/** The optional cta path. */
	@Inject
	private String optionalCtaPath;
	
	/** The mini thumbnail image. */
	@Inject
	private String miniThumbnailImage;
	
	/** The mini thumbnail alt text. */
	@Inject
	private String miniThumbnailAltText;
	
	/** The is featured. */
	@Inject
	private boolean isFeatured;
	
	/** The section align. */
	@Inject
	private String sectionAlign;

	/** The externalizer service. */
	@Inject
    ExternalizerService externalizerService;

	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;

    /** The resource resolver. */
    ;
    
    /** The description list. */
    private List<ApiErrorMessagesModel> descriptionList = new ArrayList<>();
    
    /**
     * Initializes the Training Details Card Model.
     */
    @PostConstruct
    protected void init() {
    	log.debug("Inside Training Details Cart Model Init");
    		imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
    		imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
    		primaryCtaPath = externalizerService.getFormattedUrl(primaryCtaPath, resourceResolver);
    		optionalCtaPath = externalizerService.getFormattedUrl(optionalCtaPath, resourceResolver);
    		miniThumbnailImage = externalizerService.getFormattedUrl(miniThumbnailImage, resourceResolver);
    	populateDescriptionList();
	}
	
	/**
	 * Populate description list.
	 */
	private void populateDescriptionList() {
		if (descriptionMultiField != null) {
            for (Resource resource : descriptionMultiField) {
                ApiErrorMessagesModel descriptionModel = resource.adaptTo(ApiErrorMessagesModel.class);
                if(descriptionModel.getErrorCode() != null && descriptionModel.getErrorMessage() != null) {
                	descriptionList.add(descriptionModel);
                }
            }            
        }
	}


	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the description list.
	 *
	 * @return the description list
	 */
	@Override
	public List<ApiErrorMessagesModel> getDescriptionList() {
		return new ArrayList<>(descriptionList);
	}
	
	/**
	 * Checks if is video enabled.
	 *
	 * @return true, if is video enabled
	 */
	@Override
	public boolean isVideoEnabled() {
		return videoEnabled;
	}

	/**
	 * Gets the brightcove video id.
	 *
	 * @return the brightcove video id
	 */
	@Override	
	public String getBrightcoveVideoId() {
		return brightcoveVideoId;
	}

	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	@Override
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * Gets the image video alt text.
	 *
	 * @return the image video alt text
	 */
	@Override
	public String getImageVideoAltText() {
		return imageVideoAltText;
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
	 * Gets the caption under image.
	 *
	 * @return the caption under image
	 */
	@Override
	public String getCaptionUnderImage() {
		return captionUnderImage;
	}

	/**
	 * Gets the primary cta label.
	 *
	 * @return the primary cta label
	 */
	@Override
	public String getPrimaryCtaLabel() {
		return primaryCtaLabel;
	}

	/**
	 * Gets the primary cta path.
	 *
	 * @return the primary cta path
	 */
	@Override
	public String getPrimaryCtaPath() {
		return primaryCtaPath;
	}

	/**
	 * Gets the optional cta label.
	 *
	 * @return the optional cta label
	 */
	@Override
	public String getOptionalCtaLabel() {
		return optionalCtaLabel;
	}

	/**
	 * Gets the optional cta path.
	 *
	 * @return the optional cta path
	 */
	@Override
	public String getOptionalCtaPath() {
		return optionalCtaPath;
	}

	/**
	 * Gets the mini thumbnail image.
	 *
	 * @return the mini thumbnail image
	 */
	@Override
	public String getMiniThumbnailImage() {
		return miniThumbnailImage;
	}

	/**
	 * Gets the mini thumbnail alt text.
	 *
	 * @return the mini thumbnail alt text
	 */
	@Override
	public String getMiniThumbnailAltText() {
		return miniThumbnailAltText;
	}

	/**
	 * Checks if is featured.
	 *
	 * @return true, if is featured
	 */
	@Override
	public boolean isFeatured() {
		return isFeatured;
	}

	/**
	 * Gets the section align.
	 *
	 * @return the section align
	 */
	@Override
	public String getSectionAlign() {
		return sectionAlign;
	}
}
