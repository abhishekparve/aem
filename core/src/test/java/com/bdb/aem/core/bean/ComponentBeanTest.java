package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;
import junitx.util.PrivateAccessor;
	

/**
 * The Class ComponentBeanTest.
 */
class ComponentBeanTest {

	/** The Component bean. */
	ComponentBean componentBean;

    /**
     * Sets the up.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @BeforeEach
    void setUp() throws NoSuchFieldException {
    	componentBean = new ComponentBean();
    	PrivateAccessor.setField(componentBean, "description", "description");
    	PrivateAccessor.setField(componentBean, "size", "0.2mg");
    	PrivateAccessor.setField(componentBean, "componentMaterialNumber", "componentMaterialNumber");
    	PrivateAccessor.setField(componentBean, "tdsCloneName", "tdsCloneName");
    	PrivateAccessor.setField(componentBean, "isoType", "isoType");
    }
    
    /**
     * Gets the description.
     *
     * @return the description
     */
    @Test
    void getDescription() {
    	Assert.assertEquals(componentBean.getDescription(),"description");
    }
    
    /**
     * Gets the tds clone name.
     *
     * @return the tds clone name
     */
    @Test
    void getTdsCloneName() {
        Assert.assertEquals(componentBean.getTdsCloneName(),"tdsCloneName");
    }
    
    /**
     * Gets the iso type.
     *
     * @return the iso type
     */
    @Test
    void getIsoType() {
    	Assert.assertEquals(componentBean.getIsoType(),"isoType");
    }
    
    /**
     * Gets the size.
     *
     * @return the size
     */
    @Test
    void getSize() {
    	Assert.assertEquals(componentBean.getSize(),"0.2mg");
    }
    
    /**
     * Gets the component material number.
     *
     * @return the component material number
     */
    @Test
    void getComponentMaterialNumber() {
    	Assert.assertEquals(componentBean.getComponentMaterialNumber(),"componentMaterialNumber");
    }


}
