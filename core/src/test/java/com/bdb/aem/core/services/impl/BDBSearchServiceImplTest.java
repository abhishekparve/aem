package com.bdb.aem.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class BDBSearchServiceImplTest.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class BDBSearchServiceImplTest {
	
	/** The bdb search endpoint service impl obj. */
	@InjectMocks
	private BDBSearchEndpointServiceImpl bdbSearchEndpointServiceImplObj = new BDBSearchEndpointServiceImpl();
	
	/** The config. */
	@Mock
	BDBSearchEndpointServiceImpl.Configuration config;
	
	/** The synonym results. */
	private String SYNONYM_RESULTS = "/solr/bd/admin/file?wt=json&_=1597818284848&file=_schema_analysis_synonyms_{{language}}.json&contentType=application%2Fjson%3Bcharset%3Dutf-8";
	
	/** The delete synonym. */
	private String DELETE_SYNONYM = "/solr/bd/schema/analysis/synonyms/{{language}}/{{word}}";
	
	/** The create synonym. */
	private String CREATE_SYNONYM = "/solr/bd/schema/analysis/synonyms/{{language}";
	
	/** The stop word results. */
	private String STOP_WORD_RESULTS = "/solr/bd/admin/file?wt=json&_=1596539572415&file=_schema_analysis_stopwords_{{language}}.json&contentType=application%2Fjson%3Bcharset%3Dutf-8";
	
	/** The create stop words. */
	private String CREATE_STOP_WORDS = "/solr/bd/schema/analysis/stopwords/{{language}}";
	
	/** The remove selected stop words. */
	private String REMOVE_SELECTED_STOP_WORDS = "/solr/bd/schema/analysis/stopwords/{{language}}/{{word}}";
	
	/** The synonym results servlet. */
	private String SYNONYM_RESULTS_SERVLET = "/content/bdb/paths/get-search-endpoints?param=getSynonymResults&lang={{language}}";
	
	/** The delete synonym servlet. */
	private String DELETE_SYNONYM_SERVLET = "/content/bdb/paths/get-search-endpoints?param=deleteSynonym&deleteWord={{word}}&lang={{language}}";
	
	/** The create synonym servlet. */
	private String CREATE_SYNONYM_SERVLET = "/content/bdb/paths/get-search-endpoints?param=createSynonym&lang={{language}}";
	
	/** The get stopword result servlets. */
	private String GET_STOPWORD_RESULT_SERVLETS = "/content/bdb/paths/get-search-endpoints?param=getStopWordResults&lang={{language}}";
	
	/** The create stopword servlet. */
	private String CREATE_STOPWORD_SERVLET = "/content/bdb/paths/get-search-endpoints?param=createStopWord&lang={{language}}";
	
	/** The get content page collection name. */
	private String GET_CONTENT_PAGE_COLLECTION_NAME = "bd";
	
	/** The content page path. */
	private String CONTENT_PAGE_PATH = "/content/bdb";
	
	/** The asset base path. */
	private String ASSET_BASE_PATH = "/content/dam/bd"; 
	
	/** The typeahead servlet. */
	private String TYPEAHEAD_SERVLET = "/content/bdb/paths/type-ahead-suggestion";
	
	/** The bd popular search collection name. */
	private String BD_POPULAR_SEARCH_COLLECTION_NAME = "bd-popular-search";
	
	/** The bd popular search servlet path. */
	private String BD_POPULAR_SEARCH_SERVLET_PATH = "/content/bdb/paths/popular-search.json";

	/** The solr url. */
	private String SOLR_URL = "https://devsearch.bdbiosciences.com";
	
	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "assetPath",ASSET_BASE_PATH);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "basePagePath",CONTENT_PAGE_PATH);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "contentCollectionName",GET_CONTENT_PAGE_COLLECTION_NAME);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "createStopWord",CREATE_STOP_WORDS);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "createStopWordServlet",CREATE_STOPWORD_SERVLET);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "createSynonym",CREATE_SYNONYM);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "createSynonymServlet",CREATE_SYNONYM_SERVLET);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "assetCollectionName",GET_CONTENT_PAGE_COLLECTION_NAME);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "deleteSynonym",DELETE_SYNONYM);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "deleteSynonymServlet",DELETE_SYNONYM_SERVLET);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "getStopWordResultsServlet",GET_STOPWORD_RESULT_SERVLETS);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "popularSearchedCollection",BD_POPULAR_SEARCH_COLLECTION_NAME);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "popularSearchedServlet",BD_POPULAR_SEARCH_SERVLET_PATH);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "refreshSolrCollection",GET_CONTENT_PAGE_COLLECTION_NAME);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "removeSelectedStopWord",REMOVE_SELECTED_STOP_WORDS);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "solrUrl",SOLR_URL);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "stopWordResults",STOP_WORD_RESULTS);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "synonymResults",SYNONYM_RESULTS);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "synonymResultsServlet",SYNONYM_RESULTS_SERVLET);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "typeAheadServlet",TYPEAHEAD_SERVLET);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "productsSolrQueryResultLimit",30);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "batchSizeOfPdfToIndex",500);
		PrivateAccessor.setField(bdbSearchEndpointServiceImplObj, "cellTypeQueryLimit",32000);
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertEquals(ASSET_BASE_PATH, bdbSearchEndpointServiceImplObj.getAssetPath());
		assertEquals(CONTENT_PAGE_PATH, bdbSearchEndpointServiceImplObj.getBasePagePath());
		assertEquals(GET_CONTENT_PAGE_COLLECTION_NAME, bdbSearchEndpointServiceImplObj.getContentPageCollectionName());
		assertEquals(CREATE_STOP_WORDS, bdbSearchEndpointServiceImplObj.getCreateStopWord());
		assertEquals(CREATE_STOPWORD_SERVLET, bdbSearchEndpointServiceImplObj.getCreateStopWordServlet());
		assertEquals(CREATE_SYNONYM, bdbSearchEndpointServiceImplObj.getCreateSynonym());
		assertEquals(CREATE_SYNONYM_SERVLET, bdbSearchEndpointServiceImplObj.getCreateSynonymServlet());
		assertEquals(GET_CONTENT_PAGE_COLLECTION_NAME, bdbSearchEndpointServiceImplObj.getDamCollectionName());
		assertEquals(DELETE_SYNONYM, bdbSearchEndpointServiceImplObj.getDeleteSynonym());
		assertEquals(DELETE_SYNONYM_SERVLET, bdbSearchEndpointServiceImplObj.getDeleteSynonymServlet());
		assertEquals(GET_STOPWORD_RESULT_SERVLETS, bdbSearchEndpointServiceImplObj.getGetStopWordResultsServlet());
		assertEquals(BD_POPULAR_SEARCH_COLLECTION_NAME, bdbSearchEndpointServiceImplObj.getPopularSearchedCollectionName());
		assertEquals(BD_POPULAR_SEARCH_SERVLET_PATH, bdbSearchEndpointServiceImplObj.getPopularSearchedServletPath());
		assertEquals(GET_CONTENT_PAGE_COLLECTION_NAME, bdbSearchEndpointServiceImplObj.getRefreshSolrCollection());
		assertEquals(REMOVE_SELECTED_STOP_WORDS, bdbSearchEndpointServiceImplObj.getRemoveSelectedStopWord());
		assertEquals(SOLR_URL, bdbSearchEndpointServiceImplObj.getSolrUrl());
		assertEquals(STOP_WORD_RESULTS, bdbSearchEndpointServiceImplObj.getStopWordResults());
		assertEquals(SYNONYM_RESULTS, bdbSearchEndpointServiceImplObj.getSynonymResults());
		assertEquals(SYNONYM_RESULTS_SERVLET, bdbSearchEndpointServiceImplObj.getSynonymResultsServlet());
		assertEquals(TYPEAHEAD_SERVLET, bdbSearchEndpointServiceImplObj.getTypeAheadServletPath());
		assertEquals(30, bdbSearchEndpointServiceImplObj.getProductsSolrQueryResultLimit());
		assertEquals(500, bdbSearchEndpointServiceImplObj.getBatchSizeOfPdfToIndex());
		assertEquals(32000, bdbSearchEndpointServiceImplObj.cellTypeQueryLimit());
		bdbSearchEndpointServiceImplObj.getRemoveSelectedStopWord();
		bdbSearchEndpointServiceImplObj.getAllowSolrIndexing();
		bdbSearchEndpointServiceImplObj.getAllowProductReplication();
		bdbSearchEndpointServiceImplObj.getCatalogStructureNodeType();
		bdbSearchEndpointServiceImplObj.getAdminPagePath();
		bdbSearchEndpointServiceImplObj.getProductTypesGenericListEndpoint();
	}
	
	/**
	 * Test activate.
	 */
	@Test
	void testActivate() {
		when(config.productsSolrQueryResultLimit()).thenReturn("30");
		when(config.batchSizeOfPdfToIndex()).thenReturn("500");
		when(config.cellTypeQueryLimit()).thenReturn(32000);
		bdbSearchEndpointServiceImplObj.activate(config);
	}
	
	
}