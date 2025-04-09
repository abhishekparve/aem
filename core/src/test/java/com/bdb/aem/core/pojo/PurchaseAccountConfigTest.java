package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import junit.framework.Assert;

/**
 * The Class PurchaseAccountConfigTest.
 */
class PurchaseAccountConfigTest {

	/** The purchase account test config. */
	PurchaseAccountConfig purchaseAccountTestConfig;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		JsonElement purchaseAccountSubmit = new JsonObject();
		JsonElement uploadTaxCertificateConfig = new JsonObject();
		JsonElement getdistributor = new JsonObject();
		JsonElement postsmartcardregister = new JsonObject();
		JsonElement getStatesPayload = new JsonObject();
		purchaseAccountTestConfig = new PurchaseAccountConfig(
				purchaseAccountSubmit,
				uploadTaxCertificateConfig,
				getStatesPayload,
				new JsonObject(),
				getdistributor,
				postsmartcardregister);
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertNotNull(purchaseAccountTestConfig.getPurchaseAccountSubmit());
		Assert.assertNotNull(purchaseAccountTestConfig.getUploadTaxCertificateConfig());
		Assert.assertNotNull(purchaseAccountTestConfig.getGetStatesPayload());
		Assert.assertNotNull(purchaseAccountTestConfig.getGetCountriesPayload());
	}
}
