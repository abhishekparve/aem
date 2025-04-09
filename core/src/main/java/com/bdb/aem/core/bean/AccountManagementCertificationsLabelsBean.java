package com.bdb.aem.core.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bdb.aem.core.models.UploadCertificationFileTypeModel;
import com.google.gson.JsonElement;

/**
 * The Class AccountManagementCertificationsLabelsBean.
 */
public class AccountManagementCertificationsLabelsBean {

	/** The certifications image. */
	private String certificationsImage; 
	
	/** The certifications header. */
	private String certificationsHeader;
	
	/** The upload documents cta label. */
	private String uploadDocumentsCtaLabel;
	
	/** The no certificate text. */
	private String noCertificateText;
	
	/** The upload instruction label. */
	private String uploadInstructionLabel;
	
	/** The document name. */
	private String documentName;
	
	/** The certification type. */
	private String certificationType;
	
	/** The expiry date. */
	private String expiryDate;
	
	/** The status fields. */
	private String statusFields;
	
	/** The linked address. */
	private String linkedAddress;
	
	/** The remove. */
	private String remove;
	
	/** The ship to number. */
	private String shipToNumber;
	
	/** The upload documents modal header. */
	private String uploadDocumentsModalHeader;
	
	/** The close icon. */
	private String closeIcon;
	
	/** The close icon alt text. */
	private String closeIconAltText;
	
	/** The file type text. */
	private String fileTypeText;
	
	/** The file types list. */
	private List<UploadCertificationFileTypeModel> fileTypesList;
	
	/** The address selection labels. */
	private JsonElement addressSelectionLabels;
	
	/** The drop documents text. */
	private String dropDocumentsText;
	
	/** The choose files cta label. */
	private String chooseFilesCtaLabel;
	
	/** The cancel. */
	private String cancel;
	
	/** The confirm. */
	private String confirm;
	
	/** The address linked text. */
	private String addressLinkedText;
	
	/** The upload success text. */
	private String uploadSuccessText;
	
	/** The withLabel text. */
	private String withLabel;
	
	/** The Download Template link Label. */
	private String downloadRUOCTA;

	/** The Download url  */
	private String downloadRUOUrl;

	/** The downloadInfo. */
	private String downloadRUOInfo;

	
	
	/**
	 * @return the downloadRUOCTA
	 */
	public String getDownloadRUOCTA() {
		return downloadRUOCTA;
	}

	/**
	 * @param downloadRUOCTA the downloadRUOCTA to set
	 */
	public void setDownloadRUOCTA(String downloadRUOCTA) {
		this.downloadRUOCTA = downloadRUOCTA;
	}

	/**
	 * @return the downloadRUOUrl
	 */
	public String getDownloadRUOUrl() {
		return downloadRUOUrl;
	}

	/**
	 * @param downloadRUOUrl the downloadRUOUrl to set
	 */
	public void setDownloadRUOUrl(String downloadRUOUrl) {
		this.downloadRUOUrl = downloadRUOUrl;
	}

	/**
	 * @return the downloadRUOInfo
	 */
	public String getDownloadRUOInfo() {
		return downloadRUOInfo;
	}

	/**
	 * @param downloadRUOInfo the downloadRUOInfo to set
	 */
	public void setDownloadRUOInfo(String downloadRUOInfo) {
		this.downloadRUOInfo = downloadRUOInfo;
	}

	public String getWithLabel() {
		return withLabel;
	}

	public void setWithLabel(String withLabel) {
		this.withLabel = withLabel;
	}

	public String getRowsLabel() {
		return rowsLabel;
	}

	public void setRowsLabel(String rowsLabel) {
		this.rowsLabel = rowsLabel;
	}

	public String getAndLabel() {
		return andLabel;
	}

	public void setAndLabel(String andLabel) {
		this.andLabel = andLabel;
	}

	public String getColLabel() {
		return colLabel;
	}

	public void setColLabel(String colLabel) {
		this.colLabel = colLabel;
	}

	/** The rowsLabel text. */
	private String rowsLabel;
	
	/** The andLabel text. */
	private String andLabel;
	
	/** The colLabel text. */
	private String colLabel;

	/**
	 * Gets the certifications image.
	 *
	 * @return the certifications image
	 */
	public String getCertificationsImage() {
		return certificationsImage;
	}

