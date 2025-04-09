package com.bdb.aem.core.bean;

import junitx.util.PrivateAccessor;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class DownloadDocumentDetailBean.
 */
public class DownloadDocumentDetailBeanTest {

	/** Download Document Detail Bean. */
	DownloadDocumentDetailBean downloadDocumentBean;

	    /**
    	 * Sets the up.
    	 *
    	 * @throws NoSuchFieldException the no such field exception
    	 */
	    @BeforeEach
	    void setUp() throws NoSuchFieldException {
			downloadDocumentBean = new DownloadDocumentDetailBean();
	    	PrivateAccessor.setField(downloadDocumentBean, "fileName", "fileName");
	    	PrivateAccessor.setField(downloadDocumentBean, "path", "/path/to/file");

	    }
	    

	    /**
    	 * Gets the file name
    	 *
    	 * @return the file name
    	 */
    	@Test
	    void getFileName() {
	        Assert.assertEquals(downloadDocumentBean.getFileName(), "fileName");
	    }

	       
	    /**
    	 * Gets the path
    	 *
    	 * @return path
    	 */
    	@Test
	    void getPath() {
	    	Assert.assertEquals(downloadDocumentBean.getPath(), "/path/to/file");
	    }

	    
}
