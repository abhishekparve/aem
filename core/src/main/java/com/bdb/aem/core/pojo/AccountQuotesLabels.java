package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountQuotesLabels.
 */
public class AccountQuotesLabels {
	
	/** The quotes heading. */
	@SerializedName("heading")
	private String quotesHeading;
	
	/** The quotes search place holder. */
	@SerializedName("typeHerePlaceHolder")
	private String quotesSearchPlaceHolder;
	
	/** The search and filter text. */
	@SerializedName("searchAndFilterText")
	private String searchAndFilterText;
	
	/** The quote number text. */
	@SerializedName("quoteNumberText")
	private String quoteNumberText;
	
	/** The quote date text. */
	@SerializedName("quoteDateText")
	private String quoteDateText;
	
	/** The quote status text. */
	@SerializedName("quoteStatusText")
	private String quoteStatusText;
	
	/** The view quote label. */
	@SerializedName("viewQuotesLabel")
	private String viewQuoteLabel;
	
	/** The search by label. */
	@SerializedName("searchByLabel")
	private String searchByLabel;
	
	/** The date range text. */
	@SerializedName("dateRangeText")
	private String dateRangeText;
	
	/** The search button label. */
	@SerializedName("searchButtonLabel")
	private String searchButtonLabel;
	
	/** The from placeholder text. */
	@SerializedName("fromPlaceholderText")
	private String fromPlaceholderText;
	
	/** The to placeholder text. */
	@SerializedName("toPlaceholderText")
	private String toPlaceholderText;
	
	/** The quote details label. */
	@SerializedName("quoteDetailsLabel")
	private JsonElement quoteDetailsLabel;
	
	/** The distributor name text. */
	@SerializedName("distributorNameText")
	private String distributorNameText;
	
	/** The distributor email text. */
	@SerializedName("distributorEmailText")
	private String distributorEmailText;
	
	/** The distributor phone number text. */
	@SerializedName("distributorPhoneNumberText")
	private String distributorPhoneNumberText;
	
	/** The ship to address text. */
	@SerializedName("shipToAddressText")
	private String shipToAddressText;
	
	/** The ship to number label. */
	@SerializedName("shipToNumberLabel")
	private String shipToNumberLabel;
	
	/** The acc mgr name text. */
	@SerializedName("accMgrNameText")
	private String accMgrNameText;
	
	/** The acc mgr email text. */
	@SerializedName("accMgrEmailText")
	private String accMgrEmailText;
	
	/** The acc mgr phone number. */
	@SerializedName("accMgrPhoneNumber")
	private String accMgrPhoneNumber;
	
	/** The bill to address text. */
	@SerializedName("billToAddressText")
	private String billToAddressText;
	
	/** The bill to number label. */
	@SerializedName("billToNumberLabel")
	private String billToNumberLabel;
	
	/** The pending label. */
	@SerializedName("pendingLabel")
	private String pendingLabel;
	
	/** The payer address label. */
	@SerializedName("payerAddressText")
	private String payerAddressLabel;
	
	/** The pay to number label. */
	@SerializedName("payToNumberLabel")
	private String payToNumberLabel;

	/** The no Quotes Found Label. */
	@SerializedName("noQuotesFound")
	private String noQuotesFoundLabel;

	/** The quotes Num Search Label. */
	@SerializedName("quotesNumSearchLabel")
	private String quotesNumSearchLabel;

	/** The product Num Search Label. */
	@SerializedName("productNumSearchLabel")
	private String productNumSearchLabel;

	/** The product Name Search Label. */
	@SerializedName("productNameSearchLabel")
	private String productNameSearchLabel;

	/** The emptyQuotelabel. */
	@SerializedName("emptyQuotelabel")
	private String emptyQuotelabel;

	/** The emptyQuotedesc. */
	@SerializedName("emptyQuotedesc")
	private String emptyQuotedesc;

	/** The emptyQuoteImage. */
	@SerializedName("emptyQuoteImage")
	private String emptyQuoteImage;
	
	/** The noQuotesData. */
	@SerializedName("noQuotesData")
	private String noQuotesData;
	
