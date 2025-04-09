package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class UploadCertificationFileTypeModel.
 */
@Model( adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UploadCertificationFileTypeModel {
	
	/** The id. */
	@Inject
	@SerializedName("id")
	private String id;
	
	/** The label. */
	@Inject
	@SerializedName("label")
	private String label;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
}
