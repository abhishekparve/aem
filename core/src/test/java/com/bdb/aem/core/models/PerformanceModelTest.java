package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;

import junitx.util.PrivateAccessor;

/**
 * The Class PerformanceModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class PerformanceModelTest {
	
	/** The performance model. */
	@InjectMocks
	PerformanceModel performanceModel;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The image grid list. */
	@Mock
	Resource imageGridList;
	
	@Mock
	Resource currentResource;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
    @BeforeEach
    void setUp() throws Exception {
    	List<PerformanceDetailsModel> gridBeanList = new ArrayList<>();
		PrivateAccessor.setField(performanceModel, "sectionTitle", "sectionTitle");
		PrivateAccessor.setField(performanceModel, "title", "title");
		PrivateAccessor.setField(performanceModel, "gridBeanList", gridBeanList);
		
    }
    
	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testInit() throws LoginException, NoSuchFieldException {
		Iterator<Resource> resItr = Mockito.mock(Iterator.class);
		Resource childItr = Mockito.mock(Resource.class);
		ValueMap vM = Mockito.mock(ValueMap.class);
		PrivateAccessor.setField(performanceModel, "imageGridList", imageGridList);

		when(imageGridList.hasChildren()).thenReturn(true);
		when(imageGridList.listChildren()).thenReturn(resItr);
		when(resItr.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(false);
		when(resItr.next()).thenReturn(childItr);
		when(childItr.getValueMap()).thenReturn(vM);
		when(vM.get(CommonConstants.SUB_TITLE, StringUtils.EMPTY)).thenReturn("Sub Title");
		when(childItr.hasChildren()).thenReturn(true);
		when(childItr.listChildren()).thenReturn(resItr);

		performanceModel.init();
	}

	/**
	 * Test get methods not null.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testGetMethodsNotNull() throws NoSuchFieldException {
		
		when(currentResource.getName()).thenReturn("performanceModel");
		when(currentResource.getParent()).thenReturn(currentResource);
		assertNotNull(performanceModel.getSectionTitle());
		assertNotNull(performanceModel.getTitle());
		assertNotNull(performanceModel.getGridBeanList());
		assertNotNull(performanceModel.getArticleId());
	}
}
