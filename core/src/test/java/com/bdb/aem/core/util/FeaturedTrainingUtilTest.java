package com.bdb.aem.core.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.TrainingDetailsContainerModel;
import com.bdb.aem.core.models.impl.FeaturedTrainingCarouselModelImpl;
import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

/**
 * The Class FeaturedTrainingUtilTest.
 */
@ExtendWith({MockitoExtension.class})
class FeaturedTrainingUtilTest {
	
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
    
    /** The training details model. */
    @Mock
    List<TrainingDetailsContainerModel> trainingDetailsModel;
    
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
    TrainingDetailsContainerModel model;
    
    /** The featured training carousel model. */
    @InjectMocks
    FeaturedTrainingCarouselModelImpl featuredTrainingCarouselModel;
    
    /** The path. */
    private final String PATH = "/dummy/test";
    
    /** The training details resource type. */
    private final String TRAINING_DETAILS_RESOURCE_TYPE = "bdb-aem/proxy/components/content/trainingDetailsCard";

    /** The Constant JCR_ROOT. */
    protected static final String JCR_ROOT = "/jcr:content/root";

	/**
	 * Test get model array.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testGetModelArray() throws LoginException {
		when(resourceResolver.getResource(PATH.concat(JCR_ROOT))).thenReturn(resource);
		when(resource.listChildren()).thenReturn(children);
		when(children.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(children.next()).thenReturn(resource);
		when(resource.isResourceType(TRAINING_DETAILS_RESOURCE_TYPE)).thenReturn(true);
		when(resource.getPath()).thenReturn(PATH);
		when(resourceResolver.getResource(PATH)).thenReturn(resource);
		when(resource.adaptTo(TrainingDetailsContainerModel.class)).thenReturn(model);
		assertNotNull(FeaturedTrainingUtil.getModelArray(PATH, resourceResolver, TRAINING_DETAILS_RESOURCE_TYPE));
	}

	/**
	 * Test get component nodes.
	 */
	@Test
	void testGetComponentNodes() {
		assertNotNull(FeaturedTrainingUtil.getComponentNodes(PATH, resourceResolver, TRAINING_DETAILS_RESOURCE_TYPE));
	}
}
