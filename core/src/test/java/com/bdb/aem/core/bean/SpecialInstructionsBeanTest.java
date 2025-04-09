package com.bdb.aem.core.bean;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class SpecialInstructionsBeanTest.
 */
class SpecialInstructionsBeanTest {
	
	/** The special instructions bean. */
	SpecialInstructionsBean specialInstructionsBean;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		specialInstructionsBean = new SpecialInstructionsBean();
		specialInstructionsBean.setSpecialInstructionsHeading(("12345ABCDE"));
	}
	
	/**
	 * Test.
	 */
	@Test
	void test() {
		assertEquals("12345ABCDE", specialInstructionsBean.getSpecialInstructionsHeading());
	}
}
