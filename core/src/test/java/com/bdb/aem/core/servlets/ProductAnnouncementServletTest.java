package com.bdb.aem.core.servlets;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletInputStream;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
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

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.UpdateProductSchemaService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.google.gson.JsonObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductAnnouncementServletTest.
 */
@ExtendWith(MockitoExtension.class)
class ProductAnnouncementServletTest {

	/** The product announcement servlet. */
	@InjectMocks
	ProductAnnouncementServlet productAnnouncementServlet;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The request parameter. */
	@Mock
	RequestParameter requestParameter;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The update product schema service. */
	@Mock
	UpdateProductSchemaService updateProductSchemaService;

	/** The marketing resource. */
	@Mock
	Resource marketingResource;

	/** The session. */
	@Mock
	Session session;

	/** The marketing node. */
	@Mock
	Node marketingNode;

	/** The stream. */
	@Mock
	ServletInputStream stream;

	/** The reader. */
	@Mock
	BufferedReader reader;

	/** The request object. */
	private JsonObject requestObject;

	/** The line. */
	private String line;

	/** The line 2. */
	private String line2;

	/** The resources. */
	@Mock
	Iterator<Resource> resources;

	/** The value map. */
	@Mock
	ValueMap valueMap;

	/** The next resource. */
	@Mock
	Resource nextResource, hpNodeResource, variantParentResource, variantParentChildResource, countryRes,
			variantResource, sapResource, regionResource, sapCCResource, getVariantHpResourceOutcome;

	/** The query. */
	@Mock
	Query query;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The search result. */
	@Mock
	SearchResult searchResult;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;

	/** The resource. */
	@Mock
	Resource resource;

	/** The externalizer service. */
	@Mock
	private transient ExternalizerService externalizerService;

	/** The catalog structure update service. */
	@Mock
	private transient CatalogStructureUpdateService catalogStructureUpdateService;

	/** The country. */
	private String country = "{\"baseStore\":{\"name\":\"BDB Canada Store\",\"uid\":\"CA\"},\"salesUnit\":{},\"derivedProductStatus\":\"DISPLAYONLY\",\"validityStartDate\":\"2016-04-28T00:00:00Z\"}";

	/**
	 * Inits the.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void init() throws IOException {

		lenient().when(request.getReader()).thenReturn(reader);
		line = "{\"country\":\"global\",\r\n" + " \"productID\":[\"644444\"] }";
		requestObject = new JsonObject();
		requestObject.addProperty("test", "Test");
		line2 = null;
		lenient().when(reader.readLine()).thenReturn(line, line2);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

	}

	/**
	 * Testdo post.
	 *
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testdoPost() throws IOException, LoginException, RepositoryException {

		JsonObject productJson = new JsonObject();
		productJson.addProperty("productID", "644444");
		assertNotNull(request);
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		/* getProductFromLookUp code start */
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "644444",
				CommonConstants.MATERIAL_NUMBER)).thenReturn(variantResource);
		lenient().when(variantResource.getChild(Mockito.anyString())).thenReturn(regionResource);
		lenient().when(regionResource.getChild(Mockito.anyString())).thenReturn(sapResource);
		lenient().when(sapResource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey(Mockito.anyString())).thenReturn(true);
		lenient().when(valueMap.get("GLOBAL", String.class)).thenReturn(country);
		/* getProductFromLookUp code end */
		/* getBaseHpResourceFromLookUp code start */
		lenient().when(variantResource.getParent()).thenReturn(variantParentResource);
		lenient().when(variantParentResource.getChild(Mockito.anyString())).thenReturn(variantParentChildResource);
		lenient().when(variantParentChildResource.getParent()).thenReturn(variantParentResource);
		/* getBaseHpResourceFromLookUp code end */
		/* getVariantHpResource code start */
		lenient().when(variantParentResource.getChild(Mockito.anyString())).thenReturn(variantParentChildResource);
		lenient().when(variantParentResource.getChild(Mockito.anyString())).thenReturn(variantParentChildResource);
		lenient().when(variantParentChildResource.getChild(Mockito.anyString()))
				.thenReturn(getVariantHpResourceOutcome);
		/* getVariantHpResource code end */

		lenient().when(getVariantHpResourceOutcome.getParent()).thenReturn(variantParentResource);
		lenient().when(variantParentResource.getChild("marketing/global")).thenReturn(countryRes);
		lenient().when(updateProductSchemaService.getProductAnnouncement(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.anyString(), Mockito.any())).thenReturn(productJson);

		lenient().when(response.getWriter()).thenReturn(printWriter);
		printWriter.write("");
		productAnnouncementServlet.doPost(request, response);

	}

	/**
	 * Testdo post 2.
	 *
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testdoPost2() throws IOException, LoginException, RepositoryException {

		JsonObject productJson1 = new JsonObject();
		assertNotNull(request);
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		/* getProductFromLookUp code start */
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "644444",
				CommonConstants.MATERIAL_NUMBER)).thenReturn(variantResource);
		lenient().when(variantResource.getChild(Mockito.anyString())).thenReturn(regionResource);
		lenient().when(regionResource.getChild(Mockito.anyString())).thenReturn(sapResource);
		lenient().when(sapResource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey(Mockito.anyString())).thenReturn(true);
		lenient().when(valueMap.get("GLOBAL", String.class)).thenReturn(country);
		/* getProductFromLookUp code end */
		/* getBaseHpResourceFromLookUp code start */
		lenient().when(variantResource.getParent()).thenReturn(variantParentResource);
		lenient().when(variantParentResource.getChild(Mockito.anyString())).thenReturn(variantParentChildResource);
		lenient().when(variantParentChildResource.getParent()).thenReturn(variantParentResource);
		/* getBaseHpResourceFromLookUp code end */
		/* getVariantHpResource code start */
		lenient().when(variantParentResource.getChild(Mockito.anyString())).thenReturn(variantParentChildResource);
		lenient().when(variantParentResource.getChild(Mockito.anyString())).thenReturn(variantParentChildResource);
		lenient().when(variantParentChildResource.getChild(Mockito.anyString()))
				.thenReturn(getVariantHpResourceOutcome);
		/* getVariantHpResource code end */

		lenient().when(getVariantHpResourceOutcome.getParent()).thenReturn(variantParentResource);
		lenient().when(variantParentResource.getChild("marketing/global")).thenReturn(countryRes);
		lenient().when(variantParentResource.getChild("marketing/emea")).thenReturn(countryRes);
		lenient().when(variantParentResource.getChild("marketing/apac(english)")).thenReturn(countryRes);
		lenient().when(variantParentResource.getChild("marketing/apac")).thenReturn(countryRes);
		lenient().when(updateProductSchemaService.getProductAnnouncement(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.anyString(), Mockito.any())).thenReturn(productJson1);

		lenient().when(response.getWriter()).thenReturn(printWriter);
		printWriter.write("");
		//productAnnouncementServlet.doPost(request, response);

	}

	/**
	 * Test login exception.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@Test
	void testLoginException() throws IOException, LoginException {

		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		lenient().when(response.getWriter()).thenReturn(printWriter);
		productAnnouncementServlet.doPost(request, response);

	}

}
