package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class FileUploadLabelsConfig.
 */
public class FileUploadLabelsConfig {
	
	/** The title. */
	@SerializedName("fieldTitle")
	private String fieldTitle;
	
	/** The info. */
	@SerializedName("info")
	private String info;
	
	/** The drop zone label. */
	@SerializedName("title")
	private String dropZoneTitle;
	
	/** The drop zone info. */
	@SerializedName("note")
	private String dropZoneNote;
	
	/** The choose file CTA label. */
	@SerializedName("btnName")
	private String chooseFileCTALabel;

	/**
	 * Instantiates a new file upload labels config.
	 *
	 * @param fieldTitle the field title
	 * @param info the info
	 * @param dropZoneTitle the drop zone title
	 * @param dropZoneInfo the drop zone info
	 * @param chooseFileCTALabel the choose file CTA label
	 */
	public FileUploadLabelsConfig(String fieldTitle, String info, String dropZoneTitle, String dropZoneNote,
			String chooseFileCTALabel) {
		super();
		this.fieldTitle = fieldTitle;
		this.info = info;
		this.dropZoneTitle = dropZoneTitle;
		this.dropZoneNote = dropZoneNote;
		this.chooseFileCTALabel = chooseFileCTALabel;
	}

	/**
	 * Gets the field title.
	 *
	 * @return the field title
	 */
	public String getFieldTitle() {
		return fieldTitle;
	}

	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * Gets the drop zone title.
	 *
	 * @return the drop zone title
	 */
	public String getDropZoneTitle() {
		return dropZoneTitle;
	}

	/**
	 * Gets the drop zone note.
	 *
	 * @return the drop zone note
	 */
	public String getDropZoneNote() {
		return dropZoneNote;
	}

	/**
	 * Gets the choose file CTA label.
	 *
	 * @return the choose file CTA label
	 */
	public String getChooseFileCTALabel() {
		return chooseFileCTALabel;
	}
}
