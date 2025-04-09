package com.bdb.aem.core.workflows;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrServerException;
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
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;

/**
 * @author knarayansingh
 *
 */
@ExtendWith({ MockitoExtension.class })
public class AllPDPIndexingWorkflowTest {

	/** AllPDPIndexingWorkflow Object */
	@InjectMocks
	AllPDPIndexingWorkflow allPDPIndexingWorkflow;

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

	/** The Work flow data. */
	@Mock
	 WorkflowData WorkFlowData;

	/** The object. */
	@Mock
	Object object;
	@Mock
	Resource resource;
	/** The Constant assetPath. */
	private static final String assetPath = "/content/dam/pankaj.pdf";

	/**
	 * @throws LoginException
	 * @throws WorkflowException
	 */
	@Test
	void testExecute() throws LoginException, WorkflowException,IOException,SolrServerException,RepositoryException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(solrConfig.getContentPageCollectionName()).thenReturn("Collection");
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		lenient().when(WorkFlowData.getPayload()).thenReturn(object);
		lenient().when(object.toString()).thenReturn(assetPath);
		lenient().when(resourceResolver.getResource(assetPath)).thenReturn(resource);
		allPDPIndexingWorkflow.execute(workItem, workflowSession, args);

	}

	/**
	 * To Test the Execute() method If condition.
	 * 
	 * @throws WorkflowException
	 */
	@Test
	void testIfFalseExecuteAndLoginException() throws WorkflowException {
		/*
		 * lenient().when(solrConfig.getContentPageCollectionName()).thenReturn("");
		 * allPDPIndexingWorkflow.execute(workItem, workflowSession, args);
		 */
	}

	/**
	 * To Test the Execute() method LoginException.
	 * 
	 * @throws LoginException
	 * @throws WorkflowException
	 */
	@Test
	void testExecuteLoginException() throws LoginException, WorkflowException {
		/*
		 * lenient().when(solrConfig.getContentPageCollectionName()).thenReturn(
		 * "Collection");
		 * lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).
		 * thenThrow(LoginException.class); allPDPIndexingWorkflow.execute(workItem,
		 * workflowSession, args);
		 */
	}

}
