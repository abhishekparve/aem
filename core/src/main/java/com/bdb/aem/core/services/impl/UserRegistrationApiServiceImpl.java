package com.bdb.aem.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.impl.BaseRequestImpl;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.UserRegistrationApiService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The Class UserRegistrationApiServiceImpl.
 */
@Component(service = UserRegistrationApiService.class, immediate = true)
public class UserRegistrationApiServiceImpl implements UserRegistrationApiService {

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationApiServiceImpl.class);

	/**
	 * The bdb api endpoint service.
	 */
	@Reference
	private BDBApiEndpointService bdbApiEndpointService;

	/** The rest client. */
	@Reference
	private RestClient restClient;

	/**
	 * Fetch sign up details.
	 *
	 * @param request       the request
	 * @param requestObject the request object
	 * @param response      the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Override
	public String fetchSignUpDetails(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response) throws AemInternalServerErrorException {
		LOGGER.debug("Entry fetchSignUpDetails of UserRegistrationApiService");
		return commonEndPointCall(request, requestObject, CommonConstants.SIGN_UP_CALL, response);
	}

	/**
	 * Reset password.
	 *
	 * @param request       the request
	 * @param requestObject the request object
	 * @param response      the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Override
	public String resetPassword(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response) throws AemInternalServerErrorException {
		LOGGER.debug("Entry resetPassword of UserRegistrationApiService");
		return commonEndPointCall(request, requestObject, CommonConstants.RESET_PASS, response);
	}

	/**
	 * Area of focus.
	 *
	 * @param request       the request
	 * @param requestObject the request object
	 * @param response      the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Override
	public String areaOfFocus(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response) throws AemInternalServerErrorException {
		LOGGER.debug("Entry areaOfFocus of UserRegistrationApiService");
		return commonEndPointCall(request, requestObject, CommonConstants.AREA_OF_FOCUS, response);
	}

	/**
	 * Purchase account.
	 *
	 * @param request       the request
	 * @param requestObject the request object
	 * @param response      the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Override
	public String purchaseAccount(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response) throws AemInternalServerErrorException {
		LOGGER.debug("Entry purchaseAccount of UserRegistrationApiService");
		return commonEndPointCall(request, requestObject, CommonConstants.PURCHASE_ACCOUNT, response);
	}

	/**
	 * Purchase account.
	 *
	 * @param request       the request
	 * @param requestObject the request object
	 * @param apiType       the api type
	 * @param response      the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Override
	public String commonEndPointCall(SlingHttpServletRequest request, JsonObject requestObject, String apiType,
			SlingHttpServletResponse response) throws AemInternalServerErrorException {
		LOGGER.debug("Entry commonEndPointCall of UserRegistrationApiService");
		String responseString;
		String accessToken = fetchAuthToken(request);
		String endPoint = createEndPointUrl(apiType, request);
		if (StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(endPoint)) {
			final Map<String, String> requestHeaderMap = new HashMap<>();
			requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
			requestHeaderMap.put(CommonConstants.AUTHORIZATION,
					new StringBuilder(CommonConstants.BEARER_WITH_SPACE).append(accessToken).toString());
			LOGGER.debug("End Point : {}", endPoint);
			final BaseRequest baseRequest = new BaseRequestImpl(endPoint, HttpMethodType.POST,
					requestObject.toString());
			BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
			if (!baseResponse.hasError()) {
				response.setStatus(baseResponse.getResponseData().getStatusCode());
				responseString = baseResponse.getResponseData().getData();
			} else {
				response.setStatus(baseResponse.getError().getErrorCode());
				responseString = baseResponse.getError().getMessage();
			}
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			responseString = CommonConstants.ACCESS_TOKEN_RETRIEVAL_ERROR;
			LOGGER.error("Access token : {}, End point {}", accessToken, endPoint);
		}
		LOGGER.debug("Exit commonEndPointCall of UserRegistrationApiService");
		return responseString;
	}

	/**
	 * Fetch auth token.
	 *
	 * @param request the request
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Override
	public String fetchAuthToken(SlingHttpServletRequest request) throws AemInternalServerErrorException {
		LOGGER.debug("Entry fetchAuthToken of UserRegistrationApiService");
		JsonObject requestObject = new JsonObject();
		String accessToken = StringUtils.EMPTY;
		String endPoint = getAuthTokenApiEndpoint(request);
		if (StringUtils.isNotBlank(endPoint)) {
			final Map<String, String> requestHeaderMap = new HashMap<>();
			final BaseRequest baseRequest = new BaseRequestImpl(endPoint, HttpMethodType.POST,
					requestObject.toString());
			accessToken = getAccessToken(restClient.sendRequest(baseRequest, requestHeaderMap));
		}
		LOGGER.debug("Exit fetchAuthToken of UserRegistrationApiService");
		return accessToken;
	}

	/**
	 * Gets the access token.
	 *
	 * @param baseResponse the base response
	 * @return the access token
	 */
	@Override
	public String getAccessToken(BaseResponse baseResponse) {
		LOGGER.debug("Entry getAccessToken of UserRegistrationApiService");
		String authString = StringUtils.EMPTY;
		if (!baseResponse.hasError()) {
			String jsonResponse = baseResponse.getResponseData().getData();
			JsonObject convertedObject = new Gson().fromJson(jsonResponse, JsonObject.class);
			if (null != convertedObject) {
				JsonElement element = convertedObject.get(CommonConstants.ACCESS_TOKEN);
				if (null != element) {
					authString = element.toString().trim().replace(CommonConstants.DOUBLE_QOUTES, "");
				}
			}
		}
		LOGGER.debug("Exit getAccessToken of UserRegistrationApiService");
		return authString;
	}

	/**
	 * Creates the end point url.
	 *
	 * @param serviceName the service name
	 * @param request     the request
	 * @return the string
	 */
	@Override
	public String createEndPointUrl(String serviceName, SlingHttpServletRequest request) {
		LOGGER.debug("Entry createEndPointUrl of UserRegistrationApiService");
		String endpoint = StringUtils.EMPTY;
		String siteId = CommonHelper.getSiteIdHeader(request);
		LOGGER.debug("Site id : {}",siteId);
		StringBuilder endPointUrl = new StringBuilder();
		if (null != bdbApiEndpointService) {
			endPointUrl.append(bdbApiEndpointService.getBDBHybrisDomain().trim());
			switch (serviceName) {
			case CommonConstants.SIGN_UP_CALL:
				endPointUrl.append(bdbApiEndpointService.getHybrisSignUpEndpoint().trim());
				break;
			case CommonConstants.AREA_OF_FOCUS:
				endPointUrl.append(bdbApiEndpointService.getHybrisSignUpPreferenceEndpoint().trim());
				break;
			case CommonConstants.RESET_PASS:
				endPointUrl.append(bdbApiEndpointService.getHybrisResetPasswordEndpoint().trim());
				break;
			case CommonConstants.PURCHASE_ACCOUNT:
				endPointUrl.append(bdbApiEndpointService.getPurchasingAccountRegistrationEndpoint().trim());
				break;
			default:
				return StringUtils.EMPTY;
			}
			endpoint = StringUtils.replace(endPointUrl.toString(), CommonConstants.HYBRIS_USER_ID_LITERAL,
					bdbApiEndpointService.getAnonymousUserIdPlaceholder());
			endpoint = StringUtils.replace(endpoint, CommonConstants.HYBRIS_SITE_LITERAL, siteId);
		} else {
			LOGGER.error("Could not get BdbEndpointService reference in UserRegistrationApiService");
		}
		LOGGER.debug("Exit createEndPointUrl of BaseApiServiceImpl");
		return endpoint;
	}

	/**
	 * Gets the auth token api endpoint.
	 *
	 * @param request the request
	 * @return the auth token api endpoint
	 */
	@Override
	public String getAuthTokenApiEndpoint(SlingHttpServletRequest request) {
		LOGGER.debug("Entry getAuthTokenApiEndpoint of UserRegistrationApiService");
		String endPoint = StringUtils.EMPTY;
		String siteId = CommonHelper.getSiteIdHeader(request);
		if (null != bdbApiEndpointService) {
			StringBuilder stringBuilder = new StringBuilder(bdbApiEndpointService.getBDBHybrisDomain().trim());
			stringBuilder.append(bdbApiEndpointService.getFetchAuthTokenEndpoint()).append(CommonConstants.CLIENT_ID)
					.append(CommonConstants.EQUAL).append(bdbApiEndpointService.getFetchAuthTokenEndpointClientId())
					.append(CommonConstants.AND).append(CommonConstants.GRANT_TYPE).append(CommonConstants.EQUAL)
					.append(bdbApiEndpointService.getFetchAuthTokenEndpointGrantType()).append(CommonConstants.AND)
					.append(CommonConstants.CLIENT_SECRET).append(CommonConstants.EQUAL)
					.append(bdbApiEndpointService.getFetchAuthTokenEndpointClientSecret()).append(CommonConstants.AND)
					.append(CommonConstants.SITE_ID).append(CommonConstants.EQUAL).append(siteId);
			endPoint = StringUtils.replace(stringBuilder.toString(), CommonConstants.HYBRIS_USER_ID_LITERAL,
					bdbApiEndpointService.getAnonymousUserIdPlaceholder());
		}
		LOGGER.debug("Exit getAuthTokenApiEndpoint of UserRegistrationApiService");
		return endPoint;
	}
}
