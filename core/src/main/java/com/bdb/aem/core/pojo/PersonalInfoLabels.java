package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class PersonalInfoLabels.
 */
public class PersonalInfoLabels {

    /** the title */
    @SerializedName("title")
    private String title;

    /** the firstNameLabel */
    @SerializedName("firstNameLabel")
    private String firstNameLabel;

    /** the lastNameLabel */
    @SerializedName("lastNameLabel")
    private String lastNameLabel;

    /** the companyLabel */
    @SerializedName("companyLabel")
    private String companyLabel;

    /** the phoneNumberLabel */
    @SerializedName("phoneNumberLabel")
    private String phoneNumberLabel;

    /** the eanCodeLabel */
    @SerializedName("eanCodeLabel")
    private String eanCodeLabel;

    /** the vatNumberLabel */
    @SerializedName("vatNumberLabel")
    private String vatNumberLabel;

    /** the updateInformationLabel */
    @SerializedName("updateInformationLabel")
    private String updateInformationLabel;

    /** the mobileNumberLabel */
    @SerializedName("mobileNumberLabel")
    private String mobileNumberLabel;

    /** the cancelCTALabel */
    @SerializedName("cancelCTALabel")
    private String cancelCTALabel;

    /** the updateCTALabel */
    @SerializedName("updateCTALabel")
    private String updateCTALabel;

    /** the successMessage */
    @SerializedName("successMessage")
    private String successMessage;

    /** the updateInformationModalHeader */
    @SerializedName("updateInformationModalHeader")
    private String updateInformationModalHeader;

    /** the closeIcon */
    @SerializedName("closeIcon")
    private String closeIcon;

    /** the closeIconAltText */
    @SerializedName("closeIconAltText")
    private String closeIconAltText;

    /** the firstNameError */
    @SerializedName("firstNameError")
    private String firstNameError;

    /** the lastNameError */
    @SerializedName("lastNameError")
    private String lastNameError;

    /** the phoneNumberValidationText */
    @SerializedName("phoneNumError")
    private String phoneNumberValidationText;

    /** the phoneNumPatternError */
    @SerializedName("phoneNumPatternError")
    private String phoneNumPatternError;

    /**
     * Instantiates a new PersonalInfoLabels.
     *
     * @param  title
     * @param  firstNameLabel
     * @param  lastNameLabel
     * @param  companyLabel
     * @param  phoneNumberLabel
     * @param  eanCodeLabel
     * @param  vatNumberLabel
     *  @param  updateInformationLabel
     *  @param  mobileNumberLabel
     *  @param  cancelCTALabel
     *    @param  updateCTALabel
     * @param  successMessage
     */

    public PersonalInfoLabels(String title,
                              String firstNameLabel,
                              String lastNameLabel,
                              String companyLabel,
                              String phoneNumberLabel,
                              String eanCodeLabel,
                              String vatNumberLabel,
                              String updateInformationLabel,
                              String mobileNumberLabel,
                              String cancelCTALabel,
                              String updateCTALabel,
                              String successMessage,
                              String updateInformationModalHeader,
                              String closeIcon,
                              String closeIconAltText,
                              String firstNameError,
                              String lastNameError,
                              String phoneNumberValidationText,
                              String phoneNumPatternError) {
        this.title = title;
        this.firstNameLabel = firstNameLabel;
        this.lastNameLabel = lastNameLabel;
        this.companyLabel = companyLabel;
        this.phoneNumberLabel = phoneNumberLabel;
        this.eanCodeLabel = eanCodeLabel;
        this.vatNumberLabel = vatNumberLabel;
        this.updateInformationLabel = updateInformationLabel;
        this.mobileNumberLabel = mobileNumberLabel;
        this.cancelCTALabel = cancelCTALabel;
        this.updateCTALabel = updateCTALabel;
        this.successMessage = successMessage;
        this.updateInformationModalHeader = updateInformationModalHeader;
        this.closeIcon = closeIcon;
        this.closeIconAltText = closeIconAltText;
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.phoneNumberValidationText = phoneNumberValidationText;
        this.phoneNumPatternError = phoneNumPatternError;
    }

}
