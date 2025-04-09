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
import org.apache.sling.api.request.RequestParameter;
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
 * Servlet to send the region-country mapping.
 */
@Component(name = "GetCountriesFromRegionServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Get country region servlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + GetCountriesFromRegionServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON
})
public class GetCountriesFromRegionServlet extends BaseServlet {
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    
	public static final String RESOURCE_TYPE = "bdb/get-country-from-region";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GetCountriesFromRegionServlet.class);

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
    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        JsonObject regionJson = new JsonObject();
        RequestParameter localeValue = request.getRequestParameter("locale");
        String locale = localeValue.toString();

        String[] split = locale.split("-");
        String regionId = split[split.length - 1];


        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);

            Resource regionCountryResource = resourceResolver.getResource(bdbApiEndpointService.getRegionCountryDropdownEndpoint());
            if (null != regionCountryResource && regionCountryResource.hasChildren()) {
                for (Resource childResource : regionCountryResource.getChildren()) {
                    JsonArray countriesJsonArray = new JsonArray();

                    Page regionPage = childResource.adaptTo(Page.class);
                    setRegionCountryMapping(regionJson, resourceResolver, countriesJsonArray, regionPage, regionId);

                }
            }
        } catch (LoginException e) {
            LOGGER.error("LoginException", e);
        } finally {
            CommonHelper.closeResourceResolver(resourceResolver);
        }

        response.setContentType(CommonConstants.APPLICATION_JSON);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(regionJson.toString());
        writer.flush();
    }

    /**
     * Sets the region country mapping.
     *
     * @param resourceResolver   the resource resolver
     * @param countriesJsonArray the countries Json Array
     * @param regionPage         the region page
     */
    private void setRegionCountryMapping(JsonObject regionJson, ResourceResolver resourceResolver,
                                         JsonArray countriesJsonArray, Page regionPage, String regionId) {
        if (null != regionPage) {
            String jcrPath = regionPage.getPath() + "/jcr:content";
            Resource jcrResource = resourceResolver.getResource(jcrPath);

            if (null != jcrResource) {
                ValueMap properties = jcrResource.getValueMap();
                String regionLabel = CommonHelper.getPropertyValue(properties, JcrConstants.JCR_TITLE);
                String regionValue = CommonHelper.getPropertyValue(properties, "value");
                if (regionValue.equalsIgnoreCase(regionId)) {
                    regionJson.addProperty("label", regionLabel);
                    regionJson.addProperty("id", regionValue);
                    String listPath = regionPage.getPath() + "/jcr:content/list";
                    Resource countryListResource = resourceResolver.getResource(listPath);
                    if (null != countryListResource) {
                        Iterator<Resource> items = countryListResource.listChildren();
                        setCountryJson(countriesJsonArray, items);
                        if (null != countriesJsonArray) {
                            regionJson.add("countries", countriesJsonArray);
                        }
                    }
                }
            }

        }
    }

    /**
     * Sets the country json.
     *
     * @param countriesJsonArray the countries json array
     * @param items              the items
     */
    private void setCountryJson(JsonArray countriesJsonArray, Iterator<Resource> items) {
        JsonObject countryJson;
        while (items.hasNext()) {
            Resource countryResource = items.next();
            countryJson = new JsonObject();
            if (null != countryResource) {
                ValueMap properties = countryResource.getValueMap();
                String countryLabel = CommonHelper.getPropertyValue(properties, JcrConstants.JCR_TITLE);
                String countryId = CommonHelper.getPropertyValue(properties, "value");
                if (StringUtils.isNotBlank(countryLabel) && StringUtils.isNotBlank(countryId)) {
                    countryJson.addProperty("label", countryLabel);
                    countryJson.addProperty("id", countryId);
                }
            }
            countriesJsonArray.add(countryJson);
        }
    }
}
