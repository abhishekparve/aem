package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class paymentOptionsLabelConfig.
 */
public class PaymentOptionsLabelConfig {
	
	/** The purchase order. */
	@SerializedName("purchaseOrder")
	private String purchaseOrder;
	
	/** The credit card. */
	@SerializedName("creditCard")
	private String creditCard;

	/**
	 * Instantiates a new payment options label config.
	 *
	 * @param purchaseOrder the purchase order
	 * @param creditCard the credit card
	 */
	public PaymentOptionsLabelConfig(String purchaseOrder, String creditCard) {
		super();
		this.purchaseOrder = purchaseOrder;
		this.creditCard = creditCard;
	}

	/**
	 * Gets the purchase order.
	 *
	 * @return the purchase order
	 */
	public String getPurchaseOrder() {
		return purchaseOrder;
	}

	/**
	 * Gets the credit card.
	 *
	 * @return the credit card
	 */
	public String getCreditCard() {
		return creditCard;
	}
}
