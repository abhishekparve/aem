package com.bdb.aem.core.models;

import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.wcm.api.Page;
import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The Class DownloadListModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DownloadListDocumentModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(DownloadListDocumentModel.class);

	@Inject
	@Getter
	public String uploadTypeSelection;

	@Inject
	@Getter
	public String productName;

	@Inject
	@Getter
	public Resource productTypeSelection;
	
	@Getter
	public List<String> productTypeSelectionList;

	@Inject
	@Getter
	public String documentTitle;

	@Inject
	@Getter
	public String documentLink;
	
	@Inject
	@Getter
	public String downloadIcon;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	Page currentPage;

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("DownloadListModel - init() - start");
		if( CommonConstants.AUTO.equalsIgnoreCase(uploadTypeSelection)) {
			productTypeSelectionList = new ArrayList<String>();
			String[] selectiontypes = productTypeSelection.adaptTo(String[].class);
			List<String> list = Arrays.asList(selectiontypes);
			productTypeSelectionList.addAll(list);
		}

	}

}
