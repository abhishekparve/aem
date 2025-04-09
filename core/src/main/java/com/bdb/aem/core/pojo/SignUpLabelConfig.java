package com.bdb.aem.core.pojo;

import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.annotations.SerializedName;

/**
 * The Class SignUpLabelConfig.
 */
public class SignUpLabelConfig {

	/** The heading. */
	@SerializedName("heading")
	private String heading;

	/** The sub heading. */
	@SerializedName("subHeading")
	private String subHeading;

	/** The mandatory note. */
	@SerializedName("mandatoryNote")
	private String mandatoryNote;

	/** The first name label. */
	@SerializedName("firstNameLabel")
	private String firstNameLabel;

	/** The first name error. */
	@SerializedName("firstNameError")
	private String firstNameError;

	/** The last name label. */
	@SerializedName("lastNameLabel")
	private String lastNameLabel;

	/** The last name error. */
	@SerializedName("lastNameError")
	private String lastNameError;

	/** The company label. */
	@SerializedName("companyLabel")
	private String companyLabel;

	/** The company error. */
	@SerializedName("companyError")
	private String companyError;

	/** The mobile label. */
	@SerializedName("mobileLabel")
	private String mobileLabel;

	/** The phone num label. */
	@SerializedName("phoneNumLabel")
	private String phoneNumLabel;

	/** The phone num error. */
	@SerializedName("phoneNumError")
	private String phoneNumError;

	/** The phone num pattern error. */
	@SerializedName("phoneNumPatternError")
	private String phoneNumPatternError;

	/** The email label. */
	@SerializedName("emailLabel")
	private String emailLabel;

	/** The email error. */
	@SerializedName("emailError")
	private String emailError;

	/** The email pattern error. */
	@SerializedName("emailPatternError")
	private String emailPatternError;
	
	/** The email tool tip. */
	@SerializedName("emailTooltip")
	private String emailToolTip;

	/** The country label. */
	@SerializedName("countryLabel")
	private String countryLabel;

	/** The country error. */
	@SerializedName("countryError")
	private String countryError;

	/** The password label. */
	@SerializedName("passwordLabel")
	private String passwordLabel;

	/** The password error. */
	@SerializedName("passwordError")
	private String passwordError;

	/** The password FL name error. */
	@SerializedName("passwordFLNameError")
	private String passwordFLNameError;

	/** The password pattern error. */
	@SerializedName("passwordPatternError")
	private String passwordPatternError;

	/** The confirm password label. */
	@SerializedName("confPasswordLabel")
	private String confPasswordLabel;

	/** The confirm password error. */
	@SerializedName("confPasswordError")
	private String confPasswordError;

	/** The confirm password not match error. */
	@SerializedName("confPasswordNotMatchError")
	private String confpasswordNotMatchError;

	/** The special offer email label. */
	@SerializedName("specialOfferEmailLabel")
	private String specialOfferEmailLabel;

	/** The account update email label. */
	@SerializedName("accountUpdateEmailLabel")
	private String accountUpdateEmailLabel;

	/** The age update email label. */
	@SerializedName("userAgeCheckLabel")
	private String ageCheckboxText;

	/** The tnc note. */
	@SerializedName("tncNote")
	private String tncNote;

	/** The signup btn label. */
	@SerializedName("signupBtnLabel")
	private String signupBtnLabel;

	/** The existing user label. */
	@SerializedName("existingUserLabel")
	private String existingUserLabel;

	/** The sing in label. */
	@SerializedName("singInLabel")
	private String singInLabel;
	
	/** The existing User Modal Title. */
	@SerializedName("existingUserModalTitle")
	private String existingUserModalTitle;
	
	/** The existing User Modal Description */
	@SerializedName("existingUserModalDescription")
	private String existingUserModalDescription;
	
	/** The existing User Modal CTA. */
	@SerializedName("existingUserModalCTA")
	private String existingUserModalCTA;
	
	/** The domain error. */
	@SerializedName("domainError")
	private String domainError;

