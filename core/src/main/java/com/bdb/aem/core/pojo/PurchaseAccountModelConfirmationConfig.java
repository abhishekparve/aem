package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class PurchaseAccountModelConfirmationConfig.
 */
public class PurchaseAccountModelConfirmationConfig {
	
	/** The is successful. */
	@SerializedName("isSuccessful")
	private String isSuccessful;

	/** The updateRewardsPreferences. */
	@SerializedName("updateRewardsPreferences")
	private PayloadConfig updateRewardsPreferences;


	/**
	 * Instantiates a new purchase account model confirmation config.
	 *
	 * @param isSuccessful the is successful
	 * @param updateRewardsPreferences the updateRewardsPreferences
	 */
	public PurchaseAccountModelConfirmationConfig(String isSuccessful,PayloadConfig updateRewardsPreferences) {
		this.isSuccessful = isSuccessful;
		this.updateRewardsPreferences = updateRewardsPreferences;
	}

	/**
	 * Instantiates a new purchase account model confirmation config.
	 *
	 */
	public PurchaseAccountModelConfirmationConfig() {
	}

	/**
	 * Instantiates a new purchase account model confirmation config.
	 *
	 * @param isSuccessful the is successful
	 */
	public PurchaseAccountModelConfirmationConfig(String isSuccessful) {
		this.isSuccessful = isSuccessful;
	}


	/**
	 * Gets the checks if is successful.
	 *
	 * @return the checks if is successful
	 */
	public String getIsSuccessful() {
		return isSuccessful;
	}

	/**
	 * Gets the checks if is successful.
	 *
	 * @return the updateRewardsPreferences
	 */
	public PayloadConfig getUpdateRewardsPreferences() {
		return updateRewardsPreferences;
	}


}
