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
 * The Class ApiErrorMessagesModelTest.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class ApiErrorMessagesModelTest {

    /**
     * The apiErrorMessagesModel.
     */
    @InjectMocks
    ApiErrorMessagesModel apiErrorMessagesModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        PrivateAccessor.setField(apiErrorMessagesModel, "errorCode", "errorCode");
        PrivateAccessor.setField(apiErrorMessagesModel, "errorMessage", "errorMessage");
    }

    /**
     * Test getters.
     */
    @Test
    void testGetters() {
        assertNotNull(apiErrorMessagesModel.getErrorCode());
        assertNotNull(apiErrorMessagesModel.getErrorMessage());
    }

    /**
     * Test fields.
     */
    @Test
    void testFields() {
        assertEquals("errorCode", apiErrorMessagesModel.getErrorCode());
        assertEquals("errorMessage", apiErrorMessagesModel.getErrorMessage());
    }
}

