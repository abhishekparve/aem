package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class AccMgmtDashboardModelImplTest.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class AccMgmtDashboardModelImplTest {

    /**
     * The value page title.
     */
    String VALUE_PAGE_TITLE = "Account Dashboard Test";

    /**
     * The value page resource type.
     */
    String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

    /**
     * The current page.
     */
    @Mock
    Page currentPage;

    /**
     * The request.
     */
    @Mock
    SlingHttpServletRequest request;

    /**
     * The bdb api endpoint service.
     */
    @InjectMocks
    BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();

    /**
     * The dashboard model test impl.
     */
    @InjectMocks
    private AccMgmtDashboardModelImpl accMgmtDashboardModel;

    /**
     * The context.
     */
    private AemContext context;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        context.addModelsForClasses(AccountManagementGrantsModelImplTest.class);
        Map<String, String> pageProperties = new HashMap<>();
        pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
        pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
        pageProperties.put("country", "US");
        pageProperties.put("region", "US");
        pageProperties.put("hybrisSiteId", "bdbUS");

        Map<String, String> grantsProperties = new HashMap<>();
        grantsProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/accountmanagement");

        accMgmtDashboardModel = new AccMgmtDashboardModelImpl();

        PrivateAccessor.setField(accMgmtDashboardModel, "dashboardHeaderLabel", "dashboardHeaderLabel");

        PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
        PrivateAccessor.setField(bdbApiEndpointService, "getGrantsForCustomerEndpoint", "/occ/{{site}}/getGrantsForCustomer");
        PrivateAccessor.setField(bdbApiEndpointService, "orderHistoryForGrantsEndpoint", "/occ/{{site}}/orderHistoryForGrants");
        PrivateAccessor.setField(accMgmtDashboardModel, "currentPage", currentPage);
        PrivateAccessor.setField(accMgmtDashboardModel, "bdbApiEndpointService", bdbApiEndpointService);
        accMgmtDashboardModel.init();
        PrivateAccessor.setField(accMgmtDashboardModel, "hybrisSiteId", "bdbUS");
    }

    /**
     * Test.
     */
    @Test
    void test() {
        accMgmtDashboardModel.init();
    }

    /**
     * Gets the user dashboard labels.
     *
     * @return the user dashboard labels
     */
    @Test
    void getDashboardLabels() {
        assertNotNull(accMgmtDashboardModel.getDashboardLabels());
    }

    /**
     * Gets the user dashboard config.
     *
     * @return the user dashboard config
     */
    @Test
    void getDashboardConfig() {
        assertNotNull(accMgmtDashboardModel.getDashboardConfig());
    }

}
