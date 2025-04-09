package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bdb.aem.core.bean.PurchaseAccountModelConfirmationBean;
import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class PurchaseAccountModelConfirmationLabelsTest.
 */
class PurchaseAccountModelConfirmationLabelsTest {
	
	/** The purchase account model confirmation test labels. */
	PurchaseAccountModelConfirmationLabels purchaseAccountModelConfirmationTestLabels;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PurchaseAccountModelConfirmationBean purchaseAccountModelConfirmationBean = new PurchaseAccountModelConfirmationBean();
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
		purchaseAccountModelConfirmationTestLabels = new PurchaseAccountModelConfirmationLabels(purchaseAccountModelConfirmationBean);
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(purchaseAccountModelConfirmationTestLabels.getImage(),"image");
		Assert.assertEquals(purchaseAccountModelConfirmationTestLabels.getPendingTitle(),"pendingTitle");
		Assert.assertNotNull(purchaseAccountModelConfirmationTestLabels.getTitle());
		Assert.assertNotNull(purchaseAccountModelConfirmationTestLabels.getSuccessTitle());
		Assert.assertNotNull(purchaseAccountModelConfirmationTestLabels.getCompletedContent());
		Assert.assertNotNull(purchaseAccountModelConfirmationTestLabels.getContinueCTALabel());
		Assert.assertNotNull(purchaseAccountModelConfirmationTestLabels.getContinueCTALink());
		Assert.assertNotNull(purchaseAccountModelConfirmationTestLabels.getPendingContent());
		Assert.assertNotNull(purchaseAccountModelConfirmationTestLabels.getRewardsCTALabel());
		Assert.assertNotNull(purchaseAccountModelConfirmationTestLabels.getbDRewardsConfig());
		Assert.assertNotNull(purchaseAccountModelConfirmationTestLabels.getJoinRewards());
	}

}
