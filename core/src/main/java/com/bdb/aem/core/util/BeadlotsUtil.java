package com.bdb.aem.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.BeadlotsCategoryBean;
import com.bdb.aem.core.models.BeadlotsModel;

/**
 * The Class BeadlotsUtil.
 */
public class BeadlotsUtil {

    /** The Constant log. */
    protected static final Logger log = LoggerFactory.getLogger(BeadlotsUtil.class);

    /** The Constant JCR_ROOT. */
    protected static final String JCR_ROOT = "/jcr:content/root";
    
    /**
     * Instantiates a new beadlots util.
     */
    private BeadlotsUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets the model array.
     *
     * @param pagePath the page path
     * @param resourceResolver the resource resolver
     * @param resourceType the resource type
     * @return the model array
     */
    public static List<BeadlotsCategoryBean> getModelArray(String pagePath, ResourceResolver resourceResolver,
                                                           String resourceType) {
        ArrayList<BeadlotsCategoryBean> modelObjectArray = new ArrayList<>();
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
    private static void addModelNode(String modelNode, ArrayList<BeadlotsCategoryBean> modelObjectArray,
                                     ResourceResolver resourceResolver) {
        Resource modelResource = resourceResolver.getResource(modelNode);
        if (modelResource != null) {
            BeadlotsModel model = modelResource.adaptTo(BeadlotsModel.class);
            if (model != null) {
                modelObjectArray.add(model.getBeadlotsCategoryBean());
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
