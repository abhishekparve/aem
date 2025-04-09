package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

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
import org.osgi.service.component.annotations.Reference;

import com.adobe.granite.workflow.WorkflowException;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.ProcessProductDocumentService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.tagging.TagManager;

@ExtendWith({ MockitoExtension.class })
public class BDBIndexingJobTest {
	@InjectMocks
	BDBIndexingJob BDBIndexingJob;
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
	/** The Constant assetPath. */
	private static final String assetPath = "/content/dam/pankaj.pdf";
	List<String> list = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		lenient().when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap()))
				.thenReturn(serviceResolver);
		lenient().when(serviceResolver.getResource("/content/dam/pankaj.pdf")).thenReturn(resource);
	}

	@Test
	void TestVideoThumbnailAsset() throws SolrServerException, LoginException, RepositoryException {
		String assetType = "videoThumbnailAsset";
		list.add("/content/dam/pankaj.pdf");
		lenient().when(resource.adaptTo(Asset.class)).thenReturn(finalAsset);
		BDBIndexingJob.processList(list, assetType);
	}

	@Test
	void TestPdfAsset() throws SolrServerException, LoginException, RepositoryException {
		String assetType = "pdfAsset";
		list.add("/content/dam/pankaj.pdf");
		lenient().when(resource.adaptTo(Asset.class)).thenReturn(finalAsset);
		BDBIndexingJob.processList(list, assetType);
	}

	@Test
	void TestCommerceContent() throws SolrServerException, LoginException, RepositoryException {
		String assetType = "commerceContent";
		list.add("/content/dam/pankaj.pdf");
		lenient().when(resource.adaptTo(Asset.class)).thenReturn(finalAsset);
		BDBIndexingJob.processList(list, assetType);
	}

	@Test
	void TestActivate() throws SolrServerException, LoginException, RepositoryException {
		BDBIndexingJob.activate();
	}
	
	@Test
	void testProcess() throws SolrServerException, LoginException, RepositoryException {
		lenient().when(job.getProperty(CommonConstants.PAYLOAD_PATHS, ArrayList.class)).thenReturn(new ArrayList());
		lenient().when(job.getProperty("assetType", String.class)).thenReturn("string");
		BDBIndexingJob.process(job);
	}
	
	@Test
	void testCommerceContentPDF() throws SolrServerException, LoginException, RepositoryException {
		String assetType = "commerceContentPdf";
		list.add("/content/dam/pankaj.pdf");
		lenient().when(resource.adaptTo(Asset.class)).thenReturn(finalAsset);
		lenient().when(bDBWorkflowConfigService.getVarCommerceBasePath()).thenReturn("/content/commerce/products/bdb/products/");
		lenient().when(bDBWorkflowConfigService.getDamAssetBasePath()).thenReturn("/content/dam/bdb/products/global/");
		lenient().when(serviceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(session.nodeExists(list.get(0))).thenReturn(true);
		BDBIndexingJob.processList(list, assetType);
	}


}
