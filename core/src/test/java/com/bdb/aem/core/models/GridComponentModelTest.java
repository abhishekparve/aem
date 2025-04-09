package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class GridComponentModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class GridComponentModelTest {
	
	
	/** The grid component model. */
	@InjectMocks
	GridComponentModel gridComponentModel;

	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		GridComponentDetailsModel gridComponentDetailsModel = new GridComponentDetailsModel();
		
		PrivateAccessor.setField(gridComponentDetailsModel,"productTitle","Test Title");
		
		ArrayList<GridComponentDetailsModel> gridComponentList = new ArrayList<>();
		gridComponentList.add(gridComponentDetailsModel);
		
		PrivateAccessor.setField(gridComponentModel,"cells",gridComponentList);
	}
   
    
    /**
     * Test getters.
     */
    @Test
	void testGetters() {
		assertNotNull(gridComponentModel.getCells());
		assertNotNull(gridComponentModel.getCells().get(0));
	}
    
    /**
     * Test fields.
     */
    @Test
	void testFields() {
		assertEquals("Test Title",gridComponentModel.getCells().get(0).getProductTitle());
	}
    
}