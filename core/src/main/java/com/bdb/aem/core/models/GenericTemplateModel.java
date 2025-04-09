package com.bdb.aem.core.models;

import static com.bdb.aem.core.util.CommonConstants.FALSE;
import static com.bdb.aem.core.util.CommonConstants.IS_PRINT_PDF;
import static com.bdb.aem.core.util.CommonConstants.TRUE;
import static org.apache.commons.lang3.BooleanUtils.toBooleanObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

/**
 * Model to get printPDF
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GenericTemplateModel {
	protected static final Logger logger = LoggerFactory.getLogger(GenericTemplateModel.class);

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** isPrintPDF boolean value */
	@Getter
	private boolean printPDF;

	/**
	 * Gives boolean value for isPrint
	 * 
	 * @throws LoginException
	 */
	@PostConstruct
	protected void init() throws LoginException {
		logger.debug("GenericTemplateModel - init() - start");

		if (null != request.getRequestParameter(IS_PRINT_PDF)) {
			printPDF = toBooleanObject(request.getRequestParameter(IS_PRINT_PDF).toString(), TRUE, FALSE, FALSE);
			logger.debug("isPrintPDF - {}", printPDF);
		}
		logger.debug("GenericTemplateModel - init() - end");
	}

}
