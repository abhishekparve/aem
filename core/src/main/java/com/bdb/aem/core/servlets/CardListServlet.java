package com.bdb.aem.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Servlet CardListServlet.
 */
@Component(service = Servlet.class, immediate = true, property = {
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + CardListServlet.RESOURCE_TYPE,
        ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON,
        ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST })

public class CardListServlet extends SlingAllMethodsServlet {
    private static final long serialVersionUID = 1L;
    transient Logger logger = LoggerFactory.getLogger(CardListServlet.class);

	public static final String RESOURCE_TYPE = "bdb/get-cards-data";

    @Reference
    transient ResourceResolverFactory resourceResolverFactory;
    
    @Reference
    transient ExternalizerService externalizerService;
    
    public static final String REDIRECT_URL = "redirectUrl";
	
    /**
     * doPost method which gets the cards details, facets and sends in response
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
	    JsonArray pageListArray = new JsonArray();
	    JsonObject pageJson = null;
	    JsonArray topicsArray = new JsonArray();
	    JsonArray datesArray = new JsonArray();

        try {
        	resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
        	String jsonString = getRequestJsonString(request);
        	JsonArray facetArray = getrequestFacetJson(jsonString);
        	JsonObject facetJson = new JsonParser().parse(jsonString).getAsJsonObject();
        	String rootPagePath = facetJson.get(CommonConstants.PARENTPAGEPATH)!= null?facetJson.get(CommonConstants.PARENTPAGEPATH).getAsString():StringUtils.EMPTY;
        	String tagBasedSearch = facetJson.get(CommonConstants.TAG_BASED_SEARCH)!= null?facetJson.get(CommonConstants.TAG_BASED_SEARCH).getAsString():StringUtils.EMPTY;
        	String includeChildPages = facetJson.get(CommonConstants.INCLUDE_CHILD_PAGES)!= null?facetJson.get(CommonConstants.INCLUDE_CHILD_PAGES).getAsString():StringUtils.EMPTY;
        	String categoryTags = facetJson.get(CommonConstants.TAGS_LABEL)!= null?facetJson.get(CommonConstants.TAGS_LABEL).getAsString():StringUtils.EMPTY;
        	String topicsTag = facetJson.get(CommonConstants.TOPICS_TAG_LABEL)!= null ? facetJson.get(CommonConstants.TOPICS_TAG_LABEL).getAsString() : StringUtils.EMPTY;
        	
        	PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        	Page rootPage = pageManager.getPage(rootPagePath);
	    	
	    	if(null != rootPage) {
	    		if(null != tagBasedSearch && tagBasedSearch.equalsIgnoreCase(CommonConstants.TRUE)) {
	    			getTagBasedCardList(resourceResolver, pageListArray, pageJson, rootPage, includeChildPages, categoryTags, topicsArray, datesArray, topicsTag);
	    		}
	    		else {
		  			getPathBasedCardList(resourceResolver, pageListArray, pageJson, rootPage, includeChildPages, topicsArray, datesArray, topicsTag);
		  		} 
	  		} 
	    	
        	finalResponseJson.add("facetsConfig", constructingFacetJsons(facetArray, topicsArray, datesArray, topicsTag));
        	pageListArray = sortJsonObject(pageListArray, "scheduleTime");
        	finalResponseJson.add("cardData", pageListArray);
        	apiResponse = finalResponseJson.toString();

        } catch (LoginException | ParseException e) {
			apiResponse = e.getMessage();
        	response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        	logger.error("Exception Occurred during execution {}", e);
		} finally {
			response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(apiResponse);
		}
    }

    /**
     * sort the Json ArrayResources
     *
     * @param sortBy    String
     * @param jsonArray
     * @return JsonArray
     */
    public JsonArray sortJsonObject(JsonArray jsonArray, String sortBy) {
        JsonArray sortedArray = new JsonArray();
        ArrayList<JsonObject> listJsonObj = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            listJsonObj.add((JsonObject) jsonArray.get(i));
        }

