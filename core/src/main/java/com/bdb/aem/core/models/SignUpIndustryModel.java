package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class SignUpIndustryModel.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class SignUpIndustryModel {
	
	/** The label. */
	@Inject
	@Named("industryLabel")
	@SerializedName("label")
	private String label;

	/** The code. */
	@Inject
	@Named("industryCode")
	@SerializedName("code")
	private String code;

	/** The interest multifield. */
	@Inject
	private List<Resource> interestMultifield;
	
	/** The role multifield. */
	@Inject
	private List<Resource> roleMultifield;

	/** The role list. */
	@SerializedName("roleList")
	private List<SignUpRoleModel> roleList = new ArrayList<>();

	/** The interest list. */
	@SerializedName("interestList")
	private List<SignUpInterestModel> interestList = new ArrayList<>();

	/**
	 * Populates the Role and Interest Lists.
	 */
	@PostConstruct
	protected void init() {
		if (roleMultifield != null && !roleMultifield.isEmpty()) {

			for (Resource resource : roleMultifield) {
				SignUpRoleModel role = resource.adaptTo(SignUpRoleModel.class);
				roleList.add(role);
			}
		}
		if (interestMultifield != null && !interestMultifield.isEmpty()) {

			for (Resource resource : interestMultifield) {
				SignUpInterestModel interest = resource.adaptTo(SignUpInterestModel.class);
				interestList.add(interest);
			}
		}
	}

	/**
	 * Gets the industry label field.
	 *
	 * @return - Industry Label as a String
	 */
	public String getIndustryLabel() {
		return label;
	}

	/**
	 * Gets the industry code field.
	 *
	 * @return - Industry Code as a String
	 */
	public String getIndustryCode() {
		return code;
	}

	/**
	 * Gets the role list.
	 *
	 * @return - Role List as a List of Roles
	 */
	public List<SignUpRoleModel> getRoleList() {
		return new ArrayList<>(roleList);
	}
	
	/**
	 * Gets the interest list.
	 *
	 * @return - Interest List as a List of Interests
	 */
	public List<SignUpInterestModel> getInterestList() {
		return new ArrayList<>(interestList);
	}
}