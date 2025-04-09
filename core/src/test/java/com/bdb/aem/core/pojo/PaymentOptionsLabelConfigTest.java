package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class PaymentOptionsLabelConfigTest.
 */
class PaymentOptionsLabelConfigTest {

	/** The payment options label test config. */
	PaymentOptionsLabelConfig paymentOptionsLabelTestConfig;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		paymentOptionsLabelTestConfig = new PaymentOptionsLabelConfig("purchaseOrder", "creditCard");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(paymentOptionsLabelTestConfig.getPurchaseOrder(), "purchaseOrder");
		Assert.assertEquals(paymentOptionsLabelTestConfig.getCreditCard(), "creditCard");
	}

}
