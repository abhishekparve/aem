package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class AccountManagementCommunicationSettingsConfigTest.
 */
class AccountManagementCommunicationSettingsConfigTest {
	
	/** The communication settings test config. */
	AccountManagementCommunicationSettingsConfig communicationSettingsTestConfig;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		communicationSettingsTestConfig = new AccountManagementCommunicationSettingsConfig(new JsonObject(), new JsonObject());
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertNotNull(communicationSettingsTestConfig.getGetNotificationsConfig());
		Assert.assertNotNull(communicationSettingsTestConfig.getUpdateNotificationConfig());
	}
}
