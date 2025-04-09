package com.bdb.aem.core.services.impl;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.FailedImageBean;
import com.bdb.aem.core.services.GenerateFailedImageReportService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.dam.api.AssetManager;

/**
 * The Class GenerateFailedImageReportServiceImpl.
 */
@Component(immediate = true, service = GenerateFailedImageReportService.class)
public class GenerateFailedImageReportServiceImpl implements GenerateFailedImageReportService {
	
	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateFailedImageReportServiceImpl.class);
	
	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	
	@Reference
	private WorkflowHelperService workflowHelperService;
	
	ResourceResolver serviceResolver = null;
	
	List<FailedImageBean> failedImageBeanList;
	List<Resource> olderRecordsList;
	FailedImageBean failedImageBean;
	
	public static final String IMAGE_NAME = "Image Name";
	public static final String MATERIAL_NUMBER = "Material Number";
	public static final String ERROR_REASON = "Error Reason";
	public static final String RETRY_COUNT = "Retry Count";
	
	@Override
	public void generateFailedImageReport(ResourceResolver serviceResolver, Resource failedRecordsResource, Session session, String failedImagesReportPath) {
		
		if(null != failedRecordsResource) {
			LOGGER.info("In Generate Failed Image Report Method.");
			Iterator<Resource> children = failedRecordsResource.listChildren();
			failedImageBeanList = new ArrayList<FailedImageBean>();
			olderRecordsList = new ArrayList<Resource>();
			while (children.hasNext()) {
				failedImageBean = new FailedImageBean();
				Resource child = children.next();
				ValueMap vm = child.getValueMap();
				failedImageBean.setImageName(vm.containsKey(CommonConstants.IMAGE_NAME) ? vm.get(CommonConstants.IMAGE_NAME).toString() : StringUtils.EMPTY); 
				failedImageBean.setMaterialNumber(vm.containsKey(CommonConstants.MATERIAL_NUMBER) ? vm.get(CommonConstants.MATERIAL_NUMBER).toString() : StringUtils.EMPTY);
				String[] errorReasons = vm.containsKey(CommonConstants.ERROR_REASON) ? vm.get(CommonConstants.ERROR_REASON, String[].class) : null;
				String errorReason = Arrays.toString(errorReasons);
				errorReason = null != errorReason ? errorReason.substring( 1, errorReason.length() - 1 ) : StringUtils.EMPTY;
				failedImageBean.setErrorReason(errorReason);
				failedImageBean.setRetryCount(vm.containsKey(CommonConstants.RETRY_COUNT) ? vm.get(CommonConstants.RETRY_COUNT).toString() : StringUtils.EMPTY);
				failedImageBeanList.add(failedImageBean);
				
				Calendar jcrModifiedDate = vm.containsKey("jcr:lastModified") ? vm.get("jcr:lastModified", Calendar.class) : null;
				Calendar currentDateAndTime = Calendar.getInstance();
				if (jcrModifiedDate != null && currentDateAndTime != null) {
					long timeDifference = currentDateAndTime.getTimeInMillis() - jcrModifiedDate.getTimeInMillis(); 
					long days = TimeUnit.MILLISECONDS.toDays(timeDifference); 
					if (days > 30) {
						olderRecordsList.add(child);
					}   
				}
				
			}
			LOGGER.info("Older than 30 days failed images {}", olderRecordsList);
		} else {
			LOGGER.info("Failed images resource is null ");
        }
		
		// Genereate all the failed records report as xlsx format
		createExcelReport(serviceResolver, session, failedImageBeanList, failedImagesReportPath);
		// Delete older than 30 days filed items from repo.
		deleteOlderRecords(olderRecordsList, session);
	}
	
	/**
	 * Deletes older than 30 days records form repo
	 * @param olderRecordsList
	 */
	private void deleteOlderRecords(List<Resource> olderResourceList, Session session) {
		if (null != olderResourceList && !olderResourceList.isEmpty()) {
			for (Resource failedResource : olderResourceList) {
				try {
					failedResource.adaptTo(Node.class).remove();
					session.save();
				} catch (RepositoryException e) {
					LOGGER.error("Exception occured {}", e);
				} 
			}
		}
		LOGGER.info("Deleted the older than 30 days image nodes.");
	}

	/**
	 * 
	 * @param serviceResolver
	 * @param session
	 * @param failedImageBeanList
	 */
	private void createExcelReport(ResourceResolver serviceResolver, Session session, List<FailedImageBean> failedImageBeanList, String destinationPath) {
		LOGGER.info("Inside createExcelReport method");
		//declare file name to be create   
		String FILE_NAME = "failed-image-report.xlsx"; 
		try {
						
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet(FILE_NAME);
				
			int i = 0;
			// Create the header row
			Row headerRow = sheet.createRow(i);
			headerRow.createCell(0).setCellValue(IMAGE_NAME);
			headerRow.createCell(1).setCellValue(MATERIAL_NUMBER);
			headerRow.createCell(2).setCellValue(ERROR_REASON);
			headerRow.createCell(3).setCellValue(RETRY_COUNT);
			
			for (FailedImageBean failedImageBean : failedImageBeanList) {
				Row row = sheet.createRow(i + 1); // Start from the second row
			    row.createCell(0).setCellValue(failedImageBean.getImageName());
			    row.createCell(1).setCellValue(failedImageBean.getMaterialNumber());
			    row.createCell(2).setCellValue(failedImageBean.getErrorReason());
			    row.createCell(3).setCellValue(failedImageBean.getRetryCount());
			    i++;
			}
			
			byte[] barray = createWorkbook(workbook);
			
			if (null != barray) {
				try (InputStream assetInputStream = new ByteArrayInputStream(barray);) {
					AssetManager assetMan = serviceResolver.adaptTo(AssetManager.class);
					workflowHelperService.createAsset(assetMan, assetInputStream, destinationPath + FILE_NAME,
							CommonConstants.XLSX_MIME_TYPE, serviceResolver, session);
					LOGGER.info("Successfully generated report and stored in AEM DAM.");
				} catch (FileNotFoundException e) {
					LOGGER.error("Could not find the file: ", e);
				} catch (IOException e) {
					LOGGER.error("IO Exception Occured while writing workbook: ", e);
				} catch (RepositoryException e) {
					LOGGER.error("Repository Exception Occured while writing workbook: ", e);
				} 
			}
			//closing the workbook  
			workbook.close();  
			//prints the message on the console  
			System.out.println("Excel file has been generated successfully.");
			
		} catch (IOException e) {
			LOGGER.error("IOException", e);
		}
	}
	
	/**
	 * Creates the workbook.
	 *
	 * @param workbook the workbook
	 * @return the byte[]
	 */
	public byte[] createWorkbook(Workbook workbook) {
		byte[] barray = null;
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {			
			workbook.write(bos);
			barray = bos.toByteArray();
		} catch (IOException e) {
			LOGGER.error("IO Exception Occured while writing workbook: ", e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				LOGGER.error("Exception Occured while closing workbook : ", e);
			}
		}
		return barray;
	}

}