package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Service Implementation to externalize and format urls and asset paths
 *
 */
@Component(service = ExternalizerService.class, immediate = true)
public class ExternalizerServiceImpl implements ExternalizerService {

	/** The externalizer. */
	@Reference
	Externalizer externalizer;

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ExternalizerServiceImpl.class);
	/** The bdb api endpoint service. */
	@Reference
	BDBApiEndpointService bdbApiEndpointService;
	/**
	 * Method to check the type of url and format it
	 */
	public String getFormattedUrl(String url, ResourceResolver resourceResolver) {
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		
		if(null != url && url.startsWith("mailto:")) {
			return url;
		}
		if (null != url) {
			// when url is an aem page
			if (null != pageManager.getPage(url) || url.startsWith(CommonConstants.CONST_BDB_ROOT_PATH)) {
				
				/*Added condition for Query params*/
				if(StringUtils.contains(url, "?")) 
				{
					String[] splitUrl = StringUtils.split(url, "\\?"); 
		            String firstHalfUrl = splitUrl[0];
		            String remUrl = StringUtils.replace(url, splitUrl[0], "");
		            url = externalizedUrl(firstHalfUrl, resourceResolver);
					url = addHTMLifAuthor(resourceResolver, url);
					url = url + remUrl;

		        }
				else {
					url = externalizedUrl(url, resourceResolver);
					url = addHTMLifAuthor(resourceResolver, url);
				}
			}
			// when url is an pdp page for search page.
			else if (null != pageManager.getPage(url) || url.startsWith("/{language-country}")) {
				url = externalizedUrlWithoutMapping(url, resourceResolver);
				url = addHTMLifAuthor(resourceResolver, url);
			}
			// when url is a dam path
			else if (url.startsWith(CommonConstants.CONTENT_DAM)) {
				if (null != resourceResolver.getResource(url)) {
					url = externalizedUrlWithoutMapping(url, resourceResolver);
				} else {
					url = StringUtils.EMPTY;
				}
			} else if (url.startsWith(CommonConstants.SINGLE_SLASH + CommonConstants.BIN)) {
				url = externalizedUrlWithoutMapping(url, resourceResolver);
			}
			// when url is an external link without http/https
			else if (!url.startsWith(CommonConstants.HTTP) && !url.startsWith(CommonConstants.HTTPS)
					&& !url.isEmpty()) {
				url = CommonConstants.HTTPS + CommonConstants.COLON_SLASH_SLASH + url;
			}
			// when url is an external link but unsecured
			else if (url.startsWith(CommonConstants.HTTP) && !url.isEmpty()) {
				url = url.replace(CommonConstants.HTTP, CommonConstants.HTTPS);
			}
			// when url hasn't been authored
			else if (url.isEmpty()) {
				url = StringUtils.EMPTY;
			}
			// when url is an external url or it is empty
			else {
				url = url.trim();
			}

			return url;
		}

		return url;
	}

	/**
	 * Use this method only in author, if you want to get publish URL.
	 * Ex: Forming site map url for the enduser in author.
	 * 
	 */
	public String getFormattedUrlForPublish(String url, ResourceResolver resourceResolver) {
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		if (null != url) {
			// when url is an aem page
			if (null != pageManager.getPage(url) || url.startsWith(CommonConstants.CONST_BDB_ROOT_PATH) || url.startsWith("/{language-country}")) {
				url = externalizer.publishLink(resourceResolver, url);
			}
			// when url is a dam path
			else if (url.startsWith(CommonConstants.CONTENT_DAM)) {
				if (null != resourceResolver.getResource(url)) {
					url = externalizer.publishLink(resourceResolver, url);
				} else {
					url = StringUtils.EMPTY;
				}
			} else if (url.startsWith(CommonConstants.SINGLE_SLASH + CommonConstants.BIN)) {
				url = externalizer.publishLink(resourceResolver, url);
			}
			// when url is an external link without http/https
			else if (!url.startsWith(CommonConstants.HTTP) && !url.startsWith(CommonConstants.HTTPS)
					&& !url.isEmpty()) {
				url = CommonConstants.HTTPS + CommonConstants.COLON_SLASH_SLASH + url;
			}
			// when url is an external link but unsecured
			else if (url.startsWith(CommonConstants.HTTP) && !url.isEmpty()) {
				url = url.replace(CommonConstants.HTTP, CommonConstants.HTTPS);
			}
			// when url hasn't been authored
			else if (url.isEmpty()) {
				url = StringUtils.EMPTY;
			}
			// when url is an external url or it is empty
			else {
				url = url.trim();
			}

			return url;
		}

		return url;
	}


	/**
	 * Method to externalize the aem urls.
	 *
	 * @param url the url
	 * @return the string
	 */
	public String externalizedUrl(String url, ResourceResolver resourceResolver) {

		String runmodes = bdbApiEndpointService.getCustomRunMode();
		if ((StringUtils.equalsIgnoreCase(runmodes, CommonConstants.AUTHOR))) {
			url = externalizer.authorLink(resourceResolver, url);
		} else if (StringUtils.equalsIgnoreCase(runmodes, CommonConstants.PUBLISH)) {
			url = externalizer.publishLink(resourceResolver, resourceResolver.map(url));
		}
		return url;
	}

	/**
	 * Method to externalize the aem urls.
	 *
	 * @param url the url
	 * @return the string
	 */
	public String externalizedUrlWithoutMapping(String url, ResourceResolver resourceResolver) {

		String runmodes = bdbApiEndpointService.getCustomRunMode();
		if (StringUtils.equalsIgnoreCase(runmodes, CommonConstants.AUTHOR)) {
			url = externalizer.authorLink(resourceResolver, url);
		} else if (StringUtils.equalsIgnoreCase(runmodes, CommonConstants.PUBLISH)) {
			url = externalizer.publishLink(resourceResolver, url);
		}
		return url;
	}

	private String addHTMLifAuthor(ResourceResolver resourceResolver, String url) {
		String runmodes = bdbApiEndpointService.getCustomRunMode();
		if ((StringUtils.equalsIgnoreCase(runmodes, CommonConstants.AUTHOR)) && !url.endsWith(".html")) {
			url = url + CommonConstants.DOT_HTML;
		}
		return url;
	}

}