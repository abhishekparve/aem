package com.bdb.aem.core.util;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.tagging.TagConstants;
import com.day.cq.wcm.api.NameConstants;


/**
 * This class holds constant values which are referenced and used across the AEM
 * backend landscape.
 */
public final class CommonConstants {

	/** The Constant CONTENT_TYPE_URL_ENCODED. */
	public static final String CONTENT_TYPE_URL_ENCODED = PostMethod.FORM_URL_ENCODED_CONTENT_TYPE;
	/**
	 * Content type JSON.
	 */
	public static final String CONTENT_TYPE_JSON = "application/json";

	/** The Constant XLSX_MIME_TYPE. */
	public static final String XLSX_MIME_TYPE = "application/vnd.ms-excel";

	/** The Constant XLS_MIME_TYPE. */
	public static final String XLS_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	/** The Constant CACHE_CONTROL. */
	public static final String CACHE_CONTROL = "Cache-Control";

	/** The Constant NO_CACHE. */
	public static final String NO_CACHE = "no-cache";
	/**
	 * The constant WRITE_SERVICE.
	 */
	public static final String WRITE_SERVICE = "writeService";

	/**
	 * The Constant HTTP_PROTOCOL_HOST.
	 */
	public static final String HTTP_PROTOCOL_HOST = "httpProtocolHost";
	/**
	 * The Constant HTTPS_PROTOCOL_HOST.
	 */
	public static final String HTTPS_PROTOCOL_HOST = "httpsProtocolHost";
	/**
	 * The Constant SESSION_TIMEOUT.
	 */
	public static final String SESSION_TIMEOUT = "sessionTimeout";

	/** The Constant HTTP_RESPONSE_CONTENT_TYPE. */
	public static final String HTTP_RESPONSE_CONTENT_TYPE = "content-type";

	/** The Constant APPLICATION_JSON. */
	public static final String APPLICATION_JSON = "application/json";

	public static final String RECOMMENDED= "recommended";

	public static final String MONTETATE_CONFIG = "monetateConfig";

	public static final String  PRICE_CALL_CONFIG = "priceCallConfig";

	public static final String FIRST_NAME_PLACEHOLDER = "First Name";
	public static final String LAST_NAME_PLACEHOLDER = "Last Name";
	public static final String COMPANY_PLACEHOLDER = "Company/Institution";
	public static final String PHONE_NUMBER_PLACEHOLDER = "Phone Number";
	public static final String EMAIL_PLACEHOLDER = "Email Address";
	public static final String PASS_PLACEHOLDER = "Password";
	public static final String CONFIRM_PASS_PLACEHOLDER = "Confirm Password";

	public static final String EQUAL = "=";

	public static final String P = "p";

	public static final String JSON = "json";

	/** The Constant AUTHOR. */
	public static final String AUTHOR = "author";

	/** The Constant PUBLISH. */
	public static final String PUBLISH = "publish";

	/** The Constant DOT_HTML. */
	public static final String DOT_HTML = ".html";

	/** The Constant CONTENT_DAM. */
	public static final String CONTENT_DAM = "/content/dam";

	/** The Constant HTTPS. */
	public static final String HTTPS = "https:";

	/** The Constant HTTP. */
	public static final String HTTP = "http:";

	/** The Constant HTTP Methods. */
	public static final String METHOD_PATCH = "PATCH";

	/** The Constant COLON_SLASH_SLASH. */
	public static final String COLON_SLASH_SLASH = "://";
	public static final String SINGLE_SLASH = "/";
	public static final String HYPHEN = "-";
	public static final String HASH = "#";
	public static final String SINGLE_DOT = ".";
	public static final String NEWLINE = "\n";
	public static final String TAB = "\t";
	public static final String UNDERSCORE = "_";

	/** The Constant PRODUCT_VAR_HP_PATH. */
	public static final String PRODUCT_VAR_HP_PATH = "productVarHPPath";

	/** The Constant COUNTRY. */
	public static final String COUNTRY = "country";

	/** The Constant REGION. */
	public static final String REGION = "region";

	/** The Constant LANGUAGE. */
	public static final String LANGUAGE = "language";

	/** The Constant FEED_ID. */
	public static final String FEED_ID = "{{feedId}}";

	public static final String MY_CART_PAGE = "mycartPagePath";
	public static final String CHECKOUT_PAGE_PATH = "checkoutPagePath";
	public static final String ORDER_CONFIRMATION_PAGE_PATH = "orderConfirmationPagePath";
	public static final String QUOTE_CONFIRMATION_PAGE_PATH = "quoteConfirmationPagePath";
	public static final String MY_QUOTE_PAGE_PATH = "myQuotePagePath";
	public static final String SIGNIN_PATH = "signInUrl";
	public static final String DASHBOARD_PAGE_PATH = "dashBoardUrl";

	/** The HERO MODEL Constants. */
	public static final String HERO_MODEL_PROPERTY = "heroModel";
	public static final String PRODUCT_TITLE = "title";
	public static final String PRODUCT_CLONE = "clone";
	public static final String ASSET_JSON_KEY = "asset";
	public static final String IMAGE_JSON_KEY = "image";
	public static final String DESCRIPTION_JSON_KEY = "desc";
	public static final String HP_NODE = "hp";
	public static final String MODEL_DESC = "modelDescription";
	public static final String IMAGE_ALT = "imageAltText";
	public static final String PREVIEW_IMAGE = "previewImage";
	public static final String COMPANION_PRODUCTS = "companionProduct";
	public static final String PRODUCTS_LIST = "productsList";
	public static final String IMAGE = "image";
	public static final String ALT = "alt";
	public static final String VAR_COMMERCE = "/content/commerce";
	public static final String PRODUCTS_GLOBAL = "products/global";
	public static final String IMAGE_TYPE_HERO_MODEL = "HERO, RUO_VIAL, VIAL_ARRAY, OTHER_VIAL";

	/** The Image Replication Status. */
	public static final String CUSTOM_REPLICATION_STATUS = "customReplicationStatus";
	public static final String PUBLISHED = "PUBLISHED";
	public static final String UNPUBLISHED = "UN-PUBLISHED";

	/** The Query Builder Constants. */
	public static final String PATH = "path";
	public static final String TYPE = "type";
	public static final String NODE_NAME = "nodename";
	public static final String NT_BASE = "nt:base";
	public static final String LIMIT = "p.limit";
	public static final String COMMERCE_PATH = "/content/commerce/products/bdb/";
	public static final String QUERY_PROP_OPER = "property.operation";
	public static final String JCR_ROOT = "/jcr:content/root";
	public static final String JCR_CONTENT = "/jcr:content";
	public static final String JCR_CONTENT_NODE = "jcr:content";
	public static final String LIST = "list";
	public static final String DEACTIVATE = "Deactivate";
	public static final String CQ_LAST_REPICATION_ACTION = "cq:lastReplicationAction";
	public static final String COMMERCE_PRODUCT_PATH = "/content/commerce/products/bdb/products/";
	public static final String DAM_PRODUCT_PATH = "/content/dam/bdb/products/global/";

	/** The Commerce Box Constants. */
	public static final String SAP_CC_NODENAME = "sap-cc";
	public static final String SIZE = "size";
	public static final String URL = "url";
	public static final String PROMOURL = "promotionUrl";
	public static final String METHOD = "method";
	public static final String METHOD_KEY = "Method";
	public static final String GET = "GET";
	public static final String REQUEST_PAYOAD = "requestPayload";
	public static final String CATALOG_NUMBER = "catalogNo";
	public static final String COMMERCEBOXCONFIG_JSON_NAME = "commerceBoxConfig";
	public static final String CONFIG = "config";
	public static final String YOUR_PRICE = "yourPrice";
	public static final String SIGN_IN = "signIn";
	public static final String LIST_PRICE = "listPrice";
	public static final String EST_DELIVERY = "estDelivery";
	public static final String UNIT = "unit";
	public static final String QUANTIY = "quantity";
	public static final String ADD_TO_CART = "addToCart";
	public static final String SAVE_TO_SHOPPING_LIST = "saveToShoppingList";
	public static final String REQUEST_BULK_REAGENT_QUOTE = "requestBulkReagentQuote";
	public static final String REQUEST_BULK_REAGENT_QUOTE_URL = "requestBulkReagentQuoteUrl";
	public static final String DOC_TYPE_LABELS = "docType";
	public static final String PAYLOAD = "payload";
	public static final String INCREMENT_ALT_TEXT = "plusAltText";
	public static final String DECREMENT_ALT_TEXT = "minusAltText";
	public static final String UP_ARROW = "upArrow";
	public static final String DOWN_ARROW = "downArrow";
	public static final String PLUS_ARIA_LABEL = "plusAriaLabel";
	public static final String MINUS_ARIA_LABEL = "minusAriaLabel";
	public static final String CUSTOM_DROPDOWN = "customDropdown";
	public static final String ARIA_LABEL = "ariaLabels";
	public static final String BASE_SITE_ID = "{{baseSiteId}}";
	public static final String GET_ALL_CUSTOMERS_CART = "getAllCustomersCart";
	public static final String CREATE_CART_FOR_USER = "createCartForUser";
	public static final String ADD_QUANTITY = "addQuantity";
	public static final String GUID_COOKIE_EXP_DATE = "GUIDCookieExpDate";
	public static final String ANONYMOUS_USER_ID = "anonymousUserId";
	public static final String CURRENT_USER_ID = "currentUserId";
	public static final String DATA = "data";
	public static final String VARIANT = "variant";
	public static final String SIZE_QTY = "sizeQty";
	public static final String SIZE_UOM = "sizeUOM";
	public static final String REMOVE_ITEMS_FROM_CART_MESSAGE = "removeItemsFromCartMsg";
	public static final String ADD_PRODUCTS_TOSFL_MESSAGE = "addProductToSFLCartMsg";
	public static final String EMPTY_CART_ICON = "emptyCartIcon";
	public static final String NOT_SIGNED_IN_HEADING = "notSignedInHeading";
	public static final String NOT_SIGNED_IN_DESC = "notSignedInDescription";
	public static final String GET_PROMOTIONS_MSG = "getPromotionsMsg";
	public static final String GET_PROMO_DETAILS = "getPromoDetails";
	public static final String OUT_OF_STOCK = "outOfStock";
	public static final String ATOC_ALT_TEXT = "atoCAltText";
	public static final String PDP_MODIFIER = "pdpModifier";
	public static final String UP_ARROW_ARIA_LABEL = "upArrowAriaLabel";
	public static final String DOWN_ARROW_ARIA_LABEL = "downArrowAriaLabel";
	public static final String COLOR = "color";
	public static final String LASERS = "lasers";
	public static final String REQUEST_QUOTE = "requestQuote";
	public static final String COLOR_VARIANT = "colorVariant";
	public static final String LASERS_VARIANT = "lasersVariant";
	public static final String PDP_KITS = "pdp-kits";
	public static final String PDP_INSTRUMENT = "pdp-instrument";
	public static final String ADD_TO_QUOTE = "addToQuote";
	public static final String IS_QUOTE = "isQuote";
	public static final String ATOQ_ALT_TEXT = "atoQAltText";
	public static final String SAVE_TO_QUOTE_LIST = "saveToQuoteList";
	public static final String REQUEST_BULK_REAGENT_QUOTE_TARGET = "requestBulkReagentQuoteTarget";
	public static final String ENABLE_ADD_TO_QUOTE = "enableAddToQuoteCheckBox";
	public static final String ADD_TO_QUOTE_LABEL = "addToQuoteLabel";
	public static final String QUOTE_ALT_TEXT_LABEL = "quoteAltTextLabel";
	public static final String CART_ALT_TEXT_LABEL = "cartAltTextLabel";
	public static final String DISTRIBUTOR_DELIVERY_DATE = "distributorDeliveryDate";
	public static final String IN_STOCK = "inStock";
	public static final String INQUIRE = "Inquire";
	public static final String CLONE_L = "clone";
	public static final String SIZE_L = "size";

	
	
	public static final String INQUIRE_LABEL = "inquireLabel";
	public static final String PROCEED_LABEL = "proceed";
	public static final String CLEAR_CART_LABEL = "clearCartLabel";
	public static final String CLEAR_CART = "clearCart";
	public static final String QUOTE_REFERENCE_MODAL = "quoteReferenceModal";
	public static final String RESET_HEADING = "resetCartHeading";
	public static final String RESET_DESCRIPTION = "resetCartDescription";
	public static final String RESET_LIST_HEADING = "resetCartListHeading";
	public static final String SUCCESS_MODAL_HEADING = "successModalHeading";
	public static final String SUCCESS_MODAL_DESCRIPTION = "successModalDescription";
	public static final String SUCCESS_LIST_HEADING = "successModalListHeading";
	public static final String CLEAR_CART_CTA_LABEL = "clearCartCTALabel";
	public static final String SAVE_ALL_FOR_LATER_CTA_LABEL = "saveAllForLaterCTALabel";
	public static final String APPLY_QUOTE_CTA_LABEL = "applyQuoteCTALabel";
	public static final String SUBMIT_CTA_LABEL = "submitCTALabel";

	// Url Handler constants
	public static final String CONTENT = "/content";
	public static final String HTML = ".html";
	public static final String PLP_URL = "/{language-country}/search-results?searchKey=";

	/** The PDP Tabs Constants. */
	public static final String BRAND_KEY = "brand";
	public static final String RRID = "rrid";
	public static final String CATEGORY_SIZE = "categorySize";
	public static final String BRAND_VALUE = "Brand";
	public static final String DIMENSIONS_KEY = "dimensions";
	public static final String DIMENSIONS_VALUE = "Dimensions";
	public static final String WEIGHT_KEY = "weight";
	public static final String RESOURCES = "resources";
	public static final String INCLUDED_PURCHASE = "webCompanion";
	public static final String WEIGHT_VALUE = "Weight";

