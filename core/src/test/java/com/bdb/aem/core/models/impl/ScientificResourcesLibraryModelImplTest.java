package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ScientificResourcesLibraryModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ScientificResourcesLibraryModelImplTest {
	
	/** The scientific resources library model. */
	@InjectMocks
	ScientificResourcesLibraryModelImpl scientificResourcesLibraryModel;
	
	/** The label. */
	private final String LABEL = "label";
	
	/** The value. */
	private final int VALUE = 12345;
	
	/** The dropdown items multifield. */
	private List<Resource> dropdownItemsMultifield;
	
	/** The scientific resource. */
	private Resource scientificResource;
	
	/** The context aem. */
	private AemContext contextAem;
	

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Map<String, Object> scientificResourceProperty = new HashMap<>();		
		scientificResourceProperty.put("label", LABEL);
		scientificResourceProperty.put("value", VALUE);
		scientificResource = contextAem.create().resource("/root/scientificLibrary", scientificResourceProperty);
		dropdownItemsMultifield = Arrays.asList(scientificResource);
		
		PrivateAccessor.setField(scientificResourcesLibraryModel, "headerLabel", "headerLabel");
		PrivateAccessor.setField(scientificResourcesLibraryModel, "dropdownItemsMultifield", dropdownItemsMultifield);
		PrivateAccessor.setField(scientificResourcesLibraryModel, "nameLabel", "nameLabel");
		scientificResourcesLibraryModel.init();
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		scientificResourcesLibraryModel.init();
	}
	
	/**
	 * Test get scientific resources label.
	 */
	@Test
	void testGetScientificResourcesLabel() {
		scientificResourcesLibraryModel.getNameLabel();
		scientificResourcesLibraryModel.getResourceTypesMultiField();
		assertNotNull(scientificResourcesLibraryModel.getScientificResourcesLabel());
	}
	
	/**
	 * Test get scientific resources config.
	 */
	@Test
	void testGetScientificResourcesConfig() {
		assertNotNull(scientificResourcesLibraryModel.getScientificResourcesConfig());
	}
	
}
