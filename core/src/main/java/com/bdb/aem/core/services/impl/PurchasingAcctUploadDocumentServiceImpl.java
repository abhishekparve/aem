package com.bdb.aem.core.services.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.api.response.impl.BaseResponseImpl;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.exceptions.InvalidRequestException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.FetchBearerTokenService;
import com.bdb.aem.core.services.PurchasingAcctUploadDocumentService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

/**
 * The Class PurchasingAcctUploadDocumentServiceImpl.
 */
@Component(service = PurchasingAcctUploadDocumentService.class, immediate = true)
public class PurchasingAcctUploadDocumentServiceImpl implements PurchasingAcctUploadDocumentService {

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchasingAcctUploadDocumentServiceImpl.class);

	/** The Constant REQUIRED_PARAM. */
	private static final String REQUIRED_PARAM = "Either Access token : {0} or Endpoint is missing : {1} ";

	/**
	 * The bdb api endpoint service.
	 */
	@Reference
	private BDBApiEndpointService bdbApiEndpointService;

	/** The fetch bearer token service. */
	@Reference
	private FetchBearerTokenService fetchBearerTokenService;

	/** The rest client. */
	@Reference
	private RestClient restClient;

	/**
	 * Upload document.
	 *
	 * @param request  the request
	 * @param response the response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Override
	public String uploadDocument(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws AemInternalServerErrorException {
		LOGGER.debug("Entry fetchSignUpDetails of SSOLoginServiceImpl");
		String apiResponse = StringUtils.EMPTY;
		try {
			String endPoint = CommonHelper.createEndPointUrl(bdbApiEndpointService, CommonConstants.UPLOAD_DOCUMENT,
					request);
			String accessToken = fetchBearerTokenService.fetchAuthToken(request);
			LOGGER.debug("End Point : {}", endPoint);
			if (StringUtils.isNotBlank(endPoint) && StringUtils.isNotBlank(accessToken)) {
				Map<String, RequestParameter> requestParameters = getFormData(request);
				BaseResponse baseResponse = uploadDocEndpoint(requestParameters, endPoint, accessToken);
				apiResponse = processResponse(response, baseResponse);
			} else {
				throw new AemInternalServerErrorException(CommonConstants.ACCESS_TOKEN_RETRIEVAL_ERROR,
						new InvalidRequestException(MessageFormat.format(REQUIRED_PARAM, accessToken, endPoint)));
			}
		} catch (IOException e) {
			throw new AemInternalServerErrorException(CommonConstants.AEM_INTERNAL_SERVER_ERROR, e);
		}
		LOGGER.debug("Exit fetchSignUpDetails of SSOLoginServiceImpl");
		return apiResponse;
	}

	/**
	 * Process response.
	 *
	 * @param response     the response
	 * @param baseResponse the base response
	 * @return the string
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */

	private String processResponse(SlingHttpServletResponse response, BaseResponse baseResponse)
			throws AemInternalServerErrorException {
		LOGGER.debug("Entry processResponse of PurchasingAcctUploadDocumentServiceImpl");
		String responseString;
		if (null == baseResponse.getError() && null == baseResponse.getResponseData()) {
			throw new AemInternalServerErrorException(CommonConstants.MISSING_FORM_DATA,
					new InvalidRequestException(CommonConstants.MISSING_FORM_DATA));
		} else if (!baseResponse.hasError()) {
			response.setStatus(baseResponse.getResponseData().getStatusCode());
			responseString = baseResponse.getResponseData().getData();
		} else {
			response.setStatus(baseResponse.getError().getErrorCode());
			responseString = baseResponse.getError().getMessage();
		}
		LOGGER.debug("Exit processResponse of PurchasingAcctUploadDocumentServiceImpl");
		return responseString;
	}

	/**
	 * Gets the form data.
	 *
	 * @param request the request
	 * @return the form data
	 */
	public Map<String, RequestParameter> getFormData(SlingHttpServletRequest request) {
		Map<String, RequestParameter> parameters = new HashMap<>();
		String key;
		RequestParameter[] value;
		Map<String, RequestParameter[]> params = request.getRequestParameterMap();
		for (Map.Entry<String, RequestParameter[]> entry : params.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (value.length > 0) {
				parameters.put(key, value[0]);
			}
		}
		return parameters;
	}

	/**
	 * Upload doc endpoint.
	 *
	 * @param parameters  the parameters
	 * @param endPoint    the end point
	 * @param accessToken the access token
	 * @return the base response
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 */
	private BaseResponse uploadDocEndpoint(Map<String, RequestParameter> parameters, String endPoint,
			String accessToken) throws AemInternalServerErrorException, IOException {

		if (MapUtils.isEmpty(parameters)) {
			return new BaseResponseImpl();
		} else {

			final Map<String, String> requestHeaderMap = new HashMap<>();
			requestHeaderMap.put(CommonConstants.AUTHORIZATION,
					new StringBuilder(CommonConstants.BEARER_WITH_SPACE).append(accessToken).toString());

			HttpEntity multipart = CommonHelper.createMultiPartEntity(parameters);
			HttpPost postMethod = new HttpPost(endPoint);
			postMethod.setEntity(multipart);
			return restClient.sendRequestUpload(postMethod, requestHeaderMap);
		}
	}
}