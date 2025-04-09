
package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jcr.Session;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.day.cq.commons.inherit.InheritanceValueMap;
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

import com.bdb.aem.core.bean.ReferenceBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.PageManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * Junit for CommerceBox Model.
 */

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class PdpTabsTest {

	/** The aem context. */

	private final AemContext aemContext = new AemContext();

	/** The pdp tab model. */

	@InjectMocks
	PDPTabModel pdpTabModel;

	/** Mock SlingHttpServletRequest. */

	@Mock
	SlingHttpServletRequest request;

	/** Mock Common Helper. */

	@Mock
	CommonHelper commonHelper;

	/** Mock resource. */

	@Mock
	Resource mockResource;

	/** Page manager. */

	@Mock
	PageManager pageManager;

	/** Resource resolver. */

	@Mock
	ResourceResolver resourceResolver;

	/** The externalizer service. */

	@Mock
	ExternalizerService externalizerService;

	/** Mock RequestPathInfo. */

	@Mock
	RequestPathInfo requestPath;

	/** Mock BDBApiEndpointService. */

	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	@Mock
	BDBSearchEndpointService solrConfig;;

	/** The query. */

	@Mock
	Query query;

	/** The search result. */

	@Mock
	SearchResult searchResult;

	/** The query builder. */

	@Mock
	QueryBuilder queryBuilder;

	/** The resources. */

	@Mock
	Iterator<Resource> resources;

	/** The next resource. */

	@Mock
	Resource nextResource,res,childRes;

	/** The hp node resource. */

	@Mock
	Resource hpNodeResource;

	/** The varient value map. */

	@Mock
	ValueMap varientValueMap, hpValueMap, hpProperty;

	/** The citation object mapper. */

	@Mock
	ObjectMapper citationObjectMapper;

	/** The citation bean. */

	@Mock
	ReferenceBean citationBean;
	@Mock
	Object object;

	/** The product details map. */

	/*
	 * @Mock Map<String, String> productDetailsMap = new LinkedHashMap<String,
	 * String>();
	 */

	/** The session. */

	@Mock
	Session session;

	@Mock
	InheritanceValueMap pageProperties;
	@Mock
	Object obj;
	/** The clone. */

	private String clone = "[{\"polyclonal\":\"\",\"specificity\":\"\",\"hostStrain\":\"\",\"entrezGeneId\":\"\",\"isoType\":\"Armenian Hamster IgG1, \u03ba\",\"workshopNumber\":\"\",\"immunogen\":\"H-2Kb specific cytotoxic T lymphocyte clone BM10-37\",\"tdsCloneName\":\"145-2C11\",\"cloneId\":\"145-2C11\",\"species\":\"\",\"molecularWeight\":\"\"},{\"polyclonal\":\"\",\"specificity\":\"\",\"hostStrain\":\"\",\"entrezGeneId\":\"\",\"isoType\":\"Indian Hamster IgG1, \u03ba\",\"workshopNumber\":\"\",\"immunogen\":\"H-5Kb protoxic T clone BM10-37\",\"tdsCloneName\":\"145-2C11\",\"cloneId\":\"152-2C11\",\"species\":\"\",\"molecularWeight\":\"\"}]";

	/** The bd format. */

	private String bdFormat = "[{\"formatID\":\"PerCP\",\"formatStatement\":\"PerCP is a component of the photosynthetic apparatus found in the dinoflagellate Glenodinium. PerCP is a protein complex with a molecular weight of approximately 35 kDa. Due to its photobleaching characteristics, PerCP conjugates are not recommended for use on flow cytometers with high-power lasers (>25 mW).\",\"emmisionmax\":\"454 nm\",\"excitationmax\":\"488 nm\",\"dyeName\":\"PerCP\",\"laserColor\":\"Blue\",\"laserWavelength\":\"552 nm\"}]";

	/** The citations. */

	private String referenceDetails = "[{\"citationId\":\"HP12148_610499\",\"citationdata\":\"Liu SH, Wong ML, Craik CS, Brodsky FM. Regulation of clathrin assembly and trimerization defined using recombinant triskelion hubs. Cell. 1995; 83(2):257-267. (Biology: Immunofluorescence, Immunohistochemistry, Immunoprecipitation, Western blot, Bioimaging).\",\"pubMedId\":\"7585943\"},{\"citationId\":\"HP12148_610499\",\"citationdata\":\"Liu SH, Wong ML, Craik CS, Brodsky FM. Regulation of clathrin assembly and trimerization defined using recombinant triskelion hubs. Cell. 1995; 83(2):257-267. (Biology: Immunofluorescence, Immunohistochemistry, Immunoprecipitation, Western blot, Bioimaging).\",\"pubMedId\":\"7585945\"}]";
	
	private String productApplicationTest = "[{\"baseMaterialNumber\":\"\",\"applicationName\":\"\",\"applicationStatus\":\"\",\"applicationDesc\":\"\",\"applicationStatusDesc\":\"\"}]";
	private String productVarHPPath = "var/commerce/products/bdb/product/variant/hp";

	private String assetPaths = "/content/dam";
	String MANUFACTURING_COMPONENT = "manufacturingComponent";
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(PDPTabModel.class);
		aemContext.load().json("/com/bdb/aem/core/models/PDPTabs.json", "/content");
		mockResource = aemContext.currentResource("/content/pdpTabs");
		pageManager = aemContext.pageManager();
		final String[] input2 = new String[] { "myString1", "340336" };
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(input2);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		PrivateAccessor.setField(pdpTabModel, "viewAllFormatsLabel", "viewAllFormatsLabel");
		PrivateAccessor.setField(pdpTabModel, "preparationStorage", "preparationStorage");
		PrivateAccessor.setField(pdpTabModel, "addPreparationStorage", "addPreparationStorage");
		PrivateAccessor.setField(pdpTabModel, "labelDescription", "labelDescription");
		PrivateAccessor.setField(pdpTabModel, "tdsDescription", "tdsDescription");
		PrivateAccessor.setField(pdpTabModel, "formatname", "formatname");
		PrivateAccessor.setField(pdpTabModel, "emmisionmax", "emmisionmax");
		PrivateAccessor.setField(pdpTabModel, "excitationmax", "excitationmax");
		PrivateAccessor.setField(pdpTabModel, "viewAllFormatsLabel", "viewAllFormatsLabel");
		PrivateAccessor.setField(pdpTabModel, "excitationSource", "excitationSource");
		PrivateAccessor.setField(pdpTabModel, "bdFormatdescription", "bdFormatdescription");
		PrivateAccessor.setField(pdpTabModel, "formatDetailsImage", "formatDetailsImage");
		// PrivateAccessor.setField(pdpTabModel, "productDetailsMap",
		// productDetailsMap);
		PrivateAccessor.setField(pdpTabModel, "viewAllFormatsLabel", "viewAllFormatsLabel");
		PrivateAccessor.setField(pdpTabModel, "productDetailsTabLabel", "productDetailsTabLabel");
		PrivateAccessor.setField(pdpTabModel, "antibodyDetailsTabLabel", "antibodyDetailsTabLabel");
		PrivateAccessor.setField(pdpTabModel, "formatDetailsTabLabel", "formatDetailsTabLabel");
		PrivateAccessor.setField(pdpTabModel, "referencesTabLabel", "referencesTabLabel");
		PrivateAccessor.setField(pdpTabModel, "products", mockResource);
		PrivateAccessor.setField(pdpTabModel, "rapTitleText", "RapTitleText");
		PrivateAccessor.setField(pdpTabModel, "pnTitleText", "PnTitleText");
		PrivateAccessor.setField(pdpTabModel, "noOfLinksToBeShown", 3);
		PrivateAccessor.setField(pdpTabModel, "rapshowmorelessDesktop", 3);
		PrivateAccessor.setField(pdpTabModel, "rapshowmorelessMobile", 3);
		PrivateAccessor.setField(pdpTabModel, "showmorelabel", "Showmorelabel");
		PrivateAccessor.setField(pdpTabModel, "showlesslabel", "Showlesslabel");
		PrivateAccessor.setField(pdpTabModel, "pnShowMoreLessLimitDesktop", 3);
		PrivateAccessor.setField(pdpTabModel, "pnShowMoreLessLimitMobile", 3);

	}

	/**
	 * 
	 * test init.
	 * 
	 * @throws LoginException
	 */

	@Test
	void testInit() throws LoginException {
		Map<String, String> productDetailsMap = new LinkedHashMap<String, String>();
		productDetailsMap.put(CommonConstants.PRODUCT_DETAILS, "toxicity");
		lenient().when(request.getAttribute("productVarHPPath")).thenReturn(productVarHPPath);
		lenient().when(request.getAttribute("catalogNumber")).thenReturn("123456");
		lenient().when(queryBuilder.createQuery(any(), any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getResources()).thenReturn(resources);
		lenient().when(resources.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(resources.next()).thenReturn(nextResource);
		lenient().when(nextResource.hasChildren()).thenReturn(true).thenReturn(false);
		lenient().when(nextResource.getChild("hp")).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getPath()).thenReturn("/content/dam");
		lenient().when(hpNodeResource.getParent()).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getChild("sap-cc")).thenReturn(res);
		lenient().when(hpNodeResource.getChild("region-details")).thenReturn(childRes);
		lenient().when(hpNodeResource.getParent().getParent()).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getParent().getParent().getPath()).thenReturn("/content/dam");
		lenient().when(hpNodeResource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(hpNodeResource.adaptTo(ValueMap.class)).thenReturn(hpValueMap);
		lenient().when(hpNodeResource.getPath()).thenReturn("/var/commerce");
		lenient().when(resourceResolver.getResource(productVarHPPath)).thenReturn(hpNodeResource);
		//lenient().when(hpNodeResource.getParent()).thenReturn(mockResource);
		lenient().when(hpValueMap.containsKey("brand")).thenReturn(true);
		lenient().when(hpValueMap.get("brand")).thenReturn("SomeBrand");
		lenient().when(hpValueMap.containsKey("clone")).thenReturn(true);
		lenient().when(hpValueMap.get("clone")).thenReturn(clone);
		lenient().when(hpValueMap.containsKey("tdsDescription")).thenReturn(true);
		lenient().when(hpValueMap.get("tdsDescription")).thenReturn("tdsDescription");
		lenient().when(hpValueMap.containsKey("labelDescription")).thenReturn(true);
		lenient().when(hpValueMap.get("labelDescription")).thenReturn("labelDescription");
		lenient().when(hpValueMap.containsKey("recomAssayProcedure")).thenReturn(true);
		lenient().when(hpValueMap.get("recomAssayProcedure")).thenReturn("<li>recomAssayProcedure</li>");
		lenient().when(hpValueMap.containsKey("productNotices")).thenReturn(true);
		lenient().when(hpValueMap.get("productNotices")).thenReturn("<li>productNotices</li>");
		lenient().when(hpValueMap.containsKey("addPreparationStorage")).thenReturn(true);
		lenient().when(hpValueMap.get("addPreparationStorage")).thenReturn("addPreparationStorage");
		lenient().when(hpValueMap.containsKey("preparationStorage")).thenReturn(true);
		lenient().when(hpValueMap.get("preparationStorage")).thenReturn("preparationStorage");
		lenient().when(hpValueMap.containsKey("bdFormat")).thenReturn(true);
		lenient().when(hpValueMap.get("bdFormat")).thenReturn(bdFormat);
		lenient().when(hpValueMap.containsKey("productApplicationTest")).thenReturn(true);
		lenient().when(hpValueMap.get("productApplicationTest")).thenReturn(productApplicationTest);
		lenient().when(hpValueMap.containsKey("referenceDetails")).thenReturn(true);
		lenient().when(hpValueMap.get("referenceDetails")).thenReturn(referenceDetails);
		lenient().when(hpValueMap.containsKey("laserColor")).thenReturn(true);
		lenient().when(hpValueMap.get("laserColor")).thenReturn("laserColor");
		lenient().when(hpValueMap.containsKey("formatDetailsImage")).thenReturn(true);
		lenient().when(hpValueMap.get("formatDetailsImage")).thenReturn("formatDetailsImage");
		lenient().when(externalizerService.getFormattedUrl("/content/dam/image", resourceResolver))
				.thenReturn("localhost:4502/content/dam/image");
		lenient().when(varientValueMap.get("productDetails", String.class)).thenReturn("brand");
		lenient().when(pageProperties.getInherited("enableAddToQuoteCheckBox", Boolean.FALSE)).thenReturn(Boolean.TRUE);
		lenient().when(pageProperties.get(CommonConstants.PRODUCT_DETAILS)).thenReturn(object);
		lenient().when(hpValueMap.get(CommonConstants.PRODUCT_DETAILS)).thenReturn(obj);
		lenient().when(obj.toString()).thenReturn("speciesReactivity");
		pdpTabModel.getJapanAttrubutes(productDetailsMap, pageProperties, hpNodeResource, "JP");
		pdpTabModel.getKoreaAttributes(productDetailsMap, pageProperties, hpNodeResource, "KR");
		pdpTabModel.getAttrubutes(productDetailsMap, hpProperty, hpValueMap);
		
		pdpTabModel.init();

	}
	@Test
	void testGetProductDetails() throws LoginException,Exception {
		Map<String, String> productDetailsMap = new LinkedHashMap<String, String>();
		productDetailsMap.put(CommonConstants.PRODUCT_DETAILS, "toxicity");
		lenient().when(hpNodeResource.getParent()).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getChild("sap-cc")).thenReturn(res);
		lenient().when(hpNodeResource.getChild("region-details")).thenReturn(childRes);
		lenient().when(obj.toString()).thenReturn("speciesReactivity");
		lenient().when(hpProperty.containsKey(CommonConstants.RECOM_ASSAY_PROCEDURE)).thenReturn(true);
		lenient().when(hpProperty.containsKey(CommonConstants.PREPARATION_STORAGE)).thenReturn(true);
		lenient().when(hpProperty.containsKey(CommonConstants.PRODUCT_NOTICES)).thenReturn(true);
		lenient().when(hpProperty.get(CommonConstants.PREPARATION_STORAGE)).thenReturn(obj);
		lenient().when(hpProperty.get(CommonConstants.PRODUCT_NOTICES)).thenReturn(obj);
		lenient().when(hpProperty.get(CommonConstants.RECOM_ASSAY_PROCEDURE)).thenReturn(obj);
		lenient().when(hpProperty.containsKey(CommonConstants.ADD_PREPARATION_STORAGE)).thenReturn(true);
		lenient().when(hpProperty.get(CommonConstants.ADD_PREPARATION_STORAGE)).thenReturn(obj);
		lenient().when(hpProperty.containsKey(CommonConstants.PRODUCT_APPLICATION_TEST)).thenReturn(true);
		lenient().when(hpProperty.get(CommonConstants.PRODUCT_APPLICATION_TEST)).thenReturn(obj);
		lenient().when(hpProperty.containsKey(MANUFACTURING_COMPONENT)).thenReturn(true);
		lenient().when(hpProperty.get(MANUFACTURING_COMPONENT)).thenReturn(MANUFACTURING_COMPONENT);
		//pdpTabModel.getProductDetails(hpNodeResource, productDetailsMap, hpProperty);
	}
	/**
	 * 
	 * Test getters.
	 */
	@Test
	void testGetters() {

		assertNotNull(pdpTabModel.getAddPreparationStorage());
		assertNotNull(pdpTabModel.getAntibodyDetailsTabLabel());
		assertNotNull(pdpTabModel.getBdFormatdescription());
		assertNotNull(pdpTabModel.getReferenceList());
		assertNotNull(pdpTabModel.getEmmisionmax());
		assertNotNull(pdpTabModel.getExcitationmax());
		assertNotNull(pdpTabModel.getExcitationSource());
		assertNotNull(pdpTabModel.getFormatDetailsImage());
		assertNotNull(pdpTabModel.getFormatDetailsTabLabel());
		assertNotNull(pdpTabModel.getFormatname());
		assertNotNull(pdpTabModel.getLabelDescription());
		assertNotNull(pdpTabModel.getPreparationStorage());
		assertNotNull(pdpTabModel.getProductDetailsMap());
		assertNotNull(pdpTabModel.getRecomAssayProcedureList());
		assertNotNull(pdpTabModel.getTdsDescription());
		assertNotNull(pdpTabModel.getViewAllFormatsLabel());
		assertNotNull(pdpTabModel.getProductDetailsTabLabel());
		assertNotNull(pdpTabModel.getReferencesTabLabel());
		assertNotNull(pdpTabModel.getProductNoticesList());
		assertNotNull(pdpTabModel.getRapTitleText());
		assertNotNull(pdpTabModel.getPnTitleText());
		assertNotNull(pdpTabModel.getNoOfLinksToBeShown());
		assertNotNull(pdpTabModel.getRapshowmorelessDesktop());
		assertNotNull(pdpTabModel.getRapshowmorelessMobile());
		assertNotNull(pdpTabModel.getShowmorelabel());
		assertNotNull(pdpTabModel.getShowlesslabel());
		assertNotNull(pdpTabModel.getPnShowMoreLessLimitDesktop());
		assertNotNull(pdpTabModel.getPnShowMoreLessLimitMobile());
		pdpTabModel.getCloneApiObject();
		pdpTabModel.getCloneLabelObject();
		pdpTabModel.getFormatApiObject();
		pdpTabModel.getProductApplication();
		pdpTabModel.getAdditionalProperty();
		pdpTabModel.getFormatData();
		pdpTabModel.getInstrumentCtaLink();
		pdpTabModel.getInstrumentCtatext();
		pdpTabModel.getRecentlyViewedJsonString();
		pdpTabModel.getTdsRevisionNo();
		pdpTabModel.getMaterialNumber();
		pdpTabModel.getOtherTdsDescription();
		pdpTabModel.getRecomAssayProcedure();
		pdpTabModel.getComponentsLabel();
		pdpTabModel.getComponentDescriptionLabel();
		pdpTabModel.getComponentSizeLabel();
		pdpTabModel.getComponentPartNumberLabel();
		pdpTabModel.getComponentIsotypeLabel();
		pdpTabModel.getComponentEntrezGeneIdLabel();
		pdpTabModel.getComponentCloneLabel();
		pdpTabModel.getProductNotices();
		pdpTabModel.getProductType();
		pdpTabModel.getBrandDescription();
		pdpTabModel.getTdsCloneName();
		pdpTabModel.getBrand();
		pdpTabModel.getAbSeqBrand();
		pdpTabModel.getOptiBuild();
		pdpTabModel.getIsAuthor();

	}

}