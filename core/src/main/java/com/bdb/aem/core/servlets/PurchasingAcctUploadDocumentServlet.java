
package com.bdb.aem.core.servlets;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.PurchasingAcctUploadDocumentService;
import com.bdb.aem.core.util.CommonConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
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
import java.io.PrintWriter;

/**
 * The Purchasing AcctUpload Document Servlet.
 */
@Component(name = "PurchasingAcctUploadDocumentServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Fetch Data",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + PurchasingAcctUploadDocumentServlet.RESOURCE_TYPE
})
public class PurchasingAcctUploadDocumentServlet extends BaseServlet {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/upload-purchase-account-document";
	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchasingAcctUploadDocumentServlet.class);

	/** The purchasing acct upload document service. */
	@Reference
	private transient PurchasingAcctUploadDocumentService purchasingAcctUploadDocumentService;

	/**
	 * Post method.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOGGER.debug("Entry doPost of PurchasingAcctUploadDocumentServlet");
		doGet(request, response);
		LOGGER.debug("Exit doPost of PurchasingAcctUploadDocumentServlet");
	}

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOGGER.debug("PurchasingAcctUploadDocumentServlet- request: {}", request);
		PrintWriter writer;
		String apiResponse = StringUtils.EMPTY;

		try {
			apiResponse = purchasingAcctUploadDocumentService.uploadDocument(request, response);
		} catch (AemInternalServerErrorException exception) {
			apiResponse = exception.getMessage();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			LOGGER.error("Exception Occurred during rest client execution {0}", exception.getCause());
		} finally {
			response.setContentType(CommonConstants.APPLICATION_JSON);
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			if (null != writer) {
				writer.write(apiResponse);
				writer.flush();
				writer.close();
			}
		}
		LOGGER.debug("PurchasingAcctUploadDocumentServlet Exit");
	}
}
