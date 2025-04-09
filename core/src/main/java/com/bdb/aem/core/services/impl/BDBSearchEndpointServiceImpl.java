package com.bdb.aem.core.services.impl;


import java.util.Arrays;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.util.CommonConstants;

import lombok.Getter;

/**
 * The Class BDBSearchEndpointServiceImpl.
 */
@Component(immediate = true, service = BDBSearchEndpointService.class)
@Designate(ocd = BDBSearchEndpointServiceImpl.Configuration.class)
public class BDBSearchEndpointServiceImpl implements BDBSearchEndpointService {

    /** The synonym results. */
    private String synonymResults;

    /** The delete synonym. */
    private String deleteSynonym;

    /** The create synonym. */
    private String createSynonym;

    /** The stop word results. */
    private String stopWordResults;

    /** The create stop word. */
    private String createStopWord;

    /** The remove selected stop word. */
    private String removeSelectedStopWord;

    /** The synonym results servlet. */
    private String synonymResultsServlet;

    /** The delete synonym servlet. */
    private String deleteSynonymServlet;

    /** The create synonym servlet. */
    private String createSynonymServlet;
    
    /** The reload solr server servlet. */
    private String reloadSolrServerServlet;

    /** The get stop word results servlet. */
    private String getStopWordResultsServlet;

    /** The create stop word servlet. */
    private String createStopWordServlet;

    /** The remove selected stop word servlet. */
    private String removeSelectedStopWordServlet;
    
    /** The solr url. */
    private String solrUrl;
    
    /** The slave solr url. */
    private String slaveSolrUrl;

    /** The content collection name. */
    private String contentCollectionName;

    /** The asset collection name. */
    private String assetCollectionName;
   
    /** The base page path. */
    private String basePagePath;

    /** The asset path. */
    private String assetPath;
    
    /** The type ahead servlet. */
    private String typeAheadServlet;
    
    /** The popular searched collection. */
    private String popularSearchedCollection;
    
    /** The popular searched servlet. */
    private String popularSearchedServlet;

    /**  To Refresh Solr Collection *. */
    private String refreshSolrCollection;
    
    /** The indexes to be queried. */
    private String[] indexesToBeQueried;
    
	/** The allow solr indexing. */
	private String allowSolrIndexing;
    
    /** The allow product replication. */
    private String allowProductReplication;
    
    /** The catalog structure node type. */
    private String catalogStructureNodeType;
    
    /** The admin page path. */
    private String adminPagePath;
    
    /** The product type generic list path. */
    private String productTypeGenericListPath;
    
    /** The speciesReactivityStatus. */
	private String speciesReactivityStatus;
	
	/** The applicationStatus. */
	private String applicationStatus;

    /** The products solr query result limit. */
    private int productsSolrQueryResultLimit;
    
    /** The batch size of pdf to index. */
    private int batchSizeOfPdfToIndex;
    
    /** The concatenated query fields. */
    private String concatenatedQueryFields;
    
    /** The concatenated query fields. */
    private String concatenatedFieldSplChars;
    
    /** Is GB same as EU */
    private String isEUSameAsGB;
    

    /** The Regulatory Status ivd translation regions. */
    private String ivdTranslationRegions;

    /** The Regulatory Status translation list. */
    private String ivdTranslationRegionsList;
    
    /** The concatenated excluded query fields. */
    private String concatenatedQueryExcludedFields;
    
    /** ICM Tools Service Path */
    private String icmToolsServicePath;
    
    /** ICM Tools searchPayloadPath Path */
    private String searchPayloadPath;
    
    /** Is Edismax */
    private String isEdismax;
    
	private int cellTypeQueryLimit;
	
	/** splCharsToReplaceSearchTerm */
    private String splCharsToReplaceSearchTerm;

	private String countryToRegionAndLanguge;
	
	private String combinedFieldsForPDFs;
	
	private String combinedFieldsForVideos;

    private String showCiteabWidget;

