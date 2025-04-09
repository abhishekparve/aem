package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;


/**
 * The Class AccordionListModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccordionListModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(AccordionListModel.class);

	/** The section heading. */
	@Inject
	
	/**
	 * Gets the section heading.
	 *
	 * @return the section heading
	 */
	@Getter
	public String sectionHeading;
	
	/** The section description. */
	@Inject
	
	/**
	 * Gets the section description.
	 *
	 * @return the section description
	 */
	@Getter
	public String sectionDescription;

}
