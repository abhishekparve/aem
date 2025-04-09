package com.bdb.aem.core.bean;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

/**
 * The Class ProductsOrderLookupBeanTest.
 */
class ProductsOrderLookupBeanTest {
	
	/** The products order lookup bean. */
	ProductsOrderLookupBean productsOrderLookupBean;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		productsOrderLookupBean = new ProductsOrderLookupBean();
		productsOrderLookupBean.setDefaultPRDImage("defaultPRDImage");
		productsOrderLookupBean.setDefaultPRDImageAltText("defaultPRDImageAltText");
		productsOrderLookupBean.setEstDeliveryDate("estDeliveryDate");
		productsOrderLookupBean.setProductsHeading("productsHeading");
		productsOrderLookupBean.setQuantityLabelProducts("quantityLabelProducts");
		productsOrderLookupBean.setPromocodeLabel("promocode");
		productsOrderLookupBean.setYouSavedLabel("youSaved");
		productsOrderLookupBean.setOnLabel("on");
	}
	
	/**
	 * Test.
	 */
	@Test
	void test() {
		assertEquals("defaultPRDImage", productsOrderLookupBean.getDefaultPRDImage());
		assertEquals("defaultPRDImageAltText", productsOrderLookupBean.getDefaultPRDImageAltText());
		assertEquals("estDeliveryDate", productsOrderLookupBean.getEstDeliveryDate());
		assertEquals("productsHeading", productsOrderLookupBean.getProductsHeading());
		assertEquals("quantityLabelProducts", productsOrderLookupBean.getQuantityLabelProducts());
		assertEquals("promocode", productsOrderLookupBean.getPromocodeLabel());
		assertEquals("youSaved", productsOrderLookupBean.getYouSavedLabel());
		assertEquals("on", productsOrderLookupBean.getOnLabel());
	}
}
