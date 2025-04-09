package com.bdb.aem.core.servlets.solr;


import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.google.gson.Gson;


/**
 * The Class PopularSearched.
 */
@Component(service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Popular Search",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + PopularSearched.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON
})
public class PopularSearched extends SlingAllMethodsServlet {
	public static final String RESOURCE_TYPE = "bdb/popular-search";
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(PopularSearched.class);

    /** The search service. */
    @Reference
    transient SolrSearchService searchService;
    
    /**
     * Do get.
     *
     * @param request the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {
        QueryResponse queryResponse;
        HttpSolrClient solr = searchService.solrClientPopSearch();
        if (solr != null) {
    		SolrQuery solrQuery = new SolrQuery();
    		if(StringUtils.isNotBlank(request.getParameter("country"))) {
    	        String market = "bdbio-" + request.getParameter("country").toLowerCase();
    	        buildPopularSearchSolrQuery(solrQuery, market);
    		}
            
    		try {
                queryResponse = solr.query(solrQuery);
                
                if (queryResponse != null && queryResponse.getResults() != null) {
                    queryResponse.getResults();	
                }
                
                response.setContentType(SolrSearchConstants.CONTENT_TYPE);
                response.getWriter().println(new Gson().toJson(queryResponse));
            }
    		catch (SolrServerException e) {
                LOG.error("Solr Exception has occured. {}",e);
                response.getWriter().println("Solr Exception Occured");
            }
    		catch (IOException e) {
                LOG.error("IO Exception has occured. {}", e);
            }
    		catch (ClassCastException e) {
                LOG.error("Class cast exception.", e);
            }
        }
        else {
            response.setStatus(500);
            response.getWriter().println("Solr is down!");
        }
    }

	private void buildPopularSearchSolrQuery(SolrQuery solrQuery, String market) {
		solrQuery.setQuery("market_s:" + market);
		solrQuery.addSort("frequency_i", SolrQuery.ORDER.desc);
		solrQuery.setFields("keyword_t", "market_t","frequency_i");
		solrQuery.setStart(0);
		solrQuery.setRows(5);
	}

}