package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.adobe.granite.workflow.WorkflowException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

/**
 * The Class WorkflowHelperServiceImplTest.
 */
@ExtendWith({ MockitoExtension.class })
class WorkflowHelperServiceImplTest {

	/** The process product document. */
	@InjectMocks
	WorkflowHelperServiceImpl workflowHelperServiceImpl;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;
	
	/** The replicator. */
	@Mock
	Replicator replicator;
	
	/** The resource resolver factory. */
	@Mock
	JobManager jobManager;

	/** The bDB workflow config service. */
	@Mock
	private BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The session. */
	@Mock
	private Session session;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The query. */
	@Mock
	private Query query;

	/** The search result. */
	@Mock
	private SearchResult searchResult;

	/** The resources. */
	@Mock
	private Iterator<Resource> resources;

	/** The resource. */
	@Mock
	private Resource resource;
	@Mock
	com.adobe.granite.asset.api.AssetManager removeAssetMan;
	@Mock
	InputStream assetInputStream;
	@Mock
	ValueFactory valueFactory;
	@Mock
	AssetManager assetMan;
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	/** The result count. */
	private Long resultCount = 1L;

	/** The Constant assetPath. */
	private static final String assetPath = "/content/dam/pankaj.pdf";

	/** The Constant CLINICAL_IMAGE_PATH. */
	private static final String CLINICAL_IMAGE_PATH = "/content/dam/bdb/temp-assets/images/packaging-images/clinical-vial-images";

	/** The Constant BASE_IMAGE_PATH. */
	private static final String BASE_IMAGE_PATH = "/content/dam/bdb/temp-assets/images";

	/** The Constant BASE_DOC_PATH. */
	private static final String BASE_DOC_PATH = "/content/dam/bdb/temp-assets/documents";

	/** The list. */
	private List<String> list = new ArrayList<>();

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	void setUp() throws Exception {
		list.add(BASE_DOC_PATH);
		list.add(BASE_DOC_PATH);
		list.add(BASE_DOC_PATH);
	}

	/**
	 * Replicate resource to publish test.
	 *
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	void replicateResourceToPublishTest()
			throws WorkflowException, LoginException, RepositoryException, ReplicationException {
		workflowHelperServiceImpl.replicateResourceToPublish(session, "");
	}

	/**
	 * Can process path test.
	 */
	@Test
	void canProcessPathTest() {
		workflowHelperServiceImpl.canProcessPath(CLINICAL_IMAGE_PATH, CLINICAL_IMAGE_PATH);
		workflowHelperServiceImpl.canProcessPath(CLINICAL_IMAGE_PATH, "test");
	}

