package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class QualifyingProductsModel.
 */
@Model(
		adaptables = { SlingHttpServletRequest.class, Resource.class },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QualifyingProductsModel {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(QualifyingProductsModel.class);
	
	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The resolver factory. */
	@Inject
	ResourceResolverFactory resolverFactory;
	
	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
	
	/** The resource resolver. */
	ResourceResolver resourceResolver;
	
	/** The current page. */
	@Inject
    private Page currentPage;
	
	/** The hybris site id. */
	private String hybrisSiteId;
	
	/** The cells. */
	@Inject
	@Via("resource")
	public List<QualifyingProductsDetailsModel> cells; //multifield name="./cells"
	
	/** The qualifying products title. */
	@Inject
	@Via("resource")
	public String qualifyingProductsTitle;
	
	/** The qualifying products desription. */
	@Inject
	@Via("resource")
	public String qualifyingProductsDesription;
	
	@Inject
	CatalogStructureUpdateService catalogStructureUpdateService;
	
	/** The bdb search endpoint service. */
	@Inject
	BDBSearchEndpointService solrConfig;
	
	
	/** The qual prod config json. */
	private String qualProdConfigJson;

	/** The qual prod labels json. */
	private String qualProdLabelsJson;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		LOG.debug("Inside QualifyingProductsModel Init");
		
		try {
			resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
			
			hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
			
			qualProdConfigJson = getConfigJson();
			
			qualProdLabelsJson = getLabelJson();
		}
		catch (LoginException e) {
			LOG.error("Exception {}", e.getMessage());
		} finally {
			CommonHelper.closeResourceResolver(resourceResolver);
		}
	}
	
	/**
	 * Gets the config json.
	 *
	 * @return the config json
	 */
	public String getConfigJson(){
		JsonObject promoDetailsConfig = new JsonObject();
		JsonObject pricingDetails = new JsonObject();
		
			pricingDetails.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
						+ bdbApiEndpointService.getCommerceBoxAPIEndpoint().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			pricingDetails.addProperty(CommonConstants.METHOD, CommonConstants.POST);
		
		promoDetailsConfig.add(CommonConstants.PRICINGDETAILS, pricingDetails);
		LOG.debug("promoDetailsConfig {}",promoDetailsConfig);
		return promoDetailsConfig.toString();
	}
	
	/**
	 * Gets the label json.
	 *
	 * @return the label json
	 */
	public String getLabelJson(){
		
		JsonObject promoDetailsLabels = new JsonObject();
		promoDetailsLabels.addProperty(CommonConstants.QUAL_PROD_HEADING, qualifyingProductsTitle);
		promoDetailsLabels.addProperty(CommonConstants.QUAL_PROD_DESC, qualifyingProductsDesription);
		promoDetailsLabels.addProperty(CommonConstants.ADD_BUTTON, CommonHelper.getLabel(CommonConstants.ADD_TO_CART, currentPage));
		promoDetailsLabels.addProperty(CommonConstants.CLONE, CommonHelper.getLabel(CommonConstants.CLONE, currentPage));
		promoDetailsLabels.addProperty(CommonConstants.SIZE, CommonHelper.getLabel(CommonConstants.SIZE, currentPage));
		promoDetailsLabels.addProperty(CommonConstants.STATUS_LABEL, CommonHelper.getLabel(CommonConstants.PROMOTION_STATUS_LABEL, currentPage));
		promoDetailsLabels.addProperty(CommonConstants.PRICE_PER_UNIT, CommonHelper.getLabel(CommonConstants.PRICE_PER_UNIT, currentPage));
		promoDetailsLabels.addProperty(CommonConstants.LIST_PRICE, CommonHelper.getLabel(CommonConstants.LIST_PRICE, currentPage));
		promoDetailsLabels.addProperty(CommonConstants.YOUR_PRICE, CommonHelper.getLabel(CommonConstants.YOUR_PRICE, currentPage));
		
			List<String> skuIds = new ArrayList<>();
			if(null!=cells) {
				for(QualifyingProductsDetailsModel cell:cells) {
					skuIds.add(cell.getSkuTag());
				}
			}
			
			JsonArray qualifyingProdDataArray = new JsonArray();
			for(String skuId:skuIds) {
				JsonObject qualifyingProdDataJson = getSkuIdDetails(skuId);
				qualifyingProdDataArray.add(qualifyingProdDataJson);
			}
		boolean isQuote = CommonHelper.toBoolean(CommonHelper.getLabel(CommonConstants.ENABLE_ADD_TO_QUOTE, currentPage));
		promoDetailsLabels.addProperty(CommonConstants.IS_QUOTE,isQuote);
		if(isQuote) {
			String addToQuoteLabel = CommonHelper.getLabel(CommonConstants.ADD_TO_QUOTE_LABEL,currentPage);
			String quoteAltTextLabel = CommonHelper.getLabel(CommonConstants.QUOTE_ALT_TEXT_LABEL,currentPage);
			promoDetailsLabels.addProperty(CommonConstants.ADD_TO_QUOTE, addToQuoteLabel);
			promoDetailsLabels.addProperty(CommonConstants.ADD_TO_QUOTE_ALT, quoteAltTextLabel);
		}else {	
			String cartAltTextLabel = CommonHelper.getLabel(CommonConstants.CART_ALT_TEXT_LABEL,currentPage);
			String addToCartLabel = CommonHelper.getLabel(CommonConstants.ADD_TO_CART_LABEL,currentPage);			
			promoDetailsLabels.addProperty(CommonConstants.ADD_TO_CART_LABEL, addToCartLabel);
			promoDetailsLabels.addProperty(CommonConstants.CART_ALT_TEXT, cartAltTextLabel);
		}
		promoDetailsLabels.add(CommonConstants.QUAL_PROD_DATA, qualifyingProdDataArray);
		LOG.debug("promoDetailsLabels {}",promoDetailsLabels);
		return promoDetailsLabels.toString();
	}
	
	/**
	 * Gets the sku id details.
	 *
	 * @param skuId the sku id
	 * @return the sku id details
	 */
	public JsonObject getSkuIdDetails(String skuId) {
		
		String tagName = StringUtils.EMPTY;
		String hyperlink = StringUtils.EMPTY;
		String labelDescription = StringUtils.EMPTY;
		String tdsCloneName = StringUtils.EMPTY;
		String size = StringUtils.EMPTY;
		String regulatoryStatus = StringUtils.EMPTY;
		String formattedImagePath = StringUtils.EMPTY;
		JsonObject qualifyingProdDataJson = new JsonObject();
		
		try {
			resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
			Tag tag = tagManager.resolve(skuId);

			if (null != tag) {
				tagName = tag.getName();
				String country = CommonHelper.getCountry(currentPage);
				Resource variantRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, tagName,
						CommonConstants.MATERIAL_NUMBER);

				Resource baseHpResource = CommonHelper.getBaseHpResourceFromLookUp(variantRes);
				if (null != baseHpResource) {
					ValueMap baseValueMap = baseHpResource.getValueMap();
					if (null != baseValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class))
						labelDescription = baseValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class);
					
					if (null != baseHpResource.getParent().getChild(tagName)) {
						regulatoryStatus = CommonHelper.getRuoFromRegionDetails(baseHpResource, country, tagName);
					}
					if(StringUtils.isNotBlank(regulatoryStatus) && country.equalsIgnoreCase("jp") && regulatoryStatus.equals("IVD")) {
						regulatoryStatus = CommonHelper.getTranslatedRegulatoryStatus("JP_IVD", resourceResolver, solrConfig);
					}
					tdsCloneName = getProductClone(tdsCloneName, baseValueMap);

					Resource variantHpResource = variantRes.getChild(CommonConstants.HP);
					ValueMap variantValueMap = variantHpResource.adaptTo(ValueMap.class);
					if (null != variantValueMap.get(CommonConstants.SIZE_QTY, String.class))
						size = variantValueMap.get(CommonConstants.SIZE_QTY, String.class) + CommonConstants.SPACE
								+ variantValueMap.get(CommonConstants.SIZE_UOM, String.class);
					formattedImagePath = CommonHelper.getGlobalThumbnailImage(resourceResolver, variantHpResource,
							externalizerService, bdbApiEndpointService);
					hyperlink = CommonHelper.getPdpProductUrl(variantHpResource);
				}

				String language = CommonHelper.getLanguage(currentPage) + "-" + country;
				hyperlink = CommonConstants.CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.BDB
						+ CommonConstants.SINGLE_SLASH + CommonHelper.getRegion(currentPage)
						+ CommonConstants.SINGLE_SLASH + CommonHelper.getCountry(currentPage)
						+ CommonConstants.SINGLE_SLASH + language + hyperlink;
				hyperlink = externalizerService.getFormattedUrl(hyperlink, resourceResolver);
			}

			if (StringUtils.isNotEmpty(tagName)) {
				qualifyingProdDataJson.addProperty(CommonConstants.PROD_CODE, Integer.parseInt(tagName));
			}
			qualifyingProdDataJson.addProperty(CommonConstants.PRODUCT_LINK, hyperlink);
			qualifyingProdDataJson.addProperty(CommonConstants.PROD_NAME, labelDescription);

			JsonObject fieldsJson = new JsonObject();
			fieldsJson.addProperty(CommonConstants.CLONE, tdsCloneName);
			fieldsJson.addProperty(CommonConstants.SIZE, size);
			fieldsJson.addProperty(CommonConstants.STATUS_LABEL, regulatoryStatus);

			qualifyingProdDataJson.add(CommonConstants.FIELDS, fieldsJson);
			qualifyingProdDataJson.addProperty(CommonConstants.IMAGE, formattedImagePath);
			LOG.debug("promoDetailsLabels - qualifyingProdDataJson {}", qualifyingProdDataJson);

		} catch (LoginException | NumberFormatException e) {
			LOG.error("Exception {}", e.getMessage());
		} catch (RepositoryException e) {
			LOG.error("RepositoryException {}", e.getMessage());
		}
		return qualifyingProdDataJson;
	}

	
	/**
	 * Gets the product clone.
	 *
	 * @param tdsCloneName the tds clone name
	 * @param tagValueMap the tag value map
	 * @return the product clone
	 */
	public String getProductClone(String tdsCloneName, ValueMap tagValueMap) {
		if (null != tagValueMap && null != tagValueMap.get(CommonConstants.PRODUCT_CLONE, String.class)) {
			String productData = tagValueMap.get(CommonConstants.PRODUCT_CLONE, String.class);
			JsonArray jsonArray1 = new JsonParser().parse(productData).getAsJsonArray();
			for (JsonElement jsonElement : jsonArray1) {
				JsonObject productObj = jsonElement.getAsJsonObject();
				if (null != productObj.get(CommonConstants.TDS_CLONE_NAME)) {
					tdsCloneName = productObj.get(CommonConstants.TDS_CLONE_NAME).getAsString();
				}
			}
		}
		return tdsCloneName;
	}
	
	/**
	 * Gets the cells.
	 *
	 * @return the cells
	 */
	public List<QualifyingProductsDetailsModel> getCells() {
		return new ArrayList<>(cells);
	}
	
	/**
	 * Gets the qualifying products title.
	 *
	 * @return the qualifying products title
	 */
	public String getQualifyingProductsTitle() {
		return qualifyingProductsTitle;
	}

	/**
	 * Gets the qualifying products desription.
	 *
	 * @return the qualifying products desription
	 */
	public String getQualifyingProductsDesription() {
		return qualifyingProductsDesription;
	}

	/**
	 * Gets the qual prod config json.
	 *
	 * @return the qual prod config json
	 */
	public String getQualProdConfigJson() {
		return qualProdConfigJson;
	}

	/**
	 * Gets the qual prod labels json.
	 *
	 * @return the qual prod labels json
	 */
	public String getQualProdLabelsJson() {
		return qualProdLabelsJson;
	}
}