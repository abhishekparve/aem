package com.bdb.aem.core.pojo;

import com.bdb.aem.core.bean.AddressLabelsBean;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AddressLabelsConfig.
 */
public class AddressLabelsConfig {
	
	/** The company label. */
	@SerializedName("companyLabel")
	private String companyLabel;
	
	/** The address label first. */
	@SerializedName("addressLabelFirst")
	private String addressLabelFirst;
	
	/** The address label second. */
	@SerializedName("addressLabelSecond")
	private String addressLabelSecond;
	
	/** The street address label. */
	@SerializedName("streetAddressLabel")
	private String streetAddressLabel;
	
	/** The detail address label. */
	@SerializedName("detailAddressLabel")
	private String detailAddressLabel;
	
	/** The address label. */
	@SerializedName("addressLabel")
	private String addressLabel;
	
	/** The address label error. */
	@SerializedName("mandatoryFieldError")
	private String mandatoryFieldError;
	
	/** The building label. */
	@SerializedName("buildingLabel")
	private String buildingLabel;
	
	/** The floor label. */
	@SerializedName("floorLabel")
	private String floorLabel;

	/** The room label. */
	@SerializedName("roomLabel")
	private String roomLabel;

	/** The department label. */
	@SerializedName("departmentLabel")
	private String departmentLabel;
	
	/** The phone number label. */
	@SerializedName("phoneNumberLabel")
	private String phoneNumberLabel;

	/** The vat number label. */
	@SerializedName("vatNumberLabel")
	private String vatNumberLabel;
	
	/** The phone number label error. */
	@SerializedName("phoneNumberLabelError")
	private String phoneNumberLabelError;

	/** The vat number label error. */
	@SerializedName("vatNumberLabelError")
	private String vatNumberLabelError;
	
	/** The district label. */
	@SerializedName("districtLabel")
	private String districtLabel;
	
	/** The city label. */
	@SerializedName("cityLabel")
	private String cityLabel;
	
	/** The state label. */
	@SerializedName("stateLabel")
	private String stateLabel;
	
	/** The province label. */
	@SerializedName("provinceLabel")
	private String provinceLabel;
	
	/** The postal code label. */
	@SerializedName("postalCodeLabel")
	private String postalCodeLabel;
	
	/** The pin code label. */
	@SerializedName("pinCodeLabel")
	private String pinCodeLabel;
	
	/** The zip code label. */
	@SerializedName("zipCodeLabel")
	private String zipCodeLabel;
	
	/** The country label. */
	@SerializedName("countryLabel")
	private String countryLabel;

	/** The labNameLabel. */
	@SerializedName("labNameLabel")
	private String labNameLabel;

	/** The postalCodeLabelError. */
	@SerializedName("postalCodeLabelError")
	private String postalCodeLabelError;


	/**
	 * Instantiates a new address labels config.
	 *
	 * @param addressLabelsBean the address labels bean
	 */
	public AddressLabelsConfig(AddressLabelsBean addressLabelsBean) {
		this.companyLabel = addressLabelsBean.getCompanyLabel();
		this.addressLabelFirst = addressLabelsBean.getAddressLabelFirst();
		this.addressLabelSecond = addressLabelsBean.getAddressLabelSecond();
		this.streetAddressLabel = addressLabelsBean.getStreetAddressLabel();
		this.detailAddressLabel = addressLabelsBean.getDetailAddressLabel();
		this.addressLabel = addressLabelsBean.getAddressLabel();
		this.mandatoryFieldError = addressLabelsBean.getMandatoryFieldError();
		this.buildingLabel = addressLabelsBean.getBuildingLabel();
		this.floorLabel = addressLabelsBean.getFloorLabel();
		this.roomLabel = addressLabelsBean.getRoomLabel();
		this.departmentLabel = addressLabelsBean.getDepartmentLabel();
		this.phoneNumberLabel = addressLabelsBean.getPhoneNumberLabel();
		this.vatNumberLabel = addressLabelsBean.getVatNumberLabel();
		this.phoneNumberLabelError = addressLabelsBean.getPhoneNumberLabelError();
		this.vatNumberLabelError = addressLabelsBean.getVatNumberLabelError();
		this.districtLabel = addressLabelsBean.getDistrictLabel();
		this.cityLabel = addressLabelsBean.getCityLabel();
		this.stateLabel = addressLabelsBean.getStateLabel();
		this.provinceLabel = addressLabelsBean.getProvinceLabel();
		this.postalCodeLabel = addressLabelsBean.getPostalCodeLabel();
		this.pinCodeLabel = addressLabelsBean.getPinCodeLabel();
		this.zipCodeLabel = addressLabelsBean.getZipCodeLabel();
		this.countryLabel = addressLabelsBean.getCountryLabel();
		this.labNameLabel = addressLabelsBean.getLabNameLabel();
		this.postalCodeLabelError = addressLabelsBean.getPostalCodeLabelError();
	}

