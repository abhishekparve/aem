package com.bdb.aem.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.EventBlogModel;
import com.bdb.aem.core.models.EventDetailsModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Servlet TilesDataServlet.
 */
@Component(service = Servlet.class, immediate = true, property = {
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + TilesDataServlet.RESOURCE_TYPE,
        ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON,
        ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST })

public class TilesDataServlet extends SlingAllMethodsServlet {
    private static final long serialVersionUID = 1L;
    public static final String ROOT = "root";

    transient Logger logger = LoggerFactory.getLogger(TilesDataServlet.class);

	public static final String RESOURCE_TYPE = "bdb/get-tiles-data";

    @Reference
    transient BDBSearchEndpointService solrConfig;

    @Reference
    transient SolrSearchService solrSearchService;

    @Reference
    transient ExternalizerService externalizerService;

    @Reference
    transient ResourceResolverFactory resourceResolverFactory;

    @Reference
    transient BDBApiEndpointService bdbApiEndpointService;





    String type = StringUtils.EMPTY;
    String ctaLabel = StringUtils.EMPTY;
    String deactivationDate = StringUtils.EMPTY;
	Date currentDate = new Date();
	transient EventDetailsModel eventDetailsModel = new EventDetailsModel();
	
	public static final String EVENT_BLOG_CATEGORY_TAGS = "eventBlogCategoryTags";
	public static final String PATH_BASED_SEARCH_ONLY = "pathBasedSearchOnly";
	public static final String TAG_BASED_SEARCH_ONLY = "tagBasedSearchOnly";
	public static final String TILES_COMPONENT_RESOURCE_TYPE = "bdb-aem/proxy/components/content/tilescomponent";
	public static final String EVENT_BLOG_DETAILS_RESOURCE_TYPE = "bdb-aem/proxy/components/content/eventblogDetails";
	
