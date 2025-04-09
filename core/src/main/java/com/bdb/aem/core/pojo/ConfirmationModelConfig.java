package com.bdb.aem.core.pojo;

import com.bdb.aem.core.models.AccessLinksModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class ConfirmationModelConfig.
 */
public class ConfirmationModelConfig {
	
	/** The image. */
	@SerializedName("image")
	private String image;

	/** The imageAltText. */
	@SerializedName("imageAltText")
	private String imageAltText;
	
	/** The confirmation title. */
	@SerializedName("confirmationTitle")
    private String confirmationTitle;

	/** The content. */
	@SerializedName("content")
	private String content;
	
	/** The access text. */
	@SerializedName("accessText")
    private String accessText;

	/** The linksTitle text. */
	@SerializedName("linksTitle")
	private String linksTitle;

	/** The links multifield. */
	@SerializedName("links")
	private List<AccessLinksModel> links;
	
	/** The purchasing account label. */
	@SerializedName("purchasingAccountLabel")
    private String purchasingAccountLabel;
	
	/** The skip and browse label. */
	@SerializedName("skipAndBrowseLabel")
    private String skipAndBrowseLabel;
	
	/** The skip and browse url. */
	@SerializedName("skipAndBrowseUrl")
    private String skipAndBrowseUrl;
    
	/**
	 * Constructor sets Configuration to Confirmation Model  .
	 *
	 * @param image 				 - Image Path
	 * @param confirmationTitle 	 - Confirmation Title
	 * @param accessText 		 - Access Text
	 * @param purchasingAccountLabel - Label for Purchasing Account
	 * @param skipAndBrowseLabel  - Label for Skip and Browser
	 * @param skipAndBrowseUrl 	 - URL for Skip and Browser
	 */
	public ConfirmationModelConfig(String image,
								   String imageAltText,
								   String confirmationTitle,
								   String content,
								   String accessText,
								   String linksTitle,
								   List<AccessLinksModel> links,
								   String purchasingAccountLabel,
								   String skipAndBrowseLabel,
								   String skipAndBrowseUrl) {
		this.image = image;
		this.imageAltText = imageAltText;
		this.confirmationTitle = confirmationTitle;
		this.content = content;
		this.accessText = accessText;
		this.linksTitle = linksTitle;
		links = new ArrayList<>(links);
		this.links = Collections.unmodifiableList(links);
		this.purchasingAccountLabel = purchasingAccountLabel;
		this.skipAndBrowseLabel = skipAndBrowseLabel;
		this.skipAndBrowseUrl = skipAndBrowseUrl;
	}

	/**
	 * Get the Image Path.
	 *
	 * @return - Image Path as a String
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Get the Confirmation Title.
	 *
	 * @return - Confirmation Title as a String
	 */
	public String getConfirmationTitle() {
		return confirmationTitle;
	}

	/**
	 * Get the Access Text.
	 *
	 * @return - Access Text as a String
	 */
	public String getAccessText() {
		return accessText;
	}

	/**
	 * Get the Purchasing Account Label.
	 *
	 * @return - Purchasing Account Label as a String
	 */
	public String getPurchasingAccountLabel() {
		return purchasingAccountLabel;
	}

	/**
	 * Get the Skip and Browse Label.
	 *
	 * @return - Skip and Browse Label
	 */
	public String getSkipAndBrowseLabel() {
		return skipAndBrowseLabel;
	}

	/**
	 * Get the Skip and Browse URL.
	 *
	 * @return - Skip and Browse URL
	 */
	public String getSkipAndBrowseUrl() {
		return skipAndBrowseUrl;
	}

	/**
	 * Get the imageAltText.
	 *
	 * @return - SimageAltText
	 */
	public String getImageAltText() {
		return imageAltText;
	}

	/**
	 * Get the content.
	 *
	 * @return - content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Get the linksTitle.
	 *
	 * @return - linksTitle
	 */
	public String getLinksTitle() {
		return linksTitle;
	}

	/**
	 * Get the links.
	 *
	 * @return - links
	 */
	public List<AccessLinksModel> getLinks() {
		return new ArrayList<>(links);
	}

	/**
	 * Converts the Confirmation Model Configuration Class to a String.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ConfirmationModelConfig{" +
				"image='" + image + '\'' +
				", imageAltText='" + imageAltText + '\'' +
				", confirmationTitle='" + confirmationTitle + '\'' +
				", content='" + content + '\'' +
				", accessText='" + accessText + '\'' +
				", linksTitle='" + linksTitle + '\'' +
				", links=" + links +
				", purchasingAccountLabel='" + purchasingAccountLabel + '\'' +
				", skipAndBrowseLabel='" + skipAndBrowseLabel + '\'' +
				", skipAndBrowseUrl='" + skipAndBrowseUrl + '\'' +
				'}';
	}
}