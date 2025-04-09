package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

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
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.UpdateProductSchemaService;
import com.bdb.aem.core.services.solr.FetchingCloneService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

// TODO: Auto-generated Javadoc
/**
 * The Class CompareProductsServletTest.
 */
@ExtendWith(MockitoExtension.class)
class CompareProductsServletTest {

	/** The compare products servlet. */
	@InjectMocks
	CompareProductsServlet compareProductsServlet;

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

	/** The next resource. */
	@Mock
	Resource parentResource, varientResource, nextResource, hpNodeResource, variantParentResource,
			variantParentChildResource, countryRes, variantResource, sapResource, regionResource, sapCCResource,
			getVariantHpResourceOutcome;;

	/** The query. */
	@Mock
	Query query;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The search result. */
	@Mock
	SearchResult searchResult;

	/** The varient value map. */
	@Mock
	ValueMap varientValueMap;

	/** The base hp map. */
	@Mock
	ValueMap baseHpMap;

	/** The solr config. */
	@Mock
	BDBSearchEndpointService solrConfig;

	/** The fetching clone service. */
	@Mock
	FetchingCloneService fetchingCloneService;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The catalog structure update service. */
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
	@Mock
	Object obj;

	/** The country. */
	private String country = "{\"baseStore\":{\"name\":\"BDB Canada Store\",\"uid\":\"CA\"},\"salesUnit\":{},\"derivedProductStatus\":\"DISPLAYONLY\",\"validityStartDate\":\"2016-04-28T00:00:00Z\"}";

	/** The dye name. */
	private String dyeName = "[{\"dyeName\":\"PE\",\"formatStatement\":\"R\",\"emmisionmax\":\"576\",\"excitationmax\":\"496, 566\",\"laserColor\":\"Yellow-Green\",\"laserWavelength\":\"488, 532, 561\"}]";

	/** The clone. */
	private String clone = "[{\"polyclonal\":\"FALSE\",\"specificity\":\"\",\"hostStrain\":\"\",\"entrezGeneId\":\"\",\"isoType\":\"IgG1, λ\",\"tdsCloneDisplayName\":\"DimerX/H-2Kb\",\"workshopNumber\":\"\",\"immunogen\":\"\",\"tdsCloneName\":\"DimerX/H-2Kb\",\"hostSpecies\":\"Mouse\",\"molecularWeight\":\"\"}]";

