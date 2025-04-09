package com.bdb.aem.core.services.workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.event.jobs.JobManager;
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
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.impl.BDBDistributionServiceImpl;
import com.bdb.aem.core.services.impl.BDBIndexingJob;
import com.bdb.aem.core.services.impl.BDBIndexingJobForOnDemandTDS;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

/**
 * The Class BDBDocReplicationIndexingWorkflow.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
		"process.label = BDB Doc Replication and Indexing workflow" })
public class BDBDocReplicationIndexingWorkflow implements WorkflowProcess {

	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BDBDocReplicationIndexingWorkflow.class);

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;

	/** The workflow helper service. */
	@Reference
	WorkflowHelperService workflowHelperService;

	/** The bDB workflow config service. */
	@Reference
	BDBWorkflowConfigService bDBWorkflowConfigService;
	
	/** The search config. */
    @Reference
    BDBSearchEndpointService searchConfig;
    
    /** The solr search service. */
	@Reference
	SolrSearchService solrSearchService;

	/** The job manager. */
	@Reference
	private JobManager jobManager;

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
		LOGGER.debug("Entry Execute method of BDBDocReplicationIndexingWorkflow");
		ResourceResolver serviceResolver = null;
		try {
			serviceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
			String payload = workItem.getWorkflowData().getPayload().toString();
			LOGGER.debug("Payload {}", payload);
			
			if (StringUtils.isNotBlank(payload)) {
				String typeOfAsset = workflowHelperService.getAssetType(payload);
				LOGGER.debug("Asset Type : {}", typeOfAsset);
				List<String> listOfAssets = new ArrayList<>();
				listOfAssets.add(payload);
				LOGGER.debug("listOfAssets of BDBDocReplicationIndexingWorkflow : {}", listOfAssets);
				initiateProcessing(listOfAssets, typeOfAsset, serviceResolver);
			}
		} catch (LoginException | RepositoryException | SolrServerException | IOException e) {
			LOGGER.error("Exception occurred during processing : {0}", e);
		} finally {
			CommonHelper.closeResourceResolver(serviceResolver);
		}
		LOGGER.debug("Exit Execute method of BDBDocReplicationIndexingWorkflow");
	}
	
	/**
	 * Initiate replication and indexing.
	 *
	 * @param listOfAssets the list of assets
	 * @param typeOfAsset the type of asset
	 */
	private void initiateReplicationAndIndexing(List<String> listOfAssets, String typeOfAsset) {
		workflowHelperService.addJobForReplication(listOfAssets, jobManager, BDBDistributionServiceImpl.TOPIC, 100,
				CommonConstants.REPLICATION_ALL_NODES);
		workflowHelperService.addJobForIndexing(listOfAssets, jobManager, BDBIndexingJob.TOPIC, typeOfAsset);
		workflowHelperService.addJobForIndexingOnDemandTDS(listOfAssets, jobManager, BDBIndexingJobForOnDemandTDS.TOPIC, typeOfAsset);
	}

	/**
	 * Initiate processing.
	 *
	 * @param listOfAssets the list of assets
	 * @param typeOfAsset the type of asset
	 * @param serviceResolver the service resolver
	 * @throws LoginException 
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @throws RepositoryException 
	 */
	private void initiateProcessing(List<String> listOfAssets, String typeOfAsset, ResourceResolver serviceResolver)
			throws RepositoryException, SolrServerException, IOException, LoginException {
		Resource adminPageRes = serviceResolver
				.getResource(searchConfig.getAdminPagePath() + CommonConstants.JCR_CONTENT);
		if (null != adminPageRes) {
			ValueMap valueMap = adminPageRes.adaptTo(ValueMap.class);
			if (valueMap.containsKey("slingJobEnabler") && valueMap.get("slingJobEnabler").equals("true")) {
				if (StringUtils.equalsIgnoreCase(typeOfAsset, CommonConstants.TYPE_IMAGE)) {
					workflowHelperService.addJobForReplication(listOfAssets, jobManager,
							BDBDistributionServiceImpl.TOPIC, 100, CommonConstants.REPLICATION_ALL_NODES);
					//indexing of the image related products to update the thumbnail link in solr. 
					solrSearchService.indexPdpUrls(serviceResolver,
							listOfAssets.get(0)
									.substring(0, listOfAssets.get(0).lastIndexOf(CommonConstants.SINGLE_SLASH))
									.replace(bDBWorkflowConfigService.getDamAssetBasePath(),
											bDBWorkflowConfigService.getVarCommerceBasePath()));
				} else if (StringUtils.equalsIgnoreCase(typeOfAsset, CommonConstants.TYPE_VIDEO_THUMBNAIL)) {
					initiateReplicationAndIndexing(listOfAssets, CommonConstants.TYPE_VIDEO_THUMBNAIL);
				} else if (StringUtils.equalsIgnoreCase(typeOfAsset, CommonConstants.TYPE_PDF)) {
					initiateReplicationAndIndexing(listOfAssets, CommonConstants.TYPE_PDF);
				}
			}
		}

	}
}
