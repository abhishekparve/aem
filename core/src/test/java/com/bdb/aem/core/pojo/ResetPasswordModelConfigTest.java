package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class ResetPasswordModelConfigTest.
 */
class ResetPasswordModelConfigTest {

	/** The reset password model test config. */
	ResetPasswordModelConfig resetPasswordModelTestConfig = new ResetPasswordModelConfig("title", "subTitle",
			"createPwdLabel", "confirmPwdLabel", "resetPasswordRedirectUrl", "resetPasswordCTALabel",
			new PasswordRuleLabels("ruleHeading", "lengthRuleLabel", "caseRuleLabel", "numericRuleLabel", "symbolRuleLabel", "noSpacesLabel"));

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		resetPasswordModelTestConfig = new ResetPasswordModelConfig("title", "subTitle", "createPwdLabel",
				"confirmPwdLabel", "resetPasswordRedirectUrl", "resetPasswordCTALabel", "passwordError",
				"passwordFLNameError", "passwordPatternError", "confPasswordError", "confPasswordNotMatchError",
				new PasswordRuleLabels("ruleHeading", "lengthRuleLabel", "caseRuleLabel", "numericRuleLabel", "symbolRuleLabel", "noSpacesLabel"));
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(resetPasswordModelTestConfig.getCreatePwdLabel(), "createPwdLabel");
		Assert.assertEquals(resetPasswordModelTestConfig.getSubTitle(), "subTitle");
		Assert.assertEquals(resetPasswordModelTestConfig.getTitle(), "title");
		Assert.assertNotNull(resetPasswordModelTestConfig.getConfirmPasswordPlaceholderLabel());
		Assert.assertNotNull(resetPasswordModelTestConfig.getConfirmPwdLabel());
		Assert.assertNotNull(resetPasswordModelTestConfig.getConfPasswordError());
		Assert.assertNotNull(resetPasswordModelTestConfig.getConfPasswordNotMatchError());
		Assert.assertNotNull(resetPasswordModelTestConfig.getCreatePwdLabel());
		Assert.assertNotNull(resetPasswordModelTestConfig.getPasswordError());
		Assert.assertNotNull(resetPasswordModelTestConfig.getPasswordFLNameError());		
		Assert.assertNotNull(resetPasswordModelTestConfig.getPasswordPatternError());
		Assert.assertNotNull(resetPasswordModelTestConfig.getResetPasswordCTALabel());
		Assert.assertNotNull(resetPasswordModelTestConfig.getResetPasswordRedirectUrl());
		Assert.assertNotNull(resetPasswordModelTestConfig.getPasswordRuleLabels());
	}

}
