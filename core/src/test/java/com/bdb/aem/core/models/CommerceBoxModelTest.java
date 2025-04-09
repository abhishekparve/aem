
package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.CookieNameService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Template;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * Junit for CommerceBox Model.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class CommerceBoxModelTest {

	/** The aem context. */
	private final AemContext aemContext = new AemContext();

	/** The commerce box model. */
	@InjectMocks
	CommerceBoxModel commerceBoxModel;

	/** Mock SlingHttpServletRequest. */
	@Mock
	SlingHttpServletRequest request;

	/** Mock resource. */
	@Mock
	Resource mockResource, varientResource, parentResource, marketingResource, marketingChildResource, variantHpRes;

	/** The resource quote. */
	Resource resourceQuote;

	/** Page manager. */
	@Mock
	PageManager pageManager;

	/** Resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock ResourceResolverFactory. */
	@Mock
	ExternalizerService externalizerService;

	/** Mock RequestPathInfo. */
	@Mock
	RequestPathInfo requestPath;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** Mock BDBApiEndpointService. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The page properties. */
	@Mock
	InheritanceValueMap pageProperties;

	/** The cookie name service. */
	@Mock
	CookieNameService cookieNameService;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The template. */
	@Mock
	Template template;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;

	/** The iterator. */
	@Mock
	Iterator<Resource> iterator;

	/** The base hp resource. */
	@Mock
	Resource hpParentResource, hpParentChildResource, hpParentChild2Resource, baseHpResource,marketingRes,hpRes;

	/** The value map 2. */
	@Mock
	ValueMap valueMap, valueMap1, valueMap2;

	@Mock
	Object obj;
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(CommerceBoxModel.class);
		aemContext.load().json("/com/bdb/aem/core/models/CommerceBoxTest.json", "/content");
		mockResource = aemContext.currentResource("/content/commercebox");
		pageManager = aemContext.pageManager();
		final String[] input2 = new String[] { "myString1", "340336" };
		Map<String, String> quoteProperties = new HashMap<>();
		quoteProperties.put("hybrisSiteId", "hybrisSiteId");
		resourceQuote = aemContext.create().resource("/root/aof/accountmanagement", quoteProperties);

		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(input2);
		lenient().when(request.getAttribute("catalogNumber")).thenReturn("340336");
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("");
		PrivateAccessor.setField(commerceBoxModel, "commerceBoxJsonConfigs", "commerceBoxJsonConfigs");
		PrivateAccessor.setField(commerceBoxModel, "commerceBoxJsonLabels", "commerceBoxJsonLabels");
		PrivateAccessor.setField(commerceBoxModel, "quoteCommerceBoxLabels", "quoteCommerceBoxLabels");
		PrivateAccessor.setField(commerceBoxModel, "cookiesJson", "cookiesJson");
		PrivateAccessor.setField(commerceBoxModel, "sizeConfigJson", "sizeConfigJson");
		PrivateAccessor.setField(commerceBoxModel, "addToCartConfigJson", "addToCartConfigJson");
		PrivateAccessor.setField(commerceBoxModel, "promotionsJsonLabels", "promotionsJsonLabels");
		PrivateAccessor.setField(commerceBoxModel, "promotionsJsonConfig", "promotionsJsonConfig");
		PrivateAccessor.setField(commerceBoxModel, "quotePromotionLabels", "quotePromotionLabels");
		PrivateAccessor.setField(commerceBoxModel, "otherProductsCommerceLabels", "otherProductsCommerceLabels");
		PrivateAccessor.setField(commerceBoxModel, "otherProductsPromotionLabels", "otherProductsPromotionLabels");
		PrivateAccessor.setField(commerceBoxModel, "pdpInstrumentsCommerceBoxLabels",
				"pdpInstrumentsCommerceBoxLabels");
		PrivateAccessor.setField(commerceBoxModel, "addToQuoteConfigJson", "addToQuoteConfigJson");
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testInit() throws LoginException, RepositoryException {

		Map<String, String> hpResourceProperty = new HashMap<>();
		hpResourceProperty.put("variants", "[{\r\n" + "	\"sizeQty\": \"1\",\r\n" + "	\"sizeUOM\": \"1\",\r\n"
				+ "	\"materialNumber\": \"123456\"\r\n" + "}]");
		Resource hpResource = aemContext.create().resource("/root/product/regime", hpResourceProperty);
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		when(currentPage.getContentResource()).thenReturn(resourceQuote);
		when(bdbApiEndpointService.getCommerceBoxAPIEndpoint()).thenReturn("commerceBoxAPIEndpoint");
		when(request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)).thenReturn("hpPath");
		when(resourceResolver.getResource("hpPath")).thenReturn(baseHpResource);
		when(baseHpResource.getParent()).thenReturn(hpParentResource).thenReturn(hpParentResource);
		when(hpParentResource.getChild(Mockito.anyString())).thenReturn(hpParentChildResource);
		when(hpParentChildResource.getChild(Mockito.anyString())).thenReturn(hpParentChild2Resource)
				.thenReturn(hpParentChild2Resource);
		when(hpParentChild2Resource.getParent()).thenReturn(hpParentChildResource);
		lenient().when(hpParentChild2Resource.adaptTo(ValueMap.class)).thenReturn(valueMap1);
		lenient().when(baseHpResource.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		lenient().when(hpParentResource.listChildren()).thenReturn(iterator);
		
		lenient().when(iterator.hasNext()).thenReturn(true,false);
		lenient().when(iterator.next()).thenReturn(hpRes);
		lenient().when(hpRes.getChild(CommonConstants.HP)).thenReturn(marketingRes);
		lenient().when(marketingRes.adaptTo(ValueMap.class)).thenReturn(valueMap2);
		lenient().when(valueMap2.get(CommonConstants.MATERIAL_NUMBER)).thenReturn(obj);
		lenient().when(obj.toString()).thenReturn("materialnumber");
		
		when(bdbApiEndpointService.getAllCustomersCartEndpoint()).thenReturn("allCustomersCartEndpoint");
		when(bdbApiEndpointService.getaddQuantityEndpoint()).thenReturn("addQuantityEndpoint");
		when(bdbApiEndpointService.getPromotionsMsgEndpoint()).thenReturn("promotionsMsgEndpoint");
		when(bdbApiEndpointService.getPromotionIdDetailsServletPath()).thenReturn("promotionIdDetailsServletPath");
		lenient().when(variantHpRes.getParent()).thenReturn(parentResource);
		lenient().when(parentResource.getChild(CommonConstants.MARKETING_NODE)).thenReturn(marketingResource);
		lenient().when(marketingResource.getChild("us")).thenReturn(marketingChildResource);
		lenient().when(marketingChildResource.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.get(CommonConstants.DISPLAY_BULK_REAGENT_QUOTE)).thenReturn("true");
		commerceBoxModel.getSizeVariant(resourceResolver, valueMap);
		commerceBoxModel.init();
	}

	/**
	 * test method to generate config json.
	 *
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testAllGetters() throws LoginException, RepositoryException {
		assertNotNull(commerceBoxModel.getCookiesJson());
		assertNotNull(commerceBoxModel.getAddToCartConfig());
		assertNotNull(commerceBoxModel.getPromotionsLabelsJson());
		assertNotNull(commerceBoxModel.getQuotePromotionsLabelsJson());
		assertNotNull(commerceBoxModel.getOtherProductsCommerceBoxJSON());
		assertNotNull(commerceBoxModel.getInstrumentsCommerceBoxJSON());
		assertNotNull(commerceBoxModel.getOtherProductsPromotionsLabelsJson());
		assertNotNull(commerceBoxModel.getAddToQuoteConfigJson());
		assertNotNull(commerceBoxModel.getCommerceBoxJsonLabels());
		assertNotNull(commerceBoxModel.getCommerceBoxJsonConfigs());
		assertNotNull(commerceBoxModel.getAddToCartConfigJson());
		assertNotNull(commerceBoxModel.getSizeConfigJson());
		assertNotNull(commerceBoxModel.getPromotionsJsonLabels());
		assertNotNull(commerceBoxModel.getPromotionsJsonConfig());
	}
	@Test
	void testGetters() throws LoginException, RepositoryException {
		commerceBoxModel.getOutOfStock();
		commerceBoxModel.getInStock();
		commerceBoxModel.getDistributorDeliveryDate();
		commerceBoxModel.getQuoteCommerceBoxLabels();
		commerceBoxModel.getQuotePromotionLabels();
		commerceBoxModel.getOtherProductsPromotionLabels();
		commerceBoxModel.getOtherProductsQuoteLabels();
		commerceBoxModel.getPdpInstrumentsCommerceBoxLabels();
		commerceBoxModel.getPdpQuoteInstrumentsCommerceBoxLabels();
		commerceBoxModel.getPdpInstrumentsCommerceBoxconfig();
		commerceBoxModel.getKitsAndSetsConfig();
		commerceBoxModel.getOtherProductsQuoteLabels();
		commerceBoxModel.getKitsAndSetsCommerceBoxLabels();
		commerceBoxModel.getKitsAndSetsQuoteLabels();
		commerceBoxModel.getOtherProductsCommerceLabels();
	}
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testInitLoginException() throws LoginException, RepositoryException {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		commerceBoxModel.init();
	}

}
