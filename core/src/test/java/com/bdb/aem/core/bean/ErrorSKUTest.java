package com.bdb.aem.core.bean;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class ErrorSKUTest.
 */
class ErrorSKUTest {

	/** The error SKU. */
	ErrorSKU errorSKU;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		errorSKU = new ErrorSKU();
		errorSKU.setDocumentPartNumber("12345ABCDE");
	}
	
	/**
	 * Test.
	 */
	@Test
	void test() {
		assertEquals("12345ABCDE", errorSKU.getDocumentPartNumber());
	}
}
