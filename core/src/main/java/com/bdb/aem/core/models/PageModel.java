package com.bdb.aem.core.models;

/**
 * The Interface PageModel.
 */
public interface PageModel {

	/**
	 * Gets the gets the states payload.
	 *
	 * @return the gets the states payload
	 */
	String getGetStatesPayload();

	/**
	 * Gets the gets the countries payload.
	 *
	 * @return the gets the countries payload
	 */
	String getGetCountriesPayload();

	/**
	 * Gets the error messages.
	 *
	 * @return the error messages
	 */
	String getErrorMessages();

	/**
	 * Gets the home page url.
	 *
	 * @return the home page url
	 */
	String getHomePageUrl();

	/**
	 * Gets the certifications status.
	 *
	 * @return the certifications status
	 */
	String getCertificationsStatus();

	/**
	 * Gets the order source.
	 *
	 * @return the order source
	 */
	String getOrderSource();

	/**
	 * Gets the order status.
	 *
	 * @return the order status
	 */
	String getOrderStatus();

	/**
	 * Gets the yesterday.
	 *
	 * @return the yesterday
	 */
	String getYesterday();

	/**
	 * Gets the today.
	 *
	 * @return the today
	 */
	String getToday();

	/**
	 * Gets the SignInUrl.
	 *
	 * @return the SignInUrl
	 */
	String getSignInUrl();

	/**
	 * Gets the idle time.
	 *
	 * @return the idle time.
	 */
	int getIdleTime();

	/**
	 * Gets the Log out Url.
	 *
	 * @return the og out Url.
	 */
	String getLogOutUrl();

	/**
	 * Gets the order approval promotions.
	 *
	 * @return the order approval promotions
	 */
	String getOrderApprovalPromotions();

	/**
	 * Gets the merge anonymous cart.
	 *
	 * @return the merge anonymous cart
	 */
	String getMergeAnonymousCart();

	/**
	 * get the AnonymousTokenUrl.
	 *
	 * @return the AnonymousTokenUrl
	 */
	String getAnonymousTokenUrl();

	/**
	 * Return saveToQuoteList labels.
	 * 
	 * @return
	 */
	String getSaveToQuoteList();

	/**
	 * Return addToQuote labels.
	 * 
	 * @return
	 */
	String getAddToQuote();

	/**
	 * Return addToQuote labels.
	 *
	 * @return
	 */
	String getQuoteStatus();

	/**
	 * Gets the deliveryOption.
	 *
	 * @return the deliveryOption
	 */
	String getDeliveryOption();

	/**
	 * Gets the refresh token.
	 *
	 * @return the refresh token
	 */
	String getRefreshToken();

	/**
	 * @return standard.
	 */
	String getStandard();
	
	/**
	 * @return default server error message.
	 */
	String getDefaultServerError();
	
	/**
	 * Gets the contact us url.
	 *
	 * @return the contact us url
	 */
	String getContactUsUrl();
	
	String getProductInquireUrl();

	String getRegion();

	String getCountry();

	String getLanguage();

	String getHybrisSiteId();
	
	boolean isFeLog();
	
	String getLoginErrorAlertLabels();
	
	String getMarektoSubmitCta();
	
	/**
	 * Gets the public email domains.
	 *
	 * @return the public email domains
	 */
	String getPublicEmailDomains();
}
