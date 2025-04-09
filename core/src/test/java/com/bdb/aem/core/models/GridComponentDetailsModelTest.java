package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
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
 * The Class GridComponentDetailsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class GridComponentDetailsModelTest {
	
    /** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;

    /** The resource resolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /** The context. */
    @Mock
    ComponentContext context;

    /** The grid component details model. */
    @InjectMocks
    GridComponentDetailsModel gridComponentDetailsModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(gridComponentDetailsModel, "buttonURL", "buttonURL");
		PrivateAccessor.setField(gridComponentDetailsModel, "productImage", "productImage");
		PrivateAccessor.setField(gridComponentDetailsModel, "altImage", "altImage");
		PrivateAccessor.setField(gridComponentDetailsModel, "productTitle", "productTitle");
		PrivateAccessor.setField(gridComponentDetailsModel, "productDescription", "productDescription");
		PrivateAccessor.setField(gridComponentDetailsModel, "buttonLabel", "buttonLabel");
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
        lenient().when(externalizerService.getFormattedUrl("buttonURL", resourceResolver)).thenReturn("https://www.google.co.in");
        lenient().when(externalizerService.getFormattedUrl("productImage", resourceResolver)).thenReturn("/content/us/en/img.png");

        assertNotNull(gridComponentDetailsModel.getButtonURL());
        assertNotNull(gridComponentDetailsModel.getProductImage());
        assertNotNull(gridComponentDetailsModel.getAltImage());
        assertNotNull(gridComponentDetailsModel.getProductTitle());
        assertNotNull(gridComponentDetailsModel.getProductDescription());
        assertNotNull(gridComponentDetailsModel.getButtonLabel());
    }
    
    /**
     * Test init.
     *
     * @throws LoginException the login exception
     */
    @Test
    void testInit(){
    	gridComponentDetailsModel.init();
    }
}