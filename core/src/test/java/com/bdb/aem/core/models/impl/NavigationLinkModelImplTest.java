package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.CtaModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class NavigationLinkModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class NavigationLinkModelImplTest {

	/** The navigation test model. */
	@InjectMocks
	NavigationLinkModelImpl navigationTestModel;

	/** The navigation links multifield. */
	private List<Resource> navigationLinksMultifield;

	/** The navigation resource. */
	@Mock
	Resource navigationResource;

	/** The navigation model. */
	@Mock
	CtaModel navigationModel;

	/** The context. */
	private AemContext context;

	/** The label. */
	private final String LABEL = "label";

	/** The path. */
	private final String PATH = "/content/test/navigation-page";

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		List<Resource> navigationLinksMultifield = new ArrayList<>();
		context.addModelsForClasses(NavigationLinkModelImpl.class);
		navigationLinksMultifield.add(navigationResource);
		PrivateAccessor.setField(navigationTestModel, "navigationLinksMultifield", navigationLinksMultifield);

	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		when(navigationResource.adaptTo(CtaModel.class)).thenReturn(navigationModel);
		when(navigationModel.getLabel()).thenReturn(LABEL);
		when(navigationModel.getPath()).thenReturn(PATH);
		navigationTestModel.init();
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(navigationTestModel.getNavigationLinkList());
	}
}
