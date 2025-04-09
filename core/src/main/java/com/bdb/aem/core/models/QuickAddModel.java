package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CookieNameService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;

/**
 * The Class QuickAddModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuickAddModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(QuickAddModel.class);
	
	
	/** The request. */
	@Inject
	SlingHttpServletRequest request;
	
	/** The current page. */
	@Inject
	Page currentPage;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The bdb api endpoint service. */
	@Inject
	CookieNameService cookieNameService;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	
	/** The quick add config. */
	private String quickAddConfig;


	/** The quick add labels. */
	private String quickAddLabels;
	
	
	/** The hybris site id. */
	private String hybrisSiteId;
	
	/** * Page Properties to fetch other Properties from Page */
	@Inject
	private InheritanceValueMap pageProperties;
	
	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("MyCartGrantsModel - Init method started");

			quickAddConfig = getConfigJson();

			quickAddLabels = getLabelJson().toString();
			
			logger.debug("quickAddConfig - Init {}",quickAddConfig);
			logger.debug("quickAddLabels - Init {}",quickAddLabels);
	}
	/**
	 * This method forms the json for quick add config.
	 *
	 * @return the config json
	 */
	public String getConfigJson(){
		JsonObject quickAddConfigJson = new JsonObject();
		JsonObject bulkEntry = new JsonObject();
		JsonObject bulkUpload = new JsonObject();
		
		if(hybrisSiteId==null)
			hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		
		String bulkEntryEndpoint = bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getQuickAddBulkEntryEndpoint();
		
		String bulkUploadEndpoint = bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getQuickAddBulkUploadEndpoint();
		
		if(null != hybrisSiteId) {
			bulkEntry.addProperty(CommonConstants.URL, bulkEntryEndpoint.replace(CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId));
			bulkEntry.addProperty(CommonConstants.METHOD, CommonConstants.POST);
			
			bulkUpload.addProperty(CommonConstants.URL, bulkUploadEndpoint.replace(CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId));
			bulkUpload.addProperty(CommonConstants.METHOD, CommonConstants.POST);
		}
		
		quickAddConfigJson.add(CommonConstants.BULK_ENTRY_QUICK_ADD, bulkEntry);
		quickAddConfigJson.add(CommonConstants.BULK_UPLOAD_QUICK_ADD, bulkUpload);
		
		return quickAddConfigJson.toString();
	}
		
	
	/**
	 * Gets the label json.
	 *
	 * @return the label json
	 */
	public JsonObject getLabelJson(){
		JsonObject quickAddLabelsJson = new JsonObject();
		
		JsonObject addItemsTabsJson = new JsonObject();
		JsonObject textEntryLabelsJson = new JsonObject();
		JsonObject bulkPasteLabelsJson = new JsonObject();
		JsonObject excelUploadLabelsJson = new JsonObject();
	
		// forming labels JSON 
		quickAddLabelsJson.addProperty(CommonConstants.HEADING_QUICK_ADD, CommonHelper.getLabel(CommonConstants.HEADING_QUICK_ADD, currentPage));
		
		addItemsTabsJson.addProperty(CommonConstants.ADD_ITEMS_INFO, CommonHelper.getLabel(CommonConstants.ADD_ITEMS_INFO, currentPage));
		addItemsTabsJson.addProperty(CommonConstants.CANCEL, CommonHelper.getLabel(CommonConstants.CANCEL_QUICK_ADD, currentPage));
		addItemsTabsJson.addProperty(CommonConstants.SAVE_LIST_CTA, CommonHelper.getLabel("saveListTextCTA", currentPage));
		if(pageProperties.getInherited(CommonConstants.ENABLE_ADD_TO_QUOTE,Boolean.FALSE)) {
			addItemsTabsJson.addProperty(CommonConstants.ADD_ITEMS_HEADING, CommonHelper.getLabel(CommonConstants.ADD_ITEMS_HEADING, currentPage));
			addItemsTabsJson.addProperty(CommonConstants.QUICK_ADD_TITLE, CommonHelper.getLabel(CommonConstants.QUICK_ADD_TITLE, currentPage));
		}
		textEntryLabelsJson.addProperty(CommonConstants.TAB_TITLE, CommonHelper.getLabel(CommonConstants.TAB_TITLE, currentPage));
		textEntryLabelsJson.addProperty(CommonConstants.TEXT_ENTRY_INFO, CommonHelper.getLabel(CommonConstants.TEXT_ENTRY_INFO, currentPage));
		textEntryLabelsJson.addProperty(CommonConstants.CATALOG_NUM_QUICK_ADD, CommonHelper.getLabel(CommonConstants.CATALOG_NUM_QUICK_ADD, currentPage));
		textEntryLabelsJson.addProperty(CommonConstants.CATALOG_NUM_TITLE_QUICK_ADD, CommonHelper.getLabel(CommonConstants.CATALOG_NUM_TITLE_QUICK_ADD, currentPage));
		textEntryLabelsJson.addProperty(CommonConstants.QUANTITY_QUICK_ADD, CommonHelper.getLabel("quantityQuick", currentPage));
		textEntryLabelsJson.addProperty(CommonConstants.QUANTITY_TITLE_QUICK_ADD, CommonHelper.getLabel(CommonConstants.QUANTITY_TITLE_QUICK_ADD, currentPage));
		textEntryLabelsJson.addProperty(CommonConstants.ADD_MORE_ITEMS, CommonHelper.getLabel("addMoreItemQuick", currentPage));
		textEntryLabelsJson.addProperty(CommonConstants.BLANK_FIELD_ERROR, CommonHelper.getLabel(CommonConstants.BLANK_FIELD_ERROR, currentPage));
		
		addItemsTabsJson.add(CommonConstants.TEXT_ENTRY_LABELS, textEntryLabelsJson);
		
		bulkPasteLabelsJson.addProperty(CommonConstants.TAB_TITLE, CommonHelper.getLabel("tabTitleBulk", currentPage));
		bulkPasteLabelsJson.addProperty(CommonConstants.PLACEHOLDER_QUICK_ADD, CommonHelper.getLabel(CommonConstants.PLACEHOLDER_QUICK_ADD, currentPage));
		bulkPasteLabelsJson.addProperty(CommonConstants.BULK_PASTE_INFO, CommonHelper.getLabel(CommonConstants.BULK_PASTE_INFO, currentPage));
		
		addItemsTabsJson.add(CommonConstants.BULK_PASTE_LABELS, bulkPasteLabelsJson);
		
		excelUploadLabelsJson.addProperty(CommonConstants.TAB_TITLE, CommonHelper.getLabel("tabTitleExcel", currentPage));
		excelUploadLabelsJson.addProperty(CommonConstants.UPLOAD_INFO, CommonHelper.getLabel(CommonConstants.UPLOAD_INFO, currentPage));
		excelUploadLabelsJson.addProperty(CommonConstants.DOWNLOAD_INFO, CommonHelper.getLabel(CommonConstants.DOWNLOAD_INFO, currentPage));
		excelUploadLabelsJson.addProperty(CommonConstants.DOWNLOAD_CTA, CommonHelper.getLabel(CommonConstants.DOWNLOAD_CTA, currentPage));
		excelUploadLabelsJson.addProperty(CommonConstants.DOWNLOAD_URL,externalizerService.getFormattedUrl(CommonHelper.getLabel(CommonConstants.DOWNLOAD_URL, currentPage), resourceResolver));
		
		excelUploadLabelsJson.add(CommonConstants.FILE_UPLOAD_LABELS, setFilUploadJson());
		
		addItemsTabsJson.add(CommonConstants.EXCEL_UPLOAD_LABELS, excelUploadLabelsJson);
		
		quickAddLabelsJson.add(CommonConstants.ADD_ITEMS_TABS, addItemsTabsJson);
		
		return quickAddLabelsJson;
		
	}
	
	private JsonObject setFilUploadJson() {
		
		JsonObject fileUploadLabelsJson = new JsonObject();
		fileUploadLabelsJson.addProperty(CommonConstants.ICON_SRC_QUICK_ADD, externalizerService.getFormattedUrl(CommonHelper.getLabel(CommonConstants.ICON_SRC_QUICK_ADD, currentPage), resourceResolver));
		fileUploadLabelsJson.addProperty(CommonConstants.ALT_TEXT_QUICK_ADD, CommonHelper.getLabel(CommonConstants.ALT_TEXT_QUICK_ADD, currentPage));
		fileUploadLabelsJson.addProperty(CommonConstants.TITLE_QUICK_ADD, CommonHelper.getLabel(CommonConstants.TITLE_QUICK_ADD, currentPage));
		fileUploadLabelsJson.addProperty(CommonConstants.NOTE_QUICK_ADD, CommonHelper.getLabel(CommonConstants.NOTE_QUICK_ADD, currentPage));
		fileUploadLabelsJson.addProperty(CommonConstants.BTN_NAME, CommonHelper.getLabel(CommonConstants.BTN_NAME, currentPage));
		if(pageProperties.getInherited(CommonConstants.ENABLE_ADD_TO_QUOTE,Boolean.FALSE)) {
			fileUploadLabelsJson.addProperty(CommonConstants.FIELD_TITLE, CommonHelper.getLabel(CommonConstants.FIELD_TITLE, currentPage));
			fileUploadLabelsJson.addProperty(CommonConstants.INFO_QUICK_ADD, CommonHelper.getLabel(CommonConstants.INFO_QUICK_ADD, currentPage));
			fileUploadLabelsJson.addProperty(CommonConstants.ARIA_LABEL_QUICK, CommonHelper.getLabel(CommonConstants.ARIA_LABEL_QUICK, currentPage));
			fileUploadLabelsJson.addProperty(CommonConstants.FILE_ICON_SRC,externalizerService.getFormattedUrl(CommonHelper.getLabel(CommonConstants.FILE_ICON_SRC, currentPage), resourceResolver));
			fileUploadLabelsJson.addProperty(CommonConstants.FIELD_TITLE, CommonHelper.getLabel(CommonConstants.FIELD_TITLE, currentPage));
			fileUploadLabelsJson.addProperty(CommonConstants.FILE_ICON_ALT_TEXT, CommonHelper.getLabel(CommonConstants.FILE_ICON_ALT_TEXT, currentPage));
			fileUploadLabelsJson.addProperty(CommonConstants.CLOSE_ICON, externalizerService.getFormattedUrl(CommonHelper.getLabel(CommonConstants.CLOSE_ICON, currentPage), resourceResolver));
			fileUploadLabelsJson.addProperty(CommonConstants.ARIA_LABEL_CLOSE, CommonHelper.getLabel(CommonConstants.ARIA_LABEL_CLOSE, currentPage));
		}
		return fileUploadLabelsJson;
	}
	
	/**
	 * Gets the quick add config.
	 *
	 * @return the quick add config
	 */
	public String getQuickAddConfig() {
		return quickAddConfig;
	}

	/**
	 * Gets the quick add labels.
	 *
	 * @return the quick add labels
	 */
	public String getQuickAddLabels() {
		return quickAddLabels;
	}

	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	public String getHybrisSiteId() {
        return hybrisSiteId;
    }
	
}