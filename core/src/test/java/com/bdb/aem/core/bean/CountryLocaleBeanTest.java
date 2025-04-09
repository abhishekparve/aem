package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class CountryLocaleBeanTest.
 */
class CountryLocaleBeanTest {

	/** The country locale bean. */
	CountryLocaleBean countryLocaleBean;
	
	    /**
    	 * Sets the up.
    	 *
    	 * @throws NoSuchFieldException the no such field exception
    	 */
	    @BeforeEach
	    void setUp() throws NoSuchFieldException {
	    	countryLocaleBean = new CountryLocaleBean();
	    	countryLocaleBean.setCountryName("Canada (English)");
	    	countryLocaleBean.setLocaleName("en-ca");
	    }
	    
	    /**
    	 * Gets the country name test.
    	 *
    	 * @return the country name test
    	 */
    	@Test
	    void getCountryNameTest() {
	        Assert.assertEquals(countryLocaleBean.getCountryName(), "Canada (English)");
	    }
    	
	    /**
    	 * Gets the locale name test.
    	 *
    	 * @return the locale name test
    	 */
    	@Test
	    void getLocaleNameTest() {
	    	Assert.assertEquals(countryLocaleBean.getLocaleName(), "en-ca");
	    }
}
