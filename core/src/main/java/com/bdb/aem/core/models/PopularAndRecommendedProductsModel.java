package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.MonetateEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The Class PopularAndRecommendedProductsModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PopularAndRecommendedProductsModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(PopularAndRecommendedProductsModel.class);

	/** The category select. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String categorySelect;

	/** The category title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String categoryTitle;

	/** The catalog no. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String catalogNo;

	/** The also known as. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String alsoKnownAs;
	
	/** The InquireLabel. */
	@Inject
	@Via("resource")
	@Default(values = "Inquire")
	private String inquireLabel;
	
	/** The CloneLabel. */
	@Inject
	@Via("resource")
	@Default(values = "Clone")
	private String clone;
	
	/** The SizeLabel. */
	@Inject
	@Via("resource")
	@Default(values = "Size")
	private String size;
	
	/** The Add To Shopping List. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String addToShoppingList;
	
	/** The Add To Quote List. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String addToQuoteList;
	
	/** The Compare label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String compareLabel;

	/** The monetate endpoint service. */
	@Inject
	MonetateEndpointService monetateEndpointService;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The product label json. */
	private String productLabelJson;

	/** The config json. */
	private String recommendedConfigJson;

	/** The popular products labels json. */
	private String popularProductsLabelsJson;

	/** The also viewed config. */
	private String alsoViewedConfig;

	/** The also viewed config desktop. */
	private String alsoViewedConfigDesktop;

	/** The popular products labels json. */
	private String alsoViewedLables;
	
	private String hybrisSiteId = StringUtils.EMPTY;

	/**
	 * Inits the.
	 *
	 * @throws LoginException the login exception
	 */
	@PostConstruct
	protected void init() throws LoginException {
		logger.debug("Inside init of PopularAndRecommendedProductsModel");
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);

		recommendedConfigJson = getRecommendedProductsConfigJson();

		productLabelJson = getRecommendedLabelsJson();

		popularProductsLabelsJson = getPopularLabelsJson();

		alsoViewedConfig = getAlsoViewedProductsConfig();

		alsoViewedConfigDesktop = getAlsoViewedProductsConfigDesktop();

		alsoViewedLables = getAlsoViewedProductsLables();
	}

	/**
	 * Gets the recommended labels json.
	 *
	 * @return the recommended labels json
	 */
	public String getRecommendedLabelsJson() {
		logger.debug("Inside getRecommendedLabelsJson");

		JsonObject productLabelJsonObj = new JsonObject();
		productLabelJsonObj.addProperty(CommonConstants.YOUR_PRICE,
				CommonHelper.getLabel(CommonConstants.YOUR_PRICE, currentPage));
		productLabelJsonObj.addProperty(CommonConstants.LIST_PRICE,
				CommonHelper.getLabel(CommonConstants.LIST_PRICE, currentPage));
		productLabelJsonObj.addProperty(CommonConstants.SIGN_IN,
				CommonHelper.getLabel(CommonConstants.SIGN_IN, currentPage));
		productLabelJsonObj.addProperty(CommonConstants.TITLE, categoryTitle);
		productLabelJsonObj.addProperty(CommonConstants.ADD_TO_CART,
				CommonHelper.getLabel(CommonConstants.ADD_TO_CART, currentPage));
		productLabelJsonObj.addProperty(CommonConstants.CART_ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.CART_ALT_TEXT, currentPage));
		productLabelJsonObj.addProperty(CommonConstants.PREV_ICON_ARIA_LABEL,
				CommonHelper.getLabel(CommonConstants.PREVIOUS_SLIDE_BUTTON_LABEL, currentPage));
		productLabelJsonObj.addProperty(CommonConstants.NEXT_ICON_ARIA_LABEL,
				CommonHelper.getLabel(CommonConstants.NEXT_SLIDE_BUTTON_LABEL, currentPage));
		productLabelJsonObj.addProperty(CommonConstants.INQUIRE, inquireLabel);
		productLabelJsonObj.addProperty(CommonConstants.ADD_TO_SHOPPING_LIST, addToShoppingList);
		productLabelJsonObj.addProperty(CommonConstants.ADD_TO_QUOTE_LIST, addToQuoteList);
		productLabelJsonObj.addProperty(CommonConstants.COMPARE_LABEL, compareLabel);
		productLabelJsonObj.addProperty(CommonConstants.CLONE_L, clone);
		productLabelJsonObj.addProperty(CommonConstants.SIZE_L, size);

		return productLabelJsonObj.toString();
	}

	/**
	 * Gets the popular labels json.
	 *
	 * @return the popular labels json
	 */

	public String getAlsoViewedProductsLables() {
		logger.debug("Inside getPopularLabelsJson");

		JsonObject alsoViewedLabelJson = new JsonObject();
		alsoViewedLabelJson.addProperty(CommonConstants.HEADING, categoryTitle);
		alsoViewedLabelJson.addProperty(CommonConstants.PRICE_PER_UNIT,
				CommonHelper.getLabel(CommonConstants.PRICE_PER_UNIT, currentPage));
		alsoViewedLabelJson.addProperty(CommonConstants.CATALOG_NO, catalogNo);
		alsoViewedLabelJson.addProperty(CommonConstants.ALSO_KNOWN_AS, alsoKnownAs);

		return alsoViewedLabelJson.toString();
	}

	/**
	 * Gets the popular labels json.
	 *
	 * @return the popular labels json
	 */
	public String getPopularLabelsJson() {
		logger.debug("Inside getPopularLabelsJson");

		JsonObject popularLabelsJson = new JsonObject();
		popularLabelsJson.addProperty(CommonConstants.TITLE, categoryTitle);
		popularLabelsJson.addProperty(CommonConstants.PRICE_PER_UNIT,
				CommonHelper.getLabel(CommonConstants.PRICE_PER_UNIT, currentPage));
		return popularLabelsJson.toString();
	}

	/**
	 * Gets the popular and recommended config json.
	 *
	 * @return the popular and recommended config json
	 */
	public String getAlsoViewedProductsConfig() {
		logger.debug("Inside getPopularAndRecommendedConfigJson");

		JsonObject monetateProductDetails = new JsonObject();
		JsonObject pricingDetails = new JsonObject();
		pricingDetails.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain() +
				StringUtils.replace(bdbApiEndpointService.getPriceDataEndPoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId));
		pricingDetails.addProperty(CommonConstants.METHOD, CommonConstants.POST);
		monetateProductDetails.addProperty(CommonConstants.IS_MOBILE, true);
		monetateProductDetails.add(CommonConstants.PRICE_CALL_CONFIG, pricingDetails);

		return monetateProductDetails.toString();
	}

	/**
	 * Gets the also viewed products config desktop.
	 *
	 * @return the also viewed products config desktop
	 */
	public String getAlsoViewedProductsConfigDesktop() {
		logger.debug("Inside getPopularAndRecommendedConfigJson");

		JsonObject monetateProductDetails = new JsonObject();
		JsonObject pricingDetails = new JsonObject();

		pricingDetails.addProperty(CommonConstants.URL,	bdbApiEndpointService.getBDBHybrisDomain() + 
				StringUtils.replace(bdbApiEndpointService.getPriceDataEndPoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId));
		pricingDetails.addProperty(CommonConstants.METHOD, CommonConstants.POST);
		monetateProductDetails.addProperty(CommonConstants.IS_MOBILE, false);
		monetateProductDetails.add(CommonConstants.PRICE_CALL_CONFIG, pricingDetails);

		return monetateProductDetails.toString();
	}

	/**
	 * Gets the recommended products config json.
	 *
	 * @return the recommended products config json
	 */
	public String getRecommendedProductsConfigJson() {
		logger.debug("Inside getPopularAndRecommendedConfigJson");

		JsonObject monetateProductDetails = new JsonObject();
		JsonObject pricingDetails = new JsonObject();

		pricingDetails.addProperty(CommonConstants.URL,	bdbApiEndpointService.getBDBHybrisDomain() +
				StringUtils.replace(bdbApiEndpointService.getPriceDataEndPoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId));
		pricingDetails.addProperty(CommonConstants.METHOD, CommonConstants.POST);
		monetateProductDetails.add(CommonConstants.PRICE_CALL_CONFIG, pricingDetails);

		return monetateProductDetails.toString();

	}

	
	public String getCommonConfig() {
		return monetateEndpointService.getMonetateProductsEndpoint();
	}

	/**
	 * Gets the product label json.
	 *
	 * @return the product label json
	 */
	public String getProductLabelJson() {
		return productLabelJson;
	}

	/**
	 * Gets the category select.
	 *
	 * @return the category select
	 */
	public String getCategorySelect() {
		return categorySelect;
	}

	/**
	 * Gets the category title.
	 *
	 * @return the category title
	 */
	public String getCategoryTitle() {
		return categoryTitle;
	}

	/**
	 * Gets the popular products labels json.
	 *
	 * @return the popular products labels json
	 */
	public String getPopularProductsLabelsJson() {
		return popularProductsLabelsJson;
	}

	/**
	 * Gets the recommended config json.
	 *
	 * @return the recommended config json
	 */
	public String getRecommendedConfigJson() {
		return recommendedConfigJson;
	}

	/**
	 * Gets the also viewed config.
	 *
	 * @return the also viewed config
	 */
	public String getAlsoViewedConfig() {
		return alsoViewedConfig;
	}

	/**
	 * Gets the also viewed lables.
	 *
	 * @return the also viewed lables
	 */
	public String getAlsoViewedLables() {
		return alsoViewedLables;
	}

	/**
	 * Gets the also viewed config desktop.
	 *
	 * @return the also viewed config desktop
	 */
	public String getAlsoViewedConfigDesktop() {
		return alsoViewedConfigDesktop;
	}

	/**
	 * Gets the catalog no.
	 *
	 * @return the catalog no
	 */
	public String getCatalogNo() {
		return catalogNo;
	}

	/**
	 * Gets the also known as.
	 *
	 * @return the also known as
	 */
	public String getAlsoKnownAs() {
		return alsoKnownAs;
	}

	/**
	 * @return the addToShoppingList
	 */
	public String getAddToShoppingList() {
		return addToShoppingList;
	}

	/**
	 * @return the addToQuoteList
	 */
	public String getAddToQuoteList() {
		return addToQuoteList;
	}

	/**
	 * @return the compareLabel
	 */
	public String getCompareLabel() {
		return compareLabel;
	}

}
