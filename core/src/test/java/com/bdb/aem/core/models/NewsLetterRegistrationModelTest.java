package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.ResourceResolver;
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
 * The Class NewsLetterRegistrationModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class NewsLetterRegistrationModelTest {

    /** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /** The context. */
    @Mock
    ComponentContext context;

    /** The news letter registration model. */
    @InjectMocks
    NewsLetterRegistrationModel newsLetterRegistrationModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(newsLetterRegistrationModel, "newsLetterRegLatestArticleLink", "newsLetterRegLatestArticleLink");
		PrivateAccessor.setField(newsLetterRegistrationModel, "newsLetterRegViewAllUrl", "newsLetterRegViewAllUrl");
		PrivateAccessor.setField(newsLetterRegistrationModel, "newsLetterRegImagePath", "newsLetterRegImagePath");
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
        lenient().when(externalizerService.getFormattedUrl("newsLetterRegLatestArticleLink", resourceResolver)).thenReturn("www.google.com");
        lenient().when(externalizerService.getFormattedUrl("newsLetterRegViewAllUrl", resourceResolver)).thenReturn("www.google.com");
        assertNotNull(newsLetterRegistrationModel.getNewsLetterRegLatestArticleLink());
        assertNotNull(newsLetterRegistrationModel.getNewsLetterRegViewAllUrl());
    }

    /**
     * Test get news letter reg image path.
     */
    @Test
    void testGetNewsLetterRegImagePath(){
        lenient().when(externalizerService.getFormattedUrl("newsLetterRegImagePath", resourceResolver)).thenReturn("www.google.com");
        assertNotNull(newsLetterRegistrationModel.getNewsLetterRegImagePath());
    }

    /**
     * Test init.
     */
    @Test
    void testInit(){
        //lenient().when(resourceResolver.getResource("/content/ruoFields/item0")).thenReturn(ruoItemResource);
        newsLetterRegistrationModel.init();
    }
}
