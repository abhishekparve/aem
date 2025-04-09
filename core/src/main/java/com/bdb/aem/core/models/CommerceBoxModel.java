package com.bdb.aem.core.models;

import java.util.Iterator;

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
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.CookieNameService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrUtils;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;

/**
 * Sling Model to fetch values from dialog and product structure in AEM, and
 * return respective JSONs to the FE.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CommerceBoxModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(CommerceBoxModel.class);

	/** The catalog no. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String catalogNo;

	/** The your price. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String yourPrice;

	/** The list price. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String listPrice;

	/** The est delivery. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String estDelivery;

	/** The unit. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String unit;

	/** The size. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String size;

	/** The quantity. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quantity;

	/** The quantity. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private long quantityValue;

	/** The add to cart. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String addToCart;

	/** The save to shopping list. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String saveToShoppingList;

	/** The request bulk reagent quote. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String requestBulkReagentQuote;

	/** The request bulk reagent quote url. */
	private String requestBulkReagentQuoteUrl;

	/** The sign in. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String signIn;

	/** The decrement alttext. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String decrementAlttext;

	/** The increment alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String incrementAltText;

	/** The up arrow. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String upArrow;

	/** The down arrow. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String downArrow;

	/** The plus aria label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String plusAriaLabel;

	/** The minus aria label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String minusAriaLabel;

	/** The promotion available label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String promotionAvailableLabel;

	/** The more information label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String moreInformationLabel;

	/** The promotion icon path. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String promotionIconPath;

	/** The promotion icon alt. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String promotionIconAlt;

	/** The expires label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String expiresLabel;

	/** The promo code label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String promoCodeLabel;

	/** The copy to clipboard. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String copyToClipboardLabel;

	/** The button text label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String buttonTextLabel;

	/** The out of stock label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	@Getter
	private String outOfStock;

	/** The In Stock stock label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	@Getter
	private String inStock;

	/** The distributorDeliveryDate label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	@Getter
	private String distributorDeliveryDate;

	/** The Add to cart alt text label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String atoCAltText;

	/** The navigation link label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String navigationLink;

	/** The up arrow aria label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String upArrowAriaLabel;

	/** The down arrow aria label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String downArrowAriaLabel;

	/** The color label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String color;

	/** The color label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String configuration;

	/** The lasers label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String lasers;

	/** The Add to quote label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String addToQuote;

	/** The Add to quote alt text label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String atoQAltText;

	/** The save to quote list label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String saveToQuoteList;

	/** The request reagent quote target label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String requestBulkReagentQuoteTarget;

	/** The request quote label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String requestQuote;

	/** The promoUrl. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String promoUrl;
	
	/** The InquireLabel. */
	@Inject
	@Via("resource")
	@Default(values = "Inquire")
	private String inquireLabel;

	
	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The resolver factory. */
	@Inject
	ResourceResolverFactory resolverFactory;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/** The bdb api endpoint service. */
	@Inject
	CookieNameService cookieNameService;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** Json String for commerceBox Labels. */
	private String commerceBoxJsonLabels;

	/** Json String for commerceBox Configs. */
	private String commerceBoxJsonConfigs;

	/** The cookies json. */
	private String cookiesJson;

	/** The add to cart config json. */
	private String addToCartConfigJson;

	/** The add to quote config json. */
	private String addToQuoteConfigJson;

	/** The size config json. */
	private String sizeConfigJson;

	/** The promotions json labels. */
	private String promotionsJsonLabels;

	/** The promotions json config. */
	private String promotionsJsonConfig;

	/** The quote commerce box labels. */
	@Getter
	private String quoteCommerceBoxLabels;

	/** The promotions json config. */
	@Getter
	private String quotePromotionLabels;

	/** The other products promotion labels. */
	@Getter
	private String otherProductsPromotionLabels;

	/** The other products commerce labels. */
	@Getter
	private String otherProductsCommerceLabels;

	/** The pdp instruments commerce box labels. */
	@Getter
	private String pdpInstrumentsCommerceBoxLabels;

	/** The pdp instruments commerce box labels. */
	@Getter
	private String pdpQuoteInstrumentsCommerceBoxLabels;

	/** The pdp instruments commerce box configs. */
	@Getter
	private String pdpInstrumentsCommerceBoxconfig;

	/** The pdp kits and sets commerce box config. */
	@Getter
	private String kitsAndSetsConfig;

	/** The pdp kits and sets quote commerce box labels. */
	@Getter
	private String kitsAndSetsQuoteLabels;

	/** The pdp other products quote labels. */
	@Getter
	private String otherProductsQuoteLabels;

	/** The pdp other products quote labels. */
	@Getter
	private String kitsAndSetsCommerceBoxLabels;

	/** The resource resolver. */
	ResourceResolver resourceResolver;

	/** The hybris site id. */
	String hybrisSiteId;

	@Inject
	SolrSearchService solrSearchService;
	
	@Inject
	CatalogStructureUpdateService catalogStructureUpdateService;
	
	String country = StringUtils.EMPTY;

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 * @throws RepositoryException
	 */
	@PostConstruct
	protected void init() throws RepositoryException {
		long startTime = System.currentTimeMillis();
		logger.debug("CommerceBoxModel - Init method started");

		try {
			
			// getting Service Resource Resolver
			resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
			country = CommonHelper.getCountry(currentPage);
			hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
			requestBulkReagentQuoteUrl = CommonHelper.getLabel("requestBulkReagentQuoteUrl", currentPage);
			Resource baseHpResource = null;
			if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)) {
				String productVarHPPath = request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString();
				baseHpResource = resourceResolver.getResource(productVarHPPath);
				ValueMap baseHpValueMap = baseHpResource.adaptTo(ValueMap.class);
						
				commerceBoxJsonConfigs = getConfigJson(resourceResolver, baseHpResource);

				kitsAndSetsConfig = getKitsAndSetsCommerceBoxConfigJson(resourceResolver, baseHpResource);

				commerceBoxJsonLabels = getLabelJson(baseHpResource);
				pdpInstrumentsCommerceBoxconfig = getInstrumentsCommerceBoxConfig(baseHpValueMap);
			}
			
			quoteCommerceBoxLabels = getQuoteCommerceBoxJSON();

			cookiesJson = getCookieJson();

			addToCartConfigJson = getAddToCartConfig();

			addToQuoteConfigJson = getAddToQuoteConfig();

			promotionsJsonLabels = getPromotionsLabelsJson();

			promotionsJsonConfig = getPromotionsConfigJson();

			quotePromotionLabels = getQuotePromotionsLabelsJson();

			otherProductsCommerceLabels = getOtherProductsCommerceBoxJSON();

			otherProductsPromotionLabels = getOtherProductsPromotionsLabelsJson();

			pdpInstrumentsCommerceBoxLabels = getInstrumentsCommerceBoxJSON();

			pdpQuoteInstrumentsCommerceBoxLabels = getQuoteInstrumentsCommerceBoxJSON();

			kitsAndSetsQuoteLabels = getQuoteKitsCommerceBoxJSON();

			kitsAndSetsCommerceBoxLabels = getKitsAndSetsCommerceBoxLabelsJson();

			otherProductsQuoteLabels = getOtherProductsQuoteCommerceBoxJSON();
			
		} catch (LoginException e) {
			logger.error("LoginException {}", e.getMessage());
		} finally {
			CommonHelper.closeResourceResolver(resourceResolver);
		}
		long endTime = System.currentTimeMillis();
		logger.debug("CommerceBoxModel TIME - {}" ,endTime-startTime);
	}

	/**
	 * Gets the instruments commerce box config.
	 *
	 * @return the instruments commerce box config
	 */
	private String getInstrumentsCommerceBoxConfig(ValueMap baseHpValueMap) {
		JsonObject instrumentsCommerceBoxConfig = new JsonObject();
		JsonObject requestPayloadJsonObject = new JsonObject();
		JsonArray dataJsonArray = new JsonArray();
		String catalogNumber = CommonHelper.getSelectorDetails(request);
		String categoryValue = StringUtils.EMPTY;
		dataJsonArray.add(catalogNumber);
		if (null != baseHpValueMap.get("configuration")) {
			categoryValue = baseHpValueMap.get("configuration").toString();
		}
		if (StringUtils.isNoneEmpty(hybrisSiteId)) {
			requestPayloadJsonObject.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getCommerceBoxAPIEndpoint()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			requestPayloadJsonObject.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
			requestPayloadJsonObject.add(CommonConstants.DATA, dataJsonArray);
		}
		instrumentsCommerceBoxConfig.add(CommonConstants.REQUEST_PAYOAD, requestPayloadJsonObject);
		instrumentsCommerceBoxConfig.add(CommonConstants.VARIANT, dataJsonArray);
		instrumentsCommerceBoxConfig.addProperty("configurationsValue",categoryValue);
		return instrumentsCommerceBoxConfig.toString();
	}

	/**
	 * This method forms the json for kits and sets commerce box config.
	 *
	 * @param resolver the resolver
	 * @return the config json
	 * @throws RepositoryException 
	 */
	public String getKitsAndSetsCommerceBoxConfigJson(ResourceResolver resolver, Resource baseHpResource) throws RepositoryException {

		JsonObject kitsConfig = getCommonConfigJson();
		JsonArray sizeJsonArray = getSize(resolver, baseHpResource);
		kitsConfig.add(CommonConstants.VARIANT, sizeJsonArray);
		return kitsConfig.toString();
	}

	/**
	 * This method forms the json for commerce box config.
	 *
	 * @param resolver the resolver
	 * @return the config json
	 * @throws RepositoryException 
	 */
	public String getConfigJson(ResourceResolver resolver, Resource baseHpResource) throws RepositoryException {
		JsonObject commerceBoxJsonConfig = getCommonConfigJson();
		JsonArray sizeJsonArray = getSize(resolver, baseHpResource);
		commerceBoxJsonConfig.add(CommonConstants.VARIANT, sizeJsonArray);
		return commerceBoxJsonConfig.toString();
	}

	/**
	 * This method forms the json common for commerce box config.
	 *
	 * @return the config JsonObject
	 */
	private JsonObject getCommonConfigJson() {
		JsonObject commerceBoxJsonConfig = new JsonObject();
		JsonObject requestPayloadJsonObject = new JsonObject();
		JsonObject quantityJsonObject = new JsonObject();
		JsonArray dataJsonArray = new JsonArray();

		String catalogNumber = CommonHelper.getSelectorDetails(request);
		quantityJsonObject.addProperty("minValue", 1);
		quantityJsonObject.addProperty("maxValue", quantityValue);
		dataJsonArray.add(catalogNumber);
		if (null != hybrisSiteId) {
			requestPayloadJsonObject.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getCommerceBoxAPIEndpoint()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			requestPayloadJsonObject.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
			requestPayloadJsonObject.add(CommonConstants.DATA, dataJsonArray);
		}

		commerceBoxJsonConfig.add(CommonConstants.QUANTIY, quantityJsonObject);
		commerceBoxJsonConfig.add(CommonConstants.REQUEST_PAYOAD, requestPayloadJsonObject);

		return commerceBoxJsonConfig;
	}

	/**
	 * Gets the adds the to quote config.
	 *
	 * @return the adds the to quote config
	 */
	public String getAddToQuoteConfig() {
		JsonObject addToQuoteConfig = new JsonObject();
		JsonObject getAllCustomersCart = new JsonObject();
		JsonObject createCartForUser = new JsonObject();
		JsonObject addQuantity = new JsonObject();

		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		if (null != hybrisSiteId) {
			getAllCustomersCart.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getAllCustomersCartEndpoint()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			getAllCustomersCart.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);

			createCartForUser.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getAllCustomersCartEndpoint()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			createCartForUser.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

			addQuantity.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getaddQuantityEndpoint()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			addQuantity.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		}

		addToQuoteConfig.add(CommonConstants.GET_ALL_CUSTOMERS_CART, getAllCustomersCart);
		addToQuoteConfig.add(CommonConstants.CREATE_CART_FOR_USER, createCartForUser);
		addToQuoteConfig.add(CommonConstants.ADD_QUANTITY, addQuantity);

		return addToQuoteConfig.toString();
	}

	/**
	 * This method forms the json for commerce box labels.
	 * @param hpResource 
	 *
	 * @return the label json
	 * @throws RepositoryException
	 */
	public String getLabelJson(Resource baseHpResource) throws RepositoryException {
		JsonObject commerceBoxJsonLabel = new JsonObject();
		JsonObject ariaLabelsObject = new JsonObject();
		JsonObject customDropdownObject = new JsonObject();
		JsonObject quantityObject = new JsonObject();

		// forming labels JSON
		commerceBoxJsonLabel.addProperty(CommonConstants.CATALOG_NUMBER, catalogNo);
		commerceBoxJsonLabel.addProperty(CommonConstants.YOUR_PRICE, yourPrice);
		commerceBoxJsonLabel.addProperty(CommonConstants.SIGN_IN, signIn);
		commerceBoxJsonLabel.addProperty(CommonConstants.LIST_PRICE, listPrice);
		commerceBoxJsonLabel.addProperty(CommonConstants.EST_DELIVERY, estDelivery);
		commerceBoxJsonLabel.addProperty(CommonConstants.UNIT, unit);
		commerceBoxJsonLabel.addProperty(CommonConstants.SIZE, size);
		commerceBoxJsonLabel.addProperty(CommonConstants.QUANTIY, quantity);
		commerceBoxJsonLabel.addProperty(CommonConstants.OUT_OF_STOCK, outOfStock);
		commerceBoxJsonLabel.addProperty(CommonConstants.IN_STOCK, inStock);
		commerceBoxJsonLabel.addProperty(CommonConstants.DISTRIBUTOR_DELIVERY_DATE, distributorDeliveryDate);
		commerceBoxJsonLabel.addProperty(CommonConstants.DECREMENT_ALT_TEXT, decrementAlttext);
		commerceBoxJsonLabel.addProperty(CommonConstants.INCREMENT_ALT_TEXT, incrementAltText);
		commerceBoxJsonLabel.addProperty(CommonConstants.ADD_TO_CART, addToCart);
		commerceBoxJsonLabel.addProperty(CommonConstants.SAVE_TO_SHOPPING_LIST, saveToShoppingList);
		if(null != requestBulkReagentQuoteUrl && !requestBulkReagentQuoteUrl.isEmpty() && getVarientResource(resourceResolver,baseHpResource).equals(CommonConstants.TRUE)) {
			commerceBoxJsonLabel.addProperty(CommonConstants.REQUEST_BULK_REAGENT_QUOTE_URL,
					externalizerService.getFormattedUrl(requestBulkReagentQuoteUrl, resourceResolver));
		}
		if(getVarientResource(resourceResolver,baseHpResource).equals(CommonConstants.TRUE) && null != requestBulkReagentQuoteUrl && !requestBulkReagentQuoteUrl.isEmpty()) {
			commerceBoxJsonLabel.addProperty(CommonConstants.REQUEST_BULK_REAGENT_QUOTE, requestBulkReagentQuote);
		}
		customDropdownObject.addProperty(CommonConstants.UP_ARROW, upArrow);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW, downArrow);

		quantityObject.addProperty(CommonConstants.PLUS_ARIA_LABEL, plusAriaLabel);
		quantityObject.addProperty(CommonConstants.INCREMENT_ALT_TEXT, CommonConstants.INCREMENT_ALT_TEXT);
		quantityObject.addProperty(CommonConstants.MINUS_ARIA_LABEL, minusAriaLabel);
		quantityObject.addProperty(CommonConstants.DECREMENT_ALT_TEXT, decrementAlttext);

		ariaLabelsObject.add(CommonConstants.CUSTOM_DROPDOWN, customDropdownObject);
		ariaLabelsObject.add(CommonConstants.QUANTIY, quantityObject);
		commerceBoxJsonLabel.add(CommonConstants.ARIA_LABEL, ariaLabelsObject);
		commerceBoxJsonLabel.addProperty(CommonConstants.INQUIRE, inquireLabel);

		return commerceBoxJsonLabel.toString();
	}

	public String getVarientResource(ResourceResolver resourceResolver, Resource baseHpResource)
			 {
		String catalogNumber = CommonHelper.getSelectorDetails(request);
		
		if (null != baseHpResource) {
			Resource variantHpResource = SolrUtils.getVariantHpResource(baseHpResource, catalogNumber);
			if(null != variantHpResource) {
				Resource parentResource = variantHpResource.getParent();
				if (null != parentResource.getChild(CommonConstants.MARKETING_NODE)) {
					Resource marketingResource = parentResource.getChild(CommonConstants.MARKETING_NODE);
					if (null != marketingResource.getChild(country)) {
						Resource regionRes = marketingResource.getChild(country);
						logger.debug("Get Properties from regionRes {}", regionRes);
						ValueMap varientValueMap = regionRes.getValueMap();
						if (null != varientValueMap.get(CommonConstants.DISPLAY_BULK_REAGENT_QUOTE) && varientValueMap.get(CommonConstants.DISPLAY_BULK_REAGENT_QUOTE).equals(CommonConstants.FALSE)) {
							return varientValueMap.get(CommonConstants.DISPLAY_BULK_REAGENT_QUOTE).toString();
						}

					}

				}	
			}
		}
		return CommonConstants.TRUE;
	}

	/**
	 * This method forms the json for commerce box labels.
	 *
	 * @return the label json
	 */
	public String getOtherProductsCommerceBoxJSON() {
		JsonObject commerceBoxJsonLabel = new JsonObject();
		JsonObject ariaLabelsObject = new JsonObject();
		JsonObject customDropdownObject = new JsonObject();
		JsonObject quantityObject = new JsonObject();
		JsonObject pdpModifier = new JsonObject();

		// forming labels JSON
		commerceBoxJsonLabel.addProperty(CommonConstants.CATALOG_NUMBER, catalogNo);
		commerceBoxJsonLabel.addProperty(CommonConstants.YOUR_PRICE, yourPrice);
		commerceBoxJsonLabel.addProperty(CommonConstants.LIST_PRICE, listPrice);
		commerceBoxJsonLabel.addProperty(CommonConstants.EST_DELIVERY, estDelivery);
		commerceBoxJsonLabel.addProperty(CommonConstants.UNIT, unit);
		commerceBoxJsonLabel.addProperty(CommonConstants.SIZE, size);
		commerceBoxJsonLabel.addProperty(CommonConstants.QUANTIY, quantity);
		commerceBoxJsonLabel.addProperty(CommonConstants.ADD_TO_CART, addToCart);
		commerceBoxJsonLabel.addProperty(CommonConstants.OUT_OF_STOCK, outOfStock);
		commerceBoxJsonLabel.addProperty(CommonConstants.IN_STOCK, inStock);
		commerceBoxJsonLabel.addProperty(CommonConstants.DISTRIBUTOR_DELIVERY_DATE, distributorDeliveryDate);
		commerceBoxJsonLabel.addProperty(CommonConstants.SAVE_TO_SHOPPING_LIST, saveToShoppingList);
		commerceBoxJsonLabel.addProperty(CommonConstants.SIGN_IN, signIn);

		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ALT_TEXT, upArrow);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT, downArrow);
		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ARIA_LABEL, upArrowAriaLabel);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ARIA_LABEL, downArrowAriaLabel);

		quantityObject.addProperty(CommonConstants.PLUS_ARIA_LABEL, plusAriaLabel);
		quantityObject.addProperty(CommonConstants.INCREMENT_ALT_TEXT, incrementAltText);
		quantityObject.addProperty(CommonConstants.MINUS_ARIA_LABEL, minusAriaLabel);
		quantityObject.addProperty(CommonConstants.DECREMENT_ALT_TEXT, decrementAlttext);

		pdpModifier.addProperty(CommonConstants.ATOC_ALT_TEXT, atoCAltText);

		ariaLabelsObject.add(CommonConstants.CUSTOM_DROPDOWN, customDropdownObject);
		ariaLabelsObject.add(CommonConstants.QUANTIY, quantityObject);
		ariaLabelsObject.add(CommonConstants.PDP_MODIFIER, pdpModifier);
		commerceBoxJsonLabel.add(CommonConstants.ARIA_LABEL, ariaLabelsObject);
		commerceBoxJsonLabel.addProperty(CommonConstants.INQUIRE, inquireLabel);

		return commerceBoxJsonLabel.toString();
	}

	/**
	 * This method forms the json for quote commerce box labels.
	 *
	 * @return the label json
	 */
	public String getQuoteCommerceBoxJSON() {
		JsonObject commerceBoxJsonLabel = new JsonObject();
		JsonObject ariaLabelsObject = new JsonObject();
		JsonObject customDropdownObject = new JsonObject();
		JsonObject quantityObject = new JsonObject();
		JsonObject pdpModifier = new JsonObject();

		// forming labels JSON
		commerceBoxJsonLabel.addProperty(CommonConstants.CATALOG_NUMBER, catalogNo);
		commerceBoxJsonLabel.addProperty(CommonConstants.LIST_PRICE, listPrice);
		commerceBoxJsonLabel.addProperty(CommonConstants.UNIT, unit);
		commerceBoxJsonLabel.addProperty(CommonConstants.SIZE, size);
		commerceBoxJsonLabel.addProperty(CommonConstants.QUANTIY, quantity);
		commerceBoxJsonLabel.addProperty(CommonConstants.ADD_TO_QUOTE, addToQuote);
		commerceBoxJsonLabel.addProperty(CommonConstants.OUT_OF_STOCK, outOfStock);
		commerceBoxJsonLabel.addProperty(CommonConstants.SAVE_TO_QUOTE_LIST, saveToQuoteList);
		commerceBoxJsonLabel.addProperty(CommonConstants.REQUEST_BULK_REAGENT_QUOTE, requestBulkReagentQuote);
		commerceBoxJsonLabel.addProperty(CommonConstants.REQUEST_BULK_REAGENT_QUOTE_URL,
				externalizerService.getFormattedUrl(requestBulkReagentQuoteUrl, resourceResolver));
		commerceBoxJsonLabel.addProperty(CommonConstants.REQUEST_BULK_REAGENT_QUOTE_TARGET,
				requestBulkReagentQuoteTarget);

		commerceBoxJsonLabel.addProperty(CommonConstants.IS_QUOTE,
				CommonHelper.toBoolean(CommonHelper.getLabel(CommonConstants.ENABLE_ADD_TO_QUOTE, currentPage)));

		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ALT_TEXT, upArrow);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT, downArrow);
		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ARIA_LABEL, upArrowAriaLabel);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ARIA_LABEL, downArrowAriaLabel);

		quantityObject.addProperty(CommonConstants.PLUS_ARIA_LABEL, plusAriaLabel);
		quantityObject.addProperty(CommonConstants.INCREMENT_ALT_TEXT, incrementAltText);
		quantityObject.addProperty(CommonConstants.MINUS_ARIA_LABEL, minusAriaLabel);
		quantityObject.addProperty(CommonConstants.DECREMENT_ALT_TEXT, decrementAlttext);

		pdpModifier.addProperty(CommonConstants.ATOQ_ALT_TEXT, atoQAltText);

		ariaLabelsObject.add(CommonConstants.CUSTOM_DROPDOWN, customDropdownObject);
		ariaLabelsObject.add(CommonConstants.QUANTIY, quantityObject);
		ariaLabelsObject.add(CommonConstants.PDP_MODIFIER, pdpModifier);
		commerceBoxJsonLabel.add(CommonConstants.ARIA_LABEL, ariaLabelsObject);
		commerceBoxJsonLabel.addProperty(CommonConstants.INQUIRE, inquireLabel);

		return commerceBoxJsonLabel.toString();
	}

	/**
	 * Gets the quote instruments commerce box JSON.
	 *
	 * @return the quote instruments commerce box JSON
	 */
	public String getQuoteInstrumentsCommerceBoxJSON() {
		JsonObject instrumentsBoxJson = new JsonObject();
		JsonObject ariaLabelsObject = new JsonObject();
		JsonObject customDropdownObject = new JsonObject();
		JsonObject pdpModifier = new JsonObject();

		instrumentsBoxJson.addProperty(CommonConstants.IS_QUOTE,
				CommonHelper.toBoolean(CommonHelper.getLabel(CommonConstants.ENABLE_ADD_TO_QUOTE, currentPage)));
		instrumentsBoxJson.addProperty(CommonConstants.CATALOG_NUMBER, catalogNo);
		instrumentsBoxJson.addProperty(CommonConstants.LIST_PRICE, listPrice);
		instrumentsBoxJson.addProperty("configurations", configuration);
		instrumentsBoxJson.addProperty(CommonConstants.ADD_TO_QUOTE, addToQuote);
		instrumentsBoxJson.addProperty(CommonConstants.SAVE_TO_QUOTE_LIST, saveToQuoteList);
		instrumentsBoxJson.addProperty(CommonConstants.REQUEST_QUOTE, requestQuote);

		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ALT_TEXT, upArrow);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT, downArrow);
		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ARIA_LABEL, upArrowAriaLabel);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ARIA_LABEL, downArrowAriaLabel);

		pdpModifier.addProperty(CommonConstants.ATOQ_ALT_TEXT, atoQAltText);

		ariaLabelsObject.add(CommonConstants.CUSTOM_DROPDOWN, customDropdownObject);
		ariaLabelsObject.add(CommonConstants.PDP_MODIFIER, pdpModifier);
		instrumentsBoxJson.add(CommonConstants.ARIA_LABEL, ariaLabelsObject);
		instrumentsBoxJson.addProperty(CommonConstants.INQUIRE, inquireLabel);

		return instrumentsBoxJson.toString();
	}

	/**
	 * Gets the quote kits commerce box JSON.
	 *
	 * @return the quote kits commerce box JSON
	 */
	public String getQuoteKitsCommerceBoxJSON() {
		JsonObject quoteKitsCommerceBoxJson = new JsonObject();
		JsonObject ariaLabelsObject = new JsonObject();
		JsonObject customDropdownObject = new JsonObject();
		JsonObject pdpModifier = new JsonObject();

		quoteKitsCommerceBoxJson.addProperty(CommonConstants.IS_QUOTE,
				CommonHelper.toBoolean(CommonHelper.getLabel(CommonConstants.ENABLE_ADD_TO_QUOTE, currentPage)));
		quoteKitsCommerceBoxJson.addProperty(CommonConstants.CATALOG_NUMBER, catalogNo);
		quoteKitsCommerceBoxJson.addProperty(CommonConstants.LIST_PRICE, listPrice);
		quoteKitsCommerceBoxJson.addProperty(CommonConstants.UNIT, unit);
		quoteKitsCommerceBoxJson.addProperty(CommonConstants.SIZE, size);
		quoteKitsCommerceBoxJson.addProperty(CommonConstants.QUANTIY, quantity);
		quoteKitsCommerceBoxJson.addProperty(CommonConstants.ADD_TO_QUOTE, addToQuote);
		quoteKitsCommerceBoxJson.addProperty(CommonConstants.SAVE_TO_QUOTE_LIST, saveToQuoteList);

		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ALT_TEXT, upArrow);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT, downArrow);
		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ARIA_LABEL, upArrowAriaLabel);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ARIA_LABEL, downArrowAriaLabel);

		pdpModifier.addProperty(CommonConstants.ATOQ_ALT_TEXT, atoQAltText);

		ariaLabelsObject.add(CommonConstants.CUSTOM_DROPDOWN, customDropdownObject);
		ariaLabelsObject.add(CommonConstants.PDP_MODIFIER, pdpModifier);
		quoteKitsCommerceBoxJson.add(CommonConstants.ARIA_LABEL, ariaLabelsObject);
		quoteKitsCommerceBoxJson.addProperty(CommonConstants.INQUIRE, inquireLabel);

		return quoteKitsCommerceBoxJson.toString();
	}

	private String getKitsAndSetsCommerceBoxLabelsJson() {
		JsonObject kitsCommerceBoxJson = new JsonObject();
		kitsCommerceBoxJson.addProperty(CommonConstants.OUT_OF_STOCK, outOfStock);
		kitsCommerceBoxJson.addProperty(CommonConstants.IN_STOCK, inStock);
		kitsCommerceBoxJson.addProperty(CommonConstants.DISTRIBUTOR_DELIVERY_DATE, distributorDeliveryDate);

		return kitsCommerceBoxJson.toString();
	}


	/**
	 * This method forms the json for commerce box labels.
	 *
	 * @return the label json
	 */
	public String getOtherProductsQuoteCommerceBoxJSON() {
		JsonObject quoteOtherProductLabels = new JsonObject();
		JsonObject ariaLabelsObject = new JsonObject();
		JsonObject customDropdownObject = new JsonObject();
		JsonObject quantityObject = new JsonObject();
		JsonObject pdpModifier = new JsonObject();

		quoteOtherProductLabels.addProperty(CommonConstants.CATALOG_NUMBER, catalogNo);
		quoteOtherProductLabels.addProperty(CommonConstants.LIST_PRICE, listPrice);
		quoteOtherProductLabels.addProperty(CommonConstants.UNIT, unit);
		quoteOtherProductLabels.addProperty(CommonConstants.SIZE, size);
		quoteOtherProductLabels.addProperty(CommonConstants.QUANTIY, quantity);
		quoteOtherProductLabels.addProperty(CommonConstants.ADD_TO_QUOTE, addToQuote);
		quoteOtherProductLabels.addProperty(CommonConstants.SAVE_TO_QUOTE_LIST, saveToQuoteList);

		// GE-17706 - Missing Is Quote functionality for Others template
		quoteOtherProductLabels.addProperty(CommonConstants.IS_QUOTE,
				CommonHelper.toBoolean(CommonHelper.getLabel(CommonConstants.ENABLE_ADD_TO_QUOTE, currentPage)));

		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ALT_TEXT, upArrow);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT, downArrow);
		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ARIA_LABEL, upArrowAriaLabel);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ARIA_LABEL, downArrowAriaLabel);

		quantityObject.addProperty(CommonConstants.PLUS_ARIA_LABEL, plusAriaLabel);
		quantityObject.addProperty(CommonConstants.INCREMENT_ALT_TEXT, incrementAltText);
		quantityObject.addProperty(CommonConstants.MINUS_ARIA_LABEL, minusAriaLabel);
		quantityObject.addProperty(CommonConstants.DECREMENT_ALT_TEXT, decrementAlttext);

		pdpModifier.addProperty(CommonConstants.ATOQ_ALT_TEXT, atoQAltText);

		ariaLabelsObject.add(CommonConstants.CUSTOM_DROPDOWN, customDropdownObject);
		ariaLabelsObject.add(CommonConstants.QUANTIY, quantityObject);
		ariaLabelsObject.add(CommonConstants.PDP_MODIFIER, pdpModifier);
		quoteOtherProductLabels.add(CommonConstants.ARIA_LABEL, ariaLabelsObject);
		quoteOtherProductLabels.addProperty(CommonConstants.INQUIRE, inquireLabel);

		return quoteOtherProductLabels.toString();
	}

	/**
	 * Gets the instruments commerce box JSON.
	 *
	 * @return the instruments commerce box JSON
	 */
	public String getInstrumentsCommerceBoxJSON() {
		JsonObject instrumentsBoxJson = new JsonObject();
		JsonObject ariaLabelsObject = new JsonObject();
		JsonObject customDropdownObject = new JsonObject();
		JsonObject quantityObject = new JsonObject();
		JsonObject pdpModifier = new JsonObject();

		instrumentsBoxJson.addProperty(CommonConstants.CATALOG_NUMBER, catalogNo);
		instrumentsBoxJson.addProperty(CommonConstants.YOUR_PRICE, yourPrice);
		instrumentsBoxJson.addProperty(CommonConstants.LIST_PRICE, listPrice);
		instrumentsBoxJson.addProperty(CommonConstants.EST_DELIVERY, estDelivery);
		instrumentsBoxJson.addProperty("configurations", configuration);

		instrumentsBoxJson.addProperty(CommonConstants.UNIT, unit);
		instrumentsBoxJson.addProperty(CommonConstants.SIZE, size);
		instrumentsBoxJson.addProperty(CommonConstants.ADD_TO_CART, addToCart);
		instrumentsBoxJson.addProperty(CommonConstants.OUT_OF_STOCK, outOfStock);
		instrumentsBoxJson.addProperty(CommonConstants.IN_STOCK, inStock);
		instrumentsBoxJson.addProperty(CommonConstants.DISTRIBUTOR_DELIVERY_DATE, distributorDeliveryDate);
		instrumentsBoxJson.addProperty(CommonConstants.SAVE_TO_SHOPPING_LIST, saveToShoppingList);
		instrumentsBoxJson.addProperty(CommonConstants.SIGN_IN, signIn);
		instrumentsBoxJson.addProperty(CommonConstants.REQUEST_QUOTE, requestQuote);

		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ALT_TEXT, upArrow);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT, downArrow);
		customDropdownObject.addProperty(CommonConstants.UP_ARROW_ARIA_LABEL, upArrowAriaLabel);
		customDropdownObject.addProperty(CommonConstants.DOWN_ARROW_ARIA_LABEL, downArrowAriaLabel);

		quantityObject.addProperty(CommonConstants.PLUS_ARIA_LABEL, plusAriaLabel);
		quantityObject.addProperty(CommonConstants.INCREMENT_ALT_TEXT, incrementAltText);
		quantityObject.addProperty(CommonConstants.MINUS_ARIA_LABEL, minusAriaLabel);
		quantityObject.addProperty(CommonConstants.DECREMENT_ALT_TEXT, decrementAlttext);

		pdpModifier.addProperty(CommonConstants.ATOC_ALT_TEXT, atoCAltText);

		ariaLabelsObject.add(CommonConstants.CUSTOM_DROPDOWN, customDropdownObject);
		ariaLabelsObject.add(CommonConstants.QUANTIY, quantityObject);
		ariaLabelsObject.add(CommonConstants.PDP_MODIFIER, pdpModifier);
		instrumentsBoxJson.add(CommonConstants.ARIA_LABEL, ariaLabelsObject);
		instrumentsBoxJson.addProperty(CommonConstants.INQUIRE, inquireLabel);
		
		return instrumentsBoxJson.toString();
	}

	/**
	 * Gets the cookie json.
	 *
	 * @return the cookie json
	 */
	public String getCookieJson() {
		JsonObject cookieJson = new JsonObject();
		cookieJson.addProperty(CommonConstants.GUID_COOKIE_EXP_DATE, cookieNameService.getGUIDCookieExpDate());
		cookieJson.addProperty(CommonConstants.ANONYMOUS_USER_ID, cookieNameService.getAnonymousUserId());
		cookieJson.addProperty(CommonConstants.CURRENT_USER_ID, cookieNameService.getCurrentUserId());
		return cookieJson.toString();
	}

	/**
	 * Gets the addtocart config.
	 *
	 * @return the adds the to cart config
	 */
	public String getAddToCartConfig() {
		JsonObject addToCartConfig = new JsonObject();
		JsonObject getAllCustomersCart = new JsonObject();
		JsonObject createCartForUser = new JsonObject();
		JsonObject addQuantity = new JsonObject();

		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		if (StringUtils.isNoneEmpty(hybrisSiteId)) {
			getAllCustomersCart.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getAllCustomersCartEndpoint()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			getAllCustomersCart.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);

			createCartForUser.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getAllCustomersCartEndpoint()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			createCartForUser.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

			addQuantity.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getaddQuantityEndpoint()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			addQuantity.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		}

		addToCartConfig.add(CommonConstants.GET_ALL_CUSTOMERS_CART, getAllCustomersCart);
		addToCartConfig.add(CommonConstants.CREATE_CART_FOR_USER, createCartForUser);
		addToCartConfig.add(CommonConstants.ADD_QUANTITY, addQuantity);

		return addToCartConfig.toString();
	}

	/**
	 * Gets the size variants.
	 *
	 * @param resolver the resolver
	 * @return the size
	 * @throws RepositoryException 
	 */
	public JsonArray getSize(ResourceResolver resolver, Resource baseHpResource) throws RepositoryException {
		JsonArray sizeJsonArray = new JsonArray();
		Resource baseProductResource = baseHpResource.getParent();
		Iterator<Resource> iterator = baseProductResource.listChildren();
		while (iterator.hasNext()) {

			Resource variantResource = iterator.next().getChild(CommonConstants.HP);
			if (null != variantResource) {
				ValueMap variantValueMap = variantResource.adaptTo(ValueMap.class);
				if (CommonHelper.getProductAvailabilityInRegion(
						variantValueMap.get(CommonConstants.MATERIAL_NUMBER).toString(), country, resolver,
						catalogStructureUpdateService)) {
					JsonObject sizeJsontObject = new JsonObject();
					sizeJsontObject.addProperty(CommonConstants.SIZE, variantValueMap.get(CommonConstants.SIZE_QTY)
							+ " " + variantValueMap.get(CommonConstants.SIZE_UOM));
					sizeJsontObject.addProperty(CommonConstants.CATALOG_NUMBER,
							variantValueMap.get(CommonConstants.MATERIAL_NUMBER).toString());
					
					sizeJsontObject.addProperty(CommonConstants.IS_PHANTOM, CommonHelper.isPhantomProduct(variantResource.getParent(), country));
					
					sizeJsonArray.add(sizeJsontObject);
				}
			}
		}
		return sizeJsonArray;
	}

	/**
	 * getSizeVariant.
	 *
	 * @param resolver the resolver
	 * @return variant
	 */
	public String getSizeVariant(ResourceResolver resolver, ValueMap variantValueMap) {
		String variant = null;
		variant = variantValueMap.get(CommonConstants.SIZE_QTY, String.class) + CommonConstants.SPACE
				+ variantValueMap.get(CommonConstants.SIZE_UOM, String.class);
		return variant;
	}

	/**
	 * Gets the promotions labels json.
	 *
	 * @return the promotions labels json
	 */
	public String getPromotionsLabelsJson() {
		logger.debug("Inside getPromotionsLabelsJson method");

		JsonObject promotionsJsonLabel = new JsonObject();
		promotionsJsonLabel.addProperty(CommonConstants.PROMOTION_AVAILABLE, promotionAvailableLabel);
		promotionsJsonLabel.addProperty(CommonConstants.MORE_INFORMATION, moreInformationLabel);
		promotionsJsonLabel.addProperty(CommonConstants.ICON_PATH,
				externalizerService.getFormattedUrl(promotionIconPath, resourceResolver));
		promotionsJsonLabel.addProperty(CommonConstants.IMG_ALT, promotionIconAlt);
		String urlFormate = CommonHelper.getRegion(currentPage)+"/"+CommonHelper.getCountry(currentPage)+"/"+CommonHelper.getLanguage(currentPage)+"-"+CommonHelper.getCountry(currentPage);
		if(StringUtils.isNotEmpty(promoUrl) && promoUrl.contains("na/us/en-us")) {
			promoUrl = promoUrl.replace("na/us/en-us", urlFormate);
		}
		promotionsJsonLabel.addProperty(CommonConstants.PROMO_URL,
				externalizerService.getFormattedUrl(promoUrl, resourceResolver));

		JsonObject promotionDetailsJsonLabel = new JsonObject();
		promotionDetailsJsonLabel.addProperty(CommonConstants.EXPIRES, expiresLabel);
		promotionDetailsJsonLabel.addProperty(CommonConstants.PROMOCODE, promoCodeLabel);
		promotionDetailsJsonLabel.addProperty(CommonConstants.COPY_TO_CLIPBOARD, copyToClipboardLabel);
		promotionDetailsJsonLabel.addProperty(CommonConstants.BUTTON_TEXT, buttonTextLabel);

		promotionsJsonLabel.add(CommonConstants.PROMOTION_DETAILS, promotionDetailsJsonLabel);
		return promotionsJsonLabel.toString();
	}

	/**
	 * Gets the promotions labels json.
	 *
	 * @return the promotions labels json
	 */
	public String getOtherProductsPromotionsLabelsJson() {
		return getAllPromotions();
	}

	/**
	 * Gets the promotions labels json.
	 *
	 * @return the promotions labels json
	 */
	public String getQuotePromotionsLabelsJson() {
		return getAllPromotions();
	}

	/**
	 * Gets the all promotions.
	 *
	 * @return the all promotions
	 */
	private String getAllPromotions() {
		JsonObject promotionsJsonLabel = new JsonObject();
		promotionsJsonLabel.addProperty(CommonConstants.PROMOTION_AVAILABLE, promotionAvailableLabel);
		promotionsJsonLabel.addProperty(CommonConstants.MORE_INFORMATION, moreInformationLabel);
		promotionsJsonLabel.addProperty(CommonConstants.ICON_PATH,
				externalizerService.getFormattedUrl(promotionIconPath, resourceResolver));
		promotionsJsonLabel.addProperty(CommonConstants.IMG_ALT, promotionIconAlt);
		String urlFormate = CommonHelper.getRegion(currentPage)+"/"+CommonHelper.getCountry(currentPage)+"/"+CommonHelper.getLanguage(currentPage)+"-"+CommonHelper.getCountry(currentPage);
		if(StringUtils.isNotEmpty(promoUrl) && promoUrl.contains("na/us/en-us")) {
			promoUrl = promoUrl.replace("na/us/en-us", urlFormate);
		}
		promotionsJsonLabel.addProperty(CommonConstants.PROMO_URL,
				externalizerService.getFormattedUrl(promoUrl, resourceResolver));

		JsonObject promotionDetailsJsonLabel = new JsonObject();
		promotionDetailsJsonLabel.addProperty(CommonConstants.EXPIRES, expiresLabel);
		promotionDetailsJsonLabel.addProperty(CommonConstants.PROMOCODE, promoCodeLabel);
		promotionDetailsJsonLabel.addProperty(CommonConstants.COPY_TO_CLIPBOARD, copyToClipboardLabel);
		promotionDetailsJsonLabel.addProperty(CommonConstants.BUTTON_TEXT, buttonTextLabel);
		promotionDetailsJsonLabel.addProperty(CommonConstants.NAVIGATION_LINK,
				externalizerService.getFormattedUrl(navigationLink, resourceResolver));

		promotionsJsonLabel.add(CommonConstants.PROMOTION_DETAILS, promotionDetailsJsonLabel);
		return promotionsJsonLabel.toString();
	}

	/**
	 * Gets the promotions config json.
	 *
	 * @return the promotions config json
	 */
	public String getPromotionsConfigJson() {
		logger.debug("Inside getPromotionsConfigJson method");

		JsonObject promotionJsonConfig = new JsonObject();
		JsonObject requestPayloadJsonObject1 = new JsonObject();
		JsonObject requestPayloadJsonObject2 = new JsonObject();
		JsonObject getPromotionsMsgConfig = new JsonObject();
		JsonObject getPromoDetailsConfig = new JsonObject();
		if (null != hybrisSiteId) {
			requestPayloadJsonObject1.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getPromotionsMsgEndpoint()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			requestPayloadJsonObject1.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);
		}
		requestPayloadJsonObject2.addProperty(CommonConstants.URL,
				bdbApiEndpointService.getPromotionIdDetailsServletPath());
		requestPayloadJsonObject2.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);
		String urlFormate = CommonHelper.getRegion(currentPage)+"/"+CommonHelper.getCountry(currentPage)+"/"+CommonHelper.getLanguage(currentPage)+"-"+CommonHelper.getCountry(currentPage);
		if(StringUtils.isNotEmpty(promoUrl) && promoUrl.contains("na/us/en-us")) {
			promoUrl = promoUrl.replace("na/us/en-us", urlFormate);
		}
		requestPayloadJsonObject2.addProperty(CommonConstants.PROMOURL, promoUrl);
		getPromotionsMsgConfig.add(CommonConstants.REQUEST_PAYOAD, requestPayloadJsonObject1);
		getPromoDetailsConfig.add(CommonConstants.REQUEST_PAYOAD, requestPayloadJsonObject2);

		promotionJsonConfig.add(CommonConstants.GET_PROMOTIONS_MSG, getPromotionsMsgConfig);
		promotionJsonConfig.add(CommonConstants.GET_PROMO_DETAILS, getPromoDetailsConfig);

		JsonObject configObj = new JsonObject();
		promotionsJsonConfig = configObj.toString();
		return promotionJsonConfig.toString();
	}

	/**
	 * Gets the commerce box json labels.
	 *
	 * @return commerceBoxJsonLabels
	 */
	public String getCommerceBoxJsonLabels() {
		return commerceBoxJsonLabels;
	}

	/**
	 * Gets the commerce box json configs.
	 *
	 * @return commerceBoxJsonConfigs
	 */
	public String getCommerceBoxJsonConfigs() {
		return commerceBoxJsonConfigs;
	}

	/**
	 * Gets the cookies json.
	 *
	 * @return the cookies json
	 */
	public String getCookiesJson() {
		return cookiesJson;
	}

	/**
	 * Gets the adds the to cart config json.
	 *
	 * @return the adds the to cart config json
	 */
	public String getAddToCartConfigJson() {
		return addToCartConfigJson;
	}

	/**
	 * Gets the size config json.
	 *
	 * @return the size config json
	 */
	public String getSizeConfigJson() {
		return sizeConfigJson;
	}

	/**
	 * Gets the promotions json labels.
	 *
	 * @return the promotions json labels
	 */
	public String getPromotionsJsonLabels() {
		return promotionsJsonLabels;
	}

	/**
	 * Gets the promotions json config.
	 *
	 * @return the promotions json config
	 */
	public String getPromotionsJsonConfig() {
		return promotionsJsonConfig;
	}

	/**
	 * Gets the adds the to quote config json.
	 *
	 * @return the adds the to quote config json
	 */
	public String getAddToQuoteConfigJson() {
		return addToQuoteConfigJson;
	}

}