package com.bdb.aem.core.models;

import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Sling Model to fetch values from dialog and product structure in AEM, and
 * return respective JSONs to the FE.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TilesComponentModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(TilesComponentModel.class);
	
	/** The Clear All  */
	@Inject
	@Via("resource")
	@Default(values = "")
	public String clearAll;
	
	/** The Clear All  */
	@Inject
	@Via("resource")
	@Default(values = "")
	public String pageType;

	/** The Filter By Category */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String filterbyCategory;

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

	/** The Upcoming Label */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String upcomingLabel;
	
	/** The Current Label */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String currentLabel; 

	/** The Past label */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String pastLabel;
	
	/** The View Less Label */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String viewLessLabel;
	
	/** The View More Label */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String viewMoreLabel;
	
	/** The Cart Image */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String cartImage;
	
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
	
	/** The parentPagePath */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String parentPagePath;
	
	/** The ctaLabel */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String ctaLabel;
	
	/** The deactivationDate */
	@Inject
	@Via("resource")
	@Default(values = "")
	private String deactivationDate;
	
	@Inject
	@Via("resource")
	@Default(values = "6")
	private int facetCount;
	
	/** The facet catogory Options */
	@Inject
	@ChildResource
	private Resource facetcategory;
	
	/** The Current page */
	@Inject
	Page currentPage;
	
	/** The BDB API endpoint service */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;
	
	/** The Event/Blog JSON Label */
	private String eventBlogJsonLabels;
	
	/** The Event/Blog JSON Config */
	private String eventBlogJsonConfig;
	
	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("SearchResultsModel - Init method started");
		  
			eventBlogJsonConfig = getConfigJson();
			eventBlogJsonLabels = getLabelJson();
	}
	


	/**
	 * This method forms the json for blog/event config.
	 *
	 * @return the config json
	 */
	public String getConfigJson(){
		JsonObject eventBlogJsonConfig = new JsonObject();
		JsonObject requestPayloadJsonObject = new JsonObject();
		
		
		requestPayloadJsonObject.addProperty(CommonConstants.URL, "/content/bdb/paths/get-tiles-data.json");
		requestPayloadJsonObject.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		eventBlogJsonConfig.add("getAllEvents", requestPayloadJsonObject);
				
		return eventBlogJsonConfig.toString();  
	}
	
	/**
	 * This method forms the json for blog/event labels.
	 *
	 * @return the label json
	 */
	public String getLabelJson(){
		JsonObject blogEventJsonLabel = new JsonObject();
		JsonObject statusJsonLabel = new JsonObject();
		  
		// forming labels JSON  
		blogEventJsonLabel.addProperty("type", pageType);
		blogEventJsonLabel.addProperty("cclearAll", clearAll);
		blogEventJsonLabel.addProperty("filterCategory", filterbyCategory);
		blogEventJsonLabel.addProperty(CommonConstants.UP_ARROW_IMAGE, upArrowImage);
		blogEventJsonLabel.addProperty(CommonConstants.UP_ARROW_ALT_TEXT, upArrowAltText);
		blogEventJsonLabel.addProperty(CommonConstants.DOWN_ARROW_IMAGE, downArrowImage);
		blogEventJsonLabel.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT, downArrowAltText);
		blogEventJsonLabel.addProperty("downArrowMobile", downArrowMobile);
		blogEventJsonLabel.addProperty("viewMore", viewMoreLabel);
		blogEventJsonLabel.addProperty("viewLess", viewLessLabel);
		blogEventJsonLabel.addProperty("parentPagePath", parentPagePath);
		blogEventJsonLabel.addProperty("ctaLabel",ctaLabel);
		blogEventJsonLabel.addProperty("deactivationDate",deactivationDate);
		statusJsonLabel.addProperty("upcoming", upcomingLabel);
		statusJsonLabel.addProperty("current", currentLabel);
		statusJsonLabel.addProperty("past", pastLabel);
		blogEventJsonLabel.addProperty("defaultFacetsCount", facetCount);
		
		
		blogEventJsonLabel.addProperty(CommonConstants.ADD_TO_CART_LABEL, CommonHelper.getLabel(CommonConstants.ADD_TO_CART_LABEL,currentPage));
		blogEventJsonLabel.addProperty(CommonConstants.SAVE_TO_SHOPPING_LIST, CommonHelper.getLabel(CommonConstants.SAVE_TO_SHOPPING_LIST_KEY,currentPage));
		blogEventJsonLabel.addProperty(CommonConstants.CART_IMAGE, cartImage);
		blogEventJsonLabel.addProperty(CommonConstants.CLOSE_ICON, closeIcon);
		blogEventJsonLabel.addProperty(CommonConstants.CLOSE_ICON_TEXT, closeIconText);
		blogEventJsonLabel.addProperty(CommonConstants.CART_ALT_TEXT, cartAltText);
		
		
		
		blogEventJsonLabel.add("status", statusJsonLabel);
		blogEventJsonLabel.add("facets", getFacetsList()); 
		return blogEventJsonLabel.toString();
	}
	
	
	/**
	 * @param dropdownList2 
	 * @param dropdownList
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
	 * @return the eventBlogJsonLabels
	 */
	public String getEventBlogJsonLabels() {
		return eventBlogJsonLabels;
	}

	/**
	 * @return the eventBlogJsonConfig
	 */
	public String getEventBlogJsonConfig() {
		return eventBlogJsonConfig;
	}

	
}
