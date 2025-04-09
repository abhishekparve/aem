package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class DisclaimersModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class DisclaimersModelTest {
	
	/** The disclaimers model. */
	@InjectMocks
	DisclaimersModel disclaimersModel;
	
	/** The current page. */
	@Mock
	Page currentPage;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;
	
	/** The resource. */
	Resource resource;
	
	/** The Constant REGIONAL_DISCLAIMER_LABEL. */
	public static final String REGIONAL_DISCLAIMER_LABEL = "regionalDisclaimer";
	
	/** The Constant PAGE_TITLE. */
	public static final String PAGE_TITLE = "Disclaimer Page";
	
	/** The Constant PAGE_RESOURCE_TYPE. */
	public static final String PAGE_RESOURCE_TYPE = "/content/disclaimer";
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {		
		PrivateAccessor.setField(disclaimersModel, "currentPage", currentPage);
		PrivateAccessor.setField(disclaimersModel, "request", request);
		PrivateAccessor.setField(disclaimersModel, "primaryDisclaimerDescription", "primaryDisclaimerDescription");
		PrivateAccessor.setField(disclaimersModel, "secondaryDisclaimerDescription", "secondaryDisclaimerDescription");
		PrivateAccessor.setField(disclaimersModel, "tertiaryDisclaimerDescription", "tertiaryDisclaimerDescription");
		disclaimersModel.init();
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		disclaimersModel.init();
	}
	@Test
	void testGeters() {
		disclaimersModel.getGlobalDisclaimerDescription();
		disclaimersModel.getRegionalDisclaimerDescription();
		disclaimersModel.getTertiaryDisclaimerDescription();
		disclaimersModel.getTertiaryDisclaimerFontSize();
	}
	/**
	 * Test get global disclaimer font size.
	 */
	@Test
	void testGetGlobalDisclaimerFontSize() {
		disclaimersModel.getGlobalDisclaimerFontSize();
	}

	/**
	 * Test get regional disclaimer font size.
	 */
	@Test
	void testGetRegionalDisclaimerFontSize() {
		disclaimersModel.getRegionalDisclaimerFontSize();
	}
	
	/**
	 * Test get is disabled.
	 */
	@Test
	void testGetIsDisabled() {
		disclaimersModel.getIsDisabled();
	}
}
