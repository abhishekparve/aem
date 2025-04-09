package com.bdb.aem.core.services;

import com.day.cq.workflow.WorkflowSession;
import org.apache.sling.api.resource.ResourceResolver;

public interface UpdateProductDocumentService {
    public void updateDocumentRegionList(String filePath, ResourceResolver resourceResolver, WorkflowSession workflowSession);
}