package com.bdb.aem.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bdb.aem.core.models.TextAndDownloadModel;
import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class TextAndDownloadModelImpl.
 */
@Model( adaptables = { Resource.class }, 
		adapters = { TextAndDownloadModel.class }, 
		resourceType = { TextAndDownloadModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TextAndDownloadModelImpl implements TextAndDownloadModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/textAndDownload/v1/textAndDownload";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(TextAndDownloadModelImpl.class);
	
	/** The link label. */
	@Inject
	private String linkLabel;
	
	/** The link path. */
	@Inject
	private String linkPath;
	
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

	/** The optional image path. */
	@Inject
	private String imagePath;
	
	/** The caption. */
	@Inject
	private String caption;
	
	/** The externalizer service. */
	@Inject
    ExternalizerService externalizerService;

	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;

    /** The title. */
    @Inject
    private String title;
    
    /** The section title. */
    @Inject
    private String sectionTitle;
    
    /** The current resource. */
	@Inject
	Resource currentResource;
    
    

    /**
     * Initializes the model.
     */
    @PostConstruct
    protected void init() {
    	log.debug("Inside Text and Download Model Init");
    		linkPath = externalizerService.getFormattedUrl(linkPath, resourceResolver);
    		primaryCtaPath = externalizerService.getFormattedUrl(primaryCtaPath, resourceResolver);
    		optionalCtaPath = externalizerService.getFormattedUrl(optionalCtaPath, resourceResolver);
    		imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
	}
	
	/**
	 * Gets the link label.
	 *
	 * @return the link label
	 */
	@Override
	public String getLinkLabel() {
		return linkLabel;
	}

	/**
	 * Gets the link path.
	 *
	 * @return the link path
	 */
	@Override
	public String getLinkPath() {
		return linkPath;
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
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}
	/*
	 * Gets the optional image path.
	 *
	 * @return the optional image path
	 */
	@Override
	public String getImagePath() {
		return imagePath;
	}
	
	/**
     * Gets the article id.
     *
     * @return the article id
     */
	@Override
	public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
	}


}
