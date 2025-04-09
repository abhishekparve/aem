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
 * The Class FeaturesAndSpecsModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FeaturesAndSpecsModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(FeaturesAndSpecsModel.class);

    
    /** The slides. */
    @Inject
    private List<FeaturesAndSpecsDetailsModel> slides;
    
    /** The section align. */
    @Inject
    private String sectionAlign;
    
	/** The section title. */
	@Inject
	private String sectionTitle;
	
	/** The Magnify glass Color. */
	@Inject
	private String magnifiyGlassColor;
	
	/** The current resource. */
	@Inject
	Resource currentResource;
    
    

	/**
	 * Gets the section title.
	 *
	 * @return the section title
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}

	/**
	 * Gets the article id.
	 *
	 * @return the article id
	 */
	public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
	}

    /**
     * Gets the slides.
     *
     * @return the slides
     */
    public List<FeaturesAndSpecsDetailsModel> getSlides() {
		return new ArrayList<>(slides);
	}
    
    

	/**
	 * Gets the section align.
	 *
	 * @return the section align
	 */
	public String getSectionAlign() {
		return sectionAlign;
	}
	
    public String getMagnifiyGlassColor() {
		  if(StringUtils.isNotEmpty(magnifiyGlassColor))
		  {  return magnifiyGlassColor;
				  
		  }else {
			  return "dark-blue";
		  }
	}
}
