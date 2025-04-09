package com.bdb.aem.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class GlobalNavigationInformationBeanTest.
 */
class GlobalNavigationInformationBeanTest {
	
	
	/** The navigation information bean. */
	GlobalNavigationInformationBean navigationInformationBean;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		navigationInformationBean = new GlobalNavigationInformationBean();
		List<GlobalNavigationInformationBean> navigationInformationBeanList = new ArrayList<GlobalNavigationInformationBean>();
		navigationInformationBean.setModelImageAltText("test alt text");
		navigationInformationBean.setModelImageCTALabel("CTA Label");
		navigationInformationBean.setModelImageDesc("test description");
		navigationInformationBean.setModelImageLink("/content/bdb/us/en-us/testpage");
		navigationInformationBean.setModelImagePath("/content/dam/image.png");
		navigationInformationBean.setModelImageTitle("test title");
		navigationInformationBean.setProductLabel("test product label");
		navigationInformationBean.setRedirectPath("/content/bdb/us/en-us/testpage");
		navigationInformationBeanList.add(navigationInformationBean);
		navigationInformationBean.setChildren(navigationInformationBeanList);
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(navigationInformationBean.getModelImageAltText(), "test alt text");
		Assert.assertEquals(navigationInformationBean.getModelImageCTALabel(), "CTA Label");
		Assert.assertEquals(navigationInformationBean.getModelImageDesc(), "test description");
		Assert.assertEquals(navigationInformationBean.getModelImageLink(), "/content/bdb/us/en-us/testpage");
		Assert.assertEquals(navigationInformationBean.getModelImagePath(), "/content/dam/image.png");
		Assert.assertEquals(navigationInformationBean.getModelImageTitle(), "test title");
		Assert.assertEquals(navigationInformationBean.getProductLabel(), "test product label");
		Assert.assertEquals(navigationInformationBean.getRedirectPath(), "/content/bdb/us/en-us/testpage");
		Assert.assertNotNull(navigationInformationBean.getChildren());
		
	}

}
