package com.bdb.aem.core.models.impl;

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
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.AccountManagementOrdersModel;
import com.bdb.aem.core.pojo.AccountManagementOrdersApprovalLabels;
import com.bdb.aem.core.pojo.AccountManagementOrdersConfig;
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

/**
 * The Class AccountManagementOrdersModelImpl.
 */
@Model( adaptables = { SlingHttpServletRequest.class, Resource.class }, 
		adapters = { AccountManagementOrdersModel.class },
		resourceType = { AccountManagementOrdersModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccountManagementOrdersModelImpl implements AccountManagementOrdersModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(AccountManagementOrdersModelImpl.class);
	
	/** The current page. */
	@Inject
	private Page currentPage;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;
	
	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;
	
	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	/** The orders title. */
	@Inject
	@Via("resource")
	private String ordersTitle;
	
	/** The track orders title. */
	@Inject
	@Via("resource")
	private String trackOrdersTitle;
	
	/** The track orders text. */
	@Inject
	@Via("resource")
	private String trackOrdersText;
	
	/** The re order title. */
	@Inject
	@Via("resource")
	private String reOrderTitle;
	
	/** The re order text. */
	@Inject
	@Via("resource")
	private String reOrderText;
	
	/** The search orders placeholder text. */
	@Inject
	@Via("resource")
	private String searchOrdersPlaceholderText;
	
	/** The order placed by text. */
	@Inject
	@Via("resource")
	private String orderPlacedByText;
	
	/** The self label. */
	@Inject
	@Via("resource")
	private String selfLabel;
	
	/** The organization label. */
	@Inject
	@Via("resource")
	private String organizationLabel;
	
	/** The cancel order success message. */
	@Inject
	@Via("resource")
	private String cancelOrderSuccessMessage;
	
	/** The more filters cta. */
	@Inject
	@Via("resource")
	private String moreFiltersCta;

	/** The status label. */
	@Inject
	@Via("resource")
	private String statusLabel;
	
	/** The complete label. */
	@Inject
	@Via("resource")
	private String completeLabel;
	
	/** The cancelled label. */
	@Inject
	@Via("resource")
	private String cancelledLabel;
	
	/** The open label. */
	@Inject
	@Via("resource")
	private String openLabel;
	
	/** The source label. */
	@Inject
	@Via("resource")
	private String sourceLabel;
	
	/** The web label. */
	@Inject
	@Via("resource")
	private String webLabel;
	
	/** The edi label. */
	@Inject
	@Via("resource")
	private String ediLabel;
	
	/** The offline label. */
	@Inject
	@Via("resource")
	private String offlineLabel;
	
	/** The date range label. */
	@Inject
	@Via("resource")
	private String dateRangeLabel;
	
	/** The from date placeholder. */
	@Inject
	@Via("resource")
	private String fromDatePlaceholder;
	
	/** The to date placeholder. */
	@Inject
	@Via("resource")
	private String toDatePlaceholder;
	
	/** The date format label. */
	@Inject
	@Via("resource")
	private String dateFormatLabel;
	
	/** The search cta. */
	@Inject
	@Via("resource")
	private String searchCta;

	/** The noOrdersFound. */
	@Inject
	@Via("resource")
	private String noOrdersFound;

	/** The noOrdersFoundInfo. */
	@Inject
	@Via("resource")
	private String noOrdersFoundInfo;

	/** The clearSearch. */
	@Inject
	@Via("resource")
	private String clearSearch;

	/** The order status label. */
	@Inject
	@Via("resource")
	private String orderStatusLabel;
	
	/** The appproval status label. */
	@Inject
	@Via("resource")
	private String appprovalStatusLabel;
	
	/** The pending approval. */
	@Inject
	@Via("resource")
	private String pendingApproval;
	
	/** The approved. */
	@Inject
	@Via("resource")
	private String approved;
	
	/** The declined. */
	@Inject
	@Via("resource")
	private String declined;
	
	/** The view details CTA. */
	@Inject
	@Via("resource")
	private String viewDetailsCTA;
	
	/** The invoices label. */
	@Inject
	@Via("resource")
	private String invoicesLabel;

	/** The orders number header. */
	@Inject
	@Via("resource")
	private String ordersNumberHeader;
	
	/** The order issues label. */
	@Inject
	@Via("resource")
	private String orderIssuesLabel;
	
	/** The cancel order CTA label. */
	@Inject
	@Via("resource")
	private String cancelOrderCTALabel;
	
	/** The re order CTA. */
	@Inject
	@Via("resource")
	private String reOrderCTA;
	
	/** The back to orders CTA. */
	@Inject
	@Via("resource")
	private String backToOrdersCTA;

	/** The order details heading. */
	@Inject
	@Via("resource")
	private String orderDetailsHeading;
	
	/** The download CSV cta. */
	@Inject
	@Via("resource")
	private String downloadCSVCta;
	
	/** The orders order status text. */
	@Inject
	@Via("resource")
	private String ordersOrderStatusText;
	
	/** The order detail status. */
	@Inject
	@Via("resource")
	private String orderDetailStatus;
	
	/** The order details approval status. */
	@Inject
	@Via("resource")
	private String orderDetailsApprovalStatus;
	
	/** The view more cta. */
	@Inject
	@Via("resource")
	private String viewMoreCta;
	
	/** The card number label. */
	@Inject
	@Via("resource")
	private String cardNumberLabel;

	/** The amount summary heading. */
	@Inject
	@Via("resource")
	private String amountSummaryHeading;

	/** The src. */
	@Inject
	@Via("resource")
	private String src;

	/** The alt text. */
	@Inject
	@Via("resource")
	private String altText;
	
	/** The message. */
	@Inject
	@Via("resource")
	private String message;
	
	/** The total savings. */
	@Inject
	@Via("resource")
	private String totalSavings;

	/** The address details heading. */
	@Inject
	@Via("resource")
	private String addressDetailsHeading;

	/** The special instructions heading. */
	@Inject
	@Via("resource")
	private String specialInstructionsHeading;

	/** The shipments title. */
	@Inject
	@Via("resource")
	private String shipmentsTitle;

	/** The shipments status label. */
	@Inject
	@Via("resource")
	private String shipmentsStatusLabel;
	
	/** The status label. */
	@Inject
	@Via("resource")
	private String shipmentStatus;
	
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
	
	/** The pending status label. */
	@Inject
	@Via("resource")
	private String pendingStatusLabel;
	
	/** The view shipped link message. */
	@Inject
	@Via("resource")
	private String viewShippedLinkMessage;
	
	/** The view pending link message. */
	@Inject
	@Via("resource")
	private String viewPendingLinkMessage;
	
	/** The packing list label. */
	@Inject
	@Via("resource")
	private String packingListLabel;

	/** The your price label. */
	@Inject
	@Via("resource")
	private String yourPriceLabel;

	/** The list price label. */
	@Inject
	@Via("resource")
	private String listPriceLabel;

	/** The view pending cta. */
	@Inject
	@Via("resource")
	private String viewPendingCta;

	/** The view shipped cta. */
	@Inject
	@Via("resource")
	private String viewShippedCta;

	/** The myOrders. */
	@Inject
	@Via("resource")
	private String myOrders;

	/** The allOrders. */
	@Inject
	@Via("resource")
	private String allOrders;
	
	/** The shipment label. */
	@Inject
	@Via("resource")
	@Default(values = "Shipment")
	private String shipmentLabel;
	
	/** My Web Orders. */
	@Inject
	@Via("resource")
	@Default(values = "My Web Orders")
	private String myWebOrders;
	
	/** The tabLabel. */
	@Inject
	@Via("resource")
	@Default(values = "tab")
	private String tabLabel;
	
	/** The orderDisclaimer */
	@Inject
	@Via("resource")
	@Default(values = "Note: To view your most recent web orders, use the My Web Order tab. To search and view other orders, use the")
	private String orderDisclaimer;
	
	/** The noWebOrderMessage. */
	@Inject
	@Via("resource")
	@Default(values = "You dont have any web orders placed yet, please click here to")
	private String noWebOrderMessage;
	
	/** The shopOnlineLink. */
	@Inject
	@Via("resource")
	private String shopOnlineLink;
	
	/** The shopOnlineLabel. */
	@Inject
	@Via("resource")
	@Default(values = "shop online")
	private String shopOnlineLabel;
	
	
	/** The user orders list labels. */
	private String userOrdersListLabels;
	
	/** The user orders config. */
	private String userOrdersConfig;

	/** The hybris site id. */
	private String hybrisSiteId;
	
	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
	
	/**
	 * Creates and Initializes the model with the Labels and Configs.
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Orders Init Method");
		if(StringUtils.isNotEmpty(shopOnlineLink)) {
			shopOnlineLink = externalizerService.getFormattedUrl(shopOnlineLink, resourceResolver);
		}
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		setUserOrdersListLabels(excludeUtilObject);
		setUserOrdersConfig(excludeUtilObject);
	}
	
	/**
	 * Sets the user orders list labels.
	 *
	 * @param excludeUtilObject the new user orders list labels
	 */
	private void setUserOrdersListLabels(ExcludeUtil excludeUtilObject) {
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		userOrdersListLabels = StringUtils.EMPTY;
		
		JsonElement moreFiltersLabelsJson;
		JsonElement orderHistoryDetails;
		JsonElement orderDetails;
		JsonElement amountSummary;
		JsonElement addressDetails;
		JsonElement specialInstructions;
		JsonElement shipmentsLabelsJson;
		JsonElement info;
		JsonElement icon;
		
		AccountManagementOrdersApprovalLabels ordersLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels moreFiltersLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels orderHistoryDetailsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels orderDetailsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels amountSummaryLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels addressDetailsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels specialInstructionsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels shipmentsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels infoLabels = new AccountManagementOrdersApprovalLabels();		
		AccountManagementOrdersApprovalLabels iconLabels = new AccountManagementOrdersApprovalLabels();
		
		ordersLabels.setOrdersTitle(ordersTitle);
		ordersLabels.setTrackOrdersTitle(trackOrdersTitle);
		ordersLabels.setTrackOrdersText(trackOrdersText);
		ordersLabels.setReOrderTitle(reOrderTitle);
		ordersLabels.setReOrderText(reOrderText);
		ordersLabels.setSearchOrdersPlaceholderText(searchOrdersPlaceholderText);
		ordersLabels.setOrdersOrderPlacedByText(orderPlacedByText);
		ordersLabels.setSelfLabel(selfLabel);
		ordersLabels.setOrganizationLabel(organizationLabel);
		ordersLabels.setCancelOrderSuccessMessage(cancelOrderSuccessMessage);
		ordersLabels.setMoreFiltersCta(moreFiltersCta);
		ordersLabels.setMyWebOrders(myWebOrders);
		ordersLabels.setTabLabel(tabLabel);
		ordersLabels.setOrderDisclaimer(orderDisclaimer);
		ordersLabels.setNoWebOrderMessage(noWebOrderMessage);
		ordersLabels.setShopOnlineLink(shopOnlineLink);
		ordersLabels.setShopOnlineLabel(shopOnlineLabel);
		
		moreFiltersLabels.setStatusLabel(statusLabel);
		moreFiltersLabels.setCompleteLabel(completeLabel);
		moreFiltersLabels.setCancelledLabel(cancelledLabel);
		moreFiltersLabels.setOpenLabel(openLabel);
		moreFiltersLabels.setSourceLabel(sourceLabel);
		moreFiltersLabels.setWebLabel(webLabel);
		moreFiltersLabels.setEdiLabel(ediLabel);
		moreFiltersLabels.setOfflineLabel(offlineLabel);
		moreFiltersLabels.setDateRangeLabel(dateRangeLabel);
		moreFiltersLabels.setFromDatePlaceholder(fromDatePlaceholder);
		moreFiltersLabels.setToDatePlaceholder(toDatePlaceholder);
		moreFiltersLabels.setDateFormatLabel(dateFormatLabel);
		moreFiltersLabels.setSearchCta(searchCta);
		moreFiltersLabels.setTypeHerePlaceHolder(CommonHelper.getLabel(CommonConstants.TYPE_HERE_PLACEHOLDER, currentPage));
		moreFiltersLabels.setMaterialNoLabel(CommonHelper.getLabel(CommonConstants.MATERIAL_NUMBER_LABEL, currentPage));
		moreFiltersLabels.setSearchFilter(CommonHelper.getLabel(CommonConstants.SEARCH_AND_FILTER, currentPage));
		moreFiltersLabels.setSearchButton(CommonHelper.getLabel(CommonConstants.SEARCH_LABEL, currentPage));
		moreFiltersLabels.setSelectShipToAddress(CommonHelper.getLabel(CommonConstants.SELECT_SHIP_TO_ADDRESS, currentPage));
		moreFiltersLabels.setAddressLabel(CommonHelper.getLabel(CommonConstants.ADDRESS_LABEL, currentPage));
		moreFiltersLabels.setNoOrdersFound(noOrdersFound);
		moreFiltersLabels.setNoOrdersFoundInfo(noOrdersFoundInfo);
		moreFiltersLabels.setClearSearch(clearSearch);
		moreFiltersLabels.setPoNumberHash(CommonHelper.getLabel(CommonConstants.PO_NUM, currentPage));
		moreFiltersLabels.setOrderNumberHash(CommonHelper.getLabel(CommonConstants.ORDER_NUM, currentPage));
		moreFiltersLabels.setInvoiceNumberHash(CommonHelper.getLabel(CommonConstants.INVOICE_NUM, currentPage));


		moreFiltersLabelsJson = json.toJsonTree(moreFiltersLabels);
		
		ordersLabels.setMoreFiltersLabels(moreFiltersLabelsJson);
		String orderNumberLabel = CommonHelper.getLabel(CommonConstants.ORDER_NUMBER_LABEL, currentPage);
		ordersLabels.setOrderNumberLabel(orderNumberLabel);
		ordersLabels.setOrderStatusLabel(orderStatusLabel);
		ordersLabels.setAppprovalStatusLabel(appprovalStatusLabel);
		
		String totalLabel = CommonHelper.getLabel(CommonConstants.TOTAL_LABEL, currentPage);
		String poDateLabel = CommonHelper.getLabel(CommonConstants.PO_DATE_LABEL, currentPage);
		String quoteReferenceNumberLabel = CommonHelper.getLabel(CommonConstants.QUOTE_REFERENCE_NUMBER, currentPage);
		String poNumberLabel = CommonHelper.getLabel(CommonConstants.PO_NUMBER_LABEL, currentPage);
		String sourceOrdersLabel = CommonHelper.getLabel(CommonConstants.SOURCE_LABEL, currentPage);		
		ordersLabels.setTotalLabel(totalLabel);
		ordersLabels.setPoDateLabel(poDateLabel);
		ordersLabels.setQuotereferenceNumberLabel(quoteReferenceNumberLabel);
		ordersLabels.setPoNumberLabel(poNumberLabel);
		ordersLabels.setSourceLabel(sourceOrdersLabel);
			
		ordersLabels.setPendingApproval(pendingApproval);
		ordersLabels.setApproved(approved);
		ordersLabels.setDeclined(declined);
		ordersLabels.setViewDetailsCTA(viewDetailsCTA);
		ordersLabels.setInvoicesLabel(invoicesLabel);
		
		String shipToNumberLabel = CommonHelper.getLabel(CommonConstants.SHIP_TO_NUMBER_LABEL, currentPage);
		String shipToAddressLabel = CommonHelper.getLabel(CommonConstants.SHIP_TO_ADDRESS_LABEL, currentPage);
		ordersLabels.setShipToAddressLabel(shipToAddressLabel);
		ordersLabels.setShipToNumberLabel(shipToNumberLabel);
		ordersLabels.setMyOrders(myOrders);
		ordersLabels.setAllOrders(allOrders);
		
		orderDetailsLabels.setOrderDetailsHeading(orderDetailsHeading);
		orderDetailsLabels.setDownloadCSVCta(downloadCSVCta);
		String poNumber = CommonHelper.getLabel(CommonConstants.PO_NUMBER_LABEL, currentPage);
		String poDate = CommonHelper.getLabel(CommonConstants.PO_DATE_LABEL, currentPage);
		String quoteReferenceNumber = CommonHelper.getLabel(CommonConstants.QUOTE_REFERENCE_NUMBER, currentPage);
		String orderedBy = CommonHelper.getLabel(CommonConstants.ORDERED_BY_LABEL, currentPage);
		orderDetailsLabels.setPoNumber(poNumber);
		orderDetailsLabels.setPoDate(poDate);
		orderDetailsLabels.setQuotereferenceNumber(quoteReferenceNumber);
		orderDetailsLabels.setOrderPlacedByText(orderedBy);
		orderDetailsLabels.setOrdersOrderStatusText(ordersOrderStatusText);
		orderDetailsLabels.setOrderDetailStatus(orderDetailsApprovalStatus);
		String invoicesText = CommonHelper.getLabel(CommonConstants.INVOICES_LABEL, currentPage);
		String sourceOrderDetails = CommonHelper.getLabel(CommonConstants.SOURCE_LABEL, currentPage);
		String carrierNumberLabel = CommonHelper.getLabel(CommonConstants.CARRIER_NUMBER_LABEL, currentPage);
		orderDetailsLabels.setInvoicesText(invoicesText);
		orderDetailsLabels.setViewMoreCta(viewMoreCta);
		orderDetailsLabels.setCardNumberLabel(cardNumberLabel);
		orderDetailsLabels.setSource(sourceOrderDetails);
		orderDetailsLabels.setCarrierNumberLabel(carrierNumberLabel);
		String paymentMethod = CommonHelper.getLabel(CommonConstants.PAYMENT_METHOD, currentPage);
		orderDetailsLabels.setPaymentMethod(paymentMethod);
		orderDetails = json.toJsonTree(orderDetailsLabels);
		
		iconLabels.setSrc(src);
		iconLabels.setAltText(altText);
		icon = json.toJsonTree(iconLabels);
		
		infoLabels.setIcon(icon);
		infoLabels.setMessage(message);
		info = json.toJsonTree(infoLabels);
		
		amountSummaryLabels.setAmountSummaryHeading(amountSummaryHeading);
		amountSummaryLabels.setInfo(info);
		String subtotal = CommonHelper.getLabel(CommonConstants.SUBTOTAL_LABEL, currentPage);
		String shippingAndHandling = CommonHelper.getLabel(CommonConstants.SHIPPING_AND_HANDLING_LABEL, currentPage);
		String taxes = CommonHelper.getLabel(CommonConstants.TAXES_LABEL, currentPage);
		String surcharge = CommonHelper.getLabel(CommonConstants.SURCHARGE_LABEL, currentPage);
		String promotion = CommonHelper.getLabel(CommonConstants.PROMOTION_LABEL, currentPage);
		String total = CommonHelper.getLabel(CommonConstants.TOTAL_LABEL, currentPage);
		String grantAmountUsed = CommonHelper.getLabel(CommonConstants.GRANT_AMOUNT_USED, currentPage);
		amountSummaryLabels.setSubtotal(subtotal);
		amountSummaryLabels.setShippingAndHandling(shippingAndHandling);
		amountSummaryLabels.setTaxes(taxes);
		amountSummaryLabels.setSurcharge(surcharge);
		amountSummaryLabels.setPromotion(promotion);
		amountSummaryLabels.setTotalSavings(totalSavings);
		amountSummaryLabels.setTotal(total);
		amountSummaryLabels.setGrantAmountUsed(grantAmountUsed);
		amountSummary = json.toJsonTree(amountSummaryLabels);
		
		addressDetailsLabels.setAddressDetailsHeading(addressDetailsHeading);
		String shipToLabel = CommonHelper.getLabel(CommonConstants.SHIP_TO_LABEL, currentPage);
		String billToLabel = CommonHelper.getLabel(CommonConstants.BILL_TO_LABEL, currentPage);
		String soldToLabel = CommonHelper.getLabel(CommonConstants.SOLD_TO_LABEL , currentPage);
		addressDetailsLabels.setShipToLabel(shipToLabel);
		addressDetailsLabels.setBillToLabel(billToLabel);
		addressDetailsLabels.setSoldToLabel(soldToLabel);
		
		String billToNumberLabel = CommonHelper.getLabel(CommonConstants.BILL_TO_NUMBER_LABEL, currentPage);
		String soldToNumberLabel = CommonHelper.getLabel(CommonConstants.SOLD_TO_NUMBER_LABEL, currentPage);
		addressDetailsLabels.setShipToNumberLabel(shipToNumberLabel);
		addressDetailsLabels.setBillToNumberLabel(billToNumberLabel);
		addressDetailsLabels.setSoldToNumberLabel(soldToNumberLabel);
		addressDetails = json.toJsonTree(addressDetailsLabels);
		
		specialInstructionsLabels.setSpecialInstructionsHeading(specialInstructionsHeading);
		specialInstructions = json.toJsonTree(specialInstructionsLabels);
		
		shipmentsLabels.setShipmentsTitle(shipmentsTitle);
		shipmentsLabels.setShipmentsStatusLabel(shipmentsStatusLabel);
		shipmentsLabels.setShipmentsCompleteLabel(shipmentsCompleteLabel);
		shipmentsLabels.setShipmentsCancelledLabel(shipmentsCancelledLabel);
		shipmentsLabels.setShipmentsOpenLabel(shipmentsOpenLabel);
		shipmentsLabels.setShipmentNumberLabel(shipmentNumberLabel);
		String orderedLabel = CommonHelper.getLabel(CommonConstants.ORDERED_LABEL, currentPage);
		String estDeliveryDate = CommonHelper.getLabel(CommonConstants.EST_DELIVERY_DATE_LABEL, currentPage);
		String quantityLabel = CommonHelper.getLabel(CommonConstants.PENDING_QTY_LABEL, currentPage);
		String pricePerUnit = CommonHelper.getLabel(CommonConstants.PRICE_PER_UNIT_LABEL, currentPage);
		String totalPriceLabel = CommonHelper.getLabel(CommonConstants.TOTAL_PRICE_LABEL, currentPage);
		String shipmentsTrackingStatus = CommonHelper.getLabel(CommonConstants.STATUS_LABEL, currentPage);
		String trackingNumberLabel = CommonHelper.getLabel(CommonConstants.TRACKING_NUMBER_LABEL, currentPage);
		String invoiceNumberLabel = CommonHelper.getLabel(CommonConstants.INVOICE_NUMBER, currentPage);
		shipmentsLabels.setShippedQuantityLabel(CommonHelper.getLabel(CommonConstants.SHIPPED_QUANTITY_LABEL, currentPage));
		shipmentsLabels.setOrderedLabel(orderedLabel);
		shipmentsLabels.setEstDeliveryDate(estDeliveryDate);
		shipmentsLabels.setQuantityLabel(quantityLabel);
		shipmentsLabels.setPricePerUnit(pricePerUnit);
		shipmentsLabels.setYourPriceLabel(yourPriceLabel);
		shipmentsLabels.setListPriceLabel(listPriceLabel);
		shipmentsLabels.setTotalPriceLabel(totalPriceLabel);
		shipmentsLabels.setViewPendingCta(viewPendingCta);
		shipmentsLabels.setShipmentLabel(shipmentLabel);
		shipmentsLabels.setViewShippedLinkMessage(viewShippedLinkMessage);
		shipmentsLabels.setViewPendingLinkMessage(viewPendingLinkMessage);
		shipmentsLabels.setPendingStatusLabel(pendingStatusLabel);
		shipmentsLabels.setStatusLabel(shipmentStatus);
		shipmentsLabels.setShipmentsTrackingStatus(shipmentsTrackingStatus);
		shipmentsLabels.setTrackingNumberLabel(trackingNumberLabel);
		shipmentsLabels.setPackingListLabel(packingListLabel);
		shipmentsLabels.setInvoiceNumberLabel(invoiceNumberLabel);
		shipmentsLabels.setYouSavedLabel(CommonHelper.getLabel(CommonConstants.YOU_SAVED, currentPage));
		shipmentsLabels.setOnLabel(CommonHelper.getLabel(CommonConstants.ON, currentPage));
		shipmentsLabels.setPromocodeLabel(CommonHelper.getLabel(CommonConstants.PROMO_CODE, currentPage));
		shipmentsLabels.setViewShippedCta(viewShippedCta);
		shipmentsLabelsJson = json.toJsonTree(shipmentsLabels);
		
		orderHistoryDetailsLabels.setOrdersNumberHeader(ordersNumberHeader);
		orderHistoryDetailsLabels.setOrderIssuesLabel(orderIssuesLabel);
		String contactUsCTALabel = CommonHelper.getLabel(CommonConstants.CONTACT_US_LABEL, currentPage);
		orderHistoryDetailsLabels.setContactUsCTALabel(contactUsCTALabel);
		orderHistoryDetailsLabels.setCancelOrderCTALabel(cancelOrderCTALabel);
		orderHistoryDetailsLabels.setReOrderCTA(reOrderCTA);
		orderHistoryDetailsLabels.setBackToOrdersCTA(backToOrdersCTA);
		orderHistoryDetailsLabels.setOrderDetails(orderDetails);
		orderHistoryDetailsLabels.setAmountSummary(amountSummary);
		orderHistoryDetailsLabels.setAddressDetails(addressDetails);
		orderHistoryDetailsLabels.setSpecialInstructions(specialInstructions);
		orderHistoryDetailsLabels.setShipmentsLabels(shipmentsLabelsJson);
		orderHistoryDetails = json.toJsonTree(orderHistoryDetailsLabels);
		
		ordersLabels.setOrderHistoryDetails(orderHistoryDetails);
		
		userOrdersListLabels = json.toJson(ordersLabels);
	}
	
	/**
	 * Sets the user orders config.
	 *
	 * @param excludeUtilObject the new user orders config
	 */
	private void setUserOrdersConfig(ExcludeUtil excludeUtilObject) {
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		userOrdersConfig = StringUtils.EMPTY;
		
		JsonElement getUserOrdersListJson;
		JsonElement getOrderDetailsJson;
		JsonElement getOrderPackingListJson;
		JsonElement triggerReOrderJson;
		JsonElement fetchAddressPayload;
		JsonElement myOrdersList;
		JsonElement allOrdersList;
		JsonElement cancelOrderJson;
		JsonElement trackingNumberJson;

		if (bdbApiEndpointService != null) {
			String getUserOrdersListEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getUserOrdersListEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String getOrderDetailsEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getOrderDetailsEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);	
			String getOrderPackingListEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getOrderPackingListEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String triggerReOrderJsonEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.triggerReOrderEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String cancelOrderEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.cancelOrderEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String trackingNumberEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + 
					bdbApiEndpointService.trackingNumberConfig(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			
			Payload getUserOrdersListPayload = new Payload(getUserOrdersListEndpoint, HttpConstants.METHOD_GET);
			Payload getOrderDetailsPayload = new Payload(getOrderDetailsEndpoint, HttpConstants.METHOD_GET);
			Payload getOrderPackingListPayload = new Payload(getOrderPackingListEndpoint, HttpConstants.METHOD_POST);
			Payload triggerReOrderJsonPayload = new Payload(triggerReOrderJsonEndpoint, HttpConstants.METHOD_POST);
			Payload cancelOrderPayload = new Payload(cancelOrderEndpoint, HttpConstants.METHOD_POST);
			Payload trackingNumberPayload = new Payload(trackingNumberEndpoint, HttpConstants.METHOD_POST);
			
			PayloadConfig getUserOrdersListPayloadConfig = new PayloadConfig(getUserOrdersListPayload);
			getUserOrdersListJson = json.toJsonTree(getUserOrdersListPayloadConfig);
			PayloadConfig getOrderDetailsPayloadConfig = new PayloadConfig(getOrderDetailsPayload);
			getOrderDetailsJson = json.toJsonTree(getOrderDetailsPayloadConfig);
			PayloadConfig getOrderPackingListPayloadConfig = new PayloadConfig(getOrderPackingListPayload);
			getOrderPackingListJson = json.toJsonTree(getOrderPackingListPayloadConfig);
			PayloadConfig triggerReOrderJsonPayloadConfig = new PayloadConfig(triggerReOrderJsonPayload);
			triggerReOrderJson = json.toJsonTree(triggerReOrderJsonPayloadConfig);
			PayloadConfig cancelOrderPayloadConfig = new PayloadConfig(cancelOrderPayload);
			cancelOrderJson = json.toJsonTree(cancelOrderPayloadConfig);
			PayloadConfig trackingNumberPayloadConfig = new PayloadConfig(trackingNumberPayload);
			trackingNumberJson = json.toJsonTree(trackingNumberPayloadConfig);

			String fetchAddressPayloadEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getAddressesEndpoint(), CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
			Payload fetchAddress = new Payload(fetchAddressPayloadEndpoint, HttpConstants.METHOD_GET);
			PayloadConfig fetchAddressPayloadConfig = new PayloadConfig(fetchAddress);
			fetchAddressPayload = json.toJsonTree(fetchAddressPayloadConfig);
			
			String myOrdersListEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getMyOrdersListEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			Payload myOrdersListPayload = new Payload(myOrdersListEndpoint, HttpConstants.METHOD_POST);
			PayloadConfig myOrdersListPayloadConfig = new PayloadConfig(myOrdersListPayload);
			myOrdersList = json.toJsonTree(myOrdersListPayloadConfig);

			String allOrdersListEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getAllOrdersListEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			Payload allOrdersListPayload = new Payload(allOrdersListEndpoint, HttpConstants.METHOD_POST);
			PayloadConfig allOrdersListPayloadConfig = new PayloadConfig(allOrdersListPayload);
			allOrdersList = json.toJsonTree(allOrdersListPayloadConfig);
			
			AccountManagementOrdersConfig accountManagementOrdersConfig = new
					AccountManagementOrdersConfig(
							getUserOrdersListJson,
					getOrderDetailsJson,
					getOrderPackingListJson,
					triggerReOrderJson,
					fetchAddressPayload,
					myOrdersList);
			accountManagementOrdersConfig.setAllOrdersList(allOrdersList);
			accountManagementOrdersConfig.setCancelOrderJson(cancelOrderJson);
			accountManagementOrdersConfig.setTrackingNumberConfig(trackingNumberJson);
			userOrdersConfig = json.toJson(accountManagementOrdersConfig);

		}
	}

	/**
	 * Gets the user orders list labels.
	 *
	 * @return the user orders list labels
	 */
	@Override
	public String getUserOrdersListLabels() {
		return userOrdersListLabels;
	}

	/**
	 * Gets the user orders config.
	 *
	 * @return the user orders config
	 */
	@Override
	public String getUserOrdersConfig() {
		return userOrdersConfig;
	}
	
	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	public String getHybrisSiteId() {
		return hybrisSiteId;
	}
}
