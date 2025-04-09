package com.bdb.aem.core.services;

/**
 * The Interface BdbComponentDisabler.
 */
public interface IndexContentListenerService {

	/**
	 * Gets the base page path.
	 *
	 * @return the base page path
	 */
	public String getBasePagePath();

	/**
	 * Gets the asset path.
	 *
	 * @return the asset path
	 */
	public String[] getAssetPath();
	
	/**
	 * Gets the scientific res path.
	 *
	 * @return the scientific res path
	 */
	public String getScientificResPath();

}
