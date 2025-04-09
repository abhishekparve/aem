package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

/**
 * The Class ScientificResourceNameCodeModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@ToString(exclude = { "logger" })
public class ScientificResourceNameCodeModel {

	/** The logger. */
	protected Logger logger = LoggerFactory.getLogger(ScientificResourceNameCodeModel.class);
	
	/** The resource name. */
	@Inject
	@Getter
	@SerializedName("name")
	private String resourceName;

	/** The resource code. */
	@Inject
	@Getter
	@SerializedName("code")
	private String resourceCode;
}
