package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;

/**
 * The test class for referenceBean.
 */
class ReferenceBeanTest {

	/** The citation bean. */
	ReferenceBean referenceBean;

	    /**
    	 * Sets the up.
    	 *
    	 * @throws NoSuchFieldException the no such field exception
    	 */
    	@BeforeEach
	    void setUp() throws NoSuchFieldException {
	    	referenceBean = new ReferenceBean();
	    	PrivateAccessor.setField(referenceBean, "HPReferenceID", "2345");
	    	PrivateAccessor.setField(referenceBean, "citationdata", "description");
	    	PrivateAccessor.setField(referenceBean, "pubMedId", "12234");
	    }

    	/**
	     * Gets the citation id test.
	     *
	     * @return the citation id test
	     */
	    @Test
	    void getCitationIdTest() {
	        Assert.assertEquals(referenceBean.getHPReferenceID(), "2345");
	    }

    	/**
	     * Gets the description test.
	     *
	     * @return the description test
	     */
	    @Test
	    void getDescriptionTest() {
	    	Assert.assertEquals(referenceBean.getCitationdata(), "description");
	    }
	    
    	/**
	     * Gets the pub med id test.
	     *
	     * @return the pub med id test
	     */
	    @Test
	    void getPubMedIdTest() {
	    	Assert.assertEquals(referenceBean.getPubMedId(), "12234");
	    }
	    
}
