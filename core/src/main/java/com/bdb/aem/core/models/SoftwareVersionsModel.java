package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Software Versions Model.
 *
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SoftwareVersionsModel {
	
	/**
	 * The logger.
	 */
	protected Logger logger = LoggerFactory.getLogger(SoftwareVersionsModel.class);

	/** The versionName */
	@Inject
	private String versionName;
	
	/** The versionLink */
	@Inject
	private String versionLink;

	/**
	 * @return the versionName
	 */
	public String getVersionName() {
		return versionName;
	}

	/**
	 * @return the versionLink
	 */
	public String getVersionLink() {
		return versionLink;
	}

}
