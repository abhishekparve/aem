package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class QualifyingProductsDetailsModel.
 */
@Model(
		adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class QualifyingProductsDetailsModel {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(QualifyingProductsDetailsModel.class);

	/** The sku tag. */
	@Inject
	public String skuTag;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		LOG.debug("Inside QualifyingProductsDetailsModel Init");
	}

	/**
	 * Gets the sku tag.
	 *
	 * @return the sku tag
	 */
	public String getSkuTag() {
		return skuTag;
	}
}