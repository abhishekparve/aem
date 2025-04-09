package com.bdb.aem.core.workflows;

import com.bdb.aem.core.services.UpdateProductDocumentService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = WorkflowProcess.class, property = {"process.label = Update Product Document Region"})
public class UpdateDocumentRegionWorkflow implements WorkflowProcess {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(UpdateDocumentRegionWorkflow.class);

    /** The resource resolver factory. */
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    /** The UpdateProductDocument Service. */
    @Reference
    UpdateProductDocumentService updateProductDocumentService;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
        ResourceResolver serviceResolver = null;
        try {
            serviceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
            String damFilePath = workItem.getWorkflowData().getPayload().toString();
            updateProductDocumentService.updateDocumentRegionList(damFilePath, serviceResolver, workflowSession);
        }
        catch (LoginException e) {
            LOG.error("Exception occurred in UpdateProductDocumentWorkflow {}", e.getMessage());
        }
        finally {
            if (null != serviceResolver) {
                CommonHelper.closeResourceResolver(serviceResolver);
            }
        }
    }
}