    /**
     * doPost method which gets the events details, facets and sends in response
     *
     * @param   request param
     * @param response response param
     * @throws IOException
     */
    @Override
    protected void doPost(final SlingHttpServletRequest request,
                         final SlingHttpServletResponse response) throws IOException {
    	ResourceResolver resourceResolver = null;
    	String apiResponse = null;
    	JsonObject finalResponseJson = new JsonObject();

    	  //4 json arrays
    	     JsonArray eventsArray = new JsonArray();
    	     JsonArray topicsArray = new JsonArray();
    	     JsonArray dateArray = new JsonArray();
    	     JsonArray locationArray = new JsonArray();
    	     JsonArray cardsData = new JsonArray();
    	     List<String> eventBlogList = null;

            try {
            	resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
            	String jsonString = getRequestJsonString(request);
            	JsonArray facetsArray = getrequestFacetJson(jsonString);

            	JsonObject facetJson = new JsonParser().parse(jsonString).getAsJsonObject();

            	String parentPagePath = facetJson.get(CommonConstants.PARENTPAGEPATH)!= null?facetJson.get(CommonConstants.PARENTPAGEPATH).getAsString():StringUtils.EMPTY;
            	type = facetJson.get(CommonConstants.TYPE)!= null?facetJson.get(CommonConstants.TYPE).getAsString():StringUtils.EMPTY;
            	ctaLabel = facetJson.get(CommonConstants.CTA_LABEL)!= null?facetJson.get(CommonConstants.CTA_LABEL).getAsString():StringUtils.EMPTY;
            	deactivationDate = facetJson.get(CommonConstants.DEACTIVATION_DATE)!= null?facetJson.get(CommonConstants.DEACTIVATION_DATE).getAsString():StringUtils.EMPTY;
            	
            	Resource parentResource = resourceResolver.getResource(parentPagePath);
            	Resource jcrContentResource = parentResource.getChild(JcrConstants.JCR_CONTENT);
            	String[] tagList = null;
            	ValueMap parentPageValueMap = null;
            	if(null != jcrContentResource) {
            		Resource rootRes = jcrContentResource.getChild(ROOT);
                    if (null != rootRes) {
                        Iterator<Resource> rootresList = rootRes.listChildren();
                        while (rootresList.hasNext()) {
                            Resource rootResItem = rootresList.next();
                            if (rootResItem.getResourceType().equals(TILES_COMPONENT_RESOURCE_TYPE)) {
                            	parentPageValueMap = rootResItem.getValueMap();
                            	tagList = parentPageValueMap.containsKey(EVENT_BLOG_CATEGORY_TAGS) ? parentPageValueMap.get(EVENT_BLOG_CATEGORY_TAGS, String[].class) : tagList;
                            }
                        }
                    }
            	}
            	
            	PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
    	    	Page rootPage = pageManager.getPage(parentPagePath);
    	    	if(null != parentPageValueMap && null != rootPage) {
    	    		String pathBaseSearchOnly = parentPageValueMap.containsKey(PATH_BASED_SEARCH_ONLY) ? parentPageValueMap.get(PATH_BASED_SEARCH_ONLY).toString() : "";
    	    		String tagBaseSearchOnly = parentPageValueMap.containsKey(TAG_BASED_SEARCH_ONLY) ? parentPageValueMap.get(TAG_BASED_SEARCH_ONLY).toString() : "";
    	    		if(pathBaseSearchOnly.equalsIgnoreCase(CommonConstants.TRUE)  && tagBaseSearchOnly.equalsIgnoreCase(CommonConstants.TRUE)) {
                		if(null != tagList && tagList.length > 0) {
                			tagBasedEventsAndBlogsSearch(resourceResolver,tagList,rootPage,eventsArray, topicsArray, dateArray, locationArray, eventBlogList, cardsData, true);
    	  				}
                	} else if(tagBaseSearchOnly.equalsIgnoreCase(CommonConstants.TRUE)) {
    	  				if(null != tagList && tagList.length > 0) {
    	  					tagBasedEventsAndBlogsSearch(resourceResolver,tagList,rootPage,eventsArray, topicsArray, dateArray, locationArray, eventBlogList, cardsData, true);
    	  				}
    	  			} else if(pathBaseSearchOnly.equalsIgnoreCase(CommonConstants.TRUE)) {
    	  				pathBasedEventsAndBlogsSearch(resourceResolver, rootPage, eventsArray, topicsArray, dateArray, locationArray, eventBlogList, cardsData, false, 0);
    	  			} else {
    	  				pathBasedEventsAndBlogsSearch(resourceResolver, rootPage, eventsArray, topicsArray, dateArray, locationArray, eventBlogList, cardsData, false, 0);
    	  			}
    	    	}
	  			
            	finalResponseJson.add("facetsConfig", constructingFacetJsons(facetsArray,eventsArray,topicsArray,dateArray,locationArray));
            	finalResponseJson.add("cardsData", cardsData);

	            apiResponse = finalResponseJson.toString();

            }catch (LoginException | ParseException e) {
				apiResponse = e.getMessage();
            	response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            	logger.error("Exception Occurred during execution {}", e);
			}finally {
				response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(apiResponse);
			}

    }
    
    /**
     * 
     * @param resourceResolver
     * @param tagList
     * @param rootPage
     * @param eventsArray
     * @param topicsArray
     * @param dateArray
     * @param locationArray
     * @param eventBlogList
     * @param cardsData
     * @param tagBasedSearch
     * @throws ParseException
     */
    private void tagBasedEventsAndBlogsSearch(ResourceResolver resourceResolver, String[] tagList, Page rootPage, JsonArray eventsArray, JsonArray topicsArray, JsonArray dateArray, JsonArray locationArray, List<String> eventBlogList, JsonArray cardsData, Boolean tagBasedSearch) throws ParseException {
    	int tagCount = 0;
    	eventBlogList = new ArrayList<String>();
    	for (String tagPath : tagList) {
	    	Iterator<Page> rootPageIterator = rootPage.listChildren(null, true);
	    	while(rootPageIterator.hasNext()) {
	    		Page childPage =    rootPageIterator.next();
	    		ValueMap childPageValueMap = childPage.getProperties();
	    		String[] eventBlogCategoryTags = childPageValueMap.get(CommonConstants.CQ_TAGS, String[].class);
	    		Resource childResource = resourceResolver.getResource(childPage.getPath());
	    		if(null != childResource) {
	    			if(childResource.getValueMap().get(CommonConstants.JCR_PRIMARY_TYPE).toString().equals(CommonConstants.CQ_PAGE)) {
        	    		if (null != eventBlogCategoryTags && eventBlogCategoryTags.length > 0) {
        	    			if(Arrays.stream(eventBlogCategoryTags).anyMatch(tagPath::equals)) {
        	    				searchForValidEventsAndBlogs(resourceResolver, eventsArray, topicsArray, dateArray, locationArray, cardsData, childResource, tagBasedSearch, tagCount, eventBlogList);
		                    }
        	    		}
	    			}
	    		}
	    	}
	    	tagCount ++;
		}
	}

