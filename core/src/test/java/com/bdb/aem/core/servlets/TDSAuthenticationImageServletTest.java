
package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;

import java.io.IOException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TDSAuthenticationImageServletTest {

	@InjectMocks
	TDSAuthenticatedImageServlet tdsAuthenticatedImageServlet;
	
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	@Mock
	SlingHttpServletRequest request;

	@Mock
	SlingHttpServletResponse response;

	@Test
	public void testImageServlet() throws IOException {
		
		lenient().when(request.getParameter("imagePath")).thenReturn("/path/to/image");
		lenient().when(bdbApiEndpointService.getProdUserName()).thenReturn("username");
		lenient().when(bdbApiEndpointService.getProdUserPassword()).thenReturn("password");
		
		lenient().when(bdbApiEndpointService.getSocketTimeoutConfig()).thenReturn(1000);
		lenient().when(bdbApiEndpointService.getRequestTimeoutConfig()).thenReturn(1000);
		lenient().when(bdbApiEndpointService.getConnectTimeoutConfig()).thenReturn(1000);
		
		tdsAuthenticatedImageServlet.doGet(request, response);
		
	}

}