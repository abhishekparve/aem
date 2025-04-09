package com.bdb.aem.core.util;

import org.apache.jackrabbit.vault.packaging.JcrPackage;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.replication.ReplicationStatus;
import com.day.cq.tagging.TagConstants;
import com.day.cq.wcm.api.NameConstants;

/**
 * The Class SolrSearchConstants
 */
public final class SolrSearchConstants {

    private SolrSearchConstants() {
    }
    
    public static final String CATALOG_CONTENT_HIERARCHY_PATH = "/content/commerce/products/bdb";
    
    public static final String CONTENT_TYPE = "application/json";
    public static final String UTF_ENCODING = "UTF-8";
    public static final String CONSTANT_COLON = ":";
    public static final String CONSTANT_EQUALS = "=";
    public static final String CONSTANT_COMMA = ",";
    public static final String CONSTANT_SLASH = "/";
    public static final String CONSTANT_QUOTES = "\"";
    public static final String CONSTANT_BRACKET_OPEN = "(";
    public static final String CONSTANT_BRACKET_CLOSE = ")";
    public static final String CONSTANT_SQUARE_BRACKET_OPEN = "[";
    public static final String CONSTANT_SQUARE_BRACKET_CLOSE = "]";
    public static final String CONSTANT_SPACE = " ";
    public static final String JCR_CONTENT_NODE_NAME = JcrConstants.JCR_CONTENT;
    public static final String CQ_PAGE_CONTENT = "cq:PageContent";
    public static final String CQ_TEMPLATE = NameConstants.NN_TEMPLATE;
    public static final String SOLR_QUERY_WILDCARD = "*";
    public static final String SOLR_QUERY_Q = "q";
    public static final String SOLR_QUERY_FQ = "fq";
    public static final String METADATA = "metadata";
    public static final String NAV_TITLE = "navTitle";
    public static final String SUBTITLE = "subtitle";
    public static final String PAGE_TITLE = "pageTitle";
    public static final String CONSTANT_NOSPACE = "";
    public static final String THUMBNAIL_TAG_NAME = "THUMBNAIL";
    public static final String THUMBNAIL_IMAGE = "thumbnailImage";
    public static final String SOLR_UNINDEXABLE = "solrUnindexable";
    public static final String SOLR_CHILD_PAGES_UNINDEXABLE = "solrChildPagesUnindexable";
    public static final String HP_NODE_PATH = "hpNodePath";
    public static final String VARIANT_HP_NODE_PATH = "variantHpNodePath";
    public static final String DAM_GLOBAL_PATH = "damGlobalPath";
    public static final String DOC_PART_IDS = "docPartIDs";
    public static final String MEDIAS = "medias";
    public static final String PRIMARY_SUPER_CATEGORY = "primarySuperCategory";
    public static final String CQ_PAGE = NameConstants.NT_PAGE;

    public static final String TEXT_PARAM = "text";
    public static final String FILTERS_PARAM = "filters";

    public static final String SOLR_PROTOCOL = "solr.protocol";
    public static final String SOLR_SERVER_NAME = "solr.server.name";
    public static final String SOLR_SERVER_PORT = "solr.server.port";
    public static final String SOLR_CORE_NAME = "solr.core.name";
    public static final String SOLR_PAGEPATH = "solr.core.pagepath";
    public static final String SOLR_IGNORE_INDEX_PAGEPATH = "solr.ignore.pagepath";
    public static final String SOLR_INDEX_LISTENER_ENABLED = "solr.listener.enabled";
    public static final String SOLR_INDEX_PROPERTIES = "solr.ignore.properties";
    public static final String BDBIO_HYPHEN = "bdbio-";

    public static final String JCR_PRIMARY_TYPE_NT_UNSTRUCTURED = JcrConstants.NT_UNSTRUCTURED;
    public static final String JCR_PRIMARY_TYPE_DAM_ASSETS = DamConstants.NT_DAM_ASSET;
    public static final String JCR_CONTENT_METADATA_DC_FORMAT = "jcr:content/metadata/dc:format";
    public static final String JCR_CONTENT = JcrConstants.JCR_CONTENT;
    public static final String SLING_RESOURCETYPE = JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY;