	public static final String COMPANION_PRODUCT_URL = "productURL";
	public static final String ALTERNATIVE_NAME_KEY = "alternativeName";
	public static final String ALTERNATIVE_NAME_VALUE = "Alternative Name";
	public static final String CONCENTRATION_KEY = "concentration";
	public static final String CONCENTRATION_VALUE = "Concentration";
	public static final String REGULATORY_STATUS_KEY = "regulatoryStatus";
	public static final String REGULATORY_STATUS_VALUE = "Regulatory Status";
	public static final String STORAGE_BUFFER_KEY = "storageBuffer";
	public static final String STORAGE_BUFFER_VALUE = "Storage Buffer";
	public static final String ISOTYPE_KEY = "isoType";
	public static final String ISOTYPE_VALUE = "Isotype";
	public static final String SPECIES_REACTIVITY_KEY = "speciesReactivity";
	public static final String SPECIES_REACTIVITY_VALUE = "Reactivity";
	public static final String APPLICAATION_KEY = "productApplicationTest";
	public static final String APPLICAATION_VALUE = "Application";
	public static final String IMMUNOGEN_KEY = "immunogen";
	public static final String IMMUNOGEN_VALUE = "Immunogen";
	public static final String ENTREZ_GENE_ID_KEY = "entrezGeneId";
	public static final String ENTREZ_GENE_ID_VALUE = "Entrez Gene ID";
	public static final String RRID_KEY = "rrid";
	public static final String RRID_VALUE = "RRID";
	public static final String PRODUCT_DETAILS = "productDetails";
	public static final String RECOM_ASSAY_PROCEDURE = "recomAssayProcedure";
	public static final String PREPARATION_STORAGE = "preparationStorage";
	public static final String ADD_PREPARATION_STORAGE = "addPreparationStorage";
	public static final String PRODUCT_NOTICES = "productNotices";
	public static final String BASE_STORE = "baseStore";
	public static final String REACTIVITY_STATUS = "reactivityStatus";
	public static final String REACTIVITY_STATUS_DES = "reactivityStatusDesc";
	public static final String SPECIES_DESC = "speciesDescription";
	public static final String SPECIES = "species";
	public static final String APPLICATION_NAME = "applicationName";
	public static final String APPLICATION_STATUS = "applicationStatus";
	public static final String LABEL_DESCRIPTION = "labelDescription";
	public static final String KEYWORDS ="keywords";
	public static final String BDB_CONTENT_KEYWORDS ="bdbContentKeywords";
	public static final String TDS_DESCRIPTION = "tdsDescription";
	public static final String OTHER_TDS_DESCRIPTION = "otherTdsDescription";
	public static final String CONCATENATED_QUERY_FIELD = "concatenatedQueryField";
	public static final String DYE_NAME = "dyeName";
	public static final String EMMISSION_MAX = "emmisionmax";
	public static final String EXCITATION_MAX = "excitationmax";
	public static final String LASER_COLOR = "laserColor";
	public static final String LASER_WAVELENGTH = "laserWavelength";
	public static final String BD_FORMAT = "bdFormat";
	public static final String CLONE = "clone";
    public static final String TDS_CLONE_NAME = "tdsCloneName";
    public static final String TDS_CLONE_DISPLAY_NAME = "tdsCloneDisplayName";
    public static final String FORMAT_STATEMENT = "formatStatement";
    public static final String FORMAT_DETAILS_IMAGE = "formatDetailsImage";
    public static final String REFERENCE_DETAILS = "referenceDetails";
    public static final String PUBMEDID = "pubMedId";
    public static final String PUBMEDID_VALUE = "pubMedIdValue";
    public static final String VOL_PER_TEST_KEY = "volPerTest";
    public static final String VOL_PER_TEST_VALUE = "Vol. Per Test";
    public static final String PRODUCT_URL = "productUrl";
    public static final String ID = "id";
    public static final String UNIQUE_IDENTIFIER = "uniqueIdentifier";
    public static final String BARCODE_SEQUENCE_KEY = "barcodeSequence";
    public static final String BARCODE_SEQUENCE_VALUE = "Barcode Sequence";
    public static final String SEQUENCE_ID_KEY = "seqId";
    public static final String SEQUENCE_ID_VALUE = "Sequence ID";
    public static final String CLONE_VALUE = "Clone";
    public static final String PRODUCT_TYPE_KEY = "productType";
    public static final String PRODUCT_TYPE_VALUE = "Product Type";
    public static final String PRODUCT_TITLE_VALUE = "Product Title";
    public static final String HOST_SPECIES_KEY = "hostSpecies";
    public static final String HOST_STRAIN_KEY = "hostStrain";
    public static final String HOST_SPECIES_VALUE = "Host Species";
    public static final String TARGET_SPECIES = "Target Species";
    public static final String CONJUGATE_FORMAT = "Conjugate/Format";
    public static final String LASER_LINE = "Laser Line";
    public static final String LASER_WAVLENGTH = "laserWavelength";
    public static final String SPECIFICITY = "specificity";
    public static final String REGION_KEY = "region";
    public static final String JCR_PRIMARY_TYPE = JcrConstants.JCR_PRIMARYTYPE;
    public static final String HP_SOLR_DATA = "hpSolrData";
    public static final String PRODUCT_APPLICATION_TEST = "productApplicationTest";
    public static final String UID = "uid";
    public static final String NM = "nm";
  	public static final String APPLICATION_STATUS_DESC = "applicationStatusDesc";
  	public static final String APPLICATION_DESC = "applicationDesc";
  	public static final String FLOW_CYTOMETRY = "Flow cytometry";
  	public static final String IC_FCM = "IC/FCM";
  	public static final String WEB_NAME = "webName";
  	public static final String DERIVED_PRODUCT_STATUS = "derivedProductStatus";
  	public static final String VALIDITY_START_DATE = "validityStartDate";
  	public static final String DISPLAY_ONLY = "DISPLAYONLY";
  	public static final String PURCHASEABLE = "PURCHASEABLE";
  	public static final String IS_AVAILABLE = "isAvailable";
	public static final String POST_TRANSLATIONAL_MODIFICATION = "postTranslationalModification";
	public static final String RECOMBINANT = "recombinant";
	public static final String RELEASE_DATE = "releaseDate";
	public static final String AFS_DATE = "afSDate";

  	/*Ruo Image Comstants*/
  	public static final String LYOPHILIZED = "Lyophilized";
  	public static final String ISO_TYPE_CONTROL_LYSATE = "IsotypeControlLysate";


  	public static final String MOLECULAR_WEIGHT = "molecularWeight";
  	public static final String HOST_SPECIES = "hostSpecies";
  	public static final String DOC_PART_IDS = "docPartIds";
	public static final String DOC_PART_ID = "docPartId";
  	public static final String BEAD_POSITION = "beadPosition";
  	public static final String VIDEO_ID = "videoId";
  	public static final String CURRENT_PAGE = "currentPage";
	public static final String VIDEO_TYPE = "videoType";


	public static final String SFA = "SFA";
  	public static final String SCM = "SCM";
  	public static final String KIT = "Kit";
  	public static final String OTHER = "Other";
  	public static final String INSTRUMENTS = "Instruments";

  	public static final String PROP_SFA = "sfa";
  	public static final String PROP_SCM = "scm";
  	public static final String PROP_KIT = "kitsAndSets";
  	public static final String PROP_OTHER = "otherProducts";
  	public static final String PROP_INSTRUMENTS = "instruments";

  	public static final String PDP_TEMPLATE = "pdpTemplate";
  	public static final String ROOT_PDP_LOOKUP_PATH = "/jcr:content/root/pdplookup";
  	public static final String TECH_TOOLS_PATH = "/jcr:content/technicalTools";


	/** The Catalog Update Servlet. */
	public static final String SAP = "sap";
	public static final String HP = "hp";
	public static final String PRODUCTS = "products";
	public static final String VAR_COMMERCE_PATH = VAR_COMMERCE + SINGLE_SLASH + PRODUCTS;
	public static final String BASE_PRODUCT = "baseProduct";
	public static final String VARIANTS = "variants";
	public static final String CODE = "code";
	public static final String REGION_DETAILS_NODE_NAME = "region-details";
	public static final String BATCH_MANAGEMENT_INDICATIOR = "batchManagementIndicator";
	public static final String CROSS_DISTRIBUTION_STATUS = "crossDistributionStatus";
	public static final String DANGEROUS_GOODS_INDICATOR = "dangerousGoodsIndicator";
	public static final String EAN = "ean";
	public static final String GLOBAL_WEB_AVAILABLE = "globalWebAvailable";
	public static final String GROSS_WEIGHT = "grossWeight";
	public static final String GROSS_WEIGHT_UNIT = "grossWeightUnit";
	public static final String HAZARDOUS_CODE = "hazardousCode";
	public static final String HAZARDOUS_DESCRIPTION = "hazardousDescription";
	public static final String MATERIAL_GROUP_PACKAGE = "materialGroupPackage";
	public static final String NAME = "name";
	public static final String DCTITLE = "dctitle";
	public static final String DOC_KEYWORDS = "docKeywords";
	public static final String NAME_UPPER_CASE = "nameUpperCase";
	public static final String NET_WEIGHT = "netWeight";
	public static final String PACKAGING_UNIT = "packagingUnit";
	public static final String PRODUCT_HIERARCHY = "productHierarchy";
	public static final String PRODUCT_HIERARCHY_LEVEL1 = "productHierarchyLevel1";
	public static final String PRODUCT_HIERARCHY_LEVEL2 = "productHierarchyLevel2";
	public static final String PRODUCT_HIERARCHY_LEVEL3 = "productHierarchyLevel3";
	public static final String PRODUCT_HIERARCHY_LEVEL4 = "productHierarchyLevel4";
	public static final String PRODUCT_HIERARCHY_LEVEL5 = "productHierarchyLevel5";
	public static final String RESEARCH_ONLY = "researchOnly";
	public static final String SAP_MATERIAL_TYPE = "sapMaterialType";
	public static final String SHIPPING_CONDITION_CODE = "shippingConditionCode";
	public static final String SHIPPING_CONDITION_DESCRIPTION = "shippingConditionDescription";
	public static final String TEMPERATURE_CONDITION_INDICAOTR = "temperatureConditionIndicator";
	public static final String WEIGHT_UNIT = "weightUnit";
	public static final String X_PLANT_STATUS = "xplantStatus";
	public static final String CROSS_DCHAIN_DATE = "crossDchainDate";
	public static final String SAP_CREATION_DATE = "sapCreationDate";
	public static final String X_PLANT_VALID_DATE = "xplantValidDate";
	public static final String MARKET_AVAILABILITY = "marketAvailability";
	public static final String CATALOG_TAG_HIERARCHY_PATH = "/content/cq:tags/bdb";
	public static final String CATALOG_VAR_HIERARCHY_PATH = "/content/commerce/products/bdb";
	public static final String MATERIAL_NUMBER = "materialNumber";
	public static final String TDS_DOCUMENT_TYPE = "tdsDocumentType";
	public static final String BASE_MATERIAL_NUMBER = "baseMaterialNumber";
	public static final String UNDERSCORE_BASE = "_base";
	public static final String PRODUCTS_KEY = "Products";
	public static final String SUPER_CATEGORY = "superCategory";
	public static final String PRIMARY_SUPER_CATEGORY= "primarySuperCategory";
	public static final String CATEGORY= "category";
	public static final String PRODUCT_TYPE_DISPLAY_VALUE= "productType_display_value";
	public static final String CQ_TAGS_PATH = "/content/cq:tags/";
	public static final String BDB = "bdb";
	public static final String SEARCH_KEY = "searchKey=";
	public static final String APPROVAL_STATUS= "approvalStatus";
	public static final String MPG_CATEGORY = "mpgCategory";
	public static final String CLASSIFICATION_CATEGOR_KEY = "classificationCategory";
	public static final String STATUS_MESSAGE = "statusMessage";
	public static final String ERROR_SKUS = "errorSKUs";
	public static final String CATALOG_RESPONSE = "catalogResponse";
	public static final String DATA_SOURCE = "dataSource";
	public static final String STATUS = "status";
	public static final String CATALOG_NUMBER_KEY = "catalogNumber";
	public static final String FORMAT_ID = "formartId";
	public static final String PHANTOM_CHILDS = "phantomChilds";
	public static final String IS_PHANTOM = "isPhantom";
	public static final String PHANTOM_PARENTS = "phantomParents";

	/**
	 * The Hybris constants
	 */
	public static final String HYBRIS_SITE_LITERAL = "{{site}}";
	public static final String PDP_DOC_DISPLAY_CONFIG = "pdpDocDisplayConfig";
	public static final String HYBRIS_SITE_ID = "hybrisSiteId";
	public static final String SIGN_UP_CALL = "signupcall";
	public static final String AREA_OF_FOCUS = "areaoffocus";
	public static final String RESET_PASS = "resetpassword";
	public static final String PURCHASE_ACCOUNT = "purchaseaccount";
	public static final String UPLOAD_DOCUMENT = "uploaddocument";
	public static final String HTTP_LITERAL = "http";
	public static final String HTTPS_LITERAL = "https";
	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER_WITH_SPACE = "bearer ";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String ACCESS_TOKEN_EXPIRY = "expires_in";
	public static final String CLIENT_ID = "client_id";
	public static final String GRANT_TYPE = "grant_type";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String SITE_ID = "siteId";
	public static final String AND = "&";
	public static final String HYBRIS_USER_ID_LITERAL = "{userId}";
	public static final String SITE_ID_HEADER_KEY = "siteid";
	public static final String DOUBLE_QOUTES = "\"";
	public static final String CUSTOMER_LOGIN = "customerlogin";
	public static final String COLON = ":";
	public static final String COMMA = ",";
	public static final String SLASH_SLASH_PIPE = "\\|";
	public static final String AEM_SSO_ERROR_CODE = "AEM-14";
	public static final String FE_SSO_COOKIE_NAME = "state_security_code";
	public static final String LOGIN_ERROR_COOKIE = "login_error";
	public static final String AUTH_TOKEN_COOKIE = "auth_token";
	public static final String SSO_TOKEN_COOKIE = "sso_token";
	public static final String TOKEN_EXPIRY_TIME_COOKIE = "token_expiry_time";
	public static final String AUTH_TOKEN_REFRESH_COOKIE = "hybrisRefreshToken";
	public static final String SSO_TOKEN_REFRESH_COOKIE = "ssoRefreshToken";
	public static final String USER_DETAILS_UID_COOKIE = "user_details_uid";
	public static final String USER_DETAILS_LAST_NAME_COOKIE = "user_details_lastName";
	public static final String USER_DETAILS_FIRSTNAME_COOKIE = "user_details_firstName";
	public static final String USER_DETAILS_CARTID_COOKIE = "user_details_cartid";
	public static final String USER_DETAILS_UNREAD_MESSAGES_COUNT_COOKIE = "user_details_unreadMessagesCount";
	public static final String USER_DETAILS_ACC_TYPE_COOKIE = "user_details_accountType";
	public static final String USER_DETAILS_ACC_ROLE_COOKIE = "user_details_accountRole";
	public static final String USER_DETAILS_GRANT_ENABLED_COOKIE = "user_details_grantEnabled";
	public static final String USER_DETAILS_IS_SMART_CART_USER = "user_details_isSmartCartUser";
	public static final String USER_DETAILS_IS_CREDIT_CARD_ENABLED = "user_details_isCreditCardEnabledForSite";
	public static final String USER_DETAILS_IS_REWARDS_ENABLED = "user_details_isRewardsEnabled";
	public static final String USER_DETAILS_IS_DISTRIBUTOR = "user_details_isDistributor";
	public static final String USER_DETAILS_PENDING_ORDER_COUNT_COOKIE = "user_details_pendingOrderCount";
	public static final String AUTHORIZATION_CODE = "authorizationCode";
	public static final String ERROR_CODE_TEXT = "errorCode";
	public static final String UTF_ENCODING = "UTF-8";
	public static final String ERRORS = "errors";
	public static final String ERROR = "error";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String STATE_CODE_SPLITTER = "@@separator@@";
	public static final boolean BOOLEAN_TRUE = true;
	public static final String STRING_TRUE = "true";
	public static final String HASH_PLACEHOLDER = "@@hash@@";
	public static final String AREA_OF_INTEREST = "user_details_area_of_interest";
	public static final String USER_DETAILS_COMPANY = "user_details_company";
	public static final String USER_DETAILS_COUNTRY = "user_details_country";
	public static final String USER_DETAILS_INDUSTRY = "user_details_userIndustry";
	public static final String USER_DETAILS_LEAD = "user_details_lead";
	public static final String USER_DETAILS_ROLE = "user_details_userRole";
	public static final String USER_DETAILS_ACTIVE = "user_details_active";

	/**
	 * AEM response Errors
	 */

	/** The Constant ACCESS_TOKEN_RETRIEVAL_ERROR. */
	public static final String ACCESS_TOKEN_RETRIEVAL_ERROR = "{\r\n" + "   \"errors\": [\r\n" + "      {\r\n"
			+ "         \"errorCode\": \"AEM-10\",\r\n"
			+ "         \"message\": \"Could not fetch Auth token from hybris for subsequent calls\",\r\n"
			+ "         \"type\": \"HybrisEnvironmentDown\"\r\n" + "      }\r\n" + "   ]\r\n" + "}";

	/** The Constant HYBRIS_INTERNAL_SERVER_ERROR. */
	public static final String HYBRIS_INTERNAL_SERVER_ERROR = "{\r\n" + "   \"errors\": [\r\n" + "      {\r\n"
			+ "         \"errorCode\": \"AEM-11\",\r\n"
			+ "         \"message\": \"Could not connect to Hybris Environment\",\r\n"
			+ "         \"type\": \"HybrisEnvironmentDown\"\r\n" + "      }\r\n" + "   ]\r\n" + "}";

	/** The Constant  Invalid Request Type. */
	public static final String INVALID_REQUEST_TYPE = "{\r\n" + "   \"errors\": [\r\n" + "      {\r\n"
			+ "         \"errorCode\": \"AEM-12\",\r\n"
			+ "         \"message\": \"Request type is invalid, please check the selector before making the call\",\r\n"
			+ "         \"type\": \"InvalidRequestType\"\r\n" + "      }\r\n" + "   ]\r\n" + "}";

	/** The Constant  Invalid Request Type. */
	public static final String AEM_INTERNAL_SERVER_ERROR = "{\r\n" + "   \"errors\": [\r\n" + "      {\r\n"
			+ "         \"errorCode\": \"AEM-13\",\r\n"
			+ "         \"message\": \"Could not retrieve/upload data from/to hybris\",\r\n"
			+ "         \"type\": \"AemInternalServerError\"\r\n" + "      }\r\n" + "   ]\r\n" + "}";

	/** The Constant MISSING_QUERYPARAM_ERROR_TEXT. */
	public static final String MISSING_QUERYPARAM_ERROR_TEXT = "{\r\n" + "   \"errors\": [\r\n" + "      {\r\n"
			+ "         \"errorCode\": \"AEM-15\",\r\n"
			+ "         \"message\": \"SSO Connection failed because of either query params or frontend cookie is unavailable\",\r\n"
			+ "         \"type\": \"AemInternalServerError\"\r\n" + "      }\r\n" + "   ]\r\n" + "}";

