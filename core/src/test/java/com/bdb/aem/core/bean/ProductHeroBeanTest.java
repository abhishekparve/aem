package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;

/**
 * The Class ProductHeroBeanTest.
 */
class ProductHeroBeanTest {

	/** The hero bean. */
	ProductHeroBean heroBean;

	    /**
    	 * Sets the up.
    	 *
    	 * @throws NoSuchFieldException the no such field exception
    	 */
	    @BeforeEach
	    void setUp() throws NoSuchFieldException {
	    	heroBean = new ProductHeroBean();
	    	PrivateAccessor.setField(heroBean, "image", "/test");
	    	PrivateAccessor.setField(heroBean, "desc", "description");
	    	PrivateAccessor.setField(heroBean, "imgAtlText", "imgAtlText");
	    	PrivateAccessor.setField(heroBean, "modelDescription", "modelDescription");
	    	PrivateAccessor.setField(heroBean, "previewImage", "/previewImage");
	    }
	    

	    /**
    	 * Gets the image src.
    	 *
    	 * @return the image src
    	 */
    	@Test
	    void getImageSrc() {
	        Assert.assertEquals(heroBean.getImage(), "/test");
	    }

	       
	    /**
    	 * Gets the description.
    	 *
    	 * @return the description
    	 */
    	@Test
	    void getDescription() {
	    	Assert.assertEquals(heroBean.getDesc(), "description");
	    }
	    
	    /**
    	 * Gets the img alt.
    	 *
    	 * @return the img alt
    	 */
    	@Test
	    void getImgAlt() {
	    	Assert.assertEquals(heroBean.getImgAtlText(), "imgAtlText");
	    }
	    
	    /**
    	 * Gets the model description.
    	 *
    	 * @return the model description
    	 */
    	@Test
	    void getModelDescription() {
	    	Assert.assertEquals(heroBean.getModelDescription(), "modelDescription");
	    }
	    
	    /**
    	 * Gets the preview image.
    	 *
    	 * @return the preview image
    	 */
    	@Test
	    void getPreviewImage() {
	    	Assert.assertEquals(heroBean.getPreviewImage(), "/previewImage");
	    }
	    
	    
}
