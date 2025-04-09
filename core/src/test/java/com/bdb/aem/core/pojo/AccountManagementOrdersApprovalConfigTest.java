package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class AccountManagementOrdersApprovalConfigTest.
 */
class AccountManagementOrdersApprovalConfigTest {
	
	/** The orders approval test config. */
	AccountManagementOrdersApprovalConfig ordersApprovalTestConfig;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		ordersApprovalTestConfig = new AccountManagementOrdersApprovalConfig(new JsonObject(), new JsonObject(), new JsonObject(), new JsonObject());
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertNotNull(ordersApprovalTestConfig.getGetOrderApprovalList());
		Assert.assertNotNull(ordersApprovalTestConfig.getGetOrderApprovalDetails());
		Assert.assertNotNull(ordersApprovalTestConfig.getUpdateOrderApprovalStatus());
		Assert.assertNotNull(ordersApprovalTestConfig.getUpdateOrderApprovalPoNumber());
	}
}
