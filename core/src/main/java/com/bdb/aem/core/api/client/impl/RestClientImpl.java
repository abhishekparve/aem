package com.bdb.aem.core.api.client.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.api.response.DataResponse;
import com.bdb.aem.core.api.response.ErrorResponse;
import com.bdb.aem.core.api.response.impl.BaseResponseImpl;
import com.bdb.aem.core.api.response.impl.DataResponseImpl;
import com.bdb.aem.core.api.response.impl.ErrorResponseImpl;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.TimeLoggingUtil;

/**
 * The Class RestServiceImpl. The Rest Client uses
 * PoolingHttpClientConnectionManager to create the ClosableHttpClient THe
 * Connection is set with max connections with 200
 */
@Component(service = RestClient.class, immediate = true)
public class RestClientImpl implements RestClient {
	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RestClientImpl.class);

	/**
	 * The http client.
	 */
	private CloseableHttpClient httpClient;

	/**
	 * The cm.
	 */
	private PoolingHttpClientConnectionManager cm;

	/** The BDB Api end point service. */
	@Reference
	BDBApiEndpointService bDBApiEndpointService;


	/**
	 * Send request.
	 *
	 * @param request the request
	 * @param headerMap the header map
	 * @return the base response
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	@Override
	public final BaseResponse sendRequest(final BaseRequest request, final Map<String, String> headerMap) throws AemInternalServerErrorException {

		long lStartTime = TimeLoggingUtil.getStartTime();
		LOGGER.debug("**************************** Start Request ********************* ");
		BaseResponse baseResponse = new BaseResponseImpl();
		HttpPost postMethod = null;
		HttpGet getMethod = null;
		HttpPut putMethod = null;
		HttpDelete deleteMethod = null;
		try {
			StringEntity requestEntity = new StringEntity(request.getData(), ContentType.APPLICATION_JSON);
			if ("POST".equals(request.gethttpMethodType().toString())) {
				postMethod = new HttpPost(request.getUrl());
				postMethod.setEntity(requestEntity);
			} else if ("GET".equals(request.gethttpMethodType().toString())) {
				getMethod = new HttpGet(request.getUrl());
			} else if("PUT".equals(request.gethttpMethodType().toString())){
				putMethod = new HttpPut(request.getUrl());
				putMethod.setEntity(requestEntity);
			}else if("DELETE".equals(request.gethttpMethodType().toString())){
				deleteMethod = new HttpDelete(request.getUrl());
			}

			if (headerMap != null && !headerMap.isEmpty()) {
				LOGGER.debug("headerMap not null");
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					setGetAndPostHeader(postMethod, getMethod, entry);
				}
			}

			if (postMethod != null) {
				baseResponse = getResponseData(httpClient.execute(postMethod));
			} else if (getMethod != null) {
				baseResponse = getResponseData(httpClient.execute(getMethod));
			}
			else if (putMethod != null) {
				baseResponse = getResponseData(httpClient.execute(putMethod));
			}
			else if (deleteMethod != null) {
				baseResponse = getResponseData(httpClient.execute(deleteMethod));
			}
			LOGGER.debug("Total Time Taken for API Request - {} ms", TimeLoggingUtil.logMethodTime(lStartTime));
			LOGGER.debug("**************************** End Request *********************");
		} catch (IOException | IllegalArgumentException e) {
			throw new AemInternalServerErrorException(CommonConstants.AEM_INTERNAL_SERVER_ERROR, e);
		} finally {
			if (null != postMethod) {
				postMethod.releaseConnection();
			}
		}

		LOGGER.debug("Exit sendRequestWithBody");
		return baseResponse;
	}
	
	/**
	 * Send request upload.
	 *
	 * @param postMethod the post method
	 * @param headerMap the header map
	 * @return the base response
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	@Override
	public final BaseResponse sendRequestUpload(final HttpPost postMethod, final Map<String, String> headerMap)
			throws AemInternalServerErrorException {

		long lStartTime = TimeLoggingUtil.getStartTime();
		LOGGER.debug("**************************** Start sendRequestUpload Request ********************* ");
		BaseResponse baseResponse = new BaseResponseImpl();
		HttpGet getMethod = null;
		try {
			if (headerMap != null && !headerMap.isEmpty()) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					setGetAndPostHeader(postMethod, getMethod, entry);
				}
			}
			baseResponse = getResponseData(httpClient.execute(postMethod));
			LOGGER.debug("Total Time Taken for API Request - {} ms", TimeLoggingUtil.logMethodTime(lStartTime));
			LOGGER.debug("**************************** End sendRequestUpload Request *********************");
		} catch (IOException | IllegalArgumentException e) {
			throw new AemInternalServerErrorException(CommonConstants.AEM_INTERNAL_SERVER_ERROR, e);
		} finally {
			if (null != postMethod) {
				postMethod.releaseConnection();
			}
		}
		LOGGER.trace("Exit sendRequestUpload");
		return baseResponse;
	}

	/**
	 * Sets the get and post header.
	 *
	 * @param postMethod the post method
	 * @param getMethod  the get method
	 * @param entry      the entry
	 */
	private void setGetAndPostHeader(HttpPost postMethod, HttpGet getMethod, Map.Entry<String, String> entry) {
		if (postMethod != null) {
			postMethod.addHeader(entry.getKey(), entry.getValue());
		} else if (getMethod != null) {
			getMethod.addHeader(entry.getKey(), entry.getValue());
		}
		LOGGER.debug(" Successfully setting get and post header");
	}

	/**
	 * Gets the response data.
	 *
	 * @param httpResponse the http response
	 * @return the response data
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static final BaseResponse getResponseData(HttpResponse httpResponse) throws IOException {
		String responseData;
		int statusCode;
		if (httpResponse != null) {
			LOGGER.debug("Inside httpResponse Not Null");
			statusCode = httpResponse.getStatusLine().getStatusCode();
			LOGGER.debug("Status code:"+statusCode);
			responseData = EntityUtils.toString(httpResponse.getEntity());
			LOGGER.debug("httpResponse data:"+responseData);
		} else {
			LOGGER.debug("Inside httpResponse Null");
			statusCode = javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			responseData = CommonConstants.HYBRIS_INTERNAL_SERVER_ERROR;
		}
		return createBaseResponse(statusCode, responseData);
	}

	/**
	 * Activate.
	 */
	@Activate
	public void activate() {
		LOGGER.debug("RestClientImpl Activated");
		int requestTimeoutConfig = bDBApiEndpointService.getRequestTimeoutConfig();
		int scketTimeoutConfig = bDBApiEndpointService.getSocketTimeoutConfig();
		int connectTimeoutConfig = bDBApiEndpointService.getConnectTimeoutConfig();
		try {
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustStrategy() {
				@Override
				public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1.2" },
					null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register(CommonConstants.HTTPS_LITERAL, sslsf)
					.register(CommonConstants.HTTP_LITERAL, new PlainConnectionSocketFactory()).build();
			// NOSONAR
			cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			cm.setMaxTotal(500);
			// Increase default max connection per route to 20
			cm.setDefaultMaxPerRoute(100);

			RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
					.setSocketTimeout(scketTimeoutConfig * 1000).setConnectTimeout(connectTimeoutConfig * 1000)
					.setConnectionRequestTimeout(requestTimeoutConfig * 1000).build();

			// Build the client.
			httpClient = HttpClientBuilder.create().useSystemProperties().setDefaultRequestConfig(requestConfig).setConnectionManager(cm)
					.setConnectionManagerShared(true)
					.build();

		} catch (KeyManagementException e) {
			LOGGER.error("KeyManagementException", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("NoSuchAlgorithmException", e);
		} catch (KeyStoreException e) {
			LOGGER.error("KeyStoreException", e);
		}
		LOGGER.debug("Exit RestClientImpl Activate");
	}

	/**
	 * Deactivate.
	 */
	@Deactivate
	public void deactivate() {
		LOGGER.debug("RestClientImpl Deactivated");
		try {
			if (null != httpClient) {
				httpClient.close();
			}
			if (null != cm) {
				cm.close();
			}
		} catch (IOException e) {
			LOGGER.error("IOException during closing of httpClient", e);
		}
	}

	/**
	 * Sets the BDB Api end point service.
	 *
	 * @param bDBApiEndpointService the new BDB Api end point service
	 */
	public void setBDBApiEndpointService(BDBApiEndpointService bDBApiEndpointService) {
		this.bDBApiEndpointService = bDBApiEndpointService;
	}

	/**
	 * Gets the http client.
	 *
	 * @return the http client
	 */
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * Creates the base response.
	 *
	 * @param statusCode the status code
	 * @param responseData the response data
	 * @return the base response
	 */
	private static BaseResponse createBaseResponse(int statusCode, String responseData) {
		LOGGER.debug("Status Code in createBaseResponse:" + statusCode);
		LOGGER.debug("Response Data in createBaseResponse:" + responseData);
		BaseResponse baseResponse = new BaseResponseImpl();
		if (statusCode == javax.servlet.http.HttpServletResponse.SC_OK
				|| statusCode == javax.servlet.http.HttpServletResponse.SC_CREATED
				|| statusCode == javax.servlet.http.HttpServletResponse.SC_ACCEPTED || statusCode == HttpServletResponse.SC_NO_CONTENT) {
			DataResponse dataResponse = new DataResponseImpl();
			dataResponse.setStatusCode(statusCode);
			dataResponse.setData(responseData);
			LOGGER.debug("Data Response in createBaseResponse:" + dataResponse);
			baseResponse.setResponseData(dataResponse);
			LOGGER.debug("Base Response in createBaseResponse:" + baseResponse);
		} else {
			LOGGER.debug("Inside createBaseResponse else block:");
			ErrorResponse errorResponse = new ErrorResponseImpl(statusCode, responseData);
			baseResponse.setError(errorResponse);
		}
		return baseResponse;
	}
}
