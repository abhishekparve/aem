package com.bdb.aem.core.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.impl.BaseRequestImpl;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.bean.CustomerLoginResponseBean;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.exceptions.HybrisResponseException;
import com.bdb.aem.core.exceptions.InvalidRequestException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.FetchBearerTokenService;
import com.bdb.aem.core.services.SSOLoginService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The Class SSOLoginServiceImpl.
 */
@Component(service = SSOLoginService.class, immediate = true)
public class SSOLoginServiceImpl implements SSOLoginService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SSOLoginServiceImpl.class);

	/** The Constant BASIC_REST_PARAM_ERROR. */
	private static final String BASIC_REST_PARAM_ERROR = "Basic rest parameter are not valid";

	/** The bdb api endpoint service. */
	@Reference
	private BDBApiEndpointService bdbApiEndpointService;

	/** The fetch bearer token service. */
	@Reference
	private FetchBearerTokenService fetchBearerTokenService;
	
	@Reference
	private ExternalizerService externalizerService;

	/** The rest client. */
	@Reference
	private RestClient restClient;


	/**
	 * Fetch user details.
	 *
	 * @param request    the request
	 * @param response   the response
	 * @param queryParam the query param
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Override
	public String fetchUserDetails(SlingHttpServletRequest request, SlingHttpServletResponse response,
			Map<String, String> queryParam) throws AemInternalServerErrorException {
		LOGGER.debug("Entry fetchSignUpDetails of SSOLoginServiceImpl");
		String countryCodeFromCCV2 = null;
		try {

			String endPoint = createEndPointUrl(bdbApiEndpointService, request);
			String accessToken = fetchBearerTokenService.fetchAuthToken(request);
			JsonObject requestObject = createRequestBody(queryParam);
			LOGGER.debug("In SSOLoginServiceImpl Access Token : {}, End Point : {}, Request Object : {}", accessToken, endPoint,
					requestObject);
			if (StringUtils.isNotBlank(endPoint) && StringUtils.isNotBlank(accessToken)) {
				final Map<String, String> requestHeaderMap = new HashMap<>();
				requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
				requestHeaderMap.put(CommonConstants.AUTHORIZATION,
						new StringBuilder(CommonConstants.BEARER_WITH_SPACE).append(accessToken).toString());

				final BaseRequest baseRequest = new BaseRequestImpl(endPoint, HttpMethodType.POST,
						requestObject.toString());
				LOGGER.debug("requestHeaderMap :"+ requestHeaderMap);
				BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
				processResponse(response, baseResponse,true);
				LOGGER.debug("Successful Base Response");

				countryCodeFromCCV2 = processResponse(response, baseResponse,true);
			} else {
				throw new AemInternalServerErrorException(CommonConstants.AEM_SSO_ERROR_CODE,
						new InvalidRequestException(BASIC_REST_PARAM_ERROR));
			}
		} catch (HybrisResponseException e) {
			throw new AemInternalServerErrorException(e.getMessage(), e.getCause());
		} catch (AemInternalServerErrorException | UnsupportedEncodingException e) {
			throw new AemInternalServerErrorException(CommonConstants.AEM_SSO_ERROR_CODE, e);
		} 
		LOGGER.debug("Exit fetchSignUpDetails of SSOLoginServiceImpl");
		return countryCodeFromCCV2;
	}
	
	@Override
	public void refreshTokens(SlingHttpServletRequest request, SlingHttpServletResponse response,
			JsonObject jsonObject) throws AemInternalServerErrorException {
		LOGGER.debug("Entry fetchSignUpDetails of SSOLoginServiceImpl");
		try {
			
			String endPoint = createEndPointUrl(bdbApiEndpointService, request);
			String accessToken = fetchBearerTokenService.fetchAuthToken(request);
			LOGGER.debug("Access Token : {}, End Point : {}, Request Object : {}", accessToken, endPoint,
					jsonObject);
			if (StringUtils.isNotBlank(endPoint) && StringUtils.isNotBlank(accessToken)) {
				final Map<String, String> requestHeaderMap = new HashMap<>();
				requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
				requestHeaderMap.put(CommonConstants.AUTHORIZATION,
						new StringBuilder(CommonConstants.BEARER_WITH_SPACE).append(accessToken).toString());
				final BaseRequest baseRequest = new BaseRequestImpl(endPoint, HttpMethodType.POST,
						jsonObject.toString());
				BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
				processResponse(response, baseResponse, false);
			} else {
				throw new AemInternalServerErrorException(CommonConstants.AEM_SSO_ERROR_CODE,
						new InvalidRequestException(BASIC_REST_PARAM_ERROR));
			}
		} catch (HybrisResponseException e) {
			throw new AemInternalServerErrorException(e.getMessage(), e.getCause());
		} catch (AemInternalServerErrorException | UnsupportedEncodingException e) {
			throw new AemInternalServerErrorException(CommonConstants.AEM_SSO_ERROR_CODE, e);
		} 
		LOGGER.debug("Exit fetchSignUpDetails of SSOLoginServiceImpl");
	}

	/**
	 * Process response.
	 *
	 * @param response     the response
	 * @param baseResponse the base response
	 * @throws HybrisResponseException      the hybris response exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	private String processResponse(SlingHttpServletResponse response, BaseResponse baseResponse, boolean setUserCookies)
			throws HybrisResponseException, UnsupportedEncodingException {
		LOGGER.debug("Entry processResponse of SSOLoginServiceImpl");
		String responseString;
		String newCountryCode = null;
		Gson gson = CommonHelper.getGsonInstance();
		if (!baseResponse.hasError()) {
			responseString = baseResponse.getResponseData().getData();
			LOGGER.debug("Response in processResponse of SSOLoginServiceImpl : " + responseString);
			LOGGER.info("Login response from CCV2:" + responseString);
			LOGGER.error("Login response from CCV2:" + responseString);
			CustomerLoginResponseBean customerLoginResponseBean = gson.fromJson(responseString,
					CustomerLoginResponseBean.class);
			LOGGER.debug("UID from the customerLoginResponseBean object:"+ customerLoginResponseBean.getUid());
			LOGGER.info("UID from the customerLoginResponseBean object:"+ customerLoginResponseBean.getUid());
			LOGGER.error("UID from the customerLoginResponseBean object:"+ customerLoginResponseBean.getUid());
			newCountryCode = setSuccessResponseCookies(response, customerLoginResponseBean, setUserCookies);
			newCountryCode = customerLoginResponseBean.getCountry();
			LOGGER.debug("newCountryCode in processResponse method:"+newCountryCode);
		} else {
			LOGGER.debug("HybrisResponseException12345");
			throw new HybrisResponseException(getErrorCode(baseResponse.getError().getMessage()),
					new InvalidRequestException(baseResponse.getError().getMessage()));
		}
		LOGGER.debug("Exit processResponse of SSOLoginServiceImpl");
		return newCountryCode;
	}

	/**
	 * Gets the error code.
	 *
	 * @param errorResponseMessage the error response message
	 * @return the error code
	 */
	private String getErrorCode(String errorResponseMessage) {
		LOGGER.debug("Entry getErrorCode of SSOLoginServiceImpl");
		String errorCode = CommonConstants.AEM_SSO_ERROR_CODE;
		JsonArray jsonObjectArrayFromJson = CommonHelper.getJsonObjectArrayFromJson(errorResponseMessage, CommonConstants.ERRORS);
		JsonObject jsonObjectFromJsonArray = CommonHelper.getJsonObjectFromJsonArray(jsonObjectArrayFromJson);
		String responseErrorCode = CommonHelper.getValueFromJson(jsonObjectFromJsonArray,
				CommonConstants.ERROR_CODE_TEXT);
		if (StringUtils.isNotBlank(responseErrorCode)) {
			errorCode = responseErrorCode;
		}
		LOGGER.debug("Exit getErrorCode of SSOLoginServiceImpl");
		return errorCode;
	}

	/**
	 * Creates the request body.
	 *
	 * @param queryParam the query param
	 * @return the json object
	 */
	private JsonObject createRequestBody(Map<String, String> queryParam) {
		LOGGER.debug("Entry createRequestBody of SSOLoginServiceImpl");
		JsonObject jsonObject = new JsonObject();
		String authorizationCode = CommonHelper.getValueFromMap(queryParam, CommonConstants.CODE);
		if (StringUtils.isNotBlank(authorizationCode)) {
			jsonObject.addProperty(CommonConstants.AUTHORIZATION_CODE, authorizationCode);
			LOGGER.debug("jsonObject value : " + jsonObject);
		}
		LOGGER.debug("Exit createRequestBody of SSOLoginServiceImpl");
		return jsonObject;
	}

	/**
	 * Sets the success response cookies.
	 *
	 * @param response                  the response
	 * @param customerLoginResponseBean the customer login response bean
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws HybrisResponseException the hybris response exception
	 */
	private String setSuccessResponseCookies(SlingHttpServletResponse response,
			CustomerLoginResponseBean customerLoginResponseBean, boolean setUserCookies) throws UnsupportedEncodingException, HybrisResponseException {
		LOGGER.debug("Entry setSuccessResponseCookies of SSOLoginServiceImpl");
		String countryCode = null;

		if (null != bdbApiEndpointService) {
			if(setUserCookies) {
				LOGGER.debug("Before set userDetails cookie");
				countryCode = setUserDetailsCookie(response, customerLoginResponseBean, CommonConstants.BOOLEAN_TRUE);
				LOGGER.debug("After set userDetails cookie");
			}
			setSsoTokenCookie(response, customerLoginResponseBean, -1, CommonConstants.BOOLEAN_TRUE);
			setAuthTokenCookie(response, customerLoginResponseBean, -1, CommonConstants.BOOLEAN_TRUE);
			setExpiryTimeCookie(response, customerLoginResponseBean, -1, CommonConstants.BOOLEAN_TRUE);
			removeFailureCookies(response, CommonConstants.BOOLEAN_TRUE);
		}
		LOGGER.debug("Exit setSuccessResponseCookies of SSOLoginServiceImpl");
		return countryCode;
	}
	
	/**
	 * String to integer.
	 *
	 * @param stringToConvert the string to convert
	 * @return the int
	 * @throws HybrisResponseException the hybris response exception
	 */
	public static int stringToInteger(String stringToConvert) throws HybrisResponseException {
		int number = 0;
		if (StringUtils.isNotBlank(stringToConvert)) {
			try {
				number = Integer.parseInt(stringToConvert);
			} catch (NumberFormatException e) {
				throw new HybrisResponseException(CommonConstants.AEM_SSO_ERROR_CODE, e);
			}

		}
		return number;
	}

	/**
	 * Sets the user details cookie.
	 *
	 * @param response                  the response
	 * @param customerLoginResponseBean the customer login response bean
	 * @param isSecure                  the is secure
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	private String setUserDetailsCookie(SlingHttpServletResponse response,
			CustomerLoginResponseBean customerLoginResponseBean, boolean isSecure)
			throws UnsupportedEncodingException {
		LOGGER.debug("Entry setUserDetailsCookie of SSOLoginServiceImpl");
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_FIRSTNAME_COOKIE,
				customerLoginResponseBean.getFirstName(), -1, isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_LAST_NAME_COOKIE,
				customerLoginResponseBean.getLastName(), -1, isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_UID_COOKIE, customerLoginResponseBean.getUid(), -1,
				isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_UNREAD_MESSAGES_COUNT_COOKIE, customerLoginResponseBean.getUnreadMessagesCount(), -1,
				isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_ACC_TYPE_COOKIE, customerLoginResponseBean.getAccountType(), -1,
				isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_ACC_ROLE_COOKIE, customerLoginResponseBean.getAccountRole(), -1,
				isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_GRANT_ENABLED_COOKIE, customerLoginResponseBean.getGrantEnabled(), -1,
				isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_IS_SMART_CART_USER, customerLoginResponseBean.getIsSmartCartUser(), -1,
				isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_PENDING_ORDER_COUNT_COOKIE, customerLoginResponseBean.getPendingOrderCount(), -1,
				isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_IS_REWARDS_ENABLED, customerLoginResponseBean.getRewardsEnabled(), -1, isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_IS_DISTRIBUTOR, customerLoginResponseBean.getDistributor(), -1, isSecure);
		CommonHelper.setCookie(response, CommonConstants.AREA_OF_INTEREST, customerLoginResponseBean.getAreaOfInterest(), -1,
				isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_COMPANY, customerLoginResponseBean.getCompany(), -1, isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_COUNTRY, customerLoginResponseBean.getCountry(), -1, isSecure);
		LOGGER.debug("Country cookie in Service Impl: "+ customerLoginResponseBean.getCountry());
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_INDUSTRY, customerLoginResponseBean.getUserIndustry(), -1, isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_LEAD, customerLoginResponseBean.getLeadId(), -1, isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_ROLE, customerLoginResponseBean.getUserRole(), -1, isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_ACTIVE, customerLoginResponseBean.getActive(), -1, isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_IS_CREDIT_CARD_ENABLED, customerLoginResponseBean.isCreditCardEnabledForSite(), -1,
				isSecure);
		CommonHelper.setCookie(response, CommonConstants.USER_DETAILS_COMPANY, customerLoginResponseBean.getCompany(), -1, isSecure);
		LOGGER.debug("Exit setUserDetailsCookie of SSOLoginServiceImpl");
		return customerLoginResponseBean.getCountry();
	}

	/**
	 * Sets the sso token cookie.
	 *
	 * @param response                  the response
	 * @param customerLoginResponseBean the customer login response bean
	 * @param cookieExpiryTime          the cookie expiry time
	 * @param isSecure                  the is secure
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	private void setSsoTokenCookie(SlingHttpServletResponse response,
			CustomerLoginResponseBean customerLoginResponseBean, int cookieExpiryTime, boolean isSecure)
			throws UnsupportedEncodingException {
		LOGGER.debug("Entry setSsoTokenCookie of SSOLoginServiceImpl");
		CommonHelper.setCookie(response, CommonConstants.SSO_TOKEN_COOKIE, customerLoginResponseBean.getSsoToken(),
				cookieExpiryTime, isSecure);
		CommonHelper.setCookie(response, CommonConstants.SSO_TOKEN_REFRESH_COOKIE, customerLoginResponseBean.getSsoRefreshToken(), -1,
				isSecure);
		LOGGER.debug("Exit setSsoTokenCookie of SSOLoginServiceImpl");
	}
	
	/**
	 * Sets the expiry time cookie.
	 *
	 * @param response the response
	 * @param customerLoginResponseBean the customer login response bean
	 * @param cookieExpiryTime the cookie expiry time
	 * @param isSecure the is secure
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	private void setExpiryTimeCookie(SlingHttpServletResponse response,
			CustomerLoginResponseBean customerLoginResponseBean, int cookieExpiryTime, boolean isSecure)
					throws UnsupportedEncodingException {
		LOGGER.debug("Entry setExpiryTimeCookie of SSOLoginServiceImpl");
		CommonHelper.setCookie(response, CommonConstants.TOKEN_EXPIRY_TIME_COOKIE, customerLoginResponseBean.getExpiresIn(),
				cookieExpiryTime, isSecure);
		LOGGER.debug("Exit setExpiryTimeCookie of SSOLoginServiceImpl");
	}

	/**
	 * Sets the auth token cookie.
	 *
	 * @param response                  the response
	 * @param customerLoginResponseBean the customer login response bean
	 * @param cookieExpiryTime          the cookie expiry time
	 * @param isSecure                  the is secure
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	private void setAuthTokenCookie(SlingHttpServletResponse response,
			CustomerLoginResponseBean customerLoginResponseBean, int cookieExpiryTime, boolean isSecure)
			throws UnsupportedEncodingException {
		LOGGER.debug("Entry setAuthTokenCookie of SSOLoginServiceImpl");
		CommonHelper.setCookie(response, CommonConstants.AUTH_TOKEN_COOKIE, customerLoginResponseBean.getHybrisToken(),
				cookieExpiryTime, isSecure);
		CommonHelper.setCookie(response, CommonConstants.AUTH_TOKEN_REFRESH_COOKIE, customerLoginResponseBean.getHybrisRefreshToken(), -1,
				isSecure);
		LOGGER.debug("Exit setAuthTokenCookie of SSOLoginServiceImpl");
	}

	/**
	 * Sets the failure response cookies.
	 *
	 * @param response  the response
	 * @param errorCode the error code
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@Override
	public void setFailureResponseCookies(SlingHttpServletResponse response, String errorCode)
			throws UnsupportedEncodingException {
		LOGGER.debug("Entry setFailureResponseCookies of SSOLoginServiceImpl");
		if (null != bdbApiEndpointService) {
			CommonHelper.setCookie(response, CommonConstants.LOGIN_ERROR_COOKIE, errorCode,
					bdbApiEndpointService.getCookieExpiryTime(), CommonConstants.BOOLEAN_TRUE);
			CommonHelper.removeSuccessCookies(response, CommonConstants.BOOLEAN_TRUE);
		}
		LOGGER.debug("Exit setFailureResponseCookies of SSOLoginServiceImpl");
	}

	/**
	 * Removes the failure cookies.
	 *
	 * @param response the response
	 * @param isSecure the is secure
	 */
	@Override
	public void removeFailureCookies(SlingHttpServletResponse response, Boolean isSecure) {
		LOGGER.debug("Entry removeFailureCookies of SSOLoginServiceImpl");
		CommonHelper.removeCookie(response, CommonConstants.LOGIN_ERROR_COOKIE, isSecure);
		LOGGER.debug("Exit removeFailureCookies of SSOLoginServiceImpl");
	}

	/**
	 * Checks if is required query param available.
	 *
	 * @param queryParams the query params
	 * @return true, if is required query param available
	 */
	@Override
	public boolean isRequiredQueryParamAvailable(Map<String, String> queryParams) {
		LOGGER.debug("Entry isRequiredQueryParamAvailable of SSOLoginServiceImpl");
		String code = CommonHelper.getValueFromMap(queryParams, CommonConstants.CODE);
		String state = CommonHelper.getValueFromMap(queryParams, CommonConstants.STATE);
		String url = CommonHelper.getValueFromMap(queryParams, CommonConstants.URL);
		LOGGER.debug("In isRequiredQueryParamAvailable code : {}, state : {}, url : {}", code, state, url);
		LOGGER.debug("Exit isRequiredQueryParamAvailable of SSOLoginServiceImpl");
		return (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(state) && StringUtils.isNotBlank(url));
	}
	
	
	/**
	 * Creates the end point url.
	 *
	 * @param bdbApiEndpointService the bdb api endpoint service
	 * @param request the request
	 * @return the string
	 */
	public String createEndPointUrl(BDBApiEndpointService bdbApiEndpointService, SlingHttpServletRequest request) {
		LOGGER.debug("Entry createEndPointUrl of SSOLoginServiceImpl");
		String endpoint = StringUtils.EMPTY;
		String siteId = CommonHelper.getSiteId(request);

		Map<String, String> queryParams = CommonHelper.getQueryParamsFromRequest(request);
		String newCountry = CommonHelper.getValueFromMap(queryParams, CommonConstants.COUNTRY);

		if(StringUtils.isBlank(siteId)) {
			siteId = CommonHelper.getSiteIdHeader(request);
			LOGGER.debug("siteId from getSiteId:"+siteId);
		}
		LOGGER.debug("Site id in createEndPointUrl: {}", siteId);
		if(!siteId.contains(newCountry)) {
			siteId = siteId.substring(0, siteId.length() - 2)+newCountry;
			LOGGER.debug("New Site id : {}", siteId);
		}
		if (null != bdbApiEndpointService) {
			StringBuilder endPointUrl = new StringBuilder();
			endPointUrl.append(bdbApiEndpointService.getBDBHybrisDomain().trim()).append(bdbApiEndpointService.getCustomerLoginEndpoint().trim());
			endpoint = StringUtils.replace(endPointUrl.toString(), CommonConstants.HYBRIS_SITE_LITERAL, siteId);
			endpoint = StringUtils.replace(endpoint, CommonConstants.HYBRIS_USER_ID_LITERAL, bdbApiEndpointService.getAnonymousUserIdPlaceholder());
			LOGGER.debug("Final end point url : "+endpoint);
		} else {
			LOGGER.error("Could not get BdbEndpointService reference in SSOLoginServiceImpl");
		}
		LOGGER.debug("Exit createEndPointUrl of SSOLoginServiceImpl");
		return endpoint;
	}
	
	/**
	 * Checks if is redirect url valid.
	 *
	 * @param resourceResolver the resource resolver
	 * @param queryParams the query params
	 * @return true, if is redirect url valid
	 */
	@Override
	public String getRedirectUrl(ResourceResolver resourceResolver, Map<String, String> queryParams) {
		String currentDomain = externalizerService.externalizedUrl("/", resourceResolver);
		LOGGER.debug("current domain from externalizer : {}", currentDomain);
		String redirectUrl = CommonHelper.getValueFromMap(queryParams, CommonConstants.URL);
		LOGGER.debug("redirect url in map : {}", redirectUrl);
		return StringUtils.startsWithIgnoreCase(redirectUrl, currentDomain) ? redirectUrl : currentDomain;
	}
	
	/**
	 * Checks if is valid url.
	 *
	 * @param redirectUrl the redirect url
	 * @param queryParams the query params
	 * @return true, if is valid url
	 */
	@Override
	public boolean isValidUrl(String redirectUrl, Map<String, String> queryParams) {
		return StringUtils.equals(redirectUrl, CommonHelper.getValueFromMap(queryParams, CommonConstants.URL));
	}

	@Override
	public String getRegionRedirectUrl(String redirectUrl, String countryCode) {
		String updatedRedirectUrl = null;
		if(StringUtils.isNotEmpty(redirectUrl) && StringUtils.isNotEmpty(countryCode)) {
			String[] stringArray = redirectUrl.split("bdbiosciences.com/");
			String domainUrl = stringArray[0]+"bdbiosciences.com/";
			if(countryCode.equalsIgnoreCase("jp")) {
				updatedRedirectUrl = domainUrl+"ja-"+countryCode.toLowerCase();
			} else if(countryCode.equalsIgnoreCase("kr")) {
				updatedRedirectUrl = domainUrl+"ko-"+countryCode.toLowerCase();
			} else if(countryCode.equalsIgnoreCase("cn")) {
				updatedRedirectUrl = domainUrl+"zh-"+countryCode.toLowerCase();
			} else {
				updatedRedirectUrl = domainUrl+"en-"+countryCode.toLowerCase();
			}
		}
		return updatedRedirectUrl;
	}
}
