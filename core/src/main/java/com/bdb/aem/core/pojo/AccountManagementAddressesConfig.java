package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountManagementAddressesConfig.
 */
public class AccountManagementAddressesConfig {
	
	/** The fetch address payload. */
	@SerializedName("fetchAddressPayload")
	private JsonElement fetchAddressPayload;
	
	
	/** The add addr payload. */
	@SerializedName("addAddrPayload")
	private JsonElement addAddrPayload;
	
	/** The edit nick payload. */
	@SerializedName("editNickPayload")
	private JsonElement editNickPayload;
	
	/** The set default payload. */
	@SerializedName("setDefaultPayload")
	private JsonElement setDefaultPayload;
	
	/** The set favourite payload. */
	@SerializedName("setFavouritePayload")
	private JsonElement setFavouritePayload;
	
	/** The reactivate User Payload. */
	@SerializedName("reactivateUserPayload")
	private JsonElement reactivateUserPayload;

	/**
	 * Instantiates a new account management addresses config.
	 *
	 * @param fetchAddressPayload the fetch address payload
	 * @param addAddrPayload the add addr payload
	 * @param editNickPayload the edit nick payload
	 * @param setDefaultPayload the set default payload
	 * @param setFavouritePayload the set favourite payload
	 * @param reactivateUserPayload the reactivate User Payload
	 */
	public AccountManagementAddressesConfig(JsonElement fetchAddressPayload, JsonElement addAddrPayload,
			JsonElement editNickPayload, JsonElement setDefaultPayload,
			JsonElement setFavouritePayload, JsonElement reactivateUserPayload) {
		super();
		this.fetchAddressPayload = fetchAddressPayload;
		this.addAddrPayload = addAddrPayload;
		this.editNickPayload = editNickPayload;
		this.setDefaultPayload = setDefaultPayload;
		this.setFavouritePayload = setFavouritePayload;
		this.reactivateUserPayload = reactivateUserPayload;
	}

	/**
	 * Gets the fetch address payload.
	 *
	 * @return the fetch address payload
	 */
	public JsonElement getFetchAddressPayload() {
		return fetchAddressPayload;
	}

	/**
	 * Gets the adds the addr payload.
	 *
	 * @return the adds the addr payload
	 */
	public JsonElement getAddAddrPayload() {
		return addAddrPayload;
	}


	/**
	 * Gets the edits the nick payload.
	 *
	 * @return the edits the nick payload
	 */
	public JsonElement getEditNickPayload() {
		return editNickPayload;
	}

	/**
	 * Gets the sets the default payload.
	 *
	 * @return the sets the default payload
	 */
	public JsonElement getSetDefaultPayload() {
		return setDefaultPayload;
	}

	/**
	 * Gets the sets the favourite payload.
	 *
	 * @return the sets the favourite payload
	 */
	public JsonElement getSetFavouritePayload() {
		return setFavouritePayload;
	}
	
	/**
	 * Gets the reactivate User Payload.
	 *
	 * @return the reactivate User Payload
	 */
	public JsonElement getReactivateUserPayload() {
		return reactivateUserPayload;
	}
}
