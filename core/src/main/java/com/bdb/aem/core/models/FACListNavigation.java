package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.bdb.aem.core.util.CommonConstants;

/**
 * The FAC List Navigation Model.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FACListNavigation extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = 912921492723342302L;

	@ValueMapValue
	private String label;

	@ValueMapValue
	private String path;
	
	@ValueMapValue
	private String target;
	
	@ChildResource
	private List<FacListSubNavModel> subNavigation;

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return Optional.ofNullable(label).orElse(StringUtils.EMPTY);
	}
	
	/**
	 * Gets the target.
	 * 
	 * @return the target
	 */
	public String getTarget() {
		return  Optional.ofNullable(target).orElse(StringUtils.EMPTY);
	}
	
	/**
	 * Gets the sub navigation.
	 * 
	 * @return the sub navigation
	 */
	public List<FacListSubNavModel> getSubNavigation(){
		return Optional.ofNullable(subNavigation).orElseGet(ArrayList:: new);
	}

	/**
	 * Gets the Path.
	 * 
	 * @return the path
	 */
	public String getPath() {
		return Optional.ofNullable(path).filter(StringUtils::isNotEmpty)
				.map(p -> p.contains(CommonConstants.HASH)
						? externalizer.getFormattedUrl(p.split(CommonConstants.HASH)[0], getResolver()).concat(CommonConstants.HASH).concat(p.split(CommonConstants.HASH)[1])
						: externalizer.getFormattedUrl(p, getResolver()))
				.orElse(StringUtils.EMPTY);

	}
}
