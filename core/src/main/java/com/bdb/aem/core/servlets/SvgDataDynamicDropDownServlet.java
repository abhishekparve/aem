package com.bdb.aem.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.bdb.aem.core.services.SpectrumViewerConfigService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.util.Comparator;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;



/**
 * @author Saroj
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= SVG Json Data in dynamic Dropdown",
		"sling.servlet.paths=" + "/bin/svgData", ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class SvgDataDynamicDropDownServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "/bin/svgData";

	private static final Logger LOGGER = LoggerFactory.getLogger(SvgDataDynamicDropDownServlet.class);

	transient ValueMap valueMap;
	transient List<Resource> resourceList;

	@Reference
	private transient SpectrumViewerConfigService spectrumViewerConfigService;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		try {
			resourceList = new ArrayList<>();
			Resource resource = request.getResource();
			Resource requestDataSource = resource.getChild("datasource");
			ValueMap valueMap = requestDataSource.getValueMap();
			String path = spectrumViewerConfigService.getSpectrumViewerIconsPath();
			String defaultSelectedValue = valueMap.get("defaultSelectedValue", String.class);
			LOGGER.debug("RootPath for IconPicker is: {}",path);
			LOGGER.debug("defaultSelector is: {}", valueMap.get("defaultSelectValue",String.class));
			ResourceResolver resourceResolver = request.getResourceResolver();
			if(StringUtils.isNotEmpty(path) && resourceResolver!=null) {
				Resource resourcePath = resourceResolver.getResource(path);
				if (resourcePath != null) {
					Iterator<Resource> iterator = resourcePath.listChildren();
					final Iterator<Resource> sortedIterator = getSortedIterator(iterator, new Comparator<Resource>() {
						@Override
						public int compare(Resource r, Resource t) {
							return r.getName().compareTo(t.getName());
						}
					});
					while (sortedIterator.hasNext()) {
						final Resource svg = sortedIterator.next();
						String svgName = svg.getName();
						String svgPath = svg.getPath();
						valueMap = new ValueMapDecorator(new HashMap<>());
						valueMap.put(CommonConstants.VALUE, svgPath);
						valueMap.put(CommonConstants.TEXT, svgName);
						if (StringUtils.equals(svgName, defaultSelectedValue)) {
							valueMap.put("selected", true);
						}
						resourceList.add(new ValueMapResource(resourceResolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, valueMap));
					}
					/* Create a DataSource that is used to populate the drop-down control */
					DataSource dataSource = new SimpleDataSource(resourceList.iterator());
					request.setAttribute(DataSource.class.getName(), dataSource);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error in Get Drop Down Values : {}", e.getMessage());

		}
	}

	/*
		Method to sort the Resources
		param @iterator
		param @Comparator
		return Iterator
	 */
	private Iterator<Resource> getSortedIterator(Iterator<Resource> it, Comparator<Resource> comparator) {
		List<Resource> list = new ArrayList<Resource>();
		while (it.hasNext()) {
			list.add(it.next());
		}
		Collections.sort(list, comparator);
		return list.iterator();
	}
}
