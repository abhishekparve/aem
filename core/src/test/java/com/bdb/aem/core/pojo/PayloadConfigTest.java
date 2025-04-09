package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class PayloadConfigTest.
 */
class PayloadConfigTest {
	
	/** The test payload. */
	Payload testPayload;
	
	/** The test payload config. */
	PayloadConfig testPayloadConfig;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		testPayload = new Payload("url", "method");
		testPayloadConfig = new PayloadConfig(testPayload);
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertNotNull(testPayloadConfig.getRequestPayload());
	}
}