	/**
	 * Instantiates a new sign up label config.
	 *
	 * @param heading the heading
	 * @param subHeading the sub heading
	 * @param mandatoryNote the mandatory note
	 * @param firstNameLabel the first name label
	 * @param firstNameError the first name error
	 * @param lastNameLabel the last name label
	 * @param lastNameError the last name error
	 * @param companyLabel the company label
	 * @param companyError the company error
	 * @param mobileLabel the mobile label
	 * @param phoneNumLabel the phone num label
	 * @param phoneNumError the phone num error
	 * @param phoneNumPatternError the phone num pattern error
	 * @param emailLabel the email label
	 * @param emailToolTip the email tool tip
	 * @param emailError the email error
	 * @param emailPatternError the email pattern error
	 * @param countryLabel the country label
	 * @param countryError the country error
	 * @param passwordLabel the password label
	 * @param passwordError the password error
	 * @param passwordFLNameError the password FL name error
	 * @param passwordPatternError the password pattern error
	 * @param confPasswordLabel the conf password label
	 * @param confPasswordError the conf password error
	 * @param confpasswordNotMatchError the confpassword not match error
	 * @param specialOfferEmailLabel the special offer email label
	 * @param accountUpdateEmailLabel the account update email label
	 * @param ageCheckboxText the age checkbox  text
	 * @param tncNote the tnc note
	 * @param signupBtnLabel the signup btn label
	 * @param existingUserLabel the existing user label
	 * @param singInLabel the sing in label
	 * @param existingUserModalTitle the existing user modal title
	 * @param existingUserModalDescription the existing user modal description
	 * @param existingUserModalCTA the existing user modal cta
	 */
	public SignUpLabelConfig(String heading, String subHeading, String mandatoryNote, String firstNameLabel,
			String firstNameError, String lastNameLabel, String lastNameError, String companyLabel, String companyError,
			String mobileLabel, String phoneNumLabel, String phoneNumError, String phoneNumPatternError,
			String emailLabel, String emailToolTip, String emailError, String emailPatternError, String countryLabel, String countryError,
			String passwordLabel, String passwordError, String passwordFLNameError, String passwordPatternError,
			String confPasswordLabel, String confPasswordError, String confpasswordNotMatchError,
			String specialOfferEmailLabel, String accountUpdateEmailLabel, String ageCheckboxText,String tncNote, String signupBtnLabel,
			String existingUserLabel, String singInLabel, String existingUserModalTitle, String existingUserModalDescription, String existingUserModalCTA) {
		this.heading = heading;
		this.subHeading = subHeading;
		this.mandatoryNote = mandatoryNote;
		this.firstNameLabel = (firstNameLabel != null) ? firstNameLabel : CommonConstants.FIRST_NAME_PLACEHOLDER;
		this.firstNameError = firstNameError;

		this.lastNameLabel = (lastNameLabel != null) ? lastNameLabel : CommonConstants.LAST_NAME_PLACEHOLDER;
		this.lastNameError = lastNameError;

		this.companyLabel = (companyLabel != null) ? companyLabel : CommonConstants.COMPANY_PLACEHOLDER;
		this.companyError = companyError;

		this.mobileLabel = mobileLabel;
		this.phoneNumLabel = (phoneNumLabel != null) ? phoneNumLabel : CommonConstants.PHONE_NUMBER_PLACEHOLDER;
		this.phoneNumError = phoneNumError;
		this.phoneNumPatternError = phoneNumPatternError;

		this.emailLabel = (emailLabel != null) ? emailLabel : CommonConstants.EMAIL_PLACEHOLDER;
		this.emailToolTip = emailToolTip;
		this.emailError = emailError;
		this.emailPatternError = emailPatternError;

		this.countryLabel = countryLabel;
		this.countryError = countryError;

		this.passwordLabel = (passwordLabel != null) ? passwordLabel : CommonConstants.PASS_PLACEHOLDER;
		this.passwordError = passwordError;
		this.passwordFLNameError = passwordFLNameError;
		this.passwordPatternError = passwordPatternError;

		this.confPasswordLabel = (confPasswordLabel != null) ? confPasswordLabel
				: CommonConstants.CONFIRM_PASS_PLACEHOLDER;
		this.confPasswordError = confPasswordError;
		this.confpasswordNotMatchError = confpasswordNotMatchError;

		this.specialOfferEmailLabel = specialOfferEmailLabel;
		this.accountUpdateEmailLabel = accountUpdateEmailLabel;
		this.ageCheckboxText = ageCheckboxText;
		this.tncNote = tncNote;
		this.existingUserLabel = existingUserLabel;
		this.signupBtnLabel = signupBtnLabel;
		this.singInLabel = singInLabel;
		this.existingUserModalTitle = existingUserModalTitle;
		this.existingUserModalDescription = existingUserModalDescription;
		this.existingUserModalCTA = existingUserModalCTA;
	}

