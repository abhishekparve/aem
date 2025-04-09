package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.SpectrumViewerConfigService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SVUtils;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
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

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class FetchInstrumentDataServlet.
 */
@Component(name = "FetchInstrumentData", service = Servlet.class, immediate = true,
        property = {Constants.SERVICE_DESCRIPTION + "=" + "Get Instrument Spectral and Similarity matrix data ",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + FetchInstrumentDataServlet.RESOURCE_TYPE,
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=" + CommonConstants.JSON
        })
public class FetchInstrumentDataServlet extends BaseServlet {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(FetchInstrumentDataServlet.class);

    /**
     * The Constant RESOURCE_TYPE.
     */
    public static final String RESOURCE_TYPE = "bdb-aem/components/content/spectrumViewer/v1/spectrumViewer";//"bdb/fetch-instrument-data";

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Resource Resolver Factory.
     */
    @Reference
    transient ResourceResolverFactory resolverFactory;

    @Reference
    private transient SpectrumViewerConfigService spectrumViewerConfigService;


    /**
     * Do get.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doGet(final SlingHttpServletRequest request,
                         final SlingHttpServletResponse response) throws IOException {
        LOG.debug("Inside doGet of FetchInstrumentDataServlet");
        RequestPathInfo requestPath = request.getRequestPathInfo();
        LOG.debug(" requestPath {}", requestPath);
        String selectorFileName = StringUtils.EMPTY;
        String[] selectors = requestPath.getSelectors();
        int selectorLength = selectors.length;
        if (selectorLength != 0) {
            selectorFileName = selectorLength == 1 ? selectors[0] : StringUtils.EMPTY;
            LOG.debug("Selector is : {}", selectorFileName);
        }
        String spectrumViewerConfigParentPath = SVUtils.getSvSystemConfigPath(resolverFactory, requestPath.getResourcePath());//spectrumViewerConfigService.getSpectrumViewerConfigFile();
        LOG.debug("spectrumViewerConfigParentPath si : {}", spectrumViewerConfigParentPath);
        String secureKey = spectrumViewerConfigService.getSpectrumViewerEncryptionKey();
        String encryptedValue = StringUtils.EMPTY;
        JsonObject jsonOutput = new JsonObject();
        if (StringUtils.isNotEmpty(spectrumViewerConfigParentPath) && StringUtils.isNotEmpty(selectorFileName)) {
            String fileName = searchAssetInDAM(spectrumViewerConfigParentPath, selectorFileName);
            if (StringUtils.isNotEmpty(fileName)) {
                String filePath = spectrumViewerConfigParentPath + CommonConstants.SINGLE_SLASH + fileName;
                LOG.debug("File path to read is : {}", filePath);
                String output = SVUtils.readAssetFile(resolverFactory, filePath);
                if (StringUtils.isNotEmpty(output)) {
                    LOG.debug("Entering to encrypt the file with key : {}", secureKey);
                    encryptedValue = SVUtils.encrypt(output, secureKey);
                    jsonOutput.addProperty("jsonEncodedStr", encryptedValue);
                    LOG.debug("Encryption finishes for the file");
                }
                if(StringUtils.isNotEmpty(encryptedValue)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(CommonConstants.APPLICATION_JSON);
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().println(jsonOutput);
                }else{
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    LOG.debug("File is having invalid content: {}", filePath);
                }
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                LOG.debug("File Not found in DAM : {}", selectorFileName);
            }
        }else{
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            LOG.debug("File Path Not present {}, {}", spectrumViewerConfigParentPath, selectorFileName);
        }
        LOG.debug("End of doGet of FetchInstrumentDataServlet");
    }

    private String searchAssetInDAM(String parentPath, String fileName) {
        try (ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory)) {
            LOG.debug("File reading starting with resolver: {} and file path is {}", resourceResolver, parentPath);
            int i = 0;
            Resource parentPathResource = resourceResolver.getResource(parentPath);
            if(null!=parentPathResource && parentPathResource.hasChildren()) {
                Iterable<Resource> childRes = parentPathResource.getChildren();
                for(Resource nodeRes : childRes) {
                    String damFileName = nodeRes.getName();
                    LOG.debug(" File number {} found is : {} ", ++i, damFileName);
                    if (StringUtils.contains(damFileName, fileName)) {
                        LOG.debug("Matched damResource is {}", damFileName);
                        return damFileName;
                    }
                }
            }
        } catch (LoginException e) {
            LOG.error("Exception in reading the system json file : {}", e.getMessage());
        }
        LOG.debug("Exiting the searchAssetInDAM method");
        return null;
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
        LOG.debug("Inside doPost of FetchInstrumentDataServlet");
        doGet(request, response);
    }
}