	/** The Constant MISSING_FORM_DATA. */
	public static final String MISSING_FORM_DATA = "{\r\n" + "   \"errors\": [\r\n" + "      {\r\n"
			+ "         \"errorCode\": \"AEM-16\",\r\n"
			+ "         \"message\": \"Form-data fields are missing from the request\",\r\n"
			+ "         \"type\": \"AemInternalServerError\"\r\n" + "      }\r\n" + "   ]\r\n" + "}";

	/** The Constant SIGN_OUT_API_ERROR. */
	public static final String SIGN_OUT_API_ERROR = "{\r\n" + "   \"errors\": [\r\n" + "      {\r\n"
			+ "         \"errorCode\": \"AEM-17\",\r\n"
			+ "         \"message\": \"Sign Out call to Hybris failed\",\r\n"
			+ "         \"type\": \"AemInternalServerError\"\r\n" + "      }\r\n" + "   ]\r\n" + "}";


	/**
	 * The Account Management Dashboard paths
	 */
	public static final String DASHBOARD_PATH = "/dashboard";
	public static final String ORDERS_PATH = "/orders";
	public static final String ORDERS_APPROVAL_PATH = "/orders-approval";
	public static final String SHOPPING_LISTS_PATH = "/shopping-lists";
	public static final String REWARDS_PATH = "/rewards";
	public static final String CERTIFICATIONS_PATH = "/certifications";
	public static final String PAYMENT_METHODS_PATH = "/payment-methods";
	public static final String ADDRESSES_PATH = "/addresses";
	public static final String ACCOUNT_SETTINGS_PATH = "/account-settings";
	public static final String COMMUNICATION_SETTINGS_PATH = "/communication-settings";
	public static final String GRANTS_PATH = "/grants";
	public static final String QUOTES_PATH = "/quotes";
	public static final String QUOTE_LIST_PATH = "/quote-lists";

	public static final String QUOTE_HISTORY_PATH = "/quote-history";


	/** The Global Header Constants */
	public static final String CONST_BDB_ROOT_PATH = "/content/bdb";
	public static final String CONST_HIDEINNAV = "hideInNav";
	public static final String CONST_IMAGE_DESC = "modelImageDesc";
	public static final String CONST_IMAGE_LINK = "modelImageLink";
	public static final String CONST_REDIRECT_LINK = "redirectLink";
	public static final String CONST_IMAGE_PATH = "modelImagePath";
	public static final String CONST_IMAGE_TITLE = "modelImageTitle";
	public static final String CONST_IMAGE_ALT = "modelImageAltText";
	public static final String CONST_IMAGE_CTA_LABEL = "modelImageCTALabel";
	public static final int CONST_NAV_DEPTH = 4;
	public static final String CONST_DEFAULT_REGION = "na";
	public static final String CONST_DEFAULT_COUNTRY = "us";
	public static final String CONST_DEFAULT_LANGUAGE = "en";
	public static final String CONST_DEFAULT_LOCALE = "en-us";
	public static final String CONST_DEFAULT_COUNTRY_NAME = "United States";
	public static final String CONST_DEFAULT_LANGUAGE_NAME = "English";
	public static final String REDIRECT_URL = "redirectURL";
	public static final String LOG_OUT = "log-out";
	public static final String IMG_SRC = "imgSrc";
	public static final String IMG_SRC_MOBILE = "imgSrcM";
	public static final String BIN_SIGNOUTSERVLET = "/content/bdb/paths/sign-out";

	/**
	 * The Product Announcment constants
	 */
	public static final String MARKETING_NODE = "marketing";
	public static final String PRODUCT_ANNOUNCEMENT = "productAnnouncement";
	public static final String PA_DESCRIPTION = "paDescription";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String FAQ_TITLE = "paFAQTitle";
	public static final String REGIONAL_DISCLAIMER = "regionalDisclaimers";
	public static final String PA_VIEWMORE_CTA = "paViewMoreCTA";
	public static final String OPEN_NEW_TAB = "openNewTab";
	public static final String DISCLAIMER_STATUS = "disclaimerStatus";
	public static final String MORE_INFO_LINK = "moreInfoLink";
	public static final String VIEW_MORE_LABEL = "viewMoreFaqLabel";
	public static final String VIEW_MORE_LINK = "viewMoreFaqLink";
	public static final String FAQ_PROPERTY = "faqProperty";
	public static final String QUESTION = "question";
	public static final String ANSWER = "answer";
	public static final String FORM_DATA = "form_data";
	public static final String PRODUCT_SKU = "productSku";
	public static final String PRODUCT_NAME = "productName";
	public static final String GLOBAL = "global";
	public static final String EMEA = "emea";
	public static final String APAC_EN = "apac(english)";
	public static final String APAC = "apac";
	public static final String PRODUCT_ANNOUNCEMENT_ALT_TEXT = "productAnnnouncementAltText";
	public static final String NON_PURCHASABLE = "nonPurchasable";
	public static final String HEADER_LEVEL_MESSAGES = "headerLevelMessages";
	public static final String NON_PURCHASABLE_MSG = "nonPurchasableMsg";
	public static final String NON_PURCHASABLE_ALT = "nonPurchasableAlt";
	public static final String HEADER_LEVEL_MESSAGES_MSG = "headerLevelMessagesMsg";
	public static final String HEADER_LEVEL_MESSAGES_ALT = "headerLevelMessagesAlt";
	public static final String PRODUCT_IMAGE_HREF = "productImgHref";
	public static final String PRODUCT_CATALOG_HREF = "productCatalogHref";
	public static final String DISPLAY_BULK_REAGENT_QUOTE = "displayBulkReagentQuote";
	public static final String TESTIMONIAL_ID = "testimonialId";
	public static final String LANGUAGE_PLACEHOLDER = "{en-us}";
	/**
	 *TheErrorPagepath
	 */
	public static final String ERROR_PAGE_PATH = "errorPagePath";

	/** The Master Data Page Path */
	public static final String MASTER_DATA_PAGE_PATH = "masterDataPagePath";

	/**Sign-In URL Constants */
	public static final String RESPONSE_TYPE = "response_type";
	public static final String INTERROGATION = "?";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String SCOPE = "scope";
	public static final String STATE = "state";
	public static final String SECURITY_TOKEN = "security_token";
	public static final String TOKEN = "{{token}}";
	public static final String BIN = "bin";
	public static final String UI_LOCALES = "ui_locales";
	public static final String UI_LOCALE = "ui_locale";
	public static final String DEFAULT_LOCALE = "en_US";

	/**
	 *PDPSessionModel
	 */
	public static final String CQ_TAGS = TagConstants.PN_TAGS;
	public static final String PRIMARY = "PRIMARY";
	public static final String THUMBNAIL = "THUMBNAIL";
	public static final String GALLERY = "GALLERY";
	public static final String COMPARE = "COMPARE";
	public static final String DYENAME = "DYENAME";
	public static final String CLINICAL = "clinical-vial";
	public static final String CLINICAL_TAG = "bdb:assets/images/clinical-vial";
	public static final String THUMBNAIL_TAG = "bdb:assets/images/thumbnail";
	public static final String PRIMARY_TAG = "bdb:assets/images/primary";
	public static final String GALLERY_TAG = "bdb:assets/images/gallery";
	public static final String HERO_TAG = "bdb:assets/images/hero";
	public static final String COMPARE_TAG = "bdb:assets/images/compare";
	public static final String DYENAME_TAG = "bdb:assets/images/dyename";
	public static final String HERO = "HERO";
	public static final String IMAGE_PATH = "imagePath";
	public static final String DC_TITLE = DamConstants.DC_TITLE;
	public static final String DC_CAPTION = "dc:caption";
	public static final String DC_IMAGE_ALT = "dc:imageAltText";
	public static final String IMAGE_TITLE = "imageTitle";
	public static final String IMAGE_CAPTION = "caption";
	public static final String IMAGE_ALT_TEXT = "imageAltText";
	public static final String IMAGE_TYPE = "imageType";
	public static final String IMAGE_NAME = "imageName";
	public static final String IMAGE_DESCRIPTION = "imageDescription";
	public static final String RETRY_COUNT = "retryCount";
	public static final String PROCESS_STATE = "processState";
	public static final String ERROR_LABEL = "Error";
	public static final String FAILURE_LABEL = "Failure";
	public static final String RECORD_LABEL = "record";
	public static final String ERROR_REASON = "errorReason";
	public static final String TYPE_ATTR = "@type";
	public static final String PROPERTY_VALUE = "PropertyValue";
	
	/**TDS Templates Path constants*/
	public static final String TDS_TEMPLATE_BASE_PATH = "/content/dam/bdb/tds-templates";

	/**
     * servlet to the image
     */
    public static final String IMAGE_SERVLETPATH = "/bin/tdsAuthentication";

	/**
	 * The Add To Cart constants
	 */
	public static final String MY_CART = "myCart";
	public static final String ITEMS = "items";
	public static final String ITEM = "item";
	public static final String CART_ID = "cartId";
	public static final String QUICK_ADD = "quickAdd";
	public static final String CONTINUE_SHOPPING = "continueShopping";
	public static final String EST_DELIVERY_DATE = "estDeliveryDate";
	public static final String QUANTITY = "quantityLabel";
	public static final String SAVE_LATER_QUANTITY_LABEL = "saveLAterQuantityLabel";
	public static final String PRICE_PER_UNIT = "pricePerUnit";
	public static final String TOTAL_PRICE = "totalPriceLabel";
	public static final String ADD_TO_SHOPPING_LIST = "addToShoppingList";
	public static final String ADD_TO_SHOPPINGLIST = "addToShoppingList";
	public static final String SAVE_FOR_LATER = "saveforLater";
	public static final String SAVE_FOR_LATER_CART = "savedForLaterCart";
	public static final String SAVE_FOR_LATER_QUOTE = "savedForLaterQuote";
	public static final String REMOVE = "remove";
	public static final String REQUEST_SAME_LOT_NUMBER = "requestSameLotNumber";
	public static final String QUOTE_REFERENCE = "quoteReference";
	public static final String REQUEST_LOT_NUMBERS = "requestSameLotNumbers";
	public static final String PROMO_CODE = "promocode";
	public static final String YOU_SAVED = "yousaved";
	public static final String ON = "on";
	public static final String CLOSE_ICON = "closeIcon";
	public static final String GRANT_ELIGIBLE_ICON = "grantEligibleIcon";
	public static final String GRANT_ELIGIBLE_LABEL = "grantEligibleLabel";
	public static final String HEADING = "heading";
	public static final String DESCRIPTION = "description";
	public static final String MODAL_HEADING = "modalHeading";
	public static final String MODAL_DESCRIPTION = "modalDescription";
	public static final String ENLARGED_IMAGE_PATH = "enlargedImagePath";
	public static final String MAGNIFYING_GLASS_COLOR = "magnifiyGlassColor";
	public static final String ALT_IMAGE = "altImage";
	public static final String ENLARGE_SIZE = "enlargeSize";
	public static final String ALT_TEXT = "altText";
	public static final String CLASS_NAME = NameConstants.PN_CLASS_NAME;
	public static final String EMPTY_CART = "emptyCart";
	public static final String CONTINUE_SHOPPING_URL = "continueShoppingUrl";
	public static final String CONTINUE_SHOPPING_TARGET = "continueShoppingTarget";
	public static final String PRICE_CONFIRMATION_PDF = "priceConfirmationPDF";
	public static final String QUOTE_REFERENCE_ARIA_LABEL = "quoteReferenceAriaLabel";
	public static final String UPDATE_QUANTITY_TOAST_MSG = "updateQuantityToastMsg";
	public static final String MAX_QUANTITY_ERROR_MSG = "maxQuantityErrorMessage";
	public static final String MESSAGE = "message";
	public static final String QUANTITY_VALUE = "quantity";
	public static final String CART_QUANTITY_VALUE = "cartQuantity";
	public static final String TOTAL_PROMOTIONAL_ICON = "totalPromotionalSrc";
	public static final String TOTAL_PROMOTIONAL_ICON_ALT = "totalPromotionAltText";
	public static final String QUICK_ADD_CTA = "quickAddCTA";
	public static final String YOUR_PRICE_VALUE= "yourPrice";
	public static final String LIST_PRICE_VALUE = "listPrice";
	public static final String YOUR_PRICE_LABEL_ATC = "yourPriceLabel";
	public static final String LIST_PRICE_LABEL_ATC = "listPriceLabel";
	public static final String MAX_QUANTITY_ALT_TEXT = "maxQuantityErrorAltText";
	public static final String PRODUCT_CODE= "{{productCode}}";
	public static final String MAX_PURCHASABLE_QUANTITY= "{{maxPurchasableQuantity}}";
	public static final String PROMO_CODE_INPUT_MESSAGE = "promoCodeInputMsg";
	public static final String APPLIED_SUCCESSFULLY = "appliedSuccessfully";
	public static final String INVALID_CODE = "invalidCode";
	public static final String PROMO_CODE_APPLY_CTA= "promoCodeApplyCTA";
	public static final String QUICK_ADD_PROD_NOT_AV_MSG= "quickAddProductNotAvailableMessage";
	public static final String QUICK_ADD_PROD_NOT_AV_ALT= "quickAddProductNotAvailableAltText";
	public static final String QUICK_ADD_INVALID_PROD_MSG= "quickAddInvalidProductMessage";
	public static final String QUICK_ADD_INVALID_PROD_ALT= "quickAddInvalidProductAltText";
	public static final String QUICK_ADD_FILL_ALL_ERROR= "quickAddFillAllError";
	public static final String SAVE_TO_SHOPPING_LIST_TOAST_MSG= "saveToShoppingListToastMsg";
	public static final String QUICK_ADD_PROD_NOT_AV_ERROR= "quickAddProductNotAvailableError";
	public static final String QUICK_ADD_INVALID_PROD_ERROR= "quickAddInvalidProductError";
	public static final String REPLACE_CART_ENTRY= "replaceCartEntry";
	public static final String REPLACE_SAVE_FOR_LATER= "replaceSaveForLaterEntry";
	public static final String BILL_TO_ADDRESS_LABEL = "billToAddressLabel";
	public static final String BILL_TO_NUMBER_LBL = "billToNumberLabel";
	public static final String BILL_TO_NUMBER_LABEL_ALT = "billtoNumberLabel";
	public static final String OEM_NOTIFICATION = "OEMNotification";
	public static final String PLACEHOLDER = "placeholder";
	public static final String RUO_ERROR_MESSAGE = "RUOErrorMessage";
	public static final String RUO = "RUO";


	/**
	 *getPdfPathsBasedOnType
	 */
	public static final String METADATA_PATH_AS_CHILD = "jcr:content/metadata";
	public static final String PDF_DOC_REGION = "docRegion";
	public static final String PDF_DOC_TYPE = "docType";
	public static final String PDFX_DOC_TYPE = "pdfx:docType";
	public static final String REGION_TAG = "bdb:regions/";

	/**Save to Shopping list Constants */
	public static final String CLOSE_ICON_SHOPPING_LIST = "closeIcon";
	public static final String ADD_TO_LIST_LABEL = "addToListLabel";
	public static final String CREATE_LIST_LABEL = "createListLabel";
	public static final String ADDED_TO_LIST_LABEL = "addedToListLabel";
	public static final String VIEW_LIST_LABEL = "viewListLabel";
	public static final String CLOSE_CTA_LABEL = "closeCtaLabel";
	public static final String SAVE_CTA_LABEL = "saveCtaLabel";
	public static final String CREATE_LIST_CTA_LABEL = "createListCtaLabel";
	public static final String ADD_TO_LIST_CTA_LABEL = "addToListCtaLabel";
	public static final String INPUT_LABEL = "inputLabel";
	public static final String DROPDOWN_LABEL = "dropDownLabel";
	public static final String ADD_TO_LIST_CONTENT = "addToListContent";
	public static final String ADDED_TO_LIST_CONTENT = "addedToListContent";
	public static final String ADDED_TO_LIST_ERROR_MESSAGE = "addedToErrorMessageContent";
	public static final String SAVE_CTA = "saveCta";
	public static final String CREATE_LIST = "createList";
	public static final String CLOSE_CTA = "closeCta";
	public static final String ADD_TO_LIST = "addToList";
	public static final String POST = "POST";
	public static final String GET_LIST = "getList";
	public static final String UPDATE_LIST = "updateList";
	public static final String VIEW_ALL_LIST_URL = "viewAllListUrl";
	public static final String SIGN_IN_URL = "signInUrl";

