package com.bdb.aem.core.services.tools.impl;

import static org.mockito.Mockito.lenient;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.FACReportsModel;
import com.bdb.aem.core.models.FACSelectListModel;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FACSelectListServiceImplTest {
	@InjectMocks
	FACSelectListServiceImpl facSelectListServiceImpl;

	@Mock
	FACSelectListModel slingModel;
	@Mock
	FACReportsModel FACReportsModel;
	/** Resourceresolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	@Mock
	Resource resource;
	@Mock
	Page page;
	@Mock
	Iterator<Page> pItr;
	@Mock
	Iterator<Resource> rItr;

	@BeforeEach
	void setUp() throws Exception {
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		lenient().when(slingModel.getResolver()).thenReturn(resourceResolver);
		lenient().when(slingModel.getCurrentPage()).thenReturn(page);
		lenient().when(page.listChildren()).thenReturn(pItr);
		lenient().when(pItr.hasNext()).thenReturn(true, false);
		lenient().when(pItr.next()).thenReturn(page);
		lenient().when(page.getPath()).thenReturn("/content/bdb");
		lenient().when(resource.listChildren()).thenReturn(rItr);
		lenient().when(rItr.hasNext()).thenReturn(true, false);
		lenient().when(rItr.next()).thenReturn(resource);
	}

	@Test
	void test() {
		facSelectListServiceImpl.updateSlingModel(slingModel);
	}
}
