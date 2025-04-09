package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

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
 * The Class TabSectionMutlifieldModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TabSectionMutlifieldModelTest {
	
    /** The resource resolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
    
    /** The context. */
    @Mock
    ComponentContext context;
    
    /** The tab section mutlifield model. */
    @InjectMocks
    TabSectionMutlifieldModel tabSectionMutlifieldModel;
    
    @BeforeEach
	void setUp() throws Exception {
		
		PrivateAccessor.setField(tabSectionMutlifieldModel, "tabUniqueName","tabUniqueName");
		}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
    void testInit() throws LoginException{
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        tabSectionMutlifieldModel.getImage();
        tabSectionMutlifieldModel.getImageTitle();
        tabSectionMutlifieldModel.getTabTitle();
        tabSectionMutlifieldModel.getTabLabel();
        tabSectionMutlifieldModel.getTabDescription();
        tabSectionMutlifieldModel.toString();
        
    }
    
    /**
     * Test init login exception.
     *
     * @throws LoginException the login exception
     */
    @Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
	}
    
    @Test
	void testgetSectionTitle() {
    	tabSectionMutlifieldModel.getTabUniqueName();
	}
}