	/**
	 * Gets the Heading Title Text.
	 *
	 * @return - Heading Title Text as a String
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * Gets the Sub-Heading Description Text.
	 *
	 * @return - Sub-Heading Description Text as a String
	 */
	public String getSubHeading() {
		return subHeading;
	}

	/**
	 * Gets the Fields Required Text.
	 *
	 * @return - Fields Required Text as a String
	 */
	public String getMandatoryNote() {
		return mandatoryNote;
	}

	/**
	 * Gets First Name Label Text.
	 *
	 * @return - First Name Label Text as a String
	 */
	public String getFirstNameLabel() {
		return firstNameLabel;
	}

	/**
	 * Gets the First Name Error Text.
	 *
	 * @return - First Name Error Text as a String
	 */
	public String getFirstNameError() {
		return firstNameError;
	}

	/**
	 * Gets the Last Name Label Text.
	 *
	 * @return - Last Name Label Text as a String
	 */
	public String getLastNameLabel() {
		return lastNameLabel;
	}

	/**
	 * Gets the Last Name Error Text.
	 *
	 * @return - Last Name Error Text as a String
	 */
	public String getLastNameError() {
		return lastNameError;
	}

	/**
	 * Gets the Company Label Text.
	 *
	 * @return - Company Label Text as a String
	 */
	public String getCompanyLabel() {
		return companyLabel;
	}

	/**
	 * Gets the Company Error Text.
	 *
	 * @return - Company Error Text as a String
	 */
	public String getCompanyError() {
		return companyError;
	}

	/**
	 * Gets the Mobile Label Text.
	 *
	 * @return - Mobile Label Text as a String
	 */
	public String getMobileLabel() {
		return mobileLabel;
	}

	/**
	 * Gets the Mobile Label Text.
	 *
	 * @return - Mobile Label Text as a String
	 */
	public String getPhoneNumLabel() {
		return phoneNumLabel;
	}

	/**
	 * Gets the Phone Number Error Text.
	 *
	 * @return - Phone Number Error Text as a String
	 */
	public String getPhoneNumError() {
		return phoneNumError;
	}

	/**
	 * Gets the Phone Number Pattern Error Text.
	 *
	 * @return - Phone Number Pattern Error Text
	 */
	public String getPhoneNumPatternError() {
		return phoneNumPatternError;
	}

	/**
	 * Gets the Email Label Text.
	 *
	 * @return - Email Label Text as a String
	 */
	public String getEmailLabel() {
		return emailLabel;
	}

	/**
	 * 	Gets the Email Error Text.
	 *
	 * @return - Email Error Text as a String
	 */
	public String getEmailError() {
		return emailError;
	}

	/**
	 * Gets the Email Pattern Error Text.
	 *
	 * @return - Email Pattern Error Text as a String
	 */
	public String getEmailPatternError() {
		return emailPatternError;
	}

	/**
	 * Gets the email tool tip.
	 *
	 * @return the email tool tip
	 */
	public String getEmailToolTip() {
		return emailToolTip;
	}

	/**
	 * Gets the Country Label Text.
	 *
	 * @return - Country Label Text as a String
	 */
	public String getContryLabel() {
		return countryLabel;
	}

