package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import junitx.util.PrivateAccessor;

/**
 * The Class EventBlogModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class EventBlogModelTest {
	
	/** The event blog model. */
	@InjectMocks
	EventBlogModel eventBlogModel;

	/** The multifield section. */
	@Mock
	Resource multifieldSection;

	/** The test value. */
	private String TEST_VALUE = "test";

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		Resource item = Mockito.mock(Resource.class);
		ValueMap valueMap = Mockito.mock(ValueMap.class);

		PrivateAccessor.setField(eventBlogModel, "multifieldSection", multifieldSection);
		PrivateAccessor.setField(eventBlogModel, "selection", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "blogTopic2", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "blogTopic3", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "bannerThumbnailImageBlog", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "bannerImageAltBlog", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "bannerTitleBlog", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "blogDate", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "bannerSubTitle", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "bannerThumbnailImage", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "blogTopic1", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "bannerImageAlt", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "bannerTitle", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "eventStartDate", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "eventEndDate", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "addToHomePage", false);
		PrivateAccessor.setField(eventBlogModel, "eventType", "Select Event Type");
		when(multifieldSection.getChild("item0")).thenReturn(item);
		when(item.getValueMap()).thenReturn(valueMap);
		when(valueMap.get("description")).thenReturn(TEST_VALUE);

		eventBlogModel.setSubHeading(TEST_VALUE);
		eventBlogModel.init();
	}

	/**
	 * Testget.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testget() throws NoSuchFieldException {
		PrivateAccessor.setField(eventBlogModel, "eventType", TEST_VALUE);
		PrivateAccessor.setField(eventBlogModel, "eventTopic", TEST_VALUE);

		assertEquals(TEST_VALUE, eventBlogModel.getSelection());
		assertEquals(TEST_VALUE, eventBlogModel.getBlogTopic1());
		assertEquals(TEST_VALUE, eventBlogModel.getBlogTopic2());
		assertEquals(TEST_VALUE, eventBlogModel.getBlogTopic3());
		assertEquals(TEST_VALUE, eventBlogModel.getBannerThumbnailImageBlog());
		assertEquals(TEST_VALUE, eventBlogModel.getBannerImageAltBlog());
		assertEquals(TEST_VALUE, eventBlogModel.getBannerTitleBlog());
		assertEquals(TEST_VALUE, eventBlogModel.getBlogDate());
		assertEquals(TEST_VALUE, eventBlogModel.getBannerSubTitle());
		assertEquals(TEST_VALUE, eventBlogModel.getBannerThumbnailImage());
		assertEquals(TEST_VALUE, eventBlogModel.getBannerImageAlt());
		assertEquals(TEST_VALUE, eventBlogModel.getBannerTitle());
		assertEquals(TEST_VALUE, eventBlogModel.getEventStartDate());
		assertEquals(TEST_VALUE, eventBlogModel.getEventEndDate());
		assertEquals(TEST_VALUE, eventBlogModel.getSubHeading());
		assertEquals(TEST_VALUE, eventBlogModel.getEventType());
		assertEquals(TEST_VALUE, eventBlogModel.getEventTopic());
		assertEquals(false, eventBlogModel.getAddToHomePage());
		assertEquals(multifieldSection, eventBlogModel.getMultifieldSection());

	}

	/**
	 * Test get methods not null.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testGetMethodsNotNull() throws NoSuchFieldException {
		PrivateAccessor.setField(eventBlogModel, "eventType", "Select Event Type");
		PrivateAccessor.setField(eventBlogModel, "eventTopic", "Select Event Topic");

		assertNotNull(eventBlogModel.getSelection());
		assertNotNull(eventBlogModel.getBlogTopic1());
		assertNotNull(eventBlogModel.getBlogTopic2());
		assertNotNull(eventBlogModel.getBlogTopic3());
		assertNotNull(eventBlogModel.getBannerThumbnailImageBlog());
		assertNotNull(eventBlogModel.getBannerImageAltBlog());
		assertNotNull(eventBlogModel.getBannerTitleBlog());
		assertNotNull(eventBlogModel.getBlogDate());
		assertNotNull(eventBlogModel.getBannerSubTitle());
		assertNotNull(eventBlogModel.getBannerThumbnailImage());
		assertNotNull(eventBlogModel.getBannerImageAlt());
		assertNotNull(eventBlogModel.getBannerTitle());
		assertNotNull(eventBlogModel.getEventStartDate());
		assertNotNull(eventBlogModel.getEventEndDate());
		assertNotNull(eventBlogModel.getSubHeading());
		assertNotNull(eventBlogModel.getEventType());
		assertNotNull(eventBlogModel.getEventTopic());
		assertNotNull(eventBlogModel.getMultifieldSection());
		assertNotNull(eventBlogModel.getAddToHomePage());
	}

}
