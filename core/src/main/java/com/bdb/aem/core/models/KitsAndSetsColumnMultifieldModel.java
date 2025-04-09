package com.bdb.aem.core.models;


import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
 
/**
 * The Class KitsAndSetsColumnMultifieldModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)	
public class KitsAndSetsColumnMultifieldModel {		


	/** The column data. */
	@Inject
	@Default(values=StringUtils.EMPTY)
    private String columnData;

	/**
	 * Gets the column data.
	 *
	 * @return the column data
	 */
	public String getColumnData() {
		return columnData;
	}
    
}
