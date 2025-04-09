package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import java.util.Iterator;

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

import com.day.cq.tagging.TagManager;

import junitx.util.PrivateAccessor;

/**
 * The Class FacselectSpieceListingTest.
 */
@ExtendWith({ MockitoExtension.class })
class FacselectSpieceListingTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;
	
	/** The parent res. */
	@Mock
	Resource resource, rootRes, childItem, selectedSpieceResource, selectedSpieceChild, parentResource, parentRes;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The selected spiece child res. */
	@Mock
	Iterable<Resource> childRes, selectedSpieceChildRes;
	
	/** The selected spiece child iterator. */
	@Mock
	Iterator<Resource> childItems, selectedSpieceChildIterator;
	
	/** The vm. */
	@Mock
	ValueMap vm;
	
	/** The sm. */
	@Mock
	FACReportsModel sm;
	
	/** The tm. */
	@Mock
	TagManager tm;
	
	/** The facselect spiece listing test. */
	@InjectMocks
	FacselectSpieceListing facselectSpieceListingTest;
	
	/** The root path. */
	private final String ROOT_PATH = "rootPath";
	
	/** The parent path. */
	private final String PARENT_PATH = "parentPath";
	
	/** The spiece name. */
	private final String SPIECE_NAME = "spieceName";
	
	/**
	 * Setup.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setup() throws Exception {
		PrivateAccessor.setField(facselectSpieceListingTest, "rootPath", ROOT_PATH);
		PrivateAccessor.setField(facselectSpieceListingTest, "spieceName", SPIECE_NAME);
	}
	
	
	
	/**
	 * Test get flurochrome mapping list.
	 */
	@Test
	void testGetFlurochromeMappingList() {
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource(ROOT_PATH)).thenReturn(rootRes);
		lenient().when(rootRes.hasChildren()).thenReturn(true).thenReturn(false);
		lenient().when(rootRes.getChildren()).thenReturn(childRes);
		lenient().when(childRes.iterator()).thenReturn(childItems);
		lenient().when(childItems.hasNext()).thenReturn(true, false);
		lenient().when(childItems.next()).thenReturn(childItem);
		lenient().when(childItem.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(childItem.getPath()).thenReturn(ROOT_PATH);
		lenient().when(resourceResolver.getResource(ROOT_PATH + "/jcr:content/root/report/selectedSpecies")).thenReturn(selectedSpieceResource);
		lenient().when(selectedSpieceResource.hasChildren()).thenReturn(true);
		lenient().when(selectedSpieceResource.getChildren()).thenReturn(selectedSpieceChildRes);
		lenient().when(selectedSpieceChildRes.iterator()).thenReturn(selectedSpieceChildIterator);
		lenient().when(selectedSpieceChildIterator.hasNext()).thenReturn(true, false);
		lenient().when(selectedSpieceChildIterator.next()).thenReturn(selectedSpieceChild);
		lenient().when(selectedSpieceChild.getValueMap()).thenReturn(vm);
		lenient().when(vm.get("speciesLabel")).thenReturn(SPIECE_NAME);
		lenient().when(selectedSpieceChild.getParent()).thenReturn(resource);
		lenient().when(resource.getParent()).thenReturn(parentResource);
		lenient().when(parentResource.getPath()).thenReturn(PARENT_PATH);
		
		lenient().when(resourceResolver.getResource(PARENT_PATH)).thenReturn(parentRes);
		lenient().when(parentRes.getValueMap()).thenReturn(vm);		
		lenient().when(parentRes.adaptTo(FACReportsModel.class)).thenReturn(sm);
		lenient().when(parentRes.adaptTo(TagManager.class)).thenReturn(tm);
//		lenient().when(setFluorochromes(sm)).
		
		assertNotNull(facselectSpieceListingTest.getFlurochromeMappingList());
	}
	
	/**
	 * Test get flurocrome.
	 */
	@Test
	void testGetFlurocrome() {
		assertNotNull(facselectSpieceListingTest.getFlurocrome());
	}

	/**
	 * Test get fac data endpoint.
	 */
	@Test
	void testGetFacDataEndpoint() {
		assertNotNull(facselectSpieceListingTest.getFacDataEndpoint());
	}
}