	/**
	 * Gets the company label.
	 *
	 * @return the company label
	 */
	public String getCompanyLabel() {
		return companyLabel;
	}

	/**
	 * Gets the address label first.
	 *
	 * @return the address label first
	 */
	public String getAddressLabelFirst() {
		return addressLabelFirst;
	}

	/**
	 * Gets the address label second.
	 *
	 * @return the address label second
	 */
	public String getAddressLabelSecond() {
		return addressLabelSecond;
	}

	/**
	 * Gets the street address label.
	 *
	 * @return the street address label
	 */
	public String getStreetAddressLabel() {
		return streetAddressLabel;
	}

	/**
	 * Gets the detail address label.
	 *
	 * @return the detail address label
	 */
	public String getDetailAddressLabel() {
		return detailAddressLabel;
	}

	/**
	 * Gets the address label.
	 *
	 * @return the address label
	 */
	public String getAddressLabel() {
		return addressLabel;
	}

	/**
	 * Gets the mandatory field error.
	 *
	 * @return the mandatory field error
	 */
	public String getMandatoryFieldError() {
		return mandatoryFieldError;
	}

	/**
	 * Gets the building label.
	 *
	 * @return the building label
	 */
	public String getBuildingLabel() {
		return buildingLabel;
	}

	/**
	 * Gets the floor label.
	 *
	 * @return the floor label
	 */
	public String getFloorLabel() {
		return floorLabel;
	}

	/**
	 * Gets the room label.
	 *
	 * @return the room label
	 */
	public String getRoomLabel() {
		return roomLabel;
	}

	/**
	 * Gets the department label.
	 *
	 * @return the department label
	 */
	public String getDepartmentLabel() {
		return departmentLabel;
	}

	/**
	 * Gets the phone number label.
	 *
	 * @return the phone number label
	 */
	public String getPhoneNumberLabel() {
		return phoneNumberLabel;
	}

	/**
	 * Gets the phone number label error.
	 *
	 * @return the phone number label error
	 */
	public String getPhoneNumberLabelError() {
		return phoneNumberLabelError;
	}

	/**
	 * Gets the vat number label.
	 *
	 * @return the vat number label
	 */
	public String getVatNumberLabel() {
		return vatNumberLabel;
	}

	/**
	 * Gets the vat number label error.
	 *
	 * @return the vat number label error
	 */
	public String getVatNumberLabelError() {
		return vatNumberLabelError;
	}

	/**
	 * Gets the district label.
	 *
	 * @return the district label
	 */
	public String getDistrictLabel() {
		return districtLabel;
	}

	/**
	 * Gets the city label.
	 *
	 * @return the city label
	 */
	public String getCityLabel() {
		return cityLabel;
	}

	/**
	 * Gets the state label.
	 *
	 * @return the state label
	 */
	public String getStateLabel() {
		return stateLabel;
	}

	/**
	 * Gets the province label.
	 *
	 * @return the province label
	 */
	public String getProvinceLabel() {
		return provinceLabel;
	}

	/**
	 * Gets the postal code label.
	 *
	 * @return the postal code label
	 */
	public String getPostalCodeLabel() {
		return postalCodeLabel;
	}

	/**
	 * Gets the pin code label.
	 *
	 * @return the pin code label
	 */
	public String getPinCodeLabel() {
		return pinCodeLabel;
	}

	/**
	 * Gets the zip code label.
	 *
	 * @return the zip code label
	 */
	public String getZipCodeLabel() {
		return zipCodeLabel;
	}

	/**
	 * Gets the country label.
	 *
	 * @return the country label
	 */
	public String getCountryLabel() {
		return countryLabel;
	}

	/**
	 * Gets the labNameLabel.
	 *
	 * @return the labNameLabel
	 */
	public String getLabNameLabel() {
		return labNameLabel;
	}

	/**
	 * Gets the postalCodeLabelError.
	 *
	 * @return the postalCodeLabelError
	 */
	public String getPostalCodeLabelError() {
		return postalCodeLabelError;
	}
}
