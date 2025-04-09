package com.bdb.aem.core.pojo;

import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class DashboardPageNavigationLabelsTest.
 */
class DashboardPageNavigationLabelsTest {

    /**
     * The dashboardPageNavigationLabels
     */
    DashboardPageNavigationLabels dashboardPageNavigationLabels;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        DashboardMenuItems dashboardMenuItems = new DashboardMenuItems("label", "icon", "altText", "adaLabel", "path");
        List<DashboardMenuItems> dashboardMenuItemsList = new ArrayList<>();
        dashboardMenuItemsList.add(dashboardMenuItems);
        dashboardPageNavigationLabels = new DashboardPageNavigationLabels(
                "welcomeLabel",
                dashboardMenuItemsList,
                "needHelpCtaLabel",
                "needHelpCtaLink");
    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertEquals(dashboardPageNavigationLabels.welcomeLabel, "welcomeLabel");
        Assert.assertNotNull(dashboardPageNavigationLabels.dashboardMenuItems);
        Assert.assertEquals(dashboardPageNavigationLabels.needHelpCTALabel, "needHelpCtaLabel");
        Assert.assertEquals(dashboardPageNavigationLabels.needHelpCTALink, "needHelpCtaLink");
    }

}


