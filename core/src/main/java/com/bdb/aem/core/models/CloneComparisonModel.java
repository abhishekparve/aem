package com.bdb.aem.core.models;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.bdb.aem.core.services.tools.SlingModelService;
import com.bdb.aem.core.services.tools.impl.CloneComparisonServiceImpl;

/**
 * The Clone Comparison Model.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CloneComparisonModel extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = 5622457377908244373L;

	@ChildResource
	private CloneComparisonAntigenModel antigens;
	
	@ChildResource
	private CloneComparisonCloneModel clones;
	
	private Map<String, String> configs = new HashMap<>();

	@OSGiService
	private transient CloneComparisonServiceImpl cloneComparisonService;

	/**
	 * Gets the antigens.
	 * 
	 * @return the antigens
	 */
	public CloneComparisonAntigenModel getAntigens() {
		return antigens;
	}
	
	/**
	 * Gets the clones.
	 * 
	 * @return the clones
	 */
	public CloneComparisonCloneModel getClones() {
		return clones;
	}
	
	/**
	 * @see BaseSlingModel#getDataService()
	 */
	@Override
	public SlingModelService getDataService() {
		return cloneComparisonService;
	}

	/**
	 * Gets the configs.
	 * 
	 * @return the configs
	 */
	public Map<String, String> getConfigs() {
		return configs;
	}

	/**
	 * Sets the configs.
	 * 
	 * @param configs
	 */
	public void setConfigs(Map<String, String> configs) {
		this.configs = configs;
	}

}
