package com.bdb.aem.core.pojo;

import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * The Class OrderInquiryConfigTest.
 */
class OrderInquiryConfigTest {

    /**
     * The orderInquiryConfig.
     */
    OrderInquiryConfig orderInquiryConfig;

    @Mock
    PayloadConfig getOrderInquiryDetail;

    @Mock
    PayloadConfig postOrderInquiryDetails;



    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        orderInquiryConfig = new OrderInquiryConfig(
                getOrderInquiryDetail,
                postOrderInquiryDetails
                );
    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertNotNull(orderInquiryConfig);
    }
    }