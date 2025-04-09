package com.bdb.aem.core.pdmigration;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.google.gson.JsonObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MigrationUtils {
    private static final Logger logger = LoggerFactory.getLogger(MigrationUtils.class);

    public JsonObject creteJSONAndTextFileFromExcel(InputStream inputStream, String mimeType) {
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

}
