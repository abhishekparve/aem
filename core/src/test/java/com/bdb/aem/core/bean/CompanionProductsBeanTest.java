package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;

/**
 * The Class CompanionProductsBeanTest.
 */
class CompanionProductsBeanTest {

	/** The companion bean. */
	CompanionProductsBean companionBean;

    /**
     * Sets the up.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @BeforeEach
    void setUp() throws NoSuchFieldException {
    	companionBean = new CompanionProductsBean();
    	PrivateAccessor.setField(companionBean, "productTitle", "productTitle");
    	PrivateAccessor.setField(companionBean, "size", "0.2mg");
    	PrivateAccessor.setField(companionBean, "catNo", "catNo");
    	PrivateAccessor.setField(companionBean, "imgAtlText", "imgAtlText");
    	PrivateAccessor.setField(companionBean, "image", "/image");
    }
    

    /**
     * Gets the product title.
     *
     * @return the product title
     */
    @Test
    void getProductTitle() {
        Assert.assertEquals(companionBean.getProductTitle(), "productTitle");
    }
    
    /**
     * Gets the size.
     *
     * @return the size
     */
    @Test
    void getSize() {
        Assert.assertEquals(companionBean.getSize(), "0.2mg");
    }
    
    /**
     * Gets the cat no.
     *
     * @return the cat no
     */
    @Test
    void getCatNo() {
        Assert.assertEquals(companionBean.getCatNo(), "catNo");
    }
    
    /**
     * Gets the image alt.
     *
     * @return the image alt
     */
    @Test
    void getImageAlt() {
        Assert.assertEquals(companionBean.getImgAtlText(), "imgAtlText");
    }
    
    /**
     * Gets the image.
     *
     * @return the image
     */
    @Test
    void getImage() {
        Assert.assertEquals(companionBean.getImage(), "/image");
    }


}
