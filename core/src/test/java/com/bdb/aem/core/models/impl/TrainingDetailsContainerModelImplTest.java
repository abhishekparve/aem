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
 * The Class TrainingDetailsContainerModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TrainingDetailsContainerModelImplTest {
	
	/** The training details container test model. */
	@InjectMocks
	TrainingDetailsContainerModelImpl trainingDetailsContainerTestModel;
	
	
	
	/** The training details multi field. */
	private List<Resource> trainingDetailsMultiField;
	
	/** The context. */
	private AemContext context;
	
	/** The training card resource. */
	private Resource trainingCardResource;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Map<String, String> certificationProperty = new HashMap<>();		
		certificationProperty.put("title", "title");
		trainingCardResource = context.create().resource("/root/master/certification", certificationProperty);
		
		trainingDetailsMultiField = Arrays.asList(trainingCardResource);
		
		PrivateAccessor.setField(trainingDetailsContainerTestModel, "sectionNameText", "sectionNameText");
		PrivateAccessor.setField(trainingDetailsContainerTestModel, "subTitle", "subTitle");
		PrivateAccessor.setField(trainingDetailsContainerTestModel, "trainingDetailsMultiField", trainingDetailsMultiField);
		trainingDetailsContainerTestModel.init();
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		trainingDetailsContainerTestModel.init();
	}

	/**
	 * Test gettters.
	 */
	@Test
	void testGettters() {
		assertEquals(trainingDetailsContainerTestModel.getSectionNameText(), "sectionNameText");
		assertEquals(trainingDetailsContainerTestModel.getSubTitle(), "subTitle");
		assertNotNull(trainingDetailsContainerTestModel.getTrainingDetailsList());
	}
}
