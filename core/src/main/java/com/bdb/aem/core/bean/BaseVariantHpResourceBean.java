package com.bdb.aem.core.bean;

import org.apache.sling.api.resource.Resource;

/**
 * The Class BaseVariantHpResourceBean.
 */
public class BaseVariantHpResourceBean {
	
	/** The baseHpResource. */
	private Resource baseHpResource;
	
	/** The company label. */
	private Resource variantHpResource;

	public Resource getBaseHpResource() {
		return baseHpResource;
	}

	public void setBaseHpResource(Resource baseHpResource) {
		this.baseHpResource = baseHpResource;
	}

	public Resource getVariantHpResource() {
		return variantHpResource;
	}

	public void setVariantHpResource(Resource variantHpResource) {
		this.variantHpResource = variantHpResource;
	}
	
	
}
