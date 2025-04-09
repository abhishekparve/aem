/*
 * 
 */
package com.bdb.aem.core.services;

/**
 * The Interface BDBApiEndpointService.
 */

public interface BDBApiEndpointService extends BaseService {

	/**
	 * Gets the BDB hybris domain.
	 *
	 * @return the BDB hybris domain
	 */
	public String getBDBHybrisDomain();

	/**
	 * Gets the captcha client key.
	 *
	 * @return the captcha client key
	 */
	public String getCaptchaClientKey();

	/**
	 * Gets the Anonymous token servlet path.
	 *
	 * @return the Anonymous token servlet path.
	 */
	public String getAnonymousTokenServletPath();

	/**
	 * Gets the region country dropdown endpoint.
	 *
	 * @return the region country dropdown endpoint
	 */
	public String getRegionCountryDropdownEndpoint();

	/**
	 * Gets the hybris sign up endpoint.
	 *
	 * @return the hybris sign up endpoint
	 */
	public String getHybrisSignUpEndpoint();

	/**
	 * Gets the hybris sign up preference endpoint.
	 *
	 * @return the hybris sign up preference endpoint
	 */
	public String getHybrisSignUpPreferenceEndpoint();

	/**
	 * Gets the hybris reset password endpoint.
	 *
	 * @return the hybris reset password endpoint
	 */
	public String getHybrisResetPasswordEndpoint();

	/**
	 * Gets the countries from region servlet path.
	 *
	 * @return the countries from region servlet path
	 */
	public String getCountriesFromRegionServletPath();

	/**
	 * Gets the commerce box API endpoint.
	 *
	 * @return the commerce box API endpoint
	 */
	public String getCommerceBoxAPIEndpoint();

	/**
	 * Gets the purchasing account registration endpoint.
	 *
	 * @return the purchasing account registration endpoint
	 */
	public String getPurchasingAccountRegistrationEndpoint();

	/**
	 * Gets the upload tax certificate endpoint.
	 *
	 * @return the upload tax certificate endpoint
	 */
	public String getUploadTaxCertificateEndpoint();

	/**
	 * Gets the pub med id.
	 *
	 * @return the pub med id
	 */
	public String getPubMedId();

	/**
	 * Gets the country state dropdown endpoint.
	 *
	 * @return the country state dropdown endpoint
	 */
	public String getCountryStateDropdownEndpoint();

	/**
	 * Gets the states from country servlet path.
	 *
	 * @return the states from country servlet path
	 */
	public String getStatesFromCountryServletPath();

	/**
	 * Gets the test config.
	 *
	 * @return the test config
	 */
	public String getTestConfig();

	/**
	 * Gets the user settings endpoint.
	 *
	 * @return the user settings endpoint
	 */
	public String getUserSettingsEndpoint();

	/**
	 * Update user details endpoint.
	 *
	 * @return the string
	 */
	public String updateUserDetailsEndpoint();

	/**
	 * Update user pwd endpoint.
	 *
	 * @return the string
	 */
	public String updateUserPwdEndpoint();

	/**
	 * Gets the request timeout config.
	 *
	 * @return the request timeout config
	 */
	public int getRequestTimeoutConfig();

	/**
	 * Gets the socket timeout config.
	 *
	 * @return the socket timeout config
	 */
	public int getSocketTimeoutConfig();

	/**
	 * Gets the connect timeout config.
	 *
	 * @return the connect timeout config
	 */
	public int getConnectTimeoutConfig();

	/**
	 * Gets the aem sign up endpoint.
	 *
	 * @return the aem sign up endpoint
	 */
	public String getAemSignUpEndpoint();

	/**
	 * Gets the aem sign up preference endpoint.
	 *
	 * @return the aem sign up preference endpoint
	 */
	public String getAemSignUpPreferenceEndpoint();

	/**
	 * Gets the aem reset password endpoint.
	 *
	 * @return the aem reset password endpoint
	 */
	public String getAemResetPasswordEndpoint();

