package com.bdb.aem.core.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.pojo.RowPojo;
import com.bdb.aem.core.services.CreateExcelNodeService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;

/**
 * The Class WorkflowHelperServiceImpl.
 */
@Component(service = CreateExcelNodeService.class, immediate = true)
public class CreateExcelNodeServiceImpl implements CreateExcelNodeService {
	/** The workflow helper service. */
	
	@Reference
	private WorkflowHelperService workflowHelperService;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateExcelNodeServiceImpl.class);
	
	/** The Constant anz. */
	private static final String [] anz = {"au","nz"};
	
	/** The Constant eu. */
	private static final String [] eu = {"at","be","dk","fi","fr","de","ie","it","lu","nl","no","pl","pt","es","se","ch","gb","eu"};
	
	/** The Constant BASE_NODE_PATH. */
	private static final String BASE_NODE_PATH = "/content/commerce/products/bdb/document/excel";
	
	/** The Constant ASSET_NAME. */
	private static final String ASSET_NAME = "baseFileName";
	
	/** The Constant MATARIAL_NUM. */
	private static final String MATARIAL_NUM = "matNumber";
	
	/** The Constant DOC_PART_ID. */
	private static final String DOC_PART_ID = "docPartId";
	
	/** The Constant DOC_TYPE. */
	private static final String DOC_TYPE = "docType";
	
	/** The Constant REGION. */
	private static final String REGION = "region";
	
	/** The Constant DELTA_FILE_NAME. */
	private static final String DELTA_FILE_NAME = "deltaMetadata.xlsx";
	
	/**
	 * Process doc metadata excel.
	 *
	 * @param session          the session
	 * @param resourceResolver the resource resolver
	 * @param queryParam       the query param
	 * @return the string
	 * @throws RepositoryException the repository exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String processDocMetadataExcel(Session session, ResourceResolver resourceResolver,
			Map<String, String> queryParam) throws RepositoryException, IOException{
		String response = "Could not get iterator of the excel sheet";
		String filePath = queryParam.get("filePath");
		Iterator<Row> itr = getMetadatExcel(filePath, resourceResolver);
		if (itr != null) {
			excelRowIterator(itr, session, resourceResolver, filePath);
			response = "Succesfully created structure";
		} else {
			LOGGER.error("Could not get iterator of the excel sheet");
		}
		return response;
	}
	
	/**
	 * Gets the metadat excel.
	 *
	 * @param excelPath the excel path
	 * @param resourceResolver the resource resolver
	 * @return the metadat excel
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Iterator<Row> getMetadatExcel(String excelPath, ResourceResolver resourceResolver) throws IOException {
		LOGGER.debug("Entry getMetadatExcel method of CreateExcelNodeServiceImpl");
		Iterator<Row> itr = null;
		if (StringUtils.isNoneBlank(excelPath)) {
			Resource metadataExcelResource = resourceResolver.getResource(excelPath);
			if (null != metadataExcelResource) {
				Asset asset = metadataExcelResource.adaptTo(Asset.class);
				if (null != asset && (asset.getMimeType().equals(CommonConstants.XLSX_MIME_TYPE)
						|| asset.getMimeType().equals(CommonConstants.XLS_MIME_TYPE))) {
					XSSFWorkbook xssfWorkbook = new XSSFWorkbook(asset.getOriginal().getStream());
					XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
					itr = sheet.iterator();
				}
			}
		} else {
			throw new IllegalArgumentException("Path should not be null");
		}
		LOGGER.debug("Exit getMetadatExcel method of CreateExcelNodeServiceImpl");
		return itr;
	}
	
	/**
	 * Excel row iterator.
	 *
	 * @param rowIterator the row iterator
	 * @param session the session
	 * @param resourceResolver the resource resolver
	 * @throws RepositoryException the repository exception
	 */
	public void excelRowIterator(Iterator<Row> rowIterator, Session session, ResourceResolver resourceResolver, String path) throws RepositoryException{
		List<RowPojo> rows = new ArrayList<>();
		List<RowPojo> deltaRows = new ArrayList<>();
		rowIterator.next();
		while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				setPojoCreateNode(row, rows, deltaRows);
		}
		createStructure(session, resourceResolver, rows);
		if(CollectionUtils.isNotEmpty(deltaRows)) {
			createDelta(resourceResolver, session, deltaRows, path);
		}
	}

	/**
	 * Creates the structure.
	 *
	 * @param session the session
	 * @param resourceResolver the resource resolver
	 * @param rows the rows
	 * @throws RepositoryException the repository exception
	 */
	private void createStructure(Session session, ResourceResolver resourceResolver, List<RowPojo> rows)
			throws RepositoryException {
		int counter = 0;
		int i = 0;
		for (RowPojo row : rows) {
			 creatNodesFromRow(row, session, resourceResolver, counter);
			 i++;
			 if (i == 1000) {
				 i = 0;
				 session.save();
			 }
		}
		session.save();
	}

	/**
	 * Sets the pojo create node.
	 *
	 * @param row the row
	 * @param rows the rows
	 * @param deltaRows the delta rows
	 */
	public void setPojoCreateNode(Row row, List<RowPojo> rows, List<RowPojo> deltaRows) {
		RowPojo rowPojo = new RowPojo();
		String stringCellValue = StringUtils.EMPTY;
		for (int i = 0; i < 5; i++) {
			Cell cell = row.getCell(i);
			if (null != cell) {
				stringCellValue = cell.toString();
				if (StringUtils.contains(stringCellValue, ".0")) {
					stringCellValue = stringCellValue.substring(0, (stringCellValue.indexOf(".0")));
				}
			}
			setValueToPojo(rowPojo, stringCellValue, i);
		}

		if (canProcess(rowPojo)) {
			rows.add(rowPojo);
		} else {
			deltaRows.add(rowPojo);
		}
	}
	
	/**
	 * Sets the value to pojo.
	 *
	 * @param rowPojo the row pojo
	 * @param stringCellValue the string cell value
	 * @param i the i
	 */
	public void setValueToPojo(RowPojo rowPojo, String stringCellValue, int i) {
		switch (i) {
		case 0:
			rowPojo.setMatNumber(stringCellValue.trim());
			break;
		case 1:
			rowPojo.setDocPartId(stringCellValue.trim());
			break;
		case 2:
			rowPojo.setBaseFileName(stringCellValue.trim());
			break;
		case 3:
			rowPojo.setDocType(stringCellValue.trim());
			break;
		case 4:
			rowPojo.setRegion(getRegionArrayStrings(stringCellValue.trim()));
			break;
		default:
			break;
		}
	}
	
	/**
	 * Gets the region array strings.
	 *
	 * @param input the input
	 * @return the region array strings
	 */
	public String[] getRegionArrayStrings(String input) {
		String[] array = input.split("[,\\|]");
		Set<String> finalList = new HashSet<>();
		for (String element : array) {
			if (null != element && element.length() > 0 && (!element.equalsIgnoreCase("0"))) {
				element = element.trim().toLowerCase();
				if (StringUtils.equalsIgnoreCase("anz", element)) {
					Collections.addAll(finalList, anz);
				} else if (StringUtils.equalsIgnoreCase("eu", element)) {
					Collections.addAll(finalList, eu);
				} else {
					finalList.add(element);
				}
			}
		}
		return finalList.toArray(new String[0]);
	}
	
	/**
	 * Can process.
	 *
	 * @param rowPojo the row pojo
	 * @return true, if successful
	 */
	public boolean canProcess(RowPojo rowPojo) {
		boolean canProcess = false;
		String baseFileName = rowPojo.getBaseFileName().trim();
		if (!baseFileName.equalsIgnoreCase("#N/A") && StringUtils.isNotEmpty(baseFileName)) {
			baseFileName = baseFileName.substring(0, baseFileName.indexOf(CommonConstants.DOT_PDF));
			String docPartId = rowPojo.getDocPartId();
			String matNumber = rowPojo.getMatNumber();
			if ((baseFileName.equalsIgnoreCase(docPartId) || baseFileName.equalsIgnoreCase(matNumber))
					&& rowPojo.getRegion().length > 0) {
				canProcess = true;
			} else {
				canProcess = false;
			}
		}
		return canProcess;
	}
	
	/**
	 * Creat nodes from row.
	 *
	 * @param rowPojo the row pojo
	 * @param session the session
	 * @param resourceResolver the resource resolver
	 * @param counter the counter
	 * @throws RepositoryException the repository exception
	 */
	public void creatNodesFromRow(RowPojo rowPojo, Session session, ResourceResolver resourceResolver, int counter)
			throws RepositoryException {
		if (null != session) {
			if (isNodeExist(session, BASE_NODE_PATH)) {
				addNodeSetProp(session, rowPojo,counter);
			} else {
				workflowHelperService.createNode(BASE_NODE_PATH, resourceResolver, session,
						JcrResourceConstants.NT_SLING_FOLDER, JcrResourceConstants.NT_SLING_FOLDER);
				session.save();
				addNodeSetProp(session, rowPojo, counter);
			}
		} else {
			throw new RepositoryException("Session is null");
		}
	}

	/**
	 * Checks if is node exist.
	 *
	 * @param session the session
	 * @param nodeName the node name
	 * @return true, if is node exist
	 * @throws RepositoryException the repository exception
	 */
	public boolean isNodeExist(Session session, String nodeName) throws RepositoryException {
		return session.nodeExists(nodeName);
	}

	/**
	 * Adds the node set prop.
	 *
	 * @param session the session
	 * @param rowPojo the row pojo
	 * @param counter the counter
	 * @throws RepositoryException the repository exception
	 */
	public void addNodeSetProp(Session session, RowPojo rowPojo, int counter) throws RepositoryException {
		String nodeNameToBeCreated = createNodeName(session, rowPojo.getMatNumber().trim(), counter);
		Node baseNode = session.getNode(BASE_NODE_PATH);
		Node createdNode = baseNode.addNode(nodeNameToBeCreated,JcrResourceConstants.NT_SLING_FOLDER);
		createdNode.setProperty(ASSET_NAME, rowPojo.getBaseFileName());
		createdNode.setProperty(MATARIAL_NUM, rowPojo.getMatNumber());
		createdNode.setProperty(DOC_PART_ID, rowPojo.getDocPartId());
		createdNode.setProperty(DOC_TYPE, rowPojo.getDocType());
		createdNode.setProperty(REGION, rowPojo.getRegion());
	}
	
	/**
	 * Creates the node name.
	 *
	 * @param session the session
	 * @param input the input
	 * @param counter the counter
	 * @return the string
	 * @throws RepositoryException the repository exception
	 */
	public String createNodeName(Session session, String input, int counter) throws RepositoryException {
		if (!isNodeExist(session, BASE_NODE_PATH + CommonConstants.SINGLE_SLASH + input)) {
			return input;
		} else {
			if (input.contains(CommonConstants.HYPHEN)) {
				input = input.substring(0, input.indexOf(CommonConstants.HYPHEN));
			}
			counter++;
			return createNodeName(session, (input + CommonConstants.HYPHEN +counter), counter);
		}		
	}
	
	/**
	 * Creates the delta.
	 *
	 * @param resourceResolver the resource resolver
	 * @param session the session
	 * @param deltaRows the delta rows
	 */
	public void createDelta(ResourceResolver resourceResolver, Session session, List<RowPojo> deltaRows, String path) {
		LOGGER.debug("Entry createDelta method of CreateExcelNodeServiceImpl");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(DELTA_FILE_NAME);
		createRows(sheet, deltaRows);
		byte[] barray = createWorkbook(workbook);
		if (null != barray) {
			try (InputStream assetInputStream = new ByteArrayInputStream(barray);) {
				AssetManager assetMan = resourceResolver.adaptTo(AssetManager.class);
				workflowHelperService.createAsset(assetMan, assetInputStream,
						path.replace(path.substring(path.lastIndexOf(CommonConstants.SINGLE_SLASH) + 1), DELTA_FILE_NAME),
						CommonConstants.XLSX_MIME_TYPE, resourceResolver, session);
			} catch (FileNotFoundException e) {
				LOGGER.error("Could not find the file: ", e);
			} catch (IOException e) {
				LOGGER.error("IO Exception Occured while writing workbook: ", e);
			} catch (RepositoryException e) {
				LOGGER.error("Repository Exception Occured while writing workbook: ", e);
			} 
		}
	}

	/**
	 * Creates the workbook.
	 *
	 * @param workbook the workbook
	 * @return the byte[]
	 */
	public byte[] createWorkbook(XSSFWorkbook workbook) {
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
	
	/**
	 * Creates the rows.
	 *
	 * @param sheet the sheet
	 * @param deltaRows the delta rows
	 */
	public void createRows(XSSFSheet sheet, List<RowPojo> deltaRows) {
		RowPojo pojo;
		Cell cell;
		for (int i = 0; i < deltaRows.size(); i++) {
			XSSFRow row = sheet.createRow(i);
			pojo = deltaRows.get(i);
			cell = row.createCell(0);
			cell.setCellValue(pojo.getMatNumber());
			cell = row.createCell(1);
			cell.setCellValue(pojo.getDocPartId());
			cell = row.createCell(2);
			cell.setCellValue(pojo.getBaseFileName());
		}
	}
}