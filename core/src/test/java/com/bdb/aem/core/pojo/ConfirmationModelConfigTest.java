package com.bdb.aem.core.pojo;

import com.bdb.aem.core.models.AccessLinksModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ConfirmationModelConfigTest.
 */
class ConfirmationModelConfigTest {
	
	/** The confirmation test config. */
	ConfirmationModelConfig confirmationTestConfig;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		List<AccessLinksModel> accessLinksModelList = new ArrayList<>();

		AccessLinksModel link =  new  AccessLinksModel("linkLabel","linkIcon","linkAltText");
		accessLinksModelList.add(link);

		confirmationTestConfig = new ConfirmationModelConfig("image", "imageAltText","confirmationTitle","content", "accessText","linksTitle", accessLinksModelList,"purchasingAccountLabel", "skipAndBrowseLabel", "skipAndBrowseUrl");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(confirmationTestConfig.getImage(), "image");
		Assert.assertEquals(confirmationTestConfig.getImageAltText(), "imageAltText");
		Assert.assertEquals(confirmationTestConfig.getConfirmationTitle(),"confirmationTitle");
		Assert.assertEquals(confirmationTestConfig.getContent(),"content");
		Assert.assertEquals(confirmationTestConfig.getLinksTitle(),"linksTitle");
		Assert.assertNotNull(confirmationTestConfig.getAccessText());
		Assert.assertNotNull(confirmationTestConfig.getPurchasingAccountLabel());
		Assert.assertNotNull(confirmationTestConfig.getSkipAndBrowseLabel());
		Assert.assertNotNull(confirmationTestConfig.getSkipAndBrowseUrl());
		Assert.assertNotNull(confirmationTestConfig.toString());
	}

}
