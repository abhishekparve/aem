package com.bdb.aem.core.services.impl;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
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
import com.bdb.aem.core.services.FetchBearerTokenService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The Class FetchBearerTokenServiceImpl.
 */
@Component(service = FetchBearerTokenService.class, immediate = true)
public class FetchBearerTokenServiceImpl implements FetchBearerTokenService {

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FetchBearerTokenServiceImpl.class);

	/**
	 * The bdb api endpoint service.
	 */
	@Reference
	private BDBApiEndpointService bdbApiEndpointService;

	/** The rest client. */
	@Reference
	private RestClient restClient;

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
		LOGGER.debug("Entry fetchAuthToken of FetchBearerTokenServiceImpl");
		JsonObject requestObject = new JsonObject();
		String accessToken = StringUtils.EMPTY;
		String endPoint = getAuthTokenApiEndpoint(request);
		if (StringUtils.isNotBlank(endPoint)) {
			final Map<String, String> requestHeaderMap = new HashMap<>();
			final BaseRequest baseRequest = new BaseRequestImpl(endPoint, HttpMethodType.POST,
					requestObject.toString());
			accessToken = getAccessToken(restClient.sendRequest(baseRequest, requestHeaderMap));
		}
		LOGGER.debug("Exit fetchAuthToken of FetchBearerTokenServiceImpl");
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
		LOGGER.debug("Entry getAccessToken of FetchBearerTokenServiceImpl");
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
		LOGGER.debug("Exit getAccessToken of FetchBearerTokenServiceImpl");
		return authString;
	}

	/**
	 * Gets the auth token api endpoint.
	 *
	 * @param request the request
	 * @return the auth token api endpoint
	 */
	@Override
	public String getAuthTokenApiEndpoint(SlingHttpServletRequest request) {
		LOGGER.debug("Entry getAuthTokenApiEndpoint of FetchBearerTokenServiceImpl");
		String endPoint = StringUtils.EMPTY;
		String siteId = CommonHelper.getSiteIdHeader(request);
		if(StringUtils.isBlank(siteId)) {
			siteId = CommonHelper.getSiteId(request);
		}
		LOGGER.debug("siteId in getAuthTokenApiEndpoint: {}", siteId);
		if (null != bdbApiEndpointService) {

			byte[] decodedBytes = Base64.getDecoder().decode(bdbApiEndpointService.getFetchAuthTokenEndpointClientSecret());
			String decodedClientSecret = new String(decodedBytes);

			StringBuilder stringBuilder = new StringBuilder(bdbApiEndpointService.getBDBHybrisDomain().trim());
			stringBuilder.append(bdbApiEndpointService.getFetchAuthTokenEndpoint()).append(CommonConstants.CLIENT_ID)
					.append(CommonConstants.EQUAL).append(bdbApiEndpointService.getFetchAuthTokenEndpointClientId())
					.append(CommonConstants.AND).append(CommonConstants.GRANT_TYPE).append(CommonConstants.EQUAL)
					.append(bdbApiEndpointService.getFetchAuthTokenEndpointGrantType()).append(CommonConstants.AND)
					.append(CommonConstants.CLIENT_SECRET).append(CommonConstants.EQUAL)
					.append(decodedClientSecret).append(CommonConstants.AND)
					.append(CommonConstants.SITE_ID).append(CommonConstants.EQUAL).append(siteId);
			endPoint = StringUtils.replace(stringBuilder.toString(), CommonConstants.HYBRIS_USER_ID_LITERAL,
					bdbApiEndpointService.getAnonymousUserIdPlaceholder());
		}
		LOGGER.debug("Exit getAuthTokenApiEndpoint of FetchBearerTokenServiceImpl");
		
		return endPoint;
	}
}
