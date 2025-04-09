package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.bean.TrainingDetailsBean;
import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TrainingDetailsModelImplTest {


	/** Resource Resolver */
	@Mock
	ResourceResolver resourceResolver;
	
	@InjectMocks
	TrainingDetailsModelImpl trainingDetailsModelImpl;
	
	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;
	
	/** The context. */
    @Mock
    ComponentContext context;
    
    /** The Resource. */
	@Mock
	Resource resource;
	
	/**List holds the multi-field mock data*/
	List<Resource> trainingDetails = new ArrayList<Resource>();
	
	@BeforeEach
	void setUp() throws NoSuchFieldException, LoginException
	{
		trainingDetails.add(resource);
		TrainingDetailsBean trainingDetailsBean = new TrainingDetailsBean();
		PrivateAccessor.setField(trainingDetailsBean, "trainingImage", "/content/dam/bdb/products/654321_01.png");
		PrivateAccessor.setField(trainingDetailsBean, "trainingAlt", "Alt Text");
		PrivateAccessor.setField(trainingDetailsBean, "description", "Description");
		PrivateAccessor.setField(trainingDetailsBean, "trainingTitle", "Training Title");
		
		PrivateAccessor.setField(trainingDetailsBean, "modelLabel", "Model Label");
		PrivateAccessor.setField(trainingDetailsBean, "modelUrl", "/content/model");
		PrivateAccessor.setField(trainingDetailsBean, "modelText", "Model Text");
		PrivateAccessor.setField(trainingDetailsBean, "color", "color");
		PrivateAccessor.setField(trainingDetailsBean, "ctaLabel", "Cta Label");
		PrivateAccessor.setField(trainingDetailsBean, "ctaUrl", "/content/cta");
		ArrayList<TrainingDetailsBean> trainingDetailsList = new ArrayList<>();
		trainingDetailsList.add(trainingDetailsBean);	
		lenient().when(resource.adaptTo(TrainingDetailsBean.class)).thenReturn(trainingDetailsBean);
		PrivateAccessor.setField(trainingDetailsModelImpl, "sectionTitle", "Section Title");
		PrivateAccessor.setField(trainingDetailsModelImpl, "trainingDetailsList", trainingDetailsList);
		PrivateAccessor.setField(trainingDetailsModelImpl, "trainingDetails", trainingDetails);
	}
	 /**
     * Test Not Null for the Variables.
	 *  testGetters()
     */
	@Test
	void testGetters() {
		assertNotNull(trainingDetailsModelImpl.getSectionTitle());
		assertNotNull(trainingDetailsModelImpl.getTrainingDetailsList().get(0));
	}
	/**
     * Test Variables with the mock data.
	 *  testFields()
     */
	@Test
	void testFields() {
		assertEquals("Section Title",trainingDetailsModelImpl.getSectionTitle());
		assertEquals("/content/dam/bdb/products/654321_01.png",trainingDetailsModelImpl.getTrainingDetailsList().get(0).getTrainingImage());
		assertEquals("Alt Text",trainingDetailsModelImpl.getTrainingDetailsList().get(0).getTrainingAlt());
		assertEquals("Description",trainingDetailsModelImpl.getTrainingDetailsList().get(0).getDescription());
		assertEquals("Training Title",trainingDetailsModelImpl.getTrainingDetailsList().get(0).getTrainingTitle());
		assertEquals("Model Label",trainingDetailsModelImpl.getTrainingDetailsList().get(0).getModelLabel());
		assertEquals("Model Text",trainingDetailsModelImpl.getTrainingDetailsList().get(0).getModelText());
		assertEquals("color",trainingDetailsModelImpl.getTrainingDetailsList().get(0).getColor());
		assertEquals("Cta Label",trainingDetailsModelImpl.getTrainingDetailsList().get(0).getCtaLabel());
	}
	
    /**
     * Test init.
	 * @throws LoginException 
     */
    @Test
    void testInit() throws LoginException{
    	trainingDetailsModelImpl.init();
    }
}
