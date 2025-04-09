
package com.bdb.aem.core.workflows;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.DocumentMetadataImportService;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.util.CommonHelper;


/**
 * The Class DocumentMetadataImportWorkflow to import the custom metadata of marketing documents under BDB PDF Metadata tab.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
		"process.label = Document Metadata Import" })
public class DocumentMetadataImportWorkflow implements WorkflowProcess {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(DocumentMetadataImportWorkflow.class);

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;

    /** The UpdateProductDocument Service. */
    @Reference
    DocumentMetadataImportService documentMetadataImportService;
    
	/**
	 * Execute.
	 *
	 * @param workItem        the work item
	 * @param workflowSession the workflow session
	 * @param args            the args
	 * @throws WorkflowException the workflow exception
	 */
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
		ResourceResolver serviceResolver = null;
			try {
				String payload = workItem.getWorkflowData().getPayload().toString();
				serviceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
				if (!payload.startsWith("/content/dam/bdb/marketing-documents")
						|| null == serviceResolver.getResource(payload)) {
					payload = "/content/dam/bdb/marketing-documents";
				}
				
				documentMetadataImportService.processPdfAssets(payload, serviceResolver);
				
			} catch (LoginException e) {
				LOG.error("Exception occurred in DocumentMetadataImportWorkflow {}", e.getMessage());
				 
			}
			finally {
				if (null != serviceResolver) {
				CommonHelper.closeResourceResolver(serviceResolver);
				}
			}
	}

}
