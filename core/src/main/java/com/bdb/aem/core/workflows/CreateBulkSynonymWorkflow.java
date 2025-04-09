package com.bdb.aem.core.workflows;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.CreateSynonymsService;
import com.bdb.aem.core.services.UpdateProductAnnouncementService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;

/**
 * The process to index/un-index content pages and pdf assets.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
		"process.label = Create Bulk Synonnym" })
public class CreateBulkSynonymWorkflow implements WorkflowProcess {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CreateBulkSynonymWorkflow.class);

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	
	
	@Reference transient CreateSynonymsService createSynonymsService;
	
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
			serviceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
			String damFilePath = workItem.getWorkflowData().getPayload().toString();
			//LOG.info("damFilePath:---"+ damFilePath);
			createSynonymsService.createSynonyms(damFilePath, serviceResolver);
			
		} catch (LoginException e) {
			LOG.error("Exception occured {}", e);
		} finally {
			if (null != serviceResolver) {
				CommonHelper.closeResourceResolver(serviceResolver);
			}
		}

	}
}