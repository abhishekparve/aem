package com.bdb.aem.core.script;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.dam.api.Asset;
import com.google.gson.JsonObject;

/**
 * The Servlet establishes live relation with en.
 */
@Component(immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE, service = Servlet.class, property = {
		ServletResolverConstants.SLING_SERVLET_PATHS + CommonConstants.EQUAL + "/bin/liveSync",
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST })
public class LiveSyncServlet extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1L;

	protected static final Logger logger = LoggerFactory.getLogger(LiveSyncServlet.class);

	@Reference
	transient ResourceResolverFactory resolverFactory;

	@Override
	protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		logger.info("LiveSyncServlet - start");

		String operation = request.getParameter("operation");
		logger.info("Operation - {}", operation);

		String languagePath = request.getParameter("language-path");
		logger.info("language masters path - {}", languagePath);

		String damPath = request.getParameter("dam-path");
		logger.info("File path - {}", damPath);

		ResourceResolver resolver = request.getResourceResolver();
		Session session = resolver.adaptTo(Session.class);

		InputStream fileStream = getFileStream(damPath, resolver);
		JsonObject responseObj = new JsonObject();
		PrintWriter out = response.getWriter();

		if (null != session && null != fileStream && StringUtils.isNotEmpty(operation)
				&& StringUtils.isNotEmpty(languagePath)) {
			if ("page".equalsIgnoreCase(operation)) {
				DeepTree.getPaths(languagePath, session);
				saveSession(session);
				int i = 1;
				int j = 1;
				List<ExcelBean> beanList = ExcelReader.readFromExcel(fileStream);
				if (CollectionUtils.isNotEmpty(beanList)) {
					for (ExcelBean bean : beanList) {
						if (null != bean) {
							DeepTree.getPaths(bean, session);
							j++;
							if (j % 100 == 0) {
								saveSession(session);
								logger.info("Session Saved!!");
							}
							logger.info(i++ + " Processed - " + bean.getPath());
						}
					}
				}
				saveSession(session);
				logger.info("Session Saved!!");

				responseObj.addProperty("status", "success");
				responseObj.addProperty("description", "All pages and its nodes are updated for all the given paths.");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				out.print(responseObj);
				out.flush();
			} else if ("page-properties".equalsIgnoreCase(operation)) {
				List<PathPagePropertiesBean> beanList = ExcelReader.readPathPagePropertiesFromExcel(fileStream);
				if (CollectionUtils.isNotEmpty(beanList)) {
					int i = 1;
					for (PathPagePropertiesBean bean : beanList) {
						if (null != bean) {
							DeepTree.getPaths(bean, session);
							logger.info(i++ + " Processed - " + bean.getPath());

						}
					}
				}
				saveSession(session);
				logger.info("Session Saved!!!");

				responseObj.addProperty("status", "success");
				responseObj.addProperty("description", "Page properties updated for all the given paths.");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				out.print(responseObj);
				out.flush();
			}
		} else {
			logger.error("Unable to create a session / Give proper inputs");
			responseObj.addProperty("status", "failed");
			responseObj.addProperty("description", "Unable to create a session / Give proper inputs.");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(responseObj);
			out.flush();
		}
		logoutSession(session);

	}

	private InputStream getFileStream(String damPath, ResourceResolver resolver) {
		InputStream stream = null;
		Resource excelPathResource = resolver.getResource(damPath);
		if (null != excelPathResource) {
			Asset asset = excelPathResource.adaptTo(Asset.class);
			if (null != asset) {
				stream = asset.getOriginal().getStream();
			}
		}
		return stream;
	}

	public static void saveSession(Session session) {
		try {
			if (null != session) {
				session.save();
			}
		} catch (RepositoryException e) {
			logger.error("Error while saving the session");
		}
	}

	public static void logoutSession(Session session) {
		if (null != session) {
			session.logout();
		}
	}

}