	/**
	 * Gets the Country Error Text.
	 *
	 * @return - Country Error Text as a String
	 */
	public String getContryError() {
		return countryError;
	}

	/**
	 * Gets the Password Label Text.
	 *
	 * @return - Password Label Text as a String
	 */
	public String getPasswordLabel() {
		return passwordLabel;
	}

	/**
	 * Gets the Password Error Text.
	 *
	 * @return - Password Error Text as a String
	 */
	public String getPasswordError() {
		return passwordError;
	}

	/**
	 * Gets the Password First Last Name Error Text.
	 *
	 * @return - Password First Last Name Error Text as a String
	 */
	public String getPasswordFLNameError() {
		return passwordFLNameError;
	}

	/**
	 * Gets the Password Pattern Error Text.
	 *
	 * @return - Password Pattern Error Text as a String
	 */
	public String getPasswordPatternError() {
		return passwordPatternError;
	}

	/**
	 * Gets the Confirm Password Label Text.
	 *
	 * @return - Confirm Password Label Text as a String
	 */
	public String getConfPasswordLabel() {
		return confPasswordLabel;
	}

	/**
	 * Gets the Confirm Password Error Text.
	 *
	 * @return - Confirm Password Error Text as a String
	 */
	public String getConfPasswordError() {
		return confPasswordError;
	}

	/**
	 * Gets the Confirm Password Not Match Error Text.
	 *
	 * @return - Confirm Password Not Match Error Text as a String
	 */
	public String getConfpasswordNotMatchError() {
		return confpasswordNotMatchError;
	}

	/**
	 * Gets the Confirm Password Not Match Error Text.
	 *
	 * @return - Confirm Password Not Match Error Text as a String
	 */
	public String getSpecialOfferEmailLabel() {
		return specialOfferEmailLabel;
	}

	/**
	 * Gets the Account Update Email.
	 *
	 * @return - Account Update Email as a String
	 */
	public String getAccountUpdateEmailLabel() {
		return accountUpdateEmailLabel;
	}

	/**
	 * Gets the age Update .
	 *
	 * @return - age Update checkbox text as a String
	 */
	public String getAgeCheckboxText() {
		return ageCheckboxText;
	}

	/**
	 * Gets the Terms and Conditions Note Text.
	 *
	 * @return - Terms and Conditions Note Text as a String
	 */
	public String getTncNote() {
		return tncNote;
	}

	/**
	 * Gets the Existing User Label Text.
	 *
	 * @return - Existing User Label Text as a String
	 */
	public String getExistingUserLabel() {
		return existingUserLabel;
	}

	/**
	 * Gets the Sign Up Button Label Text.
	 *
	 * @return - Sign Up Button Label Text as a String
	 */
	public String getSignupBtnLabel() {
		return signupBtnLabel;
	}

	/**
	 * Gets the Sign In Label Text.
	 *
	 * @return - Sign In Label Text as a String
	 */
	public String getSingInLabel() {
		return singInLabel;
	}
	
	/**
	 * Gets the Existing User Modal Title.
	 *
	 * @return - Existing User Modal Title as a String
	 */
	public String getExistingUserModalTitle() {
		return existingUserModalTitle;
	}
	
	/**
	 * Gets the Existing User Modal Description.
	 *
	 * @return - Existing User Modal Description as a String
	 */
	public String getExistingUserModalDescription() {
		return existingUserModalDescription;
	}
	
	/**
	 * Gets the Existing User Modal CTA.
	 *
	 * @return - Existing User Modal CTA as a String
	 */
	public String getExistingUserModalCTA() {
		return existingUserModalCTA;
	}

	/**
	 * Gets the domain error.
	 *
	 * @return the domain error
	 */
	public String getDomainError() {
		return domainError;
	}

	/**
	 * Sets the domain error.
	 *
	 * @param domainError the new domain error
	 */
	public void setDomainError(String domainError) {
		this.domainError = domainError;
	}
}