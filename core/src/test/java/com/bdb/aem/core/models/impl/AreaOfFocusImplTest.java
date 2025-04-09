package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import junitx.util.PrivateAccessor;

/**
 * The Class AreaOfFocusImplTest.
 */
@ExtendWith({ MockitoExtension.class })
class AreaOfFocusImplTest {

	/** The area of focus impl. */
	@InjectMocks
	AreaOfFocusImpl areaOfFocusImpl;

	/** The resource. */
	@Mock
	Resource resource;

	/** The Test value. */
	private String Test_Value = "test";

	/** The multiple industry config. */
	private List<Resource> multipleIndustryConfig;

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		multipleIndustryConfig = new ArrayList<>();
		multipleIndustryConfig.add(resource);

		PrivateAccessor.setField(areaOfFocusImpl, "industryTitle", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "industryDescription", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "industrySelectionText", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "roleTitle", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "roleDescription", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "roleSelectionText", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "interestTitle", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "interestDescription", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "interestSelectionText", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "nextIndustryButtonLabel", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "skipIndustryLinkLabel", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "backRoleButtonLabel", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "nextRoleButtonLabel", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "backInterestButtonLabel", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "nextInterestButtonLabel", Test_Value);
		PrivateAccessor.setField(areaOfFocusImpl, "multipleIndustryConfig", multipleIndustryConfig);
		PrivateAccessor.setField(areaOfFocusImpl, "aoFLabels", Test_Value);
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		areaOfFocusImpl.init();
	}

	/**
	 * Test all getter.
	 */
	@Test
	void testAllGetter() {
		assertEquals(Test_Value, areaOfFocusImpl.getIndustryTitle());
		assertEquals(Test_Value, areaOfFocusImpl.getIndustryDescription());
		assertEquals(Test_Value, areaOfFocusImpl.getIndustrySelectionText());
		assertEquals(Test_Value, areaOfFocusImpl.getRoleTitle());
		assertEquals(Test_Value, areaOfFocusImpl.getRoleDescription());
		assertEquals(Test_Value, areaOfFocusImpl.getRoleSelectionText());
		assertEquals(Test_Value, areaOfFocusImpl.getInterestTitle());
		assertEquals(Test_Value, areaOfFocusImpl.getInterestDescription());
		assertEquals(Test_Value, areaOfFocusImpl.getInterestSelectionText());
		assertEquals(Test_Value, areaOfFocusImpl.getNextIndustryButtonLabel());
		assertEquals(Test_Value, areaOfFocusImpl.getSkipIndustryLinkLabel());
		assertEquals(Test_Value, areaOfFocusImpl.getBackRoleButtonLabel());
		assertEquals(Test_Value, areaOfFocusImpl.getNextRoleButtonLabel());
		assertEquals(Test_Value, areaOfFocusImpl.getBackInterestButtonLabel());
		assertEquals(Test_Value, areaOfFocusImpl.getNextInterestButtonLabel());
		assertEquals(Test_Value, areaOfFocusImpl.getAoFLabels());
	}

	/**
	 * Test all getter not null.
	 */
	@Test
	void testAllGetterNotNull() {
		assertNotNull(areaOfFocusImpl.getIndustryTitle());
		assertNotNull(areaOfFocusImpl.getIndustryDescription());
		assertNotNull(areaOfFocusImpl.getIndustrySelectionText());
		assertNotNull(areaOfFocusImpl.getRoleTitle());
		assertNotNull(areaOfFocusImpl.getRoleDescription());
		assertNotNull(areaOfFocusImpl.getRoleSelectionText());
		assertNotNull(areaOfFocusImpl.getInterestTitle());
		assertNotNull(areaOfFocusImpl.getInterestDescription());
		assertNotNull(areaOfFocusImpl.getInterestSelectionText());
		assertNotNull(areaOfFocusImpl.getNextIndustryButtonLabel());
		assertNotNull(areaOfFocusImpl.getSkipIndustryLinkLabel());
		assertNotNull(areaOfFocusImpl.getBackRoleButtonLabel());
		assertNotNull(areaOfFocusImpl.getNextRoleButtonLabel());
		assertNotNull(areaOfFocusImpl.getBackInterestButtonLabel());
		assertNotNull(areaOfFocusImpl.getNextInterestButtonLabel());
		assertNotNull(areaOfFocusImpl.getIndustryList());
		assertNotNull(areaOfFocusImpl.getAoFLabels());
	}

}
