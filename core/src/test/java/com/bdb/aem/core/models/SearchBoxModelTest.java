package com.bdb.aem.core.models;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;

import junitx.util.PrivateAccessor;

/**
 * The Class SearchBoxModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class SearchBoxModelTest {

	/** The search box model. */
	@InjectMocks
	SearchBoxModel searchBoxModel;

	/** The externalizer. */
	@Mock
	ExternalizerService externalizer;

	/** The bdb search endpoint service. */
	@Mock
	BDBSearchEndpointService bdbSearchEndpointService;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The test value. */
	private String TEST_VALUE = "test";

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {

		PrivateAccessor.setField(searchBoxModel, "keywordsDataPagePath", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "noResult", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "searchIconMobile", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "searchIcon", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "closeIcon", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "searchPlaceholder", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "searchAltText", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "suggestiveSearchTitle", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "closeAltText", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "popularSearchTitle", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "recentSearchTitle", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "maxResultsToDisplay", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "inKeyword", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "searchContainerLabels", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "searchContainerConfig", TEST_VALUE);
		PrivateAccessor.setField(searchBoxModel, "popularSearchConfig", TEST_VALUE);
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		Map<String, Object> writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				"writeService");
		searchBoxModel.init();
	}

	/**
	 * Test get methodes.
	 */
	@Test
	void testGetMethodes() {
		assertEquals(TEST_VALUE, searchBoxModel.getSearchContainerLabels());
		assertEquals(TEST_VALUE, searchBoxModel.getSearchContainerConfig());
		assertEquals(TEST_VALUE, searchBoxModel.getPopularSearchConfig());
	}

	/**
	 * Test get methodes not null.
	 */
	@Test
	void testGetMethodesNotNull() {
		assertNotNull(searchBoxModel.getSearchContainerLabels());
		assertNotNull(searchBoxModel.getSearchContainerConfig());
		assertNotNull(searchBoxModel.getPopularSearchConfig());
	}

}
