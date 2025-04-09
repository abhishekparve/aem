package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GlobalHeaderCountrySelectorModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GlobalHeaderCountrySelectorModel {
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(GlobalHeaderCountrySelectorModel.class);

	/** The regionmultifield. */
	@Inject
	@Via("resource")
	private List<RegionMultifieldModel> regionmultifield;

	/**
	 * Gets the regionmultifield.
	 *
	 * @return the regionmultifield
	 */
	public List<RegionMultifieldModel> getRegionmultifield() {
		if(null!=regionmultifield)
			return new ArrayList<>(regionmultifield);
		else
    		return new ArrayList<>();
	}
	
}