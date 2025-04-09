package com.bdb.aem.core.workflows;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;

/**
 * @author knarayansingh
 *
 */
@ExtendWith({ MockitoExtension.class })
public class AllContentPageIndexingWorkflowTest {

	/** AllContentPageIndexingWorkflow Object */
	@InjectMocks
	AllContentPageIndexingWorkflow allContentPageIndexingWorkflow;

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

	/** The workflow data. */
	@Mock
	WorkflowData workflowData;

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

	/** The Session */
	@Mock
	Session session;

	@Mock
	Resource mResource;

	@Mock
	Page page;
	
	/**
	 * Sets the up.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LoginException 
	 */
	@BeforeEach
	void setUp() throws IOException, LoginException {
		lenient().when(workItem.getWorkflowData()).thenReturn(workflowData);
		lenient().when(workflowData.getPayload()).thenReturn(args);
		lenient().when(solrConfig.getBasePagePath()).thenReturn("args");
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
	}

	/**
	 * To Test the Execute() method.
	 * 
	 * @throws LoginException
	 * @throws WorkflowException
	 */
	@Test
	void testExecute() throws LoginException, WorkflowException {
		lenient().when(resourceResolver.getResource("/content")).thenReturn(mResource);
		lenient().when(mResource.getParent()).thenReturn(mResource);
		lenient().when(mResource.getPath()).thenReturn("/content");
		lenient().when(mResource.getParent().getPath()).thenReturn("/content");
		lenient().when(resourceResolver.getResource(mResource.getParent().getPath()).adaptTo(Page.class))
				.thenReturn(page);
		lenient().when(solrConfig.getContentPageCollectionName()).thenReturn("Collection");
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		allContentPageIndexingWorkflow.execute(workItem, workflowSession, args);
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
		allContentPageIndexingWorkflow.execute(workItem, workflowSession, args);
	}


}
