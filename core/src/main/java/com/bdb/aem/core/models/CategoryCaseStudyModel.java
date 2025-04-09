package com.bdb.aem.core.models;


import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;

/**
 * The Class CategoryCaseStudyModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CategoryCaseStudyModel {
    /**
     * The logger.
     */
    Logger logger = LoggerFactory.getLogger(CategoryCaseStudyModel.class);

    /** The section title. */
    @Inject
	@Named("sectionTitle")
	@Via("resource")
    private String sectionTitle;

	/** The title. */
    @Inject
	@Named("title")
	@Via("resource")
    private String title;

	/** The sub title. */
    @Inject
	@Named("subTitle")
	@Via("resource")
    private String subTitle;

    /** The description. */
    @Inject
	@Named("description")
	@Via("resource")
    private String description;

    /** The cta label. */
    @Inject
	@Named("ctaLabel")
	@Via("resource")
    private String ctaLabel;
    
    /** The cta link. */
    @Inject
	@Named("ctaLink")
	@Via("resource")
    private String ctaLink;
    
    /** The additional cta label. */
    @Inject
	@Named("additionalCtaLabel")
	@Via("resource")
    private String additionalCtaLabel;
    
    /** The additional cta link. */
    @Inject
	@Named("additionalCtaLink")
	@Via("resource")
    private String additionalCtaLink;
    
    /** The background image. */
    @Inject
	@Named("backgroundImage")
	@Via("resource")
    private String backgroundImage;

	/** The background image mobile. */
    @Inject
	@Named("backgroundImageMobile")
	@Via("resource")
    private String backgroundImageMobile;
    
	/** The foreground image. */
    @Inject
	@Named("foregroundImage")
	@Via("resource")
    private String foregroundImage;
    
    /** The background overlay. */
    @Inject
	@Named("backgroundOverlay")
	@Via("resource")
    private String backgroundOverlay;
    
    /** The section alignment. */
    @Inject
	@Named("sectionAlignment")
	@Via("resource")
    private String sectionAlignment;
    
    /** The ExternalizerService. */

    @Inject
	ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /** The current resource. */
	@Inject
	Resource currentResource;
    

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        logger.debug("CategoryCaseStudyModel Initialized :: ");
        try {
        	if(StringUtils.isNotEmpty(description)) {
        		description = CommonHelper.HandleRTEAnchorLink(description, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
            ctaLink =  externalizerService.getFormattedUrl(ctaLink, resourceResolver);
            additionalCtaLink = externalizerService.getFormattedUrl(additionalCtaLink, resourceResolver);
            backgroundImage = externalizerService.getFormattedUrl(backgroundImage, resourceResolver);
			backgroundImageMobile = externalizerService.getFormattedUrl(backgroundImageMobile, resourceResolver);
            foregroundImage = externalizerService.getFormattedUrl(foregroundImage, resourceResolver);
        } catch (IOException e) {
            logger.error("LoginException {}", e.getMessage());
        }
    }
    
    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * Gets the cta link.
	 *
	 * @return the cta link
	 */
	public String getCtaLink() {
		return ctaLink;
	}

	/**
	 * Gets the additional cta label.
	 *
	 * @return the additional cta label
	 */
	public String getAdditionalCtaLabel() {
		return additionalCtaLabel;
	}

	/**
	 * Gets the additional cta link.
	 *
	 * @return the additional cta link
	 */
	public String getAdditionalCtaLink() {
		return additionalCtaLink;
	}

	/**
	 * Gets the background overlay.
	 *
	 * @return the background overlay
	 */
	public String getBackgroundOverlay() {
		return backgroundOverlay;
	}

	/**
	 * Gets the section alignment.
	 *
	 * @return the section alignment
	 */
	public String getSectionAlignment() {
		return sectionAlignment;
	}
	 
    /**
     * Gets the section title.
     *
     * @return the section title
     */
    public String getSectionTitle() {
		return sectionTitle;
	}
    
    /**
	 * Sets the section title.
	 *
	 * @param title the new section title
	 */
    public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	/**
     * Gets the background image.
     *
     * @return the background image
     */
    public String getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * Gets the background image mobile.
	 *
	 * @return the background image mobile
	 */
	public String getBackgroundImageMobile() {
		return backgroundImageMobile;
	}

	/**
	 * Gets the foreground image.
	 *
	 * @return the foreground image
	 */
	public String getForegroundImage() {
		return foregroundImage;
	}
	
	/**
	 * Gets the foreground image.
	 *
	 * @return the foreground image
	 */
	public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
	}
}
