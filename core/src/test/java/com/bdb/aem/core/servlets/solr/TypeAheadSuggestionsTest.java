package com.bdb.aem.core.servlets.solr;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.settings.SlingSettingsService;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.KeywordsModel;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * @author knarayansingh
 *
 */
@ExtendWith(MockitoExtension.class)
public class TypeAheadSuggestionsTest {
	/**
	 * TypeAheadSuggestions Object.
	 */
	@InjectMocks
	TypeAheadSuggestions typeAheadSuggestions;

	@Mock
	SolrSearchService searchService;

	@Mock
	SlingSettingsService slingSettingsService;

	@Mock
	ExternalizerService externalizerService;

	/**
	 * The SlingHttpServletRequest
	 */
	@Mock
	SlingHttpServletRequest request;

	/**
	 * The SlingHttpServletResponse
	 */
	@Mock
	SlingHttpServletResponse response;

	/**
	 * The PrintWriter
	 */
	@Mock
	PrintWriter writer;

	/**
	 * The HttpSolrClient
	 */
	@Mock
	HttpSolrClient solr;

	/**
	 * The ResourceResolver
	 */
	@Mock
	ResourceResolver resourceResolver;

	/**
	 * The PageManager
	 */
	@Mock
	PageManager pageManager;

	/**
	 * The Page
	 */
	@Mock
	Page page;

	/**
	 * The Resource
	 */
	@Mock
	Resource rootRes;

	/**
	 * The Iterator<Resource>
	 */
	@Mock
	Iterator<Resource> resList;

	/**
	 * The Resource
	 */
	@Mock
	Resource keywordResource;

	/**
	 * The Resource
	 */
	@Mock
	Resource temp;

	/**
	 * The Resource
	 */
	@Mock
	Resource keyOptions;

	/**
	 * The Iterator<Resource>
	 */
	@Mock
	Iterator<Resource> optionList;

	/**
	 * The Resource
	 */
	@Mock
	Resource childItem;

	/**
	 * The KeywordsModel
	 */
	@Mock
	KeywordsModel km;

	/**
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		when(request.getResourceResolver()).thenReturn(resourceResolver);
		when(request.getParameter("country")).thenReturn("us");
		when(searchService.solrClient("us")).thenReturn(solr);
		when(request.getParameter(SolrSearchConstants.SOLR_QUERY_Q)).thenReturn("testSearch");
		when(request.getParameter("datapath")).thenReturn("dataPath");
		when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		when(pageManager.getPage("dataPath")).thenReturn(page);
		when(page.getContentResource()).thenReturn(rootRes);
		when(rootRes.listChildren()).thenReturn(resList);
		when(resList.hasNext()).thenReturn(true, true, false);
		when(resList.next()).thenReturn(temp);
		when(temp.isResourceType("bdb-aem/proxy/components/content/keywords")).thenReturn(false, true);
		when(temp.listChildren()).thenReturn(resList);
		when(temp.getChild("keywordOptions")).thenReturn(keyOptions);
		when(keyOptions.listChildren()).thenReturn(optionList);
		when(optionList.hasNext()).thenReturn(true, false);
		when(optionList.next()).thenReturn(childItem);
		when(childItem.adaptTo(KeywordsModel.class)).thenReturn(km);
		when(km.getKeyword()).thenReturn("testSearch");
		when(km.getUrlRedirect()).thenReturn("redirect url");
		when(externalizerService.getFormattedUrl("redirect url", resourceResolver)).thenReturn("externalUrl");

	}

	/**
	 * @throws IOException
	 */
	@Test
	public void testDoGet() throws IOException {
		when(response.getWriter()).thenReturn(writer);
		typeAheadSuggestions.doGet(request, response);
	}

	/**
	 * @throws IOException
	 */
	@Test
	public void testDoGetElse() throws IOException {
		when(searchService.solrClient("us")).thenReturn(null);
		when(km.getKeyword()).thenReturn("keyword");
		when(response.getWriter()).thenReturn(writer);
		typeAheadSuggestions.doGet(request, response);
	}

	/**
	 * @throws IOException
	 */
	@Test
	public void testDoGetElseBlock2() throws IOException {
		when(km.getKeyword()).thenReturn("keyword");
		Assertions.assertThrows(NullPointerException.class, () -> {
			typeAheadSuggestions.doGet(request, response);
		});
	}
}
