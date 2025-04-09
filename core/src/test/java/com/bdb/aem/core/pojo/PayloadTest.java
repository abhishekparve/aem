package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class PayloadTest.
 */
class PayloadTest {
	
	/** The test payload. */
	Payload testPayload;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		testPayload = new Payload("url", "method");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(testPayload.getUrl(),"url");
		Assert.assertEquals(testPayload.getMethod(),"method");
	}
}
