package com.bdb.aem.core.pojo;

import com.bdb.aem.core.bean.JoinBdRewardsBean;
import com.google.gson.annotations.SerializedName;

/**
 * The Class JoinBDRewardsModalLabelsConfig.
 */
public class JoinBDRewardsModalLabelsConfig {
	
	/** The join rewards title. */
	@SerializedName("title")
	private String joinRewardsTitle;
	
	/** The join rewards subtitle. */
	@SerializedName("subtitle")
	private String joinRewardsSubtitle;
	
	/** The not health care professional label. */
	@SerializedName("notHealthCareLabel")
	private String notHealthCareProfessionalLabel;
	
	/** The not govt employee label. */
	@SerializedName("notGovtLabel")
	private String notGovtEmployeeLabel;
	
	/** The not prohibited gifts accept. */
	@SerializedName("noGiftsPolicyLabel")
	private String notProhibitedGiftsAccept;
	
	/** The rewards tn C label. */
	@SerializedName("rewardsTnCLabel")
	private String rewardsTnCLabel;
	
	/** The join bd rewards CTA label. */
	@SerializedName("joinBdRewardsCTALabel")
	private String joinBdRewardsCTALabel;
	
	/** The join bd rewards CTA link. */
	@SerializedName("joinBdRewardsCTALink")
	private String joinBdRewardsCTALink;

	/** The join bd rewards redirectionUrl link. */
	@SerializedName("redirectionUrl")
	private String redirectionUrl;

	
	/**
	 * Instantiates a new join BD rewards modal labels config.
	 *
	 * @param joinBdRewardsBean the join bd rewards bean
	 */
	public JoinBDRewardsModalLabelsConfig(JoinBdRewardsBean joinBdRewardsBean) {
		super();
		this.joinRewardsTitle = joinBdRewardsBean.getJoinRewardsTitle();
		this.joinRewardsSubtitle = joinBdRewardsBean.getJoinRewardsSubtitle();
		this.notHealthCareProfessionalLabel = joinBdRewardsBean.getNotHealthCareProfessionalLabel();
		this.notGovtEmployeeLabel = joinBdRewardsBean.getNotGovtEmployeeLabel();
		this.notProhibitedGiftsAccept = joinBdRewardsBean.getNotProhibitedGiftsAccept();
		this.rewardsTnCLabel = joinBdRewardsBean.getRewardsTnCLabel();
		this.joinBdRewardsCTALabel = joinBdRewardsBean.getJoinBdRewardsCTALabel();
		this.joinBdRewardsCTALink = joinBdRewardsBean.getJoinBdRewardsCTALink();
		this.redirectionUrl = joinBdRewardsBean.getRedirectionUrl();
	}

	/**
	 * Gets the join rewards title.
	 *
	 * @return the join rewards title
	 */
	public String getJoinRewardsTitle() {
		return joinRewardsTitle;
	}

	/**
	 * Gets the join rewards subtitle.
	 *
	 * @return the join rewards subtitle
	 */
	public String getJoinRewardsSubtitle() {
		return joinRewardsSubtitle;
	}

	/**
	 * Gets the not health care professional label.
	 *
	 * @return the not health care professional label
	 */
	public String getNotHealthCareProfessionalLabel() {
		return notHealthCareProfessionalLabel;
	}

	/**
	 * Gets the not govt employee label.
	 *
	 * @return the not govt employee label
	 */
	public String getNotGovtEmployeeLabel() {
		return notGovtEmployeeLabel;
	}

	/**
	 * Gets the not prohibited gifts accept.
	 *
	 * @return the not prohibited gifts accept
	 */
	public String getNotProhibitedGiftsAccept() {
		return notProhibitedGiftsAccept;
	}

	/**
	 * Gets the rewards tn C label.
	 *
	 * @return the rewards tn C label
	 */
	public String getRewardsTnCLabel() {
		return rewardsTnCLabel;
	}

	/**
	 * Gets the join bd rewards CTA label.
	 *
	 * @return the join bd rewards CTA label
	 */
	public String getJoinBdRewardsCTALabel() {
		return joinBdRewardsCTALabel;
	}

	/**
	 * Gets the join bd rewards CTA link.
	 *
	 * @return the join bd rewards CTA link
	 */
	public String getJoinBdRewardsCTALink() {
		return joinBdRewardsCTALink;
	}


	/**
	 * Gets the join bd rewards redirection link.
	 *
	 * @return the join bd rewards redirection link
	 */
	public String getRedirectionUrl() {
		return redirectionUrl;
	}


}
