package com.bdb.aem.core.servlets.solr;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

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
import org.apache.solr.client.solrj.SolrServerException;
import org.osgi.framework.Constants;
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

@SuppressWarnings("CQRules:CQBP-75")
@Component(service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Fetch Clone Data",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + FetchingCloneData.RESOURCE_TYPE
})
public class FetchingCloneData extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(FetchingCloneData.class);
    
    public static final String RESOURCE_TYPE = "bdb/fetch-clone-data";

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    @Reference
    private transient FetchingCloneService fetchingCloneService;
    @Reference
    private transient ResourceResolverFactory resourceResolverFactory;
    @Reference
    private transient BDBSearchEndpointService solrConfig;
    @Reference
    private transient SolrSearchService solrSearchService;
    
    @Reference
	private transient CatalogStructureUpdateService catalogStructureUpdateService;
    
    @Override
    protected void doGet(final SlingHttpServletRequest request,
                         final SlingHttpServletResponse response) throws IOException {
        String output= StringUtils.EMPTY;
        String cloneId = StringUtils.EMPTY;
		String dyeName = StringUtils.EMPTY;
		String specificity = StringUtils.EMPTY;
        String requestData=request.getParameter("baseId");
        String country=request.getParameter(CommonConstants.COUNTRY).toLowerCase();
        
        try {
        	ResourceResolver resourceResolver = request.getResourceResolver();
        	Resource baseResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, requestData, CommonConstants.BASE_MATERIAL_NUMBER);	
			if(null != baseResource && null != baseResource.getChild(CommonConstants.HP)) {
				Resource baseHpResource = baseResource.getChild(CommonConstants.HP);
				ValueMap baseHpMap = baseHpResource.adaptTo(ValueMap.class);
				dyeName = CommonHelper.getDyeNameFromFormat(baseHpMap);
				cloneId = CommonHelper.getTdsCloneIdFromHp(baseHpMap);
				specificity = CommonHelper.getSpecificityFromHp(baseHpMap);
			}
            output=fetchingCloneService.getClonesData(country,solrConfig,solrSearchService,specificity,dyeName,cloneId);
        } catch (SolrServerException e) {
            LOG.error("Error in Fetching Solr",e);
        } catch (RepositoryException e) {
        	 LOG.error("RepositoryException",e);
		}
        response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
        response.getWriter().println(output);

    }
}
