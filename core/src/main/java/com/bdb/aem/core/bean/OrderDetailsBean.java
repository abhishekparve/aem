package com.bdb.aem.core.bean;

import com.google.gson.annotations.SerializedName;

/**
 * The Class OrderDetailsBean.
 */
public class OrderDetailsBean {

	/** The order details heading. */
	@SerializedName("orderDetailsHeading")
	private String orderDetailsHeading;

	/** The po number. */
	@SerializedName("poNumber")
	private String poNumber;

	/** The po date. */
	@SerializedName("poDate")
	private String poDate;

	/** The quoteReferenceNumber. */
	@SerializedName("quoteReferenceNumber")
	private String quoteReferenceNumber;
	
	/** The order placed by. */
	@SerializedName("orderPlacedBy")
	private String orderPlacedBy;

	/** The order status text. */
	@SerializedName("orderStatusText")
	private String orderStatusText;

	/** The source. */
	@SerializedName("source")
	private String source;

	/**
	 * Gets the order details heading.
	 *
	 * @return the order details heading
	 */
	public String getOrderDetailsHeading() {
		return orderDetailsHeading;
	}

	/**
	 * Sets the order details heading.
	 *
	 * @param orderDetailsHeading the new order details heading
	 */
	public void setOrderDetailsHeading(String orderDetailsHeading) {
		this.orderDetailsHeading = orderDetailsHeading;
	}

	/**
	 * Gets the po number.
	 *
	 * @return the po number
	 */
	public String getPoNumber() {
		return poNumber;
	}

	/**
	 * Sets the po number.
	 *
	 * @param poNumber the new po number
	 */
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	/**
	 * Gets the po date.
	 *
	 * @return the po date
	 */
	public String getPoDate() {
		return poDate;
	}

	/**
	 * Sets the po date.
	 *
	 * @param poDate the new po date
	 */
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	/**

	 * Gets the quotereferrence number.

	 *

	 * @return the quotereferrence number

	 */

	public String getQuotereferenceNumber() {

		return quoteReferenceNumber;

	}
	/**

	 * Sets the quotereferrence number.

	 *

	 * @param quoteReferenceNumber the new quotereferrence number

	 */

	public void setQuotereferenceNumber(String quoteReferenceNumber) {

		this.quoteReferenceNumber = quoteReferenceNumber;
	}
	/**
	 * Gets the order placed by.
	 *
	 * @return the order placed by
	 */
	public String getOrderPlacedBy() {
		return orderPlacedBy;
	}

	/**
	 * Sets the order placed by.
	 *
	 * @param orderPlacedBy the new order placed by
	 */
	public void setOrderPlacedBy(String orderPlacedBy) {
		this.orderPlacedBy = orderPlacedBy;
	}

	/**
	 * Gets the order status text.
	 *
	 * @return the order status text
	 */
	public String getOrderStatusText() {
		return orderStatusText;
	}

	/**
	 * Sets the order status text.
	 *
	 * @param orderStatusText the new order status text
	 */
	public void setOrderStatusText(String orderStatusText) {
		this.orderStatusText = orderStatusText;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(String source) {
		this.source = source;
	}

}
