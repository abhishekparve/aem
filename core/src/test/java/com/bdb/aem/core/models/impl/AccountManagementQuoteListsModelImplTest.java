package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class AccountManagementQuoteListsModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccountManagementQuoteListsModelImplTest {
	
	/** The value page title. */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";
	
	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";
	
	/** The quote lists test model. */
	@InjectMocks
	AccountManagementQuoteListsModelImpl quoteListsTestModel;
	
	/** The bdb api endpoint service. */
	@InjectMocks
	BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();
	
	/** The current page. */
	@Mock
	Page currentPage;
	
	/** The context. */
	private AemContext context;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(AccountManagementQuoteListsModelImpl.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("hybrisSiteId", "bdbUS");
		
		Map<String, String> quoteListsProperties = new HashMap<>();
		quoteListsProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/accountmanagement");
		quoteListsTestModel = new AccountManagementQuoteListsModelImpl();
		
		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "createShoppingListEndpoint", "/hybris/{{site}}/createShoppingListEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "getShoppingListDetailsEndpoint", "/hybris/getShoppingListDetailsEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "getShoppingListEndpoint", "/hybris/getShoppingListEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "fileUploadShoppingListEndpoint", "/hybris/fileUploadShoppingListEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "fileUploadShoppingListEntriesEndpoint", "/hybris/fileUploadShoppingListEntriesEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "removeShoppingListEndpoint", "/hybris/removeShoppingListEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "shareShoppingListEndpoint", "/hybris/shareShoppingListEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "removeShoppingListEntriesEndpoint", "/hybris/removeShoppingListEntriesEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "updateShoppingListEntriesEndpoint", "/hybris/updateShoppingListEntriesEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "productAnnouncements", "/hybris/productAnnouncements");
		PrivateAccessor.setField(bdbApiEndpointService, "addAllItemsToCartEndpoint", "/hybris/addAllItemsToCartEndpoint");
		
		PrivateAccessor.setField(quoteListsTestModel, "bdbApiEndpointService", bdbApiEndpointService);
		quoteListsTestModel.init();
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		quoteListsTestModel.init();
	}
	
	/**
	 * Test get quote list labels.
	 */
	@Test
	void testGetQuoteListLabels() {
		assertNotNull(quoteListsTestModel.getQuoteListLabels());
	}

	/**
	 * Test get quote list config.
	 */
	@Test
	void testGetQuoteListConfig() {
		assertNotNull(quoteListsTestModel.getQuoteListConfig());
	}

	/**
	 * Test get quote list config.
	 */
	@Test
	void testGetQuoteConfirmationConfig() {
		assertNotNull(quoteListsTestModel.getQuoteConfirmationConfig());
	}
}
