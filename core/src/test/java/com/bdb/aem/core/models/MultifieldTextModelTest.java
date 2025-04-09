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

import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class ListResourcesModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class MultifieldTextModelTest {
	

    /** The resource resolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /** The context. */
    @Mock
    ComponentContext context;

    /** The list resources model. */
    @InjectMocks
    MultifieldTextModel multifieldTextModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
		PrivateAccessor.setField(multifieldTextModel, "title", "rowTitle");
		PrivateAccessor.setField(multifieldTextModel, "description", "rowDescription");
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
        assertNotNull(multifieldTextModel.getTitle());
        assertNotNull(multifieldTextModel.getDescription());
    }
    
    /**
     * Test init.
     */
    @Test
    void testInit(){
    	multifieldTextModel.init();
    }
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		multifieldTextModel.init();
	}
}