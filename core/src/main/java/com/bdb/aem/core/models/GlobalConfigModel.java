package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CookieNameService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The Class GlobalConfigModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GlobalConfigModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(GlobalConfigModel.class);

	/** The request. */
	@SlingObject
	private SlingHttpServletRequest request;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/** The current page. */
	@Inject
	Page currentPage;
	
	/** The bdb api endpoint service. */
	@Inject
	CookieNameService cookieNameService;

	/**
	 * The bdb api endpoint service.
	 */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;
	/**

	
	/** The cookies json. */
	private String cookiesJson;
	
	/** The request payload obj. */
	private String requestPayloadObj;
	
	/** The my cart page path. */
	private String myCartPagePath;
	
	/** The checkout page path. */
	@Getter
	private String checkoutPagePath;

	/** The Order Confirmation page path. */
	@Getter
	private String orderConfirmationPagePath;
	
	/** The Quote Confirmation page path. */
	@Getter
	private String quoteConfirmationPagePath;
	
	/** The Order Confirmation page path. */
	@Getter
	private String myQuotePagePath;
	
	/** The sign in url. */
	@Getter
	private String signInUrl;
	
	/** The dash board url. */
	@Getter
	private String dashBoardUrl;
	
	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("Inside CategoryContentModel Init");
		String myCartPage = CommonHelper.getMyCartPage(currentPage);
		if(!StringUtil.isEmpty(myCartPage)) {
			myCartPagePath = externalizerService.getFormattedUrl(myCartPage, resourceResolver);
		}
		String checkoutPage = CommonHelper.getCheckoutPage(currentPage);
		if(!StringUtil.isEmpty(checkoutPage)) {
			checkoutPagePath = externalizerService.getFormattedUrl(checkoutPage, resourceResolver);
		}
		String orderConfirmationPage = CommonHelper.getLabel(CommonConstants.ORDER_CONFIRMATION_PAGE_PATH, currentPage);
		if(!StringUtil.isEmpty(orderConfirmationPage)) {
			orderConfirmationPagePath = externalizerService.getFormattedUrl(orderConfirmationPage, resourceResolver);
		}
		String quoteConfirmationPage = CommonHelper.getLabel(CommonConstants.QUOTE_CONFIRMATION_PAGE_PATH, currentPage);
		if(!StringUtil.isEmpty(quoteConfirmationPage)) {
			quoteConfirmationPagePath = externalizerService.getFormattedUrl(quoteConfirmationPage, resourceResolver);
		}
		String myQuotePage = CommonHelper.getLabel(CommonConstants.MY_QUOTE_PAGE_PATH, currentPage);
		if(!StringUtil.isEmpty(myQuotePage)) {
			myQuotePagePath = externalizerService.getFormattedUrl(myQuotePage, resourceResolver);
		}
		signInUrl = CommonHelper.getSignInUrl(bdbApiEndpointService,externalizerService,resourceResolver,currentPage);

		String dashboardPath = CommonHelper.getLabel(CommonConstants.DASHBOARD_PAGE_PATH, currentPage);;
		if(!StringUtil.isEmpty(dashboardPath)) {
			dashBoardUrl = externalizerService.getFormattedUrl(dashboardPath, resourceResolver);
		}
		cookiesJson = getCookieJson();
		requestPayloadObj = getRequestPayloadJson();
	}
		
	/**
	 * Gets the request payload json.
	 *
	 * @return the request payload json
	 */
	private String getRequestPayloadJson() {
		JsonObject requestJson = new JsonObject();
		JsonObject requestPayloadJson = new JsonObject();
		requestPayloadJson.addProperty("url", "https://bd-eu.baynote.net/recs/1/bd_eu?PageTemplate=PT_RelatedRec&attrs=ThumbUrl&attrs=Price&attrs=Title&attrs=ProductId&attrs=url&url=http://www.bdbiosciences.com/eu/p/347340&format=json");
		requestPayloadJson.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);
		requestJson.add("requestPayload", requestPayloadJson);
		
		return requestJson.toString();
	}

	/**
	 * Gets the cookie json.
	 *
	 * @return the cookie json
	 */
	public String getCookieJson() {
		JsonObject cookieJson = new JsonObject();
		cookieJson.addProperty(CommonConstants.GUID_COOKIE_EXP_DATE, cookieNameService.getGUIDCookieExpDate());
		cookieJson.addProperty(CommonConstants.ANONYMOUS_USER_ID, cookieNameService.getAnonymousUserId());
		cookieJson.addProperty(CommonConstants.CURRENT_USER_ID, cookieNameService.getCurrentUserId());
		
		return cookieJson.toString();
	}
	
	/**
	 * Gets the my cart page path.
	 *
	 * @return the my cart page path
	 */
	public String getMyCartPagePath() {
		return myCartPagePath;
	}
	
	/**
	 * Gets the cookies json.
	 *
	 * @return the cookies json
	 */
	public String getCookiesJson() {
		return cookiesJson;
	}
	
	/**
	 * Gets the request payload obj.
	 *
	 * @return the request payload obj
	 */
	public String getRequestPayloadObj() {
		return requestPayloadObj;
	}
}
