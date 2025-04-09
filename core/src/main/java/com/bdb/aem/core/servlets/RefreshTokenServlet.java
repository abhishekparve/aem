
package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.SSOLoginService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.JsonObject;

/**
 * The Class RefreshTokenServlet.
 */
@Component(name = "RefreshTokenServlet", immediate = true, service = Servlet.class, property = {
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + RefreshTokenServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON })
public class RefreshTokenServlet extends BaseServlet {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/refresh-token";
	
	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenServlet.class);

	/** The SSO login service. */
	@Reference
	private transient SSOLoginService sSOLoginService;

	/**
	 * Post method.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOGGER.debug("Entry doPost of RefreshTokenServlet");
		doGet(request, response);
		LOGGER.debug("Exit doPost of RefreshTokenServlet");
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

		LOGGER.debug("Entry RefreshTokenServlet ");
		String apiResponse = StringUtils.EMPTY;

		try {
			JsonObject jsonObject = getRequestObject(request);
			LOGGER.debug("Request in doGet of RefreshTokenServlet : {}", jsonObject);
			sSOLoginService.refreshTokens(request, response, jsonObject);
		} catch (AemInternalServerErrorException exception) {
			LOGGER.error("Exception Occurred in RefreshTokenServlet {}, Message : {}", exception.getCause(),
					exception.getCause().getMessage());
			apiResponse = exception.getCause().getMessage();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} finally {
			response.setContentType(CommonConstants.APPLICATION_JSON);
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			if (null != writer) {
				writer.write(apiResponse);
				writer.flush();
				writer.close();
			}
		}

		LOGGER.debug("RefreshTokenServlet Exit");

	}
}
