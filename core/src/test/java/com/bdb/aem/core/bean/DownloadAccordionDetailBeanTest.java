package com.bdb.aem.core.bean;

import junitx.util.PrivateAccessor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class DownloadAccordionDetailBean.
 */
public class DownloadAccordionDetailBeanTest {

	/** Download Accordion Detail Bean. */
	DownloadAccordionDetailBean downloadAccordionDetailBean;

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		downloadAccordionDetailBean = new DownloadAccordionDetailBean();
		List<DownloadDocumentDetailBean> documentList = new ArrayList<>();
		DownloadDocumentDetailBean documentBean = new DownloadDocumentDetailBean("fileName", "/path/to/file","downloadIcon");
		documentList.add(documentBean);

		PrivateAccessor.setField(downloadAccordionDetailBean, "title", "title");
		PrivateAccessor.setField(downloadAccordionDetailBean, "documents", documentList);

	}
	    

	/**
	 * Gets the file name
	 *
	 * @return the file name
	 */
	@Test
	void getTitle() {
		Assert.assertEquals(downloadAccordionDetailBean.getTitle(), "title");
	}


	/**
	 * Gets the path
	 *
	 * @return path
	 */
	@Test
	void getDocuments() {
		Assert.assertEquals(downloadAccordionDetailBean.getDocuments().size(), 1);
	}

	    
}
