package com.bdb.aem.core.models;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.util.CommonHelper;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class DownloadListAccordionModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class DownloadListDocumentModelTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The image text model. */
	@InjectMocks
	DownloadListDocumentModel downloadListDocumentModel;
	
	@Mock
	DownloadListDocumentModel downloadListDocument;
	
	@Mock
	Resource resource;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		List<String> docTypes = new ArrayList<String>();
		docTypes.add("pdf");
		PrivateAccessor.setField(downloadListDocumentModel, "uploadTypeSelection", "uploadTypeSelection");
		PrivateAccessor.setField(downloadListDocumentModel, "productName", "productName");
		PrivateAccessor.setField(downloadListDocumentModel, "documentTitle", "documentTitle");
		PrivateAccessor.setField(downloadListDocumentModel, "documentLink", "documentLink");
		PrivateAccessor.setField(downloadListDocumentModel, "downloadIcon", "downloadIcon");
		PrivateAccessor.setField(downloadListDocumentModel, "productTypeSelectionList", docTypes);
		PrivateAccessor.setField(downloadListDocumentModel, "productTypeSelection", resource);
	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {
		assertNotNull(downloadListDocumentModel.getDocumentLink());
		assertNotNull(downloadListDocumentModel.getDocumentTitle());
		assertNotNull(downloadListDocumentModel.getDownloadIcon());
		assertNotNull(downloadListDocumentModel.getProductName());
		assertNotNull(downloadListDocumentModel.getUploadTypeSelection());
		assertNotNull(downloadListDocumentModel.getProductTypeSelectionList());
	}

	/**
	 * Test init.
	 * 
	 * @throws LoginException
	 */
	@Test
	void testInit() throws LoginException, IOException, SolrServerException {
		lenient().when(downloadListDocument.getProductTypeSelection()).thenReturn(resource);
		lenient().when(resource.adaptTo(String[].class)).thenReturn(new String[] {"pdf"});
		downloadListDocumentModel.init();
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException, IOException, SolrServerException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		lenient().when(downloadListDocument.getProductTypeSelection()).thenReturn(resource);
		lenient().when(resource.adaptTo(String[].class)).thenReturn(new String[] {"pdf"});
		downloadListDocumentModel.init();
	}

}
