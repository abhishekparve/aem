package com.bdb.aem.core.pojo;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

/**
 * The Class AccountManagementOrdersConfigTest.
 */
class AccountManagementOrdersConfigTest {
	
	/** The orders test config. */
	AccountManagementOrdersConfig ordersTestConfig;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		ordersTestConfig = new AccountManagementOrdersConfig(new JsonObject(), new JsonObject(), new JsonObject(), new JsonObject(),new JsonObject(),new JsonObject());
		ordersTestConfig.setAllOrdersList(new JsonObject());
		ordersTestConfig.setCancelOrderJson(new JsonObject());
		ordersTestConfig.setTrackingNumberConfig(new JsonObject());
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		assertNotNull(ordersTestConfig.getGetUserOrdersListJson());
		assertNotNull(ordersTestConfig.getGetOrderDetailsJson());
		assertNotNull(ordersTestConfig.getGetOrderPackingListJson());
		assertNotNull(ordersTestConfig.getTriggerReOrderJson());
		assertNotNull(ordersTestConfig.getFetchAddressPayload());
		assertNotNull(ordersTestConfig.getMyOrdersList());
		assertNotNull(ordersTestConfig.getAllOrdersList());
		assertNotNull(ordersTestConfig.getCancelOrderJson());
		assertNotNull(ordersTestConfig.getTrackingNumberConfig());
	}
}
