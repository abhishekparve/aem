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
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.CreateExcelNodeService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

/**
 * The Class CreateExcelNodesServlet.
 */
@Component(immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE, service = Servlet.class, property = {
		ServletResolverConstants.SLING_SERVLET_PATHS + CommonConstants.EQUAL + "/bin/createexcelnodesservlet",
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON })
public class CreateExcelNodesServlet extends BaseServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	public static final Logger LOGGER = LoggerFactory.getLogger(CreateExcelNodesServlet.class);

	/** The resource resolver factory. */
	@Reference
	transient ResourceResolverFactory resourceResolverFactory;
	
	/** The create excel node service. */
	@Reference
	transient CreateExcelNodeService createExcelNodeService;

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
			resourceResolver = request.getResourceResolver();
			session = resourceResolver.adaptTo(Session.class);
			responseJson = createExcelNodeService.processDocMetadataExcel(session,resourceResolver, queryParam);
			long endTime = System.currentTimeMillis();
			String totalTime = CommonHelper.totalTimeInMinutesSeconds(endTime - startTime);
			responseJson = responseJson + ", Total time taken : " + totalTime;
		} catch (RepositoryException e) {
			LOGGER.error("Exception : ", e);
			responseJson = "Exception occured while creating excel nodes - " + e.getMessage();
		} finally {
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
	
	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}