	/** The noQuotesFoundInfo. */
	@SerializedName("noQuotesFoundInfo")
	private String noQuotesFoundInfo;
	
	/** The emptyQuoteImgAltText. */
	@SerializedName("emptyQuoteImgAltText")
	private String emptyQuoteImgAltText;
	
	/**
	 * Gets the quotes heading.
	 *
	 * @return the quotes heading
	 */
	public String getQuotesHeading() {
		return quotesHeading;
	}
	
	/**
	 * Sets the quotes heading.
	 *
	 * @param quotesHeading the new quotes heading
	 */
	public void setQuotesHeading(String quotesHeading) {
		this.quotesHeading = quotesHeading;
	}
	
	/**
	 * Gets the quotes search place holder.
	 *
	 * @return the quotes search place holder
	 */
	public String getQuotesSearchPlaceHolder() {
		return quotesSearchPlaceHolder;
	}
	
	/**
	 * Sets the quotes search place holder.
	 *
	 * @param quotesSearchPlaceHolder the new quotes search place holder
	 */
	public void setQuotesSearchPlaceHolder(String quotesSearchPlaceHolder) {
		this.quotesSearchPlaceHolder = quotesSearchPlaceHolder;
	}
	
	/**
	 * Gets the search and filter text.
	 *
	 * @return the search and filter text
	 */
	public String getSearchAndFilterText() {
		return searchAndFilterText;
	}
	
	/**
	 * Sets the search and filter text.
	 *
	 * @param searchAndFilterText the new search and filter text
	 */
	public void setSearchAndFilterText(String searchAndFilterText) {
		this.searchAndFilterText = searchAndFilterText;
	}
	
	/**
	 * Gets the quote number text.
	 *
	 * @return the quote number text
	 */
	public String getQuoteNumberText() {
		return quoteNumberText;
	}
	
	/**
	 * Sets the quote number text.
	 *
	 * @param quoteNumberText the new quote number text
	 */
	public void setQuoteNumberText(String quoteNumberText) {
		this.quoteNumberText = quoteNumberText;
	}
	
	/**
	 * Gets the quote date text.
	 *
	 * @return the quote date text
	 */
	public String getQuoteDateText() {
		return quoteDateText;
	}
	
	/**
	 * Sets the quote date text.
	 *
	 * @param quoteDateText the new quote date text
	 */
	public void setQuoteDateText(String quoteDateText) {
		this.quoteDateText = quoteDateText;
	}
	
	/**
	 * Gets the quote status text.
	 *
	 * @return the quote status text
	 */
	public String getQuoteStatusText() {
		return quoteStatusText;
	}
	
	/**
	 * Sets the quote status text.
	 *
	 * @param quoteStatusText the new quote status text
	 */
	public void setQuoteStatusText(String quoteStatusText) {
		this.quoteStatusText = quoteStatusText;
	}
	
	/**
	 * Gets the view quote label.
	 *
	 * @return the view quote label
	 */
	public String getViewQuoteLabel() {
		return viewQuoteLabel;
	}
	
	/**
	 * Sets the view quote label.
	 *
	 * @param viewQuoteLabel the new view quote label
	 */
	public void setViewQuoteLabel(String viewQuoteLabel) {
		this.viewQuoteLabel = viewQuoteLabel;
	}
	
	/**
	 * Gets the search by label.
	 *
	 * @return the search by label
	 */
	public String getSearchByLabel() {
		return searchByLabel;
	}
	
	/**
	 * Sets the search by label.
	 *
	 * @param searchByLabel the new search by label
	 */
	public void setSearchByLabel(String searchByLabel) {
		this.searchByLabel = searchByLabel;
	}
	
	/**
	 * Gets the date range text.
	 *
	 * @return the date range text
	 */
	public String getDateRangeText() {
		return dateRangeText;
	}
	
	/**
	 * Sets the date range text.
	 *
	 * @param dateRangeText the new date range text
	 */
	public void setDateRangeText(String dateRangeText) {
		this.dateRangeText = dateRangeText;
	}
	
