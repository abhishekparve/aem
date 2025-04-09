package com.bdb.aem.core.services.tools.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.servlets.ConvertExcelToJsonServlet;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

/**
 * Service to index antigen documents to Apache Solr.
 * 
 * @author ronbanerjee
 *
 */
@Component(service = IndexServiceImpl.class)
public class IndexServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceImpl.class);

	@Reference
	private SolrSearchService solrSearchService;

	@Reference
	private CloneComparisonServiceImpl cloneService;
	
	@Reference
	ExternalizerService externalizerService;
	
	@Reference
	BDBSearchEndpointService solrConfig;
	/**
	 * Method to read the data and index to solr.
	 * @param resourceResolver 
	 * @param speciesReactivity 
	 * @param speciesReactivity 
	 * 
	 * @param aL, the antigen list which is obtained by reading the excel sheet.
	 * @see ConvertExcelToJsonServlet
	 */
	public void indexToSolr(List<JsonObject> aL, String country, String type, ResourceResolver resourceResolver, String speciesReactivity) {
		
		List<SolrInputDocument> docs = new ArrayList<>();
		AtomicInteger ordinal = new AtomicInteger(1);
		
		
		
		aL.stream().filter(listData -> listData != null).forEach(listItem -> {

			SolrInputDocument document = constructDocument(listItem, type,resourceResolver,country);
			if (document.size() > 0) {
				document.addField("doctype", type);
				if(type.equalsIgnoreCase("panelType")) {
					
					String panelName = document.get("panelName").getValue().toString();
					
					String asciiPanelName = panelName.replaceAll("[^\\x20-\\x7E]", "");
				    
									
					String panelNameId = asciiPanelName.replaceAll(" ", "");
					panelNameId = panelNameId.toLowerCase();
					
					String marker= document.get("marker").getValue().toString();
					document.addField("uniqueIdentifier", panelName + CommonConstants.SPACE+ marker);
					document.addField("id", panelName + CommonConstants.SPACE+ marker);	
					document.addField("panelId", panelNameId);	
					
					
					
					
				}else {
					document.addField("uniqueIdentifier", generateId(document));
					document.addField("id", generateId(document));	
				}
				if(type.equals("cellType") && document.containsKey("reactivity") && StringUtils.isNotEmpty(speciesReactivity)) {
					 String reactivityValue = document.get("reactivity").getValue().toString();
					 if(reactivityValue.equals(speciesReactivity)) {
						 docs.add(document);
					 }
				}else {
					 docs.add(document);
				}
				
				ordinal.getAndIncrement();
				if (docs.size() == 1000) {
					commitToSolr(docs, country);
					docs.clear();
				}
			}
		});
		if (docs.size() > 0)
			commitToSolr(docs, country);
	}

	/**
	 * Commits documents to Apache SOLR.
	 * 
	 * @param docs,    the {@link SolrInputDocument}
	 * @param country, the country based on which Apache Solr Core is Selected.
	 */
	private void commitToSolr(List<SolrInputDocument> docs, String country) {

		try {
			getSolrClient(country).add(docs);
			getSolrClient(country).commit();
		} catch (SolrServerException e) {
			LOGGER.error("Solr Exception | IOException {}", e);
		} catch (IOException e) {
			LOGGER.error("IOException {}", e);
		}

	}

	/**
	 * Method to construct the Apache Solr Document based on {@code m}.
	 * @param resourceResolver 
	 * @param country 
	 * @param speciesReactivity 
	 * 
	 * @param m, the JSON object
	 * @return {@link SolrInputDocument}
	 */
	private SolrInputDocument constructDocument(JsonObject jsonObj, String type, ResourceResolver resourceResolver, String country) {

		final SolrInputDocument document = new SolrInputDocument();
		getDataMap().get(type).entrySet().forEach(item -> {

			if ((jsonObj.get(item.getValue()) != null) && !(jsonObj.get(item.getValue()) instanceof JsonNull)) {
				List<String> cellularList = new ArrayList<>();
				if(item.getValue().equals("Intracellular/Txn") || item.getValue().equals("Cell Surface") || item.getValue().equals("Secreted") || item.getValue().equals("Relevant Panels")|| item.getValue().equals("Subset Cells")) {
					String intracellular = jsonObj.get(item.getValue()).getAsString();
					com.google.gson.JsonArray myCustomArray = getJsonArrayFromExcelProperty(cellularList,
							intracellular);
					if(myCustomArray.size() >0) {
						document.addField(item.getKey(), format(myCustomArray));
					}else {
						document.addField(item.getKey(), StringUtils.EMPTY);
					}
				}else if(item.getValue().equals("Panel Accordion Description")){
					String panelAccordionDes = jsonObj.get(item.getValue()).getAsString();
					try {
					String urlFormate = getRegionAndLanFromCountryList(country, resourceResolver, solrConfig);
					panelAccordionDes = CommonHelper.HandleRTEAnchorLinkForIcm(panelAccordionDes, externalizerService, resourceResolver,urlFormate);
					document.addField(item.getKey(), panelAccordionDes);
					} catch (IOException e) {
						LOGGER.error("IOException",e);
					}
				}else {
					document.addField(item.getKey(), format(jsonObj.get(item.getValue())));
				}
			}else {
				document.addField(item.getKey(), StringUtils.EMPTY);
			}

		});
		if (handleColorsForAntigen(jsonObj, type).size() > 0) {
			document.addField("formats", format(handleColorsForAntigen(jsonObj, type)));
		}
		return document;

	}

	public com.google.gson.JsonArray getJsonArrayFromExcelProperty(List<String> cellularList, String intracellular) {
		String[] cellularArray = null;
		Gson gson = new GsonBuilder().create();
		if(StringUtils.isNotBlank(intracellular)) {
			cellularArray = intracellular.split(",|;");
			if(null != Arrays.asList(cellularArray) ) {
				cellularList = Arrays.asList(cellularArray);
			}
		}
		
		return gson.toJsonTree(cellularList).getAsJsonArray();
	}
	
	
	/**
	 * Handles the color count.
	 * 
	 * @param m,    the Json Object
	 * @param type, the doc type
	 */
	private JsonArray handleColorsForAntigen(JsonObject jsonObj, String type) {
		JsonArray colorsArray = new JsonArray();
		if (type.equals("antigen")) {
			JsonObject otherJson = jsonObj.has("otherformatsColorMapping")?jsonObj.get("otherformatsColorMapping").getAsJsonObject():null;
			getColorLabels().entrySet().forEach(i ->{
				if(null != otherJson && otherJson.has(i.getKey())) {
					String size = String.valueOf(otherJson.get(i.getKey()).getAsJsonArray().size());
					colorsArray.add(i.getValue().concat(":").concat(size));
				}
				// To be uncommented, incase 0 is to be shown for unavailable laser lines.
				// else {colorsArray.add(i.getValue().concat(":").concat("0"));}
			});
		}
		return colorsArray;
	}

	/**
	 * Map for colors versus display label.
	 * 
	 * @return colors map
	 */
	private Map<String, String> getColorLabels() {

		String[] colors = Optional.ofNullable(cloneService).map(CloneComparisonServiceImpl::getColorMap)
				.map(item -> item.split(CommonConstants.COMMA)).orElse(new String[] {});

		return Stream.of(colors)
				.filter(item -> item.contains(CommonConstants.COLON) && item.split(CommonConstants.COLON).length > 0)
				.map(item -> item.split(CommonConstants.COLON))
				.collect(Collectors.toMap(element -> element[0], element -> element[1]));
	}

	/**
	 * Formats the JSON Element based on the type. Handles type: JSON Array | String
	 * 
	 * @param jsonElement the JSON Element
	 * @return {@link Object}
	 */
	private Object format(JsonElement jsonElement) {
		if (jsonElement.isJsonArray()) {
			List<String> formatList = new ArrayList<>();
			jsonElement.getAsJsonArray().forEach(item -> formatList.add(StringUtils.isNotBlank(item.getAsString())?item.getAsString():StringUtils.EMPTY));
			return formatList;
		} else {
			return jsonElement.isJsonNull() ? null : jsonElement.getAsString().trim();
		}
	}

	/**
	 * Map containing the list of fields to index in antigen doc. TBD: This can be
	 * further enhanced to be read from OSGi Configs.
	 * 
	 * @return {@link Map}
	 */
	private Map<String, Map<String, String>> getDataMap() {
		Map<String, Map<String, String>> map = new HashMap<>();

		Map<String, String> aMap = new HashMap<>();
		aMap.put("specificity", "Specificity");
		aMap.put("alphanumericspecificity", "Alphanumeric Specificity");
		aMap.put("webName", "Web_Name");
		aMap.put("reactivity", "Species_Reactivity");
		aMap.put("entrezgeneId", "Entrezgene_ID");
		aMap.put("aliases", "Alternative_Name");
		aMap.put("geneName", "Gene Name");
		aMap.put("tdsDescription", "TDS_Description");
		aMap.put("otherFormats", "otherFormatsList");
		aMap.put("distribution", "Distribution");
		aMap.put("function", "Function");
		aMap.put("linkCatalog", "LinkCatalog");
		aMap.put("featurePanel", "FeaturePanel");
		aMap.put("publication", "Publication");
		aMap.put("clones", "clones");
		aMap.put("products", "products");

		Map<String, String> cMap = new HashMap<>();
		cMap.put("cloneName", "TdsCloneName");
		cMap.put("aliases", "AlternateName");
		cMap.put("reactivity", "TargetSpecies");
		cMap.put("crossReactivity", "TargetCrossReactivity");
		cMap.put("specificity", "TargetMolecule");
		cMap.put("application", "Application");
		cMap.put("isotype", "Isotype");
		cMap.put("hldaId", "WorkShopNumber");
		cMap.put("image", "image");
		cMap.put("regulatoryStatus", "regulatoryStatus");
		cMap.put("otherFormats", "otherFormats");
		cMap.put("imageFormat", "format");
		
		Map<String, String> cellMap = new HashMap<>();
		cellMap.put("cellType", "Cell Types");
		cellMap.put("reactivity","Reactivity");
		cellMap.put("intracellularTxn", "Intracellular/Txn");
		cellMap.put("cellSurface","Cell Surface");
		cellMap.put("secreted", "Secreted");
		cellMap.put("subCellTypeMarkers","SubCell Type Markers");
		cellMap.put("relevantPanels","Relevant Panels");
		cellMap.put("cellId","Cell ID");
		cellMap.put("subsetCells","Subset Cells");
		cellMap.put("cellImage","Images");
		cellMap.put("scientificTechnicalUrl","View Scientific and Technical URL");
		cellMap.put("cellDescription","Cell Descriptions");
		cellMap.put("cellAliases","Aliases");

		Map<String, String> panelMap = new HashMap<>();
		panelMap.put("panelName", "Panel name");
		panelMap.put("panelType","Panel Type");
		panelMap.put("marker", "Marker");
		panelMap.put("fluoroChrome","Fluorochrome");
		panelMap.put("clone", "Clone");
		panelMap.put("testOrKit","Test /kit");
		panelMap.put("volumeOrTest","Volume /test");
		panelMap.put("catalogNumber","Cat.No.");
		panelMap.put("color","Color");
		panelMap.put("laserLine","Laser Line");
		panelMap.put("panelDescription","Panel Accordion Description");
		panelMap.put("panelToolTip","Tooltip");
		
		map.put("antigen", aMap);
		map.put("clone", cMap);
		map.put("cellType", cellMap);
		map.put("panelType", panelMap);
		
		return map;
	}

	/**
	 * Gets the Apache Solr Client based on {@code country}.
	 * 
	 * @param count ry, the country based on which solr core is called.
	 * @return the {@link SolrClient}
	 */
	private SolrClient getSolrClient(String country) {
		LOGGER.debug("Indexing antigen data for country: {}", country);
		return solrSearchService.solrClient(country.toLowerCase());
	}

	/**
	 * Generates ID.
	 * 
	 * @param d, the {@link SolrInputDocument}
	 * @return ID as a String
	 */
	private String generateId(SolrInputDocument document) {
		String type = document.get("doctype").getValue().toString();

		if (type.equals("antigen")) {
			return type.concat("_").concat(document.get("specificity").getValue().toString()).concat("_")
					.concat(document.get("reactivity").getValue().toString());
		} else if (type.equals("clone")) {
			return type.concat("_").concat(document.get("cloneName").getValue().toString()).concat("_")
					.concat(document.get("reactivity").getValue().toString());
		}else if(type.equals("cellType")) {
			return document.get("cellType").getValue().toString().concat("_").concat(document.get("reactivity").getValue().toString());
		}
		return StringUtils.EMPTY;

	}
	
	private String getRegionAndLanFromCountryList(String country,ResourceResolver resourceResolver, BDBSearchEndpointService solrConfig) {
		String regionPath = StringUtils.EMPTY;
		if(null != resourceResolver.getResource(solrConfig.countryToRegionAndLanguge())) {
			Resource countryRegionsListRes= resourceResolver.getResource(solrConfig.countryToRegionAndLanguge() + CommonConstants.JCR_CONTENT +CommonConstants.SINGLE_SLASH +CommonConstants.LIST);
			if(null != countryRegionsListRes) {
				Iterator<Resource> listItemRes= countryRegionsListRes.listChildren();
				while (listItemRes.hasNext()) {
					Resource nextRes = listItemRes.next();
					ValueMap itemMap = nextRes.adaptTo(ValueMap.class);
					String listCountryValue = itemMap.get(CommonConstants.VALUE, String.class);
					if(StringUtils.isNotBlank(listCountryValue) && listCountryValue.equalsIgnoreCase(country)) {
						regionPath = itemMap.get(JcrConstants.JCR_TITLE, String.class);
					}
				}
			}	
		}
		return regionPath;
	}
}
