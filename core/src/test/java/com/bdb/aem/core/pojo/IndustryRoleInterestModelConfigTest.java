package com.bdb.aem.core.pojo;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bdb.aem.core.models.SignUpIndustryModel;

import junit.framework.Assert;

/**
 * The Class IndustryRoleInterestModelConfigTest.
 */
class IndustryRoleInterestModelConfigTest {

	/** The industry role interest model config. */
	IndustryRoleInterestModelConfig industryRoleInterestModelConfig;
	
	/** The industry multifield list. */
	private List<SignUpIndustryModel> industryMultifieldList = Arrays.asList(new SignUpIndustryModel());

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		industryRoleInterestModelConfig = new IndustryRoleInterestModelConfig("industryTitle", "industryDescription",
				"industrySelectionText", "roleTitle", "roleDescription", "roleSelectionText", "interestTitle",
				"interestDescription", "interestSelectionText", "nextIndustryButtonLabel", "skipIndustryLinkLabel",
				"backRoleButtonLabel", "nextRoleButtonLabel", "backInterestButtonLabel", "nextInterestButtonLabel",
				industryMultifieldList);
	}

	/**
	 * Test get industry title.
	 */
	@Test
	void testGetIndustryTitle() {
		Assert.assertEquals(industryRoleInterestModelConfig.getIndustryTitle(), "industryTitle");
	}

	/**
	 * Test get industry description.
	 */
	@Test
	void testGetIndustryDescription() {
		Assert.assertEquals(industryRoleInterestModelConfig.getIndustryDescription(), "industryDescription");
	}

	/**
	 * Test get industry selection text.
	 */
	@Test
	void testGetIndustrySelectionText() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getIndustrySelectionText());
	}

	/**
	 * Test get role title.
	 */
	@Test
	void testGetRoleTitle() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getRoleTitle());
	}

	/**
	 * Test get role description.
	 */
	@Test
	void testGetRoleDescription() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getRoleDescription());
	}

	/**
	 * Test get role selection text.
	 */
	@Test
	void testGetRoleSelectionText() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getRoleSelectionText());
	}

	/**
	 * Test get interest title.
	 */
	@Test
	void testGetInterestTitle() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getInterestTitle());
	}

	/**
	 * Test get interest description.
	 */
	@Test
	void testGetInterestDescription() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getInterestDescription());
	}

	/**
	 * Test get interest selection text.
	 */
	@Test
	void testGetInterestSelectionText() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getInterestSelectionText());
	}

	/**
	 * Test get next industry button label.
	 */
	@Test
	void testGetNextIndustryButtonLabel() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getNextIndustryButtonLabel());
	}

	/**
	 * Test get skip industry link label.
	 */
	@Test
	void testGetSkipIndustryLinkLabel() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getSkipIndustryLinkLabel());
	}

	/**
	 * Test get back role button label.
	 */
	@Test
	void testGetBackRoleButtonLabel() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getBackRoleButtonLabel());
	}

	/**
	 * Test get next role button label.
	 */
	@Test
	void testGetNextRoleButtonLabel() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getNextRoleButtonLabel());
	}

	/**
	 * Test get back interest button label.
	 */
	@Test
	void testGetBackInterestButtonLabel() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getBackInterestButtonLabel());
	}

	/**
	 * Test get next interest button label.
	 */
	@Test
	void testGetNextInterestButtonLabel() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getNextInterestButtonLabel());
	}

	/**
	 * Test get industry multifield.
	 */
	@Test
	void testGetIndustryMultifield() {
		Assert.assertNotNull(industryRoleInterestModelConfig.getIndustryMultifield());
	}

	/**
	 * Test to string.
	 */
	@Test
	void testToString() {
		Assert.assertNotNull(industryRoleInterestModelConfig.toString());
	}
}
