package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.PromoDetailsModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class PromotionIdDetailsServlet.
 */
@Component(name = "PromotionIdDetailsServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "PromotionIdDetailsServlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + PromotionIdDetailsServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON
})
public class PromotionIdDetailsServlet extends BaseServlet {
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    
	public static final String RESOURCE_TYPE = "bdb/get-promotion-details";

    /**
     * The Constant LOGGER.
     */
    private static final Logger logger = LoggerFactory.getLogger(PromotionIdDetailsServlet.class);
    
    /**
     * The resource resolver factory.
     */
    @Reference
    private transient ResourceResolverFactory resourceResolverFactory;

    /**
     * The bdb api endpoint service.
     */
    @Reference
    private transient BDBApiEndpointService bdbApiEndpointService;

    /** The query builder. */
    @Reference
    private transient QueryBuilder queryBuilder;
    
    /**
     * Post method.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
          doGet(request, response);
    }

    /**
     * Get Method.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
    	logger.debug("Inside doGet of PromotionIdDetailsServlet");
    	
    	RequestPathInfo requestPathInfo = request.getRequestPathInfo();
        String[] selectors = requestPathInfo.getSelectors();
        String promotionID = null!=selectors[0]?selectors[0]:StringUtils.EMPTY;
        RequestParameter prmotionPagePath = request.getRequestParameter("promotionUrl");
        String modelValues = StringUtils.EMPTY;
        ResourceResolver resourceResolver = null;
			//creating the path up till promotions
	
        if (null != prmotionPagePath) {
	    	logger.debug("Site structure path - {}",prmotionPagePath);
            resourceResolver = request.getResourceResolver();
	    	Resource promotionsResource = resourceResolver.getResource(prmotionPagePath.toString());
	    	if(null != promotionsResource) {	    		
	    		//gets path for promoDetails component - with promoId(field)=promotionID
	    		String componentPath = getPath(prmotionPagePath.toString(), promotionID, resourceResolver);
	    		if(null != componentPath) {
					//adapts promoDetails componentResource to promoDetailsModel.class
	    			Resource componentResource = resourceResolver.getResource(componentPath);
	    			if (componentResource != null) {
		    			PromoDetailsModel promoDetailsModel = componentResource.adaptTo(PromoDetailsModel.class);
						if(null != promoDetailsModel) {
							//gets component details
			    			modelValues = getPromoDetailsModelValues(promoDetailsModel);
			    			logger.debug("modelValues- {}", modelValues);
						}
	    			}
	    		}
	    	}
        }
        
        response.setContentType(CommonConstants.APPLICATION_JSON);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(modelValues);
        writer.flush();
    }
    
    
    /**
     * Gets the path.
     *
     * @param promotionsPath the promotions path
     * @param promotionID the promotion ID
     * @param resourceResolver the resource resolver
     * @return the path
     */
    private String getPath(String promotionsPath, String promotionID, ResourceResolver resourceResolver){
    	logger.debug("Inside function getPath");
        Map<String, String> params = new HashMap<>();
        params.put("path", promotionsPath);
        params.put("property", "promoId");
        params.put("property.value", promotionID);
        params.put("p.limit", "1");
        params.put("type", JcrConstants.NT_UNSTRUCTURED);
        Query query = queryBuilder.createQuery(PredicateGroup.create(params), resourceResolver.adaptTo(Session.class));
        SearchResult result = query.getResult();
        try {
        	if (null != result && result.getTotalMatches() > 0) {
                Hit hit = result.getHits().get(0);
                logger.debug("hit.getPath()- {}", hit.getPath());
                return hit.getPath();
            }
        } catch (RepositoryException e) {
            logger.error("RepositoryException", e);
        }
        return null;
    }
	
	
    /**
     * Gets the promo details model values.
     *
     * @param promoDetailsModel the promo details model
     * @return the promo details model values
     */
    private String getPromoDetailsModelValues(PromoDetailsModel promoDetailsModel) {
		logger.debug("Inside function getPromoDetailsModelValues");
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		return gson.toJson(promoDetailsModel);
	}
    
}