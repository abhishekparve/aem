package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;


/**
 * The Class GlobalFooterCategoryModel.
 */
@Model(
		adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class GlobalFooterCategoryModel {

	/** The title. */
	@Inject
	public String title;

	/** The subcategories. */
	@Inject
	public List<GlobalFooterSubcategoryModel> subcategories; //the multifield name="./subcategories"

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		if(StringUtils.isNotEmpty(title)) {
			return title;
		}
		else {
			return StringUtils.EMPTY;
		}
		
	}

	/**
	 * Gets the subcategories.
	 *
	 * @return the subcategories
	 */
	public List<GlobalFooterSubcategoryModel> getSubcategories() {
		return new ArrayList<>(subcategories);
	}
}