	public static final String HOME_PAGE_PATH = "homePagePath";

	/**Account Management Labels' Constants*/

	/*Account Settings Labels' Constants*/
	public static final String COMPANY_LABEL = "company";
	public static final String EAN_CODE_LABEL = "eANCode";
	public static final String EDIT_LABEL = "edit";
	public static final String EMAIL_ADDRESS_LABEL = "emailAddress";
	public static final String FIRST_NAME_LABEL = "firstName";
	public static final String FIRST_NAME_LABEL_TEXT = "firstNameLabel";
	public static final String LAST_NAME_LABEL = "lastName";
	public static final String LAST_NAME_LABEL_TEXT = "lastNameLabel";
	public static final String PASS_LABEL = "password";
	public static final String PHONE_NUMBER_LABEL = "phoneNumber";
	public static final String UPDATE_LABEL = "update";
	public static final String VAT_NUMBER_LABEL = "vATNumber";
	public static final String VAT_NUMBER = "vatNumberLabel";

	public static final String EDIT_PASS_LABEL = "editPwd";
	public static final String ENTER_NEW_PASS_LABEL = "enterNewPwd";
	public static final String CONFIRM_NEW_PASS_LABEL = "confirmNewPwd";
	public static final String YESTERDAY_LABEL = "yesterday";
	public static final String TODAY_LABEL = "today";
	public static final String JOB_TITLE_LABEL = "jobTitleLabel";
	public static final String INSTITUTION_LABEL = "institutionLabel";

	/*Addresses Labels' Constants*/
	public static final String ADDRESS_LABEL = "address";
	public static final String ADDRESS_LINE_ONE_LABEL = "AddressLineOne";
	public static final String ADDRESS_LINE_TWO_LABEL = "addressLineTwo";
	public static final String STREET_ADDRESS_LABEL = "streetAddress";
	public static final String BILLING_ADDRESS_LABEL = "billingAddress";
	public static final String BUILDING_LABEL = "building";
	public static final String CITY_LABEL = "city";
	public static final String COMPANY_ADDRESS_LABEL = "companyAddress";
	public static final String COUNTRY_LABEL = "countryLabel";
	public static final String NICKNAME_PLACEHOLDER_LABEL = "nicknamePlaceholder";
	public static final String DEPARTMENT_LABEL = "department";
	public static final String FLOOR_LABEL = "floor";
	public static final String PHONE_LABEL = "phone";
	public static final String PHONE_LABEL_TEXT = "phoneLabel";
	public static final String OKAY_LABEL = "okay";
	public static final String ROOM_LABEL = "room";
	public static final String SHIPPING_ADDRESS_LABEL = "shippingAddress";
	public static final String SHIP_TO_NUMBER_LABEL = "shipToNumber";
	public static final String SHIP_TO_ADDRESS_LABEL = "shipToAddress";
	public static final String STATE_LABEL = "state";
	public static final String PROVINCE_LABEL = "province";
	public static final String DISTRICT_LABEL = "district";
	public static final String POSTAL_CODE_LABEL = "postalCode";
	public static final String PIN_CODE_LABEL = "pinCode";
	public static final String ZIP_CODE_LABEL = "zipcode";
	public static final String SET_AS_DEFAULT_LABEL = "setAsDefault";
	public static final String DEFAULT_LABEL = "defaultLabel";

	public static final String GLOBAL_DISCLAIMER_LABEL = "globalDisclaimer";
	public static final String GLOBAL_DISCLAIMER_PATH = "globalDisclaimerPath";
	public static final String REGIONAL_DISCLAIMER_LABEL = "regionalDisclaimer";
	public static final String REGIONAL_DISCLAIMER_PATH = "regionalDisclaimerPath";
	public static final String GLOBAL_DISCLAIMER_FONT_SIZE_LABEL = "globalDisclaimerFontSize";
	public static final String REGIONAL_DISCLAIMER_FONT_SIZE_LABEL = "regionalDisclaimerFontSize";

	/* Field Labels Constants */
	public static final String TITLE_LABEL = "title";
	public static final String SECTION_TITLE = "sectionTitle";
	public static final String ANCHOR_ID_DESCRIPTION = "anchorIdDescription";
	public static final String ANCHOR_RESOURCE_LIN = "anchorResourceLink";

	public static final String ENABLE_BUY_ONLINE = "enableBuyOnline";
	public static final String ENABLE_BUY_ONLINE_ICON = "enableBuyOnlineIcon";
	public static final String ENABLE_BUY_ONLINE_ALT = "enableBuyOnlineAlt";
	public static final String ACCOUNT_UNDER_REVIEW = "accountUnderReview";
	public static final String ACCOUNT_UNDER_REVIEW_ICON = "accountUnderReviewIcon";
	public static final String ACCOUNT_UNDER_REVIEW_ALT = "accountUnderReviewAlt";
	public static final String BUY_ONLINE_ENABLED = "buyingOnlineEnabled";
	public static final String BUY_ONLINE_ENABLED_ICON = "buyingOnlineEnabledIcon";
	public static final String BUY_ONLINE_ENABLED_ALT = "buyingOnlineEnabledAlt";
	public static final String UPDATE_ROLE = "updateRole";
	public static final String UPDATE_ROLE_ICON= "updateRoleIcon";
	public static final String UPDATE_ROLE_ICON_ALT = "updateRoleAlt";
	public static final String UPDATE_PASS = "updatePassword";
	public static final String UPDATE_PASS_ICON = "updatePasswordIcon";
	public static final String UPDATE_PASS_ALT = "updatePasswordAlt";
	public static final String BASIC_CONFIRMATION_LABELS = "basicConformationLabels";
	public static final String PURCHASE_ACCOUNT_LABELS = "purchaseAccountLabels";
	public static final String PURCHASE_ACCOUNT_CONFIG = "purchaseAccountConfig";
	public static final String PURCHASE_ACCOUNT_SUCCESS_MESSAGE = "purchaseAccountSuccessMessage";
	public static final String TEXT = "text";
	public static final String CTA_LABEL = "ctaLabel";
	public static final String CTA_LINK = "ctaLink";
	public static final String OPEN_NEW_IMAGE_LINK_TAB = "openNewImageLinkTab";
	public static final String CTA_TARGET = "ctaTarget";
	public static final String PRODUCT_TITLE_LABEL = "productTitle";
	public static final String BANNER = "banner";
	public static final String ACCOUNT_SUMMARY = "accountSummary";
	public static final String TEXT_IMAGE_BANNER = "textImageBanner";

	public static final String NO_ORDERS_TITLE= "noOrdersTitle";
	public static final String NO_ORDERS_INFO= "noOrdersInfo";
	public static final String CANCEL_CTA= "cancelCTA";
	public static final String CONFIRM_CTA= "confirmCTA";

	/*Certificates Labels' Constants*/
	public static final String CANCEL_LABEL = "cancel";
	public static final String CERTIFICATION_TYPE_LABEL = "certificationType";
	public static final String CERTIFICATION = "Certification";
	public static final String CONFIRM_LABEL = "confirm";
	public static final String DOCUMENT_NAME_LABEL = "documentName";
	public static final String EXPIRY_DATE_LABEL = "expiryDate";
	public static final String LINKED_ADDRESS_LABEL = "linkedAddress";
	public static final String REMOVE_LABEL = "remove";
	public static final String STATUS_LABEL = "status";

	/*Communication Settings Labels' Constants*/
	public static final String EMAIL = "email";
	public static final String EMAIL_LABEL = "emailLabel";
	public static final String EMAIL_UPPER_CASE_LABEL = "emailUpperCase";
	public static final String ABANDONED_CART_CODE = "ABANDONED_CART";
	public static final String BLOG_CODE = "BLOG";
	public static final String NEWSLETTER_CODE = "NEWS_LETTER";
	public static final String TUTORIAL_CODE = "TUTORIAL";
	public static final String WEBINAR_CODE = "WEBINAR";

	/** The Constant PROFILE */
	public static final String PROFILE = "profile/";

	/** The Constant FAMILY_NAME */
	public static final String FAMILY_NAME = "familyName";

	/** The Constant GIVEN_NAME */
	public static final String GIVEN_NAME = "givenName";


	/*Dashboard Labels' Constants*/
	public static final String DATE_LABEL = "date";
	public static final String ORDER_TOTAL_LABEL = "orderTotal";
	public static final String VIEW_ALL_LABEL = "viewAll";
	public static final String VIEW_LESS_LABEL = "viewLess";
	public static final String WELCOME_LABEL = "welcome";
	public static final String MARK_ALL_READ = "markAllRead";
	public static final String UNREAD_EMAIL = "unreadEmail";
	public static final String UNREAD_MESSAGE = "unreadMessage";
	public static final String READ_MESSAGE = "readMessage";
	public static final String UNREAD_EMAIL_CAPS = "UNREAD_EMAIL";
	public static final String UNREAD_MESSAGE_CAPS = "UNREAD_MESSAGE";
	public static final String READ_MESSAGE_CAPS = "READ_MESSAGE";
	public static final String NO_QUOTE_TITLE = "noQuoteTitle";
	public static final String NO_QUOTE_INFO = "noQuotessInfo";

	/*Orders Labels' Constants*/
	public static final String APPROVAL_STATUS_LABEL = "approvalStatus";
	public static final String BILL_TO_LABEL = "billTo";
	public static final String BILL_TO_NUMBER_LABEL = "billToNumber";
	public static final String CARRIER_NUMBER_LABEL = "carrierNumber";
	public static final String CONTACT_US_LABEL = "contactUs";
	public static final String CONTACT_US_LABEL_STRING = "contactUsLabel";
	public static final String DATE_PLACED_LABEL = "datePlaced";
	public static final String DELIVERY_METHOD_LABEL = "deliveryMethod";
	public static final String ORDER_DETAILS_STATUS_LABEL = "orderDetailsStatus";
	public static final String EST_DELIVERY_LABEL = "eSTDelivery";
	public static final String EST_DELIVERY_DATE_LABEL = "eSTDeliveryDate";
	public static final String INVOICE_NUMBER = "invoiceNumber";
	public static final String INVOICES_LABEL = "invoices";
	public static final String PAYMENT_METHOD = "paymentMethod";
	public static final String ORDERED_LABEL = "ordered";
	public static final String ORDER_NUMBER_LABEL = "orderNumber";
	public static final String ORDER_NUMBER_LABEL_TEXT = "orderNumberLabel";
	public static final String PO_NUMBER = "poNumber";
	public static final String PO_NUMBER_LABEL_TEXT = "poNumberLabel";
	public static final String ORDER_STATUS_LABEL = "orderStatus";
	public static final String PACKING_LIST_LABEL = "packingList";
	public static final String PAYMENT_TYPE_LABEL = "paymentType";
	public static final String PENDING_QTY_LABEL = "pendingQty";
	public static final String ORDERED_BY_LABEL = "orderedBy";
	public static final String ORDER_PLACED_BY_LABEL = "orderPlacedBy";
	public static final String PO_DATE_LABEL = "pODate";
	public static final String QUOTE_REFERENCE_NUMBER = "quoteReferenceNumber";
	public static final String PO_NUMBER_LABEL = "pONumber";
	public static final String PRICE_PER_UNIT_LABEL = "pricePerUnit";
	public static final String PROMOTION_LABEL = "promotion";
	public static final String QUANTITY_LABEL = "quantity";
	public static final String QUANTITY_PENDING_LABEL = "quantityPending";
	public static final String SAVE_LABEL = "save";
	public static final String SEARCH_LABEL = "search";
	public static final String SEARCH_AND_FILTER = "searchAndFilter";
	public static final String TYPE_HERE_PLACEHOLDER = "typeHerePlaceHolder";
	public static final String SHIPPING_AND_HANDLING_LABEL = "shippingAndHandling";
	public static final String SHIP_TO_LABEL = "shipTo";
	public static final String SELECT_SHIP_TO_ADDRESS = "selectShipToAddress";
	public static final String SOLD_TO_LABEL = "soldTo";
	public static final String SOLD_TO_NUMBER_LABEL = "soldToNumber";
	public static final String SOURCE_LABEL = "source";
	public static final String SUBTOTAL_LABEL = "subtotal";
	public static final String SURCHARGE_LABEL = "surcharge";
	public static final String SURCHARGES_TOOL_TIP_LABEL = "surchargesToolTip";
	public static final String TAXES_LABEL = "taxes";
	public static final String TAX_TOOL_TIP_LABEL = "taxToolTip";
	public static final String TOTAL_LABEL = "total";
	public static final String TOTAL_CAPS_LABEL = "totalCaps";
	public static final String TOTAL_PRICE_LABEL = "totalPrice";
	public static final String TRACKING_NUMBER_LABEL = "trackingNumber";
	public static final String MATERIAL_NUMBER_LABEL = "materialNoLabel";
	public static final String INVOICE_NUM = "invoiceNum";
	public static final String ORDER_NUM = "orderNum";
	public static final String PO_NUM = "pONum";
	public static final String PAY_TO_LABEL = "payTo";
	public static final String PAY_TO_LBL = "payToLabel";


	/*Shopping Lists Labels' Constants*/
	public static final String SHARE_LABEL = "share";
	public static final String VIEW_DETAILS_LABEL = "viewDetails";
	public static final String SEND_LABEL = "send";
	public static final String MORE_INFO_LABEL = "moreInfo";
	public static final String ADD_MORE_ITEMS_LABEL = "addMoreItems";
	public static final String QUOTE_NUMBER = "quoteNumber";
	public static final String DATE_CREATED = "dateCreated";
	public static final String DATE_MODIFIED = "dateModified";

	/*Quotes and Quote Lists Labels's Constants*/
	public static final String QUOTE_NUMBER_LABEL = "quoteNumberLabel";
	public static final String QUOTE_DATE_LABEL = "quoteDateLabel";
	public static final String QUOTE_STATUS_LABEL = "quoteStatusLabel";
	public static final String DATE_RANGE_LABEL = "dateRangeLabel";
	public static final String FROM_PLACEHOLDER_LABEL = "fromPlaceholderLabel";
	public static final String TO_PLACEHOLDER_LABEL = "toPlaceholderLabel";
	public static final String BILL_TO_ADDRESS = "billToAddressLabel";
	public static final String DISTRIBUTOR_NAME_LABEL = "distributorNameLabel";
	public static final String DISTRIBUTOR_EMAIL_LABEL = "distributorEmailLabel";
	public static final String DISTRIBUTOR_PHONE_NUMBER_LABEL = "distributorPhoneNumberLabel";
	public static final String ACCOUNT_MANAGER_NAME_LABEL = "accountManagerNameLabel";
	public static final String ACCOUNT_MANAGER_EMAIL_LABEL = "accountManagerEmailLabel";
	public static final String ACCOUNT_MANAGER_PHONE_NUMBER_LABEL = "accountManagerPhoneNumberLabel";
	public static final String PENDING_LABEL = "pendingLabel";
	public static final String PAYER_ADDRESS_LABEL = "payerAddressLabel";
	public static final String PAY_TO_NUMBER_LABEL = "payToNumberLabel";
	public static final String PAY_TO_NUMBER_LABEL_ALT = "paytoNumberLabel";

	/*Promotions Labels' Constants*/
	public static final String CLONE_LABEL = "clone";
	public static final String SIZE_LABEL = "size";
	public static final String PROMOTION_STATUS_LABEL = "promotionStatus";
	public static final String LIST_PRICE_LABEL = "listPrice";
	public static final String ADD_TO_CART_LABEL = "addToCart";
	public static final String CAT_NO_LABEL = "catNo";
	public static final String ENDING_IN_LABEL = "endingInLabel";
	public static final String PROMO_DETAILS = "promodetails";
	public static final String PROMOTIONS = "promotions";
	public static final String PROMO_URL = "promoUrl";
	public static final String ADD_BUTTON = "addButton";
	public static final String QUAL_PROD_HEADING = "qualifyingProdHeading";
	public static final String QUAL_PROD_DESC = "qualifyingProdDesc";
	public static final String QUAL_PROD_DATA = "qualifyingProdData";
	public static final String PROD_CODE = "prodCode";
	public static final String PRODUCT_LINK = "productLink";
	public static final String PROD_NAME = "prodName";
	public static final String FIELDS = "fields";


