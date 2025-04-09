package com.bdb.aem.core.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.List;

import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.BeadlotsModel;
import com.bdb.aem.core.services.BDBApiEndpointService;

/**
 * The Class BeadlotsUtilTest.
 */
@ExtendWith({MockitoExtension.class})
class BeadlotsUtilTest {
	
	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The exclude util object. */
	@Mock
	ExcludeUtil excludeUtilObject;
    
    /** The children. */
    @Mock
    Iterator<Resource> children;

	/** The node list. */
    @Mock
    List<String> nodeList;
    
    /** The model. */
    @Mock
    BeadlotsModel model;
	
	/** The resource. */
	@Mock
	Resource resource;
    
    /** The page path. */
    private final String PAGE_PATH = "/dummy/test";
	
	/** The jcr root. */
    protected final String JCR_ROOT = "/jcr:content/root";
    
    /** The bead lots resource type. */
    private final String BEAD_LOTS_RESOURCE_TYPE = "bdb-aem/components/content/beadlotscomponent";
	
    
	/**
	 * Test get model array.
	 */
	@Test
	void testGetModelArray() {
		
		when(resourceResolver.getResource(PAGE_PATH.concat(JCR_ROOT))).thenReturn(resource);
		when(resource.listChildren()).thenReturn(children);
		when(children.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(children.next()).thenReturn(resource);
		when(resource.isResourceType(BEAD_LOTS_RESOURCE_TYPE)).thenReturn(true);
		when(resource.getPath()).thenReturn(PAGE_PATH);
		when(resourceResolver.getResource(PAGE_PATH)).thenReturn(resource);
		when(resource.adaptTo(BeadlotsModel.class)).thenReturn(model);
		
		assertNotNull(BeadlotsUtil.getModelArray(PAGE_PATH, resourceResolver, BEAD_LOTS_RESOURCE_TYPE));
		
	}
	
}