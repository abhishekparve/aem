package com.bdb.aem.core.servlets.solr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Correction;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.models.BoostrulesModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.EligibleProductsApiService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.commons.jcr.JcrConstants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * The Servlet DeleteIndexesFromSolr.
 */
@Component(service = Servlet.class, immediate = true, property = { Constants.SERVICE_DESCRIPTION + "=" + "Fetch Data",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + FetchingDataFromSolr.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON })
@Designate(ocd = FetchingDataFromSolr.Configuration.class)
public class FetchingDataFromSolr extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1L;

	transient Logger logger = LoggerFactory.getLogger(FetchingDataFromSolr.class);

	public static final String RESOURCE_TYPE = "bdb/fetch-data";

	@Reference
	transient BDBSearchEndpointService solrConfig;

	@Reference
	transient SolrSearchService solrSearchService;

	@Reference
	transient ExternalizerService externalizerService;

	@Reference
	transient ResourceResolverFactory resourceResolverFactory;

	@Reference
	transient EligibleProductsApiService eligibleProductsApiService;

	@Reference
	transient BDBApiEndpointService bdbApiEndpointService;

	@Reference
	transient CatalogStructureUpdateService catalogStructureUpdateService;

	/** The indexes to be queried. */
	private String[] specialCharactersToBeModified;

	transient Resource boostrulesRes = null;
	transient JsonObject queryResponseJson = new JsonObject();
	transient JsonObject finalResponseJson = new JsonObject();

	public static final String SOLR_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/**
	 * doPost method which quires the data from solr and sends in response
	 *
	 * @param request  param
	 * @param response response param
	 * @throws IOException
	 */
	@Override
	protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		long startTime = System.currentTimeMillis();
		ResourceResolver resourceResolver = null;
		String apiResponse = null;
		JsonObject requestObject = new JsonObject();
		String contentCollection = solrConfig.getContentPageCollectionName();
		String jsonString = getRequestJsonString(request);
		String country = getrequestAttribute(jsonString, CommonConstants.COUNTRY);
		String language = getrequestAttribute(jsonString, CommonConstants.LANGUAGE);
		if (!contentCollection.isEmpty()) {
			HttpSolrClient server = solrSearchService.solrClient(country);
			try {
				resourceResolver = request.getResourceResolver();
				JsonObject facetJson = getrequestFacetJson(jsonString);
				String promocode = facetJson.get(CommonConstants.ID) != null
						? facetJson.get(CommonConstants.ID).getAsString()
						: StringUtils.EMPTY;
				RequestPathInfo requestPathInfo = request.getRequestPathInfo();
				String[] selectors = requestPathInfo.getSelectors();
				logger.debug("selectors {}", selectors.length);

				if (selectors.length > 0 && StringUtils.equals(selectors[0], CommonConstants.PROMODETAILS)
						&& StringUtils.isNotBlank(promocode)) {
					promocode = StringUtils.substringAfter(promocode, ":");
					logger.debug("hybrisResponse started");
					String hybrisResponse = eligibleProductsApiService.fetchPromoDetails(request, requestObject,
							response, promocode);
					logger.debug("hybrisResponse end");
					if (validateHybrisResponse(hybrisResponse)) {
						SolrQuery solrQuery = makeSolrQueryFromHybrisResponse(hybrisResponse, facetJson,
								resourceResolver, language);
						logger.debug("Solr Query Hybris {}", solrQuery);
						constructingResponse(resourceResolver, server, facetJson, solrQuery, country,
								catalogStructureUpdateService);
						apiResponse = finalResponseJson.toString();
					} else {
						apiResponse = hybrisResponse;
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					}
				} else if (selectors.length > 0
						&& StringUtils.equals(selectors[0], CommonConstants.COMPANION_DETAILS_SELECTOR)) {
					promocode = StringUtils.substringAfter(promocode, ":");
					JsonArray categoryJsonArray = new JsonArray();
					String[] categorySplit = promocode.split("_");
					for (String str : categorySplit) {
						categoryJsonArray.add(str);
					}
					SolrQuery solrQuery = makeSolrQueryforCompanionProducts(facetJson, categoryJsonArray,
							resourceResolver, language);
					logger.debug("Solr Query Companion Products {}", solrQuery);
					constructingResponse(resourceResolver, server, facetJson, solrQuery, country,
							catalogStructureUpdateService);
					apiResponse = finalResponseJson.toString();

				} else {
					SolrQuery solrQuery = getSolrQuery(facetJson, resourceResolver, language);
					logger.debug("Solr Query Search Results Solr Query {}, Solr Query with facets {}",
							solrQuery.getQuery(), solrQuery);
					constructingResponse(resourceResolver, server, facetJson, solrQuery, country,
							catalogStructureUpdateService);
					logger.debug("FetchingDataFromSolr constructingResponse TIME - {}",
							System.currentTimeMillis() - startTime);
					// spell check start
					getspellCheckResponse(server, facetJson);
					logger.debug("FetchingDataFromSolr getspellCheckResponse TIME - {}",
							System.currentTimeMillis() - startTime);
					// spell check end
					apiResponse = finalResponseJson.toString();
				}
			} catch (IOException e) {
				apiResponse = finalResponseJson.toString();
				response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
				logger.error("Login exception Occurred during execution {}", e);
			} catch (SolrServerException | HttpSolrClient.RemoteSolrException e) {
				apiResponse = finalResponseJson.toString();
				response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
				logger.error("solr exception has occured {}", e);
			} catch (AemInternalServerErrorException e) {
				apiResponse = finalResponseJson.toString();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				logger.error("Exception Occurred during rest client execution {}", e);
			} catch (RepositoryException e) {
				apiResponse = finalResponseJson.toString();
				response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
				logger.error("RepositoryException Occurred during HP Resource from Lookup {}", e);
			} finally {
				server.close();
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				response.setHeader("Expires", "0"); // Proxies.
				response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
				response.setCharacterEncoding("UTF-8");
				long endTime = System.currentTimeMillis();
				logger.debug("FetchingDataFromSolr TIME - {}", endTime - startTime);
				response.getWriter().write(apiResponse);
			}
		}
	}

	/**
	 * validating if the hybris response contains any error or not
	 *
	 * @param hybrisResponse
	 * @return
	 */
	private boolean validateHybrisResponse(String hybrisResponse) {
		boolean flag = false;
		if (StringUtils.isNotBlank(hybrisResponse)) {
			JsonObject hybrisJson = new Gson().fromJson(hybrisResponse, JsonObject.class);
			JsonArray errorJson = hybrisJson.getAsJsonArray("errors");
			if (null == errorJson) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * returns the json with the spell check response
	 *
	 * @param server
	 * @param facetJson
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void getspellCheckResponse(HttpSolrClient server, JsonObject facetJson)
			throws SolrServerException, IOException {
		SolrQuery spellSolrQuery = getSpellCheckSolrQuery(facetJson);
		QueryResponse spellQueryResponse = server.query(spellSolrQuery);
		List<Collation> suggesterResponse = null != spellQueryResponse.getSpellCheckResponse()
				? spellQueryResponse.getSpellCheckResponse().getCollatedResults()
				: Collections.emptyList();
		JsonArray alternative = new JsonArray();
		if (!suggesterResponse.isEmpty()) {
			List<Correction> alternatives = suggesterResponse.get(0).getMisspellingsAndCorrections();
			StringBuilder sb = new StringBuilder();
			for (Correction correction : alternatives) {
				sb.append(correction.getCorrection());
				sb.append(CommonConstants.SPACE);
			}
			JsonObject alternativeObject = new JsonObject();
			alternativeObject.addProperty(CommonConstants.WORD, sb.toString().trim());
			alternative.add(alternativeObject);
		}
		queryResponseJson.add(CommonConstants.SUGGESTION, alternative);
		finalResponseJson.add(CommonConstants.RESPONSE, queryResponseJson);

	}

	/**
	 * adding parameters for solr spell check
	 *
	 * @param facetJson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private SolrQuery getSpellCheckSolrQuery(JsonObject facetJson) throws UnsupportedEncodingException {
		SolrQuery solrQuery = new SolrQuery();
		if (facetJson.get(CommonConstants.ID) != null) {
			String decodedKeyword = URLDecoder.decode(facetJson.get(CommonConstants.ID).getAsString(),
					StandardCharsets.UTF_8.toString());
			String keyword = StringUtils.substringAfter(decodedKeyword, CommonConstants.NAME_KEYWORD);

			solrQuery.setRequestHandler(CommonConstants.SPELL_SELECTOR);
			solrQuery.setParam(CommonConstants.DF_KEYWORD, CommonConstants.SPELLCHECK_TEXT);
			solrQuery.setParam(CommonConstants.SPELLCHECK_Q, keyword);
			solrQuery.setParam(CommonConstants.SPELLCHECK, CommonConstants.ON);
			solrQuery.setParam(CommonConstants.WT_KEYWORD, CommonConstants.JSON);
		}

		return solrQuery;
	}

	/**
	 * transforming the solr response
	 *
	 * @param resourceResolver
	 * @param server
	 * @param facetJson
	 * @param solrQuery
	 * @param catalogStructureUpdateService2
	 * @throws SolrServerException
	 * @throws IOException
	 * @throws RepositoryException
	 */
	public void constructingResponse(ResourceResolver resourceResolver, HttpSolrClient server, JsonObject facetJson,
			SolrQuery solrQuery, String country, CatalogStructureUpdateService catalogStructureUpdateService2)
			throws SolrServerException, IOException, RepositoryException {
		QueryResponse sitesQueryResponse = server.query(solrQuery);
		SolrDocumentList sitesSolrDocs = sitesQueryResponse.getResults();
		long totalRecords = sitesSolrDocs.getNumFound();
		JsonArray results = getResultsJson(sitesSolrDocs, resourceResolver, country, catalogStructureUpdateService2);
		List<FacetField> sitesFacetFieldList = sitesQueryResponse.getFacetFields();
		JsonObject sitesFacetObject = getFacetFields(sitesFacetFieldList);
		formFinalJson(facetJson, sitesSolrDocs, results, sitesFacetObject, totalRecords);
		finalResponseJson.add(CommonConstants.RESPONSE, queryResponseJson);

	}

	/**
	 * initial step to make the solr query on hybris response
	 *
	 * @param hybrisResponse
	 * @param facetJson
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private SolrQuery makeSolrQueryFromHybrisResponse(String hybrisResponse, JsonObject facetJson,
			ResourceResolver resourceResolver, String language) throws UnsupportedEncodingException {
		SolrQuery solrQuery = null;
		if (StringUtils.isNotBlank(hybrisResponse)) {
			JsonObject hybrisJson = new Gson().fromJson(hybrisResponse, JsonObject.class);
			if (hybrisJson != null) {
				JsonArray applicableCategoriesArray = hybrisJson.getAsJsonArray(CommonConstants.APPLICABLECATEGORIES);
				JsonArray applicableProductsArray = hybrisJson.getAsJsonArray(CommonConstants.APPLICABLEPRODUCTS);
				JsonArray excludedProductsArray = hybrisJson.getAsJsonArray(CommonConstants.EXCLUDEDPRODUCTS);
				if (null != applicableCategoriesArray || null != applicableProductsArray
						|| null != excludedProductsArray) {

					solrQuery = new SolrQuery(CommonConstants.Q_KEYWORD, "isAvailable_t:true");
					formFilterQueryForPromoDetails(solrQuery, applicableCategoriesArray, applicableProductsArray,
							excludedProductsArray);

					JsonArray facetfields = facetJson.getAsJsonArray(CommonConstants.FACET_FIELDS);
					if (null != facetfields && facetfields.size() > 0) {
						formFacetFieldQuery(solrQuery, facetfields);
					}

					JsonArray selectedfields = facetJson.getAsJsonArray(CommonConstants.SELECTED_FIELDS);
					if (null != selectedfields && selectedfields.size() > 0) {
						formFilterQuery(solrQuery, selectedfields);
					}

					addOtherParameters(facetJson, solrQuery, CommonConstants.PROMOTIONDETAILS, resourceResolver,
							language);

				}
			}
		}
		return solrQuery;
	}

	/**
	 * Make Solr Query Companion Products
	 * 
	 * @param facetJson
	 * @param categoryJsonArray
	 * @return
	 */
	private SolrQuery makeSolrQueryforCompanionProducts(JsonObject facetJson, JsonArray categoryJsonArray,
			ResourceResolver resourceResolver, String language) {
		SolrQuery solrQuery = new SolrQuery(CommonConstants.Q_KEYWORD, "isAvailable_t:true");
		JsonArray facetfields = facetJson.getAsJsonArray(CommonConstants.FACET_FIELDS);
		if (null != facetfields) {
			formFacetFieldQuery(solrQuery, facetfields);
		}

		JsonArray selectedfields = facetJson.getAsJsonArray(CommonConstants.SELECTED_FIELDS);
		if (null != selectedfields) {
			try {
				formFilterQuery(solrQuery, selectedfields);
			} catch (UnsupportedEncodingException e) {
				logger.error("Exception in makeSolrQueryforCompanionProducts ", e);
			}
		}
		formFilterQueryForCompanionDetails(solrQuery, categoryJsonArray);
		addOtherParameters(facetJson, solrQuery, CommonConstants.PROMOTIONDETAILS, resourceResolver, language);
		return solrQuery;
	}

	/**
	 * Method to Filter Query for Companion Details
	 * 
	 * @param solrQuery
	 * @param categoryJsonArray
	 */
	private void formFilterQueryForCompanionDetails(SolrQuery solrQuery, JsonArray categoryJsonArray) {
		if (null != categoryJsonArray) {
			StringBuilder filterQueryStr = new StringBuilder();
			for (int i = 0; i < categoryJsonArray.size(); i++) {
				if (null != categoryJsonArray.get(i)) {
					filterQueryStr.append("materialNumber_t : ");
				}
				filterQueryStr.append(categoryJsonArray.get(i).getAsString());
				filterQueryStr.append(CommonConstants.SPACE);
				if (i < categoryJsonArray.size() - 1) {
					filterQueryStr.append(CommonConstants.OR_KEYWORD);
					filterQueryStr.append(CommonConstants.SPACE);
				}
			}
			solrQuery.addFilterQuery(filterQueryStr.toString());

		}
	}

	/**
	 * verifying the hybris respone and adding them to solr query
	 *
	 * @param solrQuery
	 * @param applicableCategoriesArray
	 * @param applicableProductsArray
	 * @param excludedProductsArray
	 */
	private void formFilterQueryForPromoDetails(SolrQuery solrQuery, JsonArray applicableCategoriesArray,
			JsonArray applicableProductsArray, JsonArray excludedProductsArray) {

		if (null != applicableCategoriesArray && applicableCategoriesArray.size() > 0) {
			constructSolrFilterQueryStr(solrQuery, applicableCategoriesArray, CommonConstants.APPLICABLECATEGORIES);
		}
		if (null != applicableProductsArray && applicableProductsArray.size() > 0) {
			constructSolrFilterQueryStr(solrQuery, applicableProductsArray, CommonConstants.APPLICABLEPRODUCTS);
		}
		if (null != excludedProductsArray && excludedProductsArray.size() > 0) {
			constructSolrFilterQueryStr(solrQuery, excludedProductsArray, CommonConstants.EXCLUDEDPRODUCTS);
		}

	}

	/**
	 * constructing a solr query for the hybris response
	 *
	 * @param solrQuery
	 * @param applicableCategoriesArray
	 * @throws UnsupportedEncodingException
	 */
	public void constructSolrFilterQueryStr(SolrQuery solrQuery, JsonArray applicableCategoriesArray,
			String categoryType) {
		StringBuilder filterQueryStr = new StringBuilder();
		for (int i = 0; i < applicableCategoriesArray.size() - 1; i++) {

			if (categoryType.equals(CommonConstants.APPLICABLECATEGORIES)) {
				checkPromoCategory(applicableCategoriesArray, filterQueryStr, i);
			} else if (categoryType.equals(CommonConstants.APPLICABLEPRODUCTS)) {
				filterQueryStr.append(CommonConstants.MATERIAL_NUMBER.concat(SolrSearchConstants.UNDERSCORE_T)
						.concat(CommonConstants.COLON));
			} else if (categoryType.equals(CommonConstants.EXCLUDEDPRODUCTS)) {
				filterQueryStr.append(CommonConstants.HYPHEN.concat(CommonConstants.MATERIAL_NUMBER
						.concat(SolrSearchConstants.UNDERSCORE_T).concat(CommonConstants.COLON)));
			}
			filterQueryStr.append(applicableCategoriesArray.get(i).getAsString());
			filterQueryStr.append(CommonConstants.SPACE);
			filterQueryStr.append(CommonConstants.OR_KEYWORD);
			filterQueryStr.append(CommonConstants.SPACE);
		}
		if (applicableCategoriesArray.size() >= 1) {
			if (categoryType.equals(CommonConstants.APPLICABLECATEGORIES)) {
				checkPromoCategory(applicableCategoriesArray, filterQueryStr, applicableCategoriesArray.size() - 1);
			} else if (categoryType.equals(CommonConstants.APPLICABLEPRODUCTS)) {
				filterQueryStr.append(CommonConstants.MATERIAL_NUMBER.concat(SolrSearchConstants.UNDERSCORE_T)
						.concat(CommonConstants.COLON));
			} else if (categoryType.equals(CommonConstants.EXCLUDEDPRODUCTS)) {
				filterQueryStr.append(CommonConstants.HYPHEN.concat(CommonConstants.MATERIAL_NUMBER
						.concat(SolrSearchConstants.UNDERSCORE_T).concat(CommonConstants.COLON)));
			}
			filterQueryStr.append(applicableCategoriesArray.get(applicableCategoriesArray.size() - 1).getAsString());
		}
		solrQuery.addFilterQuery(filterQueryStr.toString());
	}

	/**
	 * check for the category type in the hybris response and prefixes the
	 * appropriate category
	 *
	 * @param applicableCategoriesArray
	 * @param filterQueryStr
	 * @param i
	 */
	public void checkPromoCategory(JsonArray applicableCategoriesArray, StringBuilder filterQueryStr, int i) {
		if (applicableCategoriesArray.get(i).getAsString().contains(CommonConstants.MPG_KEYWORD)) {
			filterQueryStr.append(CommonConstants.MPGCATEGORY + SolrSearchConstants.UNDERSCORE_TXT);
		} else if (applicableCategoriesArray.get(i).getAsString().contains(CommonConstants.CATEGORY_KEYWORD)) {
			filterQueryStr.append(CommonConstants.CLASSIFICATION_CATEGORY + SolrSearchConstants.UNDERSCORE_TXT);
		}
	}

	/**
	 * adding all the response to a queryResponse JSON
	 *
	 * @param facetJson
	 * @param sitesSolrDocs
	 * @param results
	 * @param sitesFacetObject
	 * @param totalRecords
	 */
	public void formFinalJson(JsonObject facetJson, SolrDocumentList sitesSolrDocs, JsonArray results,
			JsonObject sitesFacetObject, long totalRecords) {
		queryResponseJson.addProperty(CommonConstants.TOTALRESULTS, totalRecords);
		queryResponseJson.addProperty(CommonConstants.START,
				null != facetJson.get(CommonConstants.START) ? facetJson.get(CommonConstants.START).getAsInt() : 0);
		queryResponseJson.add(CommonConstants.RESULTS, results);
		queryResponseJson.add(CommonConstants.FACETS, sitesFacetObject);
	}

	/**
	 * returns the facets from json response string
	 *
	 * @param jsonString
	 * @return
	 */
	public JsonObject getrequestFacetJson(String jsonString) {
		JsonObject jsonObj = new JsonParser().parse(jsonString).getAsJsonObject();
		return jsonObj.getAsJsonObject(CommonConstants.FACETS);
	}

	/**
	 * Gets the request attribute.
	 *
	 * @param jsonString the json string
	 * @param attribute  the attribute
	 * @return the request attribute
	 */
	public String getrequestAttribute(String jsonString, String attribute) {
		JsonObject jsonObj = new JsonParser().parse(jsonString).getAsJsonObject();
		return jsonObj.get(attribute).getAsString().toLowerCase();
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
	 * adding all the solr facet resopnse to the json.
	 *
	 * @param facetFieldList
	 * @return
	 */
	public JsonObject getFacetFields(List<FacetField> facetFieldList) {
		JsonObject facetObject = new JsonObject();
		if (null != facetFieldList) {
			Iterator<FacetField> facetItr = facetFieldList.iterator();
			while (facetItr.hasNext()) {
				JsonArray innerFacetArray = new JsonArray();
				FacetField next = facetItr.next();
				String name = next.getName();
				if (name.equals("brand_facet_s")) {
					next.getValues().sort((h1, h2) -> h1.getName().replaceAll("[^\\w\\s]", "").compareTo(h2.getName().replaceAll("[^\\w\\s]", "")));
				}
				facetObject.add(name, getInnerFacetArray(innerFacetArray, next));
			}
		}
		return facetObject;
	}

	/**
	 * adding all the solr facet resopnse to the json.
	 *
	 * @param innerFacetArray
	 * @param next
	 */
	public JsonArray getInnerFacetArray(JsonArray innerFacetArray, FacetField next) {
		List<Count> values = next.getValues();
		// Moving the Videos to last item under Result Type facet - GE-27248
		if (next.getName().equals("docType_facet_s")) {
			// reversing the facet order for docType - GE-18644
			Collections.reverse(values);
			JsonObject productsJson = null;
			JsonObject videosJson = null;
			for (Count item : values) {
				if (item.getCount() > 0) {
					if(!item.getName().contains("Videos")) {
						productsJson = new JsonObject();
						productsJson.addProperty(CommonConstants.LABEL, item.getName());
						productsJson.addProperty(CommonConstants.COUNT, item.getCount());
						innerFacetArray.add(productsJson);
		            } else {
		            	videosJson = new JsonObject();
		            	videosJson.addProperty(CommonConstants.LABEL, item.getName());
		            	videosJson.addProperty(CommonConstants.COUNT, item.getCount());
		            }
				}
			}
			if(null != videosJson) {
				innerFacetArray.add(videosJson);
			}
		} else if (next.getName().equals("speciesReactivity_facet_ss")) {
			innerFacetArray = getTargetSpeciesFacetArray(values);
		} else if (next.getName().equals("releaseDate_facet_s")) {
			getSortedDates(values);
			for (Count item : values) {
				if (item.getCount() > 0) {
					String checkDate = item.toString();
					checkDate = checkDate.substring(0,checkDate.lastIndexOf(" "));
					Boolean canAdd = getReleaseDate(checkDate);
					if (canAdd == true) {
						JsonObject countObject = new JsonObject();
						countObject.addProperty(CommonConstants.LABEL, item.getName());
						countObject.addProperty(CommonConstants.COUNT, item.getCount());
						innerFacetArray.add(countObject);
					}
				}
			}
		} else {
			for (Count item : values) {
				if (item.getCount() > 0) {
					JsonObject countObject = new JsonObject();
					countObject.addProperty(CommonConstants.LABEL, item.getName());
					countObject.addProperty(CommonConstants.COUNT, item.getCount());
					innerFacetArray.add(countObject);
				}
			}
		}
		return innerFacetArray;
	}

	/**
	 * Sorts the product release date in desc order.
	 *
	 * @param values   solr response containing release dates
	 */
	public void getSortedDates(List<Count> values) {
		Collections.sort(values, new Comparator<Count>() {
			public int compare(Count o1, Count o2) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
					return (-1)*(sdf.parse(o1.toString()).compareTo(sdf.parse(o2.toString())));
				}
				catch (ParseException e) {
					return o1.toString().compareTo(o2.toString());
				}
			}
		});
	}

	/**
	 * Validates if product is not more than 6 months old.
	 *
	 * @param releaseDate    the product release date
	 * @return inRange       the boolean value
	 */
	public Boolean getReleaseDate(String releaseDate) {
		Boolean inRange = false;
		try {
			String sixMonthsAgo = LocalDateTime.now().minusMonths(6).toString();
			sixMonthsAgo = sixMonthsAgo.substring(0, sixMonthsAgo.indexOf("T"));
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date changeDateFormat = (Date)formatter.parse(sixMonthsAgo);
			SimpleDateFormat newFormat = new SimpleDateFormat("MMM yyyy");
			String finalDate = newFormat.format(changeDateFormat);
			Date date1 = new SimpleDateFormat("MMM yyyy").parse(finalDate);
			Date date2 = new SimpleDateFormat("MMM yyyy").parse(releaseDate);
			if (date2.compareTo(date1) >= 0) {
				inRange = true;
			}
		}
		catch(ParseException e) {
			logger.error("Error occurred in getReleaseDate", e.getMessage());
		}
		return inRange;
	}
	
	/**
	 * 
	 * @param values
	 * @return
	 */
	public JsonArray getTargetSpeciesFacetArray(List<Count> values) {
		JsonArray targetSpeciesFacetArray = new JsonArray();
		JsonObject humanMouseFacetObject;
		JsonObject withoutHumanFacetObject;
		// this loop is to maintain human & mouse json obj
		for (Count item : values) {
			humanMouseFacetObject = new JsonObject();
			if (item.getCount() > 0) {
				if(item.getName().equalsIgnoreCase("Human") || item.getName().equalsIgnoreCase("Mouse")){
					humanMouseFacetObject.addProperty(CommonConstants.LABEL, item.getName());
					humanMouseFacetObject.addProperty(CommonConstants.COUNT, item.getCount());
				}
			}
			if(humanMouseFacetObject.size() > 0) {
				targetSpeciesFacetArray.add(humanMouseFacetObject);
			}
		}
		// this loop is to handle in general except human & mouse
		for (Count item : values) {
			withoutHumanFacetObject = new JsonObject();
			if (item.getCount() > 0) {
				if((!item.getName().equalsIgnoreCase("Human")) && (!item.getName().equalsIgnoreCase("Mouse"))){
					withoutHumanFacetObject.addProperty(CommonConstants.LABEL, item.getName());
					withoutHumanFacetObject.addProperty(CommonConstants.COUNT, item.getCount());
				}				
			}
			if(withoutHumanFacetObject.size() > 0) {
				targetSpeciesFacetArray.add(withoutHumanFacetObject);
			}
		}
		return targetSpeciesFacetArray;
	}
	
	/**
	 * returns the final JSON response
	 *
	 * @param solrDocs
	 * @param resourceResolver
	 * @param catalogStructureUpdateService2
	 * @return
	 * @throws RepositoryException
	 */
	@SuppressWarnings("unused")
	public JsonArray getResultsJson(SolrDocumentList solrDocs, ResourceResolver resourceResolver, String country,
			CatalogStructureUpdateService catalogStructureUpdateService2) throws RepositoryException {
		Iterator<SolrDocument> iterator = solrDocs.iterator();

		JsonArray results = new JsonArray();

		while (iterator.hasNext()) {
			SolrDocument solrDocument = iterator.next();
			Iterator<Entry<String, Object>> itr = solrDocument.iterator();
			JsonObject facetobject = new JsonObject();

			while (itr.hasNext()) {
				Entry<String, Object> map = itr.next();
				facetobject.add(map.getKey(), new Gson().toJsonTree(map.getValue()));
			}

			if (facetobject.get("id") != null && facetobject.get("brand_t") != null
					&& facetobject.get("thumbnailImage_t") != null && null != country) {
				String thumbnailImagePath = facetobject.get("thumbnailImage_t").toString().replace("\"", "");
				facetobject.addProperty("thumbnailImage_t", thumbnailImagePath);
				String productUrl = facetobject.get("id").toString().replace("\"", "");
				String url = StringUtils.EMPTY;
				if (StringUtils.isNotBlank(productUrl)) {
					String rummode = CommonHelper.getRunMode(bdbApiEndpointService);
					if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.AUTHOR)) {
						url = CommonConstants.CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.BDB
								+ CommonConstants.SINGLE_SLASH + "{region}" + CommonConstants.SINGLE_SLASH + "{country}"
								+ CommonConstants.SINGLE_SLASH + "{language-country}" + productUrl;
					} else if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.PUBLISH)) {
						url = CommonConstants.SINGLE_SLASH + "{language-country}" + productUrl.replace("pdp.", "");
					}

					facetobject.addProperty("id", externalizerService.getFormattedUrl(url, resourceResolver));
				}
			} else if (facetobject.get("id") != null && facetobject.get("country_t") != null) {
				String url = externalizerService.getFormattedUrl(facetobject.get("id").getAsString(), resourceResolver);
				facetobject.addProperty("id", url);
			}

			// Adding Flag for Compare Products
			if (facetobject.getAsJsonObject().has(CommonConstants.PDP_TEMPLATE_SOLR) && facetobject.getAsJsonObject().has(CommonConstants.TDS_CLONE_NAME_SOLR)) {
				String templateId = facetobject.getAsJsonObject().get(CommonConstants.PDP_TEMPLATE_SOLR).getAsString();
				String cloneID = facetobject.getAsJsonObject().get(CommonConstants.TDS_CLONE_NAME_SOLR).getAsString();;
				facetobject.addProperty(CommonConstants.IS_COMPARABLE,
						((StringUtils.equals("SFA", templateId) || StringUtils.equals("SCM", templateId)) && StringUtils.isNotBlank(cloneID)) ? Boolean.TRUE
								: Boolean.FALSE);
			}

			results.add(facetobject);
		}

		return results;
	}

	/**
	 * formation of solar query for direct search call
	 *
	 * @param facetJson
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	private SolrQuery getSolrQuery(JsonObject facetJson, ResourceResolver resourceResolver, String language)
			throws UnsupportedEncodingException {
		SolrQuery solrQuery = new SolrQuery(CommonConstants.Q_KEYWORD, "isAvailable_t:true");

		if (null != facetJson) {
			JsonArray facetfields = facetJson.getAsJsonArray(CommonConstants.FACET_FIELDS);
			JsonArray selectedfields = facetJson.getAsJsonArray(CommonConstants.SELECTED_FIELDS);
			if (null != facetfields) {
				formFacetFieldQuery(solrQuery, facetfields);
			}
			if (null != selectedfields) {
				formFilterQuery(solrQuery, selectedfields);
			}
			if (null != solrConfig.isEdismax() && "true".equalsIgnoreCase(solrConfig.isEdismax())) {
				addEdismaxParameters(facetJson, solrQuery, CommonConstants.SEARCHRESULTS, resourceResolver, language);
			} else {
				addOtherParameters(facetJson, solrQuery, CommonConstants.SEARCHRESULTS, resourceResolver, language);
			}
		}
		solrQuery.add(CommonConstants.FACET_SORT, CommonConstants.FACET_SORT_VALUE);
		solrQuery.add(CommonConstants.SORT,CommonConstants.SORT_VALUE);
		solrQuery.add(CommonConstants.FACET_LIMIT, "-1");
		
		return solrQuery;
	}

	/**
	 * adding start,row and other parameters to the solr query
	 *
	 * @param facetJson
	 * @param solrQuery
	 * @param condition
	 * @throws UnsupportedEncodingException
	 */
	public void addEdismaxParameters(JsonObject facetJson, SolrQuery solrQuery, String condition,
			ResourceResolver resourceResolver, String language) throws UnsupportedEncodingException {
		if (StringUtils.equals(condition, CommonConstants.SEARCHRESULTS)) {

			String splChar = solrConfig.getSplCharsToReplaceSearchTerm();
			String[] splChars = null;
			if (StringUtils.isNotEmpty(splChar)) {
				splChars = splChar.split(",");
			}
			solrQuery.setQuery(getSearchTerm(facetJson, resourceResolver, splChars));
			solrQuery.setStart(
					null != facetJson.get(CommonConstants.START) ? facetJson.get(CommonConstants.START).getAsInt() : 0);
			solrQuery.setRows(
					null != facetJson.get(CommonConstants.ROWS) ? facetJson.get(CommonConstants.ROWS).getAsInt() : 0);
		}
		if (null != language && !language.equals("{}")) {
			solrQuery.add("lang", language);
		}

		StringBuilder pf = new StringBuilder();
		StringBuilder qf = new StringBuilder();
		String[] indexes = solrConfig.getIndexesToBeQueried();
		Map<String, Integer> formationMap = new HashMap<>();
		boostrulesMap(facetJson, resourceResolver, formationMap);

		String id = StringUtils.EMPTY;
		if (null != facetJson.get(CommonConstants.ID)) {
			String nameId = facetJson.get(CommonConstants.ID).getAsString();
			String[] array = nameId.split(":");
			id = array[1];
		}

		if (null != indexes && indexes.length > 0) {
			for (String index : indexes) {
				if (formationMap.containsKey(index) && formationMap.get(index) >= 0) {
					pf.append(index + "^" + formationMap.get(index).toString());
					pf.append(" ");
				} else {
					pf.append(index);
					pf.append(" ");
				}
				qf.append(index).append(" ");
			}
		}
		solrQuery.set("defType", "edismax");
		solrQuery.set("qf", pf.toString().trim());
		solrQuery.set("ps", 10);
		solrQuery.set("pf", qf.toString().trim());
		solrQuery.set("mm", 100);
		
		solrQuery.add("bq", "docType_t:web^10000000");
		solrQuery.add("bq", "docType_t:product^80000");
		solrQuery.add("bq", "docType_t:pdf^11000");
	}

	/**
	 * @param facetJson
	 * @param resourceResolver
	 * @return search term
	 */
	public String getSearchTerm(JsonObject facetJson, ResourceResolver resourceResolver, String[] splChars)
			throws UnsupportedEncodingException {
		String id = StringUtils.EMPTY;
		if (null != facetJson.get(CommonConstants.ID)) {
			String nameId = facetJson.get(CommonConstants.ID).getAsString();
			String[] array = nameId.split(":");
			id = array[1];
			id = id.replace("+", " ");
			if (null != splChars && splChars.length > 0) {
				for (String ch : splChars) {
					String decodedChar = URLDecoder.decode(ch, StandardCharsets.UTF_8.toString());
					id = id.replace(decodedChar, " ");
				}
			}
		}
		return id;
	}

	/**
	 * adding start,row and other parameters to the solr query
	 *
	 * @param facetJson
	 * @param solrQuery
	 * @param condition
	 */
	public void addOtherParameters(JsonObject facetJson, SolrQuery solrQuery, String condition,
			ResourceResolver resourceResolver, String language) {
		String id = StringUtils.EMPTY;
		if (null != facetJson.get(CommonConstants.ID)) {
			String nameId = facetJson.get(CommonConstants.ID).getAsString();
			String[] array = nameId.split(":");
			id = array[1];
		}

		if (StringUtils.equals(condition, CommonConstants.SEARCHRESULTS)) {
			if (id.equals("*")) {
				solrQuery
						.setQuery("{!edismax bf= mul(1000,ord(docType_facet_s))}" + getId(facetJson, resourceResolver));
			} else {
				solrQuery.setQuery("{!edismax bf= mul(1000,ord(docType_t))}" + getId(facetJson, resourceResolver));
			}
			solrQuery.setStart(
					null != facetJson.get(CommonConstants.START) ? facetJson.get(CommonConstants.START).getAsInt() : 0);
			solrQuery.setRows(
					null != facetJson.get(CommonConstants.ROWS) ? facetJson.get(CommonConstants.ROWS).getAsInt() : 0);
		} else if (StringUtils.equals(condition, "promotiondetails")) {
			solrQuery.setStart(
					null != facetJson.get(CommonConstants.START) ? facetJson.get(CommonConstants.START).getAsInt() : 0);
			solrQuery.setRows(
					null != facetJson.get(CommonConstants.ROWS) ? facetJson.get(CommonConstants.ROWS).getAsInt() : 0);
		}
		if (null != language && !language.equals("{}")) {
			solrQuery.add("lang", language);
		}
	}

	/**
	 * Gets the id.
	 *
	 * @param facetJson the facet json
	 * @return the id
	 *
	 */
	public String getId(JsonObject facetJson, ResourceResolver resourceResolver) {
		String id = StringUtils.EMPTY;
		Map<String, Integer> formationMap = new HashMap<>();
		if (null != facetJson.get(CommonConstants.ID)) {
			boostrulesMap(facetJson, resourceResolver, formationMap);
			String nameId = facetJson.get(CommonConstants.ID).getAsString();
			String[] array = nameId.split(":");
			String searchKeyword = array[1];
			searchKeyword = searchKeyword.replace("+", " ");
			searchKeyword = searchKeyword.replaceAll("( )+", " ");
			searchKeyword = searchKeyword.replace(" ", " +");
			boolean isNumeric;
			try {
				Integer.parseInt(searchKeyword);
				isNumeric = true;
			} catch (NumberFormatException e) {
				isNumeric = false;
			}
			String[] indexes = solrConfig.getIndexesToBeQueried();
			String[] splChars = specialCharactersToBeModified;
			if (null != splChars && splChars.length > 0) {
				for (int i = 0; i < splChars.length; i++) {
					if (searchKeyword.contains(splChars[i])) {
						searchKeyword = searchKeyword.replace(splChars[i], "\\" + splChars[i]);
					}
				}
			}
			int i = 0;
			for (String index : indexes) {
				i++;
				if (index.equals(CommonConstants.LASER_WAVELENGTH + SolrSearchConstants.UNDERSCORE_IS) && !isNumeric) {
					continue;
				}
				if (formationMap.containsKey(index) && formationMap.get(index) >= 0) {
					if (i <= indexes.length) {
						id = StringUtils.isNotEmpty(id) ? id.concat(" OR ") : "";
					}
					// Commenting the specificity edge case search since it is not required.
//					if("specificity_t".equalsIgnoreCase(index) && !searchKeyword.trim().contains(" ")) {
//						id = id.concat("(").concat(index).concat(":").concat("(+"+searchKeyword.trim()+")");
//						id = id.concat("^=").concat(formationMap.get(index).toString()).concat(")");
//						id = id.concat(" OR ");
//						id = id.concat("(").concat(index).concat(":").concat("("+searchKeyword.trim()+"*)");
//						id = id.concat("^=").concat(formationMap.get(index).toString()).concat(")");
//					}
//					else {
					id = id.concat("(").concat(index).concat(":").concat("(+" + searchKeyword.trim() + ")");
					id = id.concat("^=").concat(formationMap.get(index).toString()).concat(")");
//					}

				} else {
					if (i <= indexes.length) {
						id = StringUtils.isNotEmpty(id) ? id.concat(" OR ") : "";
					}
					// Commenting the specificity edge case since it is not required.
//					if("specificity_t".equalsIgnoreCase(index) && !searchKeyword.trim().contains(" ")) {
//						id = id.concat("(").concat(index).concat(":").concat("(+"+searchKeyword.trim()+"*)");
//					}
//					else {
					id = id.concat("(").concat(index).concat(":").concat("(+" + searchKeyword.trim() + ")");
//					}
					id = id.concat(")");
				}
			}
		}
		return id;
	}

	/**
	 * Constructing boost rules Map
	 *
	 * @param facetJson
	 * @param resourceResolver
	 * @param formationMap
	 */
	public void boostrulesMap(JsonObject facetJson, ResourceResolver resourceResolver,
			Map<String, Integer> formationMap) {
		String bootrulesPath = StringUtils.EMPTY;
		try {
			bootrulesPath = !facetJson.get(CommonConstants.BOOSTRULES).isJsonNull()
					? facetJson.get(CommonConstants.BOOSTRULES).getAsString()
					: StringUtils.EMPTY;
		} catch (UnsupportedOperationException e) {
			logger.error("boostrulesPath is null");
		}
		if (StringUtils.isNotEmpty(bootrulesPath) && null != resourceResolver.getResource(bootrulesPath)) {
			Resource rootRes = resourceResolver.getResource(bootrulesPath.concat("/".concat(JcrConstants.JCR_CONTENT)));
			if (null != rootRes) {
				Iterator<Resource> resList = rootRes.listChildren();
				Resource rulesResource = getBootRulesResource(resList);
				if (null != rulesResource) {
					constructingRulesMap(formationMap, rulesResource);
				}
			}
		}
	}

	/**
	 * Constructing the rules map
	 *
	 * @param formationMap
	 * @param rulesResource
	 */
	public void constructingRulesMap(Map<String, Integer> formationMap, Resource rulesResource) {
		Resource ruleOptions = rulesResource.getChild("boostRules");
		if (null != ruleOptions) {
			Iterator<Resource> list = ruleOptions.listChildren();
			while (list.hasNext()) {
				Resource childItem = list.next();
				BoostrulesModel bm = childItem.adaptTo(BoostrulesModel.class);
				formationMap.put(bm.getFacetAttributes(), bm.getPreferenceOrder());
				logger.debug("attribute {} and order {}", bm.getFacetAttributes(), bm.getPreferenceOrder());
			}
		}
	}

	/**
	 * This recursive method returns BootRules component resource
	 *
	 * @param item
	 *
	 * @return
	 */
	private Resource getBootRulesResource(Iterator<Resource> item) {
		while (item.hasNext()) {
			Resource temp = item.next();
			if (temp.isResourceType("bdb-aem/proxy/components/content/boostrulescomponent")) {
				boostrulesRes = temp;
				break;
			} else {
				getBootRulesResource(temp.listChildren());
			}
		}
		return boostrulesRes;
	}

	/**
	 * adding selected facets in this method
	 *
	 * @param solrQuery
	 * @param selectedfields
	 * @throws UnsupportedEncodingException
	 */
	public void formFilterQuery(SolrQuery solrQuery, JsonArray selectedfields) throws UnsupportedEncodingException {

		String[] filterQuery = new String[selectedfields.size() + 3];
		if (selectedfields.size() > 0) {
			for (int i = 0; i < selectedfields.size(); i++) {
				if (!CommonConstants.UNDERSCORE_GA.equalsIgnoreCase(selectedfields.get(i).getAsString())) {
					filterQuery[i] = URLDecoder.decode(selectedfields.get(i).getAsString(),
							StandardCharsets.UTF_8.toString());
				}
			}
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(SOLR_DATE_FORMAT);
		LocalDateTime now = LocalDateTime.now();
		//filterQuery[filterQuery.length - 3] = "-docType_t:video";
		filterQuery[filterQuery.length - 2] = "isAvailable_t:true";
		filterQuery[filterQuery.length - 1] = "validityStartDate_dt:[* TO ".concat(dtf.format(now)).concat("]");

		// Removing null values from the array, before firing a Solr Query
		List<String> filters = new ArrayList<String>();
		for (String validString : filterQuery) {
			if (validString != null) {
				filters.add(validString);
			}
		}
		filterQuery = filters.toArray(new String[filters.size()]);
		solrQuery.addFilterQuery(filterQuery);
	}

	/**
	 * adding authored facets in this method
	 *
	 * @param solrQuery
	 * @param facetfields
	 */
	public void formFacetFieldQuery(SolrQuery solrQuery, JsonArray facetfields) {
		if (facetfields.size() > 0) {
			String[] fields = new String[facetfields.size()];
			for (int i = 0; i < facetfields.size(); i++) {
				JsonObject facetobject = facetfields.get(i).getAsJsonObject();
				fields[i] = facetobject.get(CommonConstants.VALUE).getAsString();
			}
			solrQuery.setFacet(true);
			solrQuery.setFacetMinCount(1);
			solrQuery.addFacetField(fields);
		}
	}

	/**
	 * Deactivate.
	 */
	@Deactivate
	protected void deactivate() {
		// DoNothing
	}

	/**
	 * Gets the characters to be modified.
	 *
	 * @return the characters to be modified.
	 */
	public String[] getSpecialCharactersToBeModified() {
		return Arrays.copyOf(specialCharactersToBeModified, specialCharactersToBeModified.length);
	}

	/**
	 * Activate.
	 *
	 * @param config the config
	 */
	@Activate
	@Modified
	protected final void activate(Configuration config) {
		this.specialCharactersToBeModified = config.specialCharactersToBeModified();
	}

	/**
	 * The Interface Configuration.
	 */
	@ObjectClassDefinition(name = "BDB Search Configuration")
	public @interface Configuration {

		/**
		 * characters to be modified.
		 *
		 * @return the string[]
		 */
		@AttributeDefinition(name = "specialCharactersToBeModified", defaultValue = "name", description = "special Characters to be modified for Search Results")
		String[] specialCharactersToBeModified();
	}

}
