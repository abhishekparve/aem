package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;

/**
 * Model to set window Object for Search Box.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchBoxModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(SearchBoxModel.class);

	/** The search container labels. */
	String searchContainerLabels;

	/** The search container config. */
	String searchContainerConfig;
	
	/** The popular search config. */
	String popularSearchConfig;

	/** The externalizer. */
	@Inject
	ExternalizerService externalizer;
	
	/** The bdb search endpoint service. */
	@Inject
	BDBSearchEndpointService bdbSearchEndpointService;
	
	/** The in keyword. */
	@Inject
	@Via("resource")
	@Default(values = "in")
	private String inKeyword;
	
	/** The max results to display. */
	@Inject
	@Via("resource")
	@Default(values = "5")
	private String maxResultsToDisplay;
	
	/** The recent search to display. */
	@Inject
	@Via("resource")
	@Default(values = "Recent Searches")
	private String recentSearchTitle;
	
	/** The popular search title to display. */
	@Inject
	@Via("resource")
	@Default(values = "Popular Searches")
	private String popularSearchTitle;
	
	/** The Close alt Text to display. */
	@Inject
	@Via("resource")
	@Default(values = "Close Icon")
	private String closeAltText;
	
	/** The Suggestive Search title to display. */
	@Inject
	@Via("resource")
	@Default(values = "Suggestions")
	private String suggestiveSearchTitle;
	
	/** The Search Alt Text to display. */
	@Inject
	@Via("resource")
	@Default(values = "Search Icon")
	private String searchAltText;
	
	/** The Search Place holder to display. */
	@Inject
	@Via("resource")
	@Default(values = "Search BD Biosciences")
	private String searchPlaceholder;
	
	/** The Close Icon to display. */
	@Inject
	@Via("resource")
	@Default(values = " ")
	private String closeIcon;
	
	/** The Close Icon to display. */
	@Inject
	@Via("resource")
	@Default(values = "/content/dam/bdb/misc/icons/close-light.svg")
	private String closeIconDesktop;
	
	/** The Search Icon to display. */
	@Inject
	@Via("resource")
	@Default(values = " ")
	private String searchIcon;
	
	/** The  Search Icon mobile to display. */
	@Inject
	@Via("resource")
	@Default(values = " ")
	private String searchIconMobile;
	
	/** The no results to display. */
	@Inject
	@Via("resource")
	@Default(values = "No Results Found")
	private String noResult;

	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;
	
	@Inject
	private Page currentPage;

	/** The keyword page url. */
	private String keywordsDataPagePath;
	
	/**
	 * Init.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("SearchBoxModel - Init method started");
		
			keywordsDataPagePath = CommonHelper.getKeywordPagePath(currentPage);
			setSearchContainerLabels(resourceResolver);
			setSearchContainerConfig();
			setPopularSearchConfig();
	}

	/**
	 * Sets the search container labels.
	 */
	public void setSearchContainerLabels(ResourceResolver resourceResolver) {
		JsonObject searchContainerLabelJson = new JsonObject();
		searchContainerLabelJson.addProperty("recentSearchTitle", recentSearchTitle);
		searchContainerLabelJson.addProperty("popularSearchTitle", popularSearchTitle);
		searchContainerLabelJson.addProperty("suggestiveSearchTitle", suggestiveSearchTitle);
		searchContainerLabelJson.addProperty("closeAltText", closeAltText);
		searchContainerLabelJson.addProperty("searchAltText", searchAltText);
		searchContainerLabelJson.addProperty("searchPlaceholder", searchPlaceholder);
		searchContainerLabelJson.addProperty("closeIcon", closeIcon);
		searchContainerLabelJson.addProperty("closeIconDesktop", closeIconDesktop);
		searchContainerLabelJson.addProperty("searchIcon", searchIcon);
		searchContainerLabelJson.addProperty("searchIconMobile", searchIconMobile);
		searchContainerLabelJson.addProperty("noResult", noResult);
		searchContainerLabelJson.addProperty("inKeyword", " " + inKeyword + " ");
		searchContainerLabelJson.addProperty("maxResultsToDisplay", maxResultsToDisplay);
		if(null != CommonHelper.getLabel("searchResultPagePath", currentPage)) {
			String searchPageUrl = !CommonHelper.getLabel("searchResultPagePath", currentPage).isEmpty() ? CommonHelper.getLabel("searchResultPagePath", currentPage) : "/";
			String formattedUrl = searchPageUrl.equals("/") ? "/" : externalizer.getFormattedUrl(searchPageUrl, resourceResolver);
			searchContainerLabelJson.addProperty("searchPageUrl", formattedUrl + "?searchKey=");
		}
		searchContainerLabelJson.addProperty("keywordsDataPagePath", keywordsDataPagePath);
		
		searchContainerLabels = searchContainerLabelJson.toString();
	}

	/**
	 * Sets the search container config.
	 */
	public void setSearchContainerConfig() {
		JsonObject searchContainerConfigJson = new JsonObject();
		JsonObject requestPayloadJson = new JsonObject();
		requestPayloadJson.addProperty(CommonConstants.URL, bdbSearchEndpointService.getTypeAheadServletPath());
		requestPayloadJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);
		searchContainerConfigJson.add(CommonConstants.REQUEST_PAYOAD, requestPayloadJson);
		searchContainerConfig = searchContainerConfigJson.toString();
	}
	

	/**
	 * Sets the popular search config.
	 */
	public void setPopularSearchConfig() {
		JsonObject popularSearchConfigJson = new JsonObject();
		popularSearchConfigJson.addProperty(CommonConstants.URL, bdbSearchEndpointService.getPopularSearchedServletPath());
		popularSearchConfigJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);
		popularSearchConfig = popularSearchConfigJson.toString();
	}

	/**
	 * Gets the search container labels.
	 *
	 * @return the search container labels
	 */
	public String getSearchContainerLabels() {
		return searchContainerLabels;
	}

	/**
	 * Gets the search container config.
	 *
	 * @return the search container config
	 */
	public String getSearchContainerConfig() {
		return searchContainerConfig;
	}
	
	/**
	 * Gets the popular search config.
	 *
	 * @return the popular search config
	 */
	public String getPopularSearchConfig() {
		return popularSearchConfig;
	}

}
