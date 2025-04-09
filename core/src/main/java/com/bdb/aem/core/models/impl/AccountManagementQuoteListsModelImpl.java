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

import com.bdb.aem.core.models.AccountManagementQuoteListsModel;
import com.bdb.aem.core.models.GlobalFileUploadModel;
import com.bdb.aem.core.pojo.AccountQuoteListsConfig;
import com.bdb.aem.core.pojo.AccountQuoteListsLabels;
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
import com.google.gson.JsonObject;

/**
 * The Class AccountManagementQuoteListsModelImpl.
 */
@Model( adaptables = { SlingHttpServletRequest.class, Resource.class }, 
		adapters = { AccountManagementQuoteListsModel.class }, 
		resourceType = { AccountManagementQuoteListsModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccountManagementQuoteListsModelImpl implements AccountManagementQuoteListsModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";
	
	/** The Constant GLOBAL_FILE_UPLOAD_RESOURCE_TYPE. */
	protected static final String GLOBAL_FILE_UPLOAD_RESOURCE_TYPE = "bdb-aem/proxy/components/content/globalfileupload";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(AccountManagementQuoteListsModelImpl.class);
	
	/** The file upload labels. */
	private JsonElement fileUploadLabels;
	
	/** The quote list labels. */
	private String quoteListLabels;
	
	/** The quote list config. */
	private String quoteListConfig;

	/** The quote confirmation config. */
	private String quoteConfirmationConfig;

	/** The quote details label. */
	private String quoteDetailsLabels;
	
	/** The quote lists heading. */
	@Inject
	@Via("resource")
	private String quoteListsHeading;
	
	/** The create quote list cta label. */
	@Inject
	@Via("resource")
	private String createQuoteListCtaLabel;
	
	/** The save time icon. */
	@Inject
	@Via("resource")
	private String saveTimeIcon;
	
	/** The save time text. */
	@Inject
	@Via("resource")
	private String saveTimeText;
	
	/** The save time info. */
	@Inject
	@Via("resource")
	private String saveTimeInfo;
	
	/** The share list icon. */
	@Inject
	@Via("resource")
	private String shareListIcon;
	
	/** The share list text. */
	@Inject
	@Via("resource")
	private String shareListText;
	
	/** The share list info. */
	@Inject
	@Via("resource")
	private String shareListInfo;
	
	/** The search place holder. */
	@Inject
	@Via("resource")
	private String searchPlaceHolder;
	
	/** The created by label. */
	@Inject
	@Via("resource")
	private String createdByLabel;

	/** The date created label. */
	@Inject
	@Via("resource")
	private String dateCreatedLabel;

	/** The date modified label. */
	@Inject
	@Via("resource")
	private String dateModifiedLabel;

	/** The no of products label. */
	@Inject
	@Via("resource")
	private String noOfProductsLabel;
	
	/** The quote list created successfully text. */
	@Inject
	@Via("resource")
	private String quoteListCreatedSuccessfullyText;
	
	/** The create new invalid product error. */
	@Inject
	@Via("resource")
	private String createNewInvalidProductError;
	
	/** The share quote list success message. */
	@Inject
	@Via("resource")
	private String shareQuoteListSuccessMessage;
	
	//
	
	/** The create quote list heading. */
	@Inject
	@Via("resource")
	private String createQuoteListHeading;
	
	/** The list name label. */
	@Inject
	@Via("resource")
	private String listNameLabel;
	
	/** The quotes list list name error. */
	@Inject
	@Via("resource")
	private String quotesListListNameError;
	
	
	/** The add items heading. */
	@Inject
	@Via("resource")
	private String addItemsHeading;
	
	/** The quick add title. */
	@Inject
	@Via("resource")
	private String quickAddTitle; 
	
	/** The bulk upload list message. */
	@Inject
	@Via("resource")
	private String bulkUploadListMessage;
	
	/** The save list cta label. */
	@Inject
	@Via("resource")
	private String saveListCtaLabel;

	/** The text entry label. */
	@Inject
	@Via("resource")
	private String textEntryLabel;
	
	/** The text entry info. */
	@Inject
	@Via("resource")
	private String textEntryInfo;
	
	/** The catalog num. */
	@Inject
	@Via("resource")
	private String catalogNum;
	
	/** The quantity placeholder. */
	@Inject
	@Via("resource")
	private String quantityPlaceholder;
	
	/** The default display of items. */
	@Inject
	@Via("resource")
	private int defaultDisplayOfItems;
	
	/** The no of items per set. */
	@Inject
	@Via("resource")
	private int noOfItemsPerSet;
	
	/** The blank field error. */
	@Inject
	@Via("resource")
	private String blankFieldError;
	
	
	/** The bulk paste label. */
	@Inject
	@Via("resource")
	private String bulkPasteLabel;
	
	/** The bulk paste info. */
	@Inject
	@Via("resource")
	private String bulkPasteInfo;
	
	/** The bulk paste placeholder. */
	@Inject
	@Via("resource")
	private String bulkPastePlaceholder;
	
	/** The excel upload label. */
	@Inject
	@Via("resource")
	private String excelUploadLabel;
	
	/** The upload catalog number text. */
	@Inject
	@Via("resource")
	private String uploadCatalogNumberText;
	
	/** The download info. */
	@Inject
	@Via("resource")
	private String downloadInfo;
	
	/** The download template link label. */
	@Inject
	@Via("resource")
	private String downloadTemplateLinkLabel;
	
	/** The download url. */
	@Inject
	@Via("resource")
	private String downloadUrl;
	
	/** The file upload note. */
	@Inject
	@Via("resource")
	private String fileUploadNote;
	
	/** The global file upload path. */
	@Inject
	@Via("resource")
	private String globalFileUploadPath;
	
	/** The remove quote list header. */
	@Inject
	@Via("resource")
	private String removeQuoteListHeader;
	
	/** The remove confirmation text. */
	@Inject
	@Via("resource")
	private String removeConfirmationText;
	
	/** The share quote list header. */
	@Inject
	@Via("resource")
	private String shareQuoteListHeader;

	/** The Recipients email Text. */
	@Inject
	@Via("resource")
	private String recipientsEmailText;

	/** The emailError. */
	@Inject
	@Via("resource")
	private String emailError;
	
	/** The help text. */
	@Inject
	@Via("resource")
	private String helpText;

	/** The Note placeholder Text. */
	@Inject
	@Via("resource")
	private String notePlaceholderText;

	/** The Max Limit Error. */
	@Inject
	@Via("resource")
	private String maxLimitError;

	/** The Quote Distributor Heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String distHeading;

	/** The Quote Account Manager Heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String actMgrHeading;

	/** The Quote Confirm Message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String confirmInfoMsg;

	/** The Quote Confirm Message Alt Text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String confirmInfoAlt;

	/** The productsHeading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String productsHeading;

	/** The listPriceLabel. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String listPriceLabel;

	/** The defaultPRDImage. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String defaultPRDImage;

	/** The defaultPRDImageAltText. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String defaultPRDImageAltText;


	/** The addressDetailsHeading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String addressDetailsHeading;

	/** The pending. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String pending;

	/** The Quote RequestedBy. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String requestedBy;

	/** The Quote Requested Date. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String requestedDate;

	/** The Quote Request Currency. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotationCurrency;

	/** The quote Details heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quoteDetailsHeading;
	
	/** The quotes special instructions heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesSpecialInstructionsHeading;
	
	/** The quotes shopping list details header. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesShoppingListDetailsHeader;
	
	/** The quotes list name placeholder text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesListNamePlaceholderText;
	
	/** The quotes shopping list items text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesShoppingListItemsText;
	
	/** The quotes add to cart CTA text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesAddToCartCTAText;
	
	/** The quotes add to cart alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesAddToCartAltText;
	
	/** The quotes info icon alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesInfoIconAltText;
	
	/** The quotes quick add success msg. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesQuickAddSuccessMsg;
	
	/** The quotes add to cart success message. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesAddToCartSuccessMessage;
	
	/** The quotes add all items to cart link text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesAddAllItemsToCartLinkText;
	
	/** The quotes quick add link text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesQuickAddLinkText;
	
	/** The quotes quick add title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesQuickAddTitle;
	
	/** The quotes add items to cart link text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesAddItemsToCartLinkText;
	
	/** The quotes no items added text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesNoItemsAddedText;
	
	/** The quotes no data icon. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesNoDataIcon;
	
	/** The quotes no data icon alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String quotesNoDataIconAltText;
	
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

	/** The hybris site id. */
	private String hybrisSiteId;

	/**
	 * Creates and Initializes the model with the Labels and Configs.
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Quote Lists Init Method");
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		ExcludeUtil excludeUtilObject = new ExcludeUtil();

		setFileUploadLabels();
		setQuoteListsLabels(excludeUtilObject);
		setQuoteListsConfig(excludeUtilObject);
		setQuoteConfirmationConfig(excludeUtilObject);
		quoteDetailsLabels = setQuoteDetailsLabels();
	}
	
	/**
	 * Sets the quote lists labels.
	 *
	 * @param excludeUtilObject the new quote lists labels
	 */
	private void setQuoteListsLabels(ExcludeUtil excludeUtilObject) {
		quoteListLabels = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		
		String defaultLabel = CommonHelper.getLabel("defaultLabel",currentPage);
		String viewDetailsCTALabel = CommonHelper.getLabel("viewDetails",currentPage);
		String setAsDefaultLabel = CommonHelper.getLabel("setAsDefault",currentPage);
		String shareLabel = CommonHelper.getLabel("share",currentPage);
		String removeLabel = CommonHelper.getLabel("remove",currentPage);
		String addMoreItemsLabel = CommonHelper.getLabel("addMoreItems",currentPage);
		
		AccountQuoteListsLabels textEntryLables = new AccountQuoteListsLabels();
		textEntryLables.setTextEntryLabel(textEntryLabel);
		textEntryLables.setTextEntryInfo(textEntryInfo);
		textEntryLables.setCatalogNum(catalogNum);
		textEntryLables.setQuantityPlaceholder(quantityPlaceholder);
		textEntryLables.setAddMoreItemsLabel(addMoreItemsLabel);
		textEntryLables.setBlankFieldError(blankFieldError);
		JsonElement textEntryLablesJsonTemp = json.toJsonTree(textEntryLables);
		JsonObject textEntryLablesJson = textEntryLablesJsonTemp.getAsJsonObject();
		textEntryLablesJson.addProperty("defaultDisplayOfItems", defaultDisplayOfItems);
		textEntryLablesJson.addProperty("noOfItemsPerSet", noOfItemsPerSet);
		
		AccountQuoteListsLabels bulkPasteLabels = new AccountQuoteListsLabels();
		bulkPasteLabels.setTextEntryLabel(bulkPasteLabel);
		bulkPasteLabels.setBulkPasteInfo(bulkPasteInfo);
		bulkPasteLabels.setBulkPastePlaceholder(bulkPastePlaceholder);
		JsonElement bulkPasteLabelsJson = json.toJsonTree(bulkPasteLabels);
		
		AccountQuoteListsLabels excelUploadLabels = new AccountQuoteListsLabels();
		excelUploadLabels.setTextEntryLabel(excelUploadLabel);
		excelUploadLabels.setUploadCatalogNumberText(uploadCatalogNumberText);
		excelUploadLabels.setDownloadInfo(downloadInfo);
		excelUploadLabels.setDownloadTemplateLinkLabel(downloadTemplateLinkLabel);
		excelUploadLabels.setDownloadUrl(downloadUrl);
		excelUploadLabels.setFileUploadLabels(fileUploadLabels);
		JsonElement excelUploadLabelsJson = json.toJsonTree(excelUploadLabels);
		
		AccountQuoteListsLabels addItemsTabsLabels = new AccountQuoteListsLabels();
		addItemsTabsLabels.setAddItemsHeading(addItemsHeading);
		addItemsTabsLabels.setQuickAddTitle(quickAddTitle);
		addItemsTabsLabels.setBulkUploadListMessage(bulkUploadListMessage);
		String cancelLabel = CommonHelper.getLabel("cancel",currentPage);
		addItemsTabsLabels.setCancelLabel(cancelLabel);
		addItemsTabsLabels.setSaveListCtaLabel(saveListCtaLabel);
		addItemsTabsLabels.setTextEntryLables(textEntryLablesJson);
		addItemsTabsLabels.setBulkPasteLabels(bulkPasteLabelsJson);
		addItemsTabsLabels.setExcelUploadLabels(excelUploadLabelsJson);
		
		JsonElement addItemsTabs = json.toJsonTree(addItemsTabsLabels);
		
		AccountQuoteListsLabels createQuoteListLabels = new AccountQuoteListsLabels();
		createQuoteListLabels.setQuoteListsHeading(createQuoteListHeading);
		createQuoteListLabels.setListNameLabel(listNameLabel);
		createQuoteListLabels.setListNameError(quotesListListNameError);
		createQuoteListLabels.setSetAsDefaultLabel(setAsDefaultLabel);
		createQuoteListLabels.setAddItemsTabs(addItemsTabs);
		JsonElement createQuoteList = json.toJsonTree(createQuoteListLabels);
		
		AccountQuoteListsLabels  removeQuoteListLabels = new AccountQuoteListsLabels();
		String removeCTA = CommonHelper.getLabel("remove",currentPage);
		removeQuoteListLabels.setRemoveQuoteListHeader(removeQuoteListHeader);
		removeQuoteListLabels.setRemoveConfirmationText(removeConfirmationText);
		removeQuoteListLabels.setCancelLabel(cancelLabel);
		removeQuoteListLabels.setRemoveCTA(removeCTA);
		JsonElement removeQuoteList = json.toJsonTree(removeQuoteListLabels);
		
		AccountQuoteListsLabels shareQuoteListLabels = new AccountQuoteListsLabels();
		String sendCTA = CommonHelper.getLabel("send",currentPage);
		shareQuoteListLabels.setRemoveQuoteListHeader(shareQuoteListHeader);
		shareQuoteListLabels.setRecipientsEmailText(recipientsEmailText);
		shareQuoteListLabels.setEmailError(emailError);		
		shareQuoteListLabels.setHelpText(helpText);
		shareQuoteListLabels.setNotePlaceholderText(notePlaceholderText);
		shareQuoteListLabels.setCancelLabel(cancelLabel);
		shareQuoteListLabels.setSendCTA(sendCTA);
		shareQuoteListLabels.setMaxLimitError(maxLimitError);
		JsonElement shareQuoteList = json.toJsonTree(shareQuoteListLabels);
		
		JsonObject productLabels = new JsonObject();
		productLabels.addProperty("clone",CommonHelper.getLabel("clone",currentPage));
		productLabels.addProperty("size",CommonHelper.getLabel("size",currentPage));
		productLabels.addProperty("status",CommonHelper.getLabel("status",currentPage));
		productLabels.addProperty("catNumber",CommonHelper.getLabel("catNo",currentPage));
		productLabels.addProperty("quantity",CommonHelper.getLabel("quantityPending",currentPage));
		productLabels.addProperty("addToCartCTA",quotesAddToCartCTAText);
		productLabels.addProperty("addToCartAltText",quotesAddToCartAltText);
		productLabels.addProperty("remove",CommonHelper.getLabel("remove",currentPage));
		productLabels.addProperty("moreinfo",CommonHelper.getLabel("moreInfo",currentPage));
		productLabels.addProperty("infoIconAltText",quotesInfoIconAltText);

		JsonObject shoppingListDetails = new JsonObject();
		shoppingListDetails.add("productLabels",productLabels);
		shoppingListDetails.addProperty("prdSuccessMsg",quotesAddToCartSuccessMessage);
		shoppingListDetails.addProperty("addAllItems",quotesAddAllItemsToCartLinkText);
		shoppingListDetails.addProperty("quickAddCTA",quotesQuickAddLinkText);
		shoppingListDetails.addProperty("quickAddTitle",quotesQuickAddTitle);
		shoppingListDetails.addProperty("addItemsCTA",quotesAddItemsToCartLinkText);
		shoppingListDetails.addProperty("noData",quotesNoItemsAddedText);
		shoppingListDetails.addProperty("noDataIcon",quotesNoDataIcon);
		shoppingListDetails.addProperty("noDataIconAlttext",quotesNoDataIconAltText);
		shoppingListDetails.addProperty("listCount",quotesShoppingListItemsText);
		shoppingListDetails.addProperty("setAsDefault",CommonHelper.getLabel("setAsDefault",currentPage));
		shoppingListDetails.addProperty("shoppingListName",quotesListNamePlaceholderText);
		shoppingListDetails.addProperty("heading",quotesShoppingListDetailsHeader);
		shoppingListDetails.addProperty("quickAddSucccessMsg",quotesQuickAddSuccessMsg);
		
		AccountQuoteListsLabels accountQuoteListsLabels = new AccountQuoteListsLabels();
		accountQuoteListsLabels.setQuoteListsHeading(quoteListsHeading);
		accountQuoteListsLabels.setCreateQuoteListCtaLabel(createQuoteListCtaLabel);
		accountQuoteListsLabels.setSaveTimeIcon(saveTimeIcon);
		accountQuoteListsLabels.setSaveTimeText(saveTimeText);
		accountQuoteListsLabels.setSaveTimeInfo(saveTimeInfo);
		accountQuoteListsLabels.setShareListIcon(shareListIcon);
		accountQuoteListsLabels.setShareListText(shareListText);
		accountQuoteListsLabels.setShareListInfo(shareListInfo);
		accountQuoteListsLabels.setSearchPlaceHolder(searchPlaceHolder);
		accountQuoteListsLabels.setDefaultLabel(defaultLabel);
		accountQuoteListsLabels.setCreatedByLabel(createdByLabel);
		accountQuoteListsLabels.setDateCreatedLabel(dateCreatedLabel);
		accountQuoteListsLabels.setDateModifiedLabel(dateModifiedLabel);
		accountQuoteListsLabels.setNoOfProductsLabel(noOfProductsLabel);
		accountQuoteListsLabels.setViewDetailsCTALabel(viewDetailsCTALabel);
		accountQuoteListsLabels.setSetAsDefaultLabel(setAsDefaultLabel);
		accountQuoteListsLabels.setShareLabel(shareLabel);
		accountQuoteListsLabels.setRemoveLabel(removeLabel);
		accountQuoteListsLabels.setQuoteListCreatedSuccessfullyText(quoteListCreatedSuccessfullyText);
		accountQuoteListsLabels.setCreateNewInvalidProductError(createNewInvalidProductError);
		accountQuoteListsLabels.setShareQuoteListSuccessMessage(shareQuoteListSuccessMessage);
		accountQuoteListsLabels.setCreateQuoteList(createQuoteList);
		accountQuoteListsLabels.setRemoveQuoteList(removeQuoteList);
		accountQuoteListsLabels.setShareQuoteList(shareQuoteList);
		accountQuoteListsLabels.setShoppingListDetails(shoppingListDetails);
		quoteListLabels = json.toJson(accountQuoteListsLabels);
	}

	/**
	 * Sets the file upload labels.
	 */
	private void setFileUploadLabels() {
		GlobalFileUploadModel globalFileUploadModel = CommonHelper.getGlobalFileUploadModel(globalFileUploadPath, resourceResolver, GLOBAL_FILE_UPLOAD_RESOURCE_TYPE);
		if(null != globalFileUploadModel)
		{
			if(StringUtils.isNotBlank(fileUploadNote)){
				globalFileUploadModel.setNote(fileUploadNote);
	        }
	        ExcludeUtil excludeUtilObject = new ExcludeUtil();
	        Gson fileUploadLabelsGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
	                .addSerializationExclusionStrategy(excludeUtilObject).create();
	        fileUploadLabels = fileUploadLabelsGson.toJsonTree(globalFileUploadModel);
		}
	}
	
	/**
	 * Sets the quote lists config.
	 *
	 * @param excludeUtilObject the new quote lists config
	 */
	private void setQuoteListsConfig(ExcludeUtil excludeUtilObject) {
		quoteListConfig = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		
		AccountQuoteListsConfig accountQuoteListsConfig = new AccountQuoteListsConfig();
		
		if (bdbApiEndpointService != null) {
			String createQuoteListEndpoint = StringUtils.replace(
					bdbApiEndpointService.getCreateShoppingListEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload createQuoteListPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + createQuoteListEndpoint,
					HttpConstants.METHOD_POST);
			PayloadConfig createQuoteList = new PayloadConfig(createQuoteListPayload);
			
			String getQuoteListDetailsEndpoint = StringUtils.replace(
					bdbApiEndpointService.getGetShoppingListDetailsEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload getQuoteListDetailsPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + getQuoteListDetailsEndpoint,
					HttpConstants.METHOD_GET);
			PayloadConfig getQuoteListDetails = new PayloadConfig(getQuoteListDetailsPayload);
			
			String getQuoteListEndpoint = StringUtils.replace(
					bdbApiEndpointService.getGetShoppingListEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload getQuoteListEndpointPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + getQuoteListEndpoint,
					HttpConstants.METHOD_GET);
			PayloadConfig getQuoteList = new PayloadConfig(getQuoteListEndpointPayload);
			
			
			String fileUploadQuoteListEndpoint = StringUtils.replace(
					bdbApiEndpointService.getFileUploadShoppingListEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload fileUploadQuoteListPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + fileUploadQuoteListEndpoint,
					HttpConstants.METHOD_POST);
			PayloadConfig fileUploadQuoteList = new PayloadConfig(fileUploadQuoteListPayload);
			
			String fileUploadQuoteListEntriesEndpoint = StringUtils.replace(
					bdbApiEndpointService.getFileUploadShoppingListEntriesEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload fileUploadQuoteListEntriesPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + fileUploadQuoteListEntriesEndpoint,
					HttpConstants.METHOD_POST);
			PayloadConfig fileUploadQuoteListEntries = new PayloadConfig(fileUploadQuoteListEntriesPayload);
			
			String removeQuoteListEndpoint = StringUtils.replace(
					bdbApiEndpointService.getRemoveShoppingListEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload removeShoppingListPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + removeQuoteListEndpoint,
					HttpConstants.METHOD_DELETE);
			PayloadConfig removeQuoteList = new PayloadConfig(removeShoppingListPayload);
			
			String shareQuoteListEndpoint = StringUtils.replace(
					bdbApiEndpointService.getShareShoppingListEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload shareQuoteListPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + shareQuoteListEndpoint,
					HttpConstants.METHOD_POST);
			PayloadConfig shareQuoteList = new PayloadConfig(shareQuoteListPayload);
			
			String removeQuoteListEntriesEndpoint = StringUtils.replace(
					bdbApiEndpointService.getRemoveShoppingListEntriesEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload removeQuoteListEntriesPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + removeQuoteListEntriesEndpoint,
					HttpConstants.METHOD_DELETE);
			PayloadConfig removeQuoteListEntries = new PayloadConfig(removeQuoteListEntriesPayload);
			
			String updateQuoteListEntriesEndpoint = StringUtils.replace(
					bdbApiEndpointService.getUpdateShoppingListEntriesEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload updateQuoetListEntriesPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + updateQuoteListEntriesEndpoint,
					HttpConstants.METHOD_POST);
			PayloadConfig updateQuoteListEntries = new PayloadConfig(updateQuoetListEntriesPayload);
	
			String getProductAnnouncementsEndpoint = bdbApiEndpointService.getProductAnnouncements();
			Payload getProductAnnouncementsPayload = new Payload(getProductAnnouncementsEndpoint,
					HttpConstants.METHOD_POST);
			PayloadConfig getProductAnnouncements = new PayloadConfig(getProductAnnouncementsPayload);
	
			String addAllItemsToCartEndpoint = StringUtils.replace(
					bdbApiEndpointService.getAddAllItemsToCartEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
			Payload addAllItemsToCartPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + addAllItemsToCartEndpoint,
					HttpConstants.METHOD_POST);
			PayloadConfig addAllItemsToCart = new PayloadConfig(addAllItemsToCartPayload);
			
			accountQuoteListsConfig.setCreateQuoteList(createQuoteList);
			accountQuoteListsConfig.setGetQuoteListDetails(getQuoteListDetails);
			accountQuoteListsConfig.setGetQuoteList(getQuoteList);
			accountQuoteListsConfig.setFileUploadQuoteList(fileUploadQuoteList);
			accountQuoteListsConfig.setFileUploadQuoteListEntries(fileUploadQuoteListEntries);
			accountQuoteListsConfig.setRemoveQuoteList(removeQuoteList);
			accountQuoteListsConfig.setShareQuoteList(shareQuoteList);
			accountQuoteListsConfig.setRemoveQuoteListEntries(removeQuoteListEntries);
			accountQuoteListsConfig.setUpdateQuoteListEntries(updateQuoteListEntries);
			accountQuoteListsConfig.setGetProductAnnouncements(getProductAnnouncements);
			accountQuoteListsConfig.setAddAllItemsToCart(addAllItemsToCart);
			quoteListConfig = json.toJson(accountQuoteListsConfig);
		}
	}


	/**
	 * Sets the quote confirmation config.
	 *
	 * @param excludeUtilObject the new quote confirmation config
	 */
	public void setQuoteConfirmationConfig(ExcludeUtil excludeUtilObject) {
		JsonObject config = new JsonObject();
		JsonObject getProductAnnouncements = new JsonObject();
		getProductAnnouncements.addProperty(CommonConstants.URL, bdbApiEndpointService.getProductAnnouncements());
		getProductAnnouncements.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		String getOrderConfirmationCheckoutConfig = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getQuoteConfirmationConfig(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload getProductAnnouncementsConfigload = new Payload(getOrderConfirmationCheckoutConfig, HttpConstants.METHOD_GET);
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		config.add(CommonConstants.GET_QUOTE_INFORMATION, gson.toJsonTree(getProductAnnouncementsConfigload));
		config.add(CommonConstants.GET_PRODUCT_ANNOUNCEMENTS, getProductAnnouncements);
		quoteConfirmationConfig = config.toString();
	}

	/**
	 * Gets the quote list labels.
	 *
	 * @return the quote list labels
	 */
	@Override
	public String getQuoteListLabels() {
		return quoteListLabels;
	}
	
	/**
	 * Gets the quote list config.
	 *
	 * @return the quote list config
	 */
	@Override
	public String getQuoteListConfig() {
		return quoteListConfig;
	}


	/**
	 * Gets the quote confirmation config.
	 *
	 * @return the quote confirmation config
	 */
	@Override
	public String getQuoteConfirmationConfig() {
		return quoteConfirmationConfig;
	}

	/**
	 * Gets the quote details labels.
	 *
	 * @return the quote details labels
	 */
	@Override
	public String getQuoteDetailsLabels() {
		return quoteDetailsLabels;
	}

	/**
	 * Sets the quote details labels.
	 *
	 * @return the string
	 */
	public String setQuoteDetailsLabels() {
		JsonObject quoteDetailsLabels = new JsonObject();

		JsonObject distributorInformation = new JsonObject();
		JsonObject accountManagerInformation = new JsonObject();
		JsonObject confirmationInformation = new JsonObject();
		JsonObject quoteDetails = new JsonObject();

		distributorInformation.addProperty(CommonConstants.HEADING, distHeading);
		distributorInformation.addProperty(CommonConstants.NAME, CommonHelper.getLabel(CommonConstants.NAME_UPPER_CASE, currentPage));
		distributorInformation.addProperty(CommonConstants.EMAIL, CommonHelper.getLabel(CommonConstants.EMAIL_UPPER_CASE_LABEL, currentPage));
		distributorInformation.addProperty(CommonConstants.PHONE_NUMBER, CommonHelper.getLabel(CommonConstants.PHONE_NUMBER_LABEL, currentPage));

		quoteDetailsLabels.add(CommonConstants.DISTRIBUTOR_INFORMATION, distributorInformation);

		accountManagerInformation.addProperty(CommonConstants.HEADING, actMgrHeading);
		accountManagerInformation.addProperty(CommonConstants.NAME, CommonHelper.getLabel(CommonConstants.NAME_UPPER_CASE, currentPage));
		accountManagerInformation.addProperty(CommonConstants.EMAIL, CommonHelper.getLabel(CommonConstants.EMAIL_UPPER_CASE_LABEL, currentPage));
		accountManagerInformation.addProperty(CommonConstants.PHONE_NUMBER, CommonHelper.getLabel(CommonConstants.PHONE_NUMBER_LABEL, currentPage));

		quoteDetailsLabels.add(CommonConstants.ACCOUNT_MANAGER_INFORMATION, accountManagerInformation);

		confirmationInformation.addProperty(CommonConstants.MESSAGE, confirmInfoMsg);
		confirmationInformation.addProperty(CommonConstants.ALT_TEXT, confirmInfoAlt);

		quoteDetailsLabels.add(CommonConstants.CONFIRMATION_INFORMATION, confirmationInformation);

		quoteDetails.addProperty(CommonConstants.IS_QUOTE_DETAILS,Boolean.TRUE);
		quoteDetails.addProperty(CommonConstants.HEADING, distHeading);
		quoteDetails.addProperty(CommonConstants.NAME, CommonHelper.getLabel(CommonConstants.DISTRIBUTOR_NAME_LABEL, currentPage));
		quoteDetails.addProperty(CommonConstants.EMAIL, CommonHelper.getLabel(CommonConstants.DISTRIBUTOR_EMAIL_LABEL, currentPage));
		quoteDetails.addProperty(CommonConstants.PHONE_NUMBER, CommonHelper.getLabel(CommonConstants.DISTRIBUTOR_PHONE_NUMBER_LABEL, currentPage));

		quoteDetailsLabels.add(CommonConstants.QUOTE_DETAILS, quoteDetails);
		
		quoteDetailsLabels.addProperty(CommonConstants.SPECIAL_INSTRUCTIONS, quotesSpecialInstructionsHeading);

		JsonObject productJson = new JsonObject();

		productJson.addProperty(CommonConstants.PRODUCTS_HEADING, productsHeading);
		productJson.addProperty(CommonConstants.QUANTITY, CommonHelper.getLabel(CommonConstants.QUANTITY_TITLE_QUICK_ADD, currentPage));
		productJson.addProperty(CommonConstants.DEFAULT_PRD_IMAGE, defaultPRDImage);
		productJson.addProperty(CommonConstants.DEFAULT_PRD_IMAGE_ALT_TEXT, defaultPRDImageAltText);
		productJson.addProperty(CommonConstants.PRICE_PER_UNIT, CommonHelper.getLabel("priceUnit", currentPage));
		productJson.addProperty(CommonConstants.LIST_PRICE_LABEL_ATC, listPriceLabel);
		productJson.addProperty(CommonConstants.TOTAL_PRICE, CommonHelper.getLabel(CommonConstants.TOTAL_PRICE_LABEL, currentPage));
		quoteDetailsLabels.add(CommonConstants.PRODUCTS,productJson);

		JsonObject addressJson = new JsonObject();

		addressJson.addProperty(CommonConstants.ADDRESS_DETAILS_HEADING, addressDetailsHeading);
		addressJson.addProperty(CommonConstants.SHIP_TO_LBL, CommonHelper.getLabel(CommonConstants.SHIP_TO_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.BILL_TO_LBL, CommonHelper.getLabel(CommonConstants.BILL_TO_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.SOLD_TO_LBL, CommonHelper.getLabel(CommonConstants.SOLD_TO_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.PAY_TO_LBL, CommonHelper.getLabel(CommonConstants.PAY_TO_LABEL, currentPage));		
		addressJson.addProperty(CommonConstants.SHIP_TO_NUMBER_LABEL_ALT, CommonHelper.getLabel(CommonConstants.SHIP_TO_NUMBER, currentPage));
		addressJson.addProperty(CommonConstants.BILL_TO_NUMBER_LABEL_ALT, CommonHelper.getLabel(CommonConstants.BILL_TO_NUMBER_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.SOLDTO_NUMBER_LABEL, CommonHelper.getLabel(CommonConstants.SOLD_TO_NUMBER_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.PAY_TO_NUMBER_LABEL_ALT, CommonHelper.getLabel(CommonConstants.PAY_TO_NUMBER_LABEL, currentPage));
		addressJson.addProperty(CommonConstants.PENDING, pending);

		quoteDetailsLabels.add(CommonConstants.ADDRESS_DETAILS,addressJson);

		quoteDetailsLabels.addProperty(CommonConstants.QUOTE_NUMBER, CommonHelper.getLabel(CommonConstants.QUOTE_NUMBER_LABEL, currentPage));
		quoteDetailsLabels.addProperty(CommonConstants.REQUESTED_BY, requestedBy);
		quoteDetailsLabels.addProperty(CommonConstants.REQUESTED_DATE, requestedDate);
		quoteDetailsLabels.addProperty(CommonConstants.QUOTATION_CURRENCY, quotationCurrency);
		quoteDetailsLabels.addProperty(CommonConstants.IS_QUOTE, Boolean.FALSE);
		quoteDetailsLabels.addProperty(CommonConstants.IS_QUOTE_DETAILS, Boolean.TRUE);
		quoteDetailsLabels.addProperty(CommonConstants.QUOTE_DETAILS_HEADING, quoteDetailsHeading);
		log.debug("quoteDetailsLabels : " + quoteDetailsLabels.toString());

		return quoteDetailsLabels.toString();
	}


}
