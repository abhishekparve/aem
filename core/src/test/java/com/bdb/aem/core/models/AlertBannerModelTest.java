package com.bdb.aem.core.models;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

/**
 * Junit for Alert Banner Model
 *
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class AlertBannerModelTest {

    private final AemContext aemContext = new AemContext();

    /** Mock ResourceResolverFactory. */
    @Mock
    ExternalizerService externalizerService;

    /** Resourceresolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /** The ComponentContext context **/
    @Mock
    ComponentContext context;

    @InjectMocks
    AlertBannerModel alertBannerModel;


    @BeforeEach
    void setUp() throws Exception {
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        PrivateAccessor.setField(alertBannerModel, "alertIcon", "alertIcon");
		PrivateAccessor.setField(alertBannerModel, "alertInfoLink", "alertInfoLink");
		PrivateAccessor.setField(alertBannerModel, "alertIconOptional", "alertIconOptional");
		PrivateAccessor.setField(alertBannerModel, "alertInfoLinkOptional", "alertInfoLinkOptional");
    }

    @Test
    void testGeAlertIconPath(){
        lenient().when(externalizerService.getFormattedUrl("alertIcon", resourceResolver)).thenReturn("www.google.com");
        assertNotNull(alertBannerModel.getAlertIcon());
    }
    
    @Test
    void testGetAlertInfoLink(){
        lenient().when(externalizerService.getFormattedUrl("alertInfoLink", resourceResolver)).thenReturn("www.google.com");
        assertNotNull(alertBannerModel.getAlertInfoLink());
    }
    
    @Test
    void testGetAlertIconOptional(){
        lenient().when(externalizerService.getFormattedUrl("alertIconOptional", resourceResolver)).thenReturn("www.google.com");
        assertNotNull(alertBannerModel.getAlertIconOptional());
    }
    
    @Test
    void testGetAlertInfoLinkOptional(){
        lenient().when(externalizerService.getFormattedUrl("alertInfoLinkOptional", resourceResolver)).thenReturn("www.google.com");
        assertNotNull(alertBannerModel.getAlertInfoLinkOptional());
    }
    
    /**
     * Test init.
     */
    @Test
    void testInit(){
        alertBannerModel.init();
    }
}
