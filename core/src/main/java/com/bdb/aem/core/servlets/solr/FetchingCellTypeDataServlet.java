package com.bdb.aem.core.servlets.solr;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.solr.client.solrj.SolrServerException;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.solr.FetchingToolsService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.servlets.BaseServlet;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The Class FetchingCellTypeDataServlet.
 */
@Component(name = "FetchCellTypeData",service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "FetchCellTypeData",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + FetchingCellTypeDataServlet.RESOURCE_TYPE
})
public class FetchingCellTypeDataServlet extends BaseServlet {
    
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(FetchingCellTypeDataServlet.class);
    
    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "bdb/fetch-cellType-data";

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    
    /** The fetching tools service. */
    @Reference
    private transient FetchingToolsService fetchingToolsService;

    /** The solr search service. */
    @Reference
    private transient SolrSearchService solrSearchService;
    
    /** The catalog structure update service. */
    @Reference
    private transient CatalogStructureUpdateService catalogStructureUpdateService;
    
	/** Resource Resolver Factory. */
	@Reference
	transient ResourceResolverFactory resolverFactory;
    
       
    /**
     * Do get.
     *
     * @param request the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doGet(final SlingHttpServletRequest request,
			final SlingHttpServletResponse response) throws IOException {
		LOG.debug("Inside doPOSt of FetchingCellTypeDataServlet");
		String output = StringUtils.EMPTY;
		JsonObject requestObject = getRequestObject(request);
		String type = null != request.getParameter(CommonConstants.ICM_TYPE) ? request.getParameter(CommonConstants.ICM_TYPE) : null;
		JsonArray requestCellType = null != requestObject.get(CommonConstants.SUB_SET_IDS)
				? requestObject.get(CommonConstants.SUB_SET_IDS).getAsJsonArray()
				: null;
		JsonArray panelTypeId = null != requestObject.get(CommonConstants.PANEL_NAME) ? requestObject.get(CommonConstants.PANEL_NAME).getAsJsonArray()
				: null;
		String country = null != requestObject.get(CommonConstants.COUNTRY)
				? requestObject.get(CommonConstants.COUNTRY).getAsString().toLowerCase()
				: StringUtils.EMPTY;
		String reactivity = null != requestObject.get("reactivity")
						? requestObject.get("reactivity").getAsString()
						: StringUtils.EMPTY;		
		ResourceResolver resourceResolver = null;
		JsonArray panelOrCellData = null;
		try {
			if(null != requestCellType) {
				panelOrCellData = requestCellType;
			}else if(null != panelTypeId){
				panelOrCellData = panelTypeId;
			}
			resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
			output = fetchingToolsService.getCellTypeData(country, solrSearchService, panelOrCellData,
					type, catalogStructureUpdateService, resourceResolver,reactivity);

		} catch (SolrServerException e) {
			LOG.error("Error in Fetching Solr", e);
		} catch (LoginException e) {
			LOG.error("LoginException", e);
		} catch (RepositoryException e) {
			LOG.error("RepositoryException", e);
		} finally {
			if (null != resourceResolver) {
				resourceResolver.close();
			}
			response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
			response.getWriter().println(output);
		}
	}
    
    /**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOG.debug("Inside doPOSt of FetchingCellTypeDataServlet");
		doGet(request, response);
	}
}