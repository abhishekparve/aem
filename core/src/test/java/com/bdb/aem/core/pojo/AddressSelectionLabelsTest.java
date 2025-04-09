package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bdb.aem.core.bean.AddressSelectionLabelsBean;
import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class AddressSelectionLabelsTest.
 */
class AddressSelectionLabelsTest {
	
	/** The address selection test labels. */
	AddressSelectionLabels addressSelectionTestLabels;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		AddressSelectionLabelsBean addressSelectionLabelsTestBean = new AddressSelectionLabelsBean();
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
		addressSelectionTestLabels = new AddressSelectionLabels(addressSelectionLabelsTestBean);
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertNotNull(addressSelectionTestLabels.getAddressLabels());
        Assert.assertEquals(addressSelectionTestLabels.getChangeLinkedAddressLinkLabel(),"ChangeLinkedAddressLinkLabel");
        Assert.assertEquals(addressSelectionTestLabels.getDefaultLabel(),"Default");
        Assert.assertEquals(addressSelectionTestLabels.getFavoriteShippingAddressText(),"FavoriteAddress");
		Assert.assertNotNull(addressSelectionTestLabels.getLinkedAddress());
		Assert.assertNotNull(addressSelectionTestLabels.getLocationIcon());
		Assert.assertNotNull(addressSelectionTestLabels.getLocationIconAltText());
		Assert.assertNotNull(addressSelectionTestLabels.getNoLinkedAddressText());
		Assert.assertNotNull(addressSelectionTestLabels.getSearchAddress());
		Assert.assertNotNull(addressSelectionTestLabels.getSearchAddressTitle());
		Assert.assertNotNull(addressSelectionTestLabels.getSearchPlaceHolder());
		Assert.assertNotNull(addressSelectionTestLabels.getSelectAddressCtaLabel());
		Assert.assertNotNull(addressSelectionTestLabels.getSelectAddressTitle());
		Assert.assertNotNull(addressSelectionTestLabels.getShippingAddressLabel());
		Assert.assertNotNull(addressSelectionTestLabels.getShipToNumberLabel());
	}
}
