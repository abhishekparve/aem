package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FlurochromeModel extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = -6659167988191218296L;

	@Inject
	public String color;
	
	@Inject
	public String label;
	
	
	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Gets the color.
	 * 
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
}
