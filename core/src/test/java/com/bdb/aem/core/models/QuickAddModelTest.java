
package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.PageManager;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class QuickAddModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class QuickAddModelTest {

	/** The aem context. */
	private final AemContext aemContext = new AemContext();

	
	/** The quick add model. */
	@InjectMocks
	QuickAddModel quickAddModel;

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

	/** Mock BDBApiEndpointService. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The page properties. */
	@Mock
	InheritanceValueMap pageProperties;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(QuickAddModel.class);
		pageManager = aemContext.pageManager();
		
		lenient().when(bdbApiEndpointService.getQuickAddBulkEntryEndpoint()).thenReturn("");
		lenient().when(bdbApiEndpointService.getQuickAddBulkUploadEndpoint()).thenReturn("");
		
		lenient().when(externalizerService.getFormattedUrl("iconSrc", resourceResolver)).thenReturn("/content/dam/bdb/upload-icon.svg");
		lenient().when(externalizerService.getFormattedUrl("fileIconSrc", resourceResolver)).thenReturn("/content/dam/bdb/file-icon.svg");
		lenient().when(externalizerService.getFormattedUrl("closeIcon", resourceResolver)).thenReturn("/content/dam/bdb/close-icon.svg");
		lenient().when(externalizerService.getFormattedUrl("downloadUrl", resourceResolver)).thenReturn("/resources/documents/BulkUpload_template.xls");
		
		lenient().when(pageProperties.getInherited(CommonConstants.ENABLE_ADD_TO_QUOTE,Boolean.FALSE)).thenReturn(true);
		final String[] input2 = new String[] { "myString1", "340336" };
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(input2);
		PrivateAccessor.setField(quickAddModel, "hybrisSiteId", "bdbUS");
	}

	
	/**
	 * Test get quick add labels.
	 */
	@Test
	void testGetQuickAddLabels(){
		assertNotNull(quickAddModel.getLabelJson());
	}

	
	/**
	 * Test get quick add config.
	 */
	@Test
	void testGetQuickAddConfig(){
		assertNotNull(quickAddModel.getConfigJson());
	}
	
	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	@Test
	void getHybrisSiteId() {
		assertNotNull(quickAddModel.getHybrisSiteId());		
	}

	/**
	 * test init.
	 */
	@Test
	void testInit(){
		quickAddModel.init();
	}
}
