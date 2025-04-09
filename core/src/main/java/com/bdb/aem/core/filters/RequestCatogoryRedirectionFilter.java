package com.bdb.aem.core.filters;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.Externalizer;
import com.day.cq.commons.jcr.JcrConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.engine.EngineConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Iterator;

@Component(service = Filter.class, property = {
		EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
		EngineConstants.SLING_FILTER_REQUEST_PATTERN + "=" + "/c/.*" })
public class RequestCatogoryRedirectionFilter implements Filter {

	Logger log = LoggerFactory.getLogger(RequestCatogoryRedirectionFilter.class);
	
	@Reference
	Externalizer externalizer;
	
	@Reference
	ResourceResolverFactory resolverFactory;
	
	/** The bdb api endpoint service. */
	@Reference
	BDBApiEndpointService bdbApiEndpointService;
	
	Resource countryLanguageMapping;
	
	String redirectUrl;

	@Override
	public void destroy() {
		//Empty method
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		log.debug("Inside RequestCatogoryRedirectionFilter");
		final SlingHttpServletResponse slingResponse = (SlingHttpServletResponse) response;
		final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
		String userHeaderGeoLocation = slingRequest.getHeader("User-Geolocation");
		String userHeadersiteLocale = slingRequest.getHeader("site_locale");
		log.debug("RequestCatogoryRedirectionFilter Request User-Geolocation value :: {}" , userHeaderGeoLocation);
		log.debug("RequestCatogoryRedirectionFilter Request SiteLocale value :: {}" , userHeadersiteLocale);
		final Resource resource = slingRequest.getResource();
		ResourceResolver resourceResolver = null;
		try {
			resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
			countryLanguageMapping = resourceResolver.getResource(bdbApiEndpointService.countryLanguageMapping()+"/"+JcrConstants.JCR_CONTENT+"/list");
			Cookie userCookie = slingRequest.getCookie("site_locale");
			Cookie geoLoctionCookie = slingRequest.getCookie("User-geolocation");
			redirectUrl = externalizer.publishLink(resourceResolver, resourceResolver.map("/"));
			log.debug("Inside RequestPDPRedirectionFilter - Request Path  {}", resource.getPath());
			log.debug("Request User-Geolocation value :: {}", userHeaderGeoLocation);
			if (resource.getPath().startsWith("/c/")) {
				log.debug("Category page request captured. ");
				if (userCookie == null && null != geoLoctionCookie) {
					log.debug("RequestCatogoryRedirectionFilter - Redirection on geo location ");
					redirectUrl = categoryLandingPageAnonymous(resource, geoLoctionCookie, resourceResolver);
					CommonHelper.closeResourceResolver(resourceResolver);
					slingResponse.sendRedirect(redirectUrl);
					return;
				} else if (null != userCookie && null == geoLoctionCookie) {
					log.debug("RequestCatogoryRedirectionFilter - Redirection on user cookie ");
					redirectUrl = categoryLandingPage(resource, userCookie, resourceResolver);
					CommonHelper.closeResourceResolver(resourceResolver);
					slingResponse.sendRedirect(redirectUrl);
					return;
				} else if (null != userCookie && null != geoLoctionCookie) {
					log.debug(
							"RequestCatogoryRedirectionFilter - Redirection on user cookie but geo location also present ");
					redirectUrl = categoryLandingPage(resource, userCookie, resourceResolver);
					CommonHelper.closeResourceResolver(resourceResolver);
					slingResponse.sendRedirect(redirectUrl);
					return;
				} else if (null == userCookie && null == geoLoctionCookie && (StringUtils.isNotEmpty(userHeaderGeoLocation)||StringUtils.isNotEmpty(userHeadersiteLocale))) {
					if(StringUtils.isNotEmpty(userHeadersiteLocale)) {
						log.debug("no cookie found redirecting based on header request siteLocalegeoloaction");
						redirectUrl = categoryLandingPageFirstReq(resource, userHeadersiteLocale, resourceResolver,true);
					}else {
						log.debug("no cookie found redirecting based on header request geoloaction");
						redirectUrl = categoryLandingPageFirstReq(resource, userHeaderGeoLocation, resourceResolver,false);
					}					
					CommonHelper.closeResourceResolver(resourceResolver);
					slingResponse.sendRedirect(redirectUrl);
					return;
				}else {
					log.debug("no cookie found redirecting to home page");
					CommonHelper.closeResourceResolver(resourceResolver);
					slingResponse.sendRedirect(redirectUrl);					
					return;
				}
			}
		}catch (LoginException e) {
			log.error("Exception occured RequestCatogoryRedirectionFilter {}", e.getMessage());
			CommonHelper.closeResourceResolver(resourceResolver);
			slingResponse.sendRedirect(redirectUrl);
			return;
		}
		
		filterChain.doFilter(request, response);
	}

	private String categoryLandingPageFirstReq(Resource resource, String geoLocation,
			ResourceResolver resourceResolver, boolean flag) {
		String resourcePath = resource.getPath();
		String languageCountryLocale = regionCountryLocale(geoLocation);
		if(flag) {
			resourcePath = resourcePath.replace("/c/", "/" + geoLocation.toLowerCase() + "/");
		}else {
			resourcePath = resourcePath.replace("/c/", "/" + languageCountryLocale + "/");
		}		
		resourcePath = externalizer.publishLink(resourceResolver, resourceResolver.map(resourcePath));
		return resourcePath;
	}

	private String categoryLandingPageAnonymous(Resource resource, Cookie geoLoctionCookie, ResourceResolver resourceResolver) {
		String resourcePath = resource.getPath();
		String languageCountryLocale = regionCountryLocale(geoLoctionCookie.getValue());
		resourcePath = resourcePath.replace("/c/", "/" + languageCountryLocale + "/");
		resourcePath = externalizer.publishLink(resourceResolver, resourceResolver.map(resourcePath));
		return resourcePath;
	}

	private String categoryLandingPage(Resource resource, Cookie cookie, ResourceResolver resourceResolver) {
		String resourcePath = resource.getPath();
		resourcePath = resourcePath.replace("/c/", "/" + cookie.getValue() + "/");
		resourcePath = externalizer.publishLink(resourceResolver, resourceResolver.map(resourcePath));
		return  resourcePath;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//Empty method
	}
	
	private String regionCountryLocale(String geoValue) {
		if(null == countryLanguageMapping) {
			return "/";
		}
		  Iterator<Resource> items = countryLanguageMapping.listChildren();
		  while(items.hasNext()) {
			  Resource itemRes = items.next();
			  if (null != itemRes) {
	                ValueMap properties = itemRes.getValueMap();
	                if(StringUtils.isNotEmpty(properties.get("value", String.class))) {
	                	String country = properties.get("value", String.class).trim();
		                if(geoValue.equalsIgnoreCase(country)) {
		                	return StringUtils.isNotEmpty(properties.get(JcrConstants.JCR_TITLE, String.class))?properties.get(JcrConstants.JCR_TITLE, String.class):"en-us";
		                }
	                }  
	            }
		  }
		return "/";
	}

}