	/**
	 * Gets the search button label.
	 *
	 * @return the search button label
	 */
	public String getSearchButtonLabel() {
		return searchButtonLabel;
	}
	
	/**
	 * Sets the search button label.
	 *
	 * @param searchButtonLabel the new search button label
	 */
	public void setSearchButtonLabel(String searchButtonLabel) {
		this.searchButtonLabel = searchButtonLabel;
	}
	
	/**
	 * Gets the from placeholder text.
	 *
	 * @return the from placeholder text
	 */
	public String getFromPlaceholderText() {
		return fromPlaceholderText;
	}
	
	/**
	 * Sets the from placeholder text.
	 *
	 * @param fromPlaceholderText the new from placeholder text
	 */
	public void setFromPlaceholderText(String fromPlaceholderText) {
		this.fromPlaceholderText = fromPlaceholderText;
	}
	
	/**
	 * Gets the to placeholder text.
	 *
	 * @return the to placeholder text
	 */
	public String getToPlaceholderText() {
		return toPlaceholderText;
	}
	
	/**
	 * Sets the to placeholder text.
	 *
	 * @param toPlaceholderText the new to placeholder text
	 */
	public void setToPlaceholderText(String toPlaceholderText) {
		this.toPlaceholderText = toPlaceholderText;
	}
	
	/**
	 * Gets the quote details label.
	 *
	 * @return the quote details label
	 */
	public JsonElement getQuoteDetailsLabel() {
		return quoteDetailsLabel;
	}
	
	/**
	 * Sets the quote details label.
	 *
	 * @param quoteDetailsLabel the new quote details label
	 */
	public void setQuoteDetailsLabel(JsonElement quoteDetailsLabel) {
		this.quoteDetailsLabel = quoteDetailsLabel;
	}
	
	/**
	 * Gets the distributor name text.
	 *
	 * @return the distributor name text
	 */
	public String getDistributorNameText() {
		return distributorNameText;
	}
	
	/**
	 * Sets the distributor name text.
	 *
	 * @param distributorNameText the new distributor name text
	 */
	public void setDistributorNameText(String distributorNameText) {
		this.distributorNameText = distributorNameText;
	}
	
	/**
	 * Gets the distributor email text.
	 *
	 * @return the distributor email text
	 */
	public String getDistributorEmailText() {
		return distributorEmailText;
	}
	
	/**
	 * Sets the distributor email text.
	 *
	 * @param distributorEmailText the new distributor email text
	 */
	public void setDistributorEmailText(String distributorEmailText) {
		this.distributorEmailText = distributorEmailText;
	}
	
	/**
	 * Gets the distributor phone number text.
	 *
	 * @return the distributor phone number text
	 */
	public String getDistributorPhoneNumberText() {
		return distributorPhoneNumberText;
	}
	
	/**
	 * Sets the distributor phone number text.
	 *
	 * @param distributorPhoneNumberText the new distributor phone number text
	 */
	public void setDistributorPhoneNumberText(String distributorPhoneNumberText) {
		this.distributorPhoneNumberText = distributorPhoneNumberText;
	}
	
	/**
	 * Gets the ship to address text.
	 *
	 * @return the ship to address text
	 */
	public String getShipToAddressText() {
		return shipToAddressText;
	}
	
	/**
	 * Sets the ship to address text.
	 *
	 * @param shipToAddressText the new ship to address text
	 */
	public void setShipToAddressText(String shipToAddressText) {
		this.shipToAddressText = shipToAddressText;
	}
	
	/**
	 * Gets the ship to number label.
	 *
	 * @return the ship to number label
	 */
	public String getShipToNumberLabel() {
		return shipToNumberLabel;
	}
	
	/**
	 * Sets the ship to number label.
	 *
	 * @param shipToNumberLabel the new ship to number label
	 */
	public void setShipToNumberLabel(String shipToNumberLabel) {
		this.shipToNumberLabel = shipToNumberLabel;
	}
	
	/**
	 * Gets the acc mgr name text.
	 *
	 * @return the acc mgr name text
	 */
	public String getAccMgrNameText() {
		return accMgrNameText;
	}
	
