package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class MessageAndCodeBeanTest.
 */
class MessageAndCodeBeanTest {
	
	/** The message and code bean. */
	MessageAndCodeBean messageAndCodeBean;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		messageAndCodeBean = new MessageAndCodeBean("TEST_CODE", "TestLabel");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(messageAndCodeBean.getId(), "TEST_CODE");
		Assert.assertEquals(messageAndCodeBean.getLabel(), "TestLabel");
	}
}
