package com.bdb.aem.core.models;

import java.util.List;

public interface SocialMedia {
	
	/**
	 * Gets the Social Media Title.
	 *
	 * @return the Social Media title
	 */
	String getSocialMediaTitle();
	
	/**
	 * Gets the Social Media Details Multi-Field as a List
	 *
	 * @return the List of image url, image path and image Alt text.
	 */
	List<SocialMediaMultiFieldModel> getSocialMediaAsList();	
}
