package com.bdb.aem.core.services.solr.impl;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.vault.packaging.JcrPackage;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.adobe.acs.commons.genericlists.GenericList;
import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.bean.BaseVariantHpResourceBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.bdb.aem.core.util.SolrUtils;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class SolrSearchServiceImpl.
 */
@Component(service = SolrSearchService.class, immediate = true)
public class SolrSearchServiceImpl implements SolrSearchService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SolrSearchServiceImpl.class);

	/** The query builder. */
	@Reference
	private QueryBuilder queryBuilder;

	/** The solr config. */
	@Reference
	BDBSearchEndpointService solrConfig;

	/** The externalizer service. */
	@Reference
	ExternalizerService externalizerService;

	/** The resolver factory. */
	@Reference
	ResourceResolverFactory resolverFactory;

	/** The bdb api endpoint service. */
	@Reference
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The workflow helper service. */
	@Reference
	WorkflowHelperService workflowHelperService;
	
	/** The bDB workflow config service. */
	@Reference
	BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The rest client. */
	@Reference
	private RestClient restClient;
	
	/** The fetch OSGI Config. */
	@Reference
	ConfigurationAdmin configurationAdmin;

	/**  PDF_FILTER_DESC. */
	private static final String PDF_FILTER_DESC="dcDescription_t";
	
	/**  PDF_FILTER_TITLE. */
	private static final String PDF_FILTER_TITLE="dctitle_t";
	
	/**  PDF_FILTER_DOCTYPE. */
	private static final String ASSET_DOCTYPE="scientificResDocType_t";

	/**  PDF_FILTER_DOCTYPE. */
	private static final String PDF_FILTER_DOCTYPE="docType_t";

	/**  PDF_FILTER_DOCTYPE. */
	private static final String CQ_LAST_REPLICATED="cqLastReplicated_dt";
	
	/**  PDF_FILTER_DOCTYPE. */
	private static final String BRIGHT_COVE_VIDEO_ID="brightCoveVideoID_t";
	
	/**  SOLR_DATE_FORMAT. */
	public static final String SOLR_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	/**  SOLR_DATE_FORMAT. */
	public static final String SCIENTIFIC_VIDEO_TYPE = "videoType";
	
	public static final String TECHNICAL_DATA_SHEET_CAPS = "TechnicalDataSheet(TDS)";
	public static final String TECHNICAL_DATA_SHEET_LOWER = "Technicaldatasheet(TDS)";
	public static final String TDS = "TDS";
	
	public static final String IFU = "IFU";
	public static final String INSTRUCTION_FOR_USE_LOWER = "Instructionforuse(IFU)";
	public static final String INSTRUCTION_FOR_USE_CAPS = "InstructionForUse(IFU)";
	
	/**
	 * Crawl content.
	 *
	 * @param resourcePath the resource path
	 * @param resourceType the resource type
	 * @param session      the session
	 * @return the json array
	 * @throws LoginException the login exception
	 */
	@Override
	public JsonObject crawlContent(String resourcePath, String resourceType, Session session,ResourceResolver resourceResolver) throws LoginException {

		Map<String, String> params = new HashMap<>();
		params.put(SolrSearchConstants.QUERY_BUILDER_PATH, resourcePath);
		params.put(SolrSearchConstants.QUERY_BUILDER_TYPE, resourceType);
		//Removing cq replication action and activate param
		//params.put(SolrSearchConstants.QUERY_BUILDER_1_PROPERTY, SolrSearchConstants.CQ_LAST_REPLICATION_ACTION);
		//params.put(SolrSearchConstants.QUERY_BUILDER_1_PROPERTY_VALUE, SolrSearchConstants.ACTIVATE);
		params.put(SolrSearchConstants.QUERY_BUILDER_1_PROPERTY, SolrSearchConstants.JCR_CONTENT + CommonConstants.SINGLE_SLASH + SolrSearchConstants.SOLR_UNINDEXABLE);
		params.put(SolrSearchConstants.QUERY_BUILDER_1_PROPERTY_OPERATION, "not");
		params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");

		try {
			Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);

			SearchResult searchResults = query.getResult();

			LOG.debug("Found '{}' matches for query", searchResults.getTotalMatches());
			return createPageMetadataArray(searchResults, resourceResolver);
		} catch (RepositoryException e) {
			LOG.error("Exception due to", e);
		} finally {
			if (session.isLive()) {
				session.logout();
			}
		}
		return null;
	}

	/**
	 * Index pages to solr.
	 * Index pages to solr.
	 *
	 * @param indexPageData the index page data
	 * @return true, if successful
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws LoginException      the login exception
	 */
	@Override
	public boolean indexAllPagesToSolr(JsonObject indexPageData)
			throws SolrServerException, IOException, LoginException {
		ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
		if (null != indexPageData) {
			ArrayList<String> countriesList = (ArrayList<String>) getAllCountries(resourceResolver);
			for (String country : countriesList) {
				String coreName = solrConfig.getContentPageCollectionName() + "-" + country.toLowerCase();
				String solrUrl = solrConfig.getSolrUrl() + SolrSearchConstants.SLASH_SOLR_SLASH + coreName;
				HttpSolrClient server = new HttpSolrClient.Builder(solrUrl).withHttpClient(restClient.getHttpClient()).build();
				try {
					if (indexPageData.has(coreName)) {
						JsonArray countryBasedSolrDocs = indexPageData.get(coreName).getAsJsonArray();
						for (int i = 0; i < countryBasedSolrDocs.size(); i++) {
							JsonObject pageJsonObject = countryBasedSolrDocs.get(i).getAsJsonObject();
							SolrInputDocument doc = createPageSolrDoc(pageJsonObject);
							server.add(doc);
						}
						server.commit();
						server.close();
					}
				} catch (SolrServerException | IOException | HttpSolrClient.RemoteSolrException e) {
					LOG.error("Exception while indexing all content pages to solr", e);
				}finally {
					CommonHelper.closeResourceResolver(resourceResolver);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Index pages to solr.
	 *
	 * @param indexPageData the index page data
	 * @param server        the server
	 * @return true, if successful
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws LoginException      the login exception
	 */
	@Override
	public boolean indexPagesToSolr(JsonObject indexPageData, HttpSolrClient server)
			throws SolrServerException, IOException, LoginException {
		if (null != indexPageData) {
			SolrInputDocument doc = createPageSolrDoc(indexPageData);
			server.add(doc);
			server.commit();
			server.close();
			return true;
		}
		return false;
	}

	/**
	 * Creates the page metadata array.
	 *
	 * @param results the results
	 * @param resourceResolver 
	 * @return the json array
	 * @throws RepositoryException the repository exception
	 * @throws LoginException      the login exception
	 */
	public JsonObject createPageMetadataArray(SearchResult results, ResourceResolver resourceResolver) throws RepositoryException, LoginException {
		JsonObject solrDocs = new JsonObject();
		for (Hit hit : results.getHits()) {
			Resource pageContent = hit.getResource();
			String pagePath = hit.getPath();
			String[] pathSplit = pagePath.split("/");
			if (!pagePath.startsWith(SolrSearchConstants.LANGUAGE_MASTERS_PAGE_PATH) && pathSplit.length > 6
					&& !pathSplit[6].equals("datapage") && !pathSplit[6].equals("errorpages")) {
				JsonObject propertiesMap = createPageMetadataObject(pageContent,resourceResolver);
				String country = propertiesMap.get(CommonConstants.COUNTRY).getAsString();
				String countryCore = solrConfig.getContentPageCollectionName() + "-" + country.toLowerCase();
				JsonArray countrybasedSolrDocs = null;
				if (solrDocs.has(countryCore)) {
					countrybasedSolrDocs = solrDocs.get(countryCore).getAsJsonArray();
				} else {
					countrybasedSolrDocs = new JsonArray();
				}
				countrybasedSolrDocs.add(propertiesMap);
				solrDocs.add(countryCore, countrybasedSolrDocs);
			}
		}
		return solrDocs;
	}

	/**
	 * Creates the page metadata object.
	 *
	 * @param pageContent the page content
	 *
	 * @return the json object
	 * @throws LoginException the login exception
	 */
	@Override
	public JsonObject createPageMetadataObject(Resource pageContent, ResourceResolver resourceResolver) throws LoginException {
		JsonObject propertiesMap = new JsonObject();
		Page page = null;
		ValueMap jcrProperties = null;
		if(null != pageContent) {
			page = resourceResolver.getResource(pageContent.getPath()).adaptTo(Page.class);
			propertiesMap.addProperty(CommonConstants.UNIQUE_IDENTIFIER, pageContent.getPath());
			propertiesMap.addProperty(SolrSearchConstants.SOLRDOC_FIELD_ID, pageContent.getPath());
			propertiesMap.addProperty(SolrSearchConstants.SOLRDOC_FIELD_URL,
					pageContent.getPath() + CommonConstants.DOT_HTML);
			if(null != pageContent.getChild(JcrConstants.JCR_CONTENT)) {
				jcrProperties = pageContent.getChild(JcrConstants.JCR_CONTENT).adaptTo(ValueMap.class);
				String pageTitle = jcrProperties.get(SolrSearchConstants.JCR_TITLE, String.class);
				if (StringUtils.isEmpty(pageTitle)) {
					pageTitle = page.getTitle();
				}
				propertiesMap.addProperty(SolrSearchConstants.JCR_TITLE, pageTitle);
				propertiesMap.addProperty(CommonConstants.NAME, pageTitle);
				propertiesMap.addProperty(SolrSearchConstants.SOLRDOC_FIELD_DESC,
						SolrUtils.checkNull(jcrProperties.get(SolrSearchConstants.JCR_DESC, String.class)));
				propertiesMap.addProperty(SolrSearchConstants.NAV_TITLE,
						SolrUtils.checkNull(jcrProperties.get(SolrSearchConstants.NAV_TITLE, String.class)));
				propertiesMap.addProperty(SolrSearchConstants.PAGE_TITLE,
						SolrUtils.checkNull(jcrProperties.get(SolrSearchConstants.PAGE_TITLE, String.class)));
				propertiesMap.addProperty(SolrSearchConstants.SUBTITLE,
						SolrUtils.checkNull(jcrProperties.get(SolrSearchConstants.SUBTITLE, String.class)));
				propertiesMap.addProperty(CommonConstants.BDB_CONTENT_KEYWORDS,
						SolrUtils.checkNull(jcrProperties.get(CommonConstants.BDB_CONTENT_KEYWORDS, String.class)));
			}
			
		}
		if (null != CommonHelper.getCountry(page)) {
			propertiesMap.addProperty(SolrSearchConstants.COUNTRY, CommonHelper.getCountry(page));
		}
		if (null != CommonHelper.getLanguage(page)) {
			propertiesMap.addProperty(SolrSearchConstants.LANGUAGE, CommonHelper.getLanguage(page));
		}
		propertiesMap.add(SolrSearchConstants.SOLRDOC_FIELD_TAGS, SolrUtils.getPageTags(pageContent));
		return propertiesMap;
	}

	/**
	 * Creates the page solr doc.
	 *
	 * @param pageJsonObject the page json object
	 * @return the solr input document
	 */
	private SolrInputDocument createPageSolrDoc(JsonObject pageJsonObject) {

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField(CommonConstants.UNIQUE_IDENTIFIER,
				pageJsonObject.get(CommonConstants.UNIQUE_IDENTIFIER).getAsString());
		doc.addField(SolrSearchConstants.SOLRDOC_FIELD_ID,
				pageJsonObject.get(SolrSearchConstants.SOLRDOC_FIELD_ID).getAsString());
		doc.addField(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_T,
				SolrSearchConstants.CONTENT_PAGE);
		doc.addField(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S,
				SolrSearchConstants.INFORMATION);
		doc.addField(CommonConstants.NAME.concat(SolrSearchConstants.UNDERSCORE_T),
				pageJsonObject.get(SolrSearchConstants.JCR_TITLE).getAsString());
		doc.addField(SolrSearchConstants.SOLRDOC_FIELD_DESC.concat(SolrSearchConstants.UNDERSCORE_T),
				pageJsonObject.get(SolrSearchConstants.SOLRDOC_FIELD_DESC).getAsString());
		doc.addField(SolrSearchConstants.NAV_TITLE.concat(SolrSearchConstants.UNDERSCORE_T),
				pageJsonObject.get(SolrSearchConstants.NAV_TITLE).getAsString());
		doc.addField(SolrSearchConstants.SUBTITLE.concat(SolrSearchConstants.UNDERSCORE_T),
				pageJsonObject.get(SolrSearchConstants.SUBTITLE).getAsString());
		doc.addField(SolrSearchConstants.PAGE_TITLE.concat(SolrSearchConstants.UNDERSCORE_T),
				pageJsonObject.get(SolrSearchConstants.PAGE_TITLE).getAsString());
		doc.addField(CommonConstants.BDB_CONTENT_KEYWORDS.concat(SolrSearchConstants.UNDERSCORE_T),
				pageJsonObject.get(CommonConstants.BDB_CONTENT_KEYWORDS).getAsString());
		doc.addField("validityStartDate".concat(SolrSearchConstants.UNDERSCORE_DT), "1900-12-08T14:13:15Z");
		doc.addField("isAvailable".concat(SolrSearchConstants.UNDERSCORE_T), "true");
		if (pageJsonObject.has(SolrSearchConstants.COUNTRY)) {
			doc.addField(SolrSearchConstants.COUNTRY.concat(SolrSearchConstants.UNDERSCORE_T),
					pageJsonObject.get(SolrSearchConstants.COUNTRY).getAsString());
		}
		if (pageJsonObject.has(SolrSearchConstants.LANGUAGE)) {
			doc.addField(SolrSearchConstants.LANGUAGE.concat(SolrSearchConstants.UNDERSCORE_T),
					pageJsonObject.get(SolrSearchConstants.LANGUAGE).getAsString());
		}
		JsonArray tagsJson = pageJsonObject.get(SolrSearchConstants.SOLRDOC_FIELD_TAGS).getAsJsonArray();
		String[] tagsArray = new String[tagsJson.size()];
		for (int i = 0; i < tagsJson.size(); i++) {
			tagsArray[i] = tagsJson.get(i).getAsString();
		}
		doc.addField(SolrSearchConstants.SOLRDOC_FIELD_TAGS.concat(SolrSearchConstants.UNDERSCORE_TXT), tagsArray);

		return doc;
	}

	/**
	 * Solr client.
	 *
	 * @param country the country
	 * @return the http solr client
	 */

	@Override
	public HttpSolrClient solrClient(String country) {
		if (country!=null) {
			String solrUrl = solrConfig.getSolrUrl() + SolrSearchConstants.SLASH_SOLR_SLASH
					+ solrConfig.getContentPageCollectionName() + "-" + country.toLowerCase();
			LOG.debug("Solr Url is : {}",solrUrl);
			return new HttpSolrClient.Builder(solrUrl).withHttpClient(restClient.getHttpClient()).build();
		}else {
			country = CommonConstants.CONST_DEFAULT_COUNTRY;
			String solrUrl = solrConfig.getSolrUrl() + SolrSearchConstants.SLASH_SOLR_SLASH
					+ solrConfig.getContentPageCollectionName() + "-" + country.toLowerCase();
			LOG.debug("Solr Url is : {}",solrUrl);
			return new HttpSolrClient.Builder(solrUrl).withHttpClient(restClient.getHttpClient()).build();
			}
			}

	/**
	 * Gets the all solr clients.
	 *
	 * @return the all solr clients
	 * @throws LoginException the login exception
	 */
	@Override
	public List<HttpSolrClient> getAllSolrClients() throws LoginException {
		ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
		ArrayList<HttpSolrClient> solrClientLists = new ArrayList<>();
		ArrayList<String> countriesList = new ArrayList<>();
		if (null != bdbApiEndpointService
				&& null != resourceResolver.getResource(bdbApiEndpointService.countryDropdownEndpoint())) {
			Resource genericListResource = resourceResolver
					.getResource(bdbApiEndpointService.countryDropdownEndpoint());
			Resource listResource = resourceResolver.getResource(genericListResource.getPath()
					+ CommonConstants.JCR_CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.LIST);
			if (listResource != null && listResource.hasChildren()) {
				Iterator<Resource> items = listResource.listChildren();
				while (items.hasNext()) {
					Resource itemResource = items.next();
					ValueMap valueMap = itemResource.getValueMap();
					String country = CommonHelper.getPropertyValue(valueMap, CommonConstants.VALUE);
					if (StringUtils.isNotEmpty(country.trim())) {
						countriesList.add(country);
						String solrUrl = solrConfig.getSolrUrl() + SolrSearchConstants.SLASH_SOLR_SLASH
								+ solrConfig.getContentPageCollectionName() + "-" + country.toLowerCase();
						LOG.debug("Solr Url in getAllSolrClients(): {}",solrUrl);
						solrClientLists.add(new HttpSolrClient.Builder(solrUrl).withHttpClient(restClient.getHttpClient()).build());
					}
				}
			} else {
				LOG.debug("Either Generic List does not exist or it is empty.");
			}
		}
		return solrClientLists;
	}

	/**
	 * Gets the all countries.
	 *
	 * @param resourceResolver the resource resolver
	 * @return the all countries
	 * @throws LoginException the login exception
	 */
	@Override
	public List<String> getAllCountries(ResourceResolver resourceResolver) throws LoginException {
		ArrayList<String> countriesList = new ArrayList<>();
		if (null != bdbApiEndpointService
				&& null != resourceResolver.getResource(bdbApiEndpointService.countryDropdownEndpoint())) {
			Resource genericListResource = resourceResolver
					.getResource(bdbApiEndpointService.countryDropdownEndpoint());
			Resource listResource = resourceResolver.getResource(genericListResource.getPath()
					+ CommonConstants.JCR_CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.LIST);
			if (listResource != null && listResource.hasChildren()) {
				Iterator<Resource> items = listResource.listChildren();
				while (items.hasNext()) {
					Resource itemResource = items.next();
					ValueMap valueMap = itemResource.getValueMap();
					String value = CommonHelper.getPropertyValue(valueMap, CommonConstants.VALUE);
					if (StringUtils.isNotEmpty(value.trim())) {
						countriesList.add(value);
					}
				}
			} else {
				LOG.debug("Either Generic List does not exist or it is empty.");
			}
		}
		return countriesList;
	}

	/**
	 * Solr client.
	 *
	 * @return the http solr client
	 */
	@Override
	public HttpSolrClient solrClientPopSearch() {
		String solrUrl = solrConfig.getSolrUrl() + SolrSearchConstants.SLASH_SOLR_SLASH
				+ solrConfig.getPopularSearchedCollectionName();
		return new HttpSolrClient.Builder(solrUrl).withHttpClient(restClient.getHttpClient()).build();
	}

	/**
	 * Gets the type from assets.
	 *
	 * @param asset the asset
	 * @return the type from assets
	 */
	@Override
	public String getTypeFromAssets(Asset asset) {
		String type = asset.getMetadataValue(DamConstants.DC_FORMAT); 
		if (type != null) {
			if (type.contains("application/pdf")) {
				type = "Adobe PDF";
			} else if (type.contains("application/vnd.ms-excel")
					|| type.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
				type = "Microsoft Excel";
			} else if (type.contains("text/html")) {
				type = "Web Page";
			} else if (type.contains(JcrPackage.MIME_TYPE)) {
				type = "ZIP Archive";
			} else if (type.contains("application/xml")) {
				type = "XML Document";
			} else if (type.contains(SolrSearchConstants.PPT_FILE_FORMAT)
					|| type.contains(SolrSearchConstants.PPTX_FILE_FORMAT)) {
				type = "Microsoft PowerPoint";
			} else if (type.contains("text/plain")) {
				type = "Text File";
			} else if (type.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
					|| type.contains("application/msword")) {
				type = "Microsoft Word";
			} else if (type.contains("text/csv")) {
				type = "Comma Separated Value";
			} else if (type.contains("application/rtf")) {
				type = "Rich Text File";
			} else if (type.contains("image/png")) {
				type = "PNG Image";
			}
		}
		return type;
	}

	/**
	 * Index assets to solr.
	 *
	 * @param asset the asset
	 * @return the string
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws SAXException   the SAX exception
	 * @throws TikaException  the tika exception
	 * @throws LoginException the login exception
	 */
	@Override
	public void indexAssetsToSolr(Asset asset, ResourceResolver resourceResolver) throws IOException, SAXException, TikaException, LoginException {
		ArrayList<String> countriesList = (ArrayList<String>) getAllCountries(resourceResolver);
		HashMap<String, ArrayList<SolrInputDocument>> countryRelatedPdfMap = getCountryRelatedProductMap(resourceResolver);
		
		String pdfCombinedFields = solrConfig.getCombinedFieldsForPDFs();
		String[] pdfCombinedFieldsArr = pdfCombinedFields.split(",");
		List<String> pdfCombinedFieldsSplCharsList = decodeSpecialCharacters();
		
		JsonObject countryRelatedPdfJson = createAssetMetadataObject(asset, pdfCombinedFieldsArr, pdfCombinedFieldsSplCharsList);
		LOG.debug("countryRelatedPfdJson : {}",countryRelatedPdfJson.toString());
		countryRelatedPdfMap = setCountryRelatedPdfSolrDocs(countryRelatedPdfJson, countryRelatedPdfMap);
		commitingProductsToSolr(countriesList,countryRelatedPdfMap);
	}
	
	/**
	 * Index video thumbnail to solr.
	 *
	 * @param asset the asset
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SAXException the SAX exception
	 * @throws TikaException the tika exception
	 * @throws LoginException the login exception
	 */
	public void indexVideoThumbnailToSolr(Asset asset,ResourceResolver resolver) throws IOException, SAXException, TikaException, LoginException {
		ArrayList<String> countriesList = (ArrayList<String>) getAllCountries(resolver);
		HashMap<String, ArrayList<SolrInputDocument>> countryRelatedVideoMap = getCountryRelatedProductMap(resolver);
		JsonObject videoJson = createVideoThumbnailMetadataObject(asset);
		countryRelatedVideoMap = setCountryRelatedPdfSolrDocs(videoJson, countryRelatedVideoMap);
		commitingProductsToSolr(countriesList,countryRelatedVideoMap);
	}

	/**
	 * Commit all pdfs to solr.
	 *
	 * @param assetArray the asset array
	 * @throws LoginException the login exception
	 */
	private void commitAllPdfsToSolr(JsonArray assetArray) throws LoginException {
		ArrayList<SolrInputDocument> solrDocs = new ArrayList<>();
		ArrayList<HttpSolrClient> solrClients = (ArrayList<HttpSolrClient>) getAllSolrClients();
		
		for (int index = 0; index < assetArray.size(); index++) {
			JsonObject pageJsonObject = assetArray.get(index).getAsJsonObject();
			SolrInputDocument doc = createAssetSolrDoc(pageJsonObject);
			solrDocs.add(doc);
		}
		for (HttpSolrClient server : solrClients) {
			try {
				server.add(solrDocs);
				server.commit();
				server.close();
			} catch (SolrServerException | IOException | HttpSolrClient.RemoteSolrException e) {
				LOG.error("Exception while indexing document to solr", e);
			}
		}
	}
	
	/**
	 * Commit all video thumbnails to solr.
	 *
	 * @param assetArray the asset array
	 * @throws LoginException the login exception
	 */
	private void commitAllVideoThumbnailsToSolr(JsonArray assetArray) throws LoginException {
		ArrayList<SolrInputDocument> solrDocs = new ArrayList<>();
		ArrayList<HttpSolrClient> solrClients = (ArrayList<HttpSolrClient>) getAllSolrClients();
		for (int index = 0; index < assetArray.size(); index++) {
			JsonObject pageJsonObject = assetArray.get(index).getAsJsonObject();
			SolrInputDocument doc = createAssetSolrDoc(pageJsonObject);
			solrDocs.add(doc);
		}
		for (HttpSolrClient server : solrClients) {
			try {
				server.add(solrDocs);
				server.commit();
				server.close();
			} catch (SolrServerException | IOException | HttpSolrClient.RemoteSolrException e) {
				LOG.error("Exception while indexing video thumbnail to solr", e);
			}
		}
	}
	
	/**
	 * Creates the asset solr doc.
	 *
	 * @param pageJsonObject the page json object
	 * @return the solr input document
	 */
	private SolrInputDocument createAssetSolrDoc(JsonObject pageJsonObject) {

		LOG.debug("Converting JSON to SOLR DOC:");
		SolrInputDocument doc = new SolrInputDocument();
		List<String> combinedField = new ArrayList<>();
		for (Entry<String, JsonElement> pageJsonElement : pageJsonObject.entrySet()) {
			if("concatenatedQueryField_t".equalsIgnoreCase(pageJsonElement.getKey()) && pageJsonElement.getValue().isJsonArray()) {
				for(JsonElement element : pageJsonElement.getValue().getAsJsonArray()) {
					if(!element.isJsonNull()) {
						combinedField.add(element.getAsString());
					}
				}
				doc.addField(pageJsonElement.getKey(), combinedField);
			}
			else {
				doc.addField(pageJsonElement.getKey(), pageJsonElement.getValue().getAsString());
			}
		}
		return doc;
	}

	/**
	 * Creates the asset metadata object.
	 *
	 * @param asset the asset
	 * @return the json object
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws SAXException   the SAX exception
	 * @throws TikaException  the tika exception
	 * @throws LoginException the login exception
	 */
	private JsonObject createAssetMetadataObject(Asset asset, String[] combinedFieldIndexes, List<String> splCharsToEscape)
			throws IOException, SAXException, TikaException, LoginException {
		LOG.debug("Entry createAssetMetadataObject");
		JsonObject doc = new JsonObject();
		ResourceResolver resolver = CommonHelper.getReadServiceResolver(resolverFactory);
		JsonObject countryRelatedPdfJson = new JsonObject();
		ArrayList<String> countryList = (ArrayList<String>) getAllCountries(resolver);
		String jcrNodePath = asset.getPath() + SolrSearchConstants.CONSTANT_SLASH
				+ SolrSearchConstants.JCR_CONTENT_NODE_NAME;
		String metaDataPath = jcrNodePath + SolrSearchConstants.CONSTANT_SLASH + SolrSearchConstants.METADATA;
		Resource mdRes = resolver.getResource(metaDataPath);
		String damPath = asset.getPath();
		if (mdRes != null) {
			doc.addProperty(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_T,
					SolrSearchConstants.PDF_ASSET);
			doc.addProperty(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S,
					SolrSearchConstants.DOCUMENTS);
			doc.addProperty(CommonConstants.UNIQUE_IDENTIFIER, asset.getPath());
			doc.addProperty(SolrSearchConstants.SOLRDOC_FIELD_ID, asset.getPath());
			doc.addProperty(SolrSearchConstants.SOLRDOC_FIELD_URL.concat(SolrSearchConstants.UNDERSCORE_T),
					asset.getPath().replace(" ", "%20"));
			doc.addProperty(CommonConstants.NAME.concat(SolrSearchConstants.UNDERSCORE_T), asset.getName());
			doc.addProperty("isAvailable".concat(SolrSearchConstants.UNDERSCORE_T), "false");
			doc.addProperty("validityStartDate".concat(SolrSearchConstants.UNDERSCORE_DT), "1900-12-08T14:13:15Z");
			getLastReplicated(doc, resolver, jcrNodePath);

			ValueMap assetMetadataProperties = mdRes.adaptTo(ValueMap.class);
			if (assetMetadataProperties != null) {
				String pdfDocumentType = SolrUtils.checkNullProperty(assetMetadataProperties, SolrSearchConstants.PDFX_DOC_TYPE);
				pdfDocumentType = StringUtils.isBlank(pdfDocumentType) ? SolrUtils.checkNullProperty(assetMetadataProperties, CommonConstants.DOC_TYPE) : pdfDocumentType;
				if (StringUtils.isNotBlank(pdfDocumentType) && StringUtils.isNotEmpty(pdfDocumentType)) {
					doc.addProperty(SolrSearchConstants.SOLR_FIELD_PDFX_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_T), pdfDocumentType);
					doc.addProperty(SolrSearchConstants.SOLR_FIELD_PDFX_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S), pdfDocumentType);
					doc.addProperty(SolrSearchConstants.SCIENTIFIC_RES_ASSET_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_T),
							pdfDocumentType.replace(CommonConstants.SPACE, StringUtils.EMPTY));
					doc.addProperty(SolrSearchConstants.SCIENTIFIC_RES_ASSET_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S),
							pdfDocumentType.replace(CommonConstants.SPACE, StringUtils.EMPTY));
				}else {
					doc.addProperty(SolrSearchConstants.SOLR_FIELD_PDFX_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties,SolrSearchConstants.PRODUCT_PDFX_DOC_TYPE));
					doc.addProperty(SolrSearchConstants.SOLR_FIELD_PDFX_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S),
							SolrUtils.checkNullProperty(assetMetadataProperties,SolrSearchConstants.PRODUCT_PDFX_DOC_TYPE));
					String assetTypeWithoutSpace = SolrUtils.checkNullProperty(assetMetadataProperties, SolrSearchConstants.PRODUCT_PDFX_DOC_TYPE).replace(CommonConstants.SPACE, StringUtils.EMPTY);
					doc.addProperty(SolrSearchConstants.SCIENTIFIC_RES_ASSET_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_T),
							assetTypeWithoutSpace);
					doc.addProperty(SolrSearchConstants.SCIENTIFIC_RES_ASSET_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S),
							assetTypeWithoutSpace);
				}
				doc.addProperty(SolrSearchConstants.SOLRDOC_FIELD_ASSETYPE.concat(SolrSearchConstants.UNDERSCORE_T),
						getTypeFromAssets(asset));
				String body = parseAssettoText(asset);
				doc.addProperty(SolrSearchConstants.SOLRDOC_FIELD_BODY.concat(SolrSearchConstants.UNDERSCORE_T), body);
				
				/** Getting the base path of the product along with the hp node resource */
				/** The base path and hp node resource are required only for ON DEMAND TDS*/
				String basePath = getProductBasePath(damPath);
				Resource hpRes = CommonHelper.getBaseHpResource(resolver.getResource(basePath));
				
				// get variant resource
				ValueMap hpNodeProperties = hpRes.adaptTo(ValueMap.class);
				String skuName = SolrUtils.checkNull(hpNodeProperties.get(CommonConstants.BASE_MATERIAL_NUMBER, String.class)).replace("_base", "");
				Resource variantRes = CommonHelper.getProductFromLookUp(resolver, skuName, CommonConstants.MATERIAL_NUMBER);
				
				if (variantRes != null) {
					Resource sapCcRes = variantRes.getChild(CommonConstants.SAP_CC_NODENAME);
					if(sapCcRes != null) {
						ValueMap sapNodeProperties = sapCcRes.adaptTo(ValueMap.class);
						String phantomParents = sapNodeProperties.get(CommonConstants.PHANTOM_PARENTS, String.class);
						if(phantomParents != null) {
							String regex = "[(){}<>\\[\\]]";
					        doc.addProperty(CommonConstants.PHANTOM_PARENTS.concat(SolrSearchConstants.UNDERSCORE_TXT), phantomParents.replaceAll(regex, ""));
						}
					}
				}
				
				/** Condition to check if the metadata properties of a pdf document contains the property
				 * tdsDocumentType and its value is ON_DEMAND_TDS. If the condition satisfies, getting the values 
				 * for the custom meta properties from hp node and metadata node of pdf document and appending it under the json object
				 * in key-value pair format. If condition will exceute for OnDemandTds and else condition will exceute for published TDS */
				if( null != hpRes && CommonConstants.ON_DEMAND_TDS.equals(assetMetadataProperties.get(CommonConstants.TDS_DOCUMENT_TYPE))) {
					/** Getting the value map of HP Node properties of the product along with the sku name and variant resource */
					
					doc.addProperty((SolrSearchConstants.DOC_SKU).concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNull(hpNodeProperties.get(CommonConstants.BASE_MATERIAL_NUMBER, String.class)).replace("_base", ""));
					doc.addProperty((SolrSearchConstants.DOC_TITLE).concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNull(hpNodeProperties.get(CommonConstants.LABEL_DESCRIPTION, String.class)));
					doc.addProperty((SolrSearchConstants.BDB_ASSET_DOC_TYPE).concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties, SolrSearchConstants.DOCUMENT_TYPE));
					doc.addProperty((SolrSearchConstants.DOC_DESC).concat(SolrSearchConstants.UNDERSCORE_T), getDcDescription(hpNodeProperties));
					doc.addProperty(CommonConstants.DCTITLE.concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties, CommonConstants.DC_TITLE));
					doc.addProperty(CommonConstants.DOC_KEYWORDS.concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNull(hpNodeProperties.get(CommonConstants.KEYWORDS, String.class)));
					doc.addProperty(CommonConstants.PRODUCT_NAME.concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNull(hpNodeProperties.get(CommonConstants.BRAND_KEY, String.class)));
					doc.addProperty(SolrSearchConstants.SOLR_FIELD_DC_DESCRIPTION.concat(SolrSearchConstants.UNDERSCORE_T),
                			getDcDescription(hpNodeProperties));
					doc.addProperty(CommonConstants.TDS_DOCUMENT_TYPE.concat(SolrSearchConstants.UNDERSCORE_T), CommonConstants.ON_DEMAND_TDS);
					List<String> combinedFields = buildConcatenatedQueryFieldForPDFs(asset.getName(), assetMetadataProperties, doc, combinedFieldIndexes, splCharsToEscape);
					JsonElement element = new Gson().toJsonTree(combinedFields);
					doc.add(CommonConstants.CONCATENATED_QUERY_FIELD.concat(SolrSearchConstants.UNDERSCORE_T), element);
					if (variantRes != null) {
						try {
							/** Getting the countryRelatedPdfJson using the custom getOnDemandCountryRelatedJson method*/
							countryRelatedPdfJson = getOnDemandCountryRelatedJson(resolver, variantRes, doc,countryList);
						} catch (RepositoryException e) {
							LOG.error("Exception occured while getting the countryRelatedPdfJson for ON_DEMAND_TDS", e);
						}
					}
				} else {
					doc.addProperty((SolrSearchConstants.DOC_SKU).concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties, SolrSearchConstants.DOC_SKU));
					doc.addProperty((SolrSearchConstants.DOC_TITLE).concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties, SolrSearchConstants.DOC_TITLE));
					doc.addProperty((SolrSearchConstants.BDB_ASSET_DOC_TYPE).concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties, SolrSearchConstants.DOCUMENT_TYPE));
					doc.addProperty((SolrSearchConstants.DOC_DESC).concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties, SolrSearchConstants.DOC_DESC));
					doc.addProperty(CommonConstants.DCTITLE.concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties, CommonConstants.DC_TITLE));
					doc.addProperty(CommonConstants.DOC_KEYWORDS.concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties, CommonConstants.DOC_KEYWORDS));
					doc.addProperty(CommonConstants.PRODUCT_NAME.concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties, CommonConstants.PRODUCT_NAME));
					doc.addProperty(SolrSearchConstants.SOLR_FIELD_DC_DESCRIPTION.concat(SolrSearchConstants.UNDERSCORE_T),
							SolrUtils.checkNullProperty(assetMetadataProperties, SolrSearchConstants.DC_DESCRIPTION));
					List<String> combinedFields = buildConcatenatedQueryFieldForPDFs(asset.getName(), assetMetadataProperties, doc, combinedFieldIndexes, splCharsToEscape);
					JsonElement element = new Gson().toJsonTree(combinedFields);
					doc.add(CommonConstants.CONCATENATED_QUERY_FIELD.concat(SolrSearchConstants.UNDERSCORE_T), element);
					countryRelatedPdfJson = getCountryRelatedPdfJson(countryList, mdRes, doc, resolver);
				}
			}
		}
		LOG.debug("!!!!!!!!!!!!!!!!createAssetMetadataObject end!!!!!!!!!!!!!!!!!");
		CommonHelper.closeResourceResolver(resolver);
		return countryRelatedPdfJson;
	}
	
	/**
	 * Gets the concatenatedQueryField for OnDemand TDS as well as published TDS.
	 *
	 * @param assetName  name of the asset as string
	 * @param combinedFieldIndexes the string array of indexes [name_t, dctitle_t, dcDescription_t, docTitle_t, docDesc_t, docKeywords_t, productName_t]
	 * @param onDemandTdsDoc the JsonObject
	 * @param properties the valueMap of metadata node of pdf
	 * @param List the list of splchar
	 * @return the country List of combinedFields
	 */
	private List<String> buildConcatenatedQueryFieldForPDFs(String assetName, ValueMap properties, JsonObject onDemandTdsDoc, String[] combinedFieldIndexes, List<String> splCharsToEscape) {
		LOG.debug("Entry buildConcatenatedQueryFieldForPDFs method");
		List<String> combinedFields = new ArrayList<>();
		String[] charsToEscape = splCharsToEscape.toArray(new String[splCharsToEscape.size()]);
		JsonObject modifiedJsonObject = new JsonObject();
		if (onDemandTdsDoc.has(CommonConstants.TDS_DOCUMENT_TYPE.concat(SolrSearchConstants.UNDERSCORE_T))) {
			for (String key : onDemandTdsDoc.keySet()) {
				String modifiedKey = key.split("_")[0];
				modifiedJsonObject.add(modifiedKey, onDemandTdsDoc.get(key));
			}
		}
		if(null != combinedFieldIndexes && null != splCharsToEscape) {
			for(String index : combinedFieldIndexes) {
				String field = index.split("_")[0];
				String value = StringUtils.EMPTY;
				if("name".equalsIgnoreCase(field)) {
					value = replaceCombinedFieldCharsWithSpace(assetName, charsToEscape, " ") + " " + replaceCombinedFieldCharsWithSpace(assetName, charsToEscape, "");
					if(StringUtils.isNotBlank(value)) {
						combinedFields.add(value);
					}
				}
				else {
					if("dcTitle".equalsIgnoreCase(field)) {
						field =  CommonConstants.DC_TITLE;
					}
					else if ("dcDescription".equalsIgnoreCase(field)) {
						field =  SolrSearchConstants.DC_DESCRIPTION;
					}
					if (modifiedJsonObject.has(CommonConstants.TDS_DOCUMENT_TYPE)) {
						// Getting the value from the modifiedJsonObject using checkNullPropertyOnDemandTDS method for ON_DEMAND_TDS
						value = checkNullPropertyOnDemandTDS(modifiedJsonObject, field);
					} else {
						// Getting the value from the modifiedJsonObject using checkNullProperty method for Published TDS
						value = SolrUtils.checkNullProperty(properties, field);
					}
					value = replaceCombinedFieldCharsWithSpace(value, charsToEscape, " ") + " " + replaceCombinedFieldCharsWithSpace(value, charsToEscape, "");
					if(StringUtils.isNotBlank(value)) {
						combinedFields.add(value);
					}
				}
			}
		}
		LOG.debug("Exit buildConcatenatedQueryFieldForPDFs method");
		return combinedFields;
	}
	
	/**
	 * Gets the value for OnDemand TDS property for concatenatedQueryField .
	 *
	 * @param modifiedJsonObject  the Json Object
	 * @param property the string
	 * @return the value for each property from the modifiedJsonObject
	 */
	private String checkNullPropertyOnDemandTDS(JsonObject modifiedJsonObject, String property) {
		if (property.equalsIgnoreCase("dc:description")) {
			return modifiedJsonObject.get("dcDescription").toString();
		}
		if (null == modifiedJsonObject.get(property)) {
			return "";
		} else {
			return modifiedJsonObject.get(property).toString();
		}
	}

	/**
	 * Gets the country related pdf json.
	 *
	 * @param countryList the country list
	 * @param mdResource the md resource
	 * @param pdfDocJson the pdf doc json
	 * @param resolver the resolver
	 * @return the country related pdf json
	 */
	private JsonObject getCountryRelatedPdfJson(ArrayList<String> countryList, Resource mdResource, JsonObject pdfDocJson, ResourceResolver resolver) {
		JsonObject countryRelatedPdfJson = new JsonObject();
		
		//Updated this call to get common regions to index of product and pdf for TDS docs
		List<String> tagsArray = getCountriesToIndex(mdResource, resolver);
		LOG.debug("===============pdfJson============ {}", pdfDocJson.get("name_t"));
		if(tagsArray.contains(CommonConstants.GLOBAL)) {
			pdfDocJson.addProperty("isAvailable".concat(SolrSearchConstants.UNDERSCORE_T), "true");
			for(String country : countryList) {
				countryRelatedPdfJson.add("bdbio-" + country.toLowerCase(),pdfDocJson);
			}
		} else {
			if(tagsArray.contains("emea")) {
				Resource countryListResource = resolver.getResource(CommonConstants.EMEA_COUNTRIES_LIST_PATH);
				Iterator<Resource> items = countryListResource.listChildren();
	            while (items.hasNext()) {
	                Resource countryResource = items.next();
	                if (null != countryResource) {
	                    ValueMap properties = countryResource.getValueMap();
	                    String countryId = CommonHelper.getPropertyValue(properties, "value").toLowerCase();
	                    pdfDocJson.addProperty("isAvailable".concat(SolrSearchConstants.UNDERSCORE_T), "true");
	                    countryRelatedPdfJson.add("bdbio-" + countryId,pdfDocJson);
	                }
	            }
		} if(tagsArray.contains(CommonConstants.APAC_EN)) {
			pdfDocJson.addProperty("isAvailable".concat(SolrSearchConstants.UNDERSCORE_T), "true");
			ArrayList<String>apacCountriesList = CommonHelper.getApacTagRegions(resolver,CommonConstants.APAC_EN_REGION_TAG);
			if(apacCountriesList.size()>0) {
            	for(String countryId:apacCountriesList) {
            		countryRelatedPdfJson.add("bdbio-" + countryId,pdfDocJson);
            	}
			}
		} if(tagsArray.contains(CommonConstants.APAC)) {
			pdfDocJson.addProperty("isAvailable".concat(SolrSearchConstants.UNDERSCORE_T), "true");
			ArrayList<String>apacCountriesList = CommonHelper.getApacTagRegions(resolver,CommonConstants.APAC_REGION_TAG);
			if(apacCountriesList.size()>0) {
            	for(String countryId:apacCountriesList) {
            		countryRelatedPdfJson.add("bdbio-" + countryId,pdfDocJson);
            	}
			}
		} 
		
			
//			for(String country : countryList) {
//				countryRelatedPdfJson.add("bdbio-" + country.toLowerCase(),pdfDocJson);
//			}
			JsonObject availabilityTrueJson = new JsonParser()
					.parse(pdfDocJson.toString()).getAsJsonObject();
			availabilityTrueJson.addProperty("isAvailable".concat(SolrSearchConstants.UNDERSCORE_T), "true");
			for(String country : tagsArray) {
				if(country.equals("uk")) {
					country = "gb";
				}
				String isGBSameAsEU = solrConfig.isEUSameAsGB();
				if(StringUtils.isNotEmpty(isGBSameAsEU) && "true".equalsIgnoreCase(isGBSameAsEU) && country.equals("gb")) {
					countryRelatedPdfJson.add("bdbio-eu" ,availabilityTrueJson);
				}
				countryRelatedPdfJson.add("bdbio-" + country.toLowerCase(),availabilityTrueJson);
			}
		}
		LOG.debug("!!!!!!!!!!!!!!!!getCountryRelatedPdfJson end!!!!!!!!!!!!!!!!!");
		return countryRelatedPdfJson;
	}
	
	/**
	 * Gets the country related pdf json.
	 *
	 * @param countryList the country list
	 * @param resourceResolver the resource
	 * @param variantRes the resource
	 * @param pdfDocJson the pdf doc json
	 * @return the country related pdf json
	 */
	private JsonObject getOnDemandCountryRelatedJson(ResourceResolver resourceResolver, Resource variantRes, JsonObject pdfDocJson,
			ArrayList<String> countriesList) throws RepositoryException {
		LOG.debug("Entry getOnDemandCountryRelatedJson method");
		JsonObject countryRelatedPdfJson = new JsonObject();
		Node regionDetailsNode = variantRes.getChild(CommonConstants.SAP_CC_NODENAME).getChild(CommonConstants.REGION_DETAILS_NODE_NAME).adaptTo(Node.class);
		PropertyIterator propertyIterator = regionDetailsNode.getProperties();
		// Iterating the region-details node of the sap-cc node of a product
		while (propertyIterator.hasNext()) {
			Property property = propertyIterator.nextProperty();
			if(CommonConstants.COUNTRIES_LIST.contains(property.getName().toLowerCase()))
			 {
				JsonObject countryDetails = new JsonParser().parse(property.getValue().getString())
					.getAsJsonObject();
				// Condition to check if the element of a region-details node contains the keys derivedProductStatus
				// and validityStartDate. If yes, checking whether the derivedProductStatus is DISPLAYONLY or PURCHASEABLE
				// adding the isAvailable_t key to the JsonObject only where the product is available.
				if (countryDetails.has(CommonConstants.DERIVED_PRODUCT_STATUS) && countryDetails.has(CommonConstants.VALIDITY_START_DATE)) {
					String derivedProductStatus = countryDetails.get(CommonConstants.DERIVED_PRODUCT_STATUS).getAsString();
					if (CommonConstants.DISPLAY_ONLY.equals(derivedProductStatus) || CommonConstants.PURCHASEABLE.equals(derivedProductStatus)) {
						pdfDocJson.addProperty(CommonConstants.IS_AVAILABLE.concat(SolrSearchConstants.UNDERSCORE_T), "true");
						countryRelatedPdfJson.add(SolrSearchConstants.BDBIO_HYPHEN + property.getName().toLowerCase(), pdfDocJson);
						}
					}
				}
			}
		LOG.debug("Exit getOnDemandCountryRelatedJson method");
		return countryRelatedPdfJson;
		}
	
	/**
	 * Gets the countries to index to which the document should be indexed. if its an TDS then common countries from pdf docRegion and 
	 * available countries from product sap-cc will returned by this method. 
	 *
	 * @param mdResource the md resource
	 * @param resolver   the resolver
	 * @return the countries to index
	 */
	public List<String> getCountriesToIndex(Resource mdResource, ResourceResolver resolver) {
		String mdResourcePath = mdResource.getPath();
		ValueMap valueMap = mdResource.getValueMap();
		String mdResourceType = valueMap.get(CommonConstants.DC_FORMAT, StringUtils.EMPTY);
		//added the conditions to check for the availability of the product in the region for all doc types and index in SOLR on the basis of product availability only
		if ((CommonHelper.isDocTypeMatching(CommonConstants.DATA_TDS, valueMap)
				|| CommonHelper.isDocTypeMatching(CommonConstants.DATA_IFU_TYPE, valueMap)
				|| CommonHelper.isDocTypeMatching(CommonConstants.DATA_UG_TYPE, valueMap)
				|| CommonHelper.isDocTypeMatching(CommonConstants.DATA_EBOOK_TYPE, valueMap)
				|| CommonHelper.isDocTypeMatching(CommonConstants.DATA_PI_TYPE, valueMap)
				|| CommonHelper.isDocTypeMatching(CommonConstants.DATA_ID_TYPE, valueMap)
				|| CommonHelper.isDocTypeMatching(CommonConstants.DATA_PUBLISHED_TDS, valueMap))
				&& StringUtils.isNotBlank(mdResourcePath) && StringUtils.isNotBlank(mdResourceType)
				&& StringUtils.contains(mdResourcePath, bDBWorkflowConfigService.getDamAssetBasePath())
				&& StringUtils.contains(mdResourcePath, CommonConstants.FORWARD_SLASH_PDF)
				&& StringUtils.equals(mdResourceType, CommonConstants.APPLICATION_PDF)) {
			LOG.debug("Going to index Technical data sheet (TDS) {}", mdResourcePath);
			return getCountries(mdResource, mdResourcePath, resolver);

		} else {
			return SolrUtils.getPdfTags(mdResource, resolver);
		}
	}

	/**
	 * Gets the countries to which TDS should be indexed.
	 *
	 * @param mdResource     the md resource
	 * @param mdResourcePath the md resource path
	 * @param resolver       the resolver
	 * @return the countries
	 */
	private List<String> getCountries(Resource mdResource, String mdResourcePath, ResourceResolver resolver) {
		ArrayList<String> docTagsList = SolrUtils.getPdfTags(mdResource, resolver);
		if (docTagsList.contains(CommonConstants.GLOBAL)) {
			try {
				docTagsList = (ArrayList<String>) getAllCountries(resolver);
			} catch (LoginException e) {
				LOG.debug("Could not get countries list from acs common", e);
			}
		}
		return getAllCountriesFromProducts(mdResource, mdResourcePath, resolver, docTagsList);
	}

	/**
	 * Gets the all countries from products in which its available and if the document is docpartId type 
	 * then from the all variants it should pick the countries.
	 *
	 * @param mdResource     the md resource
	 * @param mdResourcePath the md resource path
	 * @param resolver       the resolver
	 * @param docTagsList    the doc tags list
	 * @return the all countries from products
	 */
	private List<String> getAllCountriesFromProducts(Resource mdResource, String mdResourcePath,
			ResourceResolver resolver, ArrayList<String> docTagsList) {
		LOG.debug("Entry getAllCountriesFromProducts of SolrSearchServiceImpl");
		Set<String> regionsToIndex = new HashSet<>();
		String productBasePath = getProductBasePath(mdResourcePath);
		String pdfName = CommonHelper.getAssetNameFromPath(mdResource.getParent().getParent().getPath());

		if (isDocPartIdsPdf(resolver, productBasePath, pdfName)) {
			Query query = CommonHelper.getQuery(resolver, CommonHelper.getQueryMapForProductNode(productBasePath,
					JcrResourceConstants.NT_SLING_FOLDER, CommonConstants.IS_VARIANT, CommonConstants.STRING_TRUE));
			List<String> resourcesFromQuery = workflowHelperService.getResourcesFromQuery(query);
			for (String resourcePath : resourcesFromQuery) {
				updateCountriesToIndex(resourcePath
						.substring(resourcePath.lastIndexOf(CommonConstants.SINGLE_SLASH) + 1, resourcePath.length()),
						regionsToIndex, docTagsList, resolver);
			}
		} else {
			updateCountriesToIndex(pdfName, regionsToIndex, docTagsList, resolver);
		}
		LOG.debug("Exit getAllCountriesFromProducts of SolrSearchServiceImpl");
		return new ArrayList<>(regionsToIndex);
	}

	/**
	 * Update countries to index.
	 *
	 * @param matNumber      the mat number
	 * @param regionsToIndex the regions to index
	 * @param docTagsList    the doc tags list
	 * @param resolver       the resolver
	 */
	private void updateCountriesToIndex(String matNumber, Set<String> regionsToIndex, ArrayList<String> docTagsList,
			ResourceResolver resolver) {
		for (String country : docTagsList) {
			if(country.equalsIgnoreCase("emea")) {
				Resource countryListResource = resolver.getResource(CommonConstants.EMEA_COUNTRIES_LIST_PATH);
				Iterator<Resource> items = countryListResource.listChildren();
	            while (items.hasNext()) {
	                Resource countryResource = items.next();
	                if (null != countryResource) {
	                    ValueMap properties = countryResource.getValueMap();
	                    String countryId = CommonHelper.getPropertyValue(properties, "value").toLowerCase();
	                    if (CommonHelper.getProductAvailabilityInRegion(matNumber, countryId, resolver)) {
	        				regionsToIndex.add(country);
	        			}
	                }
	            }
			} if (country.equalsIgnoreCase(CommonConstants.APAC_EN) || country.equalsIgnoreCase(CommonConstants.APAC)) {
				String regionTag = CommonConstants.REGION_TAG + country;
				ArrayList<String>apacCountriesList = CommonHelper.getApacTagRegions(resolver,regionTag);
                if(apacCountriesList.size()>0) {
                	for(String countryId:apacCountriesList) {
                		if (CommonHelper.getProductAvailabilityInRegion(matNumber, countryId, resolver)) {
	        				regionsToIndex.add(country);
	        			}
                	}
                }
			}
			
			if (CommonHelper.getProductAvailabilityInRegion(matNumber, country, resolver)) {
				regionsToIndex.add(country);
			}
		}
	}

	/**
	 * Checks if is docPartIDs pdf.
	 *
	 * @param resolver        the resolver
	 * @param productBasePath the product base path
	 * @param pdfName         the pdf name
	 * @return true, if is doc part ids pdf
	 */
	private boolean isDocPartIdsPdf(ResourceResolver resolver, String productBasePath, String pdfName) {
		Resource basePathResource = resolver.getResource(productBasePath);
		boolean isDocPartIdResource = false;
		if (null != basePathResource) {
			Resource getHpResource = basePathResource.getChild(CommonConstants.HP_NODE);
			if (null != getHpResource) {
				ValueMap valueMap = getHpResource.adaptTo(ValueMap.class);
				if (valueMap.containsKey(CommonConstants.DOC_PART_IDS_CAP)) {
					String docPartIdsValue = valueMap.get(CommonConstants.DOC_PART_IDS_CAP, String.class);
					if (StringUtils.isNotBlank(docPartIdsValue)
							&& CommonHelper.availableInDocpartIds(docPartIdsValue, pdfName)) {
						isDocPartIdResource = true;
					} else {
						isDocPartIdResource = false;
					}
				} else {
					isDocPartIdResource = false;
				}
			}
		}
		LOG.debug("isDocPartIdResource", isDocPartIdResource);
		return isDocPartIdResource;
	}

	/**
	 * Gets the product base path from metadata resource.
	 *
	 * @param mdResourcePath the md resource path
	 * @return the product base path
	 */
	public String getProductBasePath(String mdResourcePath) {
		String basePath = "";
		if (mdResourcePath.contains(CommonConstants.FORWARD_SLASH_PDF)) {
			basePath = mdResourcePath.substring(0, mdResourcePath.lastIndexOf(CommonConstants.FORWARD_SLASH_PDF));
			return basePath.replace(bDBWorkflowConfigService.getDamAssetBasePath(),
					bDBWorkflowConfigService.getVarCommerceBasePath());

		}
		return basePath;
	}
	
	/**
	 * Creates the video thumbnail metadata object.
	 *
	 * @param asset the asset
	 * @return the json object
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SAXException the SAX exception
	 * @throws TikaException the tika exception
	 * @throws LoginException the login exception
	 */
	private JsonObject createVideoThumbnailMetadataObject(Asset asset)
			throws IOException, SAXException, TikaException, LoginException {
		
		String videoCombinedFields = solrConfig.getCombinedFieldsForVideos();
		String[] videoCombinedFieldsArr = videoCombinedFields.split(",");
		List<String> videoCombinedFieldsSplCharsList = decodeSpecialCharacters();
		
		JsonObject doc = new JsonObject();
		JsonObject countryRelatedVideoJson = new JsonObject();
		ResourceResolver resolver = CommonHelper.getReadServiceResolver(resolverFactory);
		ArrayList<String> countryList = (ArrayList<String>) getAllCountries(resolver);
		String jcrNodePath = asset.getPath() + SolrSearchConstants.CONSTANT_SLASH
				+ SolrSearchConstants.JCR_CONTENT_NODE_NAME;
		doc.addProperty(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S,
				"Videos");
		doc.addProperty(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_T,
				"video");
		doc.addProperty(CommonConstants.UNIQUE_IDENTIFIER, asset.getPath());
		doc.addProperty(SolrSearchConstants.SOLRDOC_FIELD_ID, asset.getPath());
		doc.addProperty(SolrSearchConstants.SOLRDOC_FIELD_URL.concat(SolrSearchConstants.UNDERSCORE_T),
				asset.getPath().replace(" ", "%20"));
		doc.addProperty(CommonConstants.NAME.concat(SolrSearchConstants.UNDERSCORE_T), asset.getName());
		doc.addProperty("isAvailable".concat(SolrSearchConstants.UNDERSCORE_T), "false");
		doc.addProperty("validityStartDate".concat(SolrSearchConstants.UNDERSCORE_DT), "1900-12-08T14:13:15Z");
		getLastReplicated(doc, resolver, jcrNodePath);
		String metaDataPath = jcrNodePath + SolrSearchConstants.CONSTANT_SLASH + SolrSearchConstants.METADATA;
		Resource mdRes = resolver.getResource(metaDataPath);
		if (mdRes != null) {
			ValueMap properties = mdRes.adaptTo(ValueMap.class);
			if (properties != null) {
				doc.addProperty(("brightCoveVideoID").concat(SolrSearchConstants.UNDERSCORE_T),
						SolrUtils.checkNullProperty(properties, "brightCoveVideoID"));
				doc.addProperty(("dctitle").concat(SolrSearchConstants.UNDERSCORE_T),
						SolrUtils.checkNullProperty(properties, "videoTitle"));
				doc.addProperty(("dcDescription").concat(SolrSearchConstants.UNDERSCORE_T),
						SolrUtils.checkNullProperty(properties, "videoDescription"));
				doc.addProperty((SolrSearchConstants.SCIENTIFIC_RES_ASSET_DOC_TYPE).concat(SolrSearchConstants.UNDERSCORE_T),
						SolrUtils.checkNullProperty(properties, SCIENTIFIC_VIDEO_TYPE).replace(CommonConstants.SPACE, StringUtils.EMPTY));
				doc.addProperty((SolrSearchConstants.SCIENTIFIC_RES_ASSET_DOC_TYPE).concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S),
						SolrUtils.checkNullProperty(properties, SCIENTIFIC_VIDEO_TYPE).replace(CommonConstants.SPACE, StringUtils.EMPTY));
				doc.addProperty((SolrSearchConstants.SOLR_FIELD_PDFX_DOC_TYPE).concat(SolrSearchConstants.UNDERSCORE_T),
						SolrUtils.checkNullProperty(properties, SCIENTIFIC_VIDEO_TYPE));
				doc.addProperty((SolrSearchConstants.SOLR_FIELD_PDFX_DOC_TYPE).concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S),
						SolrUtils.checkNullProperty(properties, SCIENTIFIC_VIDEO_TYPE));
				
				List<String> combinedVideoFields = buildConcatenatedQueryFieldForVideos(asset.getName(), properties, videoCombinedFieldsArr, videoCombinedFieldsSplCharsList);
				JsonElement element = new Gson().toJsonTree(combinedVideoFields);
				doc.add(CommonConstants.CONCATENATED_QUERY_FIELD.concat(SolrSearchConstants.UNDERSCORE_T), element);
				
				countryRelatedVideoJson = getCountryRelatedPdfJson(countryList, mdRes, doc, resolver);
			}
		}
		CommonHelper.closeResourceResolver(resolver);
		return countryRelatedVideoJson;
	}
	
	/**
	 * Gets the concatenatedQueryField for OnDemand TDS as well as published TDS.
	 *
	 * @param assetName  name of the asset as string
	 * @param combinedFieldIndexes the string array of indexes [name_t, dctitle_t, dcDescription_t, docTitle_t, docDesc_t, docKeywords_t, productName_t]
	 * @param onDemandTdsDoc the JsonObject
	 * @param properties the valueMap of metadata node of pdf
	 * @param List the list of splchar
	 * @return the country List of combinedFields
	 */
	private List<String> buildConcatenatedQueryFieldForVideos(String assetName, ValueMap properties, String[] combinedVideoFieldIndexes, List<String> splCharsToEscape) {
		LOG.debug("Entry buildConcatenatedQueryFieldForVideos method");
		List<String> combinedFields = new ArrayList<>();
		String[] charsToEscape = splCharsToEscape.toArray(new String[splCharsToEscape.size()]);
		if(null != combinedVideoFieldIndexes && null != splCharsToEscape) {
			for(String index : combinedVideoFieldIndexes) {
				String field = index.split("_")[0];
				String value = StringUtils.EMPTY;
				if("name".equalsIgnoreCase(field)) {
					value = replaceCombinedFieldCharsWithSpace(assetName, charsToEscape, " ") + " " + replaceCombinedFieldCharsWithSpace(assetName, charsToEscape, "");
				} else if("dcTitle".equalsIgnoreCase(field)) {
					field =  CommonConstants.DC_TITLE;
					value = SolrUtils.checkNullProperty(properties, field);
					if(null == value || value.isEmpty()) {
						field =  SolrSearchConstants.VIDEO_TITLE;
						value = SolrUtils.checkNullProperty(properties, field);
					}
				}
				else if ("dcDescription".equalsIgnoreCase(field)) {
					field =  SolrSearchConstants.DC_DESCRIPTION;
					value = replaceCombinedFieldCharsWithSpace(value, charsToEscape, " ");
				} else if("docType".equalsIgnoreCase(field)) {
					field =  SolrSearchConstants.VIDEO_TYPE;
					value = SolrUtils.checkNullProperty(properties, field);
				}
				if(StringUtils.isNotBlank(value)) {
					combinedFields.add(value);
				}
				
			}
		}
		LOG.debug("Exit buildConcatenatedQueryFieldForPDFs method");
		return combinedFields;
	}
	
	/**
	 * Gets the last replicated.
	 *
	 * @param doc the doc
	 * @param resolver the resolver
	 * @param jcrNodePath the jcr node path
	 * @return the last replicated
	 */
	private void getLastReplicated(JsonObject doc, ResourceResolver resolver, String jcrNodePath) {
		Resource jcrRes = resolver.getResource(jcrNodePath);
		if (jcrRes != null) {
			ValueMap properties = jcrRes.adaptTo(ValueMap.class);
			if (properties != null) {
				Date lastReplicated = properties.get(SolrSearchConstants.CQ_LAST_REPLICATED, Date.class);
				if (null != lastReplicated)
					doc.addProperty(
							SolrSearchConstants.SOLR_FIELD_LAST_REPLICATED.concat(SolrSearchConstants.UNDERSCORE_DT),
							convertToUtc(lastReplicated));
			}
		}
	}

	/**
	 * Convert to utc.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String convertToUtc(Date date) {
		final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00.00'Z'");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.format(date);
	}

	/**
	 * Parses the assetto text.
	 *
	 * @param asset the asset
	 * @return the string
	 * @throws IOException   Signals that an I/O exception has occurred.
	 * @throws SAXException  the SAX exception
	 * @throws TikaException the tika exception
	 */
	public static String parseAssettoText(Asset asset) throws IOException, SAXException, TikaException {
		AutoDetectParser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler(-1);
		Metadata metadata = new Metadata();
		String docText = StringUtils.EMPTY;

		Resource originalAsset = asset.getOriginal();
		InputStream stream = originalAsset.adaptTo(InputStream.class);
		if (stream != null) {

			metadata.set(TikaMetadataKeys.RESOURCE_NAME_KEY, asset.getName());
			parser.parse(stream, handler, metadata);
			return handler.toString();
		}

		return docText;
	}

	/**
	 * Index hp data to solr.
	 *
	 * @param resourceResolver the resource resolver
	 * @param variantList      the variant list
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws RepositoryException the repository exception
	 * @throws LoginException      the login exception
	 */
	public void indexProductDataToSolr(ResourceResolver resourceResolver, List<String> variantList)
			throws SolrServerException, IOException, RepositoryException, LoginException {
		ArrayList<String> countriesList = (ArrayList<String>) getAllCountries(resourceResolver);
		HashMap<String, ArrayList<SolrInputDocument>> countryRelatedProductMap = getCountryRelatedProductMap(resourceResolver);

		GenericList genericProductTypes = CommonHelper.getGenericList(resourceResolver, solrConfig.getProductTypesGenericListEndpoint());
		
		for (String variantPath : variantList) {
			JsonObject countryRelatedProductJson = new JsonObject();
			Resource variantResource = resourceResolver.getResource(variantPath);
			if (null != variantResource) {
				Resource variantHpResource = variantResource.getChild(CommonConstants.HP_NODE);
				ValueMap variantValueMap = variantHpResource.adaptTo(ValueMap.class);
				JsonObject varientPropertyJson = getSolrHpJson(variantValueMap, variantHpResource, genericProductTypes,
						resourceResolver);
				countryRelatedProductJson = getSapSolrJson(resourceResolver, variantResource, variantHpResource, varientPropertyJson, countryRelatedProductJson, countriesList,solrConfig);				
				countryRelatedProductMap = setCountryRelatedProductSolrDocs(countryRelatedProductJson, countryRelatedProductMap);
			}
		}
		commitingProductsToSolr(countriesList,countryRelatedProductMap);
	}

	@Override
	public void updateProductDataLocation(ResourceResolver resourceResolver, List<String> variantList) throws LoginException {
		ArrayList<String> countriesList = (ArrayList<String>) getAllCountries(resourceResolver);

		for (String variantPath : variantList) {
			Resource variantResource = resourceResolver.getResource(variantPath);
			if (variantResource == null) continue;
			Resource baseProductResource = variantResource.getParent();
			final SolrInputDocument document = new SolrInputDocument();
			Resource variantHpResource = variantResource.getChild(CommonConstants.HP_NODE);
			if (variantHpResource == null) continue;
			ValueMap variantValueMap = variantHpResource.adaptTo(ValueMap.class);
			if (variantValueMap == null) continue;
			document.addField(CommonConstants.ID, CommonHelper.getPdpProductUrl(variantHpResource));
			String materialNum = variantValueMap.get("materialNumber", variantResource.getName());
			document.addField(CommonConstants.UNIQUE_IDENTIFIER, materialNum);
			if (baseProductResource == null) continue;
			Resource baseProductHP = baseProductResource.getChild(CommonConstants.HP_NODE);
			if (baseProductHP == null) continue;

			document.addField(SolrSearchConstants.HP_NODE_PATH_T,
					new HashMap<String, Object>() {{ put("set", baseProductHP.getPath()); }});

			document.addField(SolrSearchConstants.VARIANT_HP_NODE_PATH.concat(SolrSearchConstants.UNDERSCORE_T),
					new HashMap<String, Object>() {{ put("set", variantHpResource.getPath()); }} );
			document.addField(SolrSearchConstants.DAM_GLOBAL_PATH.concat(SolrSearchConstants.UNDERSCORE_T),
					new HashMap<String, Object>() {{ put("set", getDamGlobalPath(variantHpResource)); }});


			for (String country : countriesList) {
				submitUpdateRequest(country, document);
			}
		}
	}

	@Override
	public void updatePdfDataLocation(ResourceResolver resourceResolver, String newPath, String oldPath) throws LoginException {
		ArrayList<String> countriesList = (ArrayList<String>) getAllCountries(resourceResolver);

		Resource pdfResource = resourceResolver.getResource(newPath);
		if (pdfResource == null) return;


		for (String country : countriesList) {

			LOG.debug("Starting updating for country : {}", country);
			String coreName = solrConfig.getContentPageCollectionName() + "-" + country.toLowerCase();
			String solrUrl = solrConfig.getSolrUrl() + SolrSearchConstants.SLASH_SOLR_SLASH + coreName;
			String pdfCombinedFields = solrConfig.getCombinedFieldsForPDFs();
			String[] pdfCombinedFieldsArr = pdfCombinedFields.split(",");
			List<String> pdfCombinedFieldsSplCharsList = decodeSpecialCharacters();
			try {
				HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).withHttpClient(restClient.getHttpClient()).build();
				SolrDocument previousDocument = solrClient.getById(oldPath);
				LOG.debug("Previous document: {} at {}", previousDocument, oldPath);
				int status = 500;
				if (previousDocument != null) {

					LOG.debug("Found previous document at: {}", oldPath);
					final SolrInputDocument document = new SolrInputDocument();
					for (Entry<String, Object> field : previousDocument) {
						if (!"_version_".equals(field.getKey())) {
							document.addField(field.getKey(), field.getValue());
						}
					}
					document.setField(CommonConstants.UNIQUE_IDENTIFIER, newPath);
					document.setField(CommonConstants.ID, newPath);
					document.setField(SolrSearchConstants.PDF_URL_T, newPath);

					solrClient.deleteById(oldPath);
					solrClient.commit();
					solrClient.add(document);
					UpdateResponse response = solrClient.commit();
					status = response.getStatus();
					LOG.debug("Successfully deleted and created a new document at: {}", newPath);
				} else {
					Asset asset = pdfResource.adaptTo(Asset.class);
					if (asset == null) {
						LOG.error("Can't resolve an asset {}", newPath);
						continue;
					}
					JsonObject countryRelatedPdfJson = createAssetMetadataObject(asset, pdfCombinedFieldsArr, pdfCombinedFieldsSplCharsList);
					JsonElement jsonElement = countryRelatedPdfJson.get(coreName);
					if (jsonElement == null) {
						LOG.error("Json element of {} doesn't exist for solr core: {}", newPath, coreName);
						continue;
					}
					final SolrInputDocument document = createAssetSolrDoc(jsonElement.getAsJsonObject());

					solrClient.add(document);
					UpdateResponse response = solrClient.commit();
					status = response.getStatus();
					LOG.debug("Finished Indexing for country : {}", country);
				}

				if (status < 205) {
					// considered as success
					LOG.debug("Successfully updated index for {}, in the in this solr sore {}", newPath, solrUrl);
				} else {
					LOG.error("Something went wrong while updating index for {} in solr core {}", newPath, solrUrl);
				}
				solrClient.close();
				LOG.debug("Finished Indexing {} for solr core : {}", newPath, solrUrl);
			} catch (SolrServerException | IOException | SAXException | TikaException e) {
				LOG.error("Something went wrong while updating index for {} in colr core {}", newPath, solrUrl);
			}

		}
	}



	private void submitUpdateRequest(String country, SolrInputDocument document) {
		LOG.debug("Starting updating for country : {}", country);
		String coreName = solrConfig.getContentPageCollectionName() + "-" + country.toLowerCase();
		String solrUrl = solrConfig.getSolrUrl() + SolrSearchConstants.SLASH_SOLR_SLASH + coreName;
		String id = (String) document.getFieldValue(CommonConstants.ID);
		try {
			HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).withHttpClient(restClient.getHttpClient()).build();

			solrClient.add(document);
			UpdateResponse response = solrClient.commit();

			int status = response.getStatus();

			if (status < 205) {
				// considered as success
				LOG.debug("Successfully updated index for {}, in the in this solr sore {}", id, solrUrl);
			} else {
				LOG.error("Something went wrong while updating index for {} in solr core {}", id, solrUrl);
			}
			solrClient.close();
			LOG.debug("Finished Indexing {} for solr core : {}", id, solrUrl);
		} catch (SolrServerException | IOException e) {
			LOG.error("Something went wrong while updating index for {} in colr core {}", id, solrUrl);
		}

	}

	/**
	 * Sets the country related product solr docs.
	 *
	 * @param countryRelatedProductJson the country related product json
	 * @param countryRelatedProductMap the country related product map
	 * @return the hash map
	 */
	public HashMap<String, ArrayList<SolrInputDocument>> setCountryRelatedProductSolrDocs(
			JsonObject countryRelatedProductJson,
			HashMap<String, ArrayList<SolrInputDocument>> countryRelatedProductMap) {
		
		// Converting the comma separated combined field to a list.
		List<String> combinedFieldList = new ArrayList<String>();
		String combinedQueryFields = solrConfig.getConcatenatedQueryFields();
		String combinedQueryExcludedFields = solrConfig.getConcatenatedQueryExcludedFields();
		if(StringUtils.isNotEmpty(combinedQueryFields)) {
			String[] combinedFieldArr = combinedQueryFields.split(",");
			if(null != combinedFieldArr) {
				combinedFieldList = Arrays.asList(combinedFieldArr);
			}
		}
		
		// Formation of regex pattern to escape characters
		List<String> regexList = decodeSpecialCharacters();
		
		for (Entry<String, JsonElement> countryRelatedProductJsonEntry : countryRelatedProductJson.entrySet()) {
			if (null != countryRelatedProductJsonEntry.getValue()
					&& countryRelatedProductJsonEntry.getValue().isJsonObject()
					&& countryRelatedProductMap.containsKey(countryRelatedProductJsonEntry.getKey())) {
				ArrayList<SolrInputDocument> countrybasedSolrDocList = countryRelatedProductMap
						.get(countryRelatedProductJsonEntry.getKey());
				countrybasedSolrDocList
						.add(setSolrDocumentForProduct(countryRelatedProductJsonEntry.getValue().getAsJsonObject(), combinedFieldList, combinedQueryExcludedFields, regexList));
				countryRelatedProductMap.put(countryRelatedProductJsonEntry.getKey(), countrybasedSolrDocList);
			}
		}
		return countryRelatedProductMap;
	}

	private List<String> decodeSpecialCharacters() {
		List<String> regexList = new ArrayList<>();
		String splChars = solrConfig.getConcatenatedFieldSplChars();
		String[] chars = splChars.split(",");
		if(null != chars) {
			for(String ch : chars) {
				String decodedKeyword = StringUtils.EMPTY;
				try {
					decodedKeyword = URLDecoder.decode(ch, StandardCharsets.UTF_8.toString());
				} catch (UnsupportedEncodingException e) {
					LOG.error("Exception caught while decoding the special charater.", e);
				}
				regexList.add(decodedKeyword);
			}
		}
		return regexList;
	}
	
	/**
	 * Sets the country related pdf solr docs.
	 *
	 * @param countryRelatedProductJson the country related product json
	 * @param countryRelatedProductMap the country related product map
	 * @return the hash map
	 */
	public HashMap<String, ArrayList<SolrInputDocument>> setCountryRelatedPdfSolrDocs(
			JsonObject countryRelatedProductJson,
			HashMap<String, ArrayList<SolrInputDocument>> countryRelatedProductMap) {
		LOG.debug("!!!!!!!!!!!!!!!!setCountryRelatedPdfSolrDocs start!!!!!!!!!!!!!!!!!");
		for (Entry<String, JsonElement> countryRelatedProductJsonEntry : countryRelatedProductJson.entrySet()) {
			if (null != countryRelatedProductJsonEntry.getValue()
					&& countryRelatedProductJsonEntry.getValue().isJsonObject()
					&& countryRelatedProductMap.containsKey(countryRelatedProductJsonEntry.getKey())) {
				ArrayList<SolrInputDocument> countrybasedSolrDocList = countryRelatedProductMap
						.get(countryRelatedProductJsonEntry.getKey());
				countrybasedSolrDocList
						.add(createAssetSolrDoc(countryRelatedProductJsonEntry.getValue().getAsJsonObject()));
				countryRelatedProductMap.put(countryRelatedProductJsonEntry.getKey(), countrybasedSolrDocList);
			}
		}
		LOG.debug("countryRelatedProductMap of setCountryRelatedPdfSolrDocs method : {}", countryRelatedProductMap.toString());
		return countryRelatedProductMap;
	}
	
	/**
	 * Gets the country related product map.
	 *
	 * @param resourceResolver the resource resolver
	 * @return the country related product map
	 */
	public HashMap<String, ArrayList<SolrInputDocument>> getCountryRelatedProductMap(ResourceResolver resourceResolver){
		HashMap<String, ArrayList<SolrInputDocument>> countryRelatedProductMap = new HashMap<String, ArrayList<SolrInputDocument>>();
		if (null != bdbApiEndpointService
				&& null != resourceResolver.getResource(bdbApiEndpointService.countryDropdownEndpoint())) {
			Resource genericListResource = resourceResolver
					.getResource(bdbApiEndpointService.countryDropdownEndpoint());
			Resource listResource = resourceResolver.getResource(genericListResource.getPath()
					+ CommonConstants.JCR_CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.LIST);
			if (listResource != null && listResource.hasChildren()) {
				Iterator<Resource> items = listResource.listChildren();
				while (items.hasNext()) {
					Resource itemResource = items.next();
					ValueMap valueMap = itemResource.getValueMap();
					ArrayList<SolrInputDocument> solrDocuments = new ArrayList<>();
					String value = CommonHelper.getPropertyValue(valueMap, CommonConstants.VALUE);
					if (StringUtils.isNotEmpty(value.trim())) {
						countryRelatedProductMap.put("bdbio-" + value.toLowerCase(), solrDocuments);
					}
				}
			} else {
				LOG.debug("Either Generic List does not exist or it is empty.");
			}
		}
		LOG.debug("countryRelatedProductMap : {}", countryRelatedProductMap.toString());
		return countryRelatedProductMap;
	}

	/**
	 * Gets the solr hp json.
	 *
	 * @param variantValueMap   the variant value map
	 * @param variantHpResource the variant hp resource
	 * @param resourceResolver  the resource resolver
	 * @return the solr hp json
	 */
	public JsonObject getSolrHpJson(ValueMap variantValueMap, Resource variantHpResource, GenericList productTypesList,
									ResourceResolver resourceResolver) {
		JsonObject varientPropertyJson = new JsonObject();
		Resource baseProductHpResource = variantHpResource.getParent().getParent().getChild(CommonConstants.HP_NODE);
		ValueMap baseProductValueMap = baseProductHpResource.adaptTo(ValueMap.class);
		
		varientPropertyJson.addProperty(CommonConstants.ALTERNATIVE_NAME_KEY,
				SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.ALTERNATIVE_NAME_KEY, String.class)));
		varientPropertyJson.addProperty(CommonConstants.BRAND_KEY,
				SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.BRAND_KEY, String.class)));
		varientPropertyJson.addProperty(CommonConstants.STORAGE_BUFFER_KEY,
				SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.STORAGE_BUFFER_KEY, String.class)));
		varientPropertyJson.addProperty(CommonConstants.VOL_PER_TEST_KEY,
				SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.VOL_PER_TEST_KEY, String.class)));
		varientPropertyJson.addProperty(CommonConstants.BARCODE_SEQUENCE_KEY,
				SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.BARCODE_SEQUENCE_KEY, String.class)));
		varientPropertyJson.addProperty(CommonConstants.SEQUENCE_ID_KEY,
				SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.SEQUENCE_ID_KEY, String.class)));
		varientPropertyJson.addProperty(CommonConstants.PRODUCT_TYPE_KEY,
				SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.PRODUCT_TYPE_KEY, String.class)));
		// Commenting out as regulatoryStatus should be part of SAP Node.
		//varientPropertyJson.addProperty(CommonConstants.REGULATORY_STATUS_KEY,
		//SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.REGULATORY_STATUS_KEY, String.class)));
		varientPropertyJson.addProperty(CommonConstants.MATERIAL_NUMBER,
				SolrUtils.checkNull(variantValueMap.get(CommonConstants.MATERIAL_NUMBER, String.class)));
		varientPropertyJson.addProperty(CommonConstants.BASE_MATERIAL_NUMBER,
				SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.BASE_MATERIAL_NUMBER, String.class)));
		varientPropertyJson.addProperty(CommonConstants.LABEL_DESCRIPTION,
				SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)));
		varientPropertyJson.addProperty(SolrSearchConstants.PDP_TEMPLATE,
				SolrUtils.checkNull(baseProductValueMap.get(SolrSearchConstants.PDP_TEMPLATE, String.class)));
		varientPropertyJson.addProperty(SolrSearchConstants.DOC_PART_IDS,
				SolrUtils.checkNull(baseProductValueMap.get(SolrSearchConstants.DOC_PART_IDS, String.class)));
		varientPropertyJson.addProperty(SolrSearchConstants.MEDIAS,
				SolrUtils.checkNull(baseProductValueMap.get(SolrSearchConstants.MEDIAS, String.class)));
		varientPropertyJson.addProperty(SolrSearchConstants.HP_NODE_PATH, baseProductHpResource.getPath());
		varientPropertyJson.addProperty(SolrSearchConstants.VARIANT_HP_NODE_PATH, variantHpResource.getPath());

		varientPropertyJson.addProperty(SolrSearchConstants.DAM_GLOBAL_PATH, getDamGlobalPath(variantHpResource));

		varientPropertyJson.addProperty(CommonConstants.PRODUCT_URL, CommonHelper.getPdpProductUrl(variantHpResource));
		
		varientPropertyJson.addProperty(CommonConstants.RRID, SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.RRID, String.class)));
		varientPropertyJson.addProperty(CommonConstants.CATEGORY, SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.SUPER_CATEGORY, String.class)));
		varientPropertyJson.addProperty(CommonConstants.POST_TRANSLATIONAL_MODIFICATION, SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.POST_TRANSLATIONAL_MODIFICATION, String.class)));
		varientPropertyJson.addProperty(CommonConstants.RECOMBINANT, SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.RECOMBINANT, String.class)));
		varientPropertyJson.addProperty(CommonConstants.TDS_DOCUMENT_TYPE , SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.TDS_DOCUMENT_TYPE , String.class)));
		buildIsoTypeProperty(baseProductValueMap, varientPropertyJson);
		buildCloneProperties(baseProductValueMap, varientPropertyJson);
		varientPropertyJson.addProperty(CommonConstants.SPECIFICITY, SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.SPECIFICITY, String.class)));
		buildBDFormatProperties(baseProductValueMap, varientPropertyJson);
		buildSpeciesReactivityProperties(baseProductValueMap, varientPropertyJson);
		buildProductAppProperties(baseProductValueMap, varientPropertyJson);
		buildAppDescProperties(baseProductValueMap, varientPropertyJson);
		buildSpeciesDescProperties(baseProductValueMap, varientPropertyJson);
		varientPropertyJson.addProperty(SolrSearchConstants.IS_PRIMARY_VARIANT,
				checkPrimaryVariant(variantValueMap.get(CommonConstants.MATERIAL_NUMBER, String.class),
						variantValueMap.get(CommonConstants.BASE_MATERIAL_NUMBER, String.class)));
		buildVariantProperties(variantValueMap, varientPropertyJson);

		String thumbnailImagePath = CommonHelper.getGlobalThumbnailImage(resourceResolver, variantHpResource,
				externalizerService, bdbApiEndpointService);
		LOG.debug("Thumbnail Image Path of getSolrHpJson method :" + thumbnailImagePath);
		if (null != thumbnailImagePath) {
			varientPropertyJson.addProperty(SolrSearchConstants.THUMBNAIL_IMAGE, thumbnailImagePath);
		}
		
		// Conversion of product type value to user readable value and indexing.
		String productTypeValue = baseProductValueMap.get(CommonConstants.PRODUCT_TYPE_KEY, String.class);
	    String productTypeDisplayValue = StringUtils.EMPTY;
	    if(null != productTypesList) {
	    	productTypeDisplayValue = productTypesList.lookupTitle(productTypeValue);
	    }
	    varientPropertyJson.addProperty(CommonConstants.PRODUCT_TYPE_DISPLAY_VALUE, StringUtils.isNotEmpty(productTypeDisplayValue) ? productTypeDisplayValue : productTypeValue);
	    varientPropertyJson.addProperty(CommonConstants.KEYWORDS, SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.KEYWORDS, String.class)));
	    varientPropertyJson.addProperty(CommonConstants.TDS_DESCRIPTION, SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.TDS_DESCRIPTION, String.class)));
	    varientPropertyJson.addProperty(CommonConstants.OTHER_TDS_DESCRIPTION, SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.OTHER_TDS_DESCRIPTION, String.class)));
		return varientPropertyJson;
	}
	
	private void buildIsoTypeProperty(ValueMap baseProductValueMap, JsonObject varientPropertyJson) {
		StringBuilder isotypeStringBuilder = new StringBuilder();
		if (baseProductValueMap.containsKey(CommonConstants.CLONE)) {
			String clone = baseProductValueMap.get(CommonConstants.CLONE).toString();
			JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();

			for (JsonElement cloneElement : cloneArray) {
				JsonObject cloneJsontObject = cloneElement.getAsJsonObject();
				if (null != cloneJsontObject.get(CommonConstants.HOST_SPECIES_KEY)
						&& StringUtils.isNotEmpty(cloneJsontObject.get(CommonConstants.HOST_SPECIES_KEY).getAsString())) {
					isotypeStringBuilder.append(cloneJsontObject.get(CommonConstants.HOST_SPECIES_KEY).getAsString());
				}
				if (null != cloneJsontObject.get(CommonConstants.HOST_STRAIN_KEY) && StringUtils
						.isNotEmpty(cloneJsontObject.get(CommonConstants.HOST_STRAIN_KEY).getAsString())) {
					isotypeStringBuilder.append(CommonConstants.SPACE
							+ cloneJsontObject.get(CommonConstants.HOST_STRAIN_KEY).getAsString());
				}
				if (null != cloneJsontObject.get(CommonConstants.ISOTYPE_KEY)
						&& StringUtils.isNotEmpty(cloneJsontObject.get(CommonConstants.ISOTYPE_KEY).getAsString())) {
					isotypeStringBuilder.append(CommonConstants.SPACE + cloneJsontObject.get(CommonConstants.ISOTYPE_KEY).getAsString());
				}
			}
			varientPropertyJson.addProperty(CommonConstants.ISOTYPE_KEY,
					isotypeStringBuilder.toString().trim());
		}
		
	}

	private String checkPrimaryVariant(String materialNo, String baseMaterialNo) {
		if(baseMaterialNo.replace("_base", "").equals(materialNo)) {
			return "true";
		}
		return "false";
	}

	/**
	 * Builds the product app properties.
	 *
	 *
	 * @param varientPropertyJson the varient property json
	 */
	private void buildProductAppProperties(ValueMap baseProductValueMap, JsonObject varientPropertyJson) {
		if (baseProductValueMap.containsKey(CommonConstants.PRODUCT_APPLICATION_TEST)) {
			String application = baseProductValueMap.get(CommonConstants.PRODUCT_APPLICATION_TEST).toString();
			JsonArray applicationArray = new JsonParser().parse(application).getAsJsonArray();
			if (applicationArray.size() > 0) {
				JsonObject applicationObject = applicationArray.get(0).getAsJsonObject();
				varientPropertyJson.addProperty(CommonConstants.APPLICATION_NAME,
						SolrUtils.getJsonProperty(applicationObject, CommonConstants.APPLICATION_NAME));
			}
		}
	}
	
	private void buildAppDescProperties(ValueMap baseProductValueMap, JsonObject varientPropertyJson) {
		if (baseProductValueMap.containsKey(CommonConstants.PRODUCT_APPLICATION_TEST)) {
			String application = baseProductValueMap.get(CommonConstants.PRODUCT_APPLICATION_TEST).toString();
			JsonArray applicationArray = new JsonParser().parse(application).getAsJsonArray();
			varientPropertyJson.add(CommonConstants.APPLICATION_DESC,applicationArray);
		}
	}
	
	private void buildSpeciesDescProperties(ValueMap baseProductValueMap, JsonObject varientPropertyJson) {
		if (baseProductValueMap.containsKey(CommonConstants.SPECIES_REACTIVITY_KEY)) {
			String speciesReactivity = baseProductValueMap.get(CommonConstants.SPECIES_REACTIVITY_KEY).toString();
			JsonArray speciesArray = new JsonParser().parse(speciesReactivity).getAsJsonArray();
			varientPropertyJson.add(CommonConstants.SPECIES_DESC,speciesArray);
		}
	}

	/**
	 * Builds the variant properties.
	 *
	 * @param variantValueMap the variant value map
	 * @param varientPropertyJson the varient property json
	 */
	private void buildVariantProperties(ValueMap variantValueMap, JsonObject varientPropertyJson) {

		String size = StringUtils.EMPTY;
		String sizeUom = StringUtils.EMPTY;
		if (null != variantValueMap.get(CommonConstants.SIZE_QTY, String.class)) {
			size = variantValueMap.get(CommonConstants.SIZE_QTY, String.class);
		}
		if (null != variantValueMap.get(CommonConstants.SIZE_UOM, String.class)
				&& StringUtils.isNotEmpty(variantValueMap.get(CommonConstants.SIZE_UOM, String.class))) {
			sizeUom = (StringUtils.isNotEmpty(size) ? CommonConstants.SPACE
					: StringUtils.EMPTY )+ (variantValueMap.get(CommonConstants.SIZE_UOM, String.class));
		}
		varientPropertyJson.addProperty(CommonConstants.SIZE, size + sizeUom);
	}

	/**
	 * Builds the species reactivity properties.
	 *
	 *
	 * @param varientPropertyJson the varient property json
	 */
	private void buildSpeciesReactivityProperties(ValueMap baseProductValueMap, JsonObject varientPropertyJson) {
		if (baseProductValueMap.containsKey(CommonConstants.SPECIES_REACTIVITY_KEY)) {
			String specie = baseProductValueMap.get(CommonConstants.SPECIES_REACTIVITY_KEY).toString();
			JsonArray specieArray = new JsonParser().parse(specie).getAsJsonArray();
			if (specieArray.size() > 0) {
				JsonObject specieObject = specieArray.get(0).getAsJsonObject();
				varientPropertyJson.addProperty(CommonConstants.SPECIES,
						SolrUtils.getJsonProperty(specieObject, CommonConstants.SPECIES));
				varientPropertyJson.addProperty(CommonConstants.REACTIVITY_STATUS,
						SolrUtils.getJsonProperty(specieObject, CommonConstants.REACTIVITY_STATUS));
			}
		}
	}

	/**
	 * Builds the BD format properties.
	 *
	 *
	 * @param varientPropertyJson the varient property json
	 */
	private void buildBDFormatProperties(ValueMap baseProductValueMap, JsonObject varientPropertyJson) {
		String laserColor =StringUtils.EMPTY;
		String laserWavelength =StringUtils.EMPTY;
		if (baseProductValueMap.containsKey(CommonConstants.BD_FORMAT)) {
			String bdFormat = baseProductValueMap.get(CommonConstants.BD_FORMAT).toString();
			JsonArray bdFormatArray = new JsonParser().parse(bdFormat).getAsJsonArray();
			if (bdFormatArray.size() > 0) {
				JsonObject bdFormatObject = bdFormatArray.get(0).getAsJsonObject();
				varientPropertyJson.addProperty(CommonConstants.DYE_NAME,
						SolrUtils.getJsonProperty(bdFormatObject, CommonConstants.DYE_NAME));
				varientPropertyJson.add(CommonConstants.LASER_WAVELENGTH, getLaserWavelength(bdFormatObject));
				varientPropertyJson.addProperty(CommonConstants.LASER_WAVELENGTH + "String",
						SolrUtils.getJsonProperty(bdFormatObject, CommonConstants.LASER_WAVELENGTH));
				varientPropertyJson.addProperty(CommonConstants.LASER_COLOR,
						SolrUtils.getJsonProperty(bdFormatObject, CommonConstants.LASER_COLOR));
				if(null != bdFormatObject.get(CommonConstants.LASER_COLOR) && StringUtils.isNotEmpty(bdFormatObject.get(CommonConstants.LASER_COLOR).getAsString())) {
					laserColor = bdFormatObject.get(CommonConstants.LASER_COLOR).getAsString();
				}
				if (null != bdFormatObject.get(CommonConstants.LASER_WAVELENGTH)
						&& StringUtils.isNotEmpty(bdFormatObject.get(CommonConstants.LASER_WAVELENGTH).getAsString())) {
					laserWavelength = (StringUtils.isEmpty(laserColor) ? StringUtils.EMPTY
							: CommonConstants.SPACE) + (bdFormatObject.get(CommonConstants.LASER_WAVELENGTH).getAsString()
									.concat(CommonConstants.SPACE).concat(CommonConstants.NM));
				}
				String excitationSource = laserColor + laserWavelength;
				excitationSource = excitationSource.replace(CommonConstants.COMMA,
						CommonConstants.SPACE + CommonConstants.NM + CommonConstants.COMMA);
				varientPropertyJson.addProperty(CommonConstants.EXCITATION_SOURCE,
						excitationSource.trim());
			}
		}
	}

	/**
	 * Builds the clone properties.
	 *
	 *
	 * @param varientPropertyJson the varient property json
	 */
	private void buildCloneProperties(ValueMap baseProductValueMap, JsonObject varientPropertyJson) {
		if (baseProductValueMap.containsKey(CommonConstants.CLONE)) {
			String clone = baseProductValueMap.get(CommonConstants.CLONE).toString();
			JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();
			if (cloneArray.size() > 0) {
				JsonObject cloneObject = cloneArray.get(0).getAsJsonObject();
				varientPropertyJson.addProperty(CommonConstants.IMMUNOGEN_KEY,
						SolrUtils.getJsonProperty(cloneObject, CommonConstants.IMMUNOGEN_KEY));
				varientPropertyJson.addProperty(CommonConstants.TDS_CLONE_NAME,
						SolrUtils.getJsonProperty(cloneObject, CommonConstants.TDS_CLONE_NAME));
				varientPropertyJson.addProperty(CommonConstants.HOST_SPECIES_KEY,
						SolrUtils.getJsonProperty(cloneObject, CommonConstants.HOST_SPECIES_KEY));
				varientPropertyJson.addProperty(CommonConstants.ENTREZ_GENE_ID_KEY,
						SolrUtils.getJsonProperty(cloneObject, CommonConstants.ENTREZ_GENE_ID_KEY));
			}
		}
	}

	/**
	 * Gets the laser wavelength.
	 *
	 * @param bdFormatObject the bd format object
	 * @return the laser wavelength
	 */
	public JsonArray getLaserWavelength(JsonObject bdFormatObject) {
		JsonArray laserWaveLengthArray = new JsonArray();
		if (bdFormatObject.has(CommonConstants.LASER_WAVELENGTH)) {
			try {
				String laserWavelengthString = CommonHelper
						.removeExtraCommas(bdFormatObject.get(CommonConstants.LASER_WAVELENGTH).getAsString().trim());
				if (!laserWavelengthString.isEmpty() && !laserWavelengthString.contains(CommonConstants.COMMA)
						&& !laserWavelengthString.contains(CommonConstants.HYPHEN)) {
					laserWaveLengthArray.add(Integer.parseInt(laserWavelengthString));
				} else if (!laserWavelengthString.isEmpty() && laserWavelengthString.contains(CommonConstants.COMMA)
						&& !laserWavelengthString.contains(CommonConstants.HYPHEN)) {
					String[] laserArray = laserWavelengthString.split(CommonConstants.COMMA);
					for (String laser : laserArray) {
						laserWaveLengthArray.add(Integer.parseInt(laser.trim()));
					}
				} else if (!laserWavelengthString.isEmpty() && !laserWavelengthString.contains(CommonConstants.COMMA)
						&& laserWavelengthString.contains(CommonConstants.HYPHEN)) {
					String[] laserArray = laserWavelengthString.split(CommonConstants.HYPHEN);
					int start = Integer.parseInt(laserArray[0].trim());
					int end = Integer.parseInt(laserArray[1].trim());
					while (start <= end) {
						laserWaveLengthArray.add(start);
						start++;
					}
				}
			} catch (NumberFormatException e) {
				LOG.error("Exception during parsing laser wavelength to int");
			}
		}
		return laserWaveLengthArray;
	}

	/**
	 * Gets the dam global path.
	 *
	 * @param variantHpResource the variant hp resource
	 * @return the dam global path
	 */
	public String getDamGlobalPath(Resource variantHpResource) {
		return variantHpResource.getParent().getParent().getPath()
				.replace(CommonConstants.VAR_COMMERCE + CommonConstants.SINGLE_SLASH + CommonConstants.PRODUCTS,
						CommonConstants.CONTENT_DAM)
				.replace(CommonConstants.PRODUCTS, CommonConstants.PRODUCTS + CommonConstants.SINGLE_SLASH + "global");
	}
	
	/**
	 * Gets the dc:description for ON_DEMAND TDS.
	 *
	 * @param valueMap hpProperties
	 * @return the dc:description
	 */
	private String getDcDescription(ValueMap hpProperties) {
		String webName = SolrUtils.checkNull(hpProperties.get(CommonConstants.WEB_NAME, String.class));
		String regulatoryStatus = SolrUtils.checkNull(hpProperties.get(CommonConstants.REGULATORY_STATUS_KEY, String.class));
		String materialNumber = SolrUtils.checkNull(hpProperties.get(CommonConstants.BASE_MATERIAL_NUMBER, String.class)).replace("_base", "");
		String TDS = "TDS";
		StringBuilder sb = new StringBuilder();
		sb.append(webName).append(CommonConstants.SPACE);
		sb.append(regulatoryStatus).append(CommonConstants.SPACE);
		sb.append(CommonConstants.HYPHEN).append(CommonConstants.SPACE);
		sb.append(TDS).append(CommonConstants.SPACE);
		sb.append(materialNumber);
		String dcDescription = sb.toString();
		LOG.debug(dcDescription);
        return dcDescription;
	}

	/**
	 * Gets the sap solr json.
	 * @param resourceResolver 
	 *
	 * @param variantResource     the variant resource
	 * @param variantHpResource     the variant Hp resource
	 * @param varientPropertyJson the varient property json
	 * @param countryRelatedProductJson the country related product json
	 * @param countriesList the countries list
	 * @param solrConfig 
	 * @return the sap solr json
	 * @throws RepositoryException the repository exception
	 */
	public JsonObject getSapSolrJson(ResourceResolver resourceResolver, Resource variantResource, Resource variantHpResource,  JsonObject varientPropertyJson,
									 JsonObject countryRelatedProductJson, ArrayList<String> countriesList, BDBSearchEndpointService solrConfig) throws RepositoryException {
		Resource sapResource = variantResource.getChild(CommonConstants.SAP_CC_NODENAME);
		ValueMap sapProps = sapResource.adaptTo(ValueMap.class);
		// Getting the base product hp node resource and valueMap
		Resource baseProductHpResource = variantHpResource.getParent().getParent().getChild(CommonConstants.HP_NODE);
		ValueMap baseProductValueMap = baseProductHpResource.adaptTo(ValueMap.class);

		if (sapProps.containsKey(SolrSearchConstants.MPG_CATEGORY)) {
			String mpgCategory = sapProps.get(SolrSearchConstants.MPG_CATEGORY, String.class);
			JsonArray mpgCategoryJsonArray = new JsonParser().parse(mpgCategory).getAsJsonArray();
			String[] mpgCategoryArray = new String[mpgCategoryJsonArray.size()];
			int j = 0;
			for (JsonElement mpgCategoryElement : mpgCategoryJsonArray) {
				mpgCategoryArray[j] = mpgCategoryElement.getAsString();
				j++;
			}
			varientPropertyJson.add(SolrSearchConstants.MPG_CATEGORY, mpgCategoryJsonArray);
		}

		if (sapProps.containsKey(SolrSearchConstants.CLASSIFICATION_CATEGORY)) {
			String classificationCategory = sapProps.get(SolrSearchConstants.CLASSIFICATION_CATEGORY, String.class);
			JsonArray classificationCategoryJsonArray = new JsonParser().parse(classificationCategory).getAsJsonArray();
			String[] classificationCategoryArray = new String[classificationCategoryJsonArray.size()];
			int k = 0;
			for (JsonElement classificationCategoryElement : classificationCategoryJsonArray) {
				classificationCategoryArray[k] = classificationCategoryElement.getAsString();
				k++;
			}
			varientPropertyJson.add(SolrSearchConstants.CLASSIFICATION_CATEGORY, classificationCategoryJsonArray);
		}
		if (sapProps.containsKey(CommonConstants.PHANTOM_CHILDS)) {
			String phantomChilds = sapProps.get(CommonConstants.PHANTOM_CHILDS, String.class);
			JsonArray phantomChildJsonArray = new JsonParser().parse(phantomChilds).getAsJsonArray();
			String[] phantomChildArray = new String[phantomChildJsonArray.size()];
			int j = 0;
			for (JsonElement phantomElement : phantomChildJsonArray) {
				phantomChildArray[j] = phantomElement.getAsString();
				j++;
			}
			varientPropertyJson.add(CommonConstants.PHANTOM_CHILDS, phantomChildJsonArray);
		}
		if (sapProps.containsKey(CommonConstants.PHANTOM_PARENTS)) {
			String phantomParents = sapProps.get(CommonConstants.PHANTOM_PARENTS, String.class);
			JsonArray phantomParentJsonArray = new JsonParser().parse(phantomParents).getAsJsonArray();
			String[] phantomParentArray = new String[phantomParentJsonArray.size()];
			int j = 0;
			for (JsonElement phantomParentElement : phantomParentJsonArray) {
				phantomParentArray[j] = phantomParentElement.getAsString();
				j++;
			}
			varientPropertyJson.add(CommonConstants.PHANTOM_PARENTS, phantomParentJsonArray);
		}
		Node regionDetailsNode = variantResource.getChild(CommonConstants.SAP_CC_NODENAME)
				.getChild(CommonConstants.REGION_DETAILS_NODE_NAME).adaptTo(Node.class);
		PropertyIterator propertyIterator = regionDetailsNode.getProperties();
		varientPropertyJson.addProperty("isAvailable", "false");
		JsonObject commonVarientPropertyJson = new JsonParser().parse(varientPropertyJson.toString()).getAsJsonObject();
		for(String country : countriesList) {
			countryRelatedProductJson.add("bdbio-" + country.toLowerCase(),commonVarientPropertyJson);
		}
		if (propertyIterator.getSize() > 1) {
			String[] regionStringArray = new String[(int) propertyIterator.getSize() - 1];
			int i = 0;
			JsonArray regionJsonArray = new JsonArray();

			while (propertyIterator.hasNext()) {
				Property property = propertyIterator.nextProperty();
				if (!property.getName().equals(CommonConstants.JCR_PRIMARY_TYPE)
						&& !property.getName().equals(JcrConstants.JCR_CREATED)
						&& !property.getName().equals(JcrConstants.JCR_CREATED_BY)
						&& !property.getName().startsWith(SolrSearchConstants.CONSTANT_CQ)
						&& !property.getName().equals(NameConstants.PN_PAGE_LAST_REPLICATED_BY)
						&& !property.getName().equals(JcrConstants.JCR_MIXINTYPES)) {
					regionStringArray[i] = property.getName();
					regionJsonArray.add(property.getName());
					JsonObject countryDetails = new JsonParser().parse(property.getValue().getString())
							.getAsJsonObject();
					if(countryDetails.has(CommonConstants.IS_PHANTOM)) {
						String isPhantom = countryDetails.get(CommonConstants.IS_PHANTOM).getAsString() ;
						varientPropertyJson.addProperty(CommonConstants.IS_PHANTOM, isPhantom);
					}
					//Handling indexing for regulatory status : under SAP region node
					if(countryDetails.has("regulatoryStatus")) {
						String regulatoryStatus = countryDetails.get("regulatoryStatus").getAsString();
						varientPropertyJson.addProperty("regulatoryStatus",regulatoryStatus);
						if(varientPropertyJson.has("regulatoryStatus_display_value")) {
							varientPropertyJson.remove("regulatoryStatus_display_value");
						}
						 String ivdRegions = solrConfig.getIvdTranslationRegions();
						 if(StringUtils.isNotBlank(ivdRegions)) {
							 String[] ivdRegion = ivdRegions.split(",");
							for (String countryValue : ivdRegion) {
								if (StringUtils.isNotBlank(regulatoryStatus)
										&& property.getName().equalsIgnoreCase(countryValue)
										&& regulatoryStatus.equals("IVD")) {
									regulatoryStatus = CommonHelper.getTranslatedRegulatoryStatus(countryValue.toUpperCase() +"_IVD",
											resourceResolver,solrConfig);
									varientPropertyJson.addProperty("regulatoryStatus_display_value", regulatoryStatus);
								}
							}
						 }
					}else {
						varientPropertyJson.addProperty("regulatoryStatus",StringUtils.EMPTY);
					}
					if(countryDetails.has("toxicity") && property.getName().equalsIgnoreCase("jp")) {
						varientPropertyJson.addProperty("lawsAndRegulations",countryDetails.get("toxicity").getAsString());
					}else {
						varientPropertyJson.addProperty("lawsAndRegulations",StringUtils.EMPTY);
					}
					if (countryDetails.has("derivedProductStatus") && countryDetails.has("validityStartDate")) {
						String derivedProductStatus = countryDetails.get("derivedProductStatus").getAsString();
						if (derivedProductStatus.equals("DISPLAYONLY") || derivedProductStatus.equals("PURCHASEABLE")) {
							varientPropertyJson.addProperty("derivedProductStatus", derivedProductStatus);
							String getValidityDate = countryDetails.get("validityStartDate").getAsString();
							varientPropertyJson.addProperty("validityStartDate", getValidityDate);
							varientPropertyJson.addProperty("isAvailable", "true");
							// Fetching the releaseDate when derivedProductSatus is DISPLAYONLY and PURCHASEABLE
							// In the if condition getting the releaseDate for japan and the EMEA region from the getReleaseDate method by passing getValidityDate as a parameter
							// In the else condition getting the releaseDate from afSDate of Hp node for other countries 
							if (property.getName().equalsIgnoreCase("jp") || CommonConstants.EMEA_COUNTRIES_LIST.contains(property.getName().toLowerCase())) {
								varientPropertyJson.addProperty(CommonConstants.RELEASE_DATE, getReleaseDate(getValidityDate));
							}
							else {
								varientPropertyJson.addProperty(CommonConstants.RELEASE_DATE,
										getReleaseDate(SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.AFS_DATE, String.class))));
							}
							JsonObject countrySpecificVarientPropertyJson = new JsonParser()
									.parse(varientPropertyJson.toString()).getAsJsonObject();
							countryRelatedProductJson.add("bdbio-" + property.getName().toLowerCase(),
									countrySpecificVarientPropertyJson);
						} else {
							varientPropertyJson.addProperty("derivedProductStatus", derivedProductStatus);
							String getValidityDate = countryDetails.get("validityStartDate").getAsString();
							varientPropertyJson.addProperty("validityStartDate", getValidityDate);
							varientPropertyJson.addProperty("isAvailable", "false");
							// Fetching the releaseDate when derivedProductSatus is other than DISPLAYONLY and PURCHASEABLE
							// In the if condition getting the releaseDate for japan and the EMEA region from the getReleaseDate method by passing getValidityDate as a parameter
							// In the else condition getting the releaseDate from afSDate of Hp node for other countries 
							if (property.getName().equalsIgnoreCase("jp") || CommonConstants.EMEA_COUNTRIES_LIST.contains(property.getName().toLowerCase())) {
								varientPropertyJson.addProperty(CommonConstants.RELEASE_DATE, getReleaseDate(getValidityDate));
							}
							else {
								varientPropertyJson.addProperty(CommonConstants.RELEASE_DATE,
										getReleaseDate(SolrUtils.checkNull(baseProductValueMap.get(CommonConstants.AFS_DATE, String.class))));
							}
							JsonObject countrySpecificVarientPropertyJson = new JsonParser()
									.parse(varientPropertyJson.toString()).getAsJsonObject();
							countryRelatedProductJson.add("bdbio-" + property.getName().toLowerCase(),
									countrySpecificVarientPropertyJson);
						}
					}
					i++;
				}
			}
		}
		return countryRelatedProductJson;
	}

	/**
	 * Creates product release date from AfSDate.
	 *
	 * @param date           the AfSDate
	 * @return finalDate     the release date
	 */
	public String getReleaseDate(String date) {
		String finalDate = StringUtils.EMPTY;
		try {
			if (!StringUtils.isEmpty(date)) {
				if (date.contains("T")) {
					date = date.substring(0, date.indexOf("T"));
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date changeDateFormat = (Date)formatter.parse(date);
					SimpleDateFormat newFormat = new SimpleDateFormat("MMM yyyy");
					finalDate = newFormat.format(changeDateFormat);
				} else {
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					Date changeDateFormat = (Date)formatter.parse(date);
					SimpleDateFormat newFormat = new SimpleDateFormat("MMM yyyy");
					finalDate = newFormat.format(changeDateFormat);
				}
			}
			else {
				return finalDate;
			}
			
		}
		catch(ParseException e) {
			LOG.error("Error occurred in getReleaseDate", e.getMessage());
		}
		return finalDate;
	}

	/**
	 * Sets the solr document for hp.
	 *
	 * @param variantPropertyJson the varient property json
	 * @return the solr input document
	 */
	private SolrInputDocument setSolrDocumentForProduct(JsonObject variantPropertyJson, List<String> combinedFieldList, String combinedQueryExcludedFields, List<String> regexToEscape ) {
		final SolrInputDocument document = new SolrInputDocument();
		Map<String, String> combinedMap = new HashMap<>();

		document.addField(CommonConstants.UNIQUE_IDENTIFIER, variantPropertyJson.get(CommonConstants.MATERIAL_NUMBER).getAsString());
		document.addField(CommonConstants.ID, variantPropertyJson.get(CommonConstants.PRODUCT_URL).getAsString());
		document.addField(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_T,
				SolrSearchConstants.PDP_PRODUCT);
		document.addField(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S,
				SolrSearchConstants.PRODUCTS);
		if (variantPropertyJson.has(CommonConstants.LABEL_DESCRIPTION)) {
			document.addField(CommonConstants.LABEL_DESCRIPTION.concat(SolrSearchConstants.UNDERSCORE_T),
					variantPropertyJson.get(CommonConstants.LABEL_DESCRIPTION).getAsString());
			if(combinedFieldList.contains(CommonConstants.LABEL_DESCRIPTION.concat(SolrSearchConstants.UNDERSCORE_T))) {
				combinedMap.put(CommonConstants.LABEL_DESCRIPTION.concat(SolrSearchConstants.UNDERSCORE_T), variantPropertyJson.get(CommonConstants.LABEL_DESCRIPTION).getAsString().trim());
			}
			document.addField(CommonConstants.NAME.concat(SolrSearchConstants.UNDERSCORE_T),
					variantPropertyJson.get(CommonConstants.LABEL_DESCRIPTION).getAsString());
			if(combinedFieldList.contains(CommonConstants.NAME.concat(SolrSearchConstants.UNDERSCORE_T))) {
				combinedMap.put(CommonConstants.NAME.concat(SolrSearchConstants.UNDERSCORE_T), variantPropertyJson.get(CommonConstants.LABEL_DESCRIPTION).getAsString().trim());
			}
		}
		setDocumentField(variantPropertyJson, document, CommonConstants.VOL_PER_TEST_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.BRAND_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.ALTERNATIVE_NAME_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.STORAGE_BUFFER_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.ENTREZ_GENE_ID_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.BARCODE_SEQUENCE_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.SEQUENCE_ID_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.PRODUCT_TYPE_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.REGULATORY_STATUS_KEY,
				SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.IMMUNOGEN_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.ISOTYPE_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.TDS_CLONE_NAME, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.HOST_SPECIES_KEY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.SPECIES, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.REACTIVITY_STATUS, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		
		setDocumentField(variantPropertyJson, document, CommonConstants.TDS_DOCUMENT_TYPE, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		
		//setDocumentField(variantPropertyJson, document, CommonConstants.APPLICATION_NAME, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.APPLICATION_DESC, SolrSearchConstants.APPLICATION_ARRAY, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.DYE_NAME, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.LASER_WAVLENGTH, SolrSearchConstants.INT_ARRAY, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.LASER_WAVLENGTH + "String",
				SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.HP_NODE_PATH, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.VARIANT_HP_NODE_PATH, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.DAM_GLOBAL_PATH, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.DOC_PART_IDS, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.MEDIAS, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.LASER_COLOR, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.SPECIFICITY, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.SIZE, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.BASE_MATERIAL_NUMBER, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.MATERIAL_NUMBER, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.THUMBNAIL_IMAGE, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.PDP_TEMPLATE, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.IS_PRIMARY_VARIANT, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, "derivedProductStatus", SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, "isAvailable", SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.IS_PHANTOM, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.PHANTOM_CHILDS, SolrSearchConstants.TEXT_ARRAY, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.PHANTOM_PARENTS, SolrSearchConstants.TEXT_ARRAY, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, "validityStartDate", SolrSearchConstants.DATE, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, "releaseDate", SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.MPG_CATEGORY,
				SolrSearchConstants.TEXT_ARRAY, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.CLASSIFICATION_CATEGORY,
				SolrSearchConstants.TEXT_ARRAY, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.RRID, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.SPECIES, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.APPLICATION_NAME, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.DYE_NAME, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.TDS_CLONE_NAME, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.ISOTYPE_KEY, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.HOST_SPECIES_KEY, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.REGULATORY_STATUS_KEY, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.BRAND_KEY, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.RRID, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.SIZE, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.SPECIFICITY, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.BARCODE_SEQUENCE_KEY, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.CATEGORY, SolrSearchConstants.STRING_ARRAY, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.PRODUCT_TYPE_DISPLAY_VALUE, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.SPECIES_REACTIVITY_KEY, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.EXCITATION_SOURCE, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.SPECIES_DESC, SolrSearchConstants.SPECIES_ARRAY, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.MEDIAS, SolrSearchConstants.MEDIA_ARRAY, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, SolrSearchConstants.DOC_PART_IDS, SolrSearchConstants.DOCPART_IDS_ARRAY, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.KEYWORDS, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, "lawsAndRegulations", SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, "regulatoryStatus_display_value", SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.POST_TRANSLATIONAL_MODIFICATION, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.RECOMBINANT, SolrSearchConstants.STRING, combinedFieldList, combinedMap);
		
		setDocumentField(variantPropertyJson, document, CommonConstants.TDS_DESCRIPTION, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		setDocumentField(variantPropertyJson, document, CommonConstants.OTHER_TDS_DESCRIPTION, SolrSearchConstants.TEXT, combinedFieldList, combinedMap);
		
		// Logic to build Concatenated Query Fields by replacing special characters to space.
		buildConcatenatedQueryFields(
				variantPropertyJson,
				combinedQueryExcludedFields,
				regexToEscape,
				document,
				combinedMap);
		
		return document;
	}
	
	public void buildConcatenatedQueryFields(JsonObject variantPropertyJson, String combinedQueryExcludedFields,
			List<String> regexToEscape, final SolrInputDocument document, Map<String, String> combinedMap) {
		List<String> concatenatedFields = new ArrayList<>();
		String[] semicolon = new String[] {";"};
		String[] plus = new String[] {"+"};
		String[] regexArray = regexToEscape.toArray(new String[regexToEscape.size()]);
		if (null != combinedMap) {
			for (Map.Entry<String, String> entry : combinedMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (StringUtils.isNotEmpty(combinedQueryExcludedFields) && combinedQueryExcludedFields.contains(key)) {
					String[] propertyArr = key.split("_");
					String exclusionValue = (null != propertyArr && null != variantPropertyJson.get(propertyArr[0]))
							? variantPropertyJson.get(propertyArr[0]).getAsString()
							: StringUtils.EMPTY;
					concatenatedFields.add(exclusionValue);
				} else {
					if (null != regexArray && Arrays.stream(regexArray).anyMatch(value::contains)) {
						String replacedValue = replaceCombinedFieldCharsWithSpace(value, regexArray, " ") + " "
								+ replaceCombinedFieldCharsWithSpace(value, regexArray, "");
						if(Arrays.stream(semicolon).anyMatch(value::contains)) {
							String semicolonEscapedStr = replaceCombinedFieldCharsWithSpace(value, semicolon, " ");
							if (StringUtils.isNotEmpty(semicolonEscapedStr) && StringUtils.isNotEmpty(replacedValue)
									&& !replacedValue.trim().contains(semicolonEscapedStr.trim())) {
								concatenatedFields.add(replacedValue + " " + semicolonEscapedStr);
							}
							else {
								concatenatedFields.add(replacedValue);
							}
						}
						else {
							concatenatedFields.add(replacedValue);
						}
					}
					else {
						concatenatedFields.add(value);
					}
				}
				// Handling plp search for skus having more than one special chars, particularly '+'.
				if(Arrays.stream(plus).anyMatch(value::contains)){
					String plusEscapedStr = replaceCombinedFieldCharsWithSpace(value, plus, " ");
					if(!concatenatedFields.contains(plusEscapedStr)) concatenatedFields.add(plusEscapedStr);
				}
			}
			String[] concatenatedQueryField = concatenatedFields.toArray(new String[concatenatedFields.size()]);
			if (null != concatenatedQueryField) {
				document.addField(CommonConstants.CONCATENATED_QUERY_FIELD.concat(SolrSearchConstants.UNDERSCORE_T),
						concatenatedQueryField);
			}
		}
	}
	
	

//	public void buildConcatenatedQueryFields(JsonObject variantPropertyJson, String combinedQueryExcludedFields,
//			List<String> regexToEscape, final SolrInputDocument document, Map<String, String> combinedMap) {
//		List<String> concatenatedFields = new ArrayList<>();
//		String [] regexArray = regexToEscape.toArray(new String[regexToEscape.size()]);
//		if(null != combinedMap) {
//			for (Map.Entry<String, String> entry : combinedMap.entrySet()) {
//				String key = entry.getKey();
//			    String value = entry.getValue();
//			    if(StringUtils.isNotEmpty(combinedQueryExcludedFields) && combinedQueryExcludedFields.contains(key)) {
//			    	String[] propertyArr = key.split("_");
//					String exclusionValue = (null != propertyArr && null != variantPropertyJson.get(propertyArr[0]))
//							? variantPropertyJson.get(propertyArr[0]).getAsString()
//							: StringUtils.EMPTY;
//					concatenatedFields.add(exclusionValue);
//			    }
//			    else {
//			    	if(null != regexArray && Arrays.stream(regexArray).anyMatch(value::contains)) {
//						String replacedValue = replaceCombinedFieldCharsWithSpace(value, regexArray, " "); 
//						String tempCombinedField = StringUtils.EMPTY;
//						String[] valuesDelimited = value.split(" ");
//						if(null != valuesDelimited) {
//							for(String val : valuesDelimited) {
//
//								// Logic to escape all spl characters with Empty
//								String replacedTempVal = replaceCombinedFieldCharsWithSpace(val, regexArray, "");
//								if(!replacedValue.contains(replacedTempVal)){
//									tempCombinedField = tempCombinedField + " " + replacedTempVal;
//								}
//
//								// Logic to escape only semicolon.
//								String replacedSemicolonTempVal = val.replace(";", "");
//								if (!val.equalsIgnoreCase(replacedSemicolonTempVal)
//										&& !replacedValue.contains(replacedSemicolonTempVal)
//										&& !tempCombinedField.contains(replacedSemicolonTempVal)) {
//									tempCombinedField = tempCombinedField + " " + replacedSemicolonTempVal;
//								}
//							}
//							concatenatedFields.add(replacedValue + tempCombinedField);
//						}
//						else {
//							concatenatedFields.add(replacedValue + tempCombinedField);
//						}
//					}
//					else {
//						concatenatedFields.add(value);
//					}
//			    }
//			}
//			String[] concatenatedQueryField = concatenatedFields.toArray(new String[concatenatedFields.size()]);
//			if(null != concatenatedQueryField) {
//				document.addField(CommonConstants.CONCATENATED_QUERY_FIELD.concat(SolrSearchConstants.UNDERSCORE_T), concatenatedQueryField);
//			}
//		}
//	}
	
	/**
	 * Replaces the regex values to space.
	 * 
	 * @param value
	 * @param regex
	 * @return modified string
	 */
	public String replaceCombinedFieldCharsWithSpace(String value, String[] regex, String delimiter) {
		if(StringUtils.isNotEmpty(value) && null != regex) {
			for(String ch : regex) {
				value = value.replace(ch, delimiter);
			}
		}
		return value; 
	}

	/**
	 * CapitalizeIgnoreGreek method is use to capitalize only the alphabetical characters at the zeroth index.
	 * It ignores the greek letter at the zeroth index
	 */
	public static String capitalizeIgnoreGreek(String input) {
		String input1 = StringUtils.uncapitalize(input);
		if (StringUtils.isEmpty(input1) || Character.UnicodeBlock.of(input1.charAt(0)).equals(Character.UnicodeBlock.GREEK)) {
			return input1;
		    }
			else {
				return input1.substring(0,1).toUpperCase() + input1.substring(1);
			}
		}
	
	/**
	 * Sets the document field.
	 *
	 * @param variantPropertyJson the variant property json
	 * @param document            the document
	 * @param property            the property
	 * @param type                the type
	 */
	public void setDocumentField(JsonObject variantPropertyJson, SolrInputDocument document, String property,
								 String type, List<String> combinedFields, Map<String, String> CombinedFieldsMap) {
		if (variantPropertyJson.has(property)) {
			switch (type) {
				case SolrSearchConstants.TEXT:
					document.addField(property.concat(SolrSearchConstants.UNDERSCORE_T),
							!variantPropertyJson.get(property).isJsonNull() ? variantPropertyJson.get(property).getAsString().trim() : StringUtils.EMPTY);
					
					if(combinedFields.contains(property.concat(SolrSearchConstants.UNDERSCORE_T))) {
						CombinedFieldsMap.put(property.concat(SolrSearchConstants.UNDERSCORE_T),
								!variantPropertyJson.get(property).isJsonNull() ? variantPropertyJson.get(property).getAsString().trim() : StringUtils.EMPTY);
					}
					break;
				case SolrSearchConstants.STRING:
					document.addField(property.concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S),
							!variantPropertyJson.get(property).isJsonNull() ? capitalizeIgnoreGreek(StringUtils.capitalize(variantPropertyJson.get(property).getAsString().trim())) : StringUtils.EMPTY);
					break;
				case SolrSearchConstants.DATE:
					document.addField(property.concat(SolrSearchConstants.UNDERSCORE_DT),
							!variantPropertyJson.get(property).isJsonNull() ? variantPropertyJson.get(property).getAsString() : StringUtils.EMPTY);
					break;
				case SolrSearchConstants.TEXT_ARRAY:
					JsonArray textJsonArray = variantPropertyJson.get(property).getAsJsonArray();
					String[] textArray = new String[textJsonArray.size()];
					int j = 0;
					for (JsonElement element : textJsonArray) {
						textArray[j] = element.getAsString();
						j++;
					}
					Arrays.sort(textArray,String.CASE_INSENSITIVE_ORDER);
					document.addField(property.concat(SolrSearchConstants.UNDERSCORE_TXT), textArray);
					break;
				case SolrSearchConstants.SPECIES_ARRAY: 
					JsonArray targetSpecies = variantPropertyJson.get(property).getAsJsonArray();
					List<String> speciesList = new ArrayList<>();
					for (JsonElement element : targetSpecies) {
						JsonObject elementJsonObject = element.getAsJsonObject();
						if (null != elementJsonObject.get(CommonConstants.REACTIVITY_STATUS)
								&& StringUtils
										.isNotEmpty(elementJsonObject.get(CommonConstants.REACTIVITY_STATUS).getAsString())
								&& null != elementJsonObject.get(CommonConstants.SPECIES_DESC) && StringUtils
										.isNotEmpty(elementJsonObject.get(CommonConstants.SPECIES_DESC).getAsString())) {
							String reactivityStatus = elementJsonObject.get(CommonConstants.REACTIVITY_STATUS)
									.getAsString();
							String speciesDesc = elementJsonObject.get(CommonConstants.SPECIES_DESC).getAsString();
							String speciesStatus = solrConfig.speciesReactivityStatus();
							String[] speciesArray = speciesStatus.split(CommonConstants.COMMA);
							if (null != speciesArray && !Arrays.toString(speciesArray).contains(reactivityStatus)) {
								speciesList = getListFromString(speciesDesc, speciesList);
							}
						}
						if(null != elementJsonObject.get(CommonConstants.SPECIES_DESC) && StringUtils
										.isNotEmpty(elementJsonObject.get(CommonConstants.SPECIES_DESC).getAsString())) {
							//String reactivityStatus = elementJsonObject.get(CommonConstants.REACTIVITY_STATUS)
							//		.getAsString();
							String speciesDesc = elementJsonObject.get(CommonConstants.SPECIES_DESC).getAsString();
							String speciesStatusDesc = elementJsonObject.get(CommonConstants.REACTIVITY_STATUS_DES).getAsString();
							String speciesStatus = solrConfig.speciesReactivityStatus();
							String[] speciesArray = speciesStatus.split(CommonConstants.COMMA);
							if (null != speciesStatusDesc && !speciesStatusDesc.contains("Lack of Reactivity Confirmed in Development")) {
								if (null != speciesArray && !Arrays.toString(speciesArray).contains(speciesDesc)) {
									speciesList = getListFromString(speciesDesc, speciesList);
								}
							}
						}
					}
					if(!speciesList.isEmpty()) {
						Collections.sort(speciesList);
					}
					document.addField(CommonConstants.SPECIES_REACTIVITY_KEY.concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_SS), !speciesList.isEmpty()?speciesList:StringUtils.EMPTY);
					document.addField(CommonConstants.SPECIES_REACTIVITY_KEY.concat(SolrSearchConstants.UNDERSCORE_T), !speciesList.isEmpty()?String.join(" ", speciesList):StringUtils.EMPTY);
					
					if(combinedFields.contains(CommonConstants.SPECIES_REACTIVITY_KEY.concat(SolrSearchConstants.UNDERSCORE_T))) {
						CombinedFieldsMap.put(CommonConstants.SPECIES_REACTIVITY_KEY.concat(SolrSearchConstants.UNDERSCORE_T), !speciesList.isEmpty()?String.join(" ", speciesList):StringUtils.EMPTY);
					}
					break;		
				case SolrSearchConstants.APPLICATION_ARRAY:
					JsonArray applicationJsonArray = variantPropertyJson.get(property).getAsJsonArray();
					List<String> apps = new ArrayList<>();
					for (JsonElement element : applicationJsonArray) {
						JsonObject elementJsonObject = element.getAsJsonObject();
						if (null != elementJsonObject.get(CommonConstants.APPLICATION_STATUS)
								&& StringUtils
										.isNotEmpty(elementJsonObject.get(CommonConstants.APPLICATION_STATUS).getAsString())
								&& null != elementJsonObject.get(CommonConstants.APPLICATION_DESC)
								&& StringUtils.isNotEmpty(
										elementJsonObject.get(CommonConstants.APPLICATION_DESC).getAsString())) {
							String applicationStatus = elementJsonObject.get(CommonConstants.APPLICATION_STATUS)
									.getAsString();
						String applicationDesc = element.getAsJsonObject().get(CommonConstants.APPLICATION_DESC).getAsString();
							String applicationName = elementJsonObject.get(CommonConstants.APPLICATION_NAME)
									.getAsString();
							String applicationStatusConf = solrConfig.applicationStatus();
							String[] applicationArray = applicationStatusConf.split(CommonConstants.COMMA);
							/* Application with IC/FCM status are being processed as FCM since the values in HP source system cannot be changed in time for the L1 galaxy release.
							 * Changes are hard-coded to help achieve it.
							 * This will have to be removed after the values in HP are corrected*/
							if (null != applicationArray && !Arrays.toString(applicationArray).contains(applicationStatus)) {
								if(StringUtils.isNotBlank(applicationName) && !isIcFcm(applicationName) && !apps.contains(CommonConstants.FLOW_CYTOMETRY)) {
									apps = getListFromString(applicationDesc, apps);
								}else {
									apps = getListFromStringForFcm(applicationDesc, apps,applicationName);
								}
							}
						}
					}		
					if(!apps.isEmpty()) {
						Collections.sort(apps);
					}
					document.addField(CommonConstants.APPLICATION_NAME.concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_SS), !apps.isEmpty()?apps:StringUtils.EMPTY);
					document.addField(CommonConstants.APPLICATION_NAME.concat(SolrSearchConstants.UNDERSCORE_T), !apps.isEmpty()?String.join(" ", apps):StringUtils.EMPTY);
					
					if(combinedFields.contains(CommonConstants.APPLICATION_NAME.concat(SolrSearchConstants.UNDERSCORE_T))) {
						CombinedFieldsMap.put(CommonConstants.APPLICATION_NAME.concat(SolrSearchConstants.UNDERSCORE_T), !apps.isEmpty()?String.join(" ", apps):StringUtils.EMPTY);
					}
					break;	
				case SolrSearchConstants.INT_ARRAY:
					JsonArray intJsonArray = variantPropertyJson.get(property).getAsJsonArray();
					String[] intArray = new String[intJsonArray.size()];
					int k = 0;
					for (JsonElement element : intJsonArray) {
						intArray[k] = element.getAsString();
						k++;
					}
					Arrays.sort(intArray,String.CASE_INSENSITIVE_ORDER);
					document.addField(property.concat(SolrSearchConstants.UNDERSCORE_IS), intArray);
					break;
				case SolrSearchConstants.STRING_ARRAY:
					String categories = !variantPropertyJson.get(property).isJsonNull() ? variantPropertyJson.get(property).getAsString().trim() : StringUtils.EMPTY;
					if(StringUtils.isNotEmpty(categories)) {
						String[] superCategories = categories.split(CommonConstants.SLASH_SLASH_PIPE);
						if(null != superCategories) {
							TreeSet<String> superAndPrimarycategories = new TreeSet<String>();
							for(String superCategory : superCategories) {
								if(StringUtils.isNotEmpty(superCategory)) {
									superCategory = CommonHelper.normalizeCategories(superCategory);
									String[] superCategoryArray = superCategory.split(",(?![^()]*+\\))");
									if( null != superCategoryArray ) {
										for(int b=0; b<superCategoryArray.length;b++) {
											if(!CommonConstants.PRODUCTS.equalsIgnoreCase(superCategoryArray[b].trim()))
												superAndPrimarycategories.add(superCategoryArray[b].trim());
										}
									}
								}
							}
							String[] cats = new String[superAndPrimarycategories.size()];
							cats = superAndPrimarycategories.toArray(cats);
							Arrays.sort(cats,String.CASE_INSENSITIVE_ORDER);
							document.addField(property.concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_SS), cats);
							
							//Adding the copy field category as space separated value category_t
							document.addField(property.concat(SolrSearchConstants.UNDERSCORE_T), String.join(" ", cats));
							
							if(combinedFields.contains(property.concat(SolrSearchConstants.UNDERSCORE_T))) {
								CombinedFieldsMap.put(property.concat(SolrSearchConstants.UNDERSCORE_T), String.join(" ", cats));
							}
						}
					}
					break;
				case SolrSearchConstants.MEDIA_ARRAY:
					String media = !variantPropertyJson.get(property).isJsonNull() ? variantPropertyJson.get(property).getAsString().trim() : StringUtils.EMPTY;
					if(StringUtils.isNotEmpty(media)) {
						JsonParser parser = new JsonParser();
						JsonArray array = parser.parse(media).getAsJsonArray();
						List<String> list = new ArrayList<>();
						if(null != array) {
							for(JsonElement element : array) {
								JsonObject obj = element.getAsJsonObject();
								if(null != obj) {
									String imageName = obj.get(SolrSearchConstants.IMAGE_NAME).getAsString();
									list.add(imageName);
								}
							}
							String[] medias = new String[list.size()];
							medias = list.toArray(medias);
							document.addField(property.concat(SolrSearchConstants.UNDERSCORE_IMAGE_NAME_SS), medias);
						}
					}
					break;
				case SolrSearchConstants.DOCPART_IDS_ARRAY:
					String docPartIds = !variantPropertyJson.get(property).isJsonNull() ? variantPropertyJson.get(property).getAsString().trim() : StringUtils.EMPTY;
					if (StringUtils.isNotEmpty(docPartIds)) {
						String[] docPartIdsArray = docPartIds.split("[|]");
						String[] trimmedArray = new String[docPartIdsArray.length];

						for (int i = 0; i < docPartIdsArray.length; i++) {
						    trimmedArray[i] = docPartIdsArray[i].trim();
						}

						// trimmedArray contains trimmed strings
						document.addField(SolrSearchConstants.DOCPART_IDS_SS, trimmedArray);
					}
					break;
				default:
			}
		}
	}
	
	/* Application with IC/FCM status are being processed as FCM since the values in HP source system cannot be changed in time for the L1 galaxy release.
	 * Changes are hard-coded to help achieve it.
	 * This will have to be removed after the values in HP are corrected*/
	public boolean isIcFcm(String applicationName) {
		boolean applicationStatus = false;
		if(StringUtils.isNotBlank(applicationName)) {
			String[] applicationNameList = applicationName.split(",");
			for(int a=0; a<applicationNameList.length;a++) {
				if(CommonConstants.IC_FCM.equals(applicationNameList[a].trim())) {
					applicationStatus = true;
					break;
				}
			}
		}
		return applicationStatus;
	}

	public List<String> getListFromString(String property, List<String> apps) {
		if(StringUtils.isNotBlank(property)) {
			String[] listArray = property.split(",");
			for(int a=0; a<listArray.length;a++) {
				apps.add(listArray[a].trim());
			}
		}
		return apps;
	}
	
	/* Application with IC/FCM status are being processed as FCM since the values in HP source system cannot be changed in time for the L1 galaxy release.
	 * Changes are hard-coded to help achieve it.
	 * This will have to be removed after the values in HP are corrected*/
	public List<String> getListFromStringForFcm(String property, List<String> apps, String applicationName) {
		if(StringUtils.isNotBlank(property)) {
			String[] listArray = property.split(",");
			for(int a=0; a<listArray.length;a++) {
				if(!apps.contains(CommonConstants.FLOW_CYTOMETRY) && isIcFcm(applicationName)) {
					apps.add(listArray[a].trim());
					if(!CommonConstants.FLOW_CYTOMETRY.equals(listArray[a])) {
					 apps.add(CommonConstants.FLOW_CYTOMETRY);
					}
				}else if(!CommonConstants.FLOW_CYTOMETRY.equals(listArray[a])){
					apps.add(listArray[a].trim());
				}
			}
		}
		return apps;
	}

	/**
	 * Index pdp urls.
	 *
	 * @param resourceResolver the resource resolver
	 * @param payload the payload
	 * @throws RepositoryException the repository exception
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws LoginException      the login exception
	 */
	public void indexPdpUrls(ResourceResolver resourceResolver, String payload)
			throws RepositoryException, SolrServerException, IOException, LoginException {
		Map<String, String> params = new HashMap<>();
		params.put(SolrSearchConstants.QUERY_BUILDER_PATH, payload);
		params.put(SolrSearchConstants.QUERY_BUILDER_TYPE, solrConfig.getCatalogStructureNodeType());
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY, SolrSearchConstants.IS_VARIANT);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY_VALUE, "true");
		params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");

		Session session = resourceResolver.adaptTo(Session.class);
		Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);
		SearchResult searchResults = query.getResult();
		ArrayList<String> countriesList = (ArrayList<String>) getAllCountries(resourceResolver);
		HashMap<String, ArrayList<SolrInputDocument>> countryRelatedProductMap = getCountryRelatedProductMap(resourceResolver);
		
		GenericList genericProductTypes = CommonHelper.getGenericList(resourceResolver, solrConfig.getProductTypesGenericListEndpoint());
		
		long currentHitNo = 0;
		LOG.debug("Jcr Query Hits : {}", searchResults.getTotalMatches());
		for (Hit hit : searchResults.getHits()) {
			currentHitNo++;
			Resource variantResource = hit.getResource();
			JsonObject countryRelatedProductJson = new JsonObject();
			try {
				LOG.debug("Fetching indexing values for product path {}", hit.getPath());
				Resource variantHpResource = variantResource.getChild(CommonConstants.HP_NODE);  
				ValueMap varientProperties = variantHpResource.adaptTo(ValueMap.class);
				JsonObject varientPropertyJson = getSolrHpJson(varientProperties, variantHpResource, genericProductTypes,
						resourceResolver);
				countryRelatedProductJson = getSapSolrJson(resourceResolver,variantResource,variantHpResource, varientPropertyJson, countryRelatedProductJson, countriesList,solrConfig);				
				countryRelatedProductMap = setCountryRelatedProductSolrDocs(countryRelatedProductJson, countryRelatedProductMap);

				LOG.debug("Finished fetching indexing values for product path {}, with current hit as {}", hit.getPath(), currentHitNo);

				if (currentHitNo % 1000 == 0  || currentHitNo == searchResults.getTotalMatches()) {
					commitingProductsToSolr(countriesList,countryRelatedProductMap);
					countryRelatedProductMap = getCountryRelatedProductMap(resourceResolver);
					LOG.debug("countryRelatedProductMap- {}", countryRelatedProductMap);
					LOG.debug("Batch added to solr with current products {}", currentHitNo);
				}
			} catch (RepositoryException e) {
				LOG.error("Exception while creation json/solrdoc for variant res: {}, Error- {}",hit.getPath(), e);
			}
		}

	}

	/**
	 * Commiting products to solr.
	 *
	 * @param countriesList the countries list
	 * @param countryRelatedProductMap the country related product map
	 */
	private void commitingProductsToSolr(ArrayList<String> countriesList,
										 HashMap<String, ArrayList<SolrInputDocument>> countryRelatedProductMap) {
		for (String country : countriesList) {
			LOG.debug("Starting Indexing for country : {}", country);
			String coreName = solrConfig.getContentPageCollectionName() + "-" + country.toLowerCase();
			String solrUrl = solrConfig.getSolrUrl() + SolrSearchConstants.SLASH_SOLR_SLASH + coreName;
			LOG.debug("Solr Url : {}", solrUrl);
			try {
				LOG.debug("countryRelatedProductMap.get(coreName) : {}", countryRelatedProductMap.get(coreName));
				if (countryRelatedProductMap.containsKey(coreName) && countryRelatedProductMap.get(coreName).size()>0){
					LOG.debug("Before server : {}", country);
					HttpSolrClient server = new HttpSolrClient.Builder(solrUrl).withHttpClient(restClient.getHttpClient()).build();
					LOG.debug("Before countryBasedSolrDocs : {}", country);
					ArrayList<SolrInputDocument> countryBasedSolrDocs = countryRelatedProductMap.get(coreName);
					LOG.debug("Before add : {}", country);
					server.add(countryBasedSolrDocs);
					LOG.debug("Before commit on : {}", country);
					server.commit();
					LOG.debug("Completed commit on : {}", country);
					server.close();
					LOG.debug("Finished Indexing for country : {}", country);
				}
			} catch (SolrServerException | IOException | HttpSolrClient.RemoteSolrException e) {
				LOG.error("Exception while indexing all content pages to solr {}", e);
			}
		}
	}

	/**
	 * Index all pdfs to solr.
	 *
	 * @param resolver the resolver
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws SAXException   the SAX exception
	 * @throws TikaException  the tika exception
	 * @throws LoginException the login exception
	 */
	@Override
	public void indexAllPdfsToSolr(ResourceResolver resolver, String payload)
			throws IOException, SAXException, TikaException, LoginException {
		Session session = resolver.adaptTo(Session.class);
		SearchResult results = crawlPdfs(defaultIfNull(payload, solrConfig.getAssetPath()), session);
		ArrayList<String> countriesList = (ArrayList<String>) getAllCountries(resolver);
		HashMap<String, ArrayList<SolrInputDocument>> countryRelatedPdfMap = getCountryRelatedProductMap(resolver);
		String pdfCombinedFields = solrConfig.getCombinedFieldsForPDFs();
		String[] pdfCombinedFieldsArr = pdfCombinedFields.split(",");
		List<String> pdfCombinedFieldsSplCharsList = decodeSpecialCharacters();
		int batchSize = solrConfig.getBatchSizeOfPdfToIndex();
		int batch = 1;
		int temp = 0;
		if (null != results) {
			for (Hit hit : results.getHits()) {
				Resource pdfResource;
				if (temp == batchSize) {
					LOG.debug("Starting commit of pdf batch : {}",batch);
					commitingProductsToSolr(countriesList, countryRelatedPdfMap);
					countryRelatedPdfMap = getCountryRelatedProductMap(resolver);
					temp = 0;
					LOG.debug("Finished commit of pdf batch : {}",batch);
					batch++;
				}
				try {
					pdfResource = hit.getResource();
					LOG.debug("Processing PDF - {}", pdfResource.getPath());
					if (null != pdfResource && pdfResource.hasChildren()
							&& null != pdfResource.getChild(SolrSearchConstants.JCR_CONTENT)) {
						Asset asset = pdfResource.adaptTo(Asset.class);
						JsonObject countryRelatedPdfJson = createAssetMetadataObject(asset, pdfCombinedFieldsArr, pdfCombinedFieldsSplCharsList);
						countryRelatedPdfMap = setCountryRelatedPdfSolrDocs(countryRelatedPdfJson,
								countryRelatedPdfMap);
						temp++;
					}
				} catch (RepositoryException | TikaException e) {
					LOG.error("Exception in Indexing All pdfs:", e);
				}

			}
			commitingProductsToSolr(countriesList,countryRelatedPdfMap);
		}
		if (session.isLive()) {
			session.logout();
		}
	}
	/**
	 * Index all Scientific Resource Thumbnail Images to solr.
	 *
	 * @param resolver the resolver
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws SAXException   the SAX exception
	 * @throws TikaException  the tika exception
	 * @throws LoginException the login exception
	 */
	@Override
	public void indexAllScientificResourceThumbnailImagesToSolr(ResourceResolver resolver) {
		JsonArray solrJsonArray = new JsonArray();
		try {
			String thumbnailImagesPath = bdbApiEndpointService.getScientificResourceFolder();
			ArrayList<String> countriesList = (ArrayList<String>) getAllCountries(resolver);
			HashMap<String, ArrayList<SolrInputDocument>> countryRelatedVideoMap = getCountryRelatedProductMap(resolver);
			Resource imagesFolder=resolver.getResource(thumbnailImagesPath);
			if(null!=imagesFolder && imagesFolder.hasChildren())
			{
				Iterable<Resource> thumbnailImages = imagesFolder.getChildren();
				for(Resource imageRes:thumbnailImages)
				{
					scientificImagesIndexing(solrJsonArray, imageRes,resolver,countryRelatedVideoMap);
				}
				commitingProductsToSolr(countriesList,countryRelatedVideoMap);
			}
			
		} catch (IOException | SAXException | TikaException | LoginException e) {
			LOG.error("Exception in Indexing All Scientific Resource Thumbnail Images :", e);
		}
	}

	/**
	 * 
	 * @param solrJsonArray
	 * @param imageRes
	 *
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 * @throws LoginException
	 */
	private void scientificImagesIndexing(JsonArray solrJsonArray, Resource imageRes , ResourceResolver resolver, HashMap<String, ArrayList<SolrInputDocument>> countryRelatedVideoMap)
			throws IOException, SAXException, TikaException, LoginException {
		LOG.debug("Processing Thumbnail Images - {}", imageRes.getPath());
		if( imageRes.hasChildren() && null != imageRes.getChild(SolrSearchConstants.JCR_CONTENT) ) {
			ValueMap jcrContentProps = imageRes.getChild(SolrSearchConstants.JCR_CONTENT).getValueMap();
			if (jcrContentProps.containsKey(SolrSearchConstants.CQ_LAST_REPLICATION_ACTION) && jcrContentProps
					.get(SolrSearchConstants.CQ_LAST_REPLICATION_ACTION).equals(SolrSearchConstants.ACTIVATE)) {
				Asset asset = imageRes.adaptTo(Asset.class);
				JsonObject videoJson = createVideoThumbnailMetadataObject(asset);
				setCountryRelatedPdfSolrDocs(videoJson, countryRelatedVideoMap);
			}
		}
	}
	/**
	 * Crawl pdfs.
	 *
	 * @param resourcePath the resource path
	 * @param session      the session
	 * @return the search result
	 */
	public SearchResult crawlPdfs(String resourcePath, Session session) {
		Map<String, String> params = new HashMap<>();
		params.put(SolrSearchConstants.QUERY_BUILDER_PATH, resourcePath);
		params.put(SolrSearchConstants.QUERY_BUILDER_TYPE, SolrSearchConstants.JCR_PRIMARY_TYPE_DAM_ASSETS);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY, SolrSearchConstants.JCR_CONTENT_METADATA_DC_FORMAT);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY_VALUE, SolrSearchConstants.PDF_FILE_FORMAT);
		params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");

		Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);

		return query.getResult();
	}

	/**
	 * Un index content.
	 *
	 * @param resourcePath the resource path
	 * @param docType      the doc type
	 * @throws LoginException the login exception
	 */
	@Override
	public void unIndexContent(String resourcePath, String docType) throws LoginException {
		switch (docType) {

		case "contentPage":
			try {
				String[] pathSplit = resourcePath.split("/");
				if(pathSplit.length > 6) {
					String country = pathSplit[4];
				HttpSolrClient server = solrClient(country);
				server.deleteById(resourcePath);
				server.commit();
				server.close();
				}
			} catch (SolrServerException | IOException | HttpSolrClient.RemoteSolrException e) {
				LOG.error("Exception occured while deleting the index {}", e);
			}
			break;

			case "pdfAsset":
				try {
					ArrayList<HttpSolrClient> solrClients = (ArrayList<HttpSolrClient>) getAllSolrClients();
					for (HttpSolrClient server : solrClients) {
						server.deleteById(resourcePath);
						server.commit();
						server.close();
					}
				} catch (SolrServerException | IOException | HttpSolrClient.RemoteSolrException e) {
					LOG.error("Exception occured while deleting the index {}", e);
				}
				break;
			default:
		}
	}

	/**
	 * Gets the hp node resource.
	 *
	 * @param materialNo the material no
	 * @param country the country
	 * @param resourceResolver the resource resolver
	 * @return the hp node resource
	 */
	@Override
	public Resource getHpNodeResource(String materialNo, String country, ResourceResolver resourceResolver) {
		LOG.debug("Inside getHpNodeResource ------------->{}",materialNo);
		String hpNodePath;
		Resource resource = null;
		if (StringUtils.isNotBlank(materialNo)) {
			SolrClient server = solrClient(country);
			QueryResponse queryResponse;
			SolrQuery solrQuery = new SolrQuery();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");  
	    	LocalDateTime now = LocalDateTime.now();  
			solrQuery.addFilterQuery(SolrSearchConstants.DOCTYPE_T_PRODUCT);
			solrQuery.addFilterQuery("isAvailable_t:true");
			solrQuery.setParam("fl", SolrSearchConstants.HP_NODE_PATH_T);
			solrQuery.addFilterQuery("validityStartDate_dt:[* TO ".concat(dtf.format(now)).concat("]"));
			solrQuery.setQuery("uniqueIdentifier:".concat("\""+materialNo.toUpperCase()+"\""));
			try {
				queryResponse = server.query(solrQuery);
				SolrDocumentList sitesSolrDocs = queryResponse.getResults();
				if (!sitesSolrDocs.isEmpty()) {
					SolrDocument solrDocument = sitesSolrDocs.get(0);
					hpNodePath = solrDocument.get(SolrSearchConstants.HP_NODE_PATH_T).toString();
					LOG.debug("hpNodePath ------------->{}",hpNodePath);
					resource = resourceResolver.getResource(hpNodePath);
				}
			} catch (SolrServerException | IOException  | HttpSolrClient.RemoteSolrException e) {
				LOG.error("Error in getting HP node path {0}", e);
			}
		}
		return resource;
	}

	/**
	 * Check if product exists.
	 *
	 * @param baseMaterialNo the base material no
	 * @param country the country
	 * @param resourceResolver the resource resolver
	 * @param productType the product type
	 * @return the resource
	 */
	@Override
	public Resource checkIfProductExists(String baseMaterialNo, String country, ResourceResolver resourceResolver, String productType) {
		String hpNodePath;
		Resource resource = null;
		if (null != baseMaterialNo && !baseMaterialNo.isEmpty()) {
			SolrClient server = solrClient(country);
			QueryResponse queryResponse;
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.addFilterQuery(SolrSearchConstants.DOCTYPE_T_PRODUCT);
			solrQuery.addFilterQuery("isAvailable_t:true");
			solrQuery.setQuery(productType.concat("_t:").concat(baseMaterialNo));
			LOG.debug("checkIfProductExists : {}",solrQuery);
			try {
				queryResponse = server.query(solrQuery);
				SolrDocumentList sitesSolrDocs = queryResponse.getResults();
				if (!sitesSolrDocs.isEmpty()) {
					SolrDocument solrDocument = sitesSolrDocs.get(0);
					hpNodePath = solrDocument.get(SolrSearchConstants.HP_NODE_PATH_T).toString();
					resource = resourceResolver.getResource(hpNodePath);
					if(null!=resource) {
						return resource.getParent().getParent();
					}
				}
			} catch (SolrServerException | IOException  | HttpSolrClient.RemoteSolrException e) {
				LOG.error("Error in getting hp node path {}", e);
			}
		}
		return null;
	}

	/**
	 * Gets Hp node resource with Image name in the medias.
	 *
	 * @param imageName Image name.
	 * @param resourceResolver the resource resolver
	 * @return Hp node resource
	 */
	@Override
	public ArrayList<Resource> getMatchingImagesHpRes(String imageName, ResourceResolver resourceResolver) {
		String hpNodePath;
		ArrayList<Resource> resourceList = new ArrayList<>();
		if (StringUtils.isNotBlank(imageName)) {
			imageName = CommonConstants.DOUBLE_QOUTES.concat(imageName).concat(CommonConstants.DOUBLE_QOUTES);
			HttpSolrClient server = solrClient("us");
			QueryResponse queryResponse;
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.addFilterQuery(SolrSearchConstants.DOCTYPE_T_PRODUCT);
			solrQuery.setParam("fl", SolrSearchConstants.HP_NODE_PATH_T);
			solrQuery.setQuery("medias_imageName_ss:".concat(imageName));
			solrQuery.setRows(solrConfig.getProductsSolrQueryResultLimit());
			LOG.debug("Image Medias Solr Query : {}",solrQuery);
			try {
				queryResponse = server.query(solrQuery);
				SolrDocumentList sitesSolrDocs = queryResponse.getResults();
				LOG.debug("Search result size : {}",sitesSolrDocs.size());
				if (!sitesSolrDocs.isEmpty()) {
					for(SolrDocument solrDocument:sitesSolrDocs)
					{
						hpNodePath = solrDocument.get(SolrSearchConstants.HP_NODE_PATH_T).toString();
						Resource resource = resourceResolver.getResource(hpNodePath);
						resourceList.add(resource);
					}
				}
			} catch (SolrServerException | IOException e) {
				LOG.error("Error in getting HP node path {0}", e);
			}
		}
		return resourceList;
	}

	/**
	 * Gets the hp node resources.
	 *
	 * @param inputKey the input key
	 * @param inputValue the input value
	 * @param resourceResolver the resource resolver
	 * @return the hp node resources
	 */
	@Override
	public List<Resource> getHpNodeResources(String inputKey, String inputValue, ResourceResolver resourceResolver) {
		List<Resource> resources = new ArrayList<>();
		if (StringUtils.isNotBlank(inputKey)) {
			SolrClient server = solrClient("us");
			QueryResponse queryResponse;
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.addFilterQuery("docType_t:product");
			solrQuery.setParam("fl", "variantHpNodePath_t");
			solrQuery.setRows(solrConfig.getProductsSolrQueryResultLimit());
			if (StringUtils.equals(inputKey, SolrSearchConstants.DOCPART_IDS_SS)) {
				solrQuery.setQuery(inputKey+":".concat("\""+inputValue+"\""));
			} else {
				solrQuery.setQuery(inputKey+":".concat("\""+inputValue.toUpperCase()+"\""));
			}
			try {
				queryResponse = server.query(solrQuery);
				SolrDocumentList sitesSolrDocs = queryResponse.getResults();
				if (!sitesSolrDocs.isEmpty()) {
					formatToList(resourceResolver, resources, sitesSolrDocs);
				}
			} catch (SolrServerException | IOException e) {
				LOG.error("Error in getting HP node path {0}", e);
			}
		}
		return resources;
	}
	
	
	/**
	 * Gets the hp node resources.
	 *
	 * @param inputKey the input key
	 * @param inputValue the input value
	 * @param resourceResolver the resource resolver
	 * @return the hp node resources
	 */
	@Override
	public List<BaseVariantHpResourceBean> getBaseVariantHpNodeResources(String inputKey, String inputValue, ResourceResolver resourceResolver) {
		
		List<BaseVariantHpResourceBean> resourcesBean = new ArrayList<>();
		if (StringUtils.isNotBlank(inputKey)) {
			SolrClient server = solrClient("us");
			QueryResponse queryResponse;
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.addFilterQuery("docType_t:product");
			solrQuery.setParam("fl", "variantHpNodePath_t,hpNodePath_t");
			solrQuery.setRows(solrConfig.getProductsSolrQueryResultLimit());
			if (StringUtils.equals(inputKey, SolrSearchConstants.DOCPART_IDS_SS)) {
				solrQuery.setQuery(inputKey+":".concat("\""+inputValue+"\""));
			} else {
				solrQuery.setQuery(inputKey+":".concat("\""+inputValue.toUpperCase()+"\""));
			}
			try {
				queryResponse = server.query(solrQuery);
				SolrDocumentList sitesSolrDocs = queryResponse.getResults();
				if (!sitesSolrDocs.isEmpty()) {
					formatBaseVariantHPToList(resourceResolver, resourcesBean, sitesSolrDocs);
				}
			} catch (SolrServerException | IOException e) {
				LOG.error("Error in getting HP node path {0}", e);
			}
		}
		return resourcesBean;
	}

	/**
	 * Format to list.
	 *
	 * @param resourceResolver the resource resolver
	 * @param resources the resources
	 * @param sitesSolrDocs the sites solr docs
	 */
	private void formatToList(ResourceResolver resourceResolver, List<Resource> resources,
							  SolrDocumentList sitesSolrDocs) {
		String hpNodePath;
		for(SolrDocument solrDocument : sitesSolrDocs) {
			if (!solrDocument.isEmpty()) {
				hpNodePath = solrDocument.get("variantHpNodePath_t").toString();
				Resource resource = resourceResolver.getResource(hpNodePath);
				if(null != resource) {
					resources.add(resource);
				}
			}
		}
	}
	
	
	/**
	 * Format to list.
	 *
	 * @param resourceResolver the resource resolver
	 * @param resources the resources
	 * @param sitesSolrDocs the sites solr docs
	 */
	private void formatBaseVariantHPToList(ResourceResolver resourceResolver, List<BaseVariantHpResourceBean> resourcesBean,
							  SolrDocumentList sitesSolrDocs) {
		String baseHpNodePath;
		String variantHpNodePath;
		for(SolrDocument solrDocument : sitesSolrDocs) {
			if (!solrDocument.isEmpty()) {
				BaseVariantHpResourceBean baseVariantHpResourceBean = new BaseVariantHpResourceBean();
				variantHpNodePath = solrDocument.get("variantHpNodePath_t").toString();
				baseHpNodePath = solrDocument.get("hpNodePath_t").toString();
				Resource baseHpResource = resourceResolver.getResource(baseHpNodePath);
				Resource variantHpResource = resourceResolver.getResource(variantHpNodePath);
				if(null != baseHpResource && null != variantHpResource) {
					baseVariantHpResourceBean.setBaseHpResource(baseHpResource);
					baseVariantHpResourceBean.setVariantHpResource(variantHpResource);
					resourcesBean.add(baseVariantHpResourceBean);				}
			}
		}
	}

	/**
	 * Gets the scientific res lib.
	 *
	 * @param docTypes the doc types
	 * @param startIndex the start index
	 * @param size the size
	 * @param searchTerm the search term
	 * @return resourceList
	 */
	@Override
	public Map<String,Object> getScientificResLib(String docTypes, int startIndex,
												  int size,String searchTerm,String country) {

		ArrayList<Map<String,String>> resourceList = new ArrayList<>();
		Map<String,Object> jsonMap = new HashMap<>();
		String facetVariable =  SolrSearchConstants.SCIENTIFIC_RES_ASSET_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S).concat(CommonConstants.COLON);
		String docTypeParm = StringUtils.EMPTY;
		try {
			String[] specialSym = (String[]) configurationAdmin.getConfiguration("com.bdb.aem.core.servlets.solr.FetchingDataFromSolr").getProperties().get("specialCharactersToBeModified");
				LOG.debug("Symboles we are getting : {}",Arrays.toString(specialSym));
			searchTerm=handelingSpecialSymboles(specialSym,searchTerm);
			if(null!=docTypes)docTypes = handelingSpecialSymboles(specialSym,docTypes);
		} catch (IOException e1) {
				LOG.error("Not able to access the OSGI Configs :: ", e1);
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(SOLR_DATE_FORMAT);
		LocalDateTime now = LocalDateTime.now();
			
			String brightCoveID=bdbApiEndpointService.brightcoveAccountId();
			String brightCovePlayerId=bdbApiEndpointService.brightcovePlayerId();
			HttpSolrClient server = solrClient(country);
			QueryResponse queryResponse;
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.addFilterQuery(SolrSearchConstants.DOCTYPE_T_ASSET);
			solrQuery.addFilterQuery("isAvailable_t:true");
			solrQuery.addFilterQuery("validityStartDate_dt:[* TO ".concat(dtf.format(now)).concat("]"));
			
			if (StringUtils.isNotBlank(docTypes) && !docTypes.equalsIgnoreCase("all") && !docTypes.contains(",all") && !docTypes.contains("all,")) 
			{
				if(docTypes.contains(","))
				{
					for(String docType: docTypes.split(","))
					{
						if(docType.contains(TDS)) {
							docTypeParm = docTypeParm.concat(facetVariable.concat("\""+TECHNICAL_DATA_SHEET_CAPS+"\"").concat(" OR "));
							docTypeParm = docTypeParm.concat(facetVariable.concat("\""+TECHNICAL_DATA_SHEET_LOWER+"\"").concat(" OR "));
							docTypeParm = docTypeParm.concat(facetVariable.concat("\""+TDS+"\"").concat(" OR "));
							
						} else if(docType.contains(IFU)) {
							docTypeParm = docTypeParm.concat(facetVariable.concat("\""+INSTRUCTION_FOR_USE_CAPS+"\"").concat(" OR "));
							docTypeParm = docTypeParm.concat(facetVariable.concat("\""+INSTRUCTION_FOR_USE_LOWER+"\"").concat(" OR "));
							docTypeParm = docTypeParm.concat(facetVariable.concat("\""+IFU+"\"").concat(" OR "));
							
						} else {
							docTypeParm = docTypeParm.concat(facetVariable.concat("\""+docType+"\"").concat(" OR "));
						}						
					}
					docTypeParm= docTypeParm.substring(0,docTypeParm.lastIndexOf(" OR "));
				}
				else
				{
					if(docTypes.contains(TDS)) {
						docTypeParm = docTypeParm.concat(facetVariable.concat("\""+TECHNICAL_DATA_SHEET_CAPS+"\"").concat(" OR "));
						docTypeParm = docTypeParm.concat(facetVariable.concat("\""+TECHNICAL_DATA_SHEET_LOWER+"\"").concat(" OR "));
						//docTypeParm = facetVariable.concat("\""+TECHNICAL_DATA_SHEET_CAPS+"\"").concat(" OR ");
						//docTypeParm = facetVariable.concat("\""+TECHNICAL_DATA_SHEET_LOWER+"\"").concat(" OR ");
						docTypeParm = docTypeParm.concat(facetVariable.concat("\""+TDS+"\""));
					} else if(docTypes.contains(IFU)) {
						docTypeParm = docTypeParm.concat(facetVariable.concat("\""+INSTRUCTION_FOR_USE_CAPS+"\"").concat(" OR "));
						docTypeParm = docTypeParm.concat(facetVariable.concat("\""+INSTRUCTION_FOR_USE_LOWER+"\"").concat(" OR "));
						//docTypeParm = facetVariable.concat("\""+INSTRUCTION_FOR_USE_CAPS+"\"").concat(" OR ");
						//docTypeParm = facetVariable.concat("\""+INSTRUCTION_FOR_USE_LOWER+"\"").concat(" OR ");
						docTypeParm = docTypeParm.concat(facetVariable.concat("\""+IFU+"\""));
					} else {
						docTypeParm = facetVariable.concat("\""+docTypes+"\"");
					}
				}
			}
			else
			{
				docTypeParm=facetVariable.concat("/[A-Za-z0-9()]+/");
			}
			addSearchAttribute(searchTerm, solrQuery, docTypeParm);
			
			solrQuery.setStart(startIndex);
			solrQuery.setRows(size);
			solrQuery.setParam("fl", SolrSearchConstants.SOLR_FIELD_PDFX_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_T)+CommonConstants.COMMA+SolrSearchConstants.PDF_URL_T+CommonConstants.COMMA+PDF_FILTER_DESC+CommonConstants.COMMA+PDF_FILTER_DOCTYPE+CommonConstants.COMMA+PDF_FILTER_TITLE+CommonConstants.COMMA+BRIGHT_COVE_VIDEO_ID);
			solrQuery.setSort(CQ_LAST_REPLICATED, SolrQuery.ORDER.desc);
			LOG.debug("Query String :: {}",solrQuery);
			try {
				queryResponse = server.query(solrQuery);
				SolrDocumentList sitesSolrDocs = queryResponse.getResults();
				jsonMap.put("totalCount", sitesSolrDocs.getNumFound());
				jsonMap.put("brightCoveID", brightCoveID);
				jsonMap.put("brightCovePlayerId", brightCovePlayerId);
				if (!sitesSolrDocs.isEmpty()) {
					for (SolrDocument solrDocument : sitesSolrDocs) {
						solrResultList(resourceList, solrDocument);
					}
					jsonMap.put("cardsData", resourceList);
				}
			} catch (SolrServerException | IOException e) {
				LOG.error("Could not find the data in solr :: ", e);
			}
		
		return jsonMap;
	}

	/**
	 * 
	 * @param specialSymboles
	 * @param searchTeam
	 * @return searchTeam
	 */
	private String handelingSpecialSymboles(String[] specialSymboles, String searchTeam) {
		if(null != specialSymboles && specialSymboles.length > 0) {
			for(int i=0; i<specialSymboles.length;i++) {
				if(searchTeam.contains(specialSymboles[i])){
					searchTeam = searchTeam.replace(specialSymboles[i], "\\" +specialSymboles[i] );
				}
			}
		}
		return searchTeam;
	}
	
	/**
	 * Adds the search attribute.
	 *
	 * @param searchTerm the search term
	 * @param solrQuery the solr query
	 * @param appendDocTypes appending the DocTypes
	 */
	private void addSearchAttribute(String searchTerm, SolrQuery solrQuery,String appendDocTypes) {
		
		if (!searchTerm.isEmpty()) {
			
			searchTerm = CommonConstants.DOUBLE_QOUTES.concat(searchTerm).concat(CommonConstants.DOUBLE_QOUTES);
			if(!appendDocTypes.isEmpty())
			{
				solrQuery.setQuery("("+appendDocTypes+") AND ("+PDF_FILTER_TITLE.concat(CommonConstants.COLON) + searchTerm + " OR "+PDF_FILTER_DESC.concat(CommonConstants.COLON)+ searchTerm + " OR "+SolrSearchConstants.SOLR_FIELD_PDFX_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_T).concat(CommonConstants.COLON)+searchTerm+")");
			}
		}
		else
		{
			solrQuery.setQuery(appendDocTypes);
		}
	}
	
	
	/**
	 * Solr result list.
	 *
	 * @param resourceList the resource list
	 * @param solrDocument the solr document
	 */
	private void solrResultList(ArrayList<Map<String,String>> resourceList,
								SolrDocument solrDocument) {
		if (!solrDocument.isEmpty()) {
			Map<String,String> map = new HashMap<>();
			String pdfDamPath = solrDocument.get(SolrSearchConstants.PDF_URL_T).toString();
			map.put("resourceName", solrDocument.get(SolrSearchConstants.SOLR_FIELD_PDFX_DOC_TYPE.concat(SolrSearchConstants.UNDERSCORE_T)).toString());
			map.put("fileType", solrDocument.get(PDF_FILTER_DOCTYPE).toString());
			map.put("resourceTitle", solrDocument.get(PDF_FILTER_TITLE).toString());
			LOG.debug("resourceTitle :: {}",solrDocument.get(PDF_FILTER_TITLE).toString());
			map.put("resourceDesc", solrDocument.get(PDF_FILTER_DESC).toString());
			if(solrDocument.get(PDF_FILTER_DOCTYPE).toString().equals("pdf"))
			{
				map.put("downloadLink", solrDocument.get(SolrSearchConstants.PDF_URL_T).toString());
				if (null != pdfDamPath)
					resourceList.add(map);
			}else {
				map.put("downloadLink", solrDocument.get(BRIGHT_COVE_VIDEO_ID).toString());
				resourceList.add(map);
			}
			LOG.debug("map :: {}",map);
		}
	}
}