	/**
	 * Sets the acc mgr name text.
	 *
	 * @param accMgrNameText the new acc mgr name text
	 */
	public void setAccMgrNameText(String accMgrNameText) {
		this.accMgrNameText = accMgrNameText;
	}
	
	/**
	 * Gets the acc mgr email text.
	 *
	 * @return the acc mgr email text
	 */
	public String getAccMgrEmailText() {
		return accMgrEmailText;
	}
	
	/**
	 * Sets the acc mgr email text.
	 *
	 * @param accMgrEmailText the new acc mgr email text
	 */
	public void setAccMgrEmailText(String accMgrEmailText) {
		this.accMgrEmailText = accMgrEmailText;
	}
	
	/**
	 * Gets the acc mgr phone number.
	 *
	 * @return the acc mgr phone number
	 */
	public String getAccMgrPhoneNumber() {
		return accMgrPhoneNumber;
	}
	
	/**
	 * Sets the acc mgr phone number.
	 *
	 * @param accMgrPhoneNumber the new acc mgr phone number
	 */
	public void setAccMgrPhoneNumber(String accMgrPhoneNumber) {
		this.accMgrPhoneNumber = accMgrPhoneNumber;
	}
	
	/**
	 * Gets the bill to address text.
	 *
	 * @return the bill to address text
	 */
	public String getBillToAddressText() {
		return billToAddressText;
	}
	
	/**
	 * Sets the bill to address text.
	 *
	 * @param billToAddressText the new bill to address text
	 */
	public void setBillToAddressText(String billToAddressText) {
		this.billToAddressText = billToAddressText;
	}
	
	/**
	 * Gets the bill to number label.
	 *
	 * @return the bill to number label
	 */
	public String getBillToNumberLabel() {
		return billToNumberLabel;
	}
	
	/**
	 * Sets the bill to number label.
	 *
	 * @param billToNumberLabel the new bill to number label
	 */
	public void setBillToNumberLabel(String billToNumberLabel) {
		this.billToNumberLabel = billToNumberLabel;
	}

	/**
	 * Gets the pending label.
	 *
	 * @return the pending label
	 */
	public String getPendingLabel() {
		return pendingLabel;
	}

	/**
	 * Sets the pending label.
	 *
	 * @param pendingLabel the new pending label
	 */
	public void setPendingLabel(String pendingLabel) {
		this.pendingLabel = pendingLabel;
	}

	/**
	 * Gets the payer address label.
	 *
	 * @return the payer address label
	 */
	public String getPayerAddressLabel() {
		return payerAddressLabel;
	}

	/**
	 * Sets the payer address label.
	 *
	 * @param payerAddressLabel the new payer address label
	 */
	public void setPayerAddressLabel(String payerAddressLabel) {
		this.payerAddressLabel = payerAddressLabel;
	}

	/**
	 * Gets the pay to number label.
	 *
	 * @return the pay to number label
	 */
	public String getPayToNumberLabel() {
		return payToNumberLabel;
	}

	/**
	 * Sets the pay to number label.
	 *
	 * @param payToNumberLabel the new pay to number label
	 */
	public void setPayToNumberLabel(String payToNumberLabel) {
		this.payToNumberLabel = payToNumberLabel;
	}

	/**
	 * Sets the no Quotes Found Label.
	 *
	 * @param noQuotesFoundLabel the noQuotesFoundLabel
	 */
	public void setNoQuotesFoundLabel(String noQuotesFoundLabel) {
		this.noQuotesFoundLabel = noQuotesFoundLabel;
	}

	/**
	 * Sets the quotes Num Search Label.
	 *
	 * @param quotesNumSearchLabel the quotes Num Search Label
	 */
	public void setQuotesNumSearchLabel(String quotesNumSearchLabel) {
		this.quotesNumSearchLabel = quotesNumSearchLabel;
	}

	/**
	 * Sets the product Num Search Label.
	 *
	 * @param productNumSearchLabel productNumSearchLabel
	 */
	public void setProductNumSearchLabel(String productNumSearchLabel) {
		this.productNumSearchLabel = productNumSearchLabel;
	}

