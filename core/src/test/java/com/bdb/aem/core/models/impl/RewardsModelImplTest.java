package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

/**
 * The Class RewardsModelImplTest.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class RewardsModelImplTest {


	/**  Resource Resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The rewards model. */
	@InjectMocks
	RewardsModelImpl rewardsModel;
	
	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;
	
	/** The context. */
    @Mock
    ComponentContext context;
    
    /** The Resource. */
	@Mock
	Resource resource;
	
	/** The Resource. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The externalizer. */
	@Mock
    ExternalizerService externalizer;
	

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws LoginException the login exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException, LoginException
	{
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		PrivateAccessor.setField(rewardsModel, "redirectionUrl", "redirectionUrl");
		PrivateAccessor.setField(rewardsModel, "earnTabLabels", "earnTabLabels Title");
		PrivateAccessor.setField(rewardsModel, "activityTabLabel", "activityTabLabel");
		
		rewardsModel.init();
	}
	 /**
     * Test Not Null for the Variables.
	 *  testGetters()
     */
	@Test
	void testGetters() {
		assertNotNull(rewardsModel.getRewardsLabels());
		assertNotNull(rewardsModel.getRewardsConfig());
	}

	
    /**
     * Test init.
	 *
     */
    @Test
    void testInit(){
		rewardsModel.init();
    }


}
