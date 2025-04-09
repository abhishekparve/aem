package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FACListNavigationTest {

	@InjectMocks
	FACListNavigation facListNavigation;
	/** Resource Resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	@Mock
	Resource resource;
	@Mock
	ExternalizerService externalizer;

	@Test
	void testAllGetters() throws Exception {
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		PrivateAccessor.setField(facListNavigation, "label", "label");
		PrivateAccessor.setField(facListNavigation, "path", "path");
		facListNavigation.getLabel();
		facListNavigation.getPath();
	}

	@Test
	void testAllGettersNull() {
		facListNavigation.getLabel();
		facListNavigation.getPath();
	}

}