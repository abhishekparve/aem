package com.bdb.aem.core.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The Class BDBProfileMenuMutlifieldModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class BDBProfileMenuMutlifieldModelTest {

	/** The bdb profile menu mutlifield model. */
	BDBProfileMenuMutlifieldModel bdbProfileMenuMutlifieldModel;

	/**
	 * Test.
	 */
	@Test
	void test() {
		bdbProfileMenuMutlifieldModel = new BDBProfileMenuMutlifieldModel("label", "altText", "imgSrc", "imgSrcM", "id",
				"redirectURL");
		assertEquals("label", bdbProfileMenuMutlifieldModel.getLabel());
		assertEquals("altText", bdbProfileMenuMutlifieldModel.getAltText());
		assertEquals("imgSrc", bdbProfileMenuMutlifieldModel.getImgSrc());
		assertEquals("imgSrcM", bdbProfileMenuMutlifieldModel.getImgSrcM());
		assertEquals("redirectURL", bdbProfileMenuMutlifieldModel.getRedirectURL());
		assertEquals("id", bdbProfileMenuMutlifieldModel.getId());
	}

}
