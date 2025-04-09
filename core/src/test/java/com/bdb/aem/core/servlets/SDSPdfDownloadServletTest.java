package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.SDSDocumentSearchService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The Class SDSPdfDownloadServletTest.
 */
@ExtendWith({ MockitoExtension.class })
class SDSPdfDownloadServletTest {
	
	/** The SDS pdf download servlet. */
	@InjectMocks
	SDSPdfDownloadServlet SDSPdfDownloadServlet;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The reader. */
	@Mock
	BufferedReader reader;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;
	
	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The sds document service. */
	@Mock
	SDSDocumentSearchService sdsDocumentService;

	/** The request path. */
	@Mock
	RequestPathInfo requestPath;
	
	/** The stream. */
	@Mock
	ServletOutputStream stream;
	
	/** The request config. */
	@Mock
	RequestConfig requestConfig;
	
	/** The conn. */
	@Mock
	HttpResponse conn;
	
	/** The http client. */
	@Mock
	CloseableHttpClient httpClient;

	/** The path. */
	String[] path = { "country", "language", "12345" };

	/** The status line. */
	@Mock
	StatusLine statusLine;
	
	/** The element. */
	@Mock
	JsonElement element;

	/**
	 * Do get test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	@Test
	void doGetTest() throws IOException, AemInternalServerErrorException {
		lenient().when(request.getReader()).thenReturn(reader);
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(path);
		lenient().when(response.getWriter()).thenReturn(printWriter);
		lenient().when(response.getOutputStream()).thenReturn(stream);
		lenient().when(bdbApiEndpointService.getSdsEndpointUser()).thenReturn("SdsEndpointUser");
		lenient().when(bdbApiEndpointService.getSdsEndpointPassword()).thenReturn("SdsEndpointPassword");
		lenient().when(bdbApiEndpointService.getSdsPdfDownloadEndpoint()).thenReturn("SdsPdfDownloadEndpoint");
		lenient().when(bdbApiEndpointService.getRequestTimeoutConfig()).thenReturn(30);
		lenient().when(bdbApiEndpointService.getSocketTimeoutConfig()).thenReturn(30);
		lenient().when(bdbApiEndpointService.getConnectTimeoutConfig()).thenReturn(30);
		lenient().when(bdbApiEndpointService.getSdsPdfSearchEndpoint()).thenReturn("SdsPdfSearchEndpoint");
		JsonArray sdsArray = new JsonArray();
		JsonObject obj = new JsonObject();
		obj.addProperty("language", "language");
		sdsArray.add(element);
		lenient().when(element.getAsJsonObject()).thenReturn(obj);
		SDSPdfDownloadServlet.getStandaloneResponse(requestConfig, "language", conn, sdsArray);
		lenient().when(sdsDocumentService.searchSDSDocument("country", "12345")).thenReturn(sdsArray);
		SDSPdfDownloadServlet.doGet(request, response);

	}

	/**
	 * Do post test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void doPostTest() throws IOException {
		SDSPdfDownloadServlet.deactivate();
	}
}
