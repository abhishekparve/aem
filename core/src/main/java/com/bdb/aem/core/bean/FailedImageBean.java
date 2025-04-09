package com.bdb.aem.core.bean;

/**
 * The Class BeadlotJsonBean.
 */
public class FailedImageBean {

	
	/** The imageName. */
	private String imageName;
	
	/** The materialNumber. */
	private String materialNumber;
	
	/** The errorReason. */
	private String errorReason;
	
	/** The retryCount. */
	private String retryCount;

	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * @return the materialNumber
	 */
	public String getMaterialNumber() {
		return materialNumber;
	}

	/**
	 * @param materialNumber the materialNumber to set
	 */
	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	/**
	 * @return the errorReason
	 */
	public String getErrorReason() {
		return errorReason;
	}

	/**
	 * @param errorReason the errorReason to set
	 */
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	/**
	 * @return the retryCount
	 */
	public String getRetryCount() {
		return retryCount;
	}

	/**
	 * @param retryCount the retryCount to set
	 */
	public void setRetryCount(String retryCount) {
		this.retryCount = retryCount;
	}
	
}