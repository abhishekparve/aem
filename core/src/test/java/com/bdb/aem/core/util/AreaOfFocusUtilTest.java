package com.bdb.aem.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.AreaOfFocusModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;

import junitx.util.PrivateAccessor;

/**
 * The Class AreaOfFocusUtilTest.
 */
@ExtendWith({MockitoExtension.class})
class AreaOfFocusUtilTest {
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The resource. */
	@Mock
	Resource resource;
	
	/** The model. */
	@Mock
    private AreaOfFocusModel model;
	
	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The exclude util object. */
	@Mock
	ExcludeUtil excludeUtilObject;
	
	/** The hybris site id. */
	String hybrisSiteId = "bdbTest";
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		bdbApiEndpointService = new BDBApiEndpointServiceImpl();
		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "hybrisSignUpPreferenceEndpoint", "/hybris/{{site}}/hybrisSignUpPreferenceEndpoint");
	}


	/**
	 * Test get component node.
	 */
	@Test
	void testGetComponentNode() {
		String pagePath = "/content/bdb/etc/etc1";
        String resourceType = "resourceType";
        Mockito.when(resourceResolver.getResource(pagePath.concat(AreaOfFocusUtil.JCR_ROOT))).thenReturn(resource);
        @SuppressWarnings("unchecked")
		Iterator<Resource> resItr = mock(Iterator.class);
        Mockito.when(resItr.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(resItr.next()).thenReturn(resource);
        Mockito.when(resource.getPath()).thenReturn("prePath/jcr:content/postPath");
        Mockito.when(resource.listChildren()).thenReturn(resItr);
        Mockito.when(resource.isResourceType(resourceType)).thenReturn(true);
        Mockito.when(resItr.next()).thenReturn(resource);

        String expected = "prePath/jcr:content/postPath";
        assertEquals(expected, AreaOfFocusUtil.getComponentNode(pagePath, resourceResolver, resourceType));
	}
	
	
	/**
	 * Test get area of focus model.
	 */
	@Test
	void testGetAreaOfFocusModel() {
		String pagePath = "/content/bd/etc/etc1";
		String resourceType = "resourceType";
		Mockito.when(resourceResolver.getResource(pagePath.concat(AreaOfFocusUtil.JCR_ROOT))).thenReturn(resource);
		@SuppressWarnings("unchecked")
		Iterator<Resource> resItr = mock(Iterator.class);
        Mockito.when(resItr.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(resItr.next()).thenReturn(resource);
        Mockito.when(resource.getPath()).thenReturn("prePath/jcr:content/postPath");
        Mockito.when(resource.listChildren()).thenReturn(resItr);
        Mockito.when(resource.isResourceType(resourceType)).thenReturn(true);
        Mockito.when(resItr.next()).thenReturn(resource);

        Mockito.when(resourceResolver.getResource("prePath/jcr:content/postPath")).thenReturn(resource);
        Mockito.when(resource.adaptTo(AreaOfFocusModel.class)).thenReturn(model);
        
        assertNotNull(AreaOfFocusUtil.getAreaOfFocusModel(pagePath, resourceResolver, resourceType));
	}

	
	/**
	 * Test get area of focus label.
	 */
	@Test
	void testGetAreaOfFocusLabel() {
		String pagePath = "/content/bd/etc/etc1";
		String resourceType = "resourceType";
		
		Mockito.when(resourceResolver.getResource(pagePath.concat(AreaOfFocusUtil.JCR_ROOT))).thenReturn(resource);
		@SuppressWarnings("unchecked")
		Iterator<Resource> resItr = mock(Iterator.class);
        Mockito.when(resItr.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(resItr.next()).thenReturn(resource);
        Mockito.when(resource.getPath()).thenReturn("prePath/jcr:content/postPath");
        Mockito.when(resource.listChildren()).thenReturn(resItr);
        Mockito.when(resource.isResourceType(resourceType)).thenReturn(true);
        Mockito.when(resItr.next()).thenReturn(resource);

        Mockito.when(resourceResolver.getResource("prePath/jcr:content/postPath")).thenReturn(resource);
        Mockito.when(resource.adaptTo(AreaOfFocusModel.class)).thenReturn(model);
		
		assertNotNull(AreaOfFocusUtil.getAreaOfFocusLabel(pagePath, resourceResolver, resourceType));
	}

	/**
	 * Test get area focus configuration json.
	 */
	@Test
	void testGetAreaFocusConfigurationJson() {
		assertNotNull(AreaOfFocusUtil.getAreaFocusConfigurationJson(excludeUtilObject, bdbApiEndpointService, hybrisSiteId));
	}
}
