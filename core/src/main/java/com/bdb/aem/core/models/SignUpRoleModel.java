package com.bdb.aem.core.models;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.models.annotations.Model;

import com.google.gson.annotations.SerializedName;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

/**
 * The Class SignUpRoleModel.
 */
@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class SignUpRoleModel {
	
	/** The label. */
	@Inject
	@Named("roleLabel")
	@SerializedName("label")
	private String label;
	
	/** The code. */
	@Inject
	@Named("roleCode")
	@SerializedName("code")
	private String code;
	
	/**
	 * Gets the Role label field
	 * 
	 * @return - Role Label as a String
	 */
	public String getRoleLabel() {
		return label;
	}
	
	/**
	 * Gets the Role code field 
	 * 
	 * @return - Role Code as a String
	 */
	public String getRoleCode() {
		return code;
	}
}