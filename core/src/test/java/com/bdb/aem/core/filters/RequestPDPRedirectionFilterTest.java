package com.bdb.aem.core.filters;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.servlet.MockRequestPathInfo;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.Externalizer;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class RequestPDPRedirectionFilterTest {
	// private RequestPDPRedirectionFilter fixture = new
	// RequestPDPRedirectionFilter();

	// private TestLogger logger =
	// TestLoggerFactory.getTestLogger(fixture.getClass());
	@InjectMocks
	RequestPDPRedirectionFilter requestPDPRedirectionFilter;

	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
	@Mock
	ResourceResolverFactory resolverFactory;
	@Mock
	Externalizer externalizer;
	@Mock
	ServletRequest request;
	@Mock
	ServletResponse response;

	@Mock
	ResourceResolver resourceResolver;
	@Mock
	Resource resource;
	@Mock
	Cookie geoLoctionCookie, userCookie;
	@Mock
	Resource countryLanguageMapping;
	@Mock
	Iterator<Resource> items;
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	@BeforeEach
	void setup() throws LoginException {
		TestLoggerFactory.clear();
		lenient().when(bdbApiEndpointService.countryLanguageMapping()).thenReturn("/etc/acs-commons/lists/bdb/country-language-mapping");
		lenient().when(resourceResolver.getResource("/etc/acs-commons/lists/bdb/country-language-mapping/jcr:content/list")).thenReturn(countryLanguageMapping);
		lenient().when(countryLanguageMapping.listChildren()).thenReturn(items);
		lenient().when(items.hasNext()).thenReturn(true,false);
		lenient().when(items.next()).thenReturn(null);
	}

	@Test
	public void testDoFilter() throws IOException, ServletException, LoginException {

		SlingHttpServletRequest request = mock(SlingHttpServletRequest.class);
		SlingHttpServletResponse response = mock(SlingHttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(request.getCookie(Mockito.anyString())).thenReturn(geoLoctionCookie);
		lenient().when(request.getResource()).thenReturn(resource);
		lenient().when(resource.getPath()).thenReturn("/p/content");
		requestPDPRedirectionFilter.doFilter(request, response, filterChain);
	}

	@Test
	public void testDoFiltergeoLoctionCookie() throws IOException, ServletException, LoginException {
		SlingHttpServletRequest request = mock(SlingHttpServletRequest.class);
		SlingHttpServletResponse response = mock(SlingHttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(request.getResource()).thenReturn(resource);
		lenient().when(request.getCookie("User-geolocation")).thenReturn(geoLoctionCookie);
		lenient().when(resource.getPath()).thenReturn("/p/content");
		requestPDPRedirectionFilter.doFilter(request, response, filterChain);
	}

	@Test
	public void testDoFilterSiteLocale() throws IOException, ServletException, LoginException {
		SlingHttpServletRequest request = mock(SlingHttpServletRequest.class);
		SlingHttpServletResponse response = mock(SlingHttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(request.getResource()).thenReturn(resource);
		lenient().when(request.getCookie("site_locale")).thenReturn(userCookie);
		lenient().when(resource.getPath()).thenReturn("/p/content");
		requestPDPRedirectionFilter.doFilter(request, response, filterChain);
	}

	@Test
	public void testDoFilterCookieNull() throws IOException, ServletException, LoginException {
		SlingHttpServletRequest request = mock(SlingHttpServletRequest.class);
		SlingHttpServletResponse response = mock(SlingHttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(request.getResource()).thenReturn(resource);
		lenient().when(request.getHeader("User-Geolocation")).thenReturn("userHeader_user");
		// lenient().when(request.getCookie("site_locale")).thenReturn(userCookie);
		lenient().when(resource.getPath()).thenReturn("/p/content");
		requestPDPRedirectionFilter.doFilter(request, response, filterChain);
	}

	@Test
	public void testDoFilterNoCookie() throws IOException, ServletException, LoginException {
		SlingHttpServletRequest request = mock(SlingHttpServletRequest.class);
		SlingHttpServletResponse response = mock(SlingHttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(request.getResource()).thenReturn(resource);
		lenient().when(resource.getPath()).thenReturn("/p/content");
		requestPDPRedirectionFilter.doFilter(request, response, filterChain);
	}
}
