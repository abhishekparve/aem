package com.bdb.aem.core.models;

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
import com.bdb.aem.core.util.ExcludeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class InteractiveCarouselModel.
 */
@Model(adaptables = { Resource.class }, adapters = { InteractiveCarouselModel.class }, resourceType = {
		InteractiveCarouselModel.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InteractiveCarouselModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/interactiveCarousel/v1/interactiveCarousel";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(InteractiveCarouselModel.class);
	
	/** The Constant CAROUSEL_LIMIT. */
	protected static final int CAROUSEL_LIMIT = 8;
	
	/** The background image. */
	@Inject
	private String backgroundImage;
	
	/** The slides multifield. */
	@Inject
	private List<Resource> slidesMultifield;
	
	/** The slides list. */
	private List<InteractiveCarouselSlidesModel> slidesList = new ArrayList<>();

	/** The externalizer service. */
	@Inject
    ExternalizerService externalizerService;
    
	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

    /** The slide bar json. */
    private String slideBarJson;
    
    /** The slide count. */
    private int slideCount;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		log.debug("Inside InteractiveCarouselModel Init Level-1");
		populateSlidesList();
		populateSlideBarJon();
		backgroundImage = externalizerService.getFormattedUrl(backgroundImage, resourceResolver);
	}
	
	/**
	 * Populate slide bar jon.
	 */
	private void populateSlideBarJon() {
		slideBarJson = StringUtils.EMPTY;
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		slideBarJson = json.toJson(slidesList);
	}

	/**
	 * Populate slides list.
	 */
	private void populateSlidesList() {
		int limitCounter = 0;
		if (slidesMultifield != null) {
			for (Resource resource : slidesMultifield) {
				InteractiveCarouselSlidesModel slideResource = resource.adaptTo(InteractiveCarouselSlidesModel.class);
				if (limitCounter >= CAROUSEL_LIMIT) {
					break;
				}
				if (slideResource != null) {
					slidesList.add(slideResource);
					limitCounter++;
				}
			}
		}
		
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
	 * Gets the slides list.
	 *
	 * @return the slides list
	 */
	public List<InteractiveCarouselSlidesModel> getSlidesList() {
		return new ArrayList<>(slidesList);
	}

	/**
	 * Gets the slide bar json.
	 *
	 * @return the slide bar json
	 */
	public String getSlideBarJson() {
		return slideBarJson;
	}

	/**
	 * Gets the slide count.
	 *
	 * @return the slide count
	 */
	public int getSlideCount() {
		slideCount = slidesList.size();
		return slideCount;
	}
}
