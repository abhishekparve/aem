package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.SerializedName;

/**
 * The Class TransalationUrlsListModel.
 */
@Model(	adaptables = {SlingHttpServletRequest.class,Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TransalationUrlsListModel {
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(TransalationUrlsListModel.class);
	
	/** The translationUrl. */
	@Inject
	@SerializedName("translationUrl")
    private String translationUrl;

	/**
	 * @return the translationUrl
	 */
	public String getTranslationUrl() {
		return translationUrl;
	}
	
}
