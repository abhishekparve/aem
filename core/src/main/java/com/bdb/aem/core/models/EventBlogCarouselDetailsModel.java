package com.bdb.aem.core.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.impl.EventDetailsCarouselServiceImpl;

/**
 * The Event Blog Carousel Details Model.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class EventBlogCarouselDetailsModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(EventBlogCarouselDetailsModel.class);

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/** The selection */
	@Inject
	private String selection;

	/** The search field. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String searchField;

	/** The title */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String title;

	/** The description. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String description;

	/** The subtitle. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String subTitle;

	/** The image url. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String imageUrl;
	
	/** The image Link Url */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String imageLinkUrl;
	
	/** The open New Image Link Tab */
	@Inject
	private String openNewImageLinkTab;

	/** The image alt text. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String imageAltText;

	/** The cta label */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String ctaLabel;

	/** The cta url */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String ctaUrl;

	/** The event carousel service */
	@Inject
	private EventDetailsCarouselServiceImpl eventCarouselService;

	/** The past label */
	private String pastLabel;

	/** The upcoming label */
	private String upcomingLabel;

	/** The current label */
	private String currentLabel;
	
	/** The event time label. */
	private String eventTimeLabel;

	/** The event start date */
	private String eventStartDate;

	/** The event end date */
	private String eventEndDate;
	
	/** The event date. */
	private String eventDate;

	/** The event type */
	private String eventType;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {

		logger.debug("BlogDetailsModel Initialized");
		eventCarouselService.updateSlingModel(this, resourceResolver);
		if (getSelection().equalsIgnoreCase("event")) {
			Date currentDate = new Date();
			if (null != getEventStartDate() && null != getEventEndDate()) {
				if (getStringDateInDateFormat(getEventStartDate()) != null
						&& currentDate.before(getStringDateInDateFormat(getEventStartDate()))) {
					eventTimeLabel = getUpcomingLabel();
				} else if (getStringDateInDateFormat(getEventEndDate()) != null
						&& currentDate.after(getStringDateInDateFormat(getEventEndDate()))) {
					eventTimeLabel = getPastLabel();
				} else {
					eventTimeLabel = getCurrentLabel();
				}
			}
			eventDate = getEventStartDate();
		}
		imageUrl = externalizerService.getFormattedUrl(imageUrl, resourceResolver);
		imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
		ctaUrl = externalizerService.getFormattedUrl(ctaUrl, resourceResolver);
	}
	
	
	
	/**
	 * Gets the string date in date format.
	 *
	 * @param strDate the str date
	 * @return the string date in date format
	 */
	public  Date getStringDateInDateFormat(String strDate)  {
		return Optional.ofNullable(strDate).filter(s -> !s.equals("")).map(s -> {
			try {
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(s);
			} catch (ParseException e) {
				logger.error("ParseException {}", e.getMessage());
			}
			return null;
		}).orElse(null);
	}
    

	/**
	 * Gets the search field.
	 * 
	 * @return the search field.
	 */
	public String getSearchField() {
		return searchField;
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
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * Gets the image url.
	 * 
	 * @return the image url
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	

	/**
	 * @return the image Link Url
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}



	/**
	 * @return the open New Image Link Tab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}



	/**
	 * Gets the image alt text.
	 * 
	 * @return the image alt text
	 */
	public String getImageAltText() {
		return imageAltText;
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
	 * Gets the cta url.
	 * 
	 * @return the cta url
	 */
	public String getCtaUrl() {
		return ctaUrl;
	}

	/**
	 * Gets the past label.
	 * 
	 * @return the past label
	 */
	public String getPastLabel() {
		return pastLabel;
	}

	/**
	 * Sets the past label.
	 * 
	 * @param pastLabel
	 */
	public void setPastLabel(String pastLabel) {
		this.pastLabel = pastLabel;
	}

	/**
	 * Gets the upcoming label.
	 * 
	 * @return the upcoming label
	 */
	public String getUpcomingLabel() {
		return upcomingLabel;
	}

	/**
	 * Sets the upcoming label.
	 * 
	 * @param upcomingLabel
	 */
	public void setUpcomingLabel(String upcomingLabel) {
		this.upcomingLabel = upcomingLabel;
	}

	/**
	 * Gets the current label.
	 * 
	 * @return the current label
	 */
	public String getCurrentLabel() {
		return currentLabel;
	}

	/**
	 * Sets the current label.
	 * 
	 * @param currentLabel
	 */
	public void setCurrentLabel(String currentLabel) {
		this.currentLabel = currentLabel;
	}
	
	
	/**
	 * Gets the event time label.
	 *
	 * @return the event time label
	 */
	public String getEventTimeLabel() {
		return eventTimeLabel;
	}

	/**
	 * Gets the event start date.
	 * 
	 * @return the event start date
	 */
	public String getEventStartDate() {
		return eventStartDate;
	}

	/**
	 * Sets the event start date.
	 * 
	 * @param eventStartDate
	 */
	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	/**
	 * Gets the event end date.
	 * 
	 * @return the event end date
	 */
	public String getEventEndDate() {
		return eventEndDate;
	}

	/**
	 * Sets the event end date.
	 * 
	 * @param eventEndDate
	 */
	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	/**
	 * Gets the event date.
	 *
	 * @return the event date
	 */
	public String getEventDate() {
		return Optional.ofNullable(eventDate).filter(s -> !s.equals("")).map(s -> {
			try {
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(s);
			} catch (ParseException e) {
				logger.error("ParseException {}", e.getMessage());
			}
			return null;
		}).map(d -> new SimpleDateFormat("MMMM dd, yyyy").format(d)).orElse("");
	}

	/**
	 * Gets the event type.
	 * 
	 * @return the event type
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * Gets the selection.
	 * 
	 * @return the selection
	 */
	public String getSelection() {
		return selection;
	}

	/**
	 * Sets the event type.
	 * 
	 * @param eventType
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

}