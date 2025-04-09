package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class TilesComponentModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TilesComponentModelTest {
	
	/** The resource resolver factory. */
	@Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
	
    /** The tiles component model. */
    @InjectMocks
	TilesComponentModel tilesComponentModel;
    
    /** The resource item. */
    @Mock
    Resource facetcategory, resourceItem;
    
    /** The child items. */
    @Mock
	Iterator<Resource> childItems;

    /** The vm. */
    @Mock
    ValueMap vm;
    
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(tilesComponentModel, "facetcategory", facetcategory);
		PrivateAccessor.setField(tilesComponentModel, "pageType", CommonConstants.DROPDOWN);
		
        lenient().when(facetcategory.listChildren()).thenReturn(childItems);
        lenient().when(childItems.hasNext()).thenReturn(true).thenReturn(false);
        lenient().when(childItems.next()).thenReturn(resourceItem);
        lenient().when(resourceItem.adaptTo(ValueMap.class)).thenReturn(vm);
        when(vm.get(CommonConstants.FACETLABEL,String.class)).thenReturn("label");
        when(vm.get(CommonConstants.FACETVALUE,String.class)).thenReturn("value");
        tilesComponentModel.init();
	}
	
	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
    void testInit() throws LoginException{
        tilesComponentModel.init();
    }

	/**
	 * Test get event blog json labels.
	 */
	@Test
	void testGetEventBlogJsonLabels() {
		assertNotNull(tilesComponentModel.getEventBlogJsonLabels());
	}
	
	/**
	 * Test get event blog json config.
	 */
	@Test
	void testGetEventBlogJsonConfig() {
		assertNotNull(tilesComponentModel.getEventBlogJsonConfig());
	}
}
