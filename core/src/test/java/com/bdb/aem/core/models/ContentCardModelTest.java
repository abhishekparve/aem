package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class ContentCardModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ContentCardModelTest {

    
    
    /** The content card model. */
    @InjectMocks
    ContentCardModel contentCardModel;
    
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
	void setUp() throws Exception {
    	ContentCardMultifieldModel contentCardMultifieldModel = new ContentCardMultifieldModel();
		PrivateAccessor.setField(contentCardMultifieldModel,"altText","testAltText");
		
		ArrayList<ContentCardMultifieldModel> contentCardModelList = new ArrayList<>();
		contentCardModelList.add(contentCardMultifieldModel);
		
		PrivateAccessor.setField(contentCardModel,"contentCard",contentCardModelList);
		PrivateAccessor.setField(contentCardModel,"sectionTitleText","testSectionTitleText");
		PrivateAccessor.setField(contentCardModel,"sectionDescription","testSectionDescription");
		PrivateAccessor.setField(contentCardModel,"subtitleText","testSubtitleText");
	}
    
    /**
     * Test fields.
     */
    @Test
	void testFields() {
		assertEquals("testAltText",contentCardModel.getContentCard().get(0).getAltText());
		assertEquals("testSectionTitleText",contentCardModel.getSectionTitleText());
		assertEquals("testSectionDescription",contentCardModel.getSectionDescription());
		assertEquals("testSubtitleText",contentCardModel.getSubtitleText());
	}

}