	/**
     * Activate.
     *
     * @param config the config
     */
    @Activate
    @Modified
    protected final void activate(Configuration config) {
        this.synonymResults = config.synonymResults();
        this.deleteSynonym = config.deleteSynonym();
        this.createSynonym = config.createSynonym();
        this.stopWordResults = config.stopWordResults();
        this.createStopWord = config.createStopWord();
        this.removeSelectedStopWord = config.removeSelectedStopWord();
        this.synonymResultsServlet = config.synonymResultsServlet();
        this.deleteSynonymServlet = config.deleteSynonymServlet();
        this.createSynonymServlet = config.createSynonymServlet();
        this.reloadSolrServerServlet = config.reloadSolrServerServlet();
        this.getStopWordResultsServlet = config.getStopWordResultsServlet();
        this.createStopWordServlet = config.createStopWordServlet();
        this.removeSelectedStopWordServlet = config.removeSelectedStopWordServlet();
        this.solrUrl = config.solrUrl();
        this.slaveSolrUrl = config.slaveSolrUrl();
        this.contentCollectionName = config.contentPageCollectionName();
        this.assetCollectionName = config.assetCollectionName();
        this.basePagePath = config.basePagePath();
        this.assetPath = config.baseAssetPath();
        this.typeAheadServlet = config.typeAheadServletPath();
        this.popularSearchedCollection = config.popularSearchedCollectionName();
        this.popularSearchedServlet = config.popularSearchedServletPath();
        this.refreshSolrCollection = config.refreshSolrCollection();
        this.indexesToBeQueried = config.indexesToBeQueried();
        this.allowSolrIndexing = config.allowSolrIndexing();
        this.allowProductReplication = config.allowProductReplication();
        this.catalogStructureNodeType = config.catalogStructureNodeType();
        this.adminPagePath = config.adminPagePath();
        this.productTypeGenericListPath = config.productTypeGenericListPath();
    	this.speciesReactivityStatus = config.speciesReactivityStatus();
		this.applicationStatus = config.applicationStatus();
        this.productsSolrQueryResultLimit = Integer.valueOf(config.productsSolrQueryResultLimit());
        this.batchSizeOfPdfToIndex = Integer.valueOf(config.batchSizeOfPdfToIndex());
        this.concatenatedQueryFields = config.concatenatedQueryFields();
        this.concatenatedFieldSplChars = config.concatenatedFieldSplChars();
        this.isEUSameAsGB = config.isEUSameAsGB();
        this.ivdTranslationRegions = config.ivdTranslationRegions();
        this.ivdTranslationRegionsList = config.ivdTranslationRegionsList();
        this.concatenatedQueryExcludedFields = config.concatenatedQueryExcludedFields();
        this.icmToolsServicePath = config.getIcmToolsServicePath();
        this.searchPayloadPath = config.getSearchPayloadPath();
        this.isEdismax = config.isEdismax();
        this.cellTypeQueryLimit = config.cellTypeQueryLimit();
        this.splCharsToReplaceSearchTerm = config.splCharsToReplaceSearchTerm();
        this.countryToRegionAndLanguge = config.countryToRegionAndLanguge();
        this.combinedFieldsForPDFs = config.combinedFieldsForPDFs();
        this.combinedFieldsForVideos = config.combinedFieldsForVideos();
        this.showCiteabWidget = config.showCiteabWidget();
    }


    /**
     * Deactivate.
     */
    @Deactivate
    protected void deactivate() {
        // DoNothing
    }


    /**
     * Gets the synonym results.
     *
     * @return the synonym results
     */
    @Override
    public String getSynonymResults() {
        return synonymResults;
    }

    /**
     * Gets the delete synonym.
     *
     * @return the delete synonym
     */
    @Override
    public String getDeleteSynonym() {
        return deleteSynonym;
    }

    /**
     * Gets the creates the synonym.
     *
     * @return the creates the synonym
     */
    @Override
    public String getCreateSynonym() {
        return createSynonym;
    }

    /**
     * Gets the stop word results.
     *
     * @return the stop word results
     */
    @Override
    public String getStopWordResults() {
        return stopWordResults;
    }

    /**
     * Gets the creates the stop word.
     *
     * @return the creates the stop word
     */
    @Override
    public String getCreateStopWord() {
        return createStopWord;
    }

    /**
     * Gets the removes the selected stop word.
     *
     * @return the removes the selected stop word
     */
    @Override
    public String getRemoveSelectedStopWord() {
        return removeSelectedStopWord;
    }

    /**
     * Gets the delete synonym servlet.
     *
     * @return the delete synonym servlet
     */
    @Override
    public String getDeleteSynonymServlet() {
        return deleteSynonymServlet;
    }

