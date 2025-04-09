package com.bdb.aem.core.services.workflows;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.event.jobs.JobManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.day.cq.dam.api.Rendition;
@ExtendWith({ MockitoExtension.class })
public class BDBDocReplicationIndexingWorkflowTest {
	@InjectMocks
	BDBDocReplicationIndexingWorkflow BDBDocReplicationIndexingWorkflow;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The b DB workflow config service. */
	@Mock
	BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The session. */
	@Mock
	private Session session;

	/** The work item. */
	@Mock
	private WorkItem workItem;

	/** The Work flow data. */
	@Mock
	private WorkflowData WorkFlowData;

	/** The object. */
	@Mock
	private Object object;

	/** The workflow session. */
	@Mock
	private WorkflowSession workflowSession;

	/** The rendition. */
	@Mock
	private Rendition rendition;
	@Mock
	private Resource adminPageRes;

	/** The args. */
	@Mock
	private MetaDataMap args;

	/** The workflow helper service. */
	@Mock
	private WorkflowHelperService workflowHelperService;
	@Mock
	BDBSearchEndpointService searchConfig;
	@Mock
	ValueMap valueMap;
		/** The job manager. */
	@Mock
	private JobManager jobManager;
	
	@Mock
	SolrSearchService solrSearchService;

	/** The Constant assetPath. */
	private static final String assetPath = "/content/dam/pankaj.pdf";
	@Test
	void executeTest() throws WorkflowException, LoginException, RepositoryException {
		lenient().when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		lenient().when(WorkFlowData.getPayload()).thenReturn(object);
		lenient().when(object.toString()).thenReturn(assetPath);
		lenient().when(searchConfig.getAdminPagePath()).thenReturn("/content");
		lenient().when(resourceResolver.getResource(Mockito.any())).thenReturn(adminPageRes);
		lenient().when(adminPageRes.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(valueMap.get("slingJobEnabler")).thenReturn("true");
		lenient().when(workflowHelperService.getAssetType(assetPath)).thenReturn("imageAsset");
		lenient().when(workflowHelperService.getAssetType(assetPath)).thenReturn("videoThumbnailAsset");
		//videoThumbnailAsset
		BDBDocReplicationIndexingWorkflow.execute(workItem, workflowSession, args);
	}
	
	@Test
	void executeTestForImageAsset() throws WorkflowException, LoginException, RepositoryException {
		lenient().when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		lenient().when(WorkFlowData.getPayload()).thenReturn(object);
		lenient().when(object.toString()).thenReturn(assetPath);
		lenient().when(searchConfig.getAdminPagePath()).thenReturn("/content");
		lenient().when(resourceResolver.getResource(Mockito.any())).thenReturn(adminPageRes);
		lenient().when(adminPageRes.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(valueMap.get("slingJobEnabler")).thenReturn("true");
		lenient().when(workflowHelperService.getAssetType(assetPath)).thenReturn("imageAsset");
		lenient().when(bDBWorkflowConfigService.getDamAssetBasePath()).thenReturn("/some/asset");
		lenient().when(bDBWorkflowConfigService.getVarCommerceBasePath()).thenReturn("/some/asset");
		BDBDocReplicationIndexingWorkflow.execute(workItem, workflowSession, args);
		
		lenient().when(workflowHelperService.getAssetType(assetPath)).thenReturn("pdfAsset");
		BDBDocReplicationIndexingWorkflow.execute(workItem, workflowSession, args);
		
	}
}
