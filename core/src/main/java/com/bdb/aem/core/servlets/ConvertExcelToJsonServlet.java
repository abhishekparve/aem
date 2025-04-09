package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.services.tools.impl.IndexServiceImpl;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Component(name = "IndexExcelDataToSolr", service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "IndexExcelDataToSolr",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + ConvertExcelToJsonServlet.RESOURCE_TYPE })
public class ConvertExcelToJsonServlet extends BaseServlet {

	private static final long serialVersionUID = 7070484633098973650L;

	transient Logger logger = LoggerFactory.getLogger(ConvertExcelToJsonServlet.class);

	@Reference
	private transient ResourceResolverFactory resourceResolverFactory;

	@Reference
	transient SolrSearchService solrSearchService;

	@Reference
	private transient IndexServiceImpl indexService;

	/** The bdb api endpoint service. */
	@Reference
	private transient BDBApiEndpointService bdbApiEndpointService;
	
	@Reference
	transient BDBSearchEndpointService solrConfig;

	/** The rest client. */
	@Reference
	private transient RestClient restClient;

	public static final String RESOURCE_TYPE = "bdb/IndexExcelDataToSolr";

	/** SOLR_DATE_FORMAT. */
	public static final String SOLR_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	@Override
	public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		ResourceResolver resourceResolver = null;
			try {
				resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
			
			JsonObject requestObject = getRequestObject(request);
			String damFilePath = null != requestObject.get("damfilePath")
					? requestObject.get("damfilePath").getAsString()
					: null;
			String speciesReactivity = null != requestObject.get("speciesReactivity")
					? requestObject.get("speciesReactivity").getAsString()
					: null;
			JsonArray country = null != requestObject.get("country") ? requestObject.get("country").getAsJsonArray()
					: null;
			int start = null != requestObject.get("start") ? requestObject.get("start").getAsInt() : 0;
			int row = null != requestObject.get("row") ? requestObject.get("row").getAsInt() : solrConfig.cellTypeQueryLimit();
			String type = null != requestObject.get("type") ? requestObject.get("type").getAsString() : null;
			ArrayList<String> countriesList = (ArrayList<String>) CommonHelper.getAllCountries(resourceResolver,bdbApiEndpointService);
			if (null == country) {
				for (String countryValue : countriesList) {
					startCreatingObjectAndIndexing(damFilePath, speciesReactivity, countryValue, type, resourceResolver,
							start, row);
				}
			} else {
				for (JsonElement je : country) {
					startCreatingObjectAndIndexing(damFilePath, speciesReactivity, je.getAsString(), type,
							resourceResolver, start, row);
				}
			}
		} catch (LoginException e) {
			logger.error("Login Exception :", e);
		} catch (SolrServerException e) {
			logger.error("Solr Server Exception :", e);
		}

	}
	
	private void startCreatingObjectAndIndexing(String damFilePath, String speciesReactivity, String countryValue, String type, ResourceResolver resourceResolver, int start, int row) throws SolrServerException, IOException{
		if (StringUtils.isNotEmpty(damFilePath) && StringUtils.isNotEmpty(countryValue) && StringUtils.isNotEmpty(type) && StringUtils.isNotBlank(speciesReactivity)) {
			JsonArray solrResponse = null;
			if((!type.equalsIgnoreCase("cellType") || !type.equalsIgnoreCase("panelType")) && StringUtils.isNotEmpty(speciesReactivity)) {
				solrResponse = fetchSolrResponse(speciesReactivity, countryValue, start, row);
			}

			Resource res = resourceResolver.getResource(damFilePath);
			Asset asset = res.adaptTo(Asset.class);
			Rendition rendition = asset.getOriginal();
			InputStream inputStream = rendition.adaptTo(InputStream.class);
			JsonObject excelObject = creteJSONAndTextFileFromExcel(inputStream, asset.getMimeType());
			if (type.equalsIgnoreCase("antigen")) {
				List<JsonObject> listToBeIndexed = listOfObjectForIndexingAntigen(solrResponse, excelObject,
						speciesReactivity);
				logger.info(listToBeIndexed.toString());
				indexService.indexToSolr(listToBeIndexed, countryValue, type,resourceResolver,speciesReactivity);

			} else if (type.equalsIgnoreCase("clone")) {
				List<JsonObject> listToBeIndexed = listOfObjectForIndexingClone(solrResponse, excelObject,
						speciesReactivity);
				logger.info(listToBeIndexed.toString());
				indexService.indexToSolr(listToBeIndexed, countryValue, type,resourceResolver,speciesReactivity);
			}else if (type.equalsIgnoreCase("cellType") || type.equalsIgnoreCase("panelType")) {
				AtomicInteger ordinal = new AtomicInteger(1);
				List<JsonObject> indexItemList = new ArrayList<JsonObject>();
				while (null != excelObject.getAsJsonObject("Row " + ordinal.get())) {
					JsonObject row1 = excelObject.getAsJsonObject("Row " + ordinal.get());
					indexItemList.add(row1);
					ordinal.getAndIncrement();
				}
				logger.info("ICM Tools Type Json List {}",indexItemList.toString());
				indexService.indexToSolr(indexItemList, countryValue, type,resourceResolver,speciesReactivity);
			}

		}
	}

