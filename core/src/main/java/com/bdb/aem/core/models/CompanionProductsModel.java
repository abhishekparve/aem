package com.bdb.aem.core.models;

import com.bdb.aem.core.bean.CompanionProductsBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.RepositoryException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The Class CompanionProductsModel.
 */
@Model(adaptables = {SlingHttpServletRequest.class,
        Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CompanionProductsModel {

    /** The Constant COMPANION_MATERIAL_NUMBER. */
    protected static final String COMPANION_MATERIAL_NUMBER = "companionMaterialNumber";
    
    /** The Constant COMPANION_TYPE. */
    protected static final String COMPANION_TYPE = "companionType";
    
    /** The Constant BASE_MATERIAL_NUMBER. */
    protected static final String BASE_MATERIAL_NUMBER = "baseMaterialNumber";

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(CompanionProductsModel.class);

    /** The request. */
    @SlingObject
    private SlingHttpServletRequest request;
    
    /** The solr search service. */
    @Inject
    SolrSearchService solrSearchService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /** The companion bean list. */
    private List<CompanionProductsBean> companionBeanList;
    
    /** The view more CTA link. */
    private String viewMoreCTALink;
    
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The size label. */
    @Inject
    @Named("sizeLabel")
    @Via("resource")
    private String sizeLabel;

    /** The view details label. */
    @Inject
    @Named("viewDetailsLabel")
    @Via("resource")
    private String viewDetailsLabel;

   /** The cat no label. */
    @Inject
    @Named("catNoLabel")
    @Via("resource")
    private String catNoLabel;


    /** The products label. */
    @Inject
    @Named("productsLabel")
    @Via("resource")
    private String productsLabel;

    /** The number of products. */
    private int numberOfProducts;
    
    /** The current page. */
    @Inject
    Page currentPage;
    
    /** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The catalog structure update service. */
	@Inject
	CatalogStructureUpdateService catalogStructureUpdateService;
	
	/** The bdb search endpoint service. */
	@Inject
	BDBSearchEndpointService solrConfig;

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        logger.debug("Inside CompanionProductsModel Init");

        try {
            String productVarHPPath;
            Resource baseHpResource = null;
            if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)) {
                productVarHPPath = request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString();
                baseHpResource = resourceResolver.getResource(productVarHPPath);
            }
            if (null != baseHpResource) {
                ValueMap varientBaseHpValueMap = baseHpResource.getValueMap();
                if (null != varientBaseHpValueMap.get(CommonConstants.COMPANION_PRODUCTS)) {
                    JsonArray jsonObject = CommonHelper.getJsonObject(varientBaseHpValueMap, CommonConstants.COMPANION_PRODUCTS);
                    getCompanionList(jsonObject);
                }
            }

        } catch (JsonProcessingException e) {
           logger.error("Exception in Companion Products Model {}", e.getMessage());
        }

    }

    /**
     * Gets the companion list.
     *
     * @param jsonObject the json object
     * @return the companion list
     * @throws JsonProcessingException the json processing exception
     */
    public void getCompanionList(JsonArray jsonObject) throws JsonProcessingException {
        logger.debug("Inside Companion List Products method");
        companionBeanList = new ArrayList<>();
        viewMoreCTALink = StringUtils.EMPTY;
        if(null != CommonHelper.getLabel("searchResultPagePath", currentPage)) {
        	 String searchPageUrl = !CommonHelper.getLabel("searchResultPagePath", currentPage).isEmpty() ? CommonHelper.getLabel("searchResultPagePath", currentPage) : "/";
             viewMoreCTALink = searchPageUrl.equals("/") ? "/" : externalizerService.getFormattedUrl(searchPageUrl, resourceResolver);
        }
       
        String searchProductId = StringUtils.EMPTY;
        if (null != jsonObject) {
            JsonArray productsArray = jsonObject.getAsJsonArray();
            logger.debug("Companion Products size {} ", productsArray);
            searchProductId = iterateCompanionJsonObject(searchProductId, productsArray);
            if (!searchProductId.isEmpty()) {
            	String rummode = CommonHelper.getRunMode(bdbApiEndpointService);
            	if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.AUTHOR)) {			
            		viewMoreCTALink = StringUtils.replace(viewMoreCTALink, CommonConstants.DOT_HTML, "."+CommonConstants.COMPANION_DETAILS_SELECTOR +CommonConstants.DOT_HTML+CommonConstants.INTERROGATION+CommonConstants.SEARCH_KEY.concat(StringUtils.substring(searchProductId, 0, searchProductId.length() - 1)));
            	}else if(StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.PUBLISH)) {
					viewMoreCTALink = viewMoreCTALink.concat(CommonConstants.SINGLE_DOT+CommonConstants.COMPANION_DETAILS_SELECTOR   +CommonConstants.INTERROGATION+CommonConstants.SEARCH_KEY.concat(StringUtils.substring(searchProductId, 0, searchProductId.length() - 1)));
				}		
            }
            numberOfProducts = companionBeanList.size();
        }
    }

	/**
	 * Iterate companion json object.
	 *
	 * @param searchProductId the search product id
	 * @param productsArray the products array
	 * @return the string
	 * @throws JsonProcessingException the json processing exception
	 * @throws JsonMappingException the json mapping exception
	 */
	public String iterateCompanionJsonObject(String searchProductId, JsonArray productsArray)
			throws JsonProcessingException, JsonMappingException {
        String catalogNumber = request.getAttribute(CommonConstants.CATALOG_NUMBER_KEY) != null ? request.getAttribute(CommonConstants.CATALOG_NUMBER_KEY).toString():null;
		for (JsonElement product : productsArray) {
		    JsonObject productObj = product.getAsJsonObject();
		    String companionType = CommonHelper.getJsonProperty(productObj, COMPANION_TYPE);
		    if(StringUtils.isNotEmpty(companionType) && !"Required".equalsIgnoreCase(companionType)) {
		        String companionMaterialNumber = CommonHelper.getJsonProperty(productObj, COMPANION_MATERIAL_NUMBER);
		        String baseMaterialNumber = CommonHelper.getJsonProperty(productObj, BASE_MATERIAL_NUMBER);
		        JsonObject productDetails = getVarientHPDetails(companionMaterialNumber, baseMaterialNumber, catalogNumber);
		        if(!companionMaterialNumber.equals(catalogNumber)) {
		    	   searchProductId = searchProductId.concat(companionMaterialNumber).concat("_");
		        }
		        if (!productDetails.entrySet().isEmpty()) {
		            ObjectMapper objectMapper = new ObjectMapper()
		                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		            CompanionProductsBean companionProductsBean = objectMapper
		                    .readValue(productDetails.toString(), CompanionProductsBean.class);
		            companionBeanList.add(companionProductsBean);

		        }
		    }
		}
		return searchProductId;
	}

    /**
     * Gets the varient HP details.
     *
     * @param skuId the sku id
     * @param baseMaterialNumber the base material number
     * @return the varient HP details
     */
	public JsonObject getVarientHPDetails(String skuId, String baseMaterialNumber, String catalogNumber) {
		
		String size = StringUtils.EMPTY;
		JsonObject companionProdDataJson = new JsonObject();
		Resource baseRes = null;
		try {
			if (null != skuId && null != baseMaterialNumber && !skuId.equals(catalogNumber)) {
				Resource variantRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, skuId,
						CommonConstants.MATERIAL_NUMBER);
				if(null != variantRes) {
					baseRes = variantRes.getParent();
				}
				getVariantProperties(skuId, size, companionProdDataJson, variantRes, baseRes);
			}
		} catch (RepositoryException e) {
			logger.error("RepositoryException in Companion Products ", e);
		}
		return companionProdDataJson;
	}

	/**
	 * Gets the variant properties.
	 *
	 * @param skuId the sku id
	 * @param size the size
	 * @param companionProdDataJson the companion prod data json
	 * @param variantRes the variant res
	 * @param baseRes the base res
	 * @return the variant properties
	 */
	public void getVariantProperties(String skuId, String size, JsonObject companionProdDataJson, Resource variantRes, Resource baseRes) {
		String labelDescription = StringUtils.EMPTY;
		if (null != baseRes && null != variantRes && null != variantRes.getChild(CommonConstants.HP)) {
			
			getLabelDescFromBaseHp(companionProdDataJson, baseRes, labelDescription,skuId);
			
			Resource variantHpResource = variantRes.getChild(CommonConstants.HP);
			ValueMap variantValueMap = variantHpResource.adaptTo(ValueMap.class);
			if (null != variantValueMap.get(CommonConstants.SIZE_QTY, String.class)) {
				size = variantValueMap.get(CommonConstants.SIZE_QTY, String.class) + CommonConstants.SPACE
						+ variantValueMap.get(CommonConstants.SIZE_UOM, String.class);
			}
			String imagePath = CommonHelper.getGlobalThumbnailImage(resourceResolver, variantHpResource,
					externalizerService, bdbApiEndpointService);
			if (logger.isInfoEnabled() && !imagePath.isEmpty() && !skuId.isEmpty())
				logger.debug(String.format("Product Image  for %s is %s", skuId, imagePath));
			companionProdDataJson.addProperty("image", imagePath+"?imwidth=320");
			companionProdDataJson.addProperty("imgAtlText", "sampleImage");
			String productURl = CommonHelper.getPdpProductUrl(variantHpResource);

			String externalizedUrl = StringUtils.EMPTY;
			externalizedUrl = getProductURL(productURl, externalizedUrl);
			if (logger.isInfoEnabled() && !productURl.isEmpty() && !skuId.isEmpty())
				logger.debug(String.format("Product URL  for %s is %s ", skuId, productURl));

			companionProdDataJson.addProperty(CommonConstants.CTA_LINK, externalizedUrl);
			companionProdDataJson.addProperty(CommonConstants.CTA_TARGET, "_new");
			companionProdDataJson.addProperty(CommonConstants.SIZE_LABEL, size);
			companionProdDataJson.addProperty(CommonConstants.CAT_NO_LABEL, skuId);
		}
	}

	/**
	 * Gets the label desc from base hp.
	 *
	 * @param companionProdDataJson the companion prod data json
	 * @param baseRes the base res
	 * @param labelDescription the label description
	 * @return the label desc from base hp
	 */
	public void getLabelDescFromBaseHp(JsonObject companionProdDataJson, Resource baseRes, String labelDescription, String skuId) {
		if (null != baseRes) {
			String country = CommonHelper.getCountry(currentPage);
			Resource baseHpResource = baseRes.getChild(CommonConstants.HP);
			ValueMap companionProductBaseValueMap = baseHpResource.adaptTo(ValueMap.class);
			String regulatoryStatus = CommonHelper.getRuoFromRegionDetails(baseHpResource, country, skuId);

			if(StringUtils.isNotBlank(regulatoryStatus) && country.equalsIgnoreCase("jp") && regulatoryStatus.equals("IVD")) {

				regulatoryStatus = CommonHelper.getTranslatedRegulatoryStatus("JP_IVD", resourceResolver, solrConfig);
			}
			if (null != companionProductBaseValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)) {
				labelDescription = companionProductBaseValueMap.get(CommonConstants.LABEL_DESCRIPTION,
						String.class);
			}

			if (StringUtils.isNotBlank(regulatoryStatus)) {
				labelDescription = labelDescription + CommonConstants.SPACE
						+ regulatoryStatus;
			}
			companionProdDataJson.addProperty(CommonConstants.PRODUCT_TITLE_LABEL, labelDescription);
		}
	}

	/**
	 * Gets the product URL.
	 *
	 * @param productURl the product U rl
	 * @param externalizedUrl the externalized url
	 * @return the product URL
	 */
	public String getProductURL(String productURl, String externalizedUrl) {
		if (StringUtils.isNotBlank(productURl)) {
			String rummode = CommonHelper.getRunMode(bdbApiEndpointService);
			String regionValue = CommonHelper.getRegion(currentPage);
			String countryValue = CommonHelper.getCountry(currentPage);
			String languageCountryValue = CommonHelper.getLanguage(currentPage) + CommonConstants.HYPHEN
					+ countryValue;
			String url;
			if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.AUTHOR)) {
				url = CommonConstants.CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.BDB
						+ CommonConstants.SINGLE_SLASH + regionValue + CommonConstants.SINGLE_SLASH
						+ countryValue + CommonConstants.SINGLE_SLASH + languageCountryValue + productURl;
				externalizedUrl = externalizerService.getFormattedUrl(url, resourceResolver);
			} else if (StringUtils.isNotEmpty(rummode)
					&& rummode.equalsIgnoreCase(CommonConstants.PUBLISH)) {
				url = CommonConstants.SINGLE_SLASH + languageCountryValue + productURl.replace("pdp.", "");
				externalizedUrl = externalizerService.externalizedUrl(url, resourceResolver);
			}
		}
		return externalizedUrl;
	}



    /**
     * Gets the companion bean list.
     *
     * @return the companion bean list
     */
    public List<CompanionProductsBean> getCompanionBeanList() {
        if (null != companionBeanList) {
            return new ArrayList<>(companionBeanList);
        } else {
            return Collections.emptyList();
        }

    }

    /**
     * Gets the cat no label.
     *
     * @return the cat no label
     */
    public String getCatNoLabel() {
        return catNoLabel;
    }

    /**
     * Gets the size label.
     *
     * @return the size label
     */
    public String getSizeLabel() {
        return sizeLabel;
    }


    /**
     * Gets the view details label.
     *
     * @return the view details label
     */
    public String getViewDetailsLabel() {
        return viewDetailsLabel;
    }

    /**
     * Gets the view more CTA link.
     *
     * @return the view more CTA link
     */
    public String getViewMoreCTALink() {
        return viewMoreCTALink;
    }

    /**
     * Gets the number of products.
     *
     * @return the number of products
     */
    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    /**
     * Gets the products label.
     *
     * @return the products label
     */
    public String getProductsLabel() {
        return productsLabel;
    }


}
