package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountManagementOrdersConfig.
 */
public class AccountManagementOrdersConfig {

	/** The get user orders list json. */
	@SerializedName("getUserOrdersListJson")
	JsonElement getUserOrdersListJson;

	/** The get order details json. */
	@SerializedName("getOrderDetails")
	JsonElement getOrderDetailsJson;

	/** The get order packing list json. */
	@SerializedName("getOrderPackingList")
	JsonElement getOrderPackingListJson;

	/** The trigger re order json. */
	@SerializedName("triggerReOrder")
	JsonElement triggerReOrderJson;

	/** The fetchAddressPayload. */
	@SerializedName("fetchAddressPayload")
	JsonElement fetchAddressPayload;

	/** The myOrdersList. */
	@SerializedName("myOrdersList")
	JsonElement myOrdersList;

	/** The allOrdersList. */
	@SerializedName("allOrdersList")
	JsonElement allOrdersList;
	
	/** The cancel order json. */
	@SerializedName("cancelOrder")
	JsonElement cancelOrderJson;
	
	@SerializedName("trackingNumberConfig")
	JsonElement trackingNumberConfig;
	
	/**
	 * Instantiates a new account management orders config.
	 *
	 * @param getUserOrdersListJson the get user orders list json
	 * @param getOrderDetailsJson the get order details json
	 * @param getOrderPackingListJson the get order packing list json
	 * @param triggerReOrderJson the trigger re order json
	 * @param fetchAddressPayload the fetch address payload
	 * @param myOrdersList the my orders list
	 */
	public AccountManagementOrdersConfig(JsonElement getUserOrdersListJson, JsonElement getOrderDetailsJson,
			JsonElement getOrderPackingListJson, JsonElement triggerReOrderJson,
			JsonElement fetchAddressPayload, JsonElement myOrdersList) {
		this.getUserOrdersListJson = getUserOrdersListJson;
		this.getOrderDetailsJson = getOrderDetailsJson;
		this.getOrderPackingListJson = getOrderPackingListJson;
		this.triggerReOrderJson = triggerReOrderJson;
		this.fetchAddressPayload = fetchAddressPayload;
		this.myOrdersList = myOrdersList;
	}

	/**
	 * Gets the trigger re order json.
	 *
	 * @return the trigger re order json
	 */
	public JsonElement getTriggerReOrderJson() {
		return triggerReOrderJson;
	}

	/**
	 * Gets the gets the user orders list json.
	 *
	 * @return the gets the user orders list json
	 */
	public JsonElement getGetUserOrdersListJson() {
		return getUserOrdersListJson;
	}

	/**
	 * Gets the gets the order details json.
	 *
	 * @return the gets the order details json
	 */
	public JsonElement getGetOrderDetailsJson() {
		return getOrderDetailsJson;
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
	 * Gets the gets the fetchAddressPayload.
	 *
	 * @return the gets the fetchAddressPayload
	 */
	public JsonElement getFetchAddressPayload() {
		return fetchAddressPayload;
	}

	/**
	 * Gets the gets the myOrdersList.
	 *
	 * @return the gets the myOrdersList
	 */
	public JsonElement getMyOrdersList() {
		return myOrdersList;
	}

	/**
	 * Gets the gets the allOrdersList.
	 *
	 * @return the gets the allOrdersList
	 */
	public JsonElement getAllOrdersList() {
		return allOrdersList;
	}

	/**
	 * Sets the all orders list.
	 *
	 * @param allOrdersList the new all orders list
	 */
	public void setAllOrdersList(JsonElement allOrdersList) {
		this.allOrdersList = allOrdersList;
	}

	/**
	 * Gets the cancel order json.
	 *
	 * @return the cancel order json
	 */
	public JsonElement getCancelOrderJson() {
		return cancelOrderJson;
	}
	
	/**
	 * Sets the cancel order json.
	 *
	 * @param cancelOrderJson the new cancel order json
	 */
	public void setCancelOrderJson(JsonElement cancelOrderJson) {
		this.cancelOrderJson = cancelOrderJson;
	}

	public JsonElement getTrackingNumberConfig() {
		return trackingNumberConfig;
	}

	public void setTrackingNumberConfig(JsonElement trackingNumberConfig) {
		this.trackingNumberConfig = trackingNumberConfig;
	}
}
