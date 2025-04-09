package com.bdb.aem.core.models;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
 

/**
 * The Class KitsAndSetsRowMultifieldModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class KitsAndSetsRowMultifieldModel {		
    
    /** The row column multifield. */
    @Inject
	private List<KitsAndSetsColumnMultifieldModel> rowColumnMultifield;

	/**
	 * Gets the row column multifield.
	 *
	 * @return the row column multifield
	 */
	public List<KitsAndSetsColumnMultifieldModel> getRowColumnMultifield() {
		return new ArrayList<>(rowColumnMultifield);
	}
    	
}
