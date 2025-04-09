package com.bdb.aem.core.models;

import com.bdb.aem.core.bean.CompanionProductsBean;
import com.bdb.aem.core.bean.ProductHeroBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrUtils;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.eclipse.jetty.util.StringUtil;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.RepositoryException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.bdb.aem.core.bean.PDPImagesBean;

/**
 * The Class ProductHeroModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductHeroModel {

	/**
	 * The logger.
	 */
	Logger logger = LoggerFactory.getLogger(ProductHeroModel.class);

	/**
	 * The request.
	 */
	@SlingObject
	private SlingHttpServletRequest request;

	/**
	 * The hero bean.
	 */
	@Inject
	ProductHeroBean productHeroBean;
	
	@Inject
	PDPTabModel pdpTabModel;

	/**
	 * The BDB Api Endpoint Service.
	 */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/**
	 * The companion bean.
	 */
	@Inject
	CompanionProductsBean companionBean;

	/**
	 * The formats label.
	 */
	@Inject
	@Named("allFormatsLabel")
	@Via("resource")
	private String formatsLabel;

	/**
	 * The clone label.
	 */
	@Inject
	@Named("cloneLabel")
	@Via("resource")
	private String cloneLabel;

	/**
	 * The show less cta.
	 */
	@Inject
	@Named("showLessCta")
	@Via("resource")
	private String showLessCta;

	/**
	 * The show more cta.
	 */
	@Inject
	@Named("showMoreCta")
	@Via("resource")
	private String showMoreCta;

	/** The lines count. */
	@Inject
	@Named("linesCount")
	@Via("resource")
	private String linesCount;

	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;

	/**
	 * The product title.
	 */
	private String productTitle;

	/**
	 * The product clone.
	 */
	private String productClone;

	/** The regulatory status. */
	private String regulatoryStatus;
	
	private String abSeqBrand = "BD™ AbSeq";

	/**
	 * The bean list.
	 */
	private List<ProductHeroBean> beanList;
	
	/** The PDP Images Bean list. */
	private List<PDPImagesBean> imagesList;

	/**
	 * The externalizer service.
	 */
	@Inject
	ExternalizerService externalizerService;
	
	/** The bdb search endpoint service. */
	@Inject
	BDBSearchEndpointService solrConfig;
	
	@Inject
	SolrSearchService solrSearchService;
	
	/**
	 * The current page.
	 */
	@Inject
	private Page currentPage;

	/**
	 * The icon 1.
	 */
	private String icon1;

	/**
	 * The icon 2.
	 */
	private String icon2;

	/**
	 * The icon 3.
	 */
	private String icon3;

	/**
	 * The link 1.
	 */
	private String link1;

	/**
	 * The link 2.
	 */
	private String link2;

	/**
	 * The link 3.
	 */
	private String link3;

	/**
	 * The text 1.
	 */
	private String text1;

	/**
	 * The text 2.
	 */
	private String text2;

	/**
	 * The text 3.
	 */
	private String text3;

	/** Template Id of the Material Number. */
	private String templateId;

	/** The display spectrum viewer tool. */
	@Getter
	private Boolean displaySpectrumViewerTool;

	/** Dye name excel path. */
	private String dyeNameExcelPath;
	
	/** skuNumber. */
	private String skuNumber;
	
	/** productName. */
	private String productName;
	
	/** Page url. */
	private String pageUrl;
	
	/**
	 * The product sub Heading.
	 */
	private String productCategory;
	
	/** productImage. */
	private String productImage;
	
	/** imageList. */
	private ArrayList<String> imageList = new ArrayList<String>();
	
	/** finalImageList */
	private ArrayList<String> finalImageList = new ArrayList<String>();
	
	/** flag */
	private boolean flag = true;
	
	/** productImage description. */
	private String productImageDesc;
	
	/** The brand. */
	private String brand;
	
	/** pdpAdditionalProperties. */
	private List<JsonObject> pdpAdditionalProperties;
	
	/** The alternativeName. */
	private String alternativeName;
	
	/** The sizeQty. */
	private String sizeQty;
	
	/** weight */
	private String weight;
	
	/** depth */
	private String depth;
	
	/** keywords */
	private String keywords;
	
	/** isRelatedTo */
	private String isRelatedTo;
	
	/** releaseDate */
	private String releaseDate;
	
	/** variants */
	private ArrayList<String> variants;
	
	private String bdFormatdescription;

	private String tdsDescription;
	
	private String productSchemaDescription;
	
	private JsonObject productData;
	
	


	public Logger getLogger() {
		return logger;
	}

	public SlingHttpServletRequest getRequest() {
		return request;
	}

	public ProductHeroBean getProductHeroBean() {
		return productHeroBean;
	}

	public PDPTabModel getPdpTabModel() {
		return pdpTabModel;
	}

	public BDBApiEndpointService getBdbApiEndpointService() {
		return bdbApiEndpointService;
	}

	public CompanionProductsBean getCompanionBean() {
		return companionBean;
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public List<PDPImagesBean> getImagesList() {
		return imagesList;
	}

	public ExternalizerService getExternalizerService() {
		return externalizerService;
	}

	public BDBSearchEndpointService getSolrConfig() {
		return solrConfig;
	}

	public SolrSearchService getSolrSearchService() {
		return solrSearchService;
	}

	public Page getCurrentPage() {
		return currentPage;
	}

	public String getTemplateId() {
		return templateId;
	}

	public Boolean getDisplaySpectrumViewerTool() {
		return displaySpectrumViewerTool;
	}

	public String getDyeNameExcelPath() {
		return dyeNameExcelPath;
	}

	public ArrayList<String> getImageList() {
		return imageList;
	}

	public boolean isFlag() {
		return flag;
	}

	/**
	 * Inits the.
	 * @throws ParseException 
	 * @throws RepositoryException 
	 */
	@PostConstruct
	protected void init() throws ParseException {
		logger.debug("Inside ProductHeroModel Init");

		try {
			Resource hpBaseResource = null;
			if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)) {
				String productVarHPPath = request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString();
				hpBaseResource = resourceResolver.getResource(productVarHPPath);
			}
			String country = CommonHelper.getCountry(currentPage);
			String catalogNumber = request.getAttribute(CommonConstants.CATALOG_NUMBER_KEY) != null ? request.getAttribute(CommonConstants.CATALOG_NUMBER_KEY).toString():StringUtils.EMPTY;
			Resource variantResource = CommonHelper.getProductFromLookUp(resourceResolver, catalogNumber, CommonConstants.MATERIAL_NUMBER);
			ValueMap baseVarientValueMap = null;
			if (null != hpBaseResource) {
				baseVarientValueMap = hpBaseResource.getValueMap();
				alternativeName = null != baseVarientValueMap && baseVarientValueMap.containsKey(CommonConstants.ALTERNATIVE_NAME) ? baseVarientValueMap.get(CommonConstants.ALTERNATIVE_NAME, String.class) : StringUtils.EMPTY;
				sizeQty = null != baseVarientValueMap && baseVarientValueMap.containsKey(CommonConstants.SIZE_QTY) ? baseVarientValueMap.get(CommonConstants.SIZE_QTY, String.class) : StringUtils.EMPTY;
				depth = null != baseVarientValueMap && baseVarientValueMap.containsKey(CommonConstants.DIMENSIONS_KEY) ? baseVarientValueMap.get(CommonConstants.DIMENSIONS_KEY, String.class) : StringUtils.EMPTY;
				weight = null != baseVarientValueMap && baseVarientValueMap.containsKey(CommonConstants.WEIGHT_KEY) ? baseVarientValueMap.get(CommonConstants.WEIGHT_KEY, String.class) : StringUtils.EMPTY;
				keywords = null != baseVarientValueMap && baseVarientValueMap.containsKey(CommonConstants.KEYWORDS) ? baseVarientValueMap.get(CommonConstants.KEYWORDS, String.class) : StringUtils.EMPTY;
				isRelatedTo = null != baseVarientValueMap && baseVarientValueMap.containsKey("isRelatedTo") ? baseVarientValueMap.get("isRelatedTo", String.class) : StringUtils.EMPTY;
				variants = null != baseVarientValueMap && baseVarientValueMap.containsKey(CommonConstants.VARIANTS) ? getVariants(baseVarientValueMap) : new ArrayList<>();
				templateId = null != baseVarientValueMap && baseVarientValueMap.containsKey(CommonConstants.PDP_TEMPLATE) ? baseVarientValueMap.get(CommonConstants.PDP_TEMPLATE, String.class) : StringUtils.EMPTY;
				tdsDescription = null != baseVarientValueMap && baseVarientValueMap.containsKey(CommonConstants.TDS_DESCRIPTION) ? baseVarientValueMap.get(CommonConstants.TDS_DESCRIPTION, String.class) : StringUtils.EMPTY;
				/* for PDP hero */
				String desc = StringUtils.isNotEmpty(baseVarientValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)) ? baseVarientValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class) : StringUtils.EMPTY;
				
				if(!desc.isEmpty()){
					productTitle = desc;
				}
				if (null != catalogNumber && null != hpBaseResource.getParent().getChild(catalogNumber)) {
					regulatoryStatus = CommonHelper.getRuoFromRegionDetails(hpBaseResource, country, catalogNumber);
				}
				if(StringUtils.isNotBlank(regulatoryStatus) && country.equalsIgnoreCase("jp") && regulatoryStatus.equals("IVD")) {
					regulatoryStatus = CommonHelper.getTranslatedRegulatoryStatus("JP_IVD", resourceResolver,solrConfig);
				}
				
				productClone = getTdsClone(baseVarientValueMap);
				
				 if(null != baseVarientValueMap.get(CommonConstants.BRAND) && StringUtils.isNotEmpty(baseVarientValueMap.get(CommonConstants.BRAND,String.class))) {
						brand = baseVarientValueMap.get(CommonConstants.BRAND,String.class);
				 }
				 skuNumber = catalogNumber;
				 productName = request.getAttribute(CommonConstants.PRODUCT_NAME) != null ? request.getAttribute(CommonConstants.PRODUCT_NAME).toString():StringUtils.EMPTY;
				 productImage = request.getAttribute("thumbnailImagePath") != null ? request.getAttribute("thumbnailImagePath").toString():StringUtils.EMPTY;
				 
				 String Url  =  externalizerService.getFormattedUrl(currentPage.getPath(), resourceResolver);
				 if(StringUtils.contains(Url, "pdp")) {
					 String name = productName;
					 name = name.replace("/","-");
					 name = name.replace(" ","-");
					 Url = Url.replace("pdp", name+ "." + skuNumber) ;
				 }
				 pageUrl = Url;
				 
				 if (country.equalsIgnoreCase("JP") || CommonConstants.EMEA_COUNTRIES_LIST.contains(country.toLowerCase())) {
					 releaseDate = getReleaseDateJpAndEmea(variantResource, country);
				 } else {
					 releaseDate = null != baseVarientValueMap && baseVarientValueMap.containsKey(CommonConstants.AFS_DATE) ? solrSearchService.getReleaseDate(SolrUtils.checkNull(baseVarientValueMap.get(CommonConstants.AFS_DATE, String.class))) : StringUtils.EMPTY;
				 }
				 pdpAdditionalProperties = getAdditionalProperties(baseVarientValueMap);
				 
				 if(StringUtils.isNotEmpty(productClone) && StringUtils.isNotEmpty(regulatoryStatus)) {
					 productCategory = cloneLabel+" "+ productClone+ " "+ "(" + regulatoryStatus+ ")";
				 }
				 if(StringUtils.isNotEmpty(productClone) && StringUtils.isEmpty(regulatoryStatus)) {
					 productCategory = cloneLabel+" "+ productClone ;
				 }
				 if(StringUtils.isEmpty(productClone) && StringUtils.isNotEmpty(regulatoryStatus)) {
					 productCategory = "(" + regulatoryStatus+ ")";
				 }
				 
				if(StringUtils.isNotEmpty(tdsDescription) || StringUtils.isNotEmpty(bdFormatdescription)) {
					productSchemaDescription = getHtmlParsedString(tdsDescription + " " + bdFormatdescription);
				}
				 productData = getProductDataJson();
				/* For Product Images*/
				getAssetListBean();
			}

			String lookupPath = currentPage.getPath() + CommonConstants.ROOT_PDP_LOOKUP_PATH;
			Resource xfResource = resourceResolver.getResource(lookupPath);
			String xf = StringUtils.EMPTY;
			if( null != xfResource && StringUtils.isNotEmpty(templateId) ) {
				ValueMap xfMap = xfResource.adaptTo(ValueMap.class);

				switch (templateId) {
					case CommonConstants.SFA:
						xf = (null != xfMap.get(CommonConstants.PROP_SFA, String.class))
								? (xfMap.get(CommonConstants.PROP_SFA, String.class) + CommonConstants.TECH_TOOLS_PATH)
								: StringUtils.EMPTY;
						break;
					case CommonConstants.SCM:
						xf = (null != xfMap.get(CommonConstants.PROP_SCM, String.class))
								? (xfMap.get(CommonConstants.PROP_SCM, String.class) + CommonConstants.TECH_TOOLS_PATH)
								: StringUtils.EMPTY;
						break;
					case CommonConstants.KIT:
						xf = (null != xfMap.get(CommonConstants.PROP_KIT, String.class))
								? (xfMap.get(CommonConstants.PROP_KIT, String.class) + CommonConstants.TECH_TOOLS_PATH)
								: StringUtils.EMPTY;
						break;
					case CommonConstants.OTHER:
						xf = (null != xfMap.get(CommonConstants.PROP_OTHER, String.class))
								? (xfMap.get(CommonConstants.PROP_OTHER, String.class) + CommonConstants.TECH_TOOLS_PATH)
								: StringUtils.EMPTY;
						break;
					case CommonConstants.INSTRUMENTS:
						xf = (null != xfMap.get(CommonConstants.PROP_INSTRUMENTS, String.class))
								? (xfMap.get(CommonConstants.PROP_INSTRUMENTS, String.class)
								+ CommonConstants.TECH_TOOLS_PATH)
								: StringUtils.EMPTY;
						break;
					default:
						xf = StringUtils.EMPTY;
				}
			}
			getTechnicalTools(resourceResolver, xf, country,hpBaseResource);

		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException {}", e.getMessage());
		} finally {
			//CommonHelper.closeResourceResolver(resourceResolver);
		}
		logger.debug("End of ProductHeroModel Init");
	}
	
	private String getHtmlParsedString(String htmlString) {
		if (!StringUtils.isEmpty(htmlString)) {
			return Jsoup.parse(htmlString).text();
		}
		return htmlString;
	}
	
	private static void removeEmptyValues(JsonObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (null == value || value.toString().isEmpty()) {
                jsonObject.remove(key);
            } 
        }
    }

	
	/**
	 * Gets the List for additional properties from the hp node properties.
	 *
	 * @param valueMap baseVarientValueMap
	 * @return List<JsonObject> additionaPropList
	 */
	private  List<JsonObject> getAdditionalProperties(ValueMap baseVarientValueMap) {
		List<JsonObject> additionaPropList = new ArrayList<>();
		buildHpNodeProperties(baseVarientValueMap, CommonConstants.ENTREZ_GENE_ID_KEY, additionaPropList);
		buildHpNodeProperties(baseVarientValueMap, CommonConstants.SPECIFICITY, additionaPropList);
		buildHpNodeProperties(baseVarientValueMap, CommonConstants.RRID_KEY, additionaPropList);
		buildSpeciesDescProperties(baseVarientValueMap, additionaPropList);
		buildCloneProperty(baseVarientValueMap, CommonConstants.HOST_SPECIES_KEY, additionaPropList);
		buildCloneProperty(baseVarientValueMap, CommonConstants.ISOTYPE_KEY, additionaPropList);
		buildCloneProperty(baseVarientValueMap, CommonConstants.TDS_CLONE_NAME, additionaPropList);
		buildCloneProperty(baseVarientValueMap, CommonConstants.WORKSHOP_NUMBER, additionaPropList);
		getApplication(baseVarientValueMap, CommonConstants.APPLICATION, additionaPropList);
		buildAdditionalProperties(baseVarientValueMap, CommonConstants.DYE_NAME, additionaPropList);
		buildAdditionalProperties(baseVarientValueMap, CommonConstants.SIZE_QTY, additionaPropList);
		buildAdditionalProperties(baseVarientValueMap, CommonConstants.LASER_WAVELENGTH, additionaPropList);
		buildAdditionalProperties(baseVarientValueMap, CommonConstants.REGULATORY_STATUS_KEY, additionaPropList);
		buildAdditionalProperties(baseVarientValueMap, "alternateName", additionaPropList);
		buildVariant("isVariantOf", variants, additionaPropList);
		
		return additionaPropList;
	}
	
	private JsonObject buildVariant(String property, ArrayList<String> variants, List<JsonObject> finalArray) {
		JsonObject additionalPropertiesJson = new JsonObject();
		for (String product : variants) {
			JsonObject varJson = new JsonObject();
			varJson.addProperty(CommonConstants.NAME, property);
			varJson.addProperty(CommonConstants.VALUE, product.trim());
			removeEmptyValues(varJson);
			finalArray.add(varJson);
		}
		return additionalPropertiesJson;
		
	}

	private JsonObject buildSpeciesDescProperties(ValueMap baseProductValueMap, List<JsonObject> finalArray) {
		JsonObject additionalPropertiesJson = new JsonObject();
		if (baseProductValueMap.containsKey(CommonConstants.SPECIES_REACTIVITY_KEY)) {
			String speciesReactivity = baseProductValueMap.get(CommonConstants.SPECIES_REACTIVITY_KEY).toString();
			JsonArray speciesArray = new JsonParser().parse(speciesReactivity).getAsJsonArray();
			for (JsonElement species : speciesArray) {
				JsonObject reactivityJson = new JsonObject();
				JsonObject item = species.getAsJsonObject();
				String speciesDesc =  item.get(CommonConstants.SPECIES_DESC).getAsString();
				if (!StringUtils.isEmpty(speciesDesc)) {
					reactivityJson.addProperty(CommonConstants.NAME, "Reactivity");
					reactivityJson.addProperty(CommonConstants.VALUE, speciesDesc);
					removeEmptyValues(reactivityJson);
					finalArray.add(reactivityJson);
				}
			}
		}
		return additionalPropertiesJson;
	}
	
	/**
	 * Gets the  additional HpNodeProperties and adds it to the additionalPropertiesJson.
	 *
	 * @param valueMap baseVarientValueMap
	 * @param String property
	 * @param List<JsonObject> finalArray
	 * @return JsonObject additionalPropertiesJson
	 */
	private JsonObject buildAdditionalProperties(ValueMap baseVarientValueMap, String property, List<JsonObject> finalArray) {
		JsonObject additionalPropertiesJson = new JsonObject();
		if (CommonConstants.DYE_NAME.equals(property)) {
			getMultipleValues("Conjugate/Format", getProductFormatData(baseVarientValueMap), additionalPropertiesJson, finalArray);
			return additionalPropertiesJson;
		} else if (CommonConstants.LASER_WAVELENGTH.equals(property)) {
			getMultipleValues("Laser Line", getLaserWavelength(baseVarientValueMap), additionalPropertiesJson, finalArray);
			return additionalPropertiesJson;
		} else if (CommonConstants.REGULATORY_STATUS_KEY.equals(property)) {
			getMultipleValues("Regulatory Status", SolrUtils.checkNull(regulatoryStatus), additionalPropertiesJson, finalArray);
			return additionalPropertiesJson;
		} else if (CommonConstants.SIZE_QTY.equals(property)) {
			additionalPropertiesJson.addProperty(CommonConstants.TYPE_ATTR, CommonConstants.PROPERTY_VALUE);
			additionalPropertiesJson.addProperty(CommonConstants.NAME, "quantity");
			additionalPropertiesJson.addProperty(CommonConstants.VALUE, sizeQty);
			removeEmptyValues(additionalPropertiesJson);
			finalArray.add(additionalPropertiesJson);
			return additionalPropertiesJson;
		} else if (property.equals("alternateName")) {
			getMultipleValues(property, alternativeName, additionalPropertiesJson, finalArray);
			return additionalPropertiesJson;
		}
		return additionalPropertiesJson;
		
	}
	
	
	private JsonObject getMultipleValues(String property, String propertyValue, JsonObject additionalPropertiesJson, List<JsonObject> finalArray) {
		if (propertyValue.contains(",") || propertyValue.contains(";")) {
			String[] values = propertyValue.split("[,;]");
			for (String value : values) {
				JsonObject obj = new JsonObject();
				obj.addProperty(CommonConstants.NAME, property);
				obj.addProperty(CommonConstants.VALUE, value.trim());
				removeEmptyValues(obj);
				finalArray.add(obj);
			}
		} else {
			JsonObject obj = new JsonObject();
			obj.addProperty(CommonConstants.NAME, property);
			obj.addProperty(CommonConstants.VALUE, propertyValue.trim());
			removeEmptyValues(obj);
			finalArray.add(obj);
		} 
		return additionalPropertiesJson;
	}
	
	private JsonObject getProductDataJson() {
		JsonObject productDataJson = new JsonObject();
		addField(productDataJson, "@context", "https://schema.org/");
		addField(productDataJson, "@type", "Product");
		addField(productDataJson, "name", productName);
		addField(productDataJson, "brand", productName);
		addField(productDataJson, "logo", productImage);
		addField(productDataJson, "category", productCategory);
		addArrayField(productDataJson, "image", finalImageList);
		addField(productDataJson, "description", productSchemaDescription);
		
		addJsonArrayField(productDataJson, "additionalProperty", pdpAdditionalProperties);
		
		addField(productDataJson, "depth", depth);
		addField(productDataJson,"hasCertification", regulatoryStatus);
		addField(productDataJson, "isRelatedTo", isRelatedTo);
		addField(productDataJson, "keywords", keywords);
		addField(productDataJson, "releaseDate", releaseDate);
		addField(productDataJson, "weight", weight);
		addField(productDataJson, "mpn", skuNumber);
		addField(productDataJson, "productID", skuNumber);
		addField(productDataJson, "sku", skuNumber);
		JsonObject offer = new JsonObject();
		addField(offer, "@type", "Offer");
		addField(offer, "priceCurrency", "USD");
		addField(offer, "price", "0.00");
		productDataJson.add("Offers", offer);
		
		JsonObject potentialAction = new JsonObject();
		addField(potentialAction, "@type", "BuyAction");
		productDataJson.add("potentialAction", potentialAction);
		
		addField(productDataJson, "url", pageUrl);

		return productDataJson;
		
	}
	
	
	private JsonObject addField(JsonObject object, String key, String value) {
		if (value != null && !value.isEmpty()) {
			object.addProperty(key, value);
		}
		return object;
	}
	
	private JsonObject addArrayField(JsonObject object, String key, List<String> values) {
		if (values != null && !values.isEmpty()) {
			JsonArray jsonArray = new JsonArray();
			for (String value : values) {
				jsonArray.add(value);
			}
			object.add(key, jsonArray);
		}
		return object;
	}
	
	private JsonObject addJsonArrayField(JsonObject object, String key, List<JsonObject> additionalproperties) {
		JsonArray jsonArray = new JsonArray();
		for (JsonObject obj : additionalproperties) {
			jsonArray.add(obj);
		}
		object.add(key, jsonArray);
		return object;
	}
	
	/**
	 * Gets the application properties and adds it to the additionalPropertiesJson.
	 *
	 * @param valueMap baseHpMap
	 * @param String property
	 * @param List<JsonObject> applicationList
	 * @return JsonObject additionalPropertiesJson
	 */
	private JsonObject getApplication(ValueMap baseProductValueMap, String property, List<JsonObject> applicationList) {
		JsonObject additionalPropertiesJson = new JsonObject();
		if (baseProductValueMap.containsKey(CommonConstants.PRODUCT_APPLICATION_TEST)) {
			String application = baseProductValueMap.get(CommonConstants.PRODUCT_APPLICATION_TEST).toString();
			JsonArray applicationArray = new JsonParser().parse(application).getAsJsonArray();
			for (JsonElement app : applicationArray) {
				JsonObject applicationJson = new JsonObject();
				JsonObject item = app.getAsJsonObject();
				String applicationDesc =  item.get(CommonConstants.APPLICATION_DESC).getAsString();
				if (!StringUtils.isEmpty(applicationDesc)) {
					applicationJson.addProperty(CommonConstants.NAME, property);
					applicationJson.addProperty(CommonConstants.VALUE, applicationDesc);
					removeEmptyValues(applicationJson);
					applicationList.add(applicationJson);
				}
			}
		}
		return additionalPropertiesJson;
	}
	
	/**
	 * Gets the HpNodeProperties and adds it to the additionalPropertiesJson.
	 *
	 * @param valueMap baseHpMap
	 * @param String property
	 * @param List<JsonObject> nodeList
	 * @return JsonObject additionalPropertiesJson
	 */
	private JsonObject buildHpNodeProperties(ValueMap baseProductValueMap, String property, List<JsonObject> nodeList) {
		JsonObject additionalPropertiesJson = new JsonObject();
		if (baseProductValueMap.containsKey(property) 
				&& StringUtils.isNotEmpty(baseProductValueMap.get(property, String.class))) {
			String value = baseProductValueMap.get(property, String.class);
			getMultipleValues(property, value, additionalPropertiesJson, nodeList);
		}
		return additionalPropertiesJson;
	}
	
	/**
	 * Gets the cloneProperty and adds it to the additionalPropertiesJson.
	 *
	 * @param valueMap baseHpMap
	 * @param String property
	 * @param List<JsonObject> cloneList
	 * @return JsonObject additionalPropertiesJson
	 */
	private JsonObject buildCloneProperty(ValueMap baseProductValueMap, String property,  List<JsonObject> cloneList) {
		JsonObject additionalPropertiesJson = new JsonObject();
		if (baseProductValueMap.containsKey(CommonConstants.CLONE)) {
			String clone = baseProductValueMap.get(CommonConstants.CLONE).toString();
			JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();

			for (JsonElement cloneElement : cloneArray) {
				JsonObject cloneJsontObject = cloneElement.getAsJsonObject();
				if (null != cloneJsontObject.get(property) && StringUtils.isNotEmpty(cloneJsontObject.get(property).getAsString())) {
					String value = SolrUtils.getJsonProperty(cloneJsontObject, property);
					getMultipleValues(property, value, cloneJsontObject, cloneList);
				}
			}
		}
		return additionalPropertiesJson;
	}
	
	/**
	 * Gets the getLaserWavelength.
	 *
	 * @param valueMap baseHpMap
	 * @return String getLazerWavelength
	 */
	private String getLaserWavelength(ValueMap baseHpMap) {
		String getLazerWavelength = StringUtils.EMPTY;
		if(baseHpMap.containsKey(CommonConstants.BD_FORMAT)) {
			String format = baseHpMap.get(CommonConstants.BD_FORMAT).toString();
			JsonArray formatArray = new JsonParser().parse(format).getAsJsonArray();
			for (JsonElement formatDetailsElement : formatArray) {
				JsonObject formatDetailsJson = formatDetailsElement.getAsJsonObject();
				if (formatDetailsJson.has(CommonConstants.LASER_WAVELENGTH) && null != formatDetailsJson.get(CommonConstants.LASER_WAVELENGTH)
						&& !formatDetailsJson.get(CommonConstants.LASER_WAVELENGTH).getAsString().isEmpty()) {
					getLazerWavelength = formatDetailsJson.get(CommonConstants.LASER_WAVELENGTH).getAsString();
				}
			}
		}
		return getLazerWavelength;
	}
	
	/**
	 * Gets the getProductFormatData.
	 *
	 * @param valueMap baseHpMap
	 * @return String formatNameData
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
				if (formatDetailsJson.has(CommonConstants.FORMAT_STATEMENT)) {
					bdFormatdescription = formatDetailsJson.get(CommonConstants.FORMAT_STATEMENT).getAsString();
				}
			}
		}
		return formatNameData;
	}
	
	/**
	 * Gets the variants.
	 *
	 * @param valueMap baseHpMap
	 * @return ArrayList materialNumbers
	 */
	private ArrayList<String> getVariants(ValueMap baseHpMap) {
		ArrayList<String> materialNumbers = new ArrayList<>();
		if(baseHpMap.containsKey(CommonConstants.VARIANTS)) {
			String variant = baseHpMap.get(CommonConstants.VARIANTS).toString();
			JsonArray variantArray = new JsonParser().parse(variant).getAsJsonArray();
			for (JsonElement variantElement : variantArray) {
				JsonObject variantDetailsJson = variantElement.getAsJsonObject();
				if (variantDetailsJson.has(CommonConstants.MATERIAL_NUMBER) && null != variantDetailsJson.get(CommonConstants.MATERIAL_NUMBER)
						&& !variantDetailsJson.get(CommonConstants.MATERIAL_NUMBER).getAsString().isEmpty()) {
					materialNumbers.add(variantDetailsJson.get(CommonConstants.MATERIAL_NUMBER).getAsString());
				}
			}
		}
		return materialNumbers;
	}
	
	/**
	 * Gets the releaseDate for JP and EMEA regions.
	 *
	 * @param Resource varRes
	 * @param String country
	 * 
	 */
	private String getReleaseDateJpAndEmea(Resource varRes, String country) {
		try {
			if (null != varRes) {
				String path = varRes.getPath() + CommonConstants.SLASH + CommonConstants.SAP_CC_NODENAME + CommonConstants.SLASH + CommonConstants.REGION_DETAILS_NODE_NAME;
				Resource regionDetails = resourceResolver.getResource(path);
				if (null != regionDetails) {
					ValueMap regionsMap = regionDetails.getValueMap();
					if (country.equalsIgnoreCase("JP") && regionsMap.containsKey("JP")) {
						String region = regionsMap.get("JP").toString();
						JsonObject regionJson = new JsonParser().parse(region).getAsJsonObject();
						if (regionJson.has(CommonConstants.DERIVED_PRODUCT_STATUS) && regionJson.has(CommonConstants.VALIDITY_START_DATE)) {
							String derivedProductStatus = regionJson.get(CommonConstants.DERIVED_PRODUCT_STATUS).getAsString();
							if (derivedProductStatus.equals("DISPLAYONLY") || derivedProductStatus.equals("PURCHASEABLE")) {
								String getValidityDate = regionJson.get(CommonConstants.VALIDITY_START_DATE).getAsString();
								return solrSearchService.getReleaseDate(getValidityDate);
							}
						}
					} else if (CommonConstants.EMEA_COUNTRIES_LIST.contains(country.toLowerCase()) && regionsMap.containsKey(country.toUpperCase())) {
						String region = regionsMap.get(country.toUpperCase()).toString();
						JsonObject regionJson = new JsonParser().parse(region).getAsJsonObject();
						if (regionJson.has(CommonConstants.DERIVED_PRODUCT_STATUS) && regionJson.has(CommonConstants.VALIDITY_START_DATE)) {
							String derivedProductStatus = regionJson.get(CommonConstants.DERIVED_PRODUCT_STATUS).getAsString();
							if (derivedProductStatus.equals("DISPLAYONLY") || derivedProductStatus.equals("PURCHASEABLE")) {
								String getValidityDate = regionJson.get(CommonConstants.VALIDITY_START_DATE).getAsString();
								return solrSearchService.getReleaseDate(getValidityDate);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in fetching the releaseDate for JP and EMEA region : {}", e.getMessage());
		}
		return StringUtils.EMPTY;
	}
	
	/**
	 * Gets the tds clone.
	 *
	 * @param tagValueMap the tag value map
	 * @return the tds clone
	 */
	public String getTdsClone(ValueMap tagValueMap) {
		String tdsCloneName = StringUtils.EMPTY;
		if (null != tagValueMap && null != tagValueMap.get(CommonConstants.CLONE_LABEL, String.class)) {
			String clone = tagValueMap.get(CommonConstants.CLONE_LABEL, String.class);
			if(null != clone) {
				JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();
				for (JsonElement jsonElement : cloneArray) {
					JsonObject cloneObj = jsonElement.getAsJsonObject();
					if(null != cloneObj) {
						if (null != cloneObj.get(CommonConstants.TDS_CLONE_DISPLAY_NAME)) {
							String tdsCloneDisplayName = cloneObj.get(CommonConstants.TDS_CLONE_DISPLAY_NAME).getAsString();
							tdsCloneName = StringUtils.isNotEmpty(tdsCloneDisplayName) ? tdsCloneDisplayName : StringUtils.EMPTY;
						}
						if (null != cloneObj.get(CommonConstants.TDS_CLONE_NAME) && StringUtils.isEmpty(tdsCloneName) ) {
							tdsCloneName = cloneObj.get(CommonConstants.TDS_CLONE_NAME).getAsString();
						}
					}
				}
			}
		}
		return tdsCloneName;
	}

	/**
	 * Gets the technical tools.
	 *
	 * @param resourceResolver   the resource resolver
	 * @param toolsComponentPath the tools component path
	 * @param country the country
	 * @param hpBaseResource the hp base resource
	 * @return the technical tools
	 */
	private void getTechnicalTools(ResourceResolver resourceResolver, String toolsComponentPath, String country, Resource hpBaseResource) {
		if (null != resourceResolver && null != resourceResolver.getResource(toolsComponentPath)) {

			Resource toolsResource = resourceResolver.getResource(toolsComponentPath);
			if(null != toolsResource) {
				ValueMap toolsMap = toolsResource.getValueMap();
				icon1 = getValueMapProperty(toolsMap, "icon1");
				icon2 = getValueMapProperty(toolsMap, "icon2");
				icon3 = getValueMapProperty(toolsMap, "icon3");
				String formattedRegionalLink = null;
				if( null != getValueMapProperty(toolsMap, "link1") ) {
					formattedRegionalLink = getRegionalPageLink(getValueMapProperty(toolsMap, "link1")); 
				}
				link1 = externalizerService.getFormattedUrl(formattedRegionalLink, resourceResolver);

				String formattedRegionalLink2 = null;
				if( null != getValueMapProperty(toolsMap, "link2") ) {
					formattedRegionalLink2 = getRegionalPageLink(getValueMapProperty(toolsMap, "link2")); 
				}
				link2 = externalizerService.getFormattedUrl(formattedRegionalLink2, resourceResolver);

				String formattedRegionalLink3 = null;
				if( null != getValueMapProperty(toolsMap, "link3") ) {
					formattedRegionalLink3 = getRegionalPageLink(getValueMapProperty(toolsMap, "link3"));
				}
				link3 = externalizerService.getFormattedUrl(formattedRegionalLink3, resourceResolver);

				text1 = getValueMapProperty(toolsMap, "text1");
				text2 = getValueMapProperty(toolsMap, "text2");
				text3 = getValueMapProperty(toolsMap, "text3");
				dyeNameExcelPath = getValueMapProperty(toolsMap, "dyeNameExcelPath");
			}

			
			displaySpectrumViewerTool = getDisplaySpectrumViewer(country,hpBaseResource);

		}
	}
	
	/**
	 * Gets the region specific page url.
	 *
	 * @param languagePageUrl the languagePageUrl
	 * @return the regional specific page url
	 */
	public String getRegionalPageLink(String languagePageUrl) {
		String urlFormat = CommonHelper.getRegion(currentPage) + "/" + CommonHelper.getCountry(currentPage) + "/"
				+ CommonHelper.getLanguage(currentPage) + "-" + CommonHelper.getCountry(currentPage);
		logger.debug("urlFormate {} ", urlFormat);
		if (StringUtils.contains(languagePageUrl, "language-masters/en"))
			languagePageUrl = languagePageUrl.replace("language-masters/en", urlFormat);
		return languagePageUrl;
	}

	/**
	 * Gets the display spectrum viewer.
	 *
	 * @param country the country
	 * @param hpBaseResource the hp base resource
	 * @return the display spectrum viewer
	 */
	public Boolean getDisplaySpectrumViewer(String country, Resource hpBaseResource) {
		String catalogNo = CommonHelper.getSelectorDetails(request);
		if (null != catalogNo) {
			if (null != hpBaseResource) {
				ValueMap hpProperty = hpBaseResource.adaptTo(ValueMap.class);
				return checkHPPropertyWithExcelData(hpProperty);
			}
		}
		return false;
	}

	/**
	 * Check HP property with excel data.
	 *
	 * @param hpProperty the hp property
	 * @return the boolean
	 */
	public Boolean checkHPPropertyWithExcelData(ValueMap hpProperty) {
		List<String> dyeNameExcelList = getDyeNameExcelList();
		String dyeName = StringUtils.EMPTY;
		if (hpProperty.containsKey(CommonConstants.BD_FORMAT) && !dyeNameExcelList.isEmpty()) {
			JsonArray formatDetailsJsonArray = new JsonParser()
					.parse(hpProperty.get(CommonConstants.BD_FORMAT).toString()).getAsJsonArray();
			for (JsonElement formatDetailsElement : formatDetailsJsonArray)
				dyeName = getFormatDetailJson(formatDetailsElement);
			if (null != dyeName && dyeNameExcelList.contains(dyeName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the format detail json.
	 *
	 * @param formatDetailsElement the format details element
	 * @return the format detail json
	 */
	public String getFormatDetailJson(JsonElement formatDetailsElement) {
		String formatname = StringUtils.EMPTY;
		JsonObject formatDetailsJson = formatDetailsElement.getAsJsonObject();
		if (formatDetailsJson.has(CommonConstants.DYE_NAME) && null != formatDetailsJson.get(CommonConstants.DYE_NAME)
				&& !formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString().isEmpty()) {
			formatname = formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString();
		}
		return formatname;
	}

	/**
	 * Gets the dye name excel list.
	 *
	 * @return the dye name excel list
	 */
	public List<String> getDyeNameExcelList() {
		List<String> dyeNameExcelList = new ArrayList<>();
		try {
			if (null != dyeNameExcelPath) {
				Resource dyeNameExceResource = resourceResolver.getResource(dyeNameExcelPath);
				if (null != dyeNameExceResource) {
					Asset asset = dyeNameExceResource.adaptTo(Asset.class);
					if (null != asset && (asset.getMimeType().equals(CommonConstants.XLSX_MIME_TYPE)
							|| asset.getMimeType().equals(CommonConstants.XLS_MIME_TYPE))) {
						dyeNameExcelList = convertXLSXToList(asset.getOriginal().getStream());
					}
				}
			}
		} catch (IOException e) {

			logger.error("Error in getting Dyne Name Excel List {0}", e);
		}

		return dyeNameExcelList;
	}

	/**
	 * Convert XLSX to list.
	 *
	 * @param inputStream the input stream
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<String> convertXLSXToList(InputStream inputStream) throws IOException {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
		Iterator<Row> itr = sheet.iterator();
		List<String> dyeNameExcelList = new ArrayList<>();
		while (itr.hasNext()) {
			Row row = itr.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				dyeNameExcelList.add(cell.getStringCellValue());
			}
		}
		return dyeNameExcelList;
	}

	/**
	 * Gets the value map property.
	 *
	 * @param toolsMap the tools map
	 * @param value    the value
	 * @return the value map property
	 */
	private String getValueMapProperty(ValueMap toolsMap, String value) {
		if (null != toolsMap.get(value)) {
			value = toolsMap.get(value, String.class);
		}
		return value;
	}

	/**
	 * Gets the asset list bean.
	 *
	 * @return the asset list bean
	 * @throws JsonProcessingException the json processing exception
	 */
	private void getAssetListBean() throws JsonProcessingException {
		beanList = new ArrayList<>();
		JsonObject assetJson;
		logger.debug("Reading request Session Image Json");
		if (null != request.getAttribute("assetPaths")) {
			assetJson = (JsonObject) request.getAttribute("assetPaths");
			getImageArray(assetJson, "HERO");
			getImageArray(assetJson, "GALLERY");
			getImageArray(assetJson, "RUO_VIAL");
			getImageArray(assetJson, "VIAL_ARRAY");
			getImageArray(assetJson, "OTHER_VIAL");
		}
	}

	/**
	 * Gets the image array.
	 *
	 * @param assetJson the asset json
	 * @param objKey the obj key
	 * @return the image array
	 * @throws JsonProcessingException the json processing exception
	 */
	public void getImageArray(JsonObject assetJson, String objKey)
			throws JsonProcessingException {
		JsonArray galleryArray = null;		
		imagesList = new ArrayList<PDPImagesBean>();
		Integer num;
		JsonArray sortedGalleryArray = new JsonArray();
		if (null != assetJson && assetJson.has(objKey)) {
			galleryArray = assetJson.getAsJsonArray(objKey);
			if (null != galleryArray) {
				/** Sorting all the gallery images according to their rank.
				 * If rank is not assigned to the image, it will take a default rank of 999999
				 */
				if(StringUtils.equals(objKey , "GALLERY")) {
					for (JsonElement asset : galleryArray) {
						PDPImagesBean imagesBean = new PDPImagesBean();
						JsonObject assetObj = asset.getAsJsonObject();
						String image = CommonHelper.getJsonProperty(assetObj, "imagePath");
						Resource resource = request.getResourceResolver().getResource(image);
			            Asset imgasset = resource.adaptTo(Asset.class);
			            String rank = imgasset.getMetadataValueFromJcr("dc:displayRank");
			            if(StringUtils.isBlank(rank))
			            	rank = "999999";
			            num = Integer.valueOf(rank);
			            imagesBean.setRank(num);
			            imagesBean.setImageAsset(assetObj);
						imagesList.add(imagesBean);
					}
					Collections.sort(imagesList, (o1, o2) -> o1.getRank() - o2.getRank());
					for(int i=0; i<imagesList.size();i++) {
						PDPImagesBean l = imagesList.get(i);
						sortedGalleryArray.add(l.getImageAsset()); 
					}
					if (null != sortedGalleryArray) {
						getMediaList(sortedGalleryArray, assetJson, objKey);
					}
				}
				if(!StringUtils.equals(objKey, "GALLERY")) {
					getMediaList(galleryArray, assetJson, objKey);
				}
			}
		}
	}

	/**
	 * Gets the media list.
	 *
	 * @param galleryArray the gallery array
	 * @param assetJson the asset json
	 * @return the media list
	 * @throws JsonProcessingException the json processing exception
	 */
	public void getMediaList(JsonArray galleryArray, JsonObject assetJson, String objKey) throws JsonProcessingException {	
		for (JsonElement asset : galleryArray) {
			JsonObject assetObj = asset.getAsJsonObject();
			String image = CommonHelper.getJsonProperty(assetObj, "imagePath");
			if(null != image) {
				imageList.add(externalizerService.getFormattedUrl(image, resourceResolver));
			}
			String desc = CommonHelper.getJsonProperty(assetObj, "caption");
			if(StringUtils.equals(objKey , "HERO")) {
				productImageDesc = desc;				
			}
			String imageAltText = CommonHelper.getJsonProperty(assetObj, "imageAltText");
			String modelDescription = CommonHelper.getJsonProperty(assetObj, "imageTitle");
			assetObj.addProperty("image", image);
			assetObj.addProperty("desc", desc);
			assetObj.addProperty("modelDescription", modelDescription);
			if (null != imageAltText || !StringUtil.isEmpty(imageAltText)) {
				assetObj.addProperty("imgAtlText", imageAltText);
			} else {
				assetObj.addProperty("imgAtlText", modelDescription);
			}
			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);

			ProductHeroBean productHeroBeanObj = objectMapper.readValue(assetObj.toString(), ProductHeroBean.class);
			beanList.add(productHeroBeanObj);		
		}
		if (null != assetJson && assetJson.has(CommonConstants.VIDEO_ID)) {
			JsonObject videoObj = new JsonObject();
			videoObj.addProperty("videoId", assetJson.get(CommonConstants.VIDEO_ID).getAsString());
			videoObj.addProperty("image",
					"/etc.clientlibs/bdb-aem/clientlibs/clientlib-base/resources/images/Group11-thumbnail.png");
			videoObj.addProperty("previewImage",
					"/etc.clientlibs/bdb-aem/clientlibs/clientlib-base/resources/images/Group11-thumbnail.png");
			videoObj.addProperty("brightcovePlayerId", bdbApiEndpointService.brightcovePlayerId());
			videoObj.addProperty("brightcoveAccountId", bdbApiEndpointService.brightcoveAccountId());
			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			ProductHeroBean productHeroBeanVideoObj = objectMapper.readValue(videoObj.toString(),
					ProductHeroBean.class);
			beanList.add(productHeroBeanVideoObj);
		}
	}

	/**
	 * Gets the product title.
	 *
	 * @return the product title
	 */
	public String getProductTitle() {
		return productTitle;
	}

	/**
	 * Gets the product clone.
	 *
	 * @return the product clone
	 */
	public String getProductClone() {
		return productClone;
	}
		
	/**
	 * Gets the product category.
	 *
	 * @return the product category
	 */
	public String getProductCategory() {
		return productCategory;
	}

	/**
	 * Gets the regulatory status.
	 *
	 * @return the regulatory status
	 */
	public String getRegulatoryStatus() {
		return regulatoryStatus;
	}

	/**
	 * Gets the clone label.
	 *
	 * @return the clone label
	 */
	public String getCloneLabel() {
		return cloneLabel;
	}

	/**
	 * Gets the show less cta.
	 *
	 * @return the show less cta
	 */
	public String getShowLessCta() {
		return showLessCta;
	}

	/**
	 * Gets the show more cta.
	 *
	 * @return the show more cta
	 */
	public String getShowMoreCta() {
		return showMoreCta;
	}

	/**
	 * Gets the formats label.
	 *
	 * @return the formats label
	 */
	public String getFormatsLabel() {
		return formatsLabel;
	}

	/**
	 * Gets the icon 1.
	 *
	 * @return the icon 1
	 */
	public String getIcon1() {
		return icon1;
	}

	/**
	 * Gets the icon 2.
	 *
	 * @return the icon 2
	 */
	public String getIcon2() {
		return icon2;
	}

	/**
	 * Gets the icon 3.
	 *
	 * @return the icon 3
	 */
	public String getIcon3() {
		return icon3;
	}

	/**
	 * Gets the link 1.
	 *
	 * @return the link 1
	 */
	public String getLink1() {
		return link1;
	}

	/**
	 * Gets the link 2.
	 *
	 * @return the link 2
	 */
	public String getLink2() {
		return link2;
	}

	/**
	 * Gets the link 3.
	 *
	 * @return the link 3
	 */
	public String getLink3() {
		return link3;
	}

	/**
	 * Gets the text 1.
	 *
	 * @return the text 1
	 */
	public String getText1() {
		return text1;
	}

	/**
	 * Gets the text 2.
	 *
	 * @return the text 2
	 */
	public String getText2() {
		return text2;
	}

	/**
	 * Gets the text 3.
	 *
	 * @return the text 3
	 */
	public String getText3() {
		return text3;
	}

	/**
	 * Gets the bean list.
	 *
	 * @return the bean list
	 */
	public List<ProductHeroBean> getBeanList() {
		if (null != beanList) {
			return new ArrayList<>(beanList);
		} else {
			return Collections.emptyList();
		}

	}

	/**
	 * Gets the lines count.
	 *
	 * @return the lines count
	 */
	public String getLinesCount() {
		return linesCount;
	}
	
	public List<JsonObject> getPdpAdditionalProperties() {
		return pdpAdditionalProperties;
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
	
	public String getSkuNumber() {
		return skuNumber;
	}

	public String getProductName() {
		return productName;
	}
	
	/**
	 * Gets the current page url.
	 *
	 * @return the current page url
	 */
	public String getPageUrl() {
		return pageUrl;
	}

	public String getProductImage() {
		return productImage;
	}
	
	/**
	 * Gets the product HERO Image description.
	 *
	 * @return the product HERO Image description
	 */
	public String getProductImageDesc() {
		return productImageDesc;
	}

	/**
	 * @return the finalImageList
	 */
	public List<String> getFinalImageList() {
		if(flag == true) {
			for(String image : imageList) {
				image = "\""+image+"\"";
				finalImageList.add(image);
			}
			flag = false;
		}
		
		return finalImageList != null ? finalImageList : null;
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
	 * Gets the alternativeName.
	 *
	 * @return alternativeName
	 */
	public String getAlternativeName() {
		return alternativeName;
	}
	
	/**
	 * Gets sizeQty.
	 *
	 * @return sizeQty
	 */
	public String getSizeQty() {
		return sizeQty;
	}
	
	/**
	 * Gets weight.
	 *
	 * @return weight
	 */
	public String getWeight() {
		return weight;
	}
	
	/**
	 * Gets keywords.
	 *
	 * @return keywords
	 */
	public String getKeywords() {
		return keywords;
	}
	
	/**
	 * Gets isRelatedTo.
	 *
	 * @return isRelatedTo
	 */
	public String getIsRelatedTo() {
		return isRelatedTo;
	}
	
	/**
	 * Gets releaseDate.
	 *
	 * @return releaseDate
	 */
	public String getReleaseDate() {
		return releaseDate;
	}
	
	/**
	 * Gets variants.
	 *
	 * @return variants
	 */
	public ArrayList<String> getVariants() {
		return variants;
	}
	
	/**
	 * Gets depth.
	 *
	 * @return depth
	 */
	public String getDepth() {
		return depth;
	}
	
	/**
	 * Gets productData.
	 *
	 * @return productData
	 */
	public JsonObject getProductData() {
		return productData;
	}
	
	/**
	 * Gets tdsDescription.
	 *
	 * @return tdsDescription
	 */
	public String getTdsDescription() {
		return tdsDescription;
	}

	/**
	 * Gets productSchemaDescription.
	 *
	 * @return productSchemaDescription
	 */
	public String getProductSchemaDescription() {
		return productSchemaDescription;
	}
	
}
