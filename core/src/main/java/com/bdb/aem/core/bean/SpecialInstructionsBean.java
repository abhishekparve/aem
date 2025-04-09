package com.bdb.aem.core.bean;

import com.google.gson.annotations.SerializedName;

/**
 * The Class SpecialInstructionsBean.
 */
public class SpecialInstructionsBean {

	/** The special instructions heading. */
	@SerializedName("specialInstructionsHeading")
	private String specialInstructionsHeading;

	/**
	 * Gets the special instructions heading.
	 *
	 * @return the special instructions heading
	 */
	public String getSpecialInstructionsHeading() {
		return specialInstructionsHeading;
	}

	/**
	 * Sets the special instructions heading.
	 *
	 * @param specialInstructionsHeading the new special instructions heading
	 */
	public void setSpecialInstructionsHeading(String specialInstructionsHeading) {
		this.specialInstructionsHeading = specialInstructionsHeading;
	}

}
