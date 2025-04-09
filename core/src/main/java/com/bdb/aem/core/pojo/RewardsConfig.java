package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class RewardsConfig.
 */
public class RewardsConfig {

    /**
     * The get updateRewardsPreferences config.
     */
    @SerializedName("updateRewardsPreferences")
    private PayloadConfig updateRewardsPreferences;

    @SerializedName("siteId")
    private String siteId;

    @SerializedName("summaryTabSrc")
    private String summaryTabSrc;

    @SerializedName("rewardsTabSrc")
    private String rewardsTabSrc;

    @SerializedName("earnTabSrc")
    private String earnTabSrc;

    @SerializedName("activityTabSrc")
    private String activityTabSrc; 

    @SerializedName("annexJs")
    private String annexJs; 

	/**
     * Instantiates a new RewardsConfig
     *
     * @param updateRewardsPreferences the get updateRewardsPreferences config
     * @param siteId                   the update siteId config
     * @param summaryTabSrc                   the update summaryTabSrc config
     * @param rewardsTabSrc                   the update rewardsTabSrc config
     * @param earnTabSrc                   the update earnTabSrc config
     * @param activityTabSrc                   the update activityTabSrc config
     */
    public RewardsConfig(PayloadConfig updateRewardsPreferences,
                         String siteId,
                         String summaryTabSrc,
                         String rewardsTabSrc,
                         String earnTabSrc,
                         String activityTabSrc,
                         String annexJs) {
        this.updateRewardsPreferences = updateRewardsPreferences;
        this.siteId = siteId;
        this.summaryTabSrc = summaryTabSrc;
        this.rewardsTabSrc = rewardsTabSrc;
        this.earnTabSrc = earnTabSrc;
        this.activityTabSrc = activityTabSrc;
        this.annexJs=annexJs;
    }

    /**
     * Gets the gets the updateRewardsPreferences config.
     *
     * @return the gets the updateRewardsPreferences config
     */
    public PayloadConfig getUpdateRewardsPreferences() {
        return updateRewardsPreferences;
    }

    /**
     * Gets the annexJs config.
     *
     * @return the gets the getAnnexJs config
     */
    public String getAnnexJs() {
		return annexJs;
	}
    /**
     * Gets the gets the siteId config.
     *
     * @return the gets the siteId config
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * Gets the gets the summaryTabSrc config.
     *
     * @return the gets the summaryTabSrc config
     */
    public String getSummaryTabSrc() {
        return summaryTabSrc;
    }

    /**
     * Gets the gets the rewardsTabSrc config.
     *
     * @return the gets the rewardsTabSrc config
     */
    public String getRewardsTabSrc() {
        return rewardsTabSrc;
    }

    /**
     * Gets the gets the earnTabSrc config.
     *
     * @return the gets the earnTabSrc config
     */
    public String getEarnTabSrc() {
        return earnTabSrc;
    }

    /**
     * Gets the gets the activityTabSrc config.
     *
     * @return the gets the activityTabSrc config
     */
    public String getActivityTabSrc() {
        return activityTabSrc;
    }
}
