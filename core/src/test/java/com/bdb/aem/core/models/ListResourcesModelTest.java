package com.bdb.aem.core.models;

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
 * The Class ListResourcesModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ListResourcesModelTest {

    /** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /** The context. */
    @Mock
    ComponentContext context;

    /** The list resources model. */
    @InjectMocks
    ListResourcesModel listResourcesModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(listResourcesModel, "rowLink", "rowLink");
		PrivateAccessor.setField(listResourcesModel, "rowTitle", "rowTitle");
		PrivateAccessor.setField(listResourcesModel, "rowDescription", "rowDescription");
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
        lenient().when(externalizerService.getFormattedUrl("rowLink", resourceResolver)).thenReturn("https://www.google.co.in");

        assertNotNull(listResourcesModel.getRowLink());
        assertNotNull(listResourcesModel.getRowTitle());
        assertNotNull(listResourcesModel.getRowDescription());
    }
    
    /**
     * Test init.
     */
    @Test
    void testInit(){
    	listResourcesModel.init();
    }
}