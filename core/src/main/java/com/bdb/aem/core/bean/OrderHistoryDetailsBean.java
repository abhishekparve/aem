package com.bdb.aem.core.bean;

import com.google.gson.annotations.SerializedName;

/**
 * The Class OrderHistoryDetailsBean.
 */
public class OrderHistoryDetailsBean {

	/** The address details. */
	@SerializedName("addressDetails")
	private AddressDetailsBean addressDetails;

	/** The order details. */
	@SerializedName("orderDetails")
	private OrderDetailsBean orderDetails;

	/** The products. */
	@SerializedName("products")
	private ProductsOrderLookupBean products;

	/** The shipments labels. */
	@SerializedName("shipmentsLabels")
	private ShipmentsLabelsBean shipmentsLabels;

	/** The special instructions. */
	@SerializedName("specialInstructions")
	private SpecialInstructionsBean specialInstructions;

	/** The orders number heading. */
	@SerializedName("ordersNumberHeading")
	private String ordersNumberHeading;

	/** The order issue text. */
	@SerializedName("orderIssueText")
	private String orderIssueText;

	/** The contact us label. */
	@SerializedName("contactUsLabel")
	private String contactUsLabel;

	/** The back to search label. */
	@SerializedName("backToSearchLabel")
	private String backToSearchLabel;

	/** The contact us link. */
	@SerializedName("contactUsLink")
	private String contactUsLink;

	/**
	 * Gets the address details.
	 *
	 * @return the address details
	 */
	public AddressDetailsBean getAddressDetails() {
		return addressDetails;
	}

	/**
	 * Sets the address details.
	 *
	 * @param addressDetails the new address details
	 */
	public void setAddressDetails(AddressDetailsBean addressDetails) {
		this.addressDetails = addressDetails;
	}

	/**
	 * Gets the order details.
	 *
	 * @return the order details
	 */
	public OrderDetailsBean getOrderDetails() {
		return orderDetails;
	}

	/**
	 * Sets the order details.
	 *
	 * @param orderDetails the new order details
	 */
	public void setOrderDetails(OrderDetailsBean orderDetails) {
		this.orderDetails = orderDetails;
	}

	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public ProductsOrderLookupBean getProducts() {
		return products;
	}

	/**
	 * Sets the products.
	 *
	 * @param products the new products
	 */
	public void setProducts(ProductsOrderLookupBean products) {
		this.products = products;
	}

	/**
	 * Gets the shipments labels.
	 *
	 * @return the shipments labels
	 */
	public ShipmentsLabelsBean getShipmentsLabels() {
		return shipmentsLabels;
	}

	/**
	 * Sets the shipments labels.
	 *
	 * @param shipmentsLabels the new shipments labels
	 */
	public void setShipmentsLabels(ShipmentsLabelsBean shipmentsLabels) {
		this.shipmentsLabels = shipmentsLabels;
	}

	/**
	 * Gets the special instructions.
	 *
	 * @return the special instructions
	 */
	public SpecialInstructionsBean getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * Sets the special instructions.
	 *
	 * @param specialInstructions the new special instructions
	 */
	public void setSpecialInstructions(SpecialInstructionsBean specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	/**
	 * Gets the orders number heading.
	 *
	 * @return the orders number heading
	 */
	public String getOrdersNumberHeading() {
		return ordersNumberHeading;
	}

	/**
	 * Sets the orders number heading.
	 *
	 * @param ordersNumberHeading the new orders number heading
	 */
	public void setOrdersNumberHeading(String ordersNumberHeading) {
		this.ordersNumberHeading = ordersNumberHeading;
	}

	/**
	 * Gets the order issue text.
	 *
	 * @return the order issue text
	 */
	public String getOrderIssueText() {
		return orderIssueText;
	}

	/**
	 * Sets the order issue text.
	 *
	 * @param orderIssueText the new order issue text
	 */
	public void setOrderIssueText(String orderIssueText) {
		this.orderIssueText = orderIssueText;
	}

	/**
	 * Gets the contact us label.
	 *
	 * @return the contact us label
	 */
	public String getContactUsLabel() {
		return contactUsLabel;
	}

	/**
	 * Sets the contact us label.
	 *
	 * @param contactUsLabel the new contact us label
	 */
	public void setContactUsLabel(String contactUsLabel) {
		this.contactUsLabel = contactUsLabel;
	}

	/**
	 * Gets the back to search label.
	 *
	 * @return the back to search label
	 */
	public String getBackToSearchLabel() {
		return backToSearchLabel;
	}

	/**
	 * Sets the back to search label.
	 *
	 * @param backToSearchLabel the new back to search label
	 */
	public void setBackToSearchLabel(String backToSearchLabel) {
		this.backToSearchLabel = backToSearchLabel;
	}

	/**
	 * Gets the contact us link.
	 *
	 * @return the contact us link
	 */
	public String getContactUsLink() {
		return contactUsLink;
	}

	/**
	 * Sets the contact us link.
	 *
	 * @param contactUsLink the new contact us link
	 */
	public void setContactUsLink(String contactUsLink) {
		this.contactUsLink = contactUsLink;
	}

}
