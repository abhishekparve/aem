package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

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
 * The Class ImageTextModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ImageTextModelTest {

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

    /** The image text model. */
    @InjectMocks
    ImageTextModel imageTextModel ;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(imageTextModel, "imagePath", "imagePath");
		PrivateAccessor.setField(imageTextModel, "ctaUrl", "ctaUrl");
    }

   
    /**
     * Test get image path.
     */
    @Test
    void testGetImagePath(){
        lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver)).thenReturn("/content/dam/image");
        assertNotNull(imageTextModel.getImagePath());
    }
    
    /**
     * Test get cta url.
     */
    @Test
    void testGetCtaUrl(){
        lenient().when(externalizerService.getFormattedUrl("ctaUrl", resourceResolver)).thenReturn("www.google.com");
        assertNotNull(imageTextModel.getCtaUrl());
    }
    
    /**
     * Test init.
     */
    @Test
    void testInit(){
    	imageTextModel.init();
    }
}
