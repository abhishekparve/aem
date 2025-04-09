package com.bdb.aem.core.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class ShipmentsLabelsBeanTest.
 */
class ShipmentsLabelsBeanTest {
	
	/** The shipments labels bean. */
	ShipmentsLabelsBean shipmentsLabelsBean;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		shipmentsLabelsBean = new ShipmentsLabelsBean();
		shipmentsLabelsBean.setEstDeliveryDate("String");
		shipmentsLabelsBean.setOrderedLabel("String");
		shipmentsLabelsBean.setPackingListLabel("String");
		shipmentsLabelsBean.setPendingStatusLabel("String");
		shipmentsLabelsBean.setQuantityLabel("String");
		shipmentsLabelsBean.setShipmentNumberLabel("String");
		shipmentsLabelsBean.setShippedQuantityLabel("shippedQuantityLabel");
		shipmentsLabelsBean.setShipmentsCancelledLabel("String");
		shipmentsLabelsBean.setShipmentsCompleteLabel("String");
		shipmentsLabelsBean.setShipmentsOpenLabel("String");
		shipmentsLabelsBean.setShipmentsStatusLabel("String");
		shipmentsLabelsBean.setShipmentsTitle("String");
		shipmentsLabelsBean.setStatusLabel("String");
		shipmentsLabelsBean.setTrackingNumberLabel("String");
		shipmentsLabelsBean.setViewPendingCta("String");
		shipmentsLabelsBean.setViewPendingLinkMessage("String");
		shipmentsLabelsBean.setViewShippedCta("String");
		shipmentsLabelsBean.setViewShippedLinkMessage("String");
		shipmentsLabelsBean.setYouSavedLabel("youSavedLabel");
		shipmentsLabelsBean.setOnLabel("onLabel");
		shipmentsLabelsBean.setPromocodeLabel("promocodeLabel");
		
	}
	
	/**
	 * Test.
	 */
	@Test
	void test() {
		assertEquals("String", shipmentsLabelsBean.getEstDeliveryDate());
		assertEquals("String", shipmentsLabelsBean.getOrderedLabel());
		assertEquals("String", shipmentsLabelsBean.getPackingListLabel());
		assertEquals("String", shipmentsLabelsBean.getPendingStatusLabel());
		assertEquals("String", shipmentsLabelsBean.getQuantityLabel());
		assertEquals("String", shipmentsLabelsBean.getShipmentNumberLabel());
		assertEquals("shippedQuantityLabel", shipmentsLabelsBean.getShippedQuantityLabel());
		assertEquals("String", shipmentsLabelsBean.getShipmentsCancelledLabel());
		assertEquals("String", shipmentsLabelsBean.getShipmentsCompleteLabel());
		assertEquals("String", shipmentsLabelsBean.getShipmentsOpenLabel());
		assertEquals("String", shipmentsLabelsBean.getShipmentsStatusLabel());
		assertEquals("String", shipmentsLabelsBean.getShipmentsTitle());
		assertEquals("String", shipmentsLabelsBean.getStatusLabel());
		assertEquals("String", shipmentsLabelsBean.getTrackingNumberLabel());
		assertEquals("String", shipmentsLabelsBean.getViewPendingCta());
		assertEquals("String", shipmentsLabelsBean.getViewPendingLinkMessage());
		assertEquals("String", shipmentsLabelsBean.getViewShippedCta());
		assertEquals("String", shipmentsLabelsBean.getViewShippedLinkMessage());
		assertEquals("youSavedLabel", shipmentsLabelsBean.getYouSavedLabel());
		assertEquals("onLabel", shipmentsLabelsBean.getOnLabel());
		assertEquals("promocodeLabel", shipmentsLabelsBean.getPromocodeLabel());
	}
}
