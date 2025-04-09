package com.bdb.aem.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;

/**
 * The Class BDBTraverseNestedNode.
 */
public class BDBTraverseNestedNode {
	
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(BDBTraverseNestedNode.class);
	
	/** The paths. */
	private List<String> paths = new ArrayList<>();

	/**
	 * Gets the paths.
	 *
	 * @param baseResource the base resource
	 * @return the paths
	 */
	public static List<String> getPaths(Resource baseResource) {
		String path = Objects.requireNonNull(baseResource).getPath();
		log.debug("Getting deep tree for {}", path);
		BDBTraverseNestedNode walker = new BDBTraverseNestedNode();
		walker.walkTreeRecursively(baseResource);
		log.debug("Getting deep tree for {} finished with {} results", path, walker.paths.size());
		return walker.paths;
	}

	/**
	 * Instantiates a new traverse nested node.
	 */
	private BDBTraverseNestedNode() {
	}

	/**
	 * Walk tree recursively.
	 *
	 * @param baseResource the base resource
	 */
	public void walkTreeRecursively(Resource baseResource) {
		try {
			Node baseNode = baseResource.adaptTo(Node.class);
			boolean isHierarchyNode = baseNode.isNodeType(JcrConstants.NT_HIERARCHYNODE);
			if (isHierarchyNode) {
				paths.add(baseResource.getPath());
				Iterable<Resource> childrenIter = baseResource.getChildren();
				List<Resource> children = getChildren(childrenIter.iterator());
				children.forEach(this::walkTreeRecursively);
			}
		} catch (RepositoryException e) {
			log.warn("Exception when walking node tree at {}", baseResource.getPath());
		}
	}

	/**
	 * Gets the children.
	 *
	 * @param childrenIt the children it
	 * @return the children
	 */
	private List<Resource> getChildren(Iterator<Resource> childrenIt) {
		if (!childrenIt.hasNext()) {
			return Collections.emptyList();
		}
		List<Resource> children = new ArrayList<>();
		while (childrenIt.hasNext()) {
			children.add(childrenIt.next());
		}
		return children;
	};
}
