package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.impl.GraphImpl;

import junitx.util.PrivateAccessor;

/**
 * The Class GraphImplTest.
 */
@ExtendWith({ MockitoExtension.class })
class GraphImplTest {
	
	/** The Graph impl model. */
	@InjectMocks
	GraphImpl graphImplTestModel;
	
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The test value. */
	private String TEST_VALUE = "test";
	
	/**
	 * Setup.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setup() throws Exception {
		PrivateAccessor.setField(graphImplTestModel, "resetIcon", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "resetIconAlt", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "resetIconGray", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "resetIconGrayAlt", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "expandGraphLabel", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "expandGraphIcon", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "expandGraphIconAlt", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "zoomLabel", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "zoomPlusIcon", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "zoomPlusIconAlt", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "zoomPlusIconGray", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "zoomPlusIconGrayAlt", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "zoomMinusIcon", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "zoomMinusIconAlt", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "zoomMinusIconGray", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "zoomMinusIconGrayAlt", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "menuIcon", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "menuIconAlt", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "resetZoom", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "graphDisplayOptions", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "fluorochromeSets", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "userGuide", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "xAxis", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "yAxis", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "savedListIconAlt", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "savedList", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "savedListIcon", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "expandWorkspace", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "graphExportLabel", TEST_VALUE);
		PrivateAccessor.setField(graphImplTestModel, "expandLabel", TEST_VALUE);
		
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(graphImplTestModel.getExpandGraphLabel());
		assertNotNull(graphImplTestModel.getExpandGraphIcon());
		assertNotNull(graphImplTestModel.getExpandGraphIconAlt());
		assertNotNull(graphImplTestModel.getZoomLabel());
		assertNotNull(graphImplTestModel.getZoomPlusIcon());
		assertNotNull(graphImplTestModel.getZoomPlusIconAlt());
		assertNotNull(graphImplTestModel.getZoomPlusIconGray());
		assertNotNull(graphImplTestModel.getZoomPlusIconGrayAlt());
		assertNotNull(graphImplTestModel.getZoomMinusIcon());
		assertNotNull(graphImplTestModel.getZoomMinusIconAlt());
		assertNotNull(graphImplTestModel.getZoomMinusIconGray());
		assertNotNull(graphImplTestModel.getZoomMinusIconGrayAlt());
		assertNotNull(graphImplTestModel.getResetZoom());
		assertNotNull(graphImplTestModel.getGraphDisplayOptions());
		assertNotNull(graphImplTestModel.getFluorochromeSets());
		assertNotNull(graphImplTestModel.getUserGuide());
		assertNotNull(graphImplTestModel.getXAxis());
		assertNotNull(graphImplTestModel.getYAxis());
		assertNotNull(graphImplTestModel.getResetIcon());
		assertNotNull(graphImplTestModel.getResetIconAlt());
		assertNotNull(graphImplTestModel.getResetIconGray());
		assertNotNull(graphImplTestModel.getResetIconGrayAlt());
		assertNotNull(graphImplTestModel.getMenuIcon());
		assertNotNull(graphImplTestModel.getMenuIconAlt());
		assertNotNull(graphImplTestModel.getSavedList());
		assertNotNull(graphImplTestModel.getSavedListIcon());
		assertNotNull(graphImplTestModel.getSavedListIconAlt());
		assertNotNull(graphImplTestModel.getExpandWorkspace());
		assertNotNull(graphImplTestModel.getGraphExportLabel());
		assertNotNull(graphImplTestModel.getExpandLabel());
		
	}
}