	/**
	 * Inits the.
	 *
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	@BeforeEach
	public void init() throws IOException, SolrServerException, RepositoryException {

		lenient().when(request.getReader()).thenReturn(reader);
		line = "{\"country\":\"global\",\r\n" + " \"productID\":[\"644444\"] }";
		requestObject = new JsonObject();
		requestObject.addProperty("test", "Test");
		line2 = null;
		lenient().when(reader.readLine()).thenReturn(line, line2);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(solrSearchService.getHpNodeResource("644444", "us", resourceResolver))
				.thenReturn(varientResource);
		lenient().when(varientResource.getValueMap()).thenReturn(varientValueMap);
		lenient()
				.when(fetchingCloneService.getFormatsData(Mockito.anyString(), Mockito.any(), Mockito.any(),
						Mockito.anyString(), Mockito.anyString()))
				.thenReturn(
						"[{\"catalogNumber\":\"663991\",\"imageUrl\":\"\",\"productUrl\":\"/content/bdb/{region}/{country}/{language-country}/products/reagents/flow-cytometry-reagents/clinical-diagnostics/single-color-antibodies-asr/-ivd/-ce-ivd/pdp.cd64-bv605.663991.html\",\"name\":\"CD64 BV605\",\"brand\":\"BD Horizonâ„¢\",\"dyeName\":\"BV605\",\"cloneId\":\"10.1\",\"regulatoryStatus\":\"ASR\",\"laserColor\":\"Violet\",\"isComparable\":true},{\"catalogNumber\":\"663996\",\"imageUrl\":\"\",\"productUrl\":\"/content/bdb/{region}/{country}/{language-country}/products/reagents/flow-cytometry-reagents/clinical-diagnostics/single-color-antibodies-asr/-ivd/-ce-ivd/pdp.cd64-percp-cy55.663996.html\",\"name\":\"CD64 PerCP-Cy5.5\",\"brand\":\"BD Horizonâ„¢\",\"dyeName\":\"PerCP-Cy5.5\",\"cloneId\":\"10.1\",\"regulatoryStatus\":\"ASR\",\"laserColor\":\"Blue\",\"isComparable\":true},{\"catalogNumber\":\"555525\",\"imageUrl\":\"\",\"productUrl\":\"/content/bdb/{region}/{country}/{language-country}/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/pdp.purified-mouse-anti-human-cd64.555525.html\",\"name\":\"Purified Mouse Anti-Human CD64\",\"brand\":\"BD Pharmingen™\",\"dyeName\":\"Purified\",\"cloneId\":\"10.1\",\"regulatoryStatus\":\"RUO\",\"laserColor\":\"\",\"isComparable\":true},{\"catalogNumber\":\"555526\",\"imageUrl\":\"\",\"productUrl\":\"/content/bdb/{region}/{country}/{language-country}/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/pdp.biotin-mouse-anti-human-cd64.555526.html\",\"name\":\"Biotin Mouse Anti-Human CD64\",\"brand\":\"BD Pharmingen™\",\"dyeName\":\"Biotin\",\"cloneId\":\"10.1\",\"regulatoryStatus\":\"RUO\",\"laserColor\":\"\",\"isComparable\":true},{\"catalogNumber\":\"555527\",\"imageUrl\":\"\",\"productUrl\":\"/content/bdb/{region}/{country}/{language-country}/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/pdp.fitc-mouse-anti-human-cd64.555527.html\",\"name\":\"FITC Mouse Anti-Human CD64\",\"brand\":\"BD Pharmingen™\",\"dyeName\":\"FITC\",\"cloneId\":\"10.1\",\"regulatoryStatus\":\"RUO\",\"laserColor\":\"Blue\",\"isComparable\":true},{\"catalogNumber\":\"560970\",\"imageUrl\":\"\",\"productUrl\":\"/content/bdb/{region}/{country}/{language-country}/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/pdp.fitc-mouse-anti-human-cd64.560970.html\",\"name\":\"FITC Mouse Anti-Human CD64\",\"brand\":\"BD Pharmingen™\",\"dyeName\":\"FITC\",\"cloneId\":\"10.1\",\"regulatoryStatus\":\"RUO\",\"laserColor\":\"Blue\",\"isComparable\":true},{\"catalogNumber\":\"558592\",\"imageUrl\":\"\",\"productUrl\":\"/content/bdb/{region}/{country}/{language-country}/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/pdp.pe-mouse-anti-human-cd64.558592.html\",\"name\":\"PE Mouse anti-Human CD64 \",\"brand\":\"BD Pharmingen™\",\"dyeName\":\"PE\",\"cloneId\":\"10.1\",\"regulatoryStatus\":\"RUO\",\"laserColor\":\"Yellow-Green\",\"isComparable\":true},{\"catalogNumber\":\"561926\",\"imageUrl\":\"\",\"productUrl\":\"/content/bdb/{region}/{country}/{language-country}/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/pdp.pe-mouse-anti-human-cd64.561926.html\",\"name\":\"PE Mouse anti-Human CD64 \",\"brand\":\"BD Pharmingen™\",\"dyeName\":\"PE\",\"cloneId\":\"10.1\",\"regulatoryStatus\":\"RUO\",\"laserColor\":\"Yellow-Green\",\"isComparable\":true},{\"catalogNumber\":\"561188\",\"imageUrl\":\"\",\"productUrl\":\"/content/bdb/{region}/{country}/{language-country}/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/pdp.alexa-fluor®-700-mouse-anti-human-cd64.561188.html\",\"name\":\"Alexa Fluor® 700 Mouse anti-Human CD64 \",\"brand\":\"BD Pharmingen™\",\"dyeName\":\"Alexa 700\",\"cloneId\":\"10.1\",\"regulatoryStatus\":\"RUO\",\"laserColor\":\"Red\",\"isComparable\":true},{\"catalogNumber\":\"561191\",\"imageUrl\":\"\",\"productUrl\":\"/content/bdb/{region}/{country}/{language-country}/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/pdp.pe-cy™7-mouse-anti-human-cd64.561191.html\",\"name\":\"PE-Cy™7 Mouse anti-Human CD64\",\"brand\":\"BD Pharmingen™\",\"dyeName\":\"PE-Cy7\",\"cloneId\":\"10.1\",\"regulatoryStatus\":\"RUO\",\"laserColor\":\"ultraviolet\",\"isComparable\":true}]");
	}

	/**
	 * Testdo post.
	 *
	 * @throws LoginException      the login exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testdoPost() throws LoginException, IOException, SolrServerException, RepositoryException {

		JsonObject productJson = new JsonObject();
		productJson.addProperty("catalogNumber", "644444");
		lenient().when(request.getParameter("country")).thenReturn("us");
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		/* getProductFromLookUp code start */
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "644444",
				CommonConstants.MATERIAL_NUMBER)).thenReturn(variantResource);
		lenient().when(variantResource.getChild(Mockito.anyString())).thenReturn(regionResource);
		lenient().when(regionResource.getChild(Mockito.anyString())).thenReturn(sapResource);
		lenient().when(sapResource.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(varientValueMap.containsKey(Mockito.anyString())).thenReturn(true);
		lenient().when(varientValueMap.get("US", String.class)).thenReturn(country);
		/* getProductFromLookUp code end */
		/* getBaseHpResourceFromLookUp code start */
		lenient().when(variantResource.getParent()).thenReturn(variantParentResource);
		lenient().when(variantParentResource.getChild(Mockito.anyString())).thenReturn(variantParentChildResource);
		lenient().when(variantParentChildResource.getParent()).thenReturn(variantParentResource);
		/* getBaseHpResourceFromLookUp code end */
		lenient().when(variantParentChildResource.adaptTo(ValueMap.class)).thenReturn(baseHpMap);
		lenient().when(baseHpMap.containsKey(Mockito.anyString())).thenReturn(true);
		lenient().when(baseHpMap.get(Mockito.anyString())).thenReturn(dyeName).thenReturn(clone);
		lenient().when(updateProductSchemaService.getProductSKUDetails(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(productJson);
		lenient().when(response.getWriter()).thenReturn(printWriter);
		printWriter.write("");
		compareProductsServlet.doPost(request, response);

	}

	
	@Test
	void testGetValueMapProperty() throws IOException, LoginException, Exception {
		lenient().when(varientValueMap.get(clone)).thenReturn(obj);
		compareProductsServlet.getValueMapProperty(varientValueMap, clone);
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
		compareProductsServlet.doPost(request, response);
	}
}
