package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.Date;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class PromoGridDetailsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class PromoGridDetailsModelTest {

    /** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /** The context. */
    @Mock
    ComponentContext context;

    /** The promo grid details model. */
    @InjectMocks
    PromoGridDetailsModel promoGridDetailsModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        PrivateAccessor.setField(promoGridDetailsModel,"promoGridExpirationDate",new Date());
        PrivateAccessor.setField(promoGridDetailsModel, "promoGridImage", "promoGridImage");
		PrivateAccessor.setField(promoGridDetailsModel, "promoGridAltText", "promoGridAltText");
		PrivateAccessor.setField(promoGridDetailsModel, "promoGridTitle", "promoGridTitle");
		PrivateAccessor.setField(promoGridDetailsModel, "promoGridDescription", "promoGridDescription");
		PrivateAccessor.setField(promoGridDetailsModel, "promoGridExpirationLabel", "promoGridExpirationLabel");
		PrivateAccessor.setField(promoGridDetailsModel, "promoGridCtaLabel", "promoGridCtaLabel");
		PrivateAccessor.setField(promoGridDetailsModel, "promoGridCtaUrl", "promoGridCtaUrl");
		PrivateAccessor.setField(promoGridDetailsModel, "openNewTab", "openNewTab");
		PrivateAccessor.setField(promoGridDetailsModel, "borderEnable", "borderEnable");
		
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
        lenient().when(externalizerService.getFormattedUrl("promoGridCtaUrl", resourceResolver)).thenReturn("https://www.google.co.in");
        lenient().when(externalizerService.getFormattedUrl("promoGridImage", resourceResolver)).thenReturn("/content/us/en/img.png");

        Assertions.assertNotNull(promoGridDetailsModel.getPromoGridExpirationDate());

        assertNotNull(promoGridDetailsModel.getPromoGridImage());
        assertNotNull(promoGridDetailsModel.getPromoGridAltText());
        assertNotNull(promoGridDetailsModel.getPromoGridCtaUrl());
        assertNotNull(promoGridDetailsModel.getPromoGridTitle());
        assertNotNull(promoGridDetailsModel.getPromoGridExpirationLabel());
        assertNotNull(promoGridDetailsModel.getPromoGridDescription());
        assertNotNull(promoGridDetailsModel.getPromoGridCtaLabel());
        assertNotNull(promoGridDetailsModel.getOpenNewTab());
        assertNotNull(promoGridDetailsModel.getBorderEnable());
    }
    
    /**
     * Test init.
     * @throws LoginException 
     */
    @Test
    void testInit() throws LoginException{
    	promoGridDetailsModel.init();
    }
}