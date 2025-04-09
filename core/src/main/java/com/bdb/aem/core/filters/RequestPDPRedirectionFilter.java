package com.bdb.aem.core.filters;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
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

import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Iterator;

@Component(service = Filter.class, property = {
		EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
		EngineConstants.SLING_FILTER_REQUEST_PATTERN + "=" + "/p/.*" })
public class RequestPDPRedirectionFilter implements Filter {

	private static final String MATERIAL_NUMBER = "materialNumber";

	Logger log = LoggerFactory.getLogger(RequestPDPRedirectionFilter.class);

	@Reference
	CatalogStructureUpdateService catalogStructureUpdateService;

	@Reference
	ResourceResolverFactory resolverFactory;
	
	/** The bdb api endpoint service. */
	@Reference
	BDBApiEndpointService bdbApiEndpointService;

	/** The externalizer. */
	@Reference
	Externalizer externalizer;

	String cookieHeader;
	
	Resource countryLanguageMapping;

	@Override
	public void destroy() {
		//Empty method

	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		log.debug("Inside RequestPDPRedirectionFilter");
		final SlingHttpServletResponse slingResponse = (SlingHttpServletResponse) response;
		final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
		String userHeaderGeoLocation = slingRequest.getHeader("User-Geolocation");
		String userHeadersiteLocale = slingRequest.getHeader("site_locale");
		log.debug("Request User-Geolocation value :: {}" , userHeaderGeoLocation);
		log.debug("Request SiteLocale value :: {}" , userHeadersiteLocale);
		final Resource resource = slingRequest.getResource();
		ResourceResolver resourceResolver = null;
		try {
			resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
			countryLanguageMapping = resourceResolver.getResource(bdbApiEndpointService.countryLanguageMapping()+"/"+JcrConstants.JCR_CONTENT+"/list");
			Cookie userCookie = slingRequest.getCookie("site_locale");
			Cookie geoLoctionCookie = slingRequest.getCookie("User-geolocation");
			String redirectUrl = externalizer.publishLink(resourceResolver, resourceResolver.map("/"));
			log.debug("Inside RequestPDPRedirectionFilter - Request Path {} " ,resource.getPath());
			String queryParam = slingRequest.getQueryString();
			if (resource.getPath().startsWith("/p/")) {
				log.debug("PDP page request captured. ");
				if (userCookie == null && null != geoLoctionCookie) {
					log.debug("RequestPDPRedirectionFilter - Redriection on geo location ");
					redirectUrl = pdpPageAnonymous(resource, geoLoctionCookie, resourceResolver);
					if(StringUtils.isNotEmpty(queryParam)) {
					redirectUrl = redirectUrl+"?"+queryParam;
					}
					CommonHelper.closeResourceResolver(resourceResolver);
					slingResponse.sendRedirect(redirectUrl);	
					
					return;

				} else if (null != userCookie && null == geoLoctionCookie) {
					log.debug("RequestPDPRedirectionFilter - Redriection on user cookie ");
					redirectUrl = pdpPage(resource, userCookie, resourceResolver);
					if(StringUtils.isNotEmpty(queryParam)) {
						redirectUrl = redirectUrl+"?"+queryParam;
						}
					CommonHelper.closeResourceResolver(resourceResolver);
					slingResponse.sendRedirect(redirectUrl);					
					return;

				} else if (null != userCookie && null != geoLoctionCookie) {
					log.debug("RequestPDPRedirectionFilter - Redriection on user cookie but geo location also present ");
					redirectUrl = pdpPage(resource, userCookie, resourceResolver);
					if(StringUtils.isNotEmpty(queryParam)) {
						redirectUrl = redirectUrl+"?"+queryParam;
						}
					CommonHelper.closeResourceResolver(resourceResolver);
					slingResponse.sendRedirect(redirectUrl);					
					return;
				} else if (null == userCookie && null == geoLoctionCookie
						&& (StringUtils.isNotEmpty(userHeaderGeoLocation)
								||StringUtils.isNotEmpty(userHeadersiteLocale))) {
					if(StringUtils.isNotEmpty(userHeadersiteLocale)) {
						log.debug("RequestPDPRedirectionFilter - No  cookie present redirecting based on header siteLocale ");
						redirectUrl = pdpPageAnonymousFirstReq(resource, userHeadersiteLocale, resourceResolver,true);
						if(StringUtils.isNotEmpty(queryParam)) {
							redirectUrl = redirectUrl+"?"+queryParam;
							}
					}
					else{
						log.debug("RequestPDPRedirectionFilter - No  cookie present redirecting based on header geoloaction ");
						redirectUrl = pdpPageAnonymousFirstReq(resource, userHeaderGeoLocation, resourceResolver,false);
						if(StringUtils.isNotEmpty(queryParam)) {
							redirectUrl = redirectUrl+"?"+queryParam;
							}
					}
					CommonHelper.closeResourceResolver(resourceResolver);
					slingResponse.sendRedirect(redirectUrl);				
					return;
				} else {
					log.debug("no cookie found redirecting to home page");
					CommonHelper.closeResourceResolver(resourceResolver);
					slingResponse.sendRedirect(redirectUrl);					
					return;
				}
			}
		} catch (LoginException | RepositoryException e) {
			log.error("Exception occured equestPDPRedirectionFilter", e);
			if(null != resourceResolver) {
				slingResponse.sendRedirect(externalizer.publishLink(resourceResolver, resourceResolver.map("/")));
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

	private String pdpPageAnonymous(Resource resource, Cookie geoLoctionCookie, ResourceResolver resourceResolver)
			throws RepositoryException {
		String resourcePath = resource.getPath();
		String catlogNumber = getCatlogNumber(resourcePath);
		if (StringUtils.isNotEmpty(catlogNumber)) {
			Resource productRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, catlogNumber,
					MATERIAL_NUMBER);
			if (productRes == null)
				return "/";
			Resource hpResource = resourceResolver.getResource(productRes.getPath().concat("/hp"));
			String pdpUrl = CommonHelper.getPdpProductUrl(hpResource);
			String languageCountryLocale = regionCountryLocale(geoLoctionCookie.getValue());
			pdpUrl = "/" + languageCountryLocale + pdpUrl;
			pdpUrl = externalizer.publishLink(resourceResolver, resourceResolver.map(pdpUrl));
			return pdpUrl.replace("pdp.", "");
		} else
			return "/";

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

	private String pdpPageAnonymousFirstReq(Resource resource, String geoValue, ResourceResolver resourceResolver,boolean flag)
			throws RepositoryException {
		String resourcePath = resource.getPath();
		String catlogNumber = getCatlogNumber(resourcePath);
		if (StringUtils.isNotEmpty(catlogNumber)) {
			Resource productRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, catlogNumber,
					MATERIAL_NUMBER);
			if (productRes == null)
				return "/";
			Resource hpResource = resourceResolver.getResource(productRes.getPath().concat("/hp"));
			String pdpUrl = CommonHelper.getPdpProductUrl(hpResource);
			if(flag) {
				pdpUrl = "/" + geoValue.toLowerCase() + pdpUrl;
			}else {
				String languageCountryLocale = regionCountryLocale(geoValue);
				pdpUrl = "/" + languageCountryLocale + pdpUrl;
			}
			
			pdpUrl = externalizer.publishLink(resourceResolver, resourceResolver.map(pdpUrl));
			return pdpUrl.replace("pdp.", "");
		} else
			return "/";

	}

	private String getCatlogNumber(String resourcePath) {
		int indexOf = resourcePath.lastIndexOf("/");
		if (indexOf != -1) {
			return resourcePath.substring(indexOf + 1);
		} else
			return StringUtils.EMPTY;
	}

	private String pdpPage(Resource resource, Cookie cookie, ResourceResolver resourceResolver)
			throws RepositoryException {
		String resourcePath = resource.getPath();
		String catlogNumber = getCatlogNumber(resourcePath);
		if (StringUtils.isNotEmpty(catlogNumber)) {
			Resource productRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, catlogNumber,
					MATERIAL_NUMBER);
			if (productRes == null)
				return externalizer.publishLink(resourceResolver, resourceResolver.map("/"));
			Resource hpResource = resourceResolver.getResource(productRes.getPath().concat("/hp"));
			String pdpUrl = CommonHelper.getPdpProductUrl(hpResource);
			pdpUrl = "/" + cookie.getValue().toLowerCase() + pdpUrl;
			pdpUrl = externalizer.publishLink(resourceResolver, resourceResolver.map(pdpUrl));
			return pdpUrl.replace("pdp.", "");
		} else
			return externalizer.publishLink(resourceResolver, resourceResolver.map("/"));

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//Empty method

	}
}
