package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class BeadlotsGroupModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class BeadlotsGroupModelTest {

	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

	/** The value template. */
	String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

	/** The value page title. */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";

	/** The beadlots group model. */
	@InjectMocks
	BeadlotsGroupModel beadlotsGroupModel;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
	
	/** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;


	/** The current page. */
	@Mock
	Page currentPage;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The bdb api endpoint service. */
	@InjectMocks
	BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();

	/** The page locale. */
	Locale pageLocale = new Locale("en", "US");

	/** The bundle. */
	@Mock
	ResourceBundle bundle;

	/** The context. */
	private AemContext context;
	
	/** The resource. */
	@Mock
	Resource resource;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(BeadlotsGroupModel.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("country", "US");
		pageProperties.put("region", "US");
		pageProperties.put("hybrisSiteId", "bdbUS");

		Map<String, String> beadlotsProperties = new HashMap<>();
		beadlotsProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE,
				"bdb-aem/components/content/beadlotscomponent/v1/beadlotscomponent");

		beadlotsGroupModel = new BeadlotsGroupModel();

		PrivateAccessor.setField(beadlotsGroupModel, "information", "information");
		PrivateAccessor.setField(beadlotsGroupModel, "installationInstructionsLabel", "installationInstructionsLabel");
		PrivateAccessor.setField(beadlotsGroupModel, "installationInstructionsLink", "installationInstructionsLink");
		PrivateAccessor.setField(beadlotsGroupModel, "updaterFileLabel", "updaterFileLabel");
		PrivateAccessor.setField(beadlotsGroupModel, "updaterFileLink", "updaterFileLink");
		List<Resource> multiplePartNumbers = new ArrayList<Resource>();
		multiplePartNumbers.add(resource);
		PrivateAccessor.setField(beadlotsGroupModel, "multiplePartNumbers", multiplePartNumbers);
		PrivateAccessor.setField(beadlotsGroupModel, "externalizerService", externalizerService);
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
        lenient().when(externalizerService.getFormattedUrl("installationInstructionsLink", resourceResolver)).thenReturn("/content/dam/image.png");
        assertNotNull(beadlotsGroupModel.getInstallationInstructionsLink());
        beadlotsGroupModel.init();
	}

	/**
	 * Test shopping list labels.
	 */
	@Test
	void testShoppingListLabels() {
		assertNotNull(beadlotsGroupModel.getInformation());
		assertNotNull(beadlotsGroupModel.getUpdaterFileLabel());
		assertNotNull(beadlotsGroupModel.getUpdaterFileLink());
		beadlotsGroupModel.getAuthoredPartNumbersList();
	}

	/**
	 * Test shopping list configs.
	 */
	@Test
	void testShoppingListConfigs() {
		assertNotNull(beadlotsGroupModel.getInstallationInstructionsLabel());
		assertNotNull(beadlotsGroupModel.getInstallationInstructionsLink());
	}

}
