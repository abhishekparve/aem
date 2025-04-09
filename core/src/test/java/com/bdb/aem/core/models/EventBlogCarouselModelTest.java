package com.bdb.aem.core.models;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.impl.EventDetailsCarouselServiceImpl;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * The test class Event Blog Carousel Model Test.
 * 
 * @author ronbanerjee
 *
 */

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class EventBlogCarouselModelTest {

	@InjectMocks
	private EventBlogCarouselModel eventBlogCarouselModel;

	@InjectMocks
	private EventBlogCarouselDetailsModel eventBlogDetailsCarouselModel;
	
	 /** The resource resolver. */
    @Mock
    private ResourceResolver resourceResolver;
    
    /** The event carousel service */
    @Mock
    private EventDetailsCarouselServiceImpl eventCarouselService;
    
    @Mock
    private ExternalizerService externalizerService;

    /** The resource resolver factory. */
    @Mock
    private ResourceResolverFactory resourceResolverFactory;
    
    /** The context. */
    @Mock
    ComponentContext context;

	/**
	 * Sets up.
	 * 
	 * @throws NoSuchFieldException
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {

		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "selection", "event");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "searchField", "/content/somefield");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "title", "Title");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "description", "Lorem Ipsum Dolor sit amet");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "subTitle", "Sub title");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "imageUrl", "/some/url");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "imageAltText", "alt text");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "ctaLabel", "Click me");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "ctaUrl", "/some/url");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "pastLabel", "past");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "upcomingLabel", "upcoming");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "currentLabel", "current");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "eventTimeLabel", "Event Time");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "eventStartDate", "2020-10-21T10:00:00.000+05:30");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "eventEndDate", "2020-10-22T10:00:00.000+05:30");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "eventType", "conference");
		List<EventBlogCarouselDetailsModel> slidesList = new ArrayList<>();
		slidesList.add(eventBlogDetailsCarouselModel);
		PrivateAccessor.setField(eventBlogCarouselModel, "slides", slidesList);
		lenient().when(externalizerService.getFormattedUrl("/some/url", resourceResolver)).thenReturn("http://localhost:4502/some/url");
 
	}

	/**
	 * Test init.
	 * 
	 * @throws LoginException
	 */
	@Test
	void testInit() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		eventBlogDetailsCarouselModel.init();
	}
	
	/**
	 * Tests all getters.
	 * 
	 */
	@Test
	void testAllGetters() {
		assertNotNull(eventBlogCarouselModel.getSlides());
		assertNotNull(eventBlogDetailsCarouselModel.getSearchField());
		assertNotNull(eventBlogDetailsCarouselModel.getTitle());
		assertNotNull(eventBlogDetailsCarouselModel.getDescription());
		assertNotNull(eventBlogDetailsCarouselModel.getSubTitle());
		assertNotNull(eventBlogDetailsCarouselModel.getImageUrl());
		assertNotNull(eventBlogDetailsCarouselModel.getImageAltText());
		assertNotNull(eventBlogDetailsCarouselModel.getCtaLabel());
		assertNotNull(eventBlogDetailsCarouselModel.getCtaUrl());
		assertNotNull(eventBlogDetailsCarouselModel.getPastLabel());
		assertNotNull(eventBlogDetailsCarouselModel.getUpcomingLabel());
		assertNotNull(eventBlogDetailsCarouselModel.getCurrentLabel());
		assertNotNull(eventBlogDetailsCarouselModel.getEventTimeLabel());
		assertNotNull(eventBlogDetailsCarouselModel.getEventStartDate());
		assertNotNull(eventBlogDetailsCarouselModel.getEventEndDate());
		assertNotNull(eventBlogDetailsCarouselModel.getEventDate());
		assertNotNull(eventBlogDetailsCarouselModel.getEventType());
		assertNotNull(eventBlogDetailsCarouselModel.getSelection());
	}
	@Test
	void testAllSetters() {
		eventBlogDetailsCarouselModel.setEventType("eventType");
		eventBlogDetailsCarouselModel.setEventEndDate("setEventEndDate");
		eventBlogDetailsCarouselModel.setEventStartDate("setEventStartDate");
		eventBlogDetailsCarouselModel.setCurrentLabel("setCurrentLabel");
		eventBlogDetailsCarouselModel.setUpcomingLabel("setUpcomingLabel");
		eventBlogDetailsCarouselModel.setPastLabel("setPastLabel");
	}
	/**
	 * Test conditions for future - event start date & event end date.
	 * 
	 * @throws NoSuchFieldException
	 */
	@Test
	void testConditionsFutureDate() throws NoSuchFieldException {
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "eventStartDate", "2099-10-21T10:00:00.000+05:30");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "eventEndDate", "2099-10-22T10:00:00.000+05:30");
		assertNotNull(eventBlogDetailsCarouselModel.getEventTimeLabel());
	}
	
	/**
	 * Test conditions for selection type: blog.
	 * 
	 * @throws NoSuchFieldException
	 */
	@Test
	void testConditionsSelectionType() throws NoSuchFieldException {
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "selection", "blog");
		assertNotNull(eventBlogDetailsCarouselModel.getEventTimeLabel());
		
	}
	
	/** 
	 * Test conditions to reach the catch block.
	 * 
	 * @throws NoSuchFieldException
	 */
	@Test
	void testCatchblock() throws NoSuchFieldException {
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "eventStartDate", "2099-10-21");
		PrivateAccessor.setField(eventBlogDetailsCarouselModel, "eventEndDate", "2099-10-22");
		assertNotNull(eventBlogDetailsCarouselModel.getEventTimeLabel());
	}
}
