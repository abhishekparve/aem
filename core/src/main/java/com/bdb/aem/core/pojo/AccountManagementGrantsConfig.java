package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountManagementGrantsConfig.
 */
public class AccountManagementGrantsConfig {
	
	/** The get grants. */
	@SerializedName("getGrants")
	private JsonElement getGrants;
	
	/** The get order for grants. */
	@SerializedName("getOrderForGrants")
	private JsonElement getOrderForGrants;

	/**
	 * Instantiates a new account management grants config.
	 *
	 * @param getGrants the get grants
	 * @param getOrderForGrants the get order for grants
	 */
	public AccountManagementGrantsConfig(JsonElement getGrants, JsonElement getOrderForGrants) {
		this.getGrants = getGrants;
		this.getOrderForGrants = getOrderForGrants;
	}

	/**
	 * Gets the gets the grants.
	 *
	 * @return the gets the grants
	 */
	public JsonElement getGetGrants() {
		return getGrants;
	}

	/**
	 * Gets the gets the order for grants.
	 *
	 * @return the gets the order for grants
	 */
	public JsonElement getGetOrderForGrants() {
		return getOrderForGrants;
	}
}
