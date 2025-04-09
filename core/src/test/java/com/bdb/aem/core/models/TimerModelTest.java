package com.bdb.aem.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class TimerModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TimerModelTest {
	
	/** The timer model. */
	@InjectMocks
	TimerModel timerModel;

	/**
	 * Test.
	 * @throws NoSuchFieldException 
	 */
	@Test
	void test() throws NoSuchFieldException {
		timerModel.init();
		timerModel.getEndTime();
	}
}
