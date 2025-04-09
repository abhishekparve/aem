package com.bdb.aem.core.services.solr.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.lenient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.input.BoundedInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.jackrabbit.vault.packaging.JcrPackage;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.settings.SlingSettingsService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Reference;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.adobe.acs.commons.genericlists.GenericList;
import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.dam.api.Rendition;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ch.qos.logback.core.pattern.parser.Node;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SolrSearchServiceImplTest {
	@InjectMocks
	SolrSearchServiceImpl solrSearchServiceImpl;
	@Mock
	SolrSearchService solrSearchService;
	@Mock
	BDBWorkflowConfigService bDBWorkflowConfigService;
	@Mock
	BDBSearchEndpointService solrConfig;
	@Mock
	HttpSolrClient server;
	@Mock
	QueryResponse solrQueryResponse;
	@Mock
	SolrDocument solrDocument;
	@Mock
	SlingSettingsService slingSettingsService;
	@Mock
	ResourceResolverFactory resolverFactory;
	@Mock
	SolrQuery solrQuery;
	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;
	@Mock
	RestClient restClient;
	/** The request. */
	@Mock
	SlingHttpServletRequest requests;
	@Mock
	ExternalizerService externalizerService;
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	@Mock
	HttpGet getMethod;
	/** The response entity. */
	@Mock
	HttpEntity responseEntity;
	@Mock
	BoundedInputStream boundedInput;
	@Mock
	Iterator<Resource> languageList;
	@Mock
	HttpResponse response;
	@Mock
	HttpPost httpRequest;
	RequestConfig requestConfig;
	@Mock
	CloseableHttpClient httpClient;
	@Mock
	CloseableHttpResponse httpResponse;
	@Mock
	HttpEntity httpEntity;
	@Mock
	ValueMap valueMap, properties, variantValueMap;
	@Mock
	Asset asset;
	@Mock
	InputStream stream;
	@Mock
	GenericList genericList;

	@Mock
	Rendition rendition;

	@Mock
	DocumentBuilderFactory docFactory;

	@Mock
	DocumentBuilder docBuilder;

	@Mock
	Document doc;

	@Mock
	ByteArrayOutputStream pdfStream;

	@Mock
	ServletOutputStream outputStream;

	@Mock
	SolrInputDocument solrInputDocument;

	@Mock
	SolrInputField solrInputField;

	@Mock
	NodeList nodelist;
	@Mock
	Query query;
	@Mock
	private QueryBuilder queryBuilder;
	@Mock
	private SearchResult searchResult;
	@Mock
	Session session;
	@Mock
	Iterator<Resource> items;
	@Mock
	Object obj;
	@Mock
	Page page;
	@Mock
	List<Hit> hit = new ArrayList<>();
	@Mock
	ConfigurationAdmin configurationAdmin;
	@Mock
	Iterable<Resource> thumbnailImages;
	@Mock
	Node node;

	@Mock
	Tag tag;
	
	@Mock
	TagManager tagManager;
	@Mock
	Date date;
	
	@Mock 
	ValueMap vm;

	/** Mock Resource */
	@Mock
	Resource itemResource, listResource, genericListResource, jcrRes, mdRes, variantHpResource, baseProductHpResource,
			pageContent, mdResParent, mdResParentParent;
	private Long resultCount = 1L;
	JsonObject indexPageData = new JsonObject();
	JsonObject bdFormatObject = new JsonObject();
	List<String> variantList = new ArrayList<String>();
	ArrayList<String> countriesList = new ArrayList<String>();
	private static final String assetPath = "/content/dam/pankaj.pdf";

	@Test
	void testCrawlContent() throws IOException, LoginException, Exception {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getTotalMatches()).thenReturn(resultCount);

		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("application/pdf");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		solrSearchServiceImpl.crawlContent("resourcePath", "resourceType", session, resourceResolver);
		solrSearchServiceImpl.getTypeFromAssets(asset);
		solrSearchServiceImpl.convertToUtc(date);
	}

	@Test
	void testIndexAllPagesToSolr() throws IOException, LoginException, SolrServerException, Exception {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn(SolrSearchConstants.PPT_FILE_FORMAT);
		solrSearchServiceImpl.getTypeFromAssets(asset);
		solrSearchServiceImpl.indexAllPagesToSolr(indexPageData);
		solrSearchServiceImpl.createPageMetadataArray(searchResult, resourceResolver);
	}

	@Test
	void testGetAllCountries() throws IOException, LoginException, SolrServerException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(bdbApiEndpointService.countryDropdownEndpoint()).thenReturn("countryDropdownEndpoint");
		lenient().when(resourceResolver.getResource("countryDropdownEndpoint")).thenReturn(genericListResource);
		lenient().when(genericListResource.getPath()).thenReturn("/content/bdb");
		lenient().when(resourceResolver.getResource(genericListResource.getPath() + CommonConstants.JCR_CONTENT
				+ CommonConstants.SINGLE_SLASH + CommonConstants.LIST)).thenReturn(listResource);
		lenient().when(listResource.hasChildren()).thenReturn(true);
		lenient().when(listResource.listChildren()).thenReturn(items);
		lenient().when(items.hasNext()).thenReturn(true, false, true, false);
		lenient().when(items.next()).thenReturn(itemResource);
		lenient().when(itemResource.getValueMap()).thenReturn(valueMap);
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("text/html");
		lenient().when(valueMap.containsKey("value")).thenReturn(true);
		lenient().when(valueMap.get("value", String.class)).thenReturn(assetPath);
		solrSearchServiceImpl.getTypeFromAssets(asset);
		solrSearchServiceImpl.getAllCountries(resourceResolver);
		solrSearchServiceImpl.getAllSolrClients();
		solrSearchServiceImpl.unIndexContent("/content/bdb/na/us/products", "pdfAsset");

	}

	@Test
	void testcrawlPdfs() throws IOException, LoginException, SolrServerException, Exception {
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getTotalMatches()).thenReturn(resultCount);
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("application/xml");
		lenient().when(session.isLive()).thenReturn(true);
		lenient().when(solrConfig.getCombinedFieldsForPDFs()).thenReturn("name_t,dcTitle_t");
		lenient().when(solrConfig.getConcatenatedFieldSplChars()).thenReturn("(,),-");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		solrSearchServiceImpl.crawlPdfs("resourcePath", session);
		solrSearchServiceImpl.indexPdpUrls(resourceResolver, assetPath);
		solrSearchServiceImpl.indexAllPdfsToSolr(resourceResolver, assetPath);
	}

	@Test
	void testIndexVideoThumbnailToSolr() throws IOException, SAXException, TikaException, LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(asset.getPath()).thenReturn(assetPath);
		lenient().when(asset.getName()).thenReturn("assetName");
		lenient().when(resourceResolver.getResource(Mockito.any())).thenReturn(jcrRes);
		lenient().when(jcrRes.adaptTo(ValueMap.class)).thenReturn(properties);
		lenient().when(resourceResolver.getResource(Mockito.any())).thenReturn(mdRes);
		lenient().when(mdRes.getPath()).thenReturn("mdResPath");
		lenient().when(mdRes.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.get("dc:format", String.class)).thenReturn("");
		lenient().when(mdRes.adaptTo(ValueMap.class)).thenReturn(properties);
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn(JcrPackage.MIME_TYPE);
		solrSearchServiceImpl.getTypeFromAssets(asset);
		//solrSearchServiceImpl.indexVideoThumbnailToSolr(asset, resourceResolver);

	}

	@Test
	void testIndexProductDataToSolr() throws IOException, LoginException, SolrServerException, RepositoryException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		variantList.add("variantlist");
		lenient().when(bdbApiEndpointService.countryDropdownEndpoint()).thenReturn("countryDropdownEndpoint");
		lenient().when(resourceResolver.getResource("countryDropdownEndpoint")).thenReturn(genericListResource);
		lenient().when(genericListResource.getPath()).thenReturn("/content/bdb");
		lenient().when(resourceResolver.getResource(genericListResource.getPath() + CommonConstants.JCR_CONTENT
				+ CommonConstants.SINGLE_SLASH + CommonConstants.LIST)).thenReturn(listResource);
		lenient().when(listResource.hasChildren()).thenReturn(true);
		lenient().when(listResource.listChildren()).thenReturn(items);
		lenient().when(items.hasNext()).thenReturn(true, false);
		lenient().when(items.next()).thenReturn(itemResource);
		lenient().when(itemResource.getValueMap()).thenReturn(valueMap);
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("text/plain");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		solrSearchServiceImpl.indexProductDataToSolr(resourceResolver, variantList);
	}

	@Test
	void testSolrClient() throws IOException, LoginException, SolrServerException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("application/vnd.ms-excel");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		solrSearchServiceImpl.solrClientPopSearch();
		solrSearchServiceImpl.solrClient("Country");
	}

	@Test
	void testIndexAssetsToSolr() throws IOException, LoginException, SolrServerException, SAXException, TikaException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(asset.getPath()).thenReturn(assetPath);
		lenient().when(asset.getName()).thenReturn("assetName");
		lenient().when(resourceResolver.getResource(Mockito.any())).thenReturn(jcrRes);
		lenient().when(jcrRes.adaptTo(ValueMap.class)).thenReturn(properties);
		lenient().when(resourceResolver.getResource(Mockito.any())).thenReturn(mdRes);
		lenient().when(mdRes.adaptTo(ValueMap.class)).thenReturn(null);
		vm.put(SolrSearchConstants.DOC_SKU,"DOC_SKU");
		vm.put(SolrSearchConstants.DOC_TITLE,"DOC_TITLE");
		vm.put(SolrSearchConstants.DOCUMENT_TYPE,"DOCUMENT_TYPE");
		vm.put(SolrSearchConstants.DOC_DESC,"DOC_DESC");
		vm.put(CommonConstants.DC_TITLE,"DC_TITLE");
		vm.put(CommonConstants.DOC_KEYWORDS,"DOC_KEYWORDS");
		vm.put(CommonConstants.PRODUCT_NAME,"PRODUCT_NAME");
		vm.put(SolrSearchConstants.PDFX_DOC_TYPE,"PDFX_DOC_TYPE");
		
		//SolrSearchServiceImpl solrSearchServiceImpl = Mockito.mock(SolrSearchServiceImpl.class);
