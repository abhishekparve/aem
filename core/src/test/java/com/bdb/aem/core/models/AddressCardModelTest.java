package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The class Address Card Model Test.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class AddressCardModelTest {
	
	/**  The address card model. */
	@InjectMocks
	private AddressCardModel  addressModel;
	
	/**  The address card sections  model. */
	@InjectMocks
	private AddressCardSectionsModel secModel;
	
	/**  The address card sub sections model. */
	@InjectMocks
	private AddressCardSubSectionsModel subSecModel;
	
	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

    /** The resource resolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

	/** The path. */
	private final String PATH = "/content/item";
    
    /** The ret path. */
    private final String RET_PATH = "http://domain.test/content/item";
	
	/**
	 * Sets up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		
		PrivateAccessor.setField(addressModel, "bgColor", "bg-white");
		
		PrivateAccessor.setField(secModel, "sectionTitle", "Title");
		
		PrivateAccessor.setField(subSecModel, "sectionSubTitle", "sectionSubTitle");
		PrivateAccessor.setField(subSecModel, "description", "description");
		PrivateAccessor.setField(subSecModel, "linkUrl", PATH);
		PrivateAccessor.setField(subSecModel, "linkTitle", "linkTitle");
		PrivateAccessor.setField(subSecModel, "iconAltText", "iconAltText");
		PrivateAccessor.setField(subSecModel, "iconPath", PATH);
		
		List<AddressCardSectionsModel> secList = new ArrayList<>();
		secList.add(secModel);
		PrivateAccessor.setField(addressModel, "sections", secList);
		List<AddressCardSubSectionsModel> subSecList = new ArrayList<>();
		subSecList.add(subSecModel);
		PrivateAccessor.setField(secModel, "subSections", subSecList);
	}
	
	/**
	 * Tests all getters. 
	 */
	@Test
	void testAllGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn(RET_PATH);
		assertNotNull(addressModel.getBgColor());
		assertNotNull(addressModel.getSections());
		assertNotNull(secModel.getSectionTitle());
		assertNotNull(secModel.getSubSections());
		assertNotNull(subSecModel.getDescription());
		assertNotNull(subSecModel.getIconAltText());
		assertNotNull(subSecModel.getIconPath());
		assertNotNull(subSecModel.getLinkTitle());
		assertNotNull(subSecModel.getLinkUrl());
		assertNotNull(subSecModel.getSectionSubTitle());
		assertNotNull(addressModel.getSections());
	}

}
