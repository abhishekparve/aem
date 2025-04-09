package com.bdb.aem.core.pojo;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * The Class PaymentsConfigTest.
 */
class PaymentsConfigTest {

    /**
     * The PaymentsConfig.
     */
    PaymentsConfig paymentsConfig;

    /** The get payments. */
    @Mock
    PayloadConfig getPayments;

    /** The add credit card. */
    @Mock
    PayloadConfig addCreditCard;

    /** The remove credit card. */
    @Mock
    PayloadConfig removeCreditCard;

    /** The update credit card. */
    @Mock
    PayloadConfig updateCreditCard;
    
    /** The get paymetric token. */
    @Mock
    PayloadConfig getPaymetricToken;
    
    /** The iframe url. */
    @Mock
    String iframeUrl;


    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        paymentsConfig = new PaymentsConfig(
                getPayments,
                addCreditCard,
                removeCreditCard,
                updateCreditCard,
                getPaymetricToken,
                iframeUrl);
    }

    /**
     * Test.
     */
    @Test
    void test() {
        assertNotNull(paymentsConfig);
    }
}