	/**
	 * Assign job in batches if test.
	 *
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	void assignJobInBatchesIfTest()
			throws WorkflowException, LoginException, RepositoryException, ReplicationException {
		when(bDBWorkflowConfigService.getBatchSize()).thenReturn(1);
		workflowHelperServiceImpl.assignJobInBatches(new ArrayList<>(), jobManager, "testJob");
	}

	/**
	 * Assign job in batches else test.
	 *
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	void assignJobInBatchesElseTest()
			throws WorkflowException, LoginException, RepositoryException, ReplicationException {
		when(bDBWorkflowConfigService.getBatchSize()).thenReturn(1);
		workflowHelperServiceImpl.assignJobInBatches(list, jobManager, "testJob");
	}

	/**
	 * Gets the list of PDF assets test.
	 *
	 * @return the list of PDF assets test
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	void getListOfPDFAssetsTest() throws WorkflowException, LoginException, RepositoryException, ReplicationException {
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		when(query.getResult()).thenReturn(searchResult);
		when(searchResult.getTotalMatches()).thenReturn(resultCount);
		when(searchResult.getResources()).thenReturn(resources);
		when(resources.hasNext()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
		when(resources.next()).thenReturn(resource);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		workflowHelperServiceImpl.getListOfPDFAssets(assetPath, resourceResolver, session);
	}

	/**
	 * Gets the list of image assets test.
	 *
	 * @return the list of image assets test
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	void getListOfImageAssetsTest()
			throws WorkflowException, LoginException, RepositoryException, ReplicationException {
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		when(query.getResult()).thenReturn(searchResult);
		when(searchResult.getTotalMatches()).thenReturn(resultCount);
		when(searchResult.getResources()).thenReturn(resources);
		when(resources.hasNext()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
		when(resources.next()).thenReturn(resource);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		when(bDBWorkflowConfigService.getImageBasePath()).thenReturn(BASE_IMAGE_PATH);
		workflowHelperServiceImpl.getListOfImageAssets(BASE_IMAGE_PATH, resourceResolver, session);
	}

	/**
	 * Gets the list of image assets clinical test.
	 *
	 * @return the list of image assets clinical test
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	void getListOfImageAssetsClinicalTest()
			throws WorkflowException, LoginException, RepositoryException, ReplicationException {
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		when(query.getResult()).thenReturn(searchResult);
		when(searchResult.getTotalMatches()).thenReturn(resultCount);
		when(searchResult.getResources()).thenReturn(resources);
		when(resources.hasNext()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
		when(resources.next()).thenReturn(resource);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		when(bDBWorkflowConfigService.getClinicalImagePath()).thenReturn(CLINICAL_IMAGE_PATH);
		workflowHelperServiceImpl.getListOfImageAssets(CLINICAL_IMAGE_PATH, resourceResolver, session);
	}

	/**
	 * Creates the node test.
	 *
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	void createNodeTest() throws WorkflowException, LoginException, RepositoryException, ReplicationException {
		workflowHelperServiceImpl.createNode("", resourceResolver, session, JcrResourceConstants.NT_SLING_FOLDER);
		workflowHelperServiceImpl.addJobForIndexing(list, jobManager, BASE_IMAGE_PATH, BASE_DOC_PATH);
		workflowHelperServiceImpl.addJobForReplication(list, jobManager, BASE_IMAGE_PATH, 0, BASE_DOC_PATH);
	}

	/**
	 * Creates the node with intermediate node test.
	 *
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	//@Test
	void createNodeWithIntermediateNodeTest()
			throws WorkflowException, LoginException, RepositoryException, ReplicationException {
		when(session.nodeExists(Mockito.anyString())).thenReturn(false);
		when(resourceResolver.adaptTo(com.adobe.granite.asset.api.AssetManager.class)).thenReturn(removeAssetMan);
		when(bdbApiEndpointService.getScientificResourceFolder()).thenReturn("scientificFolder");
		when(session.getValueFactory()).thenReturn(valueFactory);
		workflowHelperServiceImpl.createFolderStructure("//", session, resourceResolver);
		workflowHelperServiceImpl.deleteAssetFromDamFolder(assetPath, resourceResolver, session);
		workflowHelperServiceImpl.createAsset(assetMan, assetInputStream, "//", BASE_DOC_PATH, resourceResolver, session);
		workflowHelperServiceImpl.getAssetType(BASE_DOC_PATH);
	}

	/**
	 * Creates the node with intermediate node else test.
	 *
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	//@Test
	void createNodeWithIntermediateNodeElseTest()
			throws WorkflowException, LoginException, RepositoryException, ReplicationException {
		when(session.nodeExists(Mockito.anyString())).thenReturn(true);
		when(bDBWorkflowConfigService.getTempAssetBasePath()).thenReturn(BASE_IMAGE_PATH);
		when(bDBWorkflowConfigService.getProcessedAssetBasePath()).thenReturn(BASE_IMAGE_PATH);
		when(resourceResolver.adaptTo(com.adobe.granite.asset.api.AssetManager.class)).thenReturn(removeAssetMan);
		workflowHelperServiceImpl.createFolderStructure("//", session, resourceResolver);
		workflowHelperServiceImpl.moveAssetFromTempAssetFolder(BASE_DOC_PATH, resourceResolver, session);
	}
}
