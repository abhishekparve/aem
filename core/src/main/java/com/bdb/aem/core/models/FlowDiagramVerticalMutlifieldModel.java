package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.ToString;

/**
 * The Class FlowDiagramVerticalMutlifieldModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@ToString(exclude = { "resolverFactory", "resourceResolver", "logger" })
public class FlowDiagramVerticalMutlifieldModel {

	/** The logger. */
	protected Logger logger = LoggerFactory.getLogger(FlowDiagramVerticalMutlifieldModel.class);

	/** The tab contentIcon. */
	@Inject
	@Getter
	private String tabTitle;
	
	/** The tab unique name. */
	@Inject
	private String tabUniqueName;
	
	/** The tab backgroundcolor. */
	@Inject
	@Getter
	private String tabBackgroundColor;
	
	/**
	 * Gets tabUniqueName as a String.
	 *
	 * @return the tabUniqueName
	 */
	public String getTabUniqueName() {
		return tabUniqueName.replaceAll("\\s", "");
	}
	
}
