package com.bdb.aem.core.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.solr.client.solrj.SolrClient;
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

import com.bdb.aem.core.bean.ProductAnnouncementBean;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.UpdateProductAnnouncementService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;

/**
 * The Class SolrSearchServiceImpl.
 */
@Component(service = UpdateProductAnnouncementService.class, immediate = true)
public class UpdateProductAnnouncementServiceImpl implements UpdateProductAnnouncementService {

	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateProductAnnouncementServiceImpl.class);

	private static final String ALT_TEXT = "altText";

	private static final String DISCLAIMER_STATUS = "disclaimerStatus";

	private static final String DISPLAY_BULK_REAGENT_QUOTE = "displayBulkReagentQuote";

	private static final String END_DATE = "endDate";

	private static final String START_DATE = "startDate";

	private static final String MORE_INFO_LINK = "moreInfoLink";

	private static final String OPEN_NEW_TAB = "openNewTab";

	private static final String PA_DESCRIPTION = "paDescription";

	private static final String PA_FAQ_TITLE = "paFAQTitle";

	private static final String PA_VIEW_MORE_CTA = "paViewMoreCTA";

	private static final String PRODUCT_ANNOUNCEMENT = "productAnnouncement";

	private static final String REGIONAL_DISCLAIMERS = "regionalDisclaimers";

	private static final String REPLACED_PRODUCT_ID = "replacedProductId";

	private static final String SMESSAGE_STATUS = "smessageStatus";

	private static final String SPECIAL_MESSAGE = "specialMessage";

	private static final String TESTIMONIAL_ID = "testimonialId";

	private static final String VIDEO_ID = "videoId";

	private static final String VIEW_MORE_FAQ_LABEL = "viewMoreFaqLabel";

	private static final String VIEW_MORE_FAQ_LINK = "viewMoreFaqLink";

	private static final String REGION = "region";

	private static final String MATERIAL_NUMBER = "materialNumber";

	private static final String MARKETING_TAG = "marketing";
	
	/** The BDB workflow Configuration service. */
	@Reference
	private BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The workflow helper service. */
	@Reference
	private WorkflowHelperService workflowHelperService;
	
	@Reference
	ResourceResolverFactory resolverFactory;
	
	@Reference
    Replicator replicator;
	
	@Reference
	SolrSearchService solrSearchService;
	
	@Override
	public void updateProductAnnouncement(String filePath, ResourceResolver resourceResolver) {
		LOGGER.debug("Entry processExcelFile method of ProcessProductAnnouncement");
		ProductAnnouncementBean productAnnouncementBean = null;
		try {
			
			Resource res = resourceResolver.getResource(filePath);
			Asset asset = res.adaptTo(Asset.class);
			Rendition rendition = asset.getOriginal();
			InputStream inputStream = rendition.adaptTo(InputStream.class);
			Workbook excelWorkBook = new XSSFWorkbook(inputStream);
			// Get first/desired sheet from the workbook
			Sheet sheet = excelWorkBook.getSheetAt(0);
			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			int rowCount = 0;
			while (rowIterator.hasNext()) {
				++rowCount;
				rowIterator.next();
					if(rowCount > 1) { 
					Map<String, String> getDataFromExcel = getExcelData(); 
						
					// Get the first and last sheet row number.
					int firstRowNum = sheet.getFirstRowNum();
					int lastRowNum = sheet.getLastRowNum();
					
					if (lastRowNum > 0) {
						// Loop in sheet rows.
						int lastCellNum = 0; /* Last column will always be equal to first row column */
						int firstCellNum = 0;
						for (int i = firstRowNum; i < lastRowNum + 1; i++) {
							// Get current row object.
							Row row1 = sheet.getRow(i);
							if (i == 0) {
								// Get first and last cell number.
								lastCellNum = row1.getLastCellNum();
								firstCellNum = row1.getFirstCellNum();
							}
							
							// Create a String list to save column data in a row.
							Iterator<Map.Entry<String, String>> mapIterator = getDataFromExcel.entrySet().iterator();
							// Loop in the row cells.
							for (int j = firstCellNum; j < lastCellNum; j++) {
								Map.Entry<String, String> entry = mapIterator.next();
								// Get current cell.
								Cell cell = row1.getCell(j);
								if (null != cell) {
									// Get cell type.
									getCellTypeData(entry, cell);
								} else {
									entry.setValue("");
								}
							}
							if (!getDataFromExcel.isEmpty()) {
								productAnnouncementBean = getAnnouncementMetaDataFromExcel(getDataFromExcel);
								createAndUpdateMarketingNode(productAnnouncementBean, resourceResolver);
							} else {
								LOGGER.debug("Excel does not contain any data");
							}
						}
					}
				}
			}
		excelWorkBook.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Mandatory Info is not present in file");
		}
		LOGGER.debug("Exit processExcelFile method of ProcessProductAnnouncement");		
	}
	
	/**
	 * 
	 * @return Excel Data
	 */
	private LinkedHashMap<String, String> getExcelData() {
		LinkedHashMap<String, String> getDataFromExcel = new LinkedHashMap<>();
		
		// Excel data key in ascending order
		getDataFromExcel.put(ALT_TEXT, "");
		getDataFromExcel.put(DISCLAIMER_STATUS, "");
		getDataFromExcel.put(DISPLAY_BULK_REAGENT_QUOTE, "");
		getDataFromExcel.put(END_DATE, "");
		getDataFromExcel.put(MATERIAL_NUMBER, "");
		getDataFromExcel.put(MORE_INFO_LINK, "");
		getDataFromExcel.put(OPEN_NEW_TAB, "");
		getDataFromExcel.put(PA_DESCRIPTION, "");
		getDataFromExcel.put(PA_FAQ_TITLE, "");
		getDataFromExcel.put(PA_VIEW_MORE_CTA, "");
		getDataFromExcel.put(PRODUCT_ANNOUNCEMENT, "");
		getDataFromExcel.put(REGION, "");
		getDataFromExcel.put(REGIONAL_DISCLAIMERS, "");
		getDataFromExcel.put(REPLACED_PRODUCT_ID, "");
		getDataFromExcel.put(SMESSAGE_STATUS, "");
		getDataFromExcel.put(SPECIAL_MESSAGE, "");
		getDataFromExcel.put(START_DATE, "");
		getDataFromExcel.put(TESTIMONIAL_ID, "");
		getDataFromExcel.put(VIDEO_ID, "");
		getDataFromExcel.put(VIEW_MORE_FAQ_LABEL, "");
		getDataFromExcel.put(VIEW_MORE_FAQ_LINK, VIEW_MORE_FAQ_LINK);
		
		return getDataFromExcel;
	}

	/**
	 * 
	 * @param entry
	 * @param cell
	 * @return cell type
	 */
	private void getCellTypeData(Entry<String, String> entry, Cell cell) {
		
		CellType cellType = cell.getCellType();

		if (cellType == CellType.NUMERIC) {
			int numberValue = (int) cell.getNumericCellValue();
			
			// BigDecimal is used to avoid double value is counted use Scientific counting
			// method.
			// For example the original double variable value is 12345678, but jdk
			// translated the value to 1.2345678E7.
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
	}

	/**
	 * 
	 * @param regions
	 */
	private void createAndUpdateMarketingNode(ProductAnnouncementBean productAnnouncementBean, ResourceResolver resourceResolver) {
		String regions = productAnnouncementBean.getRegion();
		if(regions != null && !regions.equalsIgnoreCase("Region")) {
			try {
				String materialNumber = productAnnouncementBean.getMaterialNumber();
				String country = getCountry(regions);
				SolrClient server = solrSearchService.solrClient(country);
				Resource hpResource = CommonHelper.getHpNodeResource(materialNumber, country, resourceResolver, server);
				if(null != hpResource) {
					String productBasePath = hpResource.getParent().getPath();
					String productVariantPath = productBasePath+ CommonConstants.SINGLE_SLASH + materialNumber.toLowerCase();
					String marketingNodetPath = productVariantPath	+ CommonConstants.SINGLE_SLASH + MARKETING_TAG;
					Session session = resourceResolver.adaptTo(Session.class);
					if (session.nodeExists(marketingNodetPath)) {
						replicator.replicate(session,ReplicationActionType.DEACTIVATE, marketingNodetPath);
						deleteNodeFromMarketingFolder(marketingNodetPath, resourceResolver, session);
						LOGGER.info("Deactivated and delete marketing note : ", marketingNodetPath);
					}
					workflowHelperService.createNode(marketingNodetPath, resourceResolver, session,
							  JcrResourceConstants.NT_SLING_FOLDER);
					LOGGER.info("Created marketing note : ", marketingNodetPath);
					List<String> namesList = Arrays.asList(regions.replaceAll("\\s*,\\s*", ",").split(","));
					for(String regionName : namesList){
						createMarketingNode(resourceResolver, productAnnouncementBean, regionName, session, marketingNodetPath);
					}
				}
			} catch (RepositoryException | ReplicationException e) {
				LOGGER.error("Repository Exception or Replication Exception : ", e);
			}
		}
		
	}
	
	/**
	 * 
	 * @param regions
	 * @return country
	 */
	private String getCountry(String regions) {
		String country =null;
		List<String> namesList = Arrays.asList(regions.replaceAll("\\s*,\\s*", ",").split(","));
		for(String regionName : namesList){
			if(regionName.equalsIgnoreCase(CommonConstants.GLOBAL)) {
				country = "us";
			} else if(regionName.equalsIgnoreCase(CommonConstants.EMEA)) {
				country = "de";
			} else if(regionName.equalsIgnoreCase(CommonConstants.APAC_EN) || regionName.equalsIgnoreCase(CommonConstants.APAC)) {
				country = "in";
			} else {
				country = regionName;
			}
		}
		
		return country;
	}
	
	/**
	 * 
	 * @param getDataFromExcel
	 * @param resourceResolver
	 * @param productAnnouncementBean
	 * @param regionName
	 * @param session 
	 * @param marketingNodetPath 
	 */
	private void createMarketingNode(ResourceResolver resourceResolver, ProductAnnouncementBean productAnnouncementBean, String regionName, Session session, String marketingNodetPath) {
		String destPath = marketingNodetPath + CommonConstants.SINGLE_SLASH + regionName;
		try {
			workflowHelperService.createNode(destPath, resourceResolver, session,
			  JcrResourceConstants.NT_SLING_FOLDER);
			// Set data into Node
			setAnnouncementForSKU(productAnnouncementBean, resourceResolver, destPath);
			session.save();
			replicator.replicate(session,ReplicationActionType.ACTIVATE,destPath);
			LOGGER.info("Successfully updated and replicated marketing note : ", destPath);
		} catch (RepositoryException | ReplicationException e) {
			LOGGER.error("Repository Exception or Replication Exception : ", e);
		}
	}
	
	/**
	 * 
	 * @param productAnnouncementBean
	 * @param serviceResolver
	 * @param destPath
	 * @throws RepositoryException
	 */
	private void setAnnouncementForSKU(ProductAnnouncementBean productAnnouncementBean,
			ResourceResolver serviceResolver, String destPath) throws RepositoryException {
		Resource announcementResource = serviceResolver.getResource(destPath);
		if (null != announcementResource) {
			Node currentNode = announcementResource.adaptTo(Node.class);
			placeAnnouncementdata(productAnnouncementBean, currentNode);
			LOGGER.debug("Updated announcement data for :", destPath);
		}
	}

	/**
	 * 
	 * @param productAnnouncementBean
	 * @param currentNode
	 * @throws RepositoryException
	 */
	private void placeAnnouncementdata(ProductAnnouncementBean productAnnouncementBean, Node currentNode)
			throws RepositoryException {
		currentNode.setProperty(ALT_TEXT, productAnnouncementBean.getAltText());
		currentNode.setProperty(DISCLAIMER_STATUS, productAnnouncementBean.getDisclaimerStatus());
		currentNode.setProperty(DISPLAY_BULK_REAGENT_QUOTE, productAnnouncementBean.getDisplayBulkReagentQuote());
		currentNode.setProperty(START_DATE, productAnnouncementBean.getStartDate());
		currentNode.setProperty(END_DATE, productAnnouncementBean.getEndDate());
		currentNode.setProperty(MORE_INFO_LINK, productAnnouncementBean.getMoreInfoLink());
		currentNode.setProperty(OPEN_NEW_TAB, productAnnouncementBean.getOpenNewTab());
		currentNode.setProperty(PA_DESCRIPTION, productAnnouncementBean.getPaDescription());
		currentNode.setProperty(PA_FAQ_TITLE, productAnnouncementBean.getPaFAQTitle());
		currentNode.setProperty(PRODUCT_ANNOUNCEMENT, productAnnouncementBean.getProductAnnouncement());
		currentNode.setProperty(SPECIAL_MESSAGE, productAnnouncementBean.getSpecialMessage());
		currentNode.setProperty(PA_VIEW_MORE_CTA, productAnnouncementBean.getPaViewMoreCTA());
		currentNode.setProperty(REGIONAL_DISCLAIMERS, productAnnouncementBean.getRegionalDisclaimers());
		currentNode.setProperty(SMESSAGE_STATUS, productAnnouncementBean.getSmessageStatus());
		currentNode.setProperty(VIEW_MORE_FAQ_LINK, productAnnouncementBean.getViewMoreFaqLink());
		currentNode.setProperty(VIDEO_ID, productAnnouncementBean.getVideoId());
		currentNode.setProperty(VIEW_MORE_FAQ_LABEL, productAnnouncementBean.getViewMoreFaqLabel());
		currentNode.setProperty(TESTIMONIAL_ID, productAnnouncementBean.getTestimonialId());
		currentNode.setProperty(REPLACED_PRODUCT_ID, productAnnouncementBean.getReplacedProductId());
	}

	/**
	 * 
	 * @param destPath
	 * @param serviceResolver
	 * @param session
	 * @throws RepositoryException
	 */
	private void deleteNodeFromMarketingFolder(String destPath, ResourceResolver serviceResolver, Session session)
			throws RepositoryException {
		LOGGER.debug("Entry deleteNodeFromMarketingFolder method of ProcessProductAnnouncement");
		com.adobe.granite.asset.api.AssetManager removeAssetMan = serviceResolver
				.adaptTo(com.adobe.granite.asset.api.AssetManager.class);
		LOGGER.debug("Removing resource : {}", destPath);
		removeAssetMan.removeAsset(destPath);
		session.save();
		LOGGER.debug("Exit deleteNodeFromMarketingFolder method of ProcessProductAnnouncement");
	}

	/**
	 * 
	 * @param getDataFromExcel
	 * @return productAnnouncementBean
	 */
	private ProductAnnouncementBean getAnnouncementMetaDataFromExcel(Map<String, String> getDataFromExcel) {
		LOGGER.debug("Entry getAnnouncementMetaDataFromExcel method of ProcessProductAnnouncement");
		ProductAnnouncementBean productAnnouncementBean = null;
		productAnnouncementBean = new ProductAnnouncementBean();
		productAnnouncementBean.setAltText(getDataFromExcel.get(ALT_TEXT));
		productAnnouncementBean.setDisclaimerStatus(getDataFromExcel.get(DISCLAIMER_STATUS));
		productAnnouncementBean.setDisplayBulkReagentQuote(getDataFromExcel.get(DISPLAY_BULK_REAGENT_QUOTE));
		productAnnouncementBean.setEndDate(getDataFromExcel.get(END_DATE));
		productAnnouncementBean.setMaterialNumber(getDataFromExcel.get(MATERIAL_NUMBER));
		productAnnouncementBean.setMoreInfoLink(getDataFromExcel.get(MORE_INFO_LINK));
		productAnnouncementBean.setOpenNewTab(getDataFromExcel.get(OPEN_NEW_TAB));
		productAnnouncementBean.setPaDescription(getDataFromExcel.get(PA_DESCRIPTION));
		productAnnouncementBean.setPaFAQTitle(getDataFromExcel.get(PA_FAQ_TITLE));
		productAnnouncementBean.setPaViewMoreCTA(getDataFromExcel.get(PA_VIEW_MORE_CTA));
		productAnnouncementBean.setProductAnnouncement(getDataFromExcel.get(PRODUCT_ANNOUNCEMENT));
		productAnnouncementBean.setRegion(getDataFromExcel.get(REGION));
		productAnnouncementBean.setRegionalDisclaimers(getDataFromExcel.get(REGIONAL_DISCLAIMERS));
		productAnnouncementBean.setReplacedProductId(getDataFromExcel.get(REPLACED_PRODUCT_ID));
		productAnnouncementBean.setSmessageStatus(getDataFromExcel.get(SMESSAGE_STATUS));
		productAnnouncementBean.setSpecialMessage(getDataFromExcel.get(SPECIAL_MESSAGE));
		productAnnouncementBean.setStartDate(getDataFromExcel.get(START_DATE));
		productAnnouncementBean.setTestimonialId(getDataFromExcel.get(TESTIMONIAL_ID));
		productAnnouncementBean.setVideoId(getDataFromExcel.get(VIDEO_ID));
		productAnnouncementBean.setViewMoreFaqLabel(getDataFromExcel.get(VIEW_MORE_FAQ_LABEL));
		productAnnouncementBean.setViewMoreFaqLink(getDataFromExcel.get(VIEW_MORE_FAQ_LINK));
		LOGGER.debug("Exist getAnnouncementMetaDataFromExcel method of ProcessProductAnnouncement");
		return productAnnouncementBean;
	}
}
