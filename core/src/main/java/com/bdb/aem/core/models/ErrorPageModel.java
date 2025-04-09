package com.bdb.aem.core.models;

import java.util.List;

/**
 * The Interface ErrorPageModel.
 */
public interface ErrorPageModel {
	
	/**
	 * Gets the image src.
	 *
	 * @return the image src
	 */
	String getImageSrc();	

	/**
	 * Gets the image alt text.
	 *
	 * @return the image alt text
	 */
	String getImageAltText();

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	String getTitle();

	/**
	 * Gets the subtitle.
	 *
	 * @return the subtitle
	 */
	String getSubtitle();

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	String getDescription();

	/**
	 * Gets the link info text.
	 *
	 * @return the link info text
	 */
	String getLinkInfoText();

	/**
	 * Gets the cta list.
	 *
	 * @return the cta list
	 */
	List<CtaModel> getCtaList();
}
