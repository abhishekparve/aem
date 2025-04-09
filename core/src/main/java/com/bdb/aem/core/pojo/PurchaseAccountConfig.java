package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class PurchaseAccountConfig {
	 
	 /** The purchase account submit. */
 	@SerializedName("purchaseAccountSubmit")
	 private JsonElement purchaseAccountSubmit;
	 
	 /** The upload tax certificate config. */
 	@SerializedName("uploadTaxCertificateConfig")
	 private JsonElement uploadTaxCertificateConfig;
 	
 	/** The get states payload. */
	@SerializedName("getStatesPayload")
 	private JsonElement getStatesPayload;
	 
	/** The get countries payload. */
	@SerializedName("getCountriesPayload")
	private JsonElement getCountriesPayload;

	/** The getDistributersOptions. */
	@SerializedName("getDistributersOptions")
	private JsonElement getDistributersOptions;

	/** The postSmartCartRegister. */
	@SerializedName("postSmartCartRegister")
	private JsonElement postSmartCartRegister;

	/**
	 * Instantiates a new purchase account config.
	 *
	 * @param purchaseAccountSubmit the purchase account submit
	 * @param uploadTaxCertificateConfig the upload tax certificate config
	 * @param getStatesPayload the get states payload
	 * @param getCountriesPayload the get countries payload
	 */
	public PurchaseAccountConfig(
			JsonElement purchaseAccountSubmit,
			JsonElement uploadTaxCertificateConfig,
			JsonElement getStatesPayload,
			JsonElement getCountriesPayload,
			JsonElement getDistributersOptionsPayload,
			JsonElement postSmartCartRegisterPayload) {
		this.purchaseAccountSubmit = purchaseAccountSubmit;
		this.uploadTaxCertificateConfig = uploadTaxCertificateConfig;
		this.getStatesPayload = getStatesPayload;
		this.getCountriesPayload = getCountriesPayload;
		this.getDistributersOptions = getDistributersOptionsPayload;
		this.postSmartCartRegister = postSmartCartRegisterPayload;
	}

	/**
	 * Gets the purchase account submit.
	 *
	 * @return the purchase account submit
	 */
	public JsonElement getPurchaseAccountSubmit() {
		return purchaseAccountSubmit;
	}

	/**
	 * Gets the upload tax certificate config.
	 *
	 * @return the upload tax certificate config
	 */
	public JsonElement getUploadTaxCertificateConfig() {
		return uploadTaxCertificateConfig;
	}

	/**
	 * Gets the gets the states payload.
	 *
	 * @return the gets the states payload
	 */
	public JsonElement getGetStatesPayload() {
		return getStatesPayload;
	}

	/**
	 * Gets the gets the countries payload.
	 *
	 * @return the gets the countries payload
	 */
	public JsonElement getGetCountriesPayload() {
		return getCountriesPayload;
	}

	/**
	 * Gets the getDistributersOptionsPayload.
	 *
	 * @return the getDistributersOptionsPayload
	 */
	public JsonElement getGetDistributersOptionsPayload() {
		return getDistributersOptions;
	}

	/**
	 * Gets the getDistributersOptionsPayload
	 *
	 * @return the getDistributersOptionsPayload
	 */
	public JsonElement getPostSmartCartRegisterPayload() {
		return postSmartCartRegister;
	}
}
