package com.bdb.aem.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.Externalizer;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;


/**
 * The Class PageCollectionServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PageCollectionServiceImplTest {

	/** The page collection service impl. */
	@InjectMocks
	private PageCollectionServiceImpl pageCollectionServiceImpl;
	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock Resource. */
	@Mock
	Resource resource;

	/** The session. */
	@Mock
	Session session;

	/** The page query. */
	@Mock
	Query pageQuery;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The page result. */
	@Mock
	SearchResult pageResult;

	/** The page iterator. */
	@Mock
	Iterator<Resource> pageIterator;

	/** The value map. */
	@Mock
	ValueMap valueMap;

	/** The page manager. */
	@Mock
	PageManager pageManager;

	/** The page. */
	@Mock
	Page page;

	/** The externalizer. */
	@Mock
	Externalizer externalizer;

	/** The resource path. */
	private String RESOURCE_PATH = "/content/bd/language-masters/en/products";

	/** The resource root page path. */
	private String RESOURCE_ROOT_PAGE_PATH = "/content/bdb/language-masters/en/products";

	/** The cq xf variant type. */
	private String CQ_XF_VARIANT_TYPE = "cq:xfVariantType";

	/** The cq xf variant value. */
	private String CQ_XF_VARIANT_VALUE = "DW";

	/** The list resource. */
	private List<Resource> listResource;

	/** The page list short URL. */
	private List<String> pageListShortURL;

	/** The jcr content. */
	private String JCR_CONTENT = "/content/experience-fragments/bdb/language-masters/en/products/jcr:content";
	/** The resource path xf root. */
	private String RESOURCE_PATH_XF_ROOT = "/content/experience-fragments/bdb/language-masters/en/products";

	/** The resource path bdb conf root path. */
	private String RESOURCE_PATH_BDB_CONF_ROOT_PATH = "/conf/bdb-aem/settings/wcm/templates";

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		listResource = new ArrayList<>();
		listResource.add(resource);
		listResource.add(resource);

		pageListShortURL = new ArrayList<>();
		pageListShortURL.add(RESOURCE_PATH);
		pageListShortURL.add(RESOURCE_PATH_XF_ROOT);
	}

	/**
	 * Test get replication pages.
	 */
	@Test
	void testGetReplicationPages() {

		assertNotNull(pageCollectionServiceImpl.getReplicationPages(RESOURCE_PATH, resourceResolver));
	}

	/**
	 * Test get replication pages for XF root.
	 */
	@Test
	void testGetReplicationPagesForXFRoot() {
		when(resourceResolver.getResource(JCR_CONTENT)).thenReturn(resource);
		when(resource.getValueMap()).thenReturn(valueMap);
		when(valueMap.get(CQ_XF_VARIANT_TYPE, String.class)).thenReturn(CQ_XF_VARIANT_VALUE);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(pageQuery);
		when(pageQuery.getResult()).thenReturn(pageResult);
		when(pageResult.getResources()).thenReturn(pageIterator);
		when(pageIterator.hasNext()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
		when(pageIterator.next()).thenReturn(resource);
		when(pageManager.getContainingPage(resource)).thenReturn(page);
		assertNotNull(pageCollectionServiceImpl.getReplicationPages(RESOURCE_PATH_XF_ROOT, resourceResolver));
	}

	/**
	 * Test get replication pages for XF root with null variant.
	 */
	@Test
	void testGetReplicationPagesForXFRootWithNullVariant() {
		when(resourceResolver.getResource(JCR_CONTENT)).thenReturn(resource);
		when(resource.getValueMap()).thenReturn(valueMap);
		when(resourceResolver.getResource(RESOURCE_PATH_XF_ROOT)).thenReturn(resource);
		when(resource.getChildren()).thenReturn(listResource);
		when(resource.getName()).thenReturn(RESOURCE_PATH_XF_ROOT);
		assertNotNull(pageCollectionServiceImpl.getReplicationPages(RESOURCE_PATH_XF_ROOT, resourceResolver));
	}

	/**
	 * Test get replication pages for root path.
	 */
	@Test
	void testGetReplicationPagesForRootPath() {

		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(pageQuery);
		when(pageQuery.getResult()).thenReturn(pageResult);
		when(pageResult.getResources()).thenReturn(pageIterator);
		when(pageIterator.hasNext()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
		when(pageIterator.next()).thenReturn(resource);
		assertNotNull(
				pageCollectionServiceImpl.getReplicationPages(RESOURCE_PATH_BDB_CONF_ROOT_PATH, resourceResolver));
	}

	/**
	 * Test get replication pages root page path.
	 */
	@Test
	void testGetReplicationPagesRootPagePath() {

		assertNotNull(pageCollectionServiceImpl.getReplicationPages(RESOURCE_ROOT_PAGE_PATH, resourceResolver));
	}

	/**
	 * Test get short urls.
	 */
	@Test
	void testGetShortUrls() {
		when(resourceResolver.adaptTo(Externalizer.class)).thenReturn(externalizer);
		when(resourceResolver.map(RESOURCE_PATH)).thenReturn(RESOURCE_PATH);
		assertNotNull(pageCollectionServiceImpl.getShortUrls(pageListShortURL, resourceResolver, true, true));
	}

}
