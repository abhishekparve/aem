package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.impl.BaseRequestImpl;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BeadlotFileService;
import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class BeadlotFileServiceImpl.
 */
@Component(service = BeadlotFileService.class, immediate = true)
public class BeadlotFileServiceImpl implements BeadlotFileService {


    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BeadlotFileServiceImpl.class);

    /**
     * The rest client.
     */
    @Reference
    private RestClient restClient;

    @Reference
    BDBApiEndpointService bdbApiEndpointService;

    /**
     * fetch beadlot file details.
     *
     * @param request       the request
     * @param requestObject the request object
     * @param response      the response
     * @return the string
     * @throws AemInternalServerErrorException the aem internal server error
     *                                         exception
     */
    @Override
    public String fetchBeadLotFileDetails(SlingHttpServletRequest request, JsonObject requestObject,
                                          SlingHttpServletResponse response) throws AemInternalServerErrorException {
        LOGGER.debug("Entry fetchBeadLotFileDetails method of BeadlotFileServiceImpl class");
        String responseString;
        // get endpoint
        String endPoint = bdbApiEndpointService.getBeadlotsFileDownloadEndpoint();
        if (StringUtils.isNotEmpty(endPoint)) {
            try {

                final Map<String, String> requestHeaderMap = new HashMap<>();

                String name = bdbApiEndpointService.getBeadlotsFileDownloadUsername();
                String password = bdbApiEndpointService.getBeadlotsFileDownloadPwd();
                String authString = name + ":" + password;
                String authStringEnc = Base64.getEncoder().encodeToString(
                        authString.getBytes("utf-8"));
                LOGGER.debug("Base64 encoded auth string: " + authStringEnc);

                requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
                requestHeaderMap.put(CommonConstants.AUTHORIZATION,
                        "Basic " + authStringEnc);
                LOGGER.debug("End Point : {}", endPoint);
                final BaseRequest baseRequest = new BaseRequestImpl(endPoint, HttpMethodType.POST,
                        requestObject.toString());
                BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
                if (!baseResponse.hasError()) {
                    response.setStatus(baseResponse.getResponseData().getStatusCode());
                    responseString = baseResponse.getResponseData().getData();
                } else {
                    response.setStatus(baseResponse.getResponseData().getStatusCode());
                    //response.setStatus(baseResponse.getError().getErrorCode());
                    responseString = baseResponse.getError().getMessage();
                    LOGGER.error(" error ResponseString {0} from endPoint {0}", responseString , endPoint);
                }
            } catch (UnsupportedEncodingException uex) {
                LOGGER.error("Unsupported Encoding Exception {}", uex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                responseString = CommonConstants.AEM_INTERNAL_SERVER_ERROR;
                LOGGER.error("endPoint {0}", endPoint);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseString = CommonConstants.AEM_INTERNAL_SERVER_ERROR;
            LOGGER.error("endPoint {0}", endPoint);
        }
        LOGGER.debug("Exit fetchBeadLotFileDetails of BeadlotFileServiceImpl");
        return responseString;
    }


}