	/**
	 * Gets the aem purchasing account registration endpoint.
	 *
	 * @return the aem purchasing account registration endpoint
	 */
	public String getAemPurchasingAccountRegistrationEndpoint();

	/**
	 * Gets the anonymous user id placeholder.
	 *
	 * @return the anonymous user id placeholder
	 */
	public String getAnonymousUserIdPlaceholder();

	/**
	 * Gets the current user id placeholder.
	 *
	 * @return the current user id placeholder
	 */
	public String getCurrentUserIdPlaceholder();

	/**
	 * Gets the fetch auth token endpoint client id.
	 *
	 * @return the fetch auth token endpoint client id
	 */
	public String getFetchAuthTokenEndpointClientId();

	/**
	 * Gets the fetch auth token endpoint grant type.
	 *
	 * @return the fetch auth token endpoint grant type
	 */
	public String getFetchAuthTokenEndpointGrantType();

	/**
	 * Gets the fetch auth token endpoint client secret.
	 *
	 * @return the fetch auth token endpoint client secret
	 */
	public String getFetchAuthTokenEndpointClientSecret();

	/**
	 * Gets the fetch auth token endpoint.
	 *
	 * @return the fetch auth token endpoint
	 */
	public String getFetchAuthTokenEndpoint();

	/**
	 * Gets the list of user certifications endpoint.
	 *
	 * @return the list of user certifications endpoint
	 */
	public String getListOfUserCertificationsEndpoint();

	/**
	 * Delete certificate endpoint.
	 *
	 * @return the string
	 */
	public String deleteCertificateEndpoint();

	/**
	 * Upload ruo certificate endpoint.
	 *
	 * @return the string
	 */
	public String uploadRuoCertificateEndpoint();

	/**
	 * Gets the addresses endpoint.
	 *
	 * @return the addresses endpoint
	 */
	public String getAddressesEndpoint();

	/**
	 * Gets the all customers cart endpoint.
	 *
	 * @return the all customers cart endpoint
	 */
	public String getAllCustomersCartEndpoint();

	/**
	 * Gets the adds the quantity endpoint.
	 *
	 * @return the adds the quantity endpoint
	 */
	public String getaddQuantityEndpoint();

	/**
	 * Update favorite address endpoint.
	 *
	 * @return the string
	 */
	public String updateFavoriteAddressEndpoint();

	/**
	 * Default address endpoint.
	 *
	 * @return the string
	 */
	public String defaultAddressEndpoint();

	/**
	 * Update nickname endpoint.
	 *
	 * @return the string
	 */
	public String updateNicknameEndpoint();

	/**
	 * Creates the address endpoint.
	 *
	 * @return the string
	 */
	public String createAddressEndpoint();
	
	/**
	 * Creates the address endpoint.
	 *
	 * @return the string
	 */
	public String reactivateUserEndpoint();

	/**
	 * Sso login ping id domain.
	 *
	 * @return the string
	 */
	public String ssoLoginPingIdDomain();

	/**
	 * Sso login ping id endpoint.
	 *
	 * @return the string
	 */
	public String ssoLoginPingIdEndpoint();

	/**
	 * Sso login response type.
	 *
	 * @return the string
	 */
	public String ssoLoginResponseType();

	/**
	 * Sso login client id.
	 *
	 * @return the string
	 */
	public String ssoLoginClientId();

	/**
	 * Sso customer login service.
	 *
	 * @return the string
	 */
	public String ssoCustomerLoginService();

	/**
	 * Sso login scope.
	 *
	 * @return the string
	 */
	public String ssoLoginScope();

	/**
	 * Gets the customer login endpoint.
	 *
	 * @return the customer login endpoint
	 */
	public String getCustomerLoginEndpoint();

	/**
	 * Gets the cookie expiry time.
	 *
	 * @return the cookie expiry time
	 */
	public int getCookieExpiryTime();
	
	/**
	 * ciam ping id domain
	 * @return ciam ping id domain
	 */
	public String ciamPingIdDomain();
	
