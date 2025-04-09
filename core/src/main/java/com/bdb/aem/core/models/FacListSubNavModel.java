package com.bdb.aem.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


/**
 * The FAC List Sub Navigation Model.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FacListSubNavModel extends BaseSlingModel {

	/** The constant Serial version UUID */
	private static final long serialVersionUID = 7499017944042262628L;

	@ValueMapValue
	private String label;

	@ValueMapValue
	private String path;

	@ValueMapValue
	private String iconUrl;

	@ValueMapValue
	private String description;
	
	
	/**
	 * Gets the label
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Gets the path
	 * 
	 * @return the path
	 */
	public String getPath() {	
		if(StringUtils.isNotEmpty(path)) {
			path = externalizer.getFormattedUrl(path, getResolver());
		}	
		return path;
	}

	/**
	 * Gets the icon url
	 * 
	 * @return the icon url
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * Gets the description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
