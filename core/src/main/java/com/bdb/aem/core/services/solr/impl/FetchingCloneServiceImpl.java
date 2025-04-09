package com.bdb.aem.core.services.solr.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.xml.bind.annotation.XmlRegistry;

import com.bdb.aem.core.services.BDBApiEndpointService;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.solr.FetchingCloneService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Component(service = FetchingCloneService.class, immediate = true)
public class FetchingCloneServiceImpl implements FetchingCloneService {
	private static final Logger LOG = LoggerFactory.getLogger(FetchingCloneServiceImpl.class);
	JsonObject response = new JsonObject();
	
	@Reference
	private BDBApiEndpointService bdbApiEndpointService;

	/**
	 *
	 * @param baseId
	 * @param solrConfig
	 * @param solrSearchService
	 * @return Clones Json
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Override
	public String getClonesData(String country, BDBSearchEndpointService solrConfig, SolrSearchService solrSearchService, String specificity, String dyeName, String cloneId)
			throws IOException, SolrServerException {

		String responseJson = StringUtils.EMPTY;
		HttpSolrClient server = solrSearchService.solrClient(country);
		if (StringUtils.isNotEmpty(specificity) && StringUtils.isNotEmpty(cloneId) && StringUtils.isNotEmpty(dyeName)) {
			JsonArray cloneData = fetchRelatedClones(specificity, cloneId, dyeName, server);
			if (cloneData.size() > 0) {
				responseJson = getFinalJson(cloneData);
			} else if (cloneData.size() == 0) {
				JsonObject empty = new JsonObject();
				empty.addProperty("Empty", "No Results Found");
				return empty.toString();
			}

		}else {
			LOG.debug("This product does not have Other clones .please check specificity,clone,dyename");
		}
		return responseJson;
	}

	/**
	 *
	 * @param baseId
	 * @param solrConfig
	 * @param solrSearchService
	 * @return Formats Json
	 * @throws Exception
	 * @throws IOException
	 * @throws SolrServerException 
	 * @throws RepositoryException 
	 */
	@Override
	public String getFormatsData(String country, BDBSearchEndpointService solrConfig,
			SolrSearchService solrSearchService, String dyeName, String cloneId) throws IOException, SolrServerException, RepositoryException {

		String responseJson = StringUtils.EMPTY;
		HttpSolrClient server = solrSearchService.solrClient(country);
		
		if (StringUtils.isNotEmpty(cloneId) && (StringUtils.isNotEmpty(dyeName))) {
			JsonArray formatData = fetchRelatedFormats(cloneId, dyeName, server);
			if (formatData.size() > 0) {
				responseJson = getFinalJson(formatData);
			} else if (formatData.size() == 0) {
				JsonObject empty = new JsonObject();
				empty.addProperty("Empty", "No Results Found");
				return empty.toString();
			}
		}else {
			LOG.debug("This product does not have Other Formats .please check clone,dyename");
		}
		return responseJson;

	}
	
	@Override
	public String getSpecificityData(String country, BDBSearchEndpointService solrConfig,
			SolrSearchService solrSearchService, String dyeName, String specificity) throws IOException, SolrServerException, RepositoryException {

		String responseJson = StringUtils.EMPTY;
		HttpSolrClient server = solrSearchService.solrClient(country);
		
		if (StringUtils.isNotEmpty(specificity) && (StringUtils.isNotEmpty(dyeName))) {
			JsonArray formatData = fetchSpecificity(specificity, dyeName, server);
			if (formatData.size() > 0) {
				responseJson = getFinalJson(formatData);
			} else if (formatData.size() == 0) {
				JsonObject empty = new JsonObject();
				empty.addProperty("Empty", "No Results Found");
				return empty.toString();
			}
		}else {
			LOG.debug("This product does not have Other Formats .please check specificity,dyename");
		}
		return responseJson;

	}

	
	/**
	 *
	 * @param element
	 * @param variable
	 * @return returns the values from Results Array
	 */
	private String fetchValuesfromArray(JsonElement element, String variable) {
		String value = StringUtils.EMPTY;
		if(element.getAsJsonObject().has(variable)) {
			value= element.getAsJsonObject().get(variable).getAsString();
		}
		return value;
	}

