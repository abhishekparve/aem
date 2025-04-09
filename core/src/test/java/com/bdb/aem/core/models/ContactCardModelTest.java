package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
 * The class Contact Card Model Test.
 * 
 */

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ContactCardModelTest {

	/** The card model */
	@InjectMocks
	private ContactCardModel cardModel;
	
	/** The card list model */
	@InjectMocks
	private ContactCardListModel cardListModel;
	
	/** The card mode model */
	@InjectMocks
	private ContactCardModesModel cardModeModel;
	
	/**
	 * Sets up.
	 * 
	 * @throws NoSuchFieldException
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		
		PrivateAccessor.setField(cardModeModel, "modeTitle", "Some Title");
		PrivateAccessor.setField(cardModeModel, "modeDescription", "Some Description");
		PrivateAccessor.setField(cardListModel, "icon", "some/url");
		PrivateAccessor.setField(cardListModel, "contactTypeTitle", "By Phone");
		PrivateAccessor.setField(cardModel, "pageTitle", "Contact Us");
		PrivateAccessor.setField(cardModel, "pageSubTitle", "Contact Us Sub Title");
		List<ContactCardListModel> cardList = new ArrayList<>();
		cardList.add(cardListModel);
		PrivateAccessor.setField(cardModel, "contactCards", cardList);
		List<ContactCardModesModel> modesList = new ArrayList<>();
		modesList.add(cardModeModel);
		PrivateAccessor.setField(cardListModel,"modes" , modesList);
	}
	
	/**
	 * Tests all getters. 
	 */
	@Test
	void testAllGetters() {
		assertNotNull(cardModel.getContactCards());
		assertNotNull(cardModel.getPageTitle());
		assertNotNull(cardModel.getContactCards());
		assertNotNull(cardListModel.getContactTypeTitle());
		assertNotNull(cardListModel.getIcon());
		assertNotNull(cardListModel.getModes());
		assertNotNull(cardModeModel.getModeDescription());
		assertNotNull(cardModeModel.getModeTitle());
	}
	
	@Test
	void testInit() {
		cardModel.init();
	}
}