	/**
	 * ciam login ping id end point
	 * @return ciam login ping id end point
	 */
	public String ciamLoginPingIdEndpoint();
	
	/**
	 * ciam login client id
	 * @return ciam login client id
	 */
	public String ciamLoginClientId();
	
	/**
	 * ciam b2c id
	 * @return ciam b2c id
	 */
	public String ciamB2CId();
	

	/**
	 * Gets the creates the shopping list endpoint.
	 *
	 * @return the creates the shopping list endpoint
	 */
	public String getCreateShoppingListEndpoint();

	/**
	 * Gets the gets the shopping list details endpoint.
	 *
	 * @return the gets the shopping list details endpoint
	 */
	public String getGetShoppingListDetailsEndpoint();

	/**
	 * Gets the gets the shopping list endpoint.
	 *
	 * @return the gets the shopping list endpoint
	 */
	public String getGetShoppingListEndpoint();
	
	/**
	 * Gets the gets the Quote History endpoint.
	 *
	 * @return the gets the Quote History endpoint
	 */
	public String getGetQuotesHistoryEndpoint();
	
	/**
	 * Gets the gets the Quote Details endpoint.
	 *
	 * @return the gets the Quote Details endpoint
	 */
	public String getGetQuoteDetailsEndpoint();

	/**
	 * Gets the file upload shopping list endpoint.
	 *
	 * @return the file upload shopping list endpoint
	 */
	public String getFileUploadShoppingListEndpoint();

	/**
	 * Gets the removes the shopping list endpoint.
	 *
	 * @return the removes the shopping list endpoint
	 */
	public String getRemoveShoppingListEndpoint();

	/**
	 * Gets the share shopping list endpoint.
	 *
	 * @return the share shopping list endpoint
	 */
	public String getShareShoppingListEndpoint();
	
	/**
	 * Gets the export shopping list endpoint.
	 *
	 * @return the export shopping list endpoint
	 */
	public String getExportShoppingListEndpoint();

	/**
	 * Gets the removes the shopping list entries endpoint.
	 *
	 * @return the removes the shopping list entries endpoint
	 */
	public String getRemoveShoppingListEntriesEndpoint();

	/**
	 * Gets the update shopping list entries endpoint.
	 *
	 * @return the update shopping list entries endpoint
	 */
	public String getUpdateShoppingListEntriesEndpoint();

	/**
	 * Gets the file upload shopping list entries endpoint.
	 *
	 * @return the file upload shopping list entries endpoint
	 */
	public String getFileUploadShoppingListEntriesEndpoint();

	/**
	 * Gets the cart with identifier.
	 *
	 * @return the cart with identifier
	 */
	public String getCartWithIdentifier();

	/**
	 * Gets the delete entry from cart.
	 *
	 * @return the delete entry from cart
	 */
	public String getDeleteEntryFromCart();

	/**
	 * Gets the save for later.
	 *
	 * @return the save for later
	 */
	public String getSaveForLater();

	/**
	 * Gets the replace cart entry.
	 *
	 * @return the replace cart entry
	 */
	public String getReplaceCartEntry();

	/**
	 * Gets the replace save for later entry.
	 *
	 * @return the replace save for later entry
	 */
	public String getReplaceSaveForLaterEntry();

	/**
	 * Gets the creates the save for later cart.
	 *
	 * @return the creates the save for later cart
	 */
	public String getCreateSaveForLaterCart();

	/**
	 * Gets the adds the to save for later.
	 *
	 * @return the adds the to save for later
	 */
	public String getAddToSaveForLater();

	/**
	 * Gets the adds the to cart from save to later.
	 *
	 * @return the adds the to cart from save to later
	 */
	public String getAddToCartFromSaveToLater();

	/**
	 * Gets the delete save for later.
	 *
	 * @return the delete save for later
	 */
	public String getDeleteSaveForLater();

	/**
	 * Cart config endpoint.
	 *
	 * @return the string
	 */
	public String cartConfigEndpoint();

