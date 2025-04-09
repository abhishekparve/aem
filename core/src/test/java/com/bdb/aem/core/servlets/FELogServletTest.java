package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBSearchEndpointService;

@ExtendWith({ MockitoExtension.class })
public class FELogServletTest {
	@InjectMocks
	FELogServlet FELogServlet;
	
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

	@Test
	public void doPostTest() throws IOException {
		lenient().when(request.getReader()).thenReturn(reader);
		when(response.getWriter()).thenReturn(printWriter);
		FELogServlet.doPost(request, response);
	}
}
