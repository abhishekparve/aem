package com.bdb.aem.core.script;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader {

	protected static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

	public static List<ExcelBean> readFromExcel(InputStream inputStream) {
		List<ExcelBean> beanList = new ArrayList<>();
		try {
//			File file = new File(fileName);
//			FileInputStream inputStream = new FileInputStream(file);
//			logger.info(file.getName());

			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				XSSFCell cellValue1 = row.getCell(0);
				XSSFCell cellValue2 = row.getCell(1);
				XSSFCell cellValue3 = row.getCell(2);
				String path = null != cellValue1 ? cellValue1.getStringCellValue().trim() : "";
				String compModified = null != cellValue2 ? cellValue2.getStringCellValue().trim() : "";
				String compNotModified = null != cellValue3 ? cellValue3.getStringCellValue().trim() : "";
				ExcelBean bean = new ExcelBean(path.trim(), compModified, compNotModified);
				beanList.add(bean);
			}

			workbook.close();

		} 
		catch (IOException ex) {
			logger.error("IOException Caught " + ex);
		}
		return beanList;
	}

	/**
	 * This method is to update the page properties in jcr:content
	 * 
	 * @param fileName
	 * @return
	 */
	public static List<PathPagePropertiesBean> readPathPagePropertiesFromExcel(InputStream inputStream) {

		List<PathPagePropertiesBean> beanList = new ArrayList<>();
		try {
//			File file = new File(fileName);
//			FileInputStream inputStream = new FileInputStream(file);
//			System.out.println(file.getName());

			Map<String, String> map = new LinkedHashMap<>();
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(1);

			// Read the excel and form the map
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				XSSFCell cellValue1 = row.getCell(0);
				XSSFCell cellValue2 = row.getCell(1);
				String path = null != cellValue1 ? cellValue1.getStringCellValue().trim() : "";
				String pageProperties = null != cellValue2 ? cellValue2.getStringCellValue().trim() : "";

				if (map.containsKey(path)) {
					String values = pageProperties;
					values = map.get(path) + "," + values;
					map.put(path, values);
				} else {
					map.put(path, pageProperties);
				}

			}
			workbook.close();
			// Iterating the map and converting it to POJO
			for (Map.Entry<String, String> entry : map.entrySet()) {
				PathPagePropertiesBean bean = null;
				String[] pagePropsArr = entry.getValue().split(",");
				List<String> properties = new ArrayList<>();
				if (null != pagePropsArr && pagePropsArr.length > 0) {
					for (int j = 0; j < pagePropsArr.length; j++) {
						properties.add(pagePropsArr[j].trim());
					}
					bean = new PathPagePropertiesBean(entry.getKey().trim(), properties);
					beanList.add(bean);
				}
			}
			logger.info("Bean size", beanList.size());

		} 
		catch (IOException ex) {
			logger.error("IOException Caught " + ex);
		}
		return beanList;
	}
}
