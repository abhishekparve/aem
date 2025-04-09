package com.bdb.aem.core.services;


/**
 * The Interface BDBSearchEndpointService.
 */
public interface BDBSearchEndpointService extends BaseService {


	/**
	 * Gets the synonym results.
	 *
	 * @return the synonym results
	 */
	public String getSynonymResults();

	/**
	 * Gets the delete synonym.
	 *
	 * @return the delete synonym
	 */
	public String getDeleteSynonym();

	/**
	 * Gets the creates the synonym.
	 *
	 * @return the creates the synonym
	 */
	public String getCreateSynonym();

	/**
	 * Gets the stop word results.
	 *
	 * @return the stop word results
	 */
	public String getStopWordResults();

	/**
	 * Gets the creates the stop word.
	 *
	 * @return the creates the stop word
	 */
	public String getCreateStopWord();

	/**
	 * Gets the removes the selected stop word.
	 *
	 * @return the removes the selected stop word
	 */
	public String getRemoveSelectedStopWord();

	/**
	 * Gets the synonym results servlet.
	 *
	 * @return the synonym results servlet
	 */
	public String getSynonymResultsServlet();

	/**
	 * Gets the creates the synonym servlet.
	 *
	 * @return the creates the synonym servlet
	 */
	public String getCreateSynonymServlet();
	
	/**
	 * Gets the reload solr server servlet.
	 *
	 * @return the reload solr server servlet
	 */
	public String getReloadSolrServerServlet();

	/**
	 * Gets the delete synonym servlet.
	 *
	 * @return the delete synonym servlet
	 */
	public String getDeleteSynonymServlet();

	/**
	 * Gets the gets the stop word results servlet.
	 *
	 * @return the gets the stop word results servlet
	 */
	public String getGetStopWordResultsServlet();

	/**
	 * Gets the creates the stop word servlet.
	 *
	 * @return the creates the stop word servlet
	 */
	public String getCreateStopWordServlet();

	/**
	 * Gets the removes the selected stop word servlet.
	 *
	 * @return the removes the selected stop word servlet
	 */
	public String getRemoveSelectedStopWordServlet();
	
	/**
	 * Gets the solr url.
	 *
	 * @return the solr url
	 */
	public String getSolrUrl();
	
	/**
	 * Gets the slave solr server srl.
	 *
	 * @return the slave solr url
	 */
	public String getSlaveSolrUrl();

	/**
	 * Gets the content page collection name.
	 *
	 * @return the content page collection name
	 */
	public String getContentPageCollectionName();

	/**
	 * Gets the dam collection name.
	 *
	 * @return the dam collection name
	 */
	public String getDamCollectionName();

	/**
	 * Gets the base page path.
	 *
	 * @return the base page path
	 */
	public String getBasePagePath();

	/**
	 * Gets the asset path.
	 *
	 * @return the asset path
	 */
	public String getAssetPath();

	/**
	 * Gets the asset path.
	 *
	 * @return the asset path
	 */
	public String getTypeAheadServletPath();
	
	/**
	 * Gets the popular searched collection name.
	 *
	 * @return the popular searched collection name
	 */
	public String getPopularSearchedCollectionName();
	
	/**
	 * Gets the popular searched servlet path.
	 *
	 * @return popular searched servlet path
	 */
	public String getPopularSearchedServletPath();

	/**
	 * Gets the Refresh Solr Collection.
	 *
	 * @return the refresh solr collection
	 */
	public String getRefreshSolrCollection();

	/**
	 * Gets the indexes to be queried in SOLR for Search Results.
	 *
	 * @return the indexes to be queried
	 */
	public String[] getIndexesToBeQueried();

	/**
	 * Gets the allow solr indexing.
	 *
	 * @return the allow solr indexing
	 */
	public String getAllowSolrIndexing();
	
	/**
	 * Gets the allow product replication.
	 *
	 * @return the allow product replication
	 */
	public String getAllowProductReplication();
	
	/**
	 * Gets the catalog structure node type.
	 *
	 * @return the catalog structure node type
	 */
	public String getCatalogStructureNodeType();
	
	/**
	 * Gets the admin page path.
	 *
	 * @return the admin page path
	 */
	public String getAdminPagePath();
	
	/**
	 * Gets the Product type generic list.
	 *
	 * @return path of the generic list
	 */
	public String getProductTypesGenericListEndpoint();
	
	/**
	 * Species reactivity status.
	 *
	 * @return the string
	 */
	String speciesReactivityStatus();
	
	/**
	 * Application status.
	 *
	 * @return the string
	 */
	String applicationStatus();

	/**
	 * Gets the products solr query result limit.
	 *
	 * @return the products solr query result limit
	 */
	int getProductsSolrQueryResultLimit();

	/**
	 * Gets the batch size of pdf to index.
	 *
	 * @return the batch size of pdf to index
	 */
	int getBatchSizeOfPdfToIndex();
	
	/**
	 * concatenated query fields
	 *
	 * @return the string
	 */
	String getConcatenatedQueryFields();
	
	/**
	 * getConcatenatedFieldSplChars
	 *
	 * @return the string
	 */
	String getConcatenatedFieldSplChars();
	
	/**
	 * is GB same as EU
	 *
	 * @return the string
	 */
	String isEUSameAsGB();
	
	/**
	 * Gets the ivd translation regions.
	 *
	 * @return the ivd translation regions
	 */
	String getIvdTranslationRegions();

	/**
	 * Gets the ivd translation genericlist.
	 *
	 * @return the ivd translation path
	 */
	String getivdTranslationRegionsList();
	
	/**
	 * concatenated query excluded fields
	 *
	 * @return the string
	 */
	String getConcatenatedQueryExcludedFields();
	
	/**
	 * Gets the icm tools service path.
	 *
	 * @return the icm tools service path
	 */
	String getIcmToolsServicePath();
	
	/**
	 * Gets the search payload path.
	 *
	 * @return the search payload path.
	 */
	String getSearchPayloadPath();

	/**
	 * isEdismax
	 *
	 * @return the string
	 */
	String isEdismax();
	
	/**
	 * cellTypeQueryLimit
	 *
	 * @return the string
	 */
	int cellTypeQueryLimit();
	
	/**
	 * getSplCharsToReplaceSearchTerm
	 *
	 * @return the string
	 */
	String getSplCharsToReplaceSearchTerm();

	String countryToRegionAndLanguge();
	
	/**
	 * Combined fields for PDF
	 *
	 * @return the string
	 */
	String getCombinedFieldsForPDFs();
	
	/**
	 * Combined fields for Video
	 *
	 * @return the string
	 */
	String getCombinedFieldsForVideos();

	String showCiteabWidget();


}