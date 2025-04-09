package com.bdb.aem.core.pojo;

import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class DashboardMenuItemsTest.
 */
class DashboardMenuItemsTest {

    /**
     * The dashboardMenuItems
     */
    DashboardMenuItems dashboardMenuItems;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        dashboardMenuItems = new DashboardMenuItems("label", "icon", "altText", "adaLabel", "path");
    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertEquals(dashboardMenuItems.getLabel(), "label");
        Assert.assertEquals(dashboardMenuItems.getIcon(), "icon");
        Assert.assertEquals(dashboardMenuItems.getAltText(), "altText");
        Assert.assertEquals(dashboardMenuItems.getAdaLabel(), "adaLabel");
        Assert.assertEquals(dashboardMenuItems.getPath(), "path");
    }

}

