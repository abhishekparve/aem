package com.bdb.aem.core.models;

import java.util.Optional;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Fluorochromes Model.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FluorochromesModel extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = -3592217006939256286L;

	@ValueMapValue
	private String fname;

	@ValueMapValue
	private String data;

	@ValueMapValue
	private String reagent;

	@ValueMapValue
	private String notSuggested;
	
	@ValueMapValue
	private String tableValue;

	@JsonIgnore
	public String getTableValue() {
		return tableValue;
	}


	/**
	 * Gets the fname.
	 * 
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}


	/**
	 * Gets the data.
	 * 
	 * @return the data
	 */
	public boolean getData() {
		return Optional.ofNullable(data).map(d -> Boolean.valueOf(d)).orElse(false);
	}

	/**
	 * Gets the reagent.
	 * 
	 * @return the reagent
	 */
	public boolean getReagent() {
		return Optional.ofNullable(reagent).map(r -> Boolean.valueOf(r)).orElse(false);
	}
	
	/**
	 * Gets the notSuggested.
	 * 
	 * @return the notSuggested
	 */
	public boolean getNotSuggested() {
		return Optional.ofNullable(notSuggested).map(n -> Boolean.valueOf(n)).orElse(false);
	}

}
