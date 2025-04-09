package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

/**
 * The Class CreditCardDetailModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CreditCardDetailModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(CreditCardDetailModel.class);

	@Inject
	@Getter
	public String id;

	@Inject
	@Getter
	public String icon;

	@Inject
	@Getter
	public String iconAltText;

	@Inject
	@Getter
	public String length;

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("DownloadListModel - init() - start");
	}

}
