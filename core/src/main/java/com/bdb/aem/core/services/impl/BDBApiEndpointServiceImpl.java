package com.bdb.aem.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;

/**
 * The Class BDBApiEndpointServiceImpl.
 */

@Component(immediate = true, service = BDBApiEndpointService.class)
@Designate(ocd = BDBApiEndpointServiceImpl.Configuration.class)
public class BDBApiEndpointServiceImpl implements BDBApiEndpointService {

	/** The BDB Hybris domain. */
	private String bdbHybrisDomain;

	/** The re-captcha client key. */
	private String captchaClientKey;

	/** Anonymous Token generation servlet path. */
	private String anonymousTokenServletPath;

	/** The region country end point. */
	private String regionCountryDropdownEndpoint;

	/** The hybris sign up endpoint. */
	private String hybrisSignUpEndpoint;

	/** The hybris sales rep list endpoint. */
	private String hybrisSalesRepListEndpoint;

	/** The iamge root dir path. */
	private String imageRootDir;

	/** The hybris reset password endpoint. */
	private String hybrisResetPasswordEndpoint;

	/** The hybris validate password endpoint. */
	private String hybrisValidatePasswordEndpoint;

	/** The hybris sign up preference endpoint. */
	private String hybrisSignUpPreferenceEndpoint;

	/** Countries From Region Servlet Path. */
	private String countriesFromRegionServletPath;

	/** Commerce Box API Endpoint. */
	private String commerceBoxAPIEndpoint;

	/** The purchasing account registration endpoint. */
	private String purchasingAccountRegistrationEndpoint;

	/** The upload tax certificate endpoint. */
	private String uploadTaxCertificateEndpoint;

	/** The pub med id. */
	private String pubMedId;

	/** The country state end point. */
	private String countryStateDropdownEndpoint;

	/** The states from country servlet path. */
	private String statesFromCountryServletPath;

	/** The testConfig. */
	private String testConfig;

	/** The get User Settings endpoint. */
	private String getUserSettingsEndpoint;

	/** The update User Pwd endpoint. */
	private String updateUserDetailsEndpoint;

	/** The update User Pwdendpoint. */
	private String updateUserPwdEndpoint;

	/** The get User Settings endpoint. */
	private int requestTimeoutConfig;

	/** The update User Pwd endpoint. */
	private int socketTimeoutConfig;

	/** The update User Pwdendpoint. */
	private int connectTimeoutConfig;

	/** The cookie expiry time. */
	private int cookieExpiryTime;

	/** The aem sign up endpoint. */
	private String aemSignUpEndpoint;

	/** The aem sign up preference endpoint. */
	private String aemSignUpPreferenceEndpoint;

	/** The aem reset password endpoint. */
	private String aemResetPasswordEndpoint;

	/** The aem purchasing account registration endpoint. */
	private String aemPurchasingAccountRegistrationEndpoint;

	/** The aem anonymous user id place holder text field. */
	private String anonymousUserIdPlaceholder;

	/** The aem current user id place holder text field. */
	private String currentUserIdPlaceholder;

	/** The fetch auth token endpoint client id. */
	private String fetchAuthTokenEndpointClientId;

	/** The fetch auth token endpoint grant type. */
	private String fetchAuthTokenEndpointGrantType;

	/** The fetch auth token endpoint client secret. */
	private String fetchAuthTokenEndpointClientSecret;

	/** The fetch auth token endpoint. */
	private String fetchAuthTokenEndpoint;

	/** The get list of user certifications. */
	private String getListOfUserCertificationsEndpoint;

	/** The delete certificate endpoint. */
	private String deleteCertificateEndpoint;

	/** The upload ruo certificate endpoint. */
	private String uploadRuoCertificateEndpoint;

	/** The get addresses endpoint. */
	private String getAddressesEndpoint;

	/** The get all customers cart. */
	private String getAllCustomersCart;

	/** The add quantity. */
	private String addQuantity;

	/** The favorite address update. */
	private String updateFavoriteAddressEndpoint;

	/** The default address endpoint. */
	private String defaultAddressEndpoint;

	/** The update nickname endpoint. */
	private String updateNicknameEndpoint;

	/** The create address endpoint. */
	private String createAddressEndpoint;

	/** The create address endpoint. */
	private String reactivateUserEndpoint;

	/** The sso login ping id domain. */
	private String ssoLoginPingIdDomain;

	/** The sso login ping id endpoint. */
	private String ssoLoginPingIdEndpoint;

	/** The sso login response type. */
	private String ssoLoginResponseType;

	/** The sso login client id. */
	private String ssoLoginClientId;

	/** The sso customer login service. */
	private String ssoCustomerLoginService;

	/** The sso login scope. */
	private String ssoLoginScope;

	/** The ciam ping id domain. */
	private String ciamPingIdDomain;

	/** The ciam login ping id end point. */
	private String ciamLoginPingIdEndpoint;

	/** The ciam b2c id. */
	private String ciamB2CId;

	/** The ciam login client id. */
	private String ciamLoginClientId;

	/** The createShoppingListEndpoint. */
	private String createShoppingListEndpoint;
	/** The getShoppingListDetailsEndpoint. */
	private String getShoppingListDetailsEndpoint;
	/** The getShoppingListEndpoint. */
	private String getShoppingListEndpoint;
	/** The fileUploadShoppingListEndpoint. */
	private String fileUploadShoppingListEndpoint;
	/** The removeShoppingListEndpoint. */
	private String removeShoppingListEndpoint;
	/** The shareShoppingListEndpoint. */
	private String shareShoppingListEndpoint;
	/** The exportShoppingListEndpoint. */
	private String exportShoppingListEndpoint;
	/** The removeShoppingListEntriesEndpoint. */
	private String removeShoppingListEntriesEndpoint;
	/** The updateShoppingListEntriesEndpoint. */
	private String updateShoppingListEntriesEndpoint;
	/** The fileUploadShoppingListEntriesEndpoint. */
	private String fileUploadShoppingListEntriesEndpoint;
	/** The getQuotesHistoryEndpoint. */
	private String getQuotesHistoryEndpoint;

	/** The getQuoteDetailsEndpoint. */
	private String getQuoteDetailsEndpoint;

	/** The get customer login endpoint. */
	private String getCustomerLoginEndpoint;

	/** The cart with identifier. */
	private String cartWithIdentifier;

	/** The delete entry from cart. */
	private String deleteEntryFromCart;

	/** The save for later. */
	private String saveForLater;

	/** The create save for later cart. */
	private String createSaveForLaterCart;

	/** The replace cart entry. */
	private String replaceCartEntry;

	/** The replace save for later entry. */
	private String replaceSaveForLaterEntry;

	/** The add to save for later. */
	private String addToSaveForLater;

	/** The bulk remove from cart. */
	private String bulkRemoveFromCart;

	/** The bulk add to save for later. */
	private String bulkAddToSaveForLater;

	/** The add to cart from save to later. */
	private String addToCartFromSaveToLater;

	/** The delete save for later. */
	private String deleteSaveForLater;

	/** The cart config endpoint. */
	private String cartConfigEndpoint;

	/** The country dropdown endpoint. */
	private String countryDropdownEndpoint;

	/** The language dropdown endpoint. */
	private String languageDropdownEndpoint;

	/** The get grants for customer endpoint. */
	private String getGrantsForCustomerEndpoint;

	/** The apply grants on cart endpoint. */
	private String applyGrantsOnCartEndpoint;

	/** The order history for grants endpoint. */
	private String orderHistoryForGrantsEndpoint;

	/** The getPaymentsEndpoint. */
	private String getPaymentsEndpoint;

	/** The addCreditCardEndpoint. */
	private String addCreditCardEndpoint;

	/** The removeCreditCardEndpoint. */
	private String removeCreditCardEndpoint;

	/** The updateCreditCardEndpoint. */
	private String updateCreditCardEndpoint;

	/** The getMessagesEndpoint. */
	private String getMessagesEndpoint;

	/** The readMessageEndpoint. */
	private String readMessageEndpoint;

	/** The getOrdersEndpoint. */
	private String getOrdersEndpoint;

	/** The getQuotesEndpoint. */
	private String getQuotesEndpoint;

	/** Mini Cart Count End Point. */
	private String miniCartCountConfigEndpoint;

	/** Mini Cart Entries End Point. */
	private String miniCartEntriesConfigEndpoint;

	/** The update cart quantity endpoint. */
	private String updateCartQuantityEndpoint;

	/** The update lot indicator endpoint. */
	private String updateLotIndicatorEndpoint;

	/** The order details approver endpoint. */
	private String orderDetailsApproverEndpoint;

	/** The order list approver endpoint. */
	private String orderListApproverEndpoint;

	/** The order approval decision endpoint. */
	private String orderApprovalDecisionEndpoint;

	/** The update po number endpoint. */
	private String updatePoNumberEndpoint;

	/** The Promotion ID Details servlet path. */
	private String promotionIdDetailsServletPath;

	/** The Promotions Message endpoint. */
	private String promotionsMsgEndpoint;

	/** The aem upload tax certificate endpoint. */
	private String aemUploadTaxCertificateEndpoint;

	/** The Update cart VAT Exempt status endpoint. */
	private String updatesCartVATExemptStatus;

	/** AntiBody Details EndPoint. */
	private String antiBodyDetailsEndPoint;

	/** Format Details EndPoint. */
	private String formatsDetailsEndPoint;

	/** Formats and Clones Price Data EndPoint. */
	private String priceDataEndPoint;

	/** The get user orders list endpoint. */
	private String getUserOrdersListEndpoint;

	/** The get order details endpoint. */
	private String getOrderDetailsEndpoint;

	/** The get order packing list endpoint. */
	private String getOrderPackingListEndpoint;

	/** The search results servlet path. */
	private String searchResultsServletPath;

	/** The get product announcement endpoint. */
	private String productAnnouncements;

	/** The get hybris sign out endpoint. */
	private String getHybrisSignOutEndpoint;

	/** The dev sso sign out endpoint. */
	private String devSsoSignOutEndpoint;

	/** The dev sso sign out endpoint. */
	private String ciamSignOutEndpoint;

	/** The get sign out servlet path. */
	private String getSignOutServletPath;

	/** The add All Items To Cart Endpoint. */
	private String addAllItemsToCartEndpoint;

	/** The Update VAT Exempt status endpoint. */
	private String updatesVATExemptStatus;

	/** The checkout validation endpoint. */
	private String validateMyCart;

	/** The ship to address config endpoint. */
	private String shipToAddressConfig;

	/** The shipping details config endpoint. */
	private String shippingDetailsConfig;

	/** The billing details config endpoint. */
	private String billingDetailsConfig;

	/** The apply coupon endpoint. */
	private String applyCoupon;

	/** The addressWithNoRuoEndpoint. */
	private String addressWithNoRuoEndpoint;

	/** The quick add bulk entry endpoint. */
	private String quickAddBulkEntryEndpoint;

	/** The quick add bulk upload endpoint. */
	private String quickAddBulkUploadEndpoint;

	/** The get Search List . */
	private String searchListEndpoint;

	/** The updateAddressConfig List . */
	private String updateAddressConfig;

	/** The promoProductsList Endpoint . */
	private String promoProductsListEndpoint;

	/** The quoteReference Endpoint . */
	private String quoteReference;

	/** The getQuote Endpoint . */
	private String getQuote;

	/** The quoteToCart Endpoint . */
	private String quoteToCart;

	/** The notification endpoint. */
	private String notificationEndpoint;

	/** The user idle time. */
	private int userIdleTime;

	/** The minimum products. */
	private int minimumProducts;

	/** The maximum products. */
	private int maximumProducts;

	/** Add New Address in Checkout. */
	private String addNewAddressEndPoint;

	/** The Ping ID SSO SignOut Domain *. */
	private String pingIdSsoSignOutDomain;

	/** The Ping ID SSO SignOut Domain *. */
	private String getPaymentDetails;

	/** The delete coupon. */
	private String deleteCoupon;

	/** The merge anonymous cart endpoint. */
	private String mergeAnonymousCartEndpoint;

	/** The getOtherDetails endpoint. */
	private String getOtherDetails;

	/** The order confirmation checkout endpoint. */
	private String orderConfirmationCheckoutConfig;

	/** The Get Product Details Endpoint endpoint. */
	private String getProductDetailsEndpoint;

	/** The order confirmation cancel order endpoint. */
	private String orderConfirmationCancelOrderConfig;

	/** The place order config endpoint. */
	private String placeOrderConfig;

	/** The post Smart Cart Register Endpoint. */
	private String postSmartCartRegisterEndpoint;

	/** The get Distributors Options Endpointt. */
	private String getDistributorsOptionsEndpoint;

	/** The punch out servlet endpoint. */
	private String punchOutEndpoint;

	/** The punchout transmit request. */
	private String punchoutTransmitRequest;

	/** The punchout cancel request. */
	private String punchoutCancelRequest;

	/** The getQuoteConfig. */
	private String quoteConfig;

	/** The quoteConfirmationConfig url. */
	private String quoteConfirmationConfig;

	/** The send email url. */
	private String sendEmail;

	/** The language search dropdown endpoint. */
	private String languageSearchDropdownEndpoint;

	/** The get order inquiry detail endpoint. */
	private String getOrderInquiryDetailEndpoint;

	/** The post order inquiry details endpoint. */
	private String postOrderInquiryDetailsEndpoint;

	/** The get refresh token endpoint. */
	private String getRefreshTokenEndpoint;

	/** The trigger re order endpoint. */
	private String triggerReOrderEndpoint;

	/** The myOrdersListEndpoint. */
	private String myOrdersListEndpoint;

	/** The allOrdersListEndpoint. */
	private String allOrdersListEndpoint;

	/** The beadlotsFileDownloadEndpoint. */
	private String beadlotsFileDownloadEndpoint;

	/** The beadlotsFileDownloadUsername. */
	private String beadlotsFileDownloadUsername;

	/** PbeadlotsFileDownloadPassword. */
	private String beadlotsFileDownloadPwd;

	/** beadlotsFileDownloadServletPath. */
	private String beadlotsFileDownloadServletPath;

	/** The sdsEndpointUser. */
	private String sdsEndpointUser;

	/** The sdsEndpointPassword. */
	private String sdsEndpointPassword;

	/** The tdsEndpoint. */
	private String tdsEndpoint;
	
	
	/** The sdsEndpoint. */
	private String sdsEndpoint;

	/** The sds pdf search endpoint. */
	private String sdsPdfSearchEndpoint;

	/** The sds pdf download endpoint. */
	private String sdsPdfDownloadEndpoint;

	/** The cancel order endpoint. */
	private String cancelOrderEndpoint;

	/** The required Product End Point. */
	private String requiredCompanionProductsEndpoint;

	/** The Price End Point. */
	private String getPriceEndpoint;
	/** The paymetric token endpoint. */
	private String paymetricTokenEndpoint;

	/** The paymetric domain. */
	private String paymetricDomain;

	/** The paymetric iframe endpoint. */
	private String paymetricIframeEndpoint;

	/** The brightcove player id. */
	private String brightcovePlayerId;

	/** The brightcove account id. */
	private String brightcoveAccountId;

	/** The brightcove account id. */
	private String getAllPaymentDetails;

	/** The updateRewardsPreferencesEndpoint. */
	private String updateRewardsPreferencesEndpoint;

	/** The annexSiteId. */
	private String annexSiteId;

	/** The annexSiteJs. */
	private String annexSiteJs;

	/** The annexSiteDomain. */
	private String annexSiteDomain;

	/** The summaryTabSrcEndpoint. */
	private String summaryTabSrcEndpoint;

	/** The rewardsTabSrcEndpoint. */
	private String rewardsTabSrcEndpoint;

	/** The earnTabSrcEndpoint. */
	private String earnTabSrcEndpoint;

	/** The activityTabSrcEndpoint. */
	private String activityTabSrcEndpoint;

	/** The search order lookup endpoint. */
	private String searchOrderLookupEndpoint;

	/** The vialImagesBasePath. */
	private String vialImagesBasePath;

	/** The getDropshipDetailsEndpoint. */
	private String getDropshipDetailsEndpoint;

	/** The getScientificResourceFolder. */
	private String getScientificResourceFolder;

