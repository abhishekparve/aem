package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * The class Contact Card Modes Model.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactCardModesModel {

	/** The mode title */
	@Inject
	private String modeTitle;

	/** The mode description */
	@Inject
	private String modeDescription;

	/**
	 * Gets the mode title.
	 * 
	 * @return the mode title
	 */
	public String getModeTitle() {
		return modeTitle;
	}

	/**
	 * Gets the mode description
	 * 
	 * @return the mode description
	 */
	public String getModeDescription() {
		return modeDescription;
	}

}