	/*Promotions Details' Constants*/
	public static final String PROMOTION_DETAILS = "promotionDetails";
	public static final String PROMOTION_AVAILABLE = "promotionAvailable";
	public static final String MORE_INFORMATION = "moreInformation";
	public static final String ICON_PATH = "iconPath";
	public static final String IMG_ALT = "imgAlt";
	public static final String EXPIRES = "expires";
	public static final String PROMOCODE = "promoCode";
	public static final String COPY_TO_CLIPBOARD = "copyToClipboard";
	public static final String BUTTON_TEXT = "buttonText";
	public static final String NAVIGATION_LINK = "navigationLink";
	public static final String EXPIRATION_DATE = "expirationDate";
	public static final String EXPIRATION_LABEL = "expirationLabel";


	/*Search Admin Labels' Constants*/
	public static final String SYNONYM_LABEL = "synonym";
	public static final String STOP_WORDS_LABEL = "stopWords";
	public static final String STOP_WORD_LABEL = "stopWord";
	public static final String STOP_WORDS_CAPS_LABEL = "stopWordsCaps";
	public static final String EXISTING_LABEL = "existing";
	public static final String ADD_NEW_STOP_WORD_LABEL = "addNewStopWord";
	public static final String RELOAD_SOLR = "reloadSolr";
	public static final String RELOAD_SUCCESS_MSG = "reloadSolrSuccessMsg";
	public static final String MANAGED_MAPPINGS_LABEL = "managedMappings";
	public static final String ADD_NEW_SYNONYM_LABEL = "addNewSynonym";
	public static final String FROM_LABEL = "from";
	public static final String TO_LABEL = "to";
	public static final String LANGUAGE_LABEL = "languageLabel";
	public static final String ENGLISH_LABEL = "english";
	public static final String SPANISH_LABEL = "spanish";
	public static final String ITALIAN_LABEL = "italian";
	public static final String FRENCH_LABEL = "french";
	public static final String JAPANESE_LABEL = "japanese";
	public static final String DELETE_LABEL = "delete";
	public static final String DELETE_QUESTION_LABEL = "deleteQuestion";
	public static final String MANAGED_MAPPINGS = "managedMappings";
	public static final String DELETE_SUCCESS_MSG = "deleteSuccessMsg";
	public static final String ADDED_SYNONYM = "addedSynonym";

	/**
	 * Constants for Mini Cart Label
	 */
	public static final String ITEMS_IN_CART = "itemsInCart";
	public static final String SUB_TOTAL = "subtotal";
	public static final String QUICK_ADD_LABEL = "quickAdd";
	public static final String VIEW_CART = "viewCart";
	public static final String VIEW_MORE = "viewMore";
	public static final String COMPARE_CURRENT_PDP = "compareCurrentPdp";
	public static final String VIEW_MORE_TARGET="viewMoreTarget";
	public static final String CONTINUE_SHOPPING_MODAL_TITLE="continueShoppingModalTitle";
	public static final String CONTINUE_SHOPPING_MODAL_DESCRIPTION="continueShoppingModalDescription";
	public static final String PROCEED_TO_CHECKOUT_BTN_LABEL="proceedToCheckoutBtnLabel";
	public static final String CLOSE_MODAL_BTN_LABEL="closeModalBtnLabel";
	public static final String EXPIRED_QUOTE_MODAL_TITLE="expiredQuoteModalTitle";
	public static final String EXPIRED_QUOTE_MODAL_DESCRIPTION="expiredQuoteModalDescription";
	public static final String CLEAR_CART_BTN_LABEL="clearCartBtnLabel";
	public static final String QUANTITY_MINI_CART = "quantity";
	public static final String PRICE_PER_UNIT_MINI_CART = "pricePerUnit";
	public static final String LIST_PRICE_MINI_CART = "listPrice";
	public static final String URL_CART = "urlCart";
	public static final String URL_ALT = "altCart";
	public static final String EMPTY_CART_ALT_TEXT="emptyCartAltText";
	public static final String EMPTY_CART_DESC="emptyCartDesc";
	public static final String EMPTY_CART_ANONYMOUS_USER="emptyCartAnonymousUserDesc";
	public static final String MODAL_TITLE="modalTitle";

	/**
	 * Constants for Clone and Formats
	 */
	public static final String BRAND="brand";
	public static final String BRAND_TAG_PATH = "/content/cq:tags/bdb/brands/";
	public static final String CATALOG_NO="catalogNo";
	public static final String OTHER_CLONES="otherClones";
	public static final String OTHER_FORMATS="otherFormats";
	public static final String YOUR_PRICE_CLONE_KEY="yourPrice";
	public static final String COMPARE_CLONE_KEY="compare";
	public static final String CURRENCY_CODE="currencyCode";
	public static final String RESULTS_CLONE_KEY="results";
	public static final String SELECT_ALL="selectAll";
	public static final String FILTER_RESULTS="filterResults";
	public static final String GROUP="group";
	public static final String ADD_TO_CART_ALT="cartAltText";
	public static final String ADD_TO_QUOTE_ALT="quoteAltText";
	public static final String TDS_REVISION_HP="tdsRevision";
	public static final String ALSO_KNOWN_AS="alsoKnownAs";
	public static final String IS_MOBILE="ismobile";

	/**Checkout Constants */
	public static final String CHECKOUT = "checkout";
	public static final String SHIPPING_DETAILS = "shippingDetails";
	public static final String ATTENTION = "attention";
	public static final String SHIPPING_ADDRESS = "shippingAddress";
	public static final String CHANGE_ADDRESS = "changeAddress";
	public static final String ADD_ADDRESS = "addAddress";
	public static final String PENDING_REVIEW_MSG = "pendingReviewMsg";
	public static final String DELIVER_OPTION = "deliverOptionHeading";
	public static final String OVERNIGHT = "overnight";
	public static final String INSIDE_DELIVERY = "insideDelivery";
	public static final String INSIDE_SURCHARGE_APPLIED = "insideDeliverySurcharge";
	public static final String OVERNIGHT_SURCHARGE_APPLIED = "overNightSurcharge";
	public static final String ADDRESS_NOTE = "addressNote";
	public static final String ADDRESS_LINE = "addressLine";
	public static final String BUILDING = "building";
	public static final String ROOM = "room";
	public static final String FLOOR = "floor";
	public static final String DEPARTMENT = "department";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String CITY = "city";
	public static final String ZIP_CODE = "zipCode";
	public static final String NICK_NAME = "nickName";
	public static final String CANCEL = "cancel";
	public static final String CHANGE_ADDRESS_NOTE = "changeAddressNote";
	public static final String SEARCH_ADDRESS = "searchAddress";
	public static final String DEFAULT = "default";
	public static final String SAVE = "save";
	public static final String EDIT = "edit";
	public static final String SHIPPING_METHOD = "shippingMethod";
	public static final String SAVE_AND_CONTINUE = "saveAndContinue";
	public static final String ADD_CARRIER_NUMBER = "addCarrierNumber";
	public static final String PAYMENT_DETAILS = "paymentDetails";
	public static final String PAYMENT_TYPE = "paymentType";
	public static final String PURCHASE_ORDER = "purchaseOrder";
	public static final String CREDIT_CARD = "creditCard";
	public static final String ENTER_PO_NUMBER = "enterPONumber";
	public static final String SELECT_CARD = "selectCard";
	public static final String CHANGE = "change";
	public static final String ADD_NEW_CARD = "addNewCard";
	public static final String BILLING_DETAILS = "billingDetails";
	public static final String BILLING_ADDRESS = "billingAddress";
	public static final String SAME_AS_SHIPPING_ADDRESS = "sameAsShippingAddress";
	public static final String OTHER_DETAILS = "otherDetails";
	public static final String SPECIAL_INSTRUCTION = "specialInstruction";
	public static final String SPECIAL_INSTRUCTION_LBL = "specialInstructionLbl";
	public static final String RECEIVE_ORDER_NOTIFICATION = "receiveOrderNotification";
	public static final String HAS_BOM_PRODUCT_MESSAGE = "hasBomProductMsg";
	public static final String EMAIL_VALIDATION_MSG = "emailValidationMsg";
	public static final String OTHER_NOTE = "otherNote";
	public static final String SAVE_AND_REVIEW = "saveAndReview";
	public static final String ORDER_SUMMARY = "orderSummary";
	public static final String SUBTOTAL = "subtotal";
	public static final String SHIPPING_AND_HANDLING = "shippingAndHandling";
	public static final String TAXES = "taxes";
	public static final String SURCHARGE = "surcharge";
	public static final String PROMOTION = "promotion";
	public static final String PROMOTIONS_DISCLAIMER = "promotionsDisclaimer";
	public static final String AMOUNT_SUMMARY = "amountSummary";
	public static final String INFO = "info";
	public static final String ICON = "icon";
	public static final String SRC = "src";
	public static final String CONFIRM_ORDER = "confirmOrder";
	public static final String DISCLAIMER = "disclaimer";
	public static final String TERMS_OF_USE = "termsOfUse";
	public static final String TOTAL_SAVINGS = "totalSavings";
	public static final String TOTAL = "total";
	public static final String SECONDARY_ATTENTION = "secondaryAttention";
	public static final String ATTENTION_FIELD_ERROR_MSG = "attentionFieldErrorMsg";
	public static final String MANDATORY_FIELD_ERROR_MSG = "mandatoryFieldErrorMsg";
	public static final String ATTENTION_MAX_LIMIT = "attentionMaxLimit";
	public static final String ATTENTION_MAX_LIMIT_FOR_CA = "attentionMaxLimitForCa";
	public static final String ADD_ATTENTION_MAX_LIMIT_FOR_CA = "additionalAttentionMaxLimitForCa";
	public static final String ATTENTION_MAX_LIMIT_FOR_US = "attentionMaxLimitForUs";
	public static final String ATTENTION_MAX_LIMIT_ERROR_MSG = "attentionMaxLimitErrorMsg";
	public static final String INSTRUCTIONAL_POP_UP_MSG = "instructionalPopUpMsg";
	public static final String EST_DELIVERY_DATE_CHECKOUT = "estDeliveryDate";
	public static final String ADD_NEW_ADDRESS = "addNewAddress";
	public static final String GET_SHIPPING_ADDRESS_LIST = "getShippingAddressList";
	public static final String GET_SHIPPING_DETAILS = "getShippingDetails";
	public static final String GET_BILLING_DETAILS = "getBillingDetails";
	public static final String SET_SHIPPING_DETAILS = "setShippingDetails";
	public static final String GET_ADDRESSES_LIST = "getAddressesList";
	public static final String GET_CART_DATA = "getCartData";
	public static final String GET_PAYMENT_DETAILS = "getPaymentDetails";
	public static final String GET_PAYMENT_CARDS_LIST = "getPaymentCardsList";
	public static final String SET_PAYMENT_DETAILS = "setPaymentDetails";
	public static final String SET_BILLING_DETAILS = "setBillingDetails";
	public static final String GET_OTHER_DETAILS = "getOtherDetails";
	public static final String SET_OTHER_DETAILS = "setOtherDetails";
	public static final String FAVORITE_BILLING_ADDRESS = "favoriteBillingAddress";
	public static final String ENDING_IN = "endingInLabel";
	public static final String VISA_CARD_IMG = "visaCardImgPath";
	public static final String VISA_CARD_IMG_ALT_TEXT = "visaCardImgAltText";
	public static final String MASTER_CARD_IMG = "masterCardImgPath";
	public static final String MASTER_CARD_IMG_ALT_TEXT = "masterCardImgAltText";
	public static final String AMEX_CARD_IMG = "amexCardImgPath";
	public static final String AMEX_CARD_IMG_ALT_TEXT = "amexCardAltText";
	public static final String EMAIL_VALIDATION_ERROR_MSG = "emailValidationErrorMsg";
	public static final String CC_SUCCESS_MESSAGE = "ccSuccessMessage";
	public static final String DEFAULT_SERVER_ERROR = "defaultServerError";
	public static final String SUCCESSFUL_ADDRESS_TEXT = "successfulAddressText";
	public static final String WARNING_ICON_PATH = "warningIconPath";
	public static final String WARNING_ICON_ALT_TEXT = "warningIconAltText";
	public static final String BILL_TO_PENDING_REVIEW_MSG = "billToPendingReviewMsg";
	public static final String PLACE_ORDER_CONFIG = "placeOrderConfig";
	public static final String BACK_TO_CART_CTA ="backToCartCta";
	public static final String BACK_ICON_PATH ="backIconPath";
	public static final String BACK_ICON_ALT_TEXT ="backIconAltText";
	public static final String REVIEW_CTA_LABEL ="reviewCtaLabel";
	public static final String REMOVE_CTA_LABEL ="removeCtaLabel";
	public static final String ALERT_HEADING_LABEL ="alertHeadingLabel";
	public static final String ALERT_MESSAGE ="alertMessage";
	public static final String DUPLICATE_ORDER_HEADING_LABEL ="duplicateOrderHeadingLabel";
	public static final String DUPLICATE_ORDER_TEXT1 = "duplicateOrderText1";
	public static final String DUPLICATE_ORDER_TEXT2 ="duplicateOrderText2";
	public static final String MAX_EMAIL_ID_ERROR_MSG ="maxEmailIdsErrorMsg";
	public static final String REQUEST_SAME_LOT_NUM_MSG = "requestSameLotNumMsg";
	public static final String PROMO_CODE_LABEL = "promocodeLabel";
	public static final String YOU_SAVED_LABEL = "youSavedLabel";
	public static final String ON_LABEL = "onLabel";
	public static final String GET_PRODUCT_ANNOUNCEMENTS = "getProductAnnouncements";
	public static final String DISMISS_CTA = "dismissCta";
	public static final String BACK_TO_CART_ALERT_MSG = "backToCartAlertMsg";
	public static final String REVIEW_TAX_EXEMPT_MSG = "reviewTaxExemptMsg";
	public static final String TAX_EXEMPT_MSG = "taxExemptMsg";
	public static final String BACK_TO_CART_TITLE = "backToCartTitle";
	public static final String DISTRIBUTOR_DETAILS = "distributorDetails";
	public static final String DISTRIBUTOR_ADDRESS_LABEL = "distributorAddressLabel";
	public static final String NAME_LABEL = "nameLabel";
	public static final String BILLING_DETAILS_HEADING = "billingDetailsHeading";
	public static final String VAT_EXEMPT_MSG = "VATExemptMsg";
	public static final String ERROR_ICON = "errorIcon";
	public static final String ERROR_ICON_ALT_TEXT = "errorIconAltText";
	public static final String ERROR_MSG = "errorMsg";
	public static final String INACTIVE_BILL_TO_MSG = "inActiveBilltoMsg";
	public static final String INACTIVE_SOLD_TO_PAYER_MSG = "inActiveSoldtoPayerMsg";
	public static final String INACTIVE_SHIP_TO_MSG = "inActiveShiptoMsg";
	public static final String INACTIVE_SHIP_TO_ERROR_ICON = "inactiveShipToErrorIcon";
	public static final String INACTIVE_SHIP_TO_ERROR_ICON_ALT_TEXT = "inactiveShipToErrorAltText";
	public static final String INACTIVE_SHIP_TO_ERROR_MSG = "inactiveShipToErrorMsg";
	public static final String REF_PO_NUM_PLACEHOLDER = "refPoNumPlaceholder";
	public static final String REF_PO_TOOLTIP_MSG = "refPoTooltipMsg";
	public static final String TOOL_TIP_IMAGE_ALT_TEXT = "tooltipImgAltText";
	public static final String REF_PO_NUM_MAX_LIMIT = "refPoNumMaxLimit";
	public static final String SELECT_CARD_LABEL = "selectCardLabel";
	public static final String AVAILABLE_CARDS_LABEL = "availableCardsLabel";
	public static final String ADD_DROPSHIP_ADDRESS_LABEL = "addDropshipAddrLabel";
	public static final String ACCOUNT_NUMBER_LABEL = "accountNumberLabel";
	public static final String SET_DROPSHIP_DETAILS = "setDropshipdetails";
	public static final String BACK_TO_CART = "backToCart";
	public static final String PRICE_CHANGED = "priceChanged";


