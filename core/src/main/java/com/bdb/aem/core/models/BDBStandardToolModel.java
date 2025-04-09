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
 * The Class TestimonialCarouselDetailsModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@ToString( exclude = {"resolverFactory","resourceResolver","logger"} )
public class BDBStandardToolModel {
	/** The logger. */
	protected Logger logger = LoggerFactory.getLogger(BDBStandardToolModel.class);

	/** The image path. */
	@Inject
	@Getter
	private String icon;

	/** The alt image. */
	@Inject
	@Getter
	private String iconAltText;
	
	/** The function. */
	@Inject
	@Getter
	private String className;

}
