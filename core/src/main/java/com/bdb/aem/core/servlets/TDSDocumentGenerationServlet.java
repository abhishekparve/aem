package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.OnDemandTdsGenerationService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AccessControlException;

@Component(immediate = true, service = Servlet.class, property = {
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + TDSDocumentGenerationServlet.RESOURCE_TYPE,
        ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_GET
})
public class TDSDocumentGenerationServlet extends SlingAllMethodsServlet {

    public static final String RESOURCE_TYPE = "bdb/generate-tds-document";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TDSDocumentGenerationServlet.class);

    /**
     * Resource Resolver Factory.
     */
    @Reference
    transient ResourceResolverFactory resolverFactory;

    @Reference
    transient OnDemandTdsGenerationService onDemandTdsGenerationService;

    @Reference
    CatalogStructureUpdateService catalogStructureUpdateService;

    /**
     * Get Method.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        String[] reqSelectors = request.getRequestPathInfo().getSelectors();
        String skuName = StringUtils.EMPTY;
        String locale = StringUtils.EMPTY;
        if (reqSelectors.length > 1) {
            locale = reqSelectors[0];
            skuName = reqSelectors[1];
        } else {
            LOGGER.error("! Missing Selectors !");
        }
        LOGGER.debug("SKU Name : {}", skuName);
        LOGGER.debug("Locale : {}", locale);

        ResourceResolver resourceResolver = null;
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

        try {
            resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
            Resource variantRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, skuName,
                    CommonConstants.MATERIAL_NUMBER);
            pdfStream = onDemandTdsGenerationService.getPdfStream(variantRes, resourceResolver, skuName, locale);
        } catch (LoginException | AccessControlException | TransformerException | SAXException | ParserConfigurationException | RepositoryException e1) {
            LOGGER.error("Exception {}", e1);
        }
        /**
         * finally statement executes for closing resolver and session
         */ finally {
            response.setContentType("application/pdf");
            LOGGER.debug("size of out file :: {}", pdfStream.size());
            response.setContentLength(pdfStream.size());
            response.getOutputStream().write(pdfStream.toByteArray());
            if (null != resourceResolver)
                resourceResolver.close();
            response.getOutputStream().flush();
        }
    }
}