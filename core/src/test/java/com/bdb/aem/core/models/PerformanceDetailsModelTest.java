package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import junitx.util.PrivateAccessor;


/**
 * The Class PerformanceDetailsModelTest.
 */
@ExtendWith({ MockitoExtension.class })
public class PerformanceDetailsModelTest {

	/** The performance details model. */
	@InjectMocks
	PerformanceDetailsModel performanceDetailsModel;

	/** The performance inner model. */
	private PerformanceInnerModel performanceInnerModel;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		performanceDetailsModel = new PerformanceDetailsModel();
		performanceInnerModel = new PerformanceInnerModel();
		
		PrivateAccessor.setField(performanceInnerModel, "image", "image/path.svg");
		ArrayList<PerformanceInnerModel> performanceInnerList = new ArrayList<>();
		performanceInnerList.add(performanceInnerModel);
		PrivateAccessor.setField(performanceDetailsModel, "nestedDetails", performanceInnerList);
		
		performanceDetailsModel.setNestedDetails(performanceInnerList);
		
		PrivateAccessor.setField(performanceDetailsModel, "subTitle", "subTitle");
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(performanceDetailsModel.getNestedDetails());
		assertNotNull(performanceDetailsModel.getNestedDetails().get(0));
		
		assertNotNull(performanceDetailsModel.getSubTitle());
	}
}
