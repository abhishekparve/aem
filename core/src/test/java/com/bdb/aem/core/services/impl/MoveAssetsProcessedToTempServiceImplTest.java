package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.WorkflowHelperService;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class MoveAssetsProcessedToTempServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class MoveAssetsProcessedToTempServiceImplTest {
	
	/** The move assets test. */
	@InjectMocks
	MoveAssetsProcessedToTempServiceImpl moveAssetsTest;
	
	/** The session. */
	@Mock
	Session session;
	
	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;
	
	/** The move asset man. */
	@Mock
	com.adobe.granite.asset.api.AssetManager moveAssetMan;
	
	/** The workflow helper service. */
	@Mock
	WorkflowHelperService workflowHelperService;
	
	/** The values. */
	@Mock
	List<String> values;
	
	/** The query param. */
	@Mock
	Map<String, String> queryParam;
	
	/** The query. */
	@Mock
	Query query;
	
	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;
	
	/** The path. */
	private String PATH = "/path/SourcePath/asset.png";
	
	/** The source path. */
	private String SOURCE_PATH = "/SourcePath";
	
	/** The target path. */
	private String TARGET_PATH = "/TargetPath";

	/**
	 * Test move assets.
	 *
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testMoveAssets() throws RepositoryException {
		moveAssetsTest.moveAssets(session, resourceResolver, queryParam);
	}

	/**
	 * Test get all resources.
	 *
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testGetAllResources() throws RepositoryException {
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(workflowHelperService.getResourcesFromQuery(query)).thenReturn(values);
		moveAssetsTest.getAllResources(SOURCE_PATH, TARGET_PATH, resourceResolver, session, 0);
	}
	
	/**
	 * Test move asset to temp asset folder.
	 *
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testMoveAssetToTempAssetFolder() throws RepositoryException {
		lenient().when(resourceResolver
				.adaptTo(com.adobe.granite.asset.api.AssetManager.class)).thenReturn(moveAssetMan);
		moveAssetsTest.moveAssetToTempAssetFolder(PATH, SOURCE_PATH, TARGET_PATH, resourceResolver, session);
	}

}
