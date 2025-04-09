package com.bdb.aem.core.models;

import java.util.List;

/**
 * The Interface ProtocolModel.
 */
public interface ProtocolModel {
	
	/**
	 * Gets the protocol title.
	 *
	 * @return the protocol title
	 */
	String getProtocolTitle();
	
	/**
	 * Checks if is included.
	 *
	 * @return the string
	 */
	String isIncluded();
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	String getDescription();
	
	/**
	 * Checks if is video enabled.
	 *
	 * @return true, if is video enabled
	 */
	boolean isVideoEnabled();
	
	/**
	 * Gets the brightcove video id.
	 *
	 * @return the brightcove video id
	 */
	String getBrightcoveVideoId();
	
	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	String getImagePath();
	
	/**
	 * Gets the image alt text.
	 *
	 * @return the image alt text
	 */
	String getImageAltText();
	
	/**
	 * Gets the protocols list.
	 *
	 * @return the protocols list
	 */
	List<ProtocolTextLinkModel> getProtocolsList();
	
	/**
	 * Gets the view more label.
	 *
	 * @return the view more label
	 */
	String getViewMoreLabel();
	
	/**
	 * Gets the view less label.
	 *
	 * @return the view less label
	 */
	String getViewLessLabel();

	/**
	 * Gets the protocol count.
	 *
	 * @return the protocol count
	 */
	int getProtocolCount();
	
	/**
	 * Gets the protocol object json.
	 *
	 * @return the protocol object json
	 */
	String getProtocolObjectJson();
}
