package com.bdb.aem.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class CCListTest.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class CCListTest {

    /**
     * The CCList.
     */
    @InjectMocks
    CCList ccList;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        PrivateAccessor.setField(ccList, "ccId", "ccId");
        PrivateAccessor.setField(ccList, "ccIcon", "ccIcon");
        PrivateAccessor.setField(ccList, "ccLength", "12");
    }

    /**
     * Test getters.
     */
    @Test
    void testGetters() {
        assertNotNull(ccList.getCcIcon());
        assertNotNull(ccList.getCcId());
        assertNotNull(ccList.getCcLength());
    }

    /**
     * Test fields.
     */
    @Test
    void testFields() {
        assertEquals("12", ccList.getCcLength());
        assertEquals("ccId", ccList.getCcId());
        assertEquals("ccIcon", ccList.getCcIcon());
    }
}

