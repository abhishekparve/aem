package com.bdb.aem.core.models;

/**
 * The Interface BeadlotLinkOnPdpModel.
 */
public interface BeadlotLinkOnPdpModel {

	/**
	 * Gets the checks for beadlot.
	 *
	 * @return the checks for beadlot
	 */
	public boolean getHasBeadlot();

	/**
	 * Gets the title of beadlot file.
	 *
	 * @return the title of beadlot file
	 */
	public String getTitleOfBeadlotFile();

	/**
	 * Gets the landing page url.
	 *
	 * @return the landing page url
	 */
	public String getLandingPageUrl();

	/**
	 * Gets the no beadlot text.
	 *
	 * @return the no beadlot text
	 */
	public String getNoBeadlotText();

	/**
	 * Gets the header text.
	 *
	 * @return the header text
	 */
	public String getHeaderText();
	
	/**
	 * Gets the view all text.
	 *
	 * @return the view all text
	 */
	public String getViewAllText();
}