package com.bdb.aem.core.workflows;

import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;

/**
 * @author knarayansingh
 *
 */
@ExtendWith({ MockitoExtension.class })
public class AllPdfsIndexingWorkflowTest {

	/** AllPdfsIndexingWorkflow Object */
	@InjectMocks
	AllPdfsIndexingWorkflow allPdfsIndexingWorkflow;

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

	/** The HttpSolrClient */
	@Mock
	HttpSolrClient server;

	/** The ResourceResolver */
	@Mock
	ResourceResolver resourceResolver;

	/**
	 * @throws LoginException
	 * @throws WorkflowException
	 */
	@Test
	void testExecute() throws LoginException, WorkflowException {
		lenient().when(solrConfig.getContentPageCollectionName()).thenReturn("Collection");
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		allPdfsIndexingWorkflow.execute(workItem, workflowSession, args);
	}

	/**
	 * To Test the Execute() method If condition.
	 * 
	 * @throws WorkflowException
	 */
	@Test
	void testIfFalseExecuteAndLoginException() throws WorkflowException {
		lenient().when(solrConfig.getContentPageCollectionName()).thenReturn("");
		allPdfsIndexingWorkflow.execute(workItem, workflowSession, args);
	}

	/**
	 * To Test the Execute() method LoginException.
	 * 
	 * @throws LoginException
	 * @throws WorkflowException
	 */
	@Test
	void testExecuteLoginException() throws LoginException, WorkflowException {
		lenient().when(solrConfig.getContentPageCollectionName()).thenReturn("Collection");
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		allPdfsIndexingWorkflow.execute(workItem, workflowSession, args);
	}

}
