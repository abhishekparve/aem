package com.bdb.aem.core.workflows;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
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

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.services.tools.impl.IndexServiceImpl;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SVUtils;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.bdb.aem.core.util.WorkflowConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.solr.common.SolrInputDocument;

/**
 * The Class BDBToolsWorkflow.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
		"process.label = Antigen Clone Comparison Workflow" })
public class BDBToolsWorkflow implements WorkflowProcess {

	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BDBToolsWorkflow.class);

	@Reference
	private transient ResourceResolverFactory resourceResolverFactory;

	@Reference
	private transient SolrSearchService solrSearchService;

	@Reference
	private transient IndexServiceImpl indexService;

	/** The bdb api endpoint service. */
	@Reference
	private transient BDBApiEndpointService bdbApiEndpointService;
	
	public static final String REGULATORY_STATUS = "regulatoryStatus_t" ;
	
	public static final String DYE_NAME= "dyeName_t" ;
	
	public static final String SPECIFICITY = "specificity_t" ;
	
	public static final String TDS_CLONE_NAME = "tdsCloneName_t" ;
	
	public static final String TARGET_SPECIES = "TargetSpecies" ;
	
	public static final String MATERIAL_NUMBER = "MaterialNumber" ;
	
	public static final String MATERIAL_T = "materialNumber_t" ;
	
	public static final String FORMAT = "format" ;
	
	public static final String THUMBNAIL = "Thumbnail" ;
	
	public static final String IMAGE = "image" ;
	
	@Reference
	BDBSearchEndpointService solrConfig;

	/** SOLR_DATE_FORMAT. */
	public static final String SOLR_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	JsonArray allFormats = new JsonArray();
	JsonArray regStatusFormats = new JsonArray();
	Boolean cloneIndexed = false;
	
	/**
	 * Execute.
	 *
	 * @param workItem        the work item
	 * @param workflowSession the workflow session
	 * @param args            the args
	 * @throws WorkflowException the workflow exception
	 */
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
		LOGGER.debug("Entry Execute method of ConvertExcelToJsonWorkflow");
		ResourceResolver resourceResolver = null;
		try {
			String participantArgs = args.get(WorkflowConstants.PROCESS_ARGS, String.class);
			resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
			String damFilePath = workItem.getWorkflowData().getPayload().toString();
			String[] argsFromWorkflow = participantArgs.split("@");

			LOGGER.debug("length of argument array {}", argsFromWorkflow.length);
			String[] speciesReactivityArray = argsFromWorkflow[0].split("#");
			String type = argsFromWorkflow[1];
			String[] country = argsFromWorkflow.length == 3 ? argsFromWorkflow[2].split("#") : null;
			if (null == country) {
				ArrayList<String> countriesList = (ArrayList<String>) CommonHelper.getAllCountries(resourceResolver,bdbApiEndpointService);
				country = countriesList.toArray(new String[0]);
			}			
			startIndexingOnType(resourceResolver, damFilePath, speciesReactivityArray, type, country);

		} 
		catch (IOException ex) {
			LOGGER.error("IOException occurred during processing : {0}", ex);
		}catch (SolrServerException e) {

			LOGGER.error("Exception occurred during processing : {0}", e);
		}catch (LoginException e) {
			LOGGER.error("Login Exception", e);

		}finally {
			CommonHelper.closeResourceResolver(resourceResolver);
		}
		LOGGER.debug("Exit Execute method of ConvertExcelToJsonWorkflow");
	}

	/**
	 * Start indexing on type.
	 *
	 * @param resourceResolver the resource resolver
	 * @param damFilePath the dam file path
	 * @param speciesReactivityArray the species reactivity array
	 * @param type the type
	 * @param country the country
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void startIndexingOnType(ResourceResolver resourceResolver, String damFilePath,
			String[] speciesReactivityArray, String type, String[] country) throws SolrServerException, IOException {
		if (speciesReactivityArray.length != 0 && (!StringUtils.equals("null", speciesReactivityArray[0]))) {
			for (String speciesReactivity : speciesReactivityArray) {
				for (String countryValue : country) {
						startCreatingObjectAndIndexing(damFilePath, speciesReactivity, countryValue, type,
							resourceResolver, 0, solrConfig.cellTypeQueryLimit());
				}
			}
		} else {
			for (String countryValue : country) {
				startCreatingObjectAndIndexing(damFilePath, null, countryValue, type,
						resourceResolver, 0, solrConfig.cellTypeQueryLimit());
			}
		}
	}

	private void startCreatingObjectAndIndexing(String damFilePath, String speciesReactivity, String countryValue, String type, ResourceResolver resourceResolver, int start, int row) throws SolrServerException, IOException{
		if (StringUtils.isNotEmpty(damFilePath) && StringUtils.isNotEmpty(countryValue) && StringUtils.isNotEmpty(type)) {
			JsonArray solrResponse = null;
			if((!type.equalsIgnoreCase("cellType") && !type.equalsIgnoreCase("panelType")) && StringUtils.isNotEmpty(speciesReactivity)) {
				solrResponse = fetchSolrResponse(speciesReactivity, countryValue, start, row);
			}

			Resource res = resourceResolver.getResource(damFilePath);
			Asset asset = res.adaptTo(Asset.class);
			Rendition rendition = asset.getOriginal();
			InputStream inputStream = rendition.adaptTo(InputStream.class);
			JsonObject excelObject = creteJSONAndTextFileFromExcel(inputStream, asset.getMimeType());
			List<JsonObject> listToBeIndexed = new ArrayList<>();
			if (type.equalsIgnoreCase("antigen")) {
				 listToBeIndexed = listOfObjectForIndexingAntigen(solrResponse, excelObject,
						speciesReactivity);
				
				LOGGER.info("Antigen data successfully indexed to {} ", countryValue);
			} else if (type.equalsIgnoreCase("clone")) {
				 listToBeIndexed = listOfObjectForIndexingClone(solrResponse, excelObject,
						speciesReactivity);
				LOGGER.info("Clone data successfully indexed to {}", countryValue);
			}else if (type.equalsIgnoreCase("cellType")) {
				AtomicInteger ordinal = new AtomicInteger(1);
				while (null != excelObject.getAsJsonObject("Row " + ordinal.get())) {
					JsonObject row1 = excelObject.getAsJsonObject("Row " + ordinal.get());
					listToBeIndexed.add(row1);
					ordinal.getAndIncrement();
				}
				LOGGER.info("ICM Tools cellType Json List {}",listToBeIndexed);
			}else if (type.equalsIgnoreCase("panelType")) {
				AtomicInteger ordinal = new AtomicInteger(1);
				JsonArray panelJsonArray = new JsonArray();
				while (null != excelObject.getAsJsonObject("Row " + ordinal.get())) {
					JsonObject row1 = excelObject.getAsJsonObject("Row " + ordinal.get());
					panelJsonArray.add(row1);
					ordinal.getAndIncrement();
				}
				
					JsonArray sortedPanelData = SVUtils.sortJsonObject(panelJsonArray, "Panel name");
				
					// convert JsonArray to List of JsonObject
					for (int i = 0; i < sortedPanelData.size(); i++)
						listToBeIndexed.add((JsonObject) sortedPanelData.get(i));
					
					//convert the List to Map with unique panelname
					//Map <String, List<HashSet<String>>> panelPageMap = getPanelUniqueMap(listToBeIndexed);
					//indexPanelUrl(panelPageMap, countryValue);
					
				
				
				LOGGER.info("ICM Tools PanelType Json List {}",listToBeIndexed);
			}
			
			indexService.indexToSolr(listToBeIndexed, countryValue, type, resourceResolver,speciesReactivity);

		}
	}
	
	private void indexPanelUrl(Map <String, List<HashSet<String>>> panelPageMap,String country) {
		
		List<SolrInputDocument> docs = new ArrayList<>();
		try {
			String baseUrl = getBaseUrl(country);
			if(baseUrl != null) {
			 // Using entrySet() with a for-each loop
	        for (Map.Entry<String, List<HashSet<String>>> entry : panelPageMap.entrySet()) {
	            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
	            HashSet<String> panelNameSet = entry.getValue().get(0);
	            HashSet<String> panelPropertiesSet = entry.getValue().get(1);
	            String panelName = "";

	            StringBuffer propertiesSb = new StringBuffer("");
				
	            
	            Iterator<String> panelNameIt = panelNameSet.iterator();
	            while (panelNameIt.hasNext()) {
	            	panelName = panelNameIt.next();
	               
	            }
	            Iterator<String> panelPropertiesIt = panelPropertiesSet.iterator();
	            while (panelPropertiesIt.hasNext()) {
	            	propertiesSb.append(panelPropertiesIt.next());
	            	propertiesSb.append(" ");
	               
	            }
	            
	            String propertiesString = propertiesSb.toString();
				String panelId = entry.getKey();
				String panelUrl = baseUrl+"?panel=" + panelId;
	        
	        SolrInputDocument doc = new SolrInputDocument();
			doc.addField(CommonConstants.UNIQUE_IDENTIFIER, panelUrl);
			doc.addField(SolrSearchConstants.SOLRDOC_FIELD_ID, panelUrl);
			doc.addField(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_T,
					SolrSearchConstants.CONTENT_PAGE);
			doc.addField(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_FACET_UNDERSCORE_S,
					SolrSearchConstants.INFORMATION);

			doc.addField(CommonConstants.NAME.concat(SolrSearchConstants.UNDERSCORE_T), panelName);
			doc.addField(SolrSearchConstants.PAGE_TITLE.concat(SolrSearchConstants.UNDERSCORE_T), panelName);
			

			doc.addField("validityStartDate".concat(SolrSearchConstants.UNDERSCORE_DT), "1900-12-08T14:13:15Z");
			
			doc.addField(CommonConstants.BDB_CONTENT_KEYWORDS.concat(SolrSearchConstants.UNDERSCORE_T),
					"virtualPanelPage");
			
			
			doc.addField(SolrSearchConstants.SOLRDOC_FIELD_BODY.concat(SolrSearchConstants.UNDERSCORE_T), panelName +" "+propertiesString);

			doc.addField("isAvailable".concat(SolrSearchConstants.UNDERSCORE_T), "true");

			doc.addField(SolrSearchConstants.COUNTRY.concat(SolrSearchConstants.UNDERSCORE_T),
					country.toLowerCase());

			doc.addField(SolrSearchConstants.LANGUAGE.concat(SolrSearchConstants.UNDERSCORE_T), "en");
			//doc.addField("panelId", "panel-id");	
			docs.add(doc);
	        
	        }
	        if (docs.size() > 0) {
			
			  HttpSolrClient server = solrSearchService.solrClient(country);
			  // Delete by query
			 server.deleteByQuery("bdbContentKeywords_t:virtualPanelPage");
			  
			 server.add(docs); 
			 server.commit(); 
			 server.close();
			 
	        }
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred while indexing panel page Urls", e);
		}
	}
	
	private String getBaseUrl(String country) {
		String list = "/etc/acs-commons/lists/bdb/panelPageBaseUrl/jcr:content/list";
		ResourceResolver resourceResolver = null;
		 try {
			 resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
		} catch (LoginException e) {
			e.printStackTrace();
		}
		Resource listResource = resourceResolver.getResource(list);
		
		if(listResource != null) {
			if(listResource.hasChildren()) {
				for(Resource child : listResource.getChildren()) {
					
					ValueMap vm = child.adaptTo(ValueMap.class);
					String value = vm.get("value", StringUtils.EMPTY);
					String[]  splitedValue = value.split(":");
					if(splitedValue[0].equals(country)) {
						return splitedValue[1];
					}
					
				}
			}
		}
		return null;
	}
	
	private Map <String, List<HashSet<String>>> getPanelUniqueMap(List<JsonObject> sortedlistToBeIndexed) {
		
		//convert the JsonArray to Map with unique panelname
		Map <String, List<HashSet<String>>> panelPageMap = new HashMap<>();
		
		try {
		HashSet<String> propertySet = new HashSet<String>();
		HashSet<String> panelNameSet = new HashSet<String>();
		
		String initPanelId ="";
		for (int i = 0; i < sortedlistToBeIndexed.size(); i++) {
			String panelNameOrignal = sortedlistToBeIndexed.get(i).get("Panel name").getAsString();
			String marker = sortedlistToBeIndexed.get(i).get("Marker").getAsString();
			String clone = sortedlistToBeIndexed.get(i).get("Clone").getAsString();
			String fluorochrome = sortedlistToBeIndexed.get(i).get("Fluorochrome").getAsString();
			
			//String panelId = URLEncoder.encode(panelNameOrignal, "UTF-8");
			
			String panelId = panelNameOrignal.replaceAll(" ", "");
			panelId = panelId.toLowerCase();
			
			
			if(i == 0 || initPanelId.equals(panelId) ) {
				propertySet.add(marker);
				propertySet.add(clone);
				propertySet.add(fluorochrome);
				panelNameSet.add(panelNameOrignal);
				initPanelId = panelId;
				
				if(i == sortedlistToBeIndexed.size()-1) {
					List<HashSet<String>> finalList = new ArrayList<HashSet<String>>();
					
					finalList.add(panelNameSet);
					finalList.add(propertySet);
					
					panelPageMap.put(initPanelId, finalList);
				}
			}else{
				
				List<HashSet<String>> panelPropertyList = new ArrayList<HashSet<String>>();
				
				panelPropertyList.add(panelNameSet);
				panelPropertyList.add(propertySet);
				
				panelPageMap.put(initPanelId, panelPropertyList);
				
				propertySet = new HashSet<String>();
				panelNameSet = new HashSet<String>();
				
				propertySet.add(marker);
				propertySet.add(clone);
				propertySet.add(fluorochrome);
				panelNameSet.add(panelNameOrignal);
				initPanelId = panelId;
				if(i == sortedlistToBeIndexed.size()-1) {
					List<HashSet<String>> finalList = new ArrayList<HashSet<String>>();
					
					finalList.add(panelNameSet);
					finalList.add(propertySet);
					
					panelPageMap.put(initPanelId, finalList);
				}
			}
			
		}
		}catch (Exception e) {
			LOGGER.error("UnsupportedEncodingException ", e);
		}
		return panelPageMap;
	}
	
	/**
	 * Provides list of objects to be indexed as doctype:clone based on the excel
	 * data, the {@code excelobject} and the solr response, the {@code solrResponse}
	 * 
	 * @param solrResponse,     the Solr Response
	 * @param excelObject,      the Excel Sheet Data as a {@link JsonArray}
	 * @param speciesReactivity the Species Reactivity
	 * @return the List of JsonObject which matches the critrea
	 */
	private List<JsonObject> listOfObjectForIndexingClone(JsonArray solrResponse, JsonObject excelObject,
			String speciesReactivity) {

		List<JsonObject> arr = new ArrayList<>();
		excelObject.entrySet().forEach(e -> {
			if (checkNotBlank(e.getValue())) {
				JsonObject o = e.getValue().getAsJsonObject();
				addIfEligibleClone(o, solrResponse, speciesReactivity, arr);

			}
		});
		return arr;
	}

	/**
	 * Does eligibility check based on below criterea.
	 * <ul>
	 * <li>Species Reactivity should be contained in Excel Data - TargetSpecies</li>
	 * <li>Solr Response - specificity_t should match Excel Data - TargetMolecule
	 * </li>
	 * <li>Solr Response - tdsCloneName_t should match Excel Data - TdsCloneName
	 * </li>
	 * </ul>
	 * <p>
	 * Additionally constructs: 1. format (Solr Response-dyeName_t based on Excel
	 * Sheet -Material Numaber) 2. ThumbnailImage (Excel Sheet -ThumbnailImage) 3.
	 * AvailableFormats (Solr Response - All matching items)
	 *
	 * @param e
	 * @param solrResponse
	 * @param sR
	 * @param arr
	 */
	private void addIfEligibleClone(JsonObject e, JsonArray solrResponse, String sR, List<JsonObject> arr) {
		cloneIndexed = false;
		List<JsonObject> solrMatches = new ArrayList<>();
		solrResponse.forEach(sRes -> {
			JsonObject s = sRes.getAsJsonObject();

			if (getAsString(e.get(TARGET_SPECIES)).toLowerCase().contains(sR.toLowerCase())
					&& getAsString(e.get("TargetMolecule")).equals(getAsString(s.get(SPECIFICITY)))
					&& getAsString(e.get("TdsCloneName")).equals(getAsString(s.get(TDS_CLONE_NAME)))) {
				solrMatches.add(s);
			}
		});
		if (!solrMatches.isEmpty()) {
			e.addProperty(TARGET_SPECIES, sR);
			JsonArray rStatus = new JsonArray();
			JsonArray aFormats = new JsonArray();
			JsonArray regulatoryStatusFormats = new JsonArray();
			solrMatches.forEach(match -> {
				addIfNotExists(match.get(REGULATORY_STATUS), rStatus);
				addIfNotExists(match.get(DYE_NAME), aFormats);
				if(match.has(REGULATORY_STATUS) ? (match.get(REGULATORY_STATUS).getAsString().equalsIgnoreCase("RUO")) : false)
					addIfNotExists(match.get(DYE_NAME), regulatoryStatusFormats);
				
			});
			String r = StringUtils.EMPTY;
			AtomicInteger ordinal = new AtomicInteger(0);
			int count = 0;
			r = getRuoList(rStatus, r, ordinal, count);
			e.addProperty("regulatoryStatus", r);
			e.add("otherFormats", aFormats);
			arr.add(e);
			allFormats = aFormats;
			regStatusFormats = regulatoryStatusFormats;
			
			getFormatAndThumbnail(e, solrMatches);
		}
		
		//if(!solrMatches.isEmpty()) {
			
		//}
		
		getFormatAndThumbnailForClone(e, solrResponse, sR);
		

	}
	
	/*
	 * Get RUO list with ; separator
	 */
	private String getRuoList(JsonArray rStatus, String r, AtomicInteger ordinal, int count) {
		for(JsonElement st: rStatus) {
			count++;
			if(!getAsString(st).equals(StringUtils.EMPTY)) {
				r = r.concat(getAsString(st));
				if(count != rStatus.size())
					r = r.concat("; ");
			}
			if((ordinal.get() != rStatus.size()-1)) {
				r = r.concat(" ");
			}
			ordinal.getAndIncrement();
		}
		return r;
	}
	
	/*
	 * get format for clone based on regulatory status
	 * get thumbnail for clone based on regulatory status
	 */
	private void getFormatAndThumbnail(JsonObject e, List<JsonObject> solrMatches) {
		for(JsonObject ruo : solrMatches) {
			if (checkNotBlank(ruo.get(REGULATORY_STATUS)) && ruo.get(REGULATORY_STATUS).getAsString().equalsIgnoreCase("RUO")) {
					handleFormatForClone(ruo, e);
					handleThumbnailForClone(ruo, e);
					cloneIndexed = true;
			}
			
		}
	}
	
	/*
	 * get format for clone based on clone indexed status
	 * get thumbnail for clone based on clone indexed status
	 */
	private void getFormatAndThumbnailForClone(JsonObject e, JsonArray solrResponse, String sR) {
		if(cloneIndexed.equals(Boolean.FALSE)) {
			solrResponse.forEach(sRes -> {
				JsonObject s = sRes.getAsJsonObject();
				if (getAsString(e.get(TARGET_SPECIES)).toLowerCase().contains(sR.toLowerCase())
						&& getAsString(e.get("TargetMolecule")).equals(getAsString(s.get(SPECIFICITY)))
						&& getAsString(e.get("TdsCloneName")).equals(getAsString(s.get(TDS_CLONE_NAME)))) {
						handleFormatForClone(s, e);
						handleThumbnailForClone(s, e);
				}
			});
		}
	}

	/**
	 * Finds out the format value of a clone based on Excel Sheet -MaterialNumber.
	 * 
	 * @param s, the {@link JsonObject}, solr data
	 * @param e, the {@link JsonArray}, excel data
	 */
	private void handleFormatForClone(JsonObject s, JsonObject e) {
		Optional.ofNullable(e).ifPresent(r -> {
			if ((checkNotBlank(e.get(MATERIAL_NUMBER)) ? (!e.get(MATERIAL_NUMBER).getAsString().isEmpty()) : false) && !checkNotBlank(e.get("format"))) {
				if (getAsString(e.get(MATERIAL_NUMBER)).equals(getAsString(s.get(MATERIAL_T)))) {
					e.add(FORMAT, s.get(DYE_NAME));
				}
			} else if (!checkNotBlank(e.get(FORMAT)) && ((checkNotBlank(s.get(REGULATORY_STATUS))) ?  !(s.get(REGULATORY_STATUS).getAsString().equalsIgnoreCase("RUO")) : false)) {
				getSortedFormats(allFormats, s, e);
			} else if(s.get(REGULATORY_STATUS).getAsString().equalsIgnoreCase("RUO")) {
				getSortedFormats(regStatusFormats, s, e);
			}
		});
	}

	/**
	 * Finds out the format value of a clone based on Formats' priority list.
	 * 
	 * @param s, the {@link JsonObject}, solr data
	 * @param e, the {@link JsonArray}, excel data
	 */
	private void getSortedFormats(JsonArray formatsArray, JsonObject s, JsonObject e) {
		boolean flag = false;
		JsonArray formats = new JsonArray();
		formats.add("BV421");
		formats.add("Alexa Fluor™ 647");
		formats.add("PE");
		formats.add("Purified");
		if (formatsArray.size() >0) {
			for (JsonElement je1 : formats) {
				for (JsonElement je2 : formatsArray) {
					if(je1.equals(je2)) {
						e.add("format", je2);
						flag = true;
						break;
					}
				}
				if(flag) break;
			}
			if (!checkNotBlank(e.get(FORMAT))){
				e.add(FORMAT, s.get("dyeName_t"));
			}
			}
	}
	
	/**
	 * Finds out the thumbnail image of a clone based on Excel Sheet -Thumbnail.
	 * <p>
	 * Additionally fallbacks to first match from Solr
	 * </p>
	 * 
	 * @param s, the {@link JsonObject}, the Solr data
	 * @param e, the {@link JsonObject}, the execel data
	 */
	private void handleThumbnailForClone(JsonObject s, JsonObject e) {
		Optional.ofNullable(e).ifPresent(r -> {
			if ((checkNotBlank(e.get(THUMBNAIL)) ? (!e.get(THUMBNAIL).getAsString().isEmpty()) : false) && !checkNotBlank(e.get(IMAGE))) {
				e.add(IMAGE, e.get(THUMBNAIL));

			} else if (!checkNotBlank(e.get(IMAGE))) {
				if (checkNotBlank(e.get(FORMAT))) {
					if (checkNotBlank(s.get("dyeName_t"))) {
						if(e.get(FORMAT).getAsString().equalsIgnoreCase(s.get("dyeName_t").getAsString())) {
							e.add(IMAGE, s.get("thumbnailImage_t"));
						}
					}
				}
			}
		});
	}

	/**
	 * Utility method to add unique items to the array {@code a}
	 * 
	 * @param e, the {@link JsonElement}
	 * @param a, the {@link JsonArray}
	 */
	private void addIfNotExists(JsonElement e, JsonArray a) {
		if (!a.contains(e) && !getAsString(e).equals(StringUtils.EMPTY)) {
			a.add(getAsString(e));
		}

	}

	/**
	 * Checks if {@code jE}, the {@link JsonElement} is not null.
	 * <p>
	 * Current class specific Utility method
	 * </p>
	 * 
	 * @param jE, the {@link JsonElement}
	 * @return {@link Boolean}
	 */
	private boolean checkNotBlank(JsonElement jE) {
		return Optional.ofNullable(jE).map(j -> j != null && !j.isJsonNull()).orElse(false);
	}

	/**
	 * Gets the {@code jE}, the {@link JsonElement} as a String.
	 * <p>
	 * Current class specific Utility Method
	 * </p>
	 * 
	 * @param jE, the {@link JsonElement}
	 * @return {@link String}
	 */
	private String getAsString(JsonElement jE) {
		return Optional.ofNullable(jE)
				.map(j -> checkNotBlank(j) && j.getAsString() != null ? j.getAsString() : StringUtils.EMPTY)
				.orElse(StringUtils.EMPTY);
	}

	private List<JsonObject> listOfObjectForIndexingAntigen(JsonArray solrResponse, JsonObject excelObject,
			String speciesReactivity) {
		AtomicInteger ordinal = new AtomicInteger(1);
		List<JsonObject> indexItemList = new ArrayList<JsonObject>();
		while (null != excelObject.getAsJsonObject("Row " + ordinal.get())) {
			JsonObject row = excelObject.getAsJsonObject("Row " + ordinal.get());
			if (null != row.get("Species_Reactivity") && null != row.get("Specificity") && row.get("Species_Reactivity")
					.getAsString().toLowerCase().contains(speciesReactivity.toLowerCase())) {
				row.remove("Species_Reactivity");
				row.addProperty("Species_Reactivity", speciesReactivity);
				JsonArray otherFormatsList = new JsonArray();
				JsonObject colorMappingObject = new JsonObject();
				JsonArray clones = new JsonArray();
				JsonArray products = new JsonArray();
				for (int i = 0; i < solrResponse.size(); i++) {
					JsonObject response = solrResponse.get(i).getAsJsonObject();
					if (null != response.get("tdsCloneName_t") && null != response.get("specificity_t") && response.get("specificity_t").getAsString()
							.equals(row.get("Specificity").getAsString())) {
						addData(response, row, clones, "tdsCloneName_t");
						addData(response, row, products, MATERIAL_T);
						if (null != response.get("dyeName_t") && !response.get("dyeName_t").isJsonNull()) {
							if (!otherFormatsList.contains(response.get("dyeName_t"))) {
								otherFormatsList.add(response.get("dyeName_t").getAsString());
							}
							String color = response.get("laserColor_t").getAsString();
			
							if (StringUtils.isNotEmpty(color) && colorMappingObject.has(color)) {
								JsonArray colorArray = colorMappingObject.getAsJsonArray(color);
								if (!colorArray.contains(response.get("dyeName_t"))) {
									colorArray.add(response.get("dyeName_t").getAsString());
									colorMappingObject.remove(color);
									colorMappingObject.add(color, colorArray);
								}
							} else if (StringUtils.isNotEmpty(color) && !colorMappingObject.has(color)) {
								JsonArray colorArray = new JsonArray();
								colorArray.add(response.get("dyeName_t").getAsString());
								colorMappingObject.add(color, colorArray);
							}else if(response.get("dyeName_t")!=null && response.get("dyeName_t").getAsString()!=null && response.get("dyeName_t").getAsString().equals("Antibody-Oligo")) {
								if (colorMappingObject.has("Antibody-Oligo")) {
									JsonArray colorArray = colorMappingObject.getAsJsonArray("Antibody-Oligo");
									if (!colorArray.contains(response.get("dyeName_t"))) {
										colorArray.add(response.get("dyeName_t").getAsString());
										colorMappingObject.remove("Antibody-Oligo");
										colorMappingObject.add("Antibody-Oligo", colorArray);
									}
								} else {
									JsonArray colorArray = new JsonArray();
									colorArray.add(response.get("dyeName_t").getAsString());
									colorMappingObject.add("Antibody-Oligo", colorArray);
								}
							
							} else if (StringUtils.isEmpty(color)) {
								if (colorMappingObject.has("others")) {
									JsonArray colorArray = colorMappingObject.getAsJsonArray("others");
									if (!colorArray.contains(response.get("dyeName_t"))) {
										colorArray.add(response.get("dyeName_t").getAsString());
										colorMappingObject.remove("others");
										colorMappingObject.add("others", colorArray);
									}
								} else {
									JsonArray colorArray = new JsonArray();
									colorArray.add(response.get("dyeName_t").getAsString());
									colorMappingObject.add("others", colorArray);
								}
							} 
						}
					}
				}
				row.add("otherFormatsList", otherFormatsList);
				row.add("otherformatsColorMapping", colorMappingObject);
				row.add("clones", clones);
				row.add("products", products);
				indexItemList.add(row);

			}
			ordinal.getAndIncrement();
		}
		return indexItemList;
	}
	
	/**
	 * Adding additional as per the arguments, {@code arr}, {@code key}.
	 * 
	 * @param response, the {@link JsonObject} response
	 * @param row,      the {@link JsonObject} row
	 * @param arr,      the {@link JsonArray} arr
	 * @param key,      the {@link String} key
	 */
	private void addData(JsonObject response, JsonObject row, JsonArray arr, String key) {
		Optional.ofNullable(response).filter(r -> arr != null && !arr.contains(r.get(key))).ifPresent(r -> {
			arr.add(r.get(key).getAsString());
		});

	}

	/**
	 * This method takes the input and do a solr query on particular country core
	 * and return result.
	 * 
	 * @param speciesReactivity
	 * @param country
	 * @param start
	 * @param row
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	private JsonArray fetchSolrResponse(String speciesReactivity, String country, int start, int row)
			throws SolrServerException, IOException {
		HttpSolrClient server = solrSearchService.solrClient(country);
		// Using direct URL for Local Testing
		// HttpSolrClient server = new
		// HttpSolrClient.Builder("https://stgsearch.bdbiosciences.com/solr/bdbio-us").build();
		SolrQuery query = new SolrQuery();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(SOLR_DATE_FORMAT);
		LocalDateTime now = LocalDateTime.now();
		String[] filterQuery = new String[3];
		filterQuery[filterQuery.length - 3] = "docType_t:product";
		filterQuery[filterQuery.length - 2] = "isAvailable_t:true";
		filterQuery[filterQuery.length - 1] = "validityStartDate_dt:[* TO ".concat(dtf.format(now)).concat("]");
		query.setQuery("speciesReactivity_facet_ss:" + "\""+speciesReactivity+"\"");
		query.addFilterQuery(filterQuery);
		query.setStart(start);
		query.setRows(row);
		query.setFields("speciesReactivity_facet_ss", "dyeName_t", "specificity_t", "tdsCloneName_t",
				"thumbnailImage_t", MATERIAL_T, "laserColor_t", "regulatoryStatus_t");
		QueryResponse sitesQueryResponse = server.query(query);
		SolrDocumentList sitesSolrDocs = sitesQueryResponse.getResults();
		Iterator<SolrDocument> iterator = sitesSolrDocs.iterator();
		JsonArray results = new JsonArray();
		while (iterator.hasNext()) {
			SolrDocument solrDocument = iterator.next();
			Iterator<Entry<String, Object>> itr = solrDocument.iterator();
			JsonObject facetobject = new JsonObject();

			while (itr.hasNext()) {
				Entry<String, Object> map = itr.next();
				facetobject.add(map.getKey(), new Gson().toJsonTree(map.getValue()));
			}

			results.add(facetobject);
		}
		return results;
	}

	/*
	 * Read data from an excel file sheet data to a json object
	 */
	private JsonObject creteJSONAndTextFileFromExcel(InputStream inputStream, String mimeType) {
		JsonObject tableObject = new JsonObject();
		try {
			Workbook excelWorkBook = null;
			if (mimeType.contains("application/vnd.ms-excel")) {
				excelWorkBook = new HSSFWorkbook(inputStream);
			} else {
				excelWorkBook = new XSSFWorkbook(inputStream);
			}

			// Get all excel sheet count.
			int totalSheetNumber = excelWorkBook.getNumberOfSheets();

			// Loop in all excel sheet.
			for (int i = 0; i < totalSheetNumber; i++) {
				// Get current sheet.
				Sheet sheet = excelWorkBook.getSheetAt(i);

				// Get sheet name.
				String sheetName = sheet.getSheetName();

				if (sheetName != null && sheetName.length() > 0) {
					// Get current sheet data in a list table.
					List<List<String>> sheetDataTable = getSheetDataList(sheet);

					// Generate JSON format of above sheet data and write to a JSON file.
					tableObject = getJsonObjectFromList(sheetDataTable);

				}
			}
			// Close excel work book object.
			excelWorkBook.close();

		} catch (IOException e) {
			LOGGER.error("IOException", e);

		}
		return tableObject;
	}

	/*
	 * Return sheet data in a two dimensional list. Each element in the outer list
	 * is represent a row, each element in the inner list represent a column. The
	 * first row is the column name row.
	 */
	private static List<List<String>> getSheetDataList(Sheet sheet) {
		List<List<String>> ret = new ArrayList<List<String>>();

		// Get the first and last sheet row number.
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();

		if (lastRowNum > 0) {
			// Loop in sheet rows.
			int lastCellNum = 0; /* Last column will always be equal to first row column */
			int firstCellNum = 0;
			for (int i = firstRowNum; i < lastRowNum + 1; i++) {
				// Get current row object.
				Row row = sheet.getRow(i);
				if (i == 0) {
					lastCellNum = row.getLastCellNum();
					firstCellNum = row.getFirstCellNum();
				}
				// Get first and last cell number.

				// int lastCellNum = row.getLastCellNum();

				// Create a String list to save column data in a row.
				List<String> rowDataList = new ArrayList<String>();

				// Loop in the row cells.
				for (int j = firstCellNum; j < lastCellNum; j++) {
					// Get current cell.
					Cell cell = row.getCell(j);
					if (null != cell) {
						// Get cell type.
						CellType cellType = cell.getCellType();

						if (cellType == CellType.NUMERIC) {
							int numberValue = (int) cell.getNumericCellValue();
							
							// BigDecimal is used to avoid double value is counted use Scientific counting
							// method.
							// For example the original double variable value is 12345678, but jdk
							// translated the value to 1.2345678E7.
							String stringCellValue = BigDecimal.valueOf(numberValue).toPlainString();

							rowDataList.add(stringCellValue);

						} else if (cellType == CellType.STRING) {
							String cellValue = cell.getStringCellValue();
							rowDataList.add(cellValue);
						} else if (cellType == CellType.BOOLEAN) {
							boolean numberValue = cell.getBooleanCellValue();

							String stringCellValue = String.valueOf(numberValue);

							rowDataList.add(stringCellValue);

						} else if (cellType == CellType.BLANK) {
							rowDataList.add("");
						}
					} else
						rowDataList.add(null);
				}

				// Add current row data list in the return list.
				ret.add(rowDataList);
			}
		}
		return ret;
	}

	/* Return a JSON Object from the string list. */
	private static JsonObject getJsonObjectFromList(List<List<String>> dataTable) {
		JsonObject tableJsonObject = new JsonObject();
		if (dataTable != null) {
			int rowCount = dataTable.size();

			if (rowCount > 1) {

				// The first row is the header row, store each column name.
				List<String> headerRow = dataTable.get(0);

				int columnCount = headerRow.size();

				// Loop in the row data list.
				for (int i = 1; i < rowCount; i++) {
					// Get current row data.
					List<String> dataRow = dataTable.get(i);

					// Create a JSONObject object to store row data.
					JsonObject rowJsonObject = new JsonObject();

					for (int j = 0; j < columnCount; j++) {
						String columnName = headerRow.get(j);
						String columnValue = dataRow.get(j);

						rowJsonObject.addProperty(columnName, columnValue);
					}

					tableJsonObject.add("Row " + i, rowJsonObject);
				}

			}
		}
		return tableJsonObject;
	}

	

	
}
