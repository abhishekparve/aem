package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletInputStream;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.models.AmountSummaryModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;

@ExtendWith(MockitoExtension.class)
public class DeleteFaultyProductsTest {
	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The image text model. */
	@InjectMocks
	DeleteFaultyProducts deleteFaultyProducts;

	@Mock
	Resource resource;
	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;
	/** The print writer. */
	@Mock
	PrintWriter printWriter;
	@Mock
	BufferedReader reader;
	@Mock
	Session session;
	/** The stream. */
	@Mock
	ServletInputStream stream;

	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;

	@Mock
	Replicator replicator;
	@Mock
	Node node;
	@Mock
	TagManager tagManager;
	@Mock
	Tag tag;
	@Mock
	HttpSolrClient server;
	@Mock
	Iterator<Resource> children;

	@Mock
	SolrSearchService solrSearchService;

	@Test
	public void testCheckProductInLookup() throws IOException, RepositoryException, ReplicationException {
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "12345", "materialNumber"))
				.thenReturn(resource);
		deleteFaultyProducts.checkProductInLookup(resourceResolver, "12345", "materialNumber");
		deleteFaultyProducts.getTagPath("/content/bdb");
	}

	@Test
	public void tesetBasegProductDeleteConditions() throws IOException, RepositoryException, ReplicationException {
		ArrayList<String> variantList = new ArrayList<String>();
		variantList.add("variantList");
		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "variantList",
				CommonConstants.MATERIAL_NUMBER)).thenReturn(resource);
		lenient().when(resource.adaptTo(Node.class)).thenReturn(node);
		lenient().when(resource.getPath()).thenReturn("/content/bdb");
		lenient().when(tagManager.resolve("/content/bdb")).thenReturn(tag);
		deleteFaultyProducts.checkProductInLookup(resourceResolver, "12345", "materialNumber");
		deleteFaultyProducts.deleteVariants(variantList, resourceResolver, session);
	}

	@Test
	public void testDeleteLookupPath() throws IOException, RepositoryException, ReplicationException {
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/variant/123f/12345"))
				.thenReturn(resource);
		lenient().when(resource.adaptTo(Node.class)).thenReturn(node);
		deleteFaultyProducts.deleteLookupPath("12345", "materialNumber", resourceResolver, session);
		// deleteFaultyProducts.deleteLookupPath("12345", "baseMaterialNumber",
		// resourceResolver, session);
	}

	@Test
	public void testDeleteBaseProducts() throws IOException, RepositoryException, ReplicationException {
		ArrayList<String> baseProductList = new ArrayList<String>();
		baseProductList.add("baseProductList");
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resource.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "baseProductList" + "_base",
				CommonConstants.BASE_MATERIAL_NUMBER)).thenReturn(resource);
		lenient().when(resource.listChildren()).thenReturn(children);
		lenient().when(resource.getName()).thenReturn("name");
		lenient().when(children.hasNext()).thenReturn(true, false);
		lenient().when(children.next()).thenReturn(resource);
		lenient().when(resource.getName()).thenReturn("childname");
		lenient().when(resource.adaptTo(Node.class)).thenReturn(node);
		lenient().when(resource.getPath()).thenReturn("/content/bdb");
		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(tagManager.resolve("/content/bdb")).thenReturn(tag);
		deleteFaultyProducts.deleteBaseProducts(baseProductList, resourceResolver, null, session);
	}

	@Test
	public void testDeleteBaseProductsElse() throws IOException, RepositoryException, ReplicationException, Exception {
		ArrayList<String> baseProductList = new ArrayList<String>();
		baseProductList.add("baseProductList");
		ArrayList<HttpSolrClient> solrClients = new ArrayList<>();
		solrClients.add(server);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(solrSearchService.getAllSolrClients()).thenReturn(solrClients);
		deleteFaultyProducts.deleteBaseProducts(baseProductList, resourceResolver, null, session);
		deleteFaultyProducts.deleteSolrIndexes(baseProductList);
	}

	@Test
	public void testBaseProductDeleteConditions() throws IOException, RepositoryException, ReplicationException {

		ArrayList<String> productList = new ArrayList<String>();
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resource.listChildren()).thenReturn(children);
		lenient().when(resource.getName()).thenReturn("name");
		lenient().when(children.hasNext()).thenReturn(true, false);
		lenient().when(children.next()).thenReturn(resource);
		lenient().when(resource.getName()).thenReturn("childname");
		deleteFaultyProducts.baseProductDeleteConditions(resource);
	}

}
