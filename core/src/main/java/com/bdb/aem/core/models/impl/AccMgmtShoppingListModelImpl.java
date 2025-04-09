package com.bdb.aem.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.bdb.aem.core.models.GlobalFileUploadModel;
import com.bdb.aem.core.pojo.AccountSettingConfig;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.pojo.ShoppingListConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.GlobalFileUploadUtil;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;
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

import com.bdb.aem.core.models.AccMgmtShoppingListModel;
import com.bdb.aem.core.util.ExcludeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * Implementation of interface AccMgmtShoppingListModel serving as Sling model
 * class for ShoppingList dialog in accountmanagement component
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = {
		AccMgmtShoppingListModel.class }, resourceType = {
				AccMgmtShoppingListModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AccMgmtShoppingListModelImpl implements AccMgmtShoppingListModel {

	/** The Constant log. */
	protected static final Logger loggerAccMgmtShoppingList = LoggerFactory.getLogger(AccMgmtShoppingListModelImpl.class);

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";

	/**
	 * The Constant GLOBAL_FILE_UPLOAD_RESOURCE_TYPE.
	 */
	protected static final String GLOBAL_FILE_UPLOAD_RESOURCE_TYPE = "bdb-aem/proxy/components/content/globalfileupload";


	/** Shopping list variable holding all the fields */
	private String shoppingListLabels;

	/** Shopping list variable holding all the fields */
	private String shoppingListConfig;

	/** The fileUploadLabels. */
	private String fileUploadLabels;

	/** The search place holder. */
	@Inject
	@Via("resource")
	private String globalFileUploadPath;

	/** The Shopping Lists Header Text. */
	@Inject
	@Via("resource")
	private String shoppingListsHeaderText;

	/** The Create New Shopping List CTA. */
	@Inject
	@Via("resource")
	private String createNewShoppingListCTA;

	/** The Save Time Text. */
	@Inject
	@Via("resource")
	private String saveTimeText;

	/** The SaveTimeInfo. */
	@Inject
	@Via("resource")
	private String saveTimeInfo;

	/** The Save Time Icon. */
	@Inject
	@Via("resource")
	private String saveTimeIcon;

	/** The Share List Text. */
	@Inject
	@Via("resource")
	private String shareListText;

	/** The shareListInfo. */
	@Inject
	@Via("resource")
	private String shareListInfo;

	/** The searchPlaceHolder. */
	@Inject
	@Via("resource")
	private String searchPlaceHolder;

	/** The Share List Icon. */
	@Inject
	@Via("resource")
	private String shareListIcon;

	/** The Are you sure you want to remove Text. */
	@Inject
	@Via("resource")
	private String areYouSureText;

	/** The Share Shopping List Header Header. */
	@Inject
	@Via("resource")
	private String shareShoppingListHeader;

	/** The Recipients email Text. */
	@Inject
	@Via("resource")
	private String recipientsEmailText;

	/** The emailError. */
	@Inject
	@Via("resource")
	private String emailError;

	/** The Note placeholder Text. */
	@Inject
	@Via("resource")
	private String notePlaceholderText;

	/** The Max Limit Error. */
	@Inject
	@Via("resource")
	private String maxLimitError;

	/** The export. */
	@Inject
	@Via("resource")
	private String export;
	
	/** The Created By Label. */
	@Inject
	@Via("resource")
	private String createdByLabel;

	/** The Date Created Label. */
	@Inject
	@Via("resource")
	private String dateCreatedLabel;

	/** The Date Modified Label. */
	@Inject
	@Via("resource")
	private String dateModifiedLabel;

	/** The No of products Label. */
	@Inject
	@Via("resource")
	private String noOfProductsLabel;

	/** The Create New Shopping List Header. */
	@Inject
	@Via("resource")
	private String createNewShoppingListHeader;

	/** The Shopping List Name placeholder Text. */
	@Inject
	@Via("resource")
	private String shoppingListNamePlaceholder;

	/** The listNameError. */
	@Inject
	@Via("resource")
	private String listNameError;

	/** The Add items to shopping list Text. */
	@Inject
	@Via("resource")
	private String addItemsToShoppingListText;

	/** The Text Entry Label. */
	@Inject
	@Via("resource")
	private String textEntryLabel;

	/** The text Entry Info. */
	@Inject
	@Via("resource")
	private String textEntryInfo;

	/** The quantityPlaceholder. */
	@Inject
	@Via("resource")
	private String quantityPlaceholder;

	/** The defaultDisplayOfItems. */
	@Inject
	@Via("resource")
	private int defaultDisplayOfItems;

	/** The noOfItemsPerSet. */
	@Inject
	@Via("resource")
	private int noOfItemsPerSet;

	/** The bulkPastePlaceholder. */
	@Inject
	@Via("resource")
	private String bulkPastePlaceholder;

	/** The catalogNum. */
	@Inject
	@Via("resource")
	private String catalogNum;

	/** The Bulk Paste Label. */
	@Inject
	@Via("resource")
	private String bulkPasteLabel;

	/** The Bulk Paste Info. */
	@Inject
	@Via("resource")
	private String bulkPasteInfo;

	/** The Excel Upload Label. */
	@Inject
	@Via("resource")
	private String excelUploadLabel;

	/** The Enter the catalog number and quantity Text. */
	@Inject
	@Via("resource")
	private String catalogNumberAndQuantityText;

	/** The You can bulk upload items in your shopping list Text. */
	@Inject
	@Via("resource")
	private String youCanBulkUploadText;

	/** The Catalog Number placeholder Text. */
	@Inject
	@Via("resource")
	private String catalogNumberPlaceholderText;

	/** The Quantity Text. */
	@Inject
	@Via("resource")
	private String quantityText;

	/** The Save List CTA Label. */
	@Inject
	@Via("resource")
	private String saveListCtaLabel;

	/** The Enter the catalog numbers in the box below Text. */
	@Inject
	@Via("resource")
	private String enterTheCatalogNumbersText;

	/** The Upload catalog number using template below Text. */
	@Inject
	@Via("resource")
	private String uploadCatalogNumberText;

	/** The Download Template link Label. */
	@Inject
	@Via("resource")
	private String downloadTemplateLinkLabel;

	/** The Download url  */
	@Inject
	@Via("resource")
	private String downloadUrl;

	/** The downloadInfo. */
	@Inject
	@Via("resource")
	private String downloadInfo;

	/** The note. */
	@Inject
	@Via("resource")
	private String fileUploadNote;

	/** The Your shopping list has been created successfully Text. */
	@Inject
	@Via("resource")
	private String shoppingListCreatedSuccessfullyText;

	/** The shoppingListDetailsHeader. */
	@Inject
	@Via("resource")
	private String shoppingListDetailsHeader;

	/** The No items added Text. */
	@Inject
	@Via("resource")
	private String noItemsAddedText;

	/** The noDataIconAlttext */
	@Inject
	@Via("resource")
	private String noDataIconAltText;

	/** The noDataIcon. */
	@Inject
	@Via("resource")
	private String noDataIcon;

	/** The Add items to the list CTA Label. */
	@Inject
	@Via("resource")
	private String addItemsToTheListCtaLabel;

	/** The List Name placeholder Text. */
	@Inject
	@Via("resource")
	private String listNamePlaceholderText;

	/** The Shopping list items Text. */
	@Inject
	@Via("resource")
	private String shoppingListItemsText;

	/** The Quick Add link Text. */
	@Inject
	@Via("resource")
	private String quickAddLinkText;

	/** The Quick Add Title. */
	@Inject
	@Via("resource")
	private String quickAddTitle;
	
	/** The ExportLabel. */
	@Inject
	@Via("resource")
	private String exportLabel;

	/** The Quick Add Succcess Msg. */
	@Inject
	@Via("resource")
	private String quickAddSuccessMsg;

	/** The Add items to cart link Text. */
	@Inject
	@Via("resource")
	private String addItemsToCartLinkText;

	/** The Add all items to cart link Text. */
	@Inject
	@Via("resource")
	private String addAllItemsToCartLinkText;

	/** The Add to Cart CTA Text. */
	@Inject
	@Via("resource")
	private String addToCartCtaText;

	/** The addToCartAltText. */
	@Inject
	@Via("resource")
	private String addToCartAltText;

	/** The infoIconAltText. */
	@Inject
	@Via("resource")
	private String infoIconAltText;

	/** The addToCartSuccessMessage. */
	@Inject
	@Via("resource")
	private String addToCartSuccessMessage;

	/** The The product has been added to cart Text. */
	@Inject
	@Via("resource")
	private String productAddedToCartText;

	/** The To Add Items Text. */
	@Inject
	@Via("resource")
	private String toAddItemsText;

	/** The removeShoppingListHeader. */
	@Inject
	@Via("resource")
	private String removeShoppingListHeader;

	/** The helpText. */
	@Inject
	@Via("resource")
	private String helpText;

	/** The shareShoppingListSuccessMsg. */
	@Inject
	@Via("resource")
	private String shareShoppingListSuccessMsg;

	/** The blankFieldError. */
	@Inject
	@Via("resource")
	private String blankFieldError;

	/** The createNewInvalidProductError. */
	@Inject
	@Via("resource")
	private String createNewInvalidProductError;

	/** The listNameErrorField.*/
	@Inject
	@Via("resource")
	private String listNameErrorField;
	
	/** The current page. */
	@Inject
	private Page currentPage;

	@Self
	private SlingHttpServletRequest request;

	/**
	 * The bdb api endpoint service.
	 */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;

	/**
	 * The resource resolver.
	 */
	@SlingObject
	private ResourceResolver resourceResolver;

	/**
	 * The Hybris Site ID
	 */
	private String hybrisSiteId;

	/**
	 * Populates the dashboardPageNavigationLabels.
	 */
	@PostConstruct
	protected void init() {
		loggerAccMgmtShoppingList.debug("Inside Account Management Shopping List Init Method");
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		Gson accountSettingConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();

		//Set the Hybris Site Id
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);

		shoppingListLabels = setShoppingListLabels();
		setFileUploadLabels();
		shoppingListConfig = setShoppingListConfig(accountSettingConfigGson);
	}

	/**
	 * This method returns the labels related to shopping list menu in account
	 * management page
	 * 
	 * @return shoppingListLabels
	 */
	@Override
	public String getShoppingListLabels() {
		return shoppingListLabels;
	}

	public String getShoppingListConfig() {
		return shoppingListConfig;
	}

	/**
	 * Gets the getFileUploadLabels.
	 *
	 * @return the getFileUploadLabels
	 */
	@Override
	public String getFileUploadLabels(){
		return fileUploadLabels;
	}

	private String setShoppingListLabels(){

		JsonObject createShoppingList = new JsonObject();
		JsonObject shoppingListDetails = new JsonObject();
		JsonObject removeShoppingList = new JsonObject();
		JsonObject shareShoppingList = new JsonObject();

		createShoppingList.addProperty("heading",createNewShoppingListHeader);
		createShoppingList.addProperty("listName",shoppingListNamePlaceholder);
		createShoppingList.addProperty("listNameError",listNameError);
		createShoppingList.addProperty("setAsDefault", CommonHelper.getLabel("setAsDefault",currentPage));

		JsonObject addItemsTabs = new JsonObject();
		JsonObject textEntryLabels = new JsonObject();
		JsonObject bulkPasteLabels = new JsonObject();
		JsonObject excelUploadLabels = new JsonObject();

		addItemsTabs.addProperty("addItemsHeading",addItemsToShoppingListText);
		addItemsTabs.addProperty("quickAddTitle",quickAddLinkText);
		addItemsTabs.addProperty("addItemsInfo",youCanBulkUploadText);
		addItemsTabs.addProperty("cancel",CommonHelper.getLabel("cancel",currentPage));
		addItemsTabs.addProperty("saveListCTA",saveListCtaLabel);

		textEntryLabels.addProperty("tabTitle",textEntryLabel);
		textEntryLabels.addProperty("textEntryInfo",textEntryInfo);
		textEntryLabels.addProperty("catalogNum",catalogNum);
		textEntryLabels.addProperty("quantity",quantityPlaceholder);
		textEntryLabels.addProperty("addMoreItems",CommonHelper.getLabel("addMoreItems",currentPage));
		textEntryLabels.addProperty("defaultDisplayOfItems",defaultDisplayOfItems);
		textEntryLabels.addProperty("noOfItemsPerSet",noOfItemsPerSet);
		textEntryLabels.addProperty("blankFieldError",blankFieldError);
		textEntryLabels.addProperty("createNewInvalidProductError",createNewInvalidProductError);


		bulkPasteLabels.addProperty("tabTitle",bulkPasteLabel);
		bulkPasteLabels.addProperty("bulkPasteInfo",quantityPlaceholder);
		bulkPasteLabels.addProperty("placeholder",bulkPastePlaceholder);

		excelUploadLabels.addProperty("tabTitle",excelUploadLabel);
		excelUploadLabels.addProperty("uploadInfo",uploadCatalogNumberText);
		excelUploadLabels.addProperty("downloadInfo",downloadInfo);
		excelUploadLabels.addProperty("downloadCTA",downloadTemplateLinkLabel);
		excelUploadLabels.addProperty(DamConstants.DOWNLOAD_URL,downloadUrl);

		addItemsTabs.add("textEntryLables",textEntryLabels);
		addItemsTabs.add("bulkPasteLabels",bulkPasteLabels);
		addItemsTabs.add("excelUploadLabels",excelUploadLabels);
		createShoppingList.add("addItemsTabs",addItemsTabs);

		JsonObject productLabels = new JsonObject();
		productLabels.addProperty("clone",CommonHelper.getLabel("clone",currentPage));
		productLabels.addProperty("size",CommonHelper.getLabel("size",currentPage));
		productLabels.addProperty("status",CommonHelper.getLabel("status",currentPage));
		productLabels.addProperty("catNumber",CommonHelper.getLabel("catNo",currentPage));
		productLabels.addProperty("quantity",CommonHelper.getLabel("quantityPending",currentPage));
		productLabels.addProperty("addToCartCTA",addToCartCtaText);
		productLabels.addProperty("addToCartAltText",addToCartAltText);
		productLabels.addProperty("remove",CommonHelper.getLabel("remove",currentPage));
		productLabels.addProperty("moreInfo",CommonHelper.getLabel("moreInfo",currentPage));
		productLabels.addProperty("infoIconAltText",infoIconAltText);

		shoppingListDetails.add("productLabels",productLabels);
		shoppingListDetails.addProperty("prdSuccessMsg",addToCartSuccessMessage);
		shoppingListDetails.addProperty("addAllItems",addAllItemsToCartLinkText);
		shoppingListDetails.addProperty("quickAddCTA",quickAddLinkText);
		shoppingListDetails.addProperty("quickAddTitle",quickAddTitle);
		shoppingListDetails.addProperty("addItemsCTA",addItemsToCartLinkText);
		shoppingListDetails.addProperty("noData",noItemsAddedText);
		shoppingListDetails.addProperty("noDataIcon",noDataIcon);
		shoppingListDetails.addProperty("noDataIconAltText",noDataIconAltText);
		shoppingListDetails.addProperty("listCount",shoppingListItemsText);
		shoppingListDetails.addProperty("setAsDefault",CommonHelper.getLabel("setAsDefault",currentPage));
		shoppingListDetails.addProperty("shoppingListName",listNamePlaceholderText);
		shoppingListDetails.addProperty("heading",shoppingListDetailsHeader);
		shoppingListDetails.addProperty("quickAddSucccessMsg",quickAddSuccessMsg);
		shoppingListDetails.addProperty("shoppingListNameError",listNameErrorField);
		shoppingListDetails.addProperty("exportLabel",exportLabel);

		removeShoppingList.addProperty("title",removeShoppingListHeader);
		removeShoppingList.addProperty("confirmation",areYouSureText);
		removeShoppingList.addProperty("cancel",CommonHelper.getLabel("cancel",currentPage));
		removeShoppingList.addProperty("removeCTA",CommonHelper.getLabel("remove",currentPage));

		shareShoppingList.addProperty("title",shareShoppingListHeader);
		shareShoppingList.addProperty("recipientEmail",recipientsEmailText);
		shareShoppingList.addProperty("emailError",emailError);
		shareShoppingList.addProperty("helpText",helpText);
		shareShoppingList.addProperty("emailNote",notePlaceholderText);
		shareShoppingList.addProperty("cancel",CommonHelper.getLabel("cancel",currentPage));
		shareShoppingList.addProperty("sentCTA",CommonHelper.getLabel("send",currentPage));
		shareShoppingList.addProperty("maxLimitError",maxLimitError);

		JsonObject shoppingListLabels = new JsonObject();
		shoppingListLabels.addProperty("heading",shoppingListsHeaderText);
		shoppingListLabels.addProperty("createShoppingLstCTA",createNewShoppingListCTA);
		shoppingListLabels.addProperty("saveTimeIcon",saveTimeIcon);
		shoppingListLabels.addProperty("saveTimeTitle",saveTimeText);
		shoppingListLabels.addProperty("saveTimeInfo",saveTimeInfo);
		shoppingListLabels.addProperty("shareListIcon",shareListIcon);
		shoppingListLabels.addProperty("shareListTitle",shareListText);
		shoppingListLabels.addProperty("shareListInfo",shareListInfo);
		shoppingListLabels.addProperty("searchPlaceHolder",searchPlaceHolder);
		shoppingListLabels.addProperty("default",CommonHelper.getLabel("defaultLabel",currentPage));
		shoppingListLabels.addProperty("createdBy",createdByLabel);
		shoppingListLabels.addProperty("dateCreated",dateCreatedLabel);
		shoppingListLabels.addProperty("dateModified",dateModifiedLabel);
		shoppingListLabels.addProperty("noOfProducts",noOfProductsLabel);
		shoppingListLabels.addProperty("viewDetailsCTA",CommonHelper.getLabel("viewDetails",currentPage));
		shoppingListLabels.addProperty("share",CommonHelper.getLabel("share",currentPage));
		shoppingListLabels.addProperty("remove",CommonHelper.getLabel("remove",currentPage));
		shoppingListLabels.addProperty("export",export);
		shoppingListLabels.addProperty("successMsg",shoppingListCreatedSuccessfullyText);
		shoppingListLabels.addProperty("shareShoppingListSuccessMsg",shareShoppingListSuccessMsg);
		shoppingListLabels.add("createShoppingList",createShoppingList);
		shoppingListLabels.add("shoppingListDetails",shoppingListDetails);
		shoppingListLabels.add("removeShoppingList",removeShoppingList);
		shoppingListLabels.add("shareShoppingList",shareShoppingList);
		shoppingListLabels.addProperty("createNewInvalidProductError",createNewInvalidProductError);
		return shoppingListLabels.toString();
	}

	private String setShoppingListConfig(Gson accountSettingConfigGson){
		//set the createShoppingListEndpoint
		String createShoppingListEndpoint = StringUtils.replace(
				bdbApiEndpointService.getCreateShoppingListEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload createShoppingListPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + createShoppingListEndpoint,
				HttpConstants.METHOD_POST);
		PayloadConfig createShoppingList = new PayloadConfig(createShoppingListPayload);

		//set the getShoppingListDetailsEndpoint
		String getShoppingListDetailsEndpoint = StringUtils.replace(
				bdbApiEndpointService.getGetShoppingListDetailsEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload getShoppingListDetailsPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + getShoppingListDetailsEndpoint,
				HttpConstants.METHOD_GET);
		PayloadConfig getShoppingListDetails = new PayloadConfig(getShoppingListDetailsPayload);

		//set the getShoppingListEndpoint
		String getShoppingListEndpoint = StringUtils.replace(
				bdbApiEndpointService.getGetShoppingListEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload getShoppingListEndpointPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + getShoppingListEndpoint,
				HttpConstants.METHOD_GET);
		PayloadConfig getShoppingList = new PayloadConfig(getShoppingListEndpointPayload);

		//set the fileUploadShoppingListEndpoint
		String fileUploadShoppingListEndpoint = StringUtils.replace(
				bdbApiEndpointService.getFileUploadShoppingListEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload fileUploadShoppingListPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + fileUploadShoppingListEndpoint,
				HttpConstants.METHOD_POST);
		PayloadConfig fileUploadShoppingList = new PayloadConfig(fileUploadShoppingListPayload);

		//set the removeShoppingListEndpoint
		String removeShoppingListEndpoint = StringUtils.replace(
				bdbApiEndpointService.getRemoveShoppingListEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload removeShoppingListPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + removeShoppingListEndpoint,
				HttpConstants.METHOD_DELETE);
		PayloadConfig removeShoppingList = new PayloadConfig(removeShoppingListPayload);

		//set the shareShoppingListEndpoint
		String shareShoppingListEndpoint = StringUtils.replace(
				bdbApiEndpointService.getShareShoppingListEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload shareShoppingListPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + shareShoppingListEndpoint,
				HttpConstants.METHOD_POST);
		PayloadConfig shareShoppingList = new PayloadConfig(shareShoppingListPayload);
		
		//set the exportShoppingListEndpoint
		String exportShoppingListEndpoint = StringUtils.replace(
				bdbApiEndpointService.getExportShoppingListEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload exportShoppingListPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + exportShoppingListEndpoint,
				HttpConstants.METHOD_POST);
		PayloadConfig exportShoppingList = new PayloadConfig(exportShoppingListPayload);		

		//set the removeShoppingListEntriesEndpoint
		String removeShoppingListEntriesEndpoint = StringUtils.replace(
				bdbApiEndpointService.getRemoveShoppingListEntriesEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload removeShoppingListEntriesPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + removeShoppingListEntriesEndpoint,
				HttpConstants.METHOD_DELETE);
		PayloadConfig removeShoppingListEntries = new PayloadConfig(removeShoppingListEntriesPayload);


		//set the updateShoppingListEntriesEndpoint
		String updateShoppingListEntriesEndpoint = StringUtils.replace(
				bdbApiEndpointService.getUpdateShoppingListEntriesEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload updateShoppingListEntriesPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + updateShoppingListEntriesEndpoint,
				HttpConstants.METHOD_POST);
		PayloadConfig updateShoppingListEntries = new PayloadConfig(updateShoppingListEntriesPayload);
		

		//set the fileUploadShoppingListEntries
		String fileUploadShoppingListEntriesEndpoint = StringUtils.replace(
				bdbApiEndpointService.getFileUploadShoppingListEntriesEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload fileUploadShoppingListEntriesPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + fileUploadShoppingListEntriesEndpoint,
				HttpConstants.METHOD_POST);
		PayloadConfig fileUploadShoppingListEntries = new PayloadConfig(fileUploadShoppingListEntriesPayload);


		//set the getProductAnnouncements
		String getProductAnnouncementsEndpoint =
				bdbApiEndpointService.getProductAnnouncements();
		Payload getProductAnnouncementsPayload = new Payload(getProductAnnouncementsEndpoint,
				HttpConstants.METHOD_POST);
		PayloadConfig getProductAnnouncements = new PayloadConfig(getProductAnnouncementsPayload);


		//set the addAllItemsToCart
		String addAllItemsToCartEndpoint = StringUtils.replace(
				bdbApiEndpointService.getAddAllItemsToCartEndpoint(),
				CommonConstants.HYBRIS_SITE_LITERAL,
				hybrisSiteId);
		Payload addAllItemsToCartPayload = new Payload(
				bdbApiEndpointService.getBDBHybrisDomain() + addAllItemsToCartEndpoint,
				HttpConstants.METHOD_POST);
		PayloadConfig addAllItemsToCart = new PayloadConfig(addAllItemsToCartPayload);

		// creating the final config
		ShoppingListConfig shoppingListConfig = new ShoppingListConfig(createShoppingList,
				getShoppingListDetails,
				getShoppingList,
				fileUploadShoppingList,
				removeShoppingList,
				shareShoppingList,
				exportShoppingList,
				removeShoppingListEntries,
				updateShoppingListEntries,
				fileUploadShoppingListEntries,
				getProductAnnouncements,
				addAllItemsToCart);

		return  accountSettingConfigGson.toJson(shoppingListConfig);
	}

	/**
	 * Populate globalFileUploadModel.
	 */
	private void setFileUploadLabels(){
		GlobalFileUploadModel globalFileUploadModel = CommonHelper.getGlobalFileUploadModel(globalFileUploadPath, resourceResolver, GLOBAL_FILE_UPLOAD_RESOURCE_TYPE);
		if(null != globalFileUploadModel)
		{
			fileUploadLabels = GlobalFileUploadUtil.createGlobalFileUploadLabels(globalFileUploadModel, fileUploadNote);
		}
	}
}