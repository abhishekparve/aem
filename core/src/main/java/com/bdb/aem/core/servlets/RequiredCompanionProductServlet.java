package com.bdb.aem.core.servlets;


import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.FetchRequiredCompanionProduct;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.solr.client.solrj.SolrServerException;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import java.io.IOException;
@Component(service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "RequiredCompanionProductServlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + RequiredCompanionProductServlet.RESOURCE_TYPE
})
public class RequiredCompanionProductServlet extends SlingAllMethodsServlet  {
    private static final Logger LOG = LoggerFactory.getLogger(RequiredCompanionProductServlet.class);
	private static final long serialVersionUID = 1L;

	public static final String RESOURCE_TYPE = "bdb/get-related-companion-products";
		@Reference
	    private transient ResourceResolverFactory resourceResolverFactory;
	    @Reference
	    private transient BDBSearchEndpointService solrConfig;
	    @Reference
	    private transient SolrSearchService solrSearchService;
	    @Reference
	    transient  FetchRequiredCompanionProduct fetchingService;
		@Reference
		private transient CatalogStructureUpdateService catalogStructureUpdateService;
	    
	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		LOG.debug("Inside DoGet of RequiredCompanionProductServlet");
		String output = StringUtils.EMPTY;
		ResourceResolver resourceResolver = null;
		String requestData = request.getParameter("catalogNo");
		String country = null != request.getParameter(CommonConstants.COUNTRY)?request.getParameter(CommonConstants.COUNTRY).toLowerCase():StringUtils.EMPTY;
		try {
			resourceResolver = request.getResourceResolver();
			if(null != requestData && StringUtils.isNotEmpty(country)) {
			 output = fetchingService.getRequriedCompanionProduct(requestData, country, solrConfig, solrSearchService, resourceResolver,catalogStructureUpdateService);
		    }
		} catch (SolrServerException e) {
			LOG.error("Error in RequiredCompanionProductServlet {}", e);
		} catch (RepositoryException e) {
			LOG.error("RepositoryException in RequiredCompanionProductServlet {}", e);
		}
		response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
		response.getWriter().println(output);

	}
}
