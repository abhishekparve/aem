package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountManagementOrdersApprovalConfig.
 */
public class AccountManagementOrdersApprovalConfig {
	
	/** The get order approval list. */
	@SerializedName("getOrderApprovalList")
	private JsonElement getOrderApprovalList;
	
	/** The get order approval details. */
	@SerializedName("getOrderApprovalDetails")
	private JsonElement getOrderApprovalDetails;
	
	/** The update order approval status. */
	@SerializedName("updateOrderApprovalStatus")
	private JsonElement updateOrderApprovalStatus;
	
	/** The update order approval po number. */
	@SerializedName("updateOrderApprovalPoNumber")
	private JsonElement updateOrderApprovalPoNumber;

	/**
	 * Instantiates a new account management orders approval config.
	 *
	 * @param getOrderApprovalList the get order approval list
	 * @param getOrderApprovalDetails the get order approval details
	 * @param updateOrderApprovalStatus the update order approval status
	 * @param updateOrderApprovalPoNumber the update order approval po number
	 */
	public AccountManagementOrdersApprovalConfig(JsonElement getOrderApprovalList, JsonElement getOrderApprovalDetails,
			JsonElement updateOrderApprovalStatus, JsonElement updateOrderApprovalPoNumber) {
		this.getOrderApprovalList = getOrderApprovalList;
		this.getOrderApprovalDetails = getOrderApprovalDetails;
		this.updateOrderApprovalStatus = updateOrderApprovalStatus;
		this.updateOrderApprovalPoNumber = updateOrderApprovalPoNumber;
	}

	/**
	 * Gets the gets the order approval list.
	 *
	 * @return the gets the order approval list
	 */
	public JsonElement getGetOrderApprovalList() {
		return getOrderApprovalList;
	}

	/**
	 * Gets the gets the order approval details.
	 *
	 * @return the gets the order approval details
	 */
	public JsonElement getGetOrderApprovalDetails() {
		return getOrderApprovalDetails;
	}

	/**
	 * Gets the update order approval status.
	 *
	 * @return the update order approval status
	 */
	public JsonElement getUpdateOrderApprovalStatus() {
		return updateOrderApprovalStatus;
	}

	/**
	 * Gets the update order approval po number.
	 *
	 * @return the update order approval po number
	 */
	public JsonElement getUpdateOrderApprovalPoNumber() {
		return updateOrderApprovalPoNumber;
	}
}