	/** The getScientificResPath. */
	private String getScientificResPath;

	/** The RuovialImagesBasePath. */
	private String ruoVialImagesBasePath;

	/** The ruoVialBasedOnStorageBuffer. */
	private String ruoVialBasedOnStorageBuffer;

	/** The ruoVialBasedOnVolPerTest. */
	private String ruoVialBasedOnVolPerTest;

	/** The ruoVialBasedOnProductType. */
	private String ruoVialBasedOnProductType;

	/** The formatImagesBasePath. */
	private String formatImagesBasePath;

	/** The environment type. */
	private String environmentType;

	/** The Prod User Name. */
	private String prodUserName;

	/** The Prod User Password. */
	private String prodUserPassword;

	/** The tracking number config. */
	private String trackingNumberConfig;

	/** The Custom Run mode *. */
	private String customRunMode;

	/** The event topic dropdown endpoint. */
	private String eventTopicDropdownEndpoint;

	/** The email validation endpoint. */
	private String emailValidationEndpoint;

	/** The language country mapping. */
	private String countryLanguageMapping;

	/** The clear Cart configs. */
	private String clearCart;

	private String citeabApiKey;

	private String citeabCompanySlug;

	private String citeabScriptUrl;



	/**
	 * Activate.
	 *
	 * @param config the config
	 */
	@Activate
	@Modified
	protected final void activate(Configuration config) {
		this.bdbHybrisDomain = config.bdbHybrisDomain();
		this.captchaClientKey = config.captchaClientKey();
		this.anonymousTokenServletPath = config.anonymousTokenServletPath();
		this.regionCountryDropdownEndpoint = config.regionCountryDropdownEndpoint();
		this.hybrisSignUpEndpoint = config.hybrisSignUpEndpoint();
		this.hybrisSalesRepListEndpoint = config.hybrisSalesRepListEndpoint();
		this.imageRootDir = config.imageRootDir();
		this.hybrisSignUpPreferenceEndpoint = config.hybrisSignUpPreferenceEndpoint();
		this.hybrisResetPasswordEndpoint = config.hybrisResetPasswordEndpoint();
		this.hybrisValidatePasswordEndpoint = config.hybrisValidatePasswordEndpoint();
		this.countriesFromRegionServletPath = config.countriesFromRegionServletPath();
		this.commerceBoxAPIEndpoint = config.commerceBoxAPIEndpoint();
		this.purchasingAccountRegistrationEndpoint = config.purchasingAccountRegistrationEndpoint();
		this.uploadTaxCertificateEndpoint = config.uploadTaxCertificateEndpoint();
		this.pubMedId = config.pubMedIdEndpoint();
		this.countryStateDropdownEndpoint = config.countryStateDropdownEndpoint();
		this.statesFromCountryServletPath = config.statesFromCountryServletPath();
		this.testConfig = config.testConfig();
		this.getUserSettingsEndpoint = config.getUserSettingsEndpoint();
		this.updateUserDetailsEndpoint = config.updateUserDetailsEndpoint();
		this.updateUserPwdEndpoint = config.updateUserPwdEndpoint();
		this.requestTimeoutConfig = Integer.valueOf(config.requestTimeoutConfig());
		this.socketTimeoutConfig = Integer.valueOf(config.socketTimeoutConfig());
		this.connectTimeoutConfig = Integer.valueOf(config.connectTimeoutConfig());
		this.aemSignUpEndpoint = config.aemSignUpEndpoint();
		this.aemSignUpPreferenceEndpoint = config.aemSignUpPreferenceEndpoint();
		this.aemResetPasswordEndpoint = config.aemResetPasswordEndpoint();
		this.aemPurchasingAccountRegistrationEndpoint = config.aemPurchasingAccountRegistrationEndpoint();
		this.anonymousUserIdPlaceholder = config.anonymousUserIdPlaceholder();
		this.currentUserIdPlaceholder = config.currentUserIdPlaceholder();
		this.fetchAuthTokenEndpointClientId = config.fetchAuthTokenEndpointClientId();
		this.fetchAuthTokenEndpointGrantType = config.fetchAuthTokenEndpointGrantType();
		this.fetchAuthTokenEndpointClientSecret = config.fetchAuthTokenEndpointClientSecret();
		this.fetchAuthTokenEndpoint = config.fetchAuthTokenEndpoint();
		this.getListOfUserCertificationsEndpoint = config.getListOfUserCertificationsEndpoint();
		this.deleteCertificateEndpoint = config.deleteCertificateEndpoint();
		this.uploadRuoCertificateEndpoint = config.uploadRuoCertificateEndpoint();
		this.getAddressesEndpoint = config.getAddressesEndpoint();
		this.getAllCustomersCart = config.getAllCustomersCartEndpoint();
		this.addQuantity = config.addQuantityEndpoint();
		this.updateFavoriteAddressEndpoint = config.updateFavoriteAddressEndpoint();
		this.defaultAddressEndpoint = config.defaultAddressEndpoint();
		this.updateNicknameEndpoint = config.updateNicknameEndpoint();
		this.createAddressEndpoint = config.createAddressEndpoint();
		this.reactivateUserEndpoint = config.reactivateUserEndpoint();
		this.ssoLoginPingIdDomain = config.ssoLoginPingIdDomain();
		this.ssoLoginPingIdEndpoint = config.ssoLoginPingIdEndpoint();
		this.ssoLoginResponseType = config.ssoLoginResponseType();
		this.ssoLoginClientId = config.ssoLoginClientId();
		this.ssoCustomerLoginService = config.ssoCustomerLoginService();
		this.ssoLoginScope = config.ssoLoginScope();
		this.ciamPingIdDomain = config.ciamPingIdDomain();
		this.ciamLoginPingIdEndpoint = config.ciamLoginPingIdEndpoint();
		this.ciamB2CId = config.ciamB2CId();
		this.ciamLoginClientId = config.ciamLoginClientId();
		this.getCustomerLoginEndpoint = config.getCustomerLoginEndpoint();
		this.cookieExpiryTime = Integer.valueOf(config.cookieExpiryTime());
		this.createShoppingListEndpoint = config.createShoppingListEndpoint();
		this.getShoppingListDetailsEndpoint = config.getShoppingListDetailsEndpoint();
		this.getShoppingListEndpoint = config.getShoppingListEndpoint();
		this.getQuotesHistoryEndpoint = config.getQuotesHistoryEndpoint();
		this.getQuoteDetailsEndpoint = config.getQuoteDetailsEndpoint();
		this.fileUploadShoppingListEndpoint = config.fileUploadShoppingListEndpoint();
		this.removeShoppingListEndpoint = config.removeShoppingListEndpoint();
		this.shareShoppingListEndpoint = config.shareShoppingListEndpoint();
		this.exportShoppingListEndpoint = config.exportShoppingListEndpoint();
		this.removeShoppingListEntriesEndpoint = config.removeShoppingListEntriesEndpoint();
		this.updateShoppingListEntriesEndpoint = config.updateShoppingListEntriesEndpoint();
		this.fileUploadShoppingListEntriesEndpoint = config.fileUploadShoppingListEntriesEndpoint();
		this.cartWithIdentifier = config.getCartWithIdentifier();
		this.deleteEntryFromCart = config.getDeleteEntryFromCart();
		this.saveForLater = config.getSaveForLater();
		this.createSaveForLaterCart = config.getCreateSaveForLaterCart();
		this.addToSaveForLater = config.addToSaveForLater();
		this.bulkRemoveFromCart = config.bulkRemoveFromCart();
		this.bulkAddToSaveForLater = config.bulkAddToSaveForLater();
		this.replaceCartEntry = config.getReplaceCartEntry();
		this.replaceSaveForLaterEntry = config.getReplaceSaveForLaterEntry();
		this.addToCartFromSaveToLater = config.addToCartFromSaveToLater();
		this.deleteSaveForLater = config.deleteSaveForLater();
		this.cartConfigEndpoint = config.cartConfigEndpoint();
		this.countryDropdownEndpoint = config.countryDropdownEndpoint();
		this.languageDropdownEndpoint = config.languageDropdownEndpoint();
		this.getGrantsForCustomerEndpoint = config.getGrantsForCustomerEndpoint();
		this.applyGrantsOnCartEndpoint = config.applyGrantsOnCartEndpoint();
		this.orderHistoryForGrantsEndpoint = config.orderHistoryForGrantsEndpoint();
		this.getPaymentsEndpoint = config.getPaymentsEndpoint();
		this.addCreditCardEndpoint = config.addCreditCardEndpoint();
		this.removeCreditCardEndpoint = config.removeCreditCardEndpoint();
		this.updateCreditCardEndpoint = config.updateCreditCardEndpoint();
		this.miniCartEntriesConfigEndpoint = config.miniCartEntriesConfigEndpoint();
		this.miniCartCountConfigEndpoint = config.miniCartCountConfigEndpoint();
		this.updateCartQuantityEndpoint = config.updateCartQuantityEndpoint();
		this.updateLotIndicatorEndpoint = config.updateLotIndicatorEndpoint();
		this.orderDetailsApproverEndpoint = config.orderDetailsApproverEndpoint();
		this.orderListApproverEndpoint = config.orderListApproverEndpoint();
		this.orderApprovalDecisionEndpoint = config.orderApprovalDecisionEndpoint();
		this.updatePoNumberEndpoint = config.updatePoNumberEndpoint();
		this.promotionIdDetailsServletPath = config.promotionIdDetailsServletPath();
		this.promotionsMsgEndpoint = config.promotionsMsgEndpoint();
		this.aemUploadTaxCertificateEndpoint = config.aemUploadTaxCertificateEndpoint();
		this.updatesCartVATExemptStatus = config.updatesCartVATExemptStatus();
		this.getMessagesEndpoint = config.getMessagesEndpoint();
		this.readMessageEndpoint = config.readMessageEndpoint();
		this.getOrdersEndpoint = config.getOrdersEndpoint();
		this.getQuotesEndpoint = config.getQuotesEndpoint();
		this.antiBodyDetailsEndPoint = config.antiBodyDetailsEndPoint();
		this.formatsDetailsEndPoint = config.formatDetailsEndPoint();
		this.priceDataEndPoint = config.priceDataEndPoint();
		this.getUserOrdersListEndpoint = config.getUserOrdersListEndpoint();
		this.getOrderDetailsEndpoint = config.getOrderDetailsEndpoint();
		this.getOrderPackingListEndpoint = config.getOrderPackingListEndpoint();
		this.searchResultsServletPath = config.searchResultsServletPath();
		this.productAnnouncements = config.productAnnouncements();
		this.getHybrisSignOutEndpoint = config.getHybrisSignOutEndpoint();
		this.devSsoSignOutEndpoint = config.devSsoSignOutEndpoint();
		this.ciamSignOutEndpoint = config.ciamSignOutEndpoint();
		this.getSignOutServletPath = config.getSignOutServletPath();
		this.addAllItemsToCartEndpoint = config.addAllItemsToCartEndpoint();
		this.updatesVATExemptStatus = config.updatesVATExemptStatus();
		this.validateMyCart = config.validateMyCart();
		this.shipToAddressConfig = config.shipToAddressConfig();
		this.shippingDetailsConfig = config.shippingDetailsConfig();
		this.billingDetailsConfig = config.billingDetailsConfig();
		this.applyCoupon = config.applyCoupon();
		this.quickAddBulkEntryEndpoint = config.quickAddBulkEntryEndpoint();
		this.quickAddBulkUploadEndpoint = config.quickAddBulkUploadEndpoint();
		this.addressWithNoRuoEndpoint = config.addressWithNoRuoEndpoint();
		this.searchListEndpoint = config.searchListEndpoint();
		this.updateAddressConfig = config.updateAddressConfig();
		this.promoProductsListEndpoint = config.promoProductsListEndpoint();
		this.quoteReference = config.quoteReference();
		this.getQuote = config.getQuote();
		this.quoteToCart = config.quoteToCart();
		this.notificationEndpoint = config.notificationEndpoint();
		this.userIdleTime = config.userIdleTime();
		this.minimumProducts = config.minimumProducts();
		this.maximumProducts = config.maximumProducts();
		this.addNewAddressEndPoint = config.addNewAddressEndPoint();
		this.pingIdSsoSignOutDomain = config.pingIdSsoSignOutDomain();
		this.deleteCoupon = config.getDeleteCoupon();
		this.mergeAnonymousCartEndpoint = config.mergeAnonymousCartEndpoint();
		this.getPaymentDetails = config.getPaymentDetails();
		this.getOtherDetails = config.getOtherDetails();
		this.orderConfirmationCheckoutConfig = config.orderConfirmationCheckoutData();
		this.getProductDetailsEndpoint = config.getProductDetails();
		this.orderConfirmationCancelOrderConfig = config.orderConfirmationCancelOrder();
		this.getDistributorsOptionsEndpoint = config.getDistributorsOptionsEndpoint();
		this.postSmartCartRegisterEndpoint = config.postSmartCartRegisterEndpoint();
		this.placeOrderConfig = config.placeOrderConfig();
		this.punchOutEndpoint = config.punchOutEndpoint();
		this.punchoutTransmitRequest = config.punchoutTransmitRequestEndpoint();
		this.punchoutCancelRequest = config.punchoutCancelRequestEndpoint();
		this.quoteConfig = config.getQuoteConfig();
		this.quoteConfirmationConfig = config.quoteConfirmationConfig();
		this.sendEmail = config.sendEmail();
		this.languageSearchDropdownEndpoint = config.languageSearchDropdownEndpoint();
		this.getOrderInquiryDetailEndpoint = config.getOrderInquiryDetailEndpoint();
		this.postOrderInquiryDetailsEndpoint = config.postOrderInquiryDetailsEndpoint();
		this.getRefreshTokenEndpoint = config.getRefreshTokenEndpoint();
		this.triggerReOrderEndpoint = config.triggerReOrderEndpoint();
		this.beadlotsFileDownloadEndpoint = config.beadlotsFileDownloadEndpoint();
		this.beadlotsFileDownloadUsername = config.beadlotsFileDownloadUsername();
		this.beadlotsFileDownloadPwd = config.beadlotsFileDownloadPwd();
		this.beadlotsFileDownloadServletPath = config.beadlotsFileDownloadServletPath();
		this.myOrdersListEndpoint = config.myOrdersListEndpoint();
		this.allOrdersListEndpoint = config.allOrdersListEndpoint();
		this.sdsEndpointUser = config.sdsEndpointUser();
		this.sdsEndpointPassword = config.sdsEndpointPassword();
		this.tdsEndpoint = config.tdsEndpoint();
		this.sdsEndpoint = config.sdsEndpoint();
		this.sdsPdfSearchEndpoint = config.sdsPdfSearchEndpoint();
		this.sdsPdfDownloadEndpoint = config.sdsPdfDownloadEndpoint();
		this.cancelOrderEndpoint = config.cancelOrderEndpoint();
		this.requiredCompanionProductsEndpoint = config.requiredCompanionProductsEndpoint();
		this.getPriceEndpoint = config.getPriceEndPoint();
		this.cancelOrderEndpoint = config.cancelOrderEndpoint();
		this.paymetricTokenEndpoint = config.paymetricTokenEndpoint();
		this.paymetricDomain = config.paymetricDomain();
		this.paymetricIframeEndpoint = config.paymetricIframeEndpoint();
		this.brightcoveAccountId = config.brightcoveAccountId();
		this.brightcovePlayerId = config.brightcovePlayerId();
		this.getAllPaymentDetails = config.getAllPaymentDetails();
		this.updateRewardsPreferencesEndpoint = config.updateRewardsPreferencesEndpoint();
		this.annexSiteId = config.annexSiteId();
		this.annexSiteJs = config.annexSiteJs();
		this.annexSiteDomain = config.annexSiteDomain();
		this.summaryTabSrcEndpoint = config.summaryTabSrcEndpoint();
		this.rewardsTabSrcEndpoint = config.rewardsTabSrcEndpoint();
		this.earnTabSrcEndpoint = config.earnTabSrcEndpoint();
		this.activityTabSrcEndpoint = config.activityTabSrcEndpoint();
		this.searchOrderLookupEndpoint = config.searchOrderLookupEndpoint();
		this.vialImagesBasePath = config.vialImagesBasePath();
		this.getDropshipDetailsEndpoint = config.getDropshipDetailsEndpoint();
		this.getScientificResourceFolder = config.getScientificResourceFolder();
		this.getScientificResPath = config.getScientificResPath();
		this.ruoVialImagesBasePath = config.ruoVialImagesBasePath();
		this.ruoVialBasedOnStorageBuffer = config.ruoVialBasedOnStorageBuffer();
		this.ruoVialBasedOnVolPerTest = config.ruoVialBasedOnVolPerTest();
		this.ruoVialBasedOnProductType = config.ruoVialBasedOnProductType();
		this.formatImagesBasePath = config.formatImagesBasePath();
		this.environmentType = config.environmentType();
		this.prodUserName = config.prodUserName();
		this.prodUserPassword = config.prodUserPassword();
		this.trackingNumberConfig = config.trackingNumberConfig();
		this.customRunMode = config.customRunMode();
		this.eventTopicDropdownEndpoint = config.eventTopicDropdownEndpoint();
		this.emailValidationEndpoint = config.emailValidationEndpoint();
		this.countryLanguageMapping = config.countryLanguageMapping();
		this.clearCart = config.getClearCart();
		this.citeabApiKey = config.getCiteabApiKey();
		this.citeabCompanySlug = config.getCiteabCompanySlug();
		this.citeabScriptUrl = config.getCiteabScriptUrl();
		//this.showCiteabWidget = config.showCiteabWidget();

	}

