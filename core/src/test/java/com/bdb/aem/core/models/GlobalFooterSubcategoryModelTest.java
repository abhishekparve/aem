package com.bdb.aem.core.models;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

/**
 * The Class GlobalFooterSubcategoryModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class GlobalFooterSubcategoryModelTest {

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

    /** The global footer subcategory model. */
    @InjectMocks
    GlobalFooterSubcategoryModel globalFooterSubcategoryModel;

    @BeforeEach
    void setUp() throws NoSuchFieldException{
    	PrivateAccessor.setField(globalFooterSubcategoryModel, "title", "title");
		PrivateAccessor.setField(globalFooterSubcategoryModel, "url", "url");
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
        lenient().when(externalizerService.getFormattedUrl("url", resourceResolver)).thenReturn("https://www.google.co.in");

        assertNotNull(globalFooterSubcategoryModel.getUrl());
        assertNotNull(globalFooterSubcategoryModel.getTitle());
    }

    /**
     * Test init.
     * @throws LoginException 
     */
    @Test
    void testInit() throws LoginException{
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
    	globalFooterSubcategoryModel.init();
    }
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		globalFooterSubcategoryModel.init();
	}
}
