package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class GlobalFooterCategoryModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class GlobalFooterCategoryModelTest {

    
    /** The global footer category model. */
    @InjectMocks
    GlobalFooterCategoryModel globalFooterCategoryModel;
    
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
	void setUp() throws Exception {
    	GlobalFooterSubcategoryModel globalFooterSubcategoryModel = new GlobalFooterSubcategoryModel();
		PrivateAccessor.setField(globalFooterSubcategoryModel,"title","Test Title");
		
		ArrayList<GlobalFooterSubcategoryModel> footerSubcategoryList = new ArrayList<>();
		footerSubcategoryList.add(globalFooterSubcategoryModel);
		
		PrivateAccessor.setField(globalFooterCategoryModel,"subcategories",footerSubcategoryList);
	}
   
    
    /**
     * Test getters.
     */
    @Test
	void testGetters() {
		assertNotNull(globalFooterCategoryModel.getSubcategories());
		assertNotNull(globalFooterCategoryModel.getSubcategories().get(0));
        assertNotNull(globalFooterCategoryModel.getTitle());

	}
    
    /**
     * Test fields.
     */
    @Test
	void testFields() {
		assertEquals("Test Title",globalFooterCategoryModel.getSubcategories().get(0).getTitle());
	}
}