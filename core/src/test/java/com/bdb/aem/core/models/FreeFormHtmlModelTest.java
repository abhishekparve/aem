package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class FreeFormHtmlModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FreeFormHtmlModelTest {

    
    /** The free form html model. */
    @InjectMocks
    FreeFormHtmlModel freeFormHtmlModel;
    
    /** The SlingHttpServletRequest. */
	@Mock
	SlingHttpServletRequest request;
	
    /** The ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	Resource currentResource;
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(freeFormHtmlModel, "title", "title");
		PrivateAccessor.setField(freeFormHtmlModel, "description", "description");
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(currentResource.getName()).thenReturn("freeFormHtml");
		lenient().when(currentResource.getParent()).thenReturn(currentResource);
	}
    
    /**
     * Test getters.
     */
    @Test
	void testGetters() {
		assertNotNull(freeFormHtmlModel.getTitle());
        assertNotNull(freeFormHtmlModel.getArticleId());
        assertNotNull(freeFormHtmlModel.getDescription());
	}
    @Test
   	void testInit() {
    	freeFormHtmlModel.init();
   	}
    
    /**
     * Test fields.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
	void testFields() throws NoSuchFieldException {
    	PrivateAccessor.setField(freeFormHtmlModel, "title", "");
		assertNotNull(freeFormHtmlModel.getTitle());
        assertNotNull(freeFormHtmlModel.getArticleId());
	}
}
