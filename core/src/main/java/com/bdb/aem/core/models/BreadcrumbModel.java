package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The Class BreadcrumbModelImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class  }, adapters = { BreadcrumbModel.class }, resourceType = {
		BreadcrumbModel.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BreadcrumbModel {

	/** The Constant BREADCRUMB_TITLE. */
	public static final String BCRUMB_TITLE = "breadcrumbTitle";
	/** The Constant BREADCRUMB_PATH. */
	public static final String BREADCRUMB_PATH = "breadcrumbPath";

	/**
	 * The Constant RESOURCE_TYPE.
	 */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/breadcrumb/v1/breadcrumb";

	/**
	 * The logger.
	 */
	protected final Logger logger = LoggerFactory.getLogger(BreadcrumbModel.class);

	/** The resource resolver. */
	@SlingObject
	ResourceResolver resourceResolver;

	/**
	 * The current page.
	 */
	@Inject
	private Page currentPage;


	/** The Breadcrumb Level. */
	@Inject
	@Named("breadCrumbLevel")
	@Via("resource")
	private String breadCrumbLevel;

	/**
	 * The request.
	 */
	@Inject
	SlingHttpServletRequest request;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The product name. */
	private String pdpProductName;
	public String getPdpProductName() {
		return pdpProductName;
	}

	/** The breadcrumb data. */
	private String breadcrumbJson;
	/**
	 * @return the breadcrumbJson
	 */
	public String getBreadcrumbJson() {
		return breadcrumbJson;
	}

	List<Map<String, String>> breadCrumbList = new ArrayList<>();
	List<Map<String, String>> breadCrumbMobileList = new ArrayList<>();
	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("Entry into init of BreadcrumbModelImpl");
		if(null != request && null != request.getAttribute("productName")) {
			pdpProductName = request.getAttribute("productName").toString();
		}
		logger.debug("current page path {}" , currentPage.getPath());
		int count = null != breadCrumbLevel ? Integer.parseInt(breadCrumbLevel) : 3;
		if(currentPage != null && currentPage.getParent() != null) {
			Page pages=currentPage.getParent();
			Map<String, String> breadcrumbPropertiesMobileMap = getPropertiesMap(pages, resourceResolver);
			if (!breadcrumbPropertiesMobileMap.isEmpty()) {
				breadCrumbMobileList.add(breadcrumbPropertiesMobileMap);
			}
		}
		int position = 1;
		JsonObject parentItem = null;
		JsonObject itemJson = null;
		JsonArray breadcrumbJsonArray = new JsonArray();
		JsonObject homeBreadcrumbJson = new JsonObject();

		homeBreadcrumbJson = new JsonObject();
		itemJson = new JsonObject();
		itemJson.addProperty("name","Biosciences");
		itemJson.addProperty("@id","https://www.bdbiosciences.com/"+CommonHelper.getLanguage(currentPage)+"-"+CommonHelper.getCountry(currentPage).toLowerCase());
		homeBreadcrumbJson.add("item", itemJson);
		homeBreadcrumbJson.addProperty("@type", "ListItem");
		homeBreadcrumbJson.addProperty("position", position);
		breadcrumbJsonArray.add(homeBreadcrumbJson);

		while (currentPage != null && currentPage.getAbsoluteParent(count) != null
				&& count != currentPage.getDepth()) {
			position++;
			Page page = currentPage.getAbsoluteParent(count);
			if (null != page) {
				Map<String, String> breadcrumbPropertiesMap = getPropertiesMap(page, resourceResolver);
				if (!breadcrumbPropertiesMap.isEmpty()) {
					breadCrumbList.add(breadcrumbPropertiesMap);

					parentItem = new JsonObject();
					itemJson = new JsonObject();
					itemJson.addProperty("name",breadcrumbPropertiesMap.get(BCRUMB_TITLE));
					itemJson.addProperty("@id",breadcrumbPropertiesMap.get(BREADCRUMB_PATH));
					parentItem.add("item", itemJson);
					parentItem.addProperty("@type", "ListItem");
					parentItem.addProperty("position", position);
				}
			}
			count++;
			breadcrumbJsonArray.add(parentItem);
		}

		JsonObject finalJson = new JsonObject();
		finalJson.addProperty("@type","BreadcrumbList");
		finalJson.add("itemListElement",breadcrumbJsonArray);
		breadcrumbJson = finalJson.toString();
	}

    /**
     * Gets the breadcrumb map.
     *
     * @return the breadcrumb map
     */
    public List<Map<String, String>> getBreadcrumbMap() {
        return breadCrumbList;
    }
    public List<Map<String, String>> getBreadcrumbMobileMap() {
        return breadCrumbMobileList;
    }
    /**
     * Gets the properties map.
     *
     * @param page the page
     * @return properties of current page for breadcrumb
     */
    private Map<String, String> getPropertiesMap(Page page, ResourceResolver resourceResolver) {
        Map<String, String> map = getNewBreadCrumbMap();
        ValueMap properties = page.getProperties();
        if (!properties.containsKey("hideInBreadcrumb")) {
            String title = page.getTitle();
            String pageName = page.getName();
            if (StringUtils.isNotBlank(page.getNavigationTitle())) {
                title = page.getNavigationTitle();
            } else if (StringUtils.isNotBlank(page.getPageTitle())) {
                title = page.getPageTitle();
            }
            String pagePath = null != page.getPath() ? page.getPath() : "#";
            if (pagePath.contains("/home-page")) {
            	pagePath = pagePath.replace("/home-page", "");
            }
            map.put("pageName", pageName);
            map.put(BCRUMB_TITLE, title);
			if (pagePath.contains("/pdp")) {
				pagePath = request.getPathInfo();
				String pdpPath = pagePath.substring(pagePath.lastIndexOf('/') + 1);
				String path = pdpPath.replace("pdp.", "");
				String newPath = path.replace(".html", "");
				if (pdpPath.contains("pdp"))  {
					pagePath = pagePath.replace(pdpPath, newPath);
				}
				map.put(BCRUMB_TITLE, pdpProductName);
				map.put(BREADCRUMB_PATH, externalizerService.getFormattedUrl(pagePath, resourceResolver));
			}
			else {
				map.put(BREADCRUMB_PATH, externalizerService.getFormattedUrl(pagePath, resourceResolver));
			}
		}
		return map;
	}



	/**
	 * Gets the bread crumb map.
	 *
	 * @return new instance of breadcrumb map
	 */
	private HashMap<String, String> getNewBreadCrumbMap() {
		return new HashMap<>();
	}

	/**
	 * Get the Bread Crumb Level
	 * @return
	 */
	public String getBreadCrumbLevel() {
		return breadCrumbLevel;
	}
}