package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;

import junitx.util.PrivateAccessor;

/**
 * The Class FaqListModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class FaqListModelTest {
	
	/** The faq list model 2. */
	@InjectMocks
	FaqListModel faqListModel, faqListModel2;
	
	/** The res. */
	@Mock
	Resource resource, res;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The resource resolver. */
	@Mock
	ExternalizerService externalizerService;
	
	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;
	
	/** The value map. */
	@Mock
	ValueMap valueMap;
	
	/** The image list desktop. */
	List<FaqListImageModel> imageList, imageListDesktop;
	
	/** The item 2. */
	@InjectMocks
	FaqListImageModel item1, item2;

	/**
	 * Setup.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setup() throws NoSuchFieldException {
		imageList = new ArrayList<>();
		imageListDesktop = new ArrayList<>();
		PrivateAccessor.setField(faqListModel, "question", "question");
		PrivateAccessor.setField(faqListModel, "answer", "answer");
		PrivateAccessor.setField(faqListModel, "answerPath", "answerPath");
		PrivateAccessor.setField(faqListModel, "imageType", "large");
		
		PrivateAccessor.setField(item1, "url", "url");
		PrivateAccessor.setField(item1, "urlLarge", "urlLarge");
		PrivateAccessor.setField(item1, "imageLinkUrl", "imageLinkUrl");
		PrivateAccessor.setField(item2, "url", "url");
		PrivateAccessor.setField(item2, "urlLarge", "urlLarge");
		PrivateAccessor.setField(item2, "imageLinkUrlLarge", "imageLinkUrlLarge");

		imageList.add(item1);
		imageList.add(item2);
		imageListDesktop.add(item1);
		imageListDesktop.add(item2);
		PrivateAccessor.setField(faqListModel, "imageList", imageList);
		PrivateAccessor.setField(faqListModel, "imageListDesktop", imageListDesktop);
	}
	
	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource("answerPath")).thenReturn(res);
		lenient().when(res.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.get("answerFaq")).thenReturn("answerFaq");
		faqListModel.init();
	}

	/**
	 * Test get question.
	 */
	@Test
	void testGetQuestion() {
		assertEquals("question", faqListModel.getQuestion());
	}

	/**
	 * Test get answer.
	 */
	@Test
	void testGetAnswer() {
		assertEquals("answer", faqListModel.getAnswer());
	}

	/**
	 * Test get image list.
	 */
	@Test
	void testGetImageList() {
		assertNotNull(faqListModel.getImageList());
	}

	/**
	 * Test get is large image.
	 */
	@Test
	void testGetIsLargeImage() {
		assertTrue(faqListModel.getIsLargeImage());
	}
}
