package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class AddressSelectionLabelsBeanTest.
 */
class AddressSelectionLabelsBeanTest {
	
	/** The address selection labels test bean. */
	AddressSelectionLabelsBean addressSelectionLabelsTestBean;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		addressSelectionLabelsTestBean = new AddressSelectionLabelsBean();
		addressSelectionLabelsTestBean.setAddressLabels(new JsonObject());
		addressSelectionLabelsTestBean.setChangeLinkedAddressLinkLabel("ChangeLinkedAddressLinkLabel");
		addressSelectionLabelsTestBean.setDefaultLabel("Default");
		addressSelectionLabelsTestBean.setFavoriteShippingAddressText("FavoriteAddress");
		addressSelectionLabelsTestBean.setLinkedAddress("LinkedAddress");
		addressSelectionLabelsTestBean.setLocationIcon("LocationIcon");
		addressSelectionLabelsTestBean.setLocationIconAltText("LocationIconAltText");
		addressSelectionLabelsTestBean.setNoLinkedAddressText("NoLinkedAddressText");
		addressSelectionLabelsTestBean.setSearchAddress(new JsonObject());
		addressSelectionLabelsTestBean.setSearchAddressTitle("SearchAddressTitle");
		addressSelectionLabelsTestBean.setSearchPlaceHolder("SearchPlaceHolder");
		addressSelectionLabelsTestBean.setSelectAddressCtaLabel("SelectAddressCtaLabel");
		addressSelectionLabelsTestBean.setSelectAddressTitle("SelectAddressTitle");
		addressSelectionLabelsTestBean.setShippingAddressLabel("ShippingAddressLabel");
		addressSelectionLabelsTestBean.setShipToNumberLabel("ShipToNumberLabel");		
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertNotNull(addressSelectionLabelsTestBean.getAddressLabels());
        Assert.assertEquals(addressSelectionLabelsTestBean.getChangeLinkedAddressLinkLabel(),"ChangeLinkedAddressLinkLabel");
        Assert.assertEquals(addressSelectionLabelsTestBean.getDefaultLabel(),"Default");
        Assert.assertEquals(addressSelectionLabelsTestBean.getFavoriteShippingAddressText(),"FavoriteAddress");
		Assert.assertNotNull(addressSelectionLabelsTestBean.getLinkedAddress());
		Assert.assertNotNull(addressSelectionLabelsTestBean.getLocationIcon());
		Assert.assertNotNull(addressSelectionLabelsTestBean.getLocationIconAltText());
		Assert.assertNotNull(addressSelectionLabelsTestBean.getNoLinkedAddressText());
		Assert.assertNotNull(addressSelectionLabelsTestBean.getSearchAddress());
		Assert.assertNotNull(addressSelectionLabelsTestBean.getSearchAddressTitle());
		Assert.assertNotNull(addressSelectionLabelsTestBean.getSearchPlaceHolder());
		Assert.assertNotNull(addressSelectionLabelsTestBean.getSelectAddressCtaLabel());
		Assert.assertNotNull(addressSelectionLabelsTestBean.getSelectAddressTitle());
		Assert.assertNotNull(addressSelectionLabelsTestBean.getShippingAddressLabel());
		Assert.assertNotNull(addressSelectionLabelsTestBean.getShipToNumberLabel());
	}
}
