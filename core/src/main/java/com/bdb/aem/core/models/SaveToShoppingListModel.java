package com.bdb.aem.core.models;

import com.adobe.agl.impl.StringUCharacterIterator;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CookieNameService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * The Class SaveToShoppingListModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SaveToShoppingListModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(SaveToShoppingListModel.class);
	
	
	/** The close icon. */
	@Inject
	@Via("resource")
	@Default(values = "")
	public String closeIcon;

	
	/** The add to list label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String addToListLabel;

	
	/** The create list label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String createListLabel;

	
	/** The added to list label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String addedToListLabel;

	
	/** The view list label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String viewListLabel;

	
	/** The close cta label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String closeCtaLabel;

	
	/** The save cta label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String saveCtaLabel;
	
	
	/** The create list cta label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String createListCtaLabel;

	
	/** The add to list cta label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String addToListCtaLabel;

	
	/** The input label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String inputLabel;

	
	/** The drop down label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String dropDownLabel;

	
	/** The add to list content. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String addToListContent;

	
	/** The added to list content. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String addedToListContent;
	
	/** The added to list error message. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String addedToErrorMessageContent;
	
	/** The save cta. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String saveCta;
	
	/** The create list. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String createList;
	
	/** The close cta. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String closeCta;
	
	/** The add to list. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String addToList;

	@Inject
	@Via("resource")
	@Default(values= StringUtils.EMPTY)
	private String selectQuoteList;

	@Inject
	@Via("resource")
	@Default(values=StringUtils.EMPTY)
	private String quoteDropDownLabel;
	
	@Default(values=StringUtils.EMPTY)
	private String viewAllListUrlCart;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The bdb api endpoint service. */
	@Inject
	CookieNameService cookieNameService;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	
	/** The save to list labels. */
	private String saveToListLabels;


	/** The save to list config. */
	private String saveToListConfig;
	
	
	/** The hybris site id. */
	private String hybrisSiteId;
	
	/** Quote List Label */
	private String quoteListLabels;

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("SaveToShoppingListModel - Init method started");
		
			viewAllListUrlCart = StringUtils.isNotEmpty(CommonHelper.getLabel("viewAllListUrlCart", currentPage))?CommonHelper.getLabel("viewAllListUrlCart", currentPage):StringUtils.EMPTY;
			saveToListLabels = getLabelJson();
			//Adding Configs for QuoteList and Shopping List
			JsonObject saveToListConfigJson=new JsonObject();
			JsonObject shoppingListConfig=new JsonObject();
			//Adding Shopping List Config
			getConfigJson(shoppingListConfig, resourceResolver);
			saveToListConfigJson.add("shoppingListConfig", shoppingListConfig);
			JsonObject quoteListConfig=new JsonObject();
			//Adding Quote List Config
			getConfigJson(quoteListConfig, resourceResolver);
			saveToListConfigJson.add("quoteListConfig", quoteListConfig);
			saveToListConfig=saveToListConfigJson.toString();

			quoteListLabels = getQuoteLabelJson();
			logger.debug("saveToListConfig - Init {}",saveToListConfig);
			logger.debug("saveToListLabels - Init {}",saveToListLabels);
	}
	
	/**
	 * This method forms the json for save to shopping list config.
	 *
	 * @return the config json
	 */
	public void getConfigJson(JsonObject shoppingListConfig, ResourceResolver resourceResolver){
		JsonObject getListJsonObject = new JsonObject();
		JsonObject createListJsonObject = new JsonObject();
		JsonObject payloadJsonObject = new JsonObject();
		JsonObject updateListJsonObject = new JsonObject();
		if(hybrisSiteId==null)
			hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		
		String getListEndpoint = bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getGetShoppingListEndpoint().replace(CommonConstants.HYBRIS_USER_ID_LITERAL, bdbApiEndpointService.getCurrentUserIdPlaceholder());
		
		String createListEndpoint = bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getCreateShoppingListEndpoint().replace(CommonConstants.HYBRIS_USER_ID_LITERAL, bdbApiEndpointService.getCurrentUserIdPlaceholder());
		
		String updateListEndpoint = bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getUpdateShoppingListEntriesEndpoint().replace(CommonConstants.HYBRIS_USER_ID_LITERAL, bdbApiEndpointService.getCurrentUserIdPlaceholder());
		if( null != hybrisSiteId ) {
			getListJsonObject.addProperty(CommonConstants.URL, getListEndpoint.replace(CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId));
			getListJsonObject.addProperty(CommonConstants.METHOD, CommonConstants.GET);
			
			createListJsonObject.addProperty(CommonConstants.URL, createListEndpoint.replace(CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId));
			createListJsonObject.addProperty(CommonConstants.METHOD, CommonConstants.POST);
			createListJsonObject.add(CommonConstants.PAYLOAD, payloadJsonObject);
			
			updateListJsonObject.addProperty(CommonConstants.URL, updateListEndpoint.replace(CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId));
			updateListJsonObject.addProperty(CommonConstants.METHOD, CommonConstants.POST);
			updateListJsonObject.add(CommonConstants.PAYLOAD, payloadJsonObject);
		}
		
		shoppingListConfig.add(CommonConstants.GET_LIST, getListJsonObject);
		shoppingListConfig.add(CommonConstants.CREATE_LIST, createListJsonObject);
		shoppingListConfig.add(CommonConstants.UPDATE_LIST, updateListJsonObject);
		//Configure shopping list URL
		shoppingListConfig.addProperty(CommonConstants.VIEW_ALL_LIST_URL, CommonHelper.getShortUrl(getExternalizedUrl(viewAllListUrlCart, resourceResolver), currentPage,
				resourceResolver, bdbApiEndpointService.getCustomRunMode()));
		String signInUrl = CommonHelper.getSignInUrl(bdbApiEndpointService, externalizerService, resourceResolver, currentPage);
		// SSO sign-in URL
		shoppingListConfig.addProperty(CommonConstants.SIGN_IN_URL, signInUrl);

	}
		
	/**
	 * This method forms the json for save to shopping list labels.
	 *
	 * @return the label json
	 */
	public String getLabelJson(){
		JsonObject saveToListLabelsJson = new JsonObject();
		JsonObject shoppingListLabels = new JsonObject();
		JsonObject ariaLabelsObject = new JsonObject();
		
		// forming labels JSON 
		shoppingListLabels.addProperty(CommonConstants.CLOSE_ICON_SHOPPING_LIST, closeIcon);
		shoppingListLabels.addProperty(CommonConstants.ADD_TO_LIST_LABEL, addToListLabel);
		shoppingListLabels.addProperty(CommonConstants.CREATE_LIST_LABEL, createListLabel);
		shoppingListLabels.addProperty(CommonConstants.ADDED_TO_LIST_LABEL, addedToListLabel);
		shoppingListLabels.addProperty(CommonConstants.VIEW_LIST_LABEL, viewListLabel);
		shoppingListLabels.addProperty(CommonConstants.CLOSE_CTA_LABEL, closeCtaLabel);
		shoppingListLabels.addProperty(CommonConstants.SAVE_CTA_LABEL, saveCtaLabel);
		shoppingListLabels.addProperty(CommonConstants.CREATE_LIST_CTA_LABEL, createListCtaLabel);
		shoppingListLabels.addProperty(CommonConstants.ADD_TO_LIST_CTA_LABEL, addToListCtaLabel);
		shoppingListLabels.addProperty(CommonConstants.INPUT_LABEL, inputLabel);
		shoppingListLabels.addProperty(CommonConstants.DROPDOWN_LABEL, dropDownLabel);
		shoppingListLabels.addProperty(CommonConstants.ADD_TO_LIST_CONTENT, addToListContent);
		shoppingListLabels.addProperty(CommonConstants.ADDED_TO_LIST_CONTENT, addedToListContent);
		shoppingListLabels.addProperty(CommonConstants.ADDED_TO_LIST_ERROR_MESSAGE, addedToErrorMessageContent);
		
		ariaLabelsObject.addProperty(CommonConstants.SAVE_CTA, saveCta);
		ariaLabelsObject.addProperty(CommonConstants.CREATE_LIST, createList);
		ariaLabelsObject.addProperty(CommonConstants.CLOSE_CTA, closeCta);
		ariaLabelsObject.addProperty(CommonConstants.ADD_TO_LIST, addToList);
		shoppingListLabels.add(CommonConstants.ARIA_LABEL, ariaLabelsObject);
		saveToListLabelsJson.add("shoppingListLabels",shoppingListLabels);
		
		return saveToListLabelsJson.toString();
		
	}

	private String getQuoteLabelJson()
	{
		JsonObject quoteListLabels=new JsonObject();
		quoteListLabels.addProperty(CommonConstants.CLOSE_ICON_SHOPPING_LIST,closeIcon);
		quoteListLabels.addProperty(CommonConstants.ADD_TO_LIST_LABEL,selectQuoteList);
		quoteListLabels.addProperty(CommonConstants.CREATE_LIST_LABEL,createListLabel);
		quoteListLabels.addProperty(CommonConstants.ADDED_TO_LIST_LABEL, addedToListLabel);
		quoteListLabels.addProperty(CommonConstants.VIEW_LIST_LABEL, viewListLabel);
		quoteListLabels.addProperty(CommonConstants.CLOSE_CTA_LABEL, closeCtaLabel);
		quoteListLabels.addProperty(CommonConstants.SAVE_CTA_LABEL, saveCtaLabel);
		quoteListLabels.addProperty(CommonConstants.CREATE_LIST_CTA_LABEL, createListCtaLabel);
		quoteListLabels.addProperty(CommonConstants.ADD_TO_LIST_CTA_LABEL, addToListCtaLabel);
		quoteListLabels.addProperty(CommonConstants.INPUT_LABEL, inputLabel);
		quoteListLabels.addProperty(CommonConstants.DROPDOWN_LABEL,quoteDropDownLabel);
		quoteListLabels.addProperty(CommonConstants.ADD_TO_LIST_CONTENT, addToListContent);
		quoteListLabels.addProperty(CommonConstants.ADDED_TO_LIST_CONTENT, addedToListContent);
		quoteListLabels.addProperty(CommonConstants.ADDED_TO_LIST_ERROR_MESSAGE, addedToErrorMessageContent);

		JsonObject ariaLabelsObject=new JsonObject();
		ariaLabelsObject.addProperty(CommonConstants.SAVE_CTA, saveCta);
		ariaLabelsObject.addProperty(CommonConstants.CREATE_LIST, createList);
		ariaLabelsObject.addProperty(CommonConstants.CLOSE_CTA, closeCta);
		ariaLabelsObject.addProperty(CommonConstants.ADD_TO_LIST, addToList);
		quoteListLabels.add(CommonConstants.ARIA_LABEL,ariaLabelsObject);
		return quoteListLabels.toString();
	}
	

	/**
	 * Gets the save to list labels.
	 *
	 * @return the save to list labels
	 */
	public String getSaveToListLabels() {
		return saveToListLabels;
	}

	/**
	 * Gets the save to list config.
	 *
	 * @return the save to list config
	 */
	public String getSaveToListConfig() {
		return saveToListConfig;
	}
	
	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	public String getHybrisSiteId() {
        return hybrisSiteId;
    }


	public String getQuoteListLabels() {
		return quoteListLabels;
	}
	
	public String getExternalizedUrl(String urlPath, ResourceResolver resourceResolver) {
		String finalUrl = StringUtils.EMPTY;
		if(!urlPath.isEmpty()) {
			String relativeUrl = StringUtils.substringBefore(urlPath, CommonConstants.DOT_HTML);
			String externalizedURL = externalizerService.getFormattedUrl(relativeUrl, resourceResolver);
			finalUrl = externalizedURL.concat(StringUtils.substringAfter(urlPath, CommonConstants.DOT_HTML));
		}
		return finalUrl;
	}
}
