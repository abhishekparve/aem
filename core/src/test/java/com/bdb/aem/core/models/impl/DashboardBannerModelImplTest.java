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
 * The Class DashboardBannerModelImplTest.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class DashboardBannerModelImplTest {

    /**
     * The value page title.
     */
    String VALUE_PAGE_TITLE = "Dashboard Test";

    /**
     * The value page resource type.
     */
    String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

    /**
     * The dashboard banner model test impl.
     */
    @InjectMocks
    private DashboardBannerModelImpl dashboardBannerModel;

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
        context.addModelsForClasses(DashboardBannerModelImplTest.class);
        Map<String, String> pageProperties = new HashMap<>();
        pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
        pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
        pageProperties.put("country", "US");
        pageProperties.put("region", "US");
        pageProperties.put("hybrisSiteId", "bdbUS");

        Map<String, String> grantsProperties = new HashMap<>();
        grantsProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/accountmanagement");

        dashboardBannerModel = new DashboardBannerModelImpl();

        PrivateAccessor.setField(dashboardBannerModel, "bannerTitle", "bannerTitle");

        dashboardBannerModel.init();
    }

    /**
     * Test.
     */
    @Test
    void test() {
        dashboardBannerModel.init();
    }

    /**
     * Gets the user dashboard banner labels.
     *
     * @return the user dashboard banner labels
     */
    @Test
    void getDashboardLabels() {
        assertNotNull(dashboardBannerModel.getTextImageBannerLabels());
    }

}
