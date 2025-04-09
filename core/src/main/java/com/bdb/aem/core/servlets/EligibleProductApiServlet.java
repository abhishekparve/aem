package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.exceptions.InvalidRequestException;
import com.bdb.aem.core.services.EligibleProductsApiService;
import com.bdb.aem.core.services.UserRegistrationApiService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.JsonObject;

/**
 * Servlet to fetch the details related to user registration flow of SIgn up,
 * Area of focus, Reset password and purchase account.
 */

@Component(name = "EligibleProductApiServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Get Eligible Products",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + EligibleProductApiServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON
})
public class EligibleProductApiServlet extends BaseServlet {
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/eligible-products-api";

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EligibleProductApiServlet.class);

	/**
	 * The base api service for hybris calls.
	 */
	@Reference
	private transient EligibleProductsApiService eligibleProductsApiService;

	/**
	 * Post method.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOGGER.debug("Entry doPost of UserRegistrationApiServlet");
		doGet(request, response);
		LOGGER.debug("Exit doPost of UserRegistrationApiServlet");
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

		LOGGER.debug("Entry doGet of UserRegistrationApiServlet");
		PrintWriter writer;
		String apiResponse = StringUtils.EMPTY;
		JsonObject requestObject = getRequestObject(request);

		try {
			RequestPathInfo requestPathInfo = request.getRequestPathInfo();
			String[] selectors = requestPathInfo.getSelectors();
			
			apiResponse = eligibleProductsApiService.fetchPromoDetails(request, requestObject, response,"demo");
			
			
		}  catch (AemInternalServerErrorException exception) {
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

		LOGGER.debug("Exit doGet of UserRegistrationApiServlet");

	}
}
