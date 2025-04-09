package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Servlet to send the country-state mapping.
 */
@Component(name = "GetStatesFromCountryServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Get states from country",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + GetStatesFromCountryServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON
})
public class GetStatesFromCountryServlet extends BaseServlet {
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    
	public static final String RESOURCE_TYPE = "bdb/get-states-from-country";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GetStatesFromCountryServlet.class);
    /**
     * The Constant Resource Type.
     */
    protected static final String resourceType = "bdb/getStatesFromCountry";

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

    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        JsonObject countryJson = new JsonObject();
        JsonObject requestObject = getRequestObject(request);
        String countryId = requestObject.get("countryId").getAsString();

        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);

            Resource regionCountryResource = resourceResolver.getResource(bdbApiEndpointService.getCountryStateDropdownEndpoint());
            if (null != regionCountryResource && regionCountryResource.hasChildren()) {
                for (Resource childResource : regionCountryResource.getChildren()) {
                    JsonArray statesJsonArray = new JsonArray();

                    Page countryPage = childResource.adaptTo(Page.class);
                    setCountryStateMapping(countryJson, resourceResolver, statesJsonArray, countryPage, countryId);

                }
            }
        } catch (LoginException e) {
            LOGGER.error("LoginException :", e);
        } finally {
            CommonHelper.closeResourceResolver(resourceResolver);
        }

        response.setContentType(CommonConstants.APPLICATION_JSON);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(countryJson.toString());
        writer.flush();
    }

    /**
     * Sets the country state mapping.
     *
     * @param resourceResolver   the resource resolver
     * @param statesJsonArray the countries Json Array
     * @param countryPage         the region page
     */
    private void setCountryStateMapping(JsonObject countryJson, ResourceResolver resourceResolver,
                                         JsonArray statesJsonArray, Page countryPage, String countryId) {
        if (null != countryPage) {
            String jcrPath = countryPage.getPath() + "/jcr:content";
            Resource jcrResource = resourceResolver.getResource(jcrPath);

            if (null != jcrResource) {
                ValueMap properties = jcrResource.getValueMap();
                String regionLabel = CommonHelper.getPropertyValue(properties, JcrConstants.JCR_TITLE);
                String regionValue = CommonHelper.getPropertyValue(properties, "value");
                if (regionValue.equalsIgnoreCase(countryId)) {
                    countryJson.addProperty("label", regionLabel);
                    countryJson.addProperty("id", regionValue);
                    String listPath = countryPage.getPath() + "/jcr:content/list";
                    Resource countryListResource = resourceResolver.getResource(listPath);
                    if (null != countryListResource) {
                        Iterator<Resource> items = countryListResource.listChildren();
                        setStateJson(statesJsonArray, items);
                        if (null != statesJsonArray) {
                            countryJson.add("states", statesJsonArray);
                        }
                    }
                }
            }

        }
    }

    /**
     * Sets the country json.
     *
     * @param statesJsonArray the states json array
     * @param items              the items
     */
    private void setStateJson(JsonArray statesJsonArray, Iterator<Resource> items) {
        JsonObject stateJson;
        while (items.hasNext()) {
            Resource stateResource = items.next();
            stateJson = new JsonObject();
            if (null != stateResource) {
                ValueMap properties = stateResource.getValueMap();
                String countryLabel = CommonHelper.getPropertyValue(properties, JcrConstants.JCR_TITLE);
                String countryId = CommonHelper.getPropertyValue(properties, "value");
                if (StringUtils.isNotBlank(countryLabel) && StringUtils.isNotBlank(countryId)) {
                    stateJson.addProperty("label", countryLabel);
                    stateJson.addProperty("id", countryId);
                }
            }
            statesJsonArray.add(stateJson);
        }
    }
}
