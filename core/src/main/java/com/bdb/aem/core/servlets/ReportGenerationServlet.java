package com.bdb.aem.core.servlets;

import com.bdb.aem.core.bean.AssetBean;
import com.bdb.aem.core.services.ReportGenerationService;
import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.xss.XSSAPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.List;

@Component(immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE, service = { Servlet.class }, property = {
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + ReportGenerationServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON })
public class ReportGenerationServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	private final transient Logger LOG = LoggerFactory.getLogger(ReportGenerationServlet.class);
	
	public static final String RESOURCE_TYPE = "bdb/generate-report";

	@Reference
	private transient ReportGenerationService reportGenerationService;

	@Reference
	private transient XSSAPI xssapi;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		LOG.debug("Entering ReportGenerationServlet: goGet ");
		Gson gson = new Gson();
		response.setContentType("application/json");
		try {
			LOG.debug("Entering ReportGenerationServlet: goGet");
			List<AssetBean> assetBeans = reportGenerationService.fetchTestReportData();
			response.getWriter().write(gson.toJson(assetBeans));
		} catch (IOException e) {
			LOG.error("IOException : ReportGenerationServlet.doGet {}", e.getLocalizedMessage());
		}
		LOG.debug("Exiting ReportGenerationServlet: goGet ");
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		this.doGet(request, response);
	}
}
