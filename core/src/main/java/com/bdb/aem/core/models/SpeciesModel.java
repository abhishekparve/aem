package com.bdb.aem.core.models;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * The Species Model.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SpeciesModel extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = 5556784450953736194L;

	@ValueMapValue
	private String speciesLabel;
	
	@ValueMapValue
	private String data;
	
	@ValueMapValue
	private String reagent;
	
	@ValueMapValue
	private String notSuggested;
	
	/**
	 * Gets the species label.
	 * 
	 * @return the species label
	 */
	public String getSpeciesLabel() {
		return Optional.ofNullable(speciesLabel).orElse(StringUtils.EMPTY);
	}
	
	/**
	 * Gets the data flag.
	 * 
	 * @return the data flag
	 */
	public boolean getData() {
		return Optional.ofNullable(data).filter(d -> StringUtils.isNotEmpty(d)).map(dt -> Boolean.valueOf(dt)).orElse(true);
	}
	
	/**
	 * Gets the reagent flag.
	 * 
	 * @return the reagent flag
	 */
	public boolean getReagent() {
		return Optional.ofNullable(reagent).filter(r -> StringUtils.isNotEmpty(r)).map(rg -> Boolean.valueOf(rg)).orElse(false);
	}
	
	/**
	 * Gets the notSuggested flag.
	 * 
	 * @return the notSuggested flag
	 */
	public boolean getNotSuggested() {
		return Optional.ofNullable(notSuggested).filter(n -> StringUtils.isNotEmpty(n)).map(ns -> Boolean.valueOf(ns)).orElse(false);
	}

}
