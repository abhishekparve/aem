package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class AddressCardSubSectionsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AddressCardSubSectionsModelTest {
	
	/** The externalizer service. */
	@Mock
    ExternalizerService externalizerService;

    /** The resource resolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
    
    /** The context. */
    @Mock
    ComponentContext context;
	
    /** The address card sub sections model. */
    @InjectMocks
    AddressCardSubSectionsModel addressCardSubSectionsModel;
    
    /** The path. */
    private final String PATH = "/content/item";
    
    /** The ret path. */
    private final String RET_PATH = "http://domain.test/content/item";
    
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(addressCardSubSectionsModel, "sectionSubTitle", "sectionSubTitle");
		PrivateAccessor.setField(addressCardSubSectionsModel, "description", "description");
		PrivateAccessor.setField(addressCardSubSectionsModel, "linkUrl", PATH);
		PrivateAccessor.setField(addressCardSubSectionsModel, "linkTitle", "linkTitle");
		PrivateAccessor.setField(addressCardSubSectionsModel, "iconAltText", "iconAltText");
		PrivateAccessor.setField(addressCardSubSectionsModel, "iconPath", PATH);
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn(RET_PATH);
		assertEquals("sectionSubTitle", addressCardSubSectionsModel.getSectionSubTitle());
		assertNotNull(addressCardSubSectionsModel.getDescription());
		assertEquals(PATH, addressCardSubSectionsModel.getLinkUrl());
		assertNotNull(addressCardSubSectionsModel.getLinkTitle());
		assertNotNull(addressCardSubSectionsModel.getIconAltText());
		assertEquals(PATH, addressCardSubSectionsModel.getIconPath());
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
    void testInit() throws LoginException{
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        addressCardSubSectionsModel.init();
    }
	
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		addressCardSubSectionsModel.init();
	}
}
