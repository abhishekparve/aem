package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.CtaModel;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ErrorPageModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ErrorPageModelImplTest {
	
	/** The value page title. */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";
	
	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";
	
	/** The error page test model. */
	@InjectMocks
	ErrorPageModelImpl errorPageTestModel;
	
	/** The resource resolver. */
	@Mock
    ResourceResolver resourceResolver;
	
	/** The externalizer service. */
	@Mock
    ExternalizerService externalizerService;
	
	/** The cta labels multi field. */
	private List<Resource> ctaLabelsMultiField;
	
	/** The resource. */
	@Mock
	Resource resource;
	
	/** The context. */
	private AemContext context;
	
	/** The cta model. */
	@Mock
	CtaModel ctaModel;
	
	/** The component context. */
	@Mock
    ComponentContext componentContext;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		ctaLabelsMultiField = Arrays.asList(resource);
		
		context.addModelsForClasses(ErrorPageModelImpl.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		errorPageTestModel = new ErrorPageModelImpl();

		List<CtaModel> ctaList = new ArrayList<>();
		ctaList.add(ctaModel);
		PrivateAccessor.setField(errorPageTestModel, "imageSrc", "imageSrc");
		PrivateAccessor.setField(errorPageTestModel, "imageAltText", "imageAltText");
		PrivateAccessor.setField(errorPageTestModel, "title", "404");
		PrivateAccessor.setField(errorPageTestModel, "subtitle", "subtitle");
		PrivateAccessor.setField(errorPageTestModel, "description", "description");
		PrivateAccessor.setField(errorPageTestModel, "linkInfoText", "linkInfoText");
		PrivateAccessor.setField(errorPageTestModel, "ctaList", ctaList);
		PrivateAccessor.setField(errorPageTestModel, "ctaLabelsMultiField", ctaLabelsMultiField);
		PrivateAccessor.setField(errorPageTestModel, "externalizerService", externalizerService);
		
		lenient().when(ctaModel.getLabel()).thenReturn("label");
		lenient().when(ctaModel.getPath()).thenReturn("path");
		lenient().when(resource.adaptTo(CtaModel.class)).thenReturn(ctaModel);
	}
	
	@Test 
	void testInit() throws LoginException {
        errorPageTestModel.init();
	}
	
	@Test
	void testGetImageSrc() {
		errorPageTestModel.getImageSrc();
	}
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(errorPageTestModel.getImageAltText());
		assertNotNull(errorPageTestModel.getTitle());
		assertNotNull(errorPageTestModel.getSubtitle());
		assertNotNull(errorPageTestModel.getDescription());
		assertNotNull(errorPageTestModel.getLinkInfoText());
		assertNotNull(errorPageTestModel.getCtaList());
	}
}
