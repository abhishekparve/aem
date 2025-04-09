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
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Sling Model to fetch values from dialog and product structure in AEM, and
 * return respective JSONs to the FE.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DynamicListComponentModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(DynamicListComponentModel.class);
	
	/** The reduceTopPadding */
	@Inject
	@Via("resource")
	@Default(values = "false")
	private String reduceTopPadding;
	
	/** The reduceBottomPadding */
	@Inject
	@Via("resource")
	@Default(values = "false")
	private String reduceBottomPadding;
	
	/** The backgroundColor */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String backgroundColor;
	
	/** The parentPagePath */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String parentPagePath;
	
	/** The viewType  */
	@Inject
	@Via("resource")
	@Default(values = "")
	public String tagBasedSearchOnly;
	
	/** The categoryTags */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String categoryTags;
	
	/** The viewType  */
	@Inject
	@Via("resource")
	@Default(values = "")
	public String includeChildrenPath;

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
	
	/** The includeCardThumbnail  */
	@Inject
	@Via("resource")
	@Default(values = "")
	public String includeCardThumbnail;
	
	/** The fallbackImage. */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String fallbackImage;
	
	/** The Cart Image */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String cartImage;
	
	/** The removeBorder */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String removeBorder;
	
	/** The disableFacets */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String disableFacets;
	
	/** The display date */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String displayDate;
	
	
	/** The Close Icon */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String closeIcon;
	
	/** The close Icon Label */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String closeIconText;
	
	/** The Cart Alt Text */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String cartAltText;
	
	/** The ctaLabel */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String ctaLabel;
	
	/** The facet catogory Options */
	@Inject
	@ChildResource
	private Resource facetcategory;
	
	/** The sorting Options */
	@Inject
	@ChildResource
	private Resource sortOptions;
	
	/** The viewOptions */
	@Inject
	@ChildResource
	private Resource viewOptions;
	
	/** The enableThumbnailAsFallback  */
	@Inject
	@Via("resource")
	@Default(values = "")
	public String disablePagination;
	
	/** The paginationOptions */
	@Inject
	@ChildResource
	private Resource paginationOptions;
	
	/** The filterCategory */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String filterCategory;
			
	/** The ctaStyle */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String ctaStyle;
	
	/** The showResults */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String showResults;
	
	/** The noResultsIcon */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String noResultsIcon;
	
	/** The noResults */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String noResults;
	
	/** The clearAll */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String clearAll;
	
	/** The show */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String show;
	
	/** The sortBy */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String sortBy;
	
	 @Self
	 private SlingHttpServletRequest request;
	
	@ScriptVariable
    private ResourceResolver resolver;
	
	/** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
	
	/** The Event/Blog JSON Label */
	private String cardListLabels;
	
	/** The Event/Blog JSON Config */
	private String cardListConfig;
	
	private String cardListTags[] ;
	
	private String topicsTag[] ;
	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("CardListComponentModel - Init method started");
		String cardListResourcePath = request.getResource().getPath();
		Resource resource = resolver.getResource(cardListResourcePath);
		ValueMap cardListValueMap = resource.getValueMap();
		cardListTags = cardListValueMap.get("categoryTags", String[].class);
		topicsTag = cardListValueMap.get("topicsRootTag", String[].class);
		cardListConfig = getConfigJson();
		cardListLabels = getLabelJson();
	}

	/**
	 * This method forms the json for cardlist config.
	 *
	 * @return the config json
	 */
	public String getConfigJson(){

		JsonObject cardListJsonConfig = new JsonObject();
		JsonObject requestPayloadJsonObject = new JsonObject();
		
		requestPayloadJsonObject.addProperty(CommonConstants.URL, "/content/bdb/paths/get-cards-data.json");
		requestPayloadJsonObject.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		cardListJsonConfig.add("getContent", requestPayloadJsonObject);
				
		return cardListJsonConfig.toString();
	}
	
	/**
	 * This method forms the json for cardlist labels.
	 *
	 * @return the label json
	 */
	public String getLabelJson(){
		JsonObject cardListJsonLabel = new JsonObject();
		
		// forming labels JSON  
		cardListJsonLabel.addProperty(CommonConstants.BACKGROUND_COLOR, backgroundColor);
		cardListJsonLabel.addProperty(CommonConstants.REDUCE_TOP_PADDING, reduceTopPadding);
		cardListJsonLabel.addProperty(CommonConstants.REDUCE_BOTTOM_PADDING, reduceBottomPadding);
		cardListJsonLabel.addProperty(CommonConstants.CTA_LABEL, ctaLabel);
		cardListJsonLabel.addProperty(CommonConstants.CTA_STYLE, ctaStyle);
		cardListJsonLabel.addProperty(CommonConstants.SHOW_RESULTS, showResults);
		cardListJsonLabel.addProperty(CommonConstants.NO_RESULTS_ICON, externalizerService.getFormattedUrl(noResultsIcon, resourceResolver));
		cardListJsonLabel.addProperty(CommonConstants.NO_RESULTS, noResults);
		cardListJsonLabel.addProperty(CommonConstants.UP_ARROW_IMAGE, externalizerService.getFormattedUrl(upArrowImage, resourceResolver));
		cardListJsonLabel.addProperty(CommonConstants.UP_ARROW_ALT_TEXT, upArrowAltText);
		cardListJsonLabel.addProperty(CommonConstants.DOWN_ARROW_IMAGE, externalizerService.getFormattedUrl(downArrowImage, resourceResolver));
		cardListJsonLabel.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT, downArrowAltText);
		cardListJsonLabel.addProperty(CommonConstants.DOWN_ARROW_MOBILE, downArrowMobile);
		cardListJsonLabel.addProperty(CommonConstants.CCLEAR_ALL, clearAll);
		cardListJsonLabel.addProperty(CommonConstants.SHOW_LABEL, show);
		cardListJsonLabel.addProperty(CommonConstants.PARENTPAGEPATH, parentPagePath);
		cardListJsonLabel.addProperty(CommonConstants.INCLUDE_CHILD_PAGES, includeChildrenPath);
		cardListJsonLabel.addProperty(CommonConstants.CLOSE_ICON, closeIcon);
		cardListJsonLabel.addProperty(CommonConstants.REMOVE_BORDER, removeBorder);
		cardListJsonLabel.addProperty(CommonConstants.DISABLE_FACETS, disableFacets);
		cardListJsonLabel.addProperty(CommonConstants.DISPLAY_DATE, displayDate);
		cardListJsonLabel.addProperty(CommonConstants.CLOSE_ICON_TEXT, closeIconText);
		cardListJsonLabel.addProperty(CommonConstants.SORT_BY, sortBy);
		cardListJsonLabel.addProperty(CommonConstants.DISABLE_PAGINATION, disablePagination);
		cardListJsonLabel.addProperty(CommonConstants.INCLUDE_CARD_THUMBNAIL, includeCardThumbnail);
		cardListJsonLabel.addProperty(CommonConstants.FALLBACK_IMAGE, externalizerService.getFormattedUrl(fallbackImage, resourceResolver));
		cardListJsonLabel.addProperty(CommonConstants.TAG_BASED_SEARCH, tagBasedSearchOnly);
		cardListJsonLabel.addProperty(CommonConstants.FILTER_CATEGORY, filterCategory);
		if(null != cardListTags && cardListTags.length > 0) {
			cardListJsonLabel.addProperty(CommonConstants.TAGS_LABEL, String.join(",", cardListTags));
		}
		if(null != topicsTag && topicsTag.length > 0) {
			cardListJsonLabel.addProperty(CommonConstants.TOPICS_TAG_LABEL, String.join(",", topicsTag));
		}
		cardListJsonLabel.add(CommonConstants.VIEW_OPTIONS, getViewOptionsList());
		cardListJsonLabel.add(CommonConstants.FACETS, getFacetsList()); 
		cardListJsonLabel.add(CommonConstants.SORT_OPTIONS, getSortindCategoryList()); 
		cardListJsonLabel.add(CommonConstants.PAGINATION_OPTIONS, getPaginationList()); 
		
		return cardListJsonLabel.toString();
	}
	
	/**
	 * 
	 * @return tempFacetList
	 */
	private JsonArray getFacetsList() {
		JsonArray tempFacetList = new JsonArray();
		if(null != facetcategory) {
			tempFacetList = getJsonFromValueMap(facetcategory.listChildren(),CommonConstants.FACETLABEL,CommonConstants.FACETVALUE, "");
		}
		return tempFacetList;
	}
	
	/**
	 * 
	 * @return viewOptionsArray
	 */
	private JsonArray getViewOptionsList() {
		JsonArray viewOptionsArray = new JsonArray();
		if(null != viewOptions) {
			viewOptionsArray = getJsonFromValueMap(viewOptions.listChildren(), CommonConstants.LABEL, CommonConstants.VALUE, "");
		}
		return viewOptionsArray;
	}

	
	/**
	 * 
	 * @return paginationArray
	 */
	private JsonArray getPaginationList() {
		JsonArray paginationArray = new JsonArray();
		if(null != paginationOptions) {
			paginationArray = getJsonFromValueMap(paginationOptions.listChildren() ,CommonConstants.LABEL, CommonConstants.VALUE, "");
		}
		return paginationArray;
	}

	/**
	 * 
	 * @return sortOptionsArray
	 */
	private JsonArray getSortindCategoryList() {
		JsonArray sortOptionsArray = new JsonArray();
		if(null != sortOptions) {
			sortOptionsArray = getJsonFromValueMap(sortOptions.listChildren(), CommonConstants.LABEL, CommonConstants.ID, "sortOptions"); 
		}
		return sortOptionsArray;
	}
	
	/**
	 * 
	 * @param listItems
	 * @param label
	 * @param value
	 * @param type
	 * @return
	 */
	public JsonArray getJsonFromValueMap(Iterator<Resource> listItems, String label, String value, String type) {
		JsonArray childNodeArray = new JsonArray();
		while(listItems.hasNext()) {
			Resource item = listItems.next();
			JsonObject itemJson = new JsonObject();
			ValueMap vm = item.adaptTo(ValueMap.class);
			itemJson.addProperty(CommonConstants.LABEL,vm.get(label,String.class)!=null?vm.get(label,String.class):StringUtils.EMPTY);
			if(StringUtils.equals(type, "sortOptions")) {
				itemJson.addProperty(CommonConstants.ID,vm.get(value,String.class)!=null?vm.get(value,String.class):StringUtils.EMPTY);
			} else {
				itemJson.addProperty(CommonConstants.VALUE,vm.get(value,String.class)!=null?vm.get(value,String.class):StringUtils.EMPTY);
			}
			childNodeArray.add(itemJson);
		}
		return childNodeArray;
	}
	/**
	 * @return the cardListLabels
	 */
	public String getCardListLabels() {
		return cardListLabels;
	}

	/**
	 * @return the cardListConfig
	 */
	public String getCardListConfig() {
		return cardListConfig;
	}
}
