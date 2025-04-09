package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import java.util.List;

import javax.jcr.Session;

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
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

@ExtendWith({ MockitoExtension.class })
public class QualifyingProductsModelTest {
	
	/** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;

    /** The resource resolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
    
    @InjectMocks
    QualifyingProductsModel qualifyingProductsModel;
    @Mock
    BDBApiEndpointService bdbApiEndpointService;
    @Mock
    TagManager tagManager;
    @Mock
    Tag tag; 
    @Mock
    Resource hpResource,hpParent,hpParentParent;
    @Mock
    SolrSearchService solrSearchService;
    @Mock
    ValueMap tagValueMap,variantValueMapHP;

    @Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
    @Mock
	Session session;
    @Mock
	Query query;
	@Mock
	 QueryBuilder queryBuilder;
	
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("hybrisdomain");
        lenient().when(bdbApiEndpointService.getCommerceBoxAPIEndpoint()).thenReturn("comerrecebox");
        lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
        lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("hybrisdomain");
        lenient().when(tagManager.resolve("910450")).thenReturn(tag);
        lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("hybrisdomain");
        lenient().when(tag.getName()).thenReturn("tagName");
        lenient().when(solrSearchService.getHpNodeResource("tagName", "us", resourceResolver)).thenReturn(hpResource);
        lenient().when(hpResource.getValueMap()).thenReturn(tagValueMap);
        lenient().when(tagValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("desc");
        lenient().when(tagValueMap.get(CommonConstants.REGULATORY_STATUS_KEY, String.class)).thenReturn("regkey");
        lenient().when(tagValueMap.get(CommonConstants.SIZE_QTY, String.class)).thenReturn("size");
        lenient().when(tagValueMap.get(CommonConstants.SIZE_UOM, String.class)).thenReturn("UOM");
        lenient().when(tagValueMap.get(CommonConstants.PRODUCT_CLONE, String.class)).thenReturn("[{\"polyclonal\":\"FALSE\",\"specificity\":\"CD16/CD32\",\"hostStrain\":\"SD\",\"entrezGeneId\":\"\",\"isoType\":\"IgG2b, κ\",\"tdsCloneDisplayName\":\"2.4G2 FcγRIII/FcγRII; Fcgr3/Fcgr2\",\"workshopNumber\":\"\",\"immunogen\":\"Mouse BALB/c Macrophage J774 \",\"tdsCloneName\":\"2.4G2\",\"hostSpecies\":\"Rat\",\"molecularWeight\":\"\"}]");
        lenient().when(hpResource.getParent()).thenReturn(hpParent);
        lenient().when(hpParent.getParent()).thenReturn(hpParentParent);
        lenient().when(hpParentParent.getPath()).thenReturn("/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-(ruo)/553141_base");
        lenient().when(hpResource.adaptTo(ValueMap.class)).thenReturn(variantValueMapHP);
        lenient().when(variantValueMapHP.get(CommonConstants.PRIMARY_SUPER_CATEGORY, String.class)).thenReturn("Products,Reagents,Immunoassay Reagents,,");
        lenient().when(variantValueMapHP.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("desc");
        lenient().when(hpParent.getName()).thenReturn("name");
    }
    
    @Test
    void testInit() {
    	qualifyingProductsModel.init();
    }
    @Test
    void testGetters() {
    	qualifyingProductsModel.getProductClone("tdsCloneName", tagValueMap);
    	qualifyingProductsModel.getQualifyingProductsTitle();
    	qualifyingProductsModel.getQualifyingProductsDesription();
    	qualifyingProductsModel.getQualProdConfigJson();
    	qualifyingProductsModel.getQualProdLabelsJson();
    }
    @Test
    void testSkuIdDetails() throws Exception {
    	lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "910450",CommonConstants.MATERIAL_NUMBER)).thenReturn(hpParent);
    	 lenient().when(tag.getName()).thenReturn("910450");
    	 lenient().when(solrSearchService.getHpNodeResource("910450", "us", resourceResolver)).thenReturn(hpResource);
    	 lenient().when(hpParentParent.getChild(CommonConstants.HP)).thenReturn(hpResource);
    	 lenient().when(hpParent.getChild(CommonConstants.HP)).thenReturn(hpResource);
    	 lenient().when(hpParent.getChild("910450")).thenReturn(hpResource);
    	 lenient().when(variantValueMapHP.get(CommonConstants.SIZE_QTY, String.class)).thenReturn("desc");
    	 lenient().when(hpParent.getName()).thenReturn("materialNumber");
    	 lenient().when(hpParent.getPath()).thenReturn("/content");
 		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
 		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
    	qualifyingProductsModel.getSkuIdDetails("910450");    
    }
    
    @Test
    void testLoginException() throws LoginException {
    	lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
    	qualifyingProductsModel.init();  
    }
    
    @Test
    void testGetSkuIdDetailsException() {
    	 lenient().when(tag.getName()).thenReturn("abcd");
    	 lenient().when(solrSearchService.getHpNodeResource("abcd", "us", resourceResolver)).thenReturn(hpResource);
    	//qualifyingProductsModel.getSkuIdDetails("910450");    
    }

}
