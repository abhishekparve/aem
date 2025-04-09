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
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BeadLotStructureUpdateService;
import com.day.cq.replication.ReplicationException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * The Class BeadLotStructureUpdateServletTest.
 */
@ExtendWith(MockitoExtension.class)
class BeadLotStructureUpdateServletTest {

	/** The bead lot structure update servlet. */
	@InjectMocks
	BeadLotStructureUpdateServlet beadLotStructureUpdateServlet;

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
	BeadLotStructureUpdateService beadLotStructureUpdateService;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The session. */
	@Mock
	Session session;

	/** The request object. */
	private JsonObject requestObject;

	/** The request 1. */
	private String request1;

	/** The api response. */
	private String apiResponse;

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
		apiResponse = "Testing";
		requestObject = new JsonObject();
		requestObject.addProperty("test", "Test");
		request2 = null;
		when(reader.readLine()).thenReturn(request1, request2);
		when(request.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(response.getWriter()).thenReturn(printWriter);
	}

	/**
	 * Testdo post.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws RepositoryException the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	public void testdoPost() throws IOException, RepositoryException, ReplicationException {
		when(beadLotStructureUpdateService.createBeadlotStructure(resourceResolver, requestObject, session))
				.thenReturn(apiResponse);
		beadLotStructureUpdateServlet.doPost(request, response);
	}

	/**
	 * Test json syntax exception.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws RepositoryException the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	public void testJsonSyntaxException() throws IOException, RepositoryException, ReplicationException {
		when(beadLotStructureUpdateService.createBeadlotStructure(resourceResolver, requestObject, session))
				.thenThrow(new JsonSyntaxException(""));
		beadLotStructureUpdateServlet.doPost(request, response);
	}

	/**
	 * Test json processing exception.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws RepositoryException the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	public void testJsonProcessingException() throws IOException, RepositoryException, ReplicationException {
		when(beadLotStructureUpdateService.createBeadlotStructure(resourceResolver, requestObject, session))
				.thenThrow(new RepositoryException());
		beadLotStructureUpdateServlet.doPost(request, response);
	}
}
