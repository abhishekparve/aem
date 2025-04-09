package com.bdb.aem.core.services.tools.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;

import com.bdb.aem.core.models.FACConjugateRows;
import com.bdb.aem.core.models.FACSelectConjugateModel;

/**
 * The FAC Reports Service Impl.
 * 
 * @author ronbanerjee
 *
 */
@Component(service = FACSelectConjugateServiceImpl.class)
public class FACSelectConjugateServiceImpl extends AbstractSlingModelService<FACSelectConjugateModel> {

	/**
	 * @see AbstractSlingModelService#updateSlingModel(com.bdb.aem.core.models.BaseSlingModel)
	 */
	@Override
	protected void updateSlingModel(FACSelectConjugateModel slingModel) {
		Map<String, Serializable> statisticsImageTable = new HashMap<>();
		Optional.ofNullable(slingModel).ifPresent(s -> {

			ArrayList<Map<String, String>> tableHeadings = new ArrayList<>();
			if(s.getConjugateRows() != null && s.getConjugateRows().size()>0) {
			s.getConjugateRows().get(0).getColumns().forEach(col -> {
				Map<String, String> tableHeading = new HashMap<>();
				tableHeading.put("thText", col.getColHeading());
				tableHeadings.add(tableHeading);
			});

			ArrayList<Map<String, Object>> tbodyDataItems = new ArrayList<>();

			s.getConjugateRows().forEach(row -> {
				tbodyDataItems.add(addTableBody(row));
			});

			statisticsImageTable.put("tableHeadings", tableHeadings);
			statisticsImageTable.put("tbodyData", tbodyDataItems);
			s.setStatisticsImageTable(statisticsImageTable);
			}});

	}

	/**
	 * Adds the Table Body.
	 * 
	 * @param row the row
	 * @return the map
	 */
	private Map<String, Object> addTableBody(FACConjugateRows row) {
		Map<String, Object> tbodyDataItem = new HashMap<>();
		List<Map<String, String>> trDataItems = new ArrayList<>();
		addFirstData(row, trDataItems);
		row.getColumns().forEach(column -> {
			Map<String, String> trDataItem = new HashMap<>();

			trDataItem.put("tdData", "");
			trDataItem.put("image", column.getImage() == null? "": column.getImage());
			trDataItem.put("imageLinkUrl", column.getImageLinkUrl() == null? "": column.getImageLinkUrl());
			trDataItem.put("openNewImageLinkTab", column.getOpenNewImageLinkTab() == null? "": column.getOpenNewImageLinkTab());
			trDataItem.put("readingData", column.getTableData() == null ?"":column.getTableData());
			trDataItems.add(trDataItem);
		});
		tbodyDataItem.put("trData", trDataItems);
		return tbodyDataItem;
	}

	/**
	 * Add the first item.
	 * 
	 * @param row the row
	 * @param trDataItems the table row items
	 */
	private void addFirstData(FACConjugateRows row, List<Map<String, String>> trDataItems) {
		Map<String, String> trDataItem = new HashMap<>();
		trDataItem.put("tdData", row.getRowHeading());
		trDataItem.put("image", "");
		trDataItem.put("imageLinkUrl", "");
		trDataItem.put("openNewImageLinkTab", "");
		trDataItems.add(trDataItem);
	}

}
