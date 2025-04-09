package com.bdb.aem.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * The Class EventDateAndTimeModel.
 */
@Model( adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class EventDateAndTimeModel {

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(EventDateAndTimeModel.class);

    /** The event start date. */
	@Inject
	private String multifieldEventStartDate;
	
	/** The event end date. */
	@Inject
	private String multifieldEventEndDate;
	
	/** The event date. */
	@Default(values = StringUtils.EMPTY)
	private String multifieldEventDate;
	
	/** The Constant STANDARD_DATE_FORMAT. */
	protected static final String STANDARD_DATE_FORMAT ="yyyy-MM-dd'T'HH:mm:ss.SSS";
	
	/** The Constant TIME_FORMAT. */
	protected static final String TIME_FORMAT = "hh:mm aa";
	
	/** The Constant DATE_FORMAT. */
	protected static final String DATE_FORMAT = "MMMM dd, yyyy";
	
	/** The Constant DATE_FORMAT_NEW. */
	protected static final String DATE_FORMAT_NEW = "MMM dd, yyyy";
	
	/** The Constant EVENT_DATE_MONTH. */
	protected static final String EVENT_DATE_MONTH = "MMM";
	
	/** The Constant EVENT_DATE_DAY. */
	protected static final String EVENT_DATE_DAY = "dd";
	
	/** The Constant EVENT_DATE_FORMAT. */
	protected static final String EVENT_DATE_FORMAT = "MMM dd";
	
	/** The Constant EVENT_DATE_FORMAT. */
	protected static final String EVENT_END_DATE_FORMAT = "dd, yyyy";
	
    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
		logger.debug("EventDateAndTimeModel Initialized");
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
	 * Gets the event start time.
	 *
	 * @return the event start time
	 */
	public String getEventStartTime() {
		String time = getDateAndTime(multifieldEventStartDate, TIME_FORMAT);
		if(time.contains(CommonConstants.AM))
			return time.replace(CommonConstants.AM, CommonConstants.AM_LOWERCASE);
		else
			return time.replace(CommonConstants.PM, CommonConstants.PM_LOWERCASE);
	}
	
	
	/**
	 * Gets the event end time.
	 *
	 * @return the event end time
	 */
	public String getEventEndTime() {
		String time = getDateAndTime(multifieldEventEndDate, TIME_FORMAT);
		if(time.contains(CommonConstants.AM))
			return time.replace(CommonConstants.AM, CommonConstants.AM_LOWERCASE);
		else
			return time.replace(CommonConstants.PM, CommonConstants.PM_LOWERCASE);
	}
	
	
	/**
	 * Gets the event start date.
	 *
	 * @return the event start date
	 */
	public String getMultifieldEventStartDate() {
		return getDateAndTime(multifieldEventStartDate, DATE_FORMAT);
	}
	

	/**
	 * Gets the event end date.
	 *
	 * @return the event end date
	 */
	public String getMultifieldEventEndDate() {
		return getDateAndTime(multifieldEventEndDate, DATE_FORMAT);
	}
	
	/**
	 * Gets the Event Registration Section Date.
	 *
	 * @return the Event Registration Section Date
	 */
	public String getMultifieldEventDate() {
		if (null != multifieldEventStartDate && null != multifieldEventEndDate) {
			String startDateMonth = getDateAndTime(multifieldEventStartDate, EVENT_DATE_MONTH);
		     String EndDateMonth = getDateAndTime(multifieldEventEndDate, EVENT_DATE_MONTH);
		     String startDay = getDateAndTime(multifieldEventStartDate, EVENT_DATE_DAY);
		     String endDay = getDateAndTime(multifieldEventEndDate, EVENT_DATE_DAY);
		     
			 if(EndDateMonth.equals(startDateMonth) && endDay.equals(startDay))
				 return getDateAndTime(multifieldEventStartDate, DATE_FORMAT_NEW);
			 else if(EndDateMonth.equals(startDateMonth))
				 return getDateAndTime(multifieldEventStartDate, EVENT_DATE_FORMAT)+" - "+getDateAndTime(multifieldEventEndDate, EVENT_END_DATE_FORMAT);
		     else
				 return getDateAndTime(multifieldEventStartDate, EVENT_DATE_FORMAT)+" - "+getDateAndTime(multifieldEventEndDate, DATE_FORMAT_NEW);
	
		}else
			return null;
	}	 
	
	//Gets Multifield First Start Date in standard format
	public String getMultifieldFirstStartDate() {
		return multifieldEventStartDate;
	}
	
	//Gets multifield last End Date in standard format
	public String getMultifieldLastEndDate() {
		return multifieldEventEndDate;
	}
	
}