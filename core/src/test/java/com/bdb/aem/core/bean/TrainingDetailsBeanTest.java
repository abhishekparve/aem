package com.bdb.aem.core.bean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
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

@ExtendWith({AemContextExtension.class, MockitoExtension.class })
class TrainingDetailsBeanTest {

	/**Class TrainingDetailsBean*/
	@InjectMocks
	TrainingDetailsBean trainingDetailsBean;
	
	/**Resource of multi-field*/
	@Mock
	Resource resource;
	
	/**Mocking ExternalizerService*/
	@Mock
	ExternalizerService externalizerService;
	
	/**Mocking Resource Resolver Factory*/
	@Mock
	ResourceResolverFactory resolverFactory;
	
	/**Mocking Resource Resolver*/
	@Mock
	ResourceResolver resourceResolver;
	
	/**AEM Context*/
	@Mock
	ComponentContext context;
	
	@BeforeEach
	void init() throws LoginException, NoSuchFieldException
	{
		
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
		
	}
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl("/content/model", resourceResolver)).thenReturn("/content/model");
		lenient().when(externalizerService.getFormattedUrl("/content/cta", resourceResolver)).thenReturn("/content/cta");
		assertNotNull(trainingDetailsBean.getTrainingImage());
		assertNotNull(trainingDetailsBean.getTrainingAlt());
		assertNotNull(trainingDetailsBean.getDescription());
		assertNotNull(trainingDetailsBean.getTrainingTitle());
		assertNotNull(trainingDetailsBean.getModelLabel());
		assertNotNull(trainingDetailsBean.getModelText());
		assertNotNull(trainingDetailsBean.getModelUrl());
		assertNotNull(trainingDetailsBean.getCtaLabel());
		assertNotNull(trainingDetailsBean.getCtaUrl());
		assertNotNull(trainingDetailsBean.getColor());
	}
	@Test
	void testFields() {
		lenient().when(externalizerService.getFormattedUrl("/content/model", resourceResolver)).thenReturn("/content/model");
		lenient().when(externalizerService.getFormattedUrl("/content/cta", resourceResolver)).thenReturn("/content/cta");
		assertEquals("/content/dam/bdb/products/654321_01.png",trainingDetailsBean.getTrainingImage());
		assertEquals("Alt Text",trainingDetailsBean.getTrainingAlt());
		assertEquals("Description",trainingDetailsBean.getDescription());
		assertEquals("Training Title",trainingDetailsBean.getTrainingTitle());
		assertEquals("Model Label",trainingDetailsBean.getModelLabel());
		assertEquals("/content/model",trainingDetailsBean.getModelUrl());
		assertEquals("Model Text",trainingDetailsBean.getModelText());
		assertEquals("color",trainingDetailsBean.getColor());
		assertEquals("Cta Label",trainingDetailsBean.getCtaLabel());
		assertEquals("/content/cta",trainingDetailsBean.getCtaUrl());
	}
	/**
     * Test init.
	 * @throws LoginException 
     */
    @Test
    void testInit() throws LoginException{
    	trainingDetailsBean.init();
    }

}
