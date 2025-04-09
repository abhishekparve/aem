package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class KitsAndSetsColumnMultifieldModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class KitsAndSetsColumnMultifieldModelTest {

  
    /** The kits and sets column multifield model. */
    @InjectMocks
    KitsAndSetsColumnMultifieldModel kitsAndSetsColumnMultifieldModel;
	
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
	void setUp() throws Exception {
    	PrivateAccessor.setField(kitsAndSetsColumnMultifieldModel,"columnData","test-data");
	}
  	
    
    /**
     * Test fields.
     */
    @Test
	void testFields() {
		assertEquals("test-data",kitsAndSetsColumnMultifieldModel.getColumnData());
	}

}
