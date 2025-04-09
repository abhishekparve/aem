package com.bdb.aem.core.servlets.solr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.Servlet;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.servlets.BaseServlet;
import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * <p>
 * This is a Generic Search Servlet which accepts POST calls and queries to
 * Apache Solr based on the request body.
 * </p>
 * 
 * <p>
 * The request body accepts below search parameters :
 * <ul>
 * <li>query: Full Text Search</li>
 * <li>filters: Equivalent to Apache SOLR filter query (fq)</li>
 * <li>sort: Provides sorting capabilities</li>
 * <li>pagination: Captures the number of rows to return in addition to start
 * index.</li>
 * <li>country: Used to fetch the specific Apache SOLR core.</li>
 * </ul>
 * </p>
 * 
 * @author ronbanerjee
 *
 */

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Generic Apache SOLR Search Servlet",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + GenericSearchServlet.RESOURCE_TYPE })
public class GenericSearchServlet extends BaseServlet {

	/** The Generic Search Servlet */
	private static final long serialVersionUID = -4575256877277997606L;

	/** The constant LOGGER */
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericSearchServlet.class);

	/** The constant RESOURCE_TYPE */
	protected static final String RESOURCE_TYPE = "bdb/generic-search";

	@Reference
	private transient SolrSearchService solrSearchService;

	/**
	 * Handles Pagination based on parameters in request body.
	 * 
	 * @param q,      the {@link SolrQuery}
	 * @param object, the {@link JsonObject}
	 */
	private void handlePagination(SolrQuery q, JsonObject object) {
		Optional.ofNullable(object).map(o -> o.get("pagination")).map(pg -> pg.getAsJsonObject()).ifPresent(p -> {

			q.setStart(intManipulation(p, "start", 0));
			q.setRows(intManipulation(p, "rows", 10));
		});

	}

	/**
	 * Handles the filters based on filter parameter. This is equivalent to Apache
	 * SOLR fq.
	 * 
	 * @param q,      the {@link SolrQuery}
	 * @param object, the {@link JsonObject}
	 */
	private void handleFilters(SolrQuery q, JsonObject object) {
		Optional.ofNullable(object).map(o -> o.get("filters")).map(fq -> fq.getAsJsonObject()).ifPresent(f -> {
			List<String> filters = new ArrayList<>();
			f.entrySet().forEach(i -> {
				String key = i.getKey();
				String value = i.getValue() != null && !i.getValue().isJsonNull() ? i.getValue().getAsString()
						: StringUtils.EMPTY;
				if (StringUtils.isNotEmpty(value))
					filters.add(key.concat(":").concat(value));

			});
			q.addFilterQuery(filters.stream().toArray(String[]::new));

		});
	}

	/**
	 * Handles the fields to be returned in the Apache SOLR response.
	 * 
	 * 
	 * @param q,      the {@link SolrQuery}
	 * @param object, the {@link JsonObject}
	 */
	private void handleFields(SolrQuery q, JsonObject object) {
		Optional.ofNullable(object).map(o -> o.get("fields")).map(fl -> fl.getAsJsonArray()).ifPresent(f -> {
			List<String> fields = new ArrayList<>();
			f.forEach(i -> fields.add(i.getAsString()));
			q.setFields(fields.stream().toArray(String[]::new));
		});

	}

	/**
	 * Handles sorting based on sort parameter.
	 * 
	 * @param q,      the {@link SolrQuery}
	 * @param object, the {@link JsonObject}
	 */
	private void handleSorting(SolrQuery q, JsonObject object) {
		Optional.ofNullable(object).map(o -> o.get("sort")).map(st -> st.getAsJsonObject()).ifPresent(s -> {
			s.entrySet().forEach(i -> {
				String value = i.getValue() != null && !i.getValue().isJsonNull() ? i.getValue().getAsString()
						: StringUtils.EMPTY;
				q.addSort(i.getKey(), extractOrder(value).equals("asc") ? ORDER.asc : ORDER.desc);
			});
		});
	}

	/**
	 * Handles full text search based query parameter for Apache Solr Search.
	 * 
	 * @param query,   the {@link SolrQuery}
	 * @param objectc, the {@link JsonObject}
	 */
	private void handleQuery(SolrQuery query, JsonObject object) {

		Optional.ofNullable(object).ifPresent(o -> {
			if (o.get("query")!= null && !(o.get("query").isJsonNull()) ) {
				query.setQuery(o.get("query").getAsString().equals("") ? "*:*" : o.get("query").getAsString());
			} else {
				query.setQuery("*:*");
			}

		});

	}

	/**
	 * Extracts the order either asc or desc based on {@code order}, defaults to
	 * asc.
	 * 
	 * @param order, the order
	 * @return {@link String}, the order
	 */
	private String extractOrder(String order) {
		return Optional.ofNullable(order).map(o -> o.equals("asc") ? "asc" : (o.equals("desc") ? "desc" : "asc"))
				.orElse("asc");
	}

	/**
	 * This method is used for fetching the desired integer value from a JSON object
	 * based on {@code key}
	 * 
	 * @param ob, the {@link JsonObject}
	 * @param key , the key to be searched for
	 * @param d,  the default value
	 * @return {@link Integer}
	 */
	private int intManipulation(JsonObject ob, String key, int d) {

		return Optional.ofNullable(ob)
				.map(o -> o.get(key) != null && !o.get(key).isJsonNull() ? o.get(key).getAsInt() : d).orElse(d);

	}

	/**
	 * Extracts request body from API call.
	 * 
	 * @param request, the {@link SlingHttpServletRequest}
	 * @return {@link JsonObject}
	 */
	private JsonObject getBody(SlingHttpServletRequest request) {

		JsonObject body = null;
		
			try {
				body = getRequestObject(request);
			} catch (JsonSyntaxException e) {
				LOGGER.error("JsonSyntaxException :", e);
			} catch(IOException e) {
				LOGGER.error("IOException :", e);
			}
		
		return body;
	}

	/**
	 * Sets the response to {@link SlingHttpServletResponse}
	 * 
	 * @param docList, the {@link SolrDocumentList}
	 * @param res,     the {@link SlingHttpServletResponse}
	 */
	private void handleResults(SolrDocumentList docList, SlingHttpServletResponse res) {
		Map<String, Object> results = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<>();

		docList.iterator().forEachRemaining(i -> {
			Map<String, Object> m = new HashMap<>();
			i.entrySet().forEach(e -> {
				m.put(e.getKey(), e.getValue());
			});
			result.add(m);

		});
		results.put("hits", result);
		results.put("count", docList.size());
		results.put("totalCount", docList.getNumFound());
		String m = new Gson().toJson(results);
		if (m != null)
			setReponse(m, res, 200);
		else
			setReponse("{\"Error\":\"JSON Parsing Exception.Please check logs\"}", res, 404);

	}

	/**
	 * Sets the API Response in JSON format
	 * 
	 * @param message, the message to be sent in response body
	 * @param res,     the {@link SlingHttpServletResponse}
	 */
	private void setReponse(String message, SlingHttpServletResponse res, int status) {

		res.setStatus(status);
		res.setContentType(CommonConstants.APPLICATION_JSON);
		res.setCharacterEncoding("UTF-8");
		PrintWriter writer;
		try {
			writer = res.getWriter();
			writer.write(message);
		} catch (IOException e) {
			LOGGER.error("IOException :", e);
		}
	}

	/**
	 * Gets the Apache Solr Client based on {@code country}.
	 * 
	 * @param country, the country based on which solr core is called.
	 * @return the {@link SolrClient}
	 */
	private SolrClient getSolrClient(String country) {
		LOGGER.debug("Indexing antigen data for country:", country);
		return solrSearchService.solrClient(country.toLowerCase());
	}

	/**
	 * @see SlingSafeMethodsServlet
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		JsonObject o = getBody(request);
		SolrQuery q = new SolrQuery();
		handleQuery(q, o);
		handlePagination(q, o);
		handleFilters(q, o);
		handleFields(q, o);
		handleSorting(q, o);
		QueryResponse sitesQueryResponse;
		try {
			if (o!=null && o.get("country") != null && !o.get("country").isJsonNull()
					&& !o.get("country").getAsString().equals("")) {
				sitesQueryResponse = getSolrClient(o.get("country").getAsString()).query(q);
				SolrDocumentList docList = sitesQueryResponse.getResults();
				handleResults(docList, response);
			} else {
				setReponse("{\"Error\":\"Missing Solr Core\"}", response, 404);
			}
		} catch (SolrServerException | IOException e) {
			LOGGER.error("Exception :", e);
		}

	}

	/**
	 * @see SlingAllMethodsServlet
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		doGet(request, response);
	}

}