   /**
    * 
    * @param resourceResolver
    * @param rootPage
    * @param eventsArray
    * @param topicsArray
    * @param dateArray
    * @param locationArray
    * @param eventBlogList
    * @param cardsData
    * @param pathBasedSearch
    * @param tagCount
    * @throws ParseException
    */
	private void pathBasedEventsAndBlogsSearch(ResourceResolver resourceResolver, Page rootPage, JsonArray eventsArray, JsonArray topicsArray, JsonArray dateArray, JsonArray locationArray, List<String> eventBlogList, JsonArray cardsData, Boolean pathBasedSearch, int tagCount) throws ParseException {
		Iterator<Page> rootPageIterator = rootPage.listChildren(null, true);
		while(rootPageIterator.hasNext()) {
    		Page childPage =    rootPageIterator.next();
    		Resource childResource = resourceResolver.getResource(childPage.getPath());
    	    if(childResource.getValueMap().get(CommonConstants.JCR_PRIMARY_TYPE).toString().equals(CommonConstants.CQ_PAGE)) {
                Resource jcrContentResource = childResource.getChild(JcrConstants.JCR_CONTENT);
                if (jcrContentResource!=null) {
                	searchForValidEventsAndBlogs(resourceResolver, eventsArray, topicsArray, dateArray, locationArray, cardsData, childResource, pathBasedSearch, tagCount, eventBlogList);
                }
            }
    	}
    }
	
	/**
	 * 
	 * @param resourceResolver
	 * @param eventsArray
	 * @param topicsArray
	 * @param dateArray
	 * @param locationArray
	 * @param cardsData
	 * @param childResource
	 * @param tagBasedSearch
	 * @param tagCount
	 * @param eventBlogList
	 * @throws ParseException
	 */
	private void searchForValidEventsAndBlogs(ResourceResolver resourceResolver, JsonArray eventsArray, JsonArray topicsArray, JsonArray dateArray, JsonArray locationArray, JsonArray cardsData, Resource childResource, Boolean tagBasedSearch, int tagCount, List<String> eventBlogList) throws ParseException {
		Resource jcrContentResource = childResource.getChild(JcrConstants.JCR_CONTENT);
		Resource rootRes = jcrContentResource.getChild(ROOT);
        if (null != rootRes) {
            Iterator<Resource> rootresList = rootRes.listChildren();
            while (rootresList.hasNext()) {
                Resource rootResItem = rootresList.next();
                if (rootResItem.getResourceType().equals(EVENT_BLOG_DETAILS_RESOURCE_TYPE)) {
                    JsonObject cardJsonObject = gettingDataFromEventBlog(rootResItem, childResource,
                            resourceResolver, eventsArray, topicsArray, dateArray, locationArray);
                    if (cardJsonObject.size() > 0) {
                    	if(tagBasedSearch) {
                    		if(tagCount == 0) {
                    			cardsData.add(cardJsonObject);
                    		} else {
                        		if(!eventBlogList.contains(cardJsonObject.get("urlcta").toString())) {
                            		cardsData.add(cardJsonObject);
                        		}
                    		}
                    		eventBlogList.add(cardJsonObject.get("urlcta").toString());
                    	} else {
                    		cardsData.add(cardJsonObject);
                    	}
                    }
                    break;
                }

            }
        }
	}


