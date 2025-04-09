package com.bdb.aem.core.pojo;

import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * The Class DashboardConfigTest.
 */
class DashboardConfigTest {

    /**
     * The dashboardConfig.
     */
    DashboardConfig dashboardConfig;

    @Mock
    PayloadConfig getMessages;

    @Mock
    PayloadConfig readMessage;

    @Mock
    PayloadConfig getOrders;

    @Mock
    PayloadConfig getQuotes;



    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        dashboardConfig = new DashboardConfig(
                getMessages,
                readMessage,
                getOrders,
                getQuotes
                );
    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertNotNull(dashboardConfig);
    }
    }