	/**
	 *
	 * @param specificity
	 * @param cloneId
	 * @param dyeName
	 * @param server
	 * @return relatedClones Query
	 * @throws IOException
	 * @throws SolrServerException
	 */
	private JsonArray fetchRelatedClones(String specificity, String cloneId, String dyeName, HttpSolrClient server)
			throws IOException, SolrServerException {
		SolrQuery solrQuery = cloneQuery(specificity, cloneId, dyeName);
		QueryResponse sitesQueryResponse = server.query(solrQuery);
		SolrDocumentList sitesSolrDocs = sitesQueryResponse.getResults();
		JsonArray results = getResultsJson(sitesSolrDocs);
		LOG.debug("Response for Clone Data {}" , results);
		return results;

	}

	/**
	 *
	 * @param cloneId
	 * @param dyeName
	 * @param server
	 * @return fetches related Format Query
	 * @throws IOException
	 * @throws SolrServerException
	 */
	private JsonArray fetchRelatedFormats(String cloneId, String dyeName, HttpSolrClient server)
			throws IOException, SolrServerException {
		SolrQuery solrQuery = formatQuery(cloneId, dyeName);
		QueryResponse sitesQueryResponse = server.query(solrQuery);
		SolrDocumentList sitesSolrDocs = sitesQueryResponse.getResults();
		JsonArray results = getResultsJson(sitesSolrDocs);
		LOG.debug("Response for Format Data {}" , results);
		return results;

	}

	/**
	 *
	 * @param specificity
	 * @param dyeName
	 * @param server
	 * @return fetches related Format Query
	 * @throws IOException
	 * @throws SolrServerException
	 */
	private JsonArray fetchSpecificity(String specificity, String dyeName, HttpSolrClient server)
			throws IOException, SolrServerException {
		SolrQuery solrQuery = formatSpecificityQuery(specificity, dyeName);
		QueryResponse sitesQueryResponse = server.query(solrQuery);
		SolrDocumentList sitesSolrDocs = sitesQueryResponse.getResults();
		JsonArray results = getResultsJson(sitesSolrDocs);
		LOG.debug("Response for Specificity in Format Data {}" , results);
		return results;

	} 
	/**
	 *
	 * @param specificity
	 * @param cloneId
	 * @param dyeName
	 * @return Fetching Other Clones Query
	 */
	private SolrQuery cloneQuery(String specificity, String cloneId, String dyeName) {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.addFilterQuery("isAvailable_t:true","specificity_s:\"" + specificity+"\"", "-tdsCloneName_s:\"" + cloneId+"\"", "dyeName_s:\"" + dyeName+"\"");
		solrQuery.setQuery("isPrimaryVariant_t:true");
		solrQuery.addSort("tdsCloneName_s", SolrQuery.ORDER.desc);
		solrQuery.setRows(500);
		solrQuery.setStart(0);
		return solrQuery;
	}

	/**
	 *
	 * @param cloneId
	 * @param dyeName
	 * @return Fetching Other formats Query
	 */
	private SolrQuery formatQuery(String cloneId, String dyeName) {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.addFilterQuery("isAvailable_t:true","tdsCloneName_s:\"" + cloneId +"\"", "-dyeName_s:\"" + dyeName+"\"","dyeName_s:['' TO *]");
		solrQuery.setQuery("isPrimaryVariant_t:true");
		solrQuery.addSort("dyeName_s", SolrQuery.ORDER.asc);
		solrQuery.setRows(500);
		solrQuery.setStart(0);
		return solrQuery;
	}

	/**
	 *
	 * @param specificity
	 * @param dyeName
	 * @return Fetching Other formats Query
	 */
	private SolrQuery formatSpecificityQuery(String specificity, String dyeName) {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.addFilterQuery("isAvailable_t:true","specificity_s:\"" + specificity+"\"", "-dyeName_s:\"" + dyeName+"\"","dyeName_s:['' TO *]");
		solrQuery.setQuery("isPrimaryVariant_t:true");
		solrQuery.addSort("dyeName_s", SolrQuery.ORDER.asc);
		solrQuery.setRows(500);
		solrQuery.setStart(0);
		return solrQuery;
	}
	
