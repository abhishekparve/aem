
package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccordionTableModelTest {

	@InjectMocks
	AccordionTableModel accordionTableModel;

	/** The resource resolver. */

	@Mock
	ResourceResolver resourceResolver;

	/** Mock ResourceResolverFactory. */

	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The context. */
	@Mock
	ComponentContext context;

	@Mock
	SlingHttpServletRequest request;
	
	@Mock
	AccordionListModel accordion;

	@BeforeEach
	void setUp() throws Exception {
		
		PrivateAccessor.setField(accordionTableModel, "sectionTitle","sectionTitle");
		PrivateAccessor.setField(accordionTableModel, "title","title");
		PrivateAccessor.setField(accordionTableModel, "subTitle","subTitle");
		PrivateAccessor.setField(accordionTableModel, "backgroundColor","backgroundColor");
		PrivateAccessor.setField(accordionTableModel, "viewTableLabel","viewTableLabel");
		PrivateAccessor.setField(accordionTableModel, "hideTableLabel","hideTableLabel");
		PrivateAccessor.setField(accordionTableModel, "accordions", new ArrayList<>());
	}

	@Test
	void test() throws LoginException {
		
		lenient().when(accordion.getSectionDescription()).thenReturn("sectionDescription");
		lenient().when(accordion.getSectionHeading()).thenReturn("sectionHeading");
		List<AccordionListModel> accordions = new ArrayList<>();
		accordions.add(accordion);
		
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		accordionTableModel.init();

	}

	@Test
	void testgetSectionTitle() {
		accordionTableModel.getSectionTitle();
	}

	@Test
	void testgetTitle() {
		accordionTableModel.getTitle();
	}

	@Test
	void testgetSubTitle() {
		accordionTableModel.getSubTitle();
	}

	@Test
	void testgetBackgroundColor() {
		accordionTableModel.getBackgroundColor();
	}

	@Test
	void testgetViewTableLabel() {
		accordionTableModel.getViewTableLabel();
	}

	@Test
	void testgetHideTableLabel() {
		accordionTableModel.getHideTableLabel();
	}
	
	@Test
	void testGetAccordionList() {
		accordionTableModel.getAccordionList();
	}

}
