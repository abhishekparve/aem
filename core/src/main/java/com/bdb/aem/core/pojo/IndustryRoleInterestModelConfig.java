package com.bdb.aem.core.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.bdb.aem.core.models.SignUpIndustryModel;

/**
 * The Class IndustryRoleInterestModelConfig.
 */
public class IndustryRoleInterestModelConfig {
	
	/** The industry title. */
	@SerializedName("industryTitle")
	private String industryTitle;
	
	/** The industry description. */
	@SerializedName("industryDescription")
	private String industryDescription;
	
	/** The industry selection text. */
	@SerializedName("industrySelectionText")
	private String industrySelectionText;
	
	/** The role title. */
	@SerializedName("roleTitle")
	private String roleTitle;
	
	/** The role description. */
	@SerializedName("roleDescription")
	private String roleDescription;
	
	/** The role selection text. */
	@SerializedName("roleSelectionText")
	private String roleSelectionText;
	
	/** The interest title. */
	@SerializedName("interestTitle")
	private String interestTitle;
	
	/** The interest description. */
	@SerializedName("interestDescription")
	private String interestDescription;
	
	/** The interest selection text. */
	@SerializedName("interestSelectionText")
	private String interestSelectionText;
	
	/** The next industry button label. */
	@SerializedName("nextIndustryButtonLabel")
	private String nextIndustryButtonLabel;
	
	/** The skip industry link label. */
	@SerializedName("skipIndustryLinkLabel")
	private String skipIndustryLinkLabel;
	
	/** The back role button label. */
	@SerializedName("backRoleButtonLabel")
	private String backRoleButtonLabel;
	
	/** The next role button label. */
	@SerializedName("nextRoleButtonLabel")
	private String nextRoleButtonLabel;
	
	/** The back interest button label. */
	@SerializedName("backInterestButtonLabel")
	private String backInterestButtonLabel;
	
	/** The next interest button label. */
	@SerializedName("nextInterestButtonLabel")
	private String nextInterestButtonLabel;

	/** The industry multifield. */
	@SerializedName("industryMultifield")
    private List<SignUpIndustryModel> industryMultifield;

	/**
	 * Constructor sets Configuration Class to Industry Role Interest Model  .
	 *
	 * @param industryTitle 		  - Industry Title
	 * @param industryDescription   - Industry Description
	 * @param industrySelectionText   - Industry Selection Text
	 * @param roleTitle 			  - Role Title
	 * @param roleDescription 	  - Role Description
	 * @param roleSelectionText 	  - Role Selection Text
	 * @param interestTitle 		  - Interest Title
	 * @param interestDescription   - Interest Description
	 * @param interestSelectionText   - Interest Selection Text
	 * @param nextIndustryButtonLabel - Industry Next Button Label Text
	 * @param skipIndustryLinkLabel   - Industry Skip Button Label Text
	 * @param backRoleButtonLabel   - Role Back Button Label Text
	 * @param nextRoleButtonLabel   - Role Next Button Label Text
	 * @param backInterestButtonLabel - Interest Back Button Label Text
	 * @param nextInterestButtonLabel - Interest Next Button Label Text
	 * @param industryMultifield   - Industry Multifield List
	 */
	public IndustryRoleInterestModelConfig(String industryTitle, String industryDescription,
			String industrySelectionText, String roleTitle, String roleDescription, String roleSelectionText,
			String interestTitle, String interestDescription, String interestSelectionText,
			String nextIndustryButtonLabel, String skipIndustryLinkLabel, String backRoleButtonLabel,
			String nextRoleButtonLabel, String backInterestButtonLabel, String nextInterestButtonLabel,
			List<SignUpIndustryModel> industryMultifield) {
		this.industryTitle = industryTitle;
		this.industryDescription = industryDescription;
		this.industrySelectionText = industrySelectionText;
		this.roleTitle = roleTitle;
		this.roleDescription = roleDescription;
		this.roleSelectionText = roleSelectionText;
		this.interestTitle = interestTitle;
		this.interestDescription = interestDescription;
		this.interestSelectionText = interestSelectionText;
		this.nextIndustryButtonLabel = nextIndustryButtonLabel;
		this.skipIndustryLinkLabel = skipIndustryLinkLabel;
		this.backRoleButtonLabel = backRoleButtonLabel;
		this.nextRoleButtonLabel = nextRoleButtonLabel;
		this.backInterestButtonLabel = backInterestButtonLabel;
		this.nextInterestButtonLabel = nextInterestButtonLabel;
		industryMultifield = new ArrayList<>(industryMultifield);
		this.industryMultifield = Collections.unmodifiableList(industryMultifield);
	}

