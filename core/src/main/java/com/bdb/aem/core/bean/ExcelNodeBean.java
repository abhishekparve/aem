package com.bdb.aem.core.bean;

/**
 * The Class ExcelNodeBean.
 */
public class ExcelNodeBean {

	/** The base file name. */
	private String baseFileName;
	
	/** The mat number. */
	private String matNumber;
	
	/** The doc part id. */
	private String docPartId;
	
	/** The doc type. */
	private String docType;
	
	/** The region. */
	private String[] region;

	/**
	 * Gets the base file name.
	 *
	 * @return the base file name
	 */
	public String getBaseFileName() {
		return baseFileName;
	}

	/**
	 * Sets the base file name.
	 *
	 * @param baseFileName the new base file name
	 */
	public void setBaseFileName(String baseFileName) {
		this.baseFileName = baseFileName;
	}

	/**
	 * Gets the mat number.
	 *
	 * @return the mat number
	 */
	public String getMatNumber() {
		return matNumber;
	}

	/**
	 * Sets the mat number.
	 *
	 * @param matNumber the new mat number
	 */
	public void setMatNumber(String matNumber) {
		this.matNumber = matNumber;
	}

	/**
	 * Gets the doc part id.
	 *
	 * @return the doc part id
	 */
	public String getDocPartId() {
		return docPartId;
	}

	/**
	 * Sets the doc part id.
	 *
	 * @param docPartId the new doc part id
	 */
	public void setDocPartId(String docPartId) {
		this.docPartId = docPartId;
	}

	/**
	 * Gets the doc type.
	 *
	 * @return the doc type
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * Sets the doc type.
	 *
	 * @param docType the new doc type
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String[] getRegion() {
		return region.clone();
	}

	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(String[] region) {
		this.region = region.clone();
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ExcelNodeBean [baseFileName=" + baseFileName + ", matNumber=" + matNumber + ", docPartId=" + docPartId
				+ ", docType=" + docType + ", region=" + region + "]";
	}
}
