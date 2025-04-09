package com.bdb.aem.core.models;

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
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * The Class OrderConfirmationModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OrderConfirmationModel {

	/** The Constant logger. */
	protected static final Logger logger = LoggerFactory.getLogger(OrderConfirmationModel.class);

	/** The order numbertext. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String orderNumbertext;

	/** The order number text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String orderNumberText;

	/** The contact US link. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String contactUSLink;

	/** The attention. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String attention;

	/** The shipping address. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String shippingAddress;

	/** The change address. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String changeAddress;

	/** The address details heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String addressDetailsHeading;

	/** The pending. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String pending;

	/** The special instructions. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String specialInstructions;

	/** The order details heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String orderDetailsHeading;

	/** The edit CTA. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String editCTA;

	/** The po number error msg. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String poNumberErrorMsg;

	/** The card number. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String cardNumber;

	/** The delivery option. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String deliveryOption;

	/** The shipping option. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String shippingOption;

	/** The special instruction lbl. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String specialInstructionLbl;

	/** The src. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String src;

	/** The alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String altText;

	/** The success message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String successMessage;

	/** The cancel message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String cancelMessage;

	/** The pending message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String pendingMessage;

	/** The products heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String productsHeading;

	/** The your price label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String yourPriceLabel;

	/** The list price label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String listPriceLabel;

	/** The default PRD image. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String defaultPRDImage;

	/** The default PRD image alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String defaultPRDImageAltText;

	/** The place another other. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String placeAnotherOther;

	/** The continue shopping CTA. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String continueShoppingCTA;

	/** The continue shopping label text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String continueShoppingLabelText;

	/** The cancel order CTA. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String cancelOrderCTA;

	/** The print order CTA. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String printOrderCTA;

	/** The print order label text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String printOrderLabelText;

	/** The Cancel order tile. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String CancelOrderTile;

	/** The cancel order instruction. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String cancelOrderInstruction;

	/** The Cancel confirm CTA. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String CancelConfirmCTA;

	/** The Cancel dismiss CTA. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String CancelDismissCTA;
	
	/** The dist heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String distHeading;
	
	/** The act mgr heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String actMgrHeading;
	
	/** The confirm info msg. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String confirmInfoMsg;
	
	/** The confirm info alt. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String confirmInfoAlt;
	
	/** The requested by. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String requestedBy;
	
	/** The requested date. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String requestedDate;
	
	/** The quotation currency. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotationCurrency;
	
	/** The distributor delivery date. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String distributorDeliveryDate;
	
	/** The in stock. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String inStock;
	
	/** The out of stock. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String outOfStock;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
	
	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The order confirmation label. */
	private String orderConfirmationLabel;

	/** The orderconfirmation config. */
	private String orderconfirmationConfig;

	/** The products. */
	private String products;

	/** The address details. */
	private String addressDetails;

	/** The info icon. */
	private String infoIcon;

	/** The hybris site id. */
	private String hybrisSiteId;

	/** The message. */
	private String message;
	
	/** The quote confirmation labels. */
	private String quoteConfirmationLabels;
	
	/** The enable add to quote check box. */
	private String enableAddToQuoteCheckBox;
	
	/** The quote confirmation config. */
	private String quoteConfirmationConfig;


	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("OrderConfirmationModel init() - start");
		
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		enableAddToQuoteCheckBox = CommonHelper.getLabel(CommonConstants.ENABLE_ADD_TO_QUOTE, currentPage);
		if (enableAddToQuoteCheckBox == null) {
			enableAddToQuoteCheckBox = CommonConstants.FALSE;
		}
		addressDetails = addressDetailsLabel();
		products = setProductJson();
		infoIcon = setInfoIconJson();
		message = setMessageJson();
		orderconfirmationConfig = setOrderConfirmationConfig(excludeUtilObject);
		orderConfirmationLabel = orderConfirmationLabel(resourceResolver);
		quoteConfirmationLabels = setQuoteConfirmationLabels(resourceResolver);
		quoteConfirmationConfig = setQuoteConfirmationConfig(excludeUtilObject);
	}

	/**
	 * Sets the quote confirmation labels.
	 *
	 * @param resourceResolver the resource resolver
	 * @return the string
	 */
	public String setQuoteConfirmationLabels(ResourceResolver resourceResolver) {
		JsonObject quoteConfirmation = new JsonObject();
		JsonObject distributorInformation = new JsonObject();
		JsonObject accountManagerInformation = new JsonObject();
		JsonObject confirmationInformation = new JsonObject();
		
		
		distributorInformation.addProperty(CommonConstants.HEADING, distHeading);
		distributorInformation.addProperty(CommonConstants.NAME, CommonHelper.getLabel(CommonConstants.DISTRIBUTOR_NAME_LABEL, currentPage));
		distributorInformation.addProperty(CommonConstants.EMAIL, CommonHelper.getLabel(CommonConstants.DISTRIBUTOR_EMAIL_LABEL, currentPage));
		distributorInformation.addProperty(CommonConstants.PHONE_NUMBER, CommonHelper.getLabel(CommonConstants.DISTRIBUTOR_PHONE_NUMBER_LABEL, currentPage));
		
		quoteConfirmation.add(CommonConstants.DISTRIBUTOR_INFORMATION, distributorInformation);
		
		accountManagerInformation.addProperty(CommonConstants.HEADING, actMgrHeading);
		accountManagerInformation.addProperty(CommonConstants.NAME, CommonHelper.getLabel(CommonConstants.ACCOUNT_MANAGER_NAME_LABEL, currentPage));
		accountManagerInformation.addProperty(CommonConstants.EMAIL, CommonHelper.getLabel(CommonConstants.ACCOUNT_MANAGER_EMAIL_LABEL, currentPage));
		accountManagerInformation.addProperty(CommonConstants.PHONE_NUMBER, CommonHelper.getLabel(CommonConstants.ACCOUNT_MANAGER_PHONE_NUMBER_LABEL, currentPage));
		
		quoteConfirmation.add(CommonConstants.ACCOUNT_MANAGER_INFORMATION, accountManagerInformation);
		
		confirmationInformation.addProperty(CommonConstants.MESSAGE, confirmInfoMsg);
		confirmationInformation.addProperty(CommonConstants.ALT_TEXT, confirmInfoAlt);
		
		quoteConfirmation.add(CommonConstants.CONFIRMATION_INFORMATION, confirmationInformation);
		
		quoteConfirmation.addProperty(CommonConstants.SPECIAL_INSTRUCTIONS_TEXT, specialInstructionLbl);
		quoteConfirmation.addProperty(CommonConstants.SPECIAL_INSTRUCTIONS, specialInstructions);
		quoteConfirmation.addProperty(CommonConstants.QUOTE_NUMBER, CommonHelper.getLabel(CommonConstants.QUOTE_NUMBER_LABEL, currentPage));
		quoteConfirmation.addProperty(CommonConstants.REQUESTED_BY, requestedBy);
		quoteConfirmation.addProperty(CommonConstants.REQUESTED_DATE, requestedDate);
		quoteConfirmation.addProperty(CommonConstants.QUOTATION_CURRENCY, quotationCurrency);
		quoteConfirmation.addProperty(CommonConstants.IS_QUOTE, Boolean.TRUE);
		quoteConfirmation.addProperty(CommonConstants.IS_QUOTE_DETAILS, Boolean.FALSE);
		quoteConfirmation.addProperty(CommonConstants.CONTINUE_SHOPPING_LABEL_TEXT, continueShoppingLabelText);
		quoteConfirmation.addProperty(CommonConstants.CONTINUE_SHIPPING_CTA, externalizerService.getFormattedUrl(continueShoppingCTA, resourceResolver));
		quoteConfirmation.addProperty(CommonConstants.PLACE_ANOTHER_OTHER, placeAnotherOther);
		
		return quoteConfirmation.toString();
	}
	
	/**
	 * Sets the quote confirmation config.
	 *
	 * @param excludeUtilObject the exclude util object
	 * @return the string
	 */
	public String setQuoteConfirmationConfig(ExcludeUtil excludeUtilObject) {
		JsonObject config = new JsonObject();
		JsonObject getProductAnnouncements = new JsonObject();
		getProductAnnouncements.addProperty(CommonConstants.URL, bdbApiEndpointService.getProductAnnouncements());
		getProductAnnouncements.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		String getOrderConfirmationCheckoutConfig = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getQuoteConfirmationConfig(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getProductAnnouncementsConfigload = new Payload(getOrderConfirmationCheckoutConfig, HttpConstants.METHOD_GET);
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		config.add(CommonConstants.GET_QUOTE_INFORMATION, gson.toJsonTree(getProductAnnouncementsConfigload));
		config.add(CommonConstants.GET_PRODUCT_ANNOUNCEMENTS, getProductAnnouncements);
		return config.toString();
	}

	/**
	 * Sets the message json.
	 *
	 * @return the string
	 */
	private String setMessageJson() {
		JsonObject msgJson = new JsonObject();
		msgJson.addProperty(CommonConstants.SUCCESS_MESSAGE, successMessage);
		msgJson.addProperty(CommonConstants.PENDING_MESSAGE, pendingMessage);
		msgJson.addProperty(CommonConstants.CANCEL_MESSAGE, cancelMessage);
		return msgJson.toString();
	}

	/**
	 * Sets the info icon json.
	 *
	 * @return the string
	 */
	private String setInfoIconJson() {
		JsonObject infoIconJson = new JsonObject();
		infoIconJson.addProperty(CommonConstants.SRC, src);
		infoIconJson.addProperty(CommonConstants.ALT_TEXT, altText);
		return infoIconJson.toString();
	}

	/**
	 * Sets the product json.
	 *
	 * @return the string
	 */
	private String setProductJson() {
		JsonObject productJson = new JsonObject();

		productJson.addProperty(CommonConstants.PRODUCTS_HEADING, productsHeading);
		productJson.addProperty(CommonConstants.EST_DELIVERY_DATE, CommonHelper.getLabel(CommonConstants.EST_DELIVERY_DATE_LABEL, currentPage));
		productJson.addProperty(CommonConstants.QUANTITY, CommonHelper.getLabel(CommonConstants.CART_QUANTITY_VALUE, currentPage));
		productJson.addProperty(CommonConstants.PRICE_PER_UNIT, CommonHelper.getLabel(CommonConstants.PRICE_PER_UNIT_LABEL, currentPage));
		productJson.addProperty(CommonConstants.YOUR_PRICE_LABEL_ATC, yourPriceLabel);
		productJson.addProperty(CommonConstants.LIST_PRICE_LABEL_ATC, listPriceLabel);
		productJson.addProperty(CommonConstants.TOTAL_PRICE, CommonHelper.getLabel(CommonConstants.TOTAL_PRICE_LABEL, currentPage));
		productJson.addProperty(CommonConstants.DEFAULT_PRD_IMAGE, defaultPRDImage);
		productJson.addProperty(CommonConstants.DEFAULT_PRD_IMAGE_ALT_TEXT, defaultPRDImageAltText);
		productJson.addProperty(CommonConstants.YOU_SAVED_LABEL,CommonHelper.getLabel(CommonConstants.YOU_SAVED, currentPage));
		productJson.addProperty(CommonConstants.ON_LABEL,CommonHelper.getLabel(CommonConstants.ON, currentPage));
		productJson.addProperty(CommonConstants.PROMO_CODE_LABEL,CommonHelper.getLabel(CommonConstants.PROMO_CODE, currentPage));
		productJson.addProperty("distributorDeliveryDate",distributorDeliveryDate);
		productJson.addProperty("inStock",inStock);
		productJson.addProperty("outOfStock",outOfStock);
		return productJson.toString();
	}

	/**
	 * Address details label.
	 *
	 * @return the string
	 */
	private String addressDetailsLabel() {
		JsonObject addressJson = new JsonObject();

		addressJson.addProperty(CommonConstants.ADDRESS_DETAILS_HEADING, addressDetailsHeading);
		addressJson.addProperty(CommonConstants.SHIP_TO_LBL, CommonHelper.getLabel(CommonConstants.SHIP_TO_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.BILL_TO_LBL, CommonHelper.getLabel(CommonConstants.BILL_TO_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.SOLD_TO_LBL, CommonHelper.getLabel(CommonConstants.SOLD_TO_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.SHIPTO_NUMBER, CommonHelper.getLabel(CommonConstants.SHIP_TO_NUMBER, currentPage));
		addressJson.addProperty(CommonConstants.BILLTO_NUMBER, CommonHelper.getLabel(CommonConstants.BILL_TO_NUMBER_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.SOLDTO_NUMBER_LABEL, CommonHelper.getLabel(CommonConstants.SOLD_TO_NUMBER_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.PENDING, pending);
		addressJson.addProperty(CommonConstants.PAY_TO_LBL, CommonHelper.getLabel(CommonConstants.PAY_TO_LABEL, currentPage));

		return addressJson.toString();

	}

	/**
	 * Sets the order confirmation config.
	 *
	 * @param excludeUtilObject the exclude util object
	 * @return the string
	 */
	private String setOrderConfirmationConfig(ExcludeUtil excludeUtilObject) {
		JsonObject config = new JsonObject();
		String getProductAnnouncements = bdbApiEndpointService.getProductAnnouncements();
		Payload getProductAnnouncementsConfigload = new Payload(getProductAnnouncements, HttpConstants.METHOD_POST);
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		String getOrderConfirmationCheckoutConfig = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getOrderConfirmationCheckoutConfig(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getOrderConfirmationCheckoutConfigload = new Payload(getOrderConfirmationCheckoutConfig,
				HttpConstants.METHOD_GET);
		String getOrderConfirmationCancelOrderConfig = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain()
						+ bdbApiEndpointService.getOrderConfirmationCancelOrderConfig(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getOrderConfirmationCancelOrderConfigPayload = new Payload(getOrderConfirmationCancelOrderConfig,
				HttpConstants.METHOD_POST);

		config.add(CommonConstants.GET_CHECKOUT_DATA, gson.toJsonTree(getOrderConfirmationCheckoutConfigload));
		config.add(CommonConstants.CANCEL_ORDER_CONFIG, gson.toJsonTree(getOrderConfirmationCancelOrderConfigPayload));
		config.add(CommonConstants.GET_PRODUCT_ANNOUNCEMENTS, gson.toJsonTree(getProductAnnouncementsConfigload));
		return config.toString();
	}

	/**
	 * Order confirmation label.
	 *
	 * @param resourceResolver the resource resolver
	 * @return the string
	 */
	private String orderConfirmationLabel(ResourceResolver resourceResolver) {
		JsonObject orderConfirmationJson = new JsonObject();

		orderConfirmationJson.addProperty(CommonConstants.ORDER_NUMBERTEXT, orderNumbertext);
		orderConfirmationJson.addProperty(CommonConstants.ORDER_NUMBER_TEXT, orderNumberText);
		orderConfirmationJson.addProperty(CommonConstants.CONTACT_US_LABEL, CommonHelper.getLabel(CommonConstants.CONTACT_US_LABEL, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.CONTACT_US_LINK,
				externalizerService.getFormattedUrl(contactUSLink, resourceResolver));
		orderConfirmationJson.addProperty(CommonConstants.ATTENTION, attention);
		orderConfirmationJson.addProperty(CommonConstants.SHIPPING_ADDRESS, shippingAddress);
		orderConfirmationJson.addProperty(CommonConstants.CHANGE_ADDRESS, changeAddress);
		orderConfirmationJson.addProperty(CommonConstants.SPECIAL_INSTRUCTIONS, specialInstructions);
		orderConfirmationJson.addProperty(CommonConstants.ORDER_DETAILS_HEADING, orderDetailsHeading);
		orderConfirmationJson.addProperty(CommonConstants.ORDER_NUMBER, CommonHelper.getLabel(CommonConstants.ORDER_NUMBER_LABEL, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.PO_NUMBER, CommonHelper.getLabel(CommonConstants.PO_NUMBER_LABEL, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.QUOTE_REFERENCE_NUMBER, CommonHelper.getLabel(CommonConstants.QUOTE_REFERENCE_NUMBER, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.EDIT_CTA, editCTA);
		orderConfirmationJson.addProperty(CommonConstants.SAVECTA, CommonHelper.getLabel(CommonConstants.SAVE_LABEL, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.PO_NUMBER_ERROR_MESSAGE, poNumberErrorMsg);
		orderConfirmationJson.addProperty(CommonConstants.PO_DATE_LBL, CommonHelper.getLabel(CommonConstants.PO_DATE_LABEL, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.ORDER_BY,  CommonHelper.getLabel(CommonConstants.ORDERED_BY_LABEL, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.SOURCE_LABEL, CommonHelper.getLabel(CommonConstants.SOURCE_LABEL, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.CARD_NUMBER, cardNumber);
		orderConfirmationJson.addProperty(CommonConstants.DELIVERY_OPTION, deliveryOption);
		orderConfirmationJson.addProperty(CommonConstants.INVOICES_LABEL, CommonHelper.getLabel(CommonConstants.INVOICES_LABEL, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.ORDER_STATUS_LABEL, CommonHelper.getLabel(CommonConstants.ORDER_STATUS_LABEL, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.SHIPPING_OPTION, shippingOption);
		orderConfirmationJson.addProperty(CommonConstants.CARRIER_NUMBER_LABEL, CommonHelper.getLabel(CommonConstants.CARRIER_NUMBER_LABEL, currentPage));
		orderConfirmationJson.addProperty(CommonConstants.SPECIAL_INSTRUCTIONS_TEXT, specialInstructionLbl);
		orderConfirmationJson.addProperty(CommonConstants.PLACE_ANOTHER_OTHER, placeAnotherOther);
		orderConfirmationJson.addProperty(CommonConstants.CONTINUE_SHOPPING_LABEL_TEXT, continueShoppingLabelText);
		orderConfirmationJson.addProperty(CommonConstants.CONTINUE_SHIPPING_CTA,
				externalizerService.getFormattedUrl(continueShoppingCTA, resourceResolver));
		orderConfirmationJson.addProperty(CommonConstants.CANCEL_ORDER_CTA, cancelOrderCTA);
		orderConfirmationJson.addProperty(CommonConstants.PRINT_ORDER_LABEL_TEXT, printOrderLabelText);
		orderConfirmationJson.addProperty(CommonConstants.PRINT_ORDER_CTA,
				externalizerService.getFormattedUrl(printOrderCTA, resourceResolver));
		orderConfirmationJson.addProperty(CommonConstants.CANCEL_ORDER_TILE, CancelOrderTile);
		orderConfirmationJson.addProperty(CommonConstants.CANCEL_ORDER_INSTRUCTION, cancelOrderInstruction + CommonConstants.ORDER);
		orderConfirmationJson.addProperty(CommonConstants.CANCEL_CONFIRM_CTA, CancelConfirmCTA);
		orderConfirmationJson.addProperty(CommonConstants.CANCEL_DISMISS_CTA, CancelDismissCTA);
		orderConfirmationJson.addProperty(CommonConstants.PAYMENT_METHOD, CommonHelper.getLabel(CommonConstants.PAYMENT_METHOD, currentPage));

		return orderConfirmationJson.toString();
	}

	/**
	 * Gets the order confirmation label.
	 *
	 * @return the order confirmation label
	 */
	public String getOrderConfirmationLabel() {
		return orderConfirmationLabel;
	}

	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public String getProducts() {
		return products;
	}

	/**
	 * Gets the address details.
	 *
	 * @return the address details
	 */
	public String getAddressDetails() {
		return addressDetails;
	}

	/**
	 * Gets the orderconfirmation config.
	 *
	 * @return the orderconfirmation config
	 */
	public String getOrderconfirmationConfig() {
		return orderconfirmationConfig;
	}

	/**
	 * Gets the info icon.
	 *
	 * @return the info icon
	 */
	public String getInfoIcon() {
		return infoIcon;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Gets the quote confirmation labels.
	 *
	 * @return the quote confirmation labels
	 */
	public String getQuoteConfirmationLabels() {
		return quoteConfirmationLabels;
	}
	
	/**
	 * Gets the enable add to quote check box.
	 *
	 * @return the enable add to quote check box
	 */
	public String getEnableAddToQuoteCheckBox() {
		return enableAddToQuoteCheckBox;
	}
	
	/**
	 * Gets the quote confirmation config.
	 *
	 * @return the quote confirmation config
	 */
	public String getQuoteConfirmationConfig() {
		return quoteConfirmationConfig;
	}

}
