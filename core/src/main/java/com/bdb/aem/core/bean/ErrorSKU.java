package com.bdb.aem.core.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class ErrorSKU.
 */
public class ErrorSKU {

	/** The document part number. */
	@SerializedName("documentPartNumber")
	@Expose
	private String documentPartNumber;

	/**
	 * Gets the document part number.
	 *
	 * @return the document part number
	 */
	public String getDocumentPartNumber() {
		return documentPartNumber;
	}

	/**
	 * Sets the document part number.
	 *
	 * @param documentPartNumber the new document part number
	 */
	public void setDocumentPartNumber(String documentPartNumber) {
		this.documentPartNumber = documentPartNumber;
	}

}