package com.bdb.aem.core.bean;

import com.google.gson.JsonElement;

/**
 * The Class PurchaseAccountModelConfirmationBean.
 */
public class PurchaseAccountModelConfirmationBean {
	
	/** The image. */
	private String image;
	
	/** The title. */
	private String title;
	
	/** The sub title. */
	private String pendingTitle;
	
	/** The success title. */
	private String successTitle;
	
	/** The completed content. */
	private String completedContent;
	
	/** The pending content. */
	private String pendingContent;
	
	/** The rewards CTA label. */
	private String rewardsCTALabel;
	
	/** The continue CTA label. */
	private String continueCTALabel;
	
	/** The continue CTA link. */
	private String continueCTALink;
	
	/** The join rewards. */
	private JsonElement joinRewards;
	
	/** The b D rewards config. */
	private JsonElement bDRewardsConfig;

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(String image) {
		this.image = image;
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
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the pending title.
	 *
	 * @return the pending title
	 */
	public String getPendingTitle() {
		return pendingTitle;
	}

	/**
	 * Sets the pending title.
	 *
	 * @param pendingTitle the new pending title
	 */
	public void setPendingTitle(String pendingTitle) {
		this.pendingTitle = pendingTitle;
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
	 * Sets the success title.
	 *
	 * @param successTitle the new success title
	 */
	public void setSuccessTitle(String successTitle) {
		this.successTitle = successTitle;
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
	 * Sets the completed content.
	 *
	 * @param completedContent the new completed content
	 */
	public void setCompletedContent(String completedContent) {
		this.completedContent = completedContent;
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
	 * Sets the pending content.
	 *
	 * @param pendingContent the new pending content
	 */
	public void setPendingContent(String pendingContent) {
		this.pendingContent = pendingContent;
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
	 * Sets the rewards CTA label.
	 *
	 * @param rewardsCTALabel the new rewards CTA label
	 */
	public void setRewardsCTALabel(String rewardsCTALabel) {
		this.rewardsCTALabel = rewardsCTALabel;
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
	 * Sets the continue CTA label.
	 *
	 * @param continueCTALabel the new continue CTA label
	 */
	public void setContinueCTALabel(String continueCTALabel) {
		this.continueCTALabel = continueCTALabel;
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
	 * Sets the continue CTA link.
	 *
	 * @param continueCTALink the new continue CTA link
	 */
	public void setContinueCTALink(String continueCTALink) {
		this.continueCTALink = continueCTALink;
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
	 * Sets the join rewards.
	 *
	 * @param joinRewards the new join rewards
	 */
	public void setJoinRewards(JsonElement joinRewards) {
		this.joinRewards = joinRewards;
	}

	/**
	 * Gets the b D rewards config.
	 *
	 * @return the b D rewards config
	 */
	public JsonElement getbDRewardsConfig() {
		return bDRewardsConfig;
	}

	/**
	 * Sets the b D rewards config.
	 *
	 * @param bDRewardsConfig the new b D rewards config
	 */
	public void setbDRewardsConfig(JsonElement bDRewardsConfig) {
		this.bDRewardsConfig = bDRewardsConfig;
	}
}
