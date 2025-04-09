package com.bdb.aem.core.servlets.solr;

import static org.mockito.Mockito.lenient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.RepositoryException;
import javax.json.Json;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.settings.SlingSettingsService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.models.BoostrulesModel;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.EligibleProductsApiService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr.Configuration;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

// TODO: Auto-generated Javadoc
/**
 * The Class FetchingDataFromSolrTest.
 *
 * @author knarayansingh
 */
@ExtendWith(MockitoExtension.class)
public class FetchingDataFromSolrTest {
	/**
	 * FetchingDataFromSolr Object.
	 */
	@InjectMocks
	FetchingDataFromSolr fetchingDataFromSolr;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;

	/** The solr config. */
	@Mock
	BDBSearchEndpointService solrConfig;

	/** The sling settings service. */
	@Mock
	SlingSettingsService slingSettingsService;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The SlingHttpServletRequest. */
	@Mock
	SlingHttpServletRequest request;

	/** The SlingHttpServletResponse. */
	@Mock
	SlingHttpServletResponse response;

	/** The PrintWriter. */
	@Mock
	PrintWriter writer;

	/** The HttpSolrClient. */
	@Mock
	HttpSolrClient server;

	/** The ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The EligibleProductsApiService. */
	@Mock
	EligibleProductsApiService eligibleProductsApiService;

	/** The ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The ServletInputStream. */
	@Mock
	ServletInputStream stream;

	/** The RequestPathInfo. */
	@Mock
	RequestPathInfo requestPathInfo;

	/** The QueryResponse. */
	@Mock
	QueryResponse sitesQueryResponse;

	/** The SolrQuery. */
	@Mock
	SolrQuery solrQuery;

	/** The SolrDocumentList. */
	@Mock
	SolrDocumentList sitesSolrDocs;

	/** The Iterator. */
	@Mock
	Iterator<SolrDocument> iterator;

	/** The SolrDocument. */
	@Mock
	SolrDocument solrDocument;

	/** The Iterator. */
	@Mock
	Iterator<Entry<String, Object>> itr;

	/** The List. */
	@Mock
	List<FacetField> sitesFacetFieldList;

	/** The Iterator. */
	@Mock
	Iterator<FacetField> facetItr;

	/** The FacetField. */
	@Mock
	FacetField next;
	@Mock
	Collation collation;
	@Mock
	Count count;
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
	@Mock
	SpellCheckResponse spellCheckResponse;
	@Mock
	JsonElement element;
	@Mock
	Configuration config;
	@Mock
	Resource rulesResource;
	@Mock
	Iterator<Resource> list;
	@Mock
	BoostrulesModel boostrulesModel;
	Long counts = 2L;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", "jsonObjectValue");
		JsonArray jsonArrayFacet = new JsonArray();
		jsonArrayFacet.add(jsonObject);
		JsonArray jsonArrayFormFilterQuery = new JsonArray();
		jsonArrayFormFilterQuery.add("jsonArrayFormFilterQuery");

