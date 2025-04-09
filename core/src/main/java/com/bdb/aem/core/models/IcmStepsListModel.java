package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.SerializedName;

/**
 * The Class IcmStepsListModel.
 */
@Model(	adaptables = Resource.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IcmStepsListModel {
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(IcmStepsListModel.class);
	
	/** The stepTitle. */
	@Inject
	@SerializedName("title")
    private String stepTitle;
	
	/** The firstWord. */
	@Inject
	@SerializedName("firstWord")
    private String firstWord;
	
	/** The description. */
	@Inject
	@SerializedName("description")
    private String description;
	
	/** The stepImg. */
	@Inject
	@SerializedName("imgSrc")
    private String stepImg;
	
	/** The stepImgAlt. */
	@Inject
	@SerializedName("stepImgAlt")
    private String stepImgAlt;

	public String getStepTitle() {
		return stepTitle;
	}

	public String getFirstWord() {
		return firstWord;
	}

	public String getDescription() {
		return description;
	}

	public String getStepImg() {
		return stepImg;
	}

	public String getStepImgAlt() {
		return stepImgAlt;
	}
	
	
	
}
