package com.bdb.aem.core.services.tools.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.bdb.aem.core.models.CloneComparisonModel;

/**
 * Service class for Clone Comparison Model.
 * 
 * @see CloneComparisonModel
 * 
 * @author ronbanerjee
 *
 */
@Component(service = CloneComparisonServiceImpl.class)
@Designate(ocd = CloneComparisonServiceImpl.CloneComparisionConfigs.class)
public class CloneComparisonServiceImpl extends AbstractSlingModelService<CloneComparisonModel> {

	private String apiEndpoint;

	private String viewProductsPlaceholder;

	private String linkToCatalogPlaceholder;

	private String dyeNamePlaceholder;
	
	private String dyeNamePlaceholderForClone;
	
	private String colorMap;

	@Override
	protected void updateSlingModel(CloneComparisonModel s) {
		if(null != s) {

			String site = s.getResource().getPath() != null && !(s.getResource().getPath().contains("language-masters"))
					&& s.getResource().getPath().split("/").length >= 4 ? s.getResource().getPath().split("/")[4]
							: "us";

			Map<String, String> m = new HashMap<>();
			// TBD : This has to be moved to OSGi configs
			// 13th July 2021 | Moved to OSGi Config.
			m.put("getSearchResultsData", apiEndpoint);
			m.put("site", site);
			s.setConfigs(m);
		};

	}

	/**
	 * Gets the api endpoint.
	 * 
	 * @return the api endpoint
	 */
	public String getApiEndpoint() {
		return apiEndpoint;
	}

	/**
	 * Gets the view products placeholder.
	 * 
	 * @return the view products placeholder
	 */
	public String getViewProductsPlaceholder() {
		return viewProductsPlaceholder;
	}

	/**
	 * Gets the link to catalog placeholder.
	 * 
	 * @return the link to catalog placeholder
	 */
	public String getLinkToCatalogPlaceholder() {
		return linkToCatalogPlaceholder;
	}
	
	/**
	 * Gets the color map.
	 * 
	 * @return the color map
	 */
	public String getColorMap() {
		return colorMap;
	}

	/**
	 * Gets the dyename placeholder.
	 * 
	 * @return the dyename placeholder
	 */
	public String getDyeNamePlaceholder() {
		return dyeNamePlaceholder;
	}


	/**
	 * Gets the dyename placeholder for clone.
	 * 
	 * @return the dyename placeholder for clone
	 */
	public String getDyeNamePlaceholderForClone() {
		return dyeNamePlaceholderForClone;
	}

	/**
	 * The Activate Method.
	 */
	@Activate
	protected void activate(final CloneComparisionConfigs config) {
		LOGGER.info("Into Activate {}", this.getClass().getName());

		this.apiEndpoint = config.apiEndpoint();
		this.dyeNamePlaceholder = config.dyeNamePlaceholder();
		this.linkToCatalogPlaceholder = config.linkToCatalogPlaceholder();
		this.viewProductsPlaceholder = config.viewProductsPlaceholder();
		this.dyeNamePlaceholderForClone = config.dyeNamePlaceholderForClone();
		this.colorMap = config.colorMap();
	}

	/**
	 * The OSGi Config for Clone Comparison.
	 */
	@ObjectClassDefinition(name = "BDB Tools | Clone Comparison Configurations", description = "OSGi Config Store for BDB Tools - Clone Comparison")
	public @interface CloneComparisionConfigs {

		/** The clone comparison api endpoint */
		@AttributeDefinition(name = "Clone Comparison Search Endpoint", description = "The Search Endpoint for BDB Generic Search", type = AttributeType.STRING)
		String apiEndpoint() default StringUtils.EMPTY;

		/** The view products placeholder */
		@AttributeDefinition(name = "View Products Placeholder", description = "Placeholder text for View Products. This is used for navigating to a PLP based on parameters.", type = AttributeType.STRING)
		String viewProductsPlaceholder() default StringUtils.EMPTY;

		/** The link to catalog placeholder */
		@AttributeDefinition(name = "Link to Catalog Placeholder", description = "Placeholder text for Link to catalog. This is used for navigating to a PLP based on parameters.", type = AttributeType.STRING)
		String linkToCatalogPlaceholder() default StringUtils.EMPTY;

		/** The dye name placeholder */
		@AttributeDefinition(name = "DyeName Placeholder", description = "Placeholder text for Dye Name. This is used for navigating to a PLP based on parameters.", type = AttributeType.STRING)
		String dyeNamePlaceholder() default StringUtils.EMPTY;
		
		/** The dye name placeholder for clone */
		@AttributeDefinition(name = "DyeName Placeholder For Clone", description = "Placeholder text for Dye Name (Clone Page). This is used for navigating to a PLP based on parameters.", type = AttributeType.STRING)
		String dyeNamePlaceholderForClone() default StringUtils.EMPTY;
		
		/** The color map */
		@AttributeDefinition(name = "Color Map", description = "Enter Color vs Display Name in Color1:DisplayName1,Color2:DisplayName2 format.", type = AttributeType.STRING)
		String colorMap() default StringUtils.EMPTY;
	}

}
