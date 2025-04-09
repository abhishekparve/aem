package com.bdb.aem.core.util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.jcr.Property;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Utils Class for Solr
 */
public final class SolrUtils {
	// private constructor to avoid unnecessary instantiation of the class
	private SolrUtils() {
	}


	/**
	 * This method is used to extract the tags from the content page
	 * 
	 * @param pageContent
	 * @param isAllContentWorkflow 
	 * @return JsonArray of tags which are attached to the page. Empty array if no
	 *         tags are attached
	 */
	public static JsonArray getPageTags(Resource pageContent) {
		JsonArray tagsArray= new JsonArray();
		if(null != pageContent) {
			Page page = pageContent.adaptTo(Page.class);
				Tag tags[] = page.getTags();
				for (int i = 0; i < tags.length; i++) {
					Tag tag = tags[i];
					tagsArray.add(tag.getTitle());
				}
		}		
		
		return tagsArray;
	}
	
	/**
	 * Gets the pdf tags.
	 *
	 * @param pdfMetadataResource the pdf metadata resource
	 * @param resolver the resolver
	 * @return the pdf tags
	 */
	public static ArrayList<String> getPdfTags(Resource pdfMetadataResource, ResourceResolver resolver) {
		ValueMap map = pdfMetadataResource.adaptTo(ValueMap.class);
		ArrayList<String> tagsArray = new ArrayList<String>();
		if (map.containsKey("docRegion")) {
			String[] docRegion = map.get("docRegion", String[].class);
			TagManager tageManager = resolver.adaptTo(TagManager.class);
			for (String region : docRegion) {
				Tag tag = tageManager.resolve(region);
				if(null != tag) {
				   tagsArray.add(tag.getName());	
				}
			}
		}
		return tagsArray;
	}


	/**
	 * This method converts jcr formatted date to Solr specification format
	 * 
	 * @param Takes input as Calendar
	 * @return Solr formatted date of type string
	 */
	public static String solrDate(Calendar cal) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				SolrSearchConstants.SIMPLEDATEFORMAT_TIMESTAMP);
		return dateFormat.format(cal.getTime()) + "Z";
	}

	/**
	 * This method returs "" if string is null.
	 * 
	 * @param Takes
	 *            input as string
	 * @return String value. if string value is "null" then ""
	 */
	public static String checkNull(String property) {
		if (StringUtils.isEmpty(property)) {
			return "";
		}
		return property;

	}
	
	public static String checkNullProperty(ValueMap valuemap, String property) {
		if (null==valuemap.get(property)) {
			return "";
		}
		return valuemap.get(property).toString();

	}
	
	public static Resource getVariantHpResource(Resource baseProductHpResource, String catalogNo) {
		if (null != baseProductHpResource &&  null != baseProductHpResource.getParent().getChild(catalogNo)) {
			return baseProductHpResource.getParent().getChild(catalogNo).getChild(CommonConstants.HP_NODE);
		}
		return null;
	}
	
	/**
	 * Gets the json property.
	 *
	 * @param assetObj the asset obj
	 * @param myParam the my param
	 * @return the json property
	 */
	public static String getJsonProperty(JsonObject assetObj, String myParam) {
		String property = null;
		if (null != assetObj.get(myParam)) {
			property = assetObj.get(myParam).getAsString();
		}
		return property;
	}

}
