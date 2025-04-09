package com.bdb.aem.core.pojo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

/**
 * The Class AccountQuoteListsLabelsTest.
 */
class AccountQuoteListsLabelsTest {
	
	/** The quote lists test labels. */
	AccountQuoteListsLabels quoteListsTestLabels;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		quoteListsTestLabels = new AccountQuoteListsLabels();
		quoteListsTestLabels.setAddItemsHeading("addItemsHeading");
		quoteListsTestLabels.setAddItemsTabs(new JsonObject());
		quoteListsTestLabels.setAddMoreItemsLabel("addMoreItemsLabel");
		quoteListsTestLabels.setBlankFieldError("blankFieldError");
		quoteListsTestLabels.setBulkPasteInfo("bulkPasteInfo");
		quoteListsTestLabels.setBulkPasteLabel("bulkPasteLabel");
		quoteListsTestLabels.setBulkPasteLabels(new JsonObject());
		quoteListsTestLabels.setBulkPastePlaceholder("bulkPastePlaceholder");
		quoteListsTestLabels.setBulkUploadListMessage("bulkUploadListMessage");
		quoteListsTestLabels.setCancelLabel("cancelLabel");
		quoteListsTestLabels.setCatalogNum("catalogNum");
		quoteListsTestLabels.setCreatedByLabel("createdByLabel");
		quoteListsTestLabels.setCreateNewInvalidProductError("createNewInvalidProductError");
		quoteListsTestLabels.setCreateQuoteList(new JsonObject());
		quoteListsTestLabels.setCreateQuoteListCtaLabel("createQuoteListCtaLabel");
		quoteListsTestLabels.setDateCreatedLabel("dateCreatedLabel");
		quoteListsTestLabels.setDateModifiedLabel("dateModifiedLabel");
		quoteListsTestLabels.setDefaultLabel("defaultLabel");
		quoteListsTestLabels.setDownloadInfo("downloadInfo");
		quoteListsTestLabels.setDownloadTemplateLinkLabel("downloadTemplateLinkLabel");
		quoteListsTestLabels.setDownloadUrl("downloadUrl");
		quoteListsTestLabels.setEmailError("emailError");
		quoteListsTestLabels.setExcelUploadLabels(new JsonObject());
		quoteListsTestLabels.setFileUploadLabels(new JsonObject());
		quoteListsTestLabels.setHelpText("helpText");
		quoteListsTestLabels.setListNameError("listNameError");
		quoteListsTestLabels.setListNameLabel("listNameLabel");
		quoteListsTestLabels.setMaxLimitError("maxLimitError");
		quoteListsTestLabels.setNoOfProductsLabel("noOfProductsLabel");
		quoteListsTestLabels.setNotePlaceholderText("notePlaceholderText");
		quoteListsTestLabels.setQuantityPlaceholder("quantityPlaceholder");
		quoteListsTestLabels.setQuickAddTitle("quickAddTitle");
		quoteListsTestLabels.setQuoteListCreatedSuccessfullyText("quoteListCreatedSuccessfullyText");
		quoteListsTestLabels.setQuoteListsHeading("quoteListsHeading");
		quoteListsTestLabels.setRecipientsEmailText("recipientsEmailText");
		quoteListsTestLabels.setRemoveConfirmationText("removeConfirmationText");
		quoteListsTestLabels.setRemoveCTA("removeCTA");
		quoteListsTestLabels.setRemoveLabel("removeLabel");
		quoteListsTestLabels.setRemoveQuoteList(new JsonObject());
		quoteListsTestLabels.setRemoveQuoteListHeader("removeQuoteListHeader");
		quoteListsTestLabels.setSaveListCtaLabel("saveListCtaLabel");
		quoteListsTestLabels.setSaveTimeIcon("saveTimeIcon");
		quoteListsTestLabels.setSaveTimeInfo("saveTimeInfo");
		quoteListsTestLabels.setSaveTimeText("saveTimeText");
		quoteListsTestLabels.setSearchPlaceHolder("searchPlaceHolder");
		quoteListsTestLabels.setSendCTA("sendCTA");
		quoteListsTestLabels.setSetAsDefaultLabel("setAsDefaultLabel");
		quoteListsTestLabels.setShareLabel("shareLabel");
		quoteListsTestLabels.setShareListIcon("shareListIcon");
		quoteListsTestLabels.setShareListInfo("shareListInfo");
		quoteListsTestLabels.setShareListText("shareListText");
		quoteListsTestLabels.setShareQuoteList(new JsonObject());
		quoteListsTestLabels.setShareQuoteListSuccessMessage("shareQuoteListSuccessMessage");
		quoteListsTestLabels.setTextEntryInfo("textEntryInfo");
		quoteListsTestLabels.setTextEntryLabel("textEntryLabel");
		quoteListsTestLabels.setTextEntryLables(new JsonObject());
		quoteListsTestLabels.setUploadCatalogNumberText("uploadCatalogNumberText");
		quoteListsTestLabels.setViewDetailsCTALabel("viewDetailsCTALabel");
		quoteListsTestLabels.setShoppingListDetails(new JsonObject());
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertEquals("addItemsHeading", quoteListsTestLabels.getAddItemsHeading());
		assertNotNull(quoteListsTestLabels.getAddItemsTabs());
		assertEquals("addMoreItemsLabel", quoteListsTestLabels.getAddMoreItemsLabel());
		assertNotNull(quoteListsTestLabels.getBlankFieldError());
		assertNotNull(quoteListsTestLabels.getBulkPasteInfo());
		assertNotNull(quoteListsTestLabels.getBulkPasteLabel());
		assertNotNull(quoteListsTestLabels.getBulkPasteLabels());
		assertNotNull(quoteListsTestLabels.getBulkPastePlaceholder());
		assertNotNull(quoteListsTestLabels.getBulkUploadListMessage());
		assertNotNull(quoteListsTestLabels.getCancelLabel());
		assertNotNull(quoteListsTestLabels.getCatalogNum());
		assertEquals("createdByLabel", quoteListsTestLabels.getCreatedByLabel());
		assertNotNull(quoteListsTestLabels.getCreateNewInvalidProductError());
		assertNotNull(quoteListsTestLabels.getCreateQuoteList());
		assertNotNull(quoteListsTestLabels.getCreateQuoteListCtaLabel());
		assertNotNull(quoteListsTestLabels.getDateCreatedLabel());
		assertNotNull(quoteListsTestLabels.getDateModifiedLabel());
		assertNotNull(quoteListsTestLabels.getDefaultLabel());
		assertNotNull(quoteListsTestLabels.getDownloadInfo());
		assertNotNull(quoteListsTestLabels.getDownloadTemplateLinkLabel());
		assertNotNull(quoteListsTestLabels.getDownloadUrl());
		assertNotNull(quoteListsTestLabels.getEmailError());
		assertNotNull(quoteListsTestLabels.getExcelUploadLabels());
		assertNotNull(quoteListsTestLabels.getFileUploadLabels());
		assertNotNull(quoteListsTestLabels.getHelpText());
		assertNotNull(quoteListsTestLabels.getListNameError());
		assertNotNull(quoteListsTestLabels.getListNameLabel());
		assertNotNull(quoteListsTestLabels.getMaxLimitError());
		assertNotNull(quoteListsTestLabels.getNoOfProductsLabel());
		assertNotNull(quoteListsTestLabels.getNotePlaceholderText());
		assertNotNull(quoteListsTestLabels.getQuantityPlaceholder());
		assertNotNull(quoteListsTestLabels.getQuickAddTitle());
		assertNotNull(quoteListsTestLabels.getQuoteListCreatedSuccessfullyText());
		assertNotNull(quoteListsTestLabels.getQuoteListsHeading());
		assertNotNull(quoteListsTestLabels.getRecipientsEmailText());
		assertNotNull(quoteListsTestLabels.getRemoveConfirmationText());
		assertNotNull(quoteListsTestLabels.getRemoveCTA());
		assertNotNull(quoteListsTestLabels.getRemoveLabel());
		assertNotNull(quoteListsTestLabels.getRemoveQuoteList());
		assertNotNull(quoteListsTestLabels.getRemoveQuoteListHeader());
		assertNotNull(quoteListsTestLabels.getSaveListCtaLabel());
		assertNotNull(quoteListsTestLabels.getSaveTimeIcon());
		assertNotNull(quoteListsTestLabels.getSaveTimeInfo());
		assertNotNull(quoteListsTestLabels.getSaveTimeText());
		assertNotNull(quoteListsTestLabels.getSearchPlaceHolder());
		assertNotNull(quoteListsTestLabels.getSendCTA());
		assertNotNull(quoteListsTestLabels.getSetAsDefaultLabel());
		assertNotNull(quoteListsTestLabels.getShareLabel());
		assertNotNull(quoteListsTestLabels.getShareListIcon());
		assertNotNull(quoteListsTestLabels.getShareListInfo());
		assertNotNull(quoteListsTestLabels.getShareListText());
		assertNotNull(quoteListsTestLabels.getShareQuoteList());
		assertNotNull(quoteListsTestLabels.getShareQuoteListSuccessMessage());
		assertNotNull(quoteListsTestLabels.getTextEntryInfo());
		assertNotNull(quoteListsTestLabels.getTextEntryLabel());
		assertNotNull(quoteListsTestLabels.getTextEntryLables());
		assertNotNull(quoteListsTestLabels.getUploadCatalogNumberText());
		assertNotNull(quoteListsTestLabels.getViewDetailsCTALabel());
		assertNotNull(quoteListsTestLabels.getShoppingListDetails());
	}
}
