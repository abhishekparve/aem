package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.Iterator;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.SDSDocumentSearchService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class BulletPointIconsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class DataSheetsModelTest {

	/** The bullet point icons model. */
	@InjectMocks
	DataSheetsModel dataSheetsModel;
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSheetsModelTest.class);

	/** The aem context. */
	AemContext aemContext = new AemContext();
	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The request path. */
	@Mock
	RequestPathInfo requestPath;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Resourceresolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** Sling setting service . */
	@Mock
	SlingSettingsService slingSettingsService;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The session. */
	@Mock
	Session session;

	/** The query. */
	@Mock
	Query query;

	/** The search result. */
	@Mock
	SearchResult searchResult;

	/** The resources. */
	@Mock
	Iterator<Resource> resources;

	/** The next resource. */
	@Mock
	Resource nextResource;

	/** The hp node resource. */
	@Mock
	Resource hpNodeResource, toolsResource, hpBaseResource;

	/** The varient value map. */
	@Mock
	ValueMap varientValueMap, toolsMap, valueMapMarketing;

	/** The current page. */
	@Mock
	Page currentPage;

	/** Mock ResourceResolverFactory. */
	@Mock
	ExternalizerService externalizerService;

	/** The base product mock resource. */
	@Mock
	Resource baseProductMockResourceSuperParent, baseRes, parentRes, pdfRes, docTypeResource;

	/** The child. */
	@Mock
	Resource child;

	/** The children. */
	@Mock
	Iterator<Resource> children;

	@Mock
	TagManager tagManager;

	@Mock
	Tag tagg;

	@Mock
	private SolrSearchService solrSearchService;

	/** The asset path obj. */
	JsonObject assetPathObj = new JsonObject();
	@Mock
	private Asset asset;

	@Mock
	private ValueMap hpResourceVM;
	/** The sds document service. */
	@Mock
	SDSDocumentSearchService sdsDocumentService;

	@Mock
	JsonElement element;

	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	String documenttitle = "documenttitles";
	JsonObject dataSheetsConfigObj = new JsonObject();
	JsonObject tdsConfigObj = new JsonObject();
	JsonObject labelsObject = new JsonObject();

	@BeforeEach
	void setUp() throws Exception {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		final String[] input2 = new String[] { "myString1", "940150" };
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(input2);
		lenient().when(currentPage.getPath()).thenReturn("content/page/sample");
		PrivateAccessor.setField(dataSheetsModel, "dataSheetsLabels", "DataSheetsLabels");
		PrivateAccessor.setField(dataSheetsModel, "dataSheetsConfigs", "DataSheetsConfigs");
		PrivateAccessor.setField(dataSheetsModel, "docTypes", docTypeResource);
		lenient().when(docTypeResource.listChildren()).thenReturn(children);
		lenient().when(children.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(children.next()).thenReturn(parentRes);

	}

	@Test
	void testGetters() throws NoSuchFieldException {
		assertNotNull(dataSheetsModel.getDataSheetsLabels());
		assertNotNull(dataSheetsModel.getDataSheetsConfigs());
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		final String[] input2 = new String[] { "bdb:regions/us" };
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(request.getAttribute("productVarHPPath")).thenReturn("path/to/hp");
		lenient().when(resourceResolver.getResource("path/to/hp")).thenReturn(baseRes);
		lenient().when(baseRes.getParent()).thenReturn(baseRes);
		lenient().when(baseRes.getPath()).thenReturn("/content/dam");
		lenient().when(resourceResolver.getResource("/content/dam")).thenReturn(baseRes);
		lenient().when(resourceResolver.getResource("/content/dam/pdf")).thenReturn(pdfRes);
		lenient().when(parentRes.getPath()).thenReturn("/content/dam/pdf");
		lenient().when(resourceResolver.getResource("/content/dam/pdf/jcr:content/metadata")).thenReturn(parentRes);
		lenient().when(pdfRes.listChildren()).thenReturn(children);
		lenient().when(children.next()).thenReturn(parentRes);
		lenient().when(parentRes.adaptTo(ValueMap.class)).thenReturn(valueMapMarketing);
		lenient().when(valueMapMarketing.containsKey(CommonConstants.PDF_DOC_REGION)).thenReturn(true)
				.thenReturn(false);
		lenient().when(valueMapMarketing.containsKey(CommonConstants.PDF_DOC_TYPE)).thenReturn(true).thenReturn(false);
		lenient().when(valueMapMarketing.get(CommonConstants.PDF_DOC_REGION, String[].class)).thenReturn(input2);
		lenient().when(valueMapMarketing.get(CommonConstants.DOC_TYPE)).thenReturn("docType");
		lenient().when(valueMapMarketing.get(CommonConstants.DOCUMENT_LABEL)).thenReturn("docType");
		lenient().when(valueMapMarketing.get(CommonConstants.DOCUMENT_LABEL, String.class)).thenReturn("docType");
		lenient().when(valueMapMarketing.get(CommonConstants.DOCUMENT_TYPE)).thenReturn(CommonConstants.DATA_TDS);
		lenient().when(valueMapMarketing.get(CommonConstants.DOCUMENT_TYPE, String.class))
				.thenReturn(CommonConstants.DATA_TDS);
		lenient().when(baseRes.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		String clone = "[{\"polyclonal\":\"FALSE\",\"specificity\":\"Stat3 (pY705)\",\"hostStrain\":\"\",\"entrezGeneId\":\"\",\"isoType\":\"IgG2a, \u03ba\",\"workshopNumber\":\"\",\"immunogen\":\"Phosphorylated Human Stat3 Peptide\",\"hostSpecies\":\"Mouse\",\"molecularWeight\":\"92 kDa\"}]";
		String species = "[{\"baseMaterialNumber\":\"562071_base\",\"reactivityStatus\":\"QC\",\"species\":\"Hu\",\"reactivityStatusDesc\":\"QC Testing\",\"speciesDescription\":\"Human\"},{\"baseMaterialNumber\":\"562071_base\",\"reactivityStatus\":\"TD\",\"species\":\"Ms\",\"reactivityStatusDesc\":\"Tested in Development\",\"speciesDescription\":\"Mouse\"}]";
		String app = "[{\"baseMaterialNumber\":\"562071_base\",\"applicationName\":\"ICM\",\"applicationStatus\":\"RT\",\"applicationDesc\":\"Intracellular staining (flow cytometry)\",\"applicationStatusDesc\":\"Routinely Tested\"}]";
		String variants = "[{\"baseMaterialNumber\":\"562071_base\",\"materialNumber\":\"562071\",\"tdsRevision\":\"1\",\"sizeQty\":\"250\",\"sizeUOM\":\"Tests\"}]";
		String bdFormats = "[{\"dyeName\":\"Alexa 647\",\"formatStatement\":\"Alexa Fluor\u2122 647 is part of the BD\u2122 Red family of dyes. This is a small organic fluorochrome with an excitation maximum (Ex Max) at 653 nm and an emission maximum (Em Max) at 669 nm. Alexa Fluor 647 is designed to be excited by the Red laser (627-640 nm) and detected using an optical filter centered near 520 nm (eg, a 66020 nm bandpass filter). Please ensure that your instrument\u2019s configuration (lasers and optical filters) are appropriate for this dye. Visit our Supplies, Parts and Setup Reagents page, find the link to your instrument and on the product page, navigate down to Related Products for more information.\",\"emmisionmax\":\"669\",\"excitationmax\":\"653\",\"laserColor\":\"Red\",\"laserWavelength\":\"627-640\"}]";
		lenient().when(varientValueMap.get("clone", String.class)).thenReturn(clone);
		lenient().when(varientValueMap.get("clone")).thenReturn(clone);
		lenient().when(varientValueMap.get("labelDescription")).thenReturn("description");
		lenient().when(varientValueMap.get("labelDescription", String.class)).thenReturn("description");
		lenient().when(varientValueMap.get(CommonConstants.SPECIES_REACTIVITY_KEY)).thenReturn(species);
		lenient().when(varientValueMap.get(CommonConstants.APPLICAATION_KEY)).thenReturn(app);
		lenient().when(varientValueMap.get(CommonConstants.VARIANTS)).thenReturn(variants);
		lenient().when(varientValueMap.get(CommonConstants.BD_FORMAT)).thenReturn(bdFormats);
		lenient().when(bdbApiEndpointService.getTdsEndpoint()).thenReturn("/path/to/TDS");
		lenient().when(children.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(varientValueMap.containsKey("tdsDocumentType")).thenReturn(true).thenReturn(false);
		lenient().when(varientValueMap.get("tdsDocumentType", String.class)).thenReturn("ON_DEMAND_TDS");

		dataSheetsModel.init();
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		dataSheetsModel.init();
	}

	/**
	 * Get testGetPdfMatchedWithDocType test
	 */
	@SuppressWarnings("static-access")
	@Test
	void testGetPdfMatchedWithDocType() throws LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		lenient().when(children.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(children.next()).thenReturn(parentRes);
		lenient().when(parentRes.getParent()).thenReturn(parentRes);
		lenient().when(parentRes.getName()).thenReturn("docNameSample");
		lenient().when(parentRes.getPath()).thenReturn("path");
		lenient().when(parentRes.adaptTo(ValueMap.class)).thenReturn(valueMapMarketing);
		lenient().when(valueMapMarketing.get(CommonConstants.DOCUMENT_TYPE)).thenReturn("doc");
		lenient().when(valueMapMarketing.get(CommonConstants.DOCUMENT_TYPE, String.class)).thenReturn("doc");
		lenient().when(valueMapMarketing.get(CommonConstants.DOCUMENT_LABEL)).thenReturn("doc");
		lenient().when(valueMapMarketing.get(CommonConstants.DOCUMENT_LABEL, String.class)).thenReturn("doc");
		dataSheetsModel.getPdfMatchedWithDocType(dataSheetsConfigObj, parentRes, resourceResolver, externalizerService,
				"doc", children, "778899",labelsObject, currentPage );
		
	}
	
	/**
	 * Get testGetPdfMatchedWithDocType test
	 */
	@SuppressWarnings("static-access")
	@Test
	void testGetOtherFiles() throws LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		String[] arr = new String[] {"us"};
		lenient().when(varientValueMap.containsKey(CommonConstants.PDF_DOC_REGION)).thenReturn(true).thenReturn(false);
		lenient().when(varientValueMap.containsKey(CommonConstants.PDF_DOC_TYPE)).thenReturn(true).thenReturn(false);
		lenient().when(varientValueMap.get(CommonConstants.PDF_DOC_REGION, String[].class)).thenReturn(arr);
		lenient().when(varientValueMap.get(CommonConstants.PDF_DOC_TYPE)).thenReturn("docType");
		lenient().when(parentRes.adaptTo(ValueMap.class)).thenReturn(valueMapMarketing);
		lenient().when(varientValueMap.get(CommonConstants.DOCUMENT_TYPE)).thenReturn("doc");
		lenient().when(varientValueMap.get(CommonConstants.DOCUMENT_TYPE, String.class)).thenReturn("doc");
		lenient().when(varientValueMap.get(CommonConstants.DOCUMENT_LABEL)).thenReturn("doc");
		lenient().when(varientValueMap.get(CommonConstants.DOCUMENT_LABEL, String.class)).thenReturn("doc");
		lenient().when(docTypeResource.listChildren()).thenReturn(children);
		lenient().when(children.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(children.next()).thenReturn(parentRes);
		dataSheetsModel.getOtherFiles(docTypeResource, dataSheetsConfigObj, "us", baseRes, varientValueMap, resourceResolver, externalizerService,"778899",labelsObject, currentPage);
	}

}
