package com.bdb.aem.core.models;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import com.bdb.aem.core.models.EventDetailsModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sling Model to fetch values from dialog and product structure in AEM, and
 * return respective JSONs to the FE.
 */
@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EventBlogModel {

	public static final String EVENT_TYPE_EVENT = "event";
	public static final String SELECT_EVENT_TYPE = "Select Event Type";
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(EventBlogModel.class);
	
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String bannerThumbnailImage;  
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String bannerImageAlt; 
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String bannerTitle;



	@Inject
	private Boolean addToHomePage;


	@Inject
	@ChildResource
	private Resource multifieldSection; 
	
	@Inject
	@ChildResource
	@Default(values = StringUtils.EMPTY)
	private List<EventDateAndTimeModel> eventDateAndTimeLabel;
	
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String eventType;
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String eventStartDate; 
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String eventEndDate;
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String eventTopic;
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String blogTopic1;
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String blogTopic2;
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String blogTopic3;
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String bannerThumbnailImageBlog;
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String bannerImageAltBlog;
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String bannerTitleBlog; 
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String blogDate; 
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String bannerSubTitle;
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String eventLocation;
	
	@Inject
	@Default(values=StringUtils.EMPTY)
	private String selection;
	
	private String subHeading = StringUtils.EMPTY;
	
	transient EventDetailsModel eventDetailsModel = new EventDetailsModel();
	
	
	
	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("EventBlogModel - Init method started");

		if (null != multifieldSection) {
			Resource item = multifieldSection.getChild("item0");
			if (null != item) {
				setSubHeading((String) item.getValueMap().get("description"));
			}
		}

		if(eventType.equalsIgnoreCase(SELECT_EVENT_TYPE)) {
			addToHomePage = false;
		}


		int count = 0;
    	if(null != eventDateAndTimeLabel) {
        	for(EventDateAndTimeModel dateAndTimeModel:eventDateAndTimeLabel) { 
        		if(count == 0) 
        			eventStartDate = dateAndTimeModel.getMultifieldFirstStartDate();
        			eventEndDate = dateAndTimeModel.getMultifieldLastEndDate();
        		count++;
        	}
    	}

	}
	
	


	/**
	 * @return the selection
	 */
	public String getSelection() {
		return selection;
	}

	public Boolean getAddToHomePage() {
		return addToHomePage;
	}

	/**
	 * @return the multifieldSection
	 */
	public Resource getMultifieldSection() {
		return multifieldSection;
	}




	/**
	 * @return the blogTopic2
	 */
	public String getBlogTopic2() {
		return blogTopic2;
	}




	/**
	 * @return the blogTopic3
	 */
	public String getBlogTopic3() {
		return blogTopic3;
	}




	/**
	 * @return the bannerThumbnailImageBlog
	 */
	public String getBannerThumbnailImageBlog() {
		return bannerThumbnailImageBlog;
	}




	/**
	 * @return the bannerImageAltBlog
	 */
	public String getBannerImageAltBlog() {
		return bannerImageAltBlog;
	}




	/**
	 * @return the bannerTitleBlog
	 */
	public String getBannerTitleBlog() {
		return bannerTitleBlog;
	}




	/**
	 * @return the blogDate
	 */
	public String getBlogDate() {
		return blogDate;
	}




	/**
	 * @return the bannerSubTitle
	 */
	public String getBannerSubTitle() {
		return bannerSubTitle;
	}




	/**
	 * @param subHeading the subHeading to set
	 */
	public void setSubHeading(String subHeading) {
		this.subHeading = subHeading;
	}




	/**
	 * @return the bannerThumbnailImage
	 */
	public String getBannerThumbnailImage() {
		return bannerThumbnailImage;
	}




	/**
	 * @return the blogTopic1
	 */
	public String getBlogTopic1() {
		return blogTopic1;
	}




	/**
	 * @return the bannerImageAlt
	 */
	public String getBannerImageAlt() {
		return bannerImageAlt;
	}



	/**
	 * @return the bannerTitle
	 */
	public String getBannerTitle() {
		return bannerTitle;
	}



	/**
	 * @return the subHeading
	 */
	public String getSubHeading() {
		return subHeading;
	}



	/**
	 * @return the eventType
	 */
	public String getEventType() {
		if(eventType.equalsIgnoreCase(SELECT_EVENT_TYPE)) {
			eventType = StringUtils.EMPTY;
		}

		return eventType;
	}
	




	/**
	 * @return the eventTopic
	 */
	public String getEventTopic() {
		if(eventTopic.equalsIgnoreCase("Select Event Topic")) {
			eventTopic = StringUtils.EMPTY;
		}
		return eventTopic;
	}


	/**
	 * @return the eventStartDate
	 */
	public String getEventStartDate() {
		return eventStartDate;
	}


	/**
	 * @return the eventEndDate
	 */
	public String getEventEndDate() {
		return eventEndDate;
	}



	/**
	 * @return the eventLocation
	 */
	public String getEventLocation() {
		return eventLocation;
	}	
	
	
	
}
