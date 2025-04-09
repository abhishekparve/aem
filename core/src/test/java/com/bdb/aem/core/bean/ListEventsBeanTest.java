package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class ListEventsBeanTest.
 */
class ListEventsBeanTest {

	/** The list events bean. */
	ListEventsBean listEventsBean;
	
	    /**
    	 * Sets the up.
    	 *
    	 * @throws NoSuchFieldException the no such field exception
    	 */
	    @BeforeEach
	    void setUp() throws NoSuchFieldException {
	    	listEventsBean = new ListEventsBean();
	    	listEventsBean.setTitle("BDB Medication Event");
	    	listEventsBean.setLocation("virtual");
	    	listEventsBean.setEventLink("https://example.com");
	    	listEventsBean.setDate("December 25, 2020");
	    }
	    
	    /**
    	 * Gets the country name test.
    	 *
    	 * @return the country name test
    	 */
    	@Test
	    void getCountryNameTest() {
	        Assert.assertEquals(listEventsBean.getTitle(), "BDB Medication Event");
	    }
    	
	    /**
    	 * Gets the locale name test.
    	 *
    	 * @return the locale name test
    	 */
    	@Test
	    void getLocaleNameTest() {
	    	Assert.assertEquals(listEventsBean.getLocation(), "virtual");
	    }
    	
    	/**
	     * Gets the event link test.
	     *
	     * @return the event link test
	     */
    	@Test
	    void getEventLinkTest() {
	    	Assert.assertEquals(listEventsBean.getEventLink(), "https://example.com");
	    }
    	
    	/**
	     * Gets the date test.
	     *
	     * @return the date test
	     */
    	@Test
	    void getDateTest() {
	    	Assert.assertEquals(listEventsBean.getDate(), "December 25, 2020");
	    }
}
