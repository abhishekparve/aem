package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import org.osgi.framework.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.Gson;

@Component(service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "ScientificResourcesLibraryServlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + ScientificResourcesLibraryServlet.RESOURCE_TYPE
})
public class ScientificResourcesLibraryServlet extends SlingAllMethodsServlet{
	
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/get-scientific-resources";
	
	private static final String PAGE_NUMBER = "pageNumber";
	
	private static final String PAGE_COUNT = "pageCount";
	
	private static final String DOC_TYPES = "resourceType";
	
	private static final String SEARCH_TERM = "searchTerm";
	
	private static final String COUNTRY = "country";
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(ScientificResourcesLibraryServlet.class);

	@Reference
	transient SolrSearchService solrSearchService;
	/**
     * Get Method.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException{
    	
    	Map<String,Object> jsonMap = new HashMap<>();
    	try {
			int startIndex = convetStringToInt(request.getParameter(PAGE_NUMBER));
			int size = convetStringToInt(request.getParameter(PAGE_COUNT));
			startIndex = (startIndex-1)*size;
			String country = null!=request.getParameter(COUNTRY)?request.getParameter(COUNTRY).toLowerCase():"us";
			
			String docTypes = request.getParameter(DOC_TYPES);
			if(null!=docTypes)
			{
			docTypes=docTypes.replace(CommonConstants.SPACE, ",");
			docTypes = decodeParam(docTypes);
			}
			String searchTerm = request.getParameter(SEARCH_TERM);
			searchTerm=null!=searchTerm?decodeParam(searchTerm):StringUtils.EMPTY;
			LOG.debug("parameters country :{},  docTypes : {}, searchTerm: {} ",country,docTypes,searchTerm);
			jsonMap = solrSearchService.getScientificResLib(docTypes, startIndex, size,
					searchTerm,country);
		} finally {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			String listPdf = gson.toJson(jsonMap);
			out.write(listPdf);
			out.flush();
		}
    }
    
    /**
     * 
     * @param value
     * @return index as int
     */
    public int convetStringToInt(String value) {
    	return Integer.parseInt(value);
    }
    
    public String decodeParam(String value) throws UnsupportedEncodingException {
    	return URLDecoder.decode(value, "UTF-8");
    }
}