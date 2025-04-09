package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * Junit for Breadcrumb Model.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BreadcrumbModelTest {
	/** The aem context. */
	private final AemContext aemContext = new AemContext();

	/** The breadcrumb model impl. */
	@InjectMocks
	BreadcrumbModel breadcrumbModel;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The page manager. */
	@Mock
	PageManager pageManager;
	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The properties. */
	@Mock
	ValueMap properties;

	/** The current page. */
	@Mock
	Page currentPage;
	@Mock
	SlingHttpServletRequest request;
	@Mock
	Object obj;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(BreadcrumbModel.class);
		pageManager = aemContext.pageManager();
		PrivateAccessor.setField(breadcrumbModel, "breadCrumbLevel", "3");
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		lenient().when(request.getAttribute("productName")).thenReturn(obj);
		lenient().when(obj.toString()).thenReturn("pdpProductName");
		lenient().when(currentPage.getAbsoluteParent(3)).thenReturn(currentPage);
		lenient().when(currentPage.getDepth()).thenReturn(20);
		lenient().when(currentPage.getProperties()).thenReturn(properties);
		lenient().when(currentPage.getParent()).thenReturn(currentPage);
		lenient().when(currentPage.getNavigationTitle()).thenReturn("NavigationTitle");
		lenient().when(currentPage.getPageTitle()).thenReturn("PageTitle");
		breadcrumbModel.getPdpProductName();
		breadcrumbModel.init();
	}

	/**
	 * Test get breadcrumb map.
	 */
	@Test
	void testGetBreadcrumbMap() {
		breadcrumbModel.getBreadcrumbMap();
		breadcrumbModel.getBreadcrumbMobileMap();
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	@Test
	void getMethod() {
		breadcrumbModel.getBreadCrumbLevel();
	}

}