    /**
     * Gets the creates the synonym servlet.
     *
     * @return the creates the synonym servlet
     */
    @Override
    public String getCreateSynonymServlet() {
        return createSynonymServlet;
    }

    /**
	 * @return the reloadSolrServerServlet
	 */
    @Override
	public String getReloadSolrServerServlet() {
		return reloadSolrServerServlet;
	}

	/**
     * Gets the gets the stop word results servlet.
     *
     * @return the gets the stop word results servlet
     */
    @Override
    public String getGetStopWordResultsServlet() {
        return getStopWordResultsServlet;
    }

    /**
     * Gets the creates the stop word servlet.
     *
     * @return the creates the stop word servlet
     */
    @Override
    public String getCreateStopWordServlet() {
        return createStopWordServlet;
    }

    /**
     * Gets the removes the selected stop word servlet.
     *
     * @return the removes the selected stop word servlet
     */
    @Override
    public String getRemoveSelectedStopWordServlet() {
        return removeSelectedStopWordServlet;
    }

    /**
     * Gets the synonym results servlet.
     *
     * @return the synonym results servlet
     */
    @Override
    public String getSynonymResultsServlet() {
        return synonymResultsServlet;
    }
    
    /**
     * Gets the solr url.
     *
     * @return the solr url
     */
    @Override
	public String getSolrUrl() {
		return solrUrl;
	}

	/**
	 * @return the slaveSolrUrl
	 */
    @Override
	public String getSlaveSolrUrl() {
		return slaveSolrUrl;
	}

	/**
	 * Gets the content page collection name.
	 *
	 * @return the content page collection name
	 */
	@Override
	public String getContentPageCollectionName() {
		return contentCollectionName;
	}

	/**
	 * Gets the dam collection name.
	 *
	 * @return the dam collection name
	 */
	@Override
	public String getDamCollectionName() {
		return assetCollectionName;
	}

	/**
	 * Gets the base page path.
	 *
	 * @return the base page path
	 */
	@Override
	public String getBasePagePath() {
		return basePagePath;
	}

	/**
	 * Gets the asset path.
	 *
	 * @return the asset path
	 */
	@Override
	public String getAssetPath() {
		return assetPath;
	}
	
	/**
	 * Gets the type ahead servlet path.
	 *
	 * @return the type ahead servlet path
	 */
	@Override
	public String getTypeAheadServletPath() {
		return typeAheadServlet;
	}
	
	/**
	 * Gets the popular searched servlet path.
	 *
	 * @return the popular searched servlet path
	 */
	@Override
	public String getPopularSearchedCollectionName() {
		return popularSearchedCollection;
	}
	
	/**
	 * Gets the popular searched servlet path.
	 *
	 * @return the popular searched servlet path
	 */
	public String getPopularSearchedServletPath() {
		return popularSearchedServlet;
	}

    /**
     * Get the Refresh Solr Collection.
     *
     * @return the solr collection
     */
    @Override
    public String getRefreshSolrCollection() {
        return refreshSolrCollection;
    }

    /**
     * Gets the indexes to be queried.
     *
     * @return the indexes to be queried
     */
    @Override
	public String[] getIndexesToBeQueried() {
		return Arrays.copyOf(indexesToBeQueried, indexesToBeQueried.length);
	}
    
    /**
     * Gets the allow solr indexing.
     *
     * @return the allow solr indexing
     */
    @Override
    public String getAllowSolrIndexing() {
		return allowSolrIndexing;
	}

    /**
     * Gets the allow product replication.
     *
     * @return the allow product replication
     */
    @Override
	public String getAllowProductReplication() {
		return allowProductReplication;
	}

    /**
     * Gets the catalog structure node type.
     *
     * @return the catalog structure node type
     */
    @Override
    public String getCatalogStructureNodeType() {
		return catalogStructureNodeType;
	}

    /**
     * Gets the admin page path.
     *
     * @return the admin page path
     */
    @Override
	public String getAdminPagePath() {
		return adminPagePath;
	}
    
    /**
	 * Gets the Product type generic list
	 * 
	 * @return path of the generic list
	 */
	@Override
	public String getProductTypesGenericListEndpoint() {
		return productTypeGenericListPath;
	}
	
