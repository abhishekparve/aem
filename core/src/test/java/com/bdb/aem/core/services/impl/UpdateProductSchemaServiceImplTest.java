package com.bdb.aem.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.util.Iterator;

import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.settings.SlingSettingsService;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class UpdateProductSchemaServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class UpdateProductSchemaServiceImplTest {

	/** The update product schema service impl. */
	@InjectMocks
	UpdateProductSchemaServiceImpl updateProductSchemaServiceImpl;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The next resource. */
	@Mock
	Resource nextResource;

	@Mock
	ExternalizerService externalizerService;

	@Mock
	SolrSearchService solrSearchService;

	@Mock
	SlingSettingsService slingSettingsService;

	/** The marketing resource. */
	@Mock
	Resource productTagSelectorRes, parentResource, varientResource, hpNodeResource, marketingResource, usRegionNode,
			parentsResource, childResource,res;

	/** The varient value map. */
	@Mock
	ValueMap productTagSelectorMap, varientValueMap;

	/** The tag manager. */
	@Mock
	TagManager tagManager;

	/** The tag. */
	@Mock
	Tag tag, tag1;

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

	/** The marketing node. */
	@Mock
	Node marketingNode,childNode;

	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;

	@Mock
	Page page;
	/** The FAQ array. */
	String[] FAQArray = { "question1&answer1", "question2&answer2" };

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

		lenient().when(request.getParameter("currentPage")).thenReturn("/content/bdb");
		lenient().when(resourceResolver.getResource("/content/bdb/jcr:content/root/producttagselector"))
				.thenReturn(productTagSelectorRes);
		lenient().when(productTagSelectorRes.getValueMap()).thenReturn(productTagSelectorMap);
		lenient().when(productTagSelectorMap.get("cq:tags"))
				.thenReturn("bdb:products/reagents/flow-cytometry-reagents/");
		lenient().when(productTagSelectorMap.get("cq:tags", String.class))
				.thenReturn("bdb:products/reagents/flow-cytometry-reagents/");
		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(tagManager.resolve("bdb:products/reagents/flow-cytometry-reagents/")).thenReturn(tag);
		lenient().when(tag.getName()).thenReturn("340336");
		lenient().when(marketingResource.adaptTo(Node.class)).thenReturn(marketingNode);

	}

	/**
	 * Testget marketing resource.
	 * 
	 * @throws RepositoryException
	 * @throws LockException
	 * @throws ConstraintViolationException
	 * @throws VersionException
	 * @throws PathNotFoundException
	 * @throws ItemExistsException
	 */
	@Test
	void testGetMarketingResource() throws ItemExistsException, PathNotFoundException, VersionException,
			ConstraintViolationException, LockException, RepositoryException {

		getVarientMap();
		lenient().when(request.getParameter("skuNum")).thenReturn("340336");
		assertNotNull(hpNodeResource);
		lenient().when(hpNodeResource.getParent()).thenReturn(parentResource);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "340336", CommonConstants.MATERIAL_NUMBER)).thenReturn(parentResource);
		lenient().when(parentResource.getChild("marketing")).thenReturn(marketingResource);
		lenient().when(request.getParameter("region")).thenReturn("bdb:country/global");
		lenient().when(tagManager.resolve("bdb:country/global")).thenReturn(tag1);
		lenient().when(tag1.getName()).thenReturn("global");
		lenient().when(marketingResource.getChild("global")).thenReturn(usRegionNode);
		lenient().when(solrSearchService.getHpNodeResource("340336", "us", resourceResolver)).thenReturn(nextResource);
		lenient().when(nextResource.getParent()).thenReturn(parentsResource);
		lenient().when(parentsResource.getChild("340336")).thenReturn(childResource);
		lenient().when(childResource.getChild(CommonConstants.HP_NODE)).thenReturn(nextResource);
		lenient().when(parentsResource.adaptTo(Node.class)).thenReturn(marketingNode);
		lenient().when(parentsResource.getChild(CommonConstants.MARKETING_NODE)).thenReturn(varientResource);
		lenient().when(varientResource.adaptTo(Node.class)).thenReturn(marketingNode);
		lenient().when(marketingNode.addNode(CommonConstants.MARKETING_NODE)).thenReturn(marketingNode);
		lenient().when(request.getParameter(CommonConstants.CURRENT_PAGE)).thenReturn("/content/bdb");
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(res);
		lenient().when(res.adaptTo(Page.class)).thenReturn(page);
		updateProductSchemaServiceImpl.getMarketingResource(request, resourceResolver, solrSearchService);
		updateProductSchemaServiceImpl.getCountry(request, resourceResolver);

	}

	/**
	 * Test get product announcement.
	 */
	@Test
	void testGetProductAnnouncement() throws RepositoryException {

		JsonObject productJson = new JsonObject();
		lenient().when(marketingResource.getValueMap()).thenReturn(varientValueMap);
		assertNotNull(varientValueMap);
		lenient().when(varientValueMap.get("productAnnouncement", String.class)).thenReturn("true");
		lenient().when(varientValueMap.get("smessageStatus", String.class)).thenReturn("true");
		lenient().when(varientValueMap.get("faqProperty", String[].class)).thenReturn(FAQArray);
		lenient().when(marketingResource.getParent()).thenReturn(nextResource);
		lenient().when(nextResource.getParent()).thenReturn(nextResource);
		lenient().when(nextResource.getChild("hp")).thenReturn(nextResource);
		lenient().when(nextResource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(nextResource.getPath()).thenReturn("path/to/something");
		lenient().when(bdbApiEndpointService.getVialImagesBasePath()).thenReturn("path/to/vial");
		lenient().when(resourceResolver.getResource("path/to/vial")).thenReturn(nextResource);
		lenient().when(nextResource.getName()).thenReturn("566599");
		lenient().when(nextResource.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get("bdFormat", String.class)).thenReturn("[{dyeName:\"AXE\"}]");
		lenient().when(varientValueMap.get("labelDescription", String.class)).thenReturn("Alexa");
		updateProductSchemaServiceImpl.getProductAnnouncement(marketingResource, request, productJson, resourceResolver,
				externalizerService, null, null);
		lenient().when(nextResource.adaptTo(Node.class)).thenReturn(marketingNode);
		updateProductSchemaServiceImpl.setAssayProcedure("neutralizationActivity", "targetMW", "assayRange", "true",
				nextResource);
	}
	
	/**
	 * Test set product annoucnement.
	 */
	@Test
	void testSetProductAnnoucnement() {

		String formData = "{\"productAnnouncement\":\"true\",\"paDescription\":\"<p><b>Attention:</b> test product will be discontinued in July 2020. <a href=\\\"https://publish-p14102-e36686.adobeaemcloud.com/content/bdb/us/en-us/products/instruments/flow-cytometers/clinical-cell-analyzers/pdp_test.644444_base.644444.html?wcmmode=disabled\\\"> [612887] </a> is the replacement for 553067. </p>\",\"paViewMoreCTA\":\"More Information\",\"moreInfoLink\":\"https://www.google.co.in/?gws_rd=ssl\",\"openNewTab\":\"true\",\"paFAQTitle\":\"FAQ Title Test\",\"viewMoreFaqLabel\":\"View More FAQs\",\"disclaimerStatus\":\"true\",\"regionalDisclaimers\":\"The information \",\"faqList\":[{\"question\":\"test2\",\"answer\":\"test3\"}],\"surchargeDisclaimer\":{\"specialMessage\":\"Special Message\",\"altText\":\"Alt Text\"}}";
		lenient().when(request.getParameter("form_data")).thenReturn(formData);
		assertNotNull(formData);
		updateProductSchemaServiceImpl.setProductAnnounce(request, resourceResolver, marketingResource);

	}

	/**
	 * Gets the vaient map.
	 *
	 * @return the vaient map
	 */
	public void getVarientMap() {

		lenient().when(queryBuilder.createQuery(any(), any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getResources()).thenReturn(resources);
		lenient().when(resources.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(resources.next()).thenReturn(nextResource);
		lenient().when(nextResource.hasChildren()).thenReturn(true).thenReturn(false);
		lenient().when(nextResource.getChild("hp")).thenReturn(hpNodeResource);

	}

	@Test
	public void getIsoTypeTest() {
		String clone = "[{\"polyclonal\":\"FALSE\",\"specificity\":\"Stat3 (pY705)\",\"hostStrain\":\"\",\"entrezGeneId\":\"\",\"isoType\":\"IgG2a, \u03ba\",\"workshopNumber\":\"\",\"immunogen\":\"Phosphorylated Human Stat3 Peptide\",\"hostSpecies\":\"Mouse\",\"molecularWeight\":\"92 kDa\"}]";
		lenient().when(varientValueMap.get("clone", String.class)).thenReturn(clone);
		updateProductSchemaServiceImpl.getIsoType(productTagSelectorMap);

	}

	@Test
	public void getProductSKUDetails() throws IOException, SolrServerException {
		String clone = "[{\"polyclonal\":\"FALSE\",\"specificity\":\"Stat3 (pY705)\",\"hostStrain\":\"\",\"entrezGeneId\":\"\",\"isoType\":\"IgG2a, \u03ba\",\"workshopNumber\":\"\",\"immunogen\":\"Phosphorylated Human Stat3 Peptide\",\"hostSpecies\":\"Mouse\",\"molecularWeight\":\"92 kDa\"}]";
		String species = "[{\"baseMaterialNumber\":\"562071_base\",\"reactivityStatus\":\"QC\",\"species\":\"Hu\",\"reactivityStatusDesc\":\"QC Testing\",\"speciesDescription\":\"Human\"},{\"baseMaterialNumber\":\"562071_base\",\"reactivityStatus\":\"TD\",\"species\":\"Ms\",\"reactivityStatusDesc\":\"Tested in Development\",\"speciesDescription\":\"Mouse\"}]";
		String app = "[{\"baseMaterialNumber\":\"562071_base\",\"applicationName\":\"ICM\",\"applicationStatus\":\"RT\",\"applicationDesc\":\"Intracellular staining (flow cytometry)\",\"applicationStatusDesc\":\"Routinely Tested\"}]";
		String variants = "[{\"baseMaterialNumber\":\"562071_base\",\"materialNumber\":\"562071\",\"tdsRevision\":\"1\",\"sizeQty\":\"250\",\"sizeUOM\":\"Tests\"}]";
		String bdFormats = "[{\"dyeName\":\"Alexa 647\",\"formatStatement\":\"Alexa Fluor\u2122 647 is part of the BD\u2122 Red family of dyes. This is a small organic fluorochrome with an excitation maximum (Ex Max) at 653 nm and an emission maximum (Em Max) at 669 nm. Alexa Fluor 647 is designed to be excited by the Red laser (627-640 nm) and detected using an optical filter centered near 520 nm (eg, a 66020 nm bandpass filter). Please ensure that your instrument\u2019s configuration (lasers and optical filters) are appropriate for this dye. Visit our Supplies, Parts and Setup Reagents page, find the link to your instrument and on the product page, navigate down to Related Products for more information.\",\"emmisionmax\":\"669\",\"excitationmax\":\"653\",\"laserColor\":\"Red\",\"laserWavelength\":\"627-640\"}]";
		lenient().when(marketingResource.getChild(CommonConstants.HP)).thenReturn(hpNodeResource);
		lenient().when(marketingResource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(hpNodeResource.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get("clone", String.class)).thenReturn(clone);
		lenient().when(varientValueMap.get("clone")).thenReturn(clone);
		lenient().when(varientValueMap.get("labelDescription")).thenReturn("description");
		lenient().when(varientValueMap.get("labelDescription", String.class)).thenReturn("description");
		lenient().when(varientValueMap.get(CommonConstants.SPECIES_REACTIVITY_KEY)).thenReturn(species);
		lenient().when(varientValueMap.get(CommonConstants.APPLICAATION_KEY)).thenReturn(app);
		lenient().when(varientValueMap.get(CommonConstants.VARIANTS)).thenReturn(variants);
		lenient().when(varientValueMap.get(CommonConstants.BD_FORMAT)).thenReturn(bdFormats);
		lenient().when(marketingResource.getParent()).thenReturn(marketingResource);
		lenient().when(marketingResource.getPath()).thenReturn("/path");
		lenient().when(marketingResource.listChildren()).thenReturn(resources);
		lenient().when(resources.hasNext()).thenReturn(true, false);
		lenient().when(resources.next()).thenReturn(childResource);
		lenient().when(marketingResource.getName()).thenReturn("variantsArrayName");
		lenient().when(childResource.getName()).thenReturn("hp");
		lenient().when(childResource.adaptTo(ValueMap.class)).thenReturn(productTagSelectorMap);
		lenient().when(productTagSelectorMap.get(CommonConstants.SIZE_QTY)).thenReturn("1");
		lenient().when(productTagSelectorMap.get(CommonConstants.SIZE_UOM)).thenReturn("mL");
		lenient().when(productTagSelectorMap.get(CommonConstants.MATERIAL_NUMBER)).thenReturn("566599");
		lenient().when(marketingResource.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("publish");
		updateProductSchemaServiceImpl.getProductSKUDetails(marketingResource, request, new JsonObject(),
				resourceResolver, externalizerService, "566599", solrSearchService, marketingResource,"us");

	}

}
