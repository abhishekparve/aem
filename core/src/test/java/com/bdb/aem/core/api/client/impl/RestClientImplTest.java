package com.bdb.aem.core.api.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;

/**
 * The Class RestClientImplTest.
 */
@ExtendWith(MockitoExtension.class)
public class RestClientImplTest {

	/** The rest client impl. */
	@InjectMocks
	RestClientImpl restClientImpl;

	/** The request. */
	@Mock
	BaseRequest request;

	/** The request entity. */
	@Mock
	StringEntity requestEntity;

	/** The request entity. */
	@Mock
	HttpEntity httpEntity;

	/** The http response. */
	@Mock
	CloseableHttpResponse httpResponse;

	/** The http client. */
	@Mock
	CloseableHttpClient httpClient;

	/** The status line. */
	@Mock
	StatusLine statusLine;

	/** The post method. */
	@Mock
	HttpPost postMethod;

	/** The get method. */
	@Mock
	HttpGet getMethod;

	/** The BDB api endpoint service. */
	@Mock
	BDBApiEndpointService bDBApiEndpointService;

	/** The response entity. */
	@Mock
	HttpEntity responseEntity;

	/** The cm. */
	@Mock
	PoolingHttpClientConnectionManager cm;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
	}

	/**
	 * Test send request post.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException             Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException 
	 */
	@Test
	public void testSendRequestPost() throws ClientProtocolException, IOException, AemInternalServerErrorException {
		when(request.getData()).thenReturn("requestData");
		lenient().when(request.gethttpMethodType()).thenReturn(HttpMethodType.POST);
		assertEquals("POST", HttpMethodType.POST.toString());
		when(request.getUrl()).thenReturn("/bdb/userregistration");
		final Map<String, String> headerMap = new HashMap<>();
		headerMap.put("content-type", "application/json");
		lenient().when(httpClient.execute(postMethod)).thenReturn(httpResponse);
		restClientImpl.sendRequest(request, headerMap);
	}

	/**
	 * Test send request get.
	 *
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException             Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException 
	 */
	@Test
	public void testSendRequestGet() throws ClientProtocolException, IOException, AemInternalServerErrorException {
		when(request.getData()).thenReturn("requestData");
		lenient().when(request.gethttpMethodType()).thenReturn(HttpMethodType.GET);
		assertEquals("GET", HttpMethodType.GET.toString());
		when(request.getUrl()).thenReturn("/bdb/userregistration");
		final Map<String, String> headerMap = new HashMap<>();
		headerMap.put("content-type", "application/json");
		lenient().when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(200);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		restClientImpl.sendRequest(request, headerMap);
	}
	
	@Test
	public void testSendRequestUpload() throws ClientProtocolException, IOException, AemInternalServerErrorException {
		final Map<String, String> headerMap = new HashMap<>();
		headerMap.put("content-type", "multipart/form-data");
		lenient().when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(200);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		restClientImpl.sendRequestUpload(postMethod, headerMap);
	}
	@Test
	public void testSendRequestUploadException() throws ClientProtocolException, IOException, AemInternalServerErrorException {
		final Map<String, String> headerMap = new HashMap<>();
		headerMap.put("content-type", "multipart/form-data");
		lenient().when(httpClient.execute(Mockito.any())).thenThrow(new IOException());
		Assertions.assertThrows(AemInternalServerErrorException.class, () -> {
			restClientImpl.sendRequestUpload(postMethod, headerMap);
		});
	}

	/**
	 * Test deactivate.
	 */
	@Test
	public void testDeactivate() {
		restClientImpl.deactivate();
	}

	/**
	 * Test get http client.
	 */
	@Test
	public void testGetHttpClient() {
		restClientImpl.getHttpClient();
	}

	/**
	 * Test activate.
	 */
	@Test
	public void testActivate() {
		when(bDBApiEndpointService.getRequestTimeoutConfig()).thenReturn(30);
		when(bDBApiEndpointService.getSocketTimeoutConfig()).thenReturn(30);
		when(bDBApiEndpointService.getConnectTimeoutConfig()).thenReturn(30);
		restClientImpl.activate();
	}

	/**
	 * Test set BDB api endpoint service.
	 */
	@Test
	public void testSetBDBApiEndpointService() {
		restClientImpl.setBDBApiEndpointService(bDBApiEndpointService);
	}

}