	/**
	 * Gets the products solr query result limit.
	 *
	 * @return the products solr query result limit
	 */
	@Override
	public int getProductsSolrQueryResultLimit() {
		return productsSolrQueryResultLimit;
	}
	
	/**
	 * Gets the batch size of pdf to index.
	 *
	 * @return the batch size of pdf to index
	 */
	@Override
	public int getBatchSizeOfPdfToIndex() {
		return batchSizeOfPdfToIndex;
	}
	

	 /**
	  * Gets the speciesReactivityStatus
	  * 
	  * @return speciesReactivityStatus string
	  */
	@Override
	public String speciesReactivityStatus() {
		return speciesReactivityStatus;
	}
	
	 /**
	  * Gets the applicationStatus
	  * 
	  * @return applicationStatus string
	  */
	@Override
	public String applicationStatus() {
		return applicationStatus;
	}
	
	/**
     * Gets the indexes to be combined.
     *
     * @return the indexes to be combined
     */
    @Override
	public String getConcatenatedQueryFields() {
		return concatenatedQueryFields;
	}
    
    /**
     * getConcatenatedFieldSplChars
     *
     * @return the indexes to be combined
     */
    @Override
	public String getConcatenatedFieldSplChars() {
		return concatenatedFieldSplChars;
	}
    
    /**
     * is GB same as EU
     *
     * @return String
     */
    @Override
	public String isEUSameAsGB() {
		return isEUSameAsGB;
	}
    
    /**
     * getIvdRegions
     *
     * @return Regions
     */
    @Override
    public String getIvdTranslationRegions() {
		return ivdTranslationRegions;
	}

    /**
     * getIvdGenericList
     *
     * @return String
     */
    @Override
    public String getivdTranslationRegionsList() {
		return ivdTranslationRegionsList;
	}
    
    /**
     * getConcatenatedExcluded fields
     *
     * @return the indexes to be combined
     */
    @Override
	public String getConcatenatedQueryExcludedFields() {
		return concatenatedQueryExcludedFields;
	}
    
    /**
     * getIvdRegions
     *
     * @return Regions
     */
    @Override
    public String getIcmToolsServicePath() {
		return icmToolsServicePath;
	}
    
    /**
     * getIvdRegions
     *
     * @return Regions
     */
    @Override
    public String getSearchPayloadPath() {
		return searchPayloadPath;
	}
    /** isEdismax
     *
     * @return true or false
     */
    @Override
    public String isEdismax() {
		return isEdismax;
	}
    

    @Override
	public int cellTypeQueryLimit() {
		return cellTypeQueryLimit;
	}
    

    /** getSplCharsToReplaceSearchTerm
    *
    * @return String
    */
   @Override
   public String getSplCharsToReplaceSearchTerm() {
		return splCharsToReplaceSearchTerm;
	}


    @Override
    public String countryToRegionAndLanguge() {
    	return countryToRegionAndLanguge;
    }

    @Override
    public String getCombinedFieldsForPDFs() {
    	return combinedFieldsForPDFs;
    }

    @Override
    public String showCiteabWidget() {
        return showCiteabWidget;
    }
    
    @Override
	public String getCombinedFieldsForVideos() {
		return combinedFieldsForVideos;
	}

    /**
     * The Interface Configuration.
     */
    @ObjectClassDefinition(name = "BDB Solr Configuration")
    public @interface Configuration {

        /**
         * Synonym results.
         *
         * @return the string
         */
        @AttributeDefinition(name = "synonymResults", description = "Synonym Results", type = AttributeType.STRING)
        public String synonymResults() default "/solr/bdbio-{{country}}/admin/file?wt=json&_=1597818284848&file=_schema_analysis_synonyms_{{language}}.json&contentType=application%2Fjson%3Bcharset%3Dutf-8";

        /**
         * Delete synonym.
         *
         * @return the string
         */
        @AttributeDefinition(name = "deleteSynonym", description = "Delete Synonym", type = AttributeType.STRING)
        public String deleteSynonym() default "/solr/bdbio-{{country}}/schema/analysis/synonyms/{{language}}/{{word}}";

        /**
         * Creates the synonym.
         *
         * @return the string
         */
        @AttributeDefinition(name = "createSynonym", description = "Create Synonym", type = AttributeType.STRING)
        public String createSynonym() default "/solr/bdbio-{{country}}/schema/analysis/synonyms/{{language}}";

