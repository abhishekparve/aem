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
 * The Class ListSupportModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ListSupportModelTest {

    /** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;
    
    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /** The context. */
    @Mock
    ComponentContext context;

    /** The list support model. */
    @InjectMocks
    ListSupportModel listSupportModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(listSupportModel,"listRowTitle","Support Title");
		PrivateAccessor.setField(listSupportModel,"listRowLink","listRowLink");
		PrivateAccessor.setField(listSupportModel,"listRowDescription","listRowDescription");
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
        lenient().when(externalizerService.getFormattedUrl("rowLink", resourceResolver)).thenReturn("https://www.google.co.in");
        assertNotNull(listSupportModel.getListRowLink());
        assertNotNull(listSupportModel.getListRowTitle());
        assertNotNull(listSupportModel.getListRowDescription());
    }
    
    /**
     * Test init.
     */
    @Test
    void testInit(){
    	listSupportModel.init();
    }
}