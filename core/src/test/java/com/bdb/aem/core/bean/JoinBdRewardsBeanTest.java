package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;

/**
 * The Class JoinBdRewardsBeanTest.
 */
class JoinBdRewardsBeanTest {
	
	/** The join bd rewards bean. */
	JoinBdRewardsBean joinBdRewardsBean;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		joinBdRewardsBean = new JoinBdRewardsBean();
		joinBdRewardsBean.setJoinRewardsTitle("joinRewardsTitle");
		joinBdRewardsBean.setJoinRewardsSubtitle("joinRewardsSubtitle");
		joinBdRewardsBean.setNotHealthCareProfessionalLabel("notHealthCareProfessionalLabel");
		joinBdRewardsBean.setNotGovtEmployeeLabel("notGovtEmployeeLabel");
		joinBdRewardsBean.setNotProhibitedGiftsAccept("notProhibitedGiftsAccept");
		joinBdRewardsBean.setRewardsTnCLabel("rewardsTnCLabel");
		joinBdRewardsBean.setJoinBdRewardsCTALabel("joinBdRewardsCTALabel");
		joinBdRewardsBean.setJoinBdRewardsCTALink("joinBdRewardsCTALink");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(joinBdRewardsBean.getJoinRewardsTitle(),"joinRewardsTitle");
		Assert.assertNotNull(joinBdRewardsBean.getJoinRewardsSubtitle());
		Assert.assertNotNull(joinBdRewardsBean.getNotHealthCareProfessionalLabel());
		Assert.assertNotNull(joinBdRewardsBean.getNotGovtEmployeeLabel());
		Assert.assertNotNull(joinBdRewardsBean.getNotProhibitedGiftsAccept());
		Assert.assertNotNull(joinBdRewardsBean.getRewardsTnCLabel());
		Assert.assertNotNull(joinBdRewardsBean.getJoinBdRewardsCTALabel());
		Assert.assertNotNull(joinBdRewardsBean.getJoinBdRewardsCTALink());
	}

}
