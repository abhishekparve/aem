package com.bdb.aem.core.pojo;

import com.bdb.aem.core.bean.PurchaseAccountModelConfirmationBean;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class PurchaseAccountModelConfirmationLabels.
 */
public class PurchaseAccountModelConfirmationLabels {
	
	/** The image. */
	@SerializedName("image")
	private String image;
	
	/** The title. */
	@SerializedName("title")
	private String title;
	
	/** The sub title. */
	@SerializedName("pendingTitle")
	private String pendingTitle;
	
	/** The success title. */
	@SerializedName("successTitle")
	private String successTitle;
	
	/** The completed content. */
	@SerializedName("completedContent")
	private String completedContent;
	
	/** The pending content. */
	@SerializedName("pendingContent")
	private String pendingContent;
	
	/** The rewards CTA label. */
	@SerializedName("rewardsCTALabel")
	private String rewardsCTALabel;
	
	/** The continue CTA label. */
	@SerializedName("continueCTALabel")
	private String continueCTALabel;
	
	/** The continue CTA link. */
	@SerializedName("continueCTALink")
	private String continueCTALink;
	
	/** The join rewards. */
	@SerializedName("joinRewards")
	private JsonElement joinRewards;
	
	/** The b D rewards config. */
	@SerializedName("bDRewardsConfig")
	private JsonElement bDRewardsConfig;

	/**
	 * Instantiates a new purchase account model confirmation labels.
	 *
	 * @param purchaseAccountModelConfirmationBean the purchase account model confirmation bean
	 */
	public PurchaseAccountModelConfirmationLabels(PurchaseAccountModelConfirmationBean purchaseAccountModelConfirmationBean) {
		this.image = purchaseAccountModelConfirmationBean.getImage();
		this.title = purchaseAccountModelConfirmationBean.getTitle();
		this.pendingTitle = purchaseAccountModelConfirmationBean.getPendingTitle();
		this.successTitle = purchaseAccountModelConfirmationBean.getSuccessTitle();
		this.completedContent = purchaseAccountModelConfirmationBean.getCompletedContent();
		this.pendingContent = purchaseAccountModelConfirmationBean.getPendingContent();
		this.rewardsCTALabel = purchaseAccountModelConfirmationBean.getRewardsCTALabel();
		this.continueCTALabel = purchaseAccountModelConfirmationBean.getContinueCTALabel();
		this.continueCTALink = purchaseAccountModelConfirmationBean.getContinueCTALink();
		this.joinRewards = purchaseAccountModelConfirmationBean.getJoinRewards();
		this.bDRewardsConfig = purchaseAccountModelConfirmationBean.getbDRewardsConfig();
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the sub title.
	 *
	 * @return the sub title
	 */
	public String getPendingTitle() {
		return pendingTitle;
	}

	/**
	 * Gets the success title.
	 *
	 * @return the success title
	 */
	public String getSuccessTitle() {
		return successTitle;
	}

	/**
	 * Gets the completed content.
	 *
	 * @return the completed content
	 */
	public String getCompletedContent() {
		return completedContent;
	}

	/**
	 * Gets the pending content.
	 *
	 * @return the pending content
	 */
	public String getPendingContent() {
		return pendingContent;
	}

	/**
	 * Gets the rewards CTA label.
	 *
	 * @return the rewards CTA label
	 */
	public String getRewardsCTALabel() {
		return rewardsCTALabel;
	}

	/**
	 * Gets the continue CTA label.
	 *
	 * @return the continue CTA label
	 */
	public String getContinueCTALabel() {
		return continueCTALabel;
	}

	/**
	 * Gets the continue CTA link.
	 *
	 * @return the continue CTA link
	 */
	public String getContinueCTALink() {
		return continueCTALink;
	}

	/**
	 * Gets the join rewards.
	 *
	 * @return the join rewards
	 */
	public JsonElement getJoinRewards() {
		return joinRewards;
	}

	/**
	 * Gets the BD rewards config.
	 *
	 * @return the BD rewards config
	 */
	public JsonElement getbDRewardsConfig() {
		return bDRewardsConfig;
	}
}