
package com.bdb.aem.core.workflows;

import java.io.IOException;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.google.gson.JsonObject;

/**
 * The Class AllContentPageIndexingWorkflow to index all content pages to SOLR.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
		"process.label = Index all Content pages to SOLR" })
public class AllContentPageIndexingWorkflow implements WorkflowProcess {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AllContentPageIndexingWorkflow.class);

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;

	/** The solr search service. */
	@Reference
	SolrSearchService solrSearchService;

	/** The solr config. */
	@Reference
	BDBSearchEndpointService solrConfig;

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
		Session session = null;
		String collection = solrConfig.getContentPageCollectionName();
		String payload = workItem.getWorkflowData().getPayload().toString();
		if (!collection.isEmpty() && payload.startsWith(solrConfig.getBasePagePath())) {
			try( ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory)) {
				session = resourceResolver.adaptTo(Session.class);
				JsonObject indexPageData = solrSearchService.crawlContent(payload,
						SolrSearchConstants.CQ_PAGE, session,resourceResolver);

				solrSearchService.indexAllPagesToSolr(indexPageData);
			} catch (IOException | LoginException |  SolrServerException e) {
				LOG.error("Exception due to {}", e);
			}
		}
	}

}