        /**
         * Stop word results.
         *
         * @return the string
         */
        @AttributeDefinition(name = "stopWordResults", description = "Stop Word Results", type = AttributeType.STRING)
        public String stopWordResults() default "/solr/bdbio-{{country}}/admin/file?wt=json&_=1596539572415&file=_schema_analysis_stopwords_{{language}}.json&contentType=application%2Fjson%3Bcharset%3Dutf-8";

        /**
         * Creates the stop word.
         *
         * @return the string
         */
        @AttributeDefinition(name = "createStopWord", description = "Create StopWord", type = AttributeType.STRING)
        public String createStopWord() default "/solr/bdbio-{{country}}/schema/analysis/stopwords/{{language}}";

        /**
         * Removes the selected stop word.
         *
         * @return the string
         */
        @AttributeDefinition(name = "removeSelectedStopWord", description = "Remove Selected StopWord", type = AttributeType.STRING)
        public String removeSelectedStopWord() default "/solr/bdbio-{{country}}/schema/analysis/stopwords/{{language}}/{{word}}";

        /**
         * Bdb local domain.
         *
         * @return the string
         */
        @AttributeDefinition(name = "bdbLocalDomain", description = "BDBLocalDomain", type = AttributeType.STRING)
        public String bdbLocalDomain() default "/solr/bd/schema/analysis/stopwords/{{language}}/{{word}}";

        /**
         * Synonym results servlet.
         *
         * @return the string
         */
        @AttributeDefinition(name = "synonymResultsServlet", description = "Synonyms Results Servlet", type = AttributeType.STRING)
        public String synonymResultsServlet() default "/content/bdb/paths/get-search-endpoints?param=getSynonymResults&lang={{language}}";

        /**
         * Delete synonym servlet.
         *
         * @return the string
         */
        @AttributeDefinition(name = "deleteSynonymServlet", description = "Delete Synonym Servlet", type = AttributeType.STRING)
        public String deleteSynonymServlet() default "/content/bdb/paths/get-search-endpoints?param=deleteSynonym&deleteWord={{word}}&lang={{language}}";

        /**
         * Creates the synonym servlet.
         *
         * @return the string
         */
        @AttributeDefinition(name = "createSynonymServlet", description = "Create Synonym Servlet", type = AttributeType.STRING)
        public String createSynonymServlet() default "/content/bdb/paths/get-search-endpoints?param=createSynonym&lang={{language}}";
        
        /**
         * Reloads master and slave solr server.
         *
         * @return the string
         */
        @AttributeDefinition(name = "reloadSolrServerServlet", description = "Reload Solr Server Servlet", type = AttributeType.STRING)
        public String reloadSolrServerServlet() default "/content/bdb/paths/get-search-endpoints?param=reloadSolrServer";

        /**
         * Gets the stop word results servlet.
         *
         * @return the stop word results servlet
         */
        @AttributeDefinition(name = "getStopWordResultsServlet", description = "Get StopWord Results Servlet", type = AttributeType.STRING)
        public String getStopWordResultsServlet() default "/content/bdb/paths/get-search-endpoints?param=getStopWordResults&lang={{language}}";

        /**
         * Creates the stop word servlet.
         *
         * @return the string
         */
        @AttributeDefinition(name = "createStopWordServlet", description = "Create Stop Word Servlet", type = AttributeType.STRING)
        public String createStopWordServlet() default "/content/bdb/paths/get-search-endpoints?param=createStopWord&lang={{language}}";

        /**
         * Removes the selected stop word servlet.
         *
         * @return the string
         */
        @AttributeDefinition(name = "removeSelectedStopWordServlet", description = "Remove Selected Stopword Servlet", type = AttributeType.STRING)
        public String removeSelectedStopWordServlet() default "/content/bdb/paths/get-search-endpoints?param=removeSelectedStopWord&removeWord={{word}}&lang={{language}}";

        /**
         * Solr url.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Solr Url", defaultValue ="", description = "Loadbalancer Solr Url")
        String solrUrl();
        
        /**
         * Slave Solr Url.
         *
         * @return the string[]
         */
        @AttributeDefinition(name = "Slave Solr Url", defaultValue ="", description = "Slave Solr Url")
        String slaveSolrUrl();

