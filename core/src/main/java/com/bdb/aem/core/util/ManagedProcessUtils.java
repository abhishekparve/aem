package com.bdb.aem.core.util;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.impl.BaseRequestImpl;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ManagedProcessUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetLatestSvSystemData.class);

    /**
     * Fetch auth token.
     *
     * @return the string
     * @throws AemInternalServerErrorException the aem internal server error
     *                                         exception
     */
    public static String fetchAuthToken(BDBApiEndpointService bdbApiEndpointService, RestClient restClient) throws AemInternalServerErrorException {
        LOGGER.debug("Entry fetchAuthToken of Managed Controlled Process Utils");
        JsonObject requestObject = new JsonObject();
        String accessToken = StringUtils.EMPTY;
        String endPoint = getAuthTokenApiEndpoint(bdbApiEndpointService);
        if (StringUtils.isNotBlank(endPoint)) {
            final Map<String, String> requestHeaderMap = new HashMap<>();
            final BaseRequest baseRequest = new BaseRequestImpl(endPoint, HttpMethodType.POST,
                    requestObject.toString());
            accessToken = getAccessToken(restClient.sendRequest(baseRequest, requestHeaderMap));
        }
        LOGGER.debug("Exit fetchAuthToken of Managed Controlled Process Utils");
        return accessToken;
    }


    /**
     * Gets the auth token api endpoint.
     *
     * @return the auth token api endpoint
     */
    public static String getAuthTokenApiEndpoint(BDBApiEndpointService bdbApiEndpointService) {
        LOGGER.debug("Entry getAuthTokenApiEndpoint with bdbApiEndpointService: {}", bdbApiEndpointService);
        String endPoint = StringUtils.EMPTY;
        if (null != bdbApiEndpointService) {
            byte[] decodedBytes = Base64.getDecoder().decode(bdbApiEndpointService.getFetchAuthTokenEndpointClientSecret());
            String decodedClientSecret = new String(decodedBytes);

            StringBuilder stringBuilder = new StringBuilder(bdbApiEndpointService.getBDBHybrisDomain().trim());
            stringBuilder.append(bdbApiEndpointService.getFetchAuthTokenEndpoint()).append(CommonConstants.CLIENT_ID)
                    .append(CommonConstants.EQUAL).append(bdbApiEndpointService.getFetchAuthTokenEndpointClientId())
                    .append(CommonConstants.AND).append(CommonConstants.GRANT_TYPE).append(CommonConstants.EQUAL)
                    .append(bdbApiEndpointService.getFetchAuthTokenEndpointGrantType()).append(CommonConstants.AND)
                    .append(CommonConstants.CLIENT_SECRET).append(CommonConstants.EQUAL)
                    .append(decodedClientSecret);
            endPoint = StringUtils.replace(stringBuilder.toString(), CommonConstants.HYBRIS_USER_ID_LITERAL,
                    bdbApiEndpointService.getAnonymousUserIdPlaceholder());
            LOGGER.debug("URL TO hit Rest API to get token : {}",endPoint);
        }
        LOGGER.debug("Exit getAuthTokenApiEndpoint");

        return endPoint;
    }

    /**
     * Gets the access token.
     *
     * @param baseResponse the base response
     * @return the access token
     */
    public static String getAccessToken(BaseResponse baseResponse) {
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
        LOGGER.debug("Exit getAccessToken");
        return authString;
    }

    /**
     *
     * @param resourceResolver
     * @param filePath
     * @param responseString
     * @return String
     * @throws RepositoryException
     */
    public static String createDamAsset(ResourceResolver resourceResolver, String filePath, String responseString) throws RepositoryException {
        InputStream is = new ByteArrayInputStream(responseString.getBytes()); //we are sending the JSON data as a String.
        AssetManager assetMgr = resourceResolver.adaptTo(AssetManager.class);
        Session session = resourceResolver.adaptTo(Session.class);
        assert assetMgr != null;
        ValueFactory valueFactory = session.getValueFactory();
        Asset asset = assetMgr.createOrUpdateAsset(filePath, valueFactory.createBinary(is), CommonConstants.APPLICATION_JSON, true);
        session.save();
        String assetPath = asset.getPath();
        LOGGER.debug("New Json file Created for SV System data at {}", assetPath);
        return assetPath;
    }

    /**
     *
     * @param authToken
     * @param endPoint
     * @return Response
     * @throws AemInternalServerErrorException
     */
    public static String getResponseStr(String authToken, String endPoint, RestClient restClient, HttpMethodType method) throws AemInternalServerErrorException {
        String responseString;
        BaseResponse baseResponse = getApiResponse(authToken, endPoint, new JsonObject(), restClient, method);
        responseString = baseResponse.hasError() ? baseResponse.getError().getMessage() : baseResponse.getResponseData().getData();
        return responseString;
    }

    /**
     *
     * @param authToken
     * @param endPoint
     * @param jsonObject
     * @return BaseResponse
     * @throws AemInternalServerErrorException
     */
    public static BaseResponse getApiResponse(String authToken, String endPoint, JsonObject jsonObject, RestClient restClient, HttpMethodType method) throws AemInternalServerErrorException {
        final Map<String, String> requestHeaderMap = new HashMap<>();
        requestHeaderMap.put(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
        requestHeaderMap.put(CommonConstants.AUTHORIZATION,
                new StringBuilder(CommonConstants.BEARER_WITH_SPACE).append(authToken).toString());
        LOGGER.debug("End Point : {}", endPoint);

        final BaseRequest baseRequest = new BaseRequestImpl(endPoint, method,
                jsonObject.toString());
        BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);

        return baseResponse;
    }

}
