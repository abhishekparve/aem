package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;

import junitx.util.PrivateAccessor;

/**
 * The Class GenerateHrefLangTagModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class GenerateHrefLangTagModelTest {
	
	/** The generate href lang tag model. */
	@InjectMocks
	GenerateHrefLangTagModel generateHrefLangTagModel;
	
	/** The resolver factory. */
	@Mock
    ResourceResolverFactory resolverFactory;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The request. */
	@Mock
	SlingHttpServletRequest request;
	
	/** The request path. */
	@Mock
	RequestPathInfo requestPath;
	
	/** The current page. */
	@Mock
	Page currentPage;
	
	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;
	
	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The solr config. */
	@Mock
	BDBSearchEndpointService solrConfig;
	
	/** The sap resource. */
	@Mock
	Resource resource, countryRegionsListRes, nextRes, variantResource, varSapResource, sapResource;
	
	/** The list item res. */
	@Mock
	Iterator<Resource> listItemRes;
	
	/** The item map. */
	@Mock
	ValueMap itemMap;
	
	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;
	
	/** The catalog structure update service. */
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
	
	/** The Constant CURRENT_PAGE_PATH. */
	public static final String CURRENT_PAGE_PATH = "/content/bdb/eu/es/en-es/learn/applications/multicolor-flow-cytometry/intracellular-flow-cytometry";
	
	/** The Constant CURRENT_PAGE_PATH_MODIFIED. */
	public static final String CURRENT_PAGE_PATH_MODIFIED = "/content/bdb/ES/learn/applications/multicolor-flow-cytometry/intracellular-flow-cytometry";
	
	/** The Constant CURRENT_PAGE_EXTERNALIZED. */
	public static final String CURRENT_PAGE_EXTERNALIZED = "/es/learn/applications/multicolor-flow-cytometry/intracellular-flow-cytometry";
	
	/** The Constant REGION_PATH. */
	public static final String REGION_PATH = "ES";
	
	/** The full country list. */
	List<String> fullCountryList;
	
	/** The slectors. */
	String slectors[] = {"test1", "test2"};

	/**
	 * Setup.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setup() throws Exception {
		PrivateAccessor.setField(generateHrefLangTagModel, "request", request);
		fullCountryList = new ArrayList<>();
		fullCountryList.add("ES");
		fullCountryList.add("IT");
		fullCountryList.add("IN");
		fullCountryList.add("ZH");
	}
	
	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testInit() throws LoginException, RepositoryException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(slectors);
		lenient().when(currentPage.getPath()).thenReturn(CURRENT_PAGE_PATH);
		lenient().when(solrSearchService.getAllCountries(resourceResolver)).thenReturn(fullCountryList);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn(CommonConstants.PUBLISH);
		lenient().when(solrConfig.countryToRegionAndLanguge()).thenReturn("en-es");
		lenient().when(resourceResolver.getResource("en-es")).thenReturn(countryRegionsListRes);
		lenient().when(resourceResolver.getResource("en-es" + CommonConstants.JCR_CONTENT +CommonConstants.SINGLE_SLASH +CommonConstants.LIST)).thenReturn(countryRegionsListRes);
		lenient().when(countryRegionsListRes.listChildren()).thenReturn(listItemRes);
		lenient().when(listItemRes.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(listItemRes.next()).thenReturn(nextRes);
		lenient().when(nextRes.adaptTo(ValueMap.class)).thenReturn(itemMap);
		lenient().when(itemMap.get(CommonConstants.VALUE, String.class)).thenReturn("es");
		lenient().when(itemMap.get(JcrConstants.JCR_TITLE, String.class)).thenReturn(REGION_PATH);
		lenient().when(resourceResolver.getResource(CURRENT_PAGE_PATH_MODIFIED)).thenReturn(resource);
		lenient().when(externalizerService.getFormattedUrl(CURRENT_PAGE_PATH_MODIFIED, resourceResolver)).thenReturn(CURRENT_PAGE_EXTERNALIZED);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver
				, "test2", CommonConstants.MATERIAL_NUMBER)).thenReturn(variantResource);
		lenient().when(variantResource.getChild(CommonConstants.SAP_CC_NODENAME)).thenReturn(varSapResource);
		lenient().when(varSapResource.getChild(CommonConstants.REGION_DETAILS_NODE_NAME)).thenReturn(sapResource);
		lenient().when(sapResource.adaptTo(ValueMap.class)).thenReturn(itemMap);
		
		generateHrefLangTagModel.init();
	}
}
