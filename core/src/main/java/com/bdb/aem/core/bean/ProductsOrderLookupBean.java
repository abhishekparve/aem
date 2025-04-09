package com.bdb.aem.core.bean;

import com.google.gson.annotations.SerializedName;

/**
 * The Class ProductsOrderLookupBean.
 */
public class ProductsOrderLookupBean {

	/** The products heading. */
	@SerializedName("productsHeading")
	private String productsHeading;

	/** The est delivery date. */
	@SerializedName("estDeliveryDate")
	private String estDeliveryDate;

	/** The quantity label products. */
	@SerializedName("quantityLabelProducts")
	private String quantityLabelProducts;

	/** The default PRD image. */
	@SerializedName("defaultPRDImage")
	private String defaultPRDImage;

	/** The default PRD image alt text. */
	@SerializedName("defaultPRDImageAltText")
	private String defaultPRDImageAltText;
	
	/** The you saved label. */
	@SerializedName("youSavedLabel")
	private String youSavedLabel;
	
	/** The on label. */
	@SerializedName("onLabel")
	private String onLabel;
	
	/** The promocode label. */
	@SerializedName("promocodeLabel")
	private String promocodeLabel;

	/**
	 * Gets the products heading.
	 *
	 * @return the products heading
	 */
	public String getProductsHeading() {
		return productsHeading;
	}

	/**
	 * Sets the products heading.
	 *
	 * @param productsHeading the new products heading
	 */
	public void setProductsHeading(String productsHeading) {
		this.productsHeading = productsHeading;
	}

	/**
	 * Gets the est delivery date.
	 *
	 * @return the est delivery date
	 */
	public String getEstDeliveryDate() {
		return estDeliveryDate;
	}

	/**
	 * Sets the est delivery date.
	 *
	 * @param estDeliveryDate the new est delivery date
	 */
	public void setEstDeliveryDate(String estDeliveryDate) {
		this.estDeliveryDate = estDeliveryDate;
	}

	/**
	 * Gets the quantity label products.
	 *
	 * @return the quantity label products
	 */
	public String getQuantityLabelProducts() {
		return quantityLabelProducts;
	}

	/**
	 * Sets the quantity label products.
	 *
	 * @param quantityLabelProducts the new quantity label products
	 */
	public void setQuantityLabelProducts(String quantityLabelProducts) {
		this.quantityLabelProducts = quantityLabelProducts;
	}

	/**
	 * Gets the default PRD image.
	 *
	 * @return the default PRD image
	 */
	public String getDefaultPRDImage() {
		return defaultPRDImage;
	}

	/**
	 * Sets the default PRD image.
	 *
	 * @param defaultPRDImage the new default PRD image
	 */
	public void setDefaultPRDImage(String defaultPRDImage) {
		this.defaultPRDImage = defaultPRDImage;
	}

	/**
	 * Gets the default PRD image alt text.
	 *
	 * @return the default PRD image alt text
	 */
	public String getDefaultPRDImageAltText() {
		return defaultPRDImageAltText;
	}

	/**
	 * Sets the default PRD image alt text.
	 *
	 * @param defaultPRDImageAltText the new default PRD image alt text
	 */
	public void setDefaultPRDImageAltText(String defaultPRDImageAltText) {
		this.defaultPRDImageAltText = defaultPRDImageAltText;
	}

	/**
	 * Gets the you saved label.
	 *
	 * @return the you saved label
	 */
	public String getYouSavedLabel() {
		return youSavedLabel;
	}

	/**
	 * Sets the you saved label.
	 *
	 * @param youSavedLabel the new you saved label
	 */
	public void setYouSavedLabel(String youSavedLabel) {
		this.youSavedLabel = youSavedLabel;
	}

	/**
	 * Gets the on label.
	 *
	 * @return the on label
	 */
	public String getOnLabel() {
		return onLabel;
	}

	/**
	 * Sets the on label.
	 *
	 * @param onLabel the new on label
	 */
	public void setOnLabel(String onLabel) {
		this.onLabel = onLabel;
	}
	
	/**
	 * Gets the promocode label.
	 *
	 * @return the promocode label
	 */
	public String getPromocodeLabel() {
		return promocodeLabel;
	}

	/**
	 * Sets the promocode label.
	 *
	 * @param promocodeLabel the new promocode label
	 */
	public void setPromocodeLabel(String promocodeLabel) {
		this.promocodeLabel = promocodeLabel;
	}
}
