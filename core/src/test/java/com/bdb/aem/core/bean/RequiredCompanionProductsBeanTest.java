package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;


/**
 * The Class RequiredCompanionProductsBeanTest.
 */
class RequiredCompanionProductsBeanTest {

	/** The required companion bean. */
	RequiredCompanionProductsBean requiredCompanionProductsBean;

    /**
     * Sets the up.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @BeforeEach
    void setUp() throws NoSuchFieldException {
    	requiredCompanionProductsBean = new RequiredCompanionProductsBean();
    	PrivateAccessor.setField(requiredCompanionProductsBean, "productTitle", "productTitle");
    	PrivateAccessor.setField(requiredCompanionProductsBean, "size", "0.2mg");
    	PrivateAccessor.setField(requiredCompanionProductsBean, "catNo", "catNo");
    	PrivateAccessor.setField(requiredCompanionProductsBean, "imgAtlText", "imgAtlText");
    	PrivateAccessor.setField(requiredCompanionProductsBean, "image", "/image");
    	PrivateAccessor.setField(requiredCompanionProductsBean, "ctaLink", "/ctaLink");
    	PrivateAccessor.setField(requiredCompanionProductsBean, "status", "status");
    	PrivateAccessor.setField(requiredCompanionProductsBean, "clone", "clone");
    }
    

    /**
     * Gets the product title.
     *
     * @return the product title
     */
    @Test
    void getProductTitle() {
        Assert.assertEquals(requiredCompanionProductsBean.getProductTitle(), "productTitle");
    }
    
    /**
     * Gets the size.
     *
     * @return the size
     */
    @Test
    void getSize() {
        Assert.assertEquals(requiredCompanionProductsBean.getSize(), "0.2mg");
    }
    
    /**
     * Gets the cat no.
     *
     * @return the cat no
     */
    @Test
    void getCatNo() {
        Assert.assertEquals(requiredCompanionProductsBean.getCatNo(), "catNo");
    }
    
    /**
     * Gets the image alt.
     *
     * @return the image alt
     */
    @Test
    void getImageAlt() {
        Assert.assertEquals(requiredCompanionProductsBean.getImgAtlText(), "imgAtlText");
    }
    
    /**
     * Gets the image.
     *
     * @return the image
     */
    @Test
    void getImage() {
        Assert.assertEquals(requiredCompanionProductsBean.getImage(), "/image");
    }
    
    /**
     * Gets the status.
     *
     * @return the status
     */
    @Test
    void getStatus() {
        Assert.assertEquals(requiredCompanionProductsBean.getStatus(), "status");
    }
    
    /**
     * Gets the clone.
     *
     * @return the clone
     */
    @Test
    void getClone() {
        Assert.assertEquals(requiredCompanionProductsBean.getClone(), "clone");
    }
    
    /**
     * Gets the cta link.
     *
     * @return the cta link
     */
    @Test
    void getCtaLink() {
        Assert.assertEquals(requiredCompanionProductsBean.getCtaLink(), "/ctaLink");
    }


}
