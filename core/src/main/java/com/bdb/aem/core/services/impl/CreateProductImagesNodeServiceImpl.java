package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.bean.ProcessProductImageBean;
import com.bdb.aem.core.services.CreateProductImagesNodeService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This service class is used to create image metadata node under /var/bdb/images/unprocessed-assets/{Processing-date}/{Processing-time}/{record}/.. path
 * for images that are not pushed from source/Half pipe.
 */

@Component(service = CreateProductImagesNodeService.class, immediate = true)
public class CreateProductImagesNodeServiceImpl implements CreateProductImagesNodeService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductImagesNodeServiceImpl.class);

    /**
     * The constant image name
     */
    private static final String IMAGE_NAME = "Image Name";

    /**
     * The constant material Number
     */
    private static final String MATERIAL_NUMBER = "Product Material Number";

    /**
     * The constant sling folder
     */
    private static final String IMAGE_TITLE = "Image Title";

    /**
     * The constant image desc
     */
    private static final String IMAGE_DESCRIPTION = "Image Description";

    /**
     * The constant image alt text
     */
    private static final String IMAGE_ALT_TEXT = "Image Alt Text";

    /**
     * The constant image Type
     */
    private static final String IMAGE_TYPE = "Image Type";

    /**
     * The constant caption
     */
    private static final String CAPTION = "Caption";

    /**
     * The constant record path
     */
    private static final String UNPROCESSED_RECORDS_NODE_PATH = "/var/bdb/products/images/unprocessed-records";

    /**
     * The constant record
     */
    private static final String RECORD = "record-";

    /**
     * This method is used to create image metadata node under /var/bdb/images/unprocessed-assets/{Processing-date}/{Processing-time}/{record}/.. path
     * for images that are not pushed from source/Half pipe based on Excel uploaded under file path.
     *
     * @param resource
     * @param session
     * @throws IOException
     * @throws RepositoryException
     */
    @Override
    public void createProductImageNodes(Resource resource, Session session) throws IOException, RepositoryException {
        LOGGER.debug("Entry createProductImageNodes method of CreateProductImagesNodeServiceImpl");

        Asset asset = resource.adaptTo(Asset.class);
        Rendition rendition = asset.getOriginal();
        InputStream inputStream = rendition.adaptTo(InputStream.class);

        /** Creating workbook */
        Workbook excelWorkBook = new XSSFWorkbook(inputStream);

        /** Getting the sheet */
        Sheet sheet = excelWorkBook.getSheetAt(0);

        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();

        /** Process only when we have at least an entry except headers in Excel */
        if (lastRowNum > 0) {
            Row row = sheet.getRow(firstRowNum);
            int lastCellNum = row.getLastCellNum();
            int firstCellNum = row.getFirstCellNum();
            /* Variable to store the path based on timeStamp */
            String timeNodePath = createDateAndTimeNode(session);
            int rowCount;
            /* Iterating through each excel rows */
            for (rowCount = firstRowNum + 1; rowCount <= lastRowNum; rowCount++) {
                row = sheet.getRow(rowCount);
                processingDataFromExcel(row, session, rowCount, firstCellNum, lastCellNum, timeNodePath);
            }
        }
        excelWorkBook.close();
        LOGGER.debug("Exit createProductImageNodes method of CreateProductImagesNodeServiceImpl");
    }

    /**
     * function to process each cell from Excel and map it to a property node
     *
     * @param row
     * @param session
     * @param recordCount
     * @throws RepositoryException
     */
    private void processingDataFromExcel(Row row, Session session, int recordCount, int firstCellNum, int lastCellNum, String timeNodePath) throws RepositoryException {
        LOGGER.debug("Entry processingDataFromExcel method of CreateProductImagesNodeServiceImpl");
        TreeMap<String, String> excelToNodeMapping = new TreeMap<>();
        excelToNodeMapping.put(IMAGE_NAME, "");
        excelToNodeMapping.put(MATERIAL_NUMBER, "");
        excelToNodeMapping.put(IMAGE_TITLE, "");
        excelToNodeMapping.put(IMAGE_DESCRIPTION, "");
        excelToNodeMapping.put(IMAGE_ALT_TEXT, "");
        excelToNodeMapping.put(IMAGE_TYPE, "");
        excelToNodeMapping.put(CAPTION, "");

        Iterator<Map.Entry<String, String>> mapIterator = excelToNodeMapping.entrySet().iterator();
        mappingMetaDataFromExcel(row, firstCellNum, lastCellNum, mapIterator);

        /* setting the data into product image bean file */
        ProcessProductImageBean processProductImageBean = setMetadataFromExcel(excelToNodeMapping);

        /**
         * Only creating the node when :
         * Product Name, Image Name and Image Type are present and valid for each row.
         */
        if (canProcess(processProductImageBean)) {
            StringBuilder recordNodePathBuilder = new StringBuilder();
            recordNodePathBuilder.append(timeNodePath);
            recordNodePathBuilder.append(CommonConstants.SLASH);
            recordNodePathBuilder.append(RECORD);
            recordNodePathBuilder.append(recordCount);

            String recordNodePath = recordNodePathBuilder.toString();
            JcrUtils.getOrCreateByPath(recordNodePath, CommonConstants.SLING_FOLDER, Objects.requireNonNull(session));
            String[] productSkus = processProductImageBean.getMaterialNumber().split(CommonConstants.COMMA);
            for (String productSku : productSkus) {
                /* creating product image nodes under record Node path based on productSku */
                createAndUpdateProductNodes(processProductImageBean, productSku.trim(), recordNodePath, session);
            }
        } else {
            LOGGER.info("Mandatory info is not available in the Excel in row : {}", row);
        }
        excelToNodeMapping.clear();
        LOGGER.debug("Exit processingDataFromExcel method of CreateProductImagesNodeServiceImpl");
    }

    /**
     * function to create product Image node path under record path based on product sku
     *
     * @param processProductImageBean
     * @param productSku
     * @param recordNodePath
     * @param session
     * @throws RepositoryException
     */
    private void createAndUpdateProductNodes(ProcessProductImageBean processProductImageBean, String productSku, String recordNodePath, Session session) throws RepositoryException {
        LOGGER.debug("Entry createAndUpdateProductNodes method of CreateProductImagesNodeServiceImpl");
        StringBuilder imageNodePathBuilder = new StringBuilder();
        imageNodePathBuilder.append(recordNodePath);
        imageNodePathBuilder.append(CommonConstants.SLASH);
        imageNodePathBuilder.append(productSku);
        String imageNodePath = imageNodePathBuilder.toString();
        Node resource = JcrUtils.getOrCreateByPath(imageNodePath, CommonConstants.NT_UNSTRUCTURED, Objects.requireNonNull(session));
        if (null != resource) {
            resource.setProperty(CommonConstants.IMAGE_NAME, processProductImageBean.getImageName());
            resource.setProperty(CommonConstants.MATERIAL_NUMBER, productSku);
            resource.setProperty(CommonConstants.IMAGE_TITLE, processProductImageBean.getImageTitle());
            resource.setProperty(CommonConstants.IMAGE_DESCRIPTION, processProductImageBean.getImageDescription());
            resource.setProperty(CommonConstants.IMAGE_ALT_TEXT, processProductImageBean.getImageAltText());
            resource.setProperty(CommonConstants.IMAGE_TYPE, processProductImageBean.getImageType());
            resource.setProperty(CommonConstants.IMAGE_CAPTION, processProductImageBean.getCaption());
            JcrUtils.setLastModified(Objects.requireNonNull(resource), Calendar.getInstance());
        }
        session.save();
        LOGGER.debug("Exit createAndUpdateProductNodes method of CreateProductImagesNodeServiceImpl");
    }

    /**
     * function to set Metadata from Excel to a bean file
     *
     * @param getDataFromExcel
     * @return
     */
    private ProcessProductImageBean setMetadataFromExcel(TreeMap<String, String> getDataFromExcel) {
        LOGGER.debug("Entry setMetadataFromExcel method of CreateProductImagesNodeServiceImpl");
        ProcessProductImageBean processProductImageBean = new ProcessProductImageBean();
        processProductImageBean.setImageName(getDataFromExcel.get(IMAGE_NAME).trim());
        processProductImageBean.setMaterialNumber(getDataFromExcel.get(MATERIAL_NUMBER).trim());
        processProductImageBean.setImageTitle(getDataFromExcel.get(IMAGE_TITLE).trim());
        processProductImageBean.setImageDescription(getDataFromExcel.get(IMAGE_DESCRIPTION).trim());
        processProductImageBean.setImageAltText(getDataFromExcel.get(IMAGE_ALT_TEXT).trim());
        processProductImageBean.setImageType(getDataFromExcel.get(IMAGE_TYPE).trim());
        processProductImageBean.setCaption(getDataFromExcel.get(CAPTION).trim());
        LOGGER.debug("Exit setMetadataFromExcel method of CreateProductImagesNodeServiceImpl");
        return processProductImageBean;
    }

    /**
     * function to check if mandatory info is available in Excel or not
     *
     * @param processProductImageBean
     * @return
     */
    private boolean canProcess(ProcessProductImageBean processProductImageBean) {
        if (null != processProductImageBean) {
            return StringUtils.isNotBlank(processProductImageBean.getImageName())
                    && StringUtils.isNotBlank(processProductImageBean.getMaterialNumber())
                    && StringUtils.isNotBlank(processProductImageBean.getImageType());
        }
        return false;
    }

    /**
     * function to map data from Excel
     *
     * @param row
     * @param mapIterator
     */
    private void mappingMetaDataFromExcel(Row row, int firstCellNum, int lastCellNum, Iterator<Map.Entry<String, String>> mapIterator) {
        LOGGER.debug("Entry mappingMetaDataFromExcel method of CreateProductImagesNodeServiceImpl");
        for (int c = firstCellNum; c < lastCellNum; c++) {
            Map.Entry<String, String> entry = mapIterator.next();
            Cell cell = row.getCell(c);
            if (null != cell) {
                CellType cellType = cell.getCellType();
                switch (cellType) {
                    case NUMERIC:
                        int numberValue = (int) cell.getNumericCellValue();
                        String stringCellValue = BigDecimal.valueOf(numberValue).toPlainString();
                        entry.setValue(stringCellValue);
                        break;
                    case STRING:
                        String cellValue = cell.getStringCellValue();
                        entry.setValue(cellValue);
                        break;
                    case BOOLEAN:
                        boolean booleanCellValue = cell.getBooleanCellValue();
                        String stringValue = String.valueOf(booleanCellValue);
                        entry.setValue(stringValue);
                        break;
                    default:
                        entry.setValue("");
                        break;
                }
            } else {
                entry.setValue("");
            }
        }
        LOGGER.debug("Exit mappingMetaDataFromExcel method of CreateProductImagesNodeServiceImpl");
    }

    /**
     * function to add additional path below /var/bdb/products/images/unprocessed-assets based on processing date and time.
     *
     * @param session
     * @throws RepositoryException
     */
    private String createDateAndTimeNode(Session session) throws RepositoryException {
        LOGGER.debug("Entry createDateAndTimeNode method of CreateProductImagesNodeServiceImpl");
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstants.DATE_PATTERN);
        String date = dateFormat.format(new Date());
        String time = String.valueOf(System.currentTimeMillis());

        StringBuilder dateTimeNodePathBuilder = new StringBuilder();
        dateTimeNodePathBuilder.append(UNPROCESSED_RECORDS_NODE_PATH);
        dateTimeNodePathBuilder.append(CommonConstants.SLASH);
        dateTimeNodePathBuilder.append(date);
        dateTimeNodePathBuilder.append(CommonConstants.SLASH);
        dateTimeNodePathBuilder.append(time);
        String dateTimeNodePath = dateTimeNodePathBuilder.toString();
        /*
          Date and Time Node path will be created here underneath /var/bdb/products/unprocessed-assets/images like 05-13-2024/1715856053935
          where date is in MM-DD-YYYY format and time is in milliseconds
         */
        JcrUtils.getOrCreateByPath(dateTimeNodePath, CommonConstants.SLING_FOLDER, Objects.requireNonNull(session));
        session.save();
        LOGGER.debug("Exit createDateAndTimeNode method of CreateProductImagesNodeServiceImpl");
        return dateTimeNodePath;
    }
}