	/**
	 * Constants for My Cart Grants Component
	 */
	public static final String NO_GRANTS_MSG = "noGrantsMsg";
	public static final String USE_MY_GRANTS_LABEL = "useMyGrantsLabel";
	public static final String GRANTS_LEFT_LABEL = "grantsLeftLabel";
	public static final String CHANGE_GRANTS = "change";
	public static final String NOT_ELIGIBLE_PRODUCT_MSG = "notEigibleProductMsg";
	public static final String GRANTS_INFO_TOOLTIP_TEXT = "grantsInfoTooltipText";
	public static final String ISSUE_ON_GRANT_PRODUCTS_MSG = "issueOnGrantProductsMsg";
	public static final String GRANT_TOTAL_EXCEEDS_MSG = "grantTotalExceedsMsg";
	public static final String SELECT_GRANT = "selectGrant";
	public static final String GRANTS_LEFT = "grantsLeft";
	public static final String SHIP_TO_NUMBER = "shipToNumber";
	public static final String STARTING_FROM = "startingFrom";
	public static final String VALID_UNTIL = "validUntil";
	public static final String USER_TO_SELECT_GRANT_TEXT = "userToSelectGrantText";
	public static final String CANCLE_BTN_TEXT = "cancleBtnText";
	public static final String USE_GRANT_BTN_TEXT = "useGrantBtnText";
	public static final String APPLY_GRANTS_ON_CART = "applyGrantsOnCart";
	public static final String GRANT_WARNING_ALT_TEXT = "grantWarningAltText";
	public static final String OUT_OF_TEXT = "outOfText";
	public static final String TEXT1 = "text1";
	public static final String TEXT2 = "text2";
	public static final String TOOLTIP_ALT_TEXT = "toolTipAltText";

	/** Endpoints */
	public static final String SHOPPING_LIST_URI = "/occ/v2/{{site}}/users/{userId}/account/shoppinglist";

	public static final String SPECIAL_MESSAGE_STATUS = "smessageStatus";
	public static final String SPECIAL_MESSAGE = "specialMessage";
	public static final String FAQ_MAP = "faqMap";
	public static final String FAQ_LIST = "faqList";
	public static final String SPECIAL_PRODUCTS = "products";
	public static final String SPECIAL_PRODUCT_ID = "productId";
	public static final String SPECIAL_REGION = "region";
	public static final String PRODUCT_ANNOUNCEMENT_MSG = "productAnnouncementMsg";
	public static final String FAQ_HEADING = "faqHeading";
	public static final String SPECIAL_MORE_INFO_LABEL = "moreInfoLabel";
	public static final String MORE_INFO_HREF = "moreInfohref";
	public static final String MORE_INFO_TARGET = "moreInfoTarget";
	public static final String VIEW_MORE_FAQ_LABEL = "viewMoreFaqLabel";
	public static final String VIEW_MORE_FAQ_HREF = "viewMoreFaqHref";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String SURCHARGE_DISCLAIMER = "surchargeDisclaimer";
	public static final String IS_PRINT_PDF = "isPrintPDF";


	/**
	 * Search results component constants
	 */
	public static final String CLEAR_ALL = "clearAll";
	public static final String SHOW_LABEL = "show";
	public static final String FILTER_BY_CATEGORY = "filterbyCategory";
	public static final String NEXT_LABEL = "nextLabel";
	public static final String PREV_LABEL = "prevLabel";
	public static final String UP_ARROW_IMAGE = "upArrowImage";
	public static final String UP_ARROW_ALT_TEXT = "upArrowAltText";
	public static final String DOWN_ARROW_IMAGE = "downArrowImage";
	public static final String DOWN_ARROW_ALT_TEXT = "downArrowAltText";
	public static final String DOWN_ARROW_MOBILE = "downArrowMobile";
	public static final String SHOWING_LABEL = "showing";
	public static final String RESULTS_FOR = "resultsFor";
	public static final String COMPARE_LABEL = "compare";
	public static final String COMPARE_LABEL_KEY = "compareLabel";
	public static final String ADD_TO_CART_LABEL_KEY = "addToCartLabel";
	public static final String SAVE_TO_SHOPPING_LIST_KEY = "saveToShoppingList";
	public static final String CART_IMAGE = "cartImage";
	public static final String CLOSE_ICON_TEXT = "closeIconText";
	public static final String CART_ALT_TEXT = "cartAltText";
	public static final String DROPDOWN_LIST = "dropdownList";
	public static final String RESPONSE = "response";
	public static final String TOTALRESULTS = "totalResults";
	public static final String RESULTS = "results";
	public static final String FACETS = "facets";
	public static final String LABEL = "label";
	public static final String LABELS = "labels";
	public static final String COUNT = "count";
	public static final String Q_KEYWORD = "q";
	public static final String SEARCH_COLON = "*:*";
	public static final String FACET_FIELDS = "facet-fields";
	public static final String SELECTED_FIELDS = "selected-fields";
	public static final String START = "start";
	public static final String ROWS = "rows";
	public static final String VALUE = "value";
	public static final String UNDERSCORE_GA = "_ga";
	public static final String OPTIONLABEL = "optionLabel";
	public static final String OPTIONVALUE = "optionValue";
	public static final String DROPDOWN = "dropdown";
	public static final String FACETLABEL = "facetLabel";
	public static final String FACETVALUE = "facetValue";
	public static final String FACET = "facet";
	public static final String TARGET_MW = "targetMW";
	public static final String ASSAY_RANGE = "assayRange";
	public static final String NEUTRALIZATION_ACTIVITY = "neutralizationActivity";
	public static final String CONST_TARGET_MW = "Target Molecular Weight";
	public static final String CONST_ASSAY_RANGE = "Assay Range";
	public static final String CONST_NEUTRALIZATION_ACTIVITY = "Neutralization Activity";
	public static final String SPACE = " ";
	public static final String PERPAGERESULTS = "perPageResults";
	public static final String REPLACED_PRODUCT_ID = "replacedProductId";
	public static final String PRICINGDETAILS = "getPricingDetails";
	public static final String PRODUCT_ANNOUNCEMET = "getProductAnnouncementService";
	public static final String PDP_ATTRIBUTES_STATUS = "pdpAttributesStatus";
	public static final String MPGCATEGORY = "mpgCategory:";
	public static final String MPG_KEYWORD =  "MPG";
	public static final String CATEGORY_KEYWORD = "Category";
	public static final String CLASSIFICATION_CATEGORY = "classificationCategory:";
	public static final String OR_KEYWORD = "OR";
	public static final String PROMODETAILS = "promodetails";
	public static final String COMPANION_DETAILS_SELECTOR = "companionproducts";
	public static final String APPLICABLECATEGORIES = "applicableCategories";
	public static final String APPLICABLEPRODUCTS = "applicableProducts";
	public static final String EXCLUDEDCATEGORIES = "excludedCategories";
	public static final String EXCLUDEDPRODUCTS = "excludedProducts";
	public static final String PROMOTIONDETAILS = "promotiondetails";
	public static final String SEARCHRESULTS = "searchResults";
	public static final String SEARCHINSTEAD = "searchInstead";
	public static final String SUGGESTION = "suggestion";
	public static final String WORD = "word";
	public static final String NAME_KEYWORD = "name:";
	public static final String SPELL_SELECTOR = "/spell";
	public static final String DF_KEYWORD = "df";
	public static final String SPELLCHECK_TEXT = "bdbiospellchecker_text_mv";
	public static final String SPELLCHECK_Q = "spellcheck.q";
	public static final String SPELLCHECK = "spellcheck";
	public static final String WT_KEYWORD = "wt";
	public static final String SEARCH_PAGE_PATH = "searchResultPagePath";
	public static final String REP_STATUS_ACTIVATE = "Activate";
	public static final String BOOSTRULES = "boostrules";
	public static final String IS_RANGE_SELECTOR = "isRangeSelector";
	public static final String UNITLABEL = "unitLabel";
	public static final String STEP = "step";
	public static final String MIN = "min";
	public static final String MAX = "max";
	public static final String COUNTRY_OF_ORIGIN ="countryOfOrigin";
	public static final String FACET_SORT ="facet.sort";
	public static final String SORT ="sort";
	public static final String SORT_VALUE ="score desc,validityStartDate_dt desc";
	public static final String FACET_SORT_VALUE ="index";
	public static final String F_TDS_CLONE_NAME_FACET_S_SORT ="f.tdsCloneName_facet_s.facet.sort";
	public static final String F_TDS_CLONE_NAME_FACET_S_LIMIT ="f.tdsCloneName_facet_s.facet.limit";
	public static final String CONST_25 ="25";
	public static final String CONST_200 ="200";
	public static final String FACET_LIMIT ="facet.limit";
	public static final String MODIFY_SEARCH_TEXT = "modifySearchText";
	public static final String TDS_FILE_PATH= "tdsFilePath";
	
	public static final String BRIGHT_COVE_ACCOUNT_ID= "brightcoveAccountId";
	public static final String BRIGHT_COVE_PLAYER_ID= "brightcovePlayerId";

	public static final String GET_COMPANION_PRODUCTS = "getCompanionProducts";

	/**
	 * Amount summary component constants
	 */
	public static final String AMOUNT_HEADING  = "heading";
	public static final String AMOUNT_SHIP_HANDLING  = "shippingHandling";
	public static final String AMOUNT_TAXES  = "taxes";
	public static final String AMOUNT_SURCHARGE  = "surcharge";
	public static final String AMOUNT_TOTAL  = "total";
	public static final String AMOUNT_SUMMARY_CTA_BTN  = "amountSummaryCTABtn";
	public static final String AMOUNT_REQUEST_QUOTE_CTA_LINK  = "requestQuoteCTALink";
	public static final String AMOUNT_DISCLAIMER  = "disclaimer";
	public static final String AMOUNT_TERMS_OF_USE  = "termsOfUse";
	public static final String AMOUNT_TAX_DISCLAIMER_TEXT  = "taxDisclaimerText";
	public static final String AMOUNT_VAT_EXEMPT  = "VATExempt";
	public static final String AMOUNT_SURCHARGES_TOOLTIP  = "surchargesToolTip";
	public static final String AMOUNT_TAX_TOOLTIP  = "taxToolTip";
	public static final String AMOUNT_TOOLTIP_ALT_TEXT  = "toolTipAltText";
	public static final String AMOUNT_SUBTOTAL  = "subtotal";
	public static final String AMOUNT_SUBTOTAL_PAGE  = "subTotal";
	public static final String UPDATES_VAT_EMEMPT_STATUS  = "updatesVATExemptStatus";
	public static final String VALIDATE_MY_CART  = "validateMyCart";
	public static final String NO_ADDRESS_LABEL = "noAddressLabel";
	public static final String SELECT_ADDRESS_CTA  = "selectAddressCTALabel";
	public static final String SELECT_ADDRESS_TITLE  = "selectAddressTitle";
	public static final String LINKED_ADDRESS_TITLE  = "linkedAddressTitle";
	public static final String CHANGE_ADDRESS_CTA  = "changeAddressCTATitle";
	public static final String SHIP_TO_LBL  = "shipToLabel";
	public static final String SHIPPING_ADDRESS_LBL  = "shippingAddressLabel";
	public static final String DEFAULT_LBL  = "defaultLabel";
	public static final String SHIP_TO_NUMBER_LBL  = "shipToNumberLabel";
	public static final String SHIP_TO_NUMBER_LABEL_ALT  = "shiptoNumberLabel";
	public static final String TITLE  = "title";
	public static final String SEARCH_PLACE_HOLDER  = "searchPlaceHolder";
	public static final String FAVOURITE_ADDRESS  = "favoriteAddress";
	public static final String NO_FAVOURITE_DESCRIPTION  = "noFavouriteDescription";
	public static final String FAV_ICON  = "favIcon";
	public static final String FAV_ICON_ALT_TEXT  = "favIconAltTxt";
	public static final String UP_ARROW_ICON  = "upArrowIcon";
	public static final String DOWN_ARROW_ICON  = "downArrowIcon";
	public static final String ADDRESS_LABELS  = "addressLabels";
	public static final String REQUEST_PAY_LOAD  = "requestPayload";
	public static final String GET_ADDRESS_CONFIG  = "getAddressConfig";
	public static final String TOTAL_PROMO_SAVINGS  = "totalPromoSavings";
	public static final String APPLY_COUPON = "applyCoupon";
	public static final String CONST_SAVE_CTA = "saveCTA";
	public static final String NO_SEARCH_RESULTS = "noSearchResults";
	public static final String UPDATE_ADDRESS_CONFIG = "updateAddressConfig";
	public static final String PUNCHOUT_TRANSMISSION_REQUEST = "punchoutTransmitRequest";
	public static final String PUNCHOUT_CANCEL_REQUEST = "punchoutCancelRequest";
	public static final String TRANSMIT_REQUEST = "transmitRequest";
	public static final String CANCEL_CART = "cancelCart";
	public static final String TAX_EXEMPT_NOTIFICATION = "taxExemptNotification";
	public static final String FREE = "free";
	public static final String GRANT_AMOUNT_USED = "grantAmountUsed";
	public static final String SHIPPED_QUANTITY_LABEL = "shippedQuantityLabel";

	/**
	 * Constants for Quick Add Component
	 */
	public static final String HEADING_QUICK_ADD = "heading";
	public static final String ADD_ITEMS_TABS = "addItemsTabs";
	public static final String ADD_ITEMS_HEADING = "addItemsHeading";
	public static final String QUICK_ADD_TITLE = "quickAddTitle";
	public static final String ADD_ITEMS_INFO = "addItemsInfo";
	public static final String TEXT_ENTRY_LABELS = "textEntryLables";
	public static final String TAB_TITLE = "tabTitle";
	public static final String TEXT_ENTRY_INFO = "textEntryInfo";
	public static final String CATALOG_NUM_QUICK_ADD = "catalogNum";
	public static final String CATALOG_NUM_TITLE_QUICK_ADD = "catalogNumTitle";
	public static final String QUANTITY_QUICK_ADD = "quantity";
	public static final String QUANTITY_TITLE_QUICK_ADD = "quantityTitle";
	public static final String ADD_MORE_ITEMS = "addMoreItems";
	public static final String BLANK_FIELD_ERROR = "blankFieldError";
	public static final String BULK_PASTE_LABELS = "bulkPasteLabels";
	public static final String SAVE_LIST_CTA = "saveListCTA";
	public static final String PLACEHOLDER_QUICK_ADD = "placeholder";
	public static final String BULK_PASTE_INFO = "bulkPasteInfo";
	public static final String EXCEL_UPLOAD_LABELS = "excelUploadLabels";
	public static final String UPLOAD_INFO = "uploadInfo";
	public static final String DOWNLOAD_INFO = "downloadInfo";
	public static final String DOWNLOAD_CTA = "downloadCTA";
	public static final String DOWNLOAD_URL = DamConstants.DOWNLOAD_URL;
	public static final String FILE_UPLOAD_LABELS = "fileUploadLabels";
	public static final String INFO_QUICK_ADD = "info";
	public static final String ICON_SRC_QUICK_ADD = "iconSrc";
	public static final String ALT_TEXT_QUICK_ADD = "altText";
	public static final String TITLE_QUICK_ADD = "title";
	public static final String NOTE_QUICK_ADD = "note";
	public static final String BTN_NAME = "btnName";
	public static final String BULK_ENTRY_QUICK_ADD = "bulkEntry";
	public static final String BULK_UPLOAD_QUICK_ADD = "bulkUpload";
	public static final String CANCEL_QUICK_ADD = "cancelQuickAdd";
	public static final String ARIA_LABEL_CLOSE = "ariaLabelClose";
	public static final String FILE_ICON_ALT_TEXT = "fileIconAltText";
	public static final String FILE_ICON_SRC = "fileIconSrc";
	public static final String ARIA_LABEL_QUICK = "ariaLabel";
	public static final String FIELD_TITLE = "fieldTitle";

	/**
	 * My Cart print component constants
	 */
	public static final String IS_PRINT  = "isPrint";
	public static final String MY_CART_PRINT_PAGE  = "myCartPrintPage";
	public static final String GET_CART_WITH_IDENTIFIER = "getCartWithIdentifier";
	public static final String ICONS_LIST = "iconsList";

	/** The Constant GRANTS_ORDER_HISTORY_QUERY_PARAM. */
	public static final String GRANTS_ORDER_HISTORY_QUERY_PARAM = "grantId={grantId}";

	/** The Constant for Search Admin Page Servlet. */
	public static final String BLANK = "Blank";

	/** Constants for Add New Address Checkout. */
	public static final String ADD_ADDRESS_HEADING="addressLabel";
	public static final String SET_AS_DEFAULT="setAsDefaultLabel";
	public static final String ADD_ADDRESS_CTA="addAddressCta";

	/** contants related to workflow and querybuilder */
	public static final String LIMIT_MINUS_ONE = "-1";
	public static final String PROPERTY_STRING = "property";
	public static final String PROPERTY_DOT_VALUE_STRING = "property.value";
	public static final String BASE_WITH_SLASH = "base/";
	public static final String DOT_PDF = ".pdf";
	public static final String SLASH_PDF = "pdf/";
	public static final String METADATAPATH = "/jcr:content/metadata";
	public static final String METADATA ="metadata";


