package com.bdb.aem.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;

import com.bdb.aem.core.util.SVUtils;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.SpectrumViewerConfigService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.dam.api.Asset;

/**
 * @author Saroj
 */
@Component(service = Servlet.class, property = {
		Constants.SERVICE_DESCRIPTION + "= Cytometer Config Json Data in dynamic Dropdown",
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + CytometerConfigDropdownServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class CytometerConfigDropdownServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "/bin/cytometerConfigJsonDataDropdown";


	private static final Logger LOGGER = LoggerFactory.getLogger(CytometerConfigDropdownServlet.class);

	@Reference
	private transient SpectrumViewerConfigService spectrumViewerConfigService;

	@Reference
	private transient ResourceResolverFactory resolverFactory;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

		Gson gson = new Gson();
		response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
		response.setCharacterEncoding("UTF-8");
		List<String> subtypeList = new ArrayList<>();
		try {
			/* Getting file Path from OSGI config */
			String spectrumViewerConfigParentPath = SVUtils.getSvSystemConfigFolderPath(request,resolverFactory);
			String cytometerConfigFile = spectrumViewerConfigService.getCytometerConfigFile();

			if(StringUtils.isNotEmpty(spectrumViewerConfigParentPath) && StringUtils.isNotEmpty(cytometerConfigFile)) {
				String cytoConfigFilePath = spectrumViewerConfigParentPath + CommonConstants.SINGLE_SLASH + cytometerConfigFile;
				Resource jsonResource = request.getResourceResolver().getResource(cytoConfigFilePath);
			String qs = request.getParameter("type").toString();
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
				Iterator<JsonElement> sortedIterator = SVUtils.getSortedIterator(it, new Comparator<JsonElement>() {
					@Override
					public int compare(JsonElement r, JsonElement t) {
						String a = r.getAsJsonObject().get("name").getAsString();
						String b = t.getAsJsonObject().get("name").getAsString();

						return a.compareTo(b);
					}
				});
				while (sortedIterator.hasNext()) {
					JsonObject jobj = (JsonObject) sortedIterator.next();

					boolean check = jobj.get("name").getAsString().replace("\"", "").equalsIgnoreCase(qs.replace("\"", ""));
					if (jobj.has("name") && check) {

						JsonArray configJsonoArray = jobj.getAsJsonArray("cytometer_profiles");
						Iterator<JsonElement> itc = configJsonoArray.iterator();
						while (itc.hasNext()) {

							JsonObject cjobj = (JsonObject) itc.next();
							subtypeList.add(cjobj.get("configuration_name").getAsString());
						}
						break;
					}
				}

			/* Create a DataSource that is used to populate the drop-down control */
			String jsonString = gson.toJson(subtypeList);
			response.getWriter().write(jsonString);

		}
		} catch (IOException | JsonSyntaxException e) {
			LOGGER.error("Error in Json Data Exporting : {}", e.getMessage());
		}
	}
}