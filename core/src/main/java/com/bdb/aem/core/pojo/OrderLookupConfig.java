package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class OrderLookupConfig.
 */
public class OrderLookupConfig {

	/** The search order lookup. */
	@SerializedName("searchOrderLookup")
	private JsonElement searchOrderLookup;

	/** The get order details. */
	@SerializedName("getOrderDetails")
	private JsonElement getOrderDetails;
	
	/** The tracking number config. */
	@SerializedName("trackingNumberConfig")
	private JsonElement trackingNumberConfig;
	
	/** The get order packing list json. */
	@SerializedName("getOrderPackingList")
	JsonElement getOrderPackingListJson;
	/**
	 * Instantiates a new order lookup config.
	 *
	 * @param searchOrderLookup    the search order lookup
	 * @param getOrderDetails      the get order details
	 */
	public OrderLookupConfig(JsonElement searchOrderLookup,
			JsonElement getOrderDetails, JsonElement getOrderPackingListJson) {
		this.searchOrderLookup = searchOrderLookup;
		this.getOrderDetails = getOrderDetails;
		this.getOrderPackingListJson = getOrderPackingListJson;
	}

	/**
	 * Gets the search order lookup.
	 *
	 * @return the search order lookup
	 */
	public JsonElement getSearchOrderLookup() {
		return searchOrderLookup;
	}

	/**
	 * Gets the gets the order details.
	 *
	 * @return the gets the order details
	 */
	public JsonElement getGetOrderDetails() {
		return getOrderDetails;
	}
	
	/**
	 * Gets the gets the order packing list json.
	 *
	 * @return the gets the order packing list json
	 */
	public JsonElement getGetOrderPackingListJson() {
		return getOrderPackingListJson;
	}

	/**
	 * Gets the tracking number config.
	 *
	 * @return the tracking number config
	 */
	public JsonElement getTrackingNumberConfig() {
		return trackingNumberConfig;
	}

	/**
	 * Sets the tracking number config.
	 *
	 * @param trackingNumberConfig the new tracking number config
	 */
	public void setTrackingNumberConfig(JsonElement trackingNumberConfig) {
		this.trackingNumberConfig = trackingNumberConfig;
	}
}