	/**
	 * Country dropdown endpoint.
	 *
	 * @return the string
	 */
	public String countryDropdownEndpoint();
	
	/** Gets the language dropdown endpoint.
	 *
	 * @return the language dropdown endpoint
	 */
	public String languageDropdownEndpoint();

	/**
	 * Gets the grants for customer endpoint.
	 *
	 * @return the grants for customer endpoint
	 */
	public String getGrantsForCustomerEndpoint();

	/**
	 * Gets the apply grants to cart endpoint.
	 *
	 * @return the apply grants to cart endpoint
	 */
	public String applyGrantsOnCartEndpoint();

	/**
	 * Order history for grants endpoint.
	 *
	 * @return the string
	 */
	public String orderHistoryForGrantsEndpoint();

	/**
	 * Order history for grants endpoint.
	 *
	 * @return the string
	 */
	public String getGetPaymentsEndpoint();

	/**
	 * Order history for grants endpoint.
	 *
	 * @return the string
	 */
	public String getAddCreditCardEndpoint();

	/**
	 * Order history for grants endpoint.
	 *
	 * @return the string
	 */
	public String getRemoveCreditCardEndpoint();

	/**
	 * getUpdateCreditCardEndpoint.
	 *
	 * @return the getUpdateCreditCardEndpoint
	 */
	public String getUpdateCreditCardEndpoint();

	/**
	 * Mini cart count config endpoint.
	 *
	 * @return Mini Cart Count End Point
	 */
	public String miniCartCountConfigEndpoint();

	/**
	 * Mini cart entries config endpoint.
	 *
	 * @return Mini Cart Config EndPoint
	 */
	public String miniCartEntriesConfigEndpoint();

	/**
	 * Update Cart Quantity.
	 *
	 * @return the string
	 */
	public String getUpdateCartQuantity();

	/**
	 * Update Lot Indicator.
	 *
	 * @return the string
	 */
	public String getUpdateLotIndicator();

	/**
	 * Order details approver endpoint.
	 *
	 * @return the string
	 */
	public String orderDetailsApproverEndpoint();

	/**
	 * Order list approver endpoint.
	 *
	 * @return the string
	 */
	public String orderListApproverEndpoint();

	/**
	 * Order approval decision endpoint.
	 *
	 * @return the string
	 */
	public String orderApprovalDecisionEndpoint();

	/**
	 * Update po number endpoint.
	 *
	 * @return the string
	 */
	public String updatePoNumberEndpoint();

	/**
	 * Update cart VAT Exempt status endpoint.
	 *
	 * @return the string
	 */
	public String getUpdatesCartVATExemptStatus();

	/**
	 * getGetMessagesEndpoint.
	 *
	 * @return the string
	 */
	public String getGetMessagesEndpoint();

	/**
	 * getReadMessageEndpoint.
	 *
	 * @return the string
	 */
	public String getReadMessageEndpoint();

	/**
	 * getGetOrdersEndpoint.
	 *
	 * @return the string
	 */
	public String getGetOrdersEndpoint();

	/**
	 * getGetQuotesEndpoint.
	 *
	 * @return the string
	 */
	public String getGetQuotesEndpoint();

	/**
	 * Promotion id details servlet path.
	 *
	 * @return the string
	 */
	public String getPromotionIdDetailsServletPath();

	/**
	 * Gets the promotions msg endpoint.
	 *
	 * @return the promotions msg endpoint
	 */
	public String getPromotionsMsgEndpoint();

	/**
	 * Aem upload tax certificate endpoint.
	 *
	 * @return the string
	 */
	/*
	 * Aem upload tax certificate endpoint.
	 *
	 * @return the string
	 */
	String aemUploadTaxCertificateEndpoint();

	/**
	 * Gets the user orders list endpoint.
	 *
	 * @return the user orders list endpoint
	 */
	public String getUserOrdersListEndpoint();

	/**
	 * Gets the order details endpoint.
	 *
	 * @return the order details endpoint
	 */
	public String getOrderDetailsEndpoint();

