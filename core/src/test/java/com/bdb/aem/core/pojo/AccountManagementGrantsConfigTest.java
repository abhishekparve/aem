package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import junit.framework.Assert;

/**
 * The Class AccountManagementGrantsConfigTest.
 */
class AccountManagementGrantsConfigTest {
	
	/** The grants test config. */
	AccountManagementGrantsConfig grantsTestConfig;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		grantsTestConfig = new AccountManagementGrantsConfig(new JsonObject(), new JsonObject());
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertNotNull(grantsTestConfig.getGetGrants());
		Assert.assertNotNull(grantsTestConfig.getGetOrderForGrants());
		
	}
}
