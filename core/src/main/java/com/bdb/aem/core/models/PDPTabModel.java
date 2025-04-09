
package com.bdb.aem.core.models;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.solr.client.solrj.SolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.ComponentBean;
import com.bdb.aem.core.bean.IncludedPurchaseLabelBean;
import com.bdb.aem.core.bean.PhantomProductDetailsBean;
import com.bdb.aem.core.bean.ReferenceBean;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.bdb.aem.core.util.SolrUtils;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;

/**
 * This model returns the values for Product Details as a map and strings.
 */

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PDPTabModel {

	/** The Constant MANUFACTURING_COMPONENT. */
	protected static final String MANUFACTURING_COMPONENT = "manufacturingComponent";

	/** The Constant N_A. */
	protected static final String N_A = "N/A";

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(PDPTabModel.class);

	/** The products. */
	@Inject
	@Via("resource")
	public Resource products;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The resource resolver. */
	@SlingObject
	ResourceResolver resourceResolver;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/** Externalizer Service. */
	@Inject
	ExternalizerService externalizerService;
	/** The current page. */
	@Inject
	private Page currentPage;

	/** The product details tab label. */
	@Inject
	@Via("resource")
	@Default(values = "Product Details")
	String productDetailsTabLabel;

	/** The instrument Details Label */
	@Inject
	@Via("resource")
	@Default(values = "Instrument Details")
	String instrumentDetailsTabLabel;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String productNameLabel;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String catalogNumberLabel;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quantityLabel;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String instrumentRegulatoryStatusLabel;

	/** The companion products label. */
	@Inject
	@Via("resource")
	@Default(values = "Companion Products")
	private String companionProductsLabel;

	/** The product URL. */
	private String productUrl;

	/** The antibody details tab label. */
	@Inject
	@Via("resource")
	@Default(values = "Antibody Details")
	String antibodyDetailsTabLabel;

	/** The format details tab label. */
	@Inject
	@Via("resource")
	@Default(values = "Format Details")
	String formatDetailsTabLabel;

	/** The references tab label. */
	@Inject
	@Via("resource")
	@Default(values = "References")
	String referencesTabLabel;

	/** The view all formats label. */
	@Inject
	@Via("resource")
	String viewAllFormatsLabel;

	/** The components label. */
	@Inject
	@Via("resource")
	@Default(values = "Components")
	private String componentsLabel;

	/** The component description label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String componentDescriptionLabel;

	/** The component size label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String componentSizeLabel;

	/** The component part number label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String componentPartNumberLabel;

	/** The component isotype label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String componentIsotypeLabel;

	/** The component EntrezGeneID label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String componentEntrezGeneIdLabel;

	/** The component clone label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String componentCloneLabel;


	/** The rap title text. */
	@Inject
	@Via("resource")
	String rapTitleText;

	/** The pn title text. */
	@Inject
	@Via("resource")
	String pnTitleText;

	/** The rapshowmoreless desktop. */
	@Inject
	@Via("resource")
	@Default(values = "0")
	int rapshowmorelessDesktop;

	/** The no of links to be shown. */
	@Inject
	@Via("resource")
	@Default(values = "0")
	int noOfLinksToBeShown;

	/** The rapshowmoreless mobile. */
	@Inject
	@Via("resource")
	@Default(values = "0")
	int rapshowmorelessMobile;

	/** The pn show more less limit desktop. */
	@Inject
	@Via("resource")
	@Default(values = "0")
	int pnShowMoreLessLimitDesktop;

	/** The showmorelabel. */
	@Inject
	@Via("resource")
	String showmorelabel;

	/** The showlesslabel. */
	@Inject
	@Via("resource")
	String showlesslabel;

	/** The pn show more less limit mobile. */
	@Inject
	@Via("resource")
	@Default(values = "0")
	int pnShowMoreLessLimitMobile;

	/** brandLabel. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String brandLabel;

	/**  CloneLabel. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String cloneLabel;

	/** Catalog Label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String catalogNoLabel;

	/** Regulatory Status label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String regulatoryStatusLabel;

	/** Other clones Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String otherCloneLabel;

	/** Other format Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String otherFormatLabel;

	/** Your price Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String yourPriceLabel;

	/** List Price Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String listPriceLabel;

	/** Compare Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String compareLabel;

	/** Currency Code. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String currencyCode;

	/** Results Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String resultsLabel;

	/** Select All Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String selectAllLabel;

	/** Filter Results Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String filterResultsLabel;

	/** Group Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String groupLabel;

	/** Sign In Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String signInLabel;

	/** Instrument CTA Text. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String instrumentCtatext;

	/** Instrument CTA Link. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String instrumentCtaLink;

	/** The recently viewed label. */
	@Inject
	@Via("resource")
	@Default(values="Recently Viewed")
	private String recentlyViewedLabel;

	/** The Count. */
	@Inject
	@Via("resource")
	@Default(values="Count")
	private String count;

	/**  Add to cart. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String addToCartLabel;

	/** Add to Quote Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String addToQuoteLabel;

	/** Alt Text for Add to cart Icon. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String addToCartAlt;

	/** Alt Text for Add to Quote Icon. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String addToQuoteAlt;

	/** The filter mobile. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String filterMobile;

	/** The viewMore. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String viewMore;

	/** The cancel. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String cancel;

	/** The apply. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String apply;

	/** The compareCurrentPdpLabel. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String compareCurrentPdpLabel;

	/** The apply. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String allFormats;

	/** The apply. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String allClones;


	/** The brand description. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String brandDescription;

	/** The InquireLabel. */
	@Inject
	@Via("resource")
	@Default(values = "Inquire")
	private String inquireLabel;
	
	/** The phantomTitleText. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String phantomTitleText;
	
	/** The phantomSubTitleText. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String phantomSubTitleText;
	
	/** The descriptionLabel. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String descriptionLabel;
	
	/** The sizeQuantityLabel. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String sizeQuantityLabel;
	
	/** The catNumberLabel. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String catNumberLabel;
	
	/** The bdb search endpoint service. */
	@Inject
	BDBSearchEndpointService solrConfig;
	
	/** The solr search service. */
	@Inject
	SolrSearchService solrSearchService;



	/**  * Page Properties to fetch other Properties from Page. */
	@Inject
	private InheritanceValueMap pageProperties;

	/** The recently viewed json string. */
	private String recentlyViewedJsonString;

	/** The preparation storage. */
	private String preparationStorage;

	/** The add preparation storage. */
	private String addPreparationStorage;

	/** The label description. */
	private String labelDescription;

	/** The tds description. */
	private String tdsDescription;

	/** The tds description. */
	@Getter
	private String otherTdsDescription;

	/** The format name. */
	private String formatname;

	/** The product Application. */
	private String productApplication;

	/** The product format data. */
	private String formatData;

	/** The product additional property. */
	private String additionalProperty;

	/** The emmision max. */
	private String emmisionmax;

	/** The excitation max. */
	private String excitationmax;

	/** The excitation source. */
	private String excitationSource;

	/** The bd format description. */
	private String bdFormatdescription;

	/** The format details image. */
	private String formatDetailsImage;

	/** The recomAssayProcedure. */
	private String recomAssayProcedure;

	private String abSeqBrand = "BD™ AbSeq";

	private String OptiBuild = "BD OptiBuild™";

	private String isregulatoryStatus = "Regulatory Status";

	/** The label map. */
	HashMap<String, String> labelMap = new HashMap<>();

	/** The product details map. */
	Map<String, String> productDetailsMap = new LinkedHashMap<>();

	/** The citation list. */
	ArrayList<ReferenceBean> referenceList = new ArrayList<>();

	/** The Instrument Purchase list. */
	ArrayList<IncludedPurchaseLabelBean> instrumentPurchaseList = new ArrayList<>();

	/** The component list. */
	ArrayList<ComponentBean> componentList = new ArrayList<>();

	/** The recommended assay procedure list. */
	List<String> recomAssayProcedureList = new ArrayList<>();

	/** The product notices list. */
	List<String> productNoticesList = new ArrayList<>();
	
	/** The phantom products list. */
	List<PhantomProductDetailsBean> phantomProductsList = new ArrayList<PhantomProductDetailsBean>();

	/** Clone API Label Json. */
	private String cloneLabelObject;

	/** Clone API Details Json. */
	private String cloneApiObject;

	/** Format API Details Json. */
	private String formatApiObject;

	/** Tds Revision No. */
	private String tdsRevisionNo =StringUtils.EMPTY;

	/** Catalog Number. */
	private String materialNumber;

	/** resources. */
	private String resources;

	/** included with Purchase */
	private String includedWithYourPurchase;

	/** productNotices. */
	private String productNotices;

	/** The is productType. */
	private String productType;

	/** The is author. */
	private boolean isAuthor;


	/** The tds clone name. */
	private String tdsCloneName;

	/** The brand. */
	private String brand;	

	/** The row multifield. */
	@Inject
	@Via("resource")
	private List<KitsAndSetsRowMultifieldModel> rowMultifield;

	/** The column multifield. */
	@Inject
	@Via("resource")
	private List<KitsAndSetsColumnMultifieldModel> columnMultifield;

	private String citeabApiKey;

	private String citeabCompanySlug;

	private String citeabScriptUrl;

	private String showCiteabWidget;
	
	private String phantomChilds = StringUtils.EMPTY;
	
	private String isPhantom = "false";
	private String country = StringUtils.EMPTY;
	/**
	 * Init method to return values from dialog and catalog structure as a map.
	 *
	 * @throws LoginException the login exception
	 */
	@PostConstruct
	protected void init() throws LoginException {
		long startTime = System.currentTimeMillis();
		logger.debug("PDPTabModel - Init method started");

		labelMap.put(CommonConstants.BRAND_KEY, CommonConstants.BRAND_VALUE);
		labelMap.put(CommonConstants.ALTERNATIVE_NAME_KEY, CommonConstants.ALTERNATIVE_NAME_VALUE);
		labelMap.put(CommonConstants.CONCENTRATION_KEY, CommonConstants.CONCENTRATION_VALUE);
		labelMap.put(CommonConstants.REGULATORY_STATUS_KEY, CommonConstants.REGULATORY_STATUS_VALUE);
		labelMap.put(CommonConstants.STORAGE_BUFFER_KEY, CommonConstants.STORAGE_BUFFER_VALUE);
		labelMap.put(CommonConstants.ISOTYPE_KEY, CommonConstants.ISOTYPE_VALUE);
		labelMap.put(CommonConstants.SPECIES_REACTIVITY_KEY, CommonConstants.SPECIES_REACTIVITY_VALUE);
		labelMap.put(CommonConstants.APPLICAATION_KEY, CommonConstants.APPLICAATION_VALUE);
		labelMap.put(CommonConstants.IMMUNOGEN_KEY, CommonConstants.IMMUNOGEN_VALUE);
		labelMap.put(CommonConstants.ENTREZ_GENE_ID_KEY, CommonConstants.ENTREZ_GENE_ID_VALUE);
		labelMap.put(CommonConstants.VOL_PER_TEST_KEY, CommonConstants.VOL_PER_TEST_VALUE);
		labelMap.put(CommonConstants.RRID_KEY, CommonConstants.RRID_VALUE);
		labelMap.put(CommonConstants.TARGET_MW, CommonConstants.CONST_TARGET_MW);
		labelMap.put(CommonConstants.ASSAY_RANGE, CommonConstants.CONST_ASSAY_RANGE);
		labelMap.put(CommonConstants.NEUTRALIZATION_ACTIVITY, CommonConstants.CONST_NEUTRALIZATION_ACTIVITY);
		labelMap.put(CommonConstants.DIMENSIONS_KEY, CommonConstants.DIMENSIONS_VALUE);
		labelMap.put(CommonConstants.WEIGHT_KEY, CommonConstants.WEIGHT_VALUE);

		labelMap.put("toxicity", "Toxicity");
		labelMap.put("ivd", "IVD");
		labelMap.put("ivdRegisteredName", "IVD Registered Name");
		labelMap.put("cfdaLicenseNumber", "CFDA License Number");
		labelMap.put("productName", "Product Name");
		labelMap.put("modelName", "Model Name");
		labelMap.put(CommonConstants.COUNTRY_OF_ORIGIN, "Country Of Origin");
		labelMap.put("kfdaLicenseNumber", "KFDA License Number");
		labelMap.put("adReviewNumber", "AD Review Number");

		labelMap.put(CommonConstants.BEAD_POSITION, "Bead Position");
		labelMap.put(CommonConstants.BARCODE_SEQUENCE_KEY, CommonConstants.BARCODE_SEQUENCE_VALUE);
		labelMap.put(CommonConstants.SEQUENCE_ID_KEY, CommonConstants.SEQUENCE_ID_VALUE);

		labelMap.put(CommonConstants.MOLECULAR_WEIGHT, "Molecular Weight");
		labelMap.put(CommonConstants.WORKSHOP_NUMBER, "Workshop Number");
		labelMap.put(CommonConstants.DOC_PART_IDS, "Doc Part ID");

		labelMap.put(CommonConstants.HOST_SPECIES, "Host Species");

		// Citeab Integration Configuration Data
		citeabApiKey = bdbApiEndpointService.getCiteabApiKey();
		citeabCompanySlug = bdbApiEndpointService.getCiteabCompanySlug();
		citeabScriptUrl = bdbApiEndpointService.getCiteabScriptUrl();
		showCiteabWidget = solrConfig.showCiteabWidget();
		//getCloneLabels Json
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		
		country = CommonHelper.getCountry(currentPage);

		try {

			materialNumber = CommonHelper.getSelectorDetails(request);
			String productVarHPPath;
			Resource baseHpResource = null;
			JsonObject assetJson = new JsonObject();
			String catalogNumber = request.getAttribute(CommonConstants.CATALOG_NUMBER_KEY) != null ? request.getAttribute(CommonConstants.CATALOG_NUMBER_KEY).toString():null;
			if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)) {
				productVarHPPath = null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH) ? request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString() : StringUtils.EMPTY;
				baseHpResource = resourceResolver.getResource(productVarHPPath);
			}
			if (null != request.getAttribute("assetPaths")) {
				assetJson = (JsonObject) request.getAttribute("assetPaths");
			}

			formatApiObject=addFormatJsonObject(excludeUtilObject, baseHpResource);
			cloneLabelObject=addJsonCloneObject();
			cloneApiObject=addJsonApiObject(excludeUtilObject, baseHpResource);

			if (null != baseHpResource) {
				logger.debug("PDPTabModel - hpResource path {}", baseHpResource.getPath());
				ValueMap baseHpMap = baseHpResource.adaptTo(ValueMap.class);
				tdsCloneName = getTdsCloneName(baseHpMap,CommonConstants.TDS_CLONE_DISPLAY_NAME);
				if(null != baseHpMap.get(CommonConstants.BRAND) && StringUtils.isNotEmpty(baseHpMap.get(CommonConstants.BRAND,String.class))) {
					brand = baseHpMap.get(CommonConstants.BRAND,String.class);
				}

				if(StringUtils.isNotEmpty(brandDescription)) {
					brandDescription = CommonHelper.HandleRTEAnchorLink(brandDescription, externalizerService, resourceResolver,StringUtils.EMPTY) ;
				}else {
					brandDescription = StringUtils.EMPTY;
				}
				Resource variantResource = null;
				if(null != catalogNumber && null != baseHpResource.getParent().getChild(catalogNumber)) {
					variantResource = baseHpResource.getParent().getChild(catalogNumber);
					Resource variantHpResource = variantResource.getChild(CommonConstants.HP);
					// Product Details Tab
					getProductDetails(variantHpResource, labelMap, baseHpMap);					
					
				}
				
				// check if it is a phantom product
				isPhantom = CommonHelper.isPhantomProduct(variantResource, country);
				
				// Antibody Details Tab
				getAntibodyDetails(baseHpResource, baseHpMap);

				// Format Details Tab
				getFormatDetails(baseHpResource, assetJson, baseHpMap);

				// References Tab
				getReferences(baseHpResource, baseHpMap);

				// Component Tab
				getComponentDetails(baseHpResource, baseHpMap);

				// Recently Viewed Labels
				getRecentlyViewedLabels();

				//product Application Tab
				productApplication = getProductApplicationData(baseHpMap);

				// product format name
				formatData = getProductFormatData(baseHpMap);
				if(StringUtils.isNotEmpty(formatData) && StringUtils.isNotEmpty(productApplication)) {
					additionalProperty = formatData + ","+" " + productApplication;
				}
				if(StringUtils.isEmpty(formatData) && StringUtils.isNotEmpty(productApplication)) {
					additionalProperty = productApplication;
				}
				if(StringUtils.isNotEmpty(formatData) && StringUtils.isEmpty(productApplication)) {
					additionalProperty = formatData;
				}				
			}
		} catch (JsonProcessingException e) {
			logger.error("Exception {}", e.getMessage());
		} catch (IOException e) {
			logger.error("IOException {}", e.getMessage());
		} catch (RepositoryException e) {
			logger.error("RepositoryException {}", e.getMessage());
		}
		long endTime = System.currentTimeMillis();
		logger.debug("PDPSessionModel TIME - {}" ,endTime-startTime);
	}
	
	/**
	 * Gets the recently viewed labels.
	 *
	 * @return the recently viewed labels
	 */
	public void getRecentlyViewedLabels(){
		JsonObject recentlyViewedLabels=new JsonObject();
		recentlyViewedLabels.addProperty(CommonConstants.HEADING, recentlyViewedLabel);
		recentlyViewedLabels.addProperty(CommonConstants.CATALOG_NO, catalogNoLabel);
		recentlyViewedLabels.addProperty("count", count);
		recentlyViewedJsonString = recentlyViewedLabels.toString();
	}

	/**
	 * Gets the component details.
	 *
	 * @param hpResource the hp resource
	 * @param baseHpMap the base hp map
	 * @return the component details
	 * @throws JsonProcessingException the json processing exception
	 */
	public void getComponentDetails(Resource hpResource, ValueMap baseHpMap) throws JsonProcessingException {
		productType = StringUtils.EMPTY;
		if (baseHpMap.containsKey(MANUFACTURING_COMPONENT)) {
			String manufacturingComponent = baseHpMap.get(MANUFACTURING_COMPONENT).toString();
			productType = baseHpMap.get(CommonConstants.PRODUCT_TYPE_KEY,String.class);
			JsonArray componentArray = new JsonParser().parse(manufacturingComponent).getAsJsonArray();
			ObjectMapper componentObjectMapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			for (JsonElement componentElement : componentArray) {
				JsonObject componentJsonObject = componentElement.getAsJsonObject();

				StringBuilder size = new StringBuilder();
				size.append(componentJsonObject.get(CommonConstants.SIZE_QTY).getAsString());
				size.append(CommonConstants.SPACE);
				size.append(componentJsonObject.get(CommonConstants.SIZE_UOM).getAsString());
				StringBuilder isoType = new StringBuilder();
				if(componentJsonObject.has(CommonConstants.SPECIES)) {
					isoType.append(componentJsonObject.get(CommonConstants.SPECIES).getAsString());
					isoType.append(CommonConstants.SPACE);
				}
				if(componentJsonObject.has(CommonConstants.ISOTYPE_KEY)) {
					isoType.append(componentJsonObject.get(CommonConstants.ISOTYPE_KEY).getAsString());
				}
				componentJsonObject.addProperty("size", size.toString());
				componentJsonObject.addProperty("isoType", isoType.toString());
				ComponentBean componentBean = componentObjectMapper.readValue(componentJsonObject.toString(),
						ComponentBean.class);

				componentList.add(componentBean);

			}
		}
		isAuthor = componentList.isEmpty();
		logger.debug("PDPTabModel - getComponentDetails end");
	}


	/**
	 * Adds the json api object.
	 *
	 * @param excludeUtilObject the exclude util object
	 * @param hpResource the hp resource
	 * @return the string
	 * @throws LoginException the login exception
	 */
	private String addJsonApiObject(ExcludeUtil excludeUtilObject, Resource hpResource)
			throws LoginException {
		JsonObject antiBodyDetails=new JsonObject();
		String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		String cloneApiEndPoint = externalizerService.getFormattedUrl(bdbApiEndpointService.getAntiBodyDetailsEndPoint(), resourceResolver);
		Payload cloneService = new Payload(
				cloneApiEndPoint + "?baseId=" + getBaseProductId(hpResource) + "&country={{country}}",
				HttpConstants.METHOD_GET);
		JsonElement getProductsElement = gson.toJsonTree(cloneService);
		JsonElement getPdAntiBodyPayload;
		String antibodyPdEndPoint = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getPriceDataEndPoint(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Payload getPriceData = new Payload(antibodyPdEndPoint, HttpConstants.METHOD_POST);
		getPdAntiBodyPayload = json.toJsonTree(getPriceData);
		antiBodyDetails.add("getProducts", getProductsElement);
		antiBodyDetails.add("getPriceData", getPdAntiBodyPayload);
		return antiBodyDetails.toString();
	}

	/**
	 * Adds the format json object.
	 *
	 * @param excludeUtilObject the exclude util object
	 * @param baseHpResource the base hp resource
	 * @return the string
	 * @throws LoginException the login exception
	 */
	private String addFormatJsonObject(ExcludeUtil excludeUtilObject, Resource baseHpResource) throws LoginException {
		JsonObject format=new JsonObject();
		String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		String formatApiEndPoint = externalizerService.getFormattedUrl(bdbApiEndpointService.getFormatsDetailsEndPoint(), resourceResolver);
		Payload formatPayLoad = new Payload(formatApiEndPoint + "?baseId=" + getBaseProductId(baseHpResource) + "&country={{country}}",
				HttpConstants.METHOD_GET);
		JsonElement getProductsElement = gson.toJsonTree(formatPayLoad);
		JsonElement getPdFormatsPayload;
		String formatPdEndPoint = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getPriceDataEndPoint(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Payload getPriceData = new Payload(formatPdEndPoint, HttpConstants.METHOD_POST);
		getPdFormatsPayload = json.toJsonTree(getPriceData);
		format.add("getProducts", getProductsElement);
		format.add("getPriceData", getPdFormatsPayload);

		return format.toString();
	}

	/**
	 * Gets the base product id.
	 *
	 * @param variantResource the variant resource
	 * @return the base product id
	 */
	private String getBaseProductId(Resource variantResource)
	{
		String baseId=StringUtils.EMPTY;
		if(null !=variantResource) {
			Resource parentResource = variantResource.getParent();
			baseId=parentResource.getName();
		}
		return baseId;
	}

	/**
	 * Gets the product details.
	 *
	 * @param hpResource the hp resource
	 * @param labelMap   the label map
	 * @param baseHpMap the base hp map
	 * @return the product details
	 */
	public void getProductDetails(Resource hpResource, Map<String, String> labelMap, ValueMap baseHpMap) {
		if (null != products) {
			Iterator<Resource> productLabels = products.listChildren();

			Resource regionDetails = hpResource.getParent().getChild("sap-cc")!=null?hpResource.getParent().getChild("sap-cc").getChild("region-details"):null;
			while (productLabels.hasNext()) {
				Resource productLabel = productLabels.next();
				ValueMap property = productLabel.adaptTo(ValueMap.class);
				updateProductDetails(labelMap, baseHpMap, property,regionDetails,country);
			}
			updateRemainingProductDetails(baseHpMap, hpResource);
			updatePhantomProductDetails(hpResource, country);
		}
		logger.debug("PDPTabModel - getProductDetails end");
	}

	/**
	 * 
	 * @param country 
	 * @param hpResource the variant sap resource
	 */
	private void updatePhantomProductDetails(Resource variantHpResource, String country) {
		if(null != variantHpResource) {
			Resource saCcResource = variantHpResource.getParent().getChild("sap-cc") != null ? variantHpResource.getParent().getChild("sap-cc") : null;
			if( null != saCcResource) {
				ValueMap sapCcVm = saCcResource.getValueMap();
				if(sapCcVm.containsKey(CommonConstants.PHANTOM_CHILDS)) {
					phantomChilds = sapCcVm.get(CommonConstants.PHANTOM_CHILDS).toString();
					if(null != phantomChilds) {
						phantomChilds = phantomChilds.replaceAll("[\"\\[ \\]]", "");
						List<String> phantomList = Arrays.stream(phantomChilds.split(","))
			                    .collect(Collectors.toList());
						for(String phantomChild: phantomList) {
							if(CommonHelper.getProductAvailabilityInRegion(phantomChild, country, resourceResolver)) {
								getPhantomProductDetails(resourceResolver, phantomChild, country, phantomProductsList);
							}
						}
					}
					
				}
			}
			
		}
	}

	private void getPhantomProductDetails(ResourceResolver resourceResolver, String catNumber, String country, List<PhantomProductDetailsBean> phantomProductsList) {
		PhantomProductDetailsBean phantomProductDetailsBean = new PhantomProductDetailsBean();
		SolrClient server = solrSearchService.solrClient(country);
		Resource hpResource = CommonHelper.getHpNodeResource(catNumber, country, resourceResolver, server);
		if(null != hpResource) {
			String pdpUrl = StringUtils.EMPTY;
			pdpUrl = CommonConstants.CONST_BDB_ROOT_PATH+CommonConstants.SINGLE_SLASH+CommonHelper.getRegion(currentPage)+CommonConstants.SINGLE_SLASH+CommonHelper.getCountry(currentPage);
			pdpUrl = pdpUrl+CommonConstants.SLASH+CommonHelper.getLanguage(currentPage)+"-"+country+CommonHelper.getIdFromSolr(catNumber, country, resourceResolver, server);
			String url = externalizerService.getFormattedUrl(pdpUrl,resourceResolver);
			String hpNode = CommonConstants.SLASH+catNumber+CommonConstants.SLASH+catNumber+CommonConstants.HP;
			String variantNodeHpPath = hpResource.getPath().replace(CommonConstants.SLASH+catNumber+CommonConstants.HP, hpNode);
			Resource variantHpResource = resourceResolver.getResource(variantNodeHpPath);
			ValueMap vm = variantHpResource.getValueMap();
			if(vm.containsKey(CommonConstants.SIZE_QTY) && vm.containsKey(CommonConstants.SIZE_UOM)) {
				String sizeQtyUOM = vm.get(CommonConstants.SIZE_QTY, StringUtils.EMPTY) + CommonConstants.SPACE + vm.get(CommonConstants.SIZE_UOM, StringUtils.EMPTY);
				phantomProductDetailsBean.setSizeQuantity(sizeQtyUOM);
			}
			if(vm.containsKey(CommonConstants.WEB_NAME)) {
				phantomProductDetailsBean.setProductTitle(vm.get(CommonConstants.WEB_NAME, StringUtils.EMPTY));
			}
			phantomProductDetailsBean.setMaterialNumber(catNumber);
			phantomProductDetailsBean.setProductUrl(url);
			phantomProductsList.add(phantomProductDetailsBean);
		}
	}
	
	/**
	 * Gets the product application.
	 *
	 * @param baseHpResource the base hp resource
	 * @return the product application.
	 */
	private String getProductApplicationData(ValueMap baseHpMap) {
		StringBuilder applicationStringBuilder = new StringBuilder();
		if(baseHpMap.containsKey(CommonConstants.PRODUCT_APPLICATION_TEST)) {
			String application = baseHpMap.get("productApplicationTest").toString();
			JsonArray applicationArray = new JsonParser().parse(application).getAsJsonArray();

			int index = 0;
			for (JsonElement applicationElement : applicationArray) {
				index = index + 1;
				JsonObject applicationJsontObject = applicationElement.getAsJsonObject();
				applicationStringBuilder
						.append(applicationJsontObject.get(CommonConstants.APPLICATION_DESC).getAsString() + CommonConstants.SPACE );
				String appDesc = applicationJsontObject.get(CommonConstants.APPLICATION_STATUS_DESC).getAsString();
				if( StringUtils.isNotBlank(appDesc)) {
					applicationStringBuilder
							.append( "(" + appDesc + ")");
				}
				if (index < applicationArray.size()) {
					applicationStringBuilder.append(", ");
				}
			}
		}
		return applicationStringBuilder.toString();
	}

	/**
	 * Gets the product format.
	 *
	 * @param baseHpResource the base hp resource
	 * @return the product format.
	 */
	private String getProductFormatData(ValueMap baseHpMap) {
		String formatNameData = StringUtils.EMPTY;
		if(baseHpMap.containsKey(CommonConstants.BD_FORMAT)) {
			String format = baseHpMap.get(CommonConstants.BD_FORMAT).toString();
			JsonArray formatArray = new JsonParser().parse(format).getAsJsonArray();
			for (JsonElement formatDetailsElement : formatArray) {
				JsonObject formatDetailsJson = formatDetailsElement.getAsJsonObject();
				if (formatDetailsJson.has(CommonConstants.DYE_NAME) && null != formatDetailsJson.get(CommonConstants.DYE_NAME)
						&& !formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString().isEmpty()) {
					formatNameData = formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString();
				}
			}
		}
		return formatNameData;
	}

	/**
	 * Update remaining product details.
	 *
	 * @param hpProperty the hp property
	 */
	/**
	 * @param hpProperty
	 * @param variantHpResource 
	 */
	private void updateRemainingProductDetails(ValueMap hpProperty, Resource variantHpResource) {
		
		if (hpProperty.containsKey(CommonConstants.RECOM_ASSAY_PROCEDURE)) {
			addRecomAssay(hpProperty);
		}
		if (hpProperty.containsKey(CommonConstants.PREPARATION_STORAGE)) {
			preparationStorage = hpProperty.get(CommonConstants.PREPARATION_STORAGE).toString();
		}
		if (hpProperty.containsKey(CommonConstants.ADD_PREPARATION_STORAGE)) {
			addPreparationStorage = hpProperty.get(CommonConstants.ADD_PREPARATION_STORAGE).toString();
		}
		if (hpProperty.containsKey(CommonConstants.RESOURCES)) {
			resources = hpProperty.get(CommonConstants.RESOURCES).toString();
		}
		if(hpProperty.containsKey(CommonConstants.COMPANION_PRODUCT_URL)){
			productUrl = hpProperty.get(CommonConstants.COMPANION_PRODUCT_URL).toString();
		}
		if (hpProperty.containsKey(CommonConstants.PRODUCT_NOTICES)) {
			String productNoticesMarkup = hpProperty.get(CommonConstants.PRODUCT_NOTICES).toString();
			this.productNotices = productNoticesMarkup;
			Pattern regex = Pattern.compile("<li>(.*?)</li>");
			Matcher matcher = regex.matcher(productNoticesMarkup);
			while (matcher.find()) {
				if (!matcher.group(1).trim().isEmpty()) {
					productNoticesList.add(matcher.group(1));
				}
			}
		}
		if (hpProperty.containsKey(CommonConstants.INCLUDED_PURCHASE)) {
			includedWithYourPurchase = hpProperty.get(CommonConstants.INCLUDED_PURCHASE).toString();
			final String[] tags= {"catalogNumber","productName","quantitySize"};
			Pattern regex = Pattern.compile("<p>(.*?)</p>");
			Matcher matcher = regex.matcher(includedWithYourPurchase);
			ObjectMapper componentObjectMapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			while (matcher.find()) {
				JsonObject componentJsonObject = new JsonObject();
				if (!matcher.group(1).trim().isEmpty()) {
					String[] includedPurchase = matcher.group(1).split("#");
					for (int i = 0; i < includedPurchase.length; i++) {
						StringBuilder sb = new StringBuilder();
						sb.append(includedPurchase[i].toString());
						componentJsonObject.addProperty(tags[i], String.valueOf(sb));
						if (i == includedPurchase.length - 1) {
							componentJsonObject.addProperty(tags[2], includedPurchase[includedPurchase.length - 1].split(" ")[2]);
						}
					}
				}
				IncludedPurchaseLabelBean includedPurchaseLabelBean = null;
				try {
					includedPurchaseLabelBean = componentObjectMapper.readValue(componentJsonObject.toString(),
							IncludedPurchaseLabelBean.class);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
				instrumentPurchaseList.add(includedPurchaseLabelBean);
			}
		}
	}

	/**
	 * Update product details.
	 *
	 * @param labelMap the label map
	 * @param hpProperty the hp property
	 * @param property the property
	 * @param regionDetails the region details
	 * @param country the country
	 */
	private void updateProductDetails(Map<String, String> labelMap, ValueMap hpProperty, ValueMap property, Resource regionDetails, String country) {
		if (null != property.get(CommonConstants.PRODUCT_DETAILS)) {
			if (hpProperty.containsKey(property.get(CommonConstants.PRODUCT_DETAILS)) && !property.get(CommonConstants.PRODUCT_DETAILS).equals("regulatoryStatus")) {
				// adding values to a map for the product details
				productDetailsMap.put(labelMap.get(property.get(CommonConstants.PRODUCT_DETAILS)),
						hpProperty.get(property.get(CommonConstants.PRODUCT_DETAILS)).toString());
			}
			if(null != regionDetails) {
				//for RegulatoryStatus
				getRuoAttributes(labelMap, property, regionDetails, country,CommonConstants.REGULATORY_STATUS);
				//for Japan
				getJapanAttrubutes(labelMap, property, regionDetails, country);

				//for china
				getRegionSpecificAttributes(labelMap, property, regionDetails, country,"cfdaLicenseNumber","CN");

				//for Korea
				getKoreaAttributes(labelMap, property, regionDetails, country);
			}
			getAttrubutes(labelMap, hpProperty, property);
			if (property.get(CommonConstants.PRODUCT_DETAILS).equals(CommonConstants.WORKSHOP_NUMBER)
					&& hpProperty.containsKey(CommonConstants.CLONE)) {
				updateCloneAttributes(labelMap, hpProperty, property, CommonConstants.WORKSHOP_NUMBER);
			}
			if (property.get(CommonConstants.PRODUCT_DETAILS).equals(CommonConstants.MOLECULAR_WEIGHT)
					&& hpProperty.containsKey(CommonConstants.CLONE)) {
				updateCloneAttributes(labelMap, hpProperty, property, CommonConstants.MOLECULAR_WEIGHT);
			}
			if (property.get(CommonConstants.PRODUCT_DETAILS).equals(CommonConstants.HOST_SPECIES)
					&& hpProperty.containsKey(CommonConstants.CLONE)) {
				updateCloneAttributes(labelMap, hpProperty, property, CommonConstants.HOST_SPECIES);
			}
			if (property.get(CommonConstants.PRODUCT_DETAILS).equals(CommonConstants.ENTREZ_GENE_ID_KEY)
					&& hpProperty.containsKey(CommonConstants.CLONE)) {
				updateCloneAttributes(labelMap, hpProperty, property,CommonConstants.ENTREZ_GENE_ID_KEY);
			}

		}
	}

	private void getRuoAttributes(Map<String, String> labelMap, ValueMap property, Resource regionDetails,
								  String country, String propertyName) {
		String regulatoryStatus = StringUtils.EMPTY;
		if (property.get(CommonConstants.PRODUCT_DETAILS).equals(propertyName)) {
			String value = (String) property.get(CommonConstants.PRODUCT_DETAILS);
			ValueMap rdValueMap = regionDetails.adaptTo(ValueMap.class);
			if (rdValueMap.containsKey(country.toUpperCase())) {
				String regionStrObject = rdValueMap.get(country.toUpperCase(), String.class);
				JsonObject reginJsonObject = new Gson().fromJson(regionStrObject, JsonObject.class);
				if (null != reginJsonObject && null != value && null != reginJsonObject.get(value) && value.equalsIgnoreCase(CommonConstants.REGULATORY_STATUS)) {
					regulatoryStatus = reginJsonObject.get(value).getAsString();
				}
				if(StringUtils.isNotBlank(regulatoryStatus) && country.equalsIgnoreCase("jp") && regulatoryStatus.equals("IVD")) {
					regulatoryStatus = CommonHelper.getTranslatedRegulatoryStatus("JP_IVD", resourceResolver,solrConfig);
				}
				productDetailsMap.put(labelMap.get(value),
						regulatoryStatus);
			}
		}
	}

	/**
	 * Update clone attributes.
	 *
	 * @param labelMap the label map
	 * @param hpProperty the hp property
	 * @param property the property
	 * @param hpAttributeKey the hp attribute key
	 */
	private void updateCloneAttributes(Map<String, String> labelMap, ValueMap hpProperty, ValueMap property, String hpAttributeKey) {
		if(null != hpProperty.get(CommonConstants.CLONE)) {
			String clone = hpProperty.get(CommonConstants.CLONE).toString();
			if(StringUtils.isNotEmpty(clone)) {
				JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();
				if(null != cloneArray) {
					StringBuilder attributeBuilder = new StringBuilder();
					for (JsonElement cloneElement : cloneArray) {
						JsonObject cloneJsontObject = cloneElement.getAsJsonObject();
						if(null != cloneJsontObject && null != cloneJsontObject.get(hpAttributeKey)) {
							attributeBuilder
									.append(cloneJsontObject.get(hpAttributeKey).getAsString());
						}
					}
					productDetailsMap.put(labelMap.get(property.get(CommonConstants.PRODUCT_DETAILS)),
							attributeBuilder.toString());
				}
			}
		}
	}

	/**
	 * Gets the attrubutes.
	 *
	 * @param labelMap the label map
	 * @param hpProperty the hp property
	 * @param property the property
	 * @return the attrubutes
	 */
	public void getAttrubutes(Map<String, String> labelMap, ValueMap hpProperty, ValueMap property) {
		if (property.get(CommonConstants.PRODUCT_DETAILS).equals(CommonConstants.SPECIES_REACTIVITY_KEY)
				&& hpProperty.containsKey(property.get(CommonConstants.PRODUCT_DETAILS))) {
			updateReactivity(labelMap, hpProperty, property);
		}
		if (property.get(CommonConstants.PRODUCT_DETAILS).equals(CommonConstants.APPLICAATION_KEY)
				&& hpProperty.containsKey(property.get(CommonConstants.PRODUCT_DETAILS))) {
			updateApplication(labelMap, hpProperty, property);
		}
		if (property.get(CommonConstants.PRODUCT_DETAILS).equals(CommonConstants.ISOTYPE_KEY)
				&& hpProperty.containsKey(CommonConstants.CLONE)) {
			updateIsotypeKey(labelMap, hpProperty, property);
		}
		if (property.get(CommonConstants.PRODUCT_DETAILS).equals(CommonConstants.IMMUNOGEN_KEY)
				&& hpProperty.containsKey(CommonConstants.CLONE)) {
			updateImmunogenKey(labelMap, hpProperty, property);
		}
	}

	/**
	 * Gets the japan attrubutes.
	 *
	 * @param labelMap the label map
	 * @param property the property
	 * @param regionDetails the region details
	 * @param country the country
	 * @return the japan attrubutes
	 */
	public void getJapanAttrubutes(Map<String, String> labelMap, ValueMap property, Resource regionDetails,
								   String country) {
		if(country.equalsIgnoreCase("JP")) {
			getRegionSpecificAttributes(labelMap, property, regionDetails, country,"toxicity","JP");
			getRegionSpecificAttributes(labelMap, property, regionDetails, country,"ivd","JP");
			getRegionSpecificAttributes(labelMap, property, regionDetails, country,"ivdRegisteredName","JP");
		}
	}

	/**
	 * Gets the korea attributes.
	 *
	 * @param labelMap the label map
	 * @param property the property
	 * @param regionDetails the region details
	 * @param country the country
	 * @return the korea attributes
	 */
	public void getKoreaAttributes(Map<String, String> labelMap, ValueMap property, Resource regionDetails,
								   String country) {
		if(country.equalsIgnoreCase("KR")) {
			getRegionSpecificAttributes(labelMap, property, regionDetails, country,"productName","KR");
			getRegionSpecificAttributes(labelMap, property, regionDetails, country,"modelName","KR");
			getRegionSpecificAttributes(labelMap, property, regionDetails, country,CommonConstants.COUNTRY_OF_ORIGIN,"KR");
			getRegionSpecificAttributes(labelMap, property, regionDetails, country,"kfdaLicenseNumber","KR");
			getRegionSpecificAttributes(labelMap, property, regionDetails, country,"adReviewNumber","KR");
		}
	}

	/**
	 * Gets the region specific attributes.
	 *
	 * @param labelMap the label map
	 * @param property the property
	 * @param regionDetails the region details
	 * @param country the country
	 * @param propertyName the property name
	 * @param requiredCountry the required country
	 * @return the region specific attributes
	 */
	public void getRegionSpecificAttributes(Map<String, String> labelMap, ValueMap property, Resource regionDetails, String country, String propertyName, String requiredCountry) {
		if (property.get(CommonConstants.PRODUCT_DETAILS).equals(propertyName)
				&& country.equalsIgnoreCase(requiredCountry) && null != regionDetails) {

			String value = (String) property.get(CommonConstants.PRODUCT_DETAILS);
			ValueMap rdValueMap = regionDetails.adaptTo(ValueMap.class);
			if (rdValueMap.containsKey(requiredCountry)) {
				String japanStrObject = rdValueMap.get(requiredCountry,String.class);
				JsonObject japanObject = new Gson().fromJson(japanStrObject, JsonObject.class);
				if(null != japanObject.get(value) && !"toxicity".equals(value)) {
					if (value.equalsIgnoreCase(CommonConstants.COUNTRY_OF_ORIGIN)) {
						productDetailsMap.put(labelMap.get(value), japanObject.get(value).getAsJsonObject().get("name").getAsString());
					} else {
						productDetailsMap.put(labelMap.get(value), japanObject.get(value).getAsString());
					}
				}else if(null != japanObject.get(value) && "toxicity".equals(value)) {
					productDetailsMap.put("Laws and Regulations", japanObject.get(value).getAsString());
				}
			}
		}
	}

	/**
	 * Adds the recom assay.
	 *
	 * @param hpProperty the hp property
	 */
	private void addRecomAssay(ValueMap hpProperty) {
		String recomAssayProcedureMarkup = hpProperty.get(CommonConstants.RECOM_ASSAY_PROCEDURE).toString();
		this.recomAssayProcedure = recomAssayProcedureMarkup;
		Pattern regex = Pattern.compile("<p>(.*?)</p>");
		Matcher matcher = regex.matcher(recomAssayProcedureMarkup);
		while (matcher.find()) {
			if (!matcher.group(1).trim().isEmpty()) {
				recomAssayProcedureList.add(matcher.group(1));
			}
		}
	}

	/**
	 * Update immunogen key.
	 *
	 * @param labelMap the label map
	 * @param hpProperty the hp property
	 * @param property the property
	 */
	private void updateImmunogenKey(Map<String, String> labelMap, ValueMap hpProperty, ValueMap property) {
		String clone = hpProperty.get(CommonConstants.CLONE).toString();
		JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();

		StringBuilder immunogenStringBuilder = new StringBuilder();
		for (JsonElement cloneElement : cloneArray) {
			JsonObject cloneJsontObject = cloneElement.getAsJsonObject();
			immunogenStringBuilder
					.append(cloneJsontObject.get(CommonConstants.IMMUNOGEN_KEY).getAsString());
		}
		productDetailsMap.put(labelMap.get(property.get(CommonConstants.PRODUCT_DETAILS)),
				immunogenStringBuilder.toString());
	}

	/**
	 * Update isotype key.
	 *
	 * @param labelMap the label map
	 * @param hpProperty the hp property
	 * @param property the property
	 */
	private void updateIsotypeKey(Map<String, String> labelMap, ValueMap hpProperty, ValueMap property) {
		StringBuilder isotypeStringBuilder = new StringBuilder();
		if (hpProperty.containsKey(CommonConstants.CLONE)) {
			String clone = hpProperty.get(CommonConstants.CLONE).toString();
			JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();

			for (JsonElement cloneElement : cloneArray) {
				JsonObject cloneJsontObject = cloneElement.getAsJsonObject();
				isotypeStringBuilder
						.append(cloneJsontObject.get(CommonConstants.HOST_SPECIES_KEY).getAsString() + CommonConstants.SPACE);
				isotypeStringBuilder
						.append(cloneJsontObject.get(CommonConstants.HOST_STRAIN_KEY).getAsString() + CommonConstants.SPACE);
				isotypeStringBuilder
						.append(cloneJsontObject.get(CommonConstants.ISOTYPE_KEY).getAsString());
			}
		}
		productDetailsMap.put(labelMap.get(property.get(CommonConstants.PRODUCT_DETAILS)),
				isotypeStringBuilder.toString());

	}

	/**
	 * Update application.
	 *
	 * @param labelMap the label map
	 * @param hpProperty the hp property
	 * @param property the property
	 */
	private void updateApplication(Map<String, String> labelMap, ValueMap hpProperty, ValueMap property) {
		String application = hpProperty.get(property.get(CommonConstants.PRODUCT_DETAILS)).toString();
		JsonArray applicationArray = new JsonParser().parse(application).getAsJsonArray();
		StringBuilder applicationStringBuilder = new StringBuilder();
		int index = 0;
		for (JsonElement applicationElement : applicationArray) {
			index = index + 1;
			JsonObject applicationJsontObject = applicationElement.getAsJsonObject();
			applicationStringBuilder
					.append(applicationJsontObject.get(CommonConstants.APPLICATION_DESC).getAsString() + CommonConstants.SPACE );

			String appDesc = applicationJsontObject.get(CommonConstants.APPLICATION_STATUS_DESC).getAsString();
			if( StringUtils.isNotBlank(appDesc)) {
				applicationStringBuilder
						.append( "(" + appDesc + ")");
			}
			if (index < applicationArray.size()) {
				applicationStringBuilder.append(", ");
			}
		}
		productDetailsMap.put(labelMap.get(property.get(CommonConstants.PRODUCT_DETAILS)),
				applicationStringBuilder.toString());
	}

	/**
	 * Update reactivity.
	 *
	 * @param labelMap the label map
	 * @param hpProperty the hp property
	 * @param property the property
	 */
	private void updateReactivity(Map<String, String> labelMap, ValueMap hpProperty, ValueMap property) {
		String reactivity = hpProperty.get(property.get(CommonConstants.PRODUCT_DETAILS)).toString();
		JsonArray reactivityArray = new JsonParser().parse(reactivity).getAsJsonArray();
		StringBuilder reactivityStringBuilder = new StringBuilder();
		int index = 0;
		for (JsonElement reactivityElement : reactivityArray) {
			index = index + 1;
			JsonObject reactivityJsontObject = reactivityElement.getAsJsonObject();
			reactivityStringBuilder.append(
					reactivityJsontObject.get(CommonConstants.SPECIES_DESC).getAsString() + CommonConstants.SPACE );

			String statusDesc = reactivityJsontObject.get(CommonConstants.REACTIVITY_STATUS_DES).getAsString();
			if( StringUtils.isNotBlank(statusDesc) ) {
				reactivityStringBuilder.append( "(" + statusDesc + ")");
			}
			if (index < reactivityArray.size()) {
				reactivityStringBuilder.append(", ");
			}
		}
		productDetailsMap.put(labelMap.get(property.get(CommonConstants.PRODUCT_DETAILS)),
				reactivityStringBuilder.toString());
	}

	/**
	 * Gets the antibody details.
	 *
	 * @param hpResource the hp resource
	 * @param baseHpMap the base hp map
	 * @return the antibody details
	 */
	public void getAntibodyDetails(Resource hpResource, ValueMap baseHpMap) {

		//ValueMap hpProperty = hpResource.adaptTo(ValueMap.class);
		StringBuilder labelDescriptionStringBuilder = new StringBuilder();

		Resource variantHpResource = SolrUtils.getVariantHpResource(hpResource,
				request.getAttribute("catalogNumber").toString());
		if (null != variantHpResource) {
			ValueMap valueMap = variantHpResource.adaptTo(ValueMap.class);
			if(valueMap.containsKey(CommonConstants.TDS_REVISION_HP)){
				tdsRevisionNo=valueMap.get(CommonConstants.TDS_REVISION_HP,StringUtils.EMPTY);
			}
		}
		if (baseHpMap.containsKey(CommonConstants.LABEL_DESCRIPTION)) {
			labelDescriptionStringBuilder.append(baseHpMap.get(CommonConstants.LABEL_DESCRIPTION).toString());
			labelDescriptionStringBuilder.append(" Clone: ");
			labelDescription = getTdsCloneName(baseHpMap,CommonConstants.TDS_CLONE_NAME);
		}
		if (baseHpMap.containsKey(CommonConstants.TDS_DESCRIPTION)) {
			tdsDescription = baseHpMap.get(CommonConstants.TDS_DESCRIPTION).toString();
		}
		if (baseHpMap.containsKey(CommonConstants.OTHER_TDS_DESCRIPTION)) {
			otherTdsDescription = baseHpMap.get(CommonConstants.OTHER_TDS_DESCRIPTION).toString();
		}
		logger.debug("PDPTabModel - getAntibodyDetails end");
	}

	/**
	 * Gets the tds clone name.
	 *
	 * @param baseHpMap the base hp map
	 * @param tdsCloneAttribute
	 * @param labelDescriptionStringBuilder the label description string builder
	 * @return the tds clone name
	 */
	public String getTdsCloneName(ValueMap baseHpMap, String tdsCloneAttribute) {
		String cloneName = StringUtils.EMPTY;
		if (baseHpMap.containsKey(CommonConstants.CLONE)) {
			String clone = baseHpMap.get(CommonConstants.CLONE).toString();
			JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();
			for (JsonElement cloneElement : cloneArray) {
				JsonObject cloneJsontObject = cloneElement.getAsJsonObject();
				/*
				 * If the clone array has two Json objects we are getting tdsCloneName of second
				 * object If it has single object we will get tdsCloneName
				 */
				if (tdsCloneAttribute.equals(CommonConstants.TDS_CLONE_NAME)) {
					if (null != cloneJsontObject.get(CommonConstants.TDS_CLONE_NAME) && StringUtils
							.isNotEmpty(cloneJsontObject.get(CommonConstants.TDS_CLONE_NAME).toString())) {
						cloneName = cloneJsontObject.get(CommonConstants.TDS_CLONE_NAME).getAsString();
					}
				} else if (tdsCloneAttribute.equals(CommonConstants.TDS_CLONE_DISPLAY_NAME)
						&& null != cloneJsontObject.get(CommonConstants.TDS_CLONE_DISPLAY_NAME) && StringUtils
						.isNotEmpty(cloneJsontObject.get(CommonConstants.TDS_CLONE_DISPLAY_NAME).toString())) {
					cloneName = cloneJsontObject.get(CommonConstants.TDS_CLONE_DISPLAY_NAME).getAsString();
				}

			}
		}
		return cloneName;
	}

	/**
	 * Gets the format details.
	 *
	 * @param hpResource the hp resource
	 * @param assetJson the asset json
	 * @param baseHpMap the base hp map
	 * @return the format details
	 */
	public void getFormatDetails(Resource hpResource, JsonObject assetJson, ValueMap baseHpMap) {


		if (baseHpMap.containsKey(CommonConstants.BD_FORMAT)) {
			JsonArray formatDetailsJsonArray = new JsonParser()
					.parse(baseHpMap.get(CommonConstants.BD_FORMAT).toString()).getAsJsonArray();
			if(null != assetJson && assetJson.has("OTHER_FORMAT")) {
				formatDetailsImage = assetJson.get("OTHER_FORMAT").getAsString();
			}
			for (JsonElement formatDetailsElement : formatDetailsJsonArray)
				getFormatDetailJson(formatDetailsElement);
		}
		logger.debug("PDPTabModel - getFormatDetails end");
	}

	/**
	 * Gets the format detail json.
	 *
	 * @param formatDetailsElement the format details element
	 * @return the format detail json
	 */
	private void getFormatDetailJson(JsonElement formatDetailsElement) {

		JsonObject formatDetailsJson = formatDetailsElement.getAsJsonObject();
		if (formatDetailsJson.has(CommonConstants.DYE_NAME) && (null != formatDetailsJson.get(CommonConstants.DYE_NAME))
				&& (!formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString().isEmpty())) {
			formatname = formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString();
		}
		if (formatDetailsJson.has(CommonConstants.EMMISSION_MAX)
				&& (null != formatDetailsJson.get(CommonConstants.EMMISSION_MAX))
				&& (!formatDetailsJson.get(CommonConstants.EMMISSION_MAX).getAsString().isEmpty())) {
			emmisionmax = formatDetailsJson.get(CommonConstants.EMMISSION_MAX).getAsString()
					.concat(CommonConstants.SPACE).concat(CommonConstants.NM);
		}
		if (formatDetailsJson.has(CommonConstants.EXCITATION_MAX)
				&& (null != formatDetailsJson.get(CommonConstants.EXCITATION_MAX))
				&& (!formatDetailsJson.get(CommonConstants.EXCITATION_MAX).getAsString().isEmpty())) {
			excitationmax = formatDetailsJson.get(CommonConstants.EXCITATION_MAX).getAsString()
					.concat(CommonConstants.SPACE).concat(CommonConstants.NM);
			excitationmax = excitationmax.replace(CommonConstants.COMMA,
					CommonConstants.SPACE + CommonConstants.NM + CommonConstants.COMMA);
		}
		if ((formatDetailsJson.has(CommonConstants.LASER_COLOR)
				|| formatDetailsJson.has(CommonConstants.LASER_WAVELENGTH))
				&& (null != formatDetailsJson.get(CommonConstants.LASER_COLOR))
				&& (null != formatDetailsJson.get(CommonConstants.LASER_WAVELENGTH))) {
			String lColor = formatDetailsJson.get(CommonConstants.LASER_COLOR).getAsString();
			String lWaveLength = formatDetailsJson.get(CommonConstants.LASER_WAVELENGTH).getAsString();
			if (!StringUtils.isEmpty(lColor) && !StringUtils.isEmpty(lWaveLength)) {
				excitationSource = formatDetailsJson.get(CommonConstants.LASER_COLOR).getAsString() + " "
						+ formatDetailsJson.get(CommonConstants.LASER_WAVELENGTH).getAsString()
						.concat(CommonConstants.SPACE).concat(CommonConstants.NM);
				excitationSource = excitationSource.replace(CommonConstants.COMMA,
						CommonConstants.SPACE + CommonConstants.NM + CommonConstants.COMMA);
			}
		}
		if (formatDetailsJson.has(CommonConstants.FORMAT_STATEMENT)) {
			bdFormatdescription = formatDetailsJson.get(CommonConstants.FORMAT_STATEMENT).getAsString();
		}

	}

	/**
	 * Gets the references.
	 *
	 * @param hpResource the hp resource
	 * @param baseHpMap the base hp map
	 * @return the references
	 * @throws JsonProcessingException the json processing exception
	 */
	public void getReferences(Resource hpResource, ValueMap baseHpMap) throws JsonProcessingException {

		if (baseHpMap.containsKey(CommonConstants.REFERENCE_DETAILS)) {
			String referenceJsonString = baseHpMap.get(CommonConstants.REFERENCE_DETAILS).toString();
			JsonArray referenceArray = new JsonParser().parse(referenceJsonString).getAsJsonArray();
			ObjectMapper referenceObjectMapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			for (JsonElement reference : referenceArray) {
				JsonObject referenceJsontObject = reference.getAsJsonObject();
				referenceJsontObject.addProperty(CommonConstants.PUBMEDID_VALUE, referenceJsontObject.get(CommonConstants.PUBMEDID).getAsString());
				referenceJsontObject.addProperty(CommonConstants.PUBMEDID,
						bdbApiEndpointService.getPubMedId() + referenceJsontObject.get(CommonConstants.PUBMEDID).getAsString());
				ReferenceBean referenceBean = referenceObjectMapper.readValue(referenceJsontObject.toString(),
						ReferenceBean.class);
				referenceList.add(referenceBean);
			}
			Collections.sort(referenceList);
		}

		logger.debug("PDPTabModel - getReferences end");
	}

	/**
	 * Adds the json clone object.
	 *
	 * @return the string
	 */
	private String addJsonCloneObject()
	{
		JsonObject label =new JsonObject();
		label.addProperty(CommonConstants.BRAND,brandLabel);
		label.addProperty(CommonConstants.CLONE,cloneLabel);
		label.addProperty(CommonConstants.CATALOG_NO,catalogNoLabel);
		label.addProperty(CommonConstants.STATUS,regulatoryStatusLabel);
		label.addProperty(CommonConstants.OTHER_CLONES,otherCloneLabel);
		label.addProperty(CommonConstants.OTHER_FORMATS,otherFormatLabel);
		label.addProperty(CommonConstants.YOUR_PRICE_CLONE_KEY,yourPriceLabel);
		label.addProperty(CommonConstants.LIST_PRICE_LABEL,listPriceLabel);
		label.addProperty(CommonConstants.COMPARE_CLONE_KEY,compareLabel);
		label.addProperty(CommonConstants.CURRENCY_CODE,currencyCode);
		label.addProperty(CommonConstants.RESULTS_CLONE_KEY,filterResultsLabel);
		label.addProperty(CommonConstants.SELECT_ALL,selectAllLabel);
		label.addProperty(CommonConstants.FILTER_RESULTS,filterMobile);
		label.addProperty(CommonConstants.GROUP,groupLabel);
		label.addProperty(CommonConstants.SIGN_IN,signInLabel);
		label.addProperty(CommonConstants.ADD_TO_CART_LABEL,addToCartLabel);
		label.addProperty(CommonConstants.ADD_TO_CART_ALT,addToCartAlt);

		label.addProperty(CommonConstants.COMPARE_CURRENT_PDP,compareCurrentPdpLabel);

		label.addProperty(CommonConstants.VIEW_MORE,viewMore);
		label.addProperty(CommonConstants.CANCEL,cancel);
		label.addProperty("apply",apply);
		label.addProperty("allFormats",allFormats);
		label.addProperty("allClones",allClones);
		label.addProperty(CommonConstants.INQUIRE,inquireLabel);


		label.addProperty(CommonConstants.ADD_TO_QUOTE,
				CommonHelper.getLabel(CommonConstants.ADD_TO_QUOTE_LABEL, currentPage));

		label.addProperty(CommonConstants.ADD_TO_QUOTE_ALT,
				CommonHelper.getLabel(CommonConstants.QUOTE_ALT_TEXT_LABEL, currentPage));

		Boolean isQuote=pageProperties.getInherited("enableAddToQuoteCheckBox",Boolean.FALSE);
		label.addProperty(CommonConstants.IS_QUOTE,isQuote);
		if(Boolean.TRUE.equals(isQuote)) {
			label.addProperty(CommonConstants.ADD_TO_QUOTE, addToQuoteLabel);
			label.addProperty(CommonConstants.ADD_TO_QUOTE_ALT,addToQuoteAlt);
			label.addProperty(CommonConstants.SAVE_TO_SHOPPING_LIST, CommonHelper.getLabel(CommonConstants.SAVE_TO_QUOTE_LIST,currentPage));
		}else {
			label.addProperty(CommonConstants.SAVE_TO_SHOPPING_LIST, CommonHelper.getLabel(CommonConstants.SAVE_TO_SHOPPING_LIST_KEY,currentPage));
		}
		return label.toString();
	}

	/**
	 * Gets the preparation storage.
	 *
	 * @return preparationStorage
	 */
	public String getPreparationStorage() {
		return preparationStorage;
	}

	/**
	 * Gets the adds the preparation storage.
	 *
	 * @return addPreparationStorage
	 */
	public String getAddPreparationStorage() {
		return addPreparationStorage;
	}

	/**
	 * Gets the product details map.
	 *
	 * @return the product details map
	 */
	public Map<String, String> getProductDetailsMap() {
		return productDetailsMap;
	}

	/**
	 * Gets the product details tab label.
	 *
	 * @return the product details tab label
	 */
	public String getProductDetailsTabLabel() {
		return productDetailsTabLabel;
	}

	/**
	 * Gets the resources.
	 *
	 * @return resources
	 */
	public String getResources(){ return resources; }

	/**
	 * Gets the includedWithYourPurchase.
	 *
	 * @return includedWithYourPurchase
	 */
	public String getIncludedWithYourPurchase(){ return includedWithYourPurchase; }

	public String getInstrumentDetailsTabLabel() { return instrumentDetailsTabLabel; }

	public String getProductNameLabel() {return productNameLabel;}

	public String getInstrumentRegulatoryStatusLabel(){return instrumentRegulatoryStatusLabel;}

	public String getQuantityLabel(){return quantityLabel;}

	public String getCatalogNumberLabel(){return catalogNumberLabel;}
	
	/**
	 * @return the phantomTitleText
	 */
	public String getPhantomTitleText() {
		return phantomTitleText;
	}

	/**
	 * @return the phantomSubTitleText
	 */
	public String getPhantomSubTitleText() {
		return phantomSubTitleText;
	}

	/**
	 * @return the descriptionLabel
	 */
	public String getDescriptionLabel() {
		return descriptionLabel;
	}

	/**
	 * @return the sizeQuantityLabel
	 */
	public String getSizeQuantityLabel() {
		return sizeQuantityLabel;
	}

	/**
	 * @return the catNumberLabel
	 */
	public String getCatNumberLabel() {
		return catNumberLabel;
	}

	/**
	 * Gets the companionProductsLabel.
	 *
	 * @return the companionProducts tab Label
	 */
	public String getCompanionProductsLabel() {
		return companionProductsLabel;
	}

	/**
	 * Gets the productUrl.
	 *
	 * @return the productUrl
	 */
	public String getProductUrl() {
		return productUrl;
	}

	public List<IncludedPurchaseLabelBean> getInstrumentPurchaseList(){
		return new ArrayList<>(instrumentPurchaseList);
	}

	/**
	 * Gets the antibody details tab label.
	 *
	 * @return the antibody details tab label
	 */
	public String getAntibodyDetailsTabLabel() {
		return antibodyDetailsTabLabel;
	}

	/**
	 * Gets the format details tab label.
	 *
	 * @return the format details tab label
	 */
	public String getFormatDetailsTabLabel() {
		return formatDetailsTabLabel;
	}

	/**
	 * Gets the references tab label.
	 *
	 * @return the references tab label
	 */
	public String getReferencesTabLabel() {
		return referencesTabLabel;
	}

	/**
	 * Gets the label description.
	 *
	 * @return the label description
	 */
	public String getLabelDescription() {
		return labelDescription;
	}

	/**
	 * Gets the tds description.
	 *
	 * @return the tds description
	 */
	public String getTdsDescription() {
		return tdsDescription;
	}

	/**
	 * Gets the formatname.
	 *
	 * @return the formatname
	 */
	public String getFormatname() {
		return formatname;
	}

	/**
	 * Gets the emmisionmax.
	 *
	 * @return the emmisionmax
	 */
	public String getEmmisionmax() {
		return emmisionmax;
	}

	/**
	 * Gets the excitationmax.
	 *
	 * @return the excitationmax
	 */
	public String getExcitationmax() {
		return excitationmax;
	}

	/**
	 * Gets the excitation source.
	 *
	 * @return the excitation source
	 */
	public String getExcitationSource() {
		return excitationSource;
	}

	/**
	 * Gets the view all formats label.
	 *
	 * @return the view all formats label
	 */
	public String getViewAllFormatsLabel() {
		return viewAllFormatsLabel;
	}

	/**
	 * Gets the bd format description.
	 *
	 * @return the bd format description
	 */
	public String getBdFormatdescription() {
		return bdFormatdescription;
	}

	/**
	 * Gets the format details image.
	 *
	 * @return the format details image
	 */
	public String getFormatDetailsImage() {
		return formatDetailsImage;
	}

	/**
	 * Gets the citation list.
	 *
	 * @return the citation list
	 */
	public List<ReferenceBean> getReferenceList() {
		return new ArrayList<>(referenceList);
	}


	/**
	 * Gets the component list.
	 *
	 * @return the component list
	 */
	public List<ComponentBean> getComponentList() {
		return new ArrayList<>(componentList);
	}

	/**
	 * Gets the recom assay procedure list.
	 *
	 * @return the recom assay procedure list
	 */
	public List<String> getRecomAssayProcedureList() {
		return new ArrayList<>(recomAssayProcedureList);
	}

	/**
	 * Gets the recom assay procedure list.
	 *
	 * @return the recom assay procedure list
	 */
	public String getRecomAssayProcedure() {
		return recomAssayProcedure;
	}

	/**
	 * Gets the product notices list.
	 *
	 * @return the product notices list
	 */
	public List<String> getProductNoticesList() {
		return new ArrayList<>(productNoticesList);
	}

	/**
	 * Gets the rap title text.
	 *
	 * @return the rap title text
	 */
	public String getRapTitleText() {
		return rapTitleText;
	}

	/**
	 * Gets the pn title text.
	 *
	 * @return the pn title text
	 */
	public String getPnTitleText() {
		return pnTitleText;
	}

	/**
	 * Gets the no of links to be shown.
	 *
	 * @return the no of links to be shown
	 */
	public int getNoOfLinksToBeShown() {
		return noOfLinksToBeShown;
	}

	/**
	 * Gets the rapshowmoreless desktop.
	 *
	 * @return the rapshowmoreless desktop
	 */
	public int getRapshowmorelessDesktop() {
		return rapshowmorelessDesktop;
	}

	/**
	 * Gets the rapshowmoreless mobile.
	 *
	 * @return the rapshowmoreless mobile
	 */
	public int getRapshowmorelessMobile() {
		return rapshowmorelessMobile;
	}

	/**
	 * Gets the showmorelabel.
	 *
	 * @return the showmorelabel
	 */
	public String getShowmorelabel() {
		return showmorelabel;
	}

	/**
	 * Gets the showlesslabel.
	 *
	 * @return the showlesslabel
	 */
	public String getShowlesslabel() {
		return showlesslabel;
	}

	/**
	 * Gets the pn show more less limit desktop.
	 *
	 * @return the pn show more less limit desktop
	 */
	public int getPnShowMoreLessLimitDesktop() {
		return pnShowMoreLessLimitDesktop;
	}

	/**
	 * Gets the pn show more less limit mobile.
	 *
	 * @return the pn show more less limit mobile
	 */
	public int getPnShowMoreLessLimitMobile() {
		return pnShowMoreLessLimitMobile;
	}

	/**
	 * Gets the clone label object.
	 *
	 * @return JsonString of the Clone Label values
	 */
	public String getCloneLabelObject() {
		return cloneLabelObject;
	}

	/**
	 * Gets the clone api object.
	 *
	 * @return cloneApiObject
	 */
	public String getCloneApiObject() {
		return cloneApiObject;
	}

	/**
	 * Gets the format api object.
	 *
	 * @return formatApiObject
	 */
	public String getFormatApiObject() {
		return formatApiObject;
	}

	/**
	 * Gets the instrument ctatext.
	 *
	 * @return instrumentCtatext
	 */
	public String getInstrumentCtatext() {
		return instrumentCtatext;
	}

	/**
	 * Gets the instrument cta link.
	 *
	 * @return instrumentCtaLink
	 */
	public String getInstrumentCtaLink() {
		return externalizerService.getFormattedUrl(instrumentCtaLink,resourceResolver);
	}

	/**
	 * Gets the recently viewed json string.
	 *
	 * @return the recently viewed json string
	 */
	public String getRecentlyViewedJsonString() {
		return recentlyViewedJsonString;
	}

	/**
	 * Gets the tds revision no.
	 *
	 * @return TdsRevision No
	 */
	public String getTdsRevisionNo() {
		return tdsRevisionNo;
	}

	/**
	 * Gets the material number.
	 *
	 * @return Catalog Number
	 */
	public String getMaterialNumber() {
		return materialNumber;
	}

	/**
	 * Gets the components label.
	 *
	 * @return the components label
	 */
	public String getComponentsLabel() {
		return componentsLabel;
	}

	/**
	 * Gets the component description label.
	 *
	 * @return the component description label
	 */
	public String getComponentDescriptionLabel() {
		return componentDescriptionLabel;
	}

	/**
	 * Gets the component size label.
	 *
	 * @return the component size label
	 */
	public String getComponentSizeLabel() {
		return componentSizeLabel;
	}

	/**
	 * Gets the component part number label.
	 *
	 * @return the component part number label
	 */
	public String getComponentPartNumberLabel() {
		return componentPartNumberLabel;
	}

	/**
	 * Gets the component isotype label.
	 *
	 * @return the component isotype label
	 */
	public String getComponentIsotypeLabel() {
		return componentIsotypeLabel;
	}

	/**
	 * Gets the component EntrezGeneID label.
	 *
	 * @return the component EntrezGeneId label
	 */
	public String getComponentEntrezGeneIdLabel() {
		return componentEntrezGeneIdLabel;
	}

	/**
	 * Gets the component clone label.
	 *
	 * @return the component clone label
	 */
	public String getComponentCloneLabel() {
		return componentCloneLabel;
	}


	/**
	 * Gets the row multifield.
	 *
	 * @return the row multifield
	 */
	public List<KitsAndSetsRowMultifieldModel> getRowMultifield() {
		return new ArrayList<>(rowMultifield);
	}

	/**
	 * Gets the column multifield.
	 *
	 * @return the column multifield
	 */
	public List<KitsAndSetsColumnMultifieldModel> getColumnMultifield() {
		return new ArrayList<>(columnMultifield);
	}

	/**
	 * Gets the checks if is author.
	 *
	 * @return the checks if is author
	 */
	public boolean getIsAuthor() {
		return isAuthor;
	}

	public String getIsregulatoryStatus() { return isregulatoryStatus;}

	/**
	 * Gets the product notice as string.
	 *
	 * @return string
	 */
	public String getProductNotices() {
		return productNotices;
	}

	/**
	 * Gets the productType as string.
	 *
	 * @return string
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * Gets the brand description.
	 *
	 * @return the brand description
	 */
	public String getBrandDescription() {
		return brandDescription;
	}

	/**
	 * Gets the tds clone name.
	 *
	 * @return the tds clone name
	 */
	public String getTdsCloneName() {
		return tdsCloneName;
	}

	/**
	 * Gets the product application.
	 *
	 * @return the product application
	 */
	public String getProductApplication() {
		return productApplication;
	}

	/**
	 * Gets the format name.
	 *
	 * @return the format name
	 */
	public String getFormatData() {
		return formatData;
	}

	/**
	 * Gets the additional property.
	 *
	 * @return the additional property
	 */
	public String getAdditionalProperty() {
		return additionalProperty;
	}

	/**
	 * Gets the brand.
	 *
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	public String getAbSeqBrand() {
		return abSeqBrand;
	}

	public String getOptiBuild() {
		return OptiBuild;
	}
	
	/**
	 * @return the phantomChilds
	 */
	public String getPhantomChilds() {
		return phantomChilds;
	}

	public String getCiteabApiKey() { return citeabApiKey;}

	public String getCiteabCompanySlug() { return citeabCompanySlug;}

	public String getCiteabScriptUrl() { return citeabScriptUrl;}

	public String showCiteabWidget() {return showCiteabWidget;}

	/**
	 * @return the phantomProductsList
	 */
	public List<PhantomProductDetailsBean> getPhantomProductsList() {
		return phantomProductsList;
	}

	/**
	 * @return the isPhantom
	 */
	public String getIsPhantom() {
		return isPhantom;
	}
	
}
