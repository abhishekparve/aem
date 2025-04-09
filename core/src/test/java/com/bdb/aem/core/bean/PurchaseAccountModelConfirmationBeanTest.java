package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class PurchaseAccountModelConfirmationBeanTest.
 */
class PurchaseAccountModelConfirmationBeanTest {

	/** The purchase account model confirmation bean. */
	PurchaseAccountModelConfirmationBean purchaseAccountModelConfirmationBean;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		purchaseAccountModelConfirmationBean = new PurchaseAccountModelConfirmationBean();
		purchaseAccountModelConfirmationBean.setImage("image");
		purchaseAccountModelConfirmationBean.setTitle("title");
		purchaseAccountModelConfirmationBean.setPendingTitle("pendingTitle");
		purchaseAccountModelConfirmationBean.setSuccessTitle("successTitle");
		purchaseAccountModelConfirmationBean.setPendingContent("pendingContent");
		purchaseAccountModelConfirmationBean.setCompletedContent("completedContent");
		purchaseAccountModelConfirmationBean.setRewardsCTALabel("rewardsCTALabel");
		purchaseAccountModelConfirmationBean.setContinueCTALabel("continueCTALabel");
		purchaseAccountModelConfirmationBean.setContinueCTALink("continueCTALink");
		purchaseAccountModelConfirmationBean.setJoinRewards(new JsonObject());
		purchaseAccountModelConfirmationBean.setbDRewardsConfig(new JsonObject());
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(purchaseAccountModelConfirmationBean.getImage(),"image");
		Assert.assertEquals(purchaseAccountModelConfirmationBean.getPendingTitle(),"pendingTitle");
		Assert.assertNotNull(purchaseAccountModelConfirmationBean.getTitle());
		Assert.assertNotNull(purchaseAccountModelConfirmationBean.getSuccessTitle());
		Assert.assertNotNull(purchaseAccountModelConfirmationBean.getCompletedContent());
		Assert.assertNotNull(purchaseAccountModelConfirmationBean.getContinueCTALabel());
		Assert.assertNotNull(purchaseAccountModelConfirmationBean.getContinueCTALink());
		Assert.assertNotNull(purchaseAccountModelConfirmationBean.getPendingContent());
		Assert.assertNotNull(purchaseAccountModelConfirmationBean.getRewardsCTALabel());
		Assert.assertNotNull(purchaseAccountModelConfirmationBean.getbDRewardsConfig());
		Assert.assertNotNull(purchaseAccountModelConfirmationBean.getJoinRewards());
	}

}