    public static final String QUERY_BUILDER_PATH = "path";
    public static final String QUERY_BUILDER_TYPE = "type";
    public static final String QUERY_BUILDER_NODENAME = "nodename";
    public static final String QUERY_BUILDER_OFFSET = "p.offset";
    public static final String QUERY_BUILDER_LIMIT = "p.limit";
    public static final String QUERY_BUILDER_OFFSET_DEFAULT = "0";
    public static final String QUERY_BUILDER_LIMIT_DEFAULT = "10000";
    public static final String QUERY_BUILDER_PROPERTY_ONE = "1_property";
    public static final String QUERY_BUILDER_PROPERTY = "property";
    public static final String QUERY_BUILDER_PROPERTY_VALUE = "property.value";
    public static final String QUERY_BUILDER_PROPERTY_OPERATION = "property.operation";
    public static final String QUERY_BUILDER_TAGSEARCH = "tagsearch";
    public static final String QUERY_BUILDER_1_PROPERTY = "1_property";
    public static final String QUERY_BUILDER_1_PROPERTY_VALUE = "1_property.value";
    public static final String QUERY_BUILDER_2_PROPERTY = "2_property";
    public static final String QUERY_BUILDER_2_PROPERTY_VALUE = "2_property.value";
    public static final String QUERY_BUILDER_2_PROPERTY_OPERATION = "2_property.operation";
    public static final String QUERY_BUILDER_1_PROPERTY_OPERATION = "1_property.operation";
    

    public static final String TEST_USER = "testUser";
    public static final String PROPERTY_EXCLUDE_FROM_INDEX = "notIndexable";
    public static final String PROPERTY_EXCLUDE_FROM_INDEX_VALUE = "true";
    public static final String PAGE_PROPERTY_SOLR_INDEX = "solrindex";
    public static final String PAGE_PROPERTY_SOLR_INDEX_VALUE = "need index";

    public static final String SOLRDOC_FIELD_ID = "id";
    public static final String SOLRDOC_FIELD_URL = "url";
    public static final String SOLRDOC_FIELD_TITLE = "title";
    public static final String SOLRDOC_FIELD_DESC = "description";
    public static final String SOLRDOC_FIELD_PUBLISHDATE = "publishDate";
    public static final String SOLRDOC_FIELD_AUTHOR = "author";
    public static final String SOLRDOC_FIELD_LMODIFIED = "lastModified";
    public static final String SOLRDOC_FIELD_CONTENTTYPE = "contentType";
    public static final String SOLRDOC_FIELD_ASSETYPE = "assetType";
    public static final String SOLRDOC_FIELD_BODY = "body";
    public static final String SOLRDOC_FIELD_TAGS = "tags";
    public static final String SOLRDOC_FIELD_EVENT = "eventCategory";
    public static final String SOLRDOC_FIELD_CHILDDOCS = "childDocs";
    public static final String COUNTRY = "country";
    public static final String LANGUAGE = "language";
    public static final String SOLRDOC_FIELD_DCTITLE = "dctitle";

    public static final String JCR_PUBLISHDATE = "publishdate";
    public static final String RE_ASSET = "asset";
    public static final String CQ_TAGS = TagConstants.PN_TAGS;
    public static final String EVENT_DATE = "eventDate";
    public static final String EVENT_CATEGORY = "eventCategory";
    public static final String AUCTION_DATE = "auctionDate";
    public static final String HTML_EXTENSION = ".html";
    public static final String JCR_TITLE = JcrConstants.JCR_TITLE;
    public static final String JCR_CREATED = JcrConstants.JCR_CREATED;
    public static final String JCR_DESC = JcrConstants.JCR_DESCRIPTION;
    public static final String DAM_ASSETS = "dam:Assets";
    public static final String PAGE = "page";
    public static final String SOLRDOC_FIELD_YEAR_FORMAT = "yyyy";
    public static final String SOLRDOC_FIELD_YEAR_FORMAT_TIME_RANGE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMAT_FOR_EVENTS = "YYYY-MM-dd";
    public static final String SIMPLEDATEFORMAT_TIMESTAMP = "YYYY-MM-dd'T'hh:mm:ss";
    public static final String CONSTANT_FQ_TO = " TO ";
    public static final String ACTIVATE = "Activate";
    public static final String DEACTIVATE = "Deactivate";
    public static final String CQ_LAST_REPLICATION_ACTION = ReplicationStatus.NODE_PROPERTY_LAST_REPLICATION_ACTION;
    public static final String IS_VARIANT = "isVariant";
    public static final String IS_BASE_PRODUCT = "isBaseProduct";
    public static final String LANGUAGE_MASTERS_PAGE_PATH = "/content/bdb/language-masters";
    public static final String DOC_SKU = "docSKU";
    public static final String DOC_TITLE = "docTitle";
    public static final String BDB_ASSET_DOC_TYPE = "bdbAssetDocType";
    public static final String DOC_DESC = "docDesc";
    public static final String SOLR_FIELD_PDFX_DOC_TYPE = "pdfxDocType";
    public static final String SCIENTIFIC_RES_ASSET_DOC_TYPE = "scientificResDocType";
    public static final String PDFX_DOC_TYPE = "pdfx:docType";
    public static final String PRODUCT_PDFX_DOC_TYPE = "pdfx:DocType";
    public static final String DC_DESCRIPTION = DamConstants.DC_DESCRIPTION;
    public static final String VIDEO_TITLE = "videoTitle";
    public static final String VIDEO_TYPE = "videoType";
    public static final String PDF = "pdf";
    public static final String SOLR_FIELD_DC_DESCRIPTION = "dcDescription";
    public static final String CONSTANT_CQ = "cq";
    public static final String CQ_LAST_REPLICATED = ReplicationStatus.NODE_PROPERTY_LAST_REPLICATED;
    public static final String SOLR_FIELD_LAST_REPLICATED = "cqLastReplicated";
    
