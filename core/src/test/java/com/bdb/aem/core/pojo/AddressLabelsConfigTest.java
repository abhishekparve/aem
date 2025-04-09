package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bdb.aem.core.bean.AddressLabelsBean;

import junitx.framework.Assert;

/**
 * The Class AddressLabelsConfigTest.
 */
class AddressLabelsConfigTest {
	
	/** The address test config. */
	AddressLabelsConfig addressTestConfig;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		AddressLabelsBean addressLabelsBean = new AddressLabelsBean();
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
		addressTestConfig = new AddressLabelsConfig(addressLabelsBean);
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(addressTestConfig.getAddressLabel(), "addressLabel");
		Assert.assertEquals(addressTestConfig.getCompanyLabel(),"companyLabel");
		Assert.assertNotNull(addressTestConfig.getAddressLabelFirst());
		Assert.assertNotNull(addressTestConfig.getAddressLabelSecond());
		Assert.assertNotNull(addressTestConfig.getBuildingLabel());		
		Assert.assertNotNull(addressTestConfig.getCityLabel());
		Assert.assertNotNull(addressTestConfig.getCountryLabel());
		Assert.assertNotNull(addressTestConfig.getDepartmentLabel());
		Assert.assertNotNull(addressTestConfig.getDetailAddressLabel());
		Assert.assertNotNull(addressTestConfig.getDistrictLabel());
		Assert.assertNotNull(addressTestConfig.getFloorLabel());
		Assert.assertNotNull(addressTestConfig.getMandatoryFieldError());
		Assert.assertNotNull(addressTestConfig.getPhoneNumberLabel());
		Assert.assertNotNull(addressTestConfig.getPhoneNumberLabelError());
		Assert.assertNotNull(addressTestConfig.getPinCodeLabel());
		Assert.assertNotNull(addressTestConfig.getZipCodeLabel());
		Assert.assertNotNull(addressTestConfig.getPostalCodeLabel());
		Assert.assertNotNull(addressTestConfig.getProvinceLabel());
		Assert.assertNotNull(addressTestConfig.getRoomLabel());
		Assert.assertNotNull(addressTestConfig.getStateLabel());
		Assert.assertNotNull(addressTestConfig.getStreetAddressLabel());		
	}
}
