package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class TestimonialCarouselModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TestimonialCarouselModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(TestimonialCarouselModel.class);

    
    /** The slides. */
    @Inject
    private List<TestimonialCarouselDetailsModel> slides;

    
    /**
     * Gets the slides.
     *
     * @return the slides
     */
    public List<TestimonialCarouselDetailsModel> getSlides() {
		return new ArrayList<>(slides);
	}
    
    
    /** The current resource. */
	@Inject
	Resource currentResource;
    

	@Inject
	private String title;
	
	
	/** The autoplay speed */
    @Inject
    @Default(values = "3000ms")
    private String autoplayspeed;
    
    
    /** The disable Autoplay button */
    @Inject
    private Boolean disableAutoplay;


	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return the autoplayspeed
	 */
	public String getAutoplayspeed() {
		return autoplayspeed;
	}

	/**
	 * @return the disableAutoplay
	 */
	public Boolean getDisableAutoplay() {
		return disableAutoplay;
	}

	/**
	 * Gets the article id.
	 *
	 * @return the article id
	 */
	public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
	}

}
