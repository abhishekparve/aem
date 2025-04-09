package com.bdb.aem.core.pojo;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class AccountManagementOrdersApprovalLabelsTest.
 */
class AccountManagementOrdersApprovalLabelsTest {
	
	/** The orders approval test labels. */
	AccountManagementOrdersApprovalLabels ordersApprovalTestLabels;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		ordersApprovalTestLabels = new AccountManagementOrdersApprovalLabels();
		ordersApprovalTestLabels.setAdditionalCommentsText("additionalCommentsText");
		ordersApprovalTestLabels.setAddressDetails(new JsonObject());
		ordersApprovalTestLabels.setAddressDetailsHeading("addressDetailsHeading");
		ordersApprovalTestLabels.setAltText("altText");
		ordersApprovalTestLabels.setAmountSummary(new JsonObject());
		ordersApprovalTestLabels.setAmountSummaryHeading("amountSummaryHeading");
		ordersApprovalTestLabels.setAppprovalStatusLabel("appprovalStatusLabel");
		ordersApprovalTestLabels.setApproved("approved");
		ordersApprovalTestLabels.setApproveOrderCTALabel("approveOrderCTALabel");
		ordersApprovalTestLabels.setApproveOrderModalLabel("approveOrderModalLabel");
		ordersApprovalTestLabels.setBillToLabel("billToLabel");
		ordersApprovalTestLabels.setBillToNumberLabel("billToNumberLabel");
		ordersApprovalTestLabels.setCancelledLabel("cancelledLabel");
		ordersApprovalTestLabels.setCancelOrderCTALabel("cancelOrderCTALabel");
		ordersApprovalTestLabels.setCarrierNumberLabel("carrierNumberLabel");
		ordersApprovalTestLabels.setComments("comments");
		ordersApprovalTestLabels.setCommentsErrorMsg("commentsErrorMsg");
		ordersApprovalTestLabels.setCommentsMaxLimit("commentsMaxLimit");
		ordersApprovalTestLabels.setCommentsPlaceholder("commentsPlaceholder");
		ordersApprovalTestLabels.setCommentsPlaceholderOptional("commentsPlaceholderOptional");
		ordersApprovalTestLabels.setCommentsTextOptional("commentsTextOptional");
		ordersApprovalTestLabels.setCompleteLabel("completeLabel");
		ordersApprovalTestLabels.setContactUsCTALabel("contactUsCTALabel");
		ordersApprovalTestLabels.setContactUsLink("contactUsLink");
		ordersApprovalTestLabels.setDate("date");
		ordersApprovalTestLabels.setDateFormatLabel("dateFormatLabel");
		ordersApprovalTestLabels.setDatePlaced("datePlaced");
		ordersApprovalTestLabels.setDateRangeLabel("dateRangeLabel");
		ordersApprovalTestLabels.setDeclined("declined");
		ordersApprovalTestLabels.setDeclineOrderCTALabel("declineOrderCTALabel");
		ordersApprovalTestLabels.setDeclineOrderModalTitle("declineOrderModalTitle");
		ordersApprovalTestLabels.setDefaultPRDImage("defaultPRDImage");
		ordersApprovalTestLabels.setDefaultPRDImageAltText("defaultPRDImageAltText");
		ordersApprovalTestLabels.setDeliveryMethod("deliveryMethod");
		ordersApprovalTestLabels.setDownloadCSVCta("downloadCSVCta");
		ordersApprovalTestLabels.setEdiLabel("ediLabel");
		ordersApprovalTestLabels.setEditCTA("editCTA");
		ordersApprovalTestLabels.setEmail("email");
		ordersApprovalTestLabels.setEndingWithLabel("endingWithLabel");
		ordersApprovalTestLabels.setEstDeliveryDate("estDeliveryDate");
		ordersApprovalTestLabels.setFromDatePlaceholder("fromDatePlaceholder");
		ordersApprovalTestLabels.setIcon(new JsonObject());
		ordersApprovalTestLabels.setInfo(new JsonObject());
		ordersApprovalTestLabels.setInvoicesLabel("invoicesLabel");
		ordersApprovalTestLabels.setInvoicesText("invoicesText");
		ordersApprovalTestLabels.setListPriceLabel("listPriceLabel");
		ordersApprovalTestLabels.setMessage("message");
		ordersApprovalTestLabels.setModalApproveCta("modalApproveCta");
		ordersApprovalTestLabels.setModalCancelCta("modalCancelCta");
		ordersApprovalTestLabels.setModalDeclineCta("modalDeclineCta");
		ordersApprovalTestLabels.setMoreFiltersCta("moreFiltersCta");
		ordersApprovalTestLabels.setMoreFiltersLabels(new JsonObject());
		ordersApprovalTestLabels.setOfflineLabel("offlineLabel");
		ordersApprovalTestLabels.setOpenLabel("openLabel");
		ordersApprovalTestLabels.setOrderDetails(new JsonObject());
		ordersApprovalTestLabels.setOrderDetailsHeading("orderDetailsHeading");
		ordersApprovalTestLabels.setOrderDetailsOrderNumber("orderDetailsOrderNumber");
		ordersApprovalTestLabels.setOrderDetailsOrderPlacedBy("orderDetailsOrderPlacedBy");
		ordersApprovalTestLabels.setOrderDetailStatus("orderDetailStatus");
		ordersApprovalTestLabels.setOrderedLabel("orderedLabel");
		ordersApprovalTestLabels.setOrderHistoryDetails(new JsonObject());
		ordersApprovalTestLabels.setOrderIssuesLabel("orderIssuesLabel");
		ordersApprovalTestLabels.setOrderNumber("orderNumber");
		ordersApprovalTestLabels.setOrderNumberLabel("orderNumberLabel");
		ordersApprovalTestLabels.setOrderPlacedByText("orderPlacedByText");
		ordersApprovalTestLabels.setOrdersApprovalHeader("ordersApprovalHeader");
		ordersApprovalTestLabels.setOrdersNumberHeader("ordersNumberHeader");
		ordersApprovalTestLabels.setOrdersOrderPlacedByText("ordersOrderPlacedByText");
		ordersApprovalTestLabels.setOrdersOrderStatusText("ordersOrderStatusText");
		ordersApprovalTestLabels.setOrderStatusLabel("orderStatusLabel");
		ordersApprovalTestLabels.setOrdersTitle("ordersTitle");
		ordersApprovalTestLabels.setOrganizationLabel("organizationLabel");
		ordersApprovalTestLabels.setCancelOrderSuccessMessage("cancelOrderSuccessMessage");
		ordersApprovalTestLabels.setPaymentType("paymentType");
		ordersApprovalTestLabels.setPendingApproval("pendingApproval");
		ordersApprovalTestLabels.setPoDate("poDate");
		ordersApprovalTestLabels.setPoDateLabel("poDateLabel");
		ordersApprovalTestLabels.setPoNumber("poNumber");
		ordersApprovalTestLabels.setPoNumberErrorMsg("poNumberErrorMsg");
		ordersApprovalTestLabels.setPoNumberLabel("poNumberLabel");
		ordersApprovalTestLabels.setPricePerUnit("pricePerUnit");
		ordersApprovalTestLabels.setProducts(new JsonObject());
		ordersApprovalTestLabels.setProductsHeading("productsHeading");
		ordersApprovalTestLabels.setPromotion("promotion");
		ordersApprovalTestLabels.setQuantityLabel("quantityLabel");
		ordersApprovalTestLabels.setReOrderCTA("reOrderCTA");
		ordersApprovalTestLabels.setReOrderText("reOrderText");
		ordersApprovalTestLabels.setReOrderTitle("reOrderTitle");
		ordersApprovalTestLabels.setSaveCTA("saveCTA");
		ordersApprovalTestLabels.setSearchCta("searchCta");
		ordersApprovalTestLabels.setSearchOrdersPlaceholderText("searchOrdersPlaceholderText");
		ordersApprovalTestLabels.setSelfLabel("selfLabel");
		ordersApprovalTestLabels.setShipmentNumberLabel("shipmentNumberLabel");
		ordersApprovalTestLabels.setShippedQuantityLabel("shippedQuantityLabel");
		ordersApprovalTestLabels.setShipmentsCancelledLabel("shipmentsCancelledLabel");
		ordersApprovalTestLabels.setShipmentsCompleteLabel("shipmentsCompleteLabel");
		ordersApprovalTestLabels.setShipmentsLabels(new JsonObject());
		ordersApprovalTestLabels.setShipmentsOpenLabel("shipmentsOpenLabel");
		ordersApprovalTestLabels.setShipmentsStatusLabel("shipmentsStatusLabel");
		ordersApprovalTestLabels.setShipmentsTitle("shipmentsTitle");
		ordersApprovalTestLabels.setShippingAndHandling("shippingAndHandling");
		ordersApprovalTestLabels.setShipToAddressLabel("shipToAddressLabel");
		ordersApprovalTestLabels.setShipToLabel("shipToLabel");
		ordersApprovalTestLabels.setShipToNumberLabel("shipToNumberLabel");
		ordersApprovalTestLabels.setSoldToLabel("soldToLabel");
		ordersApprovalTestLabels.setSoldToNumberLabel("soldToNumberLabel");
		ordersApprovalTestLabels.setSource("source");
		ordersApprovalTestLabels.setSourceLabel("sourceLabel");
		ordersApprovalTestLabels.setSpecialInstructions(new JsonObject());
		ordersApprovalTestLabels.setSpecialInstructionsHeading("specialInstructionsHeading");
		ordersApprovalTestLabels.setSrc("src");
		ordersApprovalTestLabels.setStatus("status");
		ordersApprovalTestLabels.setStatusHistoryDetails(new JsonObject());
		ordersApprovalTestLabels.setStatusHistoryHeading("statusHistoryHeading");
		ordersApprovalTestLabels.setStatusLabel("statusLabel");
		ordersApprovalTestLabels.setSubtotal("subtotal");
		ordersApprovalTestLabels.setSurcharge("surcharge");
		ordersApprovalTestLabels.setSurchargesToolTip("surchargesToolTip");
		ordersApprovalTestLabels.setTaxes("taxes");
		ordersApprovalTestLabels.setTaxToolTip("taxToolTip");
		ordersApprovalTestLabels.setToDatePlaceholder("toDatePlaceholder");
		ordersApprovalTestLabels.setTotal("total");
		ordersApprovalTestLabels.setTotalLabel("totalLabel");
		ordersApprovalTestLabels.setTotalPriceLabel("totalPriceLabel");
		ordersApprovalTestLabels.setTotalSavings("totalSavings");
		ordersApprovalTestLabels.setTrackOrdersText("trackOrdersText");
		ordersApprovalTestLabels.setTrackOrdersTitle("trackOrdersTitle");
		ordersApprovalTestLabels.setUser("user");
		ordersApprovalTestLabels.setViewDetailsCTA("viewDetailsCTA");
		ordersApprovalTestLabels.setViewMoreCta("viewMoreCta");
		ordersApprovalTestLabels.setCardNumberLabel("cardNumberLabel");
		ordersApprovalTestLabels.setViewPendingCta("viewPendingCta");
		ordersApprovalTestLabels.setViewShippedCta("viewShippedCta");
		ordersApprovalTestLabels.setWebLabel("webLabel");
		ordersApprovalTestLabels.setYourPriceLabel("yourPriceLabel");
		ordersApprovalTestLabels.setViewShippedLinkMessage("viewShippedLinkMessage");
		ordersApprovalTestLabels.setViewPendingLinkMessage("viewPendingLinkMessage");
		ordersApprovalTestLabels.setPendingStatusLabel("pendingStatusLabel");
		ordersApprovalTestLabels.setTrackingNumberLabel("trackingNumberLabel");
		ordersApprovalTestLabels.setPackingListLabel("packingListLabel");
		ordersApprovalTestLabels.setInvoiceNumberLabel("invoiceNumberLabel");
		ordersApprovalTestLabels.setPromocodeLabel("promocodeLabel");
		ordersApprovalTestLabels.setNoApprovalListImgSrc("noApprovalListImgSrc");
		ordersApprovalTestLabels.setNoApprovalListImgAltText("noApprovalListImgAltText");
		ordersApprovalTestLabels.setNoApprovalListMsg("noApprovalListMsg");
		ordersApprovalTestLabels.setNoApprovalListInfo("noApprovalListInfo");
		ordersApprovalTestLabels.setYouSavedLabel("youSavedLabel");
		ordersApprovalTestLabels.setOnLabel("onLabel");
		ordersApprovalTestLabels.setPaymentMethod("paymentMethod");
		ordersApprovalTestLabels.setGrantAmountUsed("grantAmountUsed");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(ordersApprovalTestLabels.getOrdersApprovalHeader(),"ordersApprovalHeader");
		Assert.assertEquals(ordersApprovalTestLabels.getOrderNumber(), "orderNumber");
		Assert.assertEquals(ordersApprovalTestLabels.getOrderPlacedByText(), "orderPlacedByText");
		Assert.assertEquals(ordersApprovalTestLabels.getOrdersTitle(),"ordersTitle");
		Assert.assertNotNull(ordersApprovalTestLabels.getAdditionalCommentsText());
		Assert.assertNotNull(ordersApprovalTestLabels.getAddressDetails());
		Assert.assertNotNull(ordersApprovalTestLabels.getAddressDetailsHeading());
		Assert.assertNotNull(ordersApprovalTestLabels.getAltText());
		Assert.assertNotNull(ordersApprovalTestLabels.getAmountSummary());
		Assert.assertNotNull(ordersApprovalTestLabels.getAmountSummaryHeading());
		Assert.assertNotNull(ordersApprovalTestLabels.getAppprovalStatusLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getApproved());
		Assert.assertNotNull(ordersApprovalTestLabels.getApproveOrderCTALabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getApproveOrderModalLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getBillToLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getBillToNumberLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getCancelledLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getCancelOrderCTALabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getCarrierNumberLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getComments());
		Assert.assertNotNull(ordersApprovalTestLabels.getCommentsErrorMsg());
		Assert.assertNotNull(ordersApprovalTestLabels.getCommentsMaxLimit());
		Assert.assertNotNull(ordersApprovalTestLabels.getCommentsPlaceholder());
		Assert.assertNotNull(ordersApprovalTestLabels.getCommentsPlaceholderOptional());
		Assert.assertNotNull(ordersApprovalTestLabels.getCommentsTextOptional());
		Assert.assertNotNull(ordersApprovalTestLabels.getCompleteLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getContactUsCTALabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getContactUsLink());
		Assert.assertNotNull(ordersApprovalTestLabels.getDate());
		Assert.assertNotNull(ordersApprovalTestLabels.getDateFormatLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getDatePlaced());
		Assert.assertNotNull(ordersApprovalTestLabels.getDateRangeLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getDeclined());
		Assert.assertNotNull(ordersApprovalTestLabels.getDeclineOrderCTALabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getDeclineOrderModalTitle());
		Assert.assertNotNull(ordersApprovalTestLabels.getDefaultPRDImage());
		Assert.assertNotNull(ordersApprovalTestLabels.getDefaultPRDImageAltText());
		Assert.assertNotNull(ordersApprovalTestLabels.getDeliveryMethod());
		Assert.assertNotNull(ordersApprovalTestLabels.getDownloadCSVCta());
		Assert.assertNotNull(ordersApprovalTestLabels.getEdiLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getEditCTA());
		Assert.assertNotNull(ordersApprovalTestLabels.getEmail());
		Assert.assertNotNull(ordersApprovalTestLabels.getEndingWithLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getEstDeliveryDate());
		Assert.assertNotNull(ordersApprovalTestLabels.getFromDatePlaceholder());
		Assert.assertNotNull(ordersApprovalTestLabels.getIcon());
		Assert.assertNotNull(ordersApprovalTestLabels.getInfo());
		Assert.assertNotNull(ordersApprovalTestLabels.getInvoicesLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getInvoicesText());
		Assert.assertNotNull(ordersApprovalTestLabels.getListPriceLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getMessage());
		Assert.assertNotNull(ordersApprovalTestLabels.getModalApproveCta());
		Assert.assertNotNull(ordersApprovalTestLabels.getModalCancelCta());
		Assert.assertNotNull(ordersApprovalTestLabels.getModalDeclineCta());
		Assert.assertNotNull(ordersApprovalTestLabels.getMoreFiltersCta());
		Assert.assertNotNull(ordersApprovalTestLabels.getMoreFiltersLabels());
		Assert.assertNotNull(ordersApprovalTestLabels.getOfflineLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getOpenLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrderDetails());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrderDetailsHeading());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrderDetailsOrderNumber());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrderDetailsOrderPlacedBy());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrderDetailStatus());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrderedLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrderHistoryDetails());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrderIssuesLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrderNumberLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrdersNumberHeader());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrdersOrderPlacedByText());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrdersOrderStatusText());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrderStatusLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getOrganizationLabel());
		assertNotNull(ordersApprovalTestLabels.getCancelOrderSuccessMessage());
		Assert.assertNotNull(ordersApprovalTestLabels.getPaymentType());
		Assert.assertNotNull(ordersApprovalTestLabels.getPendingApproval());
		Assert.assertNotNull(ordersApprovalTestLabels.getPoDate());
		Assert.assertNotNull(ordersApprovalTestLabels.getPoDateLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getPoNumber());
		Assert.assertNotNull(ordersApprovalTestLabels.getPoNumberErrorMsg());
		Assert.assertNotNull(ordersApprovalTestLabels.getPoNumberLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getPricePerUnit());
		Assert.assertNotNull(ordersApprovalTestLabels.getProducts());
		Assert.assertNotNull(ordersApprovalTestLabels.getProductsHeading());
		Assert.assertNotNull(ordersApprovalTestLabels.getPromotion());
		Assert.assertNotNull(ordersApprovalTestLabels.getQuantityLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getReOrderCTA());
		Assert.assertNotNull(ordersApprovalTestLabels.getReOrderText());
		Assert.assertNotNull(ordersApprovalTestLabels.getReOrderTitle());
		Assert.assertNotNull(ordersApprovalTestLabels.getSaveCTA());
		Assert.assertNotNull(ordersApprovalTestLabels.getSearchCta());
		Assert.assertNotNull(ordersApprovalTestLabels.getSearchOrdersPlaceholderText());
		Assert.assertNotNull(ordersApprovalTestLabels.getSelfLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getShipmentNumberLabel());
		assertEquals("shippedQuantityLabel", ordersApprovalTestLabels.getShippedQuantityLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getShipmentsCancelledLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getShipmentsCompleteLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getShipmentsLabels());
		Assert.assertNotNull(ordersApprovalTestLabels.getShipmentsOpenLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getShipmentsStatusLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getShipmentsTitle());
		Assert.assertNotNull(ordersApprovalTestLabels.getShippingAndHandling());
		Assert.assertNotNull(ordersApprovalTestLabels.getShipToAddressLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getShipToLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getShipToNumberLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getSoldToLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getSoldToNumberLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getSource());
		Assert.assertNotNull(ordersApprovalTestLabels.getSourceLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getSpecialInstructions());
		Assert.assertNotNull(ordersApprovalTestLabels.getSpecialInstructionsHeading());
		Assert.assertNotNull(ordersApprovalTestLabels.getSrc());
		Assert.assertNotNull(ordersApprovalTestLabels.getStatus());
		Assert.assertNotNull(ordersApprovalTestLabels.getStatusHistoryDetails());
		Assert.assertNotNull(ordersApprovalTestLabels.getStatusHistoryHeading());
		Assert.assertNotNull(ordersApprovalTestLabels.getStatusLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getSubtotal());
		Assert.assertNotNull(ordersApprovalTestLabels.getSurcharge());
		Assert.assertNotNull(ordersApprovalTestLabels.getSurchargesToolTip());
		Assert.assertNotNull(ordersApprovalTestLabels.getTaxes());
		Assert.assertNotNull(ordersApprovalTestLabels.getTaxToolTip());
		Assert.assertNotNull(ordersApprovalTestLabels.getToDatePlaceholder());
		Assert.assertNotNull(ordersApprovalTestLabels.getTotal());
		Assert.assertNotNull(ordersApprovalTestLabels.getTotalLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getTotalPriceLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getTotalSavings());
		Assert.assertNotNull(ordersApprovalTestLabels.getTrackOrdersText());
		Assert.assertNotNull(ordersApprovalTestLabels.getTrackOrdersTitle());
		Assert.assertNotNull(ordersApprovalTestLabels.getUser());
		Assert.assertNotNull(ordersApprovalTestLabels.getViewDetailsCTA());
		Assert.assertNotNull(ordersApprovalTestLabels.getViewMoreCta());
		Assert.assertNotNull(ordersApprovalTestLabels.getViewPendingCta());
		Assert.assertNotNull(ordersApprovalTestLabels.getViewShippedCta());
		Assert.assertNotNull(ordersApprovalTestLabels.getWebLabel());
		Assert.assertNotNull(ordersApprovalTestLabels.getYourPriceLabel());
		assertNotNull(ordersApprovalTestLabels.getViewShippedLinkMessage());
		assertNotNull(ordersApprovalTestLabels.getViewPendingLinkMessage());
		assertNotNull(ordersApprovalTestLabels.getPendingStatusLabel());
		assertNotNull(ordersApprovalTestLabels.getTrackingNumberLabel());
		assertNotNull(ordersApprovalTestLabels.getPackingListLabel());
		assertNotNull(ordersApprovalTestLabels.getInvoiceNumberLabel());
		assertNotNull(ordersApprovalTestLabels.getCardNumberLabel());
		assertNotNull(ordersApprovalTestLabels.getNoApprovalListImgSrc());
		assertNotNull(ordersApprovalTestLabels.getNoApprovalListImgAltText());
		assertNotNull(ordersApprovalTestLabels.getNoApprovalListMsg());
		assertNotNull(ordersApprovalTestLabels.getNoApprovalListInfo());
		assertEquals("youSavedLabel", ordersApprovalTestLabels.getYouSavedLabel());
		assertEquals("onLabel", ordersApprovalTestLabels.getOnLabel());
		assertEquals("promocodeLabel", ordersApprovalTestLabels.getPromocodeLabel());
		assertEquals("paymentMethod", ordersApprovalTestLabels.getPaymentMethod());
		assertNotNull(ordersApprovalTestLabels.getGrantAmountUsed());
	}
	@Test
	void testGetters() {
		ordersApprovalTestLabels.getNoOrdersFound();
		ordersApprovalTestLabels.getNoOrdersFoundInfo();
		ordersApprovalTestLabels.getClearSearch();
		ordersApprovalTestLabels.getTypeHerePlaceHolder();	
		ordersApprovalTestLabels.getMaterialNoLabel();
		ordersApprovalTestLabels.getSearchFilter();
		ordersApprovalTestLabels.getSearchButton();
		ordersApprovalTestLabels.getSelectShipToAddress();
		ordersApprovalTestLabels.getAddressLabel();
		ordersApprovalTestLabels.getMyOrders();
		ordersApprovalTestLabels.getAllOrders();
		ordersApprovalTestLabels.getPoNumberHash();
		ordersApprovalTestLabels.getOrderNumberHash();
		ordersApprovalTestLabels.getInvoiceNumberHash();
		ordersApprovalTestLabels.getShipmentLabel();
		ordersApprovalTestLabels.getNoWebOrderMessage();
		ordersApprovalTestLabels.getOrderDisclaimer();
		ordersApprovalTestLabels.getTabLabel();
		ordersApprovalTestLabels.getMyWebOrders();
		ordersApprovalTestLabels.getShopOnlineLink();
		ordersApprovalTestLabels.getShopOnlineLabel();
	}
	@Test
	void testSetters() {
		ordersApprovalTestLabels.setShipmentLabel("setShipmentLabel");
		ordersApprovalTestLabels.setShopOnlineLabel("setShopOnlineLabel");
		ordersApprovalTestLabels.setShopOnlineLink("setShopOnlineLink");
		ordersApprovalTestLabels.setMyWebOrders("setMyWebOrders");
		ordersApprovalTestLabels.setTabLabel("setTabLabel");
		ordersApprovalTestLabels.setOrderDisclaimer("setOrderDisclaimer");
		ordersApprovalTestLabels.setNoWebOrderMessage("setNoWebOrderMessage");
		ordersApprovalTestLabels.setInvoiceNumberHash("setInvoiceNumberHash");
		ordersApprovalTestLabels.setOrderNumberHash("setOrderNumberHash");	
		ordersApprovalTestLabels.setPoNumberHash("setPoNumberHash");
		ordersApprovalTestLabels.setAllOrders("setAllOrders");
		ordersApprovalTestLabels.setMyOrders("setMyOrders");
		ordersApprovalTestLabels.setAddressLabel("setAddressLabel");
		ordersApprovalTestLabels.setSelectShipToAddress("setSelectShipToAddress");	
		ordersApprovalTestLabels.setSearchButton("setSearchButton");
		ordersApprovalTestLabels.setSearchFilter("setSearchFilter");
		ordersApprovalTestLabels.setMaterialNoLabel("setMaterialNoLabel");	
		ordersApprovalTestLabels.setTypeHerePlaceHolder("setTypeHerePlaceHolder");	
		ordersApprovalTestLabels.setClearSearch("setClearSearch");
		ordersApprovalTestLabels.setNoOrdersFoundInfo("setNoOrdersFoundInfo");
		ordersApprovalTestLabels.setNoOrdersFound("setNoOrdersFound");
		
	}
	
	
	
}
