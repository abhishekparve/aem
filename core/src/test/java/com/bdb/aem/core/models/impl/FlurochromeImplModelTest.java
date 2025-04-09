package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.impl.FluorochromesImpl;

import junitx.util.PrivateAccessor;

/**
 * The Class FlurochromeModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class FlurochromeImplModelTest {
	
	/** The flurochrome model. */
	@InjectMocks
	FluorochromesImpl fluorochromesImplTestModel;
	
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The test value. */
	private String TEST_VALUE = "test";

	/** The test Array. */
	private String[] TEST_ARRAY = {"test"};

	/**
	 * Setup.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setup() throws Exception {
		PrivateAccessor.setField(fluorochromesImplTestModel, "searchTypePlaceholder", TEST_VALUE);
		PrivateAccessor.setField(fluorochromesImplTestModel, "searchInputPlaceholder", TEST_VALUE);
		PrivateAccessor.setField(fluorochromesImplTestModel, "defaultFluorochromes", TEST_ARRAY);
		PrivateAccessor.setField(fluorochromesImplTestModel, "deleteFluorIcon", TEST_VALUE);
		PrivateAccessor.setField(fluorochromesImplTestModel, "deleteFluorIconAlt", TEST_VALUE);
		PrivateAccessor.setField(fluorochromesImplTestModel, "infoFluorIcon", TEST_VALUE);
		PrivateAccessor.setField(fluorochromesImplTestModel, "infoFluorIconAlt", TEST_VALUE);
		PrivateAccessor.setField(fluorochromesImplTestModel, "searchFluorIcon", TEST_VALUE);
		PrivateAccessor.setField(fluorochromesImplTestModel, "searchFluorIconAlt", TEST_VALUE);
		
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(fluorochromesImplTestModel.getSearchTypePlaceholder());
		assertNotNull(fluorochromesImplTestModel.getSearchInputPlaceholder());
		assertNotNull(fluorochromesImplTestModel.getDefaultFluorochromes());
		assertNotNull(fluorochromesImplTestModel.getDeleteFluorIcon());
		assertNotNull(fluorochromesImplTestModel.getDeleteFluorIconAlt());
		assertNotNull(fluorochromesImplTestModel.getInfoFluorIconAlt());
		assertNotNull(fluorochromesImplTestModel.getSearchFluorIcon());
		assertNotNull(fluorochromesImplTestModel.getSearchFluorIconAlt());
		assertNotNull(fluorochromesImplTestModel.getInfoFluorIcon());
		
	}
}