	/**
	 *
	 * @param solrDocs
	 * @return Iterable JsonArray of the Query
	 */
	private JsonArray getResultsJson(SolrDocumentList solrDocs) {
		Boolean isOptiBuild=solrDocs.stream().anyMatch(doc ->doc.containsValue("BD OptiBuild™"));
		JsonObject abSeqfacetObject=null;
		Iterator<SolrDocument> iterator = solrDocs.iterator();
		JsonArray results = new JsonArray();
		while (iterator.hasNext()) {
			SolrDocument solrDocument = iterator.next();
			Iterator<Map.Entry<String, Object>> itr = solrDocument.iterator();
			JsonObject facetObject = new JsonObject();
			while (itr.hasNext()) {
				Map.Entry<String, Object> map = itr.next();
				facetObject.add(map.getKey(), new Gson().toJsonTree(map.getValue()));
			}
			if(solrDocument.containsValue("BD™ AbSeq") && Boolean.TRUE.equals(isOptiBuild)) {
				abSeqfacetObject=facetObject;
			}
			else {
				results.add(facetObject);
			}
		}
		if(abSeqfacetObject!=null)
			results.add(abSeqfacetObject);
		return results;
	}

	/**
	 *
	 * @param results
	 * @return Mapping Query to Results Json
	 */
	private String getFinalJson(JsonArray results) {
		JsonArray finalJson = new JsonArray();
		for (int i = 0; i < results.size(); i++) {
			JsonObject object = new JsonObject();
			JsonElement element = results.get(i);
			String catalogNumber=fetchValuesfromArray(element, "materialNumber_t");
			object.addProperty("catalogNumber",catalogNumber);
			object.addProperty("imageUrl", fetchValuesfromArray(element, "thumbnailImage_t"));
			String rummode = CommonHelper.getRunMode(bdbApiEndpointService);
			String id=element.getAsJsonObject().get("id").getAsString();
			String productUrl = StringUtils.EMPTY;
			if(StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.AUTHOR)) {
				productUrl= CommonConstants.CONTENT +CommonConstants.SINGLE_SLASH + CommonConstants.BDB+ CommonConstants.SINGLE_SLASH+"{region}" +CommonConstants.SINGLE_SLASH + "{country}" +CommonConstants.SINGLE_SLASH +"{language-country}"+ id+".html";
			}
			if(StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.PUBLISH)) {
				productUrl= CommonConstants.SINGLE_SLASH +"{language-country}"+ id;
				productUrl = productUrl.replace("pdp.", "");
			}

			object.addProperty("productUrl",productUrl);
			object.addProperty("name", fetchValuesfromArray(element, "name_t"));
			object.addProperty("brand", fetchValuesfromArray(element, "brand_t"));
			object.addProperty("dyeName", fetchValuesfromArray(element, "dyeName_t"));
			object.addProperty("cloneId", fetchValuesfromArray(element, "tdsCloneName_t"));
			object.addProperty("regulatoryStatus", fetchValuesfromArray(element, "regulatoryStatus_t"));
			if(fetchValuesfromArray(element, "regulatoryStatus_t").equals("IVD")) {
				object.addProperty("regulatoryStatusDisplayValue", StringUtils.isNotBlank(fetchValuesfromArray(element, "regulatoryStatus_display_value_facet_s"))?fetchValuesfromArray(element, "regulatoryStatus_display_value_facet_s"):StringUtils.EMPTY);
			}
			object.addProperty(CommonConstants.LASER_COLOR, fetchValuesfromArray(element, "laserColor_t"));
			//Adding flag for having compare checkbox
			String templateId=fetchValuesfromArray(element,CommonConstants.PDP_TEMPLATE_SOLR);
			if(StringUtils.isNotEmpty(templateId)) {
				Boolean compareFlag=StringUtils.equals("SFA", templateId) || StringUtils.equals("SCM", templateId)?Boolean.TRUE:Boolean.FALSE;
				object.addProperty(CommonConstants.IS_COMPARABLE, compareFlag);
			}
			finalJson.add(object);
		}
		return finalJson.toString();
	}

}