        /**
         * Content page collection name.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Content Page Collection name", defaultValue ="", description = "Content Page Collection name")
        String contentPageCollectionName();

        /**
         * Asset collection name.
         *
         * @return the string
         */
        @AttributeDefinition(name = "DAM Asset Collection Name", defaultValue ="", description = "DAM Asset Collection Name")
        String assetCollectionName();

    	/**
	     * Base page path.
	     *
	     * @return the string
	     */
	    @AttributeDefinition(name = "Content page path", defaultValue ="/content/bdb", description = "Content page path from where solr has to index the pages")
        String basePagePath();
    	
    	/**
	     * Base asset path.
	     *
	     * @return the string
	     */
	    @AttributeDefinition(name = "Asset Base path", defaultValue ="/content/dam/bdb", description = "Assets Root path from where solr has to index the assets")
        String baseAssetPath();
	    
	    /**
    	 * Type ahead servlet path.
    	 *
    	 * @return the string
    	 */
    	@AttributeDefinition(name = "Typeahead Servlet Path", defaultValue ="/content/bdb/paths/type-ahead-suggestion", description = "The typeahead Servlet Path")
        String typeAheadServletPath();
    	
    	/**
	     * Popular searched servlet path.
	     *
	     * @return the string
	     */
	    @AttributeDefinition(name = "Popular Searched Collection Name", defaultValue ="bd-popular-search", description = "The Popular Searched Collection Name")
    	String popularSearchedCollectionName();
	    
    	/**
	     * Popular searched servlet path.
	     *
	     * @return the string
	     */
	    @AttributeDefinition(name = "Popular Searched Servlet Path", defaultValue ="/content/bdb/paths/popular-search.json", description = "The Popular Searched Servlet Path")
	    String popularSearchedServletPath();


        /**
         * To Referesh Solr collection.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Refresh Solr Collection", defaultValue ="/solr/admin/cores?action=RELOAD&core=", description = "The refersh the solr Collection")
        String refreshSolrCollection();

        /**
         * Indexes to be queried.
         *
         * @return the string[]
         */
        @AttributeDefinition(name = "Indexes to be queried", defaultValue ="name", description = "Indexes to be queried for Search Results")
        String[] indexesToBeQueried();

        /**
         * Allow solr indexing.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Toggle Solr Indexing", defaultValue = "true", description = "Toggle solr indexing in catalog structure")
        String allowSolrIndexing();
        
        /**
         * Allow product replication.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Toggle Product Replication", defaultValue = "true", description = "Toggle product replication in solr indexing")
        String allowProductReplication();
        
        /**
         * Catalog structure node type.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Catalog Structure Node Type", defaultValue = CommonConstants.OAK_UNSTRUCTURED, description = "Catalog Structure Node Type")
        String catalogStructureNodeType();
        
        /**
         * Admin page path.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Admin Page Path", defaultValue = "/content/bdb/pdp-admin-page", description = "Admin Page Path")
        String adminPagePath();
        
        /**
         * product type generic list page path.
         *
         * @return the string
         */
        @AttributeDefinition(name = "productTypeGenericListPath", defaultValue = "/etc/acs-commons/lists/bdb/product_types_list", description = "Product type generic list page path")
        String productTypeGenericListPath();
        
        /**
		 * Secies Reactivity Status Attribute.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "speciesReactivityStatus", description = "Secies Reactivity Status Attribute", type = AttributeType.STRING)
		public String speciesReactivityStatus() default "LD";
		
		/**
		 * ApplicationStatus Attribute.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "applicationStatus", description = "ApplicationStatus Attribute", type = AttributeType.STRING)
		public String applicationStatus() default "NR";

        /**
         * Products solr query result limit.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Row count which should be returned from solr query", description = "Row count which should be returned from solr query", type = AttributeType.STRING)
		public String productsSolrQueryResultLimit() default "32000";
        
        /**
         * Batch size of pdf to index.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Batch size in which indexing should be triggered to solr", description = "Batch size in which indexing should be triggered to solr", type = AttributeType.STRING)
        public String batchSizeOfPdfToIndex() default "500";
        
        /**
         * concatenatedQueryFields.
         *
         * @return the string[]
         */
        @AttributeDefinition(name = "concatenatedQueryFields",
        		defaultValue ="materialNumber_t,name_t,brand_t,specificity_t,regulatoryStatus_t,speciesReactivity_t,isoType_t,category_t,size_t,dyeName_t,applicationName_t,productType_t,labelDescription_t,alternativeName_t,tdsCloneName_t",
        		description = "Indexes to be combined")
        String concatenatedQueryFields();
        
