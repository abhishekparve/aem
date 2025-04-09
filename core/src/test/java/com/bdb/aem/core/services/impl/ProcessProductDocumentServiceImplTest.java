
package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.bean.ExcelNodeBean;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.dam.api.Rendition;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.tagging.TagManager;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * Junit for ProcessProductDocumentServiceImplTest.
 */

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ProcessProductDocumentServiceImplTest {

	/** The process product document. */

	@InjectMocks
	ProcessProductDocumentServiceImpl ProcessProductDocumentServiceImpl;

	/** The bDB workflow config service. */

	@Mock
	private BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The workflow helper service. */

	@Mock
	private WorkflowHelperService workflowHelperService;

	/** The resource resolver. */

	@Mock
	private ResourceResolver resourceResolver;

	/** The tag manager. */

	@Mock
	private TagManager tagManager;

	/** The session. */

	@Mock
	private Session session;

	/** The current node. */

	@Mock
	private Node currentNode;

	/** The resources. */

	List<Resource> resources1 = new ArrayList<>();

	/** The resource list. */
	List<Resource> resourceList = new ArrayList<>();

	/** The solr search service. */

	@Mock
	SolrSearchService solrSearchService;

	/** The rendition. */

	@Mock
	private Rendition rendition;

	/** The args. */

	@Mock
	private MetaDataMap args;

	/** The query builder. */

	@Mock
	QueryBuilder queryBuilder;

	/** The query. */

	@Mock
	private Query query;

	/** The search result. */

	@Mock
	private SearchResult searchResult;

	/** The asset man. */

	@Mock
	private AssetManager assetMan;

	/** The remove asset man. */

	@Mock
	private com.adobe.granite.asset.api.AssetManager removeAssetMan;

	/** The current asset. */

	@Mock
	private Asset currentAsset;

	/** The final asset. */

	@Mock
	private Asset finalAsset;

	/** The asset stream. */

	@Mock
	private InputStream assetStream;

	/** The resources. */

	@Mock
	private Iterator<Resource> resources;

	/** The properties. */

	@Mock
	private ValueMap properties;

	/** The excel node bean list. */
	List<ExcelNodeBean> excelNodeBeanList = new ArrayList<ExcelNodeBean>();

	/** The resource. */

	@Mock
	private Resource resource, solrResourceList, solrResource, hpResource, variantResource, baseResource, assetResource;

	/** The value factory. */

	@Mock
	private ValueFactory valueFactory;

	/** The asset binary. */

	@Mock
	private Binary assetBinary;

	/** The replicator. */

	@Mock
	Replicator replicator;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The excel node bean. */
	@Mock
	ExcelNodeBean excelNodeBean;

	/** The result count. */

	private Long resultCount = 1L;

	/** The Constant BASE_FILE_NAME. */

	private static final String BASE_FILE_NAME = "baseFileName";

	/** The Constant assetPath. */

	private static final String assetPath = "/content/dam/pankaj.pdf";

	/** The Constant MATARIAL_NUM. */

	private static final String MAT_NUM = "matNumber";

	/** The Constant DOC_PART_ID. */

	private static final String DOC_PART_ID = "docPartId";

	/** The Constant DOC_TYPE. */

	private static final String DOC_TYPE = "docType";

	/** The Constant REGION. */

	private static final String REGION = "region";

	/** The array. */

	private String[] array = { "test", "test2" };

	/** The Constant HP_NODE_PATH. */

	private static final String HP_NODE_PATH = "/var/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/644444/hp";

	/** The Constant DAM_COMMERCE_BASE. */

	private static final String DAM_COMMERCE_BASE = "dam/products/global/";

	/** The Constant VAR_COMMERCE_BASE. */

	private static final String VAR_COMMERCE_BASE = "/var/commerce/products/bdb/products/";

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	void setUp() throws Exception {
		resources1.add(solrResource);
		// resourceList.add(excelResource);
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);

	}

	/**
	 * Execute test.
	 *
	 * @throws WorkflowException         the workflow exception
	 * @throws LoginException            the login exception
	 * @throws RepositoryException       the repository exception
	 * @throws ReplicationException      the replication exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 */
	//@Test
	void executeTest() throws WorkflowException, LoginException, RepositoryException, ReplicationException,
			InvalidTagFormatException {
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		when(query.getResult()).thenReturn(searchResult);
		when(searchResult.getTotalMatches()).thenReturn(resultCount);
		when(searchResult.getResources()).thenReturn(resources);
		when(resources.hasNext()).thenReturn(true).thenReturn(false);
		when(resources.next()).thenReturn(resource);
		when(solrSearchService.getHpNodeResources("materialNumber", "matNumber", resourceResolver))
				.thenReturn(resources1);

		lenient().when(solrResource.getParent()).thenReturn(variantResource);
		when(variantResource.getParent()).thenReturn(baseResource);
		when(baseResource.getChild(Mockito.anyString())).thenReturn(hpResource);
		when(hpResource.adaptTo(ValueMap.class)).thenReturn(properties);

		when(resource.adaptTo(ValueMap.class)).thenReturn(properties);
		when(properties.get(REGION, String[].class)).thenReturn(array);
		when(properties.get(BASE_FILE_NAME, StringUtils.EMPTY)).thenReturn(BASE_FILE_NAME);
		when(properties.get(MAT_NUM, StringUtils.EMPTY)).thenReturn(MAT_NUM);
		when(properties.get(DOC_PART_ID, StringUtils.EMPTY)).thenReturn(DOC_PART_ID);
		when(properties.get(DOC_TYPE, StringUtils.EMPTY)).thenReturn(DOC_TYPE);
		when(solrResource.getPath()).thenReturn(HP_NODE_PATH);
		when(bDBWorkflowConfigService.getVarCommerceBasePath()).thenReturn(VAR_COMMERCE_BASE);
		when(bDBWorkflowConfigService.getDamAssetBasePath()).thenReturn(DAM_COMMERCE_BASE);
		when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		when(resourceResolver.adaptTo(AssetManager.class)).thenReturn(assetMan);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(assetResource);
		when(assetResource.adaptTo(Asset.class)).thenReturn(currentAsset);
		when(currentAsset.getOriginal()).thenReturn(rendition);
		when(rendition.getStream()).thenReturn(assetStream);
		when(session.getValueFactory()).thenReturn(valueFactory);
		when(valueFactory.createBinary(assetStream)).thenReturn(assetBinary);
		when(assetMan.createOrUpdateAsset(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
				Mockito.anyBoolean())).thenReturn(finalAsset);
		when(assetResource.adaptTo(Node.class)).thenReturn(currentNode);
		when(currentNode.getPath()).thenReturn("content/dam/pankaj.pdf");
		lenient().when(resourceResolver.adaptTo(com.adobe.granite.asset.api.AssetManager.class))
				.thenReturn(removeAssetMan);
		ProcessProductDocumentServiceImpl.processDocuments(assetPath, resourceResolver, session);
	}
}