//	private List<JsonObject> listOfObjectForIndexingClone2(JsonArray solrResponse, JsonObject excelObject,
//			String speciesReactivity) {
//		AtomicInteger ordinal = new AtomicInteger(1);
//		List<JsonObject> indexItemList = new ArrayList<JsonObject>();
//		while (null != excelObject.getAsJsonObject("Row " + ordinal.get())) {
//			JsonObject row = excelObject.getAsJsonObject("Row " + ordinal.get());
//			if (null != row.get("TargetSpecies") && !row.get("TargetSpecies").isJsonNull() && null != row.get("TargetMolecule")
//					&& null != row.get("TdsCloneName") && row.get("TargetSpecies").getAsString().toLowerCase()
//							.contains(speciesReactivity.toLowerCase())) {
//				row.remove("TargetSpecies");
//				row.addProperty("TargetSpecies", speciesReactivity);
//				for (int i = 0; i < solrResponse.size(); i++) {
//					JsonObject response = solrResponse.get(i).getAsJsonObject();
//					if (null != response.get("specificity_t") && !row.get("TargetMolecule").isJsonNull()
//							&& response.get("specificity_t").getAsString()
//									.equals(row.get("TargetMolecule").getAsString())
//							&& response.get("tdsCloneName_t").getAsString()
//									.equals(row.get("TdsCloneName").getAsString())) {
//						// Adding the thumbnail image from solr response as a fallback.
//						if (null == row.get("Thumbnail") || row.get("Thumbnail").isJsonNull()
//								|| row.get("Thumbnail").getAsString().equals(StringUtils.EMPTY)) {
//							row.addProperty("Thumbnail", response.get("thumbnailImage_t").getAsString());
//						}
//						if (response.get("regulatoryStatus_t") != null
//								&& !response.get("regulatoryStatus_t").isJsonNull()
//								&& !response.get("regulatoryStatus_t").getAsString().equals("")) {
//							row.addProperty("regulatoryStatus", response.get("regulatoryStatus_t").getAsString());
//						}
//						handleFormatForClone(response, row);
//
//						indexItemList.add(row);
//						break;
//					}
//				}
//			}
//			ordinal.getAndIncrement();
//		}
//
//		return indexItemList;
//	}
	
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
		List<JsonObject> solrMatches = new ArrayList<>();
		solrResponse.forEach(sRes -> {
			JsonObject s = sRes.getAsJsonObject();

			if (getAsString(e.get("TargetSpecies")).toLowerCase().contains(sR.toLowerCase())
					&& getAsString(e.get("TargetMolecule")).equals(getAsString(s.get("specificity_t")))
					&& getAsString(e.get("TdsCloneName")).equals(getAsString(s.get("tdsCloneName_t")))) {
				solrMatches.add(s);
				handleFormatForClone(s, e);
				handleThumbnailForClone(s, e);

			}
		});
		if (solrMatches.size() > 0) {
			e.addProperty("TargetSpecies", sR);
			JsonArray rStatus = new JsonArray();
			JsonArray aFormats = new JsonArray();
			solrMatches.forEach(match -> {
				addIfNotExists(match.get("regulatoryStatus_t"), rStatus);
				addIfNotExists(match.get("dyeName_t"), aFormats);
			});
			String r = StringUtils.EMPTY;
			AtomicInteger ordinal = new AtomicInteger(0);
			for(JsonElement st: rStatus) {
				if(!getAsString(st).equals(StringUtils.EMPTY)) {
					r = r.concat(getAsString(st));
				}
				if(!(ordinal.get() == rStatus.size()-1)) {
					r = r.concat(" ");
				}
				ordinal.getAndIncrement();
			}
			e.addProperty("regulatoryStatus", r);
			e.add("otherFormats", aFormats);
			arr.add(e);
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
			if (checkNotBlank(e.get("MaterialNumber")) && !checkNotBlank(e.get("format"))) {
				if (getAsString(e.get("MaterialNumber")).equals(getAsString(s.get("materialNumber_t")))) {
					e.add("format", s.get("dyeName_t"));
				}
			} else if (!checkNotBlank(e.get("format"))) {
				e.add("format", s.get("dyeName_t"));
			}
		});
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
			if (checkNotBlank(e.get("Thumbnail")) && !checkNotBlank(e.get("image"))) {
				e.add("image", e.get("Thumbnail"));

			} else if (!checkNotBlank(e.get("image"))) {
				e.add("image", s.get("thumbnailImage_t"));
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
					if (null != response.get("specificity_t") && response.get("specificity_t").getAsString()
							.equals(row.get("Specificity").getAsString())) {
						addData(response, row, clones, "tdsCloneName_t");
						addData(response, row, products, "materialNumber_t");
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
		// HttpSolrClient server = new HttpSolrClient.Builder("https://devsearch.bdbiosciences.com/solr/bdbio-us").build();
		SolrQuery query = new SolrQuery();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(SOLR_DATE_FORMAT);
		LocalDateTime now = LocalDateTime.now();
		String[] filterQuery = new String[3];
		filterQuery[filterQuery.length - 3] = "docType_t:product";
		filterQuery[filterQuery.length - 2] = "isAvailable_t:true";
		filterQuery[filterQuery.length - 1] = "validityStartDate_dt:[* TO ".concat(dtf.format(now)).concat("]");
		query.setQuery("speciesReactivity_facet_ss:" + speciesReactivity);
		query.addFilterQuery(filterQuery);
		query.setStart(start);
		query.setRows(row);
		query.setFields("speciesReactivity_facet_ss", "dyeName_t", "specificity_t", "tdsCloneName_t",
				"thumbnailImage_t", "materialNumber_t", "laserColor_t", "regulatoryStatus_t");
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

		} 
		catch (IOException ex) {
			logger.error("IOException error occured", ex);
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
							rowDataList.add("".trim());
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
