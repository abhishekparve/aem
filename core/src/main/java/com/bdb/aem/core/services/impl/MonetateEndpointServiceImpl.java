package com.bdb.aem.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.bdb.aem.core.services.MonetateEndpointService;

/**
 * The Class MonetateEndpointServiceImpl.
 */
@Component(immediate = true, service = MonetateEndpointService.class)
@Designate(ocd = MonetateEndpointServiceImpl.Configuration.class)
public class MonetateEndpointServiceImpl implements MonetateEndpointService {

	/** The monetate products endpoint. */
	private String monetateProductsEndpoint;


	/**
	 * Activate.
	 *
	 * @param config the config
	 */
	@Activate
	@Modified
	protected final void activate(Configuration config) {
		this.monetateProductsEndpoint = config.monetateProductsEndpoint();
	}

	/**
	 * Deactivate.
	 */
	@Deactivate
	protected void deactivate() {
		// DoNothing
	}

	/**
	 * Gets the monetate products endpoint.
	 *
	 * @return the monetate products endpoint
	 */
	@Override
	public String getMonetateProductsEndpoint() {

		return monetateProductsEndpoint;
	}

	/**
	 * The Interface Configuration.
	 */
	@ObjectClassDefinition(name = "Monetate Endpoint Configuration")
	public @interface Configuration {

		/**
		 * Popular and recommended endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "monetateProductsEndpoint", description = "The endpoint for Monetate Products", type = AttributeType.STRING)
		public String monetateProductsEndpoint() default "//se.monetate.net/js/2/a-f427f57e/d/dev-gl.bdbiosciences.com/entry.js";

		
	}

}
