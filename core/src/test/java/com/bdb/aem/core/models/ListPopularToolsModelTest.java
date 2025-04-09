package com.bdb.aem.core.models;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class ListPopularToolsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ListPopularToolsModelTest {

    /** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /** The context. */
    @Mock
    ComponentContext context;

    /** The list popular tools model. */
    @InjectMocks
    ListPopularToolsModel listPopularToolsModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(listPopularToolsModel, "rowLink", "rowLink");
		PrivateAccessor.setField(listPopularToolsModel, "rowImage", "rowImage");
    	PrivateAccessor.setField(listPopularToolsModel, "rowTitle", "rowTitle");
		PrivateAccessor.setField(listPopularToolsModel, "rowDescription", "rowDescription");
		PrivateAccessor.setField(listPopularToolsModel, "rowDownloadLabel", "rowDownloadLabel");
		PrivateAccessor.setField(listPopularToolsModel, "rowDownloadPath", "rowDownloadPath");
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
        lenient().when(externalizerService.getFormattedUrl("rowLink", resourceResolver)).thenReturn("https://www.google.co.in");
        lenient().when(externalizerService.getFormattedUrl("rowImage", resourceResolver)).thenReturn("content/dam/us/us-en/images/img.png");
        lenient().when(externalizerService.getFormattedUrl("rowDownloadPath", resourceResolver)).thenReturn("http://domain.test.com");

        assertNotNull(listPopularToolsModel.getRowLink());
        assertNotNull(listPopularToolsModel.getRowImage());
        assertNotNull(listPopularToolsModel.getRowTitle());
        assertNotNull(listPopularToolsModel.getRowDescription());
        assertEquals("rowDownloadLabel", listPopularToolsModel.getRowDownloadLabel());
        assertEquals("rowDownloadPath", listPopularToolsModel.getRowDownloadPath());
    }
    
    /**
     * Test init.
     */
    @Test
    void testInit(){
    	listPopularToolsModel.init();
    }
}