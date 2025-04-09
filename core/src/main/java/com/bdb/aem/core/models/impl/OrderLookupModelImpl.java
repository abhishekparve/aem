package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.bean.*;
import com.bdb.aem.core.models.OrderLookupModel;
import com.bdb.aem.core.pojo.OrderLookupConfig;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The Class OrderLookupModelImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = {
		OrderLookupModel.class }, resourceType = {
				OrderLookupModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OrderLookupModelImpl implements OrderLookupModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/orderLookup/v1/orderLookup";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(OrderLookupModelImpl.class);

	/** The current page. */
	@Inject
	private Page currentPage;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	/** The order look up title. */
	@Inject
	@Via("resource")
	@SerializedName("orderLookUpTitle")
	private String orderLookUpTitle;

	/** The po number label. */
	@Inject
	@Via("resource")
	@SerializedName("poNumberLabel")
	private String poNumberLabel;

	/** The shipping zip code label. */
	@Inject
	@Via("resource")
	@SerializedName("shippingZipCodeLabel")
	private String shippingZipCodeLabel;

	/** The search order label. */
	@Inject
	@Via("resource")
	@SerializedName("searchOrderLabel")
	private String searchOrderLabel;

	/** The or text. */
	@Inject
	@Via("resource")
	@SerializedName("orText")
	private String orText;

	/** The sign in text. */
	@Inject
	@Via("resource")
	@SerializedName("signInText")
	private String signInText;

	/** The sign in link. */
	@SerializedName("signInLink")
	private String signInLink;

	/** The all orders text. */
	@Inject
	@Via("resource")
	@SerializedName("allOrdersText")
	private String allOrdersText;

	/** The mandatory error label. */
	@Inject
	@Via("resource")
	@SerializedName("mandatoryErrorLabel")
	private String mandatoryErrorLabel;

	/** The order look up result title. */
	@Inject
	@Via("resource")
	@SerializedName("orderLookUpResultTitle")
	private String orderLookUpResultTitle;

	/** The order number. */
	@SerializedName("orderNumber")
	private String orderNumber;
	
	/** The order number label. */
	@Inject
	@Via("resource")
	@SerializedName("orderNumberLabel")
	private String orderNumberLabel;

	/** The order status label. */
	@SerializedName("orderStatusLabel")
	private String orderStatusLabel;

	/** The po number lookup label. */
	@SerializedName("poNumberLookupLabel")
	private String poNumberLookupLabel;

	/** The po date. */
	@SerializedName("poDate")
	private String poDate;
	
	/** The po date label. */
	@Inject
	@Via("resource")
	@SerializedName("poDateLabel")
	private String poDateLabel;

	/** The source label. */
	@Inject
	@Via("resource")
	@SerializedName("sourceLabel")
	private String sourceLabel;

	/** The dropship number label. */
	@Inject
	@Via("resource")
	@SerializedName("dropshipNumberLabel")
	private String dropshipNumberLabel;

	/** The view details CTA. */
	@SerializedName("viewDetailsCTA")
	private String viewDetailsCTA;

	/** The search again CTA. */
	@Inject
	@Via("resource")
	@SerializedName("searchAgainCTA")
	private String searchAgainCTA;

	/** The search again desc text. */
	@Inject
	@Via("resource")
	@SerializedName("searchAgainDescText")
	private String searchAgainDescText;

	/** The my account text. */
	@Inject
	@Via("resource")
	@SerializedName("myAccountText")
	private String myAccountText;

	/** The my account link. */
	@Inject
	@Via("resource")
	@SerializedName("myAccountLink")
	private String myAccountLink; // this should be link

	/** The ship to address label. */
	@SerializedName("shipToAddressLabel")
	private String shipToAddressLabel;

	/** The shipto number label. */
	@SerializedName("shiptoNumberLabel")
	private String shiptoNumberLabel;

	/** The minus icon alt text. */
	@Inject
	@Via("resource")
	@SerializedName("minusIconAltText")
	private String minusIconAltText;

	/** The plus icon alt text. */
	@Inject
	@Via("resource")
	@SerializedName("plusIconAltText")
	private String plusIconAltText;

	/** The no orders alt text. */
	@Inject
	@Via("resource")
	@SerializedName("noOrdersAltText")
	private String noOrdersAltText;

	/** The no orders found. */
	@Inject
	@Via("resource")
	@SerializedName("noOrdersFound")
	private String noOrdersFound;

	/** The no orders found info. */
	@Inject
	@Via("resource")
	@SerializedName("noOrdersFoundInfo")
	private String noOrdersFoundInfo;

	/** The orders number heading. */
	@Inject
	@Via("resource")
	private String ordersNumberHeading;

	/** The order issue text. */
	@Inject
	@Via("resource")
	private String orderIssueText;

	/** The contact us label. */
	private String contactUsLabel;

	/** The back to search label. */
	@Inject
	@Via("resource")
	private String backToSearchLabel;

	/** The contact us link. */
	@Inject
	@Via("resource")
	private String contactUsLink;

	/** The order details heading. */
	@Inject
	@Via("resource")
	private String orderDetailsHeading;

	/** The order placed by. */
	@Inject
	@Via("resource")
	private String orderPlacedBy;

	/** The address details heading. */
	@Inject
	@Via("resource")
	private String addressDetailsHeading;

	/** The ship to label. */
	private String shipToLabel;

	/** The bill to label. */
	private String billToLabel;

	/** The sold to label. */
	private String soldToLabel;

	/** The billto number label. */
	private String billtoNumberLabel;

	/** The soldto number label. */
	private String soldtoNumberLabel;

	/** The special instructions heading. */
	@Inject
	@Via("resource")
	private String specialInstructionsHeading;

	/** The shipments title. */
	@Inject
	@Via("resource")
	private String shipmentsTitle;

	/** The shipments status label. */
	private String shipmentsStatusLabel;

	/** The shipments complete label. */
	@Inject
	@Via("resource")
	private String shipmentsCompleteLabel;

	/** The shipments cancelled label. */
	@Inject
	@Via("resource")
	private String shipmentsCancelledLabel;

	/** The shipments open label. */
	@Inject
	@Via("resource")
	private String shipmentsOpenLabel;

	/** The shipment number label. */
	@Inject
	@Via("resource")
	private String shipmentNumberLabel;

	/** The ordered label. */
	@Inject
	@Via("resource")
	private String orderedLabel;

	/** The est delivery date. */
	@Inject
	@Via("resource")
	private String estDeliveryDate;

	/** The quantity label. */
	@Inject
	@Via("resource")
	private String quantityLabel;

	/** The view pending cta. */
	@Inject
	@Via("resource")
	private String viewPendingCta;

	/** The view shipped cta. */
	@Inject
	@Via("resource")
	private String viewShippedCta;

	/** The view shipped link message. */
	@Inject
	@Via("resource")
	private String viewShippedLinkMessage;

	/** The view pending link message. */
	@Inject
	@Via("resource")
	private String viewPendingLinkMessage;

	/** The pending status label. */
	@Inject
	@Via("resource")
	private String pendingStatusLabel;

	/** The tracking number label. */
	@Inject
	@Via("resource")
	private String trackingNumberLabel;

	/** The packing list label. */
	@Inject
	@Via("resource")
	private String packingListLabel;

	/** The products heading. */
	@Inject
	@Via("resource")
	private String productsHeading;

	/** The quantity label products. */
	@Inject
	@Via("resource")
	private String quantityLabelProducts;

	/** The default PRD image. */
	@Inject
	@Via("resource")
	private String defaultPRDImage;

	/** The default PRD image alt text. */
	@Inject
	@Via("resource")
	private String defaultPRDImageAltText;

	/** The order history details bean. */
	@SerializedName("orderHistoryDetails")
	private OrderHistoryDetailsBean orderHistoryDetailsBean;

	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The user grants labels. */
	private String orderLookUpLabels;

	/** The user grants config. */
	private String orderLookUpConfig;

	/** The hybris site id. */
	private String hybrisSiteId;

	/**
	 * Creates and Initializes the model with the Labels and Configs.
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Order lookup Init Method");

		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		myAccountLink = externalizerService.getFormattedUrl(myAccountLink, resourceResolver);
		contactUsLink = externalizerService.getFormattedUrl(contactUsLink, resourceResolver);
		setOrderLookupLabels(excludeUtilObject);
		setOrderLookupConfig(excludeUtilObject);
	}

	/**
	 * Sets the order lookup labels.
	 *
	 * @param excludeUtilObject the new order lookup labels
	 */
	private void setOrderLookupLabels(ExcludeUtil excludeUtilObject) {
		Gson json = new GsonBuilder().disableHtmlEscaping().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();

		defaultPRDImage = externalizerService.getFormattedUrl(defaultPRDImage, resourceResolver);

		orderNumber = CommonHelper.getLabel(CommonConstants.ORDER_NUMBER_LABEL, currentPage);
		orderStatusLabel = CommonHelper.getLabel(CommonConstants.ORDER_STATUS_LABEL, currentPage);
		poNumberLookupLabel = CommonHelper.getLabel(CommonConstants.PO_NUMBER_LABEL, currentPage);
		poDate = CommonHelper.getLabel(CommonConstants.PO_DATE_LABEL, currentPage);
		viewDetailsCTA = CommonHelper.getLabel(CommonConstants.VIEW_DETAILS_LABEL, currentPage);
		shipToAddressLabel = CommonHelper.getLabel(CommonConstants.SHIP_TO_ADDRESS_LABEL, currentPage);
		shiptoNumberLabel = CommonHelper.getLabel(CommonConstants.SHIP_TO_NUMBER_LABEL, currentPage);
		contactUsLabel = CommonHelper.getLabel(CommonConstants.CONTACT_US_LABEL, currentPage);
		shipToLabel = CommonHelper.getLabel(CommonConstants.SHIP_TO_LABEL, currentPage);
		billToLabel = CommonHelper.getLabel(CommonConstants.BILL_TO_LABEL, currentPage);
		soldToLabel = CommonHelper.getLabel(CommonConstants.SOLD_TO_LABEL, currentPage);
		billtoNumberLabel = CommonHelper.getLabel(CommonConstants.BILL_TO_NUMBER_LABEL, currentPage);
		soldtoNumberLabel = CommonHelper.getLabel(CommonConstants.SOLD_TO_NUMBER_LABEL, currentPage);
		shipmentsStatusLabel = CommonHelper.getLabel(CommonConstants.STATUS_LABEL, currentPage);

		signInLink = CommonHelper.getSignInUrl(bdbApiEndpointService, externalizerService, resourceResolver,
				currentPage);

		setOrderHistoryDetails();
		orderLookUpLabels = json.toJson(this);
	}

	/**
	 * Sets the order lookup config.
	 *
	 * @param excludeUtilObject the new order lookup config
	 */
	private void setOrderLookupConfig(ExcludeUtil excludeUtilObject) {
		JsonElement searchOrderLookupJson;
		JsonElement getOrderDetailsJson;
		JsonElement trackingNumberJson;
		JsonElement getOrderPackingListJson;

		if (bdbApiEndpointService != null) {
			String searchOrderLookupEndpoint = StringUtils.replace(
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getSearchOrderLookupEndpoint(),
					CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String getOrderDetailsEndpoint = StringUtils.replace(
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getOrderDetailsEndpoint(),
					CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String trackingNumberEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + 
					bdbApiEndpointService.trackingNumberConfig(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String getOrderPackingListEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + 
					bdbApiEndpointService.getOrderPackingListEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);

			Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
					.addSerializationExclusionStrategy(excludeUtilObject).create();

			Payload searchOrderLookupPayload = new Payload(searchOrderLookupEndpoint, HttpConstants.METHOD_POST);
			Payload getOrderDetailsPayload = new Payload(getOrderDetailsEndpoint, HttpConstants.METHOD_GET);
			Payload trackingNumberPayload = new Payload(trackingNumberEndpoint, HttpConstants.METHOD_POST);
			Payload getOrderPackingListPayload = new Payload(getOrderPackingListEndpoint, HttpConstants.METHOD_POST);

			PayloadConfig searchOrderLookupPayloadConfig = new PayloadConfig(searchOrderLookupPayload);
			searchOrderLookupJson = json.toJsonTree(searchOrderLookupPayloadConfig);

			PayloadConfig getOrderDetailsPayloadConfig = new PayloadConfig(getOrderDetailsPayload);
			getOrderDetailsJson = json.toJsonTree(getOrderDetailsPayloadConfig);
			
			PayloadConfig trackingNumberPayloadConfig = new PayloadConfig(trackingNumberPayload);
			trackingNumberJson = json.toJsonTree(trackingNumberPayloadConfig);
			
			PayloadConfig getOrderPackingListPayloadConfig = new PayloadConfig(getOrderPackingListPayload);
			getOrderPackingListJson = json.toJsonTree(getOrderPackingListPayloadConfig);

			OrderLookupConfig orderLookupConfigBean = new OrderLookupConfig(searchOrderLookupJson, getOrderDetailsJson, getOrderPackingListJson);
			orderLookupConfigBean.setTrackingNumberConfig(trackingNumberJson);
			orderLookUpConfig = json.toJson(orderLookupConfigBean);
		}
	}

	/**
	 * Sets the order history details.
	 */
	private void setOrderHistoryDetails() {
		OrderDetailsBean orderDetails = new OrderDetailsBean();
		orderDetails.setOrderDetailsHeading(orderDetailsHeading);
		orderDetails.setPoNumber(poNumberLookupLabel);
		orderDetails.setPoDate(poDate);
		orderDetails.setOrderPlacedBy(orderPlacedBy);
		orderDetails.setOrderStatusText(orderStatusLabel);
		orderDetails.setSource(sourceLabel);

		AddressDetailsBean addressDetails = new AddressDetailsBean();
		addressDetails.setAddressDetailsHeading(addressDetailsHeading);
		addressDetails.setShipToLabel(shipToLabel);
		addressDetails.setBillToLabel(billToLabel);
		addressDetails.setSoldToLabel(soldToLabel);
		addressDetails.setShiptoNumberLabel(shiptoNumberLabel);
		addressDetails.setBilltoNumberLabel(billtoNumberLabel);
		addressDetails.setSoldtoNumberLabel(soldtoNumberLabel);

		SpecialInstructionsBean specialInstructions = new SpecialInstructionsBean();
		specialInstructions.setSpecialInstructionsHeading(specialInstructionsHeading);

		ShipmentsLabelsBean shipmentsLabels = new ShipmentsLabelsBean();
		shipmentsLabels.setShipmentsTitle(shipmentsTitle);
		shipmentsLabels.setShipmentsStatusLabel(shipmentsStatusLabel);
		shipmentsLabels.setShipmentsCompleteLabel(shipmentsCompleteLabel);
		shipmentsLabels.setShipmentsCancelledLabel(shipmentsCancelledLabel);
		shipmentsLabels.setShipmentsOpenLabel(shipmentsOpenLabel);
		shipmentsLabels.setShipmentNumberLabel(shipmentNumberLabel);
		shipmentsLabels.setOrderedLabel(orderedLabel);
		shipmentsLabels.setEstDeliveryDate(estDeliveryDate);
		shipmentsLabels.setQuantityLabel(quantityLabel);
		shipmentsLabels.setViewPendingCta(viewPendingCta);
		shipmentsLabels.setViewShippedCta(viewShippedCta);
		shipmentsLabels.setViewShippedLinkMessage(viewShippedLinkMessage);
		shipmentsLabels.setViewPendingLinkMessage(viewPendingLinkMessage);
		shipmentsLabels.setPendingStatusLabel(pendingStatusLabel);
		shipmentsLabels.setStatusLabel(shipmentsStatusLabel);
		shipmentsLabels.setTrackingNumberLabel(trackingNumberLabel);
		shipmentsLabels.setPackingListLabel(packingListLabel);
		shipmentsLabels.setShippedQuantityLabel(CommonHelper.getLabel(CommonConstants.SHIPPED_QUANTITY_LABEL, currentPage));
		shipmentsLabels.setPromocodeLabel(CommonHelper.getLabel(CommonConstants.PROMO_CODE, currentPage));
		shipmentsLabels.setYouSavedLabel(CommonHelper.getLabel(CommonConstants.YOU_SAVED, currentPage));
		shipmentsLabels.setOnLabel(CommonHelper.getLabel(CommonConstants.ON, currentPage));

		ProductsOrderLookupBean products = new ProductsOrderLookupBean();
		products.setProductsHeading(productsHeading);
		products.setEstDeliveryDate(estDeliveryDate);
		products.setQuantityLabelProducts(quantityLabelProducts);
		products.setDefaultPRDImage(defaultPRDImage);
		products.setDefaultPRDImageAltText(defaultPRDImageAltText);
		products.setPromocodeLabel(CommonHelper.getLabel(CommonConstants.PROMO_CODE, currentPage));
		products.setYouSavedLabel(CommonHelper.getLabel(CommonConstants.YOU_SAVED, currentPage));
		products.setOnLabel(CommonHelper.getLabel(CommonConstants.ON, currentPage));

		orderHistoryDetailsBean = new OrderHistoryDetailsBean();
		orderHistoryDetailsBean.setOrdersNumberHeading(ordersNumberHeading);
		orderHistoryDetailsBean.setOrderIssueText(orderIssueText);
		orderHistoryDetailsBean.setContactUsLabel(contactUsLabel);
		orderHistoryDetailsBean.setContactUsLink(contactUsLink);
		orderHistoryDetailsBean.setBackToSearchLabel(backToSearchLabel);
		orderHistoryDetailsBean.setAddressDetails(addressDetails);
		orderHistoryDetailsBean.setOrderDetails(orderDetails);
		orderHistoryDetailsBean.setProducts(products);
		orderHistoryDetailsBean.setShipmentsLabels(shipmentsLabels);
		orderHistoryDetailsBean.setSpecialInstructions(specialInstructions);

	}

	/**
	 * Gets the order lookup labels.
	 *
	 * @return the order lookup labels
	 */
	@Override
	public String getOrderLookupLabels() {
		return orderLookUpLabels;
	}

	/**
	 * Gets the order lookup config.
	 *
	 * @return the order lookup config
	 */
	@Override
	public String getOrderLookupConfig() {
		return orderLookUpConfig;
	}
}
