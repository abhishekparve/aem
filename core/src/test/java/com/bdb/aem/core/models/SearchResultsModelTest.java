package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;

import junitx.util.PrivateAccessor;

/**
 * The Class SearchResultsModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class SearchResultsModelTest {

	/** The search results model. */
	@InjectMocks
	SearchResultsModel searchResultsModel;

	/** The bdb search endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The dropdown list options. */
	@Mock
	Resource dropdownListOptions;

	/** The test value. */
	private String TEST_VALUE = "test";

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {

		PrivateAccessor.setField(searchResultsModel, "perPageResults", "5");
		PrivateAccessor.setField(searchResultsModel, "dropdownListOptions", dropdownListOptions);
		PrivateAccessor.setField(searchResultsModel, "facetcategory", dropdownListOptions);
		PrivateAccessor.setField(searchResultsModel, "searchResultsJsonLabels", TEST_VALUE);
		PrivateAccessor.setField(searchResultsModel, "searchResultsJsonConfigs", TEST_VALUE);
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
		Iterator<Resource> listItems = Mockito.mock(Iterator.class);
		Resource resource = Mockito.mock(Resource.class);
		ValueMap vm = Mockito.mock(ValueMap.class);

		when(bdbApiEndpointService.getSearchResultsServletPath()).thenReturn("product/bdb/paath");
		when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("hybrisDomain");
		when(bdbApiEndpointService.getProductAnnouncements()).thenReturn("productaAnnounce");
		when(bdbApiEndpointService.getSearchListEndpoint()).thenReturn("listEndpoint");
		when(dropdownListOptions.listChildren()).thenReturn(listItems);
		when(listItems.hasNext()).thenReturn(true).thenReturn(false);
		when(listItems.next()).thenReturn(resource);
		when(resource.adaptTo(ValueMap.class)).thenReturn(vm);
		when(vm.get("optionLabel", String.class)).thenReturn("optionLabel");
		when(vm.get("optionValue", String.class)).thenReturn("5");
		when(vm.get(CommonConstants.IS_RANGE_SELECTOR, Boolean.class)).thenReturn(true);
		when(vm.get(CommonConstants.IS_RANGE_SELECTOR, Boolean.class)).thenReturn(true);

		searchResultsModel.init();
	}

	/**
	 * Test get methods.
	 */
	@Test
	void testGetMethods() {
		assertEquals(TEST_VALUE, searchResultsModel.getSearchResultsJsonLabels());
		assertEquals(TEST_VALUE, searchResultsModel.getSearchResultsJsonConfigs());
	}

	/**
	 * Test get methods not null.
	 */
	@Test
	void testGetMethodsNotNull() {
		assertNotNull(searchResultsModel.getSearchResultsJsonLabels());
		assertNotNull(searchResultsModel.getSearchResultsJsonConfigs());
	}

}
