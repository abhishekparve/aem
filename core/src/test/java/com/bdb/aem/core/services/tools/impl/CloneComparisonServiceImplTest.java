package com.bdb.aem.core.services.tools.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.CloneComparisonModel;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Junit Test for {@link CloneComparisonServiceImpl}
 * 
 * @author ronbanerjee
 *
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class CloneComparisonServiceImplTest {
	
	@InjectMocks
	private CloneComparisonServiceImpl cloneService;
	
	@Mock
	private CloneComparisonModel cloneModel;
	
	@Mock
	private Resource resource;
	
	/**
	 * Sets up.
	 * @throws NoSuchFieldException
	 */
	@BeforeEach
	public void setUp() throws NoSuchFieldException {
		PrivateAccessor.setField(cloneService, "apiEndpoint", "apiEndpoint");
		PrivateAccessor.setField(cloneService, "viewProductsPlaceholder", "viewProductsPlaceholder");
		PrivateAccessor.setField(cloneService, "linkToCatalogPlaceholder", "linkToCatalogPlaceholder");
		PrivateAccessor.setField(cloneService, "dyeNamePlaceholder", "dyeNamePlaceholder");
		PrivateAccessor.setField(cloneService, "dyeNamePlaceholderForClone", "dyeNamePlaceholderForClone");
		PrivateAccessor.setField(cloneService, "colorMap", "colorMap");
		lenient().when(cloneModel.getResource()).thenReturn(resource);
		lenient().when(resource.getPath()).thenReturn("/content/bdb/language-masters/en/clone-comparison");
	}
	
	/**
	 * Tests  CloneComparisonServiceImpl#updateSlingModel
	 */
	@Test
	public void testUpdateSlingModel() {
		cloneService.updateSlingModel(cloneModel); 
	}
	
	/**
	 * Tests  CloneComparisonServiceImpl#updateSlingModel
	 */
	@Test
	public void testUpdateSlingModel2() {
		lenient().when(resource.getPath()).thenReturn("/content/bdb/na/ca/en-ca/clone-comparison");
		cloneService.updateSlingModel(cloneModel); 
	}
	
	/**
	 * Tests the getters
	 */
	@Test
	public void testGetters() {
		assertNotNull(cloneService.getApiEndpoint());
		assertNotNull(cloneService.getDyeNamePlaceholder());
		assertNotNull(cloneService.getDyeNamePlaceholderForClone());
		assertNotNull(cloneService.getLinkToCatalogPlaceholder());
		assertNotNull(cloneService.getViewProductsPlaceholder());
		assertNotNull(cloneService.getColorMap());
	}

}
