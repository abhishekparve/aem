package com.bdb.aem.core.pojo;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;


/**
 * The Class SignUpLabelConfigTest.
 */
class SignUpLabelConfigTest {

	/** The sign up label test config. */
	SignUpLabelConfig signUpLabelTestConfig = new  SignUpLabelConfig("heading", "subHeading", "mandatoryNote", null,
			"firstNameError", null, "lastNameError", null, "companyError", "mobileLabel",
			null, "phoneNumError", "phoneNumPatternError", null, "emailToolTip" ,"emailError",
			"emailPatternError", "countryLabel", "countryError", null, "passwordError",
			"passwordFLNameError", "passwordPatternError", null, "confPasswordError",
			"confpasswordNotMatchError", "specialOfferEmailLabel", "accountUpdateEmailLabel", "above 18","tncNote", "signupBtnLabel",
			"existingUserLabel", "singInLabel", "existingUserModalTitle", "existingUserModalDescription", "existingUserModalCTA");

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		signUpLabelTestConfig = new SignUpLabelConfig("heading", "subHeading", "mandatoryNote", "firstNameLabel",
				"firstNameError", "lastNameLabel", "lastNameError", "companyLabel", "companyError", "mobileLabel",
				"phoneNumLabel", "phoneNumError", "phoneNumPatternError", "emailLabel", "emailToolTip", "emailError",
				"emailPatternError", "countryLabel", "countryError", "passwordLabel", "passwordError",
				"passwordFLNameError", "passwordPatternError", "confPasswordLabel", "confPasswordError",
				"confpasswordNotMatchError", "specialOfferEmailLabel", "accountUpdateEmailLabel", "above 18","tncNote", "signupBtnLabel",
				"existingUserLabel", "singInLabel", "existingUserModalTitle", "existingUserModalDescription", "existingUserModalCTA");
		signUpLabelTestConfig.setDomainError("domainError");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(signUpLabelTestConfig.getHeading(), "heading");
		Assert.assertEquals(signUpLabelTestConfig.getSubHeading(), "subHeading");
		Assert.assertEquals(signUpLabelTestConfig.getMandatoryNote(), "mandatoryNote");
		Assert.assertNotNull(signUpLabelTestConfig.getFirstNameLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getFirstNameError());
		Assert.assertNotNull(signUpLabelTestConfig.getLastNameLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getLastNameError());
		Assert.assertNotNull(signUpLabelTestConfig.getCompanyLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getCompanyError());
		Assert.assertNotNull(signUpLabelTestConfig.getMobileLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getPhoneNumLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getPhoneNumError());	
		Assert.assertNotNull(signUpLabelTestConfig.getPhoneNumPatternError());
		Assert.assertNotNull(signUpLabelTestConfig.getEmailLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getEmailToolTip());
		Assert.assertNotNull(signUpLabelTestConfig.getEmailError());
		Assert.assertNotNull(signUpLabelTestConfig.getEmailPatternError());
		Assert.assertNotNull(signUpLabelTestConfig.getContryLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getContryError());
		Assert.assertNotNull(signUpLabelTestConfig.getPasswordLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getPasswordError());
		Assert.assertNotNull(signUpLabelTestConfig.getPasswordFLNameError());		
		Assert.assertNotNull(signUpLabelTestConfig.getPasswordPatternError());
		Assert.assertNotNull(signUpLabelTestConfig.getConfPasswordLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getConfPasswordError());
		Assert.assertNotNull(signUpLabelTestConfig.getPasswordError());
		Assert.assertNotNull(signUpLabelTestConfig.getConfpasswordNotMatchError());		
		Assert.assertNotNull(signUpLabelTestConfig.getSpecialOfferEmailLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getAccountUpdateEmailLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getTncNote());
		Assert.assertNotNull(signUpLabelTestConfig.getSignupBtnLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getExistingUserLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getSingInLabel());
		Assert.assertNotNull(signUpLabelTestConfig.getExistingUserModalTitle());
		Assert.assertNotNull(signUpLabelTestConfig.getExistingUserModalDescription());
		Assert.assertNotNull(signUpLabelTestConfig.getExistingUserModalCTA());
		assertNotNull(signUpLabelTestConfig.getAgeCheckboxText());
		assertNotNull(signUpLabelTestConfig.getDomainError());
	}
}