	/**
	 * Sets the certifications image.
	 *
	 * @param certificationsImage the new certifications image
	 */
	public void setCertificationsImage(String certificationsImage) {
		this.certificationsImage = certificationsImage;
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
	 * Sets the certifications header.
	 *
	 * @param certificationsHeader the new certifications header
	 */
	public void setCertificationsHeader(String certificationsHeader) {
		this.certificationsHeader = certificationsHeader;
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
	 * Sets the upload documents cta label.
	 *
	 * @param uploadDocumentsCtaLabel the new upload documents cta label
	 */
	public void setUploadDocumentsCtaLabel(String uploadDocumentsCtaLabel) {
		this.uploadDocumentsCtaLabel = uploadDocumentsCtaLabel;
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
	 * Sets the no certificate text.
	 *
	 * @param noCertificateText the new no certificate text
	 */
	public void setNoCertificateText(String noCertificateText) {
		this.noCertificateText = noCertificateText;
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
	 * Sets the upload instruction label.
	 *
	 * @param uploadInstructionLabel the new upload instruction label
	 */
	public void setUploadInstructionLabel(String uploadInstructionLabel) {
		this.uploadInstructionLabel = uploadInstructionLabel;
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
	 * Sets the document name.
	 *
	 * @param documentName the new document name
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
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
	 * Sets the certification type.
	 *
	 * @param certificationType the new certification type
	 */
	public void setCertificationType(String certificationType) {
		this.certificationType = certificationType;
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
	 * Sets the expiry date.
	 *
	 * @param expiryDate the new expiry date
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
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
	 * Sets the status fields.
	 *
	 * @param statusFields the new status fields
	 */
	public void setStatusFields(String statusFields) {
		this.statusFields = statusFields;
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
	 * Sets the linked address.
	 *
	 * @param linkedAddress the new linked address
	 */
	public void setLinkedAddress(String linkedAddress) {
		this.linkedAddress = linkedAddress;
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
	 * Sets the removes the.
	 *
	 * @param remove the new removes the
	 */
	public void setRemove(String remove) {
		this.remove = remove;
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
	 * Sets the ship to number.
	 *
	 * @param shipToNumber the new ship to number
	 */
	public void setShipToNumber(String shipToNumber) {
		this.shipToNumber = shipToNumber;
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
	 * Sets the upload documents modal header.
	 *
	 * @param uploadDocumentsModalHeader the new upload documents modal header
	 */
	public void setUploadDocumentsModalHeader(String uploadDocumentsModalHeader) {
		this.uploadDocumentsModalHeader = uploadDocumentsModalHeader;
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
	 * Sets the file type text.
	 *
	 * @param fileTypeText the new file type text
	 */
	public void setFileTypeText(String fileTypeText) {
		this.fileTypeText = fileTypeText;
	}

	/**
	 * Gets the file types list.
	 *
	 * @return the file types list
	 */
	public List<UploadCertificationFileTypeModel> getFileTypesList() {
		if (null != fileTypesList) {
			return new ArrayList<>(fileTypesList);
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * Sets the file types list.
	 *
	 * @param fileTypesList the new file types list
	 */
	public void setFileTypesList(List<UploadCertificationFileTypeModel> fileTypesList) {
		if (fileTypesList != null) {
			fileTypesList = new ArrayList<>(fileTypesList);
			this.fileTypesList = Collections.unmodifiableList(fileTypesList);
		}
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
	 * Sets the address selection labels.
	 *
	 * @param addressSelectionLabels the new address selection labels
	 */
	public void setAddressSelectionLabels(JsonElement addressSelectionLabels) {
		this.addressSelectionLabels = addressSelectionLabels;
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
	 * Sets the drop documents text.
	 *
	 * @param dropDocumentsText the new drop documents text
	 */
	public void setDropDocumentsText(String dropDocumentsText) {
		this.dropDocumentsText = dropDocumentsText;
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
	 * Sets the choose files cta label.
	 *
	 * @param chooseFilesCtaLabel the new choose files cta label
	 */
	public void setChooseFilesCtaLabel(String chooseFilesCtaLabel) {
		this.chooseFilesCtaLabel = chooseFilesCtaLabel;
	}

	/**
	 * Gets the cancel.
	 *
	 * @return the cancel
	 */
	public String getCancel() {
		return cancel;
	}

	/**
	 * Sets the cancel.
	 *
	 * @param cancel the new cancel
	 */
	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	/**
	 * Gets the confirm.
	 *
	 * @return the confirm
	 */
	public String getConfirm() {
		return confirm;
	}

	/**
	 * Sets the confirm.
	 *
	 * @param confirm the new confirm
	 */
	public void setConfirm(String confirm) {
		this.confirm = confirm;
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
	 * Sets the address linked text.
	 *
	 * @param addressLinkedText the new address linked text
	 */
	public void setAddressLinkedText(String addressLinkedText) {
		this.addressLinkedText = addressLinkedText;
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
	 * Sets the upload success text.
	 *
	 * @param uploadSuccessText the new upload success text
	 */
	public void setUploadSuccessText(String uploadSuccessText) {
		this.uploadSuccessText = uploadSuccessText;
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
	 * Sets the close icon.
	 *
	 * @param closeIcon the new close icon
	 */
	public void setCloseIcon(String closeIcon) {
		this.closeIcon = closeIcon;
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
	 * Sets the close icon alt text.
	 *
	 * @param closeIconAltText the new close icon alt text
	 */
	public void setCloseIconAltText(String closeIconAltText) {
		this.closeIconAltText = closeIconAltText;
	}
}