package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Iterator;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.settings.SlingSettingsService;
import org.apache.solr.client.solrj.SolrServerException;
import org.joda.time.chrono.LenientChronology;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;


/**
 * The Class FetchRequiredCompanionProductImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FetchRequiredCompanionProductImplTest {
	
	/** The FetchRequiredCompanionProductImpl. */
	@InjectMocks
	FetchRequiredCompanionProductImpl fetchRequiredCompanionProductImpl;
	
	/** The sling settings service. */
	@Mock
	SlingSettingsService slingSettingsService;
	
	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;
	
	/** The bdb api endpoint service. */
	@Mock
    BDBApiEndpointService bdbApiEndpointService; 
	
	/** The solr config. */
	@Mock
	BDBSearchEndpointService solrConfig;
	
	/** The query. */
	@Mock
	Query query;
	
	/** The result. */
	@Mock
	SearchResult result;
	
	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The hp resource. */
	@Mock
	Resource hpResource;
	
	/** The varient value map. */
	@Mock
	ValueMap varientValueMap;
	
	/** The hp requried resource. */
	@Mock 
	Resource hpRequriedResource;
	
	/** The hp parent 1. */
	@Mock 
	Resource hpParent1;
	
	/** The hp parent 11. */
	@Mock 
	Resource hpParent11;
	
	/** The tag value map. */
	@Mock
	ValueMap tagValueMap;
	
	/** The variant value map HP. */
	@Mock
	ValueMap variantValueMapHP;
	
	/** The catalog structure update service. */
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
	
	/** The asset. */
	@Mock
	Asset asset;
	
	/** The tags. */
	Object tags;
	
	/** The child. */
	@Mock
	Resource variantResource, variantHpResource, child;
	
	/** The base product dam resource. */
	@Mock
	Resource baseResource, baseHpResource, baseProductDamResource;
	
	/** The children. */
	@Mock
	Iterator<Resource> children;
	
	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;
	
	/** The session. */
	@Mock
	Session session;
	
	/**
	 * Gets the requried companion product test.
	 *
	 * @return the requried companion product test
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void getRequriedCompanionProductTest() throws IOException, SolrServerException, RepositoryException {
		lenient().when(solrSearchService.getHpNodeResource("940510", "us", resourceResolver)).thenReturn(hpResource);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "940510", CommonConstants.MATERIAL_NUMBER)).thenReturn(variantResource);
		lenient().when(variantResource.getParent()).thenReturn(baseResource);
		lenient().when(baseResource.getChild(CommonConstants.HP)).thenReturn(baseHpResource);
		lenient().when(baseHpResource.getValueMap()).thenReturn(varientValueMap);
//		when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "553141", CommonConstants.MATERIAL_NUMBER)).thenReturn(variantResource);
		
//		when(hpResource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(CommonConstants.COMPANION_PRODUCTS)).thenReturn("[{\"baseMaterialNumber\":\"940150_base\",\"companionMaterialNumber\":\"553141\",\"companionOrdinal\":\"1\",\"companionType\":\"Required\"}]");
		lenient().when(varientValueMap.get(CommonConstants.COMPANION_PRODUCTS, String.class)).thenReturn("[{\"baseMaterialNumber\":\"940150_base\",\"companionMaterialNumber\":\"553141\",\"companionOrdinal\":\"1\",\"companionType\":\"Required\"}]");
		lenient().when(solrSearchService.getHpNodeResource("553141", "us", resourceResolver)).thenReturn(hpRequriedResource);
		lenient().when(hpRequriedResource.getValueMap()).thenReturn(tagValueMap);
		lenient().when(tagValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("Purified Rat Anti-Mouse CD16/CD32 (Mouse BD Fc Block™)");
		lenient().when(tagValueMap.get(CommonConstants.SIZE_QTY, String.class)).thenReturn("size");
		lenient().when(tagValueMap.get(CommonConstants.SIZE_UOM, String.class)).thenReturn("UOM");
		lenient().when(tagValueMap.get(CommonConstants.REGULATORY_STATUS_KEY, String.class)).thenReturn("status");
		lenient().when(tagValueMap.get(CommonConstants.CLONE, String.class)).thenReturn("[{\"polyclonal\":\"FALSE\",\"specificity\":\"CD16/CD32\",\"hostStrain\":\"SD\",\"entrezGeneId\":\"\",\"isoType\":\"IgG2b, κ\",\"tdsCloneDisplayName\":\"2.4G2 FcγRIII/FcγRII; Fcgr3/Fcgr2\",\"workshopNumber\":\"\",\"immunogen\":\"Mouse BALB/c Macrophage J774 \",\"tdsCloneName\":\"2.4G2\",\"hostSpecies\":\"Rat\",\"molecularWeight\":\"\"}]");
		lenient().when(hpRequriedResource.getParent()).thenReturn(hpParent1);
		lenient().when(hpParent1.getParent()).thenReturn(hpParent11);
		lenient().when(hpParent11.getPath()).thenReturn("/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-(ruo)/553141_base");
		lenient().when(hpParent11.getChild(CommonConstants.HP)).thenReturn(baseHpResource);
		lenient().when(baseHpResource.adaptTo(ValueMap.class)).thenReturn(variantValueMapHP);
		lenient().when(variantValueMapHP.get(CommonConstants.PRIMARY_SUPER_CATEGORY, String.class)).thenReturn("Products,Reagents,Immunoassay Reagents,,");
		lenient().when(variantValueMapHP.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("desc");
		lenient().when(hpParent1.getName()).thenReturn("name");
		
		fetchRequiredCompanionProductImpl.getRequriedCompanionProduct("940510", "us", solrConfig, solrSearchService, resourceResolver, catalogStructureUpdateService);
		
	}
	
	/**
	 * Gets the requried companion.
	 *
	 * @return the requried companion
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void getRequriedCompanion() throws IOException, SolrServerException, RepositoryException {
		lenient().when(solrSearchService.getHpNodeResource("940510", "us", resourceResolver)).thenReturn(hpResource);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "940510", CommonConstants.MATERIAL_NUMBER)).thenReturn(variantResource);
		lenient().when(variantResource.getParent()).thenReturn(baseResource);
		lenient().when(baseResource.getChild(CommonConstants.HP)).thenReturn(baseHpResource);
		lenient().when(baseHpResource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "553141", CommonConstants.MATERIAL_NUMBER)).thenReturn(variantResource);
		lenient().when(variantResource.getChild(CommonConstants.HP)).thenReturn(variantHpResource);
		lenient().when(variantHpResource.adaptTo(ValueMap.class)).thenReturn(tagValueMap);
		
//		when(hpResource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(CommonConstants.COMPANION_PRODUCTS)).thenReturn("[{\"baseMaterialNumber\":\"940150_base\",\"companionMaterialNumber\":\"553141\",\"companionOrdinal\":\"1\",\"companionType\":\"Required\"}]");
		lenient().when(varientValueMap.get(CommonConstants.COMPANION_PRODUCTS, String.class)).thenReturn("[{\"baseMaterialNumber\":\"940150_base\",\"companionMaterialNumber\":\"553141\",\"companionOrdinal\":\"1\",\"companionType\":\"Required\"}]");
		lenient().when(solrSearchService.getHpNodeResource("553141", "us", resourceResolver)).thenReturn(hpRequriedResource);
		lenient().when(hpRequriedResource.getValueMap()).thenReturn(tagValueMap);
		lenient().when(tagValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("Purified Rat Anti-Mouse CD16/CD32 (Mouse BD Fc Block™)");
		lenient().when(tagValueMap.get(CommonConstants.SIZE_QTY, String.class)).thenReturn("size");
		lenient().when(tagValueMap.get(CommonConstants.SIZE_UOM, String.class)).thenReturn("UOM");
		lenient().when(tagValueMap.get(CommonConstants.REGULATORY_STATUS_KEY, String.class)).thenReturn("status");
		lenient().when(tagValueMap.get(CommonConstants.CLONE, String.class)).thenReturn("[{\"polyclonal\":\"FALSE\",\"specificity\":\"CD16/CD32\",\"hostStrain\":\"SD\",\"entrezGeneId\":\"\",\"isoType\":\"IgG2b, κ\",\"tdsCloneDisplayName\":\"2.4G2 FcγRIII/FcγRII; Fcgr3/Fcgr2\",\"workshopNumber\":\"\",\"immunogen\":\"Mouse BALB/c Macrophage J774 \",\"tdsCloneName\":\"2.4G2\",\"hostSpecies\":\"Rat\",\"molecularWeight\":\"\"}]");
		
		lenient().when(varientValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("Purified Rat Anti-Mouse CD16/CD32 (Mouse BD Fc Block™)");
		lenient().when(varientValueMap.get(CommonConstants.REGULATORY_STATUS_KEY, String.class)).thenReturn("status");
		lenient().when(varientValueMap.get(CommonConstants.CLONE, String.class)).thenReturn("[{\"polyclonal\":\"FALSE\",\"specificity\":\"CD16/CD32\",\"hostStrain\":\"SD\",\"entrezGeneId\":\"\",\"isoType\":\"IgG2b, κ\",\"tdsCloneDisplayName\":\"2.4G2 FcγRIII/FcγRII; Fcgr3/Fcgr2\",\"workshopNumber\":\"\",\"immunogen\":\"Mouse BALB/c Macrophage J774 \",\"tdsCloneName\":\"2.4G2\",\"hostSpecies\":\"Rat\",\"molecularWeight\":\"\"}]");
		
		lenient().when(variantHpResource.getParent()).thenReturn(hpParent1);
		lenient().when(hpParent1.getParent()).thenReturn(hpParent11);
		lenient().when(hpParent1.getPath()).thenReturn("/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-(ruo)/553141_base/Test");
		lenient().when(hpParent11.getPath()).thenReturn("/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-(ruo)/553141_base");
		lenient().when(hpParent11.getChild(CommonConstants.HP)).thenReturn(baseHpResource);
		lenient().when(baseHpResource.adaptTo(ValueMap.class)).thenReturn(variantValueMapHP);
		lenient().when(variantValueMapHP.get(CommonConstants.PRIMARY_SUPER_CATEGORY, String.class)).thenReturn("Products,Reagents,Immunoassay Reagents,,");
		lenient().when(variantValueMapHP.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("desc");
		lenient().when(hpParent1.getName()).thenReturn("name");
		
		
		lenient().when(bdbApiEndpointService.getVialImagesBasePath()).thenReturn("/path");
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(query.getResult()).thenReturn(result);
		fetchRequiredCompanionProductImpl.getRequriedCompanionProduct("940510", "us", solrConfig, solrSearchService, resourceResolver, catalogStructureUpdateService);
		
	}
}
