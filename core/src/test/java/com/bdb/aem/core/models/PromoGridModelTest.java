package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class PromoGridModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class PromoGridModelTest {
	
	/** The promo grid model. */
	@InjectMocks
	PromoGridModel promoGridModel;

	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PromoGridDetailsModel promoGridDetailsModel = new PromoGridDetailsModel();
		
		PrivateAccessor.setField(promoGridDetailsModel,"promoGridTitle","Test Title");
		
		ArrayList<PromoGridDetailsModel> promoGridList = new ArrayList<>();
		promoGridList.add(promoGridDetailsModel);
		
		PrivateAccessor.setField(promoGridModel,"cells",promoGridList);
	}
   
    
    /**
     * Test getters.
     */
    @Test
	void testGetters() {
		assertNotNull(promoGridModel.getCells());
		assertNotNull(promoGridModel.getCells().get(0));
	}
    
    /**
     * Test fields.
     */
    @Test
	void testFields() {
		assertEquals("Test Title",promoGridModel.getCells().get(0).getPromoGridTitle());
	}
    
}