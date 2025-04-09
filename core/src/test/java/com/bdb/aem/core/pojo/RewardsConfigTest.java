package com.bdb.aem.core.pojo;

import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * The Class RewardsConfigTest.
 */
class RewardsConfigTest {

    /**
     * The test Rewards Config.
     */
    RewardsConfig rewardsConfig;

    @Mock
    private PayloadConfig updateRewardsPreferences;

    @Mock
    Payload requestPayload;


    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
         String siteId = "";
         String summaryTabSrc = "";
         String rewardsTabSrc = "";
         String activityTabSrc = "";
        String earnTabSrc = "";
		String annexJs = "";

        updateRewardsPreferences = new PayloadConfig(requestPayload);

        rewardsConfig = new RewardsConfig(updateRewardsPreferences,
                siteId,
                summaryTabSrc,
                rewardsTabSrc,
                earnTabSrc,
                activityTabSrc,
				annexJs);
    }

    /**
     * Test.
     */
    @Test
    void test() {

        Assert.assertNotNull(rewardsConfig.getSiteId());
        Assert.assertNotNull(rewardsConfig.getSummaryTabSrc());
        Assert.assertNotNull(rewardsConfig.getActivityTabSrc());
        Assert.assertNotNull(rewardsConfig.getRewardsTabSrc());
        Assert.assertNotNull(rewardsConfig.getEarnTabSrc());
        Assert.assertNotNull(rewardsConfig.getUpdateRewardsPreferences());
		Assert.assertNotNull(rewardsConfig.getAnnexJs());
    }

}