    public static final String PPTX_FILE_FORMAT = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String PPT_FILE_FORMAT = "application/vnd.ms-powerpoint";
    public static final String DOCX_FILE_FORMAT = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String DOC_FILE_FORMAT =  "application/msword";
    public static final String XLSX_FILE_FORMAT = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String XLS_FILE_FORMAT = "application/vnd.ms-excel";
    public static final String PDF_FILE_FORMAT = "application/pdf";
    public static final String ZIP_FILE_FORMAT = JcrPackage.MIME_TYPE;
    public static final String CSV_FILE_FORMAT = "text/csv";
    public static final String RTF_FILE_FORMAT = "application/rtf";
    public static final String TEXT_FILE_FORMAT = "text/plain";
    
    public static final String SELECT_HANDLER = "/select";
    public static final String SELECT = "select";
    public static final String SELECT_QUERY_PARAM = "q";
    
    public static final String SUGGEST_HANDLER = "/suggest";
    public static final String SUGGEST_QUERY_PARAM = "suggest.q";
    public static final String SUGGEST = "suggest";
    public static final String SUGGEST_BUILD = "suggest.build";
    public static final String SUGGEST_DICTIONARY = "suggest.dictionary";
    public static final String SUGGEST_CFQ = "suggest.cfq";
    
    public static final String MPG_CATEGORY = "mpgCategory";
    public static final String CLASSIFICATION_CATEGORY = "classificationCategory";
    
    public static final String SLASH_SOLR_SLASH = "/solr/";
	public static final String UNDERSCORE_T = "_t";
	public static final String UNDERSCORE_DT = "_dt";
	public static final String UNDERSCORE_TXT = "_txt";
	public static final String UNDERSCORE_IS = "_is";
	
	public static final String TEXT = "text";
	public static final String STRING = "string";
	public static final String UNDERSCORE_FACET_UNDERSCORE_S = "_facet_s";
	public static final String UNDERSCORE_FACET_UNDERSCORE_SS = "_facet_ss";
	public static final String DATE = "date";
	public static final String TEXT_ARRAY = "textArray";
	public static final String STRING_ARRAY = "stringArray";
	public static final String INT_ARRAY = "intArray";
	public static final String APPLICATION_ARRAY = "tdsArray";
	public static final String DOCUMENT_TYPE = "docType";
	public static final String PDF_ASSET = "pdf";
	public static final String PDP_PRODUCT = "product";
	public static final String CONTENT_PAGE = "web";
	public static final String PDP_TEMPLATE = "pdpTemplate";
	public static final String COUNTRY_VARIABLE = "{{country}}";
    public static final String PRODUCT_URL = "productUrl";
    public static final String HP_NODE_PATH_T = "hpNodePath_t";
    public static final String PDF_URL_T = "url_t";
    public static final String DOCTYPE_T_PRODUCT = "docType_t:product";
    public static final String DOCTYPE_T_ASSET = "docType_t:(\"pdf\",\"video\")";
    public static final String IS_PRIMARY_VARIANT = "isPrimaryVariant";
    public static final String INFORMATION = "Information";
    public static final String DOCUMENTS = "Documents";
    public static final String PRODUCTS = "Products";
    public static final String SPECIES_ARRAY = "TagrgetSpeciesArray" ;
    public static final String MEDIA_ARRAY = "media_array";
    public static final String IMAGE_NAME = "imageName";
    public static final String IMAGE_TYPE = "imageType";
    public static final String UNDERSCORE_IMAGE_NAME_SS = "_imageName_ss";
    public static final String DOCPART_IDS_ARRAY = "docPartIds_array";
    public static final String DOCPART_IDS_SS = "docPartIds_ss";

}
