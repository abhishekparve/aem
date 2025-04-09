package com.bdb.aem.core.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.xmp.core.parser.ParseRDFException;
import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.impl.BaseRequestImpl;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.bean.CreateSynonymBean;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CreateSynonymsService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.servlets.BaseServlet;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SearchConstants;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component(service = CreateSynonymsService.class, immediate = true)
public class CreateSynonymsServiceImpl implements CreateSynonymsService {

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateSynonymsServiceImpl.class);
	private static final String TO = "to";
	private static final String FROM = "from";
	private static final String LANGUAGE = "language";
	private static final String SYNONYM = "synonym";

	@Reference
	private transient BDBSearchEndpointService bdbSearchEndpointService;

	@Reference
	private transient SolrSearchService solrSearchService;

	/** The rest client. */
	@Reference
	private transient RestClient restClient;

	@Reference
	private transient ResourceResolverFactory resolverFactory;
	
	
	
	
	@Override
	public void createSynonyms(String filePath, ResourceResolver resourceResolver) {
		
		CreateSynonymBean createSynonymBean = null;
		try {
			
			Resource res = resourceResolver.getResource(filePath);
			Asset asset = res.adaptTo(Asset.class);
			Rendition rendition = asset.getOriginal();
			InputStream inputStream = rendition.adaptTo(InputStream.class);

			Workbook excelWorkBook = new XSSFWorkbook(inputStream);

			// Get first/desired sheet from the workbook
			Sheet sheet = excelWorkBook.getSheetAt(0);
			int totalColums = sheet.getRow(0).getLastCellNum();


			// Get the first and last sheet row number.
			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();

			if (lastRowNum > 0) {

				for (int i = firstRowNum; i < lastRowNum + 1; i++) {

					if (i > 0) {

						@SuppressWarnings("serial")
						LinkedHashMap<String, String> getDataFromExcel = new LinkedHashMap<String, String>() {
							{
								put(FROM, "");
								put(TO, "");
								put(LANGUAGE, "");
								put(SYNONYM, "");

							}
						};
						
						// Get current row object.
						Row row1 = sheet.getRow(i);

						// Create a String list to save column data in a row.
						Iterator<Map.Entry<String, String>> mapIterator = getDataFromExcel.entrySet().iterator();
						// Loop in the row cells.
						for (int j = 0; j < totalColums; j++) {
							Map.Entry<String, String> entry = mapIterator.next();
							// Get current cell.
							Cell cell = row1.getCell(j);
							if (null != cell) {
								// Get cell type.
								CellType cellType = cell.getCellType();

								if (cellType == CellType.NUMERIC) {
									int numberValue = (int) cell.getNumericCellValue();
									String stringCellValue = BigDecimal.valueOf(numberValue).toPlainString();

									entry.setValue(stringCellValue);

								} else if (cellType == CellType.STRING) {							
									String cellValue = cell.getStringCellValue();
									entry.setValue(cellValue);
								} else if (cellType == CellType.BOOLEAN) {
									boolean numberValue = cell.getBooleanCellValue();
									String stringCellValue = String.valueOf(numberValue);
									entry.setValue(stringCellValue);
								} else if (cellType == CellType.BLANK) {
									entry.setValue("");
								}
							} else {
								entry.setValue("");
							}
						}
						if (getDataFromExcel.size() > 0) {
							
							createSynonymBean = getSynonymMetaDataFromExcel(getDataFromExcel);

						} else {
							
							createSynonymBean = null;
						}
						if(null!=createSynonymBean) {
							String[] valTo = createSynonymBean.getTo();

							String valFrom = createSynonymBean.getFrom();
						}
					

						getCreateSynonymEndpoint("english",createSynonymBean, resourceResolver);

					}
				
				}
			}
			excelWorkBook.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Mandatory Info is not present in file");
		}
		LOGGER.debug("Exit processExcelFile method of CreateSynonyms");

	}

	private CreateSynonymBean getSynonymMetaDataFromExcel(LinkedHashMap<String, String> getDataFromExcel) {
		LOGGER.debug("Entry getSynonymMetaDataFromExcel method of CreateSynonyms");
		CreateSynonymBean createSynonymBean = null;
		createSynonymBean = new CreateSynonymBean();
		if(getDataFromExcel.get(SYNONYM).equalsIgnoreCase("TRUE")) {
			createSynonymBean.setFrom("synonym");
		}
		else {
			createSynonymBean.setFrom(getDataFromExcel.get(FROM));
		}
		createSynonymBean.setTo(getDataFromExcel.get(TO));
		createSynonymBean.setLanguage(getDataFromExcel.get(LANGUAGE));
		createSynonymBean.setSynonym(getDataFromExcel.get(SYNONYM));

		LOGGER.debug("Exist getSynonymMetaDataFromExcel method of CreateSynonyms");
		return createSynonymBean;
	}
	
	private String getCreateSynonymEndpoint(String lang, CreateSynonymBean createSynonymBean, ResourceResolver resourceResolver)
			throws LoginException {

		String languageKey = createSynonymBean.getLanguage();

		String synonymRequestValue = createSynonymBean.getSynonym();

				
		ArrayList<String> countriesList = (ArrayList<String>) solrSearchService.getAllCountries(resourceResolver);
		
		JsonObject response = new JsonObject();
		JsonObject requestObject = new JsonObject();
		JsonElement synonym = null;
		JsonArray trimSynonymArr = new JsonArray();
		boolean isSynonym = StringUtils.equalsIgnoreCase(synonymRequestValue, "true");
		if(createSynonymBean!=null) {
			Gson gson = new Gson();
			JsonParser parser= new JsonParser();
			JsonElement element = parser.parse(gson.toJson(createSynonymBean.getTo()));
			requestObject.add(createSynonymBean.getFrom(), element);

		}
		if(isSynonym) {
			synonym = requestObject.get(SearchConstants.SYNONYM);
			/* Trimming all trailing and leading white spaces from synonym object
			 * GE-20848
			 */
			JsonArray synonymArr = synonym.getAsJsonArray();
			for (int i = 0; i < synonymArr.size(); i++) {
				JsonElement synonymObj = synonymArr.get(i);
				String trimSynonymObj = synonymObj.getAsString().trim();
				trimSynonymArr.add(trimSynonymObj);
			}
		}
		for (String country : countriesList) {

			try {

				final Map<String, String> requestHeaderMap = new HashMap<>();
				StringBuilder requestUrl = new StringBuilder();
				requestUrl = requestUrl.append(bdbSearchEndpointService.getSolrUrl())
						.append(bdbSearchEndpointService.getCreateSynonym()
								.replace(SolrSearchConstants.COUNTRY_VARIABLE, country.toLowerCase())
								.replace(SearchConstants.SEARCH_LANGUAGE_KEY_SELECTOR, languageKey));
				LOGGER.debug("getCreateSynonymEndpoint {} ", requestUrl);

				LOGGER.debug("getCreateSynonymEndpoint- requestObject {} ", requestObject);

				
				BaseRequest baseRequest = new BaseRequestImpl(requestUrl.toString(), HttpMethodType.PUT,
						isSynonym && null != trimSynonymArr ? trimSynonymArr.toString() : requestObject.toString());
				requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);

				BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
				
				
				response = fetchResponseJson(response, baseResponse, country, "getCreateSynonymEndpoint");
				

			} catch (AemInternalServerErrorException | HttpSolrClient.RemoteSolrException e) {

				LOGGER.error("Exception in AemInternalServerErrorException in getCreateSynonymEndpoint {}",
						e.getMessage());
			}
		}
		
		String name = isSynonym && null != trimSynonymArr ? trimSynonymArr.toString() : requestObject.toString();
		addRecordToReplication(name);
			

		return response.toString();
	}
	
	private void addRecordToReplication(String name) {
		Map<String, String> requestHeader = new HashMap<>();
		requestHeader.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		SolrInputDocument inputDoc = new SolrInputDocument(
				"id", currentDateTime.toString(),
				"uniqueIdentifier", currentDateTime.toString(),
				"name_t", name);
		String coreName = bdbSearchEndpointService.getContentPageCollectionName() + "-" + "replication";
		String solrUrl = bdbSearchEndpointService.getSolrUrl() + SolrSearchConstants.SLASH_SOLR_SLASH + coreName;
		try {
			HttpSolrClient server = new HttpSolrClient.Builder(solrUrl).withHttpClient(restClient.getHttpClient()).build();
			server.add(inputDoc);
			server.commit();
			server.close();
			LOGGER.debug("Finished Indexing for core : {}", coreName);
		} 
		catch (SolrServerException | IOException | HttpSolrClient.RemoteSolrException e) {
			LOGGER.error("Exception while indexing to replication core {}", e);
		}
	}
	
	private JsonObject fetchResponseJson(JsonObject response, BaseResponse baseResponse, String country, String methodName) {
		String message = StringUtils.EMPTY;
		if (null != baseResponse.getError()) {
			LOGGER.error("Error in {} {}", methodName, baseResponse.getError().getMessage());
			message = baseResponse.getError().getMessage();
		} else if (null != baseResponse.getResponseData()) {
			message = baseResponse.getResponseData().getData();
		} else {
			LOGGER.error("Blank Response in {}", methodName);
			message = CommonConstants.BLANK;
		}
		
		if (!message.equals(CommonConstants.BLANK)) {
			JsonObject messageJson = new JsonParser().parse(message).getAsJsonObject();
			response.add(country, messageJson);
		} else {
			response.addProperty(country, "Failed to delete Synonyms");
		}
		return response;
	}

}
