package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
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

import com.bdb.aem.core.config.ProductImageProcessingSchedulerConfiguration;
import com.bdb.aem.core.services.ProductImageProcessingService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

/** 
 * To process flat file product images
 */
@Designate(ocd = ProductImageProcessingSchedulerConfiguration.class)
@Component(name = "ProcessFlatFileProductImagesServlet", service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Process FlatFile Product Images Servlet",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_PATHS + CommonConstants.EQUAL + "/bin/process-flatfile-product-images"})
public class ProcessFlatFileProductImagesServlet extends SlingSafeMethodsServlet  {

	/**
     * The Constant serialVersionUID.
     */
	private static final long serialVersionUID = 1326654863854767752L;
	
	/**
	 * ProductImageProcessingService
	 */
	@Reference
    private ProductImageProcessingService productImageProcessingService;
	
	/**
     * The resource resolver factory.
     */
    @Reference
    private transient ResourceResolverFactory resourceResolverFactory;

	/** The LOGGER */
	private static final Logger logger = LoggerFactory.getLogger(ProcessFlatFileProductImagesServlet.class);
	   
    ResourceResolver resourceResolver = null;

    private String tempAssetsPath = StringUtils.EMPTY;
    
    private String unProcessedRecordsPath = StringUtils.EMPTY;

    private long defaultRetryCount ;
   
    private String failedRectionsPath = StringUtils.EMPTY;

    @Activate
    public void activate(ProductImageProcessingSchedulerConfiguration config) {
        logger.info("==== Image Workflow Scheduler Activated ==== ");
        this.defaultRetryCount = Long.parseLong(config.defaultRetryLimit());
        this.tempAssetsPath = config.assetsPathListInDAM();
        this.unProcessedRecordsPath = config.assetsReferencePathListInVar();
        this.failedRectionsPath = config.failedRecordsRepPathListInVar();
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
			 Session session = resourceResolver.adaptTo(Session.class);
			 productImageProcessingService.processProductImages(resourceResolver, session, tempAssetsPath, unProcessedRecordsPath, failedRectionsPath, defaultRetryCount);

	    	 PrintWriter out = response.getWriter();
			 response.setContentType("application/json");
			 response.setCharacterEncoding("UTF-8");
			 out.print("Successfully processed valid product images");
	     } catch (LoginException | IOException e) {
	    	 logger.error("Failed to process product images {}", e.getMessage());
			 response.getWriter().write("Either partial images processed or issue while processing images.");
	    	 logger.error("LoginException :", e);
        } finally {
            CommonHelper.closeResourceResolver(resourceResolver);
        }
	}
}