        /**
         * concatenatedQueryFields.
         *
         * @return the string[]
         */
        @AttributeDefinition(name = "concatenatedFieldSplChars", defaultValue ="(,),%2F,%22,%3B", description = "characters to be replaced with space")
        String concatenatedFieldSplChars();
        
        /**
         * is GB same as EU
         *
         * @return the string
         */
        @AttributeDefinition(name = "isEUSameAsGB", defaultValue ="true", description = "If true, GB documents are indexed in EU")
        String isEUSameAsGB();
        
        /**
         * Regulatory status regions.
         *
         * @return the string[]
         */
        @AttributeDefinition(name = "ivdTranslationRegions", defaultValue ="jp", description = "Regulatory Status Translation Regions.(mupltiple regions should be comma seperated)")
        String ivdTranslationRegions();

        /**
         * Regulatory Status generic list path.
         *
         * @return the string
         */
        @AttributeDefinition(name = "ivdTranslationRegionsList", defaultValue ="/etc/acs-commons/lists/bdb/Regulatory-Status-translation/status_attributes", description = "Regulatory Status Translation Generic List Path.")
        String ivdTranslationRegionsList();
        
        /**
         * concatenatedQueryExcludedFields.
         *
         * @return the string[]
         */
        @AttributeDefinition(name = "concatenatedQueryExcludedFields", defaultValue ="", description = "Indexes to be excluded for concatenated Query fields")
        String concatenatedQueryExcludedFields();
        
        /**
         * Removes the selected stop word servlet.
         *
         * @return the string
         */
        @AttributeDefinition(name = "getIcmToolsServicePath", description = "Icm Tools Servlet Path", type = AttributeType.STRING)
        public String getIcmToolsServicePath() default "/content/bdb/paths/icm-tools-data";
        
        /**
         * Removes the selected stop word servlet.
         *
         * @return the string
         */
        @AttributeDefinition(name = "getSearchPayloadPath", description = "Search Payload Servlet Path", type = AttributeType.STRING)
        public String getSearchPayloadPath() default "/content/bdb/paths/fetch-data.json";
        
        /** is Edismax
         *
         * @return the string
         */
        @AttributeDefinition(name = "isEdismax", defaultValue ="true", description = "If true, Edismax Query parser will be invoked for search")
        String isEdismax();
        
        /**
         * ccuapitype
         *
         * @return the string
         */
        @AttributeDefinition(name = "cellTypeQueryLimit", description = "Cell Type Query Limit", type = AttributeType.INTEGER)
        public int cellTypeQueryLimit() default 32000;
        
       /** splCharsToReplaceSearchTerm
        *
        * @return the string
        */
       @AttributeDefinition(name = "splCharsToReplaceSearchTerm", defaultValue ="", description = "Special characters to be replaced in search term (separated by comma)")
       String splCharsToReplaceSearchTerm();

        /* * is Edismax
        *
        * @return the string
        */
       @AttributeDefinition(name = "countryToRegionAndLanguge", defaultValue ="true", description = "Country,region & language mapping for ICM Tools")
       String countryToRegionAndLanguge() default "/etc/acs-commons/lists/bdb/Country-To-Region-And-Language-Mapping/countryToReginAndLanguageMapping";
    
       /** combinedFieldsForPDFs
       *
       * @return the string
       */
      @AttributeDefinition(name = "combinedFieldsForPDFs", defaultValue ="name_t,dctitle_t,dcDescription_t,docTitle_t,docDesc_t,docKeywords_t,productName_t", description = "Combined Field indexes for PDFs")
      String combinedFieldsForPDFs();
      
      /** combinedFieldsForVideos
      *
      * @return the string
      */
     @AttributeDefinition(name = "combinedFieldsForVideos", defaultValue ="name_t,dctitle_t,dcDescription_t,docType_t", description = "Combined Field indexes for Videos")
     String combinedFieldsForVideos();

      @AttributeDefinition(name = "showCiteabWidget", defaultValue = "True", description = "Citeab widget toggle" , type = AttributeType.STRING)
      String showCiteabWidget();


    }
}