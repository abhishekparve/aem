package com.bdb.aem.core.models;

import org.apache.commons.collections.CollectionUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class PerformanceDetailsModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PerformanceDetailsModel {

	/** The sub title. */
	@Inject
	@Named("subTitle")
	@Via("resource")
	private String subTitle;

	/** The nested details. */
	private List<PerformanceInnerModel> nestedDetails;

	/**
	 * Gets the nested details.
	 *
	 * @return the nested details
	 */
	public List<PerformanceInnerModel> getNestedDetails() {
		if(CollectionUtils.isNotEmpty(nestedDetails)) {
			return new ArrayList<>(nestedDetails);
		}
		else {
			return new ArrayList<>();
		}
	}

	/**
	 * Sets the nested details.
	 *
	 * @param nestedDetails the new nested details
	 */
	public void setNestedDetails(List<PerformanceInnerModel> nestedDetails) {
		this.nestedDetails = Collections.unmodifiableList(nestedDetails);
	}

	/**
	 * Gets the sub title.
	 *
	 * @return the sub title
	 */
	public String getSubTitle() {
		return subTitle;
	}
}