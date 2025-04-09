package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SocialMediaMultiFieldModelTest {

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
	
	/** The Social Media multified support model. */
	@InjectMocks
	SocialMediaMultiFieldModel socialMediaMultiFieldModel;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {		
		PrivateAccessor.setField(socialMediaMultiFieldModel, "icon", "socialMediaIcon");
		PrivateAccessor.setField(socialMediaMultiFieldModel, "url", "socialMediaURL");
		PrivateAccessor.setField(socialMediaMultiFieldModel, "alt", "socialMediaAlt");
	}
	 /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
        lenient().when(externalizerService.getFormattedUrl("socialMediaURL", resourceResolver)).thenReturn("https://www.google.co.in");

        assertNotNull(socialMediaMultiFieldModel.getIcon());
        assertNotNull(socialMediaMultiFieldModel.getUrl());
        assertNotNull(socialMediaMultiFieldModel.getAlt());
        
        assertEquals("socialMediaIcon", socialMediaMultiFieldModel.getIcon());
        assertEquals("socialMediaURL", socialMediaMultiFieldModel.getUrl());
        assertEquals("socialMediaAlt", socialMediaMultiFieldModel.getAlt());
        
    }
    
    /**
     * Test init.
     */
    @Test
    void testInit(){
    	socialMediaMultiFieldModel.init();
    }

}