	/**
	 * Deactivate.
	 */
	@Deactivate
	protected void deactivate() {
		// DoNothing
	}

	/**
	 * Gets the clear cart config.
	 *
	 * @return the clear cart
	 */
	public String getClearCart() {
		return clearCart;
	}

	/**
	 * Gets the ruo vial images base path.
	 *
	 * @return the ruo vial images base path
	 */
	public String getRuoVialImagesBasePath() {
		return ruoVialImagesBasePath;
	}

	/**
	 * Gets the ruo vial based on storage buffer.
	 *
	 * @return the ruo vial based on storage buffer
	 */
	public String getRuoVialBasedOnStorageBuffer() {
		return ruoVialBasedOnStorageBuffer;
	}

	/**
	 * Gets the ruo vial based on vol per test.
	 *
	 * @return the ruo vial based on vol per test
	 */
	public String getRuoVialBasedOnVolPerTest() {
		return ruoVialBasedOnVolPerTest;
	}

	/**
	 * Gets the ruo vial based on product type.
	 *
	 * @return the ruo vial based on product type
	 */
	public String getRuoVialBasedOnProductType() {
		return ruoVialBasedOnProductType;
	}

	/**
	 * Gets the format images base path.
	 *
	 * @return the format images base path
	 */
	public String getFormatImagesBasePath() {
		return formatImagesBasePath;
	}

	/**
	 * Gets the vialImagesBasePath.
	 *
	 * @return the vialImagesBasePath
	 */
	public String getVialImagesBasePath() {
		return vialImagesBasePath;
	}

	/**
	 * Gets the delete coupon.
	 *
	 * @return the delete coupon
	 */
	public String getDeleteCoupon() {
		return deleteCoupon;
	}

	/**
	 * @return the imageRootDir
	 */
	@Override
	public String getImageRootDir() {
		return imageRootDir;
	}

	@Override
	public String getCiteabApiKey() { return citeabApiKey;}

	@Override
	public String getCiteabCompanySlug() { return citeabCompanySlug;}

	@Override
	public String getCiteabScriptUrl() { return citeabScriptUrl;}






	/**
	 * Get the Ping ID SSO Signout Domain.
	 *
	 * @return the ping id sso sign out domain
	 */
	@Override
	public String getPingIdSsoSignOutDomain() {
		return pingIdSsoSignOutDomain;
	}

	/**
	 * Gets the BDB Hybris domain.
	 *
	 * @return the BDB hybris domain
	 */
	@Override
	public String getBDBHybrisDomain() {
		return bdbHybrisDomain;
	}

	/**
	 * Gets the captcha client key.
	 *
	 * @return the captcha client key
	 */
	public String getCaptchaClientKey() {
		return captchaClientKey;
	}

	/**
	 * Gets the Anonymous token servlet path.
	 *
	 * @return the Anonymous token servlet path.
	 */
	public String getAnonymousTokenServletPath() {
		return anonymousTokenServletPath;
	}

	/**
	 * Gets the region country dropdown end point.
	 *
	 * @return the region country dropdown end point
	 */
	public String getRegionCountryDropdownEndpoint() {
		return regionCountryDropdownEndpoint;
	}

	/**
	 * Gets the hybris SignUp Endpoint.
	 *
	 * @return the hybris SignUp Endpoint
	 */
	public String getHybrisSignUpEndpoint() {
		return hybrisSignUpEndpoint;
	}

	/**
	 * @return the hybrisSalesRespListEndpoint
	 */
	@Override
	public String getHybrisSalesRepListEndpoint() {
		return hybrisSalesRepListEndpoint;
	}

	/**
	 * Gets the hybris SignUp Preference Endpoint.
	 *
	 * @return the hybris SignUp Preference Endpoint
	 */
	public String getHybrisSignUpPreferenceEndpoint() {
		return hybrisSignUpPreferenceEndpoint;
	}

	/**
	 * Gets the hybris Reset Password Endpoint.
	 *
	 * @return the hybris Reset Password Endpoint
	 */
	public String getHybrisResetPasswordEndpoint() {
		return hybrisResetPasswordEndpoint;
	}

	/**
	 * Gets the hybris validate password endpoint.
	 *
	 * @return the hybris validate password endpoint
	 */
	@Override
	public String getHybrisValidatePasswordEndpoint() {
		return hybrisValidatePasswordEndpoint;
	}

	/**
	 * Gets the countries From Region Servlet Path.
	 *
	 * @return the countriesFromRegionServletPath
	 */
	public String getCountriesFromRegionServletPath() {
		return countriesFromRegionServletPath;
	}

	/**
	 * Gets CommerceBox API Endpoint.
	 *
	 * @return the commerceBoxAPIEndpoint
	 */
	public String getCommerceBoxAPIEndpoint() {
		return commerceBoxAPIEndpoint;
	}

	/**
	 * Gets the purchasing account registration endpoint.
	 *
	 * @return the purchasing account registration endpoint
	 */
	public String getPurchasingAccountRegistrationEndpoint() {
		return purchasingAccountRegistrationEndpoint;
	}

	/**
	 * Gets the upload tax certificate endpoint.
	 *
	 * @return the upload tax certificate endpoint
	 */
	public String getUploadTaxCertificateEndpoint() {
		return uploadTaxCertificateEndpoint;
	}

	/**
	 * Gets the pub med id.
	 *
	 * @return the pub med id
	 */
	public String getPubMedId() {
		return pubMedId;
	}

	/**
	 * Gets the States From Country End Point.
	 *
	 * @return the statesFromCountryEndPoint
	 */
	public String getCountryStateDropdownEndpoint() {
		return countryStateDropdownEndpoint;
	}

	/**
	 * Gets the states From Country Servlet Path.
	 *
	 * @return the statesFromCountryServletPath
	 */
	public String getStatesFromCountryServletPath() {
		return statesFromCountryServletPath;
	}

	/**
	 * Gets the testConfig.
	 *
	 * @return the testConfig
	 */
	public String getTestConfig() {
		return testConfig;
	}

	/**
	 * Gets the getUserSettingsEndpoint.
	 *
	 * @return the getUserSettingsEndpoint
	 */
	public String getUserSettingsEndpoint() {
		return getUserSettingsEndpoint;
	}

	/**
	 * Gets the updateUserDetailsEndpoint.
	 *
	 * @return the updateUserDetailsEndpoint
	 */
	public String updateUserDetailsEndpoint() {
		return updateUserDetailsEndpoint;
	}

	/**
	 * Gets the updateUserPwdEndpoint.
	 *
	 * @return the updateUserPwdEndpoint
	 */
	public String updateUserPwdEndpoint() {
		return updateUserPwdEndpoint;
	}

	/**
	 * Gets the request timeout config.
	 *
	 * @return the request timeout config
	 */
	public int getRequestTimeoutConfig() {
		return requestTimeoutConfig;
	}

	/**
	 * Gets the socket timeout config.
	 *
	 * @return the socket timeout config
	 */
	public int getSocketTimeoutConfig() {
		return socketTimeoutConfig;
	}

	/**
	 * Gets the connect timeout config.
	 *
	 * @return the connect timeout config
	 */
	public int getConnectTimeoutConfig() {
		return connectTimeoutConfig;
	}

	/**
	 * Gets the cookie expiry time.
	 *
	 * @return the cookie expiry time
	 */
	@Override
	public int getCookieExpiryTime() {
		return cookieExpiryTime;
	}

	/**
	 * Gets the aem sign up endpoint.
	 *
	 * @return the aem sign up endpoint
	 */
	public String getAemSignUpEndpoint() {
		return aemSignUpEndpoint;
	}

	/**
	 * Gets the aem sign up preference endpoint.
	 *
	 * @return the aem sign up preference endpoint
	 */
	public String getAemSignUpPreferenceEndpoint() {
		return aemSignUpPreferenceEndpoint;
	}

	/**
	 * Gets the aem reset password endpoint.
	 *
	 * @return the aem reset password endpoint
	 */
	public String getAemResetPasswordEndpoint() {
		return aemResetPasswordEndpoint;
	}

	/**
	 * Gets the aem purchasing account registration endpoint.
	 *
	 * @return the aem purchasing account registration endpoint
	 */
	public String getAemPurchasingAccountRegistrationEndpoint() {
		return aemPurchasingAccountRegistrationEndpoint;
	}

	/**
	 * Gets the aem user id place holder.
	 *
	 * @return the aem user id place holder
	 */
	public String getAnonymousUserIdPlaceholder() {
		return anonymousUserIdPlaceholder;
	}

	/**
	 * Gets the current user id placeholder.
	 *
	 * @return the current user id placeholder
	 */
	public String getCurrentUserIdPlaceholder() {
		return currentUserIdPlaceholder;
	}

	/**
	 * Gets the fetch auth token endpoint client id.
	 *
	 * @return the fetch auth token endpoint client id
	 */
	public String getFetchAuthTokenEndpointClientId() {
		return fetchAuthTokenEndpointClientId;
	}

	/**
	 * Gets the fetch auth token endpoint grant type.
	 *
	 * @return the fetch auth token endpoint grant type
	 */
	public String getFetchAuthTokenEndpointGrantType() {
		return fetchAuthTokenEndpointGrantType;
	}

	/**
	 * Gets the fetch auth token endpoint client secret.
	 *
	 * @return the fetch auth token endpoint client secret
	 */
	public String getFetchAuthTokenEndpointClientSecret() {
		return fetchAuthTokenEndpointClientSecret;
	}

	/**
	 * Gets the fetch auth token endpoint.
	 *
	 * @return the fetch auth token endpoint
	 */
	public String getFetchAuthTokenEndpoint() {
		return fetchAuthTokenEndpoint;
	}

	/**
	 * Gets the gets the list of user certifications.
	 *
	 * @return the gets the list of user certifications
	 */
	public String getListOfUserCertificationsEndpoint() {
		return getListOfUserCertificationsEndpoint;
	}

	/**
	 * Delete certificate endpoint.
	 *
	 * @return the string
	 */
	public String deleteCertificateEndpoint() {
		return deleteCertificateEndpoint;
	}

	/**
	 * Upload ruo certificate endpoint.
	 *
	 * @return the string
	 */
	public String uploadRuoCertificateEndpoint() {
		return uploadRuoCertificateEndpoint;
	}

	/**
	 * Gets the addresses endpoint.
	 *
	 * @return the addresses endpoint
	 */
	public String getAddressesEndpoint() {
		return getAddressesEndpoint;
	}

	/**
	 * Gets the all customers cart endpoint.
	 *
	 * @return the all customers cart endpoint
	 */
	@Override
	public String getAllCustomersCartEndpoint() {
		return getAllCustomersCart;
	}

	/**
	 * Gets the adds the quantity endpoint.
	 *
	 * @return the adds the quantity endpoint
	 */
	@Override
	public String getaddQuantityEndpoint() {
		return addQuantity;
	}

	/**
	 * Update favorite address endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String updateFavoriteAddressEndpoint() {
		return updateFavoriteAddressEndpoint;
	}

	/**
	 * Default address endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String defaultAddressEndpoint() {
		return defaultAddressEndpoint;
	}

	/**
	 * Update nickname endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String updateNicknameEndpoint() {
		return updateNicknameEndpoint;
	}

	/**
	 * Creates the address endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String createAddressEndpoint() {
		return createAddressEndpoint;
	}

	/**
	 * Creates the reactivateUser Endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String reactivateUserEndpoint() {
		return reactivateUserEndpoint;
	}

	/**
	 * Sso login ping id domain.
	 *
	 * @return the string
	 */
	@Override
	public String ssoLoginPingIdDomain() {
		return ssoLoginPingIdDomain;
	}

	/**
	 * Sso login ping id endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String ssoLoginPingIdEndpoint() {
		return ssoLoginPingIdEndpoint;
	}

	/**
	 * Sso login response type.
	 *
	 * @return the string
	 */
	@Override
	public String ssoLoginResponseType() {
		return ssoLoginResponseType;
	}

	/**
	 * Sso login client id.
	 *
	 * @return the string
	 */
	@Override
	public String ssoLoginClientId() {
		return ssoLoginClientId;
	}

	/**
	 * Sso customer login service.
	 *
	 * @return the string
	 */
	@Override
	public String ssoCustomerLoginService() {
		return ssoCustomerLoginService;
	}

	/**
	 * Sso login scope.
	 *
	 * @return the string
	 */
	@Override
	public String ssoLoginScope() {
		return ssoLoginScope;
	}

	/**
	 * Gets the customer login endpoint.
	 *
	 * @return the customer login endpoint
	 */
	@Override
	public String getCustomerLoginEndpoint() {
		return getCustomerLoginEndpoint;
	}

	/**
	 * Gets the creates the shopping list endpoint.
	 *
	 * @return the creates the shopping list endpoint
	 */
	@Override
	public String getCreateShoppingListEndpoint() {
		return createShoppingListEndpoint;
	}

	/**
	 * Gets the gets the shopping list details endpoint.
	 *
	 * @return the gets the shopping list details endpoint
	 */
	@Override
	public String getGetShoppingListDetailsEndpoint() {
		return getShoppingListDetailsEndpoint;
	}

	/**
	 * Gets the gets the shopping list endpoint.
	 *
	 * @return the gets the shopping list endpoint
	 */
	@Override
	public String getGetShoppingListEndpoint() {
		return getShoppingListEndpoint;
	}

	/**
	 * Gets the gets the Quotes History endpoint.
	 *
	 * @return the gets the Quotes History endpoint
	 */
	@Override
	public String getGetQuotesHistoryEndpoint() {
		return getQuotesHistoryEndpoint;
	}

	/**
	 * Gets the gets the Quotes Details endpoint.
	 *
	 * @return the gets the Quotes Details endpoint
	 */
	@Override
	public String getGetQuoteDetailsEndpoint() {
		return getQuoteDetailsEndpoint;
	}

	/**
	 * Gets the file upload shopping list endpoint.
	 *
	 * @return the file upload shopping list endpoint
	 */
	@Override
	public String getFileUploadShoppingListEndpoint() {
		return fileUploadShoppingListEndpoint;
	}

	/**
	 * Gets the removes the shopping list endpoint.
	 *
	 * @return the removes the shopping list endpoint
	 */
	@Override
	public String getRemoveShoppingListEndpoint() {
		return removeShoppingListEndpoint;
	}

	/**
	 * Gets the share shopping list endpoint.
	 *
	 * @return the share shopping list endpoint
	 */
	@Override
	public String getShareShoppingListEndpoint() {
		return shareShoppingListEndpoint;
	}

	/**
	 * Gets the export shopping list endpoint.
	 *
	 * @return the export shopping list endpoint
	 */
	@Override
	public String getExportShoppingListEndpoint() {
		return exportShoppingListEndpoint;
	}

