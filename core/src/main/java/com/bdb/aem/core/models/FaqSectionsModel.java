package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The class faq Sections Model
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FaqSectionsModel {

	/** The section title */
	@Inject
	@JsonProperty("label")
	@Default(values = StringUtils.EMPTY)
	private String sectionTitle;
	
	/** The section unique name. */
	@Inject
	@JsonProperty("sectionLink")
	@Default(values = StringUtils.EMPTY)
	private String sectionUniqueName;

	/** The faq List */
	@Inject
	private List<FaqListModel> questionsMap;

	/**
	 * Gets the faq List.
	 * 
	 * @return the faq list
	 */
	public List<FaqListModel> getQuestionsMap() {
		return Optional.ofNullable(questionsMap).filter(f -> !f.isEmpty()).orElseGet(ArrayList::new);
	}

	/**
	 * Gets the faq section title.
	 * 
	 * @return the faq section title
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}

	/**
	 * Gets the section unique name.
	 *
	 * @return the section unique name
	 */
	public String getSectionUniqueName() {
		return sectionUniqueName;
	}
}
