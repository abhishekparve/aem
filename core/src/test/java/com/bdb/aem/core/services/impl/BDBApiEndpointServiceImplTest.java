package com.bdb.aem.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl.Configuration;
import com.bdb.aem.core.util.CommonConstants;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The type Bdb api endpoint service impl test.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BDBApiEndpointServiceImplTest {

	/** The bdbendpoint impl obj. */
	@InjectMocks
	private BDBApiEndpointServiceImpl bdbendpointImplObj = new BDBApiEndpointServiceImpl();

	/** The config. */
	@Mock
	Configuration config;
	/** The value area of focus data page. */
	private String VALUE_AREA_OF_FOCUS_DATA_PAGE = "/content/bdp/dataPage";

	/** The value bdb hybris domain. */
	private String VALUE_BDB_HYBRIS_DOMAIN = "/hybris";

	/** The value captcha client key. */
	private String VALUE_CAPTCHA_CLIENT_KEY = "CASEDWEAWFFADSD!@ADSAD";

	/** The value region country dropdown endpoint. */
	private String VALUE_REGION_COUNTRY_DROPDOWN_ENDPOINT = "/hybris/region/countries";

	/** The value sso sign in url. */
	private String VALUE_SSO_SIGN_IN_URL = "/hybris/sso";

	/** The value sign up endpoint. */
	private String VALUE_SIGN_UP_ENDPOINT = "/hybris/signup";

	/** The value sign up preference endpoint. */
	private String VALUE_SIGN_UP_PREFERENCE_ENDPOINT = "/hybris/signupPreference";

	/** The value reset password endpoint. */
	private String VALUE_RESET_PASSWORD_ENDPOINT = "/hybris/resetPassword";

	/** The value password rules label data page. */
	private String VALUE_PASSWORD_RULES_LABEL_DATA_PAGE = "/content/bdb/passwordRulesLabel";

	/** The value get list of user certifications endpoint. */
	private String VALUE_GET_LIST_OF_USER_CERTIFICATIONS_ENDPOINT = "/hybris/ListOfUserCertificationsEndpoint";

	/** The value delete certificate endpoint. */
	private String VALUE_DELETE_CERTIFICATE_ENDPOINT = "/hybris/DeleteCertificateEndpoint";

	/** The value upload ruo certificate endpoint. */
	private String VALUE_UPLOAD_RUO_CERTIFICATE_ENDPOINT = "/hybris/UploadRuoCertificateEndpoint";

	/** The value get addresses endpoint. */
	private String VALUE_GET_ADDRESSES_ENDPOINT = "/hybris/GetAddressesEndpoint";

	/** The value update favorite address endpoint. */
	private String VALUE_UPDATE_FAVORITE_ADDRESS_ENDPOINT = "/hybris/updateFavoriteAddressEndpoint";

	/** The value default address endpoint. */
	private String VALUE_DEFAULT_ADDRESS_ENDPOINT = "/hybris/defaultAddressEndpoint";

	/** The value update nickname endpoint. */
	private String VALUE_UPDATE_NICKNAME_ENDPOINT = "/hybris/updateNicknameEndpoint";

	/** The value create address endpoint. */
	private String VALUE_CREATE_ADDRESS_ENDPOINT = "/hybris/createAddressEndpoint";
	
	/** The value reactivate user endpoint. */
	private String VALUE_REACTIVATE_USER_ENDPOINT = "/hybris/reactivateUserEndpoint";

	/** The value get grants for customer endpoint. */
	private String VALUE_GET_GRANTS_FOR_CUSTOMER_ENDPOINT = "/hybris/testUser/grant";

	/** The value get quick add bulk entry endpoint. */
	private String VALUE_GET_BULK_ENTRY_ENDPOINT = "/hybris/testUser/bulkEntry";

	/** The value get quick add bulk upload endpoint. */
	private String VALUE_GET_BULK_UPLOAD_ENDPOINT = "/hybris/testUser/bulkUpload";

	/** The value apply grants on cart endpoint. */
	private String VALUE_APPLY_GRANTS_ON_CART_ENDPOINT = "/hybris/cartId/grant";

	/** The value replace cart entry endpoint. */
	private String VALUE_REPLACE_CART_ENTRY_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry/{{entryNumber}}/{{productCode}}";

	/** The value replace save for later endpoint. */
	private String VALUE_REPLACE_SAVE_FOR_LATER_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}/{{productCode}}";

	/** The value get update cart quantity endpoint. */
	private String VALUE_GET_UPDATE_CART_QUANTITY = "/hybris/updateCartQuantity";

	/** The value get shipping details. */
	private String VALUE_GET_SHIPPING_DETAILS = "/occ/v2/{baseSiteId}/users/{userId}/carts/{cartId}/shipping";

	/** The value get billing details. */
	private String VALUE_GET_BILLING_DETAILS = "/occ/v2/{baseSiteId}/users/{userId}/carts/{cartId}/billing";

	/** The value get update lot indicator endpoint. */
	private String VALUE_GET_UPDATE_LOT_INDICATOR = "/hybris/updateLotIndicator";

	/** The value order details approver endpoint. */
	private String VALUE_ORDER_DETAILS_APPROVER_ENDPOINT = "/hybris/{{baseSiteId}}/orderDetailsApproverEndpoint";

	/** The value order list approver endpoint. */
	private String VALUE_ORDER_LIST_APPROVER_ENDPOINT = "/hybris/{{baseSiteId}}/orderListApproverEndpoint";

	/** The value order approval decision endpoint. */
	private String VALUE_ORDER_APPROVAL_DECISION_ENDPOINT = "/hybris/{{baseSiteId}}/orderApprovalDecisionEndpoint";

	/** The value merge anonymous cart endpoint. */
	private String VALUE_MERGE_ANONYMOUS_CART_ENDPOINT = "/hybris/{{site}}/mergeAnonymousCartEndpoint";

	/** The delete coupon. */
	private String DELETE_COUPON = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/coupon/{{couponCode}}";

	/** The ping id sso sign out domain. */
	private String PING_ID_SSO_SIGN_OUT_DOMAIN = "https://testsso.bdbioscience.com";

	/** The anonymous token servlet path. */
	private String ANONYMOUS_TOKEN_SERVLET_PATH = "/content/bdb/paths/bdb-anonymous-token";

	/** The purchasing account registration endpoint. */
	private String PURCHASING_ACCOUNT_REGISTRATION_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/purchasingaccount";

	/** The upload tax certificate endpoint. */
	private String UPLOAD_TAX_CERTIFICATE_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/uploadcertificate";

	/** The pub med id. */
	private String PUB_MED_ID = "https://pubmed.ncbi.nlm.nih.gov/";

	/** The test config. */
	private String TEST_CONFIG = "testConfig: DefaultValue";

	/** The user settings endpoint. */
	private String USER_SETTINGS_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/userdetails";

	/** The update user details endpoint. */
	private String UPDATE_USER_DETAILS_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The update user pwd endpoint. */
	private String UPDATE_USER_PWD_ENDPOINT = "/occ/v2/{{site}}/bdbusers/users/{userId}/password";

	/** The aem signup endpoint. */
	private String AEM_SIGNUP_ENDPOINT = "/content/bdb/paths/register-user.signupcall.json";

	/** The aem signup preference endpoint. */
	private String AEM_SIGNUP_PREFERENCE_ENDPOINT = "/content/bdb/paths/register-user.areaoffocus.json";

	/** The aem reset password endpoint. */
	private String AEM_RESET_PASSWORD_ENDPOINT = "/content/bdb/paths/register-user.resetpassword.json";

	/** The aem purchasing account registration endpont. */
	private String AEM_PURCHASING_ACCOUNT_REGISTRATION_ENDPONT = "/content/bdb/paths/register-user.purchaseaccount.json";

	/** The anonymous user id placeholder. */
	private String ANONYMOUS_USER_ID_PLACEHOLDER = "anonymous";

	/** The current user id placeholder. */
	private String CURRENT__USER_ID_PLACEHOLDER = "current";

	/** The fetch auth token endpoint client id. */
	private String FETCH_AUTH_TOKEN_ENDPOINT_CLIENT_ID = "bdb";

	/** The fetch auth token endpoint grant type. */
	private String FETCH_AUTH_TOKEN_ENDPOINT_GRANT_TYPE = "client_credentials";

	/** The fetch auth token endpoint client secret. */
	private String FETCH_AUTH_TOKEN_ENDPOINT_CLIENT_SECRET = "password";

	/** The fetch auth token endpoin. */
	private String FETCH_AUTH_TOKEN_ENDPOIN = "/authorizationserver/oauth/token?";

	/** The all customers cart. */
	private String ALL_CUSTOMERS_CART = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts";

	/** The add quantity. */
	private String ADD_QUANTITY = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry";

	/** The sso login ping id domain. */
	private String SSO_LOGIN_PING_ID_DOMAIN = "https://ssodev.bd.com";

	/** The sso login ping id endpoint. */
	private String SSO_LOGIN_PING_ID_ENDPOINT = "/as/authorization.oauth2";

	/** The sso login response type. */
	private String SSO_LOGIN_RESPONSE_TYPE = "code";

	/** The sso login client id. */
	private String SSO_LOGIN_CLIENT_ID = "Galaxy_OIDC";

	/** The sso customer login service. */
	private String SSO_CUSTOMER_LOGIN_SERVICE = "/content/bdb/paths/sso-customer-login";

	/** The sso login scope. */
	private String SSO_LOGIN_SCOPE = "openid+profile";

	/** The customer login endpoint. */
	private String CUSTOMER_LOGIN_ENDPOINT = "/occ/v2/{{site}}/login/token";

	/** The create shopping list endpont. */
	private String CREATE_SHOPPING_LIST_ENDPONT = CommonConstants.SHOPPING_LIST_URI;
	
	/** The create quote details endpont. */
	private String CREATE_QUOTES_HISTORY_ENDPONT = "/occ/v2/{{siteId}}/users/{{userId}}/quote/getquotehistory?fields=DEFAULT&shipTo={{shipTo}}";
	
	/** The create quote details endpont. */
	private String CREATE_QUOTES_DETAILS_ENDPONT = "/occ/v2/{{siteId}}/users/{{userId}}/quote/getquotedetail?custPurchaseOrderNumber={{poNumber}}&fields=DEFAULT&quoteRefNum={{quoteRefNum}}";

	/** The shopping list details endpoint. */
	private String SHOPPING_LIST_DETAILS_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/details";

	/** The file upload shhopping list endpoint. */
	private String FILE_UPLOAD_SHHOPPING_LIST_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/fileupload";

	/** The share shopping list endpont. */
	private String SHARE_SHOPPING_LIST_ENDPONT = "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/share";
	
	/** The export shopping list endpont. */
	private String EXPORT_SHOPPING_LIST_ENDPONT = "/occ/v2/{{baseSiteId}}/users/{userId}/account/shoppinglist/exportShoppingList";

	/** The remoove shopping list entries endpont. */
	private String REMOOVE_SHOPPING_LIST_ENTRIES_ENDPONT = "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/entries";

	/** The update shopping list entries endpont. */
	private String UPDATE_SHOPPING_LIST_ENTRIES_ENDPONT = "/occ/v2/{{site}}/users/{userId}/account/shoppinglist/entries";

	/** The file upload update shopping list entries endpont. */
	private String FILE_UPLOAD_SHOPPING_LIST_ENTRIES_ENDPONT = "/occ/v2/{{site}}/bdbaccount/users/{userId}/shoppinglist/fileupload/entries";

	/** The cart with identifier. */
	private String CART_WITH_IDENTIFIER = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

	/** The delete entry from cart. */
	private String DELETE_ENTRY_FROM_CART = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry/{{entryNumber}}";

	/** The save for later. */
	private String SAVE_FOR_LATER = "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart/{{savedForLaterCartId}}";

	/** The create save for later cart. */
	private String CREATE_SAVE_FOR_LATER_CART = "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart";

	/** The replace cart entry. */
	private String REPLACE_CART_ENTRY = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry/{{entryNumber}}/{{productCode}}";

	/** The replace save for lotery entry. */
	private String REPLACE_SAVE_FOR_LOTERY_ENTRY = "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}/{{productCode}}";

	/** The add save for later. */
	private String ADD_SAVE_FOR_LATER = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The bulk remove from cart. */
	private String BULK_REMOVE_FROM_CART = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The bulk add to save for latter. */
	private String BULK_ADD_TO_SAVE_FOR_LATTER = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries";

	/** The add to cart from save to latter. */
	private String ADD_TO_CART_FROM_SAVE_TO_LATTER = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/updatecurrentcart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The delete save to latter. */
	private String DELETE_SAVE_TO_LATTER = "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The cart config endpoint. */
	private String CART_CONFIG_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

	/** The country drop down endpoint. */
	private String COUNTRY_DROP_DOWN_ENDPOINT = "/etc/acs-commons/lists/bdb/region_country_language_mappings/country_mapping";
	
	/** The language drop down endpoint. */
	private String LANGUAGE_DROP_DOWN_ENDPOINT = "/etc/acs-commons/lists/bdb/region_country_language_mappings/language_mapping";

	/** The order history for grants endpoint. */
	private String ORDER_HISTORY_FOR_GRANTS_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

	/** The payments endpoint. */
	private String PAYMENTS_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries";

	/** The add credit cart endpoint. */
	private String ADD_CREDIT_CART_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

	/** The remove credit cart endpoint. */
	private String REMOVE_CREDIT_CART_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart";

	/** The update credit cart endpoint. */
	private String UPDATE_CREDIT_CART_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

	/** The messeges endpoint. */
	private String MESSEGES_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries";

	/** The read messeges endpoint. */
	private String READ_MESSEGES_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

	/** The quotes endpoint. */
	private String QUOTES_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries";

	/** The orders endpoint. */
	private String ORDERS_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart";

	/** The mini cart count config endpoint. */
	private String MINI_CART_COUNT_CONFIG_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries";

	/** The mini cart entriees config endpoint. */
	private String MINI_CART_ENTRIEES_CONFIG_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

	/** The update po number endppoiint. */
	private String UPDATE_PO_NUMBER_ENDPPOIINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart";

	/** The promotion id details servlet path. */
	private String PROMOTION_ID_DETAILS_SERVLET_PATH = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries";

	/** The promotions msg path. */
	private String PROMOTIONS_MSG_PATH = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}";

	/** The aem upload tax certificate endpoint. */
	private String AEM_UPLOAD_TAX_CERTIFICATE_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The updates cart vat exempt status. */
	private String UPDATES_CART_VAT_EXEMPT_STATUS = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The anti body details endpoint. */
	private String ANTI_BODY_DETAILS_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The price date endpoint. */
	private String PRICE_DATE_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The formats details endpoint. */
	private String FORMATS_DETAILS_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The user order list endpoint. */
	private String USER_ORDER_LIST_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The order details endpoint. */
	private String ORDER_DETAILS_ENDPOINT = "/content/bdb/paths/register-user.signupcall.json";

	/** The order packing list endpoint. */
	private String ORDER_PACKING_LIST_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The order invoice details endpoint. */
	private String ORDER_INVOICE_DETAILS_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The hybris sign out endpoint. */
	private String HYBRIS_SIGN_OUT_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The dev sso sign out endpoint. */
	private String DEV_SSO_SIGN_OUT_ENDPOINT = "/content/bdb/paths/register-user.signupcall.json";

	/** The sign out servlet path. */
	private String SIGN_OUT_SERVLET_PATH = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The search results servlet path. */
	private String SEARCH_RESULTS_SERVLET_PATH = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The product announcements. */
	private String PRODUCT_ANNOUNCEMENTS = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The add all items to cart endpoint. */
	private String ADD_ALL_ITEMS_TO_CART_ENDPOINT = "/content/bdb/paths/register-user.signupcall.json";

	/** The updates vat exempt status. */
	private String UPDATES_VAT_EXEMPT_STATUS = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The validate my cart. */
	private String VALIDATE_MY_CART = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The SHI P to ADDRES S CONFIG. */
	private String SHIP_To_ADDRESS_CONFIG = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The apply coupon. */
	private String APPLY_COUPON = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The ADDRES S WIT H no RU O ENDPOINT. */
	private String ADDRESS_WITH_No_RUO_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The search list endpoint. */
	private String SEARCH_LIST_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The update address config. */
	private String UPDATE_ADDRESS_CONFIG = "/content/bdb/paths/register-user.signupcall.json";

	/** The promo products list endpoint. */
	private String PROMO_PRODUCTS_LIST_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The quote reference. */
	private String QUOTE_REFERENCE = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The notificatioon endpoint. */
	private String NOTIFICATIOON_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The user id le time. */
	private String USER_ID_LE_TIME = "/content/bdb/paths/register-user.signupcall.json";

	/** The minimum products. */
	private String MINIMUM_PRODUCTS = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The maximum products. */
	private String MAXIMUM_PRODUCTS = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The add new address endpoint. */
	private String ADD_NEW_ADDRESS_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The payment details. */
	private String PAYMENT_DETAILS = "/content/bdb/paths/register-user.signupcall.json";

	/** The other details. */
	private String OTHER_DETAILS = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The order confirmation checkout config. */
	private String ORDER_CONFIRMATION_CHECKOUT_CONFIG = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The product detaails endpoint. */
	private String PRODUCT_DETAAILS_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The order confirmation cancel config. */
	private String ORDER_CONFIRMATION_CANCEL_CONFIG = "/content/bdb/paths/register-user.signupcall.json";

	/** The place order config. */
	private String PLACE_ORDER_CONFIG = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The post smart cart register endpoint. */
	private String POST_SMART_CART_REGISTER_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The distributer options endpoit. */
	private String DISTRIBUTER_OPTIONS_ENDPOIT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The punch out endpoint. */
	private String PUNCH_OUT_ENDPOINT = "/content/bdb/paths/register-user.signupcall.json";

	/** The landing page path. */
	private String LANDING_PAGE_PATH = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The cart page path. */
	private String CART_PAGE_PATH = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The punch out transmit request. */
	private String PUNCH_OUT_TRANSMIT_REQUEST = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The punch out cancel request. */
	private String PUNCH_OUT_CANCEL_REQUEST = "/content/bdb/paths/register-user.signupcall.json";

	/** The quote config. */
	private String QUOTE_CONFIG = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The quote confirmation config. */
	private String QUOTE_CONFIRMATION_CONFIG = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The sent email. */
	private String SENT_EMAIL = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The languag dropdown endpoint. */
	private String LANGUAG_DROPDOWN_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/updateinformation";

	/** The order inquiry details endpoint. */
	private String ORDER_INQUIRY_DETAILS_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}";

	/** The post order inquiry details endpoint. */
	private String POST_ORDER_INQUIRY_DETAILS_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/";

	/** The trigger reorder endpoint. */
	private String TRIGGER_REORDER_ENDPOINT = "/occ/v2/{{baseSiteId}}/orgUsers/{userId}/cartFromOrder";
	
	/** The refresh token endpoint. */
	private String REFRESH_TOKEN_ENDPOINT = "/content/bdb/paths/refresh-token.json";

	/** The beadlots file download endpoint. */
	private String BEADLOTS_FILE_DOWNLOAD_ENDPOINT = "https://apidev.carefusion.com/BeadLotsAPI/erpDoc/beadLots";

	/** The beadlots file download username. */
	private String BEADLOTS_FILE_DOWNLOAD_USERNAME = "beadlotsmule";

	/** The beadlots filedownload pwd. */
	private String BEADLOTS_FILEDOWNLOAD_PWD = "Be@dLotsMule*";

	/** The beadlots file download servlet path. */
	private String BEADLOTS_FILE_DOWNLOAD_SERVLET_PATH = "/content/bdb/paths/beadlots-file-download";

	/** The my orders list endpoint. */
	private String MY_ORDERS_LIST_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/current/account/orders/orderList";

	/** The all orders list endpoint. */
	private String ALL_ORDERS_LIST_ENDPOINT = "/occ/v2/{{baseSiteId}}/users/current/account/orders/allorders";

	/** The cancel order endpoint. */
	private String CANCEL_ORDER_ENDPOINT = "/occ/v2/{{baseSiteId}}/orgUsers/current/orders/{orderId}/cancellation";

	/** The required companion products endpoint. */
	private String REQUIRED_COMPANION_PRODUCTS_ENDPOINT = "/content/bdb/paths/get-related-companion-products";

	/** The price endpoint. */
	private String PRICE_ENDPOINT = "/occ/v2/bdbUS/users/{{userId}}/products";

	/** The paymetric token endpoint. */
	private String PAYMETRIC_TOKEN_ENDPOINT = "/occ/v2/{{baseSiteId}}/payment/paymetric/accesstoken";

	/** The paymetric domain. */
	private String PAYMETRIC_DOMAIN = "https://cert-xiecomm.paymetric.com";

	/** The paymetric iframe endpoint. */
	private String PAYMETRIC_IFRAME_ENDPOINT = "/diecomm/View/Iframe/{{merchant_ID}}/{{token}}/True";
	
	/** The brightcove account id. */
	private String BRIGHTCOVE_ACCOUNT_ID = "81909694001";

	/** The brightcove player id. */
	private String BRIGHTCOVE_PLAYER_ID = "smS3oTdDk";
	
	
	/**
	 * Sets up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		PrivateAccessor.setField(bdbendpointImplObj, "bdbHybrisDomain", VALUE_BDB_HYBRIS_DOMAIN);
		PrivateAccessor.setField(bdbendpointImplObj, "captchaClientKey", VALUE_CAPTCHA_CLIENT_KEY);
		PrivateAccessor.setField(bdbendpointImplObj, "regionCountryDropdownEndpoint",
				VALUE_REGION_COUNTRY_DROPDOWN_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "hybrisSignUpEndpoint", VALUE_SIGN_UP_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "hybrisSignUpPreferenceEndpoint",
				VALUE_SIGN_UP_PREFERENCE_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "hybrisResetPasswordEndpoint", VALUE_RESET_PASSWORD_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getListOfUserCertificationsEndpoint",
				VALUE_GET_LIST_OF_USER_CERTIFICATIONS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "deleteCertificateEndpoint", VALUE_DELETE_CERTIFICATE_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "uploadRuoCertificateEndpoint",
				VALUE_UPLOAD_RUO_CERTIFICATE_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getAddressesEndpoint", VALUE_GET_ADDRESSES_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "updateFavoriteAddressEndpoint",
				VALUE_UPDATE_FAVORITE_ADDRESS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "defaultAddressEndpoint", VALUE_DEFAULT_ADDRESS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "updateNicknameEndpoint", VALUE_UPDATE_NICKNAME_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "createAddressEndpoint", VALUE_CREATE_ADDRESS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "reactivateUserEndpoint", VALUE_REACTIVATE_USER_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getGrantsForCustomerEndpoint",
				VALUE_GET_GRANTS_FOR_CUSTOMER_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "applyGrantsOnCartEndpoint", VALUE_APPLY_GRANTS_ON_CART_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "updateCartQuantityEndpoint", VALUE_GET_UPDATE_CART_QUANTITY);
		PrivateAccessor.setField(bdbendpointImplObj, "updateLotIndicatorEndpoint", VALUE_GET_UPDATE_LOT_INDICATOR);
		PrivateAccessor.setField(bdbendpointImplObj, "orderDetailsApproverEndpoint",
				VALUE_ORDER_DETAILS_APPROVER_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "orderListApproverEndpoint", VALUE_ORDER_LIST_APPROVER_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "orderApprovalDecisionEndpoint",
				VALUE_ORDER_APPROVAL_DECISION_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "quickAddBulkEntryEndpoint", VALUE_GET_BULK_ENTRY_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "quickAddBulkUploadEndpoint", VALUE_GET_BULK_UPLOAD_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "replaceCartEntry", VALUE_REPLACE_CART_ENTRY_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "replaceSaveForLaterEntry", VALUE_REPLACE_SAVE_FOR_LATER_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "shippingDetailsConfig", VALUE_GET_SHIPPING_DETAILS);
		PrivateAccessor.setField(bdbendpointImplObj, "billingDetailsConfig", VALUE_GET_BILLING_DETAILS);
		PrivateAccessor.setField(bdbendpointImplObj, "mergeAnonymousCartEndpoint", VALUE_MERGE_ANONYMOUS_CART_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "deleteCoupon", DELETE_COUPON);
		PrivateAccessor.setField(bdbendpointImplObj, "pingIdSsoSignOutDomain", PING_ID_SSO_SIGN_OUT_DOMAIN);
		PrivateAccessor.setField(bdbendpointImplObj, "anonymousTokenServletPath", ANONYMOUS_TOKEN_SERVLET_PATH);
		PrivateAccessor.setField(bdbendpointImplObj, "purchasingAccountRegistrationEndpoint",
				PURCHASING_ACCOUNT_REGISTRATION_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "uploadTaxCertificateEndpoint", UPLOAD_TAX_CERTIFICATE_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "pubMedId", PUB_MED_ID);
		PrivateAccessor.setField(bdbendpointImplObj, "testConfig", TEST_CONFIG);
		PrivateAccessor.setField(bdbendpointImplObj, "getUserSettingsEndpoint", USER_SETTINGS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "updateUserDetailsEndpoint", UPDATE_USER_DETAILS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "updateUserPwdEndpoint", UPDATE_USER_PWD_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "requestTimeoutConfig", 30);
		PrivateAccessor.setField(bdbendpointImplObj, "socketTimeoutConfig", 30);
		PrivateAccessor.setField(bdbendpointImplObj, "connectTimeoutConfig", 30);
		PrivateAccessor.setField(bdbendpointImplObj, "cookieExpiryTime", 3600);
		PrivateAccessor.setField(bdbendpointImplObj, "aemSignUpEndpoint", AEM_SIGNUP_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "aemSignUpPreferenceEndpoint", AEM_SIGNUP_PREFERENCE_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "aemResetPasswordEndpoint", AEM_RESET_PASSWORD_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "aemPurchasingAccountRegistrationEndpoint",
				AEM_PURCHASING_ACCOUNT_REGISTRATION_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "anonymousUserIdPlaceholder", ANONYMOUS_USER_ID_PLACEHOLDER);
		PrivateAccessor.setField(bdbendpointImplObj, "currentUserIdPlaceholder", CURRENT__USER_ID_PLACEHOLDER);
		PrivateAccessor.setField(bdbendpointImplObj, "fetchAuthTokenEndpointClientId",
				FETCH_AUTH_TOKEN_ENDPOINT_CLIENT_ID);
		PrivateAccessor.setField(bdbendpointImplObj, "fetchAuthTokenEndpointGrantType",
				FETCH_AUTH_TOKEN_ENDPOINT_GRANT_TYPE);
		PrivateAccessor.setField(bdbendpointImplObj, "fetchAuthTokenEndpointClientSecret",
				FETCH_AUTH_TOKEN_ENDPOINT_CLIENT_SECRET);
		PrivateAccessor.setField(bdbendpointImplObj, "fetchAuthTokenEndpoint", FETCH_AUTH_TOKEN_ENDPOIN);
		PrivateAccessor.setField(bdbendpointImplObj, "getAllCustomersCart", ALL_CUSTOMERS_CART);
		PrivateAccessor.setField(bdbendpointImplObj, "addQuantity", ADD_QUANTITY);
		PrivateAccessor.setField(bdbendpointImplObj, "ssoLoginPingIdDomain", SSO_LOGIN_PING_ID_DOMAIN);
		PrivateAccessor.setField(bdbendpointImplObj, "ssoLoginPingIdEndpoint", SSO_LOGIN_PING_ID_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "ssoLoginResponseType", SSO_LOGIN_RESPONSE_TYPE);
		PrivateAccessor.setField(bdbendpointImplObj, "ssoLoginClientId", SSO_LOGIN_CLIENT_ID);
		PrivateAccessor.setField(bdbendpointImplObj, "ssoCustomerLoginService", SSO_CUSTOMER_LOGIN_SERVICE);
		PrivateAccessor.setField(bdbendpointImplObj, "ssoLoginScope", SSO_LOGIN_SCOPE);
		PrivateAccessor.setField(bdbendpointImplObj, "getCustomerLoginEndpoint", CUSTOMER_LOGIN_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "createShoppingListEndpoint", CREATE_SHOPPING_LIST_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "getShoppingListDetailsEndpoint", SHOPPING_LIST_DETAILS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getShoppingListEndpoint", CREATE_SHOPPING_LIST_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "getQuotesHistoryEndpoint", CREATE_QUOTES_HISTORY_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "getQuoteDetailsEndpoint", CREATE_QUOTES_DETAILS_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "fileUploadShoppingListEndpoint",
				FILE_UPLOAD_SHHOPPING_LIST_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "removeShoppingListEndpoint", CREATE_SHOPPING_LIST_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "shareShoppingListEndpoint", SHARE_SHOPPING_LIST_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "exportShoppingListEndpoint", EXPORT_SHOPPING_LIST_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "removeShoppingListEntriesEndpoint",
				REMOOVE_SHOPPING_LIST_ENTRIES_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "updateShoppingListEntriesEndpoint",
				UPDATE_SHOPPING_LIST_ENTRIES_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "fileUploadShoppingListEntriesEndpoint",
				FILE_UPLOAD_SHOPPING_LIST_ENTRIES_ENDPONT);
		PrivateAccessor.setField(bdbendpointImplObj, "cartWithIdentifier", CART_WITH_IDENTIFIER);
		PrivateAccessor.setField(bdbendpointImplObj, "deleteEntryFromCart", DELETE_ENTRY_FROM_CART);
		PrivateAccessor.setField(bdbendpointImplObj, "saveForLater", SAVE_FOR_LATER);
		PrivateAccessor.setField(bdbendpointImplObj, "createSaveForLaterCart", CREATE_SAVE_FOR_LATER_CART);
		PrivateAccessor.setField(bdbendpointImplObj, "addToSaveForLater", ADD_SAVE_FOR_LATER);
		PrivateAccessor.setField(bdbendpointImplObj, "bulkRemoveFromCart", BULK_REMOVE_FROM_CART);
		PrivateAccessor.setField(bdbendpointImplObj, "bulkAddToSaveForLater", BULK_ADD_TO_SAVE_FOR_LATTER);
		PrivateAccessor.setField(bdbendpointImplObj, "addToCartFromSaveToLater", ADD_TO_CART_FROM_SAVE_TO_LATTER);
		PrivateAccessor.setField(bdbendpointImplObj, "deleteSaveForLater", DELETE_SAVE_TO_LATTER);
		PrivateAccessor.setField(bdbendpointImplObj, "cartConfigEndpoint", CART_CONFIG_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "countryDropdownEndpoint", COUNTRY_DROP_DOWN_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "languageDropdownEndpoint", LANGUAGE_DROP_DOWN_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "orderHistoryForGrantsEndpoint",
				ORDER_HISTORY_FOR_GRANTS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getPaymentsEndpoint", PAYMENTS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "addCreditCardEndpoint", ADD_CREDIT_CART_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "removeCreditCardEndpoint", REMOVE_CREDIT_CART_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "updateCreditCardEndpoint", UPDATE_CREDIT_CART_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getMessagesEndpoint", MESSEGES_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "readMessageEndpoint", READ_MESSEGES_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getQuotesEndpoint", QUOTES_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getOrdersEndpoint", ORDERS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "miniCartCountConfigEndpoint", MINI_CART_COUNT_CONFIG_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "miniCartEntriesConfigEndpoint",
				MINI_CART_ENTRIEES_CONFIG_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "updatePoNumberEndpoint", UPDATE_PO_NUMBER_ENDPPOIINT);
		PrivateAccessor.setField(bdbendpointImplObj, "promotionIdDetailsServletPath",
				PROMOTION_ID_DETAILS_SERVLET_PATH);
		PrivateAccessor.setField(bdbendpointImplObj, "promotionsMsgEndpoint", PROMOTIONS_MSG_PATH);
		PrivateAccessor.setField(bdbendpointImplObj, "aemUploadTaxCertificateEndpoint",
				AEM_UPLOAD_TAX_CERTIFICATE_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "updatesCartVATExemptStatus", UPDATES_CART_VAT_EXEMPT_STATUS);
		PrivateAccessor.setField(bdbendpointImplObj, "antiBodyDetailsEndPoint", ANTI_BODY_DETAILS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "priceDataEndPoint", PRICE_DATE_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "formatsDetailsEndPoint", FORMATS_DETAILS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getUserOrdersListEndpoint", USER_ORDER_LIST_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getOrderDetailsEndpoint", ORDER_DETAILS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getOrderPackingListEndpoint", ORDER_PACKING_LIST_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getHybrisSignOutEndpoint", HYBRIS_SIGN_OUT_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "devSsoSignOutEndpoint", DEV_SSO_SIGN_OUT_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getSignOutServletPath", SIGN_OUT_SERVLET_PATH);
		PrivateAccessor.setField(bdbendpointImplObj, "searchResultsServletPath", SEARCH_RESULTS_SERVLET_PATH);
		PrivateAccessor.setField(bdbendpointImplObj, "productAnnouncements", PRODUCT_ANNOUNCEMENTS);
		PrivateAccessor.setField(bdbendpointImplObj, "addAllItemsToCartEndpoint", ADD_ALL_ITEMS_TO_CART_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "updatesVATExemptStatus", UPDATES_VAT_EXEMPT_STATUS);
		PrivateAccessor.setField(bdbendpointImplObj, "validateMyCart", VALIDATE_MY_CART);
		PrivateAccessor.setField(bdbendpointImplObj, "shipToAddressConfig", SHIP_To_ADDRESS_CONFIG);
		PrivateAccessor.setField(bdbendpointImplObj, "applyCoupon", APPLY_COUPON);
		PrivateAccessor.setField(bdbendpointImplObj, "addressWithNoRuoEndpoint", ADDRESS_WITH_No_RUO_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "searchListEndpoint", SEARCH_LIST_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "updateAddressConfig", UPDATE_ADDRESS_CONFIG);
		PrivateAccessor.setField(bdbendpointImplObj, "promoProductsListEndpoint", PROMO_PRODUCTS_LIST_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "quoteReference", QUOTE_REFERENCE);
		PrivateAccessor.setField(bdbendpointImplObj, "notificationEndpoint", NOTIFICATIOON_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "userIdleTime", 30);
		PrivateAccessor.setField(bdbendpointImplObj, "minimumProducts", 10);
		PrivateAccessor.setField(bdbendpointImplObj, "maximumProducts", 100);
		PrivateAccessor.setField(bdbendpointImplObj, "addNewAddressEndPoint", ADD_NEW_ADDRESS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getPaymentDetails", PAYMENT_DETAILS);
		PrivateAccessor.setField(bdbendpointImplObj, "getOtherDetails", OTHER_DETAILS);
		PrivateAccessor.setField(bdbendpointImplObj, "orderConfirmationCheckoutConfig",
				ORDER_CONFIRMATION_CHECKOUT_CONFIG);
		PrivateAccessor.setField(bdbendpointImplObj, "getProductDetailsEndpoint", PRODUCT_DETAAILS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "orderConfirmationCancelOrderConfig",
				ORDER_CONFIRMATION_CANCEL_CONFIG);
		PrivateAccessor.setField(bdbendpointImplObj, "placeOrderConfig", PLACE_ORDER_CONFIG);
		PrivateAccessor.setField(bdbendpointImplObj, "postSmartCartRegisterEndpoint",
				POST_SMART_CART_REGISTER_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getDistributorsOptionsEndpoint", DISTRIBUTER_OPTIONS_ENDPOIT);
		PrivateAccessor.setField(bdbendpointImplObj, "punchOutEndpoint", PUNCH_OUT_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "punchoutTransmitRequest", PUNCH_OUT_TRANSMIT_REQUEST);
		PrivateAccessor.setField(bdbendpointImplObj, "punchoutCancelRequest", PUNCH_OUT_CANCEL_REQUEST);
		PrivateAccessor.setField(bdbendpointImplObj, "quoteConfig", QUOTE_CONFIG);
		PrivateAccessor.setField(bdbendpointImplObj, "quoteConfirmationConfig", QUOTE_CONFIRMATION_CONFIG);
		PrivateAccessor.setField(bdbendpointImplObj, "sendEmail", SENT_EMAIL);
		PrivateAccessor.setField(bdbendpointImplObj, "languageSearchDropdownEndpoint", LANGUAG_DROPDOWN_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getOrderInquiryDetailEndpoint", ORDER_INQUIRY_DETAILS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "postOrderInquiryDetailsEndpoint",
				POST_ORDER_INQUIRY_DETAILS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "triggerReOrderEndpoint", TRIGGER_REORDER_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getRefreshTokenEndpoint", REFRESH_TOKEN_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "beadlotsFileDownloadEndpoint", BEADLOTS_FILE_DOWNLOAD_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "beadlotsFileDownloadUsername", BEADLOTS_FILE_DOWNLOAD_USERNAME);
		PrivateAccessor.setField(bdbendpointImplObj, "beadlotsFileDownloadPwd", BEADLOTS_FILEDOWNLOAD_PWD);
		PrivateAccessor.setField(bdbendpointImplObj, "beadlotsFileDownloadServletPath", BEADLOTS_FILE_DOWNLOAD_SERVLET_PATH);
		PrivateAccessor.setField(bdbendpointImplObj, "myOrdersListEndpoint", MY_ORDERS_LIST_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "allOrdersListEndpoint", ALL_ORDERS_LIST_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "cancelOrderEndpoint", CANCEL_ORDER_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "requiredCompanionProductsEndpoint", REQUIRED_COMPANION_PRODUCTS_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "getPriceEndpoint", PRICE_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "paymetricTokenEndpoint", PAYMETRIC_TOKEN_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "paymetricDomain", PAYMETRIC_DOMAIN);
		PrivateAccessor.setField(bdbendpointImplObj, "paymetricIframeEndpoint", PAYMETRIC_IFRAME_ENDPOINT);
		PrivateAccessor.setField(bdbendpointImplObj, "brightcovePlayerId", BRIGHTCOVE_PLAYER_ID);
		PrivateAccessor.setField(bdbendpointImplObj, "brightcoveAccountId", BRIGHTCOVE_ACCOUNT_ID);
		
	}

	/**
	 * Test activate.
	 */
	@Test
	void testActivate() {
		when(config.bdbHybrisDomain()).thenReturn("https://api-qa1.bdbiosciences.com");
		when(config.captchaClientKey()).thenReturn("6Lf1V6YZAAAAAObvM_QEf7S9ZMHslwIf2LE7qpnM");
		when(config.anonymousTokenServletPath()).thenReturn("/content/bdb/paths/bdb-anonymous-token");
		when(config.regionCountryDropdownEndpoint()).thenReturn("/etc/acs-commons/lists/bdb/region-country-mapping");
		when(config.hybrisSignUpEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/register");
		when(config.hybrisSignUpPreferenceEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/lineofwork");
		when(config.hybrisResetPasswordEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/resetpassword");
		when(config.countriesFromRegionServletPath()).thenReturn("/content/bdb/paths/get-country-from-region.json");
		when(config.commerceBoxAPIEndpoint()).thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/products");
		when(config.purchasingAccountRegistrationEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{userId}/purchasingaccount");
		when(config.uploadTaxCertificateEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/uploadcertificate");
		when(config.pubMedIdEndpoint()).thenReturn("https://pubmed.ncbi.nlm.nih.gov/");
		when(config.countryStateDropdownEndpoint()).thenReturn("/etc/acs-commons/lists/bdb/country-state-mapping");
		when(config.statesFromCountryServletPath()).thenReturn("/content/bdb/paths/get-states-from-country.json");
		when(config.testConfig()).thenReturn("testConfig: DefaultValue");
		when(config.getUserSettingsEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/userdetails");
		when(config.updateUserDetailsEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/updateinformation");
		when(config.updateUserPwdEndpoint()).thenReturn("/occ/v2/{{site}}/bdbusers/users/{userId}/password");
		when(config.getCustomerLoginEndpoint()).thenReturn("/occ/v2/{{site}}/login/token");
		when(config.requestTimeoutConfig()).thenReturn("30");
		when(config.socketTimeoutConfig()).thenReturn("30");
		when(config.cookieExpiryTime()).thenReturn("3600");
		when(config.connectTimeoutConfig()).thenReturn("30");
		when(config.aemSignUpEndpoint()).thenReturn("/content/bdb/paths/register-user.signupcall.json");
		when(config.aemSignUpPreferenceEndpoint()).thenReturn("/content/bdb/paths/register-user.areaoffocus.json");
		when(config.aemResetPasswordEndpoint()).thenReturn("/content/bdb/paths/register-user.resetpassword.json");
		when(config.aemPurchasingAccountRegistrationEndpoint())
				.thenReturn("/content/bdb/paths/register-user.purchaseaccount.json");
		when(config.anonymousUserIdPlaceholder()).thenReturn("anonymous");
		when(config.currentUserIdPlaceholder()).thenReturn("current");
		when(config.fetchAuthTokenEndpointClientId()).thenReturn("bdb");
		when(config.fetchAuthTokenEndpointGrantType()).thenReturn("client_credentials");
		when(config.fetchAuthTokenEndpointClientSecret()).thenReturn("password");
		when(config.fetchAuthTokenEndpoint()).thenReturn("/authorizationserver/oauth/token?");
		when(config.getListOfUserCertificationsEndpoint()).thenReturn("/occ/v2/{{site}}/user/certifications");
		when(config.deleteCertificateEndpoint()).thenReturn("/occ/v2/{{site}}/bdbusers/deleteCertificate");
		when(config.uploadRuoCertificateEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/upload/certificate");
		when(config.getAddressesEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/addresses/addressList");
		when(config.getAllCustomersCartEndpoint()).thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts");
		when(config.addQuantityEndpoint()).thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry");
		when(config.updateFavoriteAddressEndpoint())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{userId}/addresses/updateFavorite");
		when(config.defaultAddressEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/addresses/default");
		when(config.updateNicknameEndpoint())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{userId}/addresses/updateNickname");
		when(config.createAddressEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/addresses/create");
		when(config.reactivateUserEndpoint()).thenReturn("/occ/v2/{{baseSiteId}}/users/{userId}/reactivate");
		when(config.ssoLoginPingIdDomain()).thenReturn("https://ssodev.bd.com");
		when(config.ssoLoginPingIdEndpoint()).thenReturn("/as/authorization.oauth2");
		when(config.ssoLoginResponseType()).thenReturn("code");
		when(config.ssoLoginClientId()).thenReturn("Galaxy_OIDC");
		when(config.ssoCustomerLoginService()).thenReturn("/content/bdb/paths/sso-customer-login");
		when(config.ssoLoginScope()).thenReturn("openid+profile");
		when(config.createShoppingListEndpoint()).thenReturn(CommonConstants.SHOPPING_LIST_URI);
		when(config.getShoppingListDetailsEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{userId}/account/shoppinglist/details");
		when(config.getShoppingListEndpoint()).thenReturn(CommonConstants.SHOPPING_LIST_URI);
		when(config.fileUploadShoppingListEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{userId}/account/shoppinglist/fileupload");
		when(config.removeShoppingListEndpoint()).thenReturn(CommonConstants.SHOPPING_LIST_URI);
		when(config.shareShoppingListEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{userId}/account/shoppinglist/share");
		when(config.exportShoppingListEndpoint())
		  		.thenReturn("/occ/v2/{baseSiteId}/users/{userId}/account/shoppinglist/exportShoppingList");
		when(config.removeShoppingListEntriesEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{userId}/account/shoppinglist/entries");
		when(config.updateShoppingListEntriesEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{userId}/account/shoppinglist/entries");
		when(config.fileUploadShoppingListEntriesEndpoint())
				.thenReturn("/occ/v2/{{site}}/bdbaccount/users/{userId}/shoppinglist/fileupload/entries");
		when(config.getCartWithIdentifier()).thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		when(config.getDeleteEntryFromCart())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry/{{entryNumber}}");
		when(config.getSaveForLater())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart/{{savedForLaterCartId}}");
		when(config.getCreateSaveForLaterCart())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart");
		when(config.bulkRemoveFromCart())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/");
		when(config.bulkAddToSaveForLater()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries");
		when(config.addToSaveForLater()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}");
		when(config.getReplaceCartEntry()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry/{{entryNumber}}/{{productCode}}");
		when(config.getReplaceSaveForLaterEntry()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}/{{productCode}}");
		when(config.addToCartFromSaveToLater()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/updatecurrentcart/{{savedForLaterCartId}}/entries/{{entryNumber}}");
		when(config.deleteSaveForLater()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/savedforlatercart/{{savedForLaterCartId}}/entries/{{entryNumber}}");
		when(config.cartConfigEndpoint()).thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		when(config.countryDropdownEndpoint())
				.thenReturn("/etc/acs-commons/lists/bdb/region_country_language_mappings/country_mapping");
		when(config.languageDropdownEndpoint())
				.thenReturn("/etc/acs-commons/lists/bdb/region_country_language_mappings/language_mapping");
		when(config.getGrantsForCustomerEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/grant");
		when(config.applyGrantsOnCartEndpoint()).thenReturn("/occ/v2/{{site}}/users/{{userId}}/carts/{{cartId}}/grant");
		when(config.orderHistoryForGrantsEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{userId}/grant/orderhistory?grantId={grantId}");
		when(config.getPaymentsEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/payment");
		when(config.addCreditCardEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/payment/card");
		when(config.removeCreditCardEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/payment/card/remove");
		when(config.updateCreditCardEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/payment/card/update");
		when(config.miniCartCountConfigEndpoint())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/minicart/count");
		when(config.miniCartEntriesConfigEndpoint())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/minicart");
		when(config.updateCartQuantityEndpoint())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entry/{{entryNumber}}");
		when(config.updateLotIndicatorEndpoint())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/entries/updatelotindicator");
		when(config.orderDetailsApproverEndpoint())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{userId}/orderapprovals/{orderApprovalCode}");
		when(config.orderListApproverEndpoint()).thenReturn("/occ/v2/{{baseSiteId}}/users/{userId}/orderapprovals");
		when(config.orderApprovalDecisionEndpoint()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{userId}/orderapprovals/{orderApprovalCode}/orderApprovalDecision");
		when(config.updatePoNumberEndpoint())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{userId}/orderapprovals/{orderApprovalCode}/updatePONumber");
		when(config.promotionIdDetailsServletPath())
				.thenReturn("/content/bdb/paths/get-promotion-details.{{region}}.{{country}}.{{language}}.{{promoId}}.json");
		when(config.promotionsMsgEndpoint()).thenReturn("/occ/v2/{{baseSiteId}}/promotions/product/{{catalogId}}");
		when(config.aemUploadTaxCertificateEndpoint()).thenReturn("/content/bdb/paths/upload-purchase-account-document.json");
		when(config.updatesCartVATExemptStatus())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/vat/{{vatExemptIndicator}}");
		when(config.getMessagesEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/notification");
		when(config.readMessageEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/notification/read");
		when(config.getOrdersEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/recentorders");
		when(config.getQuotesEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/quote/quoteList");
		when(config.antiBodyDetailsEndPoint()).thenReturn("/content/bdb/paths/fetch-clone-data");
		when(config.formatDetailsEndPoint()).thenReturn("/content/bdb/paths/fetch-format-data");
		when(config.priceDataEndPoint()).thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/products");
		when(config.getUserOrdersListEndpoint()).thenReturn("");
		when(config.getOrderDetailsEndpoint()).thenReturn("");
		when(config.getOrderPackingListEndpoint()).thenReturn("");
		when(config.searchResultsServletPath()).thenReturn("/content/bdb/paths/fetch-data.json");
		when(config.productAnnouncements()).thenReturn("/content/bdb/paths/get-product-announcements");
		when(config.getHybrisSignOutEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/logout");
		when(config.devSsoSignOutEndpoint()).thenReturn("/account/logoff?to=GA");
		when(config.getSignOutServletPath()).thenReturn("/content/bdb/paths/sign-out.json");
		when(config.addAllItemsToCartEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{userId}/carts/{cartId}/entries/multiple");
		when(config.updatesVATExemptStatus())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/vat/{{vatExemptIndicator}}");
		when(config.validateMyCart())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/checkout/validate");
		when(config.shipToAddressConfig()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/addresses/{{addressType}}/sorted");
		when(config.shippingDetailsConfig())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/shippingdetails");
		when(config.billingDetailsConfig())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/billingdetails");
		when(config.applyCoupon())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/coupon/{{couponCode}}");
		when(config.quickAddBulkEntryEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{{userId}}/carts/{{cartId}}/entries/multiple");
		when(config.quickAddBulkUploadEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{{userId}}/carts/{{cartId}}/entries/excel");
		when(config.addressWithNoRuoEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{userId}/addresses/addressesWithNoRUO");
		when(config.searchListEndpoint()).thenReturn("/occ/v2/bdbUS/users/{{userId}}/products");
		when(config.updateAddressConfig()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/addresses/{{addressType}}/{{addressId}}");
		when(config.promoProductsListEndpoint()).thenReturn("/occ/v2/{{site}}/promotions/{{code}}?fields=BDBPROMOTION");
		when(config.quoteReference()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/quotereference/{{quoteReferenceNumber}}");
		when(config.notificationEndpoint()).thenReturn("/occ/v2/{{baseSiteId}}/users/{userId}/notification/optin");
		when(config.userIdleTime()).thenReturn(30);
		when(config.maximumProducts()).thenReturn(4);
		when(config.minimumProducts()).thenReturn(2);
		when(config.addNewAddressEndPoint()).thenReturn("/occ/v2/{{baseSiteId}}/users/{userId}/addresses/create");
		when(config.pingIdSsoSignOutDomain()).thenReturn("https://testsso.bdbioscience.com");
		when(config.getDeleteCoupon())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/coupon/{{couponCode}}");
		when(config.mergeAnonymousCartEndpoint())
				.thenReturn("/occ/v2/{{site}}/users/{userId}/merge/{anonymousCartGuid}]");
		when(config.getPaymentDetails())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/paymentdetails");
		when(config.getOtherDetails())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/otherdetails");
		when(config.orderConfirmationCheckoutData()).thenReturn("/occ/v2/{{baseSiteId}}/orgUsers/{{userId}}/orders/");
		when(config.getProductDetails()).thenReturn("/content/bdb/paths/compare-products");
		when(config.orderConfirmationCancelOrder())
				.thenReturn("/occ/v2/{{baseSiteId}}/orgUsers/{{userId}}/orders/{{orderId}}/cancellation");
		when(config.postSmartCartRegisterEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/smartcart/register");
		when(config.getDistributorsOptionsEndpoint()).thenReturn("/occ/v2/{{site}}/users/{userId}/distributors");
		when(config.placeOrderConfig()).thenReturn("/occ/v2/{{baseSiteId}}/orgUsers/{{userId}}/orders");
		when(config.punchOutEndpoint()).thenReturn("/occ/v2/{{baseSiteId}}/punchout/login");
		when(config.punchoutTransmitRequestEndpoint()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/cxml/requestRequisition?punchoutSessionID");
		when(config.punchoutCancelRequestEndpoint()).thenReturn(
				"/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/cxml/cancelRequisition?punchoutSessionID");
		when(config.getQuoteConfig()).thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/quote");
		when(config.quoteConfirmationConfig())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/quote/{{quoteCode}}");
		when(config.sendEmail()).thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/notification/cart");
		when(config.languageSearchDropdownEndpoint()).thenReturn("/etc/acs-commons/lists/bdb/language_list_for_search");
		when(config.triggerReOrderEndpoint()).thenReturn(TRIGGER_REORDER_ENDPOINT);
		when(config.getRefreshTokenEndpoint()).thenReturn(REFRESH_TOKEN_ENDPOINT);
		when(config.beadlotsFileDownloadEndpoint()).thenReturn(BEADLOTS_FILE_DOWNLOAD_ENDPOINT);
		when(config.beadlotsFileDownloadUsername()).thenReturn(BEADLOTS_FILE_DOWNLOAD_USERNAME);
		when(config.beadlotsFileDownloadPwd()).thenReturn(BEADLOTS_FILEDOWNLOAD_PWD);
		when(config.beadlotsFileDownloadServletPath()).thenReturn(BEADLOTS_FILE_DOWNLOAD_SERVLET_PATH);
		when(config.myOrdersListEndpoint()).thenReturn(MY_ORDERS_LIST_ENDPOINT);
		when(config.allOrdersListEndpoint()).thenReturn(ALL_ORDERS_LIST_ENDPOINT);
		when(config.cancelOrderEndpoint()).thenReturn(CANCEL_ORDER_ENDPOINT);
		when(config.requiredCompanionProductsEndpoint()).thenReturn(REQUIRED_COMPANION_PRODUCTS_ENDPOINT);
		when(config.getPriceEndPoint()).thenReturn(PRICE_ENDPOINT);
		when(config.paymetricTokenEndpoint()).thenReturn(PAYMETRIC_TOKEN_ENDPOINT);
		when(config.paymetricDomain()).thenReturn(PAYMETRIC_DOMAIN);
		when(config.paymetricIframeEndpoint()).thenReturn(PAYMETRIC_IFRAME_ENDPOINT);
		when(config.brightcoveAccountId()).thenReturn(BRIGHTCOVE_ACCOUNT_ID);
		when(config.brightcovePlayerId()).thenReturn(BRIGHTCOVE_PLAYER_ID);
		
		bdbendpointImplObj.activate(config);
	}

	/**
	 * Deactivate.
	 */
	@Test
	void deactivate() {
		bdbendpointImplObj.deactivate();
	}

	@Test
	void testGetters() {
		bdbendpointImplObj.emailValidationEndpoint();
		bdbendpointImplObj.countryLanguageMapping();
		bdbendpointImplObj.eventTopicDropdownEndpoint();
		bdbendpointImplObj.getCustomRunMode();
		bdbendpointImplObj.trackingNumberConfig();
		bdbendpointImplObj.getProdUserName();
		bdbendpointImplObj.getProdUserPassword();
		bdbendpointImplObj.getScientificResPath();
		bdbendpointImplObj.getScientificResourceFolder();
		bdbendpointImplObj.getDropshipDetailsEndpoint();
		bdbendpointImplObj.getSearchOrderLookupEndpoint();
		bdbendpointImplObj.getAllPaymentDetails();
		bdbendpointImplObj.environmentType();
		bdbendpointImplObj.getActivityTabSrcEndpoint();
		bdbendpointImplObj.getEarnTabSrcEndpoint();
		bdbendpointImplObj.getRewardsTabSrcEndpoint();
		bdbendpointImplObj.getSummaryTabSrcEndpoint();
		bdbendpointImplObj.getAnnexSiteDomain();
		bdbendpointImplObj.getAnnexSiteJs();
		bdbendpointImplObj.getUpdateRewardsPreferencesEndpoint();
		bdbendpointImplObj.getAnnexSiteId();
		bdbendpointImplObj.getSdsEndpoint();
		bdbendpointImplObj.getSdsPdfDownloadEndpoint();
		bdbendpointImplObj.getSdsPdfSearchEndpoint();
		bdbendpointImplObj.getSdsEndpoint();
		bdbendpointImplObj.getSdsEndpointUser();
		bdbendpointImplObj.getSdsEndpointPassword();
		bdbendpointImplObj.getTdsEndpoint();
		bdbendpointImplObj.getStatesFromCountryServletPath();
		bdbendpointImplObj.getCountryStateDropdownEndpoint();
		bdbendpointImplObj.getCommerceBoxAPIEndpoint();
		bdbendpointImplObj.getCountriesFromRegionServletPath();
		bdbendpointImplObj.getHybrisValidatePasswordEndpoint();
		bdbendpointImplObj.getVialImagesBasePath();
		bdbendpointImplObj.getFormatImagesBasePath();
		bdbendpointImplObj.getRuoVialBasedOnProductType();
		bdbendpointImplObj.getRuoVialBasedOnVolPerTest();
		bdbendpointImplObj.getRuoVialBasedOnStorageBuffer();
		bdbendpointImplObj.getRuoVialImagesBasePath();
		
	}
	
	/**
	 * Gets the delete coupon.
	 *
	 * @return the delete coupon
	 */
	@Test
	void getDeleteCoupon() {
		assertEquals(DELETE_COUPON, bdbendpointImplObj.getDeleteCoupon());
	}

	/**
	 * Gets the ping id sso sign out domain.
	 *
	 * @return the ping id sso sign out domain
	 */
	@Test
	void getPingIdSsoSignOutDomain() {
		assertEquals(PING_ID_SSO_SIGN_OUT_DOMAIN, bdbendpointImplObj.getPingIdSsoSignOutDomain());
	}

	/**
	 * Gets the anonymous token servlet path.
	 *
	 * @return the anonymous token servlet path
	 */
	@Test
	void getAnonymousTokenServletPath() {
		assertEquals(ANONYMOUS_TOKEN_SERVLET_PATH, bdbendpointImplObj.getAnonymousTokenServletPath());
	}

	/**
	 * Gets the purchasing account registration endpoint.
	 *
	 * @return the purchasing account registration endpoint
	 */
	@Test
	void getPurchasingAccountRegistrationEndpoint() {
		assertEquals(PURCHASING_ACCOUNT_REGISTRATION_ENDPOINT,
				bdbendpointImplObj.getPurchasingAccountRegistrationEndpoint());
	}

	/**
	 * Gets the pub med id.
	 *
	 * @return the pub med id
	 */
	@Test
	void getPubMedId() {
		assertEquals(PUB_MED_ID, bdbendpointImplObj.getPubMedId());
	}

	/**
	 * Gets the upload tax certificate endpoint.
	 *
	 * @return the upload tax certificate endpoint
	 */
	@Test
	void getUploadTaxCertificateEndpoint() {
		assertEquals(UPLOAD_TAX_CERTIFICATE_ENDPOINT, bdbendpointImplObj.getUploadTaxCertificateEndpoint());
	}

	/**
	 * Gets the test config.
	 *
	 * @return the test config
	 */
	@Test
	void getTestConfig() {
		assertEquals(TEST_CONFIG, bdbendpointImplObj.getTestConfig());
	}

	/**
	 * Gets the user settings endpoint.
	 *
	 * @return the user settings endpoint
	 */
	@Test
	void getUserSettingsEndpoint() {
		assertEquals(USER_SETTINGS_ENDPOINT, bdbendpointImplObj.getUserSettingsEndpoint());
	}

	/**
	 * Update user details endpoint.
	 */
	@Test
	void updateUserDetailsEndpoint() {
		assertEquals(UPDATE_USER_DETAILS_ENDPOINT, bdbendpointImplObj.updateUserDetailsEndpoint());
	}

	/**
	 * Update user pwd endpoint.
	 */
	@Test
	void updateUserPwdEndpoint() {
		assertEquals(UPDATE_USER_PWD_ENDPOINT, bdbendpointImplObj.updateUserPwdEndpoint());
	}

	/**
	 * Gets the request timeout config.
	 *
	 * @return the request timeout config
	 */
	@Test
	void getRequestTimeoutConfig() {
		assertEquals(30, bdbendpointImplObj.getRequestTimeoutConfig());
	}

	/**
	 * Gets the socket timeout config.
	 *
	 * @return the socket timeout config
	 */
	@Test
	void getSocketTimeoutConfig() {
		assertEquals(30, bdbendpointImplObj.getSocketTimeoutConfig());
	}

	/**
	 * Gets the connect timeout config.
	 *
	 * @return the connect timeout config
	 */
	@Test
	void getConnectTimeoutConfig() {
		assertEquals(30, bdbendpointImplObj.getConnectTimeoutConfig());
	}

	/**
	 * Gets the cookie expiry time.
	 *
	 * @return the cookie expiry time
	 */
	@Test
	void getCookieExpiryTime() {
		assertEquals(3600, bdbendpointImplObj.getCookieExpiryTime());
	}

	/**
	 * Gets the aem sign up endpoint.
	 *
	 * @return the aem sign up endpoint
	 */
	@Test
	void getAemSignUpEndpoint() {
		assertEquals(AEM_SIGNUP_ENDPOINT, bdbendpointImplObj.getAemSignUpEndpoint());
	}

	/**
	 * Gets the aem sign up preference endpoint.
	 *
	 * @return the aem sign up preference endpoint
	 */
	@Test
	void getAemSignUpPreferenceEndpoint() {
		assertEquals(AEM_SIGNUP_PREFERENCE_ENDPOINT, bdbendpointImplObj.getAemSignUpPreferenceEndpoint());
	}

	/**
	 * Gets the aem reset password endpoint.
	 *
	 * @return the aem reset password endpoint
	 */
	@Test
	void getAemResetPasswordEndpoint() {
		assertEquals(AEM_RESET_PASSWORD_ENDPOINT, bdbendpointImplObj.getAemResetPasswordEndpoint());
	}

	/**
	 * Gets the aem purchasing account registration endpoint.
	 *
	 * @return the aem purchasing account registration endpoint
	 */
	@Test
	void getAemPurchasingAccountRegistrationEndpoint() {
		assertEquals(AEM_PURCHASING_ACCOUNT_REGISTRATION_ENDPONT,
				bdbendpointImplObj.getAemPurchasingAccountRegistrationEndpoint());
	}

	/**
	 * Gets the anonymous user id placeholder.
	 *
	 * @return the anonymous user id placeholder
	 */
	@Test
	void getAnonymousUserIdPlaceholder() {
		assertEquals(ANONYMOUS_USER_ID_PLACEHOLDER, bdbendpointImplObj.getAnonymousUserIdPlaceholder());
	}

	/**
	 * Gets the current user id placeholder.
	 *
	 * @return the current user id placeholder
	 */
	@Test
	void getCurrentUserIdPlaceholder() {
		assertEquals(CURRENT__USER_ID_PLACEHOLDER, bdbendpointImplObj.getCurrentUserIdPlaceholder());
	}

	/**
	 * Gets the fetch auth token endpoint client id.
	 *
	 * @return the fetch auth token endpoint client id
	 */
	@Test
	void getFetchAuthTokenEndpointClientId() {
		assertEquals(FETCH_AUTH_TOKEN_ENDPOINT_CLIENT_ID, bdbendpointImplObj.getFetchAuthTokenEndpointClientId());
	}

	/**
	 * Gets the fetch auth token endpoint grant type.
	 *
	 * @return the fetch auth token endpoint grant type
	 */
	@Test
	void getFetchAuthTokenEndpointGrantType() {
		assertEquals(FETCH_AUTH_TOKEN_ENDPOINT_GRANT_TYPE, bdbendpointImplObj.getFetchAuthTokenEndpointGrantType());
	}

	/**
	 * Gets the fetch auth token endpoint client secret.
	 *
	 * @return the fetch auth token endpoint client secret
	 */
	@Test
	void getFetchAuthTokenEndpointClientSecret() {
		assertEquals(FETCH_AUTH_TOKEN_ENDPOINT_CLIENT_SECRET,
				bdbendpointImplObj.getFetchAuthTokenEndpointClientSecret());
	}

	/**
	 * Gets the fetch auth token endpoint.
	 *
	 * @return the fetch auth token endpoint
	 */
	@Test
	void getFetchAuthTokenEndpoint() {
		assertEquals(FETCH_AUTH_TOKEN_ENDPOIN, bdbendpointImplObj.getFetchAuthTokenEndpoint());
	}

	/**
	 * Gets the all customers cart endpoint.
	 *
	 * @return the all customers cart endpoint
	 */
	@Test
	void getAllCustomersCartEndpoint() {
		assertEquals(ALL_CUSTOMERS_CART, bdbendpointImplObj.getAllCustomersCartEndpoint());
	}

	/**
	 * Gets the adds the quantity endpoint.
	 *
	 * @return the adds the quantity endpoint
	 */
	@Test
	void getaddQuantityEndpoint() {
		assertEquals(ADD_QUANTITY, bdbendpointImplObj.getaddQuantityEndpoint());
	}

	/**
	 * Gets bdb hybris domain.
	 *
	 * @return the BDB hybris domain
	 */
	@Test
	void getBDBHybrisDomain() {
		assertEquals(VALUE_BDB_HYBRIS_DOMAIN, bdbendpointImplObj.getBDBHybrisDomain());
	}

	/**
	 * Gets the billing details config.
	 *
	 * @return the billing details config
	 */
	@Test
	void getBillingDetailsConfig() {
		assertEquals(VALUE_GET_BILLING_DETAILS, bdbendpointImplObj.getBillingDetailsConfig());
	}

	/**
	 * Gets the shipping details config.
	 *
	 * @return the shipping details config
	 */
	@Test
	void getShippingDetailsConfig() {
		assertEquals(VALUE_GET_SHIPPING_DETAILS, bdbendpointImplObj.getShippingDetailsConfig());
	}

	/**
	 * Gets captcha client key.
	 *
	 * @return the captcha client key
	 */
	@Test
	void getCaptchaClientKey() {
		assertEquals(VALUE_CAPTCHA_CLIENT_KEY, bdbendpointImplObj.getCaptchaClientKey());
	}

	/**
	 * Gets region country dropdown endpoint.
	 *
	 * @return the region country dropdown endpoint
	 */
	@Test
	void getRegionCountryDropdownEndpoint() {
		assertEquals(VALUE_REGION_COUNTRY_DROPDOWN_ENDPOINT, bdbendpointImplObj.getRegionCountryDropdownEndpoint());
	}

	/**
	 * Gets hybris sign up endpoint.
	 *
	 * @return the hybris sign up endpoint
	 */
	@Test
	void getHybrisSignUpEndpoint() {
		assertEquals(VALUE_SIGN_UP_ENDPOINT, bdbendpointImplObj.getHybrisSignUpEndpoint());
	}

	/**
	 * Gets hybris sign up preference endpoint.
	 *
	 * @return the hybris sign up preference endpoint
	 */
	@Test
	void getHybrisSignUpPreferenceEndpoint() {
		assertEquals(VALUE_SIGN_UP_PREFERENCE_ENDPOINT, bdbendpointImplObj.getHybrisSignUpPreferenceEndpoint());
	}

	/**
	 * Gets hybris reset password endpoint.
	 *
	 * @return the hybris reset password endpoint
	 */
	@Test
	void getHybrisResetPasswordEndpoint() {
		assertEquals(VALUE_RESET_PASSWORD_ENDPOINT, bdbendpointImplObj.getHybrisResetPasswordEndpoint());
	}

	/**
	 * Gets the list of user certifications endpoint.
	 *
	 * @return the list of user certifications endpoint
	 */
	@Test
	void getListOfUserCertificationsEndpoint() {
		assertEquals(VALUE_GET_LIST_OF_USER_CERTIFICATIONS_ENDPOINT,
				bdbendpointImplObj.getListOfUserCertificationsEndpoint());
	}

	/**
	 * Delete certificate endpoint.
	 */
	@Test
	void deleteCertificateEndpoint() {
		assertEquals(VALUE_DELETE_CERTIFICATE_ENDPOINT, bdbendpointImplObj.deleteCertificateEndpoint());
	}

	/**
	 * Upload ruo certificate endpoint.
	 */
	@Test
	void uploadRuoCertificateEndpoint() {
		assertEquals(VALUE_UPLOAD_RUO_CERTIFICATE_ENDPOINT, bdbendpointImplObj.uploadRuoCertificateEndpoint());
	}

	/**
	 * Gets the addresses endpoint.
	 *
	 * @return the addresses endpoint
	 */
	@Test
	void getAddressesEndpoint() {
		assertEquals(VALUE_GET_ADDRESSES_ENDPOINT, bdbendpointImplObj.getAddressesEndpoint());
	}

	/**
	 * Update favorite address endpoint.
	 */
	@Test
	void updateFavoriteAddressEndpoint() {
		assertEquals(VALUE_UPDATE_FAVORITE_ADDRESS_ENDPOINT, bdbendpointImplObj.updateFavoriteAddressEndpoint());
	}

	/**
	 * Default address endpoint.
	 */
	@Test
	void defaultAddressEndpoint() {
		assertEquals(VALUE_DEFAULT_ADDRESS_ENDPOINT, bdbendpointImplObj.defaultAddressEndpoint());
	}

	/**
	 * Update nickname endpoint.
	 */
	@Test
	void updateNicknameEndpoint() {
		assertEquals(VALUE_UPDATE_NICKNAME_ENDPOINT, bdbendpointImplObj.updateNicknameEndpoint());
	}

	/**
	 * Creates the address endpoint.
	 */
	@Test
	void createAddressEndpoint() {
		assertEquals(VALUE_CREATE_ADDRESS_ENDPOINT, bdbendpointImplObj.createAddressEndpoint());
	}
	
	/**
	 * Creates the reactivate endpoint.
	 */
	@Test
	void reactivateUserEndpoint() {
		assertEquals(VALUE_REACTIVATE_USER_ENDPOINT, bdbendpointImplObj.reactivateUserEndpoint());
	}

	/**
	 * Sso login ping id domain.
	 */
	@Test
	void ssoLoginPingIdDomain() {
		assertEquals(SSO_LOGIN_PING_ID_DOMAIN, bdbendpointImplObj.ssoLoginPingIdDomain());
	}

	/**
	 * Sso login ping id endpoint.
	 */
	@Test
	void ssoLoginPingIdEndpoint() {
		assertEquals(SSO_LOGIN_PING_ID_ENDPOINT, bdbendpointImplObj.ssoLoginPingIdEndpoint());
	}

	/**
	 * Sso login response type.
	 */
	@Test
	void ssoLoginResponseType() {
		assertEquals(SSO_LOGIN_RESPONSE_TYPE, bdbendpointImplObj.ssoLoginResponseType());
	}

	/**
	 * Sso login client id.
	 */
	@Test
	void ssoLoginClientId() {
		assertEquals(SSO_LOGIN_CLIENT_ID, bdbendpointImplObj.ssoLoginClientId());
	}

	/**
	 * Sso customer login service.
	 */
	@Test
	void ssoCustomerLoginService() {
		assertEquals(SSO_CUSTOMER_LOGIN_SERVICE, bdbendpointImplObj.ssoCustomerLoginService());
	}

	/**
	 * Sso login scope.
	 */
	@Test
	void ssoLoginScope() {
		assertEquals(SSO_LOGIN_SCOPE, bdbendpointImplObj.ssoLoginScope());
	}

	/**
	 * Gets the customer login endpoint.
	 *
	 * @return the customer login endpoint
	 */
	@Test
	void getCustomerLoginEndpoint() {
		assertEquals(CUSTOMER_LOGIN_ENDPOINT, bdbendpointImplObj.getCustomerLoginEndpoint());
	}

	/**
	 * Gets the creates the shopping list endpoint.
	 *
	 * @return the creates the shopping list endpoint
	 */
	@Test
	void getCreateShoppingListEndpoint() {
		assertEquals(CREATE_SHOPPING_LIST_ENDPONT, bdbendpointImplObj.getCreateShoppingListEndpoint());
	}
	
	/**
	 * Gets the creates the quote details endpoint.
	 *
	 * @return the creates the quote details endpoint
	 */
	@Test
	void getQuoteDetailsEndpoint() {
		assertEquals(CREATE_QUOTES_DETAILS_ENDPONT, bdbendpointImplObj.getGetQuoteDetailsEndpoint());
	}
	
	/**
	 * Gets the creates the quote history endpoint.
	 *
	 * @return the creates the quote history endpoint
	 */
	@Test
	void getQuotesHistoryEndpoint() {
		assertEquals(CREATE_QUOTES_HISTORY_ENDPONT, bdbendpointImplObj.getGetQuotesHistoryEndpoint());
	}

	/**
	 * Gets the gets the shopping list details endpoint.
	 *
	 * @return the gets the shopping list details endpoint
	 */
	@Test
	void getGetShoppingListDetailsEndpoint() {
		assertEquals(SHOPPING_LIST_DETAILS_ENDPOINT, bdbendpointImplObj.getGetShoppingListDetailsEndpoint());
	}

	/**
	 * Gets the gets the shopping list endpoint.
	 *
	 * @return the gets the shopping list endpoint
	 */
	@Test
	void getGetShoppingListEndpoint() {
		assertEquals(CREATE_SHOPPING_LIST_ENDPONT, bdbendpointImplObj.getGetShoppingListEndpoint());
	}

	/**
	 * Gets the file upload shopping list endpoint.
	 *
	 * @return the file upload shopping list endpoint
	 */
	@Test
	void getFileUploadShoppingListEndpoint() {
		assertEquals(FILE_UPLOAD_SHHOPPING_LIST_ENDPOINT, bdbendpointImplObj.getFileUploadShoppingListEndpoint());
	}

	/**
	 * Gets the removes the shopping list endpoint.
	 *
	 * @return the removes the shopping list endpoint
	 */
	@Test
	void getRemoveShoppingListEndpoint() {
		assertEquals(CREATE_SHOPPING_LIST_ENDPONT, bdbendpointImplObj.getRemoveShoppingListEndpoint());
	}

	/**
	 * Gets the share shopping list endpoint.
	 *
	 * @return the share shopping list endpoint
	 */
	@Test
	void getShareShoppingListEndpoint() {
		assertEquals(SHARE_SHOPPING_LIST_ENDPONT, bdbendpointImplObj.getShareShoppingListEndpoint());
	}
	
	/**
	 * Gets the export shopping list endpoint.
	 *
	 * @return the export shopping list endpoint
	 */
	@Test
	void getExportShoppingListEndpoint() {
		assertEquals(EXPORT_SHOPPING_LIST_ENDPONT, bdbendpointImplObj.getExportShoppingListEndpoint());
	}

	/**
	 * Gets the removes the shopping list entries endpoint.
	 *
	 * @return the removes the shopping list entries endpoint
	 */
	@Test
	void getRemoveShoppingListEntriesEndpoint() {
		assertEquals(REMOOVE_SHOPPING_LIST_ENTRIES_ENDPONT, bdbendpointImplObj.getRemoveShoppingListEntriesEndpoint());
	}

	/**
	 * Gets the update shopping list entries endpoint.
	 *
	 * @return the update shopping list entries endpoint
	 */
	@Test
	void getUpdateShoppingListEntriesEndpoint() {
		assertEquals(UPDATE_SHOPPING_LIST_ENTRIES_ENDPONT, bdbendpointImplObj.getUpdateShoppingListEntriesEndpoint());
	}

	/**
	 * Gets the file upload shopping list entries endpoint.
	 *
	 * @return the file upload shopping list entries endpoint
	 */
	@Test
	void getFileUploadShoppingListEntriesEndpoint() {
		assertEquals(FILE_UPLOAD_SHOPPING_LIST_ENTRIES_ENDPONT,
				bdbendpointImplObj.getFileUploadShoppingListEntriesEndpoint());
	}

	/**
	 * Gets the cart with identifier.
	 *
	 * @return the cart with identifier
	 */
	@Test
	void getCartWithIdentifier() {
		assertEquals(CART_WITH_IDENTIFIER, bdbendpointImplObj.getCartWithIdentifier());
	}

	/**
	 * Gets the delete entry from cart.
	 *
	 * @return the delete entry from cart
	 */
	@Test
	void getDeleteEntryFromCart() {
		assertEquals(DELETE_ENTRY_FROM_CART, bdbendpointImplObj.getDeleteEntryFromCart());
	}

	/**
	 * Gets the save for later.
	 *
	 * @return the save for later
	 */
	@Test
	void getSaveForLater() {
		assertEquals(SAVE_FOR_LATER, bdbendpointImplObj.getSaveForLater());
	}

	/**
	 * Gets the creates the save for later cart.
	 *
	 * @return the creates the save for later cart
	 */
	@Test
	void getCreateSaveForLaterCart() {
		assertEquals(CREATE_SAVE_FOR_LATER_CART, bdbendpointImplObj.getCreateSaveForLaterCart());
	}

	/**
	 * Gets the adds the to save for later.
	 *
	 * @return the adds the to save for later
	 */
	@Test
	void getAddToSaveForLater() {
		assertEquals(ADD_SAVE_FOR_LATER, bdbendpointImplObj.getAddToSaveForLater());
	}

	/**
	 * Gets the bulk remove from cart.
	 *
	 * @return the bulk remove from cart
	 */
	@Test
	void getBulkRemoveFromCart() {
		assertEquals(BULK_REMOVE_FROM_CART, bdbendpointImplObj.getBulkRemoveFromCart());
	}

	/**
	 * Gets the bulk add to save for later.
	 *
	 * @return the bulk add to save for later
	 */
	@Test
	void getBulkAddToSaveForLater() {
		assertEquals(BULK_ADD_TO_SAVE_FOR_LATTER, bdbendpointImplObj.getBulkAddToSaveForLater());
	}

	/**
	 * Gets the adds the to cart from save to later.
	 *
	 * @return the adds the to cart from save to later
	 */
	@Test
	void getAddToCartFromSaveToLater() {
		assertEquals(ADD_TO_CART_FROM_SAVE_TO_LATTER, bdbendpointImplObj.getAddToCartFromSaveToLater());
	}

	/**
	 * Gets the delete save for later.
	 *
	 * @return the delete save for later
	 */
	@Test
	void getDeleteSaveForLater() {
		assertEquals(DELETE_SAVE_TO_LATTER, bdbendpointImplObj.getDeleteSaveForLater());
	}

	/**
	 * Cart config endpoint.
	 */
	@Test
	void cartConfigEndpoint() {
		assertEquals(CART_CONFIG_ENDPOINT, bdbendpointImplObj.cartConfigEndpoint());
	}

	/**
	 * Country dropdown endpoint.
	 */
	@Test
	void countryDropdownEndpoint() {
		assertEquals(COUNTRY_DROP_DOWN_ENDPOINT, bdbendpointImplObj.countryDropdownEndpoint());
	}
	
	/**
	 * Language dropdown endpoint.
	 */
	@Test
	void languageDropdownEndpoint() {
		assertEquals(LANGUAGE_DROP_DOWN_ENDPOINT, bdbendpointImplObj.languageDropdownEndpoint());
	}

	/**
	 * Order history for grants endpoint.
	 */
	@Test
	void orderHistoryForGrantsEndpoint() {
		assertEquals(ORDER_HISTORY_FOR_GRANTS_ENDPOINT, bdbendpointImplObj.orderHistoryForGrantsEndpoint());
	}

	/**
	 * Gets the gets the payments endpoint.
	 *
	 * @return the gets the payments endpoint
	 */
	@Test
	void getGetPaymentsEndpoint() {
		assertEquals(PAYMENTS_ENDPOINT, bdbendpointImplObj.getGetPaymentsEndpoint());
	}

	/**
	 * Gets the adds the credit card endpoint.
	 *
	 * @return the adds the credit card endpoint
	 */
	@Test
	void getAddCreditCardEndpoint() {
		assertEquals(ADD_CREDIT_CART_ENDPOINT, bdbendpointImplObj.getAddCreditCardEndpoint());
	}

	/**
	 * Gets the removes the credit card endpoint.
	 *
	 * @return the removes the credit card endpoint
	 */
	@Test
	void getRemoveCreditCardEndpoint() {
		assertEquals(REMOVE_CREDIT_CART_ENDPOINT, bdbendpointImplObj.getRemoveCreditCardEndpoint());
	}

	/**
	 * Gets the update credit card endpoint.
	 *
	 * @return the update credit card endpoint
	 */
	@Test
	void getUpdateCreditCardEndpoint() {
		assertEquals(UPDATE_CREDIT_CART_ENDPOINT, bdbendpointImplObj.getUpdateCreditCardEndpoint());
	}

	/**
	 * Gets the gets the messages endpoint.
	 *
	 * @return the gets the messages endpoint
	 */
	@Test
	void getGetMessagesEndpoint() {
		assertEquals(MESSEGES_ENDPOINT, bdbendpointImplObj.getGetMessagesEndpoint());
	}

	/**
	 * Gets the read message endpoint.
	 *
	 * @return the read message endpoint
	 */
	@Test
	void getReadMessageEndpoint() {
		assertEquals(READ_MESSEGES_ENDPOINT, bdbendpointImplObj.getReadMessageEndpoint());
	}

	/**
	 * Gets the gets the quotes endpoint.
	 *
	 * @return the gets the quotes endpoint
	 */
	@Test
	void getGetQuotesEndpoint() {
		assertEquals(QUOTES_ENDPOINT, bdbendpointImplObj.getGetQuotesEndpoint());
	}

	/**
	 * Gets the gets the orders endpoint.
	 *
	 * @return the gets the orders endpoint
	 */
	@Test
	void getGetOrdersEndpoint() {
		assertEquals(ORDERS_ENDPOINT, bdbendpointImplObj.getGetOrdersEndpoint());
	}

	/**
	 * Mini cart count config endpoint.
	 */
	@Test
	void miniCartCountConfigEndpoint() {
		assertEquals(MINI_CART_COUNT_CONFIG_ENDPOINT, bdbendpointImplObj.miniCartCountConfigEndpoint());
	}

	/**
	 * Mini cart entries config endpoint.
	 */
	@Test
	void miniCartEntriesConfigEndpoint() {
		assertEquals(MINI_CART_ENTRIEES_CONFIG_ENDPOINT, bdbendpointImplObj.miniCartEntriesConfigEndpoint());
	}

	/**
	 * Update po number endpoint.
	 */
	@Test
	void updatePoNumberEndpoint() {
		assertEquals(UPDATE_PO_NUMBER_ENDPPOIINT, bdbendpointImplObj.updatePoNumberEndpoint());
	}

	/**
	 * Gets the promotion id details servlet path.
	 *
	 * @return the promotion id details servlet path
	 */
	@Test
	void getPromotionIdDetailsServletPath() {
		assertEquals(PROMOTION_ID_DETAILS_SERVLET_PATH, bdbendpointImplObj.getPromotionIdDetailsServletPath());
	}

	/**
	 * Gets the promotions msg endpoint.
	 *
	 * @return the promotions msg endpoint
	 */
	@Test
	void getPromotionsMsgEndpoint() {
		assertEquals(PROMOTIONS_MSG_PATH, bdbendpointImplObj.getPromotionsMsgEndpoint());
	}

	/**
	 * Aem upload tax certificate endpoint.
	 */
	@Test
	void aemUploadTaxCertificateEndpoint() {
		assertEquals(AEM_UPLOAD_TAX_CERTIFICATE_ENDPOINT, bdbendpointImplObj.aemUploadTaxCertificateEndpoint());
	}

	/**
	 * Gets the updates cart VAT exempt status.
	 *
	 * @return the updates cart VAT exempt status
	 */
	@Test
	void getUpdatesCartVATExemptStatus() {
		assertEquals(UPDATES_CART_VAT_EXEMPT_STATUS, bdbendpointImplObj.getUpdatesCartVATExemptStatus());
	}

	/**
	 * Gets the anti body details end point.
	 *
	 * @return the anti body details end point
	 */
	@Test
	void getAntiBodyDetailsEndPoint() {
		assertEquals(ANTI_BODY_DETAILS_ENDPOINT, bdbendpointImplObj.getAntiBodyDetailsEndPoint());
	}

	/**
	 * Gets the price data end point.
	 *
	 * @return the price data end point
	 */
	@Test
	void getPriceDataEndPoint() {
		assertEquals(PRICE_DATE_ENDPOINT, bdbendpointImplObj.getPriceDataEndPoint());
	}

	/**
	 * Gets the formats details end point.
	 *
	 * @return the formats details end point
	 */
	@Test
	void getFormatsDetailsEndPoint() {
		assertEquals(FORMATS_DETAILS_ENDPOINT, bdbendpointImplObj.getFormatsDetailsEndPoint());
	}

	/**
	 * Gets the user orders list endpoint.
	 *
	 * @return the user orders list endpoint
	 */
	@Test
	void getUserOrdersListEndpoint() {
		assertEquals(USER_ORDER_LIST_ENDPOINT, bdbendpointImplObj.getUserOrdersListEndpoint());
	}

	/**
	 * Gets the order details endpoint.
	 *
	 * @return the order details endpoint
	 */
	@Test
	void getOrderDetailsEndpoint() {
		assertEquals(ORDER_DETAILS_ENDPOINT, bdbendpointImplObj.getOrderDetailsEndpoint());
	}

	/**
	 * Gets the order packing list endpoint.
	 *
	 * @return the order packing list endpoint
	 */
	@Test
	void getOrderPackingListEndpoint() {
		assertEquals(ORDER_PACKING_LIST_ENDPOINT, bdbendpointImplObj.getOrderPackingListEndpoint());
	}

	/**
	 * Gets the hybris sign out endpoint.
	 *
	 * @return the hybris sign out endpoint
	 */
	@Test
	void getHybrisSignOutEndpoint() {
		assertEquals(HYBRIS_SIGN_OUT_ENDPOINT, bdbendpointImplObj.getHybrisSignOutEndpoint());
	}

	/**
	 * Dev sso sign out endpoint.
	 */
	@Test
	void devSsoSignOutEndpoint() {
		assertEquals(DEV_SSO_SIGN_OUT_ENDPOINT, bdbendpointImplObj.devSsoSignOutEndpoint());
	}

	/**
	 * Gets the sign out servlet path.
	 *
	 * @return the sign out servlet path
	 */
	@Test
	void getSignOutServletPath() {
		assertEquals(SIGN_OUT_SERVLET_PATH, bdbendpointImplObj.getSignOutServletPath());
	}

	/**
	 * Gets the search results servlet path.
	 *
	 * @return the search results servlet path
	 */
	@Test
	void getSearchResultsServletPath() {
		assertEquals(SEARCH_RESULTS_SERVLET_PATH, bdbendpointImplObj.getSearchResultsServletPath());
	}

	/**
	 * Gets the product announcements.
	 *
	 * @return the product announcements
	 */
	@Test
	void getProductAnnouncements() {
		assertEquals(PRODUCT_ANNOUNCEMENTS, bdbendpointImplObj.getProductAnnouncements());
	}

	/**
	 * Gets the adds the all items to cart endpoint.
	 *
	 * @return the adds the all items to cart endpoint
	 */
	@Test
	void getAddAllItemsToCartEndpoint() {
		assertEquals(ADD_ALL_ITEMS_TO_CART_ENDPOINT, bdbendpointImplObj.getAddAllItemsToCartEndpoint());
	}

	/**
	 * Gets the update VAT exempt status.
	 *
	 * @return the update VAT exempt status
	 */
	@Test
	void getUpdateVATExemptStatus() {
		assertEquals(UPDATES_VAT_EXEMPT_STATUS, bdbendpointImplObj.getUpdateVATExemptStatus());
	}

	/**
	 * Gets the validate my cart.
	 *
	 * @return the validate my cart
	 */
	@Test
	void getValidateMyCart() {
		assertEquals(VALIDATE_MY_CART, bdbendpointImplObj.getValidateMyCart());
	}

	/**
	 * Gets the ship to address config.
	 *
	 * @return the ship to address config
	 */
	@Test
	void getShipToAddressConfig() {
		assertEquals(SHIP_To_ADDRESS_CONFIG, bdbendpointImplObj.getShipToAddressConfig());
	}

	/**
	 * Gets the apply coupon.
	 *
	 * @return the apply coupon
	 */
	@Test
	void getApplyCoupon() {
		assertEquals(APPLY_COUPON, bdbendpointImplObj.getApplyCoupon());
	}

	/**
	 * Gets the address with no ruo endpoint.
	 *
	 * @return the address with no ruo endpoint
	 */
	@Test
	void getAddressWithNoRuoEndpoint() {
		assertEquals(ADDRESS_WITH_No_RUO_ENDPOINT, bdbendpointImplObj.getAddressWithNoRuoEndpoint());
	}

	/**
	 * Gets the search list endpoint.
	 *
	 * @return the search list endpoint
	 */
	@Test
	void getSearchListEndpoint() {
		assertEquals(SEARCH_LIST_ENDPOINT, bdbendpointImplObj.getSearchListEndpoint());
	}

	/**
	 * Gets the update address config.
	 *
	 * @return the update address config
	 */
	@Test
	void getUpdateAddressConfig() {
		assertEquals(UPDATE_ADDRESS_CONFIG, bdbendpointImplObj.getUpdateAddressConfig());
	}

	/**
	 * Gets the promo products list endpoint.
	 *
	 * @return the promo products list endpoint
	 */
	@Test
	void getPromoProductsListEndpoint() {
		assertEquals(PROMO_PRODUCTS_LIST_ENDPOINT, bdbendpointImplObj.getPromoProductsListEndpoint());
	}

	/**
	 * Gets the quote reference.
	 *
	 * @return the quote reference
	 */
	@Test
	void getQuoteReference() {
		assertEquals(QUOTE_REFERENCE, bdbendpointImplObj.getQuoteReference());
	}

	/**
	 * Notification endpoint.
	 */
	@Test
	void notificationEndpoint() {
		assertEquals(NOTIFICATIOON_ENDPOINT, bdbendpointImplObj.notificationEndpoint());
	}

	/**
	 * Gets the user idle time.
	 *
	 * @return the user idle time
	 */
	@Test
	void getUserIdleTime() {
		assertEquals(30, bdbendpointImplObj.getUserIdleTime());
	}

	/**
	 * Gets the minimum products.
	 *
	 * @return the minimum products
	 */
	@Test
	void getMinimumProducts() {
		assertEquals(10, bdbendpointImplObj.getMinimumProducts());
	}

	/**
	 * Gets the maximum products.
	 *
	 * @return the maximum products
	 */
	@Test
	void getMaximumProducts() {
		assertEquals(100, bdbendpointImplObj.getMaximumProducts());
	}

	/**
	 * Adds the new address end point.
	 */
	@Test
	void addNewAddressEndPoint() {
		assertEquals(ADD_NEW_ADDRESS_ENDPOINT, bdbendpointImplObj.addNewAddressEndPoint());
	}

	/**
	 * Gets the payment details.
	 *
	 * @return the payment details
	 */
	@Test
	void getPaymentDetails() {
		assertEquals(PAYMENT_DETAILS, bdbendpointImplObj.getPaymentDetails());
	}

	/**
	 * Gets the other details.
	 *
	 * @return the other details
	 */
	@Test
	void getOtherDetails() {
		assertEquals(OTHER_DETAILS, bdbendpointImplObj.getOtherDetails());
	}

	/**
	 * Gets the order confirmation checkout config.
	 *
	 * @return the order confirmation checkout config
	 */
	@Test
	void getOrderConfirmationCheckoutConfig() {
		assertEquals(ORDER_CONFIRMATION_CHECKOUT_CONFIG, bdbendpointImplObj.getOrderConfirmationCheckoutConfig());
	}

	/**
	 * Gets the product details endpoint.
	 *
	 * @return the product details endpoint
	 */
	@Test
	void getProductDetailsEndpoint() {
		assertEquals(PRODUCT_DETAAILS_ENDPOINT, bdbendpointImplObj.getProductDetailsEndpoint());
	}

	/**
	 * Gets the order confirmation cancel order config.
	 *
	 * @return the order confirmation cancel order config
	 */
	@Test
	void getOrderConfirmationCancelOrderConfig() {
		assertEquals(ORDER_CONFIRMATION_CANCEL_CONFIG, bdbendpointImplObj.getOrderConfirmationCancelOrderConfig());
	}

	/**
	 * Place order config.
	 */
	@Test
	void placeOrderConfig() {
		assertEquals(PLACE_ORDER_CONFIG, bdbendpointImplObj.placeOrderConfig());
	}

	/**
	 * Gets the post smart cart register endpoint.
	 *
	 * @return the post smart cart register endpoint
	 */
	@Test
	void getPostSmartCartRegisterEndpoint() {
		assertEquals(POST_SMART_CART_REGISTER_ENDPOINT, bdbendpointImplObj.getPostSmartCartRegisterEndpoint());
	}

	/**
	 * Gets the gets the distributors options endpoint.
	 *
	 * @return the gets the distributors options endpoint
	 */
	@Test
	void getGetDistributorsOptionsEndpoint() {
		assertEquals(DISTRIBUTER_OPTIONS_ENDPOIT, bdbendpointImplObj.getGetDistributorsOptionsEndpoint());
	}

	/**
	 * Gets the punch out endpoint.
	 *
	 * @return the punch out endpoint
	 */
	@Test
	void getPunchOutEndpoint() {
		assertEquals(PUNCH_OUT_ENDPOINT, bdbendpointImplObj.getPunchOutEndpoint());
	}

	/**
	 * Gets the punchout transmit request endpoint.
	 *
	 * @return the punchout transmit request endpoint
	 */
	@Test
	void getPunchoutTransmitRequestEndpoint() {
		assertEquals(PUNCH_OUT_TRANSMIT_REQUEST, bdbendpointImplObj.getPunchoutTransmitRequestEndpoint());
	}

	/**
	 * Gets the punchout cancel request endpoint.
	 *
	 * @return the punchout cancel request endpoint
	 */
	@Test
	void getPunchoutCancelRequestEndpoint() {
		assertEquals(PUNCH_OUT_CANCEL_REQUEST, bdbendpointImplObj.getPunchoutCancelRequestEndpoint());
	}

	/**
	 * Gets the quote config.
	 *
	 * @return the quote config
	 */
	@Test
	void getQuoteConfig() {
		assertEquals(QUOTE_CONFIG, bdbendpointImplObj.getQuoteConfig());
	}

	/**
	 * Gets the quote confirmation config.
	 *
	 * @return the quote confirmation config
	 */
	@Test
	void getQuoteConfirmationConfig() {
		assertEquals(QUOTE_CONFIRMATION_CONFIG, bdbendpointImplObj.getQuoteConfirmationConfig());
	}

	/**
	 * Send email.
	 */
	@Test
	void sendEmail() {
		assertEquals(SENT_EMAIL, bdbendpointImplObj.sendEmail());
	}

	/**
	 * Gets the language search dropdown endpoint.
	 *
	 * @return the language search dropdown endpoint
	 */
	@Test
	void getLanguageSearchDropdownEndpoint() {
		assertEquals(LANGUAG_DROPDOWN_ENDPOINT, bdbendpointImplObj.getLanguageSearchDropdownEndpoint());
	}

	/**
	 * Gets the gets the order inquiry detail endpoint.
	 *
	 * @return the gets the order inquiry detail endpoint
	 */
	@Test
	void getGetOrderInquiryDetailEndpoint() {
		assertEquals(ORDER_INQUIRY_DETAILS_ENDPOINT, bdbendpointImplObj.getGetOrderInquiryDetailEndpoint());
	}

	/**
	 * Gets the post order inquiry details endpoint.
	 *
	 * @return the post order inquiry details endpoint
	 */
	@Test
	void getPostOrderInquiryDetailsEndpoint() {
		assertEquals(POST_ORDER_INQUIRY_DETAILS_ENDPOINT, bdbendpointImplObj.getPostOrderInquiryDetailsEndpoint());
	}

	/**
	 * Gets the grants for customer endpoint.
	 *
	 * @return the grants for customer endpoint
	 */
	@Test
	void getGrantsForCustomerEndpoint() {
		assertEquals(VALUE_GET_GRANTS_FOR_CUSTOMER_ENDPOINT, bdbendpointImplObj.getGrantsForCustomerEndpoint());
	}

	/**
	 * Gets the quick add bulk entry endpoint.
	 *
	 * @return the quick add bulk entry endpoint
	 */
	@Test
	void getQuickAddBulkEntryEndpoint() {
		assertEquals(VALUE_GET_BULK_ENTRY_ENDPOINT, bdbendpointImplObj.getQuickAddBulkEntryEndpoint());
	}

	/**
	 * Gets the quick add bulk upload endpoint.
	 *
	 * @return the quick add bulk upload endpoint
	 */
	@Test
	void getQuickAddBulkUploadEndpoint() {
		assertEquals(VALUE_GET_BULK_UPLOAD_ENDPOINT, bdbendpointImplObj.getQuickAddBulkUploadEndpoint());
	}

	/**
	 * Gets the replace cart entry.
	 *
	 * @return the replace cart entry
	 */
	@Test
	void getReplaceCartEntry() {
		assertEquals(VALUE_REPLACE_CART_ENTRY_ENDPOINT, bdbendpointImplObj.getReplaceCartEntry());
	}

	/**
	 * Gets the replace save for later entry.
	 *
	 * @return the replace save for later entry
	 */
	@Test
	void getReplaceSaveForLaterEntry() {
		assertEquals(VALUE_REPLACE_SAVE_FOR_LATER_ENDPOINT, bdbendpointImplObj.getReplaceSaveForLaterEntry());
	}

	/**
	 * Apply grants on cart endpoint.
	 */
	@Test
	void applyGrantsOnCartEndpoint() {
		assertEquals(VALUE_APPLY_GRANTS_ON_CART_ENDPOINT, bdbendpointImplObj.applyGrantsOnCartEndpoint());
	}

	/**
	 * Gets the grants for customer endpoint.
	 *
	 * @return the grants for customer endpoint
	 */
	@Test
	void getUpdateCartQuantity() {
		assertEquals(VALUE_GET_UPDATE_CART_QUANTITY, bdbendpointImplObj.getUpdateCartQuantity());
	}

	/**
	 * Gets the grants for customer endpoint.
	 *
	 * @return the grants for customer endpoint
	 */
	@Test
	void getUpdateLotIndicator() {
		assertEquals(VALUE_GET_UPDATE_LOT_INDICATOR, bdbendpointImplObj.getUpdateLotIndicator());
	}

	/**
	 * Order details approver endpoint.
	 */
	@Test
	void orderDetailsApproverEndpoint() {
		assertEquals(VALUE_ORDER_DETAILS_APPROVER_ENDPOINT, bdbendpointImplObj.orderDetailsApproverEndpoint());
	}

	/**
	 * Order list approver endpoint.
	 */
	@Test
	void orderListApproverEndpoint() {
		assertEquals(VALUE_ORDER_LIST_APPROVER_ENDPOINT, bdbendpointImplObj.orderListApproverEndpoint());
	}

	/**
	 * Order approval decision endpoint.
	 */
	@Test
	void orderApprovalDecisionEndpoint() {
		assertEquals(VALUE_ORDER_APPROVAL_DECISION_ENDPOINT, bdbendpointImplObj.orderApprovalDecisionEndpoint());
	}

	/**
	 * Merge anonymous cart endpoint.
	 */
	@Test
	void mergeAnonymousCartEndpoint() {
		assertEquals(VALUE_MERGE_ANONYMOUS_CART_ENDPOINT, bdbendpointImplObj.mergeAnonymousCartEndpoint());
	}

	/**
	 * Trigger re order endpoint.
	 */
	@Test
	void triggerReOrderEndpoint() {
		assertEquals(TRIGGER_REORDER_ENDPOINT, bdbendpointImplObj.triggerReOrderEndpoint());
	}
	
	/**
	 * Test endpoints.
	 */
	@Test
	void testEndpoints() {
		assertEquals(REFRESH_TOKEN_ENDPOINT, bdbendpointImplObj.getRefreshTokenEndpoint());
		assertEquals(BEADLOTS_FILE_DOWNLOAD_ENDPOINT, bdbendpointImplObj.getBeadlotsFileDownloadEndpoint());
		assertEquals(BEADLOTS_FILE_DOWNLOAD_USERNAME, bdbendpointImplObj.getBeadlotsFileDownloadUsername());
		assertEquals(BEADLOTS_FILEDOWNLOAD_PWD, bdbendpointImplObj.getBeadlotsFileDownloadPwd());
		assertEquals(BEADLOTS_FILE_DOWNLOAD_SERVLET_PATH, bdbendpointImplObj.getBeadlotsFileDownloadServletPath());
		assertEquals(MY_ORDERS_LIST_ENDPOINT, bdbendpointImplObj.getMyOrdersListEndpoint());
		assertEquals(ALL_ORDERS_LIST_ENDPOINT, bdbendpointImplObj.getAllOrdersListEndpoint());
		assertEquals(CANCEL_ORDER_ENDPOINT, bdbendpointImplObj.cancelOrderEndpoint());
		assertEquals(REQUIRED_COMPANION_PRODUCTS_ENDPOINT, bdbendpointImplObj.getRequiredCompanionProductsConfig());
		assertEquals(PRICE_ENDPOINT, bdbendpointImplObj.getPriceConfig());
		assertEquals(PAYMETRIC_TOKEN_ENDPOINT, bdbendpointImplObj.paymetricTokenEndpoint());
		assertEquals(PAYMETRIC_DOMAIN, bdbendpointImplObj.paymetricDomain());
		assertEquals(PAYMETRIC_IFRAME_ENDPOINT, bdbendpointImplObj.paymetricIframeEndpoint());
		assertEquals(BRIGHTCOVE_ACCOUNT_ID, bdbendpointImplObj.brightcoveAccountId());
		assertEquals(BRIGHTCOVE_PLAYER_ID, bdbendpointImplObj.brightcovePlayerId());
	}
	
	
}