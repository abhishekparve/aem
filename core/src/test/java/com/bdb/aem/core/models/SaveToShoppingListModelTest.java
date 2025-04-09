
package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
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
import com.day.cq.wcm.api.PageManager;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class SaveToShoppingListModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SaveToShoppingListModelTest {

	/** The aem context. */
	private final AemContext aemContext = new AemContext();

	/** The save to shopping list model. */
	@InjectMocks
	SaveToShoppingListModel saveToShoppingListModel;

	/** Mock SlingHttpServletRequest. */
	@Mock
	SlingHttpServletRequest request;

	/** Mock Common Helper. */
	@Mock
	CommonHelper commonHelper;

	/** Mock resource. */
	@Mock
	Resource mockResource;

	/** Page manager. */
	@Mock
	PageManager pageManager;

	/** Resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock ResourceResolverFactory. */
	@Mock
	ExternalizerService externalizerService;

	/**  Mock RequestPathInfo. */
	@Mock
	RequestPathInfo requestPath;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** Mock BDBApiEndpointService. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	JsonObject saveToShoppingList=new JsonObject();
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(SaveToShoppingListModel.class);
		aemContext.load().json("/com/bdb/aem/core/models/SaveToShoppingListTest.json", "/content");
		mockResource = aemContext.currentResource("/content/savetoshoppinglist");
		pageManager = aemContext.pageManager();
		lenient().when(bdbApiEndpointService.getGetShoppingListEndpoint()).thenReturn("");
		lenient().when(bdbApiEndpointService.getCreateShoppingListEndpoint()).thenReturn("");
		lenient().when(bdbApiEndpointService.getUpdateShoppingListEntriesEndpoint()).thenReturn("");
		lenient().when(bdbApiEndpointService.getCurrentUserIdPlaceholder()).thenReturn("current");
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("");
		lenient().when(bdbApiEndpointService.ssoLoginResponseType()).thenReturn("/test/ssoLoginResponseType");
		lenient().when(bdbApiEndpointService.ssoLoginClientId()).thenReturn("/test/ssoLoginClientId");
		lenient().when(bdbApiEndpointService.ssoLoginPingIdEndpoint()).thenReturn("/test/ssoLoginPingIdEndpoint");
		lenient().when(bdbApiEndpointService.ssoLoginScope()).thenReturn("/test/ssoLoginScope");
		lenient().when(bdbApiEndpointService.ssoCustomerLoginService()).thenReturn("/test/ssoCustomerLoginService");
		lenient().when(bdbApiEndpointService.ssoLoginPingIdDomain()).thenReturn("/test/ssoLoginPingIdDomai");
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("CustomRunMode");
		lenient().when(externalizerService.getFormattedUrl("ssoCustomerLoginService", resourceResolver)).thenReturn("");
		final String[] input2 = new String[] { "myString1", "340336" };
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(input2);
		PrivateAccessor.setField(saveToShoppingListModel, "hybrisSiteId", "bdbUS");
	}

	
	/**
	 * Test get label json.
	 */
	@Test
	void testGetLabelJson(){
		assertNotNull(saveToShoppingListModel.getLabelJson());
	}

	/**
	 * test method to generate config json.
	 */
	@Test
	void testGetters(){
		//saveToShoppingListModel.getConfigJson(saveToShoppingList, resourceResolver);
		saveToShoppingListModel.getQuoteListLabels();
		saveToShoppingListModel.getSaveToListLabels();
		saveToShoppingListModel.getSaveToListConfig();
	}
	
	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	@Test
	void getHybrisSiteId() {
		assertNotNull(saveToShoppingListModel.getHybrisSiteId());		
	}

	/**
	 * test init.
	 */
	@Test
	void testInit(){
		saveToShoppingListModel.init();
	}

}
