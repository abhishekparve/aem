package com.bdb.aem.core.models;

/**
 * The Interface PurchasingAccountModel.
 */
public interface PurchasingAccountModel {
	
	/**
	 * Gets the purchase account labels.
	 *
	 * @return the purchase account labels
	 */
	String getPurchaseAccountLabels();
	
	/**
	 * Gets the purchase account config.
	 *
	 * @return the purchase account config
	 */
	String getPurchaseAccountConfig();
	
	/**
	 * Gets the purchase account confirmation labels.
	 *
	 * @return the purchase account confirmation labels
	 */
	String getPurchaseAccountConfirmationLabels();	
	
	/**
	 * Gets the purchase account confirmation config.
	 *
	 * @return the purchase account confirmation config
	 */
	String getPurchaseAccountConfirmationConfig();
}