        Collections.sort(listJsonObj,
                (o1, o2) -> o1.get(sortBy).getAsString().compareToIgnoreCase(o2.get(sortBy).getAsString()));
        Collections.reverse(listJsonObj);
        for (int i = 0; i < jsonArray.size(); i++) {
            sortedArray.add(listJsonObj.get(i));
        }
        return sortedArray;
    }
    

	/**
     * 
     * @param resourceResolver
     * @param pageListArray
     * @param pageJson
     * @param rootPage
     * @param includeChildPages
     * @param categoryTag
     * @param topicsArray
     * @param datesArray
     * @param topicsTag 
     * @throws ParseException
     */
    private void getTagBasedCardList(ResourceResolver resourceResolver, JsonArray pageListArray, JsonObject pageJson, Page rootPage, String includeChildPages, String categoryTag, JsonArray topicsArray, JsonArray datesArray, String topicsTag) throws ParseException {
		Iterator<Page> rootPageIterator = null;
		if(null != includeChildPages && includeChildPages.equalsIgnoreCase(CommonConstants.TRUE)) {
			rootPageIterator = rootPage.listChildren(null, true);
		} else {
			rootPageIterator = rootPage.listChildren();
		}
    	while(rootPageIterator.hasNext()) {
    		pageJson = new JsonObject();
    		Page childPage =    rootPageIterator.next();
    		ValueMap childPageValueMap = childPage.getProperties();
    		String[] pageTags = childPageValueMap.get(CommonConstants.CQ_TAGS, String[].class);
    		String hideInCardList = childPageValueMap.get(CommonConstants.HIDE_IN_LISTING, String.class);
    		if (null != childPage && (null != pageTags && pageTags.length > 0)) {
    			for (String pageTag : pageTags) {
    				if(categoryTag.contains(pageTag) && (null == hideInCardList || hideInCardList.equalsIgnoreCase(CommonConstants.FALSE))) {
    					getCardList(resourceResolver, pageListArray, pageJson, childPage, topicsArray, datesArray, topicsTag);
    					break;
    				}
    			}
    		}
    	}
	}

    /**
     * 
     * @param resourceResolver
     * @param pageListArray
     * @param pageJson
     * @param rootPage
     * @param includeChildPages
     * @param topicsArray
     * @param datesArray
     * @param topicsTag 
     * @throws ParseException
     */
   	private void getPathBasedCardList(ResourceResolver resourceResolver, JsonArray pageListArray, JsonObject pageJson, Page rootPage, String includeChildPages, JsonArray topicsArray, JsonArray datesArray, String topicsTag) throws ParseException {
		Iterator<Page> rootPageIterator = null;
		if(null != includeChildPages && includeChildPages.equalsIgnoreCase(CommonConstants.TRUE)) {
			rootPageIterator = rootPage.listChildren(null, true);
		} else {
			rootPageIterator = rootPage.listChildren();
		}
		while(rootPageIterator.hasNext()) {
			pageJson = new JsonObject();
    		Page childPage =    rootPageIterator.next();
    		ValueMap childPageValueMap = childPage.getProperties();
    		String hideInCardList = childPageValueMap.get(CommonConstants.HIDE_IN_LISTING, String.class);
    		if(null != childPage && (null == hideInCardList || hideInCardList.equalsIgnoreCase(CommonConstants.FALSE))) {
    			getCardList(resourceResolver, pageListArray, pageJson, childPage, topicsArray, datesArray, topicsTag);
    		}
    	}
    }
	
	/**
	 * 
	 * @param resourceResolver
	 * @param pageListArray
	 * @param pageJson
	 * @param childPage
	 * @param topicsArray
	 * @param datesArray
	 * @param topicsTag 
	 * @throws ParseException
	 */
	private void getCardList(ResourceResolver resourceResolver, JsonArray pageListArray, JsonObject pageJson, Page childPage, JsonArray topicsArray, JsonArray datesArray, String topicsTag) throws ParseException {
		Resource childResource = resourceResolver.getResource(childPage.getPath());
		if(null != childResource && childResource.getValueMap().get(CommonConstants.JCR_PRIMARY_TYPE).toString().equals(CommonConstants.CQ_PAGE)) {
			ValueMap childPageValueMap = childPage.getProperties();
			String[] pageTags = childPageValueMap.get(CommonConstants.CQ_TAGS, String[].class);
			String topics = "";
			String[] topicsTagList = topicsTag.split("[,]", 0);
			List<String> uniqueTopicsFacetList = new ArrayList<String>();
			StringBuilder topicsBuilder = new StringBuilder();
			
			if(null != pageTags && null != topicsTagList) {
				for (String pageTag : pageTags) {
					createTopicsJson(resourceResolver, pageTag, topicsTagList, topics, topicsArray, uniqueTopicsFacetList, topicsBuilder);
				}
				String topicsString = topicsBuilder.toString();
				if (topicsString.endsWith(",")) { 
					topicsString = topicsString.substring(0, topicsString.lastIndexOf(",")) ;
		        }
				pageJson.addProperty(CommonConstants.TOPICS, topicsString);
			} else {
				pageJson.addProperty(CommonConstants.TOPICS, topics);
			}
			String cardDate = childPageValueMap.get(CommonConstants.CARD_LIST_DATE, StringUtils.EMPTY);
			String cardDateInDateFormat = "";
			String cardDateInMonthFormat = "";
			pageJson.addProperty(CommonConstants.TITLE, childPageValueMap.get(CommonConstants.JCRTITLE, StringUtils.EMPTY));
			pageJson.addProperty(CommonConstants.DESCRIPTION, childPageValueMap.get(CommonConstants.JCR_DESCRIPTION, StringUtils.EMPTY));
        	
        	String jcrCreatedDate = childPageValueMap.get(CommonConstants.JCR_CREATED, StringUtils.EMPTY);
        	if(!cardDate.isEmpty()) {
        		cardDateInDateFormat = dateFormating(cardDate,CommonConstants.DATE_TYPE);
            	cardDateInMonthFormat = dateFormating(cardDate,CommonConstants.MONTH_YEAR);
        		pageJson.addProperty(CommonConstants.CARD_DATE, cardDate);
        		pageJson.addProperty(CommonConstants.DATES, cardDateInDateFormat);
        	} else {
        		cardDateInDateFormat = dateFormating(jcrCreatedDate,CommonConstants.DATE_TYPE);
            	cardDateInMonthFormat = dateFormating(jcrCreatedDate,CommonConstants.MONTH_YEAR);
        		pageJson.addProperty(CommonConstants.CARD_DATE, jcrCreatedDate);
        		pageJson.addProperty(CommonConstants.DATES, cardDateInDateFormat);
        	}
        	settingDatesFacetJson(cardDateInMonthFormat, cardDateInDateFormat, datesArray);
        	pageJson.addProperty(CommonConstants.IMAGE_URL, externalizerService.getFormattedUrl(childPageValueMap.get(CommonConstants.CARD_THUMBNAIL, StringUtils.EMPTY), resourceResolver)); 
        	pageJson.addProperty(REDIRECT_URL, externalizerService.getFormattedUrl(childPage.getPath().toString(), resourceResolver));
        	
        	pageListArray.add(pageJson);
		}
	}
	
	/**
	 * 
	 * @param resourceResolver
	 * @param pageTag
	 * @param topicsTagList
	 * @param topics
	 * @param topicsArray
	 * @param uniqueTopicsList
	 * @param topicsBuilder
	 */
	public void createTopicsJson(ResourceResolver resourceResolver, String pageTag, String[] topicsTagList, String topics, JsonArray topicsArray, List<String> uniqueTopicsList, StringBuilder topicsBuilder) {
		for(String topicTag : topicsTagList) {
			
			if(pageTag.equalsIgnoreCase(topicTag) || pageTag.startsWith(topicTag)) {
				
				TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
				Tag tag = tagManager.resolve(pageTag);
				if(null!=tag) {
					String topic = tag.getTitle();
				topics = tag.getTitle().replaceAll("[^a-zA-Z0-9]", "-").toLowerCase();
				topicsBuilder.append(topics);
				topicsBuilder.append(",");
				settingTopicsFacetJson(topic, topics, topicsArray, uniqueTopicsList);
				}
				
			
				
			}
		}		
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
	
	/**
	 * 
	 * @param facetsArray
	 * @param topicsArray
	 * @param datesArray
	 * @param topicsTag
	 * @return
	 */
	 private JsonArray constructingFacetJsons(JsonArray facetsArray,JsonArray topicsArray, JsonArray datesArray, String topicsTag) {
		JsonArray responseFacets = new JsonArray();
			for(int i=0; i<facetsArray.size();i++) {
	    		if(StringUtils.equals(CommonConstants.TOPICS, facetsArray.get(i).getAsJsonObject().get(CommonConstants.VALUE).getAsString())) {
	    			responseFacets.add(facetJsonItem(facetsArray, i, topicsArray));
				} else if(StringUtils.equals(CommonConstants.DATES, facetsArray.get(i).getAsJsonObject().get(CommonConstants.VALUE).getAsString())) {
	    			responseFacets.add(facetJsonItem(facetsArray, i, datesArray));
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
	public JsonObject facetJsonItem(JsonArray facetsArray, int i, JsonArray topicsArray) {

		JsonObject facetObj = new JsonObject();

		facetObj.addProperty(CommonConstants.NAME, facetsArray.get(i).getAsJsonObject().get(CommonConstants.LABEL).getAsString());
		facetObj.addProperty(CommonConstants.CODE, facetsArray.get(i).getAsJsonObject().get(CommonConstants.VALUE).getAsString());
		facetObj.addProperty(CommonConstants.CODE_TARGET, CommonConstants.HASH.concat(facetsArray.get(i).getAsJsonObject().get(CommonConstants.VALUE).getAsString()));
		facetObj.add(CommonConstants.CHILDREN, topicsArray);

		return facetObj;
	}
	
	/**
	 * adding topic to topic facets json
	 * @param topics
	 * @param topicCode
	 * @param topicsArray
	 * @param topicsList 
	 */
	private void settingTopicsFacetJson(String topic,String topicCode,JsonArray topicsArray, List<String> uniqueTopicsList) {

		JsonObject topicsJson = new JsonObject();
		boolean flag = true;
		for(int i=0;i<topicsArray.size();i++) {
			if(topicCode.equals(topicsArray.get(i).getAsJsonObject().get(CommonConstants.CODE).getAsString())){
				topicsArray.get(i).getAsJsonObject().addProperty(CommonConstants.COUNT, topicsArray.get(i).getAsJsonObject().get(CommonConstants.COUNT).getAsInt()+1);
				flag = false;
			}
		}
		if(flag) {
			topicsJson.addProperty(CommonConstants.NAME, topic);
			topicsJson.addProperty(CommonConstants.CODE, topicCode);
			topicsJson.addProperty(CommonConstants.IS_SELECTED, false);
			topicsJson.addProperty(CommonConstants.COUNT, 1);
		}
		
		if(topicsJson.size() > 0 && !uniqueTopicsList.contains(topicCode)) {
			topicsArray.add(topicsJson);
		}
		uniqueTopicsList.add(topicCode);
	}
	
	/**
	 * adds date value  to date json
	 *
	 * @param name
	 * @param codeValue
	 * @param dateArray
	 */
	private void settingDatesFacetJson(String name, String codeValue,JsonArray datesArray) {

		JsonObject dateJson = new JsonObject();
		boolean flag = true;
		for(int i=0;i<datesArray.size();i++) {
			if(codeValue.equals(datesArray.get(i).getAsJsonObject().get(CommonConstants.CODE).getAsString())){
				datesArray.get(i).getAsJsonObject().addProperty(CommonConstants.COUNT, datesArray.get(i).getAsJsonObject().get(CommonConstants.COUNT).getAsInt()+1);
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
			datesArray.add(dateJson); 
		}
	}
	
	/**
	 * 
	 * @param scheduleDate
	 * @param string
	 * @return
	 */
	private String dateFormating(String scheduleDate, String string) {

		String returnString = StringUtils.EMPTY;
		if(string.equals(CommonConstants.SCHEDULE_ON)) {
			returnString = getDateAndTime(scheduleDate,"dd MMM yyyy");
		}else if(string.equals(CommonConstants.DATE_TYPE)) {
			returnString = getDateAndTime(scheduleDate,"MMM_yyyy");
		}else if(string.equals(CommonConstants.MONTH_YEAR)) {
			returnString = getDateAndTime(scheduleDate,"MMMMM yyyy");
		}else if(string.equals("date")) {
			returnString = getDateAndTime(scheduleDate,"dd");
		}else if(string.equals("month")) {
			returnString = getDateAndTime(scheduleDate,"MMM");
		}
		return returnString;
	}
	
	/**
	 * 
	 * @param cardDateTime
	 * @param dateTimeFormat
	 * @return
	 */
	public  String getDateAndTime(String cardDateTime, String dateTimeFormat) {
		return Optional.ofNullable(cardDateTime).filter(s -> !s.equals("")).map(s -> {
			try {
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(s);
			} catch (ParseException e) {
				logger.error("ParseException {}", e.getMessage());
			}
			return StringUtils.EMPTY;
		}).map(d -> new SimpleDateFormat(dateTimeFormat).format(d)).orElse("");
	}

}
