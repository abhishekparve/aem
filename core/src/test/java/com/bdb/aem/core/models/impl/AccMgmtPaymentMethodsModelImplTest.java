package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
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
 * The Class AccMgmtPaymentMethodsModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccMgmtPaymentMethodsModelImplTest {

    /** The value page title. */
    String VALUE_PAGE_TITLE = "Payment Method Test";

    /** The value page resource type. */
    String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

    /** The current page. */
    @Mock
    Page currentPage;

    /** The request. */
    @Mock
    SlingHttpServletRequest request;

    /** The bdb api endpoint service. */
    @InjectMocks
    BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();

    /** The grants model test impl. */
    @InjectMocks
    private AccMgmtPaymentMethodsModelImpl accMgmtPaymentMethodsModel;

    /** The context. */
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

        accMgmtPaymentMethodsModel = new AccMgmtPaymentMethodsModelImpl();

        PrivateAccessor.setField(accMgmtPaymentMethodsModel, "paymentMethodsHeader", "paymentMethodsHeader");

        PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
        PrivateAccessor.setField(bdbApiEndpointService, "getGrantsForCustomerEndpoint","/occ/{{site}}/getGrantsForCustomer");
        PrivateAccessor.setField(bdbApiEndpointService, "orderHistoryForGrantsEndpoint","/occ/{{site}}/orderHistoryForGrants");
        PrivateAccessor.setField(accMgmtPaymentMethodsModel, "currentPage", currentPage);
        PrivateAccessor.setField(accMgmtPaymentMethodsModel, "bdbApiEndpointService", bdbApiEndpointService);
        accMgmtPaymentMethodsModel.init();
        PrivateAccessor.setField(accMgmtPaymentMethodsModel, "hybrisSiteId", "bdbUS");
    }

    /**
     * Test.
     */
    @Test
    void test() {
        accMgmtPaymentMethodsModel.init();
    }

    /**
     * Gets the user grants labels.
     *
     * @return the user grants labels
     */
    @Test
    void getPaymentsMethodLabels() {
        assertNotNull(accMgmtPaymentMethodsModel.getPaymentsMethodsLabels());
    }

    /**
     * Gets the user grants config.
     *
     * @return the user grants config
     */
    @Test
    void getPaymentsMethodConfig() {
        assertNotNull(accMgmtPaymentMethodsModel.getPaymentsMethodsConfig());
    }

}