	/**
	 * Gets the removes the shopping list entries endpoint.
	 *
	 * @return the removes the shopping list entries endpoint
	 */
	@Override
	public String getRemoveShoppingListEntriesEndpoint() {
		return removeShoppingListEntriesEndpoint;
	}

	/**
	 * Gets the update shopping list entries endpoint.
	 *
	 * @return the update shopping list entries endpoint
	 */
	@Override
	public String getUpdateShoppingListEntriesEndpoint() {
		return updateShoppingListEntriesEndpoint;
	}

	/**
	 * Gets the file upload shopping list entries endpoint.
	 *
	 * @return the file upload shopping list entries endpoint
	 */
	@Override
	public String getFileUploadShoppingListEntriesEndpoint() {
		return fileUploadShoppingListEntriesEndpoint;
	}

	/**
	 * Gets the cart with identifier.
	 *
	 * @return the cart with identifier
	 */
	public String getCartWithIdentifier() {
		return cartWithIdentifier;
	}

	/**
	 * Gets the delete entry from cart.
	 *
	 * @return the delete entry from cart
	 */
	public String getDeleteEntryFromCart() {
		return deleteEntryFromCart;
	}

	/**
	 * Gets the save for later.
	 *
	 * @return the save for later
	 */
	public String getSaveForLater() {
		return saveForLater;
	}

	/**
	 * Gets the creates the save for later cart.
	 *
	 * @return the creates the save for later cart
	 */
	public String getCreateSaveForLaterCart() {
		return createSaveForLaterCart;
	}

	/**
	 * Gets the Replace Cart Entry.
	 *
	 * @return the Replace Cart Entry
	 */
	public String getReplaceCartEntry() {
		return replaceCartEntry;
	}

	/**
	 * Gets the Replace Save For Later Entry.
	 *
	 * @return the Replace Save For Later Entry
	 */
	public String getReplaceSaveForLaterEntry() {
		return replaceSaveForLaterEntry;
	}

	/**
	 * Gets the adds the to save for later.
	 *
	 * @return the adds the to save for later
	 */
	public String getAddToSaveForLater() {
		return addToSaveForLater;
	}

	/**
	 * Gets the bulk remove from cart.
	 *
	 * @return the bulk remove from cart
	 */
	@Override
	public String getBulkRemoveFromCart() {
		return bulkRemoveFromCart;
	}

	/**
	 * Gets the bulk add to save for later.
	 *
	 * @return the bulk add to save for later
	 */
	@Override
	public String getBulkAddToSaveForLater() {
		return bulkAddToSaveForLater;
	}

	/**
	 * Gets the adds the to cart from save to later.
	 *
	 * @return the adds the to cart from save to later
	 */
	public String getAddToCartFromSaveToLater() {
		return addToCartFromSaveToLater;
	}

	/**
	 * Gets the delete save for later.
	 *
	 * @return the delete save for later
	 */
	public String getDeleteSaveForLater() {
		return deleteSaveForLater;
	}

	/**
	 * Cart config endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String cartConfigEndpoint() {
		return cartConfigEndpoint;
	}

	/**
	 * Country dropdown endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String countryDropdownEndpoint() {
		return countryDropdownEndpoint;
	}

	/**
	 * Language dropdown endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String languageDropdownEndpoint() {
		return languageDropdownEndpoint;
	}

	/**
	 * Gets the grants for customer endpoint.
	 *
	 * @return the grants for customer endpoint
	 */
	@Override
	public String getGrantsForCustomerEndpoint() {
		return getGrantsForCustomerEndpoint;
	}

	/**
	 * Gets the apply grants to cart endpoint.
	 *
	 * @return the apply grants to cart endpoint
	 */
	@Override
	public String applyGrantsOnCartEndpoint() {
		return applyGrantsOnCartEndpoint;
	}

	/**
	 * Order history for grants endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String orderHistoryForGrantsEndpoint() {
		return orderHistoryForGrantsEndpoint;
	}

	/**
	 * Update Cart Quantity.
	 *
	 * @return the string
	 */
	@Override
	public String getUpdateCartQuantity() {
		return updateCartQuantityEndpoint;
	}

	/**
	 * Update Lot Indicator.
	 *
	 * @return the string
	 */
	@Override
	public String getUpdateLotIndicator() {
		return updateLotIndicatorEndpoint;
	}

	/**
	 * getPaymentsEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getGetPaymentsEndpoint() {
		return getPaymentsEndpoint;
	}

	/**
	 * addCreditCardEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getAddCreditCardEndpoint() {
		return addCreditCardEndpoint;
	}

	/**
	 * removeCreditCardEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getRemoveCreditCardEndpoint() {
		return removeCreditCardEndpoint;
	}

	/**
	 * updateCreditCardEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getUpdateCreditCardEndpoint() {
		return updateCreditCardEndpoint;
	}

	/**
	 * getGetMessagesEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getGetMessagesEndpoint() {
		return getMessagesEndpoint;
	}

	/**
	 * getReadMessageEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getReadMessageEndpoint() {
		return readMessageEndpoint;
	}

	/**
	 * getGetQuotesEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getGetQuotesEndpoint() {
		return getQuotesEndpoint;
	}

	/**
	 * getGetOrdersEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getGetOrdersEndpoint() {
		return getOrdersEndpoint;
	}

	/**
	 * Mini cart count config endpoint.
	 *
	 * @return Mini Cart Count EndPoint
	 */
	@Override
	public String miniCartCountConfigEndpoint() {
		return miniCartCountConfigEndpoint;
	}

	/**
	 * Mini cart entries config endpoint.
	 *
	 * @return mini Cart Entries EndPoint
	 */
	@Override
	public String miniCartEntriesConfigEndpoint() {
		return miniCartEntriesConfigEndpoint;
	}

	/**
	 * Order details approver endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String orderDetailsApproverEndpoint() {
		return orderDetailsApproverEndpoint;
	}

	/**
	 * Order list approver endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String orderListApproverEndpoint() {
		return orderListApproverEndpoint;
	}

	/**
	 * Order approval decision endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String orderApprovalDecisionEndpoint() {
		return orderApprovalDecisionEndpoint;
	}

	/**
	 * Update po number endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String updatePoNumberEndpoint() {
		return updatePoNumberEndpoint;
	}

	/**
	 * Gets the promotion id details servlet path.
	 *
	 * @return the promotion id details servlet path
	 */
	@Override
	public String getPromotionIdDetailsServletPath() {
		return promotionIdDetailsServletPath;
	}

	/**
	 * Gets the promotions msg endpoint.
	 *
	 * @return the promotions msg endpoint
	 */
	@Override
	public String getPromotionsMsgEndpoint() {
		return promotionsMsgEndpoint;
	}

	/**
	 * Update cart VAT Exempt status endpoint. Aem upload tax certificate endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String aemUploadTaxCertificateEndpoint() {
		return aemUploadTaxCertificateEndpoint;
	}

	/**
	 * Gets the updates cart VAT exempt status.
	 *
	 * @return the updates cart VAT exempt status
	 */
	public String getUpdatesCartVATExemptStatus() {
		return updatesCartVATExemptStatus;
	}

	/**
	 * Gets the anti body details end point.
	 *
	 * @return antiBodyDetailsEndPoint
	 */
	@Override
	public String getAntiBodyDetailsEndPoint() {
		return antiBodyDetailsEndPoint;
	}

	/**
	 * Gets the anti body price data end point.
	 *
	 * @return antiBodyDetailsPriceData EndPoint
	 */
	@Override
	public String getPriceDataEndPoint() {
		return priceDataEndPoint;
	}

	/**
	 * Gets the formats details end point.
	 *
	 * @return formatsDetailsEndPoint
	 */
	@Override
	public String getFormatsDetailsEndPoint() {
		return formatsDetailsEndPoint;
	}

	/**
	 * Gets the user orders list endpoint.
	 *
	 * @return the user orders list endpoint
	 */
	@Override
	public String getUserOrdersListEndpoint() {
		return getUserOrdersListEndpoint;
	}

	/**
	 * Gets the order details endpoint.
	 *
	 * @return the order details endpoint
	 */
	@Override
	public String getOrderDetailsEndpoint() {
		return getOrderDetailsEndpoint;
	}

	/**
	 * Gets the order packing list endpoint.
	 *
	 * @return the order packing list endpoint
	 */
	@Override
	public String getOrderPackingListEndpoint() {
		return getOrderPackingListEndpoint;
	}

	/**
	 * Gets the hybris sign out endpoint.
	 *
	 * @return the hybris sign out endpoint
	 */
	@Override
	public String getHybrisSignOutEndpoint() {
		return getHybrisSignOutEndpoint;
	}

	/**
	 * Dev sso sign out Endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String devSsoSignOutEndpoint() {
		return devSsoSignOutEndpoint;
	}

	/**
	 * ciam sign out Endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String ciamSignOutEndpoint() {
		return ciamSignOutEndpoint;
	}

	/**
	 * Gets the sign out servlet path.
	 *
	 * @return the sign out servlet path
	 */
	@Override
	public String getSignOutServletPath() {
		return getSignOutServletPath;
	}

	/**
	 * Gets the search results servlet path.
	 *
	 * @return the search results servlet path
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bdb.aem.core.services.BDBApiEndpointService#getSearchResultsServletPath()
	 */
	@Override
	public String getSearchResultsServletPath() {
		return searchResultsServletPath;
	}

	/**
	 * getProductAnnouncements.
	 *
	 * @return path
	 */
	@Override
	public String getProductAnnouncements() {
		return productAnnouncements;
	}

	/**
	 * get addAllItemsToCart Endpoint.
	 *
	 * @return path
	 */
	@Override
	public String getAddAllItemsToCartEndpoint() {
		return addAllItemsToCartEndpoint;
	}

	/**
	 * Update VAT Exempt status.
	 *
	 * @return the string
	 */
	@Override
	public String getUpdateVATExemptStatus() {
		return updatesVATExemptStatus;
	}

	/**
	 * Checkout validation.
	 *
	 * @return the string
	 */
	@Override
	public String getValidateMyCart() {
		return validateMyCart;
	}

	/**
	 * Ship to address config.
	 *
	 * @return the string
	 */
	@Override
	public String getShipToAddressConfig() {
		return shipToAddressConfig;
	}

	/**
	 * Shipping Details config.
	 *
	 * @return the string
	 */
	@Override
	public String getShippingDetailsConfig() {
		return shippingDetailsConfig;
	}

	/**
	 * Billing Details config.
	 *
	 * @return the string
	 */
	@Override
	public String getBillingDetailsConfig() {
		return billingDetailsConfig;
	}

	/**
	 * Apply Coupon config.
	 *
	 * @return the string
	 */
	@Override
	public String getApplyCoupon() {
		return applyCoupon;
	}

	/**
	 * getAddressWithNoRuoEndpoint.
	 *
	 * @return getAddressWithNoRuoEndpoint
	 */
	@Override
	public String getAddressWithNoRuoEndpoint() {
		return addressWithNoRuoEndpoint;
	}

	/**
	 * Quick Add Bulk Entry config.
	 *
	 * @return the string
	 */
	@Override
	public String getQuickAddBulkEntryEndpoint() {
		return quickAddBulkEntryEndpoint;
	}

