package com.bdb.aem.core.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.jcr.Session;

import com.bdb.aem.core.services.PageCollectionService;
import com.bdb.aem.core.util.CommonConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.NameConstants;

@Component(service = PageCollectionService.class, name = "Page Collection Service", immediate = true)
public class PageCollectionServiceImpl implements PageCollectionService {

	private static final String XF_ROOT="/content/experience-fragments";
	private static final String CONF_TEMPLATE_SUFFIX_PATH="/settings/wcm/templates";

	private static final Logger LOGGER = LoggerFactory.getLogger(PageCollectionServiceImpl.class);
	private static final String SLASH = "/";
	private static final String CQ_XF_VARIANT_TYPE="cq:xfVariantType";
	private static final String CQ_PAGE=NameConstants.NT_PAGE;


	@Reference
	private QueryBuilder queryBuilder;

	@Override
	public List<String> getReplicationPages(String resourcePath,ResourceResolver resourceResolver) {
        LOGGER.debug("Inside getReplicationPages method: start");
	    final String rootPagePath = CommonConstants.CONST_BDB_ROOT_PATH;
        LOGGER.debug(" rootPagePath" +rootPagePath );
		final String confRootPath = CommonConstants.BDB_CONF_ROOT_PATH;
        LOGGER.debug(" confRootPath" +confRootPath );
		if(resourcePath.startsWith(XF_ROOT)) {
            LOGGER.debug(" resourcePath.startsWith(XF_ROOT) : true" );
			return getPageListReferringToXF(resourcePath,resourceResolver);
		}
		else if(resourcePath.startsWith(confRootPath)) {
            LOGGER.debug(" resourcePath.startsWith(confRootPath) : true" );
			Session session = resourceResolver.adaptTo(Session.class);
			if(session!=null) {
				return getPagesFromTemplateList(new ArrayList<>(Arrays.asList(resourcePath)),session);
			}
		}

		else if(resourcePath.startsWith(rootPagePath) || resourcePath.startsWith(CommonConstants.CONST_BDB_DAM_ROOT_PATH)) {
            LOGGER.debug(" resourcePath.startsWith(rootPagePath) || resourcePath.startsWith(DAM_ROOT) : true" );
			return new ArrayList<>(Arrays.asList(resourcePath));
		}
		return new ArrayList<>();
	}

	private List<String> getPageListReferringToXF(String resourcePath,ResourceResolver resourceResolver) {
		Set<String> pageList = new HashSet<>();
		String jcrContent = new StringBuilder(resourcePath).append(SLASH).append(JcrConstants.JCR_CONTENT).toString();
		Resource resource = resourceResolver.getResource(jcrContent);
		if(resource!=null) {
			ValueMap valueMap = resource.getValueMap();

			String variantType  = valueMap.get(CQ_XF_VARIANT_TYPE, String.class);

			if(variantType!=null && !variantType.isEmpty()) {
				pageList.addAll(listPagesUsingVariation(resourcePath,resourceResolver));
			}
			else {
				List<String> variationList = getAllVariants(resourcePath,resourceResolver);
				for(String variation : variationList) {
					pageList.addAll(listPagesUsingVariation(variation,resourceResolver));
				}
			}
		}
		return new ArrayList<>(pageList);
	}

	private List<String> getAllVariants(String xfPath,ResourceResolver resourceResolver) {
		List<String> variationList = new ArrayList<>();
		Resource xfResource = resourceResolver.getResource(xfPath);
		for(Resource variation  : xfResource.getChildren()) {
			if(!variation.getName().equals(JcrConstants.JCR_CONTENT)) {
				variationList.add(variation.getPath());
			}
		}
		return variationList;
	}

	@Override
	public List<String> listPagesUsingVariation(String variationPath,ResourceResolver resourceResolver) {
		Set<String> pageList = new HashSet<>();
		pageList.addAll(getPagesFromContentUsingXFVariation(variationPath,resourceResolver));
		pageList.addAll(getPagesFromTemplateUsingXFVariation(variationPath, resourceResolver));
		return new ArrayList<>(pageList);
	}

