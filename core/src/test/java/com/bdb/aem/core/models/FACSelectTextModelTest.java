package com.bdb.aem.core.models;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Junit Test for {@link FACSelectTextModel}
 * 
 * @author ronbanerjee
 *
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FACSelectTextModelTest {

	@InjectMocks
	private FACSelectTextModel fsText;

	@InjectMocks
	private FACGatingImages fsGating;
	
	/**
	 * Set up Method
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
	  
	   PrivateAccessor.setField(fsGating, "imageLabel", "");
	   PrivateAccessor.setField(fsGating, "imageUrl", "");
	   PrivateAccessor.setField(fsGating, "notesHeading", "");
	   PrivateAccessor.setField(fsGating, "notesDescription", "");
	   PrivateAccessor.setField(fsGating, "permLabel", "");
	   PrivateAccessor.setField(fsGating, "permUrl", "");
	   PrivateAccessor.setField(fsGating, "valueText", "");
	   PrivateAccessor.setField(fsGating, "valueHref", "");
	   
	   List<FACGatingImages>  fsGatingList1 = new ArrayList<>();
	   List<FACGatingImages>  fsGatingList2 = new ArrayList<>();
	   List<FACGatingImages>  fsGatingList3 = new ArrayList<>();
	   List<FACGatingImages>  fsGatingList4 = new ArrayList<>();
	   fsGatingList1.add(fsGating);
	   fsGatingList2.add(fsGating);
	   fsGatingList3.add(fsGating);
	   fsGatingList4.add(fsGating);
	   
	   PrivateAccessor.setField(fsText, "gatingImages", fsGatingList1);
	   PrivateAccessor.setField(fsText, "notesDescription", fsGatingList2);
	   PrivateAccessor.setField(fsText, "permBuffers", fsGatingList3);
	   PrivateAccessor.setField(fsText, "entrezLinks", fsGatingList4);
	   
	    
	}
	
	/**
	 * Tests all getters.
	 */
	@Test
	public void testGetters() {
		assertNotNull(fsGating.getImageLabel());
		assertNotNull(fsGating.getImageUrl());
		assertNotNull(fsGating.getNotesDescription());
		assertNotNull(fsGating.getNotesHeading());
		assertNotNull(fsGating.getPermLabel());
		assertNotNull(fsGating.getPermUrl());
		assertNotNull(fsGating.getValueHref());
		assertNotNull(fsGating.getValueText());
		assertNotNull(fsText.getGatingImages());
		assertNotNull(fsText.getPermBuffers());
		assertNotNull(fsText.getEntrezLinks());
		assertNotNull(fsText.getNotesDescription());
	}
}
