package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import junitx.util.PrivateAccessor;

@ExtendWith({ MockitoExtension.class })
class PerformanceInnerModelTest {
	@InjectMocks
	PerformanceInnerModel performanceInnerModel;
	
	private String TEST_VALUE="test"; 

	@Test
	void testAllGetter() throws NoSuchFieldException {
		PrivateAccessor.setField(performanceInnerModel, "image", TEST_VALUE);
		PrivateAccessor.setField(performanceInnerModel, "heading", TEST_VALUE);
		PrivateAccessor.setField(performanceInnerModel, "description", TEST_VALUE);
		PrivateAccessor.setField(performanceInnerModel, "enlargedImagePath", TEST_VALUE);
		PrivateAccessor.setField(performanceInnerModel, "enlargeSize", TEST_VALUE);
		PrivateAccessor.setField(performanceInnerModel, "altImage", TEST_VALUE);
		PrivateAccessor.setField(performanceInnerModel, "modalHeading", TEST_VALUE);
		PrivateAccessor.setField(performanceInnerModel, "modalDescription", TEST_VALUE);

		assertEquals(TEST_VALUE, performanceInnerModel.getImage());
		assertEquals(TEST_VALUE, performanceInnerModel.getHeading());
		assertEquals(TEST_VALUE, performanceInnerModel.getDescription());
		assertEquals(TEST_VALUE, performanceInnerModel.getEnlargedImagePath());
		assertEquals(TEST_VALUE, performanceInnerModel.getEnlargeSize());
		assertEquals(TEST_VALUE, performanceInnerModel.getAltImage());
		assertEquals(TEST_VALUE, performanceInnerModel.getModalHeading());
		assertEquals(TEST_VALUE, performanceInnerModel.getModalDescription());
	}

}
