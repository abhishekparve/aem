package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.SpectrumViewerConfigService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SVUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Saroj
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= SVG icon Data in dynamic Dropdown",
		"sling.servlet.paths=" + "/bin/csvg", ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class SvgIconsDynamicDropDownServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String RESOURCE_TYPE = "/bin/csvg";

	private static final Logger LOGGER = LoggerFactory.getLogger(SvgIconsDynamicDropDownServlet.class);

	@Reference
	private transient SpectrumViewerConfigService spectrumViewerConfigService;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		try {
			Gson gson = new Gson();
			response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
			response.setCharacterEncoding("UTF-8");
			ResourceResolver resourceResolver = request.getResourceResolver();
			String path = spectrumViewerConfigService.getSpectrumViewerIconsPath();
			if(StringUtils.isNotEmpty(path)) {
				Resource resourcePath = resourceResolver.getResource(path);
				if (resourcePath != null) {
					JsonArray ja = new JsonArray();
					JsonObject svgJson = new JsonObject();
					JsonElement svgJsonElement;
					Iterator<Resource> iterator = resourcePath.listChildren();
					while (iterator.hasNext()) {
						final Resource svg = iterator.next();
						String svgName = svg.getName();
						String svgPath = svg.getPath();
						svgJson.addProperty(CommonConstants.TEXT, svgName);
						svgJson.addProperty(CommonConstants.VALUE, svgPath);
						svgJsonElement = gson.fromJson(svgJson.toString(), JsonElement.class);
						ja.add(svgJsonElement);
					}
					JsonArray sortedIcons = SVUtils.sortJsonObject(ja, "text");
					String jsonString = gson.toJson(sortedIcons);
					response.getWriter().write(jsonString);
				}
			}
		} catch (IOException e) {
			LOGGER.error("Error in Get Drop Down Values", e);
		}
	}
}