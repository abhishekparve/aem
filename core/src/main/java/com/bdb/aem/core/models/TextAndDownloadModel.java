package com.bdb.aem.core.models;

/**
 * The Interface TextAndDownloadModel.
 */
public interface TextAndDownloadModel {
	
	/**
	 * Gets the link label.
	 *
	 * @return the link label
	 */
	public String getLinkLabel();

	/**
	 * Gets the link path.
	 *
	 * @return the link path
	 */
	public String getLinkPath();
	
	/**
	 * Gets the primary cta label.
	 *
	 * @return the primary cta label
	 */
	public String getPrimaryCtaLabel();

	/**
	 * Gets the primary cta path.
	 *
	 * @return the primary cta path
	 */
	public String getPrimaryCtaPath();

	/**
	 * Gets the optional cta label.
	 *
	 * @return the optional cta label
	 */
	public String getOptionalCtaLabel();

	/**
	 * Gets the optional cta path.
	 *
	 * @return the optional cta path
	 */
	public String getOptionalCtaPath();
	
	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption();


	/**
	 * Gets the optional image path.
	 *
	 * @return the optional image path
	 */
	public
	String getImagePath();
	
	/**
     * Gets the article id.
     *
     * @return the article id
     */
	public String getArticleId();

}