	public static final String ADD_ADDRESS_NOTE="addrAddAddrDescLabel";
	public static final String ADD_ADDRESS_CANCEL="addrFormCancelLabel";
	public static final String ADD_ADDRESS_LABEL="addrAddLabel";
	public static final String ADD_ADDRESS_COMPANY_LABEL="companyLabel";
	public static final String ADDRESS_LINE_1="addressLabelFirst";
	public static final String ADDRESS_LINE_2="addressLabelSecond";
	public static final String STREET_ADDRESS="streetAddressLabel";
	public static final String DETAILED_ADDRESS="detailAddressLabel";
	public static final String ADDRESS="addressLabel";
	public static final String MANDATORY_FIELD_ERROR="mandatoryFieldError";
	public static final String ADD_ADDRESS_BUILDING="buildingLabel";
	public static final String ADD_ADDRESS_FLOOR="floorLabel";
	public static final String ADD_ADDRESS_ROOM="roomLabel";
	public static final String ADD_ADDRESS_DEPARTMENT="departmentLabel";
	public static final String ADD_ADDRESS_PHONE_NUMBER="phoneNumberLabel";
	public static final String ADD_ADDRESS_PHONE_NUMBER_ERROR="phoneNumberLabelError";
	public static final String ADD_ADDRESS_DISTRICT_LABEL="districtLabel";
	public static final String ADD_ADDRESS_CITY_LABEL="cityLabel";
	public static final String ADD_ADDRESS_STATE_LABEL="stateLabel";
	public static final String ADD_ADDRESS_PROVINCE_LABEL="provinceLabel";
	public static final String ADD_ADDRESS_POSTALCODE_LABEL="postalCodeLabel";
	public static final String ADD_ADDRESS_PINCODE_LABEL="pinCodeLabel";
	public static final String ADD_ADDRESS_ZIPCODE_LABEL="zipCodeLabel";
	public static final String ADD_ADDRESS_COUNTRY_LABEL="countryLabel";
	public static final String CHANGE_BILLING_ADDRESS_LABEL="changeBillingAddressLabels";
	public static final String SHIPTO_NUMBER_LABEL="shipToNumberLabel";
	public static final String BILLTO_NUMBER_LABEL="billToNumberLabel";
	public static final String PO_NUMBER_ERROR_MESSAGE="poNumberErrorMsg";
	public static final String PONUMBER_LABEL="poNumberLabel";
	public static final String EXPIRATION_WARNING_MESSAGE="expirationWarningMessage";
	public static final String EXPIRED_MESSAGE="expiredMessage";
	public static final String EXPIRY_DATE="expiryDateLabel";
	public static final String NAME_ON_CARD_LABEL="nameOnCardLabel";
	public static final String CVV_LABEL="cvvLabel";
	public static final String REFERENCE_PO_NUMBER="referencePoNumber";
	public static final String CARD_FOR_PAYMENT_LABEL="cardforPaymentLabel";

	/** Akamai constants **/
	public static final String CONST_BDB_DAM_ROOT_PATH = "/content/dam/bdb";
	public static final String BDB_XF_ROOT_PATH = "/content/experience-fragments/bdb";
	public static final String BDB_CONF_ROOT_PATH = "/conf/bdb-aem";
	public static final String BDB_XF_RESOURCE_TYPE = "";
	public static final int SIZE_2B = 2048;
	public static final int SIZE_4B = 4096;

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

	/** Constants for Order Confirmation & Quote Confirmation */

	public static final String PENDING_MESSAGE = "PendingMessage";
	public static final String SUCCESS_MESSAGE = "successMessage";
	public static final String CANCEL_MESSAGE = "cancelMessage";
	public static final String DEFAULT_PRD_IMAGE_ALT_TEXT = "defaultPRDImageAltText";
	public static final String DEFAULT_PRD_IMAGE = "defaultPRDImage";
	public static final String PRODUCTS_HEADING = "productsHeading";
	public static final String PENDING = "pending";
	public static final String SOLDTO_NUMBER_LABEL = "soldtoNumberLabel";
	public static final String SOLD_TO_NUMBER = "soldToNumberLabel";
	public static final String SOLD_TO_LBL = "soldToLabel";
	public static final String BILL_TO_LBL = "billToLabel";
	public static final String ADDRESS_DETAILS_HEADING = "addressDetailsHeading";
	public static final String CANCEL_DISMISS_CTA = "CancelDismissCTA";
	public static final String CANCEL_CONFIRM_CTA = "CancelConfirmCTA";
	public static final String CANCEL_ORDER_INSTRUCTION = "cancelOrderInstruction";
	public static final String CANCEL_ORDER_TILE = "CancelOrderTile";
	public static final String PRINT_ORDER_CTA = "printOrderCTA";
	public static final String CANCEL_ORDER_CTA = "cancelOrderCTA";
	public static final String CONTINUE_SHIPPING_CTA = "continueShoppingCTA";
	public static final String PLACE_ANOTHER_OTHER = "placeAnotherOther";
	public static final String SPECIAL_INSTRUCTIONS_TEXT = "specialInstructionsText";
	public static final String SHIPPING_OPTION = "shippingOption";
	public static final String DELIVERY_OPTION = "deliveryOption";
	public static final String CARD_NUMBER = "cardNumber";
	public static final String ORDER_BY = "orderBy";
	public static final String SAVECTA = "saveCTA";
	public static final String EDIT_CTA = "editCTA";
	public static final String ORDER_DETAILS_HEADING = "orderDetailsHeading";
	public static final String SPECIAL_INSTRUCTIONS = "specialInstructions";
	public static final String CONTACT_US_LINK = "contactUSLink";
	public static final String ORDER_NUMBER_TEXT = "orderNumberText";
	public static final String ORDER_NUMBERTEXT = "orderNumbertext";
	public static final String PO_DATE_LBL = "poDate";
	public static final String SHIPPING_HANDLING = "shippingAndHandling";
	public static final String AMOUNT_SUMMARY_HEADING = "amountSummaryHeading";
	public static final String QUOTATION_CURRENCY = "quotationCurrency";
	public static final String REQUESTED_DATE = "requestedDate";
	public static final String REQUESTED_BY = "requestedBy";
	public static final String GET_QUOTE_INFORMATION = "getQuoteInformation";
	public static final String CONFIRMATION_INFORMATION = "confirmationInformation";
	public static final String ACCOUNT_MANAGER_INFORMATION = "accountManagerInformation";
	public static final String DISTRIBUTOR_INFORMATION = "distributorInformation";
	public static final String PRINT_ORDER_LABEL_TEXT = "printOrderLabelText";
	public static final String CONTINUE_SHOPPING_LABEL_TEXT = "continueShoppingLabelText";
	public static final String ORDER = "{order}?";
	public static final String CANCEL_ORDER_CONFIG = "cancelOrderConfig";
	public static final String GET_CHECKOUT_DATA = "getCheckoutData";
	public static final String IS_QUOTE_DETAILS = "isQuoteDetails";
	public static final String QUOTE_DETAILS_HEADING = "quoteDetailsheading";
	public static final String ORDER_NUMBER = "OrderNumber";
	public static final String BILLTO_NUMBER = "billtoNumberLabel";
	public static final String SHIPTO_NUMBER = "shiptoNumberLabel";

	/**
	 * Compare Products constants
	 */
	public static final String PRODUCT_COMPARISON  = "productComparison";
	public static final String COMPARE_HISTOGRAMS  = "compareHistograms";
	public static final String COMPARE_HISTOGRAM_HEADING = "compareHistogramHeading";
	public static final String EST_DELIVERY_DATE_LABEL_CP = "estDeliveryDateLabel";
	public static final String ADD_TO_SHOPPING_LIST_LABEL  = "addToShoppingListLabel";
	public static final String COMPARISON_TABLLE_LABELS  = "comparisionTableLabels";
	public static final String BRAND_CP = "brand";
	public static final String ALTERNATIVE_NAME = "alternativeName";
	public static final String VOL_PER_TEST  = "volPerTest";
	public static final String ISOTYPE  = "isotype";
	public static final String REACTIVITY = "reactivity";
	public static final String APPLICATION = "application";
	public static final String IMMUNOGEN  = "immunogen";
	public static final String WORKSHOP_NUMBER  = "workshopNumber";
	public static final String ENTREZ_GENE_ID = "entrezGeneID";
	public static final String STORAGE_BUFFER = "storageBuffer";
	public static final String REGULATORY_STATUS  = "regulatoryStatus";
	public static final String EXCITATION_SOURCE  = "excitationSource";
	public static final String EXCITATION_MAX_CP = "excitationMax";
	public static final String EMISSION_MAX = "emissionMax";
	public static final String CURRENT_FORMAT = "currentFormat";
	public static final String OTHER_FORMATS_CP = "otherFormats";
	public static final String ADD_TO_CART_CP = "addToCartLabel";
	public static final String COMPARISON_TABLE_LABELS = "comparisionTableLabels";
	public static final String GET_PRODUCT_DETAILS = "getProductDetails";
	public static final String GET_PRICE_DATA = "getPriceData";
	public static final String VIEW_MORE_LABEL_KEY = "viewMoreLabel";
	public static final String BACK_BUTTON_LABEL = "backButton";

	/** The Events Details constants  */
	public static final String UPCOMING = "upcoming";
	public static final String PAST = "past";
	public static final String CURRENT = "current";
	public static final String AM = "AM";
	public static final String AM_LOWERCASE = "a.m.";
	public static final String PM = "PM";
	public static final String PM_LOWERCASE = "p.m.";


	/**
	 * punchout Servlet constants
	 *
	 */
	public static final String KEY = "key";
	public static final String AUTH_KEY = "authKey";
	public static final String SID = "sid";
	public static final String OPERATION = "operation";
	public static final String URL_KEYWORD_AMP = "amp;";
	public static final String PRODUCT_CODE_KEYWORD = "productCode";
	public static final String DISPLAY_SHIPTO = "displayShipto";
	public static final String ADDRESS_ID = "addressId";
	public static final String DISPLAY_MY_ORDERS = "displayMyOrders";


	/**
	 * My Quotes Constant
	 *
	 */
	public static final String QUOTE_DETAILS = "quoteDetails";
	public static final String DIRECT_IMPORT_LABEL = "directImportLabel";
	public static final String LOCAL_LABEL = "localLabel";
	public static final String INSTRUCTIONS_PLACE_HOLDER = "instructionsPlaceHolder";
	public static final String GET_QUOTE_CTA = "getQuoteCta";
	public static final String UPGRADE_ACCOUNT = "upgradeAccount";
	public static final String DASHBOARD_BUTTON = "dashboardButton";
	public static final String CANCEL_LINK = "cancelLink";
	public static final String QUICK_QUOTE_TOAST_MSG = "quickQuoteToastMsg";
	public static final String QUICK_QUOTE_FILL_ALL_ERROR = "quickQuoteFillAllError";
	public static final String QUICK_QUOTE_INVALID_PRODUCT_ERROR = "quickQuoteInvalidProductError";
	public static final String QUICK_QUOTE_PRODUCT_NOT_AVAILABLE_ERROR = "quickQuoteProductNotAvailableError";
	public static final String ADD_PRODUCT_TO_SFL_QUOTE_MSG = "addProductToSFLQuoteMsg";
	public static final String REMOVE_ITEMS_FROM_QUOTE_MSG = "removeItemsFromQuoteMsg";
	public static final String EMPTY_QUOTE = "emptyQuote";
	public static final String EMPTY_QUOTE_ICON = "emptyQuoteIcon";
	public static final String ADD_TO_QUOTE_LIST = "addToQuoteList";
	public static final String QUOTE_ID = "quoteId";
	public static final String MY_QUOTE = "myQuote";
	public static final String GET_QUOTE = "getQuote";
	public static final String ADDRESS_DETAILS = "addressDetails";

	/** The Compare Products Toolbar Constants  */
	public static final String COMPARE_LABEL_TOOLBAR = "compareLabel";
	public static final String CLEAR_ALL_LABEL = "clearAllLabel";
	public static final String REMOVE_LABEL_TOOLBAR = "removeLabel";
	public static final String COMPARE_MESSAGE_MOBILE = "compareMessageMobile";
	public static final String COMPARE_MESSAGE_DESKTOP = "compareMessageDesktop";
	public static final String MIN_PRODUCTS = "minProducts";
	public static final String MAX_PRODUCTS = "maxProducts";
	public static final String COMPARE_PAGE_URL = "comparePageUrl";

	/** The Required Companion Products Constants  */
	public static final String COMPANION_MATERIAL_NUMBER = "companionMaterialNumber";
	public static final String COMPANION_TYPE = "companionType";
	public static final String COMPANION_ORDINAL = "companionOrdinal";
	public static final String CONJUGATE = "conjugate";

	/** Mini Quote Labels */
	public static final String URL_QUOTE="urlQuote";
	public static final String ALT_QUOTE="altQuote";
	public static final String ITEMS_IN_QUOTE="itemsInQuote";
	public static final String QUICK_QUOTE="quickQuote";
	public static final String VIEW_QUOTE="viewQuote";
	public static final String EMPTY_QUOTE_DESC="emptyQuoteDesc";
	public static final String EMPTY_QUOTE_ICON_ALT="emptyQuoteAltText";


	/** tiles/eventBlog component */
	public static final String PARENTPAGEPATH = "parentPagePath";
	public static final String DEACTIVATION_DATE = "deactivationDate";
	public static final String CQ_PAGE = NameConstants.NT_PAGE;
	public static final String EVENTS = "events";
	public static final String TOPICS = "topics";
	public static final String DATES = "dates";
	public static final String CODE_TARGET = "codeTarget";
	public static final String CHILDREN = "children";
	public static final String EVENT = "event";
	public static final String DATE_TYPE = "dateType";
	public static final String SCHEDULE_ON = "scheduledOn";
	public static final String MONTH_YEAR = "month&year";
	public static final String IS_SELECTED = "isSelected";


	/** Testimonial Update */
	public static final String TESTIMONIAL_URL="testimonialUrl";
	public static final String JCR_CONTENT_PATH="/jcr:content/root/responsivegrid";
	public static final String TESTIMONIAL_DATATYPE="bdb-aem/proxy/components/content/testimonialcarousel";


	/** Order Inquiry Update */
	public static final String AREA_OF_INQUIRY_LABELS = "areaOfInquiryLabel";
	public static final String  MANDATORY_ERROR_LABELS = "mandatoryErrorLabel";
	public static final String  PHONE_NUMBER_PATTERN_ERROR = "phoneNumPatternError";
	public static final String  COMMENTS_LABEL = "commentsLabel";
	public static final String  INQUIRY_NOTE = "inquiryNote";
	public static final String  TNC_NOTE = "tncNote";
	public static final String  SUBMIT_BTN_LABEL = "SubmitBtnLabel";
	public static final String  SUBMIT_BTN = "submitBtnLabel";
	public static final String  CANCEL_BTN_LABEL = "cancelBtnLabel";
	public static final String  SUCCESS_TITLE = "successTitle";
	public static final String  SUCCESS_SUB_TITLE = "successSubTitle";
	public static final String  ASK_QUESTION_CTA_LABEL = "askQuestionCTALabel";
	public static final String  BACK_TO_ORDER_CTA_LABEL = "backToOrderCTALabel";
	public static final String  ASK_QUESTION_CTA_LINK = "askQuestionCTALink";
	public static final String  INVOICE_NUMBER_LABEL = "invoiceNumberLabel";
	public static final String  EMAIL_FORMAT_ERROR_LABEL = "emailFormatError";

	/** Performance Constants */
	public static final String SUB_TITLE = "subTitle";
	public static final String IMAGE_LINK_URL = "imageLinkUrl";

	/** The Constants for beadlot functionality */
	public static final String DOC_DESCRIPTION = "documentDescription";
	public static final String PART_NUMBER = "partNumber";
	public static final String BEADLOT_BASE_PATH = "/content/commerce/products/bdb/beadlots";
	public static final String FOR = "for";

	/** Compare Flag*/
	public static final String IS_COMPARABLE="isComparable";
	public static final String PDP_TEMPLATE_SOLR="pdpTemplate_t";
	public static final String TDS_CLONE_NAME_SOLR="tdsCloneName_t";

	public static final String HAS_BEADLOT_FILES="hasBeadlotFiles";
	public static final String BEADLOT_TITLE="beadlotTitle";
	public static final String MANUAL="manual";
	public static final String AUTO="auto";
	public static final String URL_T = "url_t";
	public static final String DCTITLE_T = "dctitle_t";
	public static final String SLASH_SLASH_COLON = "\\:";

	public static final String OAK_UNSTRUCTURED = "oak:Unstructured";

