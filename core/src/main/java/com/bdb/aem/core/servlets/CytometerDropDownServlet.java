package com.bdb.aem.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;

import com.bdb.aem.core.util.SVUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
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

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.bdb.aem.core.services.SpectrumViewerConfigService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author Saroj
 */
@Component(service = Servlet.class, property = {
		Constants.SERVICE_DESCRIPTION + "= Cytometer Json Data in dynamic Dropdown",
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + CytometerDropDownServlet.RESOURCE_TYPE,
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class CytometerDropDownServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "/bin/cytometerJsonDataDropdown";


	private static final Logger LOGGER = LoggerFactory.getLogger(CytometerDropDownServlet.class);

	transient ValueMap valueMap;
	transient List<Resource> resourceList;

	@Reference
	private transient SpectrumViewerConfigService spectrumViewerConfigService;

	@Reference
	private transient ResourceResolverFactory resolverFactory;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		ResourceResolver resourceResolver = request.getResourceResolver();
		resourceList = new ArrayList<>();

		try {
			/* Getting file Path from OSGI config */
			String spectrumViewerConfigParentPath = SVUtils.getSvSystemConfigFolderPath(request,resolverFactory);
			String cytometerConfigFile = spectrumViewerConfigService.getCytometerConfigFile();

			if (StringUtils.isNotEmpty(spectrumViewerConfigParentPath) && StringUtils.isNotEmpty(cytometerConfigFile)) {
				Resource jsonResource = request.getResourceResolver().getResource(
						spectrumViewerConfigParentPath + CommonConstants.SINGLE_SLASH + cytometerConfigFile);

				assert jsonResource != null;
				// Getting Asset from jsonResource
				Asset jsonAsset = jsonResource.adaptTo(Asset.class);
				assert jsonAsset != null;
				// Converting Asset into input stream for JSON Object
				InputStream inputStream = jsonAsset.getOriginal().getStream();

				StringBuilder stringBuilder = new StringBuilder();
				String eachLine;
				assert inputStream != null;
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream, StandardCharsets.UTF_8));

				while ((eachLine = bufferedReader.readLine()) != null) {
					stringBuilder.append(eachLine);
				}

				JsonObject jsonObject = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
				JsonArray cJsonoArray = jsonObject.getAsJsonArray("cytometers");
				Iterator<JsonElement> it = cJsonoArray.iterator();
					Iterator<JsonElement> sortedIterator = SVUtils.getSortedIterator(it,
							new Comparator<JsonElement>() {
								@Override
								public int compare(JsonElement r, JsonElement t) {
									String a = r.getAsJsonObject().get("name").getAsString();
									String b = t.getAsJsonObject().get("name").getAsString();

									return a.compareTo(b);
								}
							});
					while (sortedIterator.hasNext()) {
						JsonObject jobj = (JsonObject) sortedIterator.next();
						valueMap = new ValueMapDecorator(new HashMap<>());
						valueMap.put(CommonConstants.VALUE, jobj.get("name").getAsString());
						valueMap.put(CommonConstants.TEXT, jobj.get("name").getAsString());
						resourceList.add(new ValueMapResource(resourceResolver, new ResourceMetadata(),
								JcrConstants.NT_UNSTRUCTURED, valueMap));
					}

				/* Create a DataSource that is used to populate the drop-down control */
				DataSource dataSource = new SimpleDataSource(resourceList.iterator());
				request.setAttribute(DataSource.class.getName(), dataSource);
			}

		} catch (IOException e) {
			LOGGER.error("Error in Json Data Exporting : {}", e.getMessage());
		}
	}
}