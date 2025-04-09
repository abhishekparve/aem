package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.HeaderCartFetchModel;
import com.bdb.aem.core.models.TransalationUrlsListModel;
import com.bdb.aem.core.pojo.HeaderCartConfig;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The Class HeaderCartFetchModelImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = {
		HeaderCartFetchModel.class }, resourceType = {
				HeaderCartFetchModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderCartFetchModelImpl implements HeaderCartFetchModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/globalheader/v1/globalheader";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(HeaderCartFetchModelImpl.class);

	/** The current page. */
	@Inject
	private Page currentPage;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The resource resolver. */
	@SlingObject
	ResourceResolver resourceResolver;

	/** The url cart. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String urlCart;

	/** The altcart. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String altcart;

	/** The cart config. */
	private String cartConfig;

	/** The hybris site id. */
	private String hybrisSiteId;
	/**
	 * Path of the Mini Cart Icon
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String miniCartIcon;
	/**
	 * Alt Text for Cart Icon
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String altIcon;
	/**
	 * Item in Cart Label
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String itemsInCart;
	/**
	 * View In Cart Label
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String viewCart;
	/**
	 * View More Label
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String viewMore;
	/**
	 * Empty Cart Text
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emptyCartText;
	/**
	 * Target for the View More Link
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String viewMoreTarget;
	/**
	 * Continue Shopping Modal Title
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String continueShoppingModalTitle;
	/**
	 * Continue Shopping Modal Description
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String continueShoppingModalDescription;
	/**
	 * Proceed To Checkout Btn Label
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String proceedToCheckoutBtnLabel;
	/**
	 * Close Modal Btn Label
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String closeModalBtnLabel;
	/**
	 * Expired Quote Modal Title
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String expiredQuoteModalTitle;
	/**
	 * Expired Quote Modal Description
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String expiredQuoteModalDescription;
	/**
	 * Clear Cart Btn Label
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String clearCartBtnLabel;
	/**
	 * Empty Cart Icon Value
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emptyCartIcon;
	/**
	 * Empty Cart Alt Text
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emptyCartAlt;
	/**
	 * Items In Quote Label
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String itemsInQuote;
	/**
	 * Quick Quote Label
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quickQuote;
	/**
	 * View Quote Label
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String viewQuote;
	/**
	 * Empty Quote Description
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emptyQuoteText;
	/**
	 * Empty Cart Anonymous User Description
	 */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emptyCartAnonymousUserDesc;

	/** * Page Properties to fetch other Properties from Page */
	@Inject
	private InheritanceValueMap pageProperties;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String modalTitle;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String modalDescription;

	/** The cell types multi field. */
	@ChildResource
	private List<TransalationUrlsListModel> translationUrlsList;

	/** Label for MiniCarts in Json */
	private String miniCartLabels;
	/**
	 * API End Point of MiniCartCount from OSGI Config
	 */
	private String cartCountConfig;
	/**
	 * API End Point of CartEntries from OSGI
	 */
	private String cartEntriesConfig;
	/**
	 * Validate My Cart
	 */
	private String validateMyCartConfig;
	/**
	 * Clear Cart
	 */
	private String clearCartConfig;
	/**
	 * MiniQuote Lables
	 */
	private String miniQuoteLabels;

	/** The translation Urls. */
	private String translationUrls;
	/**
	 * Mini Quote Config
	 */

	private String miniQuoteEntriesConfig;
	private String miniQuoteCountConfig;

	/**
	 * Fetches JsonObject for Mini Cart to work
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Header Cart Init Method");
		if (bdbApiEndpointService != null) {

			hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
			ExcludeUtil excludeUtilObject = new ExcludeUtil();
			setCartConfig(excludeUtilObject);
			addMiniCartConfig(excludeUtilObject);
			addMiniQuoteConfig(excludeUtilObject);

		}
		miniCartLabels = addMiniCartLabelJson();
		miniQuoteLabels = addMiniQuoteLabels();
		translationUrls = addTransalationUrlsLabelJson();
	}

	/**
	 * Sets the cart config.
	 *
	 * @param excludeUtilObject the new cart config
	 */
	private void setCartConfig(ExcludeUtil excludeUtilObject) {
		JsonElement getAddCartPayload;
		String getCartEndpoint = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.cartConfigEndpoint(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Payload getCartPayload = new Payload(getCartEndpoint, HttpConstants.METHOD_GET);
		getAddCartPayload = json.toJsonTree(getCartPayload);
		String formattedUrlCart = externalizerService.getFormattedUrl(urlCart, resourceResolver);
		HeaderCartConfig headerCartConfig = new HeaderCartConfig(getAddCartPayload, formattedUrlCart, altcart);
		cartConfig = json.toJson(headerCartConfig);
	}

	/**
	 * Update the Mini Cart Config with Count and Entries End Point
	 * 
	 * @param excludeUtilObject
	 */
	private void addMiniCartConfig(ExcludeUtil excludeUtilObject) {
		String getCartCountEndPoint = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.miniCartCountConfigEndpoint(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Payload getCartCountPayload = new Payload(getCartCountEndPoint, HttpConstants.METHOD_GET);
		cartCountConfig = gson.toJson(getCartCountPayload);
		String getCartEntriesEndPoint = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.miniCartEntriesConfigEndpoint(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Gson entryGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Payload getCartEntryPayload = new Payload(getCartEntriesEndPoint, HttpConstants.METHOD_GET);
		cartEntriesConfig = entryGson.toJson(getCartEntryPayload);

		String validateMyCartEndPoint = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain()
						+ bdbApiEndpointService.getValidateMyCart(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Gson validateGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Payload validateMyCartPayload = new Payload(validateMyCartEndPoint, HttpConstants.METHOD_PUT);
		validateMyCartConfig = validateGson.toJson(validateMyCartPayload);

		String clearCartEndPoint = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain()
						+ bdbApiEndpointService.getClearCart(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Gson clearCartGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Payload clearCartPayload = new Payload(clearCartEndPoint, HttpConstants.METHOD_DELETE);
		clearCartConfig = clearCartGson.toJson(clearCartPayload);

	}

	/**
	 *
	 * @param excludeUtilObject
	 * @return MiniQuoteConfig
	 */
	private void addMiniQuoteConfig(ExcludeUtil excludeUtilObject) {
		String getMiniQuoteCount = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.miniCartCountConfigEndpoint(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Payload getMiniQuoteCountPayload = new Payload(getMiniQuoteCount, HttpConstants.METHOD_GET);
		miniQuoteCountConfig = gson.toJson(getMiniQuoteCountPayload);
		String getMiniQuoteEntries = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.miniCartEntriesConfigEndpoint(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Gson entryGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Payload getMiniQuoteEntriesPayload = new Payload(getMiniQuoteEntries, HttpConstants.METHOD_GET);
		miniQuoteEntriesConfig = entryGson.toJson(getMiniQuoteEntriesPayload);

	}

	/**
	 *
	 * @return Updating with MiniCartLabelJson
	 */
	private String addMiniCartLabelJson() {
		JsonObject labels = new JsonObject();
		labels.addProperty(CommonConstants.ITEMS_IN_CART, itemsInCart);
		labels.addProperty(CommonConstants.SUB_TOTAL, pageProperties.getInherited("subTotal", StringUtils.EMPTY));
		labels.addProperty(CommonConstants.QUICK_ADD_LABEL,
				pageProperties.getInherited("quickAddCta", StringUtils.EMPTY));
		labels.addProperty(CommonConstants.VIEW_CART, viewCart);
		labels.addProperty(CommonConstants.VIEW_MORE, viewMore);
		labels.addProperty(CommonConstants.VIEW_MORE_TARGET, viewMoreTarget);
		labels.addProperty(CommonConstants.CONTINUE_SHOPPING_MODAL_TITLE, continueShoppingModalTitle);
		labels.addProperty(CommonConstants.CONTINUE_SHOPPING_MODAL_DESCRIPTION, continueShoppingModalDescription);
		labels.addProperty(CommonConstants.PROCEED_TO_CHECKOUT_BTN_LABEL, proceedToCheckoutBtnLabel);
		labels.addProperty(CommonConstants.CLOSE_MODAL_BTN_LABEL, closeModalBtnLabel);
		labels.addProperty(CommonConstants.EXPIRED_QUOTE_MODAL_TITLE, expiredQuoteModalTitle);
		labels.addProperty(CommonConstants.EXPIRED_QUOTE_MODAL_DESCRIPTION, expiredQuoteModalDescription);
		labels.addProperty(CommonConstants.CLEAR_CART_BTN_LABEL, clearCartBtnLabel);
		labels.addProperty(CommonConstants.QUANTITY_MINI_CART,
				pageProperties.getInherited("cartQuantity", StringUtils.EMPTY));
		labels.addProperty(CommonConstants.PRICE_PER_UNIT_MINI_CART,
				pageProperties.getInherited("priceUnit", StringUtils.EMPTY));
		labels.addProperty(CommonConstants.LIST_PRICE_MINI_CART,
				pageProperties.getInherited("cartListPrice", StringUtils.EMPTY));
		labels.addProperty(CommonConstants.URL_CART, miniCartIcon);
		labels.addProperty(CommonConstants.URL_ALT, altIcon);
		labels.addProperty(CommonConstants.EMPTY_CART_DESC,emptyCartText);
		labels.addProperty(CommonConstants.EMPTY_CART_ICON,emptyCartIcon);
		labels.addProperty(CommonConstants.EMPTY_CART_ALT_TEXT,emptyCartAlt);
		labels.addProperty(CommonConstants.EMPTY_CART_ANONYMOUS_USER,emptyCartAnonymousUserDesc);
		return labels.toString();

	}

	/**
	 *
	 * @return MiniQuoteLabels
	 */
	private String addMiniQuoteLabels() {
		JsonObject quoteLabel = new JsonObject();
		quoteLabel.addProperty(CommonConstants.IS_QUOTE,
				pageProperties.getInherited("enableAddToQuoteCheckBox", Boolean.FALSE));
		quoteLabel.addProperty(CommonConstants.URL_QUOTE, miniCartIcon);
		quoteLabel.addProperty(CommonConstants.ALT_QUOTE, altIcon);
		quoteLabel.addProperty(CommonConstants.ITEMS_IN_QUOTE, itemsInQuote);
		quoteLabel.addProperty(CommonConstants.SUB_TOTAL, pageProperties.getInherited("subTotal", StringUtils.EMPTY));
		quoteLabel.addProperty(CommonConstants.QUICK_QUOTE, quickQuote);
		quoteLabel.addProperty(CommonConstants.VIEW_QUOTE, viewQuote);
		quoteLabel.addProperty(CommonConstants.VIEW_MORE, viewMore);
		quoteLabel.addProperty(CommonConstants.QUANTITY_MINI_CART, pageProperties.getInherited("cartQuantity", StringUtils.EMPTY));
		quoteLabel.addProperty(CommonConstants.PRICE_PER_UNIT_MINI_CART, pageProperties.getInherited("priceUnit", StringUtils.EMPTY));
		quoteLabel.addProperty(CommonConstants.LIST_PRICE_MINI_CART, pageProperties.getInherited("cartListPrice", StringUtils.EMPTY));
		quoteLabel.addProperty(CommonConstants.EMPTY_QUOTE_DESC,emptyQuoteText);
		quoteLabel.addProperty(CommonConstants.EMPTY_QUOTE_ICON,emptyCartIcon);
		quoteLabel.addProperty(CommonConstants.EMPTY_QUOTE_ICON_ALT,emptyCartAlt);
		quoteLabel.addProperty(CommonConstants.EMPTY_CART_ANONYMOUS_USER,emptyCartAnonymousUserDesc);
		return quoteLabel.toString();
	}

	/**
	 *
	 * @return Updating TranslationUrlsJson
	 */
	private String addTransalationUrlsLabelJson() {
		JsonArray urlList = new JsonArray();
		if (translationUrlsList != null) {
			for (TransalationUrlsListModel eachTranslationObj : translationUrlsList) {
				urlList.add(
						externalizerService.getFormattedUrl(eachTranslationObj.getTranslationUrl(), resourceResolver));
			}
		}

		return urlList.toString();

	}

	/**
	 * Gets the cart config.
	 *
	 * @return the cart config
	 */
	@Override
	public String getCartConfig() {
		return cartConfig;
	}

	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	public String getHybrisSiteId() {
		return hybrisSiteId;
	}

	/**
	 *
	 * @return MiniCartLabel String format
	 */
	public String getMiniCartLabels() {
		return miniCartLabels;
	}

	/**
	 *
	 * @return cartCountConfig
	 */
	public String getCartCountConfig() {
		return cartCountConfig;
	}

	/**
	 *
	 * @return cartEntriesConfig
	 */
	public String getCartEntriesConfig() {
		return cartEntriesConfig;
	}

	/**
	 * @return the validateMyCartConfig
	 */
	public String getValidateMyCartConfig() {
		return validateMyCartConfig;
	}

	/**
	 * @return the clearCartConfig
	 */
	public String getClearCartConfig() {
		return clearCartConfig;
	}

	/**
	 *
	 * @return MiniQuoteLabels
	 */
	public String getMiniQuoteLabels() {
		return miniQuoteLabels;
	}

	/**
	 *
	 * @return miniQuoteEntriesConfig
	 */
	public String getMiniQuoteEntriesConfig() {
		return miniQuoteEntriesConfig;
	}

	/**
	 *
	 * @return miniQuoteCountConfig
	 */
	public String getMiniQuoteCountConfig() {
		return miniQuoteCountConfig;
	}

	/**
	 * @return the translationUrls
	 */
	public String getTranslationUrls() {
		return translationUrls;
	}

	/**
	 * @return the modalTitle
	 */
	public String getModalTitle() {
		return modalTitle;
	}

	/**
	 * @return the modalDescription
	 */
	public String getModalDescription() {
		return modalDescription;
	}

}