	public static final String PDP_URL = "/{superCategory}/{PDP}.{labelDescription}.{materialNumber}";
	public static final String URL_SUPER_CATEGORY= "{superCategory}";
	public static final String URL_LABEL_DESC= "{labelDescription}";
	public static final String URL_PDP= "PDP";


	/** Data Sheets Labels */
	public static final String PDF= "pdf";
	public static final String TOGGLE_PADDING_TOP = "togglePaddingTop";
	public static final String TOGGLE_PADDING_BOTTOM = "togglePaddingBottom";
	public static final String DOCUMENT_TYPE= "documentType";
	public static final String DOCUMENT_LABEL="documentLabel";
	public static final String DATA_TDS= "Technical data sheet (TDS)";
	public static final String DATA_IFU_TYPE= "Instruction for use (IFU)";
	public static final String DATA_ID_TYPE= "Ingredient disclosure";
	public static final String DATA_PI_TYPE= "Package/product insert";
	public static final String DATA_EBOOK_TYPE= "eBook";
	public static final String DATA_UG_TYPE= "User guide/manual";
	public static final String DATA_PUBLISHED_TDS= "PUBLISHED_TDS";
	public static final String DATA_SDS= "Safety Data Sheets";
	public static final String DATA_BEADLOTS= "BEADLOTS";
	public static final String DATA_CFDA = "CFDA LICENSE";
	public static final String DATA_IFU= "IFU";
	public static final String DATA_ID= "ID";
	public static final String DATA_PI= "PI";
	public static final String DATA_MANUAL= "MANUAL";
	public static final String DOWNLAOD_ALT_TEXT= "downloadAltText";
	public static final String VIEW_LESS= "viewLess";
	public static final String POP_OVER_TITLE= "popoverTitle";
	public static final String RIGHT_ARROW_ALT_TEXT= "rightArrowAltText";
	public static final String DATA_COUNT= "count";
	public static final String JCR_TITLE = JcrConstants.JCR_TITLE;
	public static final String LANGUAGE_CODE= "languageCode";
	public static final String DATA_VALUE= "value";
	public static final String FILE_ID = "fileID";
	public static final String LANGUAGE_GENERIC_LIST_PATH= "/etc/acs-commons/lists/bdb/region_country_language_mappings/language_mapping/jcr:content/list";
	public static final String BASIC = "Basic";
	public static final String FILE_PATH = "filePath";
	public static final String DOC_TYPE = "docType";
	public static final String DOC_SKU = "docSKU";
	public static final String COUNTRY_CODE = "countryCode";
	public static final String SOURCE = "source";
	public static final String BDB_LABEL = "BDB";
	public static final String APPLICATION_PDF = "application/pdf";
	public static final String ON_DEMAND_TDS = "ON_DEMAND_TDS";

	/** Credit Card Model Constants. */
	public static final String CREDIT_TITLE = "title";
	public static final String ACCEPT_INFO = "acceptInfo";
	public static final String CREDIT_CARD_NUMBER = "creditCardNumber";
	public static final String NAME_ON_CARD = "nameOnCard";
	public static final String CREDIT_EXPIRY_DATE = "expiryDate";
	public static final String CVV = "cvv";
	public static final String CREDIT_NICK_NAME = "nickName";
	public static final String SET_DEFAULT = "setDefault";
	public static final String CREDIT_CANCEL_CTA = "cancelCTA";
	public static final String CREDIT_CONFIRM_CTA = "confirmCTA";
	public static final String CC_NUMBER_ERROR = "ccNumberError";
	public static final String MANDATORY_ERROR_LABEL = "mandatoryErrorLabel";
	public static final String LENGTH = "length";
	public static final String ACCEPTED_CC_LIST = "acceptedCClist";
	public static final String GET_PAYMETRIC_TOKEN = "getPaymetricToken";
	public static final String ADD_CREDIT_CARD = "addCreditCard";
	public static final String I_FRAME_URL = "iframeUrl";
	public static final String SITE = "{{site}}";
	public static final String MERCHANT_ID = "{{merchant_ID}}";
	public static final String MERCH_ID = "{{merchId}}";

	/** Scientific Resources Library Constants. */
	public static final String NAME_SCIENTIFIC_RESOURCES = "name";
	public static final String CHILDREN_SCIENTIFIC_RESOURCES = "children";
	public static final String CODE_KEY_SCIENTIFIC_RESOURCES = "code";
	public static final String CODE_VALUE_SCIENTIFIC_RESOURCES = "resourceType";
	public static final String CODE_TARGET_KEY_SCIENTIFIC_RESOURCES = "codeTarget";
	public static final String CODE_TARGET_VALUE_SCIENTIFIC_RESOURCES = "#resourceType";

	/** runmodes**/
	public static final String RUNMODE_STAGE  = "stage";
	/** PROD runmodes**/
	public static final String RUNMODE_PROD  = "prod";
	/**SSO constants**/
	public static final String STAGE_LITERAL = "stg_";

	public static final String PAYMETRICS_MERCHANT_ID = "{{merchId}}";

	public static final String SLING_RESOURCE_TYPE = JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY;

	public static final String DISABLE_DISCLAIMER = "disableDisclaimer";

	public static final String IDENTIFIER = "identifier";
	public static final String TEST_KEY = "test";
	public static final String REQUEST_INFORMATION = "requestInformation";
	public static final String DATA_KEY = "data";
	public static final String CHECKOUT_OT_CONSENT_DATA = "checkOutOTConsentData";
	public static final String GET_USERS_SETTINGS_CONFIG = "getUserSettingsConfig";

	/** job constants**/
	public static final String PAYLOAD_PATHS  = "payloadPaths";
	public static final String REPLICATION_ALL_NODES  = "AllNodes";

	/** dam asset constants **/
	public static final String DAM_ASSET  = "dam:Asset";
	public static final String DAM_ASSET_STATE  = "dam:assetState";
	public static final String DAM_ASSET_STATE_PROCESSED = "processed";
	public static final String FAILURE = "dam:failed";
	public static final String ATTEMPTS = "dam:attempts";

	/** auto replication and indexing constants */
	public static final String TYPE_PDF  = "pdfAsset";
	public static final String TYPE_IMAGE = "imageAsset";
	public static final String TYPE_VIDEO_THUMBNAIL  = "videoThumbnailAsset";
	public static final String TYPE_PNG_IMAGE  = "PNG Image";
	public static final String TYPE_COMMERCE_CONTENT  = "commerceContent";
	public static final String PREVIOUS_SLIDE_BUTTON_LABEL = "previousSlideButton";
	public static final String NEXT_SLIDE_BUTTON_LABEL = "nextSlideButton";
	public static final String PREV_ICON_ARIA_LABEL = "prevIconAriaLabel";
	public static final String NEXT_ICON_ARIA_LABEL = "nextIconAriaLabel";

	public static final String CONTACT_US_URL = "contactUsUrl";
	public static final String SHIPMENT_DEFAULT = "Shipment";
	public static final String EMAIL_DOMAINS = "emailDomains";
	public static final String DOMAINS_KEY = "domain";
	
	/** CardList Component constants */
	public static final String BACKGROUND_COLOR = "backgroundColor";
	public static final String REDUCE_TOP_PADDING = "reduceTopPadding";
	public static final String REDUCE_BOTTOM_PADDING = "reduceBottomPadding";
	public static final String CTA_STYLE = "ctaStyle";
	public static final String SHOW_RESULTS = "showResults";
	public static final String NO_RESULTS_ICON = "noResultsIcon";
	public static final String NO_RESULTS = "noResults";
	public static final String CCLEAR_ALL = "cclearAll";
	public static final String INCLUDE_CHILD_PAGES = "includeChildPages";
	public static final String SORT_BY = "sortBy";
	public static final String TAG_BASED_SEARCH = "tagBasedSearch";
	public static final String TAGS_LABEL = "tags";
	public static final String TOPICS_TAG_LABEL = "topicsTag";
	public static final String VIEW_OPTIONS = "viewOptions";
	public static final String SORT_OPTIONS = "sortOptions";
	public static final String PAGINATION_OPTIONS = "paginationOptions";
	public static final String FILTER_CATEGORY = "filterCategory";
	public static final String DISABLE_PAGINATION = "disablePagination";
	public static final String INCLUDE_CARD_THUMBNAIL = "includeCardThumbnail";
	public static final String FALLBACK_IMAGE = "fallbackImage";
	
	/** CardList Servlet constants */
	public static final String JCRTITLE = "jcr:title";
	public static final String JCR_DESCRIPTION = "jcr:description";
	public static final String HIDE_IN_LISTING = "hideInListing";
	public static final String IMAGE_URL = "imageUrl";
	public static final String TOPIC = "topic"; 
	public static final String CARD_LIST_DATE = "cardListDate";
	public static final String CARD_DATE = "scheduleTime";
	public static final String CARD_THUMBNAIL = "cardListThumbnailImage";
	public static final String REMOVE_BORDER = "removeBorder";
	public static final String DISABLE_FACETS = "disableFacets";
	public static final String DISPLAY_DATE = "includeDate";
	
	/** Download Component constants */
	public static final String WINDOWS = "windows";
	public static final String LINUX = "linux";
	public static final String MAC = "mac";
	public static final String VERSION_NAME= "versionName";
	public static final String OS_LINKS= "osLinks";

	/** ICM Tools constants**/
	public static final String ICM_TYPE  = "type";
	public static final String SUB_SET_IDS  = "subSetId";
	public static final String PANEL_NAME  = "panelName";
	public static final String PANEL_DATA  = "panelData";
	public static final String CELL_TYPE  = "cellType";
	public static final String PANEL_TYPE  = "panelType";
	public static final String CELL_ID  = "cellId";
	public static final String CELL_DESSCRIPTION  = "cellDescription";
	public static final String CELL_IMAGE  = "cellImage";
	public static final String SUB_SET_CELLS  = "subsetCells";
	public static final String RELEVANT_PANELS  = "relevantPanels";
	public static final String CELL_SURFACE  = "cellSurface";
	public static final String SECRETED  = "secreted";
	public static final String INTRACELLULAR_KEY  = "intracellularTxn";
	public static final String CATEGOTY_NAME  = "categoryName";
	public static final String INTRACELLULAR_VALUE_KEY  = "intracellularTxn";
	public static final String SECRETED_VALUE_KEY  = "secretedProteins";
	public static final String LINKS_LIST  = "linksList";
	public static final String CELL_SURFACE_VALUE_KEY  = "Cell Surface";
	public static final String CELL_NAME  = "cellName";
	public static final String SUB_SET_CELL_IDS  = "subSetCellIds";
	public static final String PANEL_IDS  = "panelIds";
	public static final String MARKERS_LIST  = "markersList";
	public static final String CELL_SELECTION_LIST  = "cellSelectionList";
	public static final String FLUOROCHROME  = "fluoroChrome";
	public static final String TEST_OR_KIT  = "testOrKit";
	public static final String VOLUME_OR_TEST  = "volumeOrTest";
	public static final String MARKER  = "marker";
	public static final String PANEL_ID  = "panelId";
	public static final String CELLIMG  = "cellImg";

	public static final String TYPE_COMMERCE_CONTENT_PDF  = "commerceContentPdf";
	public static final String DC_FORMAT  = "dc:format";
	public static final String BRC_DESCRIPTION = "brc_long_description";
	public static final String DC_DESCRIPTION  = "dc:description";
	public static final String FORWARD_SLASH_PDF  = "/pdf";
	public static final String IS_VARIANT  = "isVariant";
	public static final String DOC_PART_IDS_CAP  = "docPartIDs";
	public static final String PO_TOOL_TIP = "poToolTip";
	/*feature/GE-17885-latest1*/

	public static final String COMP_TITLE = "compTitle";

	public static final String COMP_MSG1 = "compMsg1";

	public static final String COMP_MSG2 = "compMsg2";

	public static final String COMP_YES = "compYes";

	public static final String COMP_NO = "compNo";

	public static final String COMP_LINK = "compLink";

	public static final String NZ_PRICING_MESSAGE = "nzPricingMessage";

	/** Rep Tool constants**/
	public static final String SUB_HEADING = "subHeading";
	public static final String REP_TOOL_DESCRIPTION = "description";
	public static final String MANDATORY_NOTE = "mandatoryNote";
	public static final String ZIP_CODE_ERROR = "zipCodeError";
	public static final String AREA_OF_INTEREST_LABEL = "areaOfInterestLabel";
	public static final String AREA_OF_INTEREST_LIST = "areaOfInterestList";
	public static final String AREA_OF_INTEREST_ERROR = "areaOfInterestError";
	public static final String REP_LABEL = "label";
	public static final String SALES_REP_NAME_LABEL = "nameLabel";
	public static final String SALES_REP_PHONE_LABEL = "phoneLabel";
	public static final String AREA_OF_SPECIALITY_LABEL = "areaofSpecialityLabel";
	public static final String CONTACT_ME_BUTTON_LABEL = "contactMeBtnLabel";
	public static final String BACK_TO_REP_HOME_PAGE_LABEL = "backBtnLabel";
	public static final String BACK_TO_REP_PERSONAL_PAGE_LABEL = "backBtnLabel";
	public static final String MARKETO_FOMR_ID = "marketoFormId";

	public static final String EMEA_REGION_TAG = "bdb:regions/emea";
	public static final String APAC_REGION_TAG = "bdb:regions/apac";
	public static final String APAC_EN_REGION_TAG = "bdb:regions/apac(english)";
	public static final String SKU_NUMBER = "skuNumber";

    public static String EMEA_COUNTRIES_LIST_PATH = "/etc/acs-commons/lists/bdb/region-country-mapping/eu"+"/jcr:content/list";
	public static String APAC_EN_COUNTRIES_LIST_PATH = "/etc/acs-commons/lists/bdb/region-country-mapping/apac(english)"+"/jcr:content/list";
	public static String APAC_COUNTRIES_LIST_PATH = "/etc/acs-commons/lists/bdb/region-country-mapping/apac"+"/jcr:content/list";

	public static String EMEA_COUNTRIES_LIST = "at,be,dk,fi,fr,de,ie,it,lu,nl,no,pl,pt,es,se,ch,gb,eu";
	public static String COUNTRIES_LIST = "us,ca,br,at,be,dk,fi,fr,de,ie,it,lu,nl,no,pl,pt,es,se,ch,gb,eu,cn,in,jp,kr,sg,tw,au,nz";
	public static String APAC_EN_COUNTRIES_LIST = "in,sg,tw,au,nz";
	public static String APAC_COUNTRIES_LIST = "cn,in,jp,kr,sg,tw,au,nz";
	public static String DOC_METADATA_LIST = "docTitle, docRegion, docLang, docSKU, docDesc, docExpiryDate, docRegStatus, docRevision, docOwner, productName, docKeywords, dc:imageKeywords, docPartId, docType";
	public static final String UTM_TERM = "utm_term";

	public static final String UTM_CONTENT = "utm_content";

	public static final String REFERER = "Referer";

	public static final String STG = "stg";

	public static final String QA = "qa";

	public static final String DEV = "dev";

	public static final String UTM_CAMPAIGN = "utm_campaign";

	public static final String UTM_MEDIUM = "utm_medium";

	public static final String UTM_SOURCE = "utm_source";

	public static final String INTCID = "intcid";

	public static final String CQ_LAST_MODIFIED = "cq:lastModified";

	public static final String CARET = "^";

	public static final String PIPE = "|";

	public static final String SLASH = "/";

	public static final String PUNCH_OUT = "punchOut";

	public static final String SIGNED_IN_USER = "signed_in_user";

	public static final String USER_DETAILS_UID = "user_details_uid";

	public static final String SCROLL_DEPTH = "scrollDepth";

	public static final String PREVIOUS_PAGE = "prevPage";

	public static final String LOGGED_IN = "logged_in";

	public static final String NOT_LOGGED_IN = "not_logged_in";

	public static final String WEB = "web";
	
	public static final String CONTENT_PAGE = "Content";
	
	public static final String CONTENT_PAGE_TYPE = "contentPageType";

	public static final String CQ_LAST_REPLICATED = "cq:lastReplicated_publish";

	public static final String JCR_CREATED = "jcr:created";

	public static final String ERROR_500 = "500";

	public static final String ERROR_404 = "404";

	public static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_PATTERN = "MM-dd-yyyy";
	
	public static final String SLING_FOLDER = "sling:Folder";

	public static final String NT_UNSTRUCTURED = "nt:unstructured";

	/**
	 * Class Constructor.
	 */
	private CommonConstants() {
	}
}
