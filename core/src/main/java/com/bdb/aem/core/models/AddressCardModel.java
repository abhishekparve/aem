package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * The Address Card Model.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AddressCardModel {

	/** The background color */
	@ValueMapValue
	private String bgColor;

	/** The sections */
	@ChildResource
	private List<AddressCardSectionsModel> sections;

	/**
	 * Gets the background color.
	 * 
	 * @return the background color
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * Gets the sections.
	 * 
	 * @return the sections
	 */
	public List<AddressCardSectionsModel> getSections() {
		return Optional.ofNullable(sections).filter(s -> !s.isEmpty()).orElseGet(ArrayList::new);
	}

}
