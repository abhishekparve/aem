package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/** the class LoginInfoLabels */
public class LoginInfoLabels {

    /** the title */
    @SerializedName("title")
    private String title;

    /** the emailAddressLabel */
    @SerializedName("emailAddressLabel")
    private String emailAddressLabel;

    /** the passwordLabels */
    @SerializedName("passwordLabels")
    private String passwordLabels;

    /** the updatePassword */
    @SerializedName("updatePassword")
    private String updatePassword;

    /** the editPwdLabel */
    @SerializedName("editPwdLabel")
    private String editPwdLabel;

    /** the enterNewPwdLabel */
    @SerializedName("enterNewPwdLabel")
    private String enterNewPwdLabel;

    /** the confirmNewPwdLabel */
    @SerializedName("confirmNewPwdLabel")
    private String confirmNewPwdLabel;

    /** the cancelCTALabel */
    @SerializedName("cancelCTALabel")
    private String cancelCTALabel;

    /** the updateCTALabel */
    @SerializedName("updateCTALabel")
    private String updateCTALabel;

    /** the successMessage */
    @SerializedName("successMessage")
    private String successMessage;

    /** the updatePasswordModalHeader */
    @SerializedName("updatePasswordModalHeader")
    private String updatePasswordModalHeader;

    /** the currentPwdRequiredError */
    @SerializedName("currentPwdRequiredError")
    private String currentPwdRequiredError;

    /** the currentPwdError */
    @SerializedName("currentPasswordError")
    private String currentPwdError;

    /** the newPwdRequiredError */
    @SerializedName("newPwdRequiredError")
    private String newPwdRequiredError;

    /** the passwordRuleLabels */
    @SerializedName("passwordRuleLabels")
    private PasswordRuleLabels passwordRuleLabels;

    /** the confirmPwdRequiredError */
    @SerializedName("confirmPwdRequiredError")
    private String confirmPwdRequiredError;

    /** the confPwdNotMatchError */
    @SerializedName("confPasswordNotMatchError")
    private String confPwdNotMatchError;

    /** the newpwdFLNameError */
    @SerializedName("newpwdFLNameError")
    private String newpwdFLNameError;




    /**
     * Instantiates a new login info labels.
     *
     * @param  title
     * @param  emailAddressLabel
     * @param  passwordLabels
     * @param  updatePassword
     * @param  editPwdLabel
     * @param  enterNewPwdLabel
     * @param  confirmNewPwdLabel
     * @param  cancelCTALabel
     * @param  updateCTALabel
     * @param  successMessage
     */


    public LoginInfoLabels(String title,
                              String emailAddressLabel,
                              String passwordLabels,
                              String updatePassword,
                              String editPwdLabel,
                              String enterNewPwdLabel,
                              String confirmNewPwdLabel,
                              String cancelCTALabel,
                              String updateCTALabel,
                           String successMessage,
                           String updatePasswordModalHeader) {
        this.title = title;
        this.emailAddressLabel = emailAddressLabel;
        this.passwordLabels = passwordLabels;
        this.updatePassword = updatePassword;
        this.editPwdLabel = editPwdLabel;
        this.enterNewPwdLabel = enterNewPwdLabel;
        this.confirmNewPwdLabel = confirmNewPwdLabel;
        this.cancelCTALabel = cancelCTALabel;
        this.updateCTALabel = updateCTALabel;
        this.successMessage = successMessage;
        this.updatePasswordModalHeader = updatePasswordModalHeader;

    }

    public LoginInfoLabels(String title, String emailAddressLabel, String passwordLabels, String updatePassword, String editPwdLabel, String enterNewPwdLabel, String confirmNewPwdLabel, String cancelCTALabel, String updateCTALabel, String successMessage, String updatePasswordModalHeader, String currentPwdRequiredError, String currentPwdError, String newPwdRequiredError, PasswordRuleLabels passwordRuleLabels, String confirmPwdRequiredError, String confPwdNotMatchError, String newpwdFLNameError) {
        this.title = title;
        this.emailAddressLabel = emailAddressLabel;
        this.passwordLabels = passwordLabels;
        this.updatePassword = updatePassword;
        this.editPwdLabel = editPwdLabel;
        this.enterNewPwdLabel = enterNewPwdLabel;
        this.confirmNewPwdLabel = confirmNewPwdLabel;
        this.cancelCTALabel = cancelCTALabel;
        this.updateCTALabel = updateCTALabel;
        this.successMessage = successMessage;
        this.updatePasswordModalHeader = updatePasswordModalHeader;
        this.currentPwdRequiredError = currentPwdRequiredError;
        this.currentPwdError = currentPwdError;
        this.newPwdRequiredError = newPwdRequiredError;
        this.passwordRuleLabels = passwordRuleLabels;
        this.confirmPwdRequiredError = confirmPwdRequiredError;
        this.confPwdNotMatchError = confPwdNotMatchError;
        this.newpwdFLNameError = newpwdFLNameError;
    }

}
