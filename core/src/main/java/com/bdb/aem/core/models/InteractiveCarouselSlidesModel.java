package com.bdb.aem.core.models;

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

import com.bdb.aem.core.services.ExternalizerService;
import com.google.gson.annotations.SerializedName;

/**
 * The Class InteractiveCarouselSlidesModel.
 */
@Model(	adaptables = Resource.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InteractiveCarouselSlidesModel {
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(InteractiveCarouselSlidesModel.class);
	
	/** The title. */
	@Inject
    private String title;
	
	/** The sub title. */
	@Inject
	private String subTitle;

	/** The icons multifield. */
	@Inject
	private List<Resource> iconsMultifield;
	
	/** The icons list. */
	private List<InteractiveCarouselIconModel> iconsList = new ArrayList<>();
    
    /** The slider icon path. */
    @Inject
    @SerializedName("iconimage")
    private String sliderIconPath;
    
    /** The slider icon alt text. */
    @Inject
    @SerializedName("altText")
    private String sliderIconAltText;
    
    /** The slider title. */
    @Inject
    @SerializedName("iconText")
    private String sliderTitle;
    
	/** The cta label. */
	@Inject
	private String ctaLabel;
	
	/** The cta path. */
	@Inject
	private String ctaPath;
	
	/** The secondary cta label. */
	@Inject
	private String secondaryCtaLabel;
	
	/** The secondary cta path. */
	@Inject
	private String secondaryCtaPath;
    
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
    	log.debug("Inside InteractiveCarouselSlidesModel Init Level-2");
		populateIconsList();
		sliderIconPath = externalizerService.getFormattedUrl(sliderIconPath, resourceResolver);
		secondaryCtaPath = externalizerService.getFormattedUrl(secondaryCtaPath, resourceResolver);
		ctaPath = externalizerService.getFormattedUrl(ctaPath, resourceResolver);
	}
	
	/**
	 * Populate icons list.
	 */
	private void populateIconsList() {
		if (iconsMultifield != null) {
			for (Resource resource : iconsMultifield) {
				InteractiveCarouselIconModel iconResource = resource.adaptTo(InteractiveCarouselIconModel.class);
				if (iconResource != null) {
					iconsList.add(iconResource);
				}
			}
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
	 * Gets the sub title.
	 *
	 * @return the sub title
	 */
	public String getSubTitle() {
		return subTitle;
	}

	/**
	 * Gets the icons list.
	 *
	 * @return the icons list
	 */
	public List<InteractiveCarouselIconModel> getIconsList() {
		return new ArrayList<>(iconsList);
	}
	
	/**
	 * Gets the slider icon path.
	 *
	 * @return the slider icon path
	 */
	public String getSliderIconPath() {
		return sliderIconPath;
	}

	/**
	 * Gets the slider icon alt text.
	 *
	 * @return the slider icon alt text
	 */
	public String getSliderIconAltText() {
		return sliderIconAltText;
	}

	/**
	 * Gets the slider title.
	 *
	 * @return the slider title
	 */
	public String getSliderTitle() {
		return sliderTitle;
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
	 * Gets the cta path.
	 *
	 * @return the cta path
	 */
	public String getCtaPath() {
		return ctaPath;
	}

	/**
	 * Gets the secondary cta label.
	 *
	 * @return the secondary cta label
	 */
	public String getSecondaryCtaLabel() {
		return secondaryCtaLabel;
	}

	/**
	 * Gets the secondary cta path.
	 *
	 * @return the secondary cta path
	 */
	public String getSecondaryCtaPath() {
		return secondaryCtaPath;
	}
}
