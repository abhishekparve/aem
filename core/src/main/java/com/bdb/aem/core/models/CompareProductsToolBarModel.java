package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;


/**
 * The Class CompareProductsToolBarModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CompareProductsToolBarModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(CompareProductsToolBarModel.class);

	
	/** The compare label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String compareLabel;
	
	/** The compare products landing page. */
	@Default(values = StringUtils.EMPTY)
	private String compareProductsLandingPage;
	
	/** The clear all label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String clearAllLabel;
	
	/** The remove label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String removeLabel;
	
	/** The compare message mobile. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String compareMessageMobile;

	/** The compare message desktop. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String compareMessageDesktop;
	
	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The current page. */
	@Inject
	Page currentPage;

	
	/** The compare tool bar labels. */
	private String compareToolBarLabels;
	
	/** The externalizer service. */
	@Inject
    ExternalizerService externalizerService;
	
	/** The compare tool bar config. */
	private String compareToolBarConfig;
	
	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("CompareProductsToolBarModel - Init method started");
		compareProductsLandingPage = CommonHelper.getLabel("compareProductsLandingPage", currentPage);
		compareProductsLandingPage = externalizerService.getFormattedUrl(compareProductsLandingPage, resourceResolver);
		compareToolBarLabels = createToolBarLabels();
		compareToolBarConfig = createToolBarConfig();
	}
	
	
	/**
	 * Creates the tool bar labels.
	 *
	 * @return the string
	 * @throws LoginException the login exception
	 */
	public String createToolBarLabels() {
		JsonObject toolBarLabelsJson = new JsonObject();
		toolBarLabelsJson.addProperty(CommonConstants.COMPARE_LABEL_TOOLBAR, compareLabel);
		toolBarLabelsJson.addProperty(CommonConstants.CLEAR_ALL_LABEL, clearAllLabel);
		toolBarLabelsJson.addProperty(CommonConstants.REMOVE_LABEL_TOOLBAR, removeLabel);
		toolBarLabelsJson.addProperty(CommonConstants.COMPARE_MESSAGE_MOBILE, compareMessageMobile);
		toolBarLabelsJson.addProperty(CommonConstants.COMPARE_MESSAGE_DESKTOP, compareMessageDesktop);
		return toolBarLabelsJson.toString();
	}
	
	
	/**
	 * Creates the tool bar config.
	 *
	 * @return the string
	 */
	public String createToolBarConfig() {
		JsonObject toolBarConfigJson = new JsonObject();		
		toolBarConfigJson.addProperty(CommonConstants.MAX_PRODUCTS, bdbApiEndpointService.getMaximumProducts());
		toolBarConfigJson.addProperty(CommonConstants.MIN_PRODUCTS, bdbApiEndpointService.getMinimumProducts());
		toolBarConfigJson.addProperty(CommonConstants.COMPARE_PAGE_URL, compareProductsLandingPage);
		
		return toolBarConfigJson.toString();
	}
	

	/**
	 * Gets the compare tool bar labels.
	 *
	 * @return the compare tool bar labels
	 */
	public String getCompareToolBarLabels() {
		return compareToolBarLabels;
	}


	/**
	 * Gets the compare tool bar config.
	 *
	 * @return the compare tool bar config
	 */
	public String getCompareToolBarConfig() {
		return compareToolBarConfig;
	}

}
