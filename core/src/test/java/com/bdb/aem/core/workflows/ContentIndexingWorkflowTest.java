package com.bdb.aem.core.workflows;

import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;

/**
 * @author knarayansingh
 *
 */
@ExtendWith({ MockitoExtension.class })
public class ContentIndexingWorkflowTest {

	/** ContentIndexingWorkflowTest Object */
	@InjectMocks
	ContentIndexingWorkflow contentIndexingWorkflow;

	/** The ResourceResolverFactory */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The SolrSearchService */
	@Mock
	SolrSearchService solrSearchService;

	/** The BDBSearchEndpointService */
	@Mock
	BDBSearchEndpointService solrConfig;

	/** The WorkItem */
	@Mock
	WorkItem workItem;

	/** The WorkflowSession */
	@Mock
	WorkflowSession workflowSession;

	/** The MetaDataMap */
	@Mock
	MetaDataMap args;

	/** The ResourceResolver */
	@Mock
	ResourceResolver resourceResolver;

	/** The workflowData */
	@Mock
	WorkflowData workflowData;

	/** The payload object */
	@Mock
	Object payload;

	/** The Resource */
	@Mock
	Resource pageResource;

	/** The ValueMap */
	@Mock
	ValueMap properties;

	/** The Resource */
	@Mock
	Resource assetResource;

	/** The HttpSolrClient */
	@Mock
	HttpSolrClient server;

	/** The Asset */
	@Mock
	Asset asset;

	/** The ValueMap */
	@Mock
	ValueMap mapProperties;
	
	@Mock
	Resource mResource;
	
	@Mock
	Resource m1Resource;
	
	@Mock 
	Page page;
	
	@Mock 
	BDBApiEndpointService bdbApiService;

	/**
	 * The setup method.
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(workItem.getWorkflowData()).thenReturn(workflowData);
		lenient().when(workflowData.getPayload()).thenReturn(payload);
		lenient().when(payload.toString()).thenReturn("/content/bdb/ContnentPath");
		lenient().when(solrConfig.getBasePagePath()).thenReturn("/content/bdb");
		lenient().when(properties.get(SolrSearchConstants.CQ_LAST_REPLICATION_ACTION))
				.thenReturn(SolrSearchConstants.DEACTIVATE);

	}

	/**
	 * @throws LoginException
	 * @throws WorkflowException
	 */
	@Test
	void testExecuteForContentPages() throws LoginException, WorkflowException,Exception {
		lenient().when(resourceResolver.getResource("/content/bdb/ContnentPath")).thenReturn(pageResource);
		lenient().when(pageResource.getValueMap()).thenReturn(properties);
		lenient().when(properties.containsKey(SolrSearchConstants.CQ_LAST_REPLICATION_ACTION)).thenReturn(true);
		contentIndexingWorkflow.execute(workItem, workflowSession, args);
		contentIndexingWorkflow.forPdfAssets("/content/bdb/ContnentPath", resourceResolver);
		
	}

	/**
	 * @throws LoginException
	 * @throws WorkflowException
	 */
	@Test
	void testExecuteForContentPagesIndexContentPage() throws LoginException, WorkflowException,Exception {
		lenient().when(resourceResolver.getResource("/content")).thenReturn(mResource);
		lenient().when(mResource.getParent()).thenReturn(m1Resource);
		lenient().when(mResource.adaptTo(Asset.class)).thenReturn(asset);
		lenient().when(m1Resource.getPath()).thenReturn("/content");
		lenient().when(mResource.getParent().getPath()).thenReturn("/content");
		lenient().when(resourceResolver.getResource(mResource.getParent().getPath()).adaptTo(Page.class)).thenReturn(page);
		lenient().when(pageResource.getValueMap()).thenReturn(properties);
		lenient().when(properties.containsKey(SolrSearchConstants.CQ_LAST_REPLICATION_ACTION)).thenReturn(false);
		lenient().when(pageResource.adaptTo(ValueMap.class)).thenReturn(mapProperties);
		lenient().when(properties.containsKey(SolrSearchConstants.SOLR_UNINDEXABLE)).thenReturn(true);
		lenient().when(mResource.adaptTo(ValueMap.class)).thenReturn(mapProperties);
		lenient().when(bdbApiService.getScientificResourceFolder()).thenReturn("/content");
		lenient().when(solrSearchService.getTypeFromAssets(asset)).thenReturn("PNG Image");
		contentIndexingWorkflow.execute(workItem, workflowSession, args);
		contentIndexingWorkflow.indexContentPage(resourceResolver, "/content");
		contentIndexingWorkflow.indexPdfAsset(resourceResolver, "/content");
		contentIndexingWorkflow.forPdfAssets("/content/bdb/ContnentPath", resourceResolver);
	}

	/**
	 * @throws LoginException
	 * @throws WorkflowException
	 */
	@Test
	void testExecuteForPdfAssets() throws LoginException, WorkflowException ,Exception{
		lenient().when(payload.toString()).thenReturn("/content/dam/bdb/assets");
		lenient().when(solrConfig.getBasePagePath()).thenReturn("/content/dam/bdbAssets");
		lenient().when(solrConfig.getAssetPath()).thenReturn("/content/dam/bdb/assets");
		lenient().when(resourceResolver.getResource("/content/dam/bdb/assets")).thenReturn(assetResource);
		lenient().when(assetResource.getValueMap()).thenReturn(properties);
		lenient().when(properties.containsKey(SolrSearchConstants.CQ_LAST_REPLICATION_ACTION)).thenReturn(false);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		lenient().when(assetResource.adaptTo(Asset.class)).thenReturn(asset);
		lenient().when(bdbApiService.getScientificResourceFolder()).thenReturn("/content/dam/bdb/ScientificResources/images");
		lenient().when(solrSearchService.getTypeFromAssets(asset)).thenReturn("Adobe PDF");
		contentIndexingWorkflow.execute(workItem, workflowSession, args);
	}

	/**
	 * @throws LoginException
	 * @throws WorkflowException
	 */
	@Test
	void testLoginException() throws LoginException, WorkflowException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		contentIndexingWorkflow.execute(workItem, workflowSession, args);

	}
}
