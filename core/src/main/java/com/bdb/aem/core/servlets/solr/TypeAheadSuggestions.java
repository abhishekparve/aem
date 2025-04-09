package com.bdb.aem.core.servlets.solr;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Correction;
import org.apache.solr.client.solrj.response.Suggestion;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.KeywordsModel;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * The Servlet for typeahead suggestions.
 */

@SuppressWarnings("CQRules:CQBP-75")
@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Type Ahead Solr Suggestions Servlet",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + TypeAheadSuggestions.RESOURCE_TYPE })
public class TypeAheadSuggestions extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(TypeAheadSuggestions.class);
	public static final String RESOURCE_TYPE = "bdb/type-ahead-suggestion";
	@Reference
	transient SolrSearchService searchService;

	@Reference
	transient ExternalizerService externalizerService;

	@Reference
	transient BDBSearchEndpointService bdbSearchEndpointService;

	transient Resource res = null;

	/**
	 * doGet method for typeahead suggestions
	 *
	 * @param request  request attr
	 * @param response response attr
	 * @throws IOException
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOG.debug("start of doget method in TypeAheadSuggestions");

		long startTime = System.currentTimeMillis();
		ResourceResolver resourceResolver = request.getResourceResolver();
		Map<String, List<Suggestion>> suggestedTerms = new HashMap<>();
		String country = request.getParameter("country");
		HttpSolrClient solr = searchService.solrClient(country);
		// Gets 'q' from the request
		String queryParam = request.getParameter(SolrSearchConstants.SOLR_QUERY_Q);
		// converted Object
		JsonObject convertedObject = new JsonObject();
		Map<String, String> formationMap = keywordMapFormation(request, resourceResolver);
		if (formationMap.containsKey(queryParam)) {
			LOG.debug("Formation map contains the key");
			convertedObject.addProperty("urlredirect", formationMap.get(queryParam));
			convertedObject.add("mySuggester", new JsonArray());
			convertedObject.add(CommonConstants.SUGGESTION, new JsonArray());
		} else {
			LOG.debug("Formation map doesnt contain the key");
			if (solr != null && queryParam != null && !queryParam.isEmpty()) {
				// suggested Term as a parameter
				convertedObject = makeSolrSuggesterRequest(suggestedTerms, solr, queryParam, convertedObject);
			} else {
				response.setStatus(500);
				response.getWriter().println("Solr is down!");
			}
		}
		response.setContentType(SolrSearchConstants.CONTENT_TYPE);
		LOG.debug("time taken TypeAheadSuggestions = {}", System.currentTimeMillis() - startTime);
		response.getWriter().println(new Gson().toJson(convertedObject));
		LOG.debug("time taken TypeAheadSuggestions json = {}", System.currentTimeMillis() - startTime);
		LOG.debug("end of doget method in TypeAheadSuggestions");
	}

	/**
	 * @param response
	 * @param suggestedTerms
	 * @param solr
	 * @param queryParam
	 * @param convertedObject
	 * @return
	 * @throws org.apache.sling.commons.json.JSONException
	 * @throws IOException
	 */
	public JsonObject makeSolrSuggesterRequest(Map<String, List<Suggestion>> suggestedTerms, HttpSolrClient solr,
			String queryParam, JsonObject convertedObjectForSuggest) {
		JsonObject convertedObject = new JsonObject();
		QueryResponse queryResponse;
		QueryResponse queryResponseForCN;
		SolrQuery solrQuery = new SolrQuery();
		SolrQuery solrQueryForCN = new SolrQuery();
		JsonArray tempJsonArray = new JsonArray();

		updateSuggestQuery(solrQuery, queryParam);

		try {
			for (int i = 0; i < queryParam.length(); i++) {
				if (queryParam.charAt(i) == '%')
					queryParam = queryParam.replaceAll(String.valueOf(queryParam.charAt(i)), "");
			}
			queryResponse = solr.query(solrQuery);
			
			if (queryResponse != null && queryResponse.getSuggesterResponse() != null) {
		        suggestedTerms = queryResponse.getSuggesterResponse().getSuggestions();
		    }
			String suggestedObject = new Gson().toJson(suggestedTerms);
			convertedObjectForSuggest = new Gson().fromJson(suggestedObject, JsonObject.class);
			
			JsonArray tempMySuggester = convertedObjectForSuggest.get("mySuggester").getAsJsonArray();
			
			if(tempMySuggester.size() == 0) {
				convertedObject.add("mySuggester", tempJsonArray);
			}
			
			for (int counter_j = 0; counter_j < tempMySuggester.size(); counter_j++) {
				JsonObject tempElementsOfSuggestor = tempMySuggester.get(counter_j).getAsJsonObject();
				String eachTermOfSuggestor = tempElementsOfSuggestor.get("term").getAsString();
				
				updateSelectQuery(solrQueryForCN, tailorQueryParamCN(eachTermOfSuggestor));
				
				try {
					queryResponseForCN = solr.query(solrQueryForCN);
					
					if (queryResponseForCN != null && queryResponseForCN.getResponse() != null) {
						String testObjectForCN = new Gson().toJson(queryResponseForCN.getResponse());
						String termsManipulated = updateSelectQueryResponse(testObjectForCN, eachTermOfSuggestor);
						
						JsonObject tempJsonArrayItem = new JsonObject();
						tempJsonArrayItem.addProperty("term", termsManipulated);
						tempJsonArrayItem.addProperty("weight", 0);
						tempJsonArrayItem.addProperty("payload", "");
						tempJsonArray.add(tempJsonArrayItem);

						convertedObject.add("mySuggester", tempJsonArray);
						
						
					}
				} catch (SolrServerException e) {
					LOG.error("Solr Exception has occured. {}", e);
					System.out.println(e);
				} catch (IOException e) {
					LOG.error("IO Exception has occured. {}", e);
				} catch (ClassCastException e) {
					LOG.error("Class cast exception.", e);
				}
				
			}

			convertedObject.add(CommonConstants.SUGGESTION, getSpellKeyword(solr, queryParam));
			

		} catch (SolrServerException e) {
			LOG.error("Solr Exception has occured. {}", e);
			convertedObject.addProperty("Exception", "Solr Exception Occured");
			System.out.println(e);
		} catch (IOException e) {
			LOG.error("IO Exception has occured. {}", e);
		} catch (ClassCastException e) {
			LOG.error("Class cast exception.", e);
		}
		return convertedObject;
	}
	
	/**
	 * this method will tailor the query parameter by removing bold and replacing the space
	 * 
	 * @param string
	 * 
	 * @return string
	 */
	private String tailorQueryParamCN(String sentenceWithBold) {
    	String sentenceWithoutBold = sentenceWithBold;
		sentenceWithoutBold = sentenceWithoutBold.replaceAll("<b>", "");
		sentenceWithoutBold = sentenceWithoutBold.replaceAll("</b>", "");
		sentenceWithoutBold = sentenceWithoutBold.replaceAll(" ", "%20");
		
		return sentenceWithoutBold;
	}


	/**
	 * this method will form the complete map
	 * 
	 * @param request
	 * @param resourceResolver
	 * 
	 * @return map with all the keyword values
	 */
	public Map<String, String> keywordMapFormation(SlingHttpServletRequest request, ResourceResolver resourceResolver) {

		String keywordURL = request.getParameter("datapath");
		Map<String, String> formationMap = new HashMap<>();
		LOG.debug("page keywordURL {}", keywordURL);
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		Resource rootRes = pageManager.getPage(keywordURL) != null
				? pageManager.getPage(keywordURL).getContentResource()
				: null;
		if (null != rootRes) {
			addingOptions(resourceResolver, formationMap, rootRes);
		}
		return formationMap;
	}

	/**
	 * method to add values in hashMap
	 * 
	 * @param resourceResolver
	 * @param formationMap
	 * @param rootRes
	 */
	public void addingOptions(ResourceResolver resourceResolver, Map<String, String> formationMap, Resource rootRes) {
		Iterator<Resource> resList = rootRes.listChildren();
		Resource keywordResource = getKeywordsResource(resList);
		if (null != keywordResource) {
			Resource keyOptions = keywordResource.getChild("keywordOptions");
			if (null != keyOptions) {
				Iterator<Resource> optionList = keyOptions.listChildren();
				while (optionList.hasNext()) {
					Resource childItem = optionList.next();
					KeywordsModel km = childItem.adaptTo(KeywordsModel.class);

					formationMap.put(km.getKeyword(),
							externalizerService.getFormattedUrl(km.getUrlRedirect(), resourceResolver));
					LOG.debug("authored key {} and value {}", km.getKeyword(),
							externalizerService.getFormattedUrl(km.getUrlRedirect(), resourceResolver));
				}
			}
		}
	}

	/**
	 * This recursive method returns keyword component resource
	 * 
	 * @param item
	 * 
	 * @return
	 */
	private Resource getKeywordsResource(Iterator<Resource> item) {
		while (item.hasNext()) {
			Resource temp = item.next();
			if (temp.isResourceType("bdb-aem/proxy/components/content/keywords")) {
				res = temp;
				break;
			} else {
				getKeywordsResource(temp.listChildren());
			}
		}
		return res;
	}

	/**
	 * constructing solr Query for spell check
	 * 
	 * @param solr
	 * @param queryParam
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	private JsonArray getSpellKeyword(HttpSolrClient solr, String queryParam) throws SolrServerException, IOException {

		SolrQuery solrQuery = new SolrQuery();
		String keyword = URLDecoder.decode(queryParam, StandardCharsets.UTF_8.toString());
		solrQuery.setRequestHandler(CommonConstants.SPELL_SELECTOR);
		solrQuery.setParam(CommonConstants.DF_KEYWORD, CommonConstants.SPELLCHECK_TEXT);
		solrQuery.setParam(CommonConstants.SPELLCHECK_Q, keyword);
		solrQuery.setParam("fl", "bdbiospellchecker_text_mv");
		solrQuery.setParam(CommonConstants.SPELLCHECK, CommonConstants.ON);
		solrQuery.setParam(CommonConstants.WT_KEYWORD, CommonConstants.JSON);
		QueryResponse spellQueryResponse = solr.query(solrQuery);
		return getResponse(spellQueryResponse);
	}

	/**
	 * modifying the spell check response
	 * 
	 * @param spellQueryResponse
	 * @return
	 */
	private JsonArray getResponse(QueryResponse spellQueryResponse) {

		List<Collation> suggesterResponse = spellQueryResponse.getSpellCheckResponse().getCollatedResults();
		JsonArray alternative = new JsonArray();
		if (CollectionUtils.isNotEmpty(suggesterResponse)) {
			for (Collation collation : suggesterResponse) {
				JsonObject alternativeObject = new JsonObject();
				StringBuilder sb = new StringBuilder();
				for (Correction correction : collation.getMisspellingsAndCorrections()) {
					sb.append(correction.getCorrection());
					sb.append(CommonConstants.SPACE);
				}
				alternativeObject.addProperty(CommonConstants.WORD, sb.toString().trim());
				alternative.add(alternativeObject);
			}
		}
		return alternative;
	}

	/**
	 * Updates the query with params required
	 *
	 * @param solrQuery solrQuery object
	 * @param query     params that needs to be updated
	 */
	private void updateSelectQuery(SolrQuery solrQueryForCN, String queryParam) {
		solrQueryForCN.setRequestHandler(SolrSearchConstants.SELECT_HANDLER);
		solrQueryForCN.setParam("fl", "uniqueIdentifier,docType_t");
		solrQueryForCN.setParam("wt", "json");
		solrQueryForCN.setParam(SolrSearchConstants.SELECT_QUERY_PARAM, "bdbiospellchecker_text_mv:" + "\""+queryParam+"\"");
	}
	
	private void updateSuggestQuery(SolrQuery solrQuery, String queryParam) {
        solrQuery.setRequestHandler(SolrSearchConstants.SUGGEST_HANDLER);
        solrQuery.setParam(SolrSearchConstants.SUGGEST, Boolean.TRUE);
        solrQuery.setParam(SolrSearchConstants.SUGGEST_DICTIONARY, "mySuggester");
        solrQuery.setParam("wt", "json");
        solrQuery.setParam(SolrSearchConstants.SUGGEST_QUERY_PARAM, queryParam);
        solrQuery.setParam(SolrSearchConstants.SUGGEST_CFQ, Boolean.TRUE);
    }

	//Manipulate the query response
	/**
	 * this method will update the terms by concatenating the catalog number to it
	 * 
	 * @param string
	 * @param string
	 * 
	 * @return string
	 */
	private String updateSelectQueryResponse(String returnedResponseToParse, String eachTermOfSuggestor) {
		String termsFinalVal = eachTermOfSuggestor;
		String isProduct = "";
		String uniqueIdentifier = "";
		try {
			JsonObject json = new JsonParser().parse(returnedResponseToParse).getAsJsonObject();
			JsonArray resultAsJsonObject = json.getAsJsonArray("nvPairs");
			resultAsJsonObject.remove(1);
			resultAsJsonObject = (JsonArray) resultAsJsonObject.get(2);
			
			
			if(resultAsJsonObject.size() == 1) {
				JsonObject explrObject = (JsonObject) resultAsJsonObject.get(0);
				isProduct = explrObject.get("docType_t").getAsString();
				uniqueIdentifier = explrObject.get("uniqueIdentifier").getAsString();

				
				if (isProduct.equalsIgnoreCase("product"))
					termsFinalVal = eachTermOfSuggestor + " " + uniqueIdentifier;

				termsFinalVal = termsFinalVal.replaceAll("\"", "");
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return termsFinalVal;
	}
}
