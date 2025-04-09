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
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class ContentCardMultifieldModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ContentCardMultifieldModelTest {

    /** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;

    /** Resourceresolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /**  The ComponentContext context *. */
    @Mock
    ComponentContext context;

    /** The content card multifield model. */
    @InjectMocks
    ContentCardMultifieldModel contentCardMultifieldModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(contentCardMultifieldModel, "iconImagePath", "iconImagePath");
    	PrivateAccessor.setField(contentCardMultifieldModel, "titleLink", "titleLink");
		PrivateAccessor.setField(contentCardMultifieldModel, "title", "title");
		PrivateAccessor.setField(contentCardMultifieldModel, "altText", "altText");
		PrivateAccessor.setField(contentCardMultifieldModel, "description", "description");
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
    	lenient().when(externalizerService.getFormattedUrl("iconImagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("titleLink", resourceResolver)).thenReturn("/content/bdb/page.html");
    	assertNotNull(contentCardMultifieldModel.getIconImagePath());
    	assertNotNull(contentCardMultifieldModel.getTitleLink());
        assertNotNull(contentCardMultifieldModel.getTitle());
        assertNotNull(contentCardMultifieldModel.getAltText());
        assertNotNull(contentCardMultifieldModel.getDescription());
    }

    /**
     * Test init.
     * @throws LoginException 
     */
    @Test
    void testInit() throws LoginException{
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        contentCardMultifieldModel.init();
    }
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		contentCardMultifieldModel.init();
	}
}
