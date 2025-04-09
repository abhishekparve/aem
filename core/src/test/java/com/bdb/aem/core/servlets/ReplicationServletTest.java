package com.bdb.aem.core.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ReplicationService;
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class ReplicationServletTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ReplicationServletTest {
	
	/** The request. */
	@Mock
	SlingHttpServletRequest request;
	
	/** The response. */
	@Mock
	SlingHttpServletResponse response;
	
	/** The update product schema servlet. */
	@InjectMocks
	ReplicationServlet replicationServlet;
	
	/** The reader. */
	@Mock
	BufferedReader reader;
	
	/** The request object. */
	private JsonObject requestObject;

	/** The line. */
	private String line;

	/** The line 2. */
	private String line2;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The session. */
	@Mock
	Session session;
	
	/** The replication service. */
	@Mock
	ReplicationService replicationService;
	
	/** The print writer. */
	@Mock
	PrintWriter printWriter;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	 void setUp() throws Exception {
		when(request.getReader()).thenReturn(reader);
		line = "{\"country\":\"global\",\r\n" + " \"productID\":[\"644444\"] }";
		requestObject = new JsonObject();
		requestObject.addProperty("test", "Test");
		line2 = null;
		when(reader.readLine()).thenReturn(line, line2);
		when(request.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(response.getWriter()).thenReturn(printWriter);
	 }
	 
	/**
	 * Testdo post.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testdoPost() throws IOException {
		assertNotNull(request);
		replicationServlet.doPost(request, response);
	}

}