	/**
	 * Gets the order packing list endpoint.
	 *
	 * @return the order packing list endpoint
	 */
	public String getOrderPackingListEndpoint();

	/**
	 * Search Results Servlet Path.
	 *
	 * @return the string
	 */
	public String getSearchResultsServletPath();

	/**
	 * Gets Product Announcement Path.
	 *
	 * @return the string
	 */
	public String getProductAnnouncements();

	/**
	 * Gets the hybris sign out endpoint.
	 *
	 * @return the hybris sign out endpoint
	 */
	public String getHybrisSignOutEndpoint();

	/**
	 * Dev sso sign out endpoint.
	 *
	 * @return the string
	 */
	public String devSsoSignOutEndpoint();
	
	/**
	 * Ciam sign out endpoint.
	 *
	 * @return the string
	 */
	public String ciamSignOutEndpoint();

	/**
	 * Gets the sign out servlet path.
	 *
	 * @return the sign out servlet path
	 */
	public String getSignOutServletPath();

	/**
	 * getUpdateVATExemptStatus.
	 *
	 * @return the string
	 */
	public String getUpdateVATExemptStatus();

	/**
	 * getCheckoutValidation.
	 *
	 * @return the string
	 */
	public String getValidateMyCart();

	/**
	 * getShipToAddressConfig.
	 *
	 * @return the string
	 */
	public String getShipToAddressConfig();

	/**
	 * apply coupon endpoint.
	 *
	 * @return the string
	 */
	public String getApplyCoupon();

	/**
	 * Gets the quick add bulk entry endpoint.
	 *
	 * @return the quick add bulk entry endpoint
	 */
	public String getQuickAddBulkEntryEndpoint();

	/**
	 * Gets the quick add bulk upload endpoint.
	 *
	 * @return the quick add bulk upload endpoint
	 */
	public String getQuickAddBulkUploadEndpoint();

	/**
	 * get Add All Items To Cart Endpoint.
	 *
	 * @return getAddAllItemsToCartEndpoint
	 */
	String getAddAllItemsToCartEndpoint();

	/**
	 * getAddressWithNoRuoEndpoint.
	 *
	 * @return getAddressWithNoRuoEndpoint
	 */
	String getAddressWithNoRuoEndpoint();

	/**
	 * getSearchListEndpoint.
	 *
	 * @return the search list endpoint
	 * @returngetSearchListEndpoint
	 */
	public String getSearchListEndpoint();

	/**
	 * getAddressWithNoRuoEndpoint.
	 *
	 * @return getAddressWithNoRuoEndpoint
	 */
	String getUpdateAddressConfig();

	/**
	 * Gets the anti body details end point.
	 *
	 * @return antiBodyDetailsEndPoint
	 */
	public String getAntiBodyDetailsEndPoint();

	/**
	 * Gets the anti body and formats price data end point.
	 *
	 * @return PriceDataEndPoint
	 */
	public String getPriceDataEndPoint();

	/**
	 * Gets the formats details end point.
	 *
	 * @return FormatsDetailsEndPoint
	 */
	public String getFormatsDetailsEndPoint();

	/**
	 * Gets the promo products list end point.
	 *
	 * @return PromoProductsListEndpoint
	 */
	public String getPromoProductsListEndpoint();

	/**
	 * Gets the getQuoteReference end point.
	 *
	 * @return getQuoteReference Endpoint
	 */
	public String getQuoteReference();
	
	/**
	 * Gets the getGetQuote end point.
	 *
	 * @return getGetQuote Endpoint
	 */
	public String getGetQuote();

	/**
	 * Gets the getQuoteToCart end point.
	 *
	 * @return getQuoteToCart Endpoint
	 */
	public String getQuoteToCart();

	/**
	 * Notification endpoint.
	 *
	 * @return the string
	 */
	public String notificationEndpoint();

	/**
	 * User Idle time.
	 *
	 * @return the idle time
	 */
	public int getUserIdleTime();
	
