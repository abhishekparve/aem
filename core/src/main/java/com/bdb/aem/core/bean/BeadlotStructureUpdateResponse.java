package com.bdb.aem.core.bean;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class BeadlotStructureUpdateResponse.
 */
public class BeadlotStructureUpdateResponse {

	/** The status. */
	@SerializedName("status")
	@Expose
	private String status;
	
	/** The status message. */
	@SerializedName("statusMessage")
	@Expose
	private String statusMessage;
	
	/** The error SK us. */
	@SerializedName("errorSKUs")
	@Expose
	private Set<ErrorSKU> errorSKUs = new HashSet<>();

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the status message.
	 *
	 * @return the status message
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * Sets the status message.
	 *
	 * @param statusMessage the new status message
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/**
	 * Gets the error SK us.
	 *
	 * @return the error SK us
	 */
	public Set<ErrorSKU> getErrorSKUs() {
		return new HashSet<>(errorSKUs);
	}

	/**
	 * Sets the error SK us.
	 *
	 * @param errorSKUs the new error SK us
	 */
	public void setErrorSKUs(Set<ErrorSKU> errorSKUs) {
		if (errorSKUs == null)
	        throw new IllegalArgumentException("Parameter errorSKUs is null");
	    this.errorSKUs.clear();
	    this.errorSKUs.addAll(errorSKUs);
	}

}