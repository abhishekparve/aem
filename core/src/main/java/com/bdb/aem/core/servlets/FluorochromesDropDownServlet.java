package com.bdb.aem.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.bdb.aem.core.services.SpectrumViewerConfigService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SVUtils;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author SarojP
 */
@Component(service = Servlet.class, property = {
		Constants.SERVICE_DESCRIPTION + "= Fluorochromes Json Data in dynamic Dropdown",
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + FluorochromesDropDownServlet.RESOURCE_TYPE,
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class FluorochromesDropDownServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "/bin/flurochromesJsonDataDropdown";

	private static final Logger LOGGER = LoggerFactory.getLogger(FluorochromesDropDownServlet.class);

	transient ValueMap valueMap;
	transient List<Resource> resourceList;

	@Reference
	private transient SpectrumViewerConfigService spectrumViewerConfigService;

	@Reference
	private transient ResourceResolverFactory resolverFactory;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		LOGGER.debug("Staring with request.getRequestPathInfo {}",request.getRequestPathInfo());

		try {
			ResourceResolver resourceResolver = request.getResourceResolver();
			/* Getting file path from SV component */
			String svSystemConfigPath = SVUtils.getSvSystemConfigFolderPath(request, resolverFactory);
			LOGGER.debug("Path for Spectrum Files is:  {}", svSystemConfigPath);

			resourceList = new ArrayList<>();

			/* Getting file name from OSGI config */
			String fluorochromeConfigFile = spectrumViewerConfigService.getFluorochromeConfigFile();

			if (StringUtils.isNotEmpty(svSystemConfigPath)
					&& StringUtils.isNotEmpty(fluorochromeConfigFile)) {
				Resource jsonResource = request.getResourceResolver().getResource(
						svSystemConfigPath + CommonConstants.SINGLE_SLASH + fluorochromeConfigFile);

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
				JsonArray jsonArray = jsonObject.getAsJsonArray("fluorochromes");
				Iterator<JsonElement> it = jsonArray.iterator();
				try {
					Iterator<JsonElement> sortedIterator = SVUtils.getSortedIterator(it,
							new Comparator<JsonElement>() {
								@Override
								public int compare(JsonElement compare_r, JsonElement compare_t) {
									String tempValue1 = compare_r.getAsJsonObject().get("name").getAsString();
									String tempValue2 = compare_t.getAsJsonObject().get("name").getAsString();

									return tempValue1.compareTo(tempValue2);
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
				} catch (Exception e) {
					LOGGER.error("Error in sorting json data : {}", e.getMessage());
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