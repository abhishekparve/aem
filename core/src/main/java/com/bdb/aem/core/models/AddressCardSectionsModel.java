package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * The Address Card Sections Model.
 *
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AddressCardSectionsModel {

	/** The section title */
	@Inject
	private String sectionTitle;

	/** The sub sections */
	@Inject
	private List<AddressCardSubSectionsModel> subSections;

	/**
	 * Gets the section title.
	 * 
	 * @return the section title.
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}

	/**
	 * Gets the sub sections.
	 * 
	 * @return the sub sections.
	 */
	public List<AddressCardSubSectionsModel> getSubSections() {
		return Optional.ofNullable(subSections).filter(s -> !s.isEmpty()).orElseGet(ArrayList::new);
	}
}