	/**
	 * Quick Add Bulk Upload config.
	 *
	 * @return the string
	 */
	@Override
	public String getQuickAddBulkUploadEndpoint() {
		return quickAddBulkUploadEndpoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bdb.aem.core.services.BDBApiEndpointService#getSearchListEndpoint()
	 */

	/**
	 * Gets the search list endpoint.
	 *
	 * @return the search list endpoint
	 */
	@Override
	public String getSearchListEndpoint() {
		return searchListEndpoint;
	}

	/**
	 * Gets the update address config.
	 *
	 * @return the update address config
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bdb.aem.core.services.BDBApiEndpointService#getUpdateAddressConfig()
	 */
	@Override
	public String getUpdateAddressConfig() {
		return updateAddressConfig;
	}

	/**
	 * Gets the promo products list endpoint.
	 *
	 * @return the promo products list endpoint
	 */
	@Override
	public String getPromoProductsListEndpoint() {
		return promoProductsListEndpoint;
	}

	/**
	 * getQuoteReference.
	 *
	 * @return getQuoteReference endpoint
	 */
	@Override
	public String getQuoteReference() {
		return quoteReference;
	}

	/**
	 * getGetQuote.
	 *
	 * @return getGetQuote
	 */
	@Override
	public String getGetQuote() {
		return getQuote;
	}

	/**
	 * getQuoteToCart.
	 *
	 * @return getQuoteToCart
	 */
	@Override
	public String getQuoteToCart() {
		return quoteToCart;
	}

	/**
	 * Notification endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String notificationEndpoint() {
		return notificationEndpoint;
	}

	/**
	 * User Idle Time.
	 *
	 * @return the int
	 */
	@Override
	public int getUserIdleTime() {
		return userIdleTime;
	}

	/**
	 * Minimum Products.
	 *
	 * @return the int
	 */
	@Override
	public int getMinimumProducts() {
		return minimumProducts;
	}

	/**
	 * Minimum Products.
	 *
	 * @return the int
	 */
	@Override
	public int getMaximumProducts() {
		return maximumProducts;
	}

	/**
	 * Adds the new address end point.
	 *
	 * @return addNewAddressEndPoint
	 */
	@Override
	public String addNewAddressEndPoint() {
		return addNewAddressEndPoint;
	}

	/**
	 * Merge anonymous cart endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String mergeAnonymousCartEndpoint() {
		return mergeAnonymousCartEndpoint;
	}

	/**
	 * getPayments endpoint.
	 *
	 * @return the getPayments
	 */
	@Override
	public String getPaymentDetails() {
		return getPaymentDetails;
	}

	/**
	 * getOtherDetails endpoint.
	 *
	 * @return the getPayments
	 */
	@Override
	public String getOtherDetails() {
		return getOtherDetails;
	}

	/**
	 * Order Confirmation Checkout endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getOrderConfirmationCheckoutConfig() {
		return orderConfirmationCheckoutConfig;
	}

	/**
	 * Get Product Details Endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getProductDetailsEndpoint() {
		return getProductDetailsEndpoint;
	}

	/**
	 * Order confirmation cancel order endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getOrderConfirmationCancelOrderConfig() {
		return orderConfirmationCancelOrderConfig;
	}

	/**
	 * Place order config endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String placeOrderConfig() {
		return placeOrderConfig;
	}

	/**
	 * Get the post Smart Cart Register Endpoint.
	 *
	 * @return String
	 */
	@Override
	public String getPostSmartCartRegisterEndpoint() {
		return postSmartCartRegisterEndpoint;
	}

	/**
	 * Get the get Distributors Options Endpoint.
	 *
	 * @return String
	 */
	@Override
	public String getGetDistributorsOptionsEndpoint() {
		return getDistributorsOptionsEndpoint;
	}

	/**
	 * Get the Punch Out Servlet Path.
	 *
	 * @return the punch out endpoint
	 */
	@Override
	public String getPunchOutEndpoint() {
		return punchOutEndpoint;
	}

	/**
	 * Gets the punchout transmit request endpoint.
	 *
	 * @return the punchout transmit request endpoint
	 */
	@Override
	public String getPunchoutTransmitRequestEndpoint() {
		return punchoutTransmitRequest;
	}

	/**
	 * Gets the punchout cancel request endpoint.
	 *
	 * @return the punchout cancel request endpoint
	 */
	@Override
	public String getPunchoutCancelRequestEndpoint() {
		return punchoutCancelRequest;
	}

	/**
	 * Gets Quote Config.
	 *
	 * @return Quote Config endpoint.
	 */
	@Override
	public String getQuoteConfig() {

		return quoteConfig;
	}

	/**
	 * Gets the QuoteConfirmation url.
	 *
	 * @return QuoteConfirmation url
	 */
	@Override
	public String getQuoteConfirmationConfig() {
		return quoteConfirmationConfig;
	}

	/**
	 * Gets the send email url.
	 *
	 * @return sendEmail url
	 */
	@Override
	public String sendEmail() {
		return sendEmail;
	}

	/**
	 * Gets the language dropdown endpoint.
	 *
	 * @return the language dropdown endpoint
	 */
	@Override
	public String getLanguageSearchDropdownEndpoint() {
		return languageSearchDropdownEndpoint;
	}

	/**
	 * Gets the gets the order inquiry detail endpoint.
	 *
	 * @return the gets the order inquiry detail endpoint
	 */
	@Override
	public String getGetOrderInquiryDetailEndpoint() {
		return getOrderInquiryDetailEndpoint;
	}

	/**
	 * Gets the post order inquiry details endpoint.
	 *
	 * @return the post order inquiry details endpoint
	 */
	@Override
	public String getPostOrderInquiryDetailsEndpoint() {
		return postOrderInquiryDetailsEndpoint;
	}

	/**
	 * Gets the refresh token endpoint.
	 *
	 * @return the refresh token endpoint
	 */
	@Override
	public String getRefreshTokenEndpoint() {
		return getRefreshTokenEndpoint;
	}

	/**
	 * Trigger re order endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String triggerReOrderEndpoint() {
		return triggerReOrderEndpoint;
	}

	/**
	 * getBeadlotsFileDownloadEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getBeadlotsFileDownloadEndpoint() {
		return beadlotsFileDownloadEndpoint;
	}

	/**
	 * getBeadlotsFileDownloadUsername.
	 *
	 * @return the string
	 */
	@Override
	public String getBeadlotsFileDownloadUsername() {
		return beadlotsFileDownloadUsername;
	}

	/**
	 * TgetBeadlotsFileDownloadPassword.
	 *
	 * @return the string
	 */
	@Override
	public String getBeadlotsFileDownloadPwd() {
		return beadlotsFileDownloadPwd;
	}

	/**
	 * getBeadlotsFileDownloadServletPath.
	 *
	 * @return the string
	 */
	@Override
	public String getBeadlotsFileDownloadServletPath() {
		return beadlotsFileDownloadServletPath;
	}

	/**
	 * getMyOrdersListEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getMyOrdersListEndpoint() {
		return myOrdersListEndpoint;
	}

	/**
	 * getAllOrdersListEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getAllOrdersListEndpoint() {
		return allOrdersListEndpoint;
	}

	/**
	 * Cancel order endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String cancelOrderEndpoint() {
		return cancelOrderEndpoint;
	}

	/**
	 * Gets the sds endpoint user.
	 *
	 * @return the sds endpoint user
	 */
	public String getSdsEndpointUser() {
		return sdsEndpointUser;
	}

	/**
	 * Gets the sds endpoint password.
	 *
	 * @return the sds endpoint password
	 */
	public String getSdsEndpointPassword() {
		return sdsEndpointPassword;
	}

	/**
	 * Gets the tds endpoint.
	 *
	 * @return the tds endpoint
	 */
	public String getTdsEndpoint() {
		return tdsEndpoint;
	}

	/**
	 * Gets the sds endpoint.
	 *
	 * @return the sds endpoint
	 */
	public String getSdsEndpoint() {
		return sdsEndpoint;
	}

	/**
	 * Gets the sds pdf search endpoint.
	 *
	 * @return the sds pdf search endpoint
	 */
	public String getSdsPdfSearchEndpoint() {
		return sdsPdfSearchEndpoint;
	}

	/**
	 * Gets the sds pdf download endpoint.
	 *
	 * @return the sds pdf download endpoint
	 */
	public String getSdsPdfDownloadEndpoint() {
		return sdsPdfDownloadEndpoint;
	}

	/**
	 * getRequiredCompanionProducts endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getRequiredCompanionProductsConfig() {
		return requiredCompanionProductsEndpoint;
	}

	/**
	 * Gets the price config.
	 *
	 * @return the price config
	 */
	@Override
	public String getPriceConfig() {
		return getPriceEndpoint;
	}

	/**
	 * Paymetric token endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String paymetricTokenEndpoint() {
		return paymetricTokenEndpoint;
	}

	/**
	 * Paymetric domain.
	 *
	 * @return the string
	 */
	@Override
	public String paymetricDomain() {
		return paymetricDomain;
	}

	/**
	 * Paymetric iframe endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String paymetricIframeEndpoint() {
		return paymetricIframeEndpoint;
	}

	/**
	 * Brightcove Account Id.
	 *
	 * @return the string
	 */
	@Override
	public String brightcoveAccountId() {
		return brightcoveAccountId;
	}

	/**
	 * Brightcove player Id.
	 *
	 * @return the string
	 */
	@Override
	public String brightcovePlayerId() {
		return brightcovePlayerId;
	}

	/**
	 * Gets the update rewards preferences endpoint.
	 *
	 * @return the update rewards preferences endpoint
	 */
	@Override
	public String getUpdateRewardsPreferencesEndpoint() {
		return updateRewardsPreferencesEndpoint;
	}

	/**
	 * Gets the annex site id.
	 *
	 * @return the annex site id
	 */
	@Override
	public String getAnnexSiteId() {
		return annexSiteId;
	}

	/**
	 * Gets the annex site js.
	 *
	 * @return the annex site js
	 */
	@Override
	public String getAnnexSiteJs() {
		return annexSiteJs;
	}

	/**
	 * Gets the annex site domain.
	 *
	 * @return the annex site domain
	 */
	@Override
	public String getAnnexSiteDomain() {
		return annexSiteDomain;
	}

	/**
	 * Gets the summary tab src endpoint.
	 *
	 * @return the summary tab src endpoint
	 */
	@Override
	public String getSummaryTabSrcEndpoint() {
		return summaryTabSrcEndpoint;
	}

	/**
	 * Gets the rewards tab src endpoint.
	 *
	 * @return the rewards tab src endpoint
	 */
	@Override
	public String getRewardsTabSrcEndpoint() {
		return rewardsTabSrcEndpoint;
	}

	/**
	 * Gets the earn tab src endpoint.
	 *
	 * @return the earn tab src endpoint
	 */
	@Override
	public String getEarnTabSrcEndpoint() {
		return earnTabSrcEndpoint;
	}

	/**
	 * Gets the activity tab src endpoint.
	 *
	 * @return the activity tab src endpoint
	 */
	@Override
	public String getActivityTabSrcEndpoint() {
		return activityTabSrcEndpoint;
	}

	/**
	 * Environment type.
	 *
	 * @return the string
	 */
	@Override
	public String environmentType() {
		return environmentType;
	}

	/**
	 * getAllPaymentDetails.
	 *
	 * @return the string
	 */
	@Override
	public String getAllPaymentDetails() {
		return getAllPaymentDetails;
	}

	/**
	 * Gets the search order lookup endpoint.
	 *
	 * @return the search order lookup endpoint
	 */
	@Override
	public String getSearchOrderLookupEndpoint() {
		return searchOrderLookupEndpoint;
	}

	/**
	 * getDropshipDetailsEndpoint.
	 *
	 * @return the string
	 */
	@Override
	public String getDropshipDetailsEndpoint() {
		return getDropshipDetailsEndpoint;
	}

	/**
	 * getScientificResourceFolder.
	 *
	 * @return the string
	 */
	@Override
	public String getScientificResourceFolder() {
		return getScientificResourceFolder;
	}

	/**
	 * getScientificResPath.
	 *
	 * @return the string
	 */
	@Override
	public String getScientificResPath() {
		return getScientificResPath;
	}

	/**
	 * Get the Production User Name.
	 *
	 * @return the Production User Name
	 */
	@Override
	public String getProdUserName() {
		return prodUserName;
	}

	/**
	 * Get the Production User Password.
	 *
	 * @return the Production User Password
	 */
	@Override
	public String getProdUserPassword() {
		return prodUserPassword;
	}

	/**
	 * Tracking number config.
	 *
	 * @return the string
	 */
	@Override
	public String trackingNumberConfig() {
		return trackingNumberConfig;
	}

	/**
	 * Gets the custome run mode (author, publish).
	 *
	 * @return the custom run mode
	 */
	@Override
	public String getCustomRunMode() {
		return customRunMode;
	}

	/**
	 * Event topic dropdown endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String eventTopicDropdownEndpoint() {
		return eventTopicDropdownEndpoint;
	}

	/**
	 * Email validation endpoint.
	 *
	 * @return the string
	 */
	@Override
	public String emailValidationEndpoint() {
		return emailValidationEndpoint;
	}

	@Override
	public String countryLanguageMapping() {
		return countryLanguageMapping;
	}

	/**
	 * ciam ping id domain.
	 *
	 * @return the ciamPingIdDomain
	 */
	@Override
	public String ciamPingIdDomain() {
		return ciamPingIdDomain;
	}

	/**
	 * ciam login ping id end point
	 *
	 * @return ciam login ping id end point
	 */
	@Override
	public String ciamLoginPingIdEndpoint() {
		return ciamLoginPingIdEndpoint;
	}

	/**
	 * ciam login client id
	 *
	 * @return ciam login client id
	 */
	@Override
	public String ciamLoginClientId() {
		return ciamLoginClientId;
	}

	/**
	 * ciam b2c id
	 *
	 * @return ciam b2c id
	 */
	@Override
	public String ciamB2CId() {
		return ciamB2CId;
	}

	/**
	 * The Interface Configuration.
	 */
	@ObjectClassDefinition(name = "BDB API Configuration")
	public @interface Configuration {
		/**
		 * Get the BDB hybris domain.
		 *
		 * @return the bdb hybris domain
		 */
		@AttributeDefinition(name = "bdbHybrisDomain", description = "BDB Hybris Domain", type = AttributeType.STRING)
		String bdbHybrisDomain() default "https://api-qa1.bdbiosciences.com";

		/**
		 * captcha client key.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "captchaClientKey", description = "BDB Google Re-captcha Client Key", type = AttributeType.STRING)
		String captchaClientKey() default "6Lf1V6YZAAAAAObvM_QEf7S9ZMHslwIf2LE7qpnM";

		/**
		 * Anonymous Token Servlet Path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "anonymousTokenServletPath", description = "BDB Anonymous Token Servlet Path", type = AttributeType.STRING)
		String anonymousTokenServletPath() default "/content/bdb/paths/bdb-anonymous-token";

		/**
		 * regionCountryDropdown endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "regionCountryDropdownEndpoint", description = "The endpoint used to fetch region and country dropdown", type = AttributeType.STRING)
		public String regionCountryDropdownEndpoint() default "/etc/acs-commons/lists/bdb/region-country-mapping";

		/**
		 * Step 1 endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "hybrisSignUpEndpoint", description = "The hybris endpoint for Sign up ", type = AttributeType.STRING)
		public String hybrisSignUpEndpoint() default "/occ/v2/{{site}}/users/{userId}/register";

		/**
		 * Step 1 endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "hybrisSalesRepListEndpoint", description = "The hybris endpoint for Sales Rep List ", type = AttributeType.STRING)
		public String hybrisSalesRepListEndpoint() default "/occ/v2/{{baseSiteId}}/getreptoolconfig ";

		/**
		 * Step 1 image dir.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "imageRootDir", description = "Image Root Directory ", type = AttributeType.STRING)
		public String imageRootDir() default "/content/dam/bdb/reptool/";

		/**
		 * Step 2 endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "hybrisSignUpPreferenceEndpoint", description = "The hybris endpoint for Sign up preference", type = AttributeType.STRING)
		public String hybrisSignUpPreferenceEndpoint() default "/occ/v2/{{site}}/users/{userId}/lineofwork";

		/**
		 * Hybris reset password endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "hybrisResetPasswordEndpoint", description = "The hybris endpoint used for reset password", type = AttributeType.STRING)
		public String hybrisResetPasswordEndpoint() default "/occ/v2/{{site}}/users/{userId}/resetpassword";

		/**
		 * Hybris validate password endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "hybrisValidatePasswordEndpoint", description = "The hybris endpoint used for validate password", type = AttributeType.STRING)
		public String hybrisValidatePasswordEndpoint() default "/occ/v2/{{site}}/users/{userId}/validatepasswordtoken";

		/**
		 * getCountriesFromRegionServletPath.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getCountriesFromRegionServletPath", description = "Get Countries From Region Servlet Path", type = AttributeType.STRING)
		public String countriesFromRegionServletPath() default "/content/bdb/paths/get-country-from-region.json";

		/**
		 * getCountriesFromRegionServletPath.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "CommerceBoxAPIEndpoint", description = "Get Commerce Box API Endpoint", type = AttributeType.STRING)
		public String commerceBoxAPIEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/products";

		/**
		 * Purchasing account registration endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "PurchasingAccountRegistrationEndpoint", description = "Get Purchasing Account Registration Endpoint", type = AttributeType.STRING)
		public String purchasingAccountRegistrationEndpoint() default "/occ/v2/{{site}}/users/{userId}/purchasingaccount";

		/**
		 * Upload tax certificate endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "UploadTaxCertificateEndpoint", description = "Get Upload Tax Certificate Endpoint", type = AttributeType.STRING)
		public String uploadTaxCertificateEndpoint() default "/occ/v2/{{site}}/users/{userId}/uploadcertificate";

		/**
		 * Pub med id endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "PubMedIdEndpoint", description = "Get PubMedId Endpoint", type = AttributeType.STRING)
		public String pubMedIdEndpoint() default "https://pubmed.ncbi.nlm.nih.gov/";

		/**
		 * regionCountryDropdown endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "countryStateDropdownEndpoint", description = "The endpoint used to fetch country and states dropdown", type = AttributeType.STRING)
		public String countryStateDropdownEndpoint() default "/etc/acs-commons/lists/bdb/country-state-mapping";

		/**
		 * getStateFromCountriesServletPath.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "statesFromCountryServletPath", description = "Get States From Country Servlet Path", type = AttributeType.STRING)
		public String statesFromCountryServletPath() default "/content/bdb/paths/get-states-from-country.json";

		/**
		 * getStateFromCountriesServletPath.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "testConfig", description = "testConfig", type = AttributeType.STRING)
		public String testConfig() default "testConfig: DefaultValue";

		/**
		 * get User Settings Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "GetUserSettingsEndpoint", description = "get User Settings Endpoint", type = AttributeType.STRING)
		public String getUserSettingsEndpoint() default "/occ/v2/{{site}}/users/{userId}/userdetails";

		/**
		 * Update User Details Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "UpdateUserDetailsEndpoint", description = "Update User Details Endpoint", type = AttributeType.STRING)
		public String updateUserDetailsEndpoint() default "/occ/v2/{{site}}/users/{userId}/updateinformation";

		/**
		 * Upload update User Pwd Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "UpdateUserPwdEndpoint", description = "Update User Pwd Endpoint", type = AttributeType.STRING)
		public String updateUserPwdEndpoint() default "/occ/v2/{{site}}/bdbusers/users/{userId}/password";

		/**
		 * Gets the customer login endpoint.
		 *
		 * @return the customer login endpoint
		 */
		@AttributeDefinition(name = "getCustomerLoginEndpoint", description = "Gets Customer Login Endpoint", type = AttributeType.STRING)
		public String getCustomerLoginEndpoint() default "/occ/v2/{{site}}/users/{userId}/login/token";

		/**
		 * Request timeout config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Request Timeout config for API calls", description = "Request Timeout config for API call", type = AttributeType.STRING)
		public String requestTimeoutConfig() default "30";

		/**
		 * Socket timeout config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Socket Timeout config for API calls", description = "Socket Timeout config for API call", type = AttributeType.STRING)
		public String socketTimeoutConfig() default "30";

		/**
		 * Cookie expiry time.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Cookie Expiry Time", description = "Cookie Expiry Time", type = AttributeType.STRING)
		public String cookieExpiryTime() default "3600";

		/**
		 * Socket timeout config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Socket Timeout config for API calls", description = "Socket Timeout config for API call", type = AttributeType.STRING)
		public String connectTimeoutConfig() default "30";

		/**
		 * Aem sign up endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "aemSignUpEndpoint", description = "The aem endpoint for Sign up ", type = AttributeType.STRING)
		public String aemSignUpEndpoint() default "/content/bdb/paths/register-user.signupcall.json";
		//

		/**
		 * Aem sign up preference endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "aemSignUpPreferenceEndpoint", description = "The aem endpoint for Sign up preference", type = AttributeType.STRING)
		public String aemSignUpPreferenceEndpoint() default "/content/bdb/paths/register-user.areaoffocus.json";

		/**
		 * reset password endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "aemResetPasswordEndpoint", description = "The aem endpoint used for reset password", type = AttributeType.STRING)
		public String aemResetPasswordEndpoint() default "/content/bdb/paths/register-user.resetpassword.json";

		/**
		 * Aem Purchasing account registration endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "aemPurchasingAccountRegistrationEndpoint", description = "Get aem Purchasing Account Registration Endpoint", type = AttributeType.STRING)
		public String aemPurchasingAccountRegistrationEndpoint() default "/content/bdb/paths/register-user.purchaseaccount.json";

		/**
		 * Aem Purchasing account registration endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "anonymousUserIdPlaceholder", description = "Get aem user id place holder text", type = AttributeType.STRING)
		public String anonymousUserIdPlaceholder() default "anonymous";

		/**
		 * Current user id placeholder.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "currentUserIdPlaceholder", description = "Get current aem user id place holder text", type = AttributeType.STRING)
		public String currentUserIdPlaceholder() default "current";

		/**
		 * Fetch auth token endpoint client id.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "fetchAuthTokenEndpointClientId", description = "Get client id value for fetch auth token endpoint", type = AttributeType.STRING)
		public String fetchAuthTokenEndpointClientId() default "aem";

		/**
		 * Fetch auth token endpoint grant type.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "fetchAuthTokenEndpointGrantType", description = "Get grant type value for fetch auth token endpoint", type = AttributeType.STRING)
		public String fetchAuthTokenEndpointGrantType() default "client_credentials";

		/**
		 * Fetch auth token endpoint client secret.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "fetchAuthTokenEndpointClientSecret", description = "Get client secret value for fetch auth token endpoint", type = AttributeType.PASSWORD)
		public String fetchAuthTokenEndpointClientSecret() default "MToyT0tIY3JwSkJXOTFHQUtoMFRhZFFnPT04aGpYcmRTTktUY2UzSTRnUkJqWUt3PT0=";

		/**
		 * Fetch auth token endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "fetchAuthTokenEndpoint", description = "Fetch auth token endpoint", type = AttributeType.STRING)
		public String fetchAuthTokenEndpoint() default "/authorizationserver/oauth/token?";

		/**
		 * Gets the list of user certifications endpoint.
		 *
		 * @return the list of user certifications endpoint
		 */
		@AttributeDefinition(name = "GetListOfUserCertificationsEndpoint", description = "Get List Of User Certifications Endpoint", type = AttributeType.STRING)
		public String getListOfUserCertificationsEndpoint() default "/occ/v2/{{site}}/user/certifications";

		/**
		 * Delete certificate endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "DeleteCertificateEndpoint", description = "Delete Certificate Endpoint", type = AttributeType.STRING)
		public String deleteCertificateEndpoint() default "/occ/v2/{{site}}/bdbusers/deleteCertificate";

		/**
		 * Upload ruo certificate endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "UploadRuoCertificateEndpoint", description = "Upload RUO Certificate Endpoint", type = AttributeType.STRING)
		public String uploadRuoCertificateEndpoint() default "/occ/v2/{{site}}/users/{userId}/upload/certificate";

		/**
		 * Gets the addresses endpoint.
		 *
		 * @return the addresses endpoint
		 */
		@AttributeDefinition(name = "GetAddressesEndpoint", description = "Get Addresses Endpoint", type = AttributeType.STRING)
		public String getAddressesEndpoint() default "/occ/v2/{{site}}/users/{userId}/addresses/addressList";

		/**
		 * Gets the all customers cart endpoint.
		 *
		 * @return the all customers cart endpoint
		 */
		@AttributeDefinition(name = "GetAllCustomersCartEndpoint", description = "Get All Customers' Cart Endpoint", type = AttributeType.STRING)
		public String getAllCustomersCartEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts";

		/**
		 * Adds the quantity endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "AddQuantity", description = "Add Quantity Endpoint", type = AttributeType.STRING)
		public String addQuantityEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry";

		/**
		 * Update favorite address endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "UpdateFavoriteAddressEndpoint", description = "Update Favorite Address Endpoint", type = AttributeType.STRING)
		public String updateFavoriteAddressEndpoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/addresses/updateFavorite";

		/**
		 * Default address endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "DefaultAddressEndpoint", description = "Default Address Endpoint", type = AttributeType.STRING)
		public String defaultAddressEndpoint() default "/occ/v2/{{site}}/users/{userId}/addresses/default";

		/**
		 * Update nickname endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "UpdateNicknameEndpoint", description = "Update Nickname Endpoint", type = AttributeType.STRING)
		public String updateNicknameEndpoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/addresses/updateNickname";

		/**
		 * Creates the address endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "CreateAddressEndpoint", description = "Create Address Endpoint", type = AttributeType.STRING)
		public String createAddressEndpoint() default "/occ/v2/{{site}}/users/{userId}/addresses/create";

		/**
		 * Creates the address endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "ReactivateUserEndpoint", description = "Reactivate User Endpoint", type = AttributeType.STRING)
		public String reactivateUserEndpoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/reactivate";

		/**
		 * Sso login ping id domain.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "SsoLoginPingIdDomain", description = "SSO Login Ping-Id Domain", type = AttributeType.STRING)
		public String ssoLoginPingIdDomain() default "https://ssodev.bd.com";

		/**
		 * Sso login ping id endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "SsoLoginPingIdEndpoint", description = "SSO Login Ping-Id Endpoint", type = AttributeType.STRING)
		public String ssoLoginPingIdEndpoint() default "/as/authorization.oauth2";

		/**
		 * Sso login response type.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "SsoLoginResponseType", description = "SSO Login Response Type", type = AttributeType.STRING)
		public String ssoLoginResponseType() default "code";

		/**
		 * Sso login client id.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "SsoLoginClientId", description = "SSO Login Client-Id", type = AttributeType.PASSWORD)
		public String ssoLoginClientId() default "Galaxy_OIDC";

		/**
		 * Sso customer login service.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "SsoCustomerLoginService", description = "SSO Customer Login Service", type = AttributeType.STRING)
		public String ssoCustomerLoginService() default "/bin/ssocustomerlogin";

		/**
		 * Sso login scope.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "SsoLoginScope", description = "SSO Login Scope", type = AttributeType.STRING)
		public String ssoLoginScope() default "openid+profile";

		/**
		 * Ciam ping id domain.
		 *
		 * @return the ciamPingIdDomain
		 */
		@AttributeDefinition(name = "CiamPingIdDomain", description = "Ciam Ping-Id Domain", type = AttributeType.STRING)
		public String ciamPingIdDomain() default "https://bdciamqa.b2clogin.com/bdciamqa.onmicrosoft.com";

		/**
		 * Ciam login ping id end point.
		 *
		 * @return the ciamLoginPingIdEndpoint
		 */
		@AttributeDefinition(name = "CiamLoginPingIdEndpoint", description = "Ciam Login Ping-Id End point", type = AttributeType.STRING)
		public String ciamLoginPingIdEndpoint() default "/oauth2/v2.0/authorize";

		/**
		 * Ciam b2c id.
		 *
		 * @return the ciamB2CId
		 */
		@AttributeDefinition(name = "ciamB2CId", description = "Ciam B2c Id", type = AttributeType.STRING)
		public String ciamB2CId() default "B2C_1A_BDB_SISU";

		/**
		 * Ciam login client id.
		 *
		 * @return the ciamLoginClientId
		 */
		@AttributeDefinition(name = "ciamLoginClientId", description = "Ciam Login Client Id", type = AttributeType.STRING)
		public String ciamLoginClientId() default "43cddbd9-89fa-4037-9a89-c7d63bcee854";

		/**
		 * Get the createShoppingListEndpoint.
		 *
		 * @return the createShoppingListEndpoint
		 */
		@AttributeDefinition(name = "createShoppingListEndpoint", description = "Create Shopping List Endpoint", type = AttributeType.STRING)
		String createShoppingListEndpoint() default CommonConstants.SHOPPING_LIST_URI;

		/**
		 * Get the getShoppingListDetailsEndpoint.
		 *
		 * @return the getShoppingListDetailsEndpoint
		 */
		@AttributeDefinition(name = "getShoppingListDetailsEndpoint", description = "Get Shopping List Details Endpoint", type = AttributeType.STRING)
		String getShoppingListDetailsEndpoint() default "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/details";

		/**
		 * Get the getShoppingList.
		 *
		 * @return the getShoppingList
		 */
		@AttributeDefinition(name = "getShoppingListEndpoint", description = "Get Shopping List", type = AttributeType.STRING)
		String getShoppingListEndpoint() default CommonConstants.SHOPPING_LIST_URI;

		/**
		 * Get the getQuotesHistory.
		 *
		 * @return the getQuotesHistory
		 */
		@AttributeDefinition(name = "getQuotesHistoryEndpoint", description = "Get Quotes History", type = AttributeType.STRING)
		String getQuotesHistoryEndpoint() default "/occ/v2/{{siteId}}/users/{{userId}}/quote/getquotehistory?fields=DEFAULT";

		/**
		 * Get the getQuotesDetails.
		 *
		 * @return the getQuotesDetails
		 */
		@AttributeDefinition(name = "getQuoteDetailsEndpoint", description = "Get Quotes Details", type = AttributeType.STRING)
		String getQuoteDetailsEndpoint() default "/occ/v2/{{siteId}}/users/{{userId}}/quote/getquotedetail?custPurchaseOrderNumber={{poNumber}}&fields=DEFAULT&quoteRefNum={{quoteRefNum}}";

		/**
		 * Get the fileUploadShoppingList.
		 *
		 * @return the fileUploadShoppingList
		 */
		@AttributeDefinition(name = "fileUploadShoppingListEndpoint", description = "File Upload Shopping List Endpoint", type = AttributeType.STRING)
		String fileUploadShoppingListEndpoint() default "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/fileupload";

		/**
		 * Get the removeShoppingList.
		 *
		 * @return the removeShoppingList
		 */
		@AttributeDefinition(name = "removeShoppingListEndpoint", description = "removeShoppingList", type = AttributeType.STRING)
		String removeShoppingListEndpoint() default CommonConstants.SHOPPING_LIST_URI;

		/**
		 * Get the shareShoppingList.
		 *
		 * @return the shareShoppingList
		 */
		@AttributeDefinition(name = "shareShoppingListEndpoint", description = "Sare Shopping List", type = AttributeType.STRING)
		String shareShoppingListEndpoint() default "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/share";

		/**
		 * Get the exportShoppingList.
		 *
		 * @return the exportShoppingList
		 */
		@AttributeDefinition(name = "exportShoppingListEndpoint", description = "Export Shopping List", type = AttributeType.STRING)
		String exportShoppingListEndpoint() default "/occ/v2/{baseSiteId}/users/{userId}/account/shoppinglist/exportShoppingList";

		/**
		 * Get the removeShoppingListEntries Endpoint.
		 *
		 * @return the removeShoppingListEntries Endpoint
		 */
		@AttributeDefinition(name = "removeShoppingListEntriesEndpoint", description = "Remove Shopping List Entries Endpoint", type = AttributeType.STRING)
		String removeShoppingListEntriesEndpoint() default "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/entries";

		/**
		 * Get the updateShoppingListEntries Endpoint.
		 *
		 * @return the updateShoppingListEntries Endpoint
		 */
		@AttributeDefinition(name = "updateShoppingListEntriesEndpoint", description = "Update Shopping List Entries Endpoint", type = AttributeType.STRING)
		String updateShoppingListEntriesEndpoint() default "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/entries";

		/**
		 * Get the fileUploadShoppingListentries Endpoint.
		 *
		 * @return the fileUploadShoppingListentries Endpoint
		 */
		@AttributeDefinition(name = "fileUploadShoppingListEntriesEndpoint", description = "File Upload Shopping List Entries Endpoint", type = AttributeType.STRING)
		String fileUploadShoppingListEntriesEndpoint() default "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/fileupload/entries";

		/**
		 * Gets the cart with identifier.
		 *
		 * @return the cart with identifier
		 */
		@AttributeDefinition(name = "getCartWithIdentifier", description = "Get carts with identifiers", type = AttributeType.STRING)
		String getCartWithIdentifier() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

		/**
		 * Gets the delete entry from cart.
		 *
		 * @return the delete entry from cart
		 */
		@AttributeDefinition(name = "getDeleteEntryFromCart", description = "Delete The Entry From Cart", type = AttributeType.STRING)
		String getDeleteEntryFromCart() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry/{{entryNumber}}";

		/**
		 * Gets the save for later.
		 *
		 * @return the save for later
		 */
		@AttributeDefinition(name = "getSaveForLater", description = "Save For Later", type = AttributeType.STRING)
		String getSaveForLater() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart/{{savedForLaterCartId}}";

		/**
		 * Gets the creates the save for later cart.
		 *
		 * @return the creates the save for later cart
		 */
		@AttributeDefinition(name = "createSaveForLaterCart", description = "Get carts with identifiers", type = AttributeType.STRING)
		String getCreateSaveForLaterCart() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart";

		/**
		 * Bulk remove from cart.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "bulkRemoveFromCart", description = "Bulk Remove From Cart", type = AttributeType.STRING)
		String bulkRemoveFromCart() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

		/**
		 * Bulk add to save for later.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "bulkAddToSaveForLater", description = "Bulk Add To Save For Later", type = AttributeType.STRING)
		String bulkAddToSaveForLater() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries";

		/**
		 * Adds the to save for later.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "addToSaveForLater", description = "Add To Save For Later", type = AttributeType.STRING)
		String addToSaveForLater() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

		/**
		 * Gets the replace cart entry.
		 *
		 * @return the replace cart entry
		 */
		@AttributeDefinition(name = "replaceCartEntry", description = "Replace Cart Entry", type = AttributeType.STRING)
		String getReplaceCartEntry() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry/{{entryNumber}}/{{productCode}}";

		/**
		 * Gets the replace save for later entry.
		 *
		 * @return the replace save for later entry
		 */
		@AttributeDefinition(name = "replaceSaveForLaterEntry", description = "Replace Save For Later Entry", type = AttributeType.STRING)
		String getReplaceSaveForLaterEntry() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}/{{productCode}}";

		/**
		 * Adds the to cart from save to later.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "addToCartFromSaveToLater", description = "Add To Cart From Save To Later", type = AttributeType.STRING)
		String addToCartFromSaveToLater() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/updatecurrentcart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

		/**
		 * Delete save for later.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "deleteSaveForLater", description = "Delete Save For Later", type = AttributeType.STRING)
		String deleteSaveForLater() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

		/**
		 * Cart config endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "cartConfigEndpoint", description = "Cart Config Endpoint", type = AttributeType.STRING)
		public String cartConfigEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

		/**
		 * Country dropdown endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "countryDropdownEndpoint", description = "The endpoint used to fetch country dropdown", type = AttributeType.STRING)
		public String countryDropdownEndpoint() default "/etc/acs-commons/lists/bdb/region_country_language_mappings/country_mapping";

		/**
		 * Language dropdown endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "languageDropdownEndpoint", description = "The endpoint used to fetch lanuage dropdown", type = AttributeType.STRING)
		public String languageDropdownEndpoint() default "/etc/acs-commons/lists/bdb/region_country_language_mappings/language_mapping";

		/**
		 * Gets the grants for customer endpoint.
		 *
		 * @return the grants for customer endpoint
		 */
		@AttributeDefinition(name = "getGrantsForCustomerEndpoint", description = "Get Grants For Customer Endpoint", type = AttributeType.STRING)
		public String getGrantsForCustomerEndpoint() default "/occ/v2/{{site}}/users/{userId}/grant";

		/**
		 * Applies the grants on cart endpoint.
		 *
		 * @return the grants on cart endpoint.
		 */
		@AttributeDefinition(name = "applyGrantsOnCartEndpoint", description = "Apply Grants on Cart Endpoint", type = AttributeType.STRING)
		public String applyGrantsOnCartEndpoint() default "/occ/v2/{{site}}/users/{{userId}}/carts/{{cartId}}/grant";

		/**
		 * Order history for grants endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "orderHistoryForGrantsEndpoint", description = "Order History For Grants Endpoint", type = AttributeType.STRING)
		public String orderHistoryForGrantsEndpoint() default "/occ/v2/{{site}}/users/{userId}/grant/orderhistory?grantId={grantId}";

		/**
		 * Get Payments Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getPaymentsEndpoint", description = "Get Payments Endpoint", type = AttributeType.STRING)
		public String getPaymentsEndpoint() default "/occ/v2/{{site}}/users/{userId}/payment";

		/**
		 * Add Credit Card Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "addCreditCardEndpoint", description = "Add Credit Card Endpoint", type = AttributeType.STRING)
		public String addCreditCardEndpoint() default "/occ/v2/{{site}}/users/{userId}/payment/card";

		/**
		 * Remove Credit Card Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "removeCreditCardEndpoint", description = "Remove Credit Card Endpoint", type = AttributeType.STRING)
		public String removeCreditCardEndpoint() default "/occ/v2/{{site}}/users/{userId}/payment/card/remove";

		/**
		 * update Credit Card Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "updateCreditCardEndpoint", description = "Update Credit Card Endpoint", type = AttributeType.STRING)
		public String updateCreditCardEndpoint() default "/occ/v2/{{site}}/users/{userId}/payment/card/update";

		/**
		 * MiniCart Count config endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "miniCartCountConfigEndpoint", description = "Mini Cart Count Config Endpoint", type = AttributeType.STRING)
		public String miniCartCountConfigEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/minicart/count";

		/**
		 * MiniCart Entries config endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "miniCartEntriesConfigEndpoint", description = "Mini Cart Entries Config Endpoint", type = AttributeType.STRING)
		public String miniCartEntriesConfigEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/minicart";

		/**
		 * Update cart quantity endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "updateCartQuantityEndpoint", description = "Update Cart Quantity Endpoint", type = AttributeType.STRING)
		public String updateCartQuantityEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry/{{entryNumber}}";

		/**
		 * Update lot indicator endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "updateLotIndicatorEndpoint", description = "Update Lot Indicator Endpoint", type = AttributeType.STRING)
		public String updateLotIndicatorEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/updatelotindicator";

		/**
		 * Order details approver endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "OrderDetailsApproverEndpoint", description = "Order Details Approver Endpoint", type = AttributeType.STRING)
		public String orderDetailsApproverEndpoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/orderapprovals/{orderApprovalCode}";

		/**
		 * Order list approver endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "OrderListApproverEndpoint", description = "Order List Approver Endpoint", type = AttributeType.STRING)
		public String orderListApproverEndpoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/orderapprovals";

		/**
		 * Order approval decision endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "OrderApprovalDecisionEndpoint", description = "Order Approval Decision Endpoint", type = AttributeType.STRING)
		public String orderApprovalDecisionEndpoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/orderapprovals/{orderApprovalCode}/orderApprovalDecision";

		/**
		 * Update po number endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "UpdatePONumberEndpoint", description = "Update PO Number Endpoint", type = AttributeType.STRING)
		public String updatePoNumberEndpoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/orderapprovals/{orderApprovalCode}/updatePONumber";

		/**
		 * Promotion id details servlet path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getPromotionIdDetailsServletPath", description = "Get Promotion Id Details Servlet Path", type = AttributeType.STRING)
		public String promotionIdDetailsServletPath() default "/content/bdb/paths/get-promotion-details.{{promoId}}.json";

		/**
		 * Promotions msg endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getPromotionsMsgEndpoint", description = "Get Promotions Message Endpoint", type = AttributeType.STRING)
		public String promotionsMsgEndpoint() default "/occ/v2/{{baseSiteId}}/promotions/product/{{catalogId}}";

		/**
		 * Aem upload tax certificate endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "aemUploadTaxCertificateEndpoint", description = "AEM Upload Tax Certificate Endpoint", type = AttributeType.STRING)
		public String aemUploadTaxCertificateEndpoint() default "/content/bdb/paths/upload-purchase-account-document.json";

		/**
		 * Update cart VAT Exempt status endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "updatesCartVATExemptStatus", description = "Updates cart VAT exempt status", type = AttributeType.STRING)
		public String updatesCartVATExemptStatus() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/vat/{{vatExemptIndicator}}";

		/**
		 * getMessagesEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getMessagesEndpoint", description = "Get Messages Endpoint", type = AttributeType.STRING)
		public String getMessagesEndpoint() default "/occ/v2/{{site}}/users/{userId}/notification";

		/**
		 * readMessageEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "readMessageEndpoint", description = "Read Message Endpoint", type = AttributeType.STRING)
		public String readMessageEndpoint() default "/occ/v2/{{site}}/users/{userId}/notification/read";

		/**
		 * getOrdersEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getOrdersEndpoint", description = "Get Orders Endpoint", type = AttributeType.STRING)
		public String getOrdersEndpoint() default "/occ/v2/{{site}}/users/{userId}/account/orders/orderList";

		/**
		 * getQuotesEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getQuotesEndpoint", description = "Get Quotes Endpoint", type = AttributeType.STRING)
		public String getQuotesEndpoint() default "/occ/v2/{{site}}/users/{userId}/quote/quoteList";

		/**
		 * Anti body details end point.
		 *
		 * @return Clone Data End Point
		 */
		@AttributeDefinition(name = "cloneDataEndPoint", description = "Get Product Clones", type = AttributeType.STRING)
		public String antiBodyDetailsEndPoint() default "/content/bdb/paths/fetch-clone-data";

		/**
		 * Format details end point.
		 *
		 * @return Format Details End Point
		 */
		@AttributeDefinition(name = "Format Data EndPoint ", description = "Get Product Formats", type = AttributeType.STRING)
		public String formatDetailsEndPoint() default "/content/bdb/paths/fetch-format-data";

		/**
		 * Formats and Clones price data end point.
		 *
		 * @return priceDataEndPoint
		 */
		@AttributeDefinition(name = "Formats and Clone Data EndPoint", description = "Get Formats and Clones Details Price Data", type = AttributeType.STRING)
		public String priceDataEndPoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/products";

		/**
		 * Gets the user orders list endpoint.
		 *
		 * @return the user orders list endpoint
		 */
		@AttributeDefinition(name = "getUserOrdersListEndpoint", description = "Get User Orders List Endpoint", type = AttributeType.STRING)
		public String getUserOrdersListEndpoint() default "";

		/**
		 * Gets the order details endpoint.
		 *
		 * @return the order details endpoint
		 */
		@AttributeDefinition(name = "getOrderDetailsEndpoint", description = "Get Order Details Endpoint", type = AttributeType.STRING)
		public String getOrderDetailsEndpoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/account/orders/order/{orderId}";

		/**
		 * Gets the order packing list endpoint.
		 *
		 * @return the order packing list endpoint
		 */
		@AttributeDefinition(name = "getOrderPackingListEndpoint", description = "Get Order Packing List Endpoint", type = AttributeType.STRING)
		public String getOrderPackingListEndpoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/account/orders/document/download";

		/**
		 * getSearchResultsServletPath.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "searchResultsServletPath", description = "Get Search Results Servlet Path", type = AttributeType.STRING)
		public String searchResultsServletPath() default "/content/bdb/paths/fetch-data.json";

		/**
		 * getSearchResultsServletPath.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "productAnnouncements", description = "Get Product Announcement Path", type = AttributeType.STRING)
		public String productAnnouncements() default "/content/bdb/paths/get-product-announcements";

		/**
		 * Gets the hybris sign out endpoint.
		 *
		 * @return the hybris sign out endpoint
		 */
		@AttributeDefinition(name = "GetHybrisSignOutEndpoint", description = "Get Hybris Sign-Out Endpoint", type = AttributeType.STRING)
		public String getHybrisSignOutEndpoint() default "/occ/v2/{{site}}/users/{userId}/logout";

		/**
		 * Dev sso sign out Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "DevSsoSignOutEndpoint", description = "Dev SSO Sign-Out Endpoint", type = AttributeType.STRING)
		public String devSsoSignOutEndpoint() default "/account/logoff?to=GA";

		/**
		 * Ciam sign out Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "CiamSignOutEndpoint", description = "Ciam Sign-Out Endpoint", type = AttributeType.STRING)
		public String ciamSignOutEndpoint() default "/oauth2/v2.0/logout";

		/**
		 * Gets the sign out servlet path.
		 *
		 * @return the sign out servlet path
		 */
		@AttributeDefinition(name = "GetSignOutServletPath", description = "Get Sign-Out Servlet Path", type = AttributeType.STRING)
		public String getSignOutServletPath() default "/content/bdb/paths/sign-out.json";

		/**
		 * addAllItemsToCart endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "addAllItemsToCartEndpoint", description = "Hybris Add All Items To Cart Endpoint", type = AttributeType.STRING)
		public String addAllItemsToCartEndpoint() default "/occ/v2/{{site}}/users/{userId}/carts/{cartId}/entries/multiple";

		/**
		 * updateVATExemptStatus.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "updatesVATExemptStatus", description = "Update VAT Exempt Status", type = AttributeType.STRING)
		public String updatesVATExemptStatus() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/vat/{{vatExemptIndicator}}";

		/**
		 * checkoutValidation.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "validateMyCart", description = "validateMyCart", type = AttributeType.STRING)
		public String validateMyCart() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/checkout/validate";

		/**
		 * Ship To Address Config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "shipToAddressConfig", description = "Ship To Address Config", type = AttributeType.STRING)
		public String shipToAddressConfig() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/addresses/{{addressType}}/sorted";

		/**
		 * Shipping Details Config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "shippingDetailsConfig", description = "Shipping Details Config", type = AttributeType.STRING)
		public String shippingDetailsConfig() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/shippingdetails";

		/**
		 * Billing Details Config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "billingDetailsConfig", description = "Billing Details Config", type = AttributeType.STRING)
		public String billingDetailsConfig() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/billingdetails";

		/**
		 * Apply Coupon Config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "applyCoupon", description = "Apply Coupon endpoint", type = AttributeType.STRING)
		public String applyCoupon() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/coupon/{{couponCode}}";

		/**
		 * Quick add bulk entry endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "quickAddBulkEntryEndpoint", description = "Quick Add Bulk Entry endpoint", type = AttributeType.STRING)
		public String quickAddBulkEntryEndpoint() default "/occ/v2/{{site}}/users/{{userId}}/carts/{{cartId}}/entries/multiple";

		/**
		 * Quick add bulk upload endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "quickAddBulkUploadEndpoint", description = "Quick Add Bulk Upload endpoint", type = AttributeType.STRING)
		public String quickAddBulkUploadEndpoint() default "/occ/v2/{{site}}/users/{{userId}}/carts/{{cartId}}/entries/excel";

		/**
		 * addressWithNoRuoEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "addressWithNoRuoEndpoint", description = "Address With No Ruo Endpoint", type = AttributeType.STRING)
		public String addressWithNoRuoEndpoint() default "/occ/v2/{{site}}/users/{userId}/addresses/addressesWithNoRUO";

		/**
		 * searchListEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "searchListEndpoint", description = " Get Search List Endpoint", type = AttributeType.STRING)
		public String searchListEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/products";

		/**
		 * updateAddressConfig.
		 *
		 * @return updateAddressConfig endpoint
		 */
		@AttributeDefinition(name = "updateAddressConfig", description = " Update Address Config Endpoint", type = AttributeType.STRING)
		public String updateAddressConfig() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/addresses/{{addressType}}/{{addressId}}";

		/**
		 * updateAddressConfig.
		 *
		 * @return updateAddressConfig endpoint
		 */
		@AttributeDefinition(name = "promoProductsListEndpoint", description = " Promo Products List Endpoint", type = AttributeType.STRING)
		public String promoProductsListEndpoint() default "/occ/v2/{{site}}/promotions/{{code}}?fields=BDBPROMOTION";

		/**
		 * updateAddressConfig.
		 *
		 * @return updateAddressConfig endpoint
		 */
		@AttributeDefinition(name = "quoteReference", description = " QuoteReference Endpoint", type = AttributeType.STRING)
		public String quoteReference() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/quotereference";

		/**
		 * updateAddressConfig.
		 *
		 * @return updateAddressConfig endpoint
		 */
		@AttributeDefinition(name = "getQuote", description = " GetQuote ", type = AttributeType.STRING)
		public String getQuote() default "/occ/v2/{{siteId}}/users/{{userId}}/quote/getquotes?applicationID={{applicationId}}&quoteRefNum={{quoteNumber}}&custPurchaseOrderNumber={{custPONumber}}&ssoToken={{ssoToken}}";

		/**
		 * updateAddressConfig.
		 *
		 * @return updateAddressConfig endpoint
		 */
		@AttributeDefinition(name = "quoteToCart", description = " QuoteToCart ", type = AttributeType.STRING)
		public String quoteToCart() default "/occ/v2/{{siteId}}/users/{{userId}}/quote/quoteToCart";

		/**
		 * Notification endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "NotificationEndpoint", description = "Notification Endpoint", type = AttributeType.STRING)
		public String notificationEndpoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/notification/optin";

		/**
		 * User Idle time.
		 *
		 * @return the idle time
		 */
		@AttributeDefinition(name = "UserIdleTime", description = "User Idle Time in Minutes", type = AttributeType.INTEGER)
		public int userIdleTime() default 30;

		/**
		 * Compare Products toolbar maximum products.
		 *
		 * @return the maximum products
		 */
		@AttributeDefinition(name = "maximumProducts", description = "Compare Products Toolbar Maximum Products", type = AttributeType.INTEGER)
		public int maximumProducts() default 4;

		/**
		 * Compare Products toolbar minimum products.
		 *
		 * @return the maximum products
		 */
		@AttributeDefinition(name = "minimumProducts", description = "Compare Products Toolbar Minimum Products", type = AttributeType.INTEGER)
		public int minimumProducts() default 2;

		/**
		 * Add New Address End Point.
		 *
		 * @return addNewAddressEndPoint
		 */
		@AttributeDefinition(name = "AddNewAddressEndPoint", description = "Add New Address EndPoint", type = AttributeType.STRING)
		public String addNewAddressEndPoint() default "/occ/v2/{{baseSiteId}}/users/{userId}/addresses/create";

		/**
		 * Sso login scope.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "PingIdSsoSignOutDomain", description = "SSO Logout Scope", type = AttributeType.STRING)
		public String pingIdSsoSignOutDomain() default "https://testsso.bdbioscience.com";

		/**
		 * Gets the delete coupon.
		 *
		 * @return the delete coupon
		 */
		@AttributeDefinition(name = "Delete Coupon", description = "Delete Coupon Config", type = AttributeType.STRING)
		public String getDeleteCoupon() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/coupon/{{couponCode}}";

		/**
		 * Merge anonymous cart endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "MergeAnonymousCartEndpoint", description = "Merge Anonymous Cart Endpoint", type = AttributeType.STRING)
		public String mergeAnonymousCartEndpoint() default "/occ/v2/{{site}}/users/{userId}/merge/{anonymousCartGuid}]";

		/**
		 * Gets the payments details.
		 *
		 * @return the payments details.
		 */
		@AttributeDefinition(name = "getPaymentDetails", description = "getPaymentDetails config", type = AttributeType.STRING)
		public String getPaymentDetails() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/paymentdetails";

		/**
		 * Gets the other details.
		 *
		 * @return the other details.
		 */
		@AttributeDefinition(name = "getOtherDetails", description = "getOtherDetails config", type = AttributeType.STRING)
		public String getOtherDetails() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/otherdetails";

		/**
		 * Order Confirmation Checkoutdata endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "OrderConfirmationCheckOutDataEndpoint", description = "Order Confirmation CheckoutData Endpoint", type = AttributeType.STRING)
		public String orderConfirmationCheckoutData() default "/occ/v2/{{baseSiteId}}/orgUsers/{{userId}}/orders/";

		/**
		 * Product Comparison get Product Details endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getProductDetailsEndpoint", description = " Product Comparison Get Product Details Endpoint", type = AttributeType.STRING)
		public String getProductDetails() default "/content/bdb/paths/compare-products?country={{country}}";

		/**
		 * Order Confirmation CancelOrder endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "OrderConfirmationCancelOrderEndpoint", description = "Order Confirmation Cancel Order Endpoint", type = AttributeType.STRING)
		public String orderConfirmationCancelOrder() default "/occ/v2/{{baseSiteId}}/orgUsers/{{userId}}/orders/{{orderId}}/cancellation";

		/**
		 * Post Smart Cart Register Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "postSmartCartRegisterEndpoint", description = "Post Smart Cart Register Endpoint", type = AttributeType.STRING)
		public String postSmartCartRegisterEndpoint() default "/occ/v2/{{site}}/users/{userId}/smartcart/register";

		/**
		 * get Distributors Options Endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getDistributorsOptionsEndpoint", description = "Get Distributors Options Endpoint", type = AttributeType.STRING)
		public String getDistributorsOptionsEndpoint() default "/occ/v2/{{site}}/users/{userId}/distributors";

		/**
		 * Place order config endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "placeOrderConfig", description = "place order config Endpoint", type = AttributeType.STRING)
		public String placeOrderConfig() default "/occ/v2/{{baseSiteId}}/orgUsers/{{userId}}/orders";

		/**
		 * Punch out login endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "punchout login servlet path", description = "punchout login servlet path", type = AttributeType.STRING)
		public String punchOutEndpoint() default "/occ/v2/{{baseSiteId}}/punchout/login";

		/**
		 * Punchout transmit request endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Punchout transmit request endpoint.", description = "Punchout transmit request endpoint.", type = AttributeType.STRING)
		public String punchoutTransmitRequestEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/cxml/requestRequisition?punchoutSessionID";

		/**
		 * Punchout cancel request endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Punchout cancel request endpoint.", description = "Punchout cancel request endpoint.", type = AttributeType.STRING)
		public String punchoutCancelRequestEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/cxml/cancelRequisition?punchoutSessionID";

		/**
		 * Get getQuoteConfig endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getQuoteConfig endpoint.", description = "getQuoteConfig endpoint.", type = AttributeType.STRING)
		public String getQuoteConfig() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/quote";

		/**
		 * Quote Confirmation Config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Quote Confirmation Config", description = "Quote Confirmation Config.", type = AttributeType.STRING)
		public String quoteConfirmationConfig() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/quote/{{quoteCode}}";

		/**
		 * Send email Config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "sendEmail", description = "Send Email Config.", type = AttributeType.STRING)
		public String sendEmail() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/notification/cart";

		/**
		 * Language dropdown endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "languageSearchDropdownEndpoint", description = "The endpoint used to fetch language search dropdown", type = AttributeType.STRING)
		public String languageSearchDropdownEndpoint() default "/etc/acs-commons/lists/bdb/language_list_for_search";

		/**
		 * GetOrderInquiryDetailEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getOrderInquiryDetailEndpoint", description = "The endpoint for getting Order Inquiry Details", type = AttributeType.STRING)
		public String getOrderInquiryDetailEndpoint() default "/occ/v2/{{site}}/users/{userId}/account/orders/orderinquiry";

		/**
		 * postOrderInquiryDetailsEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "postOrderInquiryDetailsEndpoint", description = "The endpoint for posting Order Inquiry Details", type = AttributeType.STRING)
		public String postOrderInquiryDetailsEndpoint() default "/occ/v2/{{site}}/users/{userId}/account/orders/submitorderinquiry";

		/**
		 * Gets the refresh token endpoint.
		 *
		 * @return the refresh token endpoint
		 */
		@AttributeDefinition(name = "getRefreshTokenEndpoint", description = "The endpoint for Refresh Token Servlet", type = AttributeType.STRING)
		public String getRefreshTokenEndpoint() default "/content/bdb/paths/refresh-token.json";

		/**
		 * Trigger re order endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "triggerReOrderEndpoint", description = "The endpoint for Trigger ReOrder", type = AttributeType.STRING)
		public String triggerReOrderEndpoint() default "/occ/v2/{{baseSiteId}}/orgUsers/{userId}/carts/{cartId}/cartFromOrder";

		/**
		 * postOrderInquiryDetailsEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "beadlotsFileDownloadEndpoint", description = "The Mulesoft endpoint for downloading Beadlot files", type = AttributeType.STRING)
		public String beadlotsFileDownloadEndpoint() default "https://apidev.carefusion.com/BeadLotsAPI/erpDoc/beadLots";

		/**
		 * beadlotsFileDownloadUsername.
		 *
		 * @return the String
		 */
		@AttributeDefinition(name = "beadlotsFileDownloadUsername", description = "The beadlots file download username", type = AttributeType.STRING)
		public String beadlotsFileDownloadUsername() default "beadlotsmule";

		/**
		 * Gets beadlotsFileDownloadPassword.
		 *
		 * @return the beadlotsFileDownloadPassword
		 */
		@AttributeDefinition(name = "beadlotsFileDownloadPassword", description = "The beadlots file download password", type = AttributeType.PASSWORD)
		public String beadlotsFileDownloadPwd() default "Be@dLotsMule*";

		/**
		 * beadlotsFileDownloadServletPath.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "beadlotsFileDownloadServletPath", description = "The AEM endpoint for Beadlots File Download Servlet", type = AttributeType.STRING)
		public String beadlotsFileDownloadServletPath() default "/content/bdb/paths/beadlots-file-download";

		/**
		 * myOrdersListEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "myOrdersListEndpoint", description = "The Hybris endpoint for my Orders List", type = AttributeType.STRING)
		public String myOrdersListEndpoint() default "/occ/v2/{{baseSiteId}}/users/current/account/orders/orderList";

		/**
		 * allOrdersListEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "allOrdersListEndpoint", description = "The Hybris endpoint for all Orders List", type = AttributeType.STRING)
		public String allOrdersListEndpoint() default "/occ/v2/{{baseSiteId}}/users/current/account/orders/allorders";

		/**
		 * Sds endpoint user name.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "sdsEndpointUser", description = "SDS Files Endpoint User Name", type = AttributeType.STRING)
		String sdsEndpointUser() default "sdsapi";

		/**
		 * Sds endpoint user password.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "SDSEndpointPassword", description = "SDS Files Endpoint Password", type = AttributeType.STRING)
		String sdsEndpointPassword() default "Reg#l$te@";

		/**
		 * Tds AEM endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "tdsEndpoint", description = "TDS AEM Endpoint", type = AttributeType.STRING)
		String tdsEndpoint() default "/content/bdb/paths/generate-tds-document.{country}.{materialNumber}.pdf";

		/**
		 * Sds AEM endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "sdsEndpoint", description = "SDS AEM Endpoint", type = AttributeType.STRING)
		String sdsEndpoint() default "/content/bdb/paths/download-sds-pdf.{fileID}.pdf";

		/**
		 * Sds pdf search endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "sdsPdfSearchEndpoint", description = "SDS Muel PDF Search Endpoint", type = AttributeType.STRING)
		String sdsPdfSearchEndpoint() default "https://apiqa.carefusion.com/sds/search";

		/**
		 * Sds pdf dwonload endpoint.
		 * https://apiqa.carefusion.com/sds/pdf
		 * 
		 * @return the string
		 */
		@AttributeDefinition(name = "sdsPdfDownloadEndpoint", description = "SDS Muel PDF Download Endpoint", type = AttributeType.STRING)
		String sdsPdfDownloadEndpoint() default "https://apiqa.carefusion.com/sds/pdf";

		/**
		 * Cancel order endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "CancelOrderEndpoint", description = "Cancel Order Endpoint", type = AttributeType.STRING)
		public String cancelOrderEndpoint() default "/occ/v2/{{baseSiteId}}/orgUsers/current/orders/{orderId}/cancellation";

		/**
		 * Required Companion Product endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "requiredCompanionProductsEndpoint", description = "Required Companion Product Endpoint", type = AttributeType.STRING)
		public String requiredCompanionProductsEndpoint() default "/content/bdb/paths/get-related-companion-products";

		/**
		 * Get Price endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getPriceEndpoint", description = "Get Price Endpoint", type = AttributeType.STRING)
		public String getPriceEndPoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/products";

		/**
		 * Paymetric token endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "PaymetricTokenEndpoint", description = "Paymetric Token Endpoint", type = AttributeType.STRING)
		public String paymetricTokenEndpoint() default "/occ/v2/{{baseSiteId}}/payment/paymetric/accesstoken";

		/**
		 * Paymetric domain.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "PaymetricDomain", description = "Paymetric Domain", type = AttributeType.STRING)
		public String paymetricDomain() default "https://cert-xiecomm.paymetric.com";

		/**
		 * Paymetric iframe endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "PaymetricIframeEndpoint", description = "Paymetric IFrame Endpoint", type = AttributeType.STRING)
		public String paymetricIframeEndpoint() default "/diecomm/View/Iframe/{{merchant_ID}}/{{token}}/True";

		/**
		 * Brightcove player id.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "brightcovePlayerId", description = "Brightcove Player Id", type = AttributeType.STRING)
		public String brightcovePlayerId() default "smS3oTdDk";

		/**
		 * Brightcove account id.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "brightcoveAccountId", description = "Brightcove Account Id", type = AttributeType.STRING)
		public String brightcoveAccountId() default "81909694001";

		/**
		 * get all payment details.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getAllPaymentDetails", description = "getAllPaymentDetails", type = AttributeType.STRING)
		public String getAllPaymentDetails() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/paymentdetails/all";

		/**
		 * updateRewardsPreferencesEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "updateRewardsPreferencesEndpoint", description = "Update Rewards Preferences Endpoint", type = AttributeType.STRING)
		public String updateRewardsPreferencesEndpoint() default "/occ/v2/{{site}}/users/{{userId}}/rewards/join";

		/**
		 * annexSiteId.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "annexSiteId", description = "Annex Site Id", type = AttributeType.STRING)
		public String annexSiteId() default "9089100";

		/**
		 * annexSiteJs.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "annexSiteJs", description = "Annex Site JS", type = AttributeType.STRING)
		public String annexSiteJs() default "https://cdn.socialannex.com/partner/{{siteId}}/universal.js";

		/**
		 * annexSiteDomain.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "annexSiteDomain", description = "Annex Site domain", type = AttributeType.STRING)
		public String annexSiteDomain() default "https://s15.socialannex.net/apiv2";

		/**
		 * summaryTabSrcEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "summaryTabSrcEndpoint", description = "Summary Tab Src Endpoint", type = AttributeType.STRING)
		public String summaryTabSrcEndpoint() default "/advancedashoard/mobileheader/{{siteId}}/{{id}}?token={{token}}";

		/**
		 * rewardsTabSrcEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "rewardsTabSrcEndpoint", description = "Rewards Tab Src Endpoint", type = AttributeType.STRING)
		public String rewardsTabSrcEndpoint() default "/advancedashoard/rewardtab/{{siteId}}/{{id}}?token={{}}";

		/**
		 * earnTabSrcEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "earnTabSrcEndpoint", description = "Earn Tab Src Endpoint", type = AttributeType.STRING)
		public String earnTabSrcEndpoint() default "/advancedashoard/earnpoints/{{siteId}}/{{id}}?token={{}}";

		/**
		 * activityTabSrcEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "activityTabSrcEndpoint", description = "Activity Tab Src Endpoint", type = AttributeType.STRING)
		public String activityTabSrcEndpoint() default "/advancedashoard/activity/{{siteId}}/{{id}}?token={{}}";

		/**
		 * Search order lookup endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "searchOrderLookupEndpoint", description = "Search Order Lookup Endpoint", type = AttributeType.STRING)
		public String searchOrderLookupEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userid}}/account/orders/quicksearch";

		/**
		 * activityTabSrcEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "vialImagesBasePath", description = "Vial Images Base Folder Path", type = AttributeType.STRING)
		public String vialImagesBasePath() default "/content/dam/bdb/products/vialimages/packaging-images";

		/**
		 * Ruo vial images base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "ruoVialImagesBasePath", description = "RUO Vial Images Base Folder Path", type = AttributeType.STRING)
		public String ruoVialImagesBasePath() default "/content/dam/bdb/products/vialimages/packaging-images/black-vial-images";

		/**
		 * Ruo vial based on storage buffer.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "ruoVialBasedOnStorageBuffer", description = "RUO Vial Image Name For Storage Buffer", type = AttributeType.STRING)
		public String ruoVialBasedOnStorageBuffer() default "amber-powder-white.jpg";

		/**
		 * Ruo vial based on vol per test.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "ruoVialBasedOnVolPerTest", description = "RUO Vial Image Name For Vol Per Test", type = AttributeType.STRING)
		public String ruoVialBasedOnVolPerTest() default "amber-glass-septum-white.jpg";

		/**
		 * Ruo vial based on product type.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "ruoVialBasedOnProductType", description = "RUO Vial Image Name For Product Type", type = AttributeType.STRING)
		public String ruoVialBasedOnProductType() default "clear-clear-white.jpg";

		/**
		 * Format images base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "formatImagesBasePath", description = "Format Images Path", type = AttributeType.STRING)
		public String formatImagesBasePath() default "/content/dam/bdb/products/vialimages/packaging-images/formatimages";

		/**
		 * getDropshipDetailsEndpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getDropshipDetailsEndpoint", description = "getDropshipDetailsEndpoint", type = AttributeType.STRING)
		public String getDropshipDetailsEndpoint() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/dropshipdetails";

		/**
		 * getScientificResourceFolder.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getScientificResourceFolder", description = "Provide the Path for scientific resource folder", type = AttributeType.STRING)
		public String getScientificResourceFolder() default "/content/dam/bdb/ScientificResources/images";

		/**
		 * getScientificResPath.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "getScientificResPath", description = "Provide the Servlet Path", type = AttributeType.STRING)
		public String getScientificResPath() default "/content/bdb/paths/get-scientific-resources";

		/**
		 * environment type.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "environmentType", description = "Should be dev/stage/prod/local - default is local", type = AttributeType.STRING)
		public String environmentType() default "local";

		/**
		 * Production User Name.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "productionUserName", description = "Production User Name for TDS image Authentication", type = AttributeType.STRING)
		public String prodUserName() default "basicuser";

		/**
		 * Production User Password.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "productionUserPassword", description = "Production User Password for TDS image Authentication", type = AttributeType.STRING)
		public String prodUserPassword() default "9c5c160a29f2dda5ece9cc418c522fdb";

		/**
		 * Tracking number config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "TrackingNumberConfig", description = "Tracking Number Config", type = AttributeType.STRING)
		public String trackingNumberConfig() default "/occ/v2/{{baseSiteId}}/users/{userId}/account/orders/order/{orderId}/trackingurl]";

		/**
		 * Get the Custome RunMode (author, publish).
		 *
		 * @return the custom run mode
		 */

		@AttributeDefinition(name = "customRunMode", description = "Custom RunMode", type = AttributeType.STRING)
		public String customRunMode() default "publish";

		/**
		 * Event topic dropdown endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "eventTopicDropdownEndpoint", description = "The endpoint used to fetch event topics dropdown", type = AttributeType.STRING)
		public String eventTopicDropdownEndpoint() default "/etc/acs-commons/lists/bdb/event_topics";

		/**
		 * Email validation endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "EmailValidationEndpoint", description = "Email Validation Endpoint", type = AttributeType.STRING)
		public String emailValidationEndpoint() default "/occ/v2/{{baseSiteId}}/users/anonymous/smartcart/checkEmail";

		/**
		 * regionCountryDropdown endpoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "countryLanguageMapping", description = "The endpoint used to country langauge mapping", type = AttributeType.STRING)
		public String countryLanguageMapping() default "/etc/acs-commons/lists/bdb/country-language-mapping";

		/**
		 * Gets the clear cart.
		 *
		 * @return the clear cart
		 */
		@AttributeDefinition(name = "clearCart", description = "Clear Cart Config", type = AttributeType.STRING)
		public String getClearCart() default "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

		@AttributeDefinition(name = "citeabApiKey", description = "Citeab API KEY Config", type = AttributeType.STRING)
		public String getCiteabApiKey() default "92c1ee1e-704f-4c33-b1be-2ce49c469eb4";

		@AttributeDefinition(name = "citeabCompanySlug", description = "Citeab Company Slug Config", type = AttributeType.STRING)
		public String getCiteabCompanySlug() default "bd-biosciences";

		@AttributeDefinition(name = "citeabScriptUrl", description = "Citeab Script Url Config", type = AttributeType.STRING)
		public String getCiteabScriptUrl() default "https://widget.citeab.com/assets/citation_v2.js";


	}

}
