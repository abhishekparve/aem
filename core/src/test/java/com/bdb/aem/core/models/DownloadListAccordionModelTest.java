package com.bdb.aem.core.models;

import com.bdb.aem.core.util.CommonHelper;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * The Class DownloadListAccordionModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class DownloadListAccordionModelTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	@Mock
	DownloadListDocumentModel documentModel;

	/** The image text model. */
	@InjectMocks
	DownloadListAccordionModel downloadListAccordionModel;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		List<DownloadListDocumentModel> documents = new ArrayList<>();
		documents.add(documentModel);
		PrivateAccessor.setField(downloadListAccordionModel, "accordionTitle", "accordionTitle");
		PrivateAccessor.setField(downloadListAccordionModel, "documents", documents);
	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {
		assertNotNull(downloadListAccordionModel.getAccordionTitle());
		assertNotNull(downloadListAccordionModel.getDocuments());
	}

	/**
	 * Test init.
	 * 
	 * @throws LoginException
	 */
	@Test
	void testInit() throws LoginException, IOException, SolrServerException {
		downloadListAccordionModel.init();
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException, IOException, SolrServerException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		downloadListAccordionModel.init();
	}

}