	@Override
	public List<String> getPagesFromContentUsingXFVariation(String variationPath,ResourceResolver resourceResolver) {
		Set<String> pageList = new HashSet<>();
		Map<String, String> map = new HashMap<>();
    	final String rootPagePath = CommonConstants.CONST_BDB_ROOT_PATH;
		map.put(CommonConstants.QUERY_BUILDER_PATH, rootPagePath);
		map.put(CommonConstants.QUERY_BUILDER_PROPERTY, CommonConstants.BDB_XF_ROOT_PATH);
		map.put(CommonConstants.QUERY_BUILDER_PROPERTY_VALUE, variationPath);
		map.put(CommonConstants.QUERY_BUILDER_OFFSET, "0");
	    map.put(CommonConstants.QUERY_BUILDER_LIMIT,"-1");

		Session session = resourceResolver.adaptTo(Session.class);
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		if(session!=null && pageManager!=null) {
			Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);

		    SearchResult result = query.getResult();
		    Iterator<Resource> iterator = result.getResources();
		    while(iterator.hasNext()) {
		    	 Resource xfResource  = iterator.next();
		    	 Page page = pageManager.getContainingPage(xfResource);
		    	 pageList.add(page.getPath());
		    }
		}
		return new ArrayList<>(pageList);
	}

	@Override
	public List<String> getPagesFromTemplateUsingXFVariation(String variationPath,ResourceResolver resourceResolver) {
		Set<String> uniqueTemplatePaths = new HashSet<>();
		Map<String, String> templateQueryMap = new HashMap<>();
    	final String rootTemplatePath =  new StringBuilder(CommonConstants.BDB_CONF_ROOT_PATH).append(CONF_TEMPLATE_SUFFIX_PATH).toString();
		templateQueryMap.put(CommonConstants.QUERY_BUILDER_PATH, rootTemplatePath);
		templateQueryMap.put(CommonConstants.QUERY_BUILDER_PROPERTY ,CommonConstants.BDB_XF_ROOT_PATH);
		templateQueryMap.put(CommonConstants.QUERY_BUILDER_PROPERTY_VALUE, variationPath);
		templateQueryMap.put(CommonConstants.QUERY_BUILDER_OFFSET, "0");
	    templateQueryMap.put(CommonConstants.QUERY_BUILDER_LIMIT, "-1");

		Session session = resourceResolver.adaptTo(Session.class);
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		if(session!=null && pageManager!=null) {
			Query templateQuery = queryBuilder.createQuery(PredicateGroup.create(templateQueryMap), session);
		    SearchResult templateResult = templateQuery.getResult();
		    Iterator<Resource> templateIterator = templateResult.getResources();
		    while(templateIterator.hasNext()) {
		    	 Resource xfResource  = templateIterator.next();
		    	 Page page = pageManager.getContainingPage(xfResource);
		    	 Resource resource  = page.adaptTo(Resource.class);
		    	 if(resource!=null) {
		    		 String templatePath = resource.getParent().getPath();
		    		 uniqueTemplatePaths.add(templatePath);
		    	 }
		    }
		    if(LOGGER.isDebugEnabled()) {
		    	 LOGGER.debug("UNIQUE TEMPLATE PATHS {} ::",uniqueTemplatePaths);
		    }
		    return new ArrayList<>(getPagesFromTemplateList(new ArrayList<>(uniqueTemplatePaths), session));

		}
		return new ArrayList<>();
	}

	@Override
	public List<String> getPagesFromTemplateList(List<String> templatePathList, Session session){
            Set<String> pageList = new HashSet<>();
		if(!templatePathList.isEmpty()) {
		    	final String rootPagePath =  CommonConstants.CONST_BDB_ROOT_PATH;
			    Map<String, String> pageQueryMap = new HashMap<>();
			    pageQueryMap.put(CommonConstants.QUERY_BUILDER_PATH, rootPagePath);
			    pageQueryMap.put(CommonConstants.QUERY_BUILDER_TYPE, CQ_PAGE);
			    pageQueryMap.put(CommonConstants.QUERY_BUILDER_PROPERTY, "jcr:content/cq:template");
			    int count = 1;
			    for(String templatePath : templatePathList) {
			    	String propValue = new StringBuilder("property.").append(String.valueOf(count)).append("_value").toString();
			    	pageQueryMap.put(propValue, templatePath);
			    	count++;
			    }
			    pageQueryMap.put(CommonConstants.QUERY_BUILDER_OFFSET, "0");
			    pageQueryMap.put(CommonConstants.QUERY_BUILDER_LIMIT, "-1");
			    Query pageQuery = queryBuilder.createQuery(PredicateGroup.create(pageQueryMap), session);
			    SearchResult pageResult = pageQuery.getResult();
			    Iterator<Resource> pageIterator = pageResult.getResources();
			    while(pageIterator.hasNext()) {
			    	Resource pageResource  = pageIterator.next();
			    	pageList.add(pageResource.getPath());
			    }
		    }
		return new ArrayList<>(pageList);
	}

	@Override
	public List<String> getShortUrls(List<String> pageList,ResourceResolver resourceResolver, boolean includeTrailingSlash, boolean includeDomain) {
		final Set<String> finalPageList = new HashSet<>();
		Externalizer externalizer = resourceResolver.adaptTo(Externalizer.class);
		if(pageList!=null && !pageList.isEmpty() && externalizer!=null) {
			for(String page : pageList ) {
				String shortUrl = resourceResolver.map(page);
				LOGGER.debug("shortUrl from map" + shortUrl);
				shortUrl = page.equals(shortUrl) ? handleShortUrlManually(shortUrl):shortUrl;
				shortUrl = includeDomain ? addDomain(shortUrl,externalizer,resourceResolver): shortUrl;
				finalPageList.add(shortUrl);

				if(includeTrailingSlash) {
					finalPageList.add(new StringBuilder(shortUrl).append(CommonConstants.SINGLE_SLASH).toString());
				}
			}
		}
		return new ArrayList<>(finalPageList);
	}
	private String addDomain(String path, Externalizer externalizer,ResourceResolver resourceResolver) {
		//return externalizer.externalLink(resourceResolver, Externalizer.PUBLISH, path);
		//Hard-coding to dev akamai domain for now. Should be reverted once externalizer issue is resolved.
		return "https://dev-gl.bdbiosciences.com"+path;
	}

	private String handleShortUrlManually(String pagePath) {
		LinkedList<String> list = new LinkedList<>( Arrays.asList(pagePath.split(SLASH)));
		list.removeFirst();
		list.removeFirst();
		list.removeFirst();
		list.removeFirst();
		list.removeFirst();
		return new StringBuilder(SLASH).append(list.stream().collect(Collectors.joining(SLASH))).toString();
	}

}
