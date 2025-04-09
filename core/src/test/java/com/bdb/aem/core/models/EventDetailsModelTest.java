package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class EventDetailsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class EventDetailsModelTest {

	/** The event details model. */
	@InjectMocks
	private EventDetailsModel eventDetailsModel;
	
	 /** The resource resolver. */
    @Mock
    private ResourceResolver resourceResolver;
    
    /** The externalizer service. */
    @Mock
    private ExternalizerService externalizerService;

    /** The resource resolver factory. */
    @Mock
    private ResourceResolverFactory resourceResolverFactory;
    
    /** The context. */
    @Mock
    ComponentContext context;
    
    long time=8;
	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		
		MultifieldTextModel multifieldTextModel = new MultifieldTextModel();
		List<MultifieldTextModel> multifieldList = new ArrayList<>();
		multifieldList.add(multifieldTextModel);
		PrivateAccessor.setField(eventDetailsModel, "multifieldSection", multifieldList);
		
		EventDateAndTimeModel dateAndTimeModel = new EventDateAndTimeModel();
		List<EventDateAndTimeModel> dateAndTimeList = new ArrayList<>();
		dateAndTimeList.add(dateAndTimeModel);
		PrivateAccessor.setField(eventDetailsModel, "eventDateAndTimeLabel", dateAndTimeList);
		
		PrivateAccessor.setField(eventDetailsModel, "bannerImage", "/some/url");
		PrivateAccessor.setField(eventDetailsModel, "bannerThumbnailImage", "/some/url");
		PrivateAccessor.setField(eventDetailsModel, "speakerImage", "/some/url");
		PrivateAccessor.setField(eventDetailsModel, "bannerURL", "/some/url");
		PrivateAccessor.setField(eventDetailsModel, "dateIcon", "/some/url");
		PrivateAccessor.setField(eventDetailsModel, "timeIcon", "/some/url");
		PrivateAccessor.setField(eventDetailsModel, "locationIcon", "/some/url");
		PrivateAccessor.setField(eventDetailsModel, "ctaUrl", "/some/url");
		PrivateAccessor.setField(eventDetailsModel, "eventUrlCta", "/some/url");
		PrivateAccessor.setField(eventDetailsModel, "eventUrlCtaAdd", "/some/url");
		PrivateAccessor.setField(eventDetailsModel, "eventTimeLabel", "past");
		
		lenient().when(externalizerService.getFormattedUrl("/some/url", resourceResolver)).thenReturn("http://localhost:4502/some/url");
	}

	
	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {
		assertNotNull(eventDetailsModel.getMultifieldSection());
		assertNotNull(eventDetailsModel.getMultifieldSection().get(0));
		assertNotNull(eventDetailsModel.getEventDateAndTimeLabel());
		assertNotNull(eventDetailsModel.getEventDateAndTimeLabel().get(0));
		
		assertNotNull(eventDetailsModel.getBannerImage());
		assertNotNull(eventDetailsModel.getBannerThumbnailImage());
		assertNotNull(eventDetailsModel.getBannerURL());
		assertNotNull(eventDetailsModel.getSpeakerImage());
		assertNotNull(eventDetailsModel.getDateIcon());
		assertNotNull(eventDetailsModel.getTimeIcon());
		assertNotNull(eventDetailsModel.getLocationIcon());
		assertNotNull(eventDetailsModel.getCtaUrl());
		assertNotNull(eventDetailsModel.getEventUrlCta());
		assertNotNull(eventDetailsModel.getEventUrlCtaAdd());
		assertNotNull(eventDetailsModel.getEventTimeLabel());
		assertNotNull(eventDetailsModel.getEventStartDate());
		
	}
	
	/**
	 * Test conditions future date.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	
	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
    @Test
    void testInit() throws LoginException,Exception{
    	PrivateAccessor.setField(eventDetailsModel, "multifieldEventEndDate", "2099-10-22T10:00:00.000+05:30");
    	PrivateAccessor.setField(eventDetailsModel, "multifieldEventStartDate", "2010-10-21T17:00:00.000+05:30");
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        eventDetailsModel.init();
    }
    @Test
    void testInitElse() throws LoginException,Exception{
    	PrivateAccessor.setField(eventDetailsModel, "multifieldEventEndDate", "2010-10-22T17:00:00.000+05:30");
    	PrivateAccessor.setField(eventDetailsModel, "multifieldEventStartDate", "2099-10-21T10:00:00.000+05:30");
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        eventDetailsModel.init();
    }
    @Test
    void testInitElseIf() throws LoginException,Exception{
    	PrivateAccessor.setField(eventDetailsModel, "multifieldEventEndDate", "2010-10-22T17:00:00.000+05:30");
    	PrivateAccessor.setField(eventDetailsModel, "multifieldEventStartDate", "2099-10-21T10:00:00.000+05:30");
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        eventDetailsModel.init();
    }
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		eventDetailsModel.init();
	}
}