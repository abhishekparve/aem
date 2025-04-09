package com.bdb.aem.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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

import com.bdb.aem.core.models.AccountManagementOrdersApprovalModel;
import com.bdb.aem.core.pojo.AccountManagementOrdersApprovalConfig;
import com.bdb.aem.core.pojo.AccountManagementOrdersApprovalLabels;
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

/**
 * The Class AccountManagementOrdersApprovalModelImpl.
 */
@Model( adaptables = { SlingHttpServletRequest.class, Resource.class }, 
		adapters = { AccountManagementOrdersApprovalModel.class },
		resourceType = { AccountManagementOrdersApprovalModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccountManagementOrdersApprovalModelImpl implements AccountManagementOrdersApprovalModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(AccountManagementOrdersApprovalModelImpl.class);
	
	/** The current page. */
	@Inject
	private Page currentPage;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	/** The orders approval header. */
	@Inject
	@Via("resource")
	private String ordersApprovalHeader;
	
	/** The order placed by text. */
	@Inject
	@Via("resource")
	private String orderPlacedByText;
	
	/** The date placed. */
	@Inject
	@Via("resource")
	private String datePlaced;
	
	/** The status. */
	@Inject
	@Via("resource")
	private String status;
	
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
	
	/** The ending with label. */
	@Inject
	@Via("resource")
	private String endingWithLabel;
	
	/** The view details CTA. */
	@Inject
	@Via("resource")
	private String viewDetailsCTA;
	
	/** The no approval list img src. */
	@Inject
	@Via("resource")
	private String noApprovalListImgSrc;
	
	/** The no approval list img alt text. */
	@Inject
	@Via("resource")
	private String noApprovalListImgAltText;
	
	/** The no approval list msg. */
	@Inject
	@Via("resource")
	private String noApprovalListMsg;
	
	/** The no approval list info. */
	@Inject
	@Via("resource")
	private String noApprovalListInfo;
	
	/** The orders number header. */
	@Inject
	@Via("resource")
	private String ordersNumberHeader;
	
	/** The order issues label. */
	@Inject
	@Via("resource")
	private String orderIssuesLabel;
	
	/** The contact us link. */
	@Inject
	@Via("resource")
	private String contactUsLink;
	
	/** The decline order CTA label. */
	@Inject
	@Via("resource")
	private String declineOrderCTALabel;
	
	/** The approve order CTA label. */
	@Inject
	@Via("resource")
	private String approveOrderCTALabel;

	/** The approve order modal label. */
	@Inject
	@Via("resource")
	private String approveOrderModalLabel;
	
	/** The additional comments text. */
	@Inject
	@Via("resource")
	private String additionalCommentsText;
	
	/** The comments placeholder. */
	@Inject
	@Via("resource")
	private String commentsPlaceholder;
	
	/** The comments text optional. */
	@Inject
	@Via("resource")
	private String commentsTextOptional;
	
	/** The comments placeholder optional. */
	@Inject
	@Via("resource")
	private String commentsPlaceholderOptional;
	
	/** The comments max limit. */
	@Inject
	@Via("resource")
	private String commentsMaxLimit;
	
	/** The comments error msg. */
	@Inject
	@Via("resource")
	private String commentsErrorMsg;
	
	/** The modal cancel cta. */
	@Inject
	@Via("resource")
	private String modalCancelCta;
	
	/** The modal approve cta. */
	@Inject
	@Via("resource")
	private String modalApproveCta;
	
	/** The decline order modal title. */
	@Inject
	@Via("resource")
	private String declineOrderModalTitle;
	
	/** The modal decline cta. */
	@Inject
	@Via("resource")
	private String modalDeclineCta;
	
	/** The order details heading. */
	@Inject
	@Via("resource")
	private String orderDetailsHeading;
	
	/** The po number error msg. */
	@Inject
	@Via("resource")
	private String poNumberErrorMsg;
	
	/** The order details order number. */
	@Inject
	@Via("resource")
	private String orderDetailsOrderNumber;
	
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
	
	/** The status history heading. */
	@Inject
	@Via("resource")
	private String statusHistoryHeading;
	
	/** The date. */
	@Inject
	@Via("resource")
	private String date;
	
	/** The user. */
	@Inject
	@Via("resource")
	private String user;
	
	/** The email. */
	@Inject
	@Via("resource")
	private String email;
	
	/** The comments. */
	@Inject
	@Via("resource")
	private String comments;

	/** The address details heading. */
	@Inject
	@Via("resource")
	private String addressDetailsHeading;

	/** The special instructions heading. */
	@Inject
	@Via("resource")
	private String specialInstructionsHeading;

	/** The products heading. */
	@Inject
	@Via("resource")
	private String productsHeading;
	
	/** The your price label. */
	@Inject
	@Via("resource")
	private String yourPriceLabel;
	
	/** The list price label. */
	@Inject
	@Via("resource")
	private String listPriceLabel;
	
	/** The default PRD image. */
	@Inject
	@Via("resource")
	private String defaultPRDImage;
	
	/** The default PRD image alt text. */
	@Inject
	@Via("resource")
	private String defaultPRDImageAltText;
	
	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;
	
	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The orders approval list labels. */
	private String ordersApprovalListLabels;
	
	/** The order approval config. */
	private String orderApprovalConfig;
	
	/** The hybris site id. */
	private String hybrisSiteId;

	/**
	 * Creates and Initializes the model with the Labels and Configs.
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Orders Approval Init Method");					
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		setOrdersApprovalListLabels(excludeUtilObject);
		setOrderApprovalConfig(excludeUtilObject);
	}
	
	/**
	 * Sets the orders approval list labels.
	 *
	 * @param excludeUtilObject the new orders approval list labels
	 */
	private void setOrdersApprovalListLabels(ExcludeUtil excludeUtilObject) {
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		
		JsonElement orderHistoryDetails;
		JsonElement orderDetails;
		JsonElement amountSummary;
		JsonElement statusHistoryDetails;
		JsonElement addressDetails;
		JsonElement specialInstructions;
		JsonElement products;
		JsonElement info;
		JsonElement icon;
		
		AccountManagementOrdersApprovalLabels ordersApprovalLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels orderHistoryDetailsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels orderDetailsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels amountSummaryLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels statusHistoryDetailsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels addressDetailsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels specialInstructionsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels productsLabels = new AccountManagementOrdersApprovalLabels();
		AccountManagementOrdersApprovalLabels infoLabels = new AccountManagementOrdersApprovalLabels();		
		AccountManagementOrdersApprovalLabels iconLabels = new AccountManagementOrdersApprovalLabels();
		
		ordersApprovalLabels.setOrdersApprovalHeader(ordersApprovalHeader);
		String orderNumber = CommonHelper.getLabel(CommonConstants.ORDER_NUMBER_LABEL, currentPage);
		ordersApprovalLabels.setOrderNumber(orderNumber);
		ordersApprovalLabels.setOrderPlacedByText(orderPlacedByText);
		ordersApprovalLabels.setDatePlaced(datePlaced);
		ordersApprovalLabels.setStatus(status);
		ordersApprovalLabels.setPendingApproval(pendingApproval);
		ordersApprovalLabels.setApproved(approved);
		ordersApprovalLabels.setDeclined(declined);
		String paymentType = CommonHelper.getLabel(CommonConstants.PAYMENT_TYPE_LABEL, currentPage);
		String quoteReferenceNumber = CommonHelper.getLabel(CommonConstants.QUOTE_REFERENCE_NUMBER, currentPage);
		ordersApprovalLabels.setPaymentType(paymentType);
		ordersApprovalLabels.setEndingWithLabel(endingWithLabel);
		ordersApprovalLabels.setViewDetailsCTA(viewDetailsCTA);
		ordersApprovalLabels.setNoApprovalListImgSrc(noApprovalListImgSrc);
		ordersApprovalLabels.setNoApprovalListImgAltText(noApprovalListImgAltText);
		ordersApprovalLabels.setNoApprovalListMsg(noApprovalListMsg);
		ordersApprovalLabels.setNoApprovalListInfo(noApprovalListInfo);
		ordersApprovalLabels.setQuotereferenceNumber(quoteReferenceNumber);
		orderDetailsLabels.setOrderDetailsHeading(orderDetailsHeading);
		String poNumber = CommonHelper.getLabel(CommonConstants.PO_NUMBER_LABEL, currentPage);
		String editCTA = CommonHelper.getLabel(CommonConstants.EDIT_LABEL, currentPage);
		String saveCTA  = CommonHelper.getLabel(CommonConstants.SAVE_LABEL, currentPage);
		String poDate = CommonHelper.getLabel(CommonConstants.PO_DATE_LABEL, currentPage);
		
		String deliveryMethod = CommonHelper.getLabel(CommonConstants.DELIVERY_METHOD_LABEL, currentPage);
		String statusOrderDetails = CommonHelper.getLabel(CommonConstants.ORDER_DETAILS_STATUS_LABEL, currentPage);
		String sourceOrderDetails = CommonHelper.getLabel(CommonConstants.SOURCE_LABEL, currentPage);
		orderDetailsLabels.setPoNumber(poNumber);
		orderDetailsLabels.setQuotereferenceNumber(quoteReferenceNumber);
		orderDetailsLabels.setPoNumberErrorMsg(poNumberErrorMsg);
		

		orderDetailsLabels.setEditCTA(editCTA);
		orderDetailsLabels.setSaveCTA(saveCTA);
		orderDetailsLabels.setOrderDetailsOrderNumber(orderDetailsOrderNumber);
		
		orderDetailsLabels.setPoDate(poDate);
		String orderPlacedBy = CommonHelper.getLabel(CommonConstants.ORDER_PLACED_BY_LABEL, currentPage);
		orderDetailsLabels.setOrderDetailsOrderPlacedBy(orderPlacedBy);
		
		orderDetailsLabels.setDeliveryMethod(deliveryMethod);
		orderDetailsLabels.setOrderDetailStatus(statusOrderDetails);
		orderDetailsLabels.setSource(sourceOrderDetails);
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
		String surchargesToolTip = CommonHelper.getLabel(CommonConstants.SURCHARGES_TOOL_TIP_LABEL, currentPage);
		String taxToolTip = CommonHelper.getLabel(CommonConstants.TAX_TOOL_TIP_LABEL, currentPage);
		String grantAmountUsed = CommonHelper.getLabel(CommonConstants.GRANT_AMOUNT_USED, currentPage);
		amountSummaryLabels.setSubtotal(subtotal);
		amountSummaryLabels.setShippingAndHandling(shippingAndHandling);
		amountSummaryLabels.setTaxes(taxes);
		amountSummaryLabels.setSurcharge(surcharge);
		amountSummaryLabels.setPromotion(promotion);
		amountSummaryLabels.setTotalSavings(totalSavings);
		amountSummaryLabels.setTotal(total);
		amountSummaryLabels.setSurchargesToolTip(surchargesToolTip);
		amountSummaryLabels.setTaxToolTip(taxToolTip);
		amountSummaryLabels.setGrantAmountUsed(grantAmountUsed);
		amountSummary = json.toJsonTree(amountSummaryLabels);
		
		statusHistoryDetailsLabels.setStatusHistoryHeading(statusHistoryHeading);
		statusHistoryDetailsLabels.setDate(date);
		statusHistoryDetailsLabels.setUser(user);
		statusHistoryDetailsLabels.setEmail(email);
		statusHistoryDetailsLabels.setComments(comments);
		statusHistoryDetails = json.toJsonTree(statusHistoryDetailsLabels);
		
		addressDetailsLabels.setAddressDetailsHeading(addressDetailsHeading);
		String shipToLabel = CommonHelper.getLabel(CommonConstants.SHIP_TO_LABEL, currentPage);
		String billToLabel = CommonHelper.getLabel(CommonConstants.BILL_TO_LABEL, currentPage);
		String soldToLabel = CommonHelper.getLabel(CommonConstants.SOLD_TO_LABEL , currentPage);
		addressDetailsLabels.setShipToLabel(shipToLabel);
		addressDetailsLabels.setBillToLabel(billToLabel);
		addressDetailsLabels.setSoldToLabel(soldToLabel);
		
		String shipToNumberLabel = CommonHelper.getLabel(CommonConstants.SHIP_TO_NUMBER_LABEL, currentPage);
		String billToNumberLabel = CommonHelper.getLabel(CommonConstants.BILL_TO_NUMBER_LABEL, currentPage);
		String soldToNumberLabel = CommonHelper.getLabel(CommonConstants.SOLD_TO_NUMBER_LABEL, currentPage);
		addressDetailsLabels.setShipToNumberLabel(shipToNumberLabel);
		addressDetailsLabels.setBillToNumberLabel(billToNumberLabel);
		addressDetailsLabels.setSoldToNumberLabel(soldToNumberLabel);
		addressDetails = json.toJsonTree(addressDetailsLabels);
		
		specialInstructionsLabels.setSpecialInstructionsHeading(specialInstructionsHeading);
		specialInstructions = json.toJsonTree(specialInstructionsLabels);
		
		productsLabels.setProductsHeading(productsHeading);
		String estDeliveryDate = CommonHelper.getLabel(CommonConstants.EST_DELIVERY_DATE_LABEL, currentPage);
		String quantityLabel = CommonHelper.getLabel(CommonConstants.QUANTITY_PENDING_LABEL, currentPage);
		String pricePerUnit = CommonHelper.getLabel(CommonConstants.PRICE_PER_UNIT_LABEL, currentPage);
		String totalPriceLabel = CommonHelper.getLabel(CommonConstants.TOTAL_PRICE_LABEL, currentPage);
		productsLabels.setEstDeliveryDate(estDeliveryDate);
		productsLabels.setQuantityLabel(quantityLabel);
		productsLabels.setPricePerUnit(pricePerUnit);
		productsLabels.setYourPriceLabel(yourPriceLabel);
		productsLabels.setListPriceLabel(listPriceLabel);
		productsLabels.setTotalPriceLabel(totalPriceLabel);
		productsLabels.setDefaultPRDImage(defaultPRDImage);
		productsLabels.setDefaultPRDImageAltText(defaultPRDImageAltText);
		productsLabels.setPromocodeLabel(CommonHelper.getLabel(CommonConstants.PROMO_CODE, currentPage));
		productsLabels.setYouSavedLabel(CommonHelper.getLabel(CommonConstants.YOU_SAVED, currentPage));
		productsLabels.setOnLabel(CommonHelper.getLabel(CommonConstants.ON, currentPage));
		products = json.toJsonTree(productsLabels);
		
		orderHistoryDetailsLabels.setOrdersNumberHeader(ordersNumberHeader);
		orderHistoryDetailsLabels.setOrderIssuesLabel(orderIssuesLabel);
		String contactUsCTALabel = CommonHelper.getLabel(CommonConstants.CONTACT_US_LABEL, currentPage);
		orderHistoryDetailsLabels.setContactUsCTALabel(contactUsCTALabel);
		orderHistoryDetailsLabels.setContactUsLink(contactUsLink);
		orderHistoryDetailsLabels.setDeclineOrderCTALabel(declineOrderCTALabel);
		orderHistoryDetailsLabels.setApproveOrderCTALabel(approveOrderCTALabel);
		orderHistoryDetailsLabels.setApproveOrderModalLabel(approveOrderModalLabel);
		orderHistoryDetailsLabels.setAdditionalCommentsText(additionalCommentsText);
		orderHistoryDetailsLabels.setCommentsPlaceholder(commentsPlaceholder);
		orderHistoryDetailsLabels.setCommentsTextOptional(commentsTextOptional);
		orderHistoryDetailsLabels.setCommentsPlaceholderOptional(commentsPlaceholderOptional);
		orderHistoryDetailsLabels.setCommentsMaxLimit(commentsMaxLimit);
		orderHistoryDetailsLabels.setCommentsErrorMsg(commentsErrorMsg);
		orderHistoryDetailsLabels.setModalCancelCta(modalCancelCta);
		orderHistoryDetailsLabels.setModalApproveCta(modalApproveCta);
		orderHistoryDetailsLabels.setDeclineOrderModalTitle(declineOrderModalTitle);
		orderHistoryDetailsLabels.setModalDeclineCta(modalDeclineCta);
		orderHistoryDetailsLabels.setOrderDetails(orderDetails);
		orderHistoryDetailsLabels.setAmountSummary(amountSummary);
		orderHistoryDetailsLabels.setStatusHistoryDetails(statusHistoryDetails);
		orderHistoryDetailsLabels.setAddressDetails(addressDetails);
		orderHistoryDetailsLabels.setSpecialInstructions(specialInstructions);
		orderHistoryDetailsLabels.setProducts(products);
		orderHistoryDetails = json.toJsonTree(orderHistoryDetailsLabels);
		
		ordersApprovalLabels.setOrderHistoryDetails(orderHistoryDetails);
		ordersApprovalListLabels = json.toJson(ordersApprovalLabels);
	}
	
	/**
	 * Sets the order approval config.
	 *
	 * @param excludeUtilObject the new order approval config
	 */
	private void setOrderApprovalConfig(ExcludeUtil excludeUtilObject) {
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
	
		JsonElement getOrderApprovalDetailsJson;
		JsonElement getOrderApprovalListJson;
		JsonElement updateOrderApprovalStatusJson;
		JsonElement updatePoNumberJson;

		if (bdbApiEndpointService != null) {
			String orderDetailsApproverEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.orderDetailsApproverEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String orderListApproverEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.orderListApproverEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);	
			String orderApprovalDecisionEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.orderApprovalDecisionEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String updatePoNumberEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.updatePoNumberEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			
			Payload orderDetailsApproverPayload = new Payload(orderDetailsApproverEndpoint, HttpConstants.METHOD_GET);
			Payload orderListApproverPayload = new Payload(orderListApproverEndpoint, HttpConstants.METHOD_GET);
			Payload orderApprovalDecisionPayload = new Payload(orderApprovalDecisionEndpoint, HttpConstants.METHOD_POST);
			Payload updatePoNumberPayload = new Payload(updatePoNumberEndpoint, HttpConstants.METHOD_POST);
			
			PayloadConfig orderDetailsApproverPayloadConfig = new PayloadConfig(orderDetailsApproverPayload);
			getOrderApprovalDetailsJson = json.toJsonTree(orderDetailsApproverPayloadConfig);
			PayloadConfig orderListApproverPayloadConfig = new PayloadConfig(orderListApproverPayload);
			getOrderApprovalListJson = json.toJsonTree(orderListApproverPayloadConfig);
			PayloadConfig orderApprovalDecisionPayloadConfig = new PayloadConfig(orderApprovalDecisionPayload);
			updateOrderApprovalStatusJson = json.toJsonTree(orderApprovalDecisionPayloadConfig);
			PayloadConfig updatePoNumberPayloadConfig = new PayloadConfig(updatePoNumberPayload);
			updatePoNumberJson = json.toJsonTree(updatePoNumberPayloadConfig);
			
			AccountManagementOrdersApprovalConfig accountManagementOrdersApprovalConfig = new AccountManagementOrdersApprovalConfig(getOrderApprovalListJson, getOrderApprovalDetailsJson, updateOrderApprovalStatusJson, updatePoNumberJson);
			orderApprovalConfig = json.toJson(accountManagementOrdersApprovalConfig);
		}
	}

	/**
	 * Gets the orders approval list labels.
	 *
	 * @return the orders approval list labels
	 */
	@Override
	public String getOrdersApprovalListLabels() {
		return ordersApprovalListLabels;
	}

	/**
	 * Gets the order approval config.
	 *
	 * @return the order approval config
	 */
	@Override
	public String getOrderApprovalConfig() {
		return orderApprovalConfig;
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
