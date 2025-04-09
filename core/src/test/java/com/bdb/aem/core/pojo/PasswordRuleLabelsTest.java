package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class PasswordRuleLabelsTest.
 */
class PasswordRuleLabelsTest {
	
	/** The password rule test label. */
	PasswordRuleLabels passwordRuleTestLabel = new PasswordRuleLabels();

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		passwordRuleTestLabel = new PasswordRuleLabels("ruleHeading", "lengthRuleLabel", "caseRuleLabel", "numericRuleLabel", "symbolRuleLabel", "noSpacesLabel");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(passwordRuleTestLabel.getRulesHeading(), "ruleHeading");
		Assert.assertEquals(passwordRuleTestLabel.getLengthRuleLabel(),"lengthRuleLabel");
		Assert.assertNotNull(passwordRuleTestLabel.getCaseRuleLabel());
		Assert.assertNotNull(passwordRuleTestLabel.getNumericRuleLabel());
		Assert.assertNotNull(passwordRuleTestLabel.getSymbolRuleLabel());	
	}
}
