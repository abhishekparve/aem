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


@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class KitsAndSetsRowMultifieldModelTest {

  
    @InjectMocks
    KitsAndSetsRowMultifieldModel kitsAndSetsRowMultifieldModel;
	
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
	void setUp() throws Exception {
    	KitsAndSetsColumnMultifieldModel columnMultifieldModel = new KitsAndSetsColumnMultifieldModel();
		PrivateAccessor.setField(columnMultifieldModel,"columnData","test-data");
		
		ArrayList<KitsAndSetsColumnMultifieldModel> columnMultifieldList = new ArrayList<>();
		columnMultifieldList.add(columnMultifieldModel);
		PrivateAccessor.setField(kitsAndSetsRowMultifieldModel,"rowColumnMultifield",columnMultifieldList);
	}
   
    
    /**
     * Test getters.
     */
    @Test
	void testGetters() {	
		assertNotNull(kitsAndSetsRowMultifieldModel.getRowColumnMultifield());
		assertNotNull(kitsAndSetsRowMultifieldModel.getRowColumnMultifield().get(0));
	}
    
    /**
     * Test fields.
     */
    @Test
	void testFields() {
		assertEquals("test-data",kitsAndSetsRowMultifieldModel.getRowColumnMultifield().get(0).getColumnData());
	}

}
