
package com.bdb.aem.core.workflows;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
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

import javax.jcr.RepositoryException;
import java.io.IOException;

/**
 * The Class AllPDPIndexingWorkflow to index all dynamic PDP pages to SOLR.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
		"process.label = Index all dynamic PDPs to SOLR" })
public class AllPDPIndexingWorkflow implements WorkflowProcess {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AllPDPIndexingWorkflow.class);

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
		ResourceResolver resourceResolver = null;
		String collection = solrConfig.getContentPageCollectionName();
		if (!collection.isEmpty()) {
			try {
				String payload = workItem.getWorkflowData().getPayload().toString();
				resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
                if(null != resourceResolver && null != resourceResolver.getResource(payload) && payload.startsWith("/content/commerce/products/bdb/products")) {
					LOG.info("Executing AllPDPIndexingWorkflow for : {}", payload);
                    solrSearchService.indexPdpUrls(resourceResolver, payload);
                }else{
                    LOG.warn("Payload path is Invalid !! ", payload);
                }
			} catch (IOException | RepositoryException | SolrServerException | LoginException e) {
				LOG.error("Exception due to {}", e);
			}finally {
				CommonHelper.closeResourceResolver(resourceResolver);
			}
		}
	}

}
