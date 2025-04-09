package com.bdb.aem.core.models;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

/**
 * The Class TabComponent.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TabComponent {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(TabComponent.class);

	/** Tabs - Title, Description, Image and Image Caption. */
	@Inject
	@Via("resource")
	@Getter
	private List<TabSectionMutlifieldModel> tabSection;

	/** The Section Title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	@Getter
	private String sectionTitle;

	/** The title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	@Getter
	private String title;

	/** The sub title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	@Getter
	private String subTitle;
	
	/** The Background Color. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String backgroundColor;

	public Boolean isBackgroundColor() {
		if(backgroundColor.isEmpty() )
			return false;
		else if(backgroundColor.equals("gray"))
			return true;
		else
			return false;
	}

}
