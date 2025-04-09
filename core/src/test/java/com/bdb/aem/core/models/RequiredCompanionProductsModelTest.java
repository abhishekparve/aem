package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import java.util.Iterator;

import javax.jcr.Session;

import com.bdb.aem.core.services.BDBApiEndpointService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
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

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class RequiredCompanionProductsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class RequiredCompanionProductsModelTest {

	/** The aem context. */
	AemContext aemContext = new AemContext();

	/** The required companion products model. */
	@InjectMocks
	RequiredCompanionProductsModel requiredCompanionProductsModel;

	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The request path. */
	@Mock
	RequestPathInfo requestPath;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock ResourceResolverFactory. */
	@Mock
	ExternalizerService externalizerService;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The predicates. */
	@Mock
	PredicateGroup predicates;

	/** The session. */
	@Mock
	Session session;

	/** The resources. */
	@Mock
	Iterator<Resource> resources;

	/** The query. */
	@Mock
	Query query;

	/** The page. */
	@Mock
	Page page;

	/** The in VM. */
	@Mock
	InheritanceValueMap inVM;

	/** The result. */
	@Mock
	SearchResult result;

	/** The resource. */
	@Mock
	Resource resource;

	/** The varientValueMap. */
	@Mock
	ValueMap varientValueMap;

	/** The page manager. */
	@Mock
	PageManager pageManager;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(requiredCompanionProductsModel, "cloneLabel","Clone");
		PrivateAccessor.setField(requiredCompanionProductsModel, "sizeLabel", "Size");
		PrivateAccessor.setField(requiredCompanionProductsModel, "catNoLabel","catNoLabel");
		PrivateAccessor.setField(requiredCompanionProductsModel, "showMoreLabel", "Show More");
		PrivateAccessor.setField(requiredCompanionProductsModel, "showLessLabel", "Show Less");
		PrivateAccessor.setField(requiredCompanionProductsModel, "statusLabel", "status");
		PrivateAccessor.setField(requiredCompanionProductsModel, "description", "description");
		PrivateAccessor.setField(requiredCompanionProductsModel, "showCount", 5);
		PrivateAccessor.setField(requiredCompanionProductsModel, "requiredCompanionProductsConfig", "requiredCompanionProductsConfig");
		PrivateAccessor.setField(requiredCompanionProductsModel, "requiredCompanionProductsLabels", "requiredCompanionProductsLabels");

	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {

	 	assertNotNull(requiredCompanionProductsModel.getRequiredCompanionProductsConfig());
	 	assertNotNull(requiredCompanionProductsModel.getRequiredCompanionProductsLabels());
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {

		String productData = "[{\r\n" + "    \"companionType\": \"Required\",\r\n" +
				"    \"conjugate\": [{\"tdsCloneName\": \"tdsCloneName\"\r\n" + "    }],\r\n"
				+ "    \"companionOrdinal\": \"companionOrdinal\",\r\n" +
				"    \"tdsCloneName\": \"tdsCloneName\",\r\n" +
				"    \"companionMaterialNumber\": \"companionMaterialNumber\"\r\n" + "}]";
		lenient().when(bdbApiEndpointService.getRequiredCompanionProductsConfig()).thenReturn("config");
		lenient().when(request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)).thenReturn("productVarHPPath");
		lenient().when(resourceResolver.getResource("productVarHPPath")).thenReturn(resource);

		lenient().when(resource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(CommonConstants.COMPANION_PRODUCTS)).thenReturn("companionProduct");
		lenient().when(varientValueMap.get("companionProduct", String.class)).thenReturn(productData);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(result);
		lenient().when(result.getResources()).thenReturn(resources);
		lenient().when(resources.hasNext()).thenReturn(true, false);
		lenient().when(resources.next()).thenReturn(resource);
		lenient().when(resource.hasChildren()).thenReturn(true);
		lenient().when(resource.getChild(CommonConstants.HP_NODE)).thenReturn(resource);
		lenient().when(resource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("labelDescription");
		lenient().when(varientValueMap.get(CommonConstants.SIZE_QTY, String.class)).thenReturn("sizeQty");
		lenient().when(varientValueMap.get(CommonConstants.SIZE_UOM, String.class)).thenReturn("sizeUOM");
		lenient().when(varientValueMap.get(CommonConstants.REGULATORY_STATUS_KEY, String.class)).thenReturn("regulatoryStatus");
		lenient().when(varientValueMap.get(CommonConstants.CLONE, String.class)).thenReturn(productData);
		lenient().when(resource.getParent()).thenReturn(resource);
		lenient().when(resource.getParent()).thenReturn(resource);
		lenient().when(resource.getPath()).thenReturn("true");
		lenient().when(resourceResolver.getResource("true")).thenReturn(resource);
		lenient().when(query.getResult()).thenReturn(result);
		lenient().when(result.getResources()).thenReturn(resources);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(CommonConstants.SUPER_CATEGORY,
				String.class)).thenReturn("superCategory");
		lenient().when(CommonHelper.getPdpProductUrl(resource)).thenReturn("true");
		lenient().when(externalizerService.getFormattedUrl(
				"/content/bdb/na/us/en-us/supercategory/pdp.labelDescription.true",
				resourceResolver)).thenReturn(
				"/content/bdb/na/us/en-us/supercategory/pdp.labelDescription.true");
		requiredCompanionProductsModel.init();

	}

}
