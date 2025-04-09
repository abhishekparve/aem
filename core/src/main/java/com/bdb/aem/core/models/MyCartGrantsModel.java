package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CookieNameService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * The Class MyCartGrantsModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyCartGrantsModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(MyCartGrantsModel.class);
	
	
	/** The no grants msg. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String noGrantsMsg;
	
	/** The use my grants label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String useMyGrantsLabel;

	
	/** The grants left label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String grantsLeftLabel;

	
	/** The change. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String change;

	
	/** The not eigible product msg. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String notEigibleProductMsg;

	
	/** The grants info tooltip text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String grantsInfoTooltipText;

	
	/** The issue on grant products msg. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String issueOnGrantProductsMsg;

	
	/** The grant total exceeds msg. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String grantTotalExceedsMsg;

	
	/** The select grant. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String selectGrant;
	
	/** The grants left. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String grantsLeft;
	
	/** The ship to number. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String shipToNumber;
	
	/** The starting from. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String startingFrom;
	
	/** The valid until. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String validUntil;
	
	/** The user to select grant text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String userToSelectGrantText;
	
	/** The cancel btn text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String cancelBtnText;
	
	/** The use grant btn text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String useGrantBtnText;
	
	/** The text 1. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String text1;
	
	/** The text 2. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String text2;
	
	/** The tool tip alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String toolTipAltText;
	
	/** The grant warning alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String grantWarningAltText;
	
	/** The out of text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String outOfText;
	
	
	/** The request. */
	@Inject
	SlingHttpServletRequest request;
	
	/** The current page. */
	@Inject
	Page currentPage;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The bdb api endpoint service. */
	@Inject
	CookieNameService cookieNameService;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	
	/** The my cart grant config. */
	private String myCartGrantConfig;
	
	/** The bulk remove from cart config. */
	private String bulkRemoveFromCartConfig;
	
	/** The bulk add to save for later config. */
	private String bulkAddToSaveForLaterConfig;


	/** The my cart grant labels. */
	private String myCartGrantLabels;
	
	
	/** The hybris site id. */
	private String hybrisSiteId;
	
	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("MyCartGrantsModel - Init method started");
		myCartGrantConfig = getConfigJson();
		myCartGrantLabels = getLabelJson();
		logger.debug("myCartGrantConfig - Init {}",myCartGrantConfig);
		logger.debug("myCartGrantLabels - Init {}",myCartGrantLabels);
	}
	
	/**
	 * This method forms the json for my cart grants config.
	 *
	 * @return the config json
	 */
	public String getConfigJson(){
		JsonObject myCartGrantConfigJson = new JsonObject();
		JsonObject applyGrantsOnCart = new JsonObject();
		JsonObject bulkRemoveFromCartJson = new JsonObject();
		JsonObject bulkAddToSaveForLaterJson = new JsonObject();
		
		if(hybrisSiteId==null)
			hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		
		String applyGrantsEndpoint = bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.applyGrantsOnCartEndpoint();
		
		
		applyGrantsOnCart.addProperty(CommonConstants.URL, applyGrantsEndpoint.replace(CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId));
		applyGrantsOnCart.addProperty(CommonConstants.METHOD, CommonConstants.POST);
		
		myCartGrantConfigJson.add(CommonConstants.APPLY_GRANTS_ON_CART, applyGrantsOnCart);
		
		String bulkRemoveFromCart = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getBulkRemoveFromCart(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		bulkRemoveFromCartJson.addProperty(CommonConstants.URL, bulkRemoveFromCart);
		bulkRemoveFromCartJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_DELETE);
		bulkRemoveFromCartConfig = bulkRemoveFromCartJson.toString();
		
		String bulkAddToSaveForLater = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getBulkAddToSaveForLater(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		bulkAddToSaveForLaterJson.addProperty(CommonConstants.URL, bulkAddToSaveForLater);
		bulkAddToSaveForLaterJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		bulkAddToSaveForLaterConfig = bulkAddToSaveForLaterJson.toString();
		
		return myCartGrantConfigJson.toString();
	}
		
	
	/**
	 * Gets the label json.
	 *
	 * @return the label json
	 */
	public String getLabelJson(){
		JsonObject myCartGrantLabelsJson = new JsonObject();
		
		// forming labels JSON 
		myCartGrantLabelsJson.addProperty(CommonConstants.NO_GRANTS_MSG, noGrantsMsg);
		myCartGrantLabelsJson.addProperty(CommonConstants.USE_MY_GRANTS_LABEL, useMyGrantsLabel);
		myCartGrantLabelsJson.addProperty(CommonConstants.GRANTS_LEFT_LABEL, grantsLeftLabel);
		myCartGrantLabelsJson.addProperty(CommonConstants.CHANGE_GRANTS, change);
		myCartGrantLabelsJson.addProperty(CommonConstants.NOT_ELIGIBLE_PRODUCT_MSG, CommonHelper.getLabel(CommonConstants.NOT_ELIGIBLE_PRODUCT_MSG, currentPage));
		myCartGrantLabelsJson.addProperty(CommonConstants.GRANTS_INFO_TOOLTIP_TEXT, grantsInfoTooltipText);
		myCartGrantLabelsJson.addProperty(CommonConstants.ISSUE_ON_GRANT_PRODUCTS_MSG, CommonHelper.getLabel(CommonConstants.ISSUE_ON_GRANT_PRODUCTS_MSG, currentPage));
		myCartGrantLabelsJson.addProperty(CommonConstants.GRANT_TOTAL_EXCEEDS_MSG, CommonHelper.getLabel(CommonConstants.GRANT_TOTAL_EXCEEDS_MSG, currentPage));
		myCartGrantLabelsJson.addProperty(CommonConstants.SELECT_GRANT, selectGrant);
		myCartGrantLabelsJson.addProperty(CommonConstants.GRANTS_LEFT, grantsLeft);
		myCartGrantLabelsJson.addProperty(CommonConstants.SHIP_TO_NUMBER, shipToNumber);
		myCartGrantLabelsJson.addProperty(CommonConstants.STARTING_FROM, startingFrom);
		myCartGrantLabelsJson.addProperty(CommonConstants.VALID_UNTIL, validUntil);
		myCartGrantLabelsJson.addProperty(CommonConstants.USER_TO_SELECT_GRANT_TEXT, userToSelectGrantText);
		myCartGrantLabelsJson.addProperty(CommonConstants.CANCLE_BTN_TEXT, cancelBtnText);
		myCartGrantLabelsJson.addProperty(CommonConstants.USE_GRANT_BTN_TEXT, useGrantBtnText);
		myCartGrantLabelsJson.addProperty(CommonConstants.GRANT_WARNING_ALT_TEXT, grantWarningAltText);
		
		myCartGrantLabelsJson.addProperty(CommonConstants.TOOLTIP_ALT_TEXT, toolTipAltText);
		myCartGrantLabelsJson.addProperty(CommonConstants.OUT_OF_TEXT, outOfText);
		
		return myCartGrantLabelsJson.toString();
		
	}
	
	
	/**
	 * Gets the my cart grant config.
	 *
	 * @return the my cart grant config
	 */
	public String getMyCartGrantConfig() {
		return myCartGrantConfig;
	}

	/**
	 * Gets the my cart grant labels.
	 *
	 * @return the my cart grant labels
	 */
	public String getMyCartGrantLabels() {
		return myCartGrantLabels;
	}

	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	public String getHybrisSiteId() {
        return hybrisSiteId;
    }

	/**
	 * Gets the bulk remove from cart config.
	 *
	 * @return the bulk remove from cart config
	 */
	public String getBulkRemoveFromCartConfig() {
		return bulkRemoveFromCartConfig;
	}

	/**
	 * Gets the bulk add to save for later config.
	 *
	 * @return the bulk add to save for later config
	 */
	public String getBulkAddToSaveForLaterConfig() {
		return bulkAddToSaveForLaterConfig;
	}
	
}
