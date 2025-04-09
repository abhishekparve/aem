package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountQuoteListsConfig.
 */
public class AccMgmtQuotesHistoryModelConfig {
	
	/** The get Quote History Details. */
	@SerializedName("getQuoteHistoryDetails")
	private PayloadConfig getQuoteHistoryDetails;
	
	/**
	 * Gets the gets the QuoteHistoryDetails.
	 *
	 * @return the gets the QuoteHistoryDetails
	 */
	public PayloadConfig getGetQuoteHistoryDetails() {
		return getQuoteHistoryDetails;
	}

	/**
	 * Sets the gets the QuoteHistoryDetails.
	 *
	 * @param getQuoteHistoryDetails the new QuoteHistoryDetails
	 */
	public void setGetQuoteHistoryDetails(PayloadConfig getQuoteHistoryDetails) {
		this.getQuoteHistoryDetails = getQuoteHistoryDetails;
	}
	
	/** The get Quote Details. */
	@SerializedName("getQuoteDetails")
	private PayloadConfig getQuoteDetails;

	/**
	 * @return the getQuoteDetails
	 */
	public PayloadConfig getGetQuoteDetails() {
		return getQuoteDetails;
	}

	/**
	 * @param getQuoteDetails the getQuoteDetails to set
	 */
	public void setGetQuoteDetails(PayloadConfig getQuoteDetails) {
		this.getQuoteDetails = getQuoteDetails;
	}
	
}
