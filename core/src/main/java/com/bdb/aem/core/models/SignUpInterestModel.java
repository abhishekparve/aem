package com.bdb.aem.core.models;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.models.annotations.Model;

import com.google.gson.annotations.SerializedName;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

/**
 * The Class SignUpInterestModel.
 */
@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class SignUpInterestModel {
	
	/** The label. */
	@Inject
	@Named("interestLabel")
	@SerializedName("label")
	private String label;
	
	/** The code. */
	@Inject
	@Named("interestCode")
	@SerializedName("code")
	private String code;
	
	/**
	 * Gets the Interest label field.
	 *
	 * @return - Interest Label as a String
	 */
	public String getInterestLabel() {
		return label;
	}
	
	/**
	 * Gets the Interest code field.
	 *
	 * @return - Interest Code as a String
	 */
	public String getInterestCode() {
		return code;
	}
}