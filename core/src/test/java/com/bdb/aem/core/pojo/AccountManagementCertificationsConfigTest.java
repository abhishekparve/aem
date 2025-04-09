package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import junit.framework.Assert;

/**
 * The Class AccountManagementCertificationsConfigTest.
 */
class AccountManagementCertificationsConfigTest {
	
	/** The certifications test config. */
	AccountManagementCertificationsConfig certificationsTestConfig;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		certificationsTestConfig = new AccountManagementCertificationsConfig(new JsonObject(), new JsonObject(), new JsonObject(), new JsonObject());
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertNotNull(certificationsTestConfig.getGetUsersCertificatesConfig());
		Assert.assertNotNull(certificationsTestConfig.getDeleteUsersCertificatesConfig());
		Assert.assertNotNull(certificationsTestConfig.getUploadCertificatesConfig());
		Assert.assertNotNull(certificationsTestConfig.getGetAddressConfig());
	}
}