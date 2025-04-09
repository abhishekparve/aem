package com.bdb.aem.core.bean;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class OrderDetailsBeanTest.
 */
class OrderDetailsBeanTest {
	
	/** The order details bean. */
	OrderDetailsBean orderDetailsBean;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		orderDetailsBean = new OrderDetailsBean();
		orderDetailsBean.setOrderDetailsHeading("String");
		orderDetailsBean.setOrderPlacedBy("String");
		orderDetailsBean.setOrderStatusText("String");
		orderDetailsBean.setPoDate("String");
		orderDetailsBean.setPoNumber("String");
		orderDetailsBean.setSource("String");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		assertEquals("String", orderDetailsBean.getOrderDetailsHeading());
		assertEquals("String", orderDetailsBean.getOrderPlacedBy());
		assertEquals("String", orderDetailsBean.getOrderStatusText());
		assertEquals("String", orderDetailsBean.getPoDate());
		assertEquals("String", orderDetailsBean.getPoNumber());
		assertEquals("String", orderDetailsBean.getSource());
	}
}