	/**
	 * Sets the productNameSearchLabel.
	 *
	 * @param productNameSearchLabel product Name Search Label
	 */
	public void setProductNameSearchLabel(String productNameSearchLabel) {
		this.productNameSearchLabel = productNameSearchLabel;
	}

	/**
	 * gets the noQuotesFoundLabel.
	 *
	 * @return the no quotes found label
	 */
	public String getNoQuotesFoundLabel() {
		return noQuotesFoundLabel;
	}

	/**
	 * gets the quotesNumSearchLabel.
	 *
	 * @return the quotes num search label
	 */
	public String getQuotesNumSearchLabel() {
		return quotesNumSearchLabel;
	}

	/**
	 * gets the productNumSearchLabel.
	 *
	 * @return the product num search label
	 */
	public String getProductNumSearchLabel() {
		return productNumSearchLabel;
	}

	/**
	 * gets the productNameSearchLabel.
	 *
	 * @return the product name search label
	 */
	public String getProductNameSearchLabel() {
		return productNameSearchLabel;
	}

	/**
	 * gets the emptyQuotelabel.
	 *
	 * @return the emptyQuotelabel
	 */
	public String getEmptyQuotelabel() {
		return emptyQuotelabel;
	}

	/**
	 * gets the emptyQuotedesc.
	 *
	 * @return the emptyQuotedesc
	 */
	public String getEmptyQuotedesc() {
		return emptyQuotedesc;
	}

	/**
	 * gets the emptyQuoteImage.
	 *
	 * @return the emptyQuoteImage
	 */
	public String getEmptyQuoteImage() {
		return emptyQuoteImage;
	}

	/**
	 * Sets the emptyQuotelabel.
	 *
	 * @param emptyQuotelabel emptyQuotelabel
	 */
	public void setEmptyQuotelabel(String emptyQuotelabel) {
		this.emptyQuotelabel = emptyQuotelabel;
	}

	/**
	 * Sets emptyQuotedesc.
	 *
	 * @param emptyQuotedesc emptyQuotedesc
	 */
	public void setEmptyQuotedesc(String emptyQuotedesc) {
		this.emptyQuotedesc = emptyQuotedesc;
	}

	/**
	 * Sets emptyQuoteImage.
	 *
	 * @param emptyQuoteImage emptyQuoteImage
	 */
	public void setEmptyQuoteImage(String emptyQuoteImage) {
		this.emptyQuoteImage = emptyQuoteImage;
	}
	
	/**
	 * Gets the noQuotesData.
	 *
	 * @return the noQuotesData
	 */
	public String getNoQuotesData() {
		return noQuotesData;
	}
	
	/**
	 * Sets the noQuotesData.
	 *
	 * @param noQuotesData the new noQuotesData
	 */
	public void setNoQuotesData(String noQuotesData) {
		this.noQuotesData = noQuotesData;
	}
	/**
	 * Gets the noQuotesFoundInfo.
	 *
	 * @return the noQuotesFoundInfo
	 */
	public String getNoQuotesFoundInfo() {
		return noQuotesFoundInfo;
	}
	
	/**
	 * Sets the noQuotesFoundInfo.
	 *
	 * @param noQuotesFoundInfo the new noQuotesFoundInfo
	 */
	public void setNoQuotesFoundInfo(String noQuotesFoundInfo) {
		this.noQuotesFoundInfo = noQuotesFoundInfo;
	}
	/**
	 * Gets the emptyQuoteImgAltText.
	 *
	 * @return the emptyQuoteImgAltText
	 */
	public String getEmptyQuoteImgAltText() {
		return emptyQuoteImgAltText;
	}
	
	/**
	 * Sets the emptyQuoteImgAltText.
	 *
	 * @param emptyQuoteImgAltText the new emptyQuoteImgAltText
	 */
	public void setEmptyQuoteImgAltText(String emptyQuoteImgAltText) {
		this.emptyQuoteImgAltText = emptyQuoteImgAltText;
	}
}
