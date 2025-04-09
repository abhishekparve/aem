package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BeadlotsMasterModelTest {
	@InjectMocks
	BeadlotsMasterModel beadlotsMasterModel;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	/** The BDB Api Endpoint Service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;;

	/** The ComponentContext context *. */
	@Mock
	ComponentContext context;
	@Mock
	PageManager pageManager;
	/** The current page. */
	@Mock
	Page currentPage;

	/** The resource. */
	@Mock
	Resource resource;

	/** The aem context. */
	private AemContext aemContext;

	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(BeadlotsMasterModel.class);
		pageManager = aemContext.pageManager();
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(currentPage.getPath()).thenReturn("/content/");
		
		PrivateAccessor.setField(beadlotsMasterModel, "beadlotsJson","beadlotsJson");
		PrivateAccessor.setField(beadlotsMasterModel, "beadlotsConfig","beadlotsConfig");
		PrivateAccessor.setField(beadlotsMasterModel, "beadlotsMasterJson","beadlotsMasterJson");
	}

	@Test
	void testInit() {
		
		lenient().when(bdbApiEndpointService.getBeadlotsFileDownloadServletPath()).thenReturn("beadlotsConfig");
		beadlotsMasterModel.init();
	}

	@Test
	void testgetViewTableLabel() {
		beadlotsMasterModel.getBeadlotsJson();
	}

	@Test
	void testgetHideTableLabel() {
		beadlotsMasterModel.getBeadlotsConfig();
	}
	
	@Test
	void testgetBeadlotsMasterJson() {
		beadlotsMasterModel.getBeadlotsMasterJson();
	}
}
