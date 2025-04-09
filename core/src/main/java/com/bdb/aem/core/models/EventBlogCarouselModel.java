package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;


/**
 * The Class Event Blog Carousel Model.
 */
@Model( adaptables = {Resource.class, SlingHttpServletRequest.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class EventBlogCarouselModel {

	/** The logger. */
    Logger logger = LoggerFactory.getLogger(EventBlogCarouselModel.class);

	/** The slides. */
	@ChildResource
	public List<EventBlogCarouselDetailsModel> slides; 
    
	/**
	 * Gets the slides.
	 *
	 * @return the slides
	 */
	public List<EventBlogCarouselDetailsModel> getSlides() {
		return new ArrayList<>(slides);
	}
	
}