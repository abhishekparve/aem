package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class TabComponentTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TabComponentTest {
	
    /** The tab component. */
    @InjectMocks
    TabComponent tabComponent;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(tabComponent, "sectionTitle", "sectionTitle");
		PrivateAccessor.setField(tabComponent, "title", "title");
		
	}

	/**
	 * Test no background color.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testNoBackgroundColor() throws NoSuchFieldException {
		PrivateAccessor.setField(tabComponent, "backgroundColor", "");
		assertEquals(false, tabComponent.isBackgroundColor());
	}

	/**
	 * Test gray color.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testGrayColor() throws NoSuchFieldException {
		PrivateAccessor.setField(tabComponent, "backgroundColor", "gray");
		assertEquals(true, tabComponent.isBackgroundColor());
	}
	
	/**
	 * Test other color.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testOtherColor() throws NoSuchFieldException {
		PrivateAccessor.setField(tabComponent, "backgroundColor", "notAColor");
		assertEquals(false, tabComponent.isBackgroundColor());
	}
}
