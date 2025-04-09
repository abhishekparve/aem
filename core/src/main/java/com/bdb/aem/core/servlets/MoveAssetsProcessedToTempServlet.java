package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.MoveAssetsProcessedToTempService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

/**
 * The Class MoveAssetsProcessedToTempServlet.
 */
@Component(immediate = true, service = Servlet.class, configurationPolicy = ConfigurationPolicy.REQUIRE, property = {
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + MoveAssetsProcessedToTempServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON })
public class MoveAssetsProcessedToTempServlet extends BaseServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    public static final String RESOURCE_TYPE = "bdb/move-processed-assets";

	/** The Constant LOGGER. */
	public static final Logger LOGGER = LoggerFactory.getLogger(MoveAssetsProcessedToTempServlet.class);

	/** The resource resolver factory. */
	@Reference
	transient ResourceResolverFactory resourceResolverFactory;
	/** The resource resolver factory. */

	@Reference
	transient MoveAssetsProcessedToTempService moveAssetsProcessedToTempService;

	/**
	 * Do post.
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
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		PrintWriter writer = null;
		ResourceResolver resourceResolver = null;
		Session session = null;
		String responseJson = StringUtils.EMPTY;
		try {
			Map<String, String> queryParam = CommonHelper.getQueryParamsFromRequest(request);
			long startTime = System.currentTimeMillis();
			LOGGER.debug("Servlet starting time {}", startTime);
			resourceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
			session = resourceResolver.adaptTo(Session.class);
			responseJson = moveAssetsProcessedToTempService.moveAssets(session, resourceResolver, queryParam);
			long endTime = System.currentTimeMillis();
			LOGGER.debug("Servlet End time - {}", endTime);
			String totalTime = CommonHelper.totalTimeInMinutesSeconds(endTime - startTime);
			LOGGER.debug("Total time taken for Servlet - {}", totalTime);
			responseJson = responseJson + ", Total time taken : " + totalTime;
		} catch (LoginException | RepositoryException e) {
			LOGGER.error("Exception {}", e.getMessage());
			responseJson = "Exception occured while moving the resources - " + e.getMessage();
		} finally {
			CommonHelper.closeResourceResolver(resourceResolver);
			CommonHelper.closeSession(session);
			response.setContentType(CommonConstants.APPLICATION_JSON);
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			if (null != writer) {
				writer.print(responseJson);
				writer.flush();
				writer.close();
			}
		}
	}
}