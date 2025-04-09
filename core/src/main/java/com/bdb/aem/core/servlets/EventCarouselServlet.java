package com.bdb.aem.core.servlets;

import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * The Event Carousel Servlet.
 * 
 * @author ronbanerjee
 *
 */
@Component(name = "EventCarouselServlet", service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Fetch Data",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + EventCarouselServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON })
public class EventCarouselServlet extends BaseServlet {

	/** The serial version UID */
	private static final long serialVersionUID = -5422113348553878006L;

	public static final String RESOURCE_TYPE = "bdb/event-carousel";

	/** The LOGGER */
	private static final Logger LOGGER = LoggerFactory.getLogger(EventCarouselServlet.class);

	/** The Resource Resolver Factory */
	@Reference
	private transient ResourceResolverFactory resolverFactory;

	@Override
	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) {

		final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) req;
		LOGGER.debug("Into Event Carousel Servlet");
		String pagePath = getRequestParameter(req, "pagePath");
		String type = getRequestParameter(req, "type");

		res.setContentType(CommonConstants.APPLICATION_JSON);
		res.setCharacterEncoding("UTF-8");
		try (ResourceResolver resourceResolver = slingRequest.getResourceResolver()) {

			PrintWriter writer = res.getWriter();
			writer.write(getJson(pagePath, type, resourceResolver));

			writer.close();
		} catch (IOException e) {
			LOGGER.error("IOException: ", e);
		}

	}

	/**
	 * Gets the request parameter from Sling Http Servlet Request.
	 * 
	 * @param req       the request
	 * @param parameter the parameter
	 * @return the param value.
	 */
	private String getRequestParameter(SlingHttpServletRequest req, String parameter) {

		return Optional.ofNullable(req).map(r -> r.getParameter(parameter)).filter(StringUtils::isNotEmpty)
				.orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets the event resource from page.
	 * 
	 * @param pagePath the page path
	 * @return resource
	 */
	private Resource getEventResource(String pagePath, ResourceResolver resourceResolver) {
		final Resource resource = Optional.ofNullable(resourceResolver)
				.map(r -> r.getResource(pagePath + CommonConstants.JCR_ROOT)).orElse(null);
		Iterator<Resource> it = resource == null ? null : resource.listChildren();
		if (it != null && it.hasNext()) {
			while (it.hasNext()) {
				final Resource childRes = it.next();
				if (childRes.getResourceType().equals("bdb-aem/proxy/components/content/eventblogDetails")) {
					return childRes;
				}
			}
		}
		return null;
	}

	/**
	 * Gets value from value map.
	 * 
	 * @param valueMap the value map.
	 * @param key      key
	 * @return the value
	 */
	private String getValue(final ValueMap valueMap, String key) {

		return Optional.ofNullable(valueMap).map(v -> v.get(key, String.class)).orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets value map from resource in argument.
	 * 
	 * @param resource resource
	 * @return valuemap
	 */
	private ValueMap getValueMapFromResource(Resource resource) {
		return Optional.ofNullable(resource).map(Resource::getValueMap).orElse(ValueMap.EMPTY);
	}

	/**
	 * Constructs Map for Event type.
	 * 
	 * @param pagePath the page path
	 * @return map
	 */
	private Map<String, Object> handleEventType(String pagePath, ResourceResolver resourceResolver) {

		Map<String, Object> eventData = new HashMap<>();
		final Resource resource = getEventResource(pagePath, resourceResolver);
		if (resource == null) {
			return handleIfInvalidResource(eventData);
		} else {
			final Resource childResource = Optional.ofNullable(resource).map(r -> r.getChild("multifieldSection"))
					.orElse(null);

			eventData.put("imageUrl", getValue(getValueMapFromResource(resource), "bannerThumbnailImage"));
			eventData.put("title", getValue(getValueMapFromResource(resource), "bannerTitle"));
			eventData.put("imageAlt", getValue(getValueMapFromResource(resource), "bannerImageAlt"));

			if (null != childResource && childResource.listChildren().hasNext()) {
				final Resource grandChildResource = childResource.listChildren().next();
				eventData.put(CommonConstants.DESCRIPTION,
						getValue(getValueMapFromResource(grandChildResource), CommonConstants.DESCRIPTION));
			}
			eventData.put(CommonConstants.ERROR, false);
			eventData.put(CommonConstants.ERROR_MESSAGE, "");
		}

		return eventData;

	}

	/**
	 * Handles Blog Type.
	 * 
	 * @param pagePath the page path
	 * @return map
	 */
	private Map<String, Object> handleBlogType(String pagePath, ResourceResolver resourceResolver) {
		Map<String, Object> blogData = new HashMap<>();
		final Resource resource = getEventResource(pagePath, resourceResolver);

		if (resource == null) {
			return handleIfInvalidResource(blogData);
		} else {
			blogData.put("imageUrl", getValue(getValueMapFromResource(resource), "bannerThumbnailImageBlog"));
			blogData.put("title", getValue(getValueMapFromResource(resource), "bannerTitleBlog"));
			blogData.put("imageAlt", getValue(getValueMapFromResource(resource), "bannerImageAltBlog"));
			blogData.put("description", getValue(getValueMapFromResource(resource), "bannerDescription"));
			blogData.put("subTitle", getValue(getValueMapFromResource(resource), "bannerSubTitle"));
			blogData.put(CommonConstants.ERROR, false);
			blogData.put(CommonConstants.ERROR_MESSAGE, "");

		}
		return blogData;
	}

	/**
	 * Handles if invalid resource.
	 * 
	 * @param data data
	 * @return map
	 */
	private Map<String, Object> handleIfInvalidResource(Map<String, Object> data) {

		Optional.ofNullable(data).ifPresent(m -> {
			m.put("error", true);
			m.put("errorMessage", "Invalid Page Path");
		});

		return data;

	}

	/**
	 * Gets the JSON string.
	 * 
	 * @param pagePath the page path
	 * @param type     the type
	 * @return the json
	 */
	private String getJson(String pagePath, String type, ResourceResolver resourceResolver) {
		final ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			if (type.equals("event")) {

				json = mapper.writeValueAsString(handleEventType(pagePath, resourceResolver));

			} else if (type.equals("blog")) {
				json = mapper.writeValueAsString(handleBlogType(pagePath, resourceResolver));
			} else {
				Map<String, Object> data = new HashMap<>();
				data.put("error", true);
				data.put("errorMessage", "Invalid Selection");
				json = mapper.writeValueAsString(data);
			}
		} catch (JsonProcessingException e) {
			LOGGER.error("JSON Processing Exception: ", e);
		}

		return json;
	}
}