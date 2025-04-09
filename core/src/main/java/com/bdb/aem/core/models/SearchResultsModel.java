package com.bdb.aem.core.models;

import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Sling Model to fetch values from dialog and product structure in AEM, and
 * return respective JSONs to the FE.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchResultsModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(SearchResultsModel.class);
	
	/** The Clear All  */
	@Inject
	@Via("resource")
	@Default(values = "")
	public String clearAll;

	/** The Show Label. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String showLabel;

	/** The Filter By Category */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String filterbyCategory;
	
	/** The next label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String nextLabel;
	
	/** The prev label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String prevLabel;

	/** The Up Arrow Image */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String upArrowImage;

	/** The Up Arrow Alt Text. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String upArrowAltText;

	/** The Down Arrow Image. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String downArrowImage;

	/** The Down Arrow Mobile. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String downArrowMobile;
	
	/** The Down Arrow Alt Text. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String downArrowAltText;

	/** The Showing Label */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String showingLabel;

	/** The Results For Label */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String resultsForLabel;
	
	/** The Search Instead Label */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String searchInsteadLabel; 

	/** The Cart Image */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String cartImage;
	
	/** The Cart Alt Text */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String cartAltText;
	
	/** The Close Icon */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String closeIcon;
	
	
	/** The Close Icon Text */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String closeIconText;
	
	/** The Per Page Results */
	@Inject
	@Via("resource")
	@Default(values = "25")
	private String perPageResults;
	
	/** The Grant Eligible Icon */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String grantEligibleIcon;
	
	
	/** The Grant Eligible Label */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String grantEligibleLabel;
	
	/** The statusLabel */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String statusLabel;
	
	/** The cloneLabel */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String cloneLabel;
	
	/** The sizeLabel */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String sizeLabel;
	
	/** The catNoLabel */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String catNoLabel;
	
	/** The No Results Icon. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String noResultsIcon;
	
	/** The No Results Alt Text. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String noResultsAltText;
	
	/** The No Results Heading. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String noResultsHeading;
	
	/** The No Results Sub Heading. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String noResultsSubHeading;
	
	/** The No Results Clear Filter. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String noResultsClearFilters;
	
	/** The Empty Icon */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emptyIcon;
	
	/** The empty icon alt text */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emptyIconAltText;
	
	/** The try new search label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String tryNewSearchLabel;
	
	/** The try check spell */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String tryCheckSpell;
	
	/** The Try check Spell Keywords */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String tryCheckSpellKeywords;
	
	/** The Specific Results */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String specificResult;
	
	/** The No Listing */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String noListing;
	
	/** saveToQuoteList. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String saveToQuoteList;
	
	/** The InquireLabel. */
	@Inject
	@Via("resource")
	@Default(values = "Inquire")
	private String inquireLabel;
	
	/** The modify search label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String modifySearchText;
	
	
	/** The get your price label. */
	@Inject
	@Via("resource")
	@Default(values = "Get your price")
	private String getYourPriceCTA;
	
	/** The view more label. */
	@Inject
	@Via("resource")
	@Default(values = "View More")
	private String viewMoreLabel;
	
	/** The lawsAndRegulations label. */
	@Inject
	@Via("resource")
	@Default(values = "Laws and Regulations")
	private String lawsAndRegulations;
	
	/** The Dropdown List Options */
	@Inject
	@ChildResource
	private Resource dropdownListOptions;
	
	/** The facet catogory Options */
	@Inject
	@ChildResource
	private Resource facetcategory;
	
	/** The Regulator status Japan translation. */
	@Inject
	@Via("resource")
	@Default(values = "体外診断用医薬品")
	private String regStatusForJapan;
	
	/** The Current page */
	@Inject
	Page currentPage;
	
	/** The BDB API endpoint service */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;
	
	/** The Search Results JSON Label */
	private String searchResultsJsonLabels;
	
	/** The Search Results JSON Config */
	private String searchResultsJsonConfigs;

	/** The request. */
	@Inject
	private SlingHttpServletRequest request;

	private Boolean isInstrument;

	@SlingObject
	ResourceResolver resourceResolver;
	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("SearchResultsModel - Init method started");
		
			searchResultsJsonConfigs = getConfigJson();

			searchResultsJsonLabels = getLabelJson();
			
	}


	/**
	 * This method forms the json for commerce box config.
	 *
	 * @return the config json
	 */
	public String getConfigJson(){
		JsonObject searchResultsJsonConfig = new JsonObject();
		JsonObject requestPayloadJsonObject = new JsonObject();
		JsonObject productAnnouncementJsonObject = new JsonObject();
		JsonObject pricingDetailsJson = new JsonObject();
		JsonObject companionProductJson = new JsonObject();
		String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		requestPayloadJsonObject.addProperty(CommonConstants.URL, bdbApiEndpointService.getSearchResultsServletPath());
		requestPayloadJsonObject.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		searchResultsJsonConfig.add(CommonConstants.REQUEST_PAYOAD, requestPayloadJsonObject);
		
		pricingDetailsJson.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getSearchListEndpoint().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
		pricingDetailsJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		searchResultsJsonConfig.add(CommonConstants.PRICINGDETAILS, pricingDetailsJson);


		productAnnouncementJsonObject.addProperty(CommonConstants.URL, bdbApiEndpointService.getProductAnnouncements());
		productAnnouncementJsonObject.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);		
		searchResultsJsonConfig.add(CommonConstants.PRODUCT_ANNOUNCEMET, productAnnouncementJsonObject);
		searchResultsJsonConfig.addProperty(CommonConstants.TDS_FILE_PATH, bdbApiEndpointService.getTdsEndpoint());

		try{
			String productVariant = CommonHelper.getSelectorDetails(request);
			if(productVariant != null) {
				String productVarHPPath;
				Resource hpBaseResource = null;
				if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)) {
					productVarHPPath = null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH) ? request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString() : StringUtils.EMPTY;
					hpBaseResource = resourceResolver.getResource(productVarHPPath);
					if (hpBaseResource != null) {
						ValueMap baseHpMap = hpBaseResource.adaptTo(ValueMap.class);
						if (baseHpMap.containsKey(CommonConstants.COMPANION_PRODUCT_URL)) {
							String productUrl = baseHpMap.get(CommonConstants.COMPANION_PRODUCT_URL).toString();
							if (productUrl != null) {
								isInstrument = true;
								companionProductJson.addProperty(CommonConstants.URL, productUrl);
								companionProductJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
								searchResultsJsonConfig.add(CommonConstants.GET_COMPANION_PRODUCTS, companionProductJson);
							}
						}
					}
				}
			}
		} finally {
			return searchResultsJsonConfig.toString();
		}
	}
	
	/**
	 * This method forms the json for commerce box labels.
	 *
	 * @return the label json
	 */
	public String getLabelJson(){
		JsonObject searchResultsJsonLabel = new JsonObject();
		  
		// forming labels JSON 
		searchResultsJsonLabel.addProperty(CommonConstants.CLEAR_ALL, clearAll);
		searchResultsJsonLabel.addProperty(CommonConstants.SHOW_LABEL, showLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.FILTER_BY_CATEGORY, filterbyCategory);
		searchResultsJsonLabel.addProperty(CommonConstants.NEXT_LABEL, nextLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.PREV_LABEL, prevLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.UP_ARROW_IMAGE, upArrowImage);
		searchResultsJsonLabel.addProperty(CommonConstants.UP_ARROW_ALT_TEXT, upArrowAltText);
		searchResultsJsonLabel.addProperty(CommonConstants.DOWN_ARROW_IMAGE, downArrowImage);
		searchResultsJsonLabel.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT, downArrowAltText);
		searchResultsJsonLabel.addProperty(CommonConstants.DOWN_ARROW_MOBILE, downArrowMobile);
		searchResultsJsonLabel.addProperty(CommonConstants.SHOWING_LABEL, showingLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.RESULTS_FOR, resultsForLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.COMPARE_LABEL, CommonHelper.getLabel(CommonConstants.COMPARE_LABEL_KEY,currentPage));
		searchResultsJsonLabel.addProperty(CommonConstants.YOUR_PRICE_LABEL_ATC, CommonHelper.getLabel(CommonConstants.YOUR_PRICE,currentPage));
		searchResultsJsonLabel.addProperty(CommonConstants.LIST_PRICE_LABEL_ATC, CommonHelper.getLabel(CommonConstants.LIST_PRICE,currentPage));
		searchResultsJsonLabel.addProperty(CommonConstants.SAVE_TO_SHOPPING_LIST, CommonHelper.getLabel(CommonConstants.SAVE_TO_SHOPPING_LIST_KEY,currentPage));
		searchResultsJsonLabel.addProperty(CommonConstants.BOOSTRULES, CommonHelper.getLabel(CommonConstants.BOOSTRULES,currentPage));
		searchResultsJsonLabel.addProperty(CommonConstants.CART_IMAGE, cartImage);
		searchResultsJsonLabel.addProperty(CommonConstants.CLOSE_ICON, closeIcon);
		searchResultsJsonLabel.addProperty(CommonConstants.CLOSE_ICON_TEXT, closeIconText);
		searchResultsJsonLabel.addProperty(CommonConstants.GRANT_ELIGIBLE_ICON, grantEligibleIcon);
		searchResultsJsonLabel.addProperty(CommonConstants.GRANT_ELIGIBLE_LABEL, grantEligibleLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.SEARCHINSTEAD, searchInsteadLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.CLONE_LABEL, cloneLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.SIZE_LABEL, sizeLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.STATUS_LABEL, statusLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.CAT_NO_LABEL, catNoLabel);
		searchResultsJsonLabel.add(CommonConstants.DROPDOWN_LIST, getDropdownList());
		searchResultsJsonLabel.add(CommonConstants.FACETS, getFacetsList()); 
		searchResultsJsonLabel.add(CommonConstants.PERPAGERESULTS, getPerPageResults());
		searchResultsJsonLabel.addProperty(CommonConstants.SAVE_TO_QUOTE_LIST, saveToQuoteList);
		searchResultsJsonLabel.addProperty(CommonConstants.BRIGHT_COVE_ACCOUNT_ID, bdbApiEndpointService.brightcoveAccountId());
		searchResultsJsonLabel.addProperty(CommonConstants.BRIGHT_COVE_PLAYER_ID, bdbApiEndpointService.brightcovePlayerId());
		
		boolean isQuote = CommonHelper.toBoolean(CommonHelper.getLabel(CommonConstants.ENABLE_ADD_TO_QUOTE, currentPage));
		searchResultsJsonLabel.addProperty(CommonConstants.IS_QUOTE,isQuote);
		if(isQuote) {
			String addToQuoteLabel = CommonHelper.getLabel(CommonConstants.ADD_TO_QUOTE_LABEL,currentPage);
			String quoteAltTextLabel = CommonHelper.getLabel(CommonConstants.QUOTE_ALT_TEXT_LABEL,currentPage);
			searchResultsJsonLabel.addProperty(CommonConstants.ADD_TO_QUOTE, addToQuoteLabel);
			searchResultsJsonLabel.addProperty(CommonConstants.ADD_TO_QUOTE_ALT, quoteAltTextLabel);
		}else {	
			String cartAltTextLabel = CommonHelper.getLabel(CommonConstants.CART_ALT_TEXT_LABEL,currentPage);
			String addToCartLabel = CommonHelper.getLabel(CommonConstants.ADD_TO_CART_LABEL,currentPage);			
			searchResultsJsonLabel.addProperty(CommonConstants.ADD_TO_CART_LABEL, addToCartLabel);
			searchResultsJsonLabel.addProperty(CommonConstants.CART_ALT_TEXT, cartAltTextLabel);
		}
		
		searchResultsJsonLabel.add("noResults", getNoResults());
		searchResultsJsonLabel.add("noSearchResults", getNoSearchResults());
		searchResultsJsonLabel.addProperty(CommonConstants.INQUIRE, inquireLabel);
		searchResultsJsonLabel.addProperty(CommonConstants.MODIFY_SEARCH_TEXT, modifySearchText);
		searchResultsJsonLabel.addProperty("getYourPriceCTA", getYourPriceCTA);
		searchResultsJsonLabel.addProperty("viewMoreLabel", viewMoreLabel);
		searchResultsJsonLabel.addProperty("lawsAndRegulations", lawsAndRegulations);
		searchResultsJsonLabel.addProperty("regStatusForJapan", regStatusForJapan);

		return searchResultsJsonLabel.toString();
	}
	
	
	/**
	 * 
	 * @return
	 */
	private JsonElement getNoSearchResults() {
		JsonObject noResultJson = new JsonObject();
		noResultJson.addProperty("emptyIcon", emptyIcon);
		noResultJson.addProperty(CommonConstants.ALT_TEXT, emptyIconAltText);
		noResultJson.addProperty("tryNewSearch", tryNewSearchLabel);
		noResultJson.addProperty("tryCheckSpell", tryCheckSpell);
		noResultJson.addProperty("tryCheckSpellKeywords", tryCheckSpellKeywords);
		noResultJson.addProperty("specificResult", specificResult);
		noResultJson.addProperty("noListing", noListing);
		return noResultJson;
	}

	/**
	 * 
	 * @return
	 */
	private JsonElement getNoResults() {
		JsonObject noResultJson = new JsonObject();
		noResultJson.addProperty(CommonConstants.ICON, noResultsIcon);
		noResultJson.addProperty(CommonConstants.ALT_TEXT, noResultsAltText);
		noResultJson.addProperty(CommonConstants.HEADING, noResultsHeading);
		noResultJson.addProperty("subHeading", noResultsSubHeading);
		noResultJson.addProperty("clearFilters", noResultsClearFilters);
		return noResultJson;
	}

	/**
	 *  
	 * @return
	 */
	private JsonElement getPerPageResults() {
		JsonObject pageResult = new JsonObject();
		if(null != perPageResults) {
			pageResult.addProperty(CommonConstants.LABEL, perPageResults.concat(CommonConstants.SPACE).concat(CommonConstants.PRODUCTS_KEY));
			pageResult.addProperty(CommonConstants.VALUE, Integer.parseInt(perPageResults));
		}
		return pageResult;
	}


	/**
	 * 
	 * @return
	 */
	private JsonArray getDropdownList() {
		JsonArray dropdownList = new JsonArray();
		if(null != dropdownListOptions) {
		Iterator<Resource> listItems = dropdownListOptions.listChildren();
		dropdownList = getJsonFromValueMap(listItems,CommonConstants.OPTIONLABEL,CommonConstants.OPTIONVALUE,CommonConstants.DROPDOWN); // 
		}
		return dropdownList;
	}



	/**
	 *
	 * @param listItems
	 * @param value 
	 * @param label 
	 */
	public JsonArray getJsonFromValueMap(Iterator<Resource> listItems, String label, String value, String type) {
		JsonArray dropdownList = new JsonArray();
		while(listItems.hasNext()) {
			Resource item = listItems.next();
			JsonObject itemJson = new JsonObject();
			ValueMap vm = item.adaptTo(ValueMap.class);
			itemJson.addProperty(CommonConstants.LABEL,vm.get(label,String.class)!=null?vm.get(label,String.class):StringUtils.EMPTY);
			if(type.equals(CommonConstants.DROPDOWN)) {
				itemJson.addProperty(CommonConstants.VALUE,vm.get(value,String.class)!=null?Integer.parseInt(vm.get(value,String.class)):0);
			}else {
				itemJson.addProperty(CommonConstants.VALUE,vm.get(value,String.class)!=null?vm.get(value,String.class):StringUtils.EMPTY);
			}
			
			if( vm.get(CommonConstants.IS_RANGE_SELECTOR,Boolean.class)!=null && vm.get(CommonConstants.IS_RANGE_SELECTOR,Boolean.class).equals(true)){
				itemJson.addProperty(CommonConstants.IS_RANGE_SELECTOR, true);
				//add int, strings
				itemJson.addProperty(CommonConstants.UNITLABEL,vm.get(CommonConstants.UNITLABEL,String.class)!=null?vm.get(CommonConstants.UNITLABEL,String.class):StringUtils.EMPTY);
				itemJson.addProperty(CommonConstants.STEP,vm.get(CommonConstants.STEP,String.class)!=null?Integer.parseInt(vm.get(CommonConstants.STEP,String.class)):0);
				itemJson.addProperty(CommonConstants.MIN,vm.get(CommonConstants.MIN,String.class)!=null?Integer.parseInt(vm.get(CommonConstants.MIN,String.class)):0);
				itemJson.addProperty(CommonConstants.MAX,vm.get(CommonConstants.MAX,String.class)!=null?Integer.parseInt(vm.get(CommonConstants.MAX,String.class)):0);
				itemJson.addProperty(CommonConstants.TO_LABEL,vm.get(CommonConstants.TO_LABEL,String.class)!=null?vm.get(CommonConstants.TO_LABEL,String.class):StringUtils.EMPTY);
			}
			else {
				itemJson.addProperty(CommonConstants.IS_RANGE_SELECTOR, false);
			}
			
			dropdownList.add(itemJson);
		}
		return dropdownList;
	}
	
	private JsonArray getFacetsList() {
		JsonArray tempFacetList = new JsonArray();
		if(null != facetcategory) {
			tempFacetList = getJsonFromValueMap(facetcategory.listChildren(),CommonConstants.FACETLABEL,CommonConstants.FACETVALUE,CommonConstants.FACET);
			}
		return tempFacetList;
	}

	/**
	 * @return the searchResultsJsonLabels
	 */
	public String getSearchResultsJsonLabels() {
		return searchResultsJsonLabels;
	}

	/**
	 * @return the searchResultsJsonConfigs
	 */
	public String getSearchResultsJsonConfigs() {
		return searchResultsJsonConfigs;
	}

	public Boolean getIsInstrument() { return isInstrument; }

}
