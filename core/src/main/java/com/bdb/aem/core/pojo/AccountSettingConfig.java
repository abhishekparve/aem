package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;
/**
 * The Class AccountSettingConfig.
 */
public class AccountSettingConfig {

    /** the getUserSettingsConfig */
    @SerializedName("getUserSettingsConfig")
    private PayloadConfig getUserSettingsConfig;

    /** the updateUserDetailsConfig */
    @SerializedName("updateUserDetailsConfig")
    private PayloadConfig updateUserDetailsConfig;

    /** the updateUserPwdConfig */
    @SerializedName("updateUserPwdConfig")
    private PayloadConfig updateUserPwdConfig;


    public AccountSettingConfig(PayloadConfig getUserSettingsConfig, PayloadConfig updateUserDetailsConfig, PayloadConfig updateUserPwdConfig) {
        this.getUserSettingsConfig = getUserSettingsConfig;
        this.updateUserDetailsConfig = updateUserDetailsConfig;
        this.updateUserPwdConfig = updateUserPwdConfig;
    }


}