	/**
	 * Minimum Products.
	 *
	 * @return the minimum products
	 */
	public int getMinimumProducts();
	
	/**
	 * Maximum Products.
	 *
	 * @return the maximum products
	 */
	public int getMaximumProducts();

	/**
	 * Add New Address End Point.
	 *
	 * @return addNewAddressEndPoint
	 */
	public String addNewAddressEndPoint();

	/**
	 * Gets the shipping details config.
	 *
	 * @return the shipping details config
	 */
	public String getShippingDetailsConfig();

	/**
	 * Gets the billing details config.
	 *
	 * @return the billing details config
	 */
	public String getBillingDetailsConfig();

	/**
	 * Get the Ping ID SSO SignOut Domain.
	 *
	 * @return the ping id sso sign out domain
	 */

	public String getPingIdSsoSignOutDomain();

	/**
	 * Gets the delete coupon.
	 *
	 * @return the delete coupon
	 */
	public String getDeleteCoupon();

	/**
	 * Merge anonymous cart endpoint.
	 *
	 * @return the string
	 */
	public String mergeAnonymousCartEndpoint();

	/**
	 * getPaymentDetails endpoint.
	 *
	 * @return the string
	 */
	public String getPaymentDetails();

	/**
	 * getOtherDetails endpoint.
	 *
	 * @return the string
	 */
	public String getOtherDetails();

	/**
	 * Get order confirmation checkout endpoint.
	 * 
	 * @return String
	 */
	public String getOrderConfirmationCheckoutConfig();

	/**
	 * Get order confirmation cancelorder endpoint.
	 * 
	 * @return String
	 */
	public String getOrderConfirmationCancelOrderConfig();
	
	/**
	 * Get place order config endpoint.
	 * 
	 * @return String
	 */
	public String placeOrderConfig();

	/**
	 * Get Product Details Endpoint.
	 * 
	 * @return String
	 */
	public String getProductDetailsEndpoint();

	/**
	 * Get the post Smart Cart Register Endpoint.
	 *
	 * @return String
	 */
	public String getPostSmartCartRegisterEndpoint();


	/**
	 * Get the get Distributers Options Endpoint.
	 *
	 * @return String
	 */
	public String getGetDistributorsOptionsEndpoint();
	
	/**
	 * Punch Out Servlet Path.
	 *
	 * @return the punch out endpoint
	 */
	public String getPunchOutEndpoint();

	/**
	 * Gets the punchout transmit request endpoint.
	 *
	 * @return the punchout transmit request endpoint
	 */
	public String getPunchoutTransmitRequestEndpoint();
	
	/**
	 * Gets the punchout cancel request endpoint.
	 *
	 * @return the punchout cancel request endpoint
	 */
	public String getPunchoutCancelRequestEndpoint();

	/**
	 * Gets the bulk remove from cart.
	 *
	 * @return the bulk remove from cart
	 */
	public String getBulkRemoveFromCart();
	
	/**
	 * Gets the bulk add to save for later.
	 *
	 * @return the bulk add to save for later
	 */
	public String getBulkAddToSaveForLater();
	
	/**
	 * Gets Quote Config.
	 *
	 * @return Quote Config endpoint.
	 */
	public String getQuoteConfig();

	/**
	 * Gets the QuoteConfirmation url.
	 *
	 * @return QuoteConfirmation url
	 */
	public String getQuoteConfirmationConfig();

	/**
	 * Gets the send email url.
	 *
	 * @return send email url
	 */
	public String sendEmail();
	
	/** Gets the language dropdown endpoint.
	 *
	 * @return the language dropdown endpoint
	 */
	public String getLanguageSearchDropdownEndpoint();

	/** Gets the order inquiry detail endpoint.
	 *
	 * @return the order inquiry detail endpoint
	 */
	public String getGetOrderInquiryDetailEndpoint();


	/**
	 *  Gets the post order inquiry details endpoint.
	 * 
	 * ost @return the puorder inquiry details endpoint
	 *
	 * @return the post order inquiry details endpoint
	 */
	public String getPostOrderInquiryDetailsEndpoint();

