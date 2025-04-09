package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import junitx.util.PrivateAccessor;

/**
 * The test class for SAPProductVariantPropertyBean.
 */
class SAPProductVariantPropertyBeanTest {

	/** The sap product variant property bean. */
	SAPProductVariantPropertyBean sapProductVariantPropertyBean;

    	/**
	     * Sets the up.
	     *
	     * @throws NoSuchFieldException the no such field exception
	     */
	    @BeforeEach
	    void setUp() throws NoSuchFieldException {
    		sapProductVariantPropertyBean = new SAPProductVariantPropertyBean();
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "batchManagementIndicator", "batchManagementIndicator");
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "code", "code");
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "ean", "ean");
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "dangerousGoodsIndicator", "dangerousGoodsIndicator");
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "grossWeight", 0.22);
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "hazardousCode", "hazardousCode");
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "hazardousDescription", "hazardousDescription");
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "netWeight", 0.12);
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "shippingConditionCode", "shippingConditionCode");
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "shippingConditionDescription", "shippingConditionDescription");
	    	PrivateAccessor.setField(sapProductVariantPropertyBean, "temperatureConditionIndicator", "temperatureConditionIndicator");
	    }

	    /**
    	 * Test getters.
    	 */
    	@Test
	    void testGetters() { 
	        Assert.assertEquals(sapProductVariantPropertyBean.getBatchManagementIndicator(), "batchManagementIndicator");
	        Assert.assertEquals(sapProductVariantPropertyBean.getCode(), "code");
	        Assert.assertEquals(sapProductVariantPropertyBean.getDangerousGoodsIndicator(), "dangerousGoodsIndicator");
	        Assert.assertEquals(sapProductVariantPropertyBean.getEan(), "ean");
	        Assert.assertEquals(sapProductVariantPropertyBean.getGrossWeight(), 0.22);
	        Assert.assertEquals(sapProductVariantPropertyBean.getHazardousCode(), "hazardousCode");
	        Assert.assertEquals(sapProductVariantPropertyBean.getHazardousDescription(), "hazardousDescription");
	        Assert.assertEquals(sapProductVariantPropertyBean.getNetWeight(), 0.12);
	        Assert.assertEquals(sapProductVariantPropertyBean.getShippingConditionCode(), "shippingConditionCode");
	        Assert.assertEquals(sapProductVariantPropertyBean.getShippingConditionDescription(), "shippingConditionDescription");
	        Assert.assertEquals(sapProductVariantPropertyBean.getTemperatureConditionIndicator(), "temperatureConditionIndicator");
	    }

}
