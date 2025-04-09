package com.bdb.aem.core.pojo;

import java.util.ArrayList;
import java.util.List;

import com.bdb.aem.core.bean.AccountManagementCertificationsLabelsBean;
import com.bdb.aem.core.models.UploadCertificationFileTypeModel;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountManagementCertificationsModelLabels.
 */
public class AccountManagementCertificationsModelLabels {

	/** The certifications image. */
	@SerializedName("image")
	private String certificationsImage;
	
	/** The certifications header. */
	@SerializedName("certificationTitle")
	private String certificationsHeader;
	
	/** The upload documents cta label. */
	@SerializedName("uploadDocumentCTALabel")
	private String uploadDocumentsCtaLabel;

	/** The no certificate text. */
	@SerializedName("uploadDocumentsNoDataLabel")
	private String noCertificateText;
	
	/** The upload instruction label. */
	@SerializedName("uploadInstructionLabel")
	private String uploadInstructionLabel;
	
	/** The document name. */
	@SerializedName("documentNameLabel")
	private String documentName;

	/** The certification type. */
	@SerializedName("certificationTypeLabel")
	private String certificationType;

	/** The expiry date. */
	@SerializedName("expiryDateLabel")
	private String expiryDate;

	/** The status fields. */
	@SerializedName("statusLabel")
	private String statusFields;

	/** The remove. */
	@SerializedName("removeCTALabel")
	private String remove;
	
	/** The linked address. */
	@SerializedName("linkedAddressLabel")
	private String linkedAddress;
	
	/** The ship to number. */
	@SerializedName("shiptoNumberLabel")
	private String shipToNumber;

	/** The upload documents modal header. */
	@SerializedName("title")
	private String uploadDocumentsModalHeader;

	/** The close icon. */
	@SerializedName("closeIcon")
	private String closeIcon;
	
	/** The close icon alt text. */
	@SerializedName("closeIconAltText")
	private String closeIconAltText;
	
	/** The file type text. */
	@SerializedName("fileTypeLabel")
	private String fileTypeText;
	
	/** The file types list. */
	@SerializedName("fileTypesList")
	private List<UploadCertificationFileTypeModel> fileTypesList;

	/** The address selection labels. */
	@SerializedName("AddressSelection")
	private JsonElement addressSelectionLabels;

	/** The cancel CTA label. */
	@SerializedName("cancelCTALabel")
	private String cancelCTALabel;

	/** The confirm CTA label. */
	@SerializedName("confirmCTaLabel")
	private String confirmCTALabel;
	
	/** The drop documents text. */
	@SerializedName("dropDocumentsText")
	private String dropDocumentsText;

	/** The choose files cta label. */
	@SerializedName("chooseFilesCtaLabel")
	private String chooseFilesCtaLabel;

	/** The address linked text. */
	@SerializedName("addressLinkedText")
	private String addressLinkedText;

	/** The upload success text. */
	@SerializedName("successMessage")
	private String uploadSuccessText;
	
	/** The withLabel text. */
	@SerializedName("withLabel")
	private String withLabel;
	
	/** The rowsLabel text. */
	@SerializedName("rowsLabel")
	private String rowsLabel;
	
	/** The andLabel text. */
	@SerializedName("andLabel")
	private String andLabel;
	
	/** The colLabel text. */
	@SerializedName("colLabel")
	private String colLabel;
	
	/** The Download Template link Label. */
	@SerializedName("downloadRUOCTA")
	private String downloadRUOCTA;

	/** The Download url  */
	@SerializedName("downloadRUOUrl")
	private String downloadRUOUrl;

	/** The downloadInfo. */
	@SerializedName("downloadRUOInfo")
	private String downloadRUOInfo;

	/**
	 * Instantiates a new account management certifications model labels.
	 *
	 * @param accountManagementCertificationsLabelsBean the account management certifications labels bean
	 */
	public AccountManagementCertificationsModelLabels(AccountManagementCertificationsLabelsBean accountManagementCertificationsLabelsBean) {
		this.certificationsImage = accountManagementCertificationsLabelsBean.getCertificationsImage();
		this.certificationsHeader = accountManagementCertificationsLabelsBean.getCertificationsHeader();
		this.uploadDocumentsCtaLabel = accountManagementCertificationsLabelsBean.getUploadDocumentsCtaLabel();
		this.noCertificateText = accountManagementCertificationsLabelsBean.getNoCertificateText();
		this.uploadInstructionLabel = accountManagementCertificationsLabelsBean.getUploadInstructionLabel();
		this.documentName = accountManagementCertificationsLabelsBean.getDocumentName();
		this.certificationType = accountManagementCertificationsLabelsBean.getCertificationType();
		this.expiryDate = accountManagementCertificationsLabelsBean.getExpiryDate();
		this.statusFields = accountManagementCertificationsLabelsBean.getStatusFields();
		this.linkedAddress = accountManagementCertificationsLabelsBean.getLinkedAddress();
		this.remove = accountManagementCertificationsLabelsBean.getRemove();
		this.shipToNumber = accountManagementCertificationsLabelsBean.getShipToNumber();
		
		this.uploadDocumentsModalHeader = accountManagementCertificationsLabelsBean.getUploadDocumentsModalHeader();
		this.closeIcon = accountManagementCertificationsLabelsBean.getCloseIcon();
		this.closeIconAltText = accountManagementCertificationsLabelsBean.getCloseIconAltText();
		this.fileTypeText = accountManagementCertificationsLabelsBean.getFileTypeText();
		this.fileTypesList = accountManagementCertificationsLabelsBean.getFileTypesList();
		this.addressSelectionLabels = accountManagementCertificationsLabelsBean.getAddressSelectionLabels();
		this.cancelCTALabel = accountManagementCertificationsLabelsBean.getCancel();
		this.confirmCTALabel = accountManagementCertificationsLabelsBean.getConfirm();
		
		this.dropDocumentsText = accountManagementCertificationsLabelsBean.getDropDocumentsText();
		this.chooseFilesCtaLabel = accountManagementCertificationsLabelsBean.getChooseFilesCtaLabel();		
		this.addressLinkedText = accountManagementCertificationsLabelsBean.getAddressLinkedText();
		this.uploadSuccessText = accountManagementCertificationsLabelsBean.getUploadSuccessText();
		
		this.withLabel = accountManagementCertificationsLabelsBean.getWithLabel();
		this.rowsLabel = accountManagementCertificationsLabelsBean.getRowsLabel();
		this.andLabel = accountManagementCertificationsLabelsBean.getAndLabel();
		this.colLabel = accountManagementCertificationsLabelsBean.getColLabel();
		this.downloadRUOCTA = accountManagementCertificationsLabelsBean.getDownloadRUOCTA();
		this.downloadRUOInfo = accountManagementCertificationsLabelsBean.getDownloadRUOInfo();
		this.downloadRUOUrl = accountManagementCertificationsLabelsBean.getDownloadRUOUrl();
	}

