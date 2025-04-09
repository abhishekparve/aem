package com.bdb.aem.core.models;

public interface AccMgmtQuotesHistoryModel {

    public String getQuoteHistoryLabels();
    
    /**
	 * This method returns the configs related to quote History
	 * in account management page
	 * @return QuotesHistoryConfig
	 */
	public String getUserQuoteHistoryConfig();


}
