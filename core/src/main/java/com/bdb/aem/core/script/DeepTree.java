package com.bdb.aem.core.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.NameConstants;

public class DeepTree {

	protected static final Logger logger = LoggerFactory.getLogger(DeepTree.class);
	private List<String> paths = new ArrayList<>();

	// Used only for root "Eg:language-master/ko"
	public static List<String> getPaths(String baseResource, Session session) {
		DeepTree walker = new DeepTree();
		walker.walkTreeRecursively(baseResource + "/jcr:content", session, null);
		return walker.paths;
	}

	// Generic Script - Paths from Excel
	public static List<String> getPaths(ExcelBean excelBean, Session session) {
		String baseResource = excelBean.getPath();
		DeepTree walker = new DeepTree();
		walker.walkTreeRecursively(baseResource + "/jcr:content", session, excelBean);
		return walker.paths;
	}

	// Only for updating the page properties
	public static void getPaths(PathPagePropertiesBean bean, Session session) {
		String baseResource = bean.getPath() + "/jcr:content";
		Node jcrNode;
		try {
			jcrNode = session.getNode(baseResource);
			DeepTree tree = new DeepTree();
			if (null != bean && CollectionUtils.isNotEmpty(bean.getProperties())) {
				String[] propsArr = bean.getProperties().toArray(new String[bean.getProperties().size()]);
				tree.addPropertyLiveSyncCancelledMixin(jcrNode);
				tree.addPropertyInheritanceCancelled(jcrNode, propsArr);
			}
		} catch (PathNotFoundException e) {
			logger.error("Path not found : " + baseResource);
		} catch (RepositoryException e) {
			logger.error("Repository Exception : " + baseResource);
		}

	}

	public void walkTreeRecursively(String baseResource, Session session, ExcelBean excelBean) {
		try {
			Node baseNode = session.getNode(baseResource);
			if (null != excelBean) {
				processNode(baseNode, excelBean);
			} else {
				processNode(baseNode);
			}
			paths.add(baseResource);
			Iterable<Node> childrenIter = JcrUtils.getChildNodes(baseNode);
			List<Node> children = getChildren(childrenIter.iterator());
			for (Node node : children) {
				walkTreeRecursively(node.getPath(), session, excelBean);
			}
		} catch (RepositoryException e) {
			logger.error("Exception when walking node tree at :" + baseResource);
		}
	}

	private List<Node> getChildren(Iterator<Node> childrenIt) {
		if (!childrenIt.hasNext()) {
			return Collections.emptyList();
		}
		List<Node> children = new ArrayList<>();
		while (childrenIt.hasNext()) {
			children.add(childrenIt.next());
		}
		return children;
	}

	private void processNode(Node node, ExcelBean excelBean) throws RepositoryException {
		String modified = excelBean.getCompModified();
		String primaryType = node.getPrimaryNodeType().getName();

		if (!NameConstants.NT_PAGE.equalsIgnoreCase(primaryType)) {
			if ("cq:PageContent".equalsIgnoreCase(primaryType)) {
				addLiveRelationshipMixin(node);
			}

			if (JcrConstants.NT_UNSTRUCTURED.equalsIgnoreCase(primaryType) && checkIfNodeIsComponent(node)
					&& StringUtils.isBlank(modified)) {
				addLiveRelationshipMixin(node);
			} else if (JcrConstants.NT_UNSTRUCTURED.equalsIgnoreCase(primaryType) && checkIfNodeIsComponent(node)
					&& StringUtils.isNotBlank(modified)) {
				addLiveRelationshipMixin(node);
				if (!node.getName().equalsIgnoreCase("root")) {
					addLiveSyncCancelledMixin(node);
					addIsCancelledForChildren(node);
				}
			} else if (JcrConstants.NT_UNSTRUCTURED.equalsIgnoreCase(primaryType)) {
				addLiveRelationshipMixin(node);
			}
		}
	}

	public boolean checkIfNodeIsComponent(Node node) throws RepositoryException {
		String resourceType = getSlingResourceType(node);
		return (StringUtils.isNotEmpty(resourceType) && (resourceType.startsWith("bdb-aem/proxy/components")
				|| resourceType.startsWith("bdb-aem/components")));

	}

	public static String getSlingResourceType(Node node) throws RepositoryException {
		PropertyIterator pi = node.getProperties(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY);
		Property property = null;
		String resourceType = StringUtils.EMPTY;
		while (pi.hasNext()) {
			property = pi.nextProperty();
		}
		if (null != property && null != property.getValue()) {
			resourceType = property.getValue().getString();
		}
		return resourceType;
	}

	public void addLiveRelationshipMixin(Node node) throws NoSuchNodeTypeException, VersionException,
			ConstraintViolationException, LockException, RepositoryException {
		node.addMixin("cq:LiveRelationship");
	}

	public void addLiveSyncMixin(Node node) throws NoSuchNodeTypeException, VersionException,
			ConstraintViolationException, LockException, RepositoryException {
		node.addMixin("cq:LiveSync");
	}

	public void addPropertyLiveSyncCancelledMixin(Node node) throws NoSuchNodeTypeException, VersionException,
			ConstraintViolationException, LockException, RepositoryException {
		node.addMixin("cq:PropertyLiveSyncCancelled");
	}

	public void addLiveSyncCancelledMixin(Node node) throws NoSuchNodeTypeException, VersionException,
			ConstraintViolationException, LockException, RepositoryException {
		node.addMixin("cq:LiveSyncCancelled");
	}

	public void addPropertyInheritanceCancelled(Node node, String[] values) throws NoSuchNodeTypeException,
			VersionException, ConstraintViolationException, LockException, RepositoryException {
		node.setProperty("cq:propertyInheritanceCancelled", values);
	}

	public void addIsCancelledForChildren(Node node) throws NoSuchNodeTypeException, VersionException,
			ConstraintViolationException, LockException, RepositoryException {
		node.setProperty("cq:isCancelledForChildren", true);
	}

	public void processNode(Node node) throws RepositoryException {
		String primaryType = node.getPrimaryNodeType().getName();
		if (!NameConstants.NT_PAGE.equalsIgnoreCase(primaryType)) {
			if ("cq:PageContent".equalsIgnoreCase(primaryType)) {
				addLiveRelationshipMixin(node);
				addLiveSyncMixin(node);
				createLiveSyncConfigNode(node);

			} else if (JcrConstants.NT_UNSTRUCTURED.equalsIgnoreCase(primaryType) && checkIfNodeIsComponent(node)) {
				addLiveRelationshipMixin(node);
			}
		}
	}

	public void createLiveSyncConfigNode(Node node) throws RepositoryException {
		Node liveSyncNode = JcrUtils.getOrAddNode(node, "cq:LiveSyncConfig");
		liveSyncNode.setProperty("cq:master", "/content/bdb/language-masters/en");
		liveSyncNode.setProperty("cq:isDeep", true);
		liveSyncNode.setProperty("cq:rolloutConfigs", new String[] { "/libs/msm/wcm/rolloutconfigs/default" });
	}

}
