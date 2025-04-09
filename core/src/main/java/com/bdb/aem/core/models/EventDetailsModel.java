package com.bdb.aem.core.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;


/**
 * The Class EventDetailsModel.
 */
@Model( adaptables = {Resource.class, SlingHttpServletRequest.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class EventDetailsModel {

	/** The logger. */
    Logger logger = LoggerFactory.getLogger(EventDetailsModel.class);
    
    /** The Constant NO. */
    private static final String NO = "no";
    
    /** The bdb api endpoint service. */
    @Inject
    BDBApiEndpointService bdbApiEndpointService;
    
    
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

    /** The current page. */
	@Inject
    private Page currentPage;
	
	/** The multifield section. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public List<MultifieldTextModel> multifieldSection; 
	
	/** The Event date and Time section. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public List<EventDateAndTimeModel> eventDateAndTimeLabel; 
	
	/** The multifield section. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public List<MultifieldSpeakerTextModel> multifieldSpeakerSection; 
	
	/** The banner image. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String bannerImage;
	
	/** The dark mode. */
    @Inject
    @Via("resource")
    private String darkMode;

	/** The banner thumbnail image. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String bannerThumbnailImage;

	/** The banner URL. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String bannerURL;
	
	/** The event Banner Title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String bannerTitle;
	
	/** The speaker image. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String speakerImage;
	
	/** The event Brightcove Border. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
    private String eventBorder;
	
	/** The event Brightcove ID. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String eventVideoId;
	
	/** The video caption. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String eventVideoCaption;
    
    /** The video Title. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String eventVideoTitle;
    
    /** The video Description. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String eventVideoDesc;
	
	 /** The Event url cta. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String eventUrlCta;
    
    /** The Event label cta. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String eventLabelCta;
    
    /** The Event label cta add. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String eventLabelCtaAdd;
    
    /** The Root URL. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String rootURL;
    
    /** The Munchkin ID. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String munchkinID;
    
    /** The Form ID. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String formID;
    
    /** The Form Title. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String formTitle;
    
    /** The Form Body Text. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String formBodyText;
    
    /** The Event url cta add. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String eventUrlCtaAdd;
	
	/** The date icon. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String dateIcon;

	/** The time icon. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String timeIcon;

	/** The location icon. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String locationIcon;

	/** The cta url. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String ctaUrl;
	
	/** The event start date. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
    private String multifieldEventStartDate;
	
	/** The event end date.*/ 
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
    private String multifieldEventEndDate;
	
	/** The Banner Start and end date.*/ 
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
    private String bannerDate;
    
    /** The event time label. */
    private String eventTimeLabel;
    
    /** The Constant STANDARD_DATE_FORMAT. */
    protected static final String STANDARD_DATE_FORMAT ="yyyy-MM-dd'T'HH:mm:ss.SSS";
    
    /** The Constant DATE_FORMAT. */
    protected static final String DATE_FORMAT = "MMMM dd, yyyy";
	
    /** The Constant TIME_FORMAT. */
    protected static final String TIME_FORMAT = "hh:mm aa";
    
    /** The Constant START_DATE_FORMAT. */
    protected static final String START_DATE_FORMAT = "MMMM dd";
    
    /** The Constant BANNER_DATE_MONTH_FORMAT. */
    protected static final String BANNER_DATE_MONTH_FORMAT = "MMMM";
    
    /** The Constant BANNER_DATE_FORMAT. */
    protected static final String BANNER_DATE_FORMAT = "dd";
    
    /** The Constant END_DATE_FORMAT. */
    protected static final String END_DATE_FORMAT = "dd, yyyy";
    
    /** The Constant DAYS. */
    protected static final String DAYS = "days";
    
    /** The Constant DAY. */
    protected static final String DAY = "day";
	
	/** The Constant HOURS. */
    protected static final String HOURS = "hours";
    
    /** The Constant HOUR. */
    protected static final String HOUR = "hour";
	
	/** The Constant MINUTES. */
    protected static final String MINUTES = "minutes";
    
    /** The Constant MINUTE. */
    protected static final String MINUTE = "minute";

	/**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
    	
        	logger.debug("EventDetailsModel Initialized");
        	Date currentDate = new Date();
          
        	/*
        	 * First Event start date
        	 * Last Event End date
        	 */
        	int count = 0;
        	if(null != eventDateAndTimeLabel) {
	        	for(EventDateAndTimeModel dateAndTimeModel:eventDateAndTimeLabel) { 
	        		if(count == 0) 
	        			multifieldEventStartDate = dateAndTimeModel.getMultifieldFirstStartDate();
	        			multifieldEventEndDate = dateAndTimeModel.getMultifieldLastEndDate();
	        		count++;
	        	}
        	}
        	
        	if (null != multifieldEventStartDate && null != multifieldEventEndDate) {
        		Date startingDate = getStringDateInDateFormat(multifieldEventStartDate);
        		Date endingDate = getStringDateInDateFormat(multifieldEventEndDate);
        		
	            if(null != startingDate && currentDate.before(startingDate)) {
	            	eventTimeLabel = CommonHelper.getLabel(CommonConstants.UPCOMING, currentPage);
	            }
	            else if (null != endingDate && currentDate.after(endingDate)) {
	            	eventTimeLabel = CommonHelper.getLabel(CommonConstants.PAST, currentPage);
	            }
	            else{
	            	eventTimeLabel = CommonHelper.getLabel(CommonConstants.CURRENT, currentPage);
	            }
        	}
        	bannerImage = externalizerService.getFormattedUrl(bannerImage, resourceResolver);
        	bannerThumbnailImage = externalizerService.getFormattedUrl(bannerThumbnailImage, resourceResolver);
        	bannerURL = externalizerService.getFormattedUrl(bannerURL, resourceResolver);
        	speakerImage =  externalizerService.getFormattedUrl(speakerImage, resourceResolver);
        	dateIcon = externalizerService.getFormattedUrl(dateIcon, resourceResolver);
        	timeIcon = externalizerService.getFormattedUrl(timeIcon, resourceResolver);
        	locationIcon = externalizerService.getFormattedUrl(locationIcon, resourceResolver);
			ctaUrl = externalizerService.getFormattedUrl(ctaUrl, resourceResolver);
			eventUrlCta = externalizerService.getFormattedUrl(eventUrlCta, resourceResolver);
	    	eventUrlCtaAdd = externalizerService.getFormattedUrl(eventUrlCtaAdd, resourceResolver);
    }
    
    
	/**
	 * Gets the date and time.
	 *
	 * @param strDateTime the str date time
	 * @param dateTimeFormat the date time format
	 * @return the date and time
	 */
    public  String getDateAndTime(String strDateTime, String dateTimeFormat) {
		return Optional.ofNullable(strDateTime).filter(s -> !s.equals("")).map(s -> {
			try {
				return new SimpleDateFormat(STANDARD_DATE_FORMAT).parse(s);
			} catch (ParseException e) {
				logger.error("ParseException {}", e.getMessage());
			}
			return null;
		}).map(d -> new SimpleDateFormat(dateTimeFormat).format(d)).orElse("");
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
				return new SimpleDateFormat(STANDARD_DATE_FORMAT).parse(s);
			} catch (ParseException e) {
				logger.error("ParseException {}", e.getMessage());
			}
			return null;
		}).orElse(null);
	}
    
	
	/**
	 * Gets the event start date.
	 *
	 * @return the event start date
	 */
	public String getEventStartDate() {
			return getDateAndTime(multifieldEventStartDate, DATE_FORMAT);
	}

	
	/**
	 * Gets the Banner Section Date.
	 *
	 * @return the Banner Section Date
	 */
	public String getBannerDate() {
		if (null != eventDateAndTimeLabel) {
			 String startDateMonth = getDateAndTime(multifieldEventStartDate, BANNER_DATE_MONTH_FORMAT);
		     String EndDateMonth = getDateAndTime(multifieldEventEndDate, BANNER_DATE_MONTH_FORMAT);
		     String startDate = getDateAndTime(multifieldEventStartDate, BANNER_DATE_FORMAT);
		     String endDate = getDateAndTime(multifieldEventEndDate, BANNER_DATE_FORMAT);
		     
			 if(EndDateMonth.equals(startDateMonth) && endDate.equals(startDate))
				 return getDateAndTime(multifieldEventStartDate, DATE_FORMAT);
			 else if(EndDateMonth.equals(startDateMonth))
				 return getDateAndTime(multifieldEventStartDate, START_DATE_FORMAT)+" - "+getDateAndTime(multifieldEventEndDate, END_DATE_FORMAT);
		     else
				 return getDateAndTime(multifieldEventStartDate, START_DATE_FORMAT)+" - "+getDateAndTime(multifieldEventEndDate, DATE_FORMAT);
		} else
			return null;
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
	 * Gets the multifield section.
	 *
	 * @return the multifield section
	 */
	public List<MultifieldTextModel> getMultifieldSection() {
		return new ArrayList<>(multifieldSection);
	}
	
	/**
	 * Gets the Event Date and Time.
	 *
	 * @return the Event Date and Time
	 */
	public List<EventDateAndTimeModel> getEventDateAndTimeLabel() {
		return new ArrayList<>(eventDateAndTimeLabel);
	}


	/**
	 * @return the multifieldSpeakerSection
	 */
	public List<MultifieldSpeakerTextModel> getMultifieldSpeakerSection() {
		if(null == multifieldSpeakerSection) {
			return new ArrayList<>();
		}
		return new ArrayList<>(multifieldSpeakerSection);
	}

	/**
	 * @return the rootURL
	 */
	public String getRootURL() {
		return rootURL;
	}


	/**
	 * @return the munchkinID
	 */
	public String getMunchkinID() {
		return munchkinID;
	}


	/**
	 * @return the formID
	 */
	public String getFormID() {
		return formID;
	}


	/**
	 * @return the formTitle
	 */
	public String getFormTitle() {
		return formTitle;
	}


	/**
	 * @return the formBodyText
	 */
	public String getFormBodyText() {
		return formBodyText;
	}

	/**
	 * Gets the banner image.
	 *
	 * @return the banner image
	 */
	public String getBannerImage() {
		return bannerImage; 
	}
	
	/**
	 * Gets the banner title.
	 *
	 * @return the banner title
	 */
	public String getBannerTitle() {
		return bannerTitle; 
	}
	
	/**
	 * Gets the banner thumbnail image.
	 *
	 * @return the banner thumbnail image
	 */
	public String getBannerThumbnailImage() {
		return bannerThumbnailImage; 
	}

	/**
	 * @return the darkMode
	 */
	public String getDarkMode() {
		return darkMode;
	}

	/**
	 * Gets the banner URL.
	 *
	 * @return the banner URL
	 */
	public String getBannerURL() {
		return bannerURL; 
	}


	/**
	 * Gets the speaker image.
	 *
	 * @return the speaker image
	 */
	public String getSpeakerImage() {
		return speakerImage;
	}
	 
    /**
	 * @return the eventBorder
	 */
	public String getEventBorder() {
		return eventBorder;
	}

	/**
	 * @return the eventVideoId
	 */
	public String getEventVideoId() {
		return eventVideoId;
	}

    /**
	 * @return the eventVideoCaption
	 */
	public String getEventVideoCaption() {
		return eventVideoCaption;
	}

	/**
	 * @return the eventVideoTitle
	 */
	public String getEventVideoTitle() {
		return eventVideoTitle;
	}

	/**
	 * @return the eventVideoDesc
	 */
	public String getEventVideoDesc() {
		return eventVideoDesc;
	}

	/**
	 * @return the eventUrlCta
	 */
	public String getEventUrlCta() {
		return eventUrlCta;
	}

	/**
	 * @return the eventLabelCta
	 */
	public String getEventLabelCta() {
		return eventLabelCta;
	}

	/**
	 * @return the eventLabelCtaAdd
	 */
	public String getEventLabelCtaAdd() {
		return eventLabelCtaAdd;
	}

	/**
	 * @return the eventUrlCtaAdd
	 */
	public String getEventUrlCtaAdd() {
		return eventUrlCtaAdd;
	}

	/**
	 * Gets the brightcove account id.
	 *
	 * @return the brightcove account id
	 */
	public String getBrightcoveAccountId() {
		return bdbApiEndpointService.brightcoveAccountId();
	}
	
	/**
	 * Gets the brightcove player id.
	 *
	 * @return the brightcove player id
	 */
	public String getBrightcovePlayerId() {
		return bdbApiEndpointService.brightcovePlayerId();
	}

	/**
	 * Gets the date icon.
	 *
	 * @return the date icon
	 */
	public String getDateIcon() {
		return dateIcon; 
	}

	/**
	 * Gets the time icon.
	 *
	 * @return the time icon
	 */
	public String getTimeIcon() {
		return timeIcon; 
	}

	/**
	 * Gets the location icon.
	 *
	 * @return the location icon
	 */
	public String getLocationIcon() {
		return locationIcon; 
	}

	/**
	 * Gets the cta url.
	 *
	 * @return the cta url
	 */
	public String getCtaUrl() {
		return ctaUrl; 
	}

}