	/**
	 * Gets the refresh token endpoint.
	 *
	 * @return the refresh token endpoint
	 */
	String getRefreshTokenEndpoint();
	
	/**
	 * Trigger re order endpoint.
	 *
	 * @return the string
	 */
	String triggerReOrderEndpoint();

	/**
	 * getBeadlotsFileDownloadEndpoint.
	 *
	 * @return the string
	 */
	public String getBeadlotsFileDownloadEndpoint();
	/**
	 * getBeadlotsFileDownloadUsername.
	 *
	 * @return the string
	 */

	public String getBeadlotsFileDownloadUsername();

	/**
	 * TgetBeadlotsFileDownloadPassword.
	 *
	 * @return the string
	 */

	public String getBeadlotsFileDownloadPwd();

	/**
	 * getBeadlotsFileDownloadServletPath.
	 *
	 * @return the string
	 */
	public String getBeadlotsFileDownloadServletPath();

	/**
	 * getMyOrdersListEndpoint.
	 *
	 * @return the string
	 */

	public String getMyOrdersListEndpoint();

	/**
	 * getAllOrdersListEndpoint.
	 *
	 * @return the string
	 */

	public String getAllOrdersListEndpoint();

	/**
	 * Gets the sds endpoint user.
	 *
	 * @return the sds endpoint user
	 */
	public String getSdsEndpointUser(); 

	/**
	 * Gets the sds endpoint password.
	 *
	 * @return the sds endpoint password
	 */
	public String getSdsEndpointPassword(); 
	
	/**
	 * Gets the tds endpoint.
	 *
	 * @return the tds endpoint
	 */
	public String getTdsEndpoint(); 

	/**
	 * Gets the sds endpoint.
	 *
	 * @return the sds endpoint
	 */
	public String getSdsEndpoint(); 

	/**
	 * Gets the sds pdf search endpoint.
	 *
	 * @return the sds pdf search endpoint
	 */
	public String getSdsPdfSearchEndpoint(); 

	/**
	 * Gets the sds pdf download endpoint.
	 *
	 * @return the sds pdf download endpoint
	 */
	public String getSdsPdfDownloadEndpoint();
	
	/**
	 * Cancel order endpoint.
	 *
	 * @return the string
	 */
	String cancelOrderEndpoint();

	/**
	 *  getRequiredCompanionProducts endpoint.
	 *
	 * @return the string
	 */
	public String getRequiredCompanionProductsConfig();

	/**
	 *  GetPrice endpoint.
	 *
	 * @return the string
	 */
	public String getPriceConfig();
	
	/**
	 * Paymetric token endpoint.
	 *
	 * @return the string
	 */
	String paymetricTokenEndpoint();
	
	/**
	 * Paymetric domain.
	 *
	 * @return the string
	 */
	String paymetricDomain();
	
	/**
	 * Paymetric iframe endpoint.
	 *
	 * @return the string
	 */
	String paymetricIframeEndpoint();
	
	/**
	 * Brightcove account id.
	 *
	 * @return the string
	 */
	String brightcoveAccountId();

	/**
	 * Brightcove player id.
	 *
	 * @return the string
	 */
	String brightcovePlayerId();
	
	/**
	 * getPaymentDetails endpoint.
	 *
	 * @return the string
	 */
	String getAllPaymentDetails();

	/**
	 * Gets the update rewards preferences endpoint.
	 *
	 * @return the update rewards preferences endpoint
	 */
	/*
	 * getUpdateRewardsPreferencesEndpoint.
	 *
	 * @return the getUpdateRewardsPreferencesEndpoint
	 */
	String getUpdateRewardsPreferencesEndpoint();

	/**
	 * getAnnexSiteId.
	 *
	 * @return the getAnnexSiteId
	 */
	String getAnnexSiteId();

	/**
	 * getAnnexJs.
	 *
	 * @return the getAnnexJs
	 */
	String getAnnexSiteJs();
	
