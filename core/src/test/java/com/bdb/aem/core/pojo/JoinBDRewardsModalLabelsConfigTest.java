package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bdb.aem.core.bean.JoinBdRewardsBean;

import junit.framework.Assert;

/**
 * The Class JoinBDRewardsModalLabelsConfigTest.
 */
class JoinBDRewardsModalLabelsConfigTest {
	
	/** The test config. */
	JoinBDRewardsModalLabelsConfig testConfig;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		JoinBdRewardsBean joinBdRewardsBean = new JoinBdRewardsBean();
		joinBdRewardsBean.setJoinRewardsTitle("joinRewardsTitle");
		joinBdRewardsBean.setJoinRewardsSubtitle("joinRewardsSubtitle");
		joinBdRewardsBean.setNotHealthCareProfessionalLabel("notHealthCareProfessionalLabel");
		joinBdRewardsBean.setNotGovtEmployeeLabel("notGovtEmployeeLabel");
		joinBdRewardsBean.setNotProhibitedGiftsAccept("notProhibitedGiftsAccept");
		joinBdRewardsBean.setRewardsTnCLabel("rewardsTnCLabel");
		joinBdRewardsBean.setJoinBdRewardsCTALabel("joinBdRewardsCTALabel");
		joinBdRewardsBean.setJoinBdRewardsCTALink("joinBdRewardsCTALink");
		testConfig = new JoinBDRewardsModalLabelsConfig(joinBdRewardsBean);
	}

	/**
	 * Test get join rewards title.
	 */
	@Test
	void testGetJoinRewardsTitle() {
		Assert.assertEquals(testConfig.getJoinRewardsTitle(),"joinRewardsTitle");
	}

	/**
	 * Test get join rewards subtitle.
	 */
	@Test
	void testGetJoinRewardsSubtitle() {
		Assert.assertNotNull(testConfig.getJoinRewardsSubtitle());
	}

	/**
	 * Test get not health care professional label.
	 */
	@Test
	void testGetNotHealthCareProfessionalLabel() {
		Assert.assertNotNull(testConfig.getNotHealthCareProfessionalLabel());
	}

	/**
	 * Test get not govt employee label.
	 */
	@Test
	void testGetNotGovtEmployeeLabel() {
		Assert.assertNotNull(testConfig.getNotGovtEmployeeLabel());
	}

	/**
	 * Test get not prohibited gifts accept.
	 */
	@Test
	void testGetNotProhibitedGiftsAccept() {
		Assert.assertNotNull(testConfig.getNotProhibitedGiftsAccept());
	}

	/**
	 * Test get rewards tn C label.
	 */
	@Test
	void testGetRewardsTnCLabel() {
		Assert.assertNotNull(testConfig.getRewardsTnCLabel());
	}

	/**
	 * Test get join bd rewards CTA label.
	 */
	@Test
	void testGetJoinBdRewardsCTALabel() {
		Assert.assertNotNull(testConfig.getJoinBdRewardsCTALabel());
	}

	/**
	 * Test get join bd rewards CTA link.
	 */
	@Test
	void testGetJoinBdRewardsCTALink() {
		Assert.assertNotNull(testConfig.getJoinBdRewardsCTALink());
	}
}
