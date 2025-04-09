package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * Unit Test case for {@link HeaderCartFetchModelImpl }
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class HeaderCartFetchModelImplTest {
    @InjectMocks
    BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();
    @InjectMocks
    HeaderCartFetchModelImpl headerCartFetchModel;
    @Mock
    ExternalizerService externalizerService;
    @Mock
    InheritanceValueMap pageProperties;
    @Mock
    CommonHelper commonHelper;
    @Mock
    Page currentPage;
    @Mock
    ResourceResolver resourceResolver;
    private String hybrisSiteId = "abcd";
    private final AemContext aemContext = new AemContext();

    /**
     * Statements to be Prepared before Testing
     * 
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        aemContext.addModelsForClasses(HeaderCartFetchModelImpl.class);
        PrivateAccessor.setField(headerCartFetchModel, "externalizerService", externalizerService);
        PrivateAccessor.setField(headerCartFetchModel, "bdbApiEndpointService", bdbApiEndpointService);
        PrivateAccessor.setField(headerCartFetchModel, "resourceResolver", resourceResolver);
        PrivateAccessor.setField(headerCartFetchModel, "pageProperties", pageProperties);
        PrivateAccessor.setField(headerCartFetchModel, "currentPage", currentPage);
        PrivateAccessor.setField(headerCartFetchModel, "hybrisSiteId", hybrisSiteId);
        PrivateAccessor.setField(headerCartFetchModel, "miniQuoteEntriesConfig", "miniQuoteEntriesConfig");
        PrivateAccessor.setField(headerCartFetchModel, "translationUrls", "translationUrls");

    }

    @Test
    /**
     * Testing Getters and Init Method
     */
    void testInit() {
        headerCartFetchModel.init();
        headerCartFetchModel.getCartConfig();
        headerCartFetchModel.getCartCountConfig();
        headerCartFetchModel.getCartEntriesConfig();
        headerCartFetchModel.getValidateMyCartConfig();
        headerCartFetchModel.getClearCartConfig();
        headerCartFetchModel.getMiniCartLabels();
        headerCartFetchModel.getTranslationUrls();
        headerCartFetchModel.getHybrisSiteId();
        headerCartFetchModel.getMiniQuoteLabels();
        headerCartFetchModel.getMiniQuoteCountConfig();
        assertNotNull(headerCartFetchModel.getMiniQuoteEntriesConfig());

    }
}