//		SolrSearchServiceImpl solrSearchServiceImpl = Mockito.spy(SolrSearchServiceImpl.class);
//		lenient().when(SolrSearchServiceImpl.parseAssettoText(asset)).thenReturn("mock");
		
		SolrDocumentList docList = new SolrDocumentList();
		List<SolrDocument> solrList = new ArrayList<>();
		LinkedHashMap<String, Object> m = new LinkedHashMap<String, Object>();
		String[] specificity = { "TCR VÎ±7.2" };
		m.put("specificity", specificity);
		String[] cloneName = { "cloneName" };
		m.put("tdsCloneName", cloneName);
		String[] dyeName = { "BV421" };
		m.put("dyeName", dyeName);
		m.put("id", "/content/bd-aem");
		solrList.add(solrDocument);
		SolrDocument solrDoc = new SolrDocument(m);
		docList.add(solrDoc);
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		lenient().when(server.query(Mockito.any())).thenReturn(solrQueryResponse);
		lenient().when(solrQueryResponse.getResults()).thenReturn(docList);
		lenient().when(properties.get(SolrSearchConstants.CQ_LAST_REPLICATED, Date.class)).thenReturn(date);
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("application/msword");
		lenient().when(solrConfig.getCombinedFieldsForPDFs()).thenReturn("name_t,dcTitle_t");
		lenient().when(solrConfig.getConcatenatedFieldSplChars()).thenReturn("(,),-");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		solrSearchServiceImpl.indexAssetsToSolr(asset, resourceResolver);
	}

	@Test
	void testGetHpNodeResources() throws IOException, LoginException, SolrServerException, SAXException, TikaException {
		// SolrSearchServiceImpl.getLaserWavelength(bdFormatObject);
		// SolrSearchServiceImpl.getHpNodeResources("inputKey", "inputValue",
		// resourceResolver);
		// SolrSearchServiceImpl.parseAssettoText(asset);
		lenient().when(bdbApiEndpointService.getScientificResourceFolder()).thenReturn(assetPath);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(mdRes);
		lenient().when(mdRes.hasChildren()).thenReturn(true);
		lenient().when(mdRes.getChildren()).thenReturn(thumbnailImages);
		lenient().when(thumbnailImages.iterator()).thenReturn(items);
		lenient().when(items.hasNext()).thenReturn(true, false);
		lenient().when(items.next()).thenReturn(itemResource);
		lenient().when(itemResource.hasChildren()).thenReturn(true);
		lenient().when(itemResource.getChild(SolrSearchConstants.JCR_CONTENT)).thenReturn(genericListResource);
		lenient().when(genericListResource.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.containsKey(SolrSearchConstants.CQ_LAST_REPLICATION_ACTION)).thenReturn(true);
		lenient().when(valueMap.get(SolrSearchConstants.CQ_LAST_REPLICATION_ACTION)).thenReturn("cq");
		lenient().when(resourceResolver.getResource("countryDropdownEndpoint")).thenReturn(genericListResource);
		lenient().when(genericListResource.getPath()).thenReturn("/content/bdb");
		lenient().when(resourceResolver.getResource(genericListResource.getPath() + CommonConstants.JCR_CONTENT
				+ CommonConstants.SINGLE_SLASH + CommonConstants.LIST)).thenReturn(listResource);
		lenient().when(listResource.hasChildren()).thenReturn(true);
		lenient().when(listResource.listChildren()).thenReturn(items);
		lenient().when(items.hasNext()).thenReturn(true, false, true, false);
		lenient().when(items.next()).thenReturn(itemResource);
		lenient().when(itemResource.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.containsKey("value")).thenReturn(true);
		lenient().when(valueMap.get("value", String.class)).thenReturn(assetPath);
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("text/csv");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		solrSearchServiceImpl.indexAllScientificResourceThumbnailImagesToSolr(resourceResolver);
		solrSearchServiceImpl.getListFromStringForFcm(assetPath, variantList, assetPath);
		solrSearchServiceImpl.getListFromString(assetPath, variantList);
		solrSearchServiceImpl.unIndexContent("/content/bdb/na/us/products", "contentPage");

		// SolrSearchServiceImpl.indexAssetsToSolr(asset);
	}

	@Test
	void testGetLaserWavelength() throws IOException, LoginException, SolrServerException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(variantHpResource.getParent()).thenReturn(genericListResource);
		lenient().when(genericListResource.getPath()).thenReturn(assetPath);
		lenient().when(genericListResource.getParent()).thenReturn(itemResource);
		lenient().when(itemResource.getChild(CommonConstants.HP_NODE)).thenReturn(baseProductHpResource);
		lenient().when(baseProductHpResource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(itemResource.getPath()).thenReturn("/content/bdb/hp");
		lenient().when(valueMap.get(CommonConstants.PRIMARY_SUPER_CATEGORY, String.class)).thenReturn(assetPath);
		lenient().when(valueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn(assetPath);
		lenient().when(genericListResource.getName()).thenReturn("catalognumberName");

		// lenient().when(valueMap.containsKey(CommonConstants.CLONE)).thenReturn(true);
		// lenient().when(valueMap.get(CommonConstants.CLONE)).thenReturn(obj);
		// lenient().when(obj.toString()).thenReturn("clone");
		lenient().when(variantValueMap.get(CommonConstants.MATERIAL_NUMBER, String.class)).thenReturn("materialNumber");
		lenient().when(variantValueMap.get(CommonConstants.BASE_MATERIAL_NUMBER, String.class))
				.thenReturn("basematerialNumber_base");
		// lenient().when(genericListResource.getPath()).thenReturn("/content/bdb/hp");
		// lenient().when(valueMap.containsKey(CommonConstants.SPECIES_REACTIVITY_KEY)).thenReturn(true);
		lenient().when(valueMap.get(CommonConstants.SPECIES_REACTIVITY_KEY)).thenReturn(obj);
		lenient().when(obj.toString()).thenReturn(assetPath);
		lenient().when(solrConfig.getProductTypesGenericListEndpoint()).thenReturn("/path/to/generic-list");
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getTotalMatches()).thenReturn(resultCount);
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("application/rtf");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		solrSearchServiceImpl.getSolrHpJson(variantValueMap, variantHpResource, genericList, resourceResolver);
		solrSearchServiceImpl.createPageMetadataObject(null, resourceResolver);
	}

	@Test
	void testSrolrClient() throws IOException, LoginException, SolrServerException, Exception {
		indexPageData.add(CommonConstants.UNIQUE_IDENTIFIER, bdFormatObject);
		bdFormatObject.addProperty(CommonConstants.LASER_WAVELENGTH, CommonConstants.LASER_WAVELENGTH);
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(pageContent.getParent()).thenReturn(genericListResource);
		lenient().when(pageContent.getPath()).thenReturn(assetPath);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(mdRes);
		lenient().when(mdRes.adaptTo(Page.class)).thenReturn(page);
		lenient().when(genericListResource.getParent()).thenReturn(genericListResource);
		lenient().when(genericListResource.getPath()).thenReturn("/content/bdb/hp");
		lenient().when(pageContent.getChild("jcr:content")).thenReturn(baseProductHpResource);
		lenient().when(baseProductHpResource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		solrSearchServiceImpl.getLaserWavelength(bdFormatObject);
		solrSearchServiceImpl.getDamGlobalPath(pageContent);
		solrSearchServiceImpl.getLaserWavelength(bdFormatObject);

		// SolrSearchServiceImpl.getSapSolrJson(genericListResource, indexPageData,
		// bdFormatObject, countriesList);
	}

	@Test
	public void testReplaceCombinedFieldCharsWithSpace() {
		String replacedValue = solrSearchServiceImpl.replaceCombinedFieldCharsWithSpace("563262;123", new String[] {";","/"}, " ");
		assertEquals("563262 123", replacedValue);

	}

	@Test
	public void testReplaceCombinedFieldCharsWithOutSpace() {
		String replacedValue = solrSearchServiceImpl.replaceCombinedFieldCharsWithSpace("563262", new String[] {";","/"}, "");
		assertEquals("563262", replacedValue);

	}

	@Test
	public void testBuildConcatenatedQueryFields() {
		JsonObject variantPropertyJson = new JsonObject();
		variantPropertyJson.addProperty("specificity", "CD45/RA");
		String combinedQueryExcludedFields = "specificity_t";
		List<String> regexToEscape = new ArrayList<>();
		regexToEscape.add("/");
		regexToEscape.add(";");
		regexToEscape.add(".");
		regexToEscape.add("-");
		SolrInputDocument document = new SolrInputDocument();
		Map<String, String> combinedMap = new HashMap<>();
		combinedMap.put("tdsCloneName_t", "Ha31/8");
		combinedMap.put("specificity_t", "CD45/RA");
		solrSearchServiceImpl.buildConcatenatedQueryFields(variantPropertyJson, combinedQueryExcludedFields,
				regexToEscape, document, combinedMap);
	}
	
	@Test
	public void testGetCountriesToIndex() {
		String [] tagsArray = new String[1];
		tagsArray[0] = "bdb:regions/jp";
		lenient().when(mdRes.getPath()).thenReturn("/content/dam/bdb/products/global/instruments/flow-cytometers/clinical-cell-analyzers/facscanto/657338_base/pdf/23-15025.pdf/jcr:content/metadata");
		lenient().when(mdRes.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.get("dc:format", String.class)).thenReturn(CommonConstants.APPLICATION_PDF);
		lenient().when(mdRes.adaptTo(ValueMap.class)).thenReturn(properties);
		lenient().when(properties.containsKey("docRegion")).thenReturn(true);
		lenient().when(properties.get("docRegion", String[].class)).thenReturn(tagsArray);
		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(tagManager.resolve("bdb:regions/jp")).thenReturn(tag);
		lenient().when(tag.getName()).thenReturn("jp");
		lenient().when(bDBWorkflowConfigService.getDamAssetBasePath()).thenReturn("/content/dam/bdb/products/global/");
		lenient().when(bDBWorkflowConfigService.getVarCommerceBasePath()).thenReturn("/content/commerce/products/bdb/products/");
		lenient().when(mdRes.getParent()).thenReturn(mdResParent);
		lenient().when(mdResParent.getParent()).thenReturn(mdResParentParent);
		lenient().when(mdResParentParent.getPath()).thenReturn("/content/dam/bdb/products/global/instruments/flow-cytometers/clinical-cell-analyzers/facscanto/657338_base/pdf/23-15025.pdf");
		solrSearchServiceImpl.getCountriesToIndex(mdRes,resourceResolver);
	}
	
	@Test
	public void testIndexPagesToSolr() throws SolrServerException, IOException, LoginException {
		
		BufferedReader br = new BufferedReader(new FileReader("src/test/resources/com/bdb/aem/core/workflows/solrWebTest.json"));
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(br).getAsJsonObject();
		System.out.println(object);
		solrSearchServiceImpl.indexPagesToSolr(object, server);
	}
	
	@Test
	public void testHttpSolrClient() {
		
		lenient().when(solrConfig.getSolrUrl()).thenReturn("/solr-url");
		lenient().when(solrConfig.getContentPageCollectionName()).thenReturn("/bdbio");
		solrSearchServiceImpl.solrClient("us");
		lenient().when(solrConfig.getPopularSearchedCollectionName()).thenReturn("/bd-popular-search");
		solrSearchServiceImpl.solrClientPopSearch();
	}
	
	@Test
	public void testGetTypeFromAssets() {
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("application/pdf");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("application/vnd.ms-excel");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("text/htmlf");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn(JcrPackage.MIME_TYPE);
		solrSearchServiceImpl.getTypeFromAssets(asset);
		
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("application/xml");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn(SolrSearchConstants.PPT_FILE_FORMAT);
		solrSearchServiceImpl.getTypeFromAssets(asset);
		
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("text/plain");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("application/msword");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("text/csv");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("application/rtf");
		solrSearchServiceImpl.getTypeFromAssets(asset);
		
		lenient().when(asset.getMetadataValue(DamConstants.DC_FORMAT)).thenReturn("image/png");
		solrSearchServiceImpl.getTypeFromAssets(asset);
	}
}
