
package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import com.bdb.aem.core.services.BDBApiEndpointService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.CommonTestConstants;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import static org.junit.jupiter.api.Assertions.*;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * Junit for ExternalizerServiceImpl
 *
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ExternalizerServiceImplTest {

	@InjectMocks
	ExternalizerServiceImpl externalizerServiceImpl;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock Externalizer */
	@Mock
	Externalizer _externalizer;

	/** Mock PageManager */
	@Mock
	PageManager pageManager;

	/** Mock Page object */
	@Mock
	Page page;

	/** Mock Resource */
	@Mock
	Resource resource;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	CommonHelper commonHelper;
	
	/** Runmodes */
	Set<String> runmodes = new HashSet<String>();

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	void setUp() throws Exception {

		commonHelper = mock(CommonHelper.class);
		//bdbApiEndpointService = mock(BDBApiEndpointService.class);
		lenient().when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		lenient().when(_externalizer.authorLink(resourceResolver, "/content/bdb")).thenReturn("/content/bdb");
		
	}
	@Test
	void testUrl() {
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		externalizerServiceImpl.getFormattedUrl("/content/bdb", resourceResolver);
		externalizerServiceImpl.getFormattedUrlForPublish("/content/dam/assets", resourceResolver);
	}
	@Test
	void testUrlMailTo() {
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		externalizerServiceImpl.getFormattedUrl("mailto:", resourceResolver);
		
	}
	@Test
	void testUrlIf() {
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		lenient().when(_externalizer.authorLink(resourceResolver, "/content/bdb")).thenReturn("/content/bdb");
		externalizerServiceImpl.getFormattedUrl("/content/bdb?cache", resourceResolver);
	}
	@Test
	void testUrlElse() {
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		lenient().when(_externalizer.authorLink(resourceResolver, "/{language-country}")).thenReturn("/content/bdb");
		externalizerServiceImpl.getFormattedUrl("/{language-country}", resourceResolver);
	}
	/**
	 * Test url formatter for content pages .
	 */
	@Test
	void testUrlFormatterContent() {

		String url = CommonTestConstants.SAMPLE_CONTENT_PAGE_PATH;
		lenient().when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		lenient().when(pageManager.getPage(url)).thenReturn(page);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		//externalizerServiceImpl.getFormattedUrl("/{language-country}", resourceResolver);
		lenient().when(resourceResolver.getResource("/content/dam/assets")).thenReturn(resource);
		externalizerServiceImpl.getFormattedUrl("/bin", resourceResolver);
		externalizerServiceImpl.getFormattedUrlForPublish("/content/dam/assets", resourceResolver);
		externalizerServiceImpl.externalizedUrl(url, resourceResolver);
		externalizerServiceImpl.externalizedUrlWithoutMapping(url, resourceResolver);
	}

	/** 
	 * Test url formatter dam assets.
	 */
	@Test
	void testUrlFormatterDam() {

		String url = CommonTestConstants.SAMPLE_DAM_PATH;
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("publish");
		when(resourceResolver.getResource(url)).thenReturn(resource);
		externalizerServiceImpl.getFormattedUrl(url, resourceResolver);
		externalizerServiceImpl.getFormattedUrlForPublish("/bin", resourceResolver);
		externalizerServiceImpl.externalizedUrl(url, resourceResolver);
		externalizerServiceImpl.externalizedUrlWithoutMapping(url, resourceResolver);
	}
	
	/** 
	 * Test url formatter dam path when asset does not exist.
	 */
	@Test
	void testUrlFormatterNonExistentDamPath() {

		String url = CommonTestConstants.SAMPLE_DAM_PATH;
		//lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		assertNotNull(externalizerServiceImpl.getFormattedUrl(url, resourceResolver));
		externalizerServiceImpl.getFormattedUrlForPublish("http:", resourceResolver);
	}

	/** 
	 * Test url formatter for external links.
	 */
	@Test
	void testUrlFormatterExternal() {
		assertNotNull(externalizerServiceImpl.getFormattedUrl(CommonTestConstants.SAMPLE_EXTERNAL_URL, resourceResolver));
		externalizerServiceImpl.getFormattedUrlForPublish("/{language-country}", resourceResolver);
	}

	/** 
	 * Test url formatter for external links without http/https.
	 */
	@Test
	void testUrlFormatterExternalWithoutHttp() {
		assertNotNull(externalizerServiceImpl.getFormattedUrl(CommonTestConstants.SAMPLE_EXTERNAL_URL_WITHOUT_HTTP, resourceResolver));
		externalizerServiceImpl.getFormattedUrlForPublish("https/", resourceResolver);
	}
	
	/** 
	 * Test url formatter for external links with http.
	 */
	@Test
	void testUrlFormatterExternalWithHttp() {
		assertNotNull(externalizerServiceImpl.getFormattedUrl(CommonTestConstants.SAMPLE_EXTERNAL_URL_WITH_HTTP, resourceResolver));
	}

	/** 
	 * Test url formatter for unauthored/empty urls.
	 */
	@Test
	void testUrlFormatterNotAuthored() {
		assertNotNull(externalizerServiceImpl.getFormattedUrl("", resourceResolver));
	}
	@Test
	void testUrlFormatterNull() {
		assertNotNull(externalizerServiceImpl.getFormattedUrlForPublish("", resourceResolver));
	}

}