		String str = "{\"message\":\"Hi\",\"facets\":{\"boostrules\":\"null\",\"id\":\"1234:1234\",\"facet-fields\":"
				+ jsonArrayFacet.toString() + ",\"selected-fields\":" + jsonArrayFormFilterQuery.toString()
				+ "},\"country\":\"us\",\"language\":\"en\"}";
		lenient().when(solrConfig.getContentPageCollectionName()).thenReturn("contentCollection");
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
		ServletInputStream servletInputStream = new ServletInputStream() {
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener arg0) {
			}
		};
		lenient().when(request.getInputStream()).thenReturn(servletInputStream);
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);

		lenient().when(response.getWriter()).thenReturn(writer);
	}

	/**
	 * Test do post.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws LoginException                  the login exception
	 * @throws AemInternalServerErrorException
	 */
	@Test
	public void testDoPost() throws IOException, LoginException, AemInternalServerErrorException {
		String[] selector = new String[1];
		selector[0] = "promodetails";
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", "jsonObjectValue");
		JsonArray jsonArrayFacet = new JsonArray();
		jsonArrayFacet.add(jsonObject);
		JsonArray jsonArrayFormFilterQuery = new JsonArray();
		jsonArrayFormFilterQuery.add("jsonArrayFormFilterQuery");
		String str = "{\"message\":\"Hi\",\"facets\":{\"id\":\"1234:1234\",\"facet-fields\":"
				+ jsonArrayFacet.toString() + ",\"selected-fields\":" + jsonArrayFormFilterQuery.toString()
				+ "},\"country\":\"us\",\"language\":\"en\"}";
		lenient().when(requestPathInfo.getSelectors()).thenReturn(selector);
		lenient().when(eligibleProductsApiService.fetchPromoDetails(request, jsonObject, response, "1234"))
				.thenReturn(str);

		fetchingDataFromSolr.doPost(request, response);
	}

	/**
	 * Test do post else.
	 *
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws LoginException      the login exception
	 * @throws SolrServerException the solr server exception
	 */
	@Test
	public void testDoPostElse() throws IOException, LoginException, SolrServerException {
		String[] selector = new String[1];
		selector[0] = "notcompanionproducts";
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", "valu:e");
		jsonObject.addProperty("id", "id:s");
		jsonObject.addProperty("boostrules", "boostrules");
		JsonArray results = new JsonArray();
		JsonArray selectedfields = new JsonArray();
		results.add(jsonObject);
		String[] indexes = { "laserWavelength_is", "1" };
		lenient().when(requestPathInfo.getSelectors()).thenReturn(selector);
		lenient().when(server.query(Mockito.any())).thenReturn(sitesQueryResponse);
		lenient().when(sitesQueryResponse.getResults()).thenReturn(sitesSolrDocs);
		lenient().when(sitesSolrDocs.iterator()).thenReturn(iterator);
		lenient().when(iterator.hasNext()).thenReturn(true, false);
		lenient().when(iterator.next()).thenReturn(solrDocument);
		lenient().when(solrDocument.iterator()).thenReturn(itr);
		lenient().when(itr.hasNext()).thenReturn(false);
		lenient().when(sitesQueryResponse.getFacetFields()).thenReturn(sitesFacetFieldList);
		lenient().when(sitesFacetFieldList.iterator()).thenReturn(facetItr);
		lenient().when(facetItr.hasNext()).thenReturn(true, false);
		lenient().when(facetItr.next()).thenReturn(next);
		lenient().when(next.getName()).thenReturn("name");
		List<Count> val = new ArrayList<Count>();
		val.add(count);
		lenient().when(next.getValues()).thenReturn(val);
		lenient().when(count.getCount()).thenReturn(counts);
		lenient().when(solrConfig.getIndexesToBeQueried()).thenReturn(indexes);
		fetchingDataFromSolr.doPost(request, response);
	}

	/**
	 * Test construct response.
	 *
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws RepositoryException
	 */
	@Test
	public void testConstructResponse() throws SolrServerException, IOException, RepositoryException {
		String[] selector = new String[1];
		selector[0] = "companionproducts";
		lenient().when(requestPathInfo.getSelectors()).thenReturn(selector);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", "valu:e");
		jsonObject.addProperty("id", "id:s");
		jsonObject.addProperty("boostrules", "boostrules");
		JsonArray results = new JsonArray();
		JsonArray selectedfields = new JsonArray();
		results.add(jsonObject);
		String[] indexes = { "laserWavelength_is", "1" };
		Map<String, Integer> formationMap = new HashMap<>();
		lenient().when(server.query(solrQuery)).thenReturn(sitesQueryResponse);
		lenient().when(server.query(Mockito.any())).thenReturn(sitesQueryResponse);
		lenient().when(sitesQueryResponse.getResults()).thenReturn(sitesSolrDocs);
		lenient().when(sitesSolrDocs.iterator()).thenReturn(iterator);
		lenient().when(iterator.hasNext()).thenReturn(true, false);
		lenient().when(iterator.next()).thenReturn(solrDocument);
		lenient().when(solrDocument.iterator()).thenReturn(itr);
		lenient().when(itr.hasNext()).thenReturn(false);
		lenient().when(sitesQueryResponse.getFacetFields()).thenReturn(sitesFacetFieldList);
		lenient().when(sitesFacetFieldList.iterator()).thenReturn(facetItr);
		lenient().when(facetItr.hasNext()).thenReturn(true, false);
		lenient().when(facetItr.next()).thenReturn(next);
		lenient().when(next.getName()).thenReturn("name");
		List<Count> val = new ArrayList<Count>();
		val.add(count);
		lenient().when(next.getValues()).thenReturn(val);
		lenient().when(count.getCount()).thenReturn(counts);
		lenient().when(solrConfig.getIndexesToBeQueried()).thenReturn(indexes);
		lenient().when(config.specialCharactersToBeModified()).thenReturn(indexes);
		lenient().when(rulesResource.getChild("boostRules")).thenReturn(rulesResource);
		lenient().when(rulesResource.listChildren()).thenReturn(list);
		lenient().when(list.hasNext()).thenReturn(true, false);
		lenient().when(list.next()).thenReturn(rulesResource);
		lenient().when(rulesResource.adaptTo(BoostrulesModel.class)).thenReturn(boostrulesModel);
		fetchingDataFromSolr.constructingResponse(resourceResolver, server, jsonObject, solrQuery, "us",
				catalogStructureUpdateService);
		fetchingDataFromSolr.getId(jsonObject, resourceResolver);
		fetchingDataFromSolr.activate(config);
		fetchingDataFromSolr.getSpecialCharactersToBeModified();// formFacetFieldQuery
		fetchingDataFromSolr.formFacetFieldQuery(solrQuery, results);
		fetchingDataFromSolr.formFilterQuery(solrQuery, selectedfields);
		fetchingDataFromSolr.constructingRulesMap(formationMap, rulesResource);
		fetchingDataFromSolr.boostrulesMap(jsonObject, resourceResolver, formationMap);
		fetchingDataFromSolr.addOtherParameters(jsonObject, solrQuery, "promotiondetails", resourceResolver,
				"language");
		fetchingDataFromSolr.doPost(request, response);
	}

	/**
	 * Gets the spell check response test.
	 *
	 * @return the spell check response test
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 */
	@Test
	public void getspellCheckResponseTest() throws SolrServerException, IOException {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", "1234");
		List<Collation> suggesterResponse = new ArrayList<>();
		suggesterResponse.add(collation);
		lenient().when(server.query(Mockito.any())).thenReturn(sitesQueryResponse);
		lenient().when(sitesQueryResponse.getSpellCheckResponse()).thenReturn(spellCheckResponse);
		lenient().when(spellCheckResponse.getCollatedResults()).thenReturn(suggesterResponse);
		fetchingDataFromSolr.getspellCheckResponse(server, jsonObject);
		/*
		 * Assertions.assertThrows(NullPointerException.class, () -> {
		 * fetchingDataFromSolr.getspellCheckResponse(server, jsonObject); });
		 */

	}

	/**
	 * Construct solr filter query str test.
	 */
	@Test
	public void constructSolrFilterQueryStrTest() {
		JsonArray applicableCategoriesArray = new JsonArray();
		applicableCategoriesArray.add("applicableCategoriesArray1");
		applicableCategoriesArray.add("applicableCategoriesArray1MPGCategory");
		applicableCategoriesArray.add("applicableCategoriesArray2MPG");
		applicableCategoriesArray.add("applicableProducts");
		applicableCategoriesArray.add("excludedProducts");
		fetchingDataFromSolr.constructSolrFilterQueryStr(solrQuery, applicableCategoriesArray, "applicableCategories");
		fetchingDataFromSolr.constructSolrFilterQueryStr(solrQuery, applicableCategoriesArray, "applicableProducts");
		fetchingDataFromSolr.constructSolrFilterQueryStr(solrQuery, applicableCategoriesArray, "excludedProducts");

	}

	/**
	 * Adds the other parameters test.
	 */
	@Test
	public void addOtherParametersTest() {
		JsonObject jsonObject = new JsonObject();
		fetchingDataFromSolr.addOtherParameters(jsonObject, solrQuery, "searchResults", resourceResolver, "en");
	}
	
	/**
	 * Adds the other parameters test.
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void addEdismaxParametersTest() throws UnsupportedEncodingException {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", "name:human-cd3");
		jsonObject.add("boostrules", JsonNull.INSTANCE);
		lenient().when(solrConfig.getIndexesToBeQueried()).thenReturn(new String[] {"brand_t", "name_t"});
		lenient().when(solrConfig.getSplCharsToReplaceSearchTerm()).thenReturn("-");
		fetchingDataFromSolr.addEdismaxParameters(jsonObject, solrQuery, "searchResults", resourceResolver, "en");
	}

}
