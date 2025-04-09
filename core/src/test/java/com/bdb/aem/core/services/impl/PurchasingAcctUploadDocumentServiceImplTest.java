
package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.api.response.DataResponse;
import com.bdb.aem.core.api.response.ErrorResponse;
import com.bdb.aem.core.api.response.impl.BaseResponseImpl;
import com.bdb.aem.core.api.response.impl.DataResponseImpl;
import com.bdb.aem.core.api.response.impl.ErrorResponseImpl;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.FetchBearerTokenService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class PurchasingAcctUploadDocumentServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PurchasingAcctUploadDocumentServiceImplTest {

	/** The purchasing acct upload document service impl. */
	@InjectMocks
	PurchasingAcctUploadDocumentServiceImpl purchasingAcctUploadDocumentServiceImpl;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The fetch bearer token service. */
	@Mock
	FetchBearerTokenService fetchBearerTokenService;

	/** The rest client. */
	@Mock
	private RestClient restClient;

	/** The response. */
	@Mock
	private SlingHttpServletResponse response;

	/** The base response. */
	private BaseResponse baseResponse = new BaseResponseImpl();;

	/** The request. */
	private MockSlingHttpServletRequest request;

	/**
	 * Sets the up.
	 *
	 * @param context the new up
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp(AemContext context) throws Exception {
		request = context.request();
		request.addRequestParameter("key", "value");
		when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("test");
		when(bdbApiEndpointService.getUploadTaxCertificateEndpoint()).thenReturn("test");
	}

	/**
	 * Test upload document process response IF.
	 *
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testUploadDocumentProcessResponseIF() throws AemInternalServerErrorException, IOException {
		when(fetchBearerTokenService.fetchAuthToken(request)).thenReturn("test");
		when(restClient.sendRequestUpload(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		Assertions.assertThrows(AemInternalServerErrorException.class, () -> {
			purchasingAcctUploadDocumentServiceImpl.uploadDocument(request, response);
		});
	}

	/**
	 * Test upload document process response else IF.
	 *
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testUploadDocumentProcessResponseElseIF() throws AemInternalServerErrorException, IOException {
		DataResponse dataResponse = new DataResponseImpl();
		baseResponse.setResponseData(dataResponse);
		when(fetchBearerTokenService.fetchAuthToken(request)).thenReturn("test");
		when(restClient.sendRequestUpload(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		purchasingAcctUploadDocumentServiceImpl.uploadDocument(request, response);
	}

	/**
	 * Test upload document process response else.
	 *
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testUploadDocumentProcessResponseElse() throws AemInternalServerErrorException, IOException {
		ErrorResponse errorResponse = new ErrorResponseImpl(0, "Error");
		baseResponse.setError(errorResponse);
		when(fetchBearerTokenService.fetchAuthToken(request)).thenReturn("test");
		when(restClient.sendRequestUpload(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		purchasingAcctUploadDocumentServiceImpl.uploadDocument(request, response);
	}

	/**
	 * Test upload document else.
	 *
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testUploadDocumentElse() throws AemInternalServerErrorException, IOException {
		when(fetchBearerTokenService.fetchAuthToken(request)).thenReturn("");
		Assertions.assertThrows(AemInternalServerErrorException.class, () -> {
			purchasingAcctUploadDocumentServiceImpl.uploadDocument(request, response);
		});
	}
}
