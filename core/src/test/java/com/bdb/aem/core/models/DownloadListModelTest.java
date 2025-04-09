package com.bdb.aem.core.models;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.GetDocumentsService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * The Class DownloadListModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class DownloadListModelTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	@Mock
	ExternalizerService externalizerService;

	/** The image text model. */
	@InjectMocks
	DownloadListModel downloadListModel;

	/** The Accordion Model. */
	@Mock
	DownloadListAccordionModel accordion;

	/** The Document Model. */
	@Mock
	DownloadListDocumentModel document;

	/**
	 * The resource resolver.
	 */
	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Resource resource;

	@Mock
	Asset asset;
	@Mock
	GetDocumentsService solrDocumentsService;
	@Mock
	SolrSearchService solrSearchService;
	@Mock
	SolrDocumentList solrDocumentList;
	@Mock
	Iterator<SolrDocument> iterator;
	@Mock
	SolrDocument solrDocument;
	@Mock
	Iterator<Map.Entry<String, Object>> itr;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		List<DownloadListAccordionModel> accordions = new ArrayList<>();
		List<DownloadListDocumentModel> documents = new ArrayList<>();
		List<String> docTypes = new ArrayList<String>();
		docTypes.add("pdf");
		lenient().when(accordion.getAccordionTitle()).thenReturn("accordionTitle");
		lenient().when(document.getDocumentTitle()).thenReturn("docTitle");
		lenient().when(document.getDownloadIcon()).thenReturn("downloadIcon");
		lenient().when(document.getDocumentLink()).thenReturn("/link");
		lenient().when(document.getProductName()).thenReturn("Biotin");
		lenient().when(document.getProductTypeSelectionList()).thenReturn(docTypes);
		documents.add(document);
		accordions.add(accordion);
		lenient().when(accordion.getDocuments()).thenReturn(documents);
		PrivateAccessor.setField(downloadListModel, "image", true);
		PrivateAccessor.setField(downloadListModel, "accordions", accordions);
		lenient().when(resource.getName()).thenReturn("downloadListModel");
		lenient().when(resource.getParent()).thenReturn(resource);

	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {
		// assertNotNull(downloadListModel.getAccordionList());
		assertTrue(downloadListModel.isImage());
		downloadListModel.getArticleId();
		downloadListModel.getSectionTitle();
	}

	/**
	 * Test init.
	 * 
	 * @throws LoginException
	 */
	@Test
	void testInit() throws LoginException, IOException, SolrServerException {
		lenient().when(document.getUploadTypeSelection()).thenReturn("manual");
		downloadListModel.init();
	}

	@Test
	void testInitElse() throws LoginException, IOException, SolrServerException {
		lenient().when(document.getUploadTypeSelection()).thenReturn("auto");
		lenient().when(solrDocumentsService.getDocumentsByProductNameAndType("Biotin", "pdf", "us", solrSearchService))
				.thenReturn(solrDocumentList);
		lenient().when(solrDocumentList.iterator()).thenReturn(iterator);
		lenient().when(iterator.hasNext()).thenReturn(true, false);
		lenient().when(iterator.next()).thenReturn(solrDocument);
		lenient().when(solrDocument.iterator()).thenReturn(itr);
		lenient().when(itr.hasNext()).thenReturn(false);
		downloadListModel.init();
		downloadListModel.getAccordionList();
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException, IOException, SolrServerException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		downloadListModel.init();
	}

	@Test
	void testManualUpload() {
		assertNotNull(downloadListModel.fetchManualUploadDocumentDetails("Document Title", "/path/to/link", "downloadIcon"));
	}

	@Test
	void testManualUploadWithoutDocTitle() {
		when(resourceResolver.getResource("/path/to/link")).thenReturn(resource);
		when(resource.adaptTo(Asset.class)).thenReturn(asset);
		when(asset.getMetadataValue("dc:title")).thenReturn("docTitle");
		assertNotNull(downloadListModel.fetchManualUploadDocumentDetails(null, "/path/to/link","downloadIcon"));

	}

}
