package com.bdb.aem.core.bean;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ProductAnnouncementBeanTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ProductAnnouncementBeanTest {

	/** The product announcement bean. */
	ProductAnnouncementBean productAnnouncementBean;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		productAnnouncementBean = new ProductAnnouncementBean();
		PrivateAccessor.setField(productAnnouncementBean, "question", "question");
		PrivateAccessor.setField(productAnnouncementBean, "answer", "answer");
	}
	
	/**
	 * Test.
	 */
	@Test
	void test() {
		assertEquals("question", productAnnouncementBean.getQuestion());
		assertEquals("answer", productAnnouncementBean.getAnswer());
	}
}
