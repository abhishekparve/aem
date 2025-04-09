package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.TrainingDetailsCardModel;
import com.bdb.aem.core.models.TrainingDetailsContainerModel;
import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class FeaturedTrainingCarouselModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class FeaturedTrainingCarouselModelImplTest {

	/** The externalizer service. */
	@Mock
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
    
    /** The context. */
    @Mock
    ComponentContext context;
    
    /** The training details model. */
    List<TrainingDetailsCardModel> trainingDetailsModel = new ArrayList<TrainingDetailsCardModel>();
    
    /** The resource. */
    @Mock
    Resource resource;
    
    /** The node list. */
    @Mock
    List<String> nodeList;
    
    /** The children. */
    @Mock
    Iterator<Resource> children;
    
    /** The model. */
    @Mock
    TrainingDetailsCardModel cardModel;
    
    /** The model. */
    @Mock
    TrainingDetailsContainerModel model;
    
    /** The featured training models. */
    @Mock
    private List<TrainingDetailsCardModel> featuredTrainingModels;
    
    /** The featured training models test. */
    @Mock
    private List<TrainingDetailsCardModel> featuredTrainingModelsTest;
    
    /** The featured training carousel model. */
    @InjectMocks
    FeaturedTrainingCarouselModelImpl featuredTrainingCarouselModel;
    
    /** The featured training mock model. */
    @Mock
    FeaturedTrainingCarouselModelImpl featuredTrainingMockModel;
    
    /** The path. */
    private final String PATH = "/dummy/test";
    
    /** The ret path. */
    private final String RET_PATH = "https://www.testurl.com/dummy/test";
    
    /** The training details resource type. */
    private final String TRAINING_DETAILS_RESOURCE_TYPE = "bdb-aem/proxy/components/content/trainingDetailsCard";
    
    /** The Constant JCR_ROOT. */
    protected static final String JCR_ROOT = "/jcr:content/root";
    
    /** The card model 1. */
    @InjectMocks
    TrainingDetailsCardModelImpl cardModel1 = new TrainingDetailsCardModelImpl();
    
    /** The card model 2. */
    @InjectMocks
    TrainingDetailsCardModelImpl cardModel2 = new TrainingDetailsCardModelImpl();
    
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		PrivateAccessor.setField(cardModel1, "title", "title");
		PrivateAccessor.setField(cardModel1, "isFeatured", true);
		PrivateAccessor.setField(cardModel2, "title", "title");
		PrivateAccessor.setField(cardModel2, "isFeatured", true);
		trainingDetailsModel.add(cardModel1);
		trainingDetailsModel.add(cardModel2);
		PrivateAccessor.setField(featuredTrainingCarouselModel, "sectionTitle", "sectionTitle");
		PrivateAccessor.setField(featuredTrainingCarouselModel, "title", "title");
		PrivateAccessor.setField(featuredTrainingCarouselModel, "commonCtaLabel", "commonCtaLabel");
		PrivateAccessor.setField(featuredTrainingCarouselModel, "commonCtaPath", PATH);
		PrivateAccessor.setField(featuredTrainingCarouselModel, "trainingDetailsPagePath", PATH);
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		when(resourceResolver.getResource(PATH.concat(JCR_ROOT))).thenReturn(resource);
		when(resource.listChildren()).thenReturn(children);
		when(children.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).
		thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).
		thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).
		thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).
		thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).
		thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).
		thenReturn(false);
		when(children.next()).thenReturn(resource);
		when(resource.isResourceType(TRAINING_DETAILS_RESOURCE_TYPE)).thenReturn(true);
		when(resource.getPath()).thenReturn(PATH);
		when(resourceResolver.getResource(PATH)).thenReturn(resource);
		when(resource.adaptTo(TrainingDetailsContainerModel.class)).thenReturn(model);
		when(model.getTrainingDetailsList()).thenReturn(trainingDetailsModel);
		featuredTrainingCarouselModel.init();
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn(RET_PATH);
		assertEquals("sectionTitle", featuredTrainingCarouselModel.getSectionTitle());
		assertEquals("title", featuredTrainingCarouselModel.getTitle());
		assertEquals("commonCtaLabel", featuredTrainingCarouselModel.getCommonCtaLabel());
		assertEquals(PATH, featuredTrainingCarouselModel.getCommonCtaPath());
		featuredTrainingCarouselModel.init();
	}
}
