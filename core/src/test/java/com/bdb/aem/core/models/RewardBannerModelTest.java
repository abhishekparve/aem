package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;

import junitx.util.PrivateAccessor;

/**
 * The Class RewardBannerModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class RewardBannerModelTest {

	/** The reward banner model. */
	@InjectMocks
	RewardBannerModel rewardBannerModel;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The test value. */
	private String TEST_VALUE = "test";

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		PrivateAccessor.setField(rewardBannerModel, "rewardsTitle", TEST_VALUE);
		PrivateAccessor.setField(rewardBannerModel, "rewardPoints", TEST_VALUE);
		PrivateAccessor.setField(rewardBannerModel, "rewardsSubTitle", TEST_VALUE);
		PrivateAccessor.setField(rewardBannerModel, "altText", TEST_VALUE);
		PrivateAccessor.setField(rewardBannerModel, "signUpLabel", TEST_VALUE);
		PrivateAccessor.setField(rewardBannerModel, "imagePath", TEST_VALUE);
		PrivateAccessor.setField(rewardBannerModel, "link", TEST_VALUE);
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		rewardBannerModel.init();
	}

	/**
	 * Gets the methods.
	 *
	 * @return the methods
	 */
	@Test
	void getMethods() {
		assertEquals(TEST_VALUE, rewardBannerModel.getRewardsTitle());
		assertEquals(TEST_VALUE, rewardBannerModel.getRewardPoints());
		assertEquals(TEST_VALUE, rewardBannerModel.getRewardsSubTitle());
		assertEquals(TEST_VALUE, rewardBannerModel.getAltText());
		assertEquals(TEST_VALUE, rewardBannerModel.getSignUpLabel());
		assertEquals(TEST_VALUE, rewardBannerModel.getImagePath());
		assertEquals(TEST_VALUE, rewardBannerModel.getLink());

	}

}
