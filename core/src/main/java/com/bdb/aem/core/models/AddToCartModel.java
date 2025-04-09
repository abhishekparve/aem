package com.bdb.aem.core.models;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

/**
 * The Class AddToCartModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AddToCartModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(AddToCartModel.class);

	/** The my cart. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String myCart;

	/** The items. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String items;

	/** The item. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String item;

	/** The cart id. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String cartId;

	/** The continue shopping. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String continueShopping;

	/** The continue shopping URL. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String continueShoppingUrl;

	/** The products. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String products;
	
	/** The nzPricingMessage. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String nzPricingMessage;

	/** The est delivery date. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String estDeliveryDate;

	/** The quantity label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quantityLabel;

	/** The save L ater quantity label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String saveLAterQuantityLabel;

	/** The list price label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String listPriceLabel;

	/** The total price label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String totalPriceLabel;

	/** The add to cart label. */
	@Inject
	@Via("resource")
	@Default(values = "Add to Cart")
	private String addToCart;
	
	/** The add to shopping list. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String addToShoppingList;

	/** The savefor later. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String saveforLater;

	/** The request same lot number. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String requestSameLotNumber;

	/** The quote reference. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quoteReference;
	
	/** The get quote. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String getQuote;

	/** The quote to cart. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quoteToCart;

	/** The quote reference aria label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quoteReferenceAriaLabel;

	/** The request same lot numbers. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String requestSameLotNumbers;

	/** The heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String heading;

	/** The description. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String description;

	/** The alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String altText;

	/** The price confirmation PDF. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String priceConfirmationPDF;

	/** The price confirmation PDF CTA. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String myCartPrintPage;

	/** The Not Signed In Heading message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String notSignedInHeading;
	
	/** The Not Signed In Description message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String notSignedInDescription;
	
	/** The max quantity alt text message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emptyCartIcon;

	/** The promocode input message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String promoCodeInputMsg;

	/**  The Applied Successfully. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String appliedSuccessfully;

	/**  Invalid code. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String invalidCode;

	/**  The Promotions. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String promotions;

	/**  Promo code apply CTA. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String promoCodeApplyCTA;

	/** The billToLabel. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String billToLabel;

	/** The billToNumberLabel. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String billToNumberLabel;

	/** The disclaimer heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String disclaimerHeading;

	/** The disclaimer description. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String disclaimerDescription;

	/**  Tool - Icon and Alt text multifield. */
	@Inject
	@Via("resource")
	private List<BDBStandardToolModel> tools;

	/**  MyQuote - myQuote. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String myQuote;

	/**  MyQuote - QuoteId. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quoteId;

	/**  MyQuote - addToQuoteList. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String addToQuoteList;

	/**  MyQuote - addToQuoteDlg. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String addToQuoteDlg;

	/**  MyQuote - surchargeMessageAltText. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String surchargeMessageAltText;

	/**  MyQuote - upgradeAccountHeading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String upgradeAccountHeading;

	/**  MyQuote - closeIcon. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String closeIcon;

	/**  MyQuote - surchargeMessage. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String surchargeMessage;

	/**  MyQuote - upgradeAccountDesc. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String upgradeAccountDesc;

	/**  MyQuote - upgradeAccountCancelLink. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String upgradeAccountCancelLink;

	/**  MyQuote - upgradeAccountDashboardButton. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String upgradeAccountDashboardButton;

	/**  MyQuote - summaryLabelsTitle. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String summaryLabelsTitle;

	/**  MyQuote - summaryLabelsGetQuoteCTA. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String summaryLabelsGetQuoteCTA;

	/**  MyQuote - summaryLabelsInstHolder. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String summaryLabelsInstHolder;

	/**  MyQuote - summaryLabelsLocalLabel. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String summaryLabelsLocalLabel;

	/**  MyQuote - summaryLabelsDirectImportLabel. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String summaryLabelsDirectImportLabel;

	/** The quick quote toast msg. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quickQuoteToastMsg;

	/**  MyQuote - quickAddQuote. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quickAddQuote;

	/** The Email heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emailHeading;

	/** The Email message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emailMessage;

	/** The Email Placeholder. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emailPlaceholder;

	/** The Email cancel. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emailCancel;

	/** The Email send. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emailSend;

	/** The Email Close Alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emailAltText;

	/** The Email Validation Message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emailValidationMsg;

	/** The Email Validation Error Message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emailValidationErrorMsg;

	/** The Max Email IDs Error Message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String maxEmailIdsErrorMsg;
	
	/** The RUO Error Message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String ruoMessage;
	
	/** The RUO Error Message Alt Text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String ruoMessageAltText;
	
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
	
	/** The InquireLabel. */
	@Inject
	@Via("resource")
	@Default(values = "Inquire")
	private String inquireLabel;
	
	/** The Dangerous Goods. */
	@Inject
	@Via("resource")
	@Default(values = "Special handling message for sheath fluid, ice pack & dangerous goods goes here")
	private String dangerousGoodsMsg;
	
	/** The dangerousGoodInfoAltText. */
	@Inject
	@Via("resource")
	@Default(values = "Alt Text")
	private String dangerousGoodInfoAltText;

	/** The clear Cart Label. */
	@Inject
	@Via("resource")
	@Default(values = "clear Cart")
	private String clearCartLabel;
	
	/** The ClearCartHeading. */
	@Inject
	@Via("resource")
	@Default(values = "Alert")
	private String ClearCartHeading;
	
	/** The InquireLabel. */
	@Inject
	@Via("resource")
	@Default(values = "Are you sure, do you want to remove all the products from the cart ?")
	private String clearCartDescription;
	
	/** The cancelLabel. */
	@Inject
	@Via("resource")
	@Default(values = "Cancel")
	private String cancelLabel;
	
	/** The Quantity Info Message. */
	@Inject
	@Via("resource")
	@Default(values = "A minimum quantity of {{minPurchasableQuantity}} is required for this product.")
	private String quantityInfoMessage;
	
	/** The cancelLabel. */
	@Inject
	@Via("resource")
	@Default(values = "Info Icon")
	private String quantityAltText;

	/** The cancelLabel. */
	@Inject
	@Via("resource")
	@Default(values = "There are issues with one or more products and will not be proceed to checkout.")
	private String invalidProductsError;
	
	/** The quoteReferenceNotAllowed. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String quoteReferenceNotAllowed;
	
	/** The Reset Cart Heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String resetCartHeading;
	
	/** The Reset Cart Description. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String resetCartDescription;
	
	/** The Reset Cart List Heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String resetCartListHeading;
	
	/** Success Modal Heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String successModalHeading;
	
	/** Success Modal Description. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String successModalDescription;
	
	/** Success Modal Description. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String successModalListHeading;
	
	/** Clear Cart CTA Label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String clearCartCTALabel;
	
	/** Save All For Later CTA Label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String saveAllForLaterCTALabel;
	
	/** Apply Quote CTA Label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String applyQuoteCTALabel;

	/** Submit CTA Label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String submitCTALabel;

  /** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The my cart labels. */
	private String myCartLabels;

	/** The my cart config. */
	private String myCartConfig;

	/** The my cart print PDF labels. */
	@Getter
	private String myCartPrintPDFLabels;
	
	/** The my quote Reference Modal. */
	@Getter
	private String quoteReferenceModal;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/**  The MyQuote - MyQuote Label. */
	private String myQuoteLabels;

	/**  The MyQuote - enableAddToQuote Label. */
	private String enableAddToQuoteCheckBox;
	
	/** The MyQuote - myQuoteConfig Label */
	private String myQuoteConfig;

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("AddToCartModel - Init method started");
			enableAddToQuoteCheckBox = CommonHelper.getLabel(CommonConstants.ENABLE_ADD_TO_QUOTE, currentPage);
			if (enableAddToQuoteCheckBox == null) {
				enableAddToQuoteCheckBox = CommonConstants.FALSE;
			}
			if (enableAddToQuoteCheckBox != null && enableAddToQuoteCheckBox.equals(CommonConstants.TRUE)) {
				myQuoteLabels = createMyQuoteLabels();
		        myQuoteConfig = createMyQuoteConfig();

			}
			myCartLabels = myCartLabels();
			myCartConfig = getMyCartJsonConfig().toString();
			myCartPrintPDFLabels = printPDFLabelsJson(tools);
	}

	/**
	 * Creates the my quote labels.
	 *
	 * @return the string
	 * @throws LoginException the login exception
	 * @return myQuote config 
	 */
	private String createMyQuoteConfig() {
		JsonObject myQuoteConfig = getMyCartJsonConfig();
		JsonObject clearCartJson = new JsonObject();
		JsonObject getQuote = new JsonObject();
		String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		getQuote.addProperty(CommonConstants.URL,
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getQuoteConfig()
						.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
		getQuote.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		
		clearCartJson.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getClearCart().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
		clearCartJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_DELETE);

		
		myQuoteConfig.add(CommonConstants.GET_QUOTE, getQuote);
		myQuoteConfig.add("clearCart", clearCartJson);
		
		return myQuoteConfig.toString();
	}

	/**
	 * @return
	 * @throws LoginException
	 */
	public String createMyQuoteLabels() {
		JsonObject myQuoteJsonLabel = new JsonObject();
		JsonObject emptyQuote = new JsonObject();
		JsonObject maxQuantityError = new JsonObject();
		JsonObject quickAddProdNotAvailableError = new JsonObject();
		JsonObject quickAddInvalidProdError = new JsonObject();
		JsonObject surchargeJson = new JsonObject();
		JsonObject upgradeJson = new JsonObject();
		JsonObject nonPurchasableJson = new JsonObject();
		JsonObject headerLevelMessagesJson = new JsonObject();
		JsonObject summaryLabelJson = new JsonObject();
		JsonObject clearCartJsonLabel = new JsonObject();
		JsonObject minQuantityJsonLabel = new JsonObject();

		myQuoteJsonLabel.addProperty(CommonConstants.IS_QUOTE, Boolean.TRUE);
		myQuoteJsonLabel.addProperty(CommonConstants.MY_QUOTE, myQuote);
		myQuoteJsonLabel.addProperty(CommonConstants.ITEMS, items);
		myQuoteJsonLabel.addProperty(CommonConstants.ITEM, item);
		myQuoteJsonLabel.addProperty(CommonConstants.QUOTE_ID, quoteId);
		myQuoteJsonLabel.addProperty(CommonConstants.QUICK_ADD, quickAddQuote);
		myQuoteJsonLabel.addProperty(CommonConstants.CONTINUE_SHOPPING, continueShopping);
		myQuoteJsonLabel.addProperty(CommonConstants.CONTINUE_SHOPPING_URL,
				externalizerService.getFormattedUrl(continueShoppingUrl, resourceResolver));
		myQuoteJsonLabel.addProperty(CommonConstants.QUICK_QUOTE,quickAddQuote);
		myQuoteJsonLabel.addProperty(CommonConstants.PRODUCTS, products);
		myQuoteJsonLabel.addProperty(CommonConstants.NZ_PRICING_MESSAGE, nzPricingMessage);
		myQuoteJsonLabel.addProperty(CommonConstants.QUANTITY,
				CommonHelper.getLabel(CommonConstants.CART_QUANTITY_VALUE, currentPage));
		myQuoteJsonLabel.addProperty(CommonConstants.SAVE_LATER_QUANTITY_LABEL, saveLAterQuantityLabel);
		myQuoteJsonLabel.addProperty(CommonConstants.ADD_TO_QUOTE_LIST, addToQuoteList);
		myQuoteJsonLabel.addProperty(CommonConstants.SAVE_FOR_LATER, saveforLater);
		myQuoteJsonLabel.addProperty(CommonConstants.REMOVE,
				CommonHelper.getLabel(CommonConstants.REMOVE, currentPage));
		myQuoteJsonLabel.addProperty(CommonConstants.ADD_TO_QUOTE, addToQuoteDlg);
		myQuoteJsonLabel.addProperty(CommonConstants.CLOSE_ICON, closeIcon);
		myQuoteJsonLabel.addProperty(CommonConstants.ON, CommonHelper.getLabel(CommonConstants.ON, currentPage));
		myQuoteJsonLabel.addProperty(CommonConstants.TOTAL_PRICE, totalPriceLabel);
		emptyQuote.addProperty(CommonConstants.HEADING, heading);
		emptyQuote.addProperty(CommonConstants.DESCRIPTION, description);
		emptyQuote.addProperty(CommonConstants.ALT_TEXT, altText);
		emptyQuote.addProperty(CommonConstants.EMPTY_QUOTE_ICON, emptyCartIcon);
		emptyQuote.addProperty(CommonConstants.NOT_SIGNED_IN_HEADING, notSignedInHeading);
		emptyQuote.addProperty(CommonConstants.NOT_SIGNED_IN_DESC, notSignedInDescription);

		myQuoteJsonLabel.add(CommonConstants.EMPTY_QUOTE, emptyQuote);

		myQuoteJsonLabel.addProperty(CommonConstants.REMOVE_ITEMS_FROM_QUOTE_MSG, CommonConstants.PRODUCT_CODE
				+ CommonHelper.getLabel(CommonConstants.REMOVE_ITEMS_FROM_QUOTE_MSG, currentPage));
		myQuoteJsonLabel.addProperty(CommonConstants.ADD_PRODUCT_TO_SFL_QUOTE_MSG, CommonConstants.PRODUCT_CODE
				+ CommonHelper.getLabel(CommonConstants.ADD_PRODUCT_TO_SFL_QUOTE_MSG, currentPage));
		myQuoteJsonLabel.addProperty(CommonConstants.UPDATE_QUANTITY_TOAST_MSG, CommonConstants.PRODUCT_CODE
				+ CommonHelper.getLabel(CommonConstants.UPDATE_QUANTITY_TOAST_MSG, currentPage));

		maxQuantityError.addProperty(CommonConstants.MESSAGE,
				CommonHelper.getLabel(CommonConstants.MAX_QUANTITY_ERROR_MSG, currentPage)
						+ CommonConstants.MAX_PURCHASABLE_QUANTITY);
		maxQuantityError.addProperty(CommonConstants.ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.MAX_QUANTITY_ALT_TEXT, currentPage));

		myQuoteJsonLabel.add(CommonConstants.MAX_QUANTITY_ERROR_MSG, maxQuantityError);

		quickAddProdNotAvailableError.addProperty(CommonConstants.MESSAGE,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_PROD_NOT_AV_MSG, currentPage));
		quickAddProdNotAvailableError.addProperty(CommonConstants.ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_PROD_NOT_AV_ALT, currentPage));

		myQuoteJsonLabel.add(CommonConstants.QUICK_QUOTE_PRODUCT_NOT_AVAILABLE_ERROR, quickAddProdNotAvailableError);

		quickAddInvalidProdError.addProperty(CommonConstants.MESSAGE,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_INVALID_PROD_MSG, currentPage));
		quickAddInvalidProdError.addProperty(CommonConstants.ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_INVALID_PROD_ALT, currentPage));

		myQuoteJsonLabel.add(CommonConstants.QUICK_QUOTE_INVALID_PRODUCT_ERROR, quickAddInvalidProdError);

		myQuoteJsonLabel.addProperty(CommonConstants.QUICK_QUOTE_FILL_ALL_ERROR,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_FILL_ALL_ERROR, currentPage));
		myQuoteJsonLabel.addProperty(CommonConstants.SAVE_TO_SHOPPING_LIST_TOAST_MSG,
				CommonHelper.getLabel(CommonConstants.SAVE_TO_SHOPPING_LIST_TOAST_MSG, currentPage));

		myQuoteJsonLabel.addProperty(CommonConstants.QUICK_QUOTE_TOAST_MSG, quickQuoteToastMsg);

		surchargeJson.addProperty(CommonConstants.MESSAGE, surchargeMessage);
		surchargeJson.addProperty(CommonConstants.ALT_TEXT, surchargeMessageAltText);

		myQuoteJsonLabel.add(CommonConstants.SURCHARGE_DISCLAIMER, surchargeJson);

		upgradeJson.addProperty(CommonConstants.HEADING, upgradeAccountHeading);
		upgradeJson.addProperty(CommonConstants.DESCRIPTION, upgradeAccountDesc);
		upgradeJson.addProperty(CommonConstants.CANCEL_LINK, upgradeAccountCancelLink);
		upgradeJson.addProperty(CommonConstants.DASHBOARD_BUTTON, upgradeAccountDashboardButton);

		myQuoteJsonLabel.add(CommonConstants.UPGRADE_ACCOUNT, upgradeJson);

		myQuoteJsonLabel.addProperty(CommonConstants.PRODUCT_ANNOUNCEMENT_ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.PRODUCT_ANNOUNCEMENT_ALT_TEXT, currentPage));

		nonPurchasableJson.addProperty(CommonConstants.MESSAGE,
				CommonHelper.getLabel(CommonConstants.NON_PURCHASABLE_MSG, currentPage));
		nonPurchasableJson.addProperty(CommonConstants.ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.NON_PURCHASABLE_ALT, currentPage));

		myQuoteJsonLabel.add(CommonConstants.NON_PURCHASABLE, nonPurchasableJson);

		headerLevelMessagesJson.addProperty(CommonConstants.MESSAGE,
				CommonHelper.getLabel(CommonConstants.HEADER_LEVEL_MESSAGES_MSG, currentPage));
		headerLevelMessagesJson.addProperty(CommonConstants.ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.HEADER_LEVEL_MESSAGES_ALT, currentPage));

		myQuoteJsonLabel.add(CommonConstants.HEADER_LEVEL_MESSAGES, headerLevelMessagesJson);

		summaryLabelJson.addProperty(CommonConstants.TITLE, summaryLabelsTitle);
		summaryLabelJson.addProperty(CommonConstants.GET_QUOTE_CTA, summaryLabelsGetQuoteCTA);
		summaryLabelJson.addProperty(CommonConstants.INSTRUCTIONS_PLACE_HOLDER, summaryLabelsInstHolder);
		summaryLabelJson.addProperty(CommonConstants.LOCAL_LABEL, summaryLabelsLocalLabel);
		summaryLabelJson.addProperty(CommonConstants.DIRECT_IMPORT_LABEL, summaryLabelsDirectImportLabel);

		myQuoteJsonLabel.add(CommonConstants.QUOTE_DETAILS, summaryLabelJson);
		
		myQuoteJsonLabel.addProperty(CommonConstants.LIST_PRICE_LABEL_ATC,
				CommonHelper.getLabel(CommonConstants.LIST_PRICE_VALUE, currentPage));
		
		myQuoteJsonLabel.addProperty(CommonConstants.PRICE_PER_UNIT,
				CommonHelper.getLabel(CommonConstants.PRICE_PER_UNIT, currentPage));

		myQuoteJsonLabel.addProperty("dangerousGoodsMsg", dangerousGoodsMsg);
		myQuoteJsonLabel.addProperty("dangerousGoodInfoAltText", dangerousGoodInfoAltText);

		clearCartJsonLabel.addProperty(CommonConstants.HEADING, ClearCartHeading);
		clearCartJsonLabel.addProperty(CommonConstants.DESCRIPTION, clearCartDescription);
		clearCartJsonLabel.addProperty(CommonConstants.CANCEL_LABEL, cancelLabel);
		clearCartJsonLabel.addProperty(CommonConstants.PROCEED_LABEL, clearCartLabel);
		
		myQuoteJsonLabel.addProperty(CommonConstants.CLEAR_CART_LABEL, clearCartLabel);
		myQuoteJsonLabel.add(CommonConstants.CLEAR_CART, clearCartJsonLabel);
		
		minQuantityJsonLabel.addProperty("message", quantityInfoMessage);
		minQuantityJsonLabel.addProperty("altText", quantityAltText);
		myQuoteJsonLabel.add("minQuantityInfoMessage", minQuantityJsonLabel);

		return myQuoteJsonLabel.toString();
	}

	/**
	 * My Cart print PDF labels.
	 *
	 * @param tools the tools
	 * @return the string
	 */
	public String printPDFLabelsJson(List<BDBStandardToolModel> tools) {
		JsonObject printPDFLabels = new JsonObject();
		JsonObject disclaimer = new JsonObject();
		JsonObject addressLabels = new JsonObject();
		JsonObject billingAddress = new JsonObject();
		JsonObject email = new JsonObject();

		email.addProperty(CommonConstants.HEADING, emailHeading);
		email.addProperty(CommonConstants.MESSAGE, emailMessage);
		email.addProperty(CommonConstants.CART_ID, cartId);
		email.addProperty(CommonConstants.PLACEHOLDER, emailPlaceholder);
		email.addProperty(CommonConstants.CANCEL, emailCancel);
		email.addProperty(CommonConstants.SEND_LABEL, emailSend);
		email.addProperty(CommonConstants.ALT_TEXT, emailAltText);
		email.addProperty(CommonConstants.EMAIL_VALIDATION_MSG, emailValidationMsg);
		email.addProperty(CommonConstants.EMAIL_VALIDATION_ERROR_MSG, emailValidationErrorMsg);
		email.addProperty(CommonConstants.MAX_EMAIL_ID_ERROR_MSG, maxEmailIdsErrorMsg);

		disclaimer.addProperty(CommonConstants.HEADING, disclaimerHeading);
		disclaimer.addProperty(CommonConstants.DESCRIPTION, disclaimerDescription);
		printPDFLabels.add(CommonConstants.DISCLAIMER, disclaimer);

		addressLabels.addProperty(CommonConstants.SHIP_TO_LBL, billToLabel);
		addressLabels.addProperty(CommonConstants.SHIP_TO_NUMBER_LBL, billToNumberLabel);
		billingAddress.add(CommonConstants.ADDRESS_LABELS, addressLabels);
		printPDFLabels.add(CommonConstants.BILLING_ADDRESS, billingAddress);
		printPDFLabels.add(CommonConstants.EMAIL, email);

		printPDFLabels.add(CommonConstants.ICONS_LIST, myCartPrintHeaderLabels(tools));
		return printPDFLabels.toString();
	}

	/**
	 * My cart print header labels.
	 *
	 * @param tools the tools
	 * @return the json array
	 */
	public JsonArray myCartPrintHeaderLabels(List<BDBStandardToolModel> tools) {
		JsonArray toolArray = new JsonArray();
		if (CollectionUtils.isNotEmpty(tools)) {
			for (BDBStandardToolModel tool : tools) {
				JsonObject iconObj = new JsonObject();
				iconObj.addProperty(CommonConstants.ICON, tool.getIcon());
				iconObj.addProperty(CommonConstants.ALT_TEXT, tool.getIconAltText());
				iconObj.addProperty(CommonConstants.CLASS_NAME, tool.getClassName());
				toolArray.add(iconObj);
			}
		}
		return toolArray;
	}

	/**
	 * This method forms the json for commerce box labels.
	 *
	 * @return the label json
	 * @throws LoginException the login exception
	 */
	public String myCartLabels() {
		JsonObject myCartJsonLabel = new JsonObject();
		JsonObject emptyCart = new JsonObject();
		JsonObject maxQuantityError = new JsonObject();
		JsonObject quickAddProdNotAvailableError = new JsonObject();
		JsonObject quickAddInvalidProdError = new JsonObject();
		JsonObject nonPurchasableJson = new JsonObject();
		JsonObject headerLevelMessagesJson = new JsonObject();
		JsonObject upgradeAccount = new JsonObject();
		JsonObject rouErrorMessage = new JsonObject();
		JsonObject clearCartJsonLabel = new JsonObject();
		JsonObject minQuantityJsonLabel = new JsonObject();
		JsonObject quoteReferenceModal = new JsonObject();

		// forming labels JSON
		myCartJsonLabel.addProperty(CommonConstants.MY_CART, myCart);
		myCartJsonLabel.addProperty(CommonConstants.ITEMS, items);
		myCartJsonLabel.addProperty(CommonConstants.ITEM, item);
		myCartJsonLabel.addProperty(CommonConstants.CART_ID, cartId);
		myCartJsonLabel.addProperty(CommonConstants.QUICK_ADD,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_CTA, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.CONTINUE_SHOPPING, continueShopping);
		myCartJsonLabel.addProperty(CommonConstants.PRODUCTS, products);
		myCartJsonLabel.addProperty(CommonConstants.NZ_PRICING_MESSAGE, nzPricingMessage);
		myCartJsonLabel.addProperty(CommonConstants.EST_DELIVERY_DATE, estDeliveryDate);
		myCartJsonLabel.addProperty(CommonConstants.QUANTITY,
				CommonHelper.getLabel(CommonConstants.CART_QUANTITY_VALUE, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.SAVE_LATER_QUANTITY_LABEL, saveLAterQuantityLabel);
		myCartJsonLabel.addProperty(CommonConstants.PRICE_PER_UNIT,
				CommonHelper.getLabel(CommonConstants.PRICE_PER_UNIT, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.YOUR_PRICE_LABEL_ATC,
				CommonHelper.getLabel(CommonConstants.YOUR_PRICE_VALUE, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.LIST_PRICE_LABEL_ATC,
				CommonHelper.getLabel(CommonConstants.LIST_PRICE_VALUE, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.TOTAL_PRICE, totalPriceLabel);
		myCartJsonLabel.addProperty(CommonConstants.ADD_TO_SHOPPING_LIST, addToShoppingList);
		myCartJsonLabel.addProperty(CommonConstants.SAVE_FOR_LATER, saveforLater);
		myCartJsonLabel.addProperty(CommonConstants.REMOVE, CommonHelper.getLabel(CommonConstants.REMOVE, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.REQUEST_SAME_LOT_NUMBER, requestSameLotNumber);
		myCartJsonLabel.addProperty(CommonConstants.ADD_TO_CART, addToCart);
		myCartJsonLabel.addProperty(CommonConstants.QUOTE_REFERENCE, quoteReference);
		myCartJsonLabel.addProperty(CommonConstants.REQUEST_LOT_NUMBERS, requestSameLotNumbers);
		myCartJsonLabel.addProperty(CommonConstants.PROMO_CODE, CommonHelper.getLabel(CommonConstants.PROMO_CODE, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.YOU_SAVED, CommonHelper.getLabel(CommonConstants.YOU_SAVED, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.ON, CommonHelper.getLabel(CommonConstants.ON, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.CONTINUE_SHOPPING_URL,
				externalizerService.getFormattedUrl(continueShoppingUrl, resourceResolver));
		myCartJsonLabel.addProperty(CommonConstants.PRICE_CONFIRMATION_PDF, priceConfirmationPDF);
		myCartJsonLabel.addProperty(CommonConstants.QUOTE_REFERENCE_ARIA_LABEL, quoteReferenceAriaLabel);
		myCartJsonLabel.addProperty(CommonConstants.MY_CART_PRINT_PAGE,
				externalizerService.getFormattedUrl(myCartPrintPage, resourceResolver) + "?isPrintPDF=true");
		myCartJsonLabel.addProperty(CommonConstants.PROMO_CODE_INPUT_MESSAGE, promoCodeInputMsg);
		myCartJsonLabel.addProperty(CommonConstants.APPLIED_SUCCESSFULLY, appliedSuccessfully);
		myCartJsonLabel.addProperty(CommonConstants.INVALID_CODE, invalidCode);
		myCartJsonLabel.addProperty(CommonConstants.PROMOTIONS, promotions);
		myCartJsonLabel.addProperty(CommonConstants.PROMO_CODE_APPLY_CTA, promoCodeApplyCTA);

		myCartJsonLabel.addProperty(CommonConstants.REMOVE_ITEMS_FROM_CART_MESSAGE, CommonConstants.PRODUCT_CODE
				+ CommonHelper.getLabel(CommonConstants.REMOVE_ITEMS_FROM_CART_MESSAGE, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.ADD_PRODUCTS_TOSFL_MESSAGE, CommonConstants.PRODUCT_CODE
				+ CommonHelper.getLabel(CommonConstants.ADD_PRODUCTS_TOSFL_MESSAGE, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.UPDATE_QUANTITY_TOAST_MSG, CommonConstants.PRODUCT_CODE
				+ CommonHelper.getLabel(CommonConstants.UPDATE_QUANTITY_TOAST_MSG, currentPage));

		maxQuantityError.addProperty(CommonConstants.MESSAGE,
				CommonHelper.getLabel(CommonConstants.MAX_QUANTITY_ERROR_MSG, currentPage)
						+ CommonConstants.MAX_PURCHASABLE_QUANTITY);
		maxQuantityError.addProperty(CommonConstants.ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.MAX_QUANTITY_ALT_TEXT, currentPage));

		quickAddProdNotAvailableError.addProperty(CommonConstants.MESSAGE,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_PROD_NOT_AV_MSG, currentPage));
		quickAddProdNotAvailableError.addProperty(CommonConstants.ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_PROD_NOT_AV_ALT, currentPage));

		quickAddInvalidProdError.addProperty(CommonConstants.MESSAGE,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_INVALID_PROD_MSG, currentPage));
		quickAddInvalidProdError.addProperty(CommonConstants.ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_INVALID_PROD_ALT, currentPage));

		nonPurchasableJson.addProperty(CommonConstants.MESSAGE,
				CommonHelper.getLabel(CommonConstants.NON_PURCHASABLE_MSG, currentPage));
		nonPurchasableJson.addProperty(CommonConstants.ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.NON_PURCHASABLE_ALT, currentPage));

		headerLevelMessagesJson.addProperty(CommonConstants.MESSAGE,
				CommonHelper.getLabel(CommonConstants.HEADER_LEVEL_MESSAGES_MSG, currentPage));
		headerLevelMessagesJson.addProperty(CommonConstants.ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.HEADER_LEVEL_MESSAGES_ALT, currentPage));

		emptyCart.addProperty(CommonConstants.HEADING, heading);
		emptyCart.addProperty(CommonConstants.DESCRIPTION, description);
		emptyCart.addProperty(CommonConstants.ALT_TEXT, altText);
		emptyCart.addProperty(CommonConstants.EMPTY_CART_ICON, emptyCartIcon);
		emptyCart.addProperty(CommonConstants.NOT_SIGNED_IN_HEADING, notSignedInHeading);
		emptyCart.addProperty(CommonConstants.NOT_SIGNED_IN_DESC, notSignedInDescription);

		myCartJsonLabel.add(CommonConstants.EMPTY_CART, emptyCart);
		myCartJsonLabel.add(CommonConstants.MAX_QUANTITY_ERROR_MSG, maxQuantityError);

		myCartJsonLabel.add(CommonConstants.QUICK_ADD_PROD_NOT_AV_ERROR, quickAddProdNotAvailableError);
		myCartJsonLabel.add(CommonConstants.QUICK_ADD_INVALID_PROD_ERROR, quickAddInvalidProdError);

		myCartJsonLabel.add(CommonConstants.HEADER_LEVEL_MESSAGES, headerLevelMessagesJson);
		myCartJsonLabel.add(CommonConstants.NON_PURCHASABLE, nonPurchasableJson);
		myCartJsonLabel.addProperty(CommonConstants.PRODUCT_ANNOUNCEMENT_ALT_TEXT,
				CommonHelper.getLabel(CommonConstants.PRODUCT_ANNOUNCEMENT_ALT_TEXT, currentPage));

		myCartJsonLabel.addProperty(CommonConstants.QUICK_ADD_FILL_ALL_ERROR,
				CommonHelper.getLabel(CommonConstants.QUICK_ADD_FILL_ALL_ERROR, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.SAVE_TO_SHOPPING_LIST_TOAST_MSG,
				CommonHelper.getLabel(CommonConstants.SAVE_TO_SHOPPING_LIST_TOAST_MSG, currentPage));
		
		upgradeAccount.addProperty(CommonConstants.HEADING, upgradeAccountHeading);
		upgradeAccount.addProperty(CommonConstants.DESCRIPTION, upgradeAccountDesc);
		upgradeAccount.addProperty(CommonConstants.CANCEL_LINK, upgradeAccountCancelLink);
		upgradeAccount.addProperty(CommonConstants.DASHBOARD_BUTTON, upgradeAccountDashboardButton);
		
		myCartJsonLabel.add(CommonConstants.UPGRADE_ACCOUNT, upgradeAccount);
		
		rouErrorMessage.addProperty(CommonConstants.MESSAGE, ruoMessage);
		rouErrorMessage.addProperty(CommonConstants.ALT_TEXT, ruoMessageAltText);
		
		myCartJsonLabel.add(CommonConstants.RUO_ERROR_MESSAGE, rouErrorMessage);
		
		myCartJsonLabel.addProperty(CommonConstants.OUT_OF_STOCK, outOfStock);
		myCartJsonLabel.addProperty(CommonConstants.IN_STOCK, inStock);
		myCartJsonLabel.addProperty(CommonConstants.DISTRIBUTOR_DELIVERY_DATE, distributorDeliveryDate);
		myCartJsonLabel.addProperty(CommonConstants.INQUIRE, inquireLabel);
		myCartJsonLabel.addProperty("dangerousGoodsMsg", dangerousGoodsMsg);
		myCartJsonLabel.addProperty("dangerousGoodInfoAltText", dangerousGoodInfoAltText);
		
		
		clearCartJsonLabel.addProperty(CommonConstants.HEADING, ClearCartHeading);
		clearCartJsonLabel.addProperty(CommonConstants.DESCRIPTION, clearCartDescription);
		clearCartJsonLabel.addProperty(CommonConstants.CANCEL, cancelLabel);
		clearCartJsonLabel.addProperty(CommonConstants.PROCEED_LABEL, clearCartLabel);
		
		myCartJsonLabel.addProperty(CommonConstants.CLEAR_CART_LABEL, clearCartLabel);
		myCartJsonLabel.add(CommonConstants.CLEAR_CART, clearCartJsonLabel);
		
		minQuantityJsonLabel.addProperty("message", quantityInfoMessage);
		minQuantityJsonLabel.addProperty("altText", quantityAltText);
		myCartJsonLabel.add("minQuantityInfoMessage", minQuantityJsonLabel);
		myCartJsonLabel.addProperty("invalidProductsError", invalidProductsError);
		myCartJsonLabel.addProperty("quoteReferenceNotAllowed", quoteReferenceNotAllowed);
		
		quoteReferenceModal.addProperty(CommonConstants.RESET_HEADING, resetCartHeading);
		quoteReferenceModal.addProperty(CommonConstants.RESET_DESCRIPTION, resetCartDescription);
		quoteReferenceModal.addProperty(CommonConstants.RESET_LIST_HEADING, resetCartListHeading);
		quoteReferenceModal.addProperty(CommonConstants.SUCCESS_MODAL_HEADING, successModalHeading);
		quoteReferenceModal.addProperty(CommonConstants.SUCCESS_MODAL_DESCRIPTION, successModalDescription);
		quoteReferenceModal.addProperty(CommonConstants.SUCCESS_LIST_HEADING, successModalListHeading);
		quoteReferenceModal.addProperty(CommonConstants.CLEAR_CART_CTA_LABEL, clearCartCTALabel);
		quoteReferenceModal.addProperty(CommonConstants.SAVE_ALL_FOR_LATER_CTA_LABEL, saveAllForLaterCTALabel);
		quoteReferenceModal.addProperty(CommonConstants.APPLY_QUOTE_CTA_LABEL, applyQuoteCTALabel);
		quoteReferenceModal.addProperty(CommonConstants.SUBMIT_CTA_LABEL, submitCTALabel);
		myCartJsonLabel.add(CommonConstants.QUOTE_REFERENCE_MODAL, quoteReferenceModal);
		
		return myCartJsonLabel.toString();
	}

	/**
	 * Gets the my cart json config.
	 *
	 * @return the my cart json config
	 */
	public JsonObject getMyCartJsonConfig() {
		JsonObject myCartConfigJson = new JsonObject();
		JsonObject getCartWithIdentifier = new JsonObject();
		JsonObject deleteEntryFromCart = new JsonObject();
		JsonObject saveForLater = new JsonObject();
		JsonObject getSaveForLater = new JsonObject();
		JsonObject createSaveForLaterCart = new JsonObject();
		JsonObject addToSaveForLater = new JsonObject();
		JsonObject addToCartFromSaveToLater = new JsonObject();
		JsonObject deleteSaveForLater = new JsonObject();
		JsonObject updateCartQuantity = new JsonObject();
		JsonObject updateLotIndicator = new JsonObject();
		JsonObject updatesCartVATExemptStatus = new JsonObject();
		JsonObject getProductAnnouncements = new JsonObject();
		JsonObject applyCoupon = new JsonObject();
		JsonObject replaceCartEntryJson = new JsonObject();
		JsonObject replaceSaveForLaterEntryJson = new JsonObject();
		JsonObject quoteReference = new JsonObject();
		JsonObject getQuote = new JsonObject();
		JsonObject quoteToCart = new JsonObject();
		JsonObject deleteCoupon = new JsonObject();
		JsonObject sendEmail = new JsonObject();
		JsonObject clearCartJson = new JsonObject();

		String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);

		if (null != hybrisSiteId) {
			getCartWithIdentifier.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getCartWithIdentifier()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			getCartWithIdentifier.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);

			deleteEntryFromCart.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getDeleteEntryFromCart()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			deleteEntryFromCart.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_DELETE);

			getSaveForLater.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
					+ bdbApiEndpointService.getSaveForLater().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			getSaveForLater.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);

			createSaveForLaterCart.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getCreateSaveForLaterCart()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			createSaveForLaterCart.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

			addToSaveForLater.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
					+ bdbApiEndpointService.getAddToSaveForLater().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			addToSaveForLater.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

			addToCartFromSaveToLater.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getAddToCartFromSaveToLater()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			addToCartFromSaveToLater.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

			deleteSaveForLater.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getDeleteSaveForLater()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			deleteSaveForLater.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_DELETE);

			updateCartQuantity.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getUpdateCartQuantity()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			updateCartQuantity.addProperty(CommonConstants.METHOD, CommonConstants.METHOD_PATCH);

			updateLotIndicator.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getUpdateLotIndicator()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			updateLotIndicator.addProperty(CommonConstants.METHOD, CommonConstants.METHOD_PATCH);

			updatesCartVATExemptStatus.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getUpdatesCartVATExemptStatus()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			updatesCartVATExemptStatus.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_PUT);

			applyCoupon.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
					+ bdbApiEndpointService.getApplyCoupon().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			applyCoupon.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

			quoteReference.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
					+ bdbApiEndpointService.getQuoteReference().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			quoteReference.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
			
			getQuote.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
					+ bdbApiEndpointService.getGetQuote().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			getQuote.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

			quoteToCart.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
					+ bdbApiEndpointService.getQuoteToCart().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			quoteToCart.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

			sendEmail.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
					+ bdbApiEndpointService.sendEmail().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			sendEmail.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

		}

		getProductAnnouncements.addProperty(CommonConstants.URL, bdbApiEndpointService.getProductAnnouncements());
		getProductAnnouncements.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

		replaceSaveForLaterEntryJson.addProperty(CommonConstants.URL,
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getReplaceSaveForLaterEntry()
						.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
		replaceSaveForLaterEntryJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

		replaceCartEntryJson.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getReplaceCartEntry().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
		replaceCartEntryJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

		deleteCoupon.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getDeleteCoupon().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
		deleteCoupon.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_DELETE);
		
		clearCartJson.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getClearCart().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
		clearCartJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_DELETE);

		myCartConfigJson.add("getCartWithIdentifier", getCartWithIdentifier);
		myCartConfigJson.add("deleteEntryFromCart", deleteEntryFromCart);
		myCartConfigJson.add("updateCartQuantity", updateCartQuantity);
		myCartConfigJson.add("updateLotIndicator", updateLotIndicator);
		myCartConfigJson.add("updatesCartVATExemptStatus", updatesCartVATExemptStatus);
		myCartConfigJson.add("applyCoupon", applyCoupon);

		saveForLater.add(CommonConstants.REPLACE_SAVE_FOR_LATER, replaceSaveForLaterEntryJson);
		saveForLater.add("getSaveForLater", getSaveForLater);
		saveForLater.add("createSaveForLaterCart", createSaveForLaterCart);
		saveForLater.add("addToSaveForLater", addToSaveForLater);
		saveForLater.add("addToCartFromSaveToLater", addToCartFromSaveToLater);
		saveForLater.add("deleteSaveForLater", deleteSaveForLater);
		myCartConfigJson.add("saveForLater", saveForLater);
		myCartConfigJson.add(CommonConstants.REPLACE_CART_ENTRY, replaceCartEntryJson);
		myCartConfigJson.add("getProductAnnouncements", getProductAnnouncements);
		myCartConfigJson.add("quoteReference", quoteReference);
		myCartConfigJson.add("getQuote", getQuote);
		myCartConfigJson.add("quoteToCart", quoteToCart);
		myCartConfigJson.add("deleteCoupon", deleteCoupon);
		myCartConfigJson.add("sendEmail", sendEmail);
		myCartConfigJson.add("clearCart", clearCartJson);

		return myCartConfigJson;
	}

	/**
	 * Gets the my cart labels.
	 *
	 * @return the my cart labels
	 */
	public String getMyCartLabels() {
		return myCartLabels;
	}

	/**
	 * Gets the my cart config.
	 *
	 * @return the my cart config
	 */
	public String getMyCartConfig() {
		return myCartConfig;
	}

	/**
	 * Gets the my quote labels.
	 *
	 * @return myQuoteLabels
	 */
	public String getMyQuoteLabels() {
		return myQuoteLabels;
	}

	/**
	 * Gets the enable add to quote.
	 *
	 * @return enableAddToQuote
	 */
	public String getEnableAddToQuoteCheckBox() {
		return enableAddToQuoteCheckBox;
	}
	
	/**
	 * @return myQuoteConfig
	 */
	public String getMyQuoteConfig() {
		return myQuoteConfig;
	}

}
