package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardPageNavigationLabels {

    /** The welcomeLabel. */
    @SerializedName("welcomeLabel")
    String welcomeLabel;

    /** The dashboardMenuItems. */
    @SerializedName("dashboardMenuItems")
    List<DashboardMenuItems> dashboardMenuItems;

    /** The needHelpCTALabel. */
    @SerializedName("needHelpCTALabel")
    String needHelpCTALabel;

    /** The needHelpCTALink. */
    @SerializedName("needHelpCTALink")
    String needHelpCTALink;

    /**
     * Constructor sets DashboardMenuItems  .
     *
     * @param welcomeLabel - welcomeLabel
     * @param dashboardMenuItems - dashboardMenuItems
     * @param needHelpCTALabel - needHelpCTALabel
     * @param needHelpCTALink - needHelpCTALink
     *
     */
    public DashboardPageNavigationLabels(String welcomeLabel,
                                         List<DashboardMenuItems> dashboardMenuItems,
                                         String needHelpCTALabel,
                                         String needHelpCTALink) {
        this.welcomeLabel = welcomeLabel;
        this.dashboardMenuItems = dashboardMenuItems;
        this.needHelpCTALabel = needHelpCTALabel;
        this.needHelpCTALink = needHelpCTALink;
    }
}
