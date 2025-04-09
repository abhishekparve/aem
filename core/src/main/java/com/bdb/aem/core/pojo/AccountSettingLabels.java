package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountSettingLabels.
 */
public class AccountSettingLabels
{
    /** the title */
    @SerializedName("title")
    private String title;

    /** the personalInfoLabels */
    @SerializedName("personalInfoLabels")
    private PersonalInfoLabels personalInfoLabels;
    
    /** the distributorInfoLabels */
    @SerializedName("distributorLabels")
    private DistributorInfoLabels distributorInfoLabels;

    /** the loginInfoLabels */
    @SerializedName("loginInfoLabels")
    private LoginInfoLabels loginInfoLabels;

    /** the accountPreferenceLabels */
    @SerializedName("accountPreferenceLabels")
    private AccountPreferenceLabels accountPreferenceLabels;
    
    /** the next Button Labels */
    @SerializedName("nextButtonLabel")
    private String  nextButtonLabel;

    /**
     * Instantiates a new password rule labels.
     *
     * @param  title
     * @param  personalInfoLabels
     * @param  loginInfoLabels
     * @param  accountPreferenceLabels
     * @param  nextButtonLabel
     */
    public AccountSettingLabels(String title,
                                PersonalInfoLabels personalInfoLabels,
                                DistributorInfoLabels distributorInfoLabels,
                                LoginInfoLabels loginInfoLabels,
                                AccountPreferenceLabels accountPreferenceLabels,
                                String nextButtonLabel)                              
    {
        this.title = title;
        this.personalInfoLabels = personalInfoLabels;
        this.distributorInfoLabels = distributorInfoLabels;
        this.loginInfoLabels = loginInfoLabels;
        this.accountPreferenceLabels = accountPreferenceLabels;
        this.nextButtonLabel = nextButtonLabel;
    }
}
