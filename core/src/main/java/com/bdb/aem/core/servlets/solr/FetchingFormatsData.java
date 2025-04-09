package com.bdb.aem.core.servlets.solr;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.solr.FetchingCloneService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

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

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Fetch Format Data",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + FetchingFormatsData.RESOURCE_TYPE
})
public class FetchingFormatsData extends SlingAllMethodsServlet {
	private static final Logger LOG = LoggerFactory.getLogger(FetchingFormatsData.class);
	public static final String RESOURCE_TYPE = "bdb/fetch-format-data";
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
	
	private String abSeqBrand = "BD™ AbSeq";
	
	@Reference
	private transient CatalogStructureUpdateService catalogStructureUpdateService;

	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		
		String brand = StringUtils.EMPTY;
		String output = StringUtils.EMPTY;
		String specificityOutput = StringUtils.EMPTY;
		String cloneId = StringUtils.EMPTY;
		String dyeName = StringUtils.EMPTY;
		String requestData = request.getParameter("baseId");
		String country=request.getParameter("country").toLowerCase();
		String specificity = StringUtils.EMPTY;
		
			try {
				ResourceResolver resourceResolver = request.getResourceResolver();
				Resource baseResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, requestData, CommonConstants.BASE_MATERIAL_NUMBER);	
					if(null != baseResource && null != baseResource.getChild(CommonConstants.HP)) {
						Resource baseHpResource = baseResource.getChild(CommonConstants.HP);
						ValueMap baseHpMap = baseHpResource.adaptTo(ValueMap.class);
						dyeName = CommonHelper.getDyeNameFromFormat(baseHpMap);
						cloneId = CommonHelper.getTdsCloneIdFromHp(baseHpMap);
						if(null != baseHpMap.get(CommonConstants.BRAND) && StringUtils.isNotEmpty(baseHpMap.get(CommonConstants.BRAND,String.class))) {
							brand = baseHpMap.get(CommonConstants.BRAND,String.class);
						 }
						specificity = CommonHelper.getSpecificityFromHp(baseHpMap);
					}
					if(!cloneId.equals("Polyclonal") && !cloneId.equals("Antibody-Oligo") && !brand.equals(abSeqBrand)) {
						output = fetchingCloneService.getFormatsData(country, solrConfig,solrSearchService,dyeName,cloneId);
					}
				//	if(cloneId.equals("") || cloneId.isEmpty() || StringUtils.isEmpty(cloneId)) 
						specificityOutput=fetchingCloneService.getSpecificityData(country,solrConfig,solrSearchService,dyeName,specificity);
				
			} catch (IOException | SolrServerException e) {
				LOG.error("Error in Fetching Solr", e);
			} catch (RepositoryException e) {
				LOG.error("RepositoryException", e);
			}
		response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
		 if(StringUtils.isNotEmpty(output)) {
		//if(output != null) !output.isEmpty() StringUtils.isNotEmpty(output)
			response.getWriter().println(output);
		}
		else {
			response.getWriter().println(specificityOutput);
		}

	}

}
