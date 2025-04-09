package com.bdb.aem.core.workflows;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.ImageMetadataImportService;
import com.bdb.aem.core.services.UpdateImageMetadataService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

/**
 * This workflow is used to add metadata to product images moved to product dam base folder
 * Configured as a process step inside replication and indexing post-processing workflow
 */

@Component(immediate = true, service = WorkflowProcess.class, property = {
        Constants.SERVICE_DESCRIPTION + "= Used to import image metadata from product nodes",
        "process.label" + "= Image Metadata Import"})
public class ImageMetadataImportWorkflow implements WorkflowProcess {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageMetadataImportWorkflow.class);
    /**
     * PNG Extension.
     */
    private static final String IMAGE_PNG = "image/png";

    /**
     * The constant dc:format
     */
    private static final String DC_FORMAT = "dc:format";

    /**
     * The bdb workflow config service.
     */
    @Reference
    BDBWorkflowConfigService bdbWorkflowConfigService;
    /**
     * The workflow helper service.
     */
    @Reference
    WorkflowHelperService workflowHelperService;
    /**
     * Resource Resolver Factory.
     */
    @Reference
    ResourceResolverFactory resolverFactory;

    /**
     * The product img migration service.
     */
    @Reference
    ImageMetadataImportService imageMetadataImportService;
    
    @Reference
    UpdateImageMetadataService updateImageMetadataService;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
        LOGGER.debug("Entry Execute method of ImageMetadataImportWorkflow");
        ResourceResolver resourceResolver = null;
        Boolean isFlatFileProductImage = false;
        try {
            resourceResolver = CommonHelper.getServiceResolver(resolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);
            SearchResult result = null;
            if (null != workflowSession) {
                String payload = workItem.getWorkflowData().getPayload().toString();
                LOGGER.debug("Payload {}", payload);
                /* validating dam path and processing only images asset */
                if (StringUtils.isNotBlank(payload) && workflowHelperService.canProcessPath(payload, bdbWorkflowConfigService.getDamAssetBasePath())) {
                	 Resource imageResource = resourceResolver.getResource(payload);
                	// checking whether it's a flat file product image.
                	result = updateImageMetadataService.getMatchingFlatFileImageNode(resourceResolver, payload, result);
                    if(null != result && result.getTotalMatches() > 0) {
                    	updateImageMetadataService.updateFlatFileImageMetadata(resourceResolver, session, imageResource, result);
                    } else {
                        Asset currentAsset = imageResource.adaptTo(Asset.class);
                        if (null != currentAsset && IMAGE_PNG.equalsIgnoreCase(String.valueOf(currentAsset.getMetadata(DC_FORMAT)))) {
                            LOGGER.debug("Adding Image metadata for {}", payload);
                            imageMetadataImportService.processProductImage(payload, resourceResolver, session);
                        }
                    }
                }
            }
        } catch (LoginException | RepositoryException | InvalidTagFormatException e) {
            LOGGER.error("Exception occurred in ImageMetadataImportWorkflow {}", e.getMessage());
        } finally {
            if (null != resourceResolver) {
                CommonHelper.closeResourceResolver(resourceResolver);
            }
        }
        LOGGER.debug("Exit Execute method of ImageMetadataImportWorkflow");
    }
}