package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountManagementCommunicationSettingsConfig.
 */
public class AccountManagementCommunicationSettingsConfig {
	
	/** The get notifications config. */
	@SerializedName("getNotificationsConfig")
	private JsonElement getNotificationsConfig;
	
	/** The update notification config. */
	@SerializedName("updateNotificationConfig")
	private JsonElement updateNotificationConfig;

	/**
	 * Instantiates a new account management communication settings config.
	 *
	 * @param getNotificationsConfig the get notifications config
	 * @param updateNotificationConfig the update notification config
	 */
	public AccountManagementCommunicationSettingsConfig(JsonElement getNotificationsConfig,
			JsonElement updateNotificationConfig) {
		this.getNotificationsConfig = getNotificationsConfig;
		this.updateNotificationConfig = updateNotificationConfig;
	}

	/**
	 * Gets the gets the notifications config.
	 *
	 * @return the gets the notifications config
	 */
	public JsonElement getGetNotificationsConfig() {
		return getNotificationsConfig;
	}

	/**
	 * Gets the update notification config.
	 *
	 * @return the update notification config
	 */
	public JsonElement getUpdateNotificationConfig() {
		return updateNotificationConfig;
	}
}
