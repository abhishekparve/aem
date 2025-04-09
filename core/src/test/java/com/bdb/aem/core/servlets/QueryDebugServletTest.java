package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.RequestDispatcher;

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
public class QueryDebugServletTest {
	@InjectMocks
	QueryDebugServlet queryDebugServlet;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	@Mock
	RequestDispatcher requestDispatcher;

	@Test
	public void doPostTest() throws IOException{
		lenient().when(request.getRequestDispatcher("/bin/querybuilder.json")).thenReturn(requestDispatcher);
		queryDebugServlet.doGet(request, response);
	}
}
