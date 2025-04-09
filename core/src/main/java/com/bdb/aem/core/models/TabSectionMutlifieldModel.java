package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import lombok.Getter;
import lombok.ToString;

/**
 * The Class TabSectionMutlifieldModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@ToString(exclude = { "resolverFactory", "resourceResolver", "logger" })
public class TabSectionMutlifieldModel {

	/** The logger. */
	protected Logger logger = LoggerFactory.getLogger(TabSectionMutlifieldModel.class);

	/** The Tab Heading. */
	@Inject
	@Getter
	private String tabLabel;
	
	/** The tab unique name. */
	@Inject
	private String tabUniqueName;
	
	/** The url unique name. */
	@Inject
	private String uniqueUrlName;

	/** The Tab title. */
	@Inject
	@Getter
	private String tabTitle;

	/** The tab Desc. */
	@Inject
	@Getter
	private String tabDescription;

	/** The image path. */
	@Inject
	@Getter
	private String image;

	/** The image caption. */
	@Inject
	@Getter
	private String imageTitle;

	/**
	 * Gets tabUniqueName as a String.
	 *
	 * @return the tabUniqueName
	 */
	public String getTabUniqueName() {
		return tabUniqueName.replaceAll("\\s", "");
	}
	
		public String getUniqueUrlName() {
		return (uniqueUrlName != null && !uniqueUrlName.isEmpty()) ? uniqueUrlName : tabUniqueName;
		
	}
	
}
