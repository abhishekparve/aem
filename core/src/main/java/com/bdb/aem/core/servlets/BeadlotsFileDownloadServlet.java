package com.bdb.aem.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.vault.packaging.JcrPackage;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BeadlotFileService;
import com.google.gson.JsonObject;

/**
 * Servlet to fetch the beadlot document
 */

@Component(name = "BeadlotsFileDownloadServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "BeadlotsFileDownloadServlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + BeadlotsFileDownloadServlet.RESOURCE_TYPE
})
public class BeadlotsFileDownloadServlet extends BaseServlet {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    public static final String RESOURCE_TYPE = "bdb/beadlots-file-download";
    
    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BeadlotsFileDownloadServlet.class);

    /**
     * The base api service for mulesoft calls.
     */
    @Reference
    private transient BeadlotFileService beadlotFileService;

    /**
     * Post method.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        LOGGER.debug("Entry doPost of BeadlotsFileDownloadServlet");
        doGet(request, response);
        LOGGER.debug("Exit doPost of BeadlotsFileDownloadServlet");
    }

    /**
     * Do get.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        LOGGER.debug("Entry doGet of BeadlotsFileDownloadServlet");
        String apiResponse = StringUtils.EMPTY;
        JsonObject requestObject = getRequestObject(request);
        byte[] buffer= null;
        String filename= StringUtils.EMPTY;
        try {
        	if (null != requestObject) {
        		apiResponse = beadlotFileService.fetchBeadLotFileDetails(request, requestObject, response);
        		if(StringUtils.isNotBlank(apiResponse)) {
        			buffer = Base64.decodeBase64(apiResponse.getBytes());
        			filename = requestObject.get("beadlots").getAsJsonObject().get("documentNumber").toString().replaceAll("\"", "") + ".zip";
        		}
        	}
        } catch (AemInternalServerErrorException exception) {
            apiResponse = exception.getMessage();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOGGER.error("Exception Occurred during rest client execution {0}", exception.getCause());
        } finally {
        	if (buffer != null) {
        		response.setContentType(JcrPackage.MIME_TYPE);
        		response.setContentLength(buffer.length);
        		response.setHeader("Content-Disposition","attachment;filename=\"" +filename + "\"");
        		response.getOutputStream().write(buffer);
        		response.getOutputStream().flush();
        	} 
        }
    }

    }
