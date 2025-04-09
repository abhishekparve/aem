
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
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;

/**
 * The Class AllScientificThumbnailImagesIndexingWorkflow to index all Thumbnail images related to Scientific Resource Videos to Solr.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
		"process.label = Index all Scientific Resource Thumbnail Images to SOLR" })
public class AllScientificThumbnailImagesIndexingWorkflow implements WorkflowProcess {

	/** The Constant LOG. */
	private static final Logger logger = LoggerFactory.getLogger(AllScientificThumbnailImagesIndexingWorkflow.class);

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;

	/** The solr search service. */
	@Reference
	SolrSearchService solrSearchService;
	
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
		logger.debug("WorkItem workItem - {}, WorkflowSession workflowSession - {}, MetaDataMap args - {}", 
				workItem, 
				workflowSession, 
				args);
		ResourceResolver resourceResolver = null;
		try {
			resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
			solrSearchService.indexAllScientificResourceThumbnailImagesToSolr(resourceResolver);
		} catch (LoginException e) {
			logger.error("Exception Caught", e);
		} finally {
			CommonHelper.closeResourceResolver(resourceResolver);
		}
	}

}
