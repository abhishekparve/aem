package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import junit.framework.Assert;

/**
 * The Class AccountManagementAddressesConfigTest.
 */
class AccountManagementAddressesConfigTest {
	
	/** The account management addresses test config. */
	AccountManagementAddressesConfig accountManagementAddressesTestConfig;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		accountManagementAddressesTestConfig = new AccountManagementAddressesConfig(new JsonObject(), new JsonObject(), new JsonObject(), new JsonObject(), new JsonObject(), new JsonObject());
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertNotNull(accountManagementAddressesTestConfig.getFetchAddressPayload());
		Assert.assertNotNull(accountManagementAddressesTestConfig.getAddAddrPayload());
		Assert.assertNotNull(accountManagementAddressesTestConfig.getEditNickPayload());
		Assert.assertNotNull(accountManagementAddressesTestConfig.getSetDefaultPayload());
		Assert.assertNotNull(accountManagementAddressesTestConfig.getSetFavouritePayload());
		Assert.assertNotNull(accountManagementAddressesTestConfig.getReactivateUserPayload());
	}
}
