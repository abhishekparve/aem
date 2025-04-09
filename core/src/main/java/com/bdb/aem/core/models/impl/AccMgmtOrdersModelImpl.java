package com.bdb.aem.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.AccMgmtOrdersModel;
import com.bdb.aem.core.util.ExcludeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * Implementation of interface AccMgmtOrdersModel serving as Sling model class
 * for Orders dialog in accountmanagement component
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = {
		AccMgmtOrdersModel.class }, resourceType = {
				AccMgmtOrdersModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AccMgmtOrdersModelImpl implements AccMgmtOrdersModel {

	// Adapted to SlingHttpServletRequest.class for i18 fields

	/** The Constant log. */
	protected static final Logger loggerAccMgmtOrders = LoggerFactory.getLogger(AccMgmtOrdersModelImpl.class);

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";

	/** Orders Labels variable holding all the fields */
	private String ordersLabels;

	/** The Orders Header. */
	@Inject
	@Via("resource")
	@SerializedName("ordersHeader")
	private String ordersHeader;

	/** The Orders Header. */
	@Inject
	@Via("resource")
	@SerializedName("ordersApprovalHeader")
	private String ordersApprovalHeader;

	/** The Track Orders Text. */
	@Inject
	@Via("resource")
	@SerializedName("trackOrdersText")
	private String trackOrdersText;

	/** The Reorder Text. */
	@Inject
	@Via("resource")
	@SerializedName("reorderText")
	private String reorderText;

	/** The Order Placed By Text. */
	@Inject
	@Via("resource")
	@SerializedName("orderPlacedByText")
	private String orderPlacedByText;

	/** The Date Range Text. */
	@Inject
	@Via("resource")
	@SerializedName("dateRangeText")
	private String dateRangeText;

	/** The Invoices Text. */
	@Inject
	@Via("resource")
	@SerializedName("invoicesText")
	private String invoicesText;

	/** The Ship to Address Text. */
	@Inject
	@Via("resource")
	@SerializedName("shipToAddressText")
	private String shipToAddressText;

	/** The Facing issues with the order Text. */
	@Inject
	@Via("resource")
	@SerializedName("facingIssuesWithTheOrderText")
	private String facingIssuesWithTheOrderText;

	/** The Contact Us Url. */
	@Inject
	@Via("resource")
	@SerializedName("contactUsLinkUrl")
	private String contactUsLinkUrl;

	/** The Cancel Order link Label. */
	@Inject
	@Via("resource")
	@SerializedName("cancelOrderLinkLabel")
	private String cancelOrderLinkLabel;

	/** The ReOrder CTA Label. */
	@Inject
	@Via("resource")
	@SerializedName("reorderCtaLabel")
	private String reorderCtaLabel;
	
	/** The Back to order CTA Label. */
	@Inject
	@Via("resource")
	@SerializedName("backToOrdersCTA")
	private String backToOrdersCTA;

	/** The Order Details Text. */
	@Inject
	@Via("resource")
	@SerializedName("orderDetailsText")
	private String orderDetailsText;

	/** The Download CSV CTA Label. */
	@Inject
	@Via("resource")
	@SerializedName("downloadCsvCtaLabel")
	private String downloadCsvCtaLabel;

	/** The Approval Status helptext Text. */
	@Inject
	@Via("resource")
	@SerializedName("approvalStatusHelptextText")
	private String approvalStatusHelptextText;

	/** The Account Summary header Text. */
	@Inject
	@Via("resource")
	@SerializedName("accountSummaryHeaderText")
	private String accountSummaryHeaderText;

	/** The Taxes helptext Text. */
	@Inject
	@Via("resource")
	@SerializedName("taxesHelptext	")
	private String taxesHelptext;

	/** The Surcharge helptext Text. */
	@Inject
	@Via("resource")
	@SerializedName("surchargeHelptext")
	private String surchargeHelptext;

	/** The Address Details header Text. */
	@Inject
	@Via("resource")
	@SerializedName("addressDetailsHeaderText")
	private String addressDetailsHeaderText;

	/** The Shipments Header Text. */
	@Inject
	@Via("resource")
	@SerializedName("shipmentsHeaderText")
	private String shipmentsHeaderText;

	/** The Shipment Number Text. */
	@Inject
	@Via("resource")
	@SerializedName("shipmentNumberText")
	private String shipmentNumberText;

	/** The Remaining Shipment message Text. */
	@Inject
	@Via("resource")
	@SerializedName("remainingShipmentMessageText")
	private String remainingShipmentMessageText;

	/** The View Pending link Label. */
	@Inject
	@Via("resource")
	@SerializedName("viewPendingLinkLabel")
	private String viewPendingLinkLabel;

	/** The Orders Number Header. */
	@Inject
	@Via("resource")
	@SerializedName("ordersNumberHeader")
	private String ordersNumberHeader;

	/** The Special Instructions header. */
	@Inject
	@Via("resource")
	@SerializedName("specialInstructionsheader")
	private String specialInstructionsheader;

	/** The Special Instructions Text. */
	@Inject
	@Via("resource")
	@SerializedName("specialInstructionsText")
	private String specialInstructionsText;

	/** The More filters Label. */
	@Inject
	@Via("resource")
	@SerializedName("moreFiltersLabel")
	private String moreFiltersLabel;

	/** The declineOrder. */
	@Inject
	@Via("resource")
	@SerializedName("declineOrder")
	private String declineOrder;

	/** The approveOrder Label. */
	@Inject
	@Via("resource")
	@SerializedName("approveOrder")
	private String approveOrder;

	/** The amountSummaryText. */
	@Inject
	@Via("resource")
	@SerializedName("amountSummaryText")
	private String amountSummaryText;

	/** The additionalCommentsText. */
	@Inject
	@Via("resource")
	@SerializedName("additionalCommentsText")
	private String additionalCommentsText;

	/** The cancelCtaLabel. */
	@Inject
	@Via("resource")
	@SerializedName("cancelCtaLabel")
	private String cancelCtaLabel;

	/** The approveCtaLabel. */
	@Inject
	@Via("resource")
	@SerializedName("approveCtaLabel")
	private String approveCtaLabel;

	/** The declineCtaLabel. */
	@Inject
	@Via("resource")
	@SerializedName("declineCtaLabel")
	private String declineCtaLabel;

	/** The maximumCharacterText. */
	@Inject
	@Via("resource")
	@SerializedName("maximumCharacterText")
	private String maximumCharacterText;


	/** The productsLabel. */
	@Inject
	@Via("resource")
	@SerializedName("productsLabel")
	private String productsLabel;
	/**
	 * Populates the dashboardPageNavigationLabels.
	 */
	@PostConstruct
	protected void init() {
		loggerAccMgmtOrders.debug("Inside Account Management Orders Init Method");
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		Gson accountSettingConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		ordersLabels = accountSettingConfigGson.toJson(this);
	}

	/**
	 * This method returns the labels related to Orders menu in account management
	 * page
	 * 
	 * @return ordersMenuLabels
	 */
	@Override
	public String getOrdersLabels() {
		return ordersLabels;
	}
}