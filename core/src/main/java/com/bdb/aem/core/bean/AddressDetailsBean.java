package com.bdb.aem.core.bean;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AddressDetailsBean.
 */
public class AddressDetailsBean {

	/** The address details heading. */
	@SerializedName("addressDetailsHeading")
	private String addressDetailsHeading;

	/** The ship to label. */
	@SerializedName("shipToLabel")
	private String shipToLabel;

	/** The bill to label. */
	@SerializedName("billToLabel")
	private String billToLabel;

	/** The sold to label. */
	@SerializedName("soldToLabel")
	private String soldToLabel;

	/** The shipto number label. */
	@SerializedName("shiptoNumberLabel")
	private String shiptoNumberLabel;

	/** The billto number label. */
	@SerializedName("billtoNumberLabel")
	private String billtoNumberLabel;

	/** The soldto number label. */
	@SerializedName("soldtoNumberLabel")
	private String soldtoNumberLabel;

	/**
	 * Gets the address details heading.
	 *
	 * @return the address details heading
	 */
	public String getAddressDetailsHeading() {
		return addressDetailsHeading;
	}

	/**
	 * Sets the address details heading.
	 *
	 * @param addressDetailsHeading the new address details heading
	 */
	public void setAddressDetailsHeading(String addressDetailsHeading) {
		this.addressDetailsHeading = addressDetailsHeading;
	}

	/**
	 * Gets the ship to label.
	 *
	 * @return the ship to label
	 */
	public String getShipToLabel() {
		return shipToLabel;
	}

	/**
	 * Sets the ship to label.
	 *
	 * @param shipToLabel the new ship to label
	 */
	public void setShipToLabel(String shipToLabel) {
		this.shipToLabel = shipToLabel;
	}

	/**
	 * Gets the bill to label.
	 *
	 * @return the bill to label
	 */
	public String getBillToLabel() {
		return billToLabel;
	}

	/**
	 * Sets the bill to label.
	 *
	 * @param billToLabel the new bill to label
	 */
	public void setBillToLabel(String billToLabel) {
		this.billToLabel = billToLabel;
	}

	/**
	 * Gets the sold to label.
	 *
	 * @return the sold to label
	 */
	public String getSoldToLabel() {
		return soldToLabel;
	}

	/**
	 * Sets the sold to label.
	 *
	 * @param soldToLabel the new sold to label
	 */
	public void setSoldToLabel(String soldToLabel) {
		this.soldToLabel = soldToLabel;
	}

	/**
	 * Gets the shipto number label.
	 *
	 * @return the shipto number label
	 */
	public String getShiptoNumberLabel() {
		return shiptoNumberLabel;
	}

	/**
	 * Sets the shipto number label.
	 *
	 * @param shiptoNumberLabel the new shipto number label
	 */
	public void setShiptoNumberLabel(String shiptoNumberLabel) {
		this.shiptoNumberLabel = shiptoNumberLabel;
	}

	/**
	 * Gets the billto number label.
	 *
	 * @return the billto number label
	 */
	public String getBilltoNumberLabel() {
		return billtoNumberLabel;
	}

	/**
	 * Sets the billto number label.
	 *
	 * @param billtoNumberLabel the new billto number label
	 */
	public void setBilltoNumberLabel(String billtoNumberLabel) {
		this.billtoNumberLabel = billtoNumberLabel;
	}

	/**
	 * Gets the soldto number label.
	 *
	 * @return the soldto number label
	 */
	public String getSoldtoNumberLabel() {
		return soldtoNumberLabel;
	}

	/**
	 * Sets the soldto number label.
	 *
	 * @param soldtoNumberLabel the new soldto number label
	 */
	public void setSoldtoNumberLabel(String soldtoNumberLabel) {
		this.soldtoNumberLabel = soldtoNumberLabel;
	}

}
