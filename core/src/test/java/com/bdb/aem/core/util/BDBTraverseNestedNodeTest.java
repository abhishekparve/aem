package com.bdb.aem.core.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.jcr.JcrConstants;

/**
 * The Class BDBTraverseNestedNodeTest.
 */
@ExtendWith({MockitoExtension.class})
class BDBTraverseNestedNodeTest {
	
	/** The base resource. */
	@Mock
	Resource baseResource;
	
	/** The base node. */
	@Mock
	Node baseNode;
	
	/** The children iter. */
	@Mock
	Iterable<Resource> childrenIter;
    
    /** The children. */
    @Mock
    Iterator<Resource> children;
	
	/** The children list. */
	@Mock
	List<Resource> childrenList;
    
    /** The nested node type. */
    private final String NESTED_NODE_TYPE = "/dummy/test";
    
    
	/**
	 * Test get paths.
	 *
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testGetPaths() throws RepositoryException {
		
		lenient().when(baseResource.getPath()).thenReturn(NESTED_NODE_TYPE);
		
		lenient().when(baseResource.adaptTo(Node.class)).thenReturn(baseNode);
		lenient().when(baseNode.isNodeType(JcrConstants.NT_HIERARCHYNODE)).thenReturn(true);
		lenient().when(baseResource.getPath()).thenReturn(NESTED_NODE_TYPE);
		lenient().when(baseResource.getChildren()).thenReturn(childrenIter);
		lenient().when(childrenIter.iterator()).thenReturn(children);
		
		lenient().when(children.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		lenient().when(children.next()).thenReturn(baseResource);
		
		assertNotNull(BDBTraverseNestedNode.getPaths(baseResource));
		
	}
}