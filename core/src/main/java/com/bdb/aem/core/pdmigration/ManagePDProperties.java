package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;
import com.adobe.acs.commons.mcp.form.CheckboxComponent;
import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.form.PathfieldComponent;
import com.adobe.acs.commons.mcp.model.ManagedProcess;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.google.gson.JsonObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

public class ManagePDProperties extends ProcessDefinition {
    public static final String EXCEL_SHEET_NAME = "excelSheetName";
    private static final Logger logger = LoggerFactory.getLogger(ManagePDProperties.class);
    List<String> properties = new ArrayList<>();
    List<JsonObject> excelObject;
    String nodePath;
    ManagedProcess processInfo;

    private final Replicator replicator;

    private HashMap<String, String> excelConfigurationMap;
    @FormField(name = "Input XLSX File",
            component = PathfieldComponent.AssetSelectComponent.class,
            required = true)
    private String fileName;

    @FormField(name = "File Config", required = true ,
            description = "Input format has to be like : <sheetName>:path=<jcrNodes path column>:props=<properties columns list comma separated> . eg : sheet1:path=A:props=B,C,D")
    private String excelConfig;

    @FormField(name = "Metadata path", required = true, description = "The actual metadata node that needs to be modified. eg./jcr:content/metadata ")
    private String metadataPath;

    @FormField(name = "Activate Resource", component = CheckboxComponent.class, options = {"horizontal", "default=false"})
    private boolean activationNeeded;



    public ManagePDProperties(Replicator replicator) {
        this.replicator = replicator;
    }

    @Override
    public void buildProcess(ProcessInstance instance, ResourceResolver rr) throws LoginException {

        logger.info("Inside buildProcess Method");
        processInfo = instance.getInfo();
        instance.getInfo().setDescription("Running PD Properties Update MCP");
        instance.defineCriticalAction("Update Product Data Properties", rr, this::initiateProcess);

    }

    @Override
    public void storeReport(ProcessInstance instance, ResourceResolver rr)  {
       // Not implementing this as this method has a known bug in the current ACS commons version.
    }

    @Override
    public void init() throws RepositoryException {
        setExcelConfiguration();
    }

    protected void initiateProcess(ActionManager manager) {
        manager.deferredWithResolver(this::triggerUpdate);
    }

    protected void setExcelConfiguration() {
        excelConfigurationMap = new HashMap<>();
        if (excelConfig != null && excelConfig.length() > 0 && excelConfig.contains(":")) {
            String[] excelConfigurationArray = excelConfig.split(":");
            excelConfigurationMap.put(EXCEL_SHEET_NAME, excelConfigurationArray[0]);
            for (String configElement : excelConfigurationArray) {
                if (configElement.contains("=")) {
                    String[] configEntries = configElement.split("=");
                    excelConfigurationMap.put(configEntries[0], configEntries[1]);
                }
            }
        }

    }

    protected void triggerUpdate(ResourceResolver rr) throws ReplicationException {

        readExcelFile(rr, fileName);
        performUpdate(rr);

    }

    private void performUpdate(ResourceResolver res) throws ReplicationException {

        for (JsonObject obj : excelObject) {
            Resource resource = res.getResource(obj.get(nodePath).toString()
                    .replace("[()]", "").replace("\"", "")+metadataPath);
            if (resource != null) {
                ModifiableValueMap propertiesMap = resource.adaptTo(ModifiableValueMap.class);
                if (propertiesMap != null) {
                    updateJcrProperties(obj, propertiesMap);
                }

                if(activationNeeded){
                    Session session = res.adaptTo(Session.class);
                    replicator.replicate(session, ReplicationActionType.ACTIVATE, obj.get(nodePath).toString()
                            .replace("[()]", "").replace("\"", ""));

                }


            }
        }

    }

    private void updateJcrProperties(JsonObject obj, ModifiableValueMap propertiesMap) {
        Set<String> set = obj.keySet();
        for (String propKey : set) {
            if (!propKey.equalsIgnoreCase(nodePath))
                propertiesMap.put(propKey, obj.get(propKey).toString().replace("\"", ""));


        }
    }

    private void readExcelFile(ResourceResolver res, String hpFileName) {
        Asset asset;
        Resource resource = res.getResource(hpFileName);
        if (resource != null) {
             asset = resource.adaptTo(Asset.class);
            if (asset != null) {
                Rendition rendition = asset.getOriginal();
                InputStream inputStream = rendition.adaptTo(InputStream.class);
                excelObject = createJSONAndTextFileFromExcel(inputStream, asset.getMimeType());
            }
        }
    }

    List<JsonObject> createJSONAndTextFileFromExcel(InputStream inputStream, String mimeType) {
        List<JsonObject> list = new ArrayList<>();
        try {
            Workbook excelWorkBook;
            if (mimeType.contains("application/vnd.ms-excel")) {
                excelWorkBook = new HSSFWorkbook(inputStream);
            } else {
                excelWorkBook = new XSSFWorkbook(inputStream);
            }

            // Get all Excel sheet count.
            int totalSheetNumber = excelWorkBook.getNumberOfSheets();

            // Loop in all excel sheet.
            for (int i = 0; i < totalSheetNumber; i++) {
                // Get current sheet.
                Sheet sheet = excelWorkBook.getSheetAt(i);

                // Get sheet name.
                String sheetName = sheet.getSheetName();

                if (sheetName != null && sheetName.length() > 0 && sheetName.equalsIgnoreCase(excelConfigurationMap.get(EXCEL_SHEET_NAME))
                ) {
                    // Get current sheet data in a list table.
                    List<List<String>> sheetDataTable = getSheetDataList(sheet);

                    // Generate JSON format of above sheet data and write to a JSON file.
                    list = getJsonObjectFromList(sheetDataTable);

                }
            }
            // Close excel work book object.
            excelWorkBook.close();

        } catch (IOException ex) {
            logger.error("IOException error occurred", ex);
        }
        return list;
    }

    private static List<List<String>> getSheetDataList(Sheet sheet) {
        List<List<String>> ret = new ArrayList<>();

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

                List<String> rowDataList = new ArrayList<>();

                iterateRowCells(lastCellNum, firstCellNum, row, rowDataList);

                // Add current row data list in the return list.
                ret.add(rowDataList);
            }
        }
        return ret;
    }

    private static void iterateRowCells(int lastCellNum, int firstCellNum, Row row, List<String> rowDataList) {
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
    }

    private static int getColumnIndex(String character) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return (alphabet.indexOf(character));
    }

    private List<JsonObject> getJsonObjectFromList(List<List<String>> dataTable) {
        List<JsonObject> list = new ArrayList<>();
        if (dataTable != null) {
            int rowCount = dataTable.size();

            if (rowCount > 1) {

                // The first row is the header row, store each column name.
                List<String> headerRow = dataTable.get(0);
                nodePath = headerRow.get(getColumnIndex(excelConfigurationMap.get("path")));
                int columnCount = headerRow.size();
                String[] columnsFromExcelConfig = excelConfigurationMap.get("props").split(",");
                for (String item : columnsFromExcelConfig) {
                    properties.add(headerRow.get(getColumnIndex(item)));
                }

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

                    list.add(rowJsonObject);
                }

            }
        }
        return list;
    }

}