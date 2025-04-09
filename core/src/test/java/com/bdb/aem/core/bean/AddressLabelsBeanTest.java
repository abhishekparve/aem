package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class AddressLabelsBeanTest.
 */
class AddressLabelsBeanTest {
	
	/** The address labels bean. */
	AddressLabelsBean addressLabelsBean;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		addressLabelsBean = new AddressLabelsBean();
		addressLabelsBean.setCompanyLabel("companyLabel");
		addressLabelsBean.setAddressLabelFirst("addressLabelFirst");
		addressLabelsBean.setAddressLabelSecond("addressLabelSecond");
		addressLabelsBean.setStreetAddressLabel("streetAddressLabel");
		addressLabelsBean.setDetailAddressLabel("detailAddressLabel");
		addressLabelsBean.setAddressLabel("addressLabel");
		addressLabelsBean.setMandatoryFieldError("mandatoryFieldError");
		addressLabelsBean.setBuildingLabel("buildingLabel");
		addressLabelsBean.setFloorLabel("floorLabel");
		addressLabelsBean.setRoomLabel("roomLabel");
		addressLabelsBean.setDepartmentLabel("departmentLabel");
		addressLabelsBean.setPhoneNumberLabel("phoneNumberLabel");
		addressLabelsBean.setPhoneNumberLabelError("phoneNumberLabelError");
		addressLabelsBean.setDistrictLabel("districtLabel");
		addressLabelsBean.setCityLabel("cityLabel");
		addressLabelsBean.setStateLabel("stateLabel");
		addressLabelsBean.setProvinceLabel("provinceLabel");
		addressLabelsBean.setPostalCodeLabel("postalCodeLabel");
		addressLabelsBean.setPinCodeLabel("pinCodeLabel");
		addressLabelsBean.setZipCodeLabel("zipCodeLabel");
		addressLabelsBean.setCountryLabel("countryLabel");		
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(addressLabelsBean.getAddressLabel(), "addressLabel");
		Assert.assertEquals(addressLabelsBean.getCompanyLabel(),"companyLabel");
		Assert.assertNotNull(addressLabelsBean.getAddressLabelFirst());
		Assert.assertNotNull(addressLabelsBean.getAddressLabelSecond());
		Assert.assertNotNull(addressLabelsBean.getBuildingLabel());		
		Assert.assertNotNull(addressLabelsBean.getCityLabel());
		Assert.assertNotNull(addressLabelsBean.getCountryLabel());
		Assert.assertNotNull(addressLabelsBean.getDepartmentLabel());
		Assert.assertNotNull(addressLabelsBean.getDetailAddressLabel());
		Assert.assertNotNull(addressLabelsBean.getDistrictLabel());
		Assert.assertNotNull(addressLabelsBean.getFloorLabel());
		Assert.assertNotNull(addressLabelsBean.getMandatoryFieldError());
		Assert.assertNotNull(addressLabelsBean.getPhoneNumberLabel());
		Assert.assertNotNull(addressLabelsBean.getPhoneNumberLabelError());
		Assert.assertNotNull(addressLabelsBean.getPinCodeLabel());
		Assert.assertNotNull(addressLabelsBean.getZipCodeLabel());
		Assert.assertNotNull(addressLabelsBean.getPostalCodeLabel());
		Assert.assertNotNull(addressLabelsBean.getProvinceLabel());
		Assert.assertNotNull(addressLabelsBean.getRoomLabel());
		Assert.assertNotNull(addressLabelsBean.getStateLabel());
		Assert.assertNotNull(addressLabelsBean.getStreetAddressLabel());		
	}

}
