package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class HeaderCartConfig.
 */
public class HeaderCartConfig {
	
	
	/** The get cart with identifier. */
	@SerializedName("getCartWithIdentifier")
	private JsonElement getCartWithIdentifier;
	
	/** The cart url. */
	@SerializedName("urlCart")
	private String cartUrl;
	
	/** The cart alt. */
	@SerializedName("altcart")
	private String cartAlt;


	/**
	 * Instantiates a new header cart config.
	 *
	 * @param getCartWithIdentifier the get cart with identifier
	 * @param cartUrl the cart url
	 * @param cartAlt the cart alt
	 */
	public HeaderCartConfig(JsonElement getCartWithIdentifier, String cartUrl, String cartAlt) {
		super();
		this.getCartWithIdentifier = getCartWithIdentifier;
		this.cartUrl = cartUrl;
		this.cartAlt = cartAlt;
	}

	/**
	 * Gets the fetch address payload.
	 *
	 * @return the fetch address payload
	 */
	public JsonElement getFetchAddressPayload() {
		return getCartWithIdentifier;
	}

	/**
	 * Gets the cart url.
	 *
	 * @return the cart url
	 */
	public String getCartUrl() {
		return cartUrl;
	}

	/**
	 * Gets the cart alt.
	 *
	 * @return the cart alt
	 */
	public String getCartAlt() {
		return cartAlt;
	}
	
}