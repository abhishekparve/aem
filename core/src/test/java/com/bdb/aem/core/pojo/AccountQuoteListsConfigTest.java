package com.bdb.aem.core.pojo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class AccountQuoteListsConfigTest.
 */
class AccountQuoteListsConfigTest {
	
	/** The quote lists config. */
	AccountQuoteListsConfig quoteListsConfig;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		quoteListsConfig = new AccountQuoteListsConfig();
		quoteListsConfig.setAddAllItemsToCart(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
		quoteListsConfig.setCreateQuoteList(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
		quoteListsConfig.setFileUploadQuoteList(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
		quoteListsConfig.setFileUploadQuoteListEntries(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
		quoteListsConfig.setGetProductAnnouncements(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
		quoteListsConfig.setGetQuoteList(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
		quoteListsConfig.setGetQuoteListDetails(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
		quoteListsConfig.setRemoveQuoteList(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
		quoteListsConfig.setRemoveQuoteListEntries(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
		quoteListsConfig.setShareQuoteList(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
		quoteListsConfig.setUpdateQuoteListEntries(new PayloadConfig(new Payload("TestUrl", "TestMethod")));
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(quoteListsConfig.getAddAllItemsToCart());
		assertNotNull(quoteListsConfig.getCreateQuoteList());
		assertNotNull(quoteListsConfig.getFileUploadQuoteList());
		assertNotNull(quoteListsConfig.getFileUploadQuoteListEntries());
		assertNotNull(quoteListsConfig.getGetProductAnnouncements());
		assertNotNull(quoteListsConfig.getGetQuoteList());
		assertNotNull(quoteListsConfig.getGetQuoteListDetails());
		assertNotNull(quoteListsConfig.getRemoveQuoteList());
		assertNotNull(quoteListsConfig.getRemoveQuoteListEntries());
		assertNotNull(quoteListsConfig.getShareQuoteList());
		assertNotNull(quoteListsConfig.getUpdateQuoteListEntries());
	}
}
