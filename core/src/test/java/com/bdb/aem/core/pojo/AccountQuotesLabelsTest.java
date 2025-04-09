package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

/**
 * The Class AccountQuotesLabelsTest.
 */
class AccountQuotesLabelsTest {
	
	/** The quotes test labels. */
	AccountQuotesLabels quotesTestLabels;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		quotesTestLabels = new AccountQuotesLabels();
        quotesTestLabels.setDistributorNameText("distributorNameText");
		quotesTestLabels.setDistributorEmailText("distributorEmailText");
		quotesTestLabels.setDistributorPhoneNumberText("distributorPhoneNumberText");
		quotesTestLabels.setShipToAddressText("shipToAddressText");
		quotesTestLabels.setShipToNumberLabel("shipToNumberLabel");
		quotesTestLabels.setAccMgrNameText("accMgrNameText");
		quotesTestLabels.setAccMgrEmailText("accMgrEmailText");
		quotesTestLabels.setAccMgrPhoneNumber("accMgrPhoneNumber");
		quotesTestLabels.setBillToAddressText("billToAddressText");
		quotesTestLabels.setBillToNumberLabel("billToNumberLabel");
		quotesTestLabels.setPendingLabel("pendingLabel");
		quotesTestLabels.setPayerAddressLabel("payerAddressLabel");
		quotesTestLabels.setPayToNumberLabel("payToNumberLabel");
        quotesTestLabels.setQuotesHeading("quotesHeading");
		quotesTestLabels.setQuotesSearchPlaceHolder("quotesSearchPlaceHolder");
		quotesTestLabels.setSearchAndFilterText("searchAndFilterText");
		quotesTestLabels.setViewQuoteLabel("viewQuoteLabel");
		quotesTestLabels.setSearchByLabel("searchByLabel");
		quotesTestLabels.setSearchButtonLabel("searchButtonLabel");
		quotesTestLabels.setQuoteNumberText("quoteNumberText");
		quotesTestLabels.setQuoteDateText("quoteDateText");
		quotesTestLabels.setQuoteStatusText("quoteStatusText");
		quotesTestLabels.setDateRangeText("dateRangeText");
		quotesTestLabels.setFromPlaceholderText("fromPlaceholderText");
		quotesTestLabels.setToPlaceholderText("toPlaceholderText");
		quotesTestLabels.setQuoteDetailsLabel(new JsonObject());
		quotesTestLabels.setNoQuotesFoundLabel("setNoQuotesFoundLabel");
		quotesTestLabels.setQuotesNumSearchLabel("setQuotesNumSearchLabel");
		quotesTestLabels.setProductNumSearchLabel("setProductNumSearchLabel");
		quotesTestLabels.setProductNameSearchLabel("setProductNameSearchLabel");
		quotesTestLabels.setEmptyQuotelabel("setEmptyQuotelabel");
		quotesTestLabels.setEmptyQuotedesc("setEmptyQuotedesc");
		quotesTestLabels.setEmptyQuoteImage("setEmptyQuoteImage");
		quotesTestLabels.setNoQuotesData("setNoQuotesData");	
		quotesTestLabels.setNoQuotesFoundInfo("setNoQuotesFoundInfo");
		quotesTestLabels.setEmptyQuoteImgAltText("setEmptyQuoteImgAltText");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		quotesTestLabels.getNoQuotesData();
		quotesTestLabels.getNoQuotesFoundInfo();
		quotesTestLabels.getEmptyQuoteImgAltText();
		quotesTestLabels.getProductNameSearchLabel();
		quotesTestLabels.getNoQuotesFoundLabel();
		quotesTestLabels.getQuotesNumSearchLabel();
		quotesTestLabels.getProductNumSearchLabel();
		quotesTestLabels.getEmptyQuotelabel();
		quotesTestLabels.getEmptyQuotedesc();
		quotesTestLabels.getEmptyQuoteImage();
        quotesTestLabels.getDistributorNameText();
		quotesTestLabels.getDistributorEmailText();
		quotesTestLabels.getDistributorPhoneNumberText();
		quotesTestLabels.getShipToAddressText();
		quotesTestLabels.getShipToNumberLabel();
		quotesTestLabels.getAccMgrNameText();
		quotesTestLabels.getAccMgrEmailText();
		quotesTestLabels.getAccMgrPhoneNumber();
		quotesTestLabels.getBillToAddressText();
		quotesTestLabels.getBillToNumberLabel();
		quotesTestLabels.getPendingLabel();
		quotesTestLabels.getPayerAddressLabel();
		quotesTestLabels.getPayToNumberLabel();
        quotesTestLabels.getQuotesHeading();
		quotesTestLabels.getQuotesSearchPlaceHolder();
		quotesTestLabels.getSearchAndFilterText();
		quotesTestLabels.getViewQuoteLabel();
		quotesTestLabels.getSearchByLabel();
		quotesTestLabels.getSearchButtonLabel();
		quotesTestLabels.getQuoteNumberText();
		quotesTestLabels.getQuoteDateText();
		quotesTestLabels.getQuoteStatusText();
		quotesTestLabels.getDateRangeText();
		quotesTestLabels.getFromPlaceholderText();
		quotesTestLabels.getToPlaceholderText();
		quotesTestLabels.getQuoteDetailsLabel();
	}
}
