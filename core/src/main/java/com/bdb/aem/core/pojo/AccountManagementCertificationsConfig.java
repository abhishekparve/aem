package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountManagementCertificationsConfig.
 */
public class AccountManagementCertificationsConfig {
	
	/** The get users certificates config. */
	@SerializedName("getUsersCertificatesConfig")
	private JsonElement getUsersCertificatesConfig;
	
	/** The delete users certificates config. */
	@SerializedName("deleteUsersCertificatesConfig")
	private JsonElement deleteUsersCertificatesConfig;
	
	/** The upload certificates config. */
	@SerializedName("uploadCertificatesConfig")
	private JsonElement uploadCertificatesConfig;
	
	/** The get address config. */
	@SerializedName("getAddressConfig")
	private JsonElement getAddressConfig;
	
	/**
	 * Instantiates a new account management certifications config.
	 *
	 * @param getUsersCertificatesConfig the get users certificates config
	 * @param deleteUsersCertificatesConfig the delete users certificates config
	 * @param uploadCertificatesConfig the upload certificates config
	 * @param getAddressConfig the get address config
	 */
	public AccountManagementCertificationsConfig(JsonElement getUsersCertificatesConfig, JsonElement deleteUsersCertificatesConfig,
			JsonElement uploadCertificatesConfig, JsonElement getAddressConfig) {
		this.getUsersCertificatesConfig = getUsersCertificatesConfig;
		this.deleteUsersCertificatesConfig = deleteUsersCertificatesConfig;
		this.uploadCertificatesConfig = uploadCertificatesConfig;
		this.getAddressConfig = getAddressConfig;
	}

	/**
	 * Gets the gets the users certificates config.
	 *
	 * @return the gets the users certificates config
	 */
	public JsonElement getGetUsersCertificatesConfig() {
		return getUsersCertificatesConfig;
	}

	/**
	 * Gets the delete users certificates config.
	 *
	 * @return the delete users certificates config
	 */
	public JsonElement getDeleteUsersCertificatesConfig() {
		return deleteUsersCertificatesConfig;
	}

	/**
	 * Gets the upload certificates config.
	 *
	 * @return the upload certificates config
	 */
	public JsonElement getUploadCertificatesConfig() {
		return uploadCertificatesConfig;
	}
	
	/**
	 * Gets the gets the address config.
	 *
	 * @return the gets the address config
	 */
	public JsonElement getGetAddressConfig() {
		return getAddressConfig;
	}
}
