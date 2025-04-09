package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.SocialMedia;
import com.bdb.aem.core.models.SocialMediaMultiFieldModel;

/**
 * 
 * SocialMedia Class Implementation
 *
 */
@Model(
        adaptables = {Resource.class},
        adapters = {SocialMedia.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SocialMediaImpl implements SocialMedia{

	/** The logger. */
    Logger logger = LoggerFactory.getLogger(SocialMediaImpl.class);
	
	 /** Title for the Social Media */
	@Inject
	String socialMediaTitle;
	
	 /** Social Media Details : a multi-field property fetching as List of Resources */
	@Inject
	List<Resource> socialMediaDetails;
	
	/** Variable which holds the socialMediaDetails multi-field as list*/
	private List<SocialMediaMultiFieldModel> socialMediaAsList = new ArrayList<>();
	
	
	 /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        	logger.debug("Social Media Initiated");
            if (socialMediaDetails != null && !socialMediaDetails.isEmpty()) {
            	logger.debug("social list :: {}", socialMediaDetails);
                for (Resource resource : socialMediaDetails) {
                	SocialMediaMultiFieldModel socialMedia = resource.adaptTo(SocialMediaMultiFieldModel.class); 
                	socialMediaAsList.add(socialMedia);
                }
            }
    }

	/**
	 * Getter methods for social Media Title
	 * @return social Media Title
	 */
	@Override
	public String getSocialMediaTitle() {
		return socialMediaTitle;
	}
	/**
	 * Getter to fetch the multi-field data of social Media Details
	 * @return Social Media Details as Array List
	 */
	 @Override
	 public List<SocialMediaMultiFieldModel> getSocialMediaAsList() {
			return new ArrayList<>(socialMediaAsList);
		}
}
