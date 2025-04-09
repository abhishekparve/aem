package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.Cookie;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.services.SSOLoginService;
import com.bdb.aem.core.util.CommonConstants;

/**
 * The Class SSOLoginServletTest.
 */
@ExtendWith(MockitoExtension.class)
class SSOLoginServletTest {

	/** Servlet sso login. */
	@InjectMocks
	SSOLoginServlet sSOLoginServlet;

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

	/** The sSO Login service. */
	@Mock
	SSOLoginService sSOLoginService;
	
	/** The resource resolver factory. */
	@Mock
	@Reference
	transient ResourceResolverFactory resourceResolverFactory;

	/**
	 * Inits the.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void init() throws IOException {
		HashMap<String, String[]> hashMap = new HashMap<>();
		String[] queryParams = { "testValue" };
		hashMap.put("testKey", queryParams);
		String[] queryParams1 = { CommonConstants.STATE + CommonConstants.STATE_CODE_SPLITTER + CommonConstants.STATE + CommonConstants.STATE_CODE_SPLITTER + "bdbUS"};
		hashMap.put(CommonConstants.STATE, queryParams1);
		String[] queryParams2 = { CommonConstants.CODE };
		hashMap.put(CommonConstants.CODE, queryParams2);
		lenient().when(request.getParameterMap()).thenReturn(hashMap);
		lenient().when(sSOLoginService.getRedirectUrl(Mockito.any(), Mockito.any())).thenReturn("l");
		lenient().when(sSOLoginService.isRequiredQueryParamAvailable(Mockito.anyMap())).thenReturn(true);

	}

	/**
	 * Test invalid exception.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testInvalidException() throws IOException {
		Cookie cookie = new Cookie("testKey", "testValue");
		Cookie[] cookieArray = { cookie };
		lenient().when(sSOLoginService.isValidUrl(Mockito.any(), Mockito.any())).thenReturn(true);
		lenient().when(request.getCookies()).thenReturn(cookieArray);
		lenient().when(response.getWriter()).thenReturn(printWriter);
		//sSOLoginServlet.doPost(request, response);
	}

	/**
	 * Test aem internal server error exception.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testAemInternalServerErrorException() throws IOException {
		Cookie cookie = new Cookie(CommonConstants.FE_SSO_COOKIE_NAME, "testValue");
		Cookie[] cookieArray = { cookie };
		lenient().when(sSOLoginService.isValidUrl(Mockito.any(), Mockito.any())).thenReturn(true);
		lenient().when(request.getCookies()).thenReturn(cookieArray);
		//sSOLoginServlet.doPost(request, response);
	}
	
	/**
	 * Test login exception.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testLoginException() throws IOException {
		Cookie cookie = new Cookie(CommonConstants.FE_SSO_COOKIE_NAME, "testValue");
		Cookie[] cookieArray = { cookie };
		lenient().when(sSOLoginService.isValidUrl(Mockito.any(), Mockito.any())).thenReturn(false);
		lenient().when(request.getCookies()).thenReturn(cookieArray);
		//sSOLoginServlet.doPost(request, response);
	}
}
