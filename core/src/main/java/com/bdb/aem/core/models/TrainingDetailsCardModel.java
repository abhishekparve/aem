package com.bdb.aem.core.models;

import java.util.List;

/**
 * The Interface TrainingDetailsCardModel.
 */
public interface TrainingDetailsCardModel {
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	String getTitle();
	
	/**
	 * Gets the description list.
	 *
	 * @return the description list
	 */
	List<ApiErrorMessagesModel> getDescriptionList();
	
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
	 * Gets the image link url.
	 *
	 * @return the image link url
	 */
	String getImageLinkUrl();
	
	/**
	 * Gets the open image in new tab.
	 *
	 * @return the open image in new tab
	 */
	String getOpenNewImageLinkTab();
	
	/**
	 * Gets the image video alt text.
	 *
	 * @return the image video alt text
	 */
	String getImageVideoAltText();
	
	/**
	 * Gets the caption under image.
	 *
	 * @return the caption under image
	 */
	String getCaptionUnderImage();
	
	/**
	 * Gets the primary cta label.
	 *
	 * @return the primary cta label
	 */
	String getPrimaryCtaLabel();
	
	/**
	 * Gets the primary cta path.
	 *
	 * @return the primary cta path
	 */
	String getPrimaryCtaPath();
	
	/**
	 * Gets the optional cta label.
	 *
	 * @return the optional cta label
	 */
	String getOptionalCtaLabel();
	
	/**
	 * Gets the optional cta path.
	 *
	 * @return the optional cta path
	 */
	String getOptionalCtaPath();
	
	/**
	 * Gets the mini thumbnail image.
	 *
	 * @return the mini thumbnail image
	 */
	String getMiniThumbnailImage();
	
	/**
	 * Gets the mini thumbnail alt text.
	 *
	 * @return the mini thumbnail alt text
	 */
	String getMiniThumbnailAltText();
	
	/**
	 * Checks if is featured.
	 *
	 * @return true, if is featured
	 */
	boolean isFeatured();
	
	/**
	 * Gets the section align.
	 *
	 * @return the section align
	 */
	String getSectionAlign();
}
