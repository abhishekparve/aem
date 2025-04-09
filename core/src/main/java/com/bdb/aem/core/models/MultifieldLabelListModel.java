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
 * The Class MultifieldLabelListModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@ToString(exclude = { "logger" })
public class MultifieldLabelListModel {
	
	/** The logger. */
	protected Logger logger = LoggerFactory.getLogger(MultifieldLabelListModel.class);
	
	/** The description text. */
	@Inject
	@Getter
	private String descriptionText;
}
