package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class AccountManagementModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccountManagementModelImplTest {

    /** The value page path. */
    String VALUE_PAGE_PATH = "/content/bdb/testPage";

    /** The value page resource type. */
    String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

    /** The value template. */
    String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

    /** The value page title. */
    String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";

    /** The account management model. */
    @InjectMocks
    AccountManagementModelImpl accountManagementModel;

    /** The page. */
    private Page page;
    
    /** The context. */
    private AemContext context;
    
    /** The externalizer. */
    @Mock
    ExternalizerService externalizer;
    
    /** The resource resolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;


    /** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        context.addModelsForClasses(AccountManagementModelImpl.class);
        Map<String, String> pageProperties = new HashMap<>();
        pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
        pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
        page = context.create().page(VALUE_PAGE_PATH, VALUE_TEMPLATE, pageProperties);

        Map<String, String> purchaseProperties = new HashMap<>();
        purchaseProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE,
                "bdb-aem/components/content/accountmanagement/v1/accountmanagement");
        PrivateAccessor.setField(accountManagementModel, "needHelpUrl", "test");
        PrivateAccessor.setField(accountManagementModel, "dashboardLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "ordersLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "ordersApprovalLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "shoppingListsLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "rewardsLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "certificationsLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "paymentMethodsLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "addressesLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "accountSettingsLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "communicationSettingsLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "grantsLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "quotesLabel", "test");
        PrivateAccessor.setField(accountManagementModel, "quoteListLabel", "test");

        lenient().when(externalizer.getFormattedUrl("test", resourceResolver)).thenReturn("value");
        
        accountManagementModel.init();
    }

    /**
     * Test init.
     */
    @Test
    void testInit() {
        accountManagementModel.init();
    }
    
    /**
     * Test get dashboard page navigation labels.
     */
    @Test
    void testGetDashboardPageNavigationLabels() {
        assertNotNull(accountManagementModel.getDashboardPageNavigationLabels());
    }

}

