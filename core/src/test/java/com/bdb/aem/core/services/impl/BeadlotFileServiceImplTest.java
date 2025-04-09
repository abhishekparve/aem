package com.bdb.aem.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.client.impl.RestClientImpl;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.impl.BaseRequestImpl;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.api.response.DataResponse;
import com.bdb.aem.core.api.response.ErrorResponse;
import com.bdb.aem.core.api.response.impl.BaseResponseImpl;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BeadlotFileService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BeadlotFileServiceImplTest {
	
	@InjectMocks
	BeadlotFileServiceImpl beadlotFileServiceImpl; 
	
//	@Mock
//	BeadlotFileServiceImpl beadlotFileServiceImpl;
//	
	@Mock
	SlingHttpServletRequest request;
	
	@Mock
	SlingHttpServletResponse response;
	
	@Mock
	RestClientImpl restClientImpl;
	
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	@Mock
	BaseResponseImpl baseResponseImpl;
	
	@Mock
	BaseRequest baseRequest;
	
	@Mock
	BaseResponse baseResponse;
	
	@Mock
	CloseableHttpClient httpClient;
	
	@Mock
	HttpUriRequest httpRequest;
	
	@Mock
	RestClient restClient;
	
	@Mock
	CloseableHttpResponse httpResponse;
	
	@Mock
	DataResponse dataResponse;
	
	@Mock
	ErrorResponse errorResponse;
	
	private String responseString = "{\r\n" + "   \"cartID\": \"a02426e1-8cfa-4610-b6c1-f4fbcc3b25f4\",\r\n"
			+ "   \"expiresIn\": \"7199\",\r\n" + "   \"firstName\": \"Saurabh\",\r\n"
			+ "   \"hybrisToken\": \"7d82dc09-67f3-4b2a-8136-ebccee223963\",\r\n" + "   \"lastName\": \"Sethia\",\r\n"
			+ "   \"ssoToken\": \"Xevatn6zjwbRAEb16zbH3wsGSJ0H\",\r\n" + "   \"uid\": \"caweb_987735000\"\r\n" + "}";
	
	private String errorResponseString = "{\r\n" + "   \"errors\": [\r\n" + "      {\r\n"
			+ "         \"errorCode\": \"-1\",\r\n" + "         \"message\": \"Invalid captcha entered xbc\",\r\n"
			+ "         \"type\": \"BdbRecaptchaError\"\r\n" + "      }\r\n" + "   ]\r\n" + "}";

	
	@Test
	void testFetchBeadLotFileDetails() throws AemInternalServerErrorException, ClientProtocolException, IOException, NoSuchFieldException {
		PrivateAccessor.setField(restClientImpl, "httpClient", httpClient);
		PrivateAccessor.setField(baseResponseImpl, "data", dataResponse);
		String content = "{\r\n"
				+ "    \"products\": [{\r\n"
				+ "        \"baseProduct\": {\r\n"
				+ "            \"code\": \"code\",\r\n"
				+ "            \"variants\": \"variants\"\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ " \r\n"
				+ "\r\n"
				+ "    }],\r\n"
				+ "    \"parentPagePath\": \"page/path\",\r\n"
				+ "    \"type\": \"event\",\r\n"
				+ "    \"deactivationDate\": \"2018-01-09T11:11:02.0+03:00\",\r\n"
				+ "    \"ctaLabel\": \"ctaLabel\",\r\n"
				+ "    \"timeout\": 5000,\r\n"
				+ "    \"isEnable\": true\r\n"
				+ "}";
		JsonObject requestObject = new JsonParser().parse(content).getAsJsonObject();
		when(bdbApiEndpointService.getBeadlotsFileDownloadEndpoint()).thenReturn("end");  //https://apidev.carefusion.com/BeadLotsAPI/erpDoc/beadLots
		when(bdbApiEndpointService.getBeadlotsFileDownloadUsername()).thenReturn("");  //beadlotsmule
		when(bdbApiEndpointService.getBeadlotsFileDownloadPwd()).thenReturn("");	   //Be@dLotsMule			//POST end HTTP/1.1
		when(restClient.sendRequest(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		when(baseResponse.hasError()).thenReturn(false);
		when(baseResponse.getResponseData()).thenReturn(dataResponse);
		when(dataResponse.getData()).thenReturn(responseString);
		beadlotFileServiceImpl.fetchBeadLotFileDetails(request, requestObject, response);
	}

	@Test
	void testFetchBeadLotFileDetailsElse() throws AemInternalServerErrorException, ClientProtocolException, IOException, NoSuchFieldException {
		PrivateAccessor.setField(restClientImpl, "httpClient", httpClient);
		PrivateAccessor.setField(baseResponseImpl, "data", dataResponse);
		String content = "{\r\n"
				+ "    \"products\": [{\r\n"
				+ "        \"baseProduct\": {\r\n"
				+ "            \"code\": \"code\",\r\n"
				+ "            \"variants\": \"variants\"\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ " \r\n"
				+ "\r\n"
				+ "    }],\r\n"
				+ "    \"parentPagePath\": \"page/path\",\r\n"
				+ "    \"type\": \"event\",\r\n"
				+ "    \"deactivationDate\": \"2018-01-09T11:11:02.0+03:00\",\r\n"
				+ "    \"ctaLabel\": \"ctaLabel\",\r\n"
				+ "    \"timeout\": 5000,\r\n"
				+ "    \"isEnable\": true\r\n"
				+ "}";
		JsonObject requestObject = new JsonParser().parse(content).getAsJsonObject();
		when(bdbApiEndpointService.getBeadlotsFileDownloadEndpoint()).thenReturn("end");  //https://apidev.carefusion.com/BeadLotsAPI/erpDoc/beadLots
		when(bdbApiEndpointService.getBeadlotsFileDownloadUsername()).thenReturn("");  //beadlotsmule
		when(bdbApiEndpointService.getBeadlotsFileDownloadPwd()).thenReturn("");	   //Be@dLotsMule			//POST end HTTP/1.1
		when(restClient.sendRequest(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		when(baseResponse.hasError()).thenReturn(true);
		when(baseResponse.getResponseData()).thenReturn(dataResponse);
		when(baseResponse.getError()).thenReturn(errorResponse);
		beadlotFileServiceImpl.fetchBeadLotFileDetails(request, requestObject, response);
	}
	
	@Test
	void testFetchBeadLotFileDetailsOuterElse() throws AemInternalServerErrorException {
		String content = "{\r\n"
				+ "    \"products\": [{\r\n"
				+ "        \"baseProduct\": {\r\n"
				+ "            \"code\": \"code\",\r\n"
				+ "            \"variants\": \"variants\"\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ " \r\n"
				+ "\r\n"
				+ "    }],\r\n"
				+ "    \"parentPagePath\": \"page/path\",\r\n"
				+ "    \"type\": \"event\",\r\n"
				+ "    \"deactivationDate\": \"2018-01-09T11:11:02.0+03:00\",\r\n"
				+ "    \"ctaLabel\": \"ctaLabel\",\r\n"
				+ "    \"timeout\": 5000,\r\n"
				+ "    \"isEnable\": true\r\n"
				+ "}";
		JsonObject requestObject = new JsonParser().parse(content).getAsJsonObject();
		beadlotFileServiceImpl.fetchBeadLotFileDetails(request, requestObject, response);
	}


}


