package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class HeroCarouselModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroCarouselModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(HeroCarouselModel.class);

   
    /** The slides. */
    @Inject
    private List<HeroCarouselMultifieldModel> slides;

    
    /**
     * Gets the slides.
     *
     * @return the slides
     */
    public List<HeroCarouselMultifieldModel> getSlides() {
		return new ArrayList<>(slides);
	}
    
  


}
