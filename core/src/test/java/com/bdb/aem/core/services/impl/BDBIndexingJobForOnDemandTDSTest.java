package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.ProcessProductDocumentService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.dam.api.Asset;

@ExtendWith({ MockitoExtension.class })
public class BDBIndexingJobForOnDemandTDSTest {
	@InjectMocks
	BDBIndexingJobForOnDemandTDS BDBIndexingJobForOnDemandTDS;
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The process product document service. */
	@Mock
	ProcessProductDocumentService processProductDocumentService;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;
	
	/** The b DB workflow config service. */
	@Mock
	BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The resource resolver. */

	@Mock
	ResourceResolver serviceResolver;
	@Mock
	Resource resource;
	@Mock
	private Asset finalAsset;
	
	/** The session. */

	@Mock
	Session session;

	/** The current node. */
	@Mock
	private Node currentNode;
	@Mock
	Job job;

	@BeforeEach
	void setUp() throws Exception {
		lenient().when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap()))
				.thenReturn(serviceResolver);
		lenient().when(serviceResolver.getResource("/content/dam/pankaj.pdf")).thenReturn(resource);
	}

	@Test
	void TestActivate() throws SolrServerException, LoginException, RepositoryException {
		BDBIndexingJobForOnDemandTDS.activate();
	}
	
	@Test
	void testProcess() throws SolrServerException, LoginException, RepositoryException {
		lenient().when(job.getProperty(CommonConstants.PAYLOAD_PATHS, ArrayList.class)).thenReturn(new ArrayList());
		lenient().when(job.getProperty("assetType", String.class)).thenReturn("string");
		BDBIndexingJobForOnDemandTDS.process(job);
	}
	

}
