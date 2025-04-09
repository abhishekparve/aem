package com.bdb.aem.core.util;

import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// TODO: Auto-generated Javadoc
/**
 * The Class UrlHandlerTest.
 */
@ExtendWith({ MockitoExtension.class })
class UrlHandlerTest {

	/** The url handler. */
	@InjectMocks
	UrlHandler urlHandler;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/**
	 * Test get modified url.
	 */
	@Test
	void testGetModifiedUrl() {
		String url = "contentType";
		urlHandler.getModifiedUrl(url, resourceResolver);
	}

	/**
	 * Test get modified url if.
	 */
	@Test
	void testGetModifiedUrlIf() {
		String url = "/contentType";
		when(resourceResolver.map(url)).thenReturn("contentType");
		urlHandler.getModifiedUrl(url, resourceResolver);
	}

}
