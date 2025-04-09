package com.bdb.aem.core.models.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
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

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.util.CommonHelper;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class SearchAdminPageModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SearchAdminPageModelImplTest {

	@InjectMocks
	SearchAdminPageModelImpl searchAdminPageModelImpl;

	@Mock
	BDBSearchEndpointService bdbSearchEndpointService;

	@Mock
	ResourceResolverFactory resolverFactory;

	 /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
    /** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	/** Mock Resource */
	@Mock
	Resource res;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(bdbApiEndpointService.getLanguageSearchDropdownEndpoint()).thenReturn("language");
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(res);
		PrivateAccessor.setField(searchAdminPageModelImpl, "languageList", "languageList");
		PrivateAccessor.setField(searchAdminPageModelImpl, "searchAdminScreenConfig", "searchAdminScreenConfig");
		PrivateAccessor.setField(searchAdminPageModelImpl, "getSynonymResults", "getSynonymResults");
		PrivateAccessor.setField(searchAdminPageModelImpl, "deleteSynonym", "deleteSynonym");
		PrivateAccessor.setField(searchAdminPageModelImpl, "createSynonym", "createSynonym");
		PrivateAccessor.setField(searchAdminPageModelImpl, "getStopWordResults", "getStopWordResults");
		PrivateAccessor.setField(searchAdminPageModelImpl, "createStopWord", "createStopWord");
		PrivateAccessor.setField(searchAdminPageModelImpl, "removeSelectedStopWord", "removeSelectedStopWord");
		PrivateAccessor.setField(searchAdminPageModelImpl, "synonymLabel", "synonymLabel");
		PrivateAccessor.setField(searchAdminPageModelImpl, "stopWordLabel", "stopWordLabel");
		PrivateAccessor.setField(searchAdminPageModelImpl, "refreshSolrCollection", "refreshSolrCollection");

	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		searchAdminPageModelImpl.init();
	}

	/**
	 * Test getters.
	 */
	@Test
	void testAllGetters() {
		assertEquals(searchAdminPageModelImpl.getLanguageList(), "languageList");
		assertEquals(searchAdminPageModelImpl.getSearchAdminScreenConfig(), "searchAdminScreenConfig");
		assertEquals(searchAdminPageModelImpl.getGetSynonymResults(), "getSynonymResults");
		assertEquals(searchAdminPageModelImpl.getDeleteSynonym(), "deleteSynonym");
		assertEquals(searchAdminPageModelImpl.getCreateSynonym(), "createSynonym");
		assertEquals(searchAdminPageModelImpl.getGetStopWordResults(), "getStopWordResults");
		assertEquals(searchAdminPageModelImpl.getCreateStopWord(), "createStopWord");
		assertEquals(searchAdminPageModelImpl.getRemoveSelectedStopWord(), "removeSelectedStopWord");
		assertEquals(searchAdminPageModelImpl.getSynonymLabel(), "synonymLabel");
		assertEquals(searchAdminPageModelImpl.getStopWordLabel(), "stopWordLabel");
		assertEquals(searchAdminPageModelImpl.getRefreshSolrCollection(), "refreshSolrCollection");
		searchAdminPageModelImpl.getStopWord();
		searchAdminPageModelImpl.getSearch();
		searchAdminPageModelImpl.getExisting();
		searchAdminPageModelImpl.getAddNewStopWord();
		searchAdminPageModelImpl.getSave();
		searchAdminPageModelImpl.getManagedMappings();
		searchAdminPageModelImpl.getAddNewSynonym();
		searchAdminPageModelImpl.getTo();
		searchAdminPageModelImpl.getFrom();
		searchAdminPageModelImpl.getDelete();
		searchAdminPageModelImpl.getDeleteQuestion();
		searchAdminPageModelImpl.getDeleteSuccessMsg();
		searchAdminPageModelImpl.getAddedSynonym();
		searchAdminPageModelImpl.getCancel();
		searchAdminPageModelImpl.getSynonymScreenLabels();
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenThrow(LoginException.class);
		searchAdminPageModelImpl.init();
	}

}
