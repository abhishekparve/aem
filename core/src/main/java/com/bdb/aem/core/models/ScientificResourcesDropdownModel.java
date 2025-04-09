package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.SerializedName;

/**
 * The Class ScientificResourcesDropdownModel.
 */
@Model(	adaptables = Resource.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ScientificResourcesDropdownModel {
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(ScientificResourcesDropdownModel.class);
	
	/** The label. */
	@Inject
	@SerializedName("label")
    private String label;
	
	/** The value. */
	@Inject
	@SerializedName("value")
    private int value;

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
