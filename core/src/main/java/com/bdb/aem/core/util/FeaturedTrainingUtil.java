package com.bdb.aem.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.TrainingDetailsContainerModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Class FeaturedTrainingUtil.
 */
public class FeaturedTrainingUtil {
	
	/**
	 * Instantiates a new featured training util.
	 */
	private FeaturedTrainingUtil() {
		throw new IllegalStateException("Utility class");
	}

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(FeaturedTrainingUtil.class);
	
	/** The Constant JCR_ROOT. */
	protected static final String JCR_ROOT = "/jcr:content/root";

	/**
	 * Gets the model array.
	 *
	 * @param pagePath the page path
	 * @param resourceResolver the resource resolver
	 * @param resourceType the resource type
	 * @return the model array
	 */
	public static List<TrainingDetailsContainerModel> getModelArray(String pagePath, ResourceResolver resourceResolver,
			String resourceType) {
		ArrayList<TrainingDetailsContainerModel> modelObjectArray = new ArrayList<>();
		if (null != pagePath && null != resourceResolver) {
			List<String> modelNodes = getComponentNodes(pagePath, resourceResolver, resourceType);
			for (String modelNode : modelNodes) {
				if (StringUtils.isNotEmpty(modelNode)) {
					addModelNode(modelNode, modelObjectArray, resourceResolver);
				}
			}
		}
		return modelObjectArray;
	}

	/**
	 * Adds the model node.
	 *
	 * @param modelNode the model node
	 * @param modelObjectArray the model object array
	 * @param resourceResolver the resource resolver
	 */
	private static void addModelNode(String modelNode, ArrayList<TrainingDetailsContainerModel> modelObjectArray,
			ResourceResolver resourceResolver) {
		Resource modelResource = resourceResolver.getResource(modelNode);
		if (modelResource != null) {
			TrainingDetailsContainerModel model = modelResource.adaptTo(TrainingDetailsContainerModel.class);
			if (model != null && model.getTrainingDetailsList() != null) {
				modelObjectArray.add(model);
			}
		}
	}

	/**
	 * Gets the component nodes.
	 *
	 * @param pagePath the page path
	 * @param resourceResolver the resource resolver
	 * @param resourceType the resource type
	 * @return the component nodes
	 */
	public static List<String> getComponentNodes(String pagePath, ResourceResolver resourceResolver,
			String resourceType) {
		ArrayList<String> nodePaths = new ArrayList<>();
		String nodePath;
		if (StringUtils.isNotEmpty(pagePath) && null != resourceResolver) {
			Resource pageContentResource = resourceResolver.getResource(pagePath.concat(JCR_ROOT));
			if (pageContentResource != null) {
				Iterator<Resource> children = pageContentResource.listChildren();
				while (children.hasNext()) {
					Resource child = children.next();
					if (child.isResourceType(resourceType)) {
						nodePath = child.getPath();
						nodePaths.add(nodePath);
					}
				}
			}
		}
		return nodePaths;
	}
}
