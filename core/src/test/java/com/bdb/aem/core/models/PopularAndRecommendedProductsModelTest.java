package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.MonetateEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.wcm.api.Page;

import junitx.util.PrivateAccessor;

/**
 * The Class PopularAndRecommendedProductsModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class PopularAndRecommendedProductsModelTest {

	/** The popular and recommended products model. */
	@InjectMocks
	PopularAndRecommendedProductsModel popularAndRecommendedProductsModel;

	/** The bdb search endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	@Mock
	MonetateEndpointService monetateEndpointService;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	@Mock
	SlingHttpServletRequest request;

	/** The dropdown list options. */
	@Mock
	Page currentPage;

	/** The test value. */
	private String TEST_VALUE = "test";

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {

		PrivateAccessor.setField(popularAndRecommendedProductsModel, "categorySelect", TEST_VALUE);
		PrivateAccessor.setField(popularAndRecommendedProductsModel, "productLabelJson", TEST_VALUE);
		PrivateAccessor.setField(popularAndRecommendedProductsModel, "alsoViewedConfigDesktop", TEST_VALUE);
		PrivateAccessor.setField(popularAndRecommendedProductsModel, "alsoViewedLables", TEST_VALUE);
		PrivateAccessor.setField(popularAndRecommendedProductsModel, "alsoViewedConfig", TEST_VALUE);
		PrivateAccessor.setField(popularAndRecommendedProductsModel, "recommendedConfigJson", TEST_VALUE);
		PrivateAccessor.setField(popularAndRecommendedProductsModel, "popularProductsLabelsJson", TEST_VALUE);
		PrivateAccessor.setField(popularAndRecommendedProductsModel, "categoryTitle", TEST_VALUE);
		PrivateAccessor.setField(popularAndRecommendedProductsModel, "catalogNo", TEST_VALUE);
		PrivateAccessor.setField(popularAndRecommendedProductsModel, "alsoKnownAs", TEST_VALUE);

	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		popularAndRecommendedProductsModel.init();
	}

	/**
	 * Test all get methods.
	 */
	@Test
	void testAllGetMethods() {
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getProductLabelJson());
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getCategorySelect());
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getCategoryTitle());
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getPopularProductsLabelsJson());
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getRecommendedConfigJson());
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getAlsoViewedConfig());
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getAlsoViewedLables());
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getAlsoViewedConfigDesktop());
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getCatalogNo());
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getAlsoKnownAs());
	}

	/**
	 * Test all get methods not null.
	 */
	@Test
	void testAllGetMethodsNotNull() {
		assertNotNull(popularAndRecommendedProductsModel.getProductLabelJson());
		assertNotNull(popularAndRecommendedProductsModel.getCategorySelect());
		assertNotNull(popularAndRecommendedProductsModel.getCategoryTitle());
		assertNotNull(popularAndRecommendedProductsModel.getPopularProductsLabelsJson());
		assertNotNull(popularAndRecommendedProductsModel.getRecommendedConfigJson());
		assertNotNull(popularAndRecommendedProductsModel.getAlsoViewedConfig());
		assertNotNull(popularAndRecommendedProductsModel.getAlsoViewedLables());
		assertNotNull(popularAndRecommendedProductsModel.getAlsoViewedConfigDesktop());
		assertNotNull(popularAndRecommendedProductsModel.getCatalogNo());
		assertNotNull(popularAndRecommendedProductsModel.getAlsoKnownAs());

	}

	@Test
	void testGetCommonConfig() {
		when(monetateEndpointService.getMonetateProductsEndpoint()).thenReturn(TEST_VALUE);
		assertEquals(TEST_VALUE, popularAndRecommendedProductsModel.getCommonConfig());
	}

}
