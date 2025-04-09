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
 * The Class SmallImageModel.
 */
@Model(	adaptables = Resource.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SmallImageModel {

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(SmallImageModel.class);
	
	/** The label. */
	@Inject
    private String label;

    /** The path. */
    @Inject
    private String path;
    
	/** The small image link url. */
	@Inject
	private String smallImageLinkUrl;
	
	/** The image link tab. */
	@Inject
	private String openNewImageLinkTab;
    
    /** The enlarged image path. */
    @Inject
    private String enlargedImagePath;
    
    /** The image enlarge size. */
    @Inject
    private String imageEnlargeSize;

	/** The small image title. */
    @Inject
    private String smallImageTitle;
    
    /** The small image caption. */
    @Inject
    private String smallImageCaption;
    
    /** The small image description. */
    @Inject
    private String smallImageDescription;
    
    /** The background color small image. */
    @Inject
    private String backgroundColorSmallImage;
    
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
    	log.debug("Inside Small Image Model Init");
    		path = externalizerService.getFormattedUrl(path, resourceResolver);
    		enlargedImagePath = externalizerService.getFormattedUrl(enlargedImagePath, resourceResolver);
    		smallImageLinkUrl = externalizerService.getFormattedUrl(smallImageLinkUrl, resourceResolver);
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
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
	 * Gets the image enlarge size.
	 *
	 * @return the image enlarge size
	 */
	public String getImageEnlargeSize() {
		return imageEnlargeSize;
	}
	
	/**
	 * Gets the small image title.
	 *
	 * @return the small image title
	 */
	public String getSmallImageTitle() {
		return smallImageTitle;
	}

	/**
	 * Gets the small image caption.
	 *
	 * @return the small image caption
	 */
	public String getSmallImageCaption() {
		return smallImageCaption;
	}

	/**
	 * Gets the small image description.
	 *
	 * @return the small image description
	 */
	public String getSmallImageDescription() {
		return smallImageDescription;
	}

	/**
	 * Gets the background color small image.
	 *
	 * @return the background color small image
	 */
	public String getBackgroundColorSmallImage() {
		return backgroundColorSmallImage;
	}
	
	/**
	 * @return the smallImageLinkUrl
	 */
	public String getSmallImageLinkUrl() {
		return smallImageLinkUrl;
	}

	/**
	 * @return the openNewImageLinkTab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}
	
}