    /**
     * adding children to the main category facets
     * @param facetsArray
     * @param eventsArray
     * @param topicsArray
     * @param dateArray
     * @param locationArray
     * @return
     */
    private JsonArray constructingFacetJsons(JsonArray facetsArray,JsonArray eventsArray,JsonArray topicsArray,JsonArray dateArray, JsonArray locationArray) {

    	JsonArray responseFacets = new JsonArray();


    	for(int i=0; i<facetsArray.size();i++) {

    		if(StringUtils.equals(CommonConstants.EVENTS, facetsArray.get(i).getAsJsonObject().get(CommonConstants.VALUE).getAsString())) {

    			responseFacets.add(facetJsonItem(facetsArray, i,eventsArray));

    		}else if(StringUtils.equals(CommonConstants.TOPICS, facetsArray.get(i).getAsJsonObject().get(CommonConstants.VALUE).getAsString())) {

    			responseFacets.add(facetJsonItem(facetsArray, i,topicsArray));

    		}else if(StringUtils.equals(CommonConstants.DATES, facetsArray.get(i).getAsJsonObject().get(CommonConstants.VALUE).getAsString())) {

    			responseFacets.add(facetJsonItem(facetsArray, i,dateArray));

    		}else if(StringUtils.equals("locations", facetsArray.get(i).getAsJsonObject().get(CommonConstants.VALUE).getAsString())) {

    			responseFacets.add(facetJsonItem(facetsArray, i,locationArray));
    		}
    	}

    	return responseFacets;
	}



	/**
	 * forming the facets json
	 *
	 * @param facetsArray
	 * @param i
	 * @param jsonArray
	 * @return
	 */
	public JsonObject facetJsonItem(JsonArray facetsArray, int i, JsonArray jsonArray) {

		JsonObject facetObj = new JsonObject();

		facetObj.addProperty(CommonConstants.NAME, facetsArray.get(i).getAsJsonObject().get(CommonConstants.LABEL).getAsString());
		facetObj.addProperty(CommonConstants.CODE, facetsArray.get(i).getAsJsonObject().get(CommonConstants.VALUE).getAsString());
		facetObj.addProperty(CommonConstants.CODE_TARGET, CommonConstants.HASH.concat(facetsArray.get(i).getAsJsonObject().get(CommonConstants.VALUE).getAsString()));
		facetObj.add(CommonConstants.CHILDREN, jsonArray);

		return facetObj;
	}

