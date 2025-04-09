package com.bdb.aem.core.services.solr.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.jcr.RepositoryException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.FetchingToolsService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class FetchingToolsServiceImpl.
 */
@Component(service = FetchingToolsService.class, immediate = true)
public class FetchingToolsServiceImpl implements FetchingToolsService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FetchingToolsServiceImpl.class);

	/** The response. */
	JsonObject response = new JsonObject();
	
	@Reference
	BDBSearchEndpointService solrConfig;
	
	@Reference
	ExternalizerService externalizerService;
	
	/** The bdb api endpoint service. */
	@Reference
	BDBApiEndpointService bdbApiEndpointService;

	/**
	 * Gets the cell type data.
	 *
	 * @param country                       the country
	 * @param solrSearchService             the solr search service
	 * @param cellType                      the cell type
	 * @param panelType                     the panel type
	 * @param type                          the type
	 * @param catalogStructureUpdateService the catalog structure update service
	 * @param resourceResolver              the resource resolver
	 * @return the cell type data
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	@Override
	public String getCellTypeData(String country, SolrSearchService solrSearchService, JsonArray cellType,
			String type, CatalogStructureUpdateService catalogStructureUpdateService,
			ResourceResolver resourceResolver, String reactivity)
			throws IOException, SolrServerException, RepositoryException {
		String responseJson = StringUtils.EMPTY;
		JsonArray cellOrPanelData = new JsonArray();
		HttpSolrClient server = solrSearchService.solrClient(country);
		LOG.debug("Request Type for ICM {}", type);
		// If the type equals panelData then fetching only the panel data from solr using fetchPanelTypes method
        if (type.equalsIgnoreCase(CommonConstants.PANEL_DATA)) {
        	cellOrPanelData = fetchPanelTypes(server);
		} else {
			cellOrPanelData = fetchCellTypes(cellType, server, type, reactivity);
		}

        if (cellOrPanelData.size() > 0 && type.equalsIgnoreCase(CommonConstants.CELL_TYPE)) {
			responseJson = getCellFinalJson(cellOrPanelData, cellType);
		} else if (cellOrPanelData.size() > 0 && type.equalsIgnoreCase(CommonConstants.PANEL_TYPE)) {
			responseJson = getPanelFinalJson(cellOrPanelData, resourceResolver, catalogStructureUpdateService);
		}else if (cellOrPanelData.size() > 0 && type.equalsIgnoreCase(CommonConstants.PANEL_DATA)) {
			responseJson = getPanelFinalJson(cellOrPanelData, resourceResolver, catalogStructureUpdateService);
		}else if (cellOrPanelData.size() == 0) {
			JsonObject empty = new JsonObject();
			empty.addProperty("Empty", "No Results Found");
			return empty.toString();
		}
		return responseJson;
	}

	/**
	 * Gets the panel final json.
	 *
	 * @param cellTypeData                  the cell type data
	 * @param resourceResolver              the resource resolver
	 * @param catalogStructureUpdateService the catalog structure update service
	 * @return the panel final json
	 * @throws RepositoryException the repository exception
	 * @throws IOException 
	 */
	private String getPanelFinalJson(JsonArray cellTypeData, ResourceResolver resourceResolver,
			CatalogStructureUpdateService catalogStructureUpdateService) throws RepositoryException, IOException {
		JsonObject cellListJsonObj = constructPanelResponseFromSolrData(cellTypeData, resourceResolver,
				catalogStructureUpdateService);
		LOG.debug("Response for CellType Formated Data {}", cellTypeData);
		return cellListJsonObj.toString();
	}

	/**
	 * Construct panel response from solr data.
	 *
	 * @param results                       the results
	 * @param resourceResolver              the resource resolver
	 * @param catalogStructureUpdateService the catalog structure update service
	 * @return the json object
	 * @throws RepositoryException the repository exception
	 * @throws IOException 
	 */
	private JsonObject constructPanelResponseFromSolrData(JsonArray results, ResourceResolver resourceResolver,
			CatalogStructureUpdateService catalogStructureUpdateService) throws RepositoryException, IOException {
		JsonObject panelListJsonObj = new JsonObject();
		JsonArray panelFiltersList = new JsonArray();
		JsonObject instrumentFilterTypeJson = new JsonObject();
		JsonObject paneTypeFilterJson = new JsonObject();
		JsonArray instFiltersOptionsList = new JsonArray();
		JsonArray panelFiltersOptionsList = new JsonArray();
		HashSet<String> colorFilterSet = new HashSet<String>();
		HashSet<String> paneTypeFilterSet = new HashSet<String>();
		String accordionDescription = StringUtils.EMPTY;
		String previousDescription = StringUtils.EMPTY;

		JsonArray panels = new JsonArray();
		JsonArray tablebodyData = null;
		JsonArray tableHeadings = new JsonArray();
		tableHeadings.add("Laser Line");
		tableHeadings.add("Marker");
		tableHeadings.add("Fluorochrome");
		tableHeadings.add("Clone");
		tableHeadings.add("Test per Kit");
		tableHeadings.add("Volume per Test");
		tableHeadings.add("Cat. Number");
		tableHeadings.add("Add to Cart");

		JsonObject accordionDataJson = new JsonObject();
		JsonArray reagentTables = new JsonArray();
		JsonObject reagentsSectionJson = new JsonObject();

		String previousPanelName = StringUtils.EMPTY;
		String pdpProductUrl = StringUtils.EMPTY;
		JsonObject accordionItemJson = new JsonObject();
		HashMap<String,String> toolTipMap = new  HashMap<String, String>();
		int i = 0;
		if (null != results && results.size() > 0) {
			for (JsonElement panelJsonEle : results) {
				JsonObject panelJsonObject = panelJsonEle.getAsJsonObject();

				JsonObject tableRowJson = new JsonObject();
				String panelTypeName = getJsonProperty(panelJsonObject, CommonConstants.PANEL_TYPE);
				if (StringUtils.isNotBlank(panelTypeName)) {
					paneTypeFilterSet.add(panelTypeName);
				}
				String color = getJsonProperty(panelJsonObject, CommonConstants.COLOR);
				if (StringUtils.isNotBlank(color)) {
					colorFilterSet.add(color);
				}
				String panelName = getJsonProperty(panelJsonObject, CommonConstants.PANEL_NAME);
				String panelId = getJsonProperty(panelJsonObject, CommonConstants.PANEL_ID);
				String marker = getJsonProperty(panelJsonObject, CommonConstants.MARKER);
				String fluoroChrome = getJsonProperty(panelJsonObject, CommonConstants.FLUOROCHROME);
				String clone = getJsonProperty(panelJsonObject, CommonConstants.CLONE);
				String testOrKit = getJsonProperty(panelJsonObject, CommonConstants.TEST_OR_KIT);
				String volumeOrTest = getJsonProperty(panelJsonObject, CommonConstants.VOLUME_OR_TEST);
				String catalogNumber = getJsonProperty(panelJsonObject, CommonConstants.CATALOG_NUMBER_KEY);
				String toolTip = getJsonProperty(panelJsonObject, "panelToolTip");
				toolTipMap.put(panelTypeName, toolTip);
				Resource variantRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver,
						catalogNumber, CommonConstants.MATERIAL_NUMBER);
				if(!previousPanelName.equals(panelName)){
					previousDescription = accordionDescription;
				}
				accordionDescription = getJsonProperty(panelJsonObject, "panelDescription");
				accordionDescription = HandleRTEAnchorLinkExtrnalization(accordionDescription, externalizerService, resourceResolver, StringUtils.EMPTY);
				if (null != variantRes) {
					Resource variantHpResource = variantRes.getChild(CommonConstants.HP);
					pdpProductUrl = CommonHelper.getPdpProductUrl(variantHpResource);
					if (StringUtils.isNotBlank(pdpProductUrl)) {
						String rummode = CommonHelper.getRunMode(bdbApiEndpointService);
						if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.AUTHOR)) {
							pdpProductUrl = CommonConstants.CONTENT + CommonConstants.SINGLE_SLASH
									+ CommonConstants.BDB + CommonConstants.SINGLE_SLASH + "{region}"
									+ CommonConstants.SINGLE_SLASH + "{country}" + CommonConstants.SINGLE_SLASH
									+ "{language-country}" + pdpProductUrl + ".html";
						} else if (StringUtils.isNotEmpty(rummode)
								&& rummode.equalsIgnoreCase(CommonConstants.PUBLISH)) {
							pdpProductUrl = CommonConstants.SINGLE_SLASH + "{language-country}"
									+ pdpProductUrl.replace("pdp.", "");
						}
					}
				}
				String laserLine = getJsonProperty(panelJsonObject, "laserLine");
				laserLine = StringUtils.isNotBlank(laserLine) ? laserLine.concat(CommonConstants.SPACE+CommonConstants.NM) : StringUtils.EMPTY;

				/* Condition to get unique panel name object from list of panel names */
				if (StringUtils.isBlank(previousPanelName) || !previousPanelName.equals(panelName)) {
					if (!accordionItemJson.entrySet().isEmpty()) {
						panels.add(accordionItemJson);
					}

					getCommonJsonElements(previousDescription, tableHeadings, accordionDataJson, reagentTables,
							reagentsSectionJson, accordionItemJson);
					accordionItemJson = new JsonObject();
					accordionItemJson.addProperty("name", panelName);
					accordionItemJson.addProperty("panelTypeId", panelTypeName);
					accordionItemJson.addProperty("id", i);
					accordionItemJson.addProperty("panelId", panelId);
					reagentsSectionJson = new JsonObject();
					tablebodyData = new JsonArray();
					reagentTables = new JsonArray();
					accordionDataJson = new JsonObject();
					i++;
				}
				/* Logic to update table rows and repeating items of json object */
				previousPanelName = panelName;
				tableRowJson.addProperty("title", color + CommonConstants.SPACE + laserLine);
				tableRowJson.addProperty("key", color);
				tableRowJson.addProperty("marker", marker);
				tableRowJson.addProperty("fluorochrome", fluoroChrome);
				tableRowJson.addProperty("clone", clone);
				tableRowJson.addProperty("testPerKit", testOrKit);
				tableRowJson.addProperty("volPerTest", volumeOrTest);
				tableRowJson.addProperty("catNumber", catalogNumber);
				tableRowJson.addProperty("productUrl", pdpProductUrl);
				tablebodyData.add(tableRowJson);
				reagentsSectionJson.add("tbodyData", tablebodyData);

			}

			getCommonJsonElements(accordionDescription, tableHeadings, accordionDataJson, reagentTables,
					reagentsSectionJson, accordionItemJson);
			panels.add(accordionItemJson);

			/* color filter start */
			createColorFilterJson(panelFiltersList, instrumentFilterTypeJson, instFiltersOptionsList, colorFilterSet);
			/* panelType filter start */
			createPanelTypeFilterJson(panelFiltersList, paneTypeFilterJson, panelFiltersOptionsList, paneTypeFilterSet,toolTipMap);
			/* number of parameters filter start */
			String json = "{\r\n" + "            \"name\": \"Number of Parameters\",\r\n"
					+ "            \"id\": \"parameters\",\r\n" + "            \"multiSelect\": false,\r\n"
					+ "            \"filterOptions\": [\r\n" + "                {\r\n"
					+ "                    \"key\": \"gt18\",\r\n" + "                    \"value\": \"> 18\",\r\n"
					+ "                    \"selected\": false,\r\n" + "                    \"hasTooltip\": false,\r\n"
					+ "                    \"tooltipText\": \"\"\r\n" + "                },\r\n"
					+ "				{\r\n" + "                    \"key\": \"ltet18\",\r\n"
					+ "                    \"value\": \"≤ 18\",\r\n" + "                    \"selected\": false,\r\n"
					+ "                    \"hasTooltip\": false,\r\n" + "                    \"tooltipText\": \"\"\r\n"
					+ "                },\r\n" + "                {\r\n"
					+ "                    \"key\": \"ltet12\",\r\n" + "                    \"value\": \"≤ 12\",\r\n"
					+ "                    \"selected\": false,\r\n" + "                    \"hasTooltip\": false,\r\n"
					+ "                    \"tooltipText\": \"\"\r\n" + "                },\r\n"+ "                {\r\n"
					+ "                    \"key\": \"ltet8\",\r\n" + "                    \"value\": \"≤ 8\",\r\n"
					+ "                    \"selected\": false,\r\n" + "                    \"hasTooltip\": false,\r\n"
					+ "                    \"tooltipText\": \"\"\r\n" + "                },\r\n"
					+ "                {\r\n" + "                    \"key\": \"All\",\r\n"
					+ "                    \"value\": \"All\",\r\n" + "                    \"selected\": true,\r\n"
					+ "                    \"hasTooltip\": false,\r\n" + "                    \"tooltipText\": \"\"\r\n"
					+ "                }\r\n" + "            ]\r\n" + "        }";
			JsonObject numberOfParameterJson = new JsonParser().parse(json).getAsJsonObject();
			panelFiltersList.add(numberOfParameterJson);
			// number of parameters filter end
		}
		panelListJsonObj.add("filters", panelFiltersList);
		panelListJsonObj.add("panels", panels);
		return panelListJsonObj;
	}

	/**
	 * Gets the common json elements.
	 *
	 * @param accordionDescription the accordion description
	 * @param tableHeadings the table headings
	 * @param accordionDataJson the accordion data json
	 * @param reagentTables the reagent tables
	 * @param reagentsSectionJson the reagents section json
	 * @param accordionItemJson the accordion item json
	 * @return the common json elements
	 */
	public void getCommonJsonElements(String accordionDescription, JsonArray tableHeadings,
			JsonObject accordionDataJson, JsonArray reagentTables, JsonObject reagentsSectionJson,
			JsonObject accordionItemJson) {
		reagentsSectionJson.addProperty("sectionTitle", "Panel Reagents");
		reagentsSectionJson.add("tableHeadings", tableHeadings);
		reagentTables.add(reagentsSectionJson);
		accordionDataJson.add("reagentTables", reagentTables);
		accordionDataJson.addProperty("rteData", accordionDescription);
		accordionItemJson.add("data", accordionDataJson);
	}

	/**
	 * Creates the panel type filter json.
	 *
	 * @param panelFiltersList        the panel filters list
	 * @param paneTypeFilterJson      the pane type filter json
	 * @param panelFiltersOptionsList the panel filters options list
	 * @param paneTypeFilterSet       the pane type filter set
	 * @param toolTipMap 
	 */
	public void createPanelTypeFilterJson(JsonArray panelFiltersList, JsonObject paneTypeFilterJson,
			JsonArray panelFiltersOptionsList, Set<String> paneTypeFilterSet, Map<String, String> toolTipMap) {
		Iterator<String> paneTypeItem = paneTypeFilterSet.iterator();
		while (paneTypeItem.hasNext()) {
			JsonObject panelFilterJson = new JsonObject();
			String paneTypeValue = paneTypeItem.next();
			panelFilterJson.addProperty("key", paneTypeValue);
			panelFilterJson.addProperty("value", paneTypeValue);
			panelFilterJson.addProperty("selected", Boolean.TRUE);
			if(toolTipMap.containsKey(paneTypeValue)) {
				String panlToolTipMessage = toolTipMap.get(paneTypeValue);
				panelFilterJson.addProperty("hasTooltip", Boolean.TRUE);
				panelFilterJson.addProperty("tooltipText", panlToolTipMessage);
			}
			
			panelFiltersOptionsList.add(panelFilterJson);
		}
		paneTypeFilterJson.addProperty("name", "Panel Type");
		paneTypeFilterJson.addProperty("id", "panelType");
		paneTypeFilterJson.addProperty("multiSelect", Boolean.TRUE);
		paneTypeFilterJson.add("filterOptions", panelFiltersOptionsList);
		panelFiltersList.add(paneTypeFilterJson);
	}

	/**
	 * Creates the color filter json.
	 *
	 * @param panelFiltersList         the panel filters list
	 * @param instrumentFilterTypeJson the instrument filter type json
	 * @param instFiltersOptionsList   the inst filters options list
	 * @param colorFilterSet           the color filter set
	 */
	public void createColorFilterJson(JsonArray panelFiltersList, JsonObject instrumentFilterTypeJson,
			JsonArray instFiltersOptionsList, Set<String> colorFilterSet) {
		Iterator<String> colrItem = colorFilterSet.iterator();
		while (colrItem.hasNext()) {
			JsonObject colorJsonObj = new JsonObject();
			String colorValue = colrItem.next();
			colorJsonObj.addProperty("key", colorValue);
			colorJsonObj.addProperty("value", colorValue);
			colorJsonObj.addProperty("selected", Boolean.TRUE);
			colorJsonObj.addProperty("hasTooltip", Boolean.FALSE);
			colorJsonObj.addProperty("tooltipText", StringUtils.EMPTY);
			instFiltersOptionsList.add(colorJsonObj);
		}
		instrumentFilterTypeJson.addProperty("name", "Instrument Configuration");
		instrumentFilterTypeJson.addProperty("id", "instrumentConfig");
		instrumentFilterTypeJson.addProperty("multiSelect", Boolean.TRUE);
		instrumentFilterTypeJson.add("filterOptions", instFiltersOptionsList);
		panelFiltersList.add(instrumentFilterTypeJson);
	}

	/**
	 * Gets the final json.
	 *
	 * @param cellTypeData the cell type data
	 * @param cellType 
	 * @return the final json
	 */
	private String getCellFinalJson(JsonArray cellTypeData, JsonArray cellType) {
		JsonObject cellListJsonObj = constructJsonResponseFromSolrData(cellTypeData,cellType);
		LOG.debug("Response for CellType Formated Data {}", cellListJsonObj);
		return cellListJsonObj.toString();
	}

	/**
	 * Fetch cell types.
	 *
	 * @param subSetArray the sub set array
	 * @param server      the server
	 * @param type        the type
	 * @param reactivity 
	 * @return the json array
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 */
	private JsonArray fetchCellTypes(JsonArray subSetArray, HttpSolrClient server, String type, String reactivity)
			throws SolrServerException, IOException {
		StringBuilder builder = new StringBuilder();
		if (null != subSetArray) {
			for (JsonElement subSetId : subSetArray) {
				String subSetValue = subSetId.getAsString();
				builder.append("\"" + subSetValue + "\"" + ",");
			}
		}

		String subSetString = builder.toString();
		String formatedSubSet = subSetString.substring(0, subSetString.length() - 1);
		String subsetValues = "(" + formatedSubSet.concat(")");
		SolrQuery solrQuery = subCellTypeQuery(subsetValues, type,reactivity);
		LOG.debug("ICM Solr Query {}", solrQuery);
		QueryResponse sitesQueryResponse = server.query(solrQuery);
		SolrDocumentList sitesSolrDocs = sitesQueryResponse.getResults();
		JsonArray results = getResultsJson(sitesSolrDocs);
		LOG.debug("Response for CellType  Data {}", results);
		return results;

	}
	/**
	 * Fetch panel types from solr.
	 *
	 * @param server      the server 
	 * @return the json array   the results
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 */
	private JsonArray fetchPanelTypes(HttpSolrClient server)
			throws SolrServerException, IOException {
		LOG.debug(":: Fetching the Panel Data from Solr ::");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.addFilterQuery("doctype:\""+"panelType\"");
		solrQuery.setQuery("*:*");
		solrQuery.setRows(solrConfig.cellTypeQueryLimit());
		solrQuery.setStart(0);
		LOG.debug("ICM Solr Query {}", solrQuery);
		QueryResponse sitesQueryResponse = server.query(solrQuery);
		SolrDocumentList sitesSolrDocs = sitesQueryResponse.getResults();
		JsonArray results = getResultsJson(sitesSolrDocs);
		LOG.debug("Response for PanelType  Data {}", results);
		return results;
	}

	/**
	 * Construct json response from solr data.
	 *
	 * @param results the results
	 * @param cellType 
	 * @return the json object
	 */
	private JsonObject constructJsonResponseFromSolrData(JsonArray results, JsonArray cellType) {
		JsonObject cellListJsonObj = new JsonObject();
		JsonArray cellSeletionList = new JsonArray();
		JsonArray newList = new JsonArray();

		if (null != results && results.size() > 0 && cellType.size() > 0) {
			for (JsonElement subSetId : cellType) {
				String subSetValue = subSetId.getAsString();
				for (JsonElement cellJsonEle : results) {
					JsonObject cellJsonObject = cellJsonEle.getAsJsonObject();
					String cellId = getJsonProperty(cellJsonObject, CommonConstants.CELL_ID);
					if(subSetValue.equals(cellId)) {
						JsonObject cellDataInfo = new JsonObject();					
						String cellName = getJsonProperty(cellJsonObject, CommonConstants.CELL_TYPE);
						String cellDescription = getJsonProperty(cellJsonObject, CommonConstants.CELL_DESSCRIPTION);
						String cellImg = getJsonProperty(cellJsonObject, CommonConstants.CELL_IMAGE);
						String reactivity = getJsonProperty(cellJsonObject,"reactivity");
						/* Arrays */
						JsonArray markersList = new JsonArray();
						JsonObject intercellular = new JsonObject();
						JsonObject cellSurface = new JsonObject();
						JsonObject secreted = new JsonObject();
						JsonArray subsetCellsArray = cellJsonObject.getAsJsonArray(CommonConstants.SUB_SET_CELLS);
						JsonArray panelArray = cellJsonObject.getAsJsonArray(CommonConstants.RELEVANT_PANELS);
						JsonArray cellSurfaceArray = cellJsonObject.getAsJsonArray(CommonConstants.CELL_SURFACE);
						JsonArray secretedArray = cellJsonObject.getAsJsonArray(CommonConstants.SECRETED);
						JsonArray intracellularTxnArray = cellJsonObject.getAsJsonArray(CommonConstants.INTRACELLULAR_KEY);
						
						cellSurface.addProperty(CommonConstants.CATEGOTY_NAME, CommonConstants.CELL_SURFACE);
						cellSurface.add(CommonConstants.LINKS_LIST,
								!cellSurfaceArray.get(0).getAsString().isEmpty() ? cellSurfaceArray : newList);
						markersList.add(cellSurface);
						
						intercellular.addProperty(CommonConstants.CATEGOTY_NAME, CommonConstants.INTRACELLULAR_VALUE_KEY);
						intercellular.add(CommonConstants.LINKS_LIST,
								!intracellularTxnArray.get(0).getAsString().isEmpty() ? intracellularTxnArray : newList);
						markersList.add(intercellular);

						secreted.addProperty(CommonConstants.CATEGOTY_NAME, CommonConstants.SECRETED_VALUE_KEY);
						secreted.add(CommonConstants.LINKS_LIST, !secretedArray.get(0).getAsString().isEmpty() ? secretedArray : newList);
						markersList.add(secreted);

						cellDataInfo.addProperty(CommonConstants.CELL_ID, cellId);
						cellDataInfo.addProperty(CommonConstants.CELL_NAME, cellName);
						cellDataInfo.addProperty(CommonConstants.CELL_DESSCRIPTION, cellDescription);
						cellDataInfo.addProperty(CommonConstants.CELLIMG, cellImg);
						cellDataInfo.addProperty("reactivity", reactivity);
						cellDataInfo.add(CommonConstants.SUB_SET_CELL_IDS,
								!subsetCellsArray.get(0).getAsString().isEmpty() ? subsetCellsArray : newList);
						cellDataInfo.add(CommonConstants.PANEL_IDS, !panelArray.get(0).getAsString().isEmpty() ? panelArray : newList);
						cellDataInfo.add(CommonConstants.MARKERS_LIST, markersList);

						cellSeletionList.add(cellDataInfo);
						cellListJsonObj.add(CommonConstants.CELL_SELECTION_LIST, cellSeletionList);
					}

				}
			}
			
		}
		return cellListJsonObj;
	}

	/**
	 * Gets the json property.
	 *
	 * @param cellJsonObject the cell json object
	 * @param property       the property
	 * @return the json property
	 */
	public String getJsonProperty(JsonObject cellJsonObject, String property) {
		String propertyValue = StringUtils.EMPTY;
		if (null != cellJsonObject.get(property)) {
			return cellJsonObject.get(property).getAsString().trim();
		} else {
			return propertyValue;
		}
	}

	/**
	 * Gets the solr result json.
	 *
	 * @param server       the server
	 * @param subSolrQuery the sub solr query
	 * @return the solr result json
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 */
	public JsonArray getSolrResultJson(HttpSolrClient server, SolrQuery subSolrQuery)
			throws SolrServerException, IOException {
		QueryResponse subQueryResponse = server.query(subSolrQuery);
		SolrDocumentList subSolrDocs = subQueryResponse.getResults();
		return getResultsJson(subSolrDocs);
	}

	/**
	 * Gets the sub cell ids.
	 *
	 * @param results the results
	 * @return the sub cell ids
	 */
	public String getSubCellIds(JsonArray results) {
		String subsetValues = StringUtils.EMPTY;
		for (JsonElement cellTypeJsonEle : results) {
			JsonObject cellTypeJsonObj = cellTypeJsonEle.getAsJsonObject();
			StringBuilder builder = new StringBuilder();
			if (null != cellTypeJsonObj.get(CommonConstants.SUB_SET_CELLS)) {
				JsonArray subSetArray = cellTypeJsonObj.getAsJsonArray(CommonConstants.SUB_SET_CELLS);
				for (JsonElement subSetId : subSetArray) {
					String subSetValue = subSetId.getAsString();
					builder.append("\"" + subSetValue + "\"" + ",");
				}
				String subSetString = builder.toString();
				String formatedSubSet = subSetString.substring(0, subSetString.length() - 1);
				subsetValues = "(" + formatedSubSet.concat(")");
			}

		}

		return subsetValues;
	}

	/**
	 * Sub cell type query.
	 *
	 * @param subCellType the sub cell type
	 * @param type        the type
	 * @param reactivity 
	 * @return the solr query
	 */
	private SolrQuery subCellTypeQuery(String subCellType, String type, String reactivity) {
		SolrQuery solrQuery = new SolrQuery("q", "*:*");
		if (type.equalsIgnoreCase(CommonConstants.CELL_TYPE)) {
			solrQuery.addFilterQuery("doctype:"+ CommonConstants.CELL_TYPE);
			solrQuery.addFilterQuery("cellId:" + subCellType);
			solrQuery.addFilterQuery("reactivity:" + reactivity);
		} else if (type.equalsIgnoreCase(CommonConstants.PANEL_TYPE)) {
			solrQuery.addFilterQuery("doctype:"+CommonConstants.PANEL_TYPE);
			solrQuery.addFilterQuery("panelName:" + subCellType);
		}
		solrQuery.setRows(solrConfig.cellTypeQueryLimit());
		solrQuery.setStart(0);
		return solrQuery;
	}

	/**
	 * Gets the results json.
	 *
	 * @param sitesSolrDocs the sites solr docs
	 * @return the results json
	 */
	private JsonArray getResultsJson(SolrDocumentList sitesSolrDocs) {
		Iterator<SolrDocument> iterator = sitesSolrDocs.iterator();
		JsonArray results = new JsonArray();
		while (iterator.hasNext()) {
			SolrDocument solrDocument = iterator.next();
			Iterator<Map.Entry<String, Object>> itr = solrDocument.iterator();
			JsonObject facetObject = new JsonObject();
			while (itr.hasNext()) {
				Map.Entry<String, Object> map = itr.next();
				facetObject.add(map.getKey(), new Gson().toJsonTree(map.getValue()));
			}

			results.add(facetObject);
		}

		return results;
	}
   
	public static String HandleRTEAnchorLinkExtrnalization(String html, ExternalizerService externalizerService,ResourceResolver resourceResolver, String urlFormate ) throws IOException {
		Reader reader = new StringReader(html);
		HTMLEditorKit.Parser parser = new ParserDelegator();
		HashMap<String, String> linkMap = new HashMap<String, String>();


		parser.parse(reader, new HTMLEditorKit.ParserCallback() {
			public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
				if (t == HTML.Tag.A) {
					Object link = a.getAttribute(HTML.Attribute.HREF);
					if (link != null) {

						String link_value = String.valueOf(link);
						if(link_value.startsWith("/content/bdb")) {
							link_value = externalizerService.getFormattedUrl(link_value.startsWith("/content")?link_value.replace(".html", ""):link_value, resourceResolver);
						}
						linkMap.put(StringEscapeUtils.escapeHtml4(String.valueOf(link)), link_value);
					}
				}
			}
		}, true);

		reader.close();
		for (String i : linkMap.keySet()) {
			  html = html.replace(i, linkMap.get(i));
			}
		return html;
		
		
		}
}