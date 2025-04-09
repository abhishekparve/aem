
package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

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
 * The Class MyCartGrantsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class MyCartGrantsModelTest {

	/** The aem context. */
	private final AemContext aemContext = new AemContext();

	/** The my cart grants model. */
	@InjectMocks
	MyCartGrantsModel myCartGrantsModel;

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
	
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(MyCartGrantsModel.class);
		pageManager = aemContext.pageManager();
		
		lenient().when(bdbApiEndpointService.applyGrantsOnCartEndpoint()).thenReturn("");
		
		final String[] input2 = new String[] { "myString1", "340336" };
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(input2);
		PrivateAccessor.setField(myCartGrantsModel, "hybrisSiteId", "bdbUS");
	}

	
	
	/**
	 * Test get my cart grant labels.
	 */
	@Test
	void testGetMyCartGrantLabels(){
		assertNotNull(myCartGrantsModel.getLabelJson());
	}

	
	/**
	 * Test get my cart grant config.
	 */
	@Test
	void testGetMyCartGrantConfig(){
		assertNotNull(myCartGrantsModel.getConfigJson());
	}
	
	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	@Test
	void getHybrisSiteId() {
		assertNotNull(myCartGrantsModel.getHybrisSiteId());		
	}

	/**
	 * test init.
	 */
	@Test
	void testInit(){
		myCartGrantsModel.init();
	}

}
