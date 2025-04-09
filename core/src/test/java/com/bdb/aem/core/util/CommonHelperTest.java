package com.bdb.aem.core.util;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
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
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;

import junitx.util.PrivateAccessor;

@ExtendWith({MockitoExtension.class})
public class CommonHelperTest {
	@InjectMocks
	CommonHelper CommonHelper;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The resource. */
	@Mock
	Resource resource,hpNodeResource;
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	@Mock
	SolrSearchService solrSearchService;
	@Mock
	ResourceResolverFactory resourceResolverFactory;
	@Mock
	Query query;
	@Mock
	 SearchResult searchResult;
	@Mock
    Iterator<Resource> resources;
	@Mock
	Session session;
	@Mock
	Page currentPage;
	@Mock
	HttpServletRequest request;
	@Mock
	Enumeration<String> headerNames;
	@Mock
	SlingHttpServletRequest req;
	@Mock
    ExternalizerService externalizerService;
	@Mock
	QueryBuilder queryBuilder;
	@Mock
	TagManager tagManager;
	@Mock
	Asset asset;
	@Mock
	Object obj;
	@Mock
	ValueMap childValueMap;
	@Mock
	 ResourceMetadata  resourceMetadata;
	@Mock
	Tag tag;
	
	
	String productVarient="productVarient";
	String country="country";
	String path="/content/bdb/na/products/reagents";
	 Long resultCount = 1L;
	 JsonArray ruoJsonArray=new JsonArray();
	 String[] dyeNameList= {"country","country2"};
	 List<Resource> resourceList=new ArrayList<>();
	 @BeforeEach
	void setUp() throws Exception {
		 lenient().when(resourceResolver.getResource(Mockito.any())).thenReturn(hpNodeResource);
		 lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		 lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		 lenient().when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		 lenient().when(query.getResult()).thenReturn(searchResult);
		 lenient().when(searchResult.getTotalMatches()).thenReturn(resultCount);
		 lenient().when(searchResult.getResources()).thenReturn(resources);
		 lenient().when(resources.next()).thenReturn(resource);
		}
	 @Test
	void testGetVariantRes() throws LoginException ,Exception{
		when(resources.hasNext()).thenReturn(true).thenReturn(false);
		when(resources.next()).thenReturn(resource);
		when(resource.hasChildren()).thenReturn(true);
		when(session.isLive()).thenReturn(true);
		CommonHelper.getVarientDetails(productVarient, country, solrSearchService);
		CommonHelper.getVariantRes(resource, query);
		CommonHelper.getResourceResolver(session, resourceResolverFactory);
		CommonHelper.stringToInteger("1");
		CommonHelper.toTitleCase(country);
		CommonHelper.sessionLogout(session);
		CommonHelper.saveSession(session);
		CommonHelper.closeSession(session);
	}
	
	@Test
	void testGetpath() throws LoginException ,Exception{
		lenient().when(request.getHeaderNames()).thenReturn(headerNames);
		lenient().when(headerNames.hasMoreElements()).thenReturn(true,false);
		lenient().when(headerNames.nextElement()).thenReturn(country);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("publish");
		CommonHelper.getHeadersInfo(request);
		CommonHelper.getSpecificHeader(request, country);
		CommonHelper.getCurrentRegionUrl(path, currentPage, resourceResolver, bdbApiEndpointService);
		CommonHelper.getShortUrl(path, currentPage, resourceResolver, "publish");
		CommonHelper.splitStringByKey(path, country);
		CommonHelper.checkForMultipleSuperCategories("c|d");
		CommonHelper.removeTrailingStartingHyphens("-name-");
		CommonHelper.getSpecificityFromHp(childValueMap);
	}
	@Test
	void testGetcreateEndPointUrl() throws LoginException ,Exception{
		Map<String, String> mapObj=new HashMap<>();
		mapObj.put("string1", "value");
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn(country);
		lenient().when(resource.getParent()).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getParent()).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getPath()).thenReturn("/content/dam");
		lenient().when(currentPage.getAbsoluteParent(2)).thenReturn(currentPage);
		lenient().when(currentPage.getPath()).thenReturn(country);
		lenient().when(hpNodeResource.listChildren()).thenReturn(resources);
		when(resources.hasNext()).thenReturn(true,false);
		when(resources.next()).thenReturn(resource);
		lenient().when(hpNodeResource.getName()).thenReturn(country);
		lenient().when(hpNodeResource.getResourceMetadata()).thenReturn(resourceMetadata);
		lenient().when(hpNodeResource.getChild(CommonConstants.HP)).thenReturn(resource);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(childValueMap);
		CommonHelper.createEndPointUrl(bdbApiEndpointService, country, req)	;
		CommonHelper.getThumbnailImagePath(resource, externalizerService, resourceResolver);
		CommonHelper.getEmailDomainList(currentPage, resourceResolver);
		CommonHelper.getResourceMetadataProperty(resourceResolver, country, path);
		CommonHelper.getMetaDataAttribute(asset, country);
	

	}
	@Test
	void testGetGlobalThumbnailImage() throws LoginException ,Exception{
		Object[] objs= {"obj1"};
		Map<String, String> mapObj=new HashMap<>();
		mapObj.put("string1", "value");
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn(country);
		lenient().when(resource.getParent()).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getParent()).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getPath()).thenReturn("/content/dam");
		lenient().when(currentPage.getAbsoluteParent(2)).thenReturn(currentPage);
		lenient().when(currentPage.getPath()).thenReturn(country);
		lenient().when(hpNodeResource.listChildren()).thenReturn(resources);
		when(resources.hasNext()).thenReturn(true,false);
		when(resources.next()).thenReturn(resource);
		lenient().when(hpNodeResource.getName()).thenReturn(country);
		lenient().when(hpNodeResource.getResourceMetadata()).thenReturn(resourceMetadata);
		lenient().when(hpNodeResource.getChild(CommonConstants.HP)).thenReturn(resource);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(childValueMap);
		lenient().when(resource.adaptTo(Asset.class)).thenReturn(asset);
		lenient().when(asset.getMetadata(CommonConstants.CQ_TAGS)).thenReturn(objs);
		CommonHelper.getGlobalThumbnailImage(resourceResolver, resource, externalizerService, bdbApiEndpointService);

	}
	
	@Test
	void testGetHandleRTEAnchorLink() throws LoginException ,Exception{
		resourceList.add(hpNodeResource);
		lenient().when(hpNodeResource.getChild(CommonConstants.METADATA_PATH_AS_CHILD)).thenReturn(resource);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(childValueMap);
		lenient().when(childValueMap.get("cq:sku-id", String[].class)).thenReturn(dyeNameList);
		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(tagManager.resolve(Mockito.any())).thenReturn(tag);
		lenient().when(tag.getTitle()).thenReturn(country);
		CommonHelper.HandleRTEAnchorLink(path, externalizerService, resourceResolver, country);
		CommonHelper.getDyeNamePaths(ruoJsonArray, asset, path, childValueMap, childValueMap, path, resourceResolver);
		CommonHelper.getVialImages(resourceResolver, ruoJsonArray, country, resourceList);
				
	}
	
}
