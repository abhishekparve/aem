package com.bdb.aem.core.bean;

/**
 * The Class PdfMetaDataBean.
 */
public class PdfMetaDataBean {

	/** The doc title. */
	private String docTitle = "";

	/** The doc type. */
	private String docType = "";

	/** The doc region. */
	private String[] docRegion;

	/** The doc lang. */
	private String docLang = "EN";

	/** The doc sku. */
	private String docSku = "";

	/** The doc desc. */
	private String docDesc = "";

	/** The doc expiry date. */
	private String docExpiryDate = "";

	/** The doc reg status. */
	private String docRegStatus = "";

	/** The doc revision. */
	private String docRevision = "";

	/** The doc owner. */
	private String docOwner = "PT";

	/** The temp folder file name. */
	private String tempFolderFileName;

	/** The temp folder file path. */
	private String tempFolderFilePath;

	/** The dam folder file name. */
	private String damFolderFileName;

	/** The dam folder file path. */
	private String damFolderFilePath;
	
	/** The doc part id. */
	private String docPartId;
	
	/** The doc keywords. */
	private String docKeywords;
	
	/** The product name. */
	private String productName;
	
	/** The Basic description. */
	private String desc = "";
	
	/**
	 * Gets the doc keywords.
	 *
	 * @return the doc keywords
	 */
	public String getDocKeywords() {
		return docKeywords;
	}

	/**
	 * Sets the doc keywords.
	 *
	 * @param docKeywords the new doc keywords
	 */
	public void setDocKeywords(String docKeywords) {
		this.docKeywords = docKeywords;
	}

	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets the product name.
	 *
	 * @param productName the new product name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
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
	 * Gets the doc title.
	 *
	 * @return the doc title
	 */
	public String getDocTitle() {
		return docTitle;
	}

	/**
	 * Gets the temp folder file path.
	 *
	 * @return the temp folder file path
	 */
	public String getTempFolderFilePath() {
		return tempFolderFilePath;
	}

	/**
	 * Sets the temp folder file path.
	 *
	 * @param tempFolderFilePath the new temp folder file path
	 */
	public void setTempFolderFilePath(String tempFolderFilePath) {
		this.tempFolderFilePath = tempFolderFilePath;
	}

	/**
	 * Gets the dam folder file name.
	 *
	 * @return the dam folder file name
	 */
	public String getDamFolderFileName() {
		return damFolderFileName;
	}

	/**
	 * Sets the dam folder file name.
	 *
	 * @param damFolderFileName the new dam folder file name
	 */
	public void setDamFolderFileName(String damFolderFileName) {
		this.damFolderFileName = damFolderFileName;
	}

	/**
	 * Gets the dam folder file path.
	 *
	 * @return the dam folder file path
	 */
	public String getDamFolderFilePath() {
		return damFolderFilePath;
	}

	/**
	 * Sets the dam folder file path.
	 *
	 * @param damFolderFilePath the new dam folder file path
	 */
	public void setDamFolderFilePath(String damFolderFilePath) {
		this.damFolderFilePath = damFolderFilePath;
	}

	/**
	 * Sets the doc title.
	 *
	 * @param docTitle the new doc title
	 */
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
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
	 * Gets the doc region.
	 *
	 * @return the doc region
	 */
	public String[] getDocRegion() {
		return docRegion.clone();
	}

	/**
	 * Sets the doc region.
	 *
	 * @param docRegion the new doc region
	 */
	public void setDocRegion(String[] docRegion) {
		this.docRegion = docRegion.clone();
	}

	/**
	 * Gets the doc lang.
	 *
	 * @return the doc lang
	 */
	public String getDocLang() {
		return docLang;
	}

	/**
	 * Sets the doc lang.
	 *
	 * @param docLang the new doc lang
	 */
	public void setDocLang(String docLang) {
		this.docLang = docLang;
	}

	/**
	 * Gets the doc sku.
	 *
	 * @return the doc sku
	 */
	public String getDocSku() {
		return docSku;
	}

	/**
	 * Sets the doc sku.
	 *
	 * @param docSku the new doc sku
	 */
	public void setDocSku(String docSku) {
		this.docSku = docSku;
	}

	/**
	 * Gets the doc desc.
	 *
	 * @return the doc desc
	 */
	public String getDocDesc() {
		return docDesc;
	}

	/**
	 * Sets the doc desc.
	 *
	 * @param docDesc the new doc desc
	 */
	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	/**
	 * Gets the doc expiry date.
	 *
	 * @return the doc expiry date
	 */
	public String getDocExpiryDate() {
		return docExpiryDate;
	}

	/**
	 * Sets the doc expiry date.
	 *
	 * @param docExpiryDate the new doc expiry date
	 */
	public void setDocExpiryDate(String docExpiryDate) {
		this.docExpiryDate = docExpiryDate;
	}

	/**
	 * Gets the doc reg status.
	 *
	 * @return the doc reg status
	 */
	public String getDocRegStatus() {
		return docRegStatus;
	}

	/**
	 * Sets the doc reg status.
	 *
	 * @param docRegStatus the new doc reg status
	 */
	public void setDocRegStatus(String docRegStatus) {
		this.docRegStatus = docRegStatus;
	}

	/**
	 * Gets the doc revision.
	 *
	 * @return the doc revision
	 */
	public String getDocRevision() {
		return docRevision;
	}

	/**
	 * Sets the doc revision.
	 *
	 * @param docRevision the new doc revision
	 */
	public void setDocRevision(String docRevision) {
		this.docRevision = docRevision;
	}

	/**
	 * Gets the doc owner.
	 *
	 * @return the doc owner
	 */
	public String getDocOwner() {
		return docOwner;
	}

	/**
	 * Sets the doc owner.
	 *
	 * @param docOwner the new doc owner
	 */
	public void setDocOwner(String docOwner) {
		this.docOwner = docOwner;
	}

	/**
	 * Gets the temp folder file name.
	 *
	 * @return the temp folder file name
	 */
	public String getTempFolderFileName() {
		return tempFolderFileName;
	}

	/**
	 * Sets the temp folder file name.
	 *
	 * @param tempFileFolderFileName the new temp folder file name
	 */
	public void setTempFolderFileName(String tempFileFolderFileName) {
		this.tempFolderFileName = tempFileFolderFileName;
	}

	/**
	 * Gets the basic tab description.
	 * 
	 * @return the Basic desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the basic tab description
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
