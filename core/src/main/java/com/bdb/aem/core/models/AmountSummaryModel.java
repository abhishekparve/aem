package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The Class Amount Summary Model.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AmountSummaryModel {

	/** The logger. */
	protected static final Logger logger = LoggerFactory.getLogger(AmountSummaryModel.class);

	/** The Amount Summary. */
	@Inject
	@Via("resource")
	public String heading;

	/** The shipping handling. */
	@Inject
	@Via("resource")
	public String shippingHandling;

	/** The taxes. */
	@Inject
	@Via("resource")
	public String taxes;

	/** The surcharge. */
	@Inject
	@Via("resource")
	public String surcharge;

	/** The total. */
	@Inject
	@Via("resource")
	public String total;

	/** The amount summary CTA btn. */
	@Inject
	@Via("resource")
	public String amountSummaryCTABtn;

	/** The request quote CTA link. */
	@Inject
	@Via("resource")
	public String requestQuoteCTALink;

	/** The disclaimer. */
	@Inject
	@Via("resource")
	public String disclaimer;

	/** The terms of use. */
	@Inject
	@Via("resource")
	public String termsOfUse;

	/** The tax disclaimer text. */
	@Inject
	@Via("resource")
	public String taxDisclaimerText;

	/** The vat exempt. */
	@Inject
	@Via("resource")
	public String vatExempt;

	/** The surcharges tool tip. */
	@Inject
	@Via("resource")
	public String surchargesToolTip;

	/** The tax tool tip. */
	@Inject
	@Via("resource")
	public String taxToolTip;

	/** The tool tip alt text. */
	@Inject
	@Via("resource")
	public String toolTipAltText;

	/** The no address label. */
	@Inject
	@Via("resource")
	public String noAddressLabel;

	/** The select address CTA label. */
	@Inject
	@Via("resource")
	public String selectAddressCTALabel;

	/** The select address title. */
	@Inject
	@Via("resource")
	public String selectAddressTitle;

	/** The linked address title. */
	@Inject
	@Via("resource")
	public String linkedAddressTitle;

	/** The change address CTA title. */
	@Inject
	@Via("resource")
	public String changeAddressCTATitle;

	/** The ship to label. */
	@Inject
	@Via("resource")
	public String shipToLabel;
	
	/** The sold to label. */
	@Inject
	@Via("resource")
	public String soldToLabel;

	/** The shipping address label. */
	@Inject
	@Via("resource")
	public String shippingAddressLabel;

	/** The default label. */
	@Inject
	@Via("resource")
	public String defaultLabel;

	/** The ship to number label. */
	@Inject
	@Via("resource")
	public String shipToNumberLabel;
	
	/** The sold to number label. */
	@Inject
	@Via("resource")
	public String soldToNumberLabel;

	/** The change address. */
	@Inject
	@Via("resource")
	public String changeAddress;

	/** The title. */
	@Inject
	@Via("resource")
	public String title;

	/** The search place holder. */
	@Inject
	@Via("resource")
	public String searchPlaceHolder;

	/** The favorite address. */
	@Inject
	@Via("resource")
	public String favoriteAddress;

	/** The fav icon. */
	@Inject
	@Via("resource")
	public String favIcon;

	/** The fav icon alt txt. */
	@Inject
	@Via("resource")
	public String favIconAltTxt;

	/** The up arrow icon. */
	@Inject
	@Via("resource")
	public String upArrowIcon;

	/** The down arrow icon. */
	@Inject
	@Via("resource")
	public String downArrowIcon;

	/** The total promo savings. */
	@Inject
	@Via("resource")
	public String totalPromoSavings;

	/** The save CTA. */
	@Inject
	@Via("resource")
	public String saveCTA;

	/** The cancel CTA. */
	@Inject
	@Via("resource")
	public String cancelCTA;

	/** The no search results. */
	@Inject
	@Via("resource")
	public String noSearchResults;

	/** The promotion. */
	@Inject
	@Via("resource")
	public String promotion;

	/** The confirm order. */
	@Inject
	@Via("resource")
	public String confirmOrder;

	/** The src. */
	@Inject
	@Via("resource")
	public String src;

	/** The alt text. */
	@Inject
	@Via("resource")
	public String altText;

	/** The total promotional src. */
	@Inject
	@Via("resource")
	@SerializedName("totalPromotionalSrc")
	public String totalPromotionalSrc;

	/** The total promotion alt text. */
	@Inject
	@Via("resource")
	@SerializedName("totalPromotionAltText")
	public String totalPromotionAltText;

	/** The message. */
	@Inject
	@Via("resource")
	public String message;

	/** The oem notification message. */
	@Inject
	@Via("resource")
	public String oemNotificationMessage;

	/** The oem notification icon. */
	@Inject
	@Via("resource")
	public String oemNotificationIcon;

	/** The oem notification icon alt text. */
	@Inject
	@Via("resource")
	public String oemNotificationIconAltText;
	
	/** The Tax Exempt Icon Source.*/
	@Inject
	@Via("resource")
	public String taxExemptIconSrc;
	
	/** The Tax Exempt Icon alt text. */
	@Inject
	@Via("resource")
	public String taxExemptIconAltText;
	
	/** The Tax Exempt Message. */
	@Inject
	@Via("resource")
	public String taxExemptMsg;
	
	@Inject
	@Via("resource")
	public String transmitRequest;

	@Inject
	@Via("resource")
	public String cancelCart;

	/** The Promotions Disclaimer. */
	@Inject
	@Via("resource")
	public String promotionsDisclaimer;
	
	/** The China Checkout Free Label. */
	@Inject
	@Via("resource")
	public String free;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The resolver factory. */
	@Inject
	ResourceResolverFactory resolverFactory;

	/** The amount summary labels. */
	private String amountSummary;

	/** The amount summary config. */
	private String myCartAmountSummaryConfig;

	/** The amount summary labels. */
	private String shipTo;

	/** The amount summary config. */
	private String shipToConfig;

	/** The Ship to address labels. */
	@Getter
	private String shipToAddressLabels;

	/** The Checkout amountSummary labels. */
	@Getter
	private String amountSummaryForCheckout;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
	
	/** The resource resolver. */
	ResourceResolver resourceResolver;
	
	/** The resource */
	@Inject
	private Resource resource;

	/** The Order Confirmation. */
	private String orderConfirmationAmountSummary;

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("AmountSummaryModel init() - start");
		try {
			resourceResolver = resource.getResourceResolver();
			amountSummary = amountSummaryLabels();
			amountSummaryForCheckout = amountSummaryCheckoutLabels();
			myCartAmountSummaryConfig = amountSummaryConfigs();
			shipTo = shipToLabels();
			shipToConfig = shipToConfigs();
			shipToAddressLabels = getShipingAddressLabels();
			orderConfirmationAmountSummary = getOrderConfirmationAmountSummaryJson();
		} catch (IOException e) {
			logger.error("Exception in inits method {}", e.getMessage());
		}

	}

	/**
	 * getShippingAddress labels
	 * 
	 * @return
	 */
	private String getShipingAddressLabels() {
		JsonObject shippingAddress = new JsonObject();
		JsonObject addressLabels = new JsonObject();

		addressLabels.addProperty(CommonConstants.SHIP_TO_LBL, shipToLabel);
		addressLabels.addProperty(CommonConstants.SHIP_TO_NUMBER_LBL, shipToNumberLabel);
		addressLabels.addProperty(CommonConstants.SOLD_TO_LBL, soldToLabel);
		addressLabels.addProperty(CommonConstants.SOLD_TO_NUMBER, soldToNumberLabel);
		shippingAddress.add(CommonConstants.ADDRESS_LABELS, addressLabels);

		return shippingAddress.toString();
	}

	/**
	 * This method forms the json for amount summary labels.
	 *
	 * @return the label json
	 * @throws IOException 
	 */
	public String amountSummaryLabels() throws IOException {

		JsonObject amountSummaryLabels = new JsonObject();
		JsonObject icon = new JsonObject();
		JsonObject info = new JsonObject();
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_SUBTOTAL,
				CommonHelper.getLabel(CommonConstants.AMOUNT_SUBTOTAL, currentPage));
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_HEADING, heading);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_SHIP_HANDLING, shippingHandling);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TAXES, taxes);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_SURCHARGE, surcharge);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TOTAL, total);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_SUMMARY_CTA_BTN, amountSummaryCTABtn);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_REQUEST_QUOTE_CTA_LINK, requestQuoteCTALink);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_DISCLAIMER, disclaimer);
		if(StringUtils.isNotEmpty(termsOfUse)) {
			amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TERMS_OF_USE, CommonHelper.HandleRTEAnchorLink(termsOfUse, externalizerService, resourceResolver,StringUtils.EMPTY));
		}else {
			amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TERMS_OF_USE,StringUtils.EMPTY);
		}
		
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TAX_DISCLAIMER_TEXT, taxDisclaimerText);
		String region = CommonHelper.getRegion(currentPage);
		if(null != region && region.equals("eu")) {
			amountSummaryLabels.addProperty(CommonConstants.AMOUNT_VAT_EXEMPT, vatExempt);
		}		
		
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TAX_TOOLTIP, taxToolTip);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_SURCHARGES_TOOLTIP, surchargesToolTip);
		
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TOOLTIP_ALT_TEXT, toolTipAltText);
		amountSummaryLabels.addProperty(CommonConstants.TOTAL_PROMO_SAVINGS, totalPromoSavings);

		amountSummaryLabels.addProperty(CommonConstants.PROMOTION, promotion);
		amountSummaryLabels.addProperty(CommonConstants.PROMOTIONS_DISCLAIMER, promotionsDisclaimer);
		amountSummaryLabels.addProperty(CommonConstants.TRANSMIT_REQUEST, transmitRequest);
		amountSummaryLabels.addProperty(CommonConstants.CANCEL_CART, cancelCart);

		String grantAmountUsed = CommonHelper.getLabel(CommonConstants.GRANT_AMOUNT_USED, currentPage);
		amountSummaryLabels.addProperty(CommonConstants.GRANT_AMOUNT_USED, grantAmountUsed);

		icon.addProperty(CommonConstants.SRC, src);
		icon.addProperty(CommonConstants.ALT_TEXT, altText);
		info.addProperty(CommonConstants.MESSAGE, message);
		info.addProperty(CommonConstants.TOTAL_PROMOTIONAL_ICON, totalPromotionalSrc);
		info.addProperty(CommonConstants.TOTAL_PROMOTIONAL_ICON_ALT, totalPromotionAltText);

		info.add(CommonConstants.ICON, icon);
		amountSummaryLabels.add(CommonConstants.INFO, info);

		return amountSummaryLabels.toString();
	}

	/**
	 * This method forms the json for amount summary labels for checkout.
	 *
	 * @return the label json
	 * @throws IOException 
	 */
	public String amountSummaryCheckoutLabels() throws IOException {

		JsonObject amountSummaryLabels = new JsonObject();
		JsonObject oemNotification = new JsonObject();
		JsonObject oemIcon = new JsonObject();
		JsonObject icon = new JsonObject();
		JsonObject info = new JsonObject();
		JsonObject taxExempt = new JsonObject();
		JsonObject taxExemptIcon = new JsonObject();
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_SUBTOTAL,
				CommonHelper.getLabel(CommonConstants.AMOUNT_SUBTOTAL, currentPage));
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_HEADING, heading);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_SHIP_HANDLING, shippingHandling);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TAXES, taxes);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_SURCHARGE, surcharge);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TOTAL, total);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_SUMMARY_CTA_BTN, amountSummaryCTABtn);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_DISCLAIMER, disclaimer);
		if(StringUtils.isNotEmpty(termsOfUse)) {
			amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TERMS_OF_USE, CommonHelper.HandleRTEAnchorLink(termsOfUse, externalizerService, resourceResolver,StringUtils.EMPTY));
		}else {
			amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TERMS_OF_USE,StringUtils.EMPTY);
		}
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TAX_DISCLAIMER_TEXT, taxDisclaimerText);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_SURCHARGES_TOOLTIP, surchargesToolTip);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TAX_TOOLTIP, taxToolTip);
		amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TOOLTIP_ALT_TEXT, toolTipAltText);
		amountSummaryLabels.addProperty(CommonConstants.TOTAL_PROMO_SAVINGS, totalPromoSavings);
		String region = CommonHelper.getRegion(currentPage);
		if(null != region && region.equals("eu")) {
			amountSummaryLabels.addProperty(CommonConstants.AMOUNT_VAT_EXEMPT, vatExempt);
			amountSummaryLabels.addProperty(CommonConstants.AMOUNT_TAX_TOOLTIP, taxToolTip);
		}

		amountSummaryLabels.addProperty(CommonConstants.PROMOTION, promotion);
		amountSummaryLabels.addProperty(CommonConstants.CONFIRM_ORDER, confirmOrder);
		String grantAmountUsed = CommonHelper.getLabel(CommonConstants.GRANT_AMOUNT_USED, currentPage);
		amountSummaryLabels.addProperty(CommonConstants.GRANT_AMOUNT_USED, grantAmountUsed);

		icon.addProperty(CommonConstants.SRC, src);
		icon.addProperty(CommonConstants.ALT_TEXT, altText);
		info.addProperty(CommonConstants.MESSAGE, message);
		info.addProperty(CommonConstants.TOTAL_PROMOTIONAL_ICON, totalPromotionalSrc);
		info.addProperty(CommonConstants.TOTAL_PROMOTIONAL_ICON_ALT, totalPromotionAltText);

		info.add(CommonConstants.ICON, icon);
		amountSummaryLabels.add(CommonConstants.INFO, info);

		oemIcon.addProperty(CommonConstants.SRC, oemNotificationIcon);
		oemIcon.addProperty(CommonConstants.ALT_TEXT, oemNotificationIconAltText);
		oemNotification.add(CommonConstants.ICON, oemIcon);
		oemNotification.addProperty(CommonConstants.MESSAGE, oemNotificationMessage);
		amountSummaryLabels.add(CommonConstants.OEM_NOTIFICATION, oemNotification);
		
		taxExemptIcon.addProperty(CommonConstants.SRC, taxExemptIconSrc);
		taxExemptIcon.addProperty(CommonConstants.ALT_TEXT, taxExemptIconAltText);
		taxExempt.add(CommonConstants.ICON, taxExemptIcon);
		taxExempt.addProperty(CommonConstants.MESSAGE, taxExemptMsg);
		
		amountSummaryLabels.add(CommonConstants.TAX_EXEMPT_NOTIFICATION, taxExempt);
		
		amountSummaryLabels.addProperty(CommonConstants.FREE, free);
		
		return amountSummaryLabels.toString();
	}

	/**
	 * This method forms the json for ship to labels.
	 *
	 * @return the label json
	 */
	public String shipToLabels() {

		JsonObject shipToLabels = new JsonObject();
		JsonObject addressLabels = new JsonObject();
		JsonObject searchAddress = new JsonObject();
		JsonObject inActiveShiptoMsg = new JsonObject();

		shipToLabels.addProperty(CommonConstants.NO_ADDRESS_LABEL, noAddressLabel);
		shipToLabels.addProperty(CommonConstants.SELECT_ADDRESS_CTA, selectAddressCTALabel);
		shipToLabels.addProperty(CommonConstants.SELECT_ADDRESS_TITLE, selectAddressTitle);
		shipToLabels.addProperty(CommonConstants.LINKED_ADDRESS_TITLE, linkedAddressTitle);
		shipToLabels.addProperty(CommonConstants.CHANGE_ADDRESS_CTA, changeAddressCTATitle);
		shipToLabels.addProperty(CommonConstants.FAVOURITE_ADDRESS, favoriteAddress);
		shipToLabels.addProperty(CommonConstants.FAV_ICON, favIcon);
		shipToLabels.addProperty(CommonConstants.FAV_ICON_ALT_TEXT, favIconAltTxt);
		shipToLabels.addProperty(CommonConstants.UP_ARROW_ICON, upArrowIcon);
		shipToLabels.addProperty(CommonConstants.DOWN_ARROW_ICON, downArrowIcon);
		shipToLabels.addProperty(CommonConstants.CONST_SAVE_CTA, saveCTA);
		shipToLabels.addProperty(CommonConstants.CANCEL_CTA, cancelCTA);

		addressLabels.addProperty(CommonConstants.SHIP_TO_LBL, shipToLabel);
		addressLabels.addProperty(CommonConstants.SHIPPING_ADDRESS_LBL, shippingAddressLabel);
		addressLabels.addProperty(CommonConstants.DEFAULT_LBL, defaultLabel);
		addressLabels.addProperty(CommonConstants.SHIP_TO_NUMBER_LBL, shipToNumberLabel);
		addressLabels.addProperty(CommonConstants.CHANGE_ADDRESS, changeAddress);
		addressLabels.addProperty(CommonConstants.SOLD_TO_LBL, soldToLabel);
		addressLabels.addProperty(CommonConstants.SOLD_TO_NUMBER, soldToNumberLabel);

		searchAddress.addProperty(CommonConstants.TITLE, title);
		searchAddress.addProperty(CommonConstants.SEARCH_PLACE_HOLDER, searchPlaceHolder);
		searchAddress.addProperty(CommonConstants.NO_SEARCH_RESULTS, noSearchResults);

		String inactiveShipToErrorIcon = CommonHelper.getLabel(CommonConstants.INACTIVE_SHIP_TO_ERROR_ICON, currentPage);
		String inactiveShipToErrorAltText = CommonHelper.getLabel(CommonConstants.INACTIVE_SHIP_TO_ERROR_ICON_ALT_TEXT, currentPage);
		String inactiveShipToErrorMsg = CommonHelper.getLabel(CommonConstants.INACTIVE_SHIP_TO_ERROR_MSG, currentPage);
		inActiveShiptoMsg.addProperty(CommonConstants.ERROR_ICON,inactiveShipToErrorIcon);
		inActiveShiptoMsg.addProperty(CommonConstants.ERROR_ICON_ALT_TEXT,inactiveShipToErrorAltText);
		inActiveShiptoMsg.addProperty(CommonConstants.ERROR_MSG,inactiveShipToErrorMsg);

		shipToLabels.add(CommonConstants.ADDRESS_LABELS, addressLabels);
		shipToLabels.add(CommonConstants.SEARCH_ADDRESS, searchAddress);
		shipToLabels.add(CommonConstants.INACTIVE_SHIP_TO_MSG, inActiveShiptoMsg);

		return shipToLabels.toString();
	}

	/**
	 * Gets the amount summary json config.
	 *
	 * @return the amount summary json config
	 */
	public String amountSummaryConfigs() {

		JsonObject amountSummaryConfigs = new JsonObject();
		JsonObject updatesVATExemptStatus = new JsonObject();
		JsonObject validateMyCart = new JsonObject();
		JsonObject punchoutTransmitRequestJson = new JsonObject();
		JsonObject punchoutCancelRequestJson = new JsonObject();

		String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		if (null != hybrisSiteId) {
			updatesVATExemptStatus.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getUpdateVATExemptStatus()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			updatesVATExemptStatus.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_PUT);

			validateMyCart.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
					+ bdbApiEndpointService.getValidateMyCart().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			validateMyCart.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_PUT);

			punchoutTransmitRequestJson.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService
							.getPunchoutTransmitRequestEndpoint().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId) + CommonConstants.EQUAL);
			punchoutTransmitRequestJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);

			punchoutCancelRequestJson.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService
							.getPunchoutCancelRequestEndpoint().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId) + CommonConstants.EQUAL);
			punchoutCancelRequestJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);

		}

		amountSummaryConfigs.add(CommonConstants.UPDATES_VAT_EMEMPT_STATUS, updatesVATExemptStatus);
		amountSummaryConfigs.add(CommonConstants.VALIDATE_MY_CART, validateMyCart);
		amountSummaryConfigs.add(CommonConstants.PUNCHOUT_TRANSMISSION_REQUEST, punchoutTransmitRequestJson);
		amountSummaryConfigs.add(CommonConstants.PUNCHOUT_CANCEL_REQUEST, punchoutCancelRequestJson);

		return amountSummaryConfigs.toString();
	}

	/**
	 * Gets the ship to json config.
	 *
	 * @return the ship to json config
	 */
	public String shipToConfigs() {

		JsonObject shipToConfigs = new JsonObject();
		JsonObject getAddressConfig = new JsonObject();
		JsonObject updateAddressConfig = new JsonObject();
		JsonObject requestPayload = new JsonObject();
		JsonObject updateAddressRequestPayload = new JsonObject();

		String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		if (null != hybrisSiteId) {
			requestPayload.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getShipToAddressConfig()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			requestPayload.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);

			updateAddressRequestPayload.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getUpdateAddressConfig()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			updateAddressRequestPayload.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_PUT);
		}

		getAddressConfig.add(CommonConstants.REQUEST_PAY_LOAD, requestPayload);
		updateAddressConfig.add(CommonConstants.REQUEST_PAY_LOAD, updateAddressRequestPayload);

		shipToConfigs.add(CommonConstants.GET_ADDRESS_CONFIG, getAddressConfig);
		shipToConfigs.add(CommonConstants.UPDATE_ADDRESS_CONFIG, updateAddressConfig);

		return shipToConfigs.toString();
	}

	/**
	 * To get the json string for order cofirmation json.
	 * 
	 * @return
	 */
	private String getOrderConfirmationAmountSummaryJson() {
		JsonObject orderConfirmationLabels = new JsonObject();
		JsonObject icon = new JsonObject();
		JsonObject info = new JsonObject();

		icon.addProperty(CommonConstants.SRC, src);
		icon.addProperty(CommonConstants.ALT_TEXT, altText);
		info.addProperty(CommonConstants.MESSAGE, message);
		String quoteReferenceNumber = CommonHelper.getLabel(CommonConstants.QUOTE_REFERENCE_NUMBER, currentPage);
		info.add(CommonConstants.ICON, icon);
		orderConfirmationLabels.add(CommonConstants.INFO, info);

		orderConfirmationLabels.addProperty(CommonConstants.AMOUNT_SUMMARY_HEADING, heading);
		orderConfirmationLabels.addProperty(CommonConstants.AMOUNT_SUBTOTAL,
				CommonHelper.getLabel(CommonConstants.AMOUNT_SUBTOTAL, currentPage));
		orderConfirmationLabels.addProperty(CommonConstants.SHIPPING_AND_HANDLING, shippingHandling);
		orderConfirmationLabels.addProperty(CommonConstants.AMOUNT_TAXES, taxes);
		orderConfirmationLabels.addProperty(CommonConstants.AMOUNT_SURCHARGE, surcharge);
		orderConfirmationLabels.addProperty(CommonConstants.PROMOTION, promotion);
		orderConfirmationLabels.addProperty(CommonConstants.TOTAL_PROMO_SAVINGS, totalPromoSavings);
		orderConfirmationLabels.addProperty(CommonConstants.AMOUNT_TOTAL, total);
		orderConfirmationLabels.addProperty(CommonConstants.AMOUNT_SURCHARGES_TOOLTIP, surchargesToolTip);
		orderConfirmationLabels.addProperty(CommonConstants.AMOUNT_TAX_TOOLTIP, taxToolTip);
		orderConfirmationLabels.addProperty(CommonConstants.AMOUNT_TOOLTIP_ALT_TEXT, toolTipAltText);

		String grantAmountUsed = CommonHelper.getLabel(CommonConstants.GRANT_AMOUNT_USED, currentPage);
		orderConfirmationLabels.addProperty(CommonConstants.GRANT_AMOUNT_USED, grantAmountUsed);
		if(quoteReferenceNumber!= null)
		orderConfirmationLabels.addProperty(CommonConstants.QUOTE_REFERENCE_NUMBER, quoteReferenceNumber);
		else
			quoteReferenceNumber="No quoteReferenceNumber";
		return orderConfirmationLabels.toString();
	}

	/**
	 * Gets the amount summary labels.
	 *
	 * @return the amount summary labels
	 */
	public String getAmountSummary() {
		return amountSummary;
	}

	/**
	 * Gets the amount summary configs.
	 *
	 * @return the amount summary configs
	 */
	public String getMyCartAmountSummaryConfig() {
		return myCartAmountSummaryConfig;
	}

	/**
	 * Gets the ship to labels.
	 *
	 * @return the ship to labels
	 */
	public String getShipTo() {
		return shipTo;
	}

	/**
	 * Gets the ship to configs.
	 *
	 * @return the ship to configs
	 */
	public String getShipToConfig() {
		return shipToConfig;
	}

	public String getOrderConfirmationAmountSummary() {
		return orderConfirmationAmountSummary;
	}

}