	public String getWithLabel() {
		return withLabel;
	}

	public String getRowsLabel() {
		return rowsLabel;
	}

	public String getAndLabel() {
		return andLabel;
	}

	public String getColLabel() {
		return colLabel;
	}

	/**
	 * Gets the certifications image.
	 *
	 * @return the certifications image
	 */
	public String getCertificationsImage() {
		return certificationsImage;
	}

	/**
	 * Gets the certifications header.
	 *
	 * @return the certifications header
	 */
	public String getCertificationsHeader() {
		return certificationsHeader;
	}

	/**
	 * Gets the upload documents cta label.
	 *
	 * @return the upload documents cta label
	 */
	public String getUploadDocumentsCtaLabel() {
		return uploadDocumentsCtaLabel;
	}

	/**
	 * Gets the no certificate text.
	 *
	 * @return the no certificate text
	 */
	public String getNoCertificateText() {
		return noCertificateText;
	}

	/**
	 * Gets the upload instruction label.
	 *
	 * @return the upload instruction label
	 */
	public String getUploadInstructionLabel() {
		return uploadInstructionLabel;
	}

	/**
	 * Gets the document name.
	 *
	 * @return the document name
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * Gets the certification type.
	 *
	 * @return the certification type
	 */
	public String getCertificationType() {
		return certificationType;
	}

	/**
	 * Gets the expiry date.
	 *
	 * @return the expiry date
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Gets the status fields.
	 *
	 * @return the status fields
	 */
	public String getStatusFields() {
		return statusFields;
	}

	/**
	 * Gets the linked address.
	 *
	 * @return the linked address
	 */
	public String getLinkedAddress() {
		return linkedAddress;
	}

	/**
	 * Gets the removes the.
	 *
	 * @return the removes the
	 */
	public String getRemove() {
		return remove;
	}

	/**
	 * Gets the ship to number.
	 *
	 * @return the ship to number
	 */
	public String getShipToNumber() {
		return shipToNumber;
	}

	/**
	 * Gets the upload documents modal header.
	 *
	 * @return the upload documents modal header
	 */
	public String getUploadDocumentsModalHeader() {
		return uploadDocumentsModalHeader;
	}

	/**
	 * Gets the close icon.
	 *
	 * @return the close icon
	 */
	public String getCloseIcon() {
		return closeIcon;
	}

	/**
	 * Gets the close icon alt text.
	 *
	 * @return the close icon alt text
	 */
	public String getCloseIconAltText() {
		return closeIconAltText;
	}

	/**
	 * Gets the file type text.
	 *
	 * @return the file type text
	 */
	public String getFileTypeText() {
		return fileTypeText;
	}

	/**
	 * Gets the file types list.
	 *
	 * @return the file types list
	 */
	public List<UploadCertificationFileTypeModel> getFileTypesList() {
		return new ArrayList<>(fileTypesList);
	}

	/**
	 * Gets the address selection labels.
	 *
	 * @return the address selection labels
	 */
	public JsonElement getAddressSelectionLabels() {
		return addressSelectionLabels;
	}

	/**
	 * Gets the drop documents text.
	 *
	 * @return the drop documents text
	 */
	public String getDropDocumentsText() {
		return dropDocumentsText;
	}

	/**
	 * Gets the choose files cta label.
	 *
	 * @return the choose files cta label
	 */
	public String getChooseFilesCtaLabel() {
		return chooseFilesCtaLabel;
	}

	/**
	 * Gets the cancel CTA label.
	 *
	 * @return the cancel CTA label
	 */
	public String getCancelCTALabel() {
		return cancelCTALabel;
	}

	/**
	 * Gets the confirm CTA label.
	 *
	 * @return the confirm CTA label
	 */
	public String getConfirmCTALabel() {
		return confirmCTALabel;
	}

	/**
	 * Gets the address linked text.
	 *
	 * @return the address linked text
	 */
	public String getAddressLinkedText() {
		return addressLinkedText;
	}

	/**
	 * Gets the upload success text.
	 *
	 * @return the upload success text
	 */
	public String getUploadSuccessText() {
		return uploadSuccessText;
	}

	/**
	 * @return the downloadRUOCTA
	 */
	public String getDownloadRUOCTA() {
		return downloadRUOCTA;
	}

	/**
	 * @return the downloadRUOUrl
	 */
	public String getDownloadRUOUrl() {
		return downloadRUOUrl;
	}

	/**
	 * @return the downloadRUOInfo
	 */
	public String getDownloadRUOInfo() {
		return downloadRUOInfo;
	}
	

	
	
	
}