	private JsonObject gettingDataFromEventBlog(Resource rootResItem, Resource tile, ResourceResolver resourceResolver,JsonArray eventsArray,JsonArray topicsArray,JsonArray dateArray, JsonArray locationArray) throws ParseException {

    	JsonObject cardsJson = new JsonObject();
    	EventBlogModel eventBlogModel = rootResItem.adaptTo(EventBlogModel.class);
    	String eventTopicTitle;

    	if(null != eventBlogModel && dateValidity(eventBlogModel,deactivationDate)) {

    		if(type.equals(CommonConstants.EVENT) && eventBlogModel.getSelection().equals(CommonConstants.EVENT)) {
    			cardsJson.addProperty("topicType", eventBlogModel.getEventTopic().replace(" ", "_"));
//    			cardsJson.addProperty("eventType", eventBlogModel.getEventType().replace(" ", "_"));
    			cardsJson.addProperty("eventType", eventBlogModel.getEventType());

    			cardsJson.addProperty("urlimage", eventBlogModel.getBannerThumbnailImage());
        		cardsJson.addProperty("altText", eventBlogModel.getBannerImageAlt());
        		cardsJson.addProperty("heading", eventBlogModel.getBannerTitle());
        		cardsJson.addProperty("subHeading", eventBlogModel.getSubHeading());

        		cardsJson.addProperty(CommonConstants.DATE_TYPE, dateFormating(eventBlogModel.getEventStartDate(),CommonConstants.DATE_TYPE));
        		cardsJson.addProperty(CommonConstants.SCHEDULE_ON, dateFormating(eventBlogModel.getEventStartDate(),CommonConstants.SCHEDULE_ON));

        		String startDateMonth = dateFormating(eventBlogModel.getEventStartDate(),"month");
        		String endDateMonth = dateFormating(eventBlogModel.getEventEndDate(),"month");
        		String startDate = dateFormating(eventBlogModel.getEventStartDate(),"date");
        		String endDate = dateFormating(eventBlogModel.getEventEndDate(),"date");
        		if(startDateMonth.equals(endDateMonth) && startDate.equals(endDate)) {
        			cardsJson.addProperty("scheduledTo", "");
        		}else
        			cardsJson.addProperty("scheduledTo", dateFormating(eventBlogModel.getEventEndDate(),CommonConstants.SCHEDULE_ON));

        		cardsJson.addProperty("scheduleType", settingScheduleType(eventBlogModel));

        		cardsJson.addProperty("labelcta", ctaLabel);
        		cardsJson.addProperty("urlcta", externalizerService.getFormattedUrl(tile.getPath(), resourceResolver));
        		cardsJson.addProperty("locationType", eventBlogModel.getEventLocation());

        		if(!eventBlogModel.getEventTopic().isEmpty()) {
        			Resource genericListResource = resourceResolver.getResource(bdbApiEndpointService.eventTopicDropdownEndpoint());
					if(null != genericListResource) {
						eventTopicTitle = CommonHelper.getGenericListTitle(eventBlogModel.getEventTopic(), genericListResource, resourceResolver);
					} else {
						eventTopicTitle = eventBlogModel.getEventTopic();
					}
        			settingTopicJson(eventTopicTitle,eventBlogModel.getEventTopic().replace(" ", "_"),topicsArray);
        		}

        		settingEventJson(eventBlogModel.getEventType(),eventsArray);
        		settingLocationJson(eventBlogModel.getEventLocation(),locationArray);
        		settingDateJson(dateFormating(eventBlogModel.getEventStartDate(),CommonConstants.MONTH_YEAR),dateFormating(eventBlogModel.getEventStartDate(),CommonConstants.DATE_TYPE),dateArray);

    		}else if(type.equals("blog") && eventBlogModel.getSelection().equals("blog")) {

    			cardsJson.addProperty("topicType", eventBlogModel.getBlogTopic1().replace(" ", "_"));
    			cardsJson.addProperty("topicTypeTwo", eventBlogModel.getBlogTopic2().replace(" ", "_"));
    			cardsJson.addProperty("topicTypeThree", eventBlogModel.getBlogTopic3().replace(" ", "_"));

    			cardsJson.addProperty("urlimage", eventBlogModel.getBannerThumbnailImageBlog());
        		cardsJson.addProperty("altText", eventBlogModel.getBannerImageAltBlog());
        		cardsJson.addProperty("heading", eventBlogModel.getBannerTitleBlog());
        		cardsJson.addProperty("subHeading", eventBlogModel.getBannerSubTitle());

        		cardsJson.addProperty(CommonConstants.DATE_TYPE, dateFormating(eventBlogModel.getBlogDate(),CommonConstants.DATE_TYPE));
        		cardsJson.addProperty(CommonConstants.SCHEDULE_ON, dateFormating(eventBlogModel.getBlogDate(),CommonConstants.SCHEDULE_ON));
        		cardsJson.addProperty("scheduleType", settingScheduleType(eventBlogModel));
        		cardsJson.addProperty("labelcta", ctaLabel);
        		cardsJson.addProperty("urlcta", externalizerService.getFormattedUrl(tile.getPath(), resourceResolver));

        		if(!eventBlogModel.getBlogTopic1().isEmpty()) {
        			settingTopicJson(eventBlogModel.getBlogTopic1(),eventBlogModel.getBlogTopic1().replace(" ", "_"),topicsArray);
        		}
        		if(!eventBlogModel.getBlogTopic2().isEmpty()) {
        			settingTopicJson(eventBlogModel.getBlogTopic2(),eventBlogModel.getBlogTopic2().replace(" ", "_"),topicsArray);
        		}
        		if(!eventBlogModel.getBlogTopic3().isEmpty()) {
        			settingTopicJson(eventBlogModel.getBlogTopic3(),eventBlogModel.getBlogTopic3().replace(" ", "_"),topicsArray);
        		}

    			settingDateJson(dateFormating(eventBlogModel.getBlogDate(),CommonConstants.MONTH_YEAR),dateFormating(eventBlogModel.getBlogDate(),CommonConstants.DATE_TYPE),dateArray);

    		}



    	}
    	return cardsJson;
	}




	/**
	 * checking if event date is after the deactivation date
	 * @param eventBlogModel
	 * @param deactivationDate
	 * @return
	 */
	private boolean dateValidity(EventBlogModel eventBlogModel, String deactivationDate) {

		boolean flag = true;
		if (type.equals(CommonConstants.EVENT)) {
			String eventDate = eventBlogModel.getEventStartDate();
			flag = assigningFlagValue(deactivationDate, flag, eventDate);
		}else {
			String eventDate = eventBlogModel.getBlogDate();
			flag = assigningFlagValue(deactivationDate, flag, eventDate);
		}
		return flag;
	}