	/**
	 * Gets Industry Title Label.
	 *
	 * @return - Industry Title Label as a String
	 */
	public String getIndustryTitle() {
		return industryTitle;
	}

	/**
	 * Gets Industry Description Label.
	 *
	 * @return - Industry Description as a String
	 */
	public String getIndustryDescription() {
		return industryDescription;
	}

	/**
	 * Gets Industry Selection Text Label .
	 *
	 * @return - Industry Selection Text Label as a String
	 */
	public String getIndustrySelectionText() {
		return industrySelectionText;
	}

	/**
	 * Gets Role Title Label.
	 *
	 * @return - Role Title Label as a String
	 */
	public String getRoleTitle() {
		return roleTitle;
	}

	/**
	 * Gets Role Description Label.
	 *
	 * @return - Role Description Label as a String
	 */
	public String getRoleDescription() {
		return roleDescription;
	}

	/**
	 * Gets Role Selection Text Label.
	 *
	 * @return - Role Selection Text Label as a String
	 */
	public String getRoleSelectionText() {
		return roleSelectionText;
	}

	/**
	 * Gets Interest Title Label.
	 *
	 * @return - Interest Title Label as a String
	 */
	public String getInterestTitle() {
		return interestTitle;
	}

	/**
	 * Gets Interest Description Label.
	 *
	 * @return - Interest Description Label as a String
	 */
	public String getInterestDescription() {
		return interestDescription;
	}

	/**
	 * Gets Interest Selection Text Label.
	 *
	 * @return - Interest Selection Text Label as a String
	 */
	public String getInterestSelectionText() {
		return interestSelectionText;
	}

	/**
	 * Gets Industry's Next Button Label Text .
	 *
	 * @return - Industry's Next Button Label Text as a String
	 */
	public String getNextIndustryButtonLabel() {
		return nextIndustryButtonLabel;
	}

	/**
	 * Gets Industry's Skip Button Label Text.
	 *
	 * @return - Industry's Skip Button Label Text as a String
	 */
	public String getSkipIndustryLinkLabel() {
		return skipIndustryLinkLabel;
	}

	/**
	 * Gets Role's Back Button Label Text.
	 *
	 * @return - Role's Back Button Label Text as a String
	 */
	public String getBackRoleButtonLabel() {
		return backRoleButtonLabel;
	}

	/**
	 * Gets Role's Next Button Label Text.
	 *
	 * @return - Role's Next Button Label Text as a String
	 */
	public String getNextRoleButtonLabel() {
		return nextRoleButtonLabel;
	}

	/**
	 * Gets Interest's Back Button Label Text.
	 *
	 * @return Interest's Back Button Label Text as a String
	 */
	public String getBackInterestButtonLabel() {
		return backInterestButtonLabel;
	}

	/**
	 * Gets Interest's Next Button Label Text.
	 *
	 * @return - Interest's Next Button Label Text as a String
	 */
	public String getNextInterestButtonLabel() {
		return nextInterestButtonLabel;
	}

	/**
	 * Gets the Industry List.
	 *
	 * @return - Industry List as a List of Industries
	 */
	public List<SignUpIndustryModel> getIndustryMultifield() {
		return new ArrayList<>(industryMultifield);
	}

	/**
	 * Converts the Industry Role Interest Model Configuration Class to a String.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "IndustryRoleInterestModelConfig [industryTitle=" + industryTitle + ", industryDescription="
				+ industryDescription + ", industrySelectionText=" + industrySelectionText + ", roleTitle=" + roleTitle
				+ ", roleDescription=" + roleDescription + ", roleSelectionText=" + roleSelectionText
				+ ", interestTitle=" + interestTitle + ", interestDescription=" + interestDescription
				+ ", interestSelectionText=" + interestSelectionText + ", nextIndustryButtonLabel="
				+ nextIndustryButtonLabel + ", skipIndustryLinkLabel=" + skipIndustryLinkLabel
				+ ", backRoleButtonLabel=" + backRoleButtonLabel + ", nextRoleButtonLabel=" + nextRoleButtonLabel
				+ ", backInterestButtonLabel=" + backInterestButtonLabel + ", nextInterestButtonLabel="
				+ nextInterestButtonLabel + ", industryMultifield=" + industryMultifield + "]";
	}	
}