package com.bdb.aem.core.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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
import com.bdb.aem.core.services.SignOutService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.JsonObject;


/**
 * The Class SignOutServiceImpl.
 */
@Component(service = SignOutService.class, immediate = true)
public class SignOutServiceImpl implements SignOutService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SignOutServiceImpl.class);
	
	/** The bdb api endpoint service. */
	@Reference
	private BDBApiEndpointService bdbApiEndpointService;

	/** The rest client. */
	@Reference
	private RestClient restClient;

	/**
	 * Fetch sign out details.
	 *
	 * @param request the request
	 * @param requestObject the request object
	 * @param response the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@Override
	public Boolean fetchSignOutDetails(SlingHttpServletRequest request, JsonObject requestObject,
			SlingHttpServletResponse response) throws AemInternalServerErrorException {
		LOGGER.debug("Entry fetchSignOutDetails of SignOutService");
		Boolean flag = true;
		String accessToken = CommonHelper.getSpecificCookie(request, CommonConstants.AUTH_TOKEN_COOKIE);
		LOGGER.debug("Access Token (cookie): {}", accessToken);
		String endPoint = createEndPointUrl(request);
		LOGGER.debug("End Point: {}", endPoint);
		
		if (StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(endPoint)) {
			final Map<String, String> requestHeaderMap = new HashMap<>();
			requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
			requestHeaderMap.put(CommonConstants.AUTHORIZATION,	new StringBuilder(CommonConstants.BEARER_WITH_SPACE)
					.append(accessToken).toString());
			
			final BaseRequest baseRequest = new BaseRequestImpl(endPoint, HttpMethodType.POST,
					requestObject.toString());
			BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
			
			if (!baseResponse.hasError()) {
				LOGGER.debug("SUCCESS Hybris redirect - {}", baseResponse.getResponseData().getStatusCode());
			} else {
				response.setStatus(baseResponse.getError().getErrorCode());
				String responseString = baseResponse.getError().getMessage();
				LOGGER.debug("ERROR Hybris redirect - {}", responseString);
			}

		}
		else {
			LOGGER.debug("Not found - Access token : {}, End point {}", accessToken, endPoint);
			flag = false;
		}
		
		LOGGER.debug("Exit fetchSignOutDetails of SignOutService");
		return flag;
	}
	
	
	/**
	 * Creates the end point url.
	 *
	 * @param request the request
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@Override
	public String createEndPointUrl(SlingHttpServletRequest request){
		LOGGER.debug("Entry createEndPointUrl of SignOutService");
		String endpoint = StringUtils.EMPTY;
		StringBuilder endPointUrl = new StringBuilder();
		
		String siteId = CommonHelper.getSiteIdHeader(request);
		String userIdDetails = CommonHelper.getSpecificCookie(request, CommonConstants.USER_DETAILS_UID_COOKIE);
		LOGGER.debug("UID while creating the endpoint url for logout" + userIdDetails);
		LOGGER.error("UID while creating the endpoint url for logout" + userIdDetails);
		
		if (null != bdbApiEndpointService) {
			endPointUrl.append(bdbApiEndpointService.getBDBHybrisDomain().trim());
			endPointUrl.append(bdbApiEndpointService.getHybrisSignOutEndpoint().trim());
			endpoint = StringUtils.replace(endPointUrl.toString(), CommonConstants.HYBRIS_USER_ID_LITERAL,
					userIdDetails);
			endpoint = StringUtils.replace(endpoint, CommonConstants.HYBRIS_SITE_LITERAL, siteId);
		}
		else {
			LOGGER.error("Could not get BdbEndpointService reference in SignOutService");
		}
		LOGGER.debug("Exit createEndPointUrl of SignOutService");
		LOGGER.error("Endpoint url for logout" + userIdDetails);
		return endpoint;
	}
}
