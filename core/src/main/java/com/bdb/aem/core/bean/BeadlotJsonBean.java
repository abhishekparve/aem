package com.bdb.aem.core.bean;

import java.util.Map;

/**
 * The Class BeadlotJsonBean.
 */
public class BeadlotJsonBean {

	/** The part number. */
	private String partNumber;

	/** The document description. */
	private String documentDescription;
	
	/** The part number. */
	private String materialnumber;
	
	/** The document description. */
	private String materialDescpription;

	/** The beadlot file. */
	private Map<String, String> beadlotFile;

	/**
	 * Gets the part number.
	 *
	 * @return the part number
	 */
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * Sets the part number.
	 *
	 * @param partNumber the new part number
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * Gets the document description.
	 *
	 * @return the document description
	 */
	public String getDocumentDescription() {
		return documentDescription;
	}

	/**
	 * Sets the document description.
	 *
	 * @param documentDescription the new document description
	 */
	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	/**
	 * Gets the beadlot file.
	 *
	 * @return the beadlot file
	 */
	public Map<String, String> getBeadlotFile() {
		return beadlotFile;
	}

	/**
	 * Sets the beadlot file.
	 *
	 * @param beadlotFile the beadlot file
	 */
	public void setBeadlotFile(Map<String, String> beadlotFile) {
		this.beadlotFile = beadlotFile;
	}

	/**
	 * Gets the materialnumber.
	 *
	 * @return the materialnumber
	 */
	public String getMaterialnumber() {
		return materialnumber;
	}

	/**
	 * Sets the materialnumber.
	 *
	 * @param materialnumber the new materialnumber
	 */
	public void setMaterialnumber(String materialnumber) {
		this.materialnumber = materialnumber;
	}

	/**
	 * Gets the material descpription.
	 *
	 * @return the material descpription
	 */
	public String getMaterialDescpription() {
		return materialDescpription;
	}

	/**
	 * Sets the material descpription.
	 *
	 * @param materialDescpription the new material descpription
	 */
	public void setMaterialDescpription(String materialDescpription) {
		this.materialDescpription = materialDescpription;
	}
}
