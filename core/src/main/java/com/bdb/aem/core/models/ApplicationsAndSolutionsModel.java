package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
	

/**
 * The Class ApplicationsAndSolutionsModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ApplicationsAndSolutionsModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(ApplicationsAndSolutionsModel.class);

    /** The section title. */
    @Inject
    private String sectionTitle;
    
    /** The anchor identifier. */
    @Inject
    private String anchorIdentifier;
    
    /** The title. */
    @Inject
    private String title;
    
    /** The sub title. */
    @Inject
    private String subTitle;
    
    /** The Modal Image Title or singleImageTitle */
    @Inject
    private String singleImageTitle;
    
    /** The Modal Image Title or imageTitle */
    @Inject
    private String imageTitle;
    
	/** The Modal Image Description or singleImageDescription */
    @Inject
    private String singleImageDescription;
    
    /** The background color. */
    @Inject
    private String backgroundColor;

	/** The background content color. */
	@Inject
	private String contentBackgroundColor;
	
	/** The Magnify glass Color. */
	@Inject
	private String magnifiyGlassColor;
	
	/** The current resource. */
	@Inject
	Resource currentResource;
	
	/** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    

	/** The slides. */
    @ChildResource
    private List<ApplicationsAndSolutionsContentModel> slides;

	
	/**
	 * Gets the article id.
	 *
	 * @return the article id
	 */
    public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
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
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Gets the background content color.
	 *
	 * @return the background content color
	 */
	public String getContentBackgroundColor() {
		return contentBackgroundColor;
	}

	/**
	 * Gets the slides.
	 *
	 * @return the slides
	 */
	public List<ApplicationsAndSolutionsContentModel> getSlides() {
		return new ArrayList<>(slides);
	}

	/**
	 * Gets the anchor identifier.
	 *
	 * @return the anchor identifier
	 */
	public String getAnchorIdentifier() {
		return anchorIdentifier;
	}
	
	  public String getMagnifiyGlassColor() {
		  if(StringUtils.isNotEmpty(magnifiyGlassColor))
		  {  return magnifiyGlassColor;
				  
		  }else {
			  return "dark-blue";
		  }
		}
	  
	  public String getSingleImageTitle() {
		  if(StringUtils.isNotEmpty(singleImageTitle)) {
				try {
					singleImageTitle = CommonHelper.HandleRTEAnchorLink(singleImageTitle, externalizerService, resourceResolver, StringUtils.EMPTY);
				} catch (IOException e) {
					 logger.error("Exception {}", e.getMessage());
				}
	    	}
			return singleImageTitle;
		}
	  
	  public String getImageTitle() {
		  if(StringUtils.isNotEmpty(imageTitle)) {
				try {
					imageTitle = CommonHelper.HandleRTEAnchorLink(imageTitle, externalizerService, resourceResolver, StringUtils.EMPTY);
				} catch (IOException e) {
					 logger.error("LoginException {}", e.getMessage());
				}
	    	}
			return imageTitle;
		}

	public String getSingleImageDescription() {
		if(StringUtils.isNotEmpty(singleImageDescription)) {
			try {
				singleImageDescription = CommonHelper.HandleRTEAnchorLink(singleImageDescription, externalizerService, resourceResolver, StringUtils.EMPTY);
			} catch (IOException e) {
				 logger.error("LoginException {}", e.getMessage());
			}
    	}
		return singleImageDescription;
	}
	
	
	
}
