package com.bdb.aem.core.models;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TimerModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(TimerModel.class);

	@Getter
	public String time;
	
	private long startTime;
	
	public String getEndTime() {
		return String.valueOf(new Date().getTime()-startTime);
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
    protected void init() {
		startTime = new Date().getTime();
		time = String.valueOf(startTime);	
	}

}
