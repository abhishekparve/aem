package com.bdb.aem.core.models;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.fasterxml.jackson.core.JsonProcessingException;

import junitx.util.PrivateAccessor;

/**
 * The class Faq Model Test.
 */

@ExtendWith({ MockitoExtension.class })
public class FaqModelTest {

	/** The faq model. */
	@InjectMocks
	private FaqModel faqModel;

	/** The faq sections model. */
	@InjectMocks
	private FaqSectionsModel sectionsModel;

	/** The faq list model. */
	@InjectMocks
	private FaqListModel listModel;

	/** The faq list image model. */
	@InjectMocks
	private FaqListImageModel imgModel;
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	@Mock
	 Resource resource;
	/** The externalizer service. */
	@Mock
    ExternalizerService externalizerService;
	/**
	 * Sets up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
	
		PrivateAccessor.setField(imgModel, "url", "some/url");
		PrivateAccessor.setField(imgModel, "altText", "alternative");
		PrivateAccessor.setField(faqModel, "expandAll", "Expand");
		PrivateAccessor.setField(faqModel, "collapseAll", "Collapse");
		PrivateAccessor.setField(faqModel, "showingLabel", "Showing");
		PrivateAccessor.setField(faqModel, "resultsForLabel", "Results for");
		PrivateAccessor.setField(faqModel, "newSearchLabel", "Try New Search");
		PrivateAccessor.setField(sectionsModel, "sectionTitle", "Section 1");
		List<FaqListImageModel> iList = new ArrayList<>();
		iList.add(imgModel);
		PrivateAccessor.setField(listModel, "question", "Question 1");
		PrivateAccessor.setField(listModel, "answer", "Answer 1");
		PrivateAccessor.setField(listModel, "imageList", iList);
		List<FaqSectionsModel> sections = new ArrayList<>();
		sections.add(sectionsModel);
		List<FaqListModel> list = new ArrayList<>();
		list.add(listModel);
		PrivateAccessor.setField(sectionsModel, "questionsMap", list);
		PrivateAccessor.setField(faqModel, "faqData", sections);
		PrivateAccessor.setField(faqModel, "faqHeader", "Frequently Asked Questions");
		PrivateAccessor.setField(faqModel, "showSearchBar", true);
		PrivateAccessor.setField(faqModel, "searchPlaceholder", "Search Here");
		PrivateAccessor.setField(faqModel, "jsonData", "jsonData");
	}

	/**
	 * Tests all getters.
	 * 
	 */
	@Test
	void testAllGetters() {
		faqModel.getAccordionActiveIcon();
		faqModel.getAccordionInActiveIcon();
		assertNotNull(listModel.getImageList());
		assertNotNull(listModel.getQuestion());
		assertNotNull(listModel.getAnswer());
		assertNotNull(sectionsModel.getQuestionsMap());
		assertNotNull(sectionsModel.getSectionTitle());
		assertEquals(true, faqModel.getShowSearchBar());
		assertNotNull(faqModel.getFaqData());
		assertNotNull(faqModel.getCollapseAll());
		assertNotNull(faqModel.getExpandAll());
		assertNotNull(faqModel.getNewSearchLabel());
		assertNotNull(faqModel.getShowingLabel());
		assertNotNull(faqModel.getResultsForLabel());
		assertNotNull(faqModel.getJsonData());
	}

	/**
	 * Tests specific getters on else condition.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testConditions() throws NoSuchFieldException {
		PrivateAccessor.setField(sectionsModel, "questionsMap", null);
		PrivateAccessor.setField(faqModel, "faqData", null);

		assertNotNull(sectionsModel.getQuestionsMap());

		assertNotNull(faqModel.getFaqData());

	}

	/**
	 * Test init.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	@Test
	void testInit() throws JsonProcessingException {
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		faqModel.init();
	}

}
