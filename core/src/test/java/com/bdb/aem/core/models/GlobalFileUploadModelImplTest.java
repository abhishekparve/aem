package com.bdb.aem.core.models;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
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
 * The Class GlobalFileModelTest.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class GlobalFileUploadModelTest {

    /**
     * The value page path.
     */
    String VALUE_PAGE_PATH = "/content/bdb/testPage";

    /**
     * The value page resource type.
     */
    String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

    /**
     * The value template.
     */
    String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

    /**
     * The value page title.
     */
    String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";

    /**
     * The title.
     */
    String title = "title";

    /**
     * The resource resolver factory.
     */
    @Mock
    ResourceResolverFactory resourceResolverFactory;


    @InjectMocks
    GlobalFileUploadModel globalFileUploadModel;

    private Page page;
    private AemContext context;
    private Resource resourceGlobalFileUpload;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        context.addModelsForClasses(GlobalFileUploadModel.class);
        Map<String, String> pageProperties = new HashMap<>();
        pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
        pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
        page = context.create().page(VALUE_PAGE_PATH, VALUE_TEMPLATE, pageProperties);

        Map<String, String> purchaseProperties = new HashMap<>();
        purchaseProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE,
                "bdb-aem/components/content/globalfileupload/v1/globalfileupload");

        resourceGlobalFileUpload = context.create()
                .resource(page.getContentResource().getPath() + "/root/globalfileupload", purchaseProperties);

        globalFileUploadModel = new GlobalFileUploadModel();

        PrivateAccessor.setField(globalFileUploadModel, "info", "info");
        PrivateAccessor.setField(globalFileUploadModel, "iconSrc", "iconSrc");
        globalFileUploadModel.init();
    }

    /**
     * Test init.
     */
    @Test
    void testInit() {
        globalFileUploadModel.init();
    }



}

