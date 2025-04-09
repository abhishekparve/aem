package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountPreferenceLabels.
 */
public class AccountPreferenceLabels {
    /**
     * the title
     */
    @SerializedName("title")
    private String title;

    /**
     * the subTitle
     */
    @SerializedName("subTitle")
    private String subTitle;

    /**
     * the industryTitle
     */
    @SerializedName("industryTitle")
    private String industryTitle;

    /**
     * the roleTitle
     */
    @SerializedName("roleTitle")
    private String roleTitle;

    /**
     * the interestTitle
     */
    @SerializedName("interestTitle")

    private String interestTitle;

    /**
     * the updateLabel
     */
    @SerializedName("updateLabel")
    private String updateLabel;

    /**
     * the successMessage
     */
    @SerializedName("successMessage")
    private String successMessage;

    /**
     * the closeIcon
     */
    @SerializedName("closeIcon")
    private String closeIcon;

    /**
     * the closeIconAltText
     */
    @SerializedName("closeIconAltText")
    private String closeIconAltText;

    /**
     * the logoIcon
     */
    @SerializedName("logoIcon")
    private String logoIcon;

    /**
     * the logoIconAltText
     */
    @SerializedName("logoIconAltText")
    private String logoIconAltText;

    public AccountPreferenceLabels(String title,
                                   String subTitle,
                                   String industryTitle,
                                   String roleTitle,
                                   String interestTitle,
                                   String updateLabel,
                                   String successMessage,
                                   String closeIcon,
                                   String closeIconAltText,
                                   String logoIcon,
                                   String logoIconAltText) {
        this.title = title;
        this.subTitle = subTitle;
        this.industryTitle = industryTitle;
        this.roleTitle = roleTitle;
        this.interestTitle = interestTitle;
        this.updateLabel = updateLabel;
        this.successMessage = successMessage;
        this.closeIcon = closeIcon;
        this.closeIconAltText = closeIconAltText;
        this.logoIcon = logoIcon;
        this.logoIconAltText = logoIconAltText;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getIndustryTitle() {
        return industryTitle;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public String getInterestTitle() {
        return interestTitle;
    }

    public String getUpdateLabel() {
        return updateLabel;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getCloseIcon() {
        return closeIcon;
    }

    public String getLogoIcon() {
        return logoIcon;
    }

    public String getCloseIconAltText() {
        return closeIconAltText;
    }

    public String getLogoIconAltText() {
        return logoIconAltText;
    }
    
}