	/**
	 * Assigning flag value
	 *
	 * @param deactivationDate
	 * @param flag
	 * @param eventDate
	 * @return
	 */
	public boolean assigningFlagValue(String deactivationDate, boolean flag, String eventDate) {
		if (StringUtils.isNotBlank(deactivationDate) && StringUtils.isNotBlank(eventDate)
				&& eventDetailsModel.getStringDateInDateFormat(eventDate)
						.before(eventDetailsModel.getStringDateInDateFormat(deactivationDate))) {
			flag = false;
		}
		return flag;
	}


	/**
	 * adding topic to topic facets json
	 * @param topics
	 * @param topicCode
	 * @param topicsArray
	 */
	private void settingTopicJson(String topics,String topicCode,JsonArray topicsArray) {

		JsonObject topicsJson = new JsonObject();

		boolean flag = true;
		for(int i=0;i<topicsArray.size();i++) {
			if(topicCode.equals(topicsArray.get(i).getAsJsonObject().get(CommonConstants.CODE).getAsString())){
				topicsArray.get(i).getAsJsonObject().addProperty(CommonConstants.COUNT, topicsArray.get(i).getAsJsonObject().get(CommonConstants.COUNT).getAsInt()+1);
				flag = false;
			}
		}
		if(flag) {
			topicsJson.addProperty(CommonConstants.NAME, topics);
			topicsJson.addProperty(CommonConstants.CODE, topicCode);
			topicsJson.addProperty(CommonConstants.IS_SELECTED, false);
			topicsJson.addProperty(CommonConstants.COUNT, 1);
		}
		if(topicsJson.size()>0) {
		topicsArray.add(topicsJson);
		}

	}

	/**
	 *
	 * Adding event type to facets json
	 * @param eventType
	 * @param eventsArray
	 */
	private void settingEventJson(String eventType,JsonArray eventsArray) {
		JsonObject eventsJson = new JsonObject();

		if(StringUtils.isNotBlank(eventType)) {
			boolean flag = true;
			for(int i=0;i<eventsArray.size();i++) {
				if(eventType.equals(eventsArray.get(i).getAsJsonObject().get(CommonConstants.CODE).getAsString())){
					eventsArray.get(i).getAsJsonObject().addProperty(CommonConstants.COUNT, eventsArray.get(i).getAsJsonObject().get(CommonConstants.COUNT).getAsInt()+1);
					flag = false;
				}
			}
			if(flag) {
				eventsJson.addProperty(CommonConstants.NAME, eventType);
				eventsJson.addProperty(CommonConstants.CODE, eventType);
				eventsJson.addProperty(CommonConstants.IS_SELECTED, false);
				eventsJson.addProperty(CommonConstants.COUNT, 1);
			}
			if(eventsJson.size()>0) {
				eventsArray.add(eventsJson);
			}
		}
	}

	/**
	 * Adding Location to facets json
	 *
	 * @param eventLocation
	 * @param locationArray
	 */
	private void settingLocationJson(String eventLocation, JsonArray locationArray) {
		JsonObject locationJson = new JsonObject();

		if(StringUtils.isNotBlank(eventLocation)) {
			boolean flag = true;
			for(int i=0;i<locationArray.size();i++) {
				if(eventLocation.equals(locationArray.get(i).getAsJsonObject().get(CommonConstants.CODE).getAsString())){
					locationArray.get(i).getAsJsonObject().addProperty(CommonConstants.COUNT, locationArray.get(i).getAsJsonObject().get(CommonConstants.COUNT).getAsInt()+1);
					flag = false;
				}
			}
			if(flag) {
				locationJson.addProperty(CommonConstants.NAME, eventLocation);
				locationJson.addProperty(CommonConstants.CODE, eventLocation);
				locationJson.addProperty(CommonConstants.IS_SELECTED, false);
				locationJson.addProperty(CommonConstants.COUNT, 1);
			}
			if(locationJson.size()>0) {
				locationArray.add(locationJson);
			}
		}

	}

	/**
	 *
	 * setting schedule type attribute in cards json
	 *
	 * @param eventBlogModel
	 * @return
	 */
	private String settingScheduleType(EventBlogModel eventBlogModel) {

		String scheduleType = StringUtils.EMPTY;

		if (type.equals(CommonConstants.EVENT)) {
			String eventStartDate = eventBlogModel.getEventStartDate();
			String eventEndDate = eventBlogModel.getEventEndDate();

			if (null != eventStartDate && null != eventEndDate) {
				scheduleType = checkingDates(eventStartDate, eventEndDate);
			}
		} else {
			String eventStartDate = eventBlogModel.getBlogDate();

			if (null != eventStartDate) {
				scheduleType = checkingDates(eventStartDate, eventStartDate);
			}

		}

		return scheduleType;
	}



