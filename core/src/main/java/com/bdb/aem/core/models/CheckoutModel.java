package com.bdb.aem.core.models;

import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Class CheckoutModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CheckoutModel {

	/** The logger. */
	protected static final Logger logger = LoggerFactory.getLogger(CheckoutModel.class);

	/** The checkout. */
	@Inject
	@Via("resource")
	public String checkout;
	
	/** The nzPricingMessage. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String nzPricingMessage;

	/** The shipping details. */
	@Inject
	@Via("resource")
	public String shippingDetails;

	/** The attention. */
	@Inject
	@Via("resource")
	public String attention;
	
	/** The secondary attention. */
	@Inject
	@Via("resource")
	public String secondaryAttention;
	
	/** The attention max limit. */
	@Inject
	@Via("resource")
	public String attentionMaxLimit;
	
	/** The attention max limit for ca. */
	@Inject
	@Via("resource")
	public String attentionMaxLimitForCa;
	
	/** The attention max limit for us. */
	@Inject
	@Via("resource")
	public String attentionMaxLimitForUs;
	
	/** The additional attention max limit for ca. */
	@Inject
	@Via("resource")
	public String additionalAttentionMaxLimitForCa;

	/** The shipping address. */
	@Inject
	@Via("resource")
	public String shippingAddress;

	/** The change address. */
	@Inject
	@Via("resource")
	public String changeAddress;

	/** The add address. */
	@Inject
	@Via("resource")
	public String addAddress;
	
	/** The Add Dropship Address. */
	@Inject
	@Via("resource")
	public String addDropshipAddrLabel;
	
	/** The Account Number. */
	@Inject
	@Via("resource")
	public String accountNumberLabel;
	
	/** The Vat Number. */
	@Inject
	@Via("resource")
	public String vatNumberLabel;

	/** The pending review msg. */
	@Inject
	@Via("resource")
	public String pendingReviewMsg;

	/** The deliver option. */
	@Inject
	@Via("resource")
	public String deliverOption;

	/** The overnight. */
	@Inject
	@Via("resource")
	public String overnight;
	
	/** The inside delivery. */
	@Inject
	@Via("resource")
	public String insideDelivery;
	
	/** The over night surcharge. */
	@Inject
	@Via("resource")
	public String overNightSurcharge;
		
	/** The inside delivery surcharge. */
	@Inject
	@Via("resource")
	public String insideDeliverySurcharge;

	/** The address note. */
	@Inject
	@Via("resource")
	public String addressNote;

	/** The address line. */
	@Inject
	@Via("resource")
	public String addressLine;

	/** The building. */
	@Inject
	@Via("resource")
	public String building;

	/** The room. */
	@Inject
	@Via("resource")
	public String room;

	/** The floor. */
	@Inject
	@Via("resource")
	public String floor;

	/** The department. */
	@Inject
	@Via("resource")
	public String department;

	/** The phone number. */
	@Inject
	@Via("resource")
	public String phoneNumber;

	/** The city. */
	@Inject
	@Via("resource")
	public String city;

	/** The state. */
	@Inject
	@Via("resource")
	public String state;

	/** The zip code. */
	@Inject
	@Via("resource")
	public String zipCode;

	/** The country. */
	@Inject
	@Via("resource")
	public String country;

	/** The nick name. */
	@Inject
	@Via("resource")
	public String nickName;

	/** The cancel. */
	@Inject
	@Via("resource")
	public String cancel;

	/** The change address note. */
	@Inject
	@Via("resource")
	public String changeAddressNote;

	/** The search address. */
	@Inject
	@Via("resource")
	public String searchAddress;

	/** The default name. */
	@Inject
	@Via("resource")
	@Named("default")
	public String defaultName;

	/** The save. */
	@Inject
	@Via("resource")
	public String save;

	/** The edit. */
	@Inject
	@Via("resource")
	public String edit;

	/** The shipping method. */
	@Inject
	@Via("resource")
	public String shippingMethod;

	/** The save and continue. */
	@Inject
	@Via("resource")
	public String saveAndContinue;

	/** The add carrier number. */
	@Inject
	@Via("resource")
	public String addCarrierNumber;

	/** The payment details. */
	@Inject
	@Via("resource")
	public String paymentDetails;

	/** The payment type. */
	@Inject
	@Via("resource")
	public String paymentType;

	/** The purchase order. */
	@Inject
	@Via("resource")
	public String purchaseOrder;

	/** The credit card. */
	@Inject
	@Via("resource")
	public String creditCard;

	/** The enter PO number. */
	@Inject
	@Via("resource")
	public String enterPONumber;
	
	/** The enter PO tooltip. */
	@Inject @Default(values="PO number should not exceed 35 characters")
	@Via("resource")
	public String poToolTip;

	/** The select card. */
	@Inject
	@Via("resource")
	public String selectCard;

	/** The change. */
	@Inject
	@Via("resource")
	public String change;

	/** The add new card. */
	@Inject
	@Via("resource")
	public String addNewCard;

	/** The billing details. */
	@Inject
	@Via("resource")
	public String billingDetails;

	/** The billing address. */
	@Inject
	@Via("resource")
	public String billingAddress;

	/** The same as shipping address. */
	@Inject
	@Via("resource")
	public String sameAsShippingAddress;

	/** The other details. */
	@Inject
	@Via("resource")
	public String otherDetails;

	/** The special instruction. */
	@Inject
	@Via("resource")
	public String specialInstruction;

	/** The special instruction lbl. */
	@Inject
	@Via("resource")
	public String specialInstructionLbl;

	/** The email. */
	@Inject
	@Via("resource")
	public String email;

	/** The receive order notification. */
	@Inject
	@Via("resource")
	public String receiveOrderNotification;
	
	/** The Has BOM product message */
	@Inject
	@Via("resource")
	public String hasBomProductMsg;

	/** The email validation msg. */
	@Inject
	@Via("resource")
	public String emailValidationMsg;
	
	/** The cc success msg. */
	@Inject
	@Via("resource")
	public String ccSuccessMessage;

	/** The other note. */
	@Inject
	@Via("resource")
	public String otherNote;

	/** The save and review. */
	@Inject
	@Via("resource")
	public String saveAndReview;

	/** The order summary. */
	@Inject
	@Via("resource")
	public String orderSummary;

	/** The add address heading. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String addAddressHeading;

	/** The set as default label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String setAsDefaultLabel;
	
	/** The add address cta. */
	@Inject
	@Via("resource")
	@Default(values= StringUtils.EMPTY)
	public String addAddressCta;
	
	/**
	 * Company Label
	 */
	/** Company Label. */

	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String companyLabel;
	
	/** Address Line 2. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String addressLineSecond;
	
	/** Street Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String streetLabel;
	
	/** Detailed Address. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String detailedAddress;
	
	/** Address. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String genericAddressLabel;
	
	/** Mandatory Field Error. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String mandatoryFieldError;
	
	/** Phone Number Error. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String phoneNumberError;
	
	/** District Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String districtLabel;
	
	/** Province Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String provinceLabel;
	
	/** Postal Code. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String postalLabel;
	
	/** Pin Label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String pinLabel;
	
	/** The change billing address labels. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String changeBillingAddressLabels;
	
	/** The ship to number label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String shipToNumberLabel;
	
	/** The bill to number label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String billToNumberLabel;
	
	/** The po number error msg. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String poNumberErrorMsg;
	
	/** The po number label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String poNumberLabel;
	
	/** The expiration warning message. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String expirationWarningMessage;
	
	/** The expired message. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String expiredMessage;
	
	/** The default label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String defaultLabel;
	
	/** The expiry date label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String expiryDateLabel;
	
	/** The name on card label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String nameOnCardLabel;
	
	/** The cvv label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String cvvLabel;
	
	/** The reference po number. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String referencePoNumber;
	
	/** The cardfor payment label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String cardforPaymentLabel;

	/** The Bill to address label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String billToAddressLabel;

	/** The Favorite Billing Address label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String favoriteBillingAddress;

	/** The Ending In label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String endingInLabel;

	/** The Visa card label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String visaCardImgPath;

	/** The Visa card alt text label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String visaCardImgAltText;

	/** The Master card label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String masterCardImgPath;

	/** The Master card alt text label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String masterCardImgAltText;

	/** The Amex card label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String amexCardImgPath;

	/** The Amex card alt text label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String amexCardAltText;

	/** The warning icon label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String warningIconPath;

	/** The warning icon alt text label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String warningIconAltText;

	/** The bill to pending review message label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String billToPendingReviewMsg;
	
	/** The bill to pending review message label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String requestSameLotNumMsg;

	/** The successful address text label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String successfulAddressText;
	
	/** The alert heading label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String alertHeadingLabel;
	
	/** The aler message label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String alertMessage;
	
	/** The duplicate order heading label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String duplicateOrderHeadingLabel;
	
	/** The Duplicate order text 1 label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String duplicateOrderText1;
	
	/** The Duplicate order text 2 label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String duplicateOrderText2;
	
	/** The Back to cart Alert Message label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String backToCartAlertMsg;
	
	/** The Back to cart DismissCTA label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String dismissCta;
	
	/** The Back to cart Title label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String backToCartTitle;
	
	/** The Back to cart CTA label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String backToCartCta;
	
	/** The Back to cart icon path. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String backIconPath;
	
	/** The back icon alt text label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String backIconAltText;
	
	/** The review cta label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String reviewCtaLabel;
	
	/** The upArrowAltText label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String upArrowAltText;
	
	/** The downArrowAltText label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String downArrowAltText;
	
	/** The remove cta label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String removeCtaLabel;
	
	/** The Max email id error message label. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String maxEmailIdsErrorMsg;
		
	/** The Review Tax Exempt Msg. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String reviewTaxExemptMsg;
	
	/** The Billing Details Heading. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String billingDetailsHeading;
	
	/** The Heading. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String headingLabel;
	
	/** The Name. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String nameLabel;
	
	/** The distributorAddressLabel. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String distributorAddressLabel;
	
	/** The Button Text. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String buttonText;

	/** The VAT Exempt Message. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String vatExemptMsg;

	/** The Inactive Bill to error icon. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String inactiveBillToErrorIcon;

	/** The Inactive Bill to error Alt text. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String inactiveBillToErrorAltText;

	/** The Inactive Bill to error message */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String inactiveBillToErrorMsg;

	/** The Inactive sold to payer error icon  */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String inactiveSoldToErrorIcon;

	/** The Inactive sold to payer error icon Alt text  */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String inactiveSoldToErrorAltText;
	
	/** The Inactive sold to payer error message  */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String inactiveSoldToErrorMsg;

	/** The Reference PO number placeholder  */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String refPoNumPlaceholder;
	
	/** The Reference PO Tooltip message  */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String refPoTooltipMsg;
	
	/** The Tooltip alt text  */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String tooltipImgAltText;
	
	/** Reference PO Number Max limit  */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String refPoNumMaxLimit;
	
	/** The Select cards label  */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String selectCardLabel;
	
	/** The Available cards label  */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	public String availableCardsLabel;
	
	/** The check out ot consent url. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String checkOutOtConsentUrl;
	
	/** The check out ot consent data payload. */
	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String checkOutOtConsentDataPayload;
	
	/** The distributorDeliveryDate. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String distributorDeliveryDate;
	
	/** The In Stock. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String inStock;
	
	/** The Out of Stock. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String outOfStock;
	
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

	/** The Alert Heading. */
	@Inject
	@Via("resource")
	@Default(values = "Alert")
	public String alertHeading;
	
	/** The Description. */
	@Inject
	@Via("resource")
	@Default(values = "The promotions applied to the cart have been modified or are no longer valid. Please review before proceeding.")
	public String description;
	
	/** The Cancel Label. */
	@Inject
	@Via("resource")
	@Default(values = "Cancel")
	public String cancelLabel;
	
	/** The Back To Cart. */
	@Inject
	@Via("resource")
	@Default(values = "Back To Cart")
	public String backToCart;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The checkout labels. */
	private String checkoutLabels;

	/** The hybris site id. */
	private String hybrisSiteId;
	
	/** The checkout config. */
	private String checkoutConfig;

	/**  Add Address Labels. */
	private String addAddressLabels;
	
	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;
	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("CheckoutModel init() - start");
			checkoutLabels = checkoutLabels();
			ExcludeUtil excludeUtilObject = new ExcludeUtil();
			hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
			checkoutConfig=setCheckoutConfig(excludeUtilObject);
			addAddressLabels=setAddressLabel();
	}

	/**
	 * This method forms the json for checkout labels.
	 *
	 * @return the label json
	 */
	public String checkoutLabels() {

		JsonObject checkoutLabelsJson = new JsonObject();
		JsonObject distributorDetails = new JsonObject();
		JsonObject inActiveBilltoMsg = new JsonObject();
		JsonObject inActiveSoldtoPayerMsg = new JsonObject();
		JsonObject inActiveShiptoMsg = new JsonObject();
		JsonObject priceChangedJsonLabels = new JsonObject();

		checkoutLabelsJson.addProperty(CommonConstants.CHECKOUT, checkout);
		checkoutLabelsJson.addProperty(CommonConstants.NZ_PRICING_MESSAGE, nzPricingMessage);
		checkoutLabelsJson.addProperty(CommonConstants.SHIPPING_DETAILS, shippingDetails);
		checkoutLabelsJson.addProperty(CommonConstants.ATTENTION, attention);
		checkoutLabelsJson.addProperty(CommonConstants.SECONDARY_ATTENTION, secondaryAttention);
		checkoutLabelsJson.addProperty(CommonConstants.MANDATORY_FIELD_ERROR_MSG, CommonHelper.getLabel(CommonConstants.ATTENTION_FIELD_ERROR_MSG, currentPage));
		checkoutLabelsJson.addProperty(CommonConstants.ATTENTION_MAX_LIMIT, attentionMaxLimit);
		checkoutLabelsJson.addProperty(CommonConstants.ATTENTION_MAX_LIMIT_FOR_CA, attentionMaxLimitForCa);
		checkoutLabelsJson.addProperty(CommonConstants.ADD_ATTENTION_MAX_LIMIT_FOR_CA, additionalAttentionMaxLimitForCa);
		checkoutLabelsJson.addProperty(CommonConstants.ATTENTION_MAX_LIMIT_FOR_US, attentionMaxLimitForUs);
		checkoutLabelsJson.addProperty(CommonConstants.ATTENTION_MAX_LIMIT_ERROR_MSG, CommonHelper.getLabel(CommonConstants.ATTENTION_MAX_LIMIT_ERROR_MSG, currentPage));
		checkoutLabelsJson.addProperty(CommonConstants.INSTRUCTIONAL_POP_UP_MSG, CommonHelper.getLabel(CommonConstants.INSTRUCTIONAL_POP_UP_MSG, currentPage));
		checkoutLabelsJson.addProperty(CommonConstants.SHIPPING_ADDRESS, shippingAddress);
		checkoutLabelsJson.addProperty(CommonConstants.CHANGE_ADDRESS, changeAddress);
		checkoutLabelsJson.addProperty(CommonConstants.ADD_ADDRESS_HEADING, addAddress);
		checkoutLabelsJson.addProperty(CommonConstants.ADD_DROPSHIP_ADDRESS_LABEL, addDropshipAddrLabel);
		checkoutLabelsJson.addProperty(CommonConstants.PENDING_REVIEW_MSG, pendingReviewMsg);
		checkoutLabelsJson.addProperty(CommonConstants.DELIVER_OPTION, deliverOption);
		checkoutLabelsJson.addProperty(CommonConstants.OVERNIGHT, overnight);
		checkoutLabelsJson.addProperty(CommonConstants.INSIDE_DELIVERY, insideDelivery);
		checkoutLabelsJson.addProperty(CommonConstants.OVERNIGHT_SURCHARGE_APPLIED, overNightSurcharge);
		checkoutLabelsJson.addProperty(CommonConstants.INSIDE_SURCHARGE_APPLIED, insideDeliverySurcharge);
		checkoutLabelsJson.addProperty(CommonConstants.ADD_ADDRESS_NOTE, addressNote);
		checkoutLabelsJson.addProperty(CommonConstants.ADDRESS_LINE, addressLine);
		checkoutLabelsJson.addProperty(CommonConstants.BUILDING, building);
		checkoutLabelsJson.addProperty(CommonConstants.ROOM, room);
		checkoutLabelsJson.addProperty(CommonConstants.FLOOR, floor);
		checkoutLabelsJson.addProperty(CommonConstants.DEPARTMENT, department);
		checkoutLabelsJson.addProperty(CommonConstants.PHONE_NUMBER, phoneNumber);
		checkoutLabelsJson.addProperty(CommonConstants.CITY, city);
		checkoutLabelsJson.addProperty(CommonConstants.STATE, state);
		checkoutLabelsJson.addProperty(CommonConstants.ZIP_CODE, zipCode);
		checkoutLabelsJson.addProperty(CommonConstants.COUNTRY, country);
		checkoutLabelsJson.addProperty(CommonConstants.NICK_NAME, nickName);
		checkoutLabelsJson.addProperty(CommonConstants.ADD_ADDRESS_CANCEL, cancel);
		checkoutLabelsJson.addProperty(CommonConstants.CHANGE_ADDRESS_NOTE, changeAddressNote);
		checkoutLabelsJson.addProperty(CommonConstants.SEARCH_ADDRESS, searchAddress);
		checkoutLabelsJson.addProperty(CommonConstants.DEFAULT, defaultName);
		checkoutLabelsJson.addProperty(CommonConstants.SAVE, save);
		checkoutLabelsJson.addProperty(CommonConstants.EDIT, edit);
		checkoutLabelsJson.addProperty(CommonConstants.SHIPPING_METHOD, shippingMethod);
		checkoutLabelsJson.addProperty(CommonConstants.SAVE_AND_CONTINUE, saveAndContinue);
		checkoutLabelsJson.addProperty(CommonConstants.ADD_CARRIER_NUMBER, addCarrierNumber);
		checkoutLabelsJson.addProperty(CommonConstants.PAYMENT_DETAILS, paymentDetails);
		checkoutLabelsJson.addProperty(CommonConstants.PAYMENT_TYPE, paymentType);
		checkoutLabelsJson.addProperty(CommonConstants.PURCHASE_ORDER, purchaseOrder);
		checkoutLabelsJson.addProperty(CommonConstants.CREDIT_CARD, creditCard);
		checkoutLabelsJson.addProperty(CommonConstants.ENTER_PO_NUMBER, enterPONumber);
		checkoutLabelsJson.addProperty(CommonConstants.PO_TOOL_TIP, poToolTip);
		checkoutLabelsJson.addProperty(CommonConstants.SELECT_CARD, selectCard);
		checkoutLabelsJson.addProperty(CommonConstants.CHANGE, change);
		checkoutLabelsJson.addProperty(CommonConstants.ADD_NEW_CARD, addNewCard);
		checkoutLabelsJson.addProperty(CommonConstants.BILLING_DETAILS, billingDetails);
		checkoutLabelsJson.addProperty(CommonConstants.BILLING_ADDRESS, billingAddress);
		checkoutLabelsJson.addProperty(CommonConstants.SAME_AS_SHIPPING_ADDRESS, sameAsShippingAddress);
		checkoutLabelsJson.addProperty(CommonConstants.OTHER_DETAILS, otherDetails);
		checkoutLabelsJson.addProperty(CommonConstants.SPECIAL_INSTRUCTION, specialInstruction);
		checkoutLabelsJson.addProperty(CommonConstants.SPECIAL_INSTRUCTION_LBL, specialInstructionLbl);
		checkoutLabelsJson.addProperty(CommonConstants.EMAIL, email);
		checkoutLabelsJson.addProperty(CommonConstants.RECEIVE_ORDER_NOTIFICATION, receiveOrderNotification);
		checkoutLabelsJson.addProperty(CommonConstants.HAS_BOM_PRODUCT_MESSAGE, hasBomProductMsg );
		checkoutLabelsJson.addProperty(CommonConstants.EMAIL_VALIDATION_MSG, emailValidationMsg);
		checkoutLabelsJson.addProperty(CommonConstants.OTHER_NOTE, otherNote);
		checkoutLabelsJson.addProperty(CommonConstants.SAVE_AND_REVIEW, saveAndReview);
		checkoutLabelsJson.addProperty(CommonConstants.PRODUCTS_HEADING, orderSummary);
		checkoutLabelsJson.addProperty(CommonConstants.MAX_EMAIL_ID_ERROR_MSG, maxEmailIdsErrorMsg);
		checkoutLabelsJson.addProperty(CommonConstants.REQUEST_SAME_LOT_NUM_MSG, requestSameLotNumMsg);
		
		String estDeliveryDate = CommonHelper.getLabel(CommonConstants.EST_DELIVERY_DATE_LABEL, currentPage);
		String quantityLabel = CommonHelper.getLabel(CommonConstants.CART_QUANTITY_VALUE, currentPage);
		String pricePerUnit = CommonHelper.getLabel(CommonConstants.PRICE_PER_UNIT_LABEL, currentPage);
		String totalPriceLabel = CommonHelper.getLabel(CommonConstants.TOTAL_PRICE_LABEL, currentPage);
		String subtotal = CommonHelper.getLabel(CommonConstants.SUBTOTAL_LABEL, currentPage);
		String shippingAndHandling = CommonHelper.getLabel(CommonConstants.SHIPPING_AND_HANDLING_LABEL, currentPage);
		String taxes = CommonHelper.getLabel(CommonConstants.TAXES_LABEL, currentPage);
		String surcharge = CommonHelper.getLabel(CommonConstants.SURCHARGE_LABEL, currentPage);
		String promotion = CommonHelper.getLabel(CommonConstants.PROMOTION_LABEL, currentPage);
		String emailValidationError = CommonHelper.getLabel(CommonConstants.EMAIL_VALIDATION_ERROR_MSG, currentPage);
		
		checkoutLabelsJson.addProperty(CommonConstants.EST_DELIVERY_DATE_CHECKOUT, estDeliveryDate);
		checkoutLabelsJson.addProperty(CommonConstants.TOTAL_PRICE, totalPriceLabel);
		checkoutLabelsJson.addProperty(CommonConstants.PRICE_PER_UNIT_LABEL, pricePerUnit);
		checkoutLabelsJson.addProperty(CommonConstants.QUANTITY, quantityLabel);		
		checkoutLabelsJson.addProperty(CommonConstants.SUBTOTAL_LABEL, subtotal);
		checkoutLabelsJson.addProperty(CommonConstants.SHIPPING_AND_HANDLING_LABEL, shippingAndHandling);
		checkoutLabelsJson.addProperty(CommonConstants.TAXES_LABEL, taxes);
		checkoutLabelsJson.addProperty(CommonConstants.SURCHARGE_LABEL, surcharge);
		checkoutLabelsJson.addProperty(CommonConstants.PROMOTION_LABEL, promotion);
		
		checkoutLabelsJson.addProperty(CommonConstants.YOUR_PRICE_LABEL_ATC,
				CommonHelper.getLabel(CommonConstants.YOUR_PRICE_VALUE, currentPage));
		checkoutLabelsJson.addProperty(CommonConstants.LIST_PRICE_LABEL_ATC,
				CommonHelper.getLabel(CommonConstants.LIST_PRICE_VALUE, currentPage));
		

		checkoutLabelsJson.addProperty(CommonConstants.ADD_ADDRESS_LABEL,addAddressHeading);
		checkoutLabelsJson.addProperty(CommonConstants.SET_AS_DEFAULT,setAsDefaultLabel);
		checkoutLabelsJson.addProperty(CommonConstants.ADD_ADDRESS_CTA,addAddressCta);
		
		checkoutLabelsJson.addProperty(CommonConstants.CHANGE_BILLING_ADDRESS_LABEL,changeBillingAddressLabels);
		checkoutLabelsJson.addProperty(CommonConstants.SHIPTO_NUMBER_LABEL,shipToNumberLabel);
		checkoutLabelsJson.addProperty(CommonConstants.BILLTO_NUMBER_LABEL,billToNumberLabel);
		checkoutLabelsJson.addProperty(CommonConstants.PO_NUMBER_ERROR_MESSAGE,poNumberErrorMsg);
		checkoutLabelsJson.addProperty(CommonConstants.PONUMBER_LABEL,poNumberLabel);
		checkoutLabelsJson.addProperty(CommonConstants.EXPIRATION_WARNING_MESSAGE,expirationWarningMessage);
		checkoutLabelsJson.addProperty(CommonConstants.EXPIRED_MESSAGE,expiredMessage);
		checkoutLabelsJson.addProperty(CommonConstants.DEFAULT_LABEL,defaultLabel);
		checkoutLabelsJson.addProperty(CommonConstants.EXPIRY_DATE,expiryDateLabel);
		checkoutLabelsJson.addProperty(CommonConstants.NAME_ON_CARD_LABEL,nameOnCardLabel);
		checkoutLabelsJson.addProperty(CommonConstants.CVV_LABEL,cvvLabel);
		checkoutLabelsJson.addProperty(CommonConstants.REFERENCE_PO_NUMBER,referencePoNumber);
		checkoutLabelsJson.addProperty(CommonConstants.CARD_FOR_PAYMENT_LABEL,cardforPaymentLabel);
		checkoutLabelsJson.addProperty(CommonConstants.BILL_TO_ADDRESS_LABEL,billToAddressLabel);
		checkoutLabelsJson.addProperty(CommonConstants.FAVORITE_BILLING_ADDRESS,favoriteBillingAddress);
		checkoutLabelsJson.addProperty(CommonConstants.ENDING_IN,endingInLabel);
		checkoutLabelsJson.addProperty(CommonConstants.EMAIL_VALIDATION_ERROR_MSG,emailValidationError);
		checkoutLabelsJson.addProperty(CommonConstants.CC_SUCCESS_MESSAGE,ccSuccessMessage);
		checkoutLabelsJson.addProperty(CommonConstants.VISA_CARD_IMG,visaCardImgPath);
		checkoutLabelsJson.addProperty(CommonConstants.VISA_CARD_IMG_ALT_TEXT,visaCardImgAltText);
		checkoutLabelsJson.addProperty(CommonConstants.MASTER_CARD_IMG,masterCardImgPath);
		checkoutLabelsJson.addProperty(CommonConstants.MASTER_CARD_IMG_ALT_TEXT,masterCardImgAltText);
		checkoutLabelsJson.addProperty(CommonConstants.AMEX_CARD_IMG,amexCardImgPath);
		checkoutLabelsJson.addProperty(CommonConstants.AMEX_CARD_IMG_ALT_TEXT,amexCardAltText);
		checkoutLabelsJson.addProperty(CommonConstants.WARNING_ICON_PATH,warningIconPath);
		checkoutLabelsJson.addProperty(CommonConstants.WARNING_ICON_ALT_TEXT,warningIconAltText);
		checkoutLabelsJson.addProperty(CommonConstants.SUCCESSFUL_ADDRESS_TEXT,successfulAddressText);
		checkoutLabelsJson.addProperty(CommonConstants.BILL_TO_PENDING_REVIEW_MSG,billToPendingReviewMsg);
		
		checkoutLabelsJson.addProperty(CommonConstants.BACK_TO_CART_ALERT_MSG,backToCartAlertMsg);
		checkoutLabelsJson.addProperty(CommonConstants.DISMISS_CTA,dismissCta);
		checkoutLabelsJson.addProperty(CommonConstants.BACK_TO_CART_TITLE,backToCartTitle);
		checkoutLabelsJson.addProperty(CommonConstants.BACK_TO_CART_CTA,backToCartCta);
		checkoutLabelsJson.addProperty(CommonConstants.BACK_ICON_PATH,backIconPath);
		checkoutLabelsJson.addProperty(CommonConstants.BACK_ICON_ALT_TEXT,backIconAltText);
		checkoutLabelsJson.addProperty(CommonConstants.REVIEW_CTA_LABEL,reviewCtaLabel);
		checkoutLabelsJson.addProperty(CommonConstants.UP_ARROW_ALT_TEXT,upArrowAltText);
		checkoutLabelsJson.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT,downArrowAltText);
		checkoutLabelsJson.addProperty(CommonConstants.REMOVE_CTA_LABEL,removeCtaLabel);
		checkoutLabelsJson.addProperty(CommonConstants.ALERT_HEADING_LABEL,alertHeadingLabel);
		checkoutLabelsJson.addProperty(CommonConstants.ALERT_MESSAGE,alertMessage);
		checkoutLabelsJson.addProperty(CommonConstants.DUPLICATE_ORDER_HEADING_LABEL,duplicateOrderHeadingLabel);
		checkoutLabelsJson.addProperty(CommonConstants.DUPLICATE_ORDER_TEXT1,duplicateOrderText1);
		checkoutLabelsJson.addProperty(CommonConstants.DUPLICATE_ORDER_TEXT2,duplicateOrderText2);

		checkoutLabelsJson.addProperty(CommonConstants.PROMO_CODE_LABEL,CommonHelper.getLabel(CommonConstants.PROMO_CODE, currentPage));
		checkoutLabelsJson.addProperty(CommonConstants.YOU_SAVED_LABEL,CommonHelper.getLabel(CommonConstants.YOU_SAVED, currentPage));
		checkoutLabelsJson.addProperty(CommonConstants.ON_LABEL,CommonHelper.getLabel(CommonConstants.ON, currentPage));
		checkoutLabelsJson.addProperty(CommonConstants.VAT_EXEMPT_MSG,vatExemptMsg);
		
		checkoutLabelsJson.addProperty(CommonConstants.REF_PO_NUM_PLACEHOLDER,refPoNumPlaceholder);
		checkoutLabelsJson.addProperty(CommonConstants.REF_PO_TOOLTIP_MSG,refPoTooltipMsg);
		checkoutLabelsJson.addProperty(CommonConstants.TOOL_TIP_IMAGE_ALT_TEXT,tooltipImgAltText);
		checkoutLabelsJson.addProperty(CommonConstants.REF_PO_NUM_MAX_LIMIT,refPoNumMaxLimit);
		checkoutLabelsJson.addProperty(CommonConstants.SELECT_CARD_LABEL,selectCardLabel);
		checkoutLabelsJson.addProperty(CommonConstants.AVAILABLE_CARDS_LABEL,availableCardsLabel);
		
		checkoutLabelsJson.addProperty(CommonConstants.REVIEW_TAX_EXEMPT_MSG,reviewTaxExemptMsg);
		
		distributorDetails.addProperty(CommonConstants.BILLING_DETAILS_HEADING, billingDetailsHeading);
		distributorDetails.addProperty(CommonConstants.HEADING, headingLabel);
		distributorDetails.addProperty(CommonConstants.NAME_LABEL, nameLabel);
		distributorDetails.addProperty(CommonConstants.DISTRIBUTOR_ADDRESS_LABEL, distributorAddressLabel);
		distributorDetails.addProperty(CommonConstants.BUTTON_TEXT, buttonText);
		
		checkoutLabelsJson.add(CommonConstants.DISTRIBUTOR_DETAILS, distributorDetails);

		inActiveBilltoMsg.addProperty(CommonConstants.ERROR_ICON,inactiveBillToErrorIcon);
		inActiveBilltoMsg.addProperty(CommonConstants.ERROR_ICON_ALT_TEXT,inactiveBillToErrorAltText);
		inActiveBilltoMsg.addProperty(CommonConstants.ERROR_MSG,inactiveBillToErrorMsg);
		checkoutLabelsJson.add(CommonConstants.INACTIVE_BILL_TO_MSG, inActiveBilltoMsg);

		inActiveSoldtoPayerMsg.addProperty(CommonConstants.ERROR_ICON,inactiveSoldToErrorIcon);
		inActiveSoldtoPayerMsg.addProperty(CommonConstants.ERROR_ICON_ALT_TEXT,inactiveSoldToErrorAltText);
		inActiveSoldtoPayerMsg.addProperty(CommonConstants.ERROR_MSG,inactiveSoldToErrorMsg);
		checkoutLabelsJson.add(CommonConstants.INACTIVE_SOLD_TO_PAYER_MSG, inActiveSoldtoPayerMsg);

		String inactiveShipToErrorIcon = CommonHelper.getLabel(CommonConstants.INACTIVE_SHIP_TO_ERROR_ICON, currentPage);
		String inactiveShipToErrorAltText = CommonHelper.getLabel(CommonConstants.INACTIVE_SHIP_TO_ERROR_ICON_ALT_TEXT, currentPage);
		String inactiveShipToErrorMsg = CommonHelper.getLabel(CommonConstants.INACTIVE_SHIP_TO_ERROR_MSG, currentPage);
		inActiveShiptoMsg.addProperty(CommonConstants.ERROR_ICON,inactiveShipToErrorIcon);
		inActiveShiptoMsg.addProperty(CommonConstants.ERROR_ICON_ALT_TEXT,inactiveShipToErrorAltText);
		inActiveShiptoMsg.addProperty(CommonConstants.ERROR_MSG,inactiveShipToErrorMsg);
		checkoutLabelsJson.add(CommonConstants.INACTIVE_SHIP_TO_MSG, inActiveShiptoMsg);
		
		checkoutLabelsJson.addProperty("distributorDeliveryDate",distributorDeliveryDate);
		checkoutLabelsJson.addProperty("inStock",inStock);
		checkoutLabelsJson.addProperty("outOfStock",outOfStock);
		checkoutLabelsJson.addProperty(CommonConstants.INQUIRE, inquireLabel);
		checkoutLabelsJson.addProperty("dangerousGoodsMsg", dangerousGoodsMsg);
		checkoutLabelsJson.addProperty("dangerousGoodInfoAltText", dangerousGoodInfoAltText);
		
		priceChangedJsonLabels.addProperty(CommonConstants.HEADING, alertHeading);
		priceChangedJsonLabels.addProperty(CommonConstants.DESCRIPTION, description);
		priceChangedJsonLabels.addProperty(CommonConstants.CANCEL, cancelLabel);
		priceChangedJsonLabels.addProperty(CommonConstants.BACK_TO_CART, backToCart);
		checkoutLabelsJson.add(CommonConstants.PRICE_CHANGED, priceChangedJsonLabels);
		
		return checkoutLabelsJson.toString();
	}
	
	/**
	 * Sets the checkout config.
	 *
	 * @param excludeUtilObject the exclude util object
	 * @return the string
	 */
	private String setCheckoutConfig(ExcludeUtil excludeUtilObject)
	{
		JsonObject config = new JsonObject();
		
		String addNewAddress = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.addNewAddressEndPoint(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Payload getAddAddressPayload = new Payload(addNewAddress, HttpConstants.METHOD_POST);
		
		String getAddressesList = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getShipToAddressConfig(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getAddressesListPayload = new Payload(getAddressesList, HttpConstants.METHOD_GET);
		
		String getShippingDetails = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getShippingDetailsConfig(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getShippingDetailsPayload = new Payload(getShippingDetails, HttpConstants.METHOD_GET);
		
		String getBillingDetails = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getBillingDetailsConfig(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getBillingDetailsPayload = new Payload(getBillingDetails, HttpConstants.METHOD_GET);
		
		String setBillingDetails = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getBillingDetailsConfig(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload setBillingDetailsPayload = new Payload(setBillingDetails, HttpConstants.METHOD_POST);
		
		String setShippingDetails = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getShippingDetailsConfig(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload setShippingDetailsPayload = new Payload(setShippingDetails, HttpConstants.METHOD_POST);
		
		String getCartData = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getCartWithIdentifier(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getCartDataPayload = new Payload(getCartData, HttpConstants.METHOD_GET);
		
		String getPaymentDetails = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getPaymentDetails(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getPaymentDetailsPayload = new Payload(getPaymentDetails, HttpConstants.METHOD_GET);
		
		String getPaymentCardsList = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getAllPaymentDetails(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getPaymentCardsListPayload = new Payload(getPaymentCardsList, HttpConstants.METHOD_GET);
		
		String setPaymentDetails = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getPaymentDetails(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload setPaymentDetailsPayload = new Payload(setPaymentDetails, HttpConstants.METHOD_PUT);
		
		String getOtherDetails = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getOtherDetails(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getOtherDetailsPayload = new Payload(getOtherDetails, HttpConstants.METHOD_GET);
		
		String setOtherDetails = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getOtherDetails(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload setOtherDetailsPayload = new Payload(setOtherDetails, HttpConstants.METHOD_POST);
		
		String placeOrderConfig = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.placeOrderConfig(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload placeOrderConfigPayload = new Payload(placeOrderConfig, HttpConstants.METHOD_POST);
		
		String setDropshipDetails = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getDropshipDetailsEndpoint(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload setDropshipDetailsPayload = new Payload(setDropshipDetails, HttpConstants.METHOD_POST);
		
		String getProductAnnouncements = bdbApiEndpointService.getProductAnnouncements();
		Payload getProductAnnouncementsPayload = new Payload(getProductAnnouncements, HttpConstants.METHOD_POST);
		
        JsonParser parser = new JsonParser();
    	JsonElement dataPayload;
    	try {
    		dataPayload = parser.parse(checkOutOtConsentDataPayload);
		} catch (JsonParseException e) {
			logger.error("JsonParseException {}", e.getMessage());
			dataPayload = new JsonObject();
		}
        JsonObject checkOutOTConsentData = new JsonObject();
        checkOutOTConsentData.addProperty(CommonConstants.URL, checkOutOtConsentUrl);
        checkOutOTConsentData.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
        checkOutOTConsentData.add(CommonConstants.DATA_KEY, dataPayload);
        
        String userSettingsEndpoint = StringUtils.replace(
                bdbApiEndpointService.getUserSettingsEndpoint(),
                CommonConstants.HYBRIS_SITE_LITERAL,
                hybrisSiteId);
        Payload userSettingsRequestPayload = new Payload(
                bdbApiEndpointService.getBDBHybrisDomain() + userSettingsEndpoint,
                HttpConstants.METHOD_GET);
        PayloadConfig getUserSettingsConfig = new PayloadConfig(userSettingsRequestPayload);
		
		config.add(CommonConstants.GET_CART_WITH_IDENTIFIER,gson.toJsonTree(getCartDataPayload));
		config.add(CommonConstants.ADD_NEW_ADDRESS,gson.toJsonTree(getAddAddressPayload));
		config.add(CommonConstants.GET_ADDRESSES_LIST,gson.toJsonTree(getAddressesListPayload));
		config.add(CommonConstants.GET_SHIPPING_DETAILS,gson.toJsonTree(getShippingDetailsPayload));
		config.add(CommonConstants.SET_SHIPPING_DETAILS,gson.toJsonTree(setShippingDetailsPayload));
		config.add(CommonConstants.GET_PAYMENT_DETAILS,gson.toJsonTree(getPaymentDetailsPayload));
		config.add(CommonConstants.GET_PAYMENT_CARDS_LIST,gson.toJsonTree(getPaymentCardsListPayload));
		config.add(CommonConstants.SET_PAYMENT_DETAILS,gson.toJsonTree(setPaymentDetailsPayload));
		config.add(CommonConstants.GET_BILLING_DETAILS,gson.toJsonTree(getBillingDetailsPayload));
		config.add(CommonConstants.SET_BILLING_DETAILS,gson.toJsonTree(setBillingDetailsPayload));
		config.add(CommonConstants.GET_OTHER_DETAILS,gson.toJsonTree(getOtherDetailsPayload));
		config.add(CommonConstants.SET_OTHER_DETAILS,gson.toJsonTree(setOtherDetailsPayload));
		config.add(CommonConstants.PLACE_ORDER_CONFIG,gson.toJsonTree(placeOrderConfigPayload));
		config.add(CommonConstants.GET_PRODUCT_ANNOUNCEMENTS,gson.toJsonTree(getProductAnnouncementsPayload));
		config.add(CommonConstants.SET_DROPSHIP_DETAILS,gson.toJsonTree(setDropshipDetailsPayload));
		config.add(CommonConstants.CHECKOUT_OT_CONSENT_DATA, checkOutOTConsentData);
		config.add(CommonConstants.GET_USERS_SETTINGS_CONFIG, gson.toJsonTree(getUserSettingsConfig));
		return config.toString();
	}

	/**
	 * Sets the address label.
	 *
	 * @return AddAddressLabel
	 */
	private String setAddressLabel()
	{
		JsonObject address=new JsonObject();
		address.addProperty(CommonConstants.ADD_ADDRESS_COMPANY_LABEL,companyLabel);
		address.addProperty(CommonConstants.ADDRESS_LINE_1,addressLine);
		address.addProperty(CommonConstants.ADDRESS_LINE_2,addressLineSecond);
		address.addProperty(CommonConstants.STREET_ADDRESS,streetLabel);
		address.addProperty(CommonConstants.DETAILED_ADDRESS,detailedAddress);
		address.addProperty(CommonConstants.ADDRESS,genericAddressLabel);
		address.addProperty(CommonConstants.MANDATORY_FIELD_ERROR,mandatoryFieldError);
		address.addProperty(CommonConstants.ADD_ADDRESS_BUILDING,building);
		address.addProperty(CommonConstants.ADD_ADDRESS_FLOOR,floor);
		address.addProperty(CommonConstants.ADD_ADDRESS_ROOM,room);
		address.addProperty(CommonConstants.ADD_ADDRESS_DEPARTMENT,department);
		address.addProperty(CommonConstants.ADD_ADDRESS_PHONE_NUMBER,phoneNumber);
		address.addProperty(CommonConstants.ADD_ADDRESS_PHONE_NUMBER_ERROR,phoneNumberError);
		address.addProperty(CommonConstants.ADD_ADDRESS_DISTRICT_LABEL,districtLabel);
		address.addProperty(CommonConstants.ADD_ADDRESS_CITY_LABEL,city);
		address.addProperty(CommonConstants.ADD_ADDRESS_STATE_LABEL,state);
		address.addProperty(CommonConstants.ADD_ADDRESS_PROVINCE_LABEL,provinceLabel);
		address.addProperty(CommonConstants.ADD_ADDRESS_POSTALCODE_LABEL,postalLabel);
		address.addProperty(CommonConstants.ADD_ADDRESS_PINCODE_LABEL,pinLabel);
		address.addProperty(CommonConstants.ADD_ADDRESS_ZIPCODE_LABEL,zipCode);
		address.addProperty(CommonConstants.ADD_ADDRESS_COUNTRY_LABEL,country);
		address.addProperty(CommonConstants.ACCOUNT_NUMBER_LABEL, accountNumberLabel);
		address.addProperty(CommonConstants.VAT_NUMBER, vatNumberLabel);
		
		
		
		
		return address.toString();
	}

	/**
	 * Gets the my cart labels.
	 *
	 * @return the my cart labels
	 */
	public String getCheckoutLabels() {
		return checkoutLabels;
	}

	/**
	 *  Checkout Config.
	 *
	 * @return checkout Config
	 */
	public String getCheckoutConfig() {
		return checkoutConfig;
	}

	/**
	 * Gets the adds the address labels.
	 *
	 * @return Add Address Labels
	 */
	public String getAddAddressLabels() {
		return addAddressLabels;
	}
}