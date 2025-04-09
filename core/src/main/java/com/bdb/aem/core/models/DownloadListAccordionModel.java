package com.bdb.aem.core.models;

import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;


/**
 * The Class DownloadListModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DownloadListAccordionModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(DownloadListAccordionModel.class);

	@Inject
	@Getter
	public String accordionTitle;

	@Inject
	@Getter
	public List<DownloadListDocumentModel> documents;

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
	}

}
