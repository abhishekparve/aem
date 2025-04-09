package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class ResetPasswordModelConfig.
 */
public class ResetPasswordModelConfig {
	
	/** The title. */
	@SerializedName("title")
	private String title;
	
	/** The description. */
	@SerializedName("subTitle")
	private String subTitle;
	
	/** The create password placeholder label. */
	@SerializedName("createPwdLabel")
	private String createPwdLabel;
	
	/** The confirm password placeholder label. */
	@SerializedName("confirmPwdLabel")
	private String confirmPwdLabel;
	
	/** The reset password CTA label. */
	@SerializedName("resetPasswordBtnLabel")
	private String resetPasswordCTALabel;

    /** The reset password CTA url. */
    @SerializedName("resetPasswordRedirectUrl")
    private String resetPasswordRedirectUrl;

    /** the passwordError */
    @SerializedName("passwordError")
    private String passwordError;

    /** the passwordFLNameError */
    @SerializedName("passwordFLNameError")
    private String passwordFLNameError;

    /** the passwordPatternError */
    @SerializedName("passwordPatternError")
    private String passwordPatternError;

    /** the confPasswordError */
    @SerializedName("confPasswordError")
    private String confPasswordError;

    /** the confPasswordNotMatchError */
    @SerializedName("confPasswordNotMatchError")
    private String confPasswordNotMatchError;

	/** the passwordRuleLabels */
	@SerializedName("passwordRuleLabels")
	private PasswordRuleLabels passwordRuleLabels;



	/**
	 * Instantiates a new reset password model config.
	 *
	 */
	public ResetPasswordModelConfig(String title, String subTitle, String createPwdLabel,
			String confirmPwdLabel, String resetPasswordRedirectUrl, String resetPasswordCTALabel,
                                    String passwordError,String passwordFLNameError,String passwordPatternError,
                                    String confPasswordError, String confPasswordNotMatchError,PasswordRuleLabels passwordRuleLabels) {
		this.title = title;
		this.subTitle = subTitle;
		this.createPwdLabel = createPwdLabel;
		this.confirmPwdLabel = confirmPwdLabel;
		this.resetPasswordCTALabel = resetPasswordCTALabel;
		this.resetPasswordRedirectUrl= resetPasswordRedirectUrl;
		this.passwordError = passwordError;
		this.passwordFLNameError= passwordFLNameError;
		this.passwordPatternError = passwordPatternError;
		this.confPasswordError = confPasswordError;
		this.confPasswordNotMatchError =confPasswordNotMatchError;
		this.passwordRuleLabels = passwordRuleLabels;
	}

	public ResetPasswordModelConfig(String title, String subTitle, String createPwdLabel,
									String confirmPwdLabel, String resetPasswordRedirectUrl, String resetPasswordCTALabel, PasswordRuleLabels passwordRuleLabels) {
		this.title = title;
		this.subTitle = subTitle;
		this.createPwdLabel = createPwdLabel;
		this.confirmPwdLabel = confirmPwdLabel;
		this.resetPasswordCTALabel = resetPasswordCTALabel;
		this.resetPasswordRedirectUrl= resetPasswordRedirectUrl;
		this.passwordRuleLabels=passwordRuleLabels;
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getSubTitle() {
		return subTitle;
	}

	/**
	 * Gets the creates the password placeholder label.
	 *
	 * @return the creates the password placeholder label
	 */
	public String getCreatePwdLabel() {
		return createPwdLabel;
	}

	/**
	 * Gets the confirm password placeholder label.
	 *
	 * @return the confirm password placeholder label
	 */
	public String getConfirmPasswordPlaceholderLabel() {
		return confirmPwdLabel;
	}

	/**
	 * Gets the reset password CTA label.
	 *
	 * @return the reset password CTA label
	 */
	public String getResetPasswordCTALabel() {
		return resetPasswordCTALabel;
	}

    /**
     * Gets the reset password Redirect url.
     *
     * @return the reset password Redirect url
     */
    public String getResetPasswordRedirectUrl() {
        return resetPasswordRedirectUrl;
    }

    /**
     * Gets Password  symbolRule Label.
     *
     * @return the Password  error
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * Gets Password  symbolRule Label.
     *
     * @return the Password  FL Name error
     */
    public String getPasswordFLNameError() {
        return passwordFLNameError;
    }

    /**
     * Gets Password  symbolRule Label.
     *
     * @return the Password  Pattern error
     */
    public String getPasswordPatternError() {
        return passwordPatternError;
    }


    /**
     * Gets Password  symbolRule Label.
     *
     * @return the confirm Password  error
     */
    public String getConfPasswordError() {
        return confPasswordError;
    }

    /**
     * Gets Password  symbolRule Label.
     *
     * @return the confirm Password  not match error
     */
    public String getConfPasswordNotMatchError() {
        return confPasswordNotMatchError;
    }


	/**
	 * Gets the confirm pwd label.
	 *
	 * @return the confirm pwd label
	 */
	public String getConfirmPwdLabel() {
		return confirmPwdLabel;
	}

	/**
	 * Gets the password rule labels.
	 *
	 * @return the password rule labels
	 */
	public PasswordRuleLabels getPasswordRuleLabels() {
		return passwordRuleLabels;
	}
}