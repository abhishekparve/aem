package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.PurchasingAcctUploadDocumentService;

/**
 * The Class PurchasingAcctUploadDocumentServlet.
 */
@ExtendWith(MockitoExtension.class)
class PurchasingAcctUploadDocumentServletTest {

	/** The purchasing acct upload document servlet. */
	@InjectMocks
	PurchasingAcctUploadDocumentServlet purchasingAcctUploadDocumentServlet;

	/** The reader. */
	@Mock
	BufferedReader reader;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The request path info mock. */
	@Mock
	RequestPathInfo requestPathInfo;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The purchasing acct upload document service. */
	@Mock
	PurchasingAcctUploadDocumentService purchasingAcctUploadDocumentService;

	/**
	 * Inits the.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void init() throws IOException {
	}

	/**
	 * Test do post.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	@Test
	public void testDoPost() throws IOException, AemInternalServerErrorException {
		when(purchasingAcctUploadDocumentService.uploadDocument(request, response)).thenReturn("TestSuccess");
		when(response.getWriter()).thenReturn(printWriter);
		purchasingAcctUploadDocumentServlet.doPost(request, response);
	}

	/**
	 * Test do post printwriter null.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	@Test
	public void testDoPostPrintwriterNull() throws IOException, AemInternalServerErrorException {
		when(purchasingAcctUploadDocumentService.uploadDocument(request, response)).thenReturn("TestSuccess");
		when(response.getWriter()).thenReturn(printWriter);
		purchasingAcctUploadDocumentServlet.doPost(request, response);
	}

	/**
	 * Test do post exception.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	@Test
	public void testDoPostException() throws IOException, AemInternalServerErrorException {
		when(purchasingAcctUploadDocumentService.uploadDocument(request, response))
				.thenThrow(new AemInternalServerErrorException("TestFailure"));
		when(response.getWriter()).thenReturn(null);
		purchasingAcctUploadDocumentServlet.doPost(request, response);
	}
}
