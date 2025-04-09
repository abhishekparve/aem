package com.bdb.aem.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.CreateProductImagesNodeService;
import com.bdb.aem.core.util.CommonHelper;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;

/**
 * This workflow is used to create image metadata node under /var/bdb/images/unprocessed-assets/{Processing-date}/{Processing-time}/{record}/.. path
 * for images that are not pushed from source/Half pipe.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
        Constants.SERVICE_DESCRIPTION + "= used for nodes creation based on the excel data for Images that are not pushed from Half pipe",
        "process.label = Create Product Images Node Workflow"})
public class CreateProductImagesNodeWorkflow implements WorkflowProcess {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CreateProductImagesNodeWorkflow.class);

    /**
     * The resource resolver factory.
     */
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    CreateProductImagesNodeService createProductImagesNodeService;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
        ResourceResolver serviceResolver = null;
        Session session = null;
        try {
            serviceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
            session = serviceResolver.adaptTo(Session.class);
            String damFilePath = workItem.getWorkflowData().getPayload().toString();
            Resource resource = serviceResolver.getResource(damFilePath);
            if (null != resource) {
                createProductImagesNodeService.createProductImageNodes(resource, session);
            } else {
                LOG.error("File is not present in the dam path {}", damFilePath);
            }
        } catch (LoginException e) {
            LOG.error("Exception occurred while getting resource resolver {0}", e);
            throw new WorkflowException(e);
        } catch (RepositoryException e) {
            LOG.error("Exception occurred while creating the nodes {0}", e);
            throw new WorkflowException(e);
        } catch (IOException e) {
            LOG.error("Exception occurred while reading the excel file {0}", e);
            throw new WorkflowException(e);
        } finally {
            if (null != session && session.isLive()) {
                session.logout();
            }
            if (null != serviceResolver) {
                CommonHelper.closeResourceResolver(serviceResolver);
            }
        }
    }
}