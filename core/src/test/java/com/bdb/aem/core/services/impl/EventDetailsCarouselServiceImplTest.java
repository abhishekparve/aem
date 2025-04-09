package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.EventBlogCarouselDetailsModel;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The test class Event Details Carousel Service Impl Test.
 * 
 * @author ronbanerjee
 *
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class EventDetailsCarouselServiceImplTest {

	/** The corousel service */
	@InjectMocks
	private EventDetailsCarouselServiceImpl carouselService;

	/** The sling model */
	@Mock
	private EventBlogCarouselDetailsModel slingModel;

	/** The resolver */
	@Mock
	private ResourceResolver resolver;

	/** The  resource */
	@Mock
	private Resource resource;

	/** The value map */
	@Mock
	private ValueMap valueMap;

	/** The resource iterator */
	@Mock
	private Iterator<Resource> it;

	/**
	 * Sets up.
	 * 
	 * @throws NoSuchFieldException
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {

		lenient().when(slingModel.getSearchField()).thenReturn("/some/url");
		lenient().when(slingModel.getSelection()).thenReturn("event");
		lenient().when(resolver.getResource("/some/url/jcr:content")).thenReturn(resource);
		lenient().when(resolver.getResource("/some/url/jcr:content/root")).thenReturn(resource);
		lenient().when(resource.getValueMap()).thenReturn(valueMap);
		lenient().when(resource.listChildren()).thenReturn(it);
		lenient().when(it.hasNext()).thenReturn(true);
		lenient().when(it.next()).thenReturn(resource);
		lenient().when(valueMap.get("current", String.class)).thenReturn("Current");
		lenient().when(valueMap.get("past", String.class)).thenReturn("Past");
		lenient().when(valueMap.get("upcoming", String.class)).thenReturn("Upcoming");
		lenient().when(resource.getResourceType()).thenReturn("bdb-aem/proxy/components/content/eventblogDetails");

	}

	/**
	 * Tests updateSlingModel()
	 * 
	 */
	@Test
	void testUpdateSlingModel() {
		carouselService.updateSlingModel(slingModel, resolver);
	}

	/** Tests updateSlingModel with conditions.
	 * 
	 */
	@Test
	void testUpdateSlingModelWithCondition() {
		lenient().when(slingModel.getSelection()).thenReturn("blog");
		lenient().when(it.hasNext()).thenReturn(false);
		carouselService.updateSlingModel(slingModel, resolver);
	}

	

}
