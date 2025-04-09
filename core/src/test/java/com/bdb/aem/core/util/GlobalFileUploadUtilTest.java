package com.bdb.aem.core.util;

import com.bdb.aem.core.models.GlobalFileUploadModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class GlobalFileUploadUtilTest.
 */
@ExtendWith({MockitoExtension.class})
class GlobalFileUploadUtilTest {


    /**
     * The model.
     */
    @Mock
    private GlobalFileUploadModel model;



     /**
     * Test Create Global File Upload label.
     */
    @Test
    void testCreateGlobalFileUploadLabels() {

        assertNotNull(GlobalFileUploadUtil.createGlobalFileUploadLabels(model, "note"));
    }


}