	/**
	 * getAnnexSiteDomain.
	 *
	 * @return the getAnnexSiteDomain
	 */
	String getAnnexSiteDomain();

	/**
	 * getSummaryTabSrcEndpoint.
	 *
	 * @return the getSummaryTabSrcEndpoint
	 */
	String getSummaryTabSrcEndpoint();

	/**
	 * getRewardsTabSrcEndpoint.
	 *
	 * @return the getRewardsTabSrcEndpoint
	 */
	String getRewardsTabSrcEndpoint();

	/**
	 * GetEarnTabSrcEndpoint.
	 *
	 * @return the getEarnTabSrcEndpoint
	 */
	String getEarnTabSrcEndpoint();

	/**
	 * getActivityTabSrcEndpoint.
	 *
	 * @return the getActivityTabSrcEndpoint
	 */
	String getActivityTabSrcEndpoint();
	
	/**
	 * getVialImagesBasePath.
	 *
	 * @return the getVialImagesBasePath
	 */
	String getVialImagesBasePath();
	
	/**
	 * getDropshipDetailsEndpoint.
	 *
	 * @return the getDropshipDetailsEndpoint
	 */
	String getDropshipDetailsEndpoint();
	
	/**
	 * getScientificResourceFolder.
	 *
	 * @return the getScientificResourceFolder
	 */
	String getScientificResourceFolder();

	/**
	 * Gets servlet end point.
	 *
	 * @return getScientificResPath
	 */
	String getScientificResPath();
	
	/**
	 * Gets the search order lookup endpoint.
	 *
	 * @return the search order lookup endpoint
	 */
	String getSearchOrderLookupEndpoint();
	
	/**
	 * Gets the ruo vial images base path.
	 *
	 * @return the ruo vial images base path
	 */
	String getRuoVialImagesBasePath();
	
	/**
	 * Gets the ruo vial based on storage buffer.
	 *
	 * @return the ruo vial based on storage buffer
	 */
	String getRuoVialBasedOnStorageBuffer();
	
	/**
	 * Gets the ruo vial based on vol per test.
	 *
	 * @return the ruo vial based on vol per test
	 */
	String getRuoVialBasedOnVolPerTest();
	
	/**
	 * Gets the ruo vial based on product type.
	 *
	 * @return the ruo vial based on product type
	 */
	String getRuoVialBasedOnProductType();
	
	/**
	 * Gets the format images base path.
	 *
	 * @return the format images base path
	 */
	String getFormatImagesBasePath();

	/**
	 * gets environmentType
	 * 
	 * @return string
	 */
	String environmentType();
	/**
	 * Get the Production User Name.
	 *
	 * @return the Production User Name
	 */
	String getProdUserName();


	/**
	 * Get the Production User Password
	 *
	 * @return the Production User Password
	 */
	String getProdUserPassword();
	
	/**
	 * Tracking number config.
	 *
	 * @return the string
	 */
	String trackingNumberConfig();

	/**
	 * Gets the Custom RunMode
	 * @return
	 */
	String getCustomRunMode();
	
	/**
	 * Event topic dropdown endpoint.
	 *
	 * @return the string
	 */
	public String eventTopicDropdownEndpoint();
	
	/**
	 * Email validation endpoint.
	 *
	 * @return the string
	 */
	String emailValidationEndpoint();

	/**
	 * Gets the hybris validate password endpoint.
	 *
	 * @return the hybris validate password endpoint
	 */
	public String getHybrisValidatePasswordEndpoint();
	
	String countryLanguageMapping();
	
	/**
	 * Clear Cart.
	 *
	 * @return the string
	 */
	String getClearCart();
	
	/**
	 * Gets the hybris sales rep list endpoint.
	 *
	 * @return the hybris sales rep list endpoint
	 */
	String getHybrisSalesRepListEndpoint();
	
	String getImageRootDir();

	String getCiteabApiKey();

	String getCiteabCompanySlug();
	String getCiteabScriptUrl();

	//String getShowCiteabWidget();

}
