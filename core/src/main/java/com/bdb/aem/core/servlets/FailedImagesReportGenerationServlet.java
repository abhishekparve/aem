package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.config.FailedImageReportGenerationConfig;
import com.bdb.aem.core.config.ProductImageProcessingSchedulerConfiguration;
import com.bdb.aem.core.services.GenerateFailedImageReportService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

/** 
 * To process flat file product images
 */
@Designate(ocd = ProductImageProcessingSchedulerConfiguration.class)
@Component(name = "FailedImagesReportGenerationServlet", service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "FailedImagesReportGenerationServlet",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_PATHS + CommonConstants.EQUAL + "/bin/failed-product-images-report"})
public class FailedImagesReportGenerationServlet extends SlingSafeMethodsServlet  {

	/**
     * The Constant serialVersionUID.
     */
	private static final long serialVersionUID = 2682674001096166921L;

	/**
	 * ProductImageProcessingService
	 */
	@Reference
    private GenerateFailedImageReportService generateFailedImageReportService;
	
	/**
     * The resource resolver factory.
     */
    @Reference
    private transient ResourceResolverFactory resourceResolverFactory;

	/** The LOGGER */
	private static final Logger logger = LoggerFactory.getLogger(FailedImagesReportGenerationServlet.class);
	   
    ResourceResolver resourceResolver = null;

    private String failedRecordsInVar = StringUtils.EMPTY;
   
    private String failedReportDestinationPath = StringUtils.EMPTY;

    @Activate
    public void activate(FailedImageReportGenerationConfig config) {
        this.failedRecordsInVar = config.failedRecordsInVar();
        this.failedReportDestinationPath = config.failedReportDestinationPath();
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
		logger.info("Invoking ProcessFlatFileProductImagesServlet");
	
	    try {
	    	 resourceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
	    	 Resource resource = resourceResolver.getResource(failedRecordsInVar);
			 Session session = resourceResolver.adaptTo(Session.class);
			 if (null != failedRecordsInVar) {
	        		generateFailedImageReportService.generateFailedImageReport(resourceResolver, resource, session, failedReportDestinationPath);
	        	}
	    	 PrintWriter out = response.getWriter();
			 response.setContentType("application/json");
			 response.setCharacterEncoding("UTF-8");
			 out.print("Successfully generate report");
	     } catch (LoginException | IOException e) {
	    	 logger.error("Failed to generate report {}", e.getMessage());
	    	 logger.error("LoginException :", e);
        } finally {
            CommonHelper.closeResourceResolver(resourceResolver);
        }
	}
}