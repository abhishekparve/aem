package com.bdb.aem.core.services;

import java.util.List;

import com.bdb.aem.core.bean.AssetBean;

public interface ReportGenerationService {

	public void fetchReportData();
	
	public List<AssetBean> fetchTestReportData();
}
