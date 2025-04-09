package com.bdb.aem.core.models.impl;

import static junit.framework.Assert.assertNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
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
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * JUnit for AccMgmtShoppingListModelImpl.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class AccMgmtShoppingListModelImplTest {

    /** The value page resource type. */
    String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

    /** The value template. */
    String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

    /** The value page title. */
    String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";


    /**
     * Mock AccMgmtShoppingListModelImpl.
     */
    @InjectMocks
    AccMgmtShoppingListModelImpl accMgmtShoppingListModelImpl;

    /** The i 18 n. */
    @Mock
    I18n i18n;


    /** The resource resolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;


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

    /**
     * Sets things up.
     *
     * @throws Exception the exception
     */
	@BeforeEach
	void setUp() throws Exception {
        context.addModelsForClasses(AccountManagementCertificationsModelImpl.class);
        Map<String, String> pageProperties = new HashMap<>();
        pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
        pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
        pageProperties.put("country", "US");
        pageProperties.put("region", "US");
        pageProperties.put("hybrisSiteId", "bdbUS");

        Map<String, String> certificationsProperties = new HashMap<>();
        certificationsProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE,
                "bdb-aem/components/content/accountmanagement/v1/accountmanagement");

        accMgmtShoppingListModelImpl = new AccMgmtShoppingListModelImpl();


        PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
        PrivateAccessor.setField(accMgmtShoppingListModelImpl, "currentPage", currentPage);
        PrivateAccessor.setField(accMgmtShoppingListModelImpl, "request", request);
        PrivateAccessor.setField(accMgmtShoppingListModelImpl, "bdbApiEndpointService", bdbApiEndpointService);
        PrivateAccessor.setField(accMgmtShoppingListModelImpl, "hybrisSiteId", "bdbUS");

	}

	/**
	 * Test method.
	 */
	@Test
	void testInit() {
		accMgmtShoppingListModelImpl.init();
	}
	
	/**
	 * Test shopping list labels.
	 */
	@Test
	void testShoppingListLabels() {
		assertNull(accMgmtShoppingListModelImpl.getShoppingListLabels());
	}

    /**
     * Test method.
     */
    @Test
    void testShoppingListConfigs() {
        assertNull(accMgmtShoppingListModelImpl.getShoppingListConfig());
    }

 
}
