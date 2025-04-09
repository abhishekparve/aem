package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.SSOLoginService;
import com.day.cq.replication.ReplicationException;
import com.google.gson.JsonObject;
import static org.mockito.Mockito.doThrow;

/**
 * The Class RefreshTokenServletTest.
 */
@ExtendWith(MockitoExtension.class)
class RefreshTokenServletTest {

	/** The Refresh token servlet. */
	@InjectMocks
	RefreshTokenServlet RefreshTokenServlet;

	/** The reader. */
	@Mock
	BufferedReader reader;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The bead lot structure update service. */
	@Mock
	SSOLoginService sSOLoginService;

	/** The request object. */
	private JsonObject requestObject;

	/** The request 1. */
	private String request1;

	/** The request 2. */
	private String request2;

	/**
	 * Inits the.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void init() throws IOException {
		when(request.getReader()).thenReturn(reader);
		request1 = "{\"test\":Test}";
		requestObject = new JsonObject();
		requestObject.addProperty("test", "Test");
		request2 = null;
		when(reader.readLine()).thenReturn(request1, request2);
		when(response.getWriter()).thenReturn(printWriter);
	}

	/**
	 * Testdo post.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	public void testdoPost() throws IOException {
		RefreshTokenServlet.doPost(request, response);
	}

	/**
	 * Test exception.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	public void testException() throws IOException, AemInternalServerErrorException {
		doThrow(new AemInternalServerErrorException("test", new AemInternalServerErrorException("test")))
				.when(sSOLoginService).refreshTokens(request, response, requestObject);
		RefreshTokenServlet.doPost(request, response);
	}
}