	/**
	 * checking the event dates with current date
	 *
	 * @param eventStartDate
	 * @param eventEndDate
	 * @return
	 */
	public String checkingDates(String eventStartDate, String eventEndDate) {
		String scheduleType;
		if (eventDetailsModel.getStringDateInDateFormat(eventStartDate) != null
				&& currentDate.before(eventDetailsModel.getStringDateInDateFormat(eventStartDate))) {
			scheduleType = CommonConstants.UPCOMING;
		} else if (eventDetailsModel.getStringDateInDateFormat(eventEndDate) != null
				&& currentDate.after(eventDetailsModel.getStringDateInDateFormat(eventEndDate))) {
			scheduleType = CommonConstants.PAST;
		} else {
			scheduleType = CommonConstants.CURRENT;
		}
		return scheduleType;
	}

	/**
	 * adds date value  to date json
	 *
	 * @param name
	 * @param codeValue
	 * @param dateArray
	 */
	private void settingDateJson(String name, String codeValue,JsonArray dateArray) {

		JsonObject dateJson = new JsonObject();

			boolean flag = true;
			for(int i=0;i<dateArray.size();i++) {
				if(codeValue.equals(dateArray.get(i).getAsJsonObject().get(CommonConstants.CODE).getAsString())){
					dateArray.get(i).getAsJsonObject().addProperty(CommonConstants.COUNT, dateArray.get(i).getAsJsonObject().get(CommonConstants.COUNT).getAsInt()+1);
					flag = false;
				}
			}
			if(flag) {
				dateJson.addProperty(CommonConstants.NAME, name);
				dateJson.addProperty(CommonConstants.CODE, codeValue);
				dateJson.addProperty(CommonConstants.IS_SELECTED, false);
				dateJson.addProperty(CommonConstants.COUNT, 1);
			}

			if(dateJson.size()>0) {
			dateArray.add(dateJson);
			}

	}

	/**
	 * datre formating method
	 *
	 * @param eventDate
	 * @param string
	 * @return
	 */
	private String dateFormating(String eventDate, String string) {

		String returnString = StringUtils.EMPTY;
		if(string.equals(CommonConstants.SCHEDULE_ON)) {
			returnString = getDateAndTime(eventDate,"dd MMM yyyy");
		}else if(string.equals(CommonConstants.DATE_TYPE)) {
			returnString = getDateAndTime(eventDate,"MMM_yyyy");
		}else if(string.equals(CommonConstants.MONTH_YEAR)) {
			returnString = getDateAndTime(eventDate,"MMMMM yyyy");
		}else if(string.equals("date")) {
			returnString = getDateAndTime(eventDate,"dd");
		}else if(string.equals("month")) {
			returnString = getDateAndTime(eventDate,"MMM");
		}
		return returnString;
	}

	/**
	 * Gets the date and time.
	 *
	 * @param strDateTime the str date time
	 * @param dateTimeFormat the date time format
	 * @return the date and time
	 */
	public  String getDateAndTime(String strDateTime, String dateTimeFormat) {
		return Optional.ofNullable(strDateTime).filter(s -> !s.equals("")).map(s -> {
			try {
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(s);
			} catch (ParseException e) {
				logger.error("ParseException {}", e.getMessage());
			}
			return StringUtils.EMPTY;
		}).map(d -> new SimpleDateFormat(dateTimeFormat).format(d)).orElse("");
	}

	/**
	 * returns the JSON response as a string
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String getRequestJsonString(final SlingHttpServletRequest request) throws IOException {
		StringBuilder jb = new StringBuilder();
		InputStream stream = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		while ((line = reader.readLine()) != null) {
			jb.append(line);
		}
		return jb.toString();
	}

	/**
	 * returns the facets from json response string
	 *
	 * @param jsonString
	 * @return
	 */
	public JsonArray getrequestFacetJson(String jsonString) {
		JsonObject jsonObj = new JsonParser().parse(jsonString).getAsJsonObject();
		return jsonObj.getAsJsonArray(CommonConstants.FACETS);
	}

}
