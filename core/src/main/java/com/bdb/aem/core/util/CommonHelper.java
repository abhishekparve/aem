package com.bdb.aem.core.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.genericlists.GenericList;
import com.bdb.aem.core.models.GlobalFileUploadModel;
import com.bdb.aem.core.models.MasterDataMessagesModel;
import com.bdb.aem.core.pojo.PrefferedPdfInfo;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class CommonHelper.
 */
public class CommonHelper {

	/**
	 * Single color antibodies ruo folder name
	 */
	private static final String SINGLE_COLOR_ANTIBODIES_RUO = "Single Color Antibodies (RUO)";

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CommonHelper.class);

	/**
	 * Instantiates a new common helper.
	 */
	private CommonHelper() {

	}

	/** The gson instance. */
	private static Gson gsonInstance;

	/**
	 * Gets the service resolver.
	 *
	 * @param resolverFactory the resolver factory
	 * @return the service resolver
	 * @throws LoginException the login exception
	 */
	public static ResourceResolver getServiceResolver(ResourceResolverFactory resolverFactory) throws LoginException {
		LOG.debug("Inside getServiceResolver of CommonHelper");
		Map<String, Object> writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				"writeService");
		return resolverFactory.getServiceResourceResolver(writeServiceAuth);
	}

	public static ResourceResolver getServiceResolverReplication(ResourceResolverFactory resolverFactory)
			throws LoginException {
		LOG.debug("Inside getServiceResolverReplication of CommonHelper");
		Map<String, Object> writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				"writeServiceReplicate");
		return resolverFactory.getServiceResourceResolver(writeServiceAuth);
	}

	/**
	 * This Utility Method is to get the existing Gson Object created by factor.
	 *
	 * @return the gson instance
	 */
	public static Gson getGsonInstance() {
		if (gsonInstance == null) {
			gsonInstance = new Gson();
		}
		return gsonInstance;
	}

	/**
	 * Gets the selector details.
	 *
	 * @param request the request
	 * @return the selector details
	 */
	public static String getSelectorDetails(SlingHttpServletRequest request) {

		RequestPathInfo requestPath = request.getRequestPathInfo();
		LOG.debug(" requestPath {}", requestPath);
		LOG.debug(" requestPath {}" + requestPath);
		String productVarient = null;
		if (requestPath.getSelectors().length != 0) {
			String[] selectors = requestPath.getSelectors();
			LOG.debug(" selectors {}", selectors);
			LOG.debug(" selectors {}" + selectors.length);
			if (selectors.length == 2) {
				productVarient = selectors[1];
			}
		}
		LOG.debug(" Product Varient {}", productVarient);
		return productVarient;
	}

	/**
	 * Gets the varient details.
	 *
	 * @param productVarient    the product varient
	 * @param country           the country
	 * @param solrSearchService the solr search service
	 * @return the varient details
	 */
	public static Resource getVarientDetails(String productVarient, String country,
			SolrSearchService solrSearchService) {
		Resource varientResource = null;
		// RegetHpNodePath
		return varientResource;
	}

	/**
	 * Gets the variant res.
	 *
	 * @param varientResource the varient resource
	 * @param query           the query
	 * @return the variant res
	 */
	public static Resource getVariantRes(Resource varientResource, Query query) {
		SearchResult result = query.getResult();
		LOG.debug("Query Result Size {} ", result.getTotalMatches());
		if (null != result.getResources()) {
			Iterator<Resource> resources = result.getResources();
			while (resources.hasNext()) {
				Resource nextResource = resources.next();
				if (nextResource.hasChildren()) {
					varientResource = nextResource.getChild(CommonConstants.HP_NODE);
				}
			}
		} else {
			LOG.debug("Product Resource Not Found ");
		}
		return varientResource;
	}

	/**
	 * Gets the creates the query map.
	 *
	 * @param productVarient the product varient
	 * @return the creates the query map
	 */
	private static Map<String, String> getCreateQueryMap(String productVarient) {
		Map<String, String> map = new HashMap<>();
		map.put(CommonConstants.PATH, SolrSearchConstants.CATALOG_CONTENT_HIERARCHY_PATH);
		map.put(CommonConstants.TYPE, JcrConstants.NT_UNSTRUCTURED);
		map.put(CommonConstants.NODE_NAME, productVarient);
		map.put(CommonConstants.LIMIT, "-1");
		return map;
	}

	/**
	 * Gets the query.
	 *
	 * @param resourceResolver the resource resolver
	 * @param map              the map
	 * @return the query
	 */
	public static Query getQuery(ResourceResolver resourceResolver, Map<String, String> map) {
		Query query = null;
		if (null != resourceResolver) {
			QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
			Session session = resourceResolver.adaptTo(Session.class);
			query = queryBuilder.createQuery(PredicateGroup.create(map), session);

		}
		return query;
	}

	/**
	 * Close resource resolver.
	 *
	 * @param resourceResolver the resource resolver
	 */
	public static void closeResourceResolver(ResourceResolver resourceResolver) {
		if (resourceResolver != null && resourceResolver.isLive()) {
			resourceResolver.close();
		}
	}

	/**
	 * Gets the property value.
	 *
	 * @param properties the properties
	 * @param key        the key
	 * @return the property value
	 */
	public static String getPropertyValue(ValueMap properties, String key) {
		String propertyValue = StringUtils.EMPTY;
		if (null != properties && properties.containsKey(key)) {
			propertyValue = properties.get(key, String.class);
		}
		return propertyValue;
	}

	/**
	 * Session logout.
	 *
	 * @param session the session
	 */
	public static void sessionLogout(Session session) {
		if (session != null && session.isLive()) {
			session.logout();
		}
	}

	/**
	 * Sets the hybris site id.
	 *
	 * @param currentPage the current page
	 * @return the string
	 */
	public static String setHybrisSiteId(Page currentPage) {
		String hybrisSiteId = readFromAbsolutePath(currentPage,CommonConstants.HYBRIS_SITE_ID);
		return StringUtils.isNotEmpty(hybrisSiteId) ? hybrisSiteId : StringUtils.EMPTY;
	}

	/**
	 * Gets the language TDS visibility flag.
	 *
	 * @param currentPage the current page
	 * @return the string
	 */
	public static String tdsDisplayOnPDPConfig(Page currentPage) {
		String tdsDisplayonPDPConfig = readTdsConfigFromAbsolutePath(currentPage,CommonConstants.PDP_DOC_DISPLAY_CONFIG);
		return StringUtils.isNotEmpty(tdsDisplayonPDPConfig) ? tdsDisplayonPDPConfig : StringUtils.EMPTY;
	}
	
	

	/**
	 * Gets the externalized image.
	 *
	 * @param resourcePath        the resource path
	 * @param resourceResolver    the resource resolver
	 * @param image               the image
	 * @param externalizerService the externalizer service
	 * @return the externalized image
	 */
	public static String getExternalizedImage(String resourcePath, ResourceResolver resourceResolver, String image,
			ExternalizerService externalizerService) {
		String damPath = resourcePath
				.replace(CommonConstants.VAR_COMMERCE + CommonConstants.SINGLE_SLASH + CommonConstants.PRODUCTS,
						CommonConstants.CONTENT_DAM)
				.replace(CommonConstants.PRODUCTS, CommonConstants.PRODUCTS + CommonConstants.SINGLE_SLASH + "global");
		String assetPath = damPath + CommonConstants.SINGLE_SLASH + image;
		return externalizerService.getFormattedUrl(assetPath, resourceResolver);
	}

	/**
	 * Gets the country.
	 *
	 * @param currentPage the current page
	 * @return the country
	 */
	public static String getCountry(Page currentPage) {
		String country = readFromAbsolutePath(currentPage,CommonConstants.COUNTRY);
		return StringUtils.isNotEmpty(country) ? country : CommonConstants.CONST_DEFAULT_COUNTRY;
	}

	/**
	 * Gets the language.
	 *
	 * @param currentPage the current page
	 * @return the language
	 */
	public static String getLanguage(Page currentPage) {
		String language =  readFromAbsolutePath(currentPage,CommonConstants.LANGUAGE);
		return StringUtils.isNotEmpty(language) ? language : CommonConstants.CONST_DEFAULT_LANGUAGE;

	}

	/**
	 * Gets the region.
	 *
	 * @param currentPage the current page
	 * @return the region
	 */
	public static String getRegion(Page currentPage) {
		String region =  readFromAbsolutePath(currentPage,CommonConstants.REGION);
		return StringUtils.isNotEmpty(region) ? region : CommonConstants.CONST_DEFAULT_REGION;
	}

	/**
	 * Reading the value from the absolute path
	 * @param currentPage
	 * @param property
	 * @return
	 */
	private static String readFromAbsolutePath(Page currentPage, String property) {
		Page page = null != currentPage ? currentPage.getAbsoluteParent(4) : null;
		String propertyVal = StringUtils.EMPTY;
		if (null != page && null != page.getContentResource()) {
			LOG.debug("Reading page value at {} ", page.getContentResource().getPath());
			ValueMap vp = page.getProperties();
			propertyVal = null != vp && vp.containsKey(property) ? vp.get(property, String.class) : StringUtils.EMPTY;
			if(StringUtils.isNotEmpty(propertyVal) && !property.equalsIgnoreCase(CommonConstants.HYBRIS_SITE_ID)) {
				return propertyVal.toLowerCase();
			}else{
				return  propertyVal;
			}
		}
		LOG.debug("Property Val {} ", propertyVal);
		return propertyVal;
	}
	
	/**
	 * Reading the value from the absolute path
	 * @param currentPage
	 * @param property
	 * @return
	 */
	private static String readTdsConfigFromAbsolutePath(Page currentPage, String property) {
		Page page = null != currentPage ? currentPage.getAbsoluteParent(4) : null;
		String propertyVal = StringUtils.EMPTY;
		if (null != page && null != page.getContentResource()) {
			LOG.debug("Reading page value at {} ", page.getContentResource().getPath());
			ValueMap vp = page.getProperties();
			propertyVal = null != vp && vp.containsKey(property) ? vp.get(property, String.class) : StringUtils.EMPTY;
			if(StringUtils.isNotEmpty(propertyVal) && !property.equalsIgnoreCase(CommonConstants.PDP_DOC_DISPLAY_CONFIG)) {
				return propertyVal.toLowerCase();
			}else{
				return  propertyVal;
			}
		}
		LOG.debug("Property Val {} ", propertyVal);
		return propertyVal;
	}



	/**
	 * Gets the generic list title.
	 *
	 * @param selectedValue       the selected value
	 * @param genericListResource the generic list resource
	 * @param resourceResolver    the resource resolver
	 * @return the generic list title
	 */
	public static String getGenericListTitle(String selectedValue, Resource genericListResource,
			ResourceResolver resourceResolver) {
		// To get the title of the selected value from generic list
		if (null != resourceResolver) {
			Resource listResource = resourceResolver.getResource(genericListResource.getPath()
					+ CommonConstants.JCR_CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.LIST);
			if (listResource != null && listResource.hasChildren()) {
				Iterator<Resource> items = listResource.listChildren();
				while (items.hasNext()) {
					Resource itemResource = items.next();
					ValueMap valueMap = itemResource.getValueMap();
					String title = CommonHelper.getPropertyValue(valueMap, JcrConstants.JCR_TITLE);
					String value = CommonHelper.getPropertyValue(valueMap, "value");
					if (value.equalsIgnoreCase(selectedValue)) {
						return title;
					}
				}
			}
		}
		return selectedValue;
	}
	
	/**
	 * Gets the country XF path.
	 *
	 * @param region       the region
	 * @param resourceResolver    the resource resolver
	 * @return the country XF path
	 */
	public static String getRegionSpecificXf(String region, ResourceResolver resourceResolver) {
		String XF_PATH = "/content/experience-fragments/bdb";
		String countryXfPath = null;
		if (null != resourceResolver) {
			Resource listResource = resourceResolver.getResource(XF_PATH);
			if (listResource != null && listResource.hasChildren()) {
				Iterator<Resource> items = listResource.listChildren();
				while (items.hasNext()) {
					Resource regionResource = items.next();
					if ((regionResource != null) && (regionResource.hasChildren()) && !(regionResource.getPath().contains("language-masters"))) {
						Iterator<Resource> item = regionResource.listChildren();
						while (item.hasNext()) {
							Resource countryResource = item.next();
							String countryPath = countryResource.getName().toString();
							if (region.equalsIgnoreCase(countryPath)) {
								Iterator<Resource> iterate = countryResource.listChildren();
								while (iterate.hasNext()) {
									Resource resourceChild = iterate.next();
									countryXfPath = resourceChild.getPath();
									return countryXfPath;
								}

								LOG.debug("countryXfPath");
							}

					 }
					}
				}
			}
		}
		return countryXfPath;
	}

	/**
	 * Gets the generic list.
	 *
	 * @param genericListResource the generic list resource
	 * @param resourceResolver    the resource resolver
	 * @return the generic list
	 */
	public static String getGenericList(Resource genericListResource, ResourceResolver resourceResolver) {
		if (null != genericListResource) {
			Resource listResource = resourceResolver.getResource(genericListResource.getPath()
					+ CommonConstants.JCR_CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.LIST);
			if (listResource != null && listResource.hasChildren()) {
				JsonArray listArray = new JsonArray();
				Iterator<Resource> items = listResource.listChildren();

				while (items.hasNext()) {
					JsonObject listObject = new JsonObject();
					Resource itemResource = items.next();
					ValueMap valueMap = itemResource.getValueMap();

					if (StringUtils.isNotEmpty(CommonHelper.getPropertyValue(valueMap, CommonConstants.VALUE))) {
						listObject.addProperty(CommonConstants.LABEL,
								CommonHelper.getPropertyValue(valueMap, JcrConstants.JCR_TITLE));
						listObject.addProperty(CommonConstants.ID,
								CommonHelper.getPropertyValue(valueMap, CommonConstants.VALUE));
						listArray.add(listObject);
					}
				}
				return listArray.toString();
			} else {
				LOG.error("Generic List is empty.");
				return null;
			}
		} else {
			LOG.error("Generic List does not exist.");
			return null;
		}
	}

	/**
	 * Gets the my cart page.
	 *
	 * @param currentPage the current page
	 * @return the my cart page
	 */
	public static String getMyCartPage(Page currentPage) {
		String cartPagePath = StringUtils.EMPTY;
		if (null != currentPage) {
			InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			cartPagePath = inVM.getInherited(CommonConstants.MY_CART_PAGE, String.class);
		}
		return cartPagePath;
	}

	/**
	 * Gets the checkout page.
	 *
	 * @param currentPage the current page
	 * @return the checkout page
	 */
	public static String getCheckoutPage(Page currentPage) {
		String checkoutPage = StringUtils.EMPTY;
		if (null != currentPage) {
			InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			checkoutPage = inVM.getInherited(CommonConstants.CHECKOUT_PAGE_PATH, String.class);
		}
		return checkoutPage;
	}

	/**
	 * Gets the headers info.
	 *
	 * @param request the request
	 * @return the headers info
	 */
	public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * Gets the specific header.
	 *
	 * @param request   the request
	 * @param headerKey the header key
	 * @return the specific header
	 */
	public static String getSpecificHeader(HttpServletRequest request, String headerKey) {
		return request.getHeader(headerKey);
	}

	/**
	 * Gets the site id header.
	 *
	 * @param request the request
	 * @return the site id header
	 */
	public static String getSiteIdHeader(HttpServletRequest request) {
		String headerValue = request.getHeader("siteId");
		if (StringUtils.isNotBlank(headerValue)) {
			return headerValue;
		} else {
			return request.getHeader("siteid");
		}
	}

	/**
	 * Gets the error page path.
	 *
	 * @param currentPage the current page
	 * @return the error page path
	 */
	public static String getErrorPagePath(Page currentPage) {
		String errorPagePath = StringUtils.EMPTY;
		if (null != currentPage) {
			InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			errorPagePath = inVM.getInherited(CommonConstants.ERROR_PAGE_PATH, String.class);
		}
		return errorPagePath;
	}

	/**
	 * Gets the master data page path.
	 *
	 * @param currentPage the current page
	 * @return the master data page path
	 */
	public static String getMasterDataPagePath(Page currentPage) {
		String masterDataPagePath = StringUtils.EMPTY;
		if (null != currentPage) {
			InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			masterDataPagePath = inVM.getInherited(CommonConstants.MASTER_DATA_PAGE_PATH, String.class);
		}
		return masterDataPagePath;
	}

	/**
	 * Gets the component node.
	 *
	 * @param pagePath         the page path
	 * @param resourceResolver the resource resolver
	 * @param resourceType     the resource type
	 * @return the component node
	 */
	public static String getComponentNode(String pagePath, ResourceResolver resourceResolver, String resourceType) {
		String nodePath = org.apache.commons.lang3.StringUtils.EMPTY;
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(pagePath) && null != resourceResolver) {
			Resource pageContentResource = resourceResolver.getResource(pagePath.concat(CommonConstants.JCR_ROOT));
			if (pageContentResource != null) {
				Iterator<Resource> children = pageContentResource.listChildren();
				while (children.hasNext()) {
					Resource child = children.next();
					if (child.isResourceType(resourceType)) {
						nodePath = child.getPath();
						break;
					}
				}
			}
		}
		return nodePath;
	}

	/**
	 * Gets the globalFileUpload model.
	 *
	 * @param pagePath         - the path to the data pages
	 * @param resourceResolver - resource resolver object
	 * @param resourceType     - the resource type
	 * @return returns GlobalFileUpload Model
	 */
	public static GlobalFileUploadModel getGlobalFileUploadModel(String pagePath, ResourceResolver resourceResolver,
			String resourceType) {

		if (null != pagePath && null != resourceResolver) {
			String componentNode = getComponentNode(pagePath, resourceResolver, resourceType);

			if (org.apache.commons.lang3.StringUtils.isNotEmpty(componentNode)) {

				Resource modelResource = resourceResolver.getResource(componentNode);
				if (modelResource != null) {
					GlobalFileUploadModel globalFileUploadModel = modelResource.adaptTo(GlobalFileUploadModel.class);
					if (globalFileUploadModel != null) {
						return globalFileUploadModel;
					}
				}
			}
		}
		return null;
	}

	/**
	 * sets the cookie.
	 *
	 * @param response    the response
	 * @param cookieName  the cookie name
	 * @param cookieValue the cookie value
	 * @param timeout     the timeout
	 * @param isSecure    the is secure
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static void setCookie(SlingHttpServletResponse response, String cookieName, String cookieValue, int timeout,
			boolean isSecure) throws UnsupportedEncodingException {

		Cookie cookie = new Cookie(cookieName, URLEncoder.encode(cookieValue, CommonConstants.UTF_ENCODING));
		cookie.setPath(CommonConstants.SINGLE_SLASH);
		cookie.setMaxAge(timeout);
		cookie.setSecure(isSecure);
		response.addCookie(cookie);

	}

	/**
	 * Removes the cookie.
	 *
	 * @param response   the response
	 * @param cookieName the cookie name
	 * @param isSecure   the is secure
	 */
	public static void removeCookie(SlingHttpServletResponse response, String cookieName, boolean isSecure) {

		Cookie cookie = new Cookie(cookieName, StringUtils.EMPTY);
		cookie.setPath(CommonConstants.SINGLE_SLASH);
		cookie.setMaxAge(0);
		cookie.setSecure(isSecure);
		response.addCookie(cookie);

	}

	/**
	 * Removes the success cookies.
	 *
	 * @param response the response
	 * @param isSecure the is secure
	 */
	public static void removeSuccessCookies(SlingHttpServletResponse response, Boolean isSecure) {
		LOG.debug("Entry removeSuccessCookies");
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_FIRSTNAME_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_LAST_NAME_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_UID_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_UNREAD_MESSAGES_COUNT_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_ACC_TYPE_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_ACC_ROLE_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_GRANT_ENABLED_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_IS_SMART_CART_USER, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_PENDING_ORDER_COUNT_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.AUTH_TOKEN_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.SSO_TOKEN_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.AUTH_TOKEN_REFRESH_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.SSO_TOKEN_REFRESH_COOKIE, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_IS_REWARDS_ENABLED, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_IS_DISTRIBUTOR, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.AREA_OF_INTEREST, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.USER_DETAILS_IS_CREDIT_CARD_ENABLED, isSecure);
		CommonHelper.removeCookie(response, CommonConstants.TOKEN_EXPIRY_TIME_COOKIE, isSecure);
		LOG.debug("Exit removeSuccessCookies");
	}

	/**
	 * Checks if is security code same.
	 *
	 * @param queryParam             the query param
	 * @param securityCodeFromCookie the security code from cookie
	 * @return true, if is security code same
	 */
	public static boolean isSecurityCodeSame(Map<String, String> queryParam, String securityCodeFromCookie) {
		boolean isSame = false;
		String result = getValueFromMap(queryParam, CommonConstants.STATE);
		LOG.debug("result from queryParam in isSecurityCodeSame : " + result);
		LOG.debug("securityCodeFromCookie in isSecurityCodeSame : " + securityCodeFromCookie);
		if (StringUtils.isNotBlank(result)) {
			result = result.trim().substring(result.indexOf(CommonConstants.EQUAL) + 1);
			LOG.debug("result in isSecurityCodeSame : " + result);
			isSame = StringUtils.equals(result, securityCodeFromCookie);
		}
		return true;
	}

	/**
	 * Gets the specific cookie.
	 *
	 * @param request    the request
	 * @param cookieName the cookie name
	 * @return the specific cookie
	 */
	public static String getSpecificCookie(SlingHttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		String defaultValue = StringUtils.EMPTY;
		if (null != cookies) {
			LOG.debug("Cookies array not null");
			for (Cookie cookie : cookies) {
				try {
					if (cookieName.equalsIgnoreCase(cookie.getName())) {
						LOG.debug("Matched cookie Name : "+ cookie.getName());
						return URLDecoder.decode((cookie.getValue()), CommonConstants.UTF_ENCODING);
					}
				} catch (UnsupportedEncodingException e) {
					LOG.warn("getSpecificCookie UnsupportedEncodingException: {0}", e);
				}
			}
		}
		LOG.debug("Specific Cookie : {}", defaultValue);
		return (defaultValue);
	}

	/**
	 * Gets the query params.
	 *
	 * @param request the request
	 * @return the query params
	 */
	public static Map<String, String> getQueryParamsFromRequest(SlingHttpServletRequest request) {
		Map<String, String> queryParams = new HashMap<>();
		Map<String, String[]> allMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : allMap.entrySet()) {
			queryParams.put(entry.getKey(), entry.getValue()[0]);
		}
		return queryParams;
	}

	/**
	 * Gets the value from map.
	 *
	 * @param queryParam the query param
	 * @param key        the key
	 * @return the value from map
	 */
	public static String getValueFromMap(Map<String, String> queryParam, String key) {
		return queryParam.entrySet().stream().filter(map -> key.equals(map.getKey())).map(Map.Entry::getValue)
				.collect(Collectors.joining());
	}

	/**
	 * Gets the required params.
	 *
	 * @param request the request
	 * @return the required params
	 */
	public static Map<String, String> getRequiredParams(SlingHttpServletRequest request) {
		Map<String, String> requiredParams = new HashMap<>();
		Map<String, String> queryParams = getQueryParamsFromRequest(request);
		String queryParamState = getValueFromMap(queryParams, CommonConstants.STATE);
		LOG.debug("Query Params in getRequiredParams:" + queryParamState);
		if (StringUtils.isNotEmpty(queryParamState)) {
			String[] stateCodeAndUrl = queryParamState.split(CommonConstants.STATE_CODE_SPLITTER);
			if (null != stateCodeAndUrl && stateCodeAndUrl.length == 3) {
				requiredParams.put(CommonConstants.CODE, getValueFromMap(queryParams, CommonConstants.CODE));
				requiredParams.put(CommonConstants.STATE, stateCodeAndUrl[0]);
				requiredParams.put(CommonConstants.URL,
						stateCodeAndUrl[2].substring(4).replace(CommonConstants.HASH_PLACEHOLDER, "#/"));
			}
		}
		LOG.debug("Query Params : {}", queryParams);
		return requiredParams;
	}

	/**
	 * Gets the required params.
	 *
	 * @param request the request
	 * @return the required params
	 */
	public static String getSiteId(SlingHttpServletRequest request) {
		String value = StringUtils.EMPTY;
		Map<String, String> queryParams = getQueryParamsFromRequest(request);
		String queryParamState = getValueFromMap(queryParams, CommonConstants.STATE);
		if (StringUtils.isNotEmpty(queryParamState)) {
			String[] stateCodeAndUrl = queryParamState.split(CommonConstants.STATE_CODE_SPLITTER);
			if (null != stateCodeAndUrl && stateCodeAndUrl.length == 3) {
				value = stateCodeAndUrl[1];
			}
		}
		return value;
	}

	/**
	 * Gets the value from json.
	 *
	 * @param jsonObject the json object
	 * @param key        the key
	 * @return the value from json
	 */
	public static String getValueFromJson(JsonObject jsonObject, String key) {
		String value = StringUtils.EMPTY;
		JsonElement jsonElement = jsonObject.get(key);
		if (null != jsonElement && StringUtils.isNotBlank(jsonElement.getAsString())) {
			value = jsonElement.getAsString();
		}
		return value;
	}

	/**
	 * Gets the json object array from json.
	 *
	 * @param jsonString the json string
	 * @param key        the key
	 * @return the json object array from json
	 */
	public static JsonArray getJsonObjectArrayFromJson(String jsonString, String key) {
		Gson gson = getGsonInstance();
		JsonObject jsonObjectTemp = gson.fromJson(jsonString, JsonObject.class);
		JsonArray jsonArray = jsonObjectTemp.getAsJsonArray(key);
		if (null != jsonArray && jsonArray.size() > 0) {
			return jsonArray;
		}
		return new JsonArray();
	}

	/**
	 * Gets the json object from json array.
	 *
	 * @param jsonArray the json array
	 * @return the json object from json array
	 */
	public static JsonObject getJsonObjectFromJsonArray(JsonArray jsonArray) {
		if (null != jsonArray && jsonArray.size() > 0) {
			return jsonArray.get(0).getAsJsonObject();
		}
		return new JsonObject();
	}

	/**
	 * Gets the sign in url.
	 *
	 * @param bdbApiEndpointService   the bdb api endpoint service
	 * @param externalizerService     the externalizer service
	 * @param resourceResolver        the resource resolver
	 * @param currentPage             the current page
	 * @param environmentTypeProvider the environmentTypeProvider
	 * @return the sign in url
	 */
	public static String getSignInUrl(BDBApiEndpointService bdbApiEndpointService,
			ExternalizerService externalizerService, ResourceResolver resourceResolver, Page currentPage) {
		String signInUrl = StringUtils.EMPTY;
		if (bdbApiEndpointService != null) {
			String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
			String pingIdDomain = bdbApiEndpointService.ciamPingIdDomain();
			String pingIdEndpoint = bdbApiEndpointService.ciamLoginPingIdEndpoint();
			String responseType = bdbApiEndpointService.ssoLoginResponseType();
			String clientId = bdbApiEndpointService.ciamLoginClientId();
			String ciamB2CId = bdbApiEndpointService.ciamB2CId();
			String redirectUri = externalizerService.getFormattedUrl(bdbApiEndpointService.ssoCustomerLoginService(),
					resourceResolver);
			String scope = bdbApiEndpointService.ssoLoginScope();
			StringBuilder state = new StringBuilder();
			state.append(CommonConstants.SECURITY_TOKEN).append(CommonConstants.EQUAL).append("@@token@@");
			String url = "@@url@@";
			String locale = CommonConstants.DEFAULT_LOCALE;
			LOG.debug("default locale to SSO : " + locale);
			if (null != currentPage) {
				locale = getLanguage(currentPage) + "-" + getCountry(currentPage).toLowerCase();

				String runmodes = bdbApiEndpointService.environmentType();
				LOG.debug("runmode************************ : {}", runmodes);
				/**
				if (runmodes.equalsIgnoreCase(CommonConstants.RUNMODE_STAGE)) {
					locale = CommonConstants.STAGE_LITERAL + locale;
					LOG.debug("locale set to SSO in stage : " + locale);
				}
				*/
				LOG.debug("locale set to SSO : " + locale);
			}
			StringBuilder path = new StringBuilder();
			path.append(pingIdDomain).append(pingIdEndpoint).append(CommonConstants.INTERROGATION)
				.append(CommonConstants.P).append(CommonConstants.EQUAL).append(ciamB2CId)
			  	.append(CommonConstants.AND).append(CommonConstants.CLIENT_ID).append(CommonConstants.EQUAL)
			  	.append(clientId).append(CommonConstants.AND).append(CommonConstants.REDIRECT_URI)
			  	.append(CommonConstants.EQUAL).append(redirectUri).append(CommonConstants.AND)
			  	.append(CommonConstants.SCOPE).append(CommonConstants.EQUAL).append(scope)
			  	.append(CommonConstants.AND).append(CommonConstants.RESPONSE_TYPE).append(CommonConstants.EQUAL)
			  	.append(responseType).append(CommonConstants.AND).append(CommonConstants.UI_LOCALE).append(CommonConstants.EQUAL)
			  	.append(locale).append(CommonConstants.AND).append(CommonConstants.STATE).append(CommonConstants.EQUAL).append(state)
			  	.append(CommonConstants.STATE_CODE_SPLITTER).append(hybrisSiteId).append(CommonConstants.STATE_CODE_SPLITTER)
				.append(CommonConstants.URL).append(CommonConstants.EQUAL).append(url);
			  
			signInUrl = path.toString();
		}
		return signInUrl;
	}

	/**
	 * This function return the ResourceResolver object.
	 *
	 * @param session                 the session
	 * @param resourceResolverFactory the resource resolver factory
	 * @return the resource resolver
	 * @throws LoginException the login exception
	 */
	public static ResourceResolver getResourceResolver(Session session, ResourceResolverFactory resourceResolverFactory)
			throws LoginException {
		LOG.debug("Entering CommonHelper getResourceResolver");
		return resourceResolverFactory.getResourceResolver(
				Collections.<String, Object>singletonMap(JcrResourceConstants.AUTHENTICATION_INFO_SESSION, session));
	}

	/**
	 * Gets the current region url.
	 *
	 * @param url                  the url
	 * @param currentPage          the current page
	 * @param resourceResolver     the resource resolver
	 * @param bdbApiEndpointService the bdbApiEndpointService
	 * @return the current region url
	 */

	public static String getCurrentRegionUrl(String url, Page currentPage, ResourceResolver resourceResolver,
			BDBApiEndpointService bdbApiEndpointService) {

		String runmodes = bdbApiEndpointService.getCustomRunMode();
		if (!url.trim().isEmpty()) {
			if (StringUtils.equalsIgnoreCase(runmodes, CommonConstants.AUTHOR)) {

				String[] urlFragments = url.split(CommonConstants.SINGLE_SLASH);
				if (urlFragments.length > 8) {
					url = setRegionToURL(currentPage, urlFragments);
				}
			} else if (StringUtils.equalsIgnoreCase(runmodes, CommonConstants.PUBLISH)) {

				String[] urlFragments = url.split(CommonConstants.SINGLE_SLASH);
				if (urlFragments[3].contains("content")) {
					url = setRegionToURL(currentPage, urlFragments);
				} else {
					urlFragments[3] = CommonHelper.getRegion(currentPage).concat(CommonConstants.SINGLE_SLASH)
							.concat(CommonHelper.getCountry(currentPage)).concat(CommonConstants.SINGLE_SLASH)
							.concat(CommonHelper.getLanguage(currentPage) + CommonConstants.HYPHEN
									+ CommonHelper.getCountry(currentPage));
					url = Arrays.stream(urlFragments).collect(Collectors.joining(CommonConstants.SINGLE_SLASH));
				}

			}
		}

		return url;
	}

	/**
	 * Gets the short URL
	 *
	 * @param url                  the url
	 * @param currentPage          the current page
	 * @param resourceResolver     the resource resolver
	 * @param customRunMode  	   the Custom RunMode
	 * @return the current region url
	 */

	public static String getShortUrl(String url, Page currentPage, ResourceResolver resourceResolver,
			String customRunMode) {

		if (StringUtils.isNotBlank(url)) {
			if (StringUtils.equalsIgnoreCase(customRunMode, CommonConstants.AUTHOR)) {

				String[] urlFragments = url.split(CommonConstants.SINGLE_SLASH);
				if (urlFragments.length > 8) {
					url = setRegionToURL(currentPage, urlFragments);
				}
			} else if (StringUtils.equalsIgnoreCase(customRunMode, CommonConstants.PUBLISH)) {

				String[] urlFragments = url.split(CommonConstants.SINGLE_SLASH);
				if(urlFragments.length>3){
					if (urlFragments[3].contains("content")) {
					url = setRegionToURL(currentPage, urlFragments);
					} else {
						urlFragments[3] = CommonHelper.getLanguage(currentPage) + CommonConstants.HYPHEN
							+ CommonHelper.getCountry(currentPage);
						url = Arrays.stream(urlFragments).collect(Collectors.joining(CommonConstants.SINGLE_SLASH));
					}
				}

			}
		}

		return url;
	}

	/**
	 * Sets the region to URL.
	 *
	 * @param currentPage  the current page
	 * @param urlFragments the url fragments
	 * @return the string
	 */
	public static String setRegionToURL(Page currentPage, String[] urlFragments) {
		String url;
		urlFragments[5] = CommonHelper.getRegion(currentPage);
		urlFragments[6] = CommonHelper.getCountry(currentPage);
		urlFragments[7] = CommonHelper.getLanguage(currentPage) + CommonConstants.HYPHEN
				+ CommonHelper.getCountry(currentPage);
		url = Arrays.stream(urlFragments).collect(Collectors.joining(CommonConstants.SINGLE_SLASH));
		return url;
	}
	
	/**
	 * Gets the short URL
	 *
	 * @param url                  the url
	 * @param countryXfPath          the countryXfPath
	 * @param resourceResolver     the resource resolver
	 * @param customRunMode  	   the Custom RunMode
	 * @return the current region url
	 */

	public static String getShortUrlXf(String url, String countryXfPath, ResourceResolver resourceResolver,
			String customRunMode) {
		
		String[] countryXfPathFragments = countryXfPath.split(CommonConstants.SINGLE_SLASH);

		if (StringUtils.isNotBlank(url)) {
			if (StringUtils.equalsIgnoreCase(customRunMode, CommonConstants.AUTHOR)) {

				String[] urlFragments = url.split(CommonConstants.SINGLE_SLASH);
				
				if (urlFragments.length > 8) {
					url = setRegionToURLXf(countryXfPath, urlFragments);
				}
			} else if (StringUtils.equalsIgnoreCase(customRunMode, CommonConstants.PUBLISH)) {

				String[] urlFragments = url.split(CommonConstants.SINGLE_SLASH);
				if(urlFragments.length>3){
					if (urlFragments[3].contains("content")) {
					url = setRegionToURLXf(countryXfPath, urlFragments);
					} else {
						urlFragments[3] = countryXfPathFragments[6];
						url = Arrays.stream(urlFragments).collect(Collectors.joining(CommonConstants.SINGLE_SLASH));
					}
				}

			}
		}

		return url;
	}
	
	/**
	 * Sets the region to URL of Xf.
	 *
	 * @param countryXfPath  the countryXfPath
	 * @param urlFragments the url fragments
	 * @return the string
	 */
	public static String setRegionToURLXf(String countryXfPath, String[] urlFragments) {
		String url;
		String[] countryXfPathFragments = countryXfPath.split(CommonConstants.SINGLE_SLASH);
		urlFragments[5] = countryXfPathFragments[4];
		urlFragments[6] = countryXfPathFragments[5];
		urlFragments[7] = countryXfPathFragments[6];
		url = Arrays.stream(urlFragments).collect(Collectors.joining(CommonConstants.SINGLE_SLASH));
		return url;
	}


	/**
	 * Split string by key.
	 *
	 * @param input the input
	 * @param key   the key
	 * @return the string[]
	 */
	public static String[] splitStringByKey(String input, String key) {
		LOG.debug("Entering CommonHelper splitStringByKey");
		String[] splittedArray = {};
		if (StringUtils.isNotBlank(input) && StringUtils.isNotBlank(key)) {
			splittedArray = input.split(key);
		}
		return splittedArray;
	}

	/**
	 * Close session.
	 *
	 * @param session the session
	 */
	public static void closeSession(Session session) {
		if (null != session && session.isLive()) {
			session.logout();
		}
	}

	/**
	 * Save session.
	 *
	 * @param session the session
	 * @throws RepositoryException the repository exception
	 */
	public static void saveSession(Session session) throws RepositoryException {
		if (null != session && session.isLive()) {
			session.save();
		}
	}

	/**
	 * Getsthehomepage.
	 *
	 * @param currentPage the current page
	 * @return the home page url
	 * @paramcurrentPagethecurrentpage
	 * @returnthehomepage
	 */
	public static String getHomePageUrl(Page currentPage) {
		String homePagePath = StringUtils.EMPTY;
		if (null != currentPage) {
			InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			homePagePath = inVM.getInherited(CommonConstants.HOME_PAGE_PATH, String.class);
		}
		return homePagePath;
	}

	/**
	 * Gets the label from PageProperties Labels.
	 *
	 * @param labelKey    the label key
	 * @param currentPage the current page
	 * @return the label
	 */
	public static String getLabel(String labelKey, Page currentPage) {
		String label = StringUtils.EMPTY;
		if (null != currentPage) {
			InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			label = inVM.getInherited(labelKey, String.class);
		}
		return label;
	}

	/**
	 * Creates the end point url.
	 *
	 * @param bdbApiEndpointService the bdb api endpoint service
	 * @param serviceName           the service name
	 * @param request               the request
	 * @return the string
	 */
	public static String createEndPointUrl(BDBApiEndpointService bdbApiEndpointService, String serviceName,
			SlingHttpServletRequest request) {
		LOG.debug("Entry createEndPointUrl of CommonHelper");
		String endpoint = StringUtils.EMPTY;
		String siteId = CommonHelper.getSiteIdHeader(request);
		LOG.debug("Site id : {}", siteId);
		StringBuilder endPointUrl = new StringBuilder();
		if (null != bdbApiEndpointService) {
			endPointUrl.append(bdbApiEndpointService.getBDBHybrisDomain().trim());
			switch (serviceName) {
			case CommonConstants.SIGN_UP_CALL:
				endPointUrl.append(bdbApiEndpointService.getHybrisSignUpEndpoint().trim());
				break;
			case CommonConstants.AREA_OF_FOCUS:
				endPointUrl.append(bdbApiEndpointService.getHybrisSignUpPreferenceEndpoint().trim());
				break;
			case CommonConstants.RESET_PASS:
				endPointUrl.append(bdbApiEndpointService.getHybrisResetPasswordEndpoint().trim());
				break;
			case CommonConstants.PURCHASE_ACCOUNT:
				endPointUrl.append(bdbApiEndpointService.getPurchasingAccountRegistrationEndpoint().trim());
				break;
			case CommonConstants.UPLOAD_DOCUMENT:
				endPointUrl.append(bdbApiEndpointService.getUploadTaxCertificateEndpoint().trim());
				break;
			default:
				return StringUtils.EMPTY;
			}
			endpoint = StringUtils.replace(endPointUrl.toString(), CommonConstants.HYBRIS_USER_ID_LITERAL,
					bdbApiEndpointService.getAnonymousUserIdPlaceholder());
			endpoint = StringUtils.replace(endpoint, CommonConstants.HYBRIS_SITE_LITERAL, siteId);
		} else {
			LOG.error("Could not get BdbEndpointService reference in CommonHelper");
		}
		LOG.debug("Exit createEndPointUrl of CommonHelper");
		return endpoint;
	}

	/**
	 * Creates the multi part entity.
	 *
	 * @param parameters the parameters
	 * @return the http entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static HttpEntity createMultiPartEntity(Map<String, RequestParameter> parameters) throws IOException {
		LOG.debug("Entry createMultiPartEntity of CommonHelper");
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		String key;
		RequestParameter value;

		for (Map.Entry<String, RequestParameter> entry : parameters.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (value.isFormField()) {
				builder.addTextBody(key, Streams.asString(value.getInputStream()));
			} else {
				ByteArrayBody byteArrayBody = new ByteArrayBody(IOUtils.toByteArray(value.getInputStream()),
						value.getFileName());
				builder.addPart("file", byteArrayBody);
			}
		}
		LOG.debug("Exit createMultiPartEntity of CommonHelper");
		return builder.build();
	}

	/**
	 * Gets the master data message model.
	 *
	 * @param pagePath         the page path
	 * @param resourceResolver the resource resolver
	 * @param resourceType     the resource type
	 * @return the master data message model
	 */
	public static MasterDataMessagesModel getMasterDataMessageModel(String pagePath, ResourceResolver resourceResolver,
			String resourceType) {
		if (null != pagePath && null != resourceResolver) {
			String componentNode = getComponentNode(pagePath, resourceResolver, resourceType);
			if (StringUtils.isNotEmpty(componentNode)) {
				Resource modelResource = resourceResolver.getResource(componentNode);
				if (modelResource != null) {
					return modelResource.adaptTo(MasterDataMessagesModel.class);
				}
			}
		}
		return null;
	}

	/**
	 * Check for multiple super categories.
	 *
	 * @param superCategoryValue the super category value
	 * @return the string
	 */
	public static String checkForMultipleSuperCategories(String superCategoryValue) {
		if (superCategoryValue.contains("|")) {
			String[] superCategories = superCategoryValue.split(Pattern.quote("|"));
			return superCategories[0];
		} else {
			return superCategoryValue;
		}
	}

	/**
	 * A recursive function that removes the extra commas from super categories.
	 *
	 * @param superCategory the super category
	 * @return the string
	 */
	public static String removeExtraCommas(String superCategory) {
		if (superCategory.endsWith(CommonConstants.COMMA)) {
			superCategory = superCategory.substring(0, superCategory.length() - 1).trim();
			superCategory = removeExtraCommas(superCategory);
		}
		return superCategory;
	}


	/**
	 * Gets the thumbnail image path.
	 *
	 * @param variantHpResource   the variant hp resource
	 * @param externalizerService the externalizer service
	 * @param resourceResolver    the resource resolver
	 * @return the thumbnail image path
	 */
	public static String getThumbnailImagePath(Resource variantHpResource, ExternalizerService externalizerService,
			ResourceResolver resourceResolver) {
		if (null != variantHpResource) {
			String baseProductGlobalDamPath = variantHpResource.getParent().getParent().getPath()
					.replace("/content/commerce" + CommonConstants.SINGLE_SLASH + CommonConstants.PRODUCTS,
							CommonConstants.CONTENT_DAM)
					.replace("/bdb/products", CommonConstants.SINGLE_SLASH + "bdb" + CommonConstants.SINGLE_SLASH
							+ CommonConstants.PRODUCTS + CommonConstants.SINGLE_SLASH + "global");
			if (null != resourceResolver.getResource(baseProductGlobalDamPath)) {
				Map<String, String> map = new HashMap<>();
				map.put(SolrSearchConstants.QUERY_BUILDER_PATH, baseProductGlobalDamPath);
				map.put(SolrSearchConstants.QUERY_BUILDER_TYPE, SolrSearchConstants.DAM_ASSETS);
				map.put(SolrSearchConstants.QUERY_BUILDER_TAGSEARCH, SolrSearchConstants.THUMBNAIL_TAG_NAME);
				Query query = CommonHelper.getQuery(resourceResolver, map);
				Resource thumbnailImageResource = null;
				if (null != query) {
					SearchResult result = query.getResult();
					Iterator<Resource> resources = result.getResources();
					while (resources.hasNext()) {
						thumbnailImageResource = resources.next();
					}
					if (null != thumbnailImageResource) {
						return externalizerService.getFormattedUrl(
								thumbnailImageResource.getParent().getParent().getPath(), resourceResolver);
					}
				}
			}
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Gets the pdp product url.
	 *
	 * @param variantHpResource the variant hp resource
	 * @return the pdp product url
	 */
	public static String getPdpProductUrl(Resource variantHpResource) {
		String url = CommonConstants.PDP_URL;
		if (null != variantHpResource) {
			Resource baseProductHpResource = variantHpResource.getParent().getParent().getChild(CommonConstants.HP);
			if (null != baseProductHpResource) {
				ValueMap baseProductValueMap = baseProductHpResource.adaptTo(ValueMap.class);
				String superCategory = CommonHelper.checkForMultipleSuperCategories(SolrUtils
						.checkNull(baseProductValueMap.get(CommonConstants.PRIMARY_SUPER_CATEGORY, String.class))
						.trim());
				superCategory = normalizeCategories(superCategory);
				superCategory = replaceCommaExceptInParentheses(superCategory);
				String labelDescription = baseProductValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)
						.trim().toLowerCase();
				labelDescription = nameValidator(labelDescription);
				String catalogNumber = variantHpResource.getParent().getName();
				url = url.replace(CommonConstants.URL_SUPER_CATEGORY, superCategory.trim());
				url = url.replace("(", CommonConstants.HYPHEN);
				url = url.replace(CommonConstants.URL_LABEL_DESC, labelDescription.trim().toLowerCase())
						.replaceAll("[&()?:!.;{}]+", "");
				url = removeExtraSpaces(url);
				url = url.replace(CommonConstants.SPACE, CommonConstants.HYPHEN)
						.replace(CommonConstants.COMMA, CommonConstants.HYPHEN).replace(CommonConstants.URL_PDP, "pdp.")
						.replace(CommonConstants.MATERIAL_NUMBER, "." + catalogNumber);
				url = url.toLowerCase();
				url = url.replaceAll("[-]+", "-");
				url = url.replaceAll("-/", "/").replaceAll("/-", "/");
			}
		}
		return url;
	}

	/**
	 * Performs category normalization if required.
	 *
	 * @param superCategory comma separated hierarchy of categories
	 */
	public static String normalizeCategories(String superCategory) {
		return StringUtils.containsIgnoreCase(superCategory, SINGLE_COLOR_ANTIBODIES_RUO) ? limitPrimaryCategoryToFive(superCategory) : superCategory;
	}

	/**
	 *
	 * @param superCategory
	 * @return string
	 */
	private static String limitPrimaryCategoryToFive(String superCategory) {
		if(StringUtils.isNotBlank(superCategory)) {
			String[] superCategoryArray = superCategory.split(",");
			StringBuilder superCategoryBuilder = new StringBuilder();
			int count = 0;
			for(String category : superCategoryArray) {
				superCategoryBuilder.append(category);
				++count;
				if (count < 5) {
					superCategoryBuilder.append(",");
				}
				if (count >= 5) {
					break;
				}
			}
			return superCategoryBuilder.toString();
		}

	return superCategory;
	}

	/**
	 * For Brand name BD Cytoperm™, the name validator will convert it to bd-cytoperm.
	 * All the special charcters and spaces will be replaced with hyphen(-). After replacing if there are "-"
	 * at the beginning and/or at the end of the string, that will also be removed from the string
	 * 
	 * Node name validator.
	 * 
	 * @param name the name
	 * @return the string
	 */
	public static String nameValidator(String name) {
		if (StringUtils.isNotEmpty(name)) {
			name = name.replaceAll("[^A-Za-z0-9]+", "-").toLowerCase();
			name = StringUtils.stripEnd(name, "-");
			name = StringUtils.stripStart(name, "-");
			return name;
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Removes the trailing starting hyphens.
	 *
	 * @param name the name
	 * @return the string
	 */
	public static String removeTrailingStartingHyphens(String name) {
		if (name.startsWith("-")) {
			name = name.substring(1);
		}
		if (name.endsWith("-")) {
			name = name.substring(0, name.length() - 1);
		}
		return name;
	}

	/**
	 * Replace comma except in parentheses.
	 *
	 * @param string the string
	 * @return the string
	 */
	public static String replaceCommaExceptInParentheses(String string) {

		String[] strArray = string.split(",(?![^()]*+\\))");
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for (String str : strArray) {
			sb.append(nameValidator(str).trim());
			if (i < strArray.length) {
				sb.append(CommonConstants.SINGLE_SLASH);
			}
			i++;
		}
		return sb.toString();
	}

	/**
	 * Removes the extra spaces after and before comma.
	 *
	 * @param superCategory the super category
	 * @return the string
	 */
	private static String removeExtraSpacesAfterAndBeforeComma(String superCategory) {
		superCategory = superCategory.replace(", ", ",").replace(" ,", ",");
		return superCategory;
	}

	/**
	 * Removes the extra spaces.
	 *
	 * @param superCategory the super category
	 * @return the string
	 */
	private static String removeExtraSpaces(String superCategory) {
		StringTokenizer st = new StringTokenizer(superCategory, " ");
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreElements()) {
			sb.append(st.nextElement()).append(" ");
		}
		String nameWithProperSpacing = sb.toString();
		nameWithProperSpacing = nameWithProperSpacing.trim();
		return nameWithProperSpacing;
	}

	/**
	 * Gets the search result page path.
	 *
	 * @param currentPage the current page
	 * @return the search result page path
	 */
	public static String getSearchResultPagePath(Page currentPage) {
		String searchResultPath = StringUtils.EMPTY;
		if (null != currentPage) {
			InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			searchResultPath = inVM.getInherited(CommonConstants.SEARCH_PAGE_PATH, String.class);
		}
		return searchResultPath;
	}

	/**
	 * Gets the asset name from path.
	 *
	 * @param input the input
	 * @return the asset name from path
	 */
	public static String getAssetNameFromPath(String input) {
		String assetName = StringUtils.EMPTY;
		if (input.contains(CommonConstants.SINGLE_SLASH) && input.contains(".")) {
			input = input.substring(input.lastIndexOf(CommonConstants.SINGLE_SLASH) + 1);
			assetName = input.substring(0, input.lastIndexOf("."));
		}
		return assetName;
	}

	/**
	 * Gets the query map for product node.
	 *
	 * @param pathValue     the path value
	 * @param typeValue     the type value
	 * @param property      the property
	 * @param propertyValue the property value
	 * @return the query map for product node
	 */
	public static Map<String, String> getQueryMapForProductNode(String pathValue, String typeValue, String property,
			String propertyValue) {
		Map<String, String> map = new HashMap<>();
		map.put(CommonConstants.PATH, pathValue);
		map.put(CommonConstants.TYPE, typeValue);
		map.put(CommonConstants.PROPERTY_STRING, property);
		map.put(CommonConstants.PROPERTY_DOT_VALUE_STRING, propertyValue);
		map.put(CommonConstants.LIMIT, CommonConstants.LIMIT_MINUS_ONE);
		return map;
	}

	/**
	 * Gets the dam path from var com resource.
	 *
	 * @param bDBWorkflowConfigService the b DB workflow config service
	 * @param input                    the input
	 * @param fileName                 the file name
	 * @return the dam path from var com resource
	 */
	public static String getDamPathFromVarComResource(BDBWorkflowConfigService bDBWorkflowConfigService, String input,
			String fileName) {
		String destinationPath = StringUtils.EMPTY;
		if (null != bDBWorkflowConfigService) {
			destinationPath = input.replace(bDBWorkflowConfigService.getVarCommerceBasePath(),
					bDBWorkflowConfigService.getDamAssetBasePath());
			destinationPath = destinationPath.substring(0, destinationPath.indexOf(CommonConstants.BASE_WITH_SLASH));
			destinationPath = new StringBuilder(destinationPath).append(CommonConstants.BASE_WITH_SLASH)
					.append(CommonConstants.SLASH_PDF).append(fileName).append(CommonConstants.DOT_PDF).toString()
					.trim();
			LOG.debug("Destination path in Dam : {} ", destinationPath);
		}
		return destinationPath;
	}

	/**
	 * Gets the creates the query map.
	 *
	 * @param propertyValue the property value
	 * @param property1     the property 1
	 * @param property2     the property 2
	 * @param path          the path
	 * @return the creates the query map
	 */
	public static Map<String, String> getQueryMapForExcelNode(String propertyValue, String property1, String property2,
			String path) {
		Map<String, String> map = new HashMap<>();
		map.put(CommonConstants.PATH, path);
		map.put(CommonConstants.TYPE, JcrResourceConstants.NT_SLING_FOLDER);
		map.put("group.1_property", property1);
		map.put("group.1_property.value", propertyValue);
		map.put("group.2_property", property2);
		map.put("group.2_property.value", propertyValue);
		map.put("group.p.or", "true");
		map.put(CommonConstants.LIMIT, CommonConstants.LIMIT_MINUS_ONE);
		return map;
	}

	/**
	 * Gets the json object.
	 *
	 * @param varientValueMap the varient value map
	 * @param property        the property
	 * @return the json object
	 */
	public static JsonArray getJsonObject(ValueMap varientValueMap, String property) {
		String productData = varientValueMap.get(property, String.class);
		return new JsonParser().parse(productData).getAsJsonArray();
	}

	/**
	 * Gets the json property.
	 *
	 * @param assetObj the asset obj
	 * @param myParam  the my param
	 * @return the json property
	 */
	public static String getJsonProperty(JsonObject assetObj, String myParam) {
		String property = null;
		if (null != assetObj.get(myParam)) {
			property = assetObj.get(myParam).getAsString();
		}
		return property;
	}

	/**
	 * Gets the value from json object provided in string format.
	 *
	 * @param jsonStructure the json structure
	 * @param key           the key
	 * @return the value from json
	 */
	public static String getValueFromJsonString(String jsonStructure, String key) {
		return Optional.ofNullable(jsonStructure).map(j -> new Gson().fromJson(j, JsonObject.class))
				.map(jObj -> getValueFromJson(jObj, key)).orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets the keyword data page path.
	 *
	 * @param currentPage the current page
	 * @return the keyword data page path
	 */
	public static String getKeywordPagePath(Page currentPage) {
		String keywordPath = StringUtils.EMPTY;
		if (null != currentPage) {
			InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			keywordPath = inVM.getInherited("keywordsDataPagePath", String.class);
		}
		return keywordPath;
	}

	/**
	 * Gets the product category.
	 *
	 * @param currentPage the current page
	 * @return the product category
	 */
	public static String getProductCategory(Page currentPage) {
		String category = StringUtils.EMPTY;
		if (null != currentPage) {
			InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			category = inVM.getInherited("productCategoryTags", String.class);
		}
		return category;
	}

	/**
	 * Gets the PDP Url from bdb tag.
	 *
	 * @param tagId             tag id
	 * @param country           the country
	 * @param resource          resource
	 * @param resolver          resolver
	 * @param solrSearchService the solr search service
	 * @return the PDP URL
	 */
	public static String getPdpUrlFromTag(String tagId, String country, Resource resource, ResourceResolver resolver,
			SolrSearchService solrSearchService) {

		final StringBuilder url = new StringBuilder();
		final Page currentPage = getPageFromResource(resource);
		final String locale = getLanguage(currentPage) + "-" + getCountry(currentPage);
		final String catalogueId = Optional.ofNullable(resolver).map(r -> r.adaptTo(TagManager.class))
				.map(t -> t.resolve(tagId)).map(Tag::getName).orElse(StringUtils.EMPTY);

		final String partialUrl = Optional.ofNullable(catalogueId).filter(StringUtils::isNotEmpty)
				.map(c -> solrSearchService.getHpNodeResource(c, country, resolver)).map(r -> getPdpProductUrl(r))
				.orElse(StringUtils.EMPTY);

		return Optional.ofNullable(partialUrl).filter(StringUtils::isNotEmpty)
				.map(u -> (url.append(CommonConstants.CONTENT).append(CommonConstants.SINGLE_SLASH)
						.append(CommonConstants.BDB).append(CommonConstants.SINGLE_SLASH)
						.append(CommonHelper.getRegion(currentPage)).append(CommonConstants.SINGLE_SLASH)
						.append(CommonHelper.getCountry(currentPage)).append(CommonConstants.SINGLE_SLASH)
						.append(locale).append(u)).toString())
				.orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets the page from resource.
	 *
	 * @param res the resource
	 * @return page
	 */
	public static Page getPageFromResource(Resource res) {

		return Optional.ofNullable(res).map(Resource::getResourceResolver).map(r -> r.adaptTo(PageManager.class))
				.map(p -> p.getContainingPage(res)).orElse(null);
	}

	/**
	 * Gets the end point url.
	 *
	 * @param bdbApiEndpointService the bdb api endpoint service
	 * @param endPoint              the end point
	 * @param isAnonymous           the is anonymous
	 * @param currentPage           the current page
	 * @return the end point url
	 */
	public static String getEndPointUrl(BDBApiEndpointService bdbApiEndpointService, String endPoint,
			boolean isAnonymous, Page currentPage) {
		String siteId = setHybrisSiteId(currentPage);
		StringBuilder endPointUrl = new StringBuilder();
		if (null != bdbApiEndpointService) {
			endPointUrl.append(bdbApiEndpointService.getBDBHybrisDomain().trim());
			endPoint = StringUtils.replace(endPoint, CommonConstants.HYBRIS_SITE_LITERAL, siteId);
			if (isAnonymous) {
				endPoint = StringUtils.replace(endPoint, CommonConstants.HYBRIS_USER_ID_LITERAL,
						bdbApiEndpointService.getAnonymousUserIdPlaceholder());
			}
			endPointUrl.append(endPoint);

		} else {
			LOG.error("Could not get BdbEndpointService reference in CommonHelper");
			return StringUtils.EMPTY;
		}
		return endPointUrl.toString();
	}

	/**
	 * @param bdbApiEndpointService
	 * @param endPoint
	 * @param isAnonymous
	 * @param currentPage
	 * @return
	 */
	public static String getEndPointUrlWithoutSiteId(BDBApiEndpointService bdbApiEndpointService, String endPoint,
			boolean isAnonymous, Page currentPage) {
		StringBuilder endPointUrl = new StringBuilder();
		if (null != bdbApiEndpointService) {
			endPointUrl.append(bdbApiEndpointService.getBDBHybrisDomain().trim());
			endPoint = StringUtils.replace(endPoint, CommonConstants.HYBRIS_SITE_LITERAL, "{{siteId}}");
			if (isAnonymous) {
				endPoint = StringUtils.replace(endPoint, CommonConstants.HYBRIS_USER_ID_LITERAL,
						bdbApiEndpointService.getAnonymousUserIdPlaceholder());
			}
			endPointUrl.append(endPoint);

		} else {
			LOG.error("Could not get BdbEndpointService reference in CommonHelper");
			return StringUtils.EMPTY;
		}
		return endPointUrl.toString();
	}

	/**
	 * converts string to boolean.
	 *
	 * @param str the str
	 * @return true, if successful
	 */
	public static boolean toBoolean(String str) {

		if (null == str) {
			return false;
		} else if (CommonConstants.TRUE.equalsIgnoreCase(str)) {
			return true;
		} else if (CommonConstants.FALSE.equalsIgnoreCase(str)) {
			return false;
		}
		return false;
	}

	/**
	 * Gets the global thumbnail image.
	 *
	 * @param resourceResolver      the resource resolver
	 * @param variantResource   the base product resource
	 * @param externalizerService   the externalizer service
	 * @param bdbApiEndpointService the bdb api endpoint service
	 * @return the global thumbnail image
	 */
	public static String getGlobalThumbnailImage(ResourceResolver resourceResolver, Resource variantResource,
			ExternalizerService externalizerService, BDBApiEndpointService bdbApiEndpointService) {
		
		LOG.debug("Entry getGlobalThumbnailImage method of CommonHelper");
		String thumbnailImagePath = StringUtils.EMPTY;
		String runMode = bdbApiEndpointService.getCustomRunMode();
		List<String> thumbnailImageListFromMediasAttr = null;
		if (null != variantResource) {
			String damGlobalPath = variantResource.getParent().getParent().getPath()
					.replace(CommonConstants.VAR_COMMERCE + CommonConstants.SINGLE_SLASH + CommonConstants.PRODUCTS,
							CommonConstants.CONTENT_DAM)
					.replace(CommonConstants.PRODUCTS,
							CommonConstants.PRODUCTS + CommonConstants.SINGLE_SLASH + "global");
			Resource baseProductDamResource = resourceResolver.getResource(damGlobalPath);
			String materialNumber = variantResource.getParent().getName();
			String tagResourcesPath = getProductTagPath(variantResource);
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
			ValueMap baseValueMap = variantResource.getParent().getParent().getChild(CommonConstants.HP)
					.adaptTo(ValueMap.class);
			if (null != baseProductDamResource) {
				String variantRes = materialNumber.concat("_01.png");
				String vialImgPath = baseProductDamResource.getPath() + CommonConstants.SINGLE_SLASH + variantRes;
				Iterator<Resource> children = baseProductDamResource.listChildren();
				/* To get the Thumbnail Tagged Image from Dam Base */
				Map<String, String> mapObj = new HashMap<String, String>();
				if(StringUtils.isNotBlank(baseValueMap.get("medias", String.class))) {
					String medias = baseValueMap.get("medias", String.class);
					thumbnailImageListFromMediasAttr = getMediasImageFromBaseHp(medias,CommonConstants.THUMBNAIL);
				}
				
				while (children.hasNext()) {
					Resource child = children.next();
					Asset asset = child.adaptTo(Asset.class);
					/* Checking if the child is not null and it does not contain jcr:content node */
					if (null != child.getName() && !(CommonConstants.JCR_CONTENT_NODE.equals(child.getName()))) {
						String childPath = child.getPath() + CommonConstants.JCR_CONTENT;
						Resource imageResource = resourceResolver.getResource(childPath);
						if (null != imageResource) {
							/* Getting the value map of the imageResource resource */
							ValueMap property = imageResource.adaptTo(ValueMap.class);
							if (null != property && replicationStatusCheck(property, runMode)) {
									/* To get Images based on Product SKU */
									thumbnailImagePath = getImageBasedOnProductSku(resourceResolver,asset,child,materialNumber);
									if (!StringUtils.isEmpty(thumbnailImagePath)) break;
									/* To get product images */
									getThumbnailTaggedImage(thumbnailImagePath, tagManager, asset, mapObj,thumbnailImageListFromMediasAttr);
									if(mapObj.containsKey(CommonConstants.THUMBNAIL) && StringUtils.isNotEmpty(mapObj.get(CommonConstants.THUMBNAIL))) {
										thumbnailImagePath = mapObj.get(CommonConstants.THUMBNAIL);
									}
									if (!StringUtils.isEmpty(thumbnailImagePath)) {
										break;
									}
							}
						}
					}
				}
				if (StringUtils.isEmpty(thumbnailImagePath)) {
                    if (mapObj.containsKey(CommonConstants.PRIMARY)
                            && StringUtils.isNotEmpty(mapObj.get(CommonConstants.PRIMARY))) {
                        String primaryImagePath = mapObj.get(CommonConstants.PRIMARY);
                        thumbnailImagePath = primaryImagePath;
                    } else if (mapObj.containsKey(CommonConstants.HERO)
                            && StringUtils.isNotEmpty(mapObj.get(CommonConstants.HERO))) {
                    	String heroImagePath = mapObj.get(CommonConstants.HERO);
                        thumbnailImagePath = heroImagePath;
                    } else if (mapObj.containsKey(CommonConstants.GALLERY)
                            && StringUtils.isNotEmpty(mapObj.get(CommonConstants.GALLERY))) {
                    	String galleryImagePath = mapObj.get(CommonConstants.GALLERY);
                        thumbnailImagePath = galleryImagePath;
                    } else if (null != resourceResolver.getResource(vialImgPath)) {
                    	/* Getting the valueMap of the jcr:content node of vialImgPath */
                    	ValueMap vialImgproperties = resourceResolver.getResource(vialImgPath + CommonConstants.JCR_CONTENT).adaptTo(ValueMap.class);
        				if (null != vialImgproperties && replicationStatusCheck(vialImgproperties, runMode)) {
        						thumbnailImagePath = vialImgPath; 
        				}
                    }
                }
				LOG.debug("mapObj : " + mapObj);
			}
			/* To get vial images if product images are not available */
			
			ValueMap variantValueMap = variantResource.adaptTo(ValueMap.class);
			thumbnailImagePath = getOtherVialOrRuoImage(resourceResolver, baseValueMap, bdbApiEndpointService,
					thumbnailImagePath, materialNumber, tagResourcesPath, variantValueMap);

			/* To get Placeholder Image */
			/* To get Placeholder Image when the image asset is unpublished */
			String thumbnailImageJcrPath = thumbnailImagePath + CommonConstants.JCR_CONTENT;
			if (null != thumbnailImageJcrPath && null != resourceResolver.getResource(thumbnailImageJcrPath)) {
				Resource thumbnailBaseImage = resourceResolver.getResource(thumbnailImageJcrPath);
				if (null != thumbnailBaseImage) {
					/* Getting the value map of the thumbnailBaseImage resource */
					ValueMap property = thumbnailBaseImage.adaptTo(ValueMap.class);
					if (null != property) {
						/* Checking if the value map property has cq:lastReplicationAction as Deactivate.
						 * If the condition satisfies, assigning the placeholder path to thumbnailImagePath */
						String lastReplicationAction = property.get(CommonConstants.CQ_LAST_REPICATION_ACTION, String.class);
						if ((null == lastReplicationAction || CommonConstants.DEACTIVATE.equals(lastReplicationAction)) && !runMode.contentEquals("publish")) {
							thumbnailImagePath = "/content/dam/bdb/general/placeholder/placeholder.png";
						}
					}
				}	
			}
			
			if (StringUtils.isEmpty(thumbnailImagePath)) {
				thumbnailImagePath = "/content/dam/bdb/general/placeholder/placeholder.png";
			}
		}
		LOG.debug("Thumbnail Image path is : " + thumbnailImagePath);
		LOG.debug("Exit getGlobalThumbnailImage method of CommonHelper");
		return thumbnailImagePath;
			
		}

	/**
	 * this function will match the SKU number with materialNumber and return it's path.
	 * @param resourceResolver
	 * @param asset
	 * @param child
	 * @param materialNumber
	 * @return
	 */
	public static String getImageBasedOnProductSku(ResourceResolver resourceResolver, Asset asset, Resource child, String materialNumber) {
		String childPath = child.getPath() + CommonConstants.METADATAPATH;
		Resource imageResource = resourceResolver.getResource(childPath);
		if (null != imageResource) {
			/* Getting the value map of the imageResource resource */
			ValueMap property = imageResource.adaptTo(ValueMap.class);
			if(null != property){
				String[] productSkus = new String[]{property.get(CommonConstants.SKU_NUMBER, String.class)};
				for(String sku : productSkus){
					if(null != sku && sku.equalsIgnoreCase(materialNumber)){
						return asset.getPath();
					}
				}
			}
		}
		return StringUtils.EMPTY;
	}


	/**
	 * Gets the replication status for image.
	 * 
	 * replicationStatusCheck method checks the values of the customReplicationStatus
	 * and the cq:lastReplicationAction if the values are PUBLISHED and Activate then it returns true
	 * @param ValueMap   the valueMap
	 * @param String     the runmode
	 * @return boolean 
	 */
	private static boolean replicationStatusCheck(ValueMap valueMap, String runMode) {
		if (null != valueMap) {
			String customReplicationStatus = valueMap.get(CommonConstants.CUSTOM_REPLICATION_STATUS, String.class);
			String lastReplicationAction = valueMap.get(CommonConstants.CQ_LAST_REPICATION_ACTION, String.class);
			if (null != customReplicationStatus || null != lastReplicationAction) {
				if (CommonConstants.PUBLISHED.equals(customReplicationStatus) || CommonConstants.REP_STATUS_ACTIVATE.equals(lastReplicationAction)) {
					return true;
				}
			}
			// If the runMode is publish then returning true
			if (runMode.equalsIgnoreCase("publish")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the product image.
	 *
	 * @param resourceResolver   the resource resolver
	 * @param thumbnailImagePath the thumbnail image path
	 * @param tagManager         the tag manager
	 * @param vialImgPath        the vial img path
	 * @param asset              the asset
	 * @param mapObj
	 * @param thumbnailImageListFromMediasAttr 
	 * @return
	 * @return the product image
	 */
	public static Map<String, String> getThumbnailTaggedImage(String thumbnailImagePath, TagManager tagManager,
			Asset asset, Map<String, String> mapObj, List<String> thumbnailListFromMedias) {
		if (null != asset && null != asset.getMetadata(CommonConstants.CQ_TAGS)) {
			Object[] tags = (Object[]) asset.getMetadata(CommonConstants.CQ_TAGS);
			List<Object> tagList = Arrays.asList(tags);
			if (CollectionUtils.isNotEmpty(tagList) && !tagList.contains(CommonConstants.CLINICAL_TAG)) {
				if (tagList.contains(CommonConstants.THUMBNAIL_TAG)) {
					thumbnailImagePath = getImageWhentumbnailTagMatched(thumbnailImagePath, asset,thumbnailListFromMedias);
					mapObj.put(CommonConstants.THUMBNAIL, thumbnailImagePath);
				} else if (tagList.contains(CommonConstants.PRIMARY_TAG)) {
					String primaryImage = getImageWhenTagMatched(thumbnailImagePath, tagManager, asset, tags,
							CommonConstants.PRIMARY);
					mapObj.put(CommonConstants.PRIMARY, primaryImage);
				} else if (tagList.contains(CommonConstants.HERO_TAG)) {
					String heroImage = getImageWhenTagMatched(thumbnailImagePath, tagManager, asset, tags,
							CommonConstants.HERO);
					mapObj.put(CommonConstants.HERO, heroImage);
				} else if (tagList.contains(CommonConstants.GALLERY_TAG)) {
					String galleryImage = getImageWhenTagMatched(thumbnailImagePath, tagManager, asset, tags,
							CommonConstants.GALLERY);
					mapObj.put(CommonConstants.GALLERY, galleryImage);
				}
			}
		}
		return mapObj;
	}
	public static String getImageWhenTagMatched(String thumbnailImagePath, TagManager tagManager, Asset asset,
			Object[] tags, String thumbnail) {
		for (Object tagObject : tags) {
			Tag tag = tagManager.resolve(tagObject.toString());
			if (null != tag && tag.getTitle().equals(thumbnail)) {
				thumbnailImagePath = asset.getPath();
				break;
			}
		}
		return thumbnailImagePath;
	}

	public static String getImageWhentumbnailTagMatched(String thumbnailImagePath, Asset asset,
			List<String> thumbnailImageListFromMediasAttr) {
		String imageName = asset.getName();
		if(null != thumbnailImageListFromMediasAttr && !thumbnailImageListFromMediasAttr.isEmpty()) {
			Optional<String> thumbObj = thumbnailImageListFromMediasAttr.stream().filter(t -> StringUtils.isNotEmpty(t) && t.equalsIgnoreCase(imageName)).findFirst();
			if(thumbObj.isPresent()) {
				thumbnailImagePath = asset.getPath();
				return thumbnailImagePath;
			}else {
				return thumbnailImagePath;
			}
		}else {
			return thumbnailImagePath;
		}
	}
	
	/**
	 * Gets the other vial image.
	 *
	 * @param resourceResolver      the resource resolver
	 * @param baseProductResource   the base product resource
	 * @param externalizerService   the externalizer service
	 * @param bdbApiEndpointService the bdb api endpoint service
	 * @param thumbnailImagePath    the thumbnail image path
	 * @param materialNumber        the material number
	 * @param variantValueMap 
	 * @return the other vial image
	 */
	public static String getOtherVialOrRuoImage(ResourceResolver resourceResolver, ValueMap hpValueMap,
			BDBApiEndpointService bdbApiEndpointService, String thumbnailImagePath, String materialNumber,
			String tagValue, ValueMap variantValueMap) {
		JsonArray ruoJsonArray = new JsonArray();
		JsonArray otherVialArray = new JsonArray();
		if (StringUtils.isEmpty(thumbnailImagePath)) {
			List<Resource> resourceList = CommonHelper.getOtherVialImages(tagValue, hpValueMap, resourceResolver,
					bdbApiEndpointService.getVialImagesBasePath());
			getVialImages(resourceResolver, otherVialArray, materialNumber, resourceList);
			if ((null != hpValueMap.get(CommonConstants.REGULATORY_STATUS)
					&& hpValueMap.get(CommonConstants.REGULATORY_STATUS, String.class).equals(CommonConstants.RUO))
					&& (hpValueMap.get(CommonConstants.PDP_TEMPLATE, String.class).equals(CommonConstants.SFA)
							|| hpValueMap.get(CommonConstants.PDP_TEMPLATE, String.class)
									.equals(CommonConstants.OTHER))) {
				CommonHelper.getRuoVial(resourceResolver, ruoJsonArray, hpValueMap, resourceList,
						bdbApiEndpointService, variantValueMap);
			}
			if (ruoJsonArray.size() > 0) {
				thumbnailImagePath = iterateJsonArray(thumbnailImagePath, ruoJsonArray);
			}
			if (otherVialArray.size() > 0) {
				thumbnailImagePath = iterateJsonArray(thumbnailImagePath, otherVialArray);
			}
		}
		return thumbnailImagePath;
	}

	/**
	 * Iterate json array.
	 *
	 * @param thumbnailImagePath the thumbnail image path
	 * @param ruoJsonArray       the ruo json array
	 * @return the string
	 */
	public static String iterateJsonArray(String thumbnailImagePath, JsonArray ruoJsonArray) {
		for (JsonElement ruoImages : ruoJsonArray) {
			if (null != ruoImages.getAsJsonObject().get("imagePath")) {
				thumbnailImagePath = ruoImages.getAsJsonObject().get("imagePath").getAsString();
				break;
			}
		}
		return thumbnailImagePath;
	}

	/**
	 * Gets the vial images.
	 *
	 * @param resourceResolver    the resource resolver
	 * @param ruoJsonArray        the ruo json array
	 * @param otherVialArray      the other vial array
	 * @param materialNumber      the material number
	 * @param resourceList        the resource list
	 * @param hpResource          the hp resource
	 * @param externalizerService the externalizer service
	 * @return the vial images
	 */
	public static void getVialImages(ResourceResolver resourceResolver, JsonArray otherVialArray, String materialNumber,
			List<Resource> resourceList) {
		LOG.debug("Entered Unique and Bottle Vial Image Resource Path");
		ValueMap properties;

		if (CollectionUtils.isNotEmpty(resourceList)) {
			for (Resource resItem : resourceList) {
				Asset asset = resItem.adaptTo(Asset.class);
				JsonObject otherVialObj = new JsonObject();
				String bottleOrUniqueImagePath = resItem.getPath();
				LOG.debug("Bottel Or unique ImagePath Path {}", bottleOrUniqueImagePath);
				Resource metaDataResource = resItem.getChild(CommonConstants.METADATA_PATH_AS_CHILD);
				properties = metaDataResource.adaptTo(ValueMap.class);
				if (MapUtils.isNotEmpty(properties)) {
					String[] skuIds = properties.get("cq:sku-id", String[].class);
					if (null != skuIds && skuIds.length > 0) {
						getUniqueVial(resourceResolver, otherVialArray, asset, materialNumber, otherVialObj,
								bottleOrUniqueImagePath, skuIds);
					} else {
						LOG.debug("No SKUs Associated with Image");
					}
				}
			}
		}

	}

	/**
	 * Gets the unique vial.
	 *
	 * @param resourceResolver the resource resolver
	 * @param otherVialArray   the other vial array
	 * @param asset            the asset
	 * @param materialNumber   the material number
	 * @param otherVialObj     the other vial obj
	 * @param ruoImagePath     the ruo image path
	 * @param skuIds           the sku ids
	 * @return the unique vial
	 */
	public static void getUniqueVial(ResourceResolver resourceResolver, JsonArray otherVialArray, Asset asset,
			String materialNumber, JsonObject otherVialObj, String bottleOrUniqueImagePath, String[] skuIds) {
		TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
		for (String skuNumber : skuIds) {
			Tag tag = tagManager.resolve(skuNumber);
			if (null != tag) {
				String skuTitle = tag.getTitle();
				if (materialNumber.equalsIgnoreCase(skuTitle)) {
					LOG.debug("Matched skuId Tag {}", skuNumber);
					otherVialObj.addProperty("imagePath", bottleOrUniqueImagePath);
					getMetaDataAttributes(otherVialArray, otherVialObj, asset);
				} else {
					LOG.debug("Sku Does not Matched For Image {}", bottleOrUniqueImagePath);
				}
			} else {
				LOG.debug("Image Tag Does Not Have AEM CQTag");
			}
		}
	}

	/**
	 * Gets the ruo vial.
	 *
	 * @param resourceResolver      the resource resolver
	 * @param ruoJsonArray          the ruo json array
	 * @param asset                 the asset
	 * @param hpResource            the hp resource
	 * @param properties            the properties
	 * @param ruoVialObj            the ruo vial obj
	 * @param resourceList
	 * @param bdbApiEndpointService
	 * @param variantValueMap 
	 * @param ruoImagePath          the ruo image path
	 * @param externalizerService   the externalizer service
	 * @return the ruo vial
	 */
	public static void getRuoVial(ResourceResolver resourceResolver, JsonArray ruoJsonArray, ValueMap hpValueMap,
			List<Resource> resourceList, BDBApiEndpointService bdbApiEndpointService, ValueMap variantValueMap) {

		String storageBufVal = hpValueMap.get(CommonConstants.STORAGE_BUFFER, String.class);
		String volPerTestVal = hpValueMap.get(CommonConstants.VOL_PER_TEST_KEY, String.class);
		String productTypeVal = hpValueMap.get(CommonConstants.PRODUCT_TYPE_KEY, String.class);
		String ruoVialImagePath = bdbApiEndpointService.getRuoVialImagesBasePath() + CommonConstants.SINGLE_SLASH;
		String variantSize = variantValueMap.get("sizeQty", String.class);
		Double variantSizeValue = null;
		if(StringUtils.isNotBlank(variantSize) && NumberUtils.isParsable(variantSize)) {
			variantSizeValue = Double.parseDouble(variantSize);
		}
		JsonObject ruoVialObj = new JsonObject();
		if (StringUtils.isNotBlank(storageBufVal)
				&& StringUtils.containsIgnoreCase(storageBufVal, CommonConstants.LYOPHILIZED)) {
			LOG.debug("Inside RUO Vial Which has matched storageBuffer");
			getImgBasedOnVolPerTestOrStorageBuffer(resourceResolver, ruoJsonArray, ruoVialObj, ruoVialImagePath,
					bdbApiEndpointService.getRuoVialBasedOnStorageBuffer());
		} else if (StringUtils.isNotBlank(volPerTestVal) && volPerTestVal.contains("20") && null != variantSizeValue && variantSizeValue >= 100) {
			LOG.debug("Inside RUO Vial Which has matched volPerTest");
			getImgBasedOnVolPerTestOrStorageBuffer(resourceResolver, ruoJsonArray, ruoVialObj, ruoVialImagePath,
					bdbApiEndpointService.getRuoVialBasedOnVolPerTest());
		} else if (CollectionUtils.isNotEmpty(resourceList)) {
			LOG.debug("Inside RUO Vial Which has matched DyeName");
			getRuoBasedOnDyeName(ruoJsonArray, hpValueMap, resourceList, ruoVialImagePath, resourceResolver);
		} else if (StringUtils.isNotBlank(productTypeVal)
				&& productTypeVal.equals(CommonConstants.ISO_TYPE_CONTROL_LYSATE)) {
			LOG.debug("Inside RUO Vial Which has matched productType");
			getImgBasedOnVolPerTestOrStorageBuffer(resourceResolver, ruoJsonArray, ruoVialObj, ruoVialImagePath,
					bdbApiEndpointService.getRuoVialBasedOnProductType());
		}
	}

	public static void getRuoBasedOnDyeName(JsonArray ruoJsonArray, ValueMap hpValueMap, List<Resource> resourceList, String ruoVialImageBasePath, ResourceResolver resource) {
		for (Resource resItem : resourceList) {
			Asset asset = resItem.adaptTo(Asset.class);
			String ruoImagePath = resItem.getPath();
			LOG.debug("RuoImagePath Path {}", ruoImagePath);
			Resource metaDataResource = resItem.getChild(CommonConstants.METADATA_PATH_AS_CHILD);
			ValueMap properties = metaDataResource.adaptTo(ValueMap.class);
			getDyeNamePaths(ruoJsonArray, asset, ruoImagePath, properties, hpValueMap, ruoVialImageBasePath, resource);
		}
	}

	public static void getImgBasedOnVolPerTestOrStorageBuffer(ResourceResolver resourceResolver, JsonArray ruoJsonArray,
			JsonObject ruoVialObj, String vialImagePath, String assetName) {
		Resource amberWhiteImgResource = resourceResolver.getResource(vialImagePath + assetName);
		LOG.debug("RUO Vial Resource {}", amberWhiteImgResource);
		if (null != amberWhiteImgResource) {
			Asset ruoAsset = amberWhiteImgResource.adaptTo(Asset.class);
			ruoVialObj.addProperty("imagePath", vialImagePath + assetName);
			getMetaDataAttributes(ruoJsonArray, ruoVialObj, ruoAsset);
		}
	}

	public static void getMetaDataAttributes(JsonArray ruoJsonArray, JsonObject ruoVialObj, Asset ruoAsset) {
		String title = getMetaDataAttribute(ruoAsset, CommonConstants.DC_TITLE);
		String[] titleArray = title.split("\\|");
		ruoVialObj.addProperty(CommonConstants.IMAGE_TITLE, titleArray[0]);
		String caption = getMetaDataAttribute(ruoAsset, CommonConstants.DC_CAPTION);
		ruoVialObj.addProperty(CommonConstants.IMAGE_CAPTION, caption);
		String altText = getMetaDataAttribute(ruoAsset, CommonConstants.DC_IMAGE_ALT);
		ruoVialObj.addProperty(CommonConstants.IMAGE_ALT_TEXT, altText);
		ruoJsonArray.add(ruoVialObj);
	}

	/**
	 * Gets the dye name paths.
	 *
	 * @param ruoJsonArray        the ruo json array
	 * @param asset               the asset
	 * @param ruoImagePath        the ruo image path
	 * @param properties          the value map of ruo vial image
	 * @param hpValueMap          the value map of product hp node
	 * @param ruoVialImageBasePath 
	 * @param externalizerService the externalizer service
	 * @param resourceResolver    the resource resolver
	 * @return the dye name paths
	 */
	public static void getDyeNamePaths(JsonArray ruoJsonArray, Asset asset, String ruoImagePath, ValueMap properties,
			ValueMap hpValueMap, String ruoVialImageBasePath, ResourceResolver resource) {
		String[] dyeNameList = properties.get("dyeName", String[].class);
		String[] brandNameList = properties.get("brandNames", String[].class);
		String productDyeName = CommonHelper.getDyeNameFromFormat(hpValueMap);
		String productBrandName = hpValueMap.get(CommonConstants.BRAND, String.class);
		Boolean isBrandMatched = compareBrandName(brandNameList, productBrandName, resource);
		if (null != dyeNameList && dyeNameList.length > 0) {
			for (String dyeName : dyeNameList) {
				JsonObject ruoVialObj = new JsonObject();
				if (null != productDyeName && dyeName.equalsIgnoreCase(productDyeName) && isBrandMatched) {
					LOG.debug("Inside RUO Vial Which has matched DyeName and BrandName");
					if(ruoImagePath.contains(ruoVialImageBasePath)) {
						ruoVialObj.addProperty("imagePath", ruoImagePath);
						getMetaDataAttributes(ruoJsonArray, ruoVialObj, asset);
						LOG.debug("Dye Name: {} Brand Name: {}", productDyeName, productBrandName);
						LOG.debug("RUO Vial Image path for the matched dyeName and brandName : {}", ruoImagePath);
					}
				}
			}
		}
	}
	
	/**
	 * Compares the product brand attribute with the brandNameList of the vial image
	 * and return true if there is any match found.
	 *
	 * @param brandNameList       the brand name list
	 * @param productDyeName      the product dye name
	 * @param resourceResolver    the resource resolver
	 * @return the boolean value
	 */
	private static boolean compareBrandName(String[] brandNameList, String productBrandName, ResourceResolver resourceResolver) {
		if (null != brandNameList && brandNameList.length > 0 && StringUtils.isNotEmpty(productBrandName)) {
			for (String brandName : brandNameList) {
				String brandPath = CommonConstants.BRAND_TAG_PATH + brandName.substring(brandName.lastIndexOf("/") +1 );
					Resource brandResource = resourceResolver.getResource(brandPath);
					if (null != brandResource) {
		                 ValueMap valueMap = brandResource.adaptTo(ValueMap.class);
		                 String jcrTitle = valueMap.get(CommonConstants.JCRTITLE, String.class);
		                 if (jcrTitle.equalsIgnoreCase(productBrandName)) {
		                	 return true;
		                 }
				}
		}
	}
		return false;
	}

	/**
	 * Gets the meta data attribute.
	 *
	 * @param asset    the asset
	 * @param property the property
	 * @return the meta data attribute
	 */
	public static String getMetaDataAttribute(Asset asset, String property) {
		String title = StringUtils.EMPTY;
		if (null != asset && null != asset.getMetadataValue(property)) {
			title = asset.getMetadataValue(property);
		}
		return title;
	}

	/**
	 * Gets the pdf paths based on type.
	 *
	 * @param basePdfPath the base pdf path
	 * @param pdfType     the pdf type
	 * @param resolver    the resolver
	 * @param locale      the locale
	 * @param skuID       can be null.
	 * @return list of pdf paths
	 */
	public static ArrayList<String> getPdfPathsBasedOnType(String basePdfPath, String pdfType,
			ResourceResolver resolver, String locale, String skuID) {
		ArrayList<String> pdfList = new ArrayList<String>();
		locale = null != locale ? (CommonConstants.REGION_TAG + locale.toLowerCase()) : StringUtils.EMPTY;
		if (null != basePdfPath && null != pdfType && null != resolver) {
			Resource baseRes = resolver.getResource(basePdfPath);
			if (null != baseRes) {
				Iterator<Resource> listPdf = baseRes.listChildren();
				while (listPdf.hasNext()) {
					Resource pdfRes = listPdf.next();
					String pdfName = pdfRes.getName();
					Resource res = pdfRes.getChild(CommonConstants.METADATA_PATH_AS_CHILD);
					if (null != res) {
						ValueMap vm = res.adaptTo(ValueMap.class);
						if (vm.containsKey(CommonConstants.PDF_DOC_REGION)
								&& vm.containsKey(CommonConstants.PDF_DOC_TYPE)) {
							String[] docRegion = vm.get(CommonConstants.PDF_DOC_REGION, String[].class);

							String docType = vm.get(CommonConstants.PDF_DOC_TYPE).toString().toLowerCase()
									.replaceAll(CommonConstants.SPACE, StringUtils.EMPTY);
							pdfType = pdfType.toLowerCase().replaceAll(CommonConstants.SPACE, StringUtils.EMPTY);

							if (pdfType.equals(docType)) {
								boolean matchedRegion = false;
								for (String region : docRegion) {
									if(region.equals("bdb:regions/uk")) {
										region = "bdb:regions/gb";
									}
									if (region.equals(locale) || region.equals("bdb:regions/global")) {
										matchedRegion = true;
										break;
									}
								}
								if (matchedRegion) {
									if (null == skuID || skuID.equals(pdfName)) {
										pdfList.add(pdfRes.getPath());
									}
								}

							}
						}
					}
				}
			}
		}
		return pdfList;
	}

	/**
	 * Gets the date and time.
	 *
	 * @param strDateTime    the str date time
	 * @param dateTimeFormat the date time format
	 * @return the date and time
	 */
	public static String getDateAndTime(String strDateTime, String dateTimeFormat) {
		return Optional.ofNullable(strDateTime).filter(s -> !s.equals("")).map(s -> {
			try {
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(s);
			} catch (ParseException e) {
				LOG.error("ParseException {}", e.getMessage());
			}
			return StringUtils.EMPTY;
		}).map(d -> new SimpleDateFormat(dateTimeFormat).format(d)).orElse("");
	}

	/**
	 * Gets the date and time.
	 *
	 * @param bdbApiEndpointService the sling settings service
	 * @return the date and time
	 */
	public static String getRunMode(BDBApiEndpointService bdbApiEndpointService) {
		String runmodes = bdbApiEndpointService.getCustomRunMode();
		if (StringUtils.equalsIgnoreCase(runmodes, CommonConstants.AUTHOR)) {
			return CommonConstants.AUTHOR;
		} else if (StringUtils.equalsIgnoreCase(runmodes, CommonConstants.PUBLISH)) {
			return CommonConstants.PUBLISH;
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Gets metadata property value.
	 *
	 * @param resourceResolver for the resource resolver.
	 * @param property         metadata property value.
	 * @param path             for the resource path.
	 * @return value for metadata property.
	 */
	public static String getResourceMetadataProperty(ResourceResolver resourceResolver, String property, String path) {
		Resource resource = resourceResolver.getResource(path);
		Map<String, String> metadaData = resource.getResourceMetadata().getParameterMap();
		String value = metadaData.get(property);
		LOG.debug("CommonHelper.getResourceMetadata resource : {}, property : {}, value :{}", path, property, value);
		return value;
	}

	/**
	 * Gets the other vial images.
	 *
	 * @param materialNumber   the material number
	 * @param hpResource       the hp resource
	 * @param resourceResolver the resource resolver
	 * @param vialBasePath     the vial base path
	 * @return the other vial images
	 */
	public static List<Resource> getOtherVialImages(String materialNumber, ValueMap hpValueMap,
			ResourceResolver resourceResolver, String vialBasePath) {
		LOG.debug("Entry Other Vial Images Query");
		String dyeName = getDyeNameFromFormat(hpValueMap);
		String brandName = hpValueMap.get(CommonConstants.BRAND, String.class);
		String tagPathName = "bdb:formats/" + CommonHelper.nameValidator(dyeName).toLowerCase();
		String brandPathName = "bdb:brands/" + CommonHelper.nameValidator(brandName).toLowerCase();
		
		Map<String, String> queryMap = getQuery(materialNumber, dyeName, vialBasePath,
				tagPathName, brandPathName);
		LOG.debug("QueryMap for Vial Images : {}", queryMap);
		Query query = CommonHelper.getQuery(resourceResolver, queryMap);
		LOG.debug("Query for Vial Images : {}", null != query ? query.toString() : StringUtils.EMPTY);
		LOG.debug("Exit getOtherVialImages method of PDPSessionModel");
		return getResourcesFromQuery(query);
	}

	public static String getProductTagPath(Resource hpResource) {
		LOG.debug("Entry Product Tags");
		String tagPath = StringUtils.EMPTY;
		if (null != hpResource) {
			String materialPath = hpResource.getParent().getPath();
			tagPath = materialPath.replace("/content/commerce/products/bdb/products", "bdb:products");
			LOG.debug("Exit getProductTagPath {}", tagPath);
		}
		return tagPath;
	}

	/**
	 * Gets the dye name from format.
	 *
	 * @param hpResource the hp resource
	 * @return the dye name from format
	 */
	public static String getDyeNameFromFormat(ValueMap hpValueMap) {
		String dyeName = StringUtils.EMPTY;
		if (hpValueMap.containsKey(CommonConstants.BD_FORMAT)) {
			JsonArray formatDetailsJsonArray = new JsonParser()
					.parse(hpValueMap.get(CommonConstants.BD_FORMAT).toString()).getAsJsonArray();
			for (JsonElement formatDetailsElement : formatDetailsJsonArray) {
				dyeName = getDyeName(formatDetailsElement);
			}
		}

		return dyeName;
	}

	/**
	 * Gets the dye name.
	 *
	 * @param formatDetailsElement the format details element
	 * @return the dye name
	 */
	public static String getDyeName(JsonElement formatDetailsElement) {
		JsonObject formatDetailsJson = formatDetailsElement.getAsJsonObject();
		String formatname = StringUtils.EMPTY;
		if (formatDetailsJson.has(CommonConstants.DYE_NAME) && null != formatDetailsJson.get(CommonConstants.DYE_NAME)
				&& !formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString().isEmpty()) {
			formatname = formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString();
		}
		return formatname;
	}

	/**
	 * Gets the query.
	 *
	 * @param materialNumber the material number
	 * @param skuIDKey       the sku ID key
	 * @param dyeNameMapKey  the dye name map key
	 * @param dyeNameValue   the dye name value
	 * @param basePath       the base path
	 * @return the query
	 */
	private static Map<String, String> getQuery(String materialNumber, String dyeNameValue,
			String basePath, String tagPathName, String brandPathName) {
		Map<String, String> map = new HashMap<>();
		LOG.debug("Vial Images Folder Path {}", basePath);
		map.put(CommonConstants.PATH, basePath);
		map.put(SolrSearchConstants.QUERY_BUILDER_TYPE, SolrSearchConstants.JCR_PRIMARY_TYPE_DAM_ASSETS);
		// The below query will return the ruo black-vial image path 
		// if any materialNumber tag is tagged in the Product Tags field (cq:sku-id) of the Product Sku Tab.
		map.put("group.1_group.1_property", "jcr:content/metadata/cq:sku-id");
		map.put("group.1_group.1_property.value", materialNumber);
		// The below query for dyeName and brandNames is an 'AND' condition where
		// the property and value of dyeName and the brandNames is added to the map only if the dyeName is not "NA"
		// and is not null/empty and the brandPathName is also not empty or null.
		// The below query will return the ruo black-vial image path only when the correct dyeName is entered
		// in the Dye Name field (dyeName) and the correct brand name is tagged 
		// in the Brand Names field (brandNames) of the Product Sku Tab.
		if (StringUtils.isNotEmpty(dyeNameValue) && !dyeNameValue.equals("NA") && StringUtils.isNotEmpty(brandPathName)) {
			map.put("group.2_group.1_property", "jcr:content/metadata/dyeName");
			map.put("group.2_group.1_property.value", dyeNameValue);
			map.put("group.2_group.2_property", "jcr:content/metadata/brandNames");
			map.put("group.2_group.2_property.value", brandPathName);
		}
		// Below query for cq:tags will return the format image path  
		// if a dyeName tag is tagged for that product in the Tags field (cq:tags) of the Basic Tab.
		map.put("group.3_group.1_property", "jcr:content/metadata/cq:tags");
		map.put("group.3_group.1_property.value", tagPathName);
		map.put("group.p.or", "true");
		map.put(CommonConstants.LIMIT, CommonConstants.LIMIT_MINUS_ONE);
		map.put("orderby", "path");
		return map;

	}

	/**
	 * Gets the resources from query.
	 *
	 * @param query the query
	 * @return the resources from query
	 */
	private static List<Resource> getResourcesFromQuery(Query query) {
		LOG.debug("Entry getResourcesFromQuery");
		List<Resource> resourceList = new ArrayList<>();
		if (null != query && null != query.getResult()) {
			SearchResult result = query.getResult();
			LOG.debug("Query Result Size {} ", result.getTotalMatches());
			Iterator<Resource> resources = result.getResources();
			if (null != resources) {
				while (resources.hasNext()) {
					resourceList.add(resources.next());
				}
			} else {
				LOG.debug("Resource Not Found");
			}
		} else {
			LOG.debug("Could not create query from the params {}", query);
		}
		LOG.debug("Exit getResourcesFromQuery method");
		return resourceList;
	}

	public static boolean getProductAvailabilityInRegion(String productId, String country,
			ResourceResolver resourceResolver, CatalogStructureUpdateService catalogStructureUpdateService)
			throws RepositoryException {
		Resource variantResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, productId,
				CommonConstants.MATERIAL_NUMBER);
		if (null != variantResource) {
			Resource sapResource = variantResource.getChild(CommonConstants.SAP_CC_NODENAME)
					.getChild(CommonConstants.REGION_DETAILS_NODE_NAME);
			ValueMap valueMap = sapResource.adaptTo(ValueMap.class);
			if (valueMap.containsKey(country.toUpperCase())) {
				JsonObject countryDetails = new JsonParser().parse(valueMap.get(country.toUpperCase(), String.class))
						.getAsJsonObject();
				if (countryDetails.has("derivedProductStatus") && countryDetails.has("validityStartDate")) {
					String derivedProductStatus = countryDetails.get("derivedProductStatus").getAsString();
					if (derivedProductStatus.equals("DISPLAYONLY") || derivedProductStatus.equals("PURCHASEABLE")) {
						if (checkProductValidityOnDate(countryDetails.get("validityStartDate").getAsString())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static boolean checkProductValidityOnDate(String validityStartDateString) {
		SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		java.util.Date currentDate = new java.util.Date();
		Date validityStartDate = null;
		try {
			validityStartDate = sdfo.parse(validityStartDateString);
		} catch (ParseException e) {
			return false;
		}
		if (currentDate.after(validityStartDate)) {
			return true;
		}

		return false;
	}
	
	/**
	 * Checks whether it is a Phantom product or not.
	 *
	 * @param variantResource the material number
	 * @param country       the sku ID key
	 * @return the phantomFlag
	 */
	public static String isPhantomProduct(Resource variantResource, String country)
			throws RepositoryException {
		String isPhantom = "false";
		if (null != variantResource) {
			Resource sapResource = variantResource.getChild(CommonConstants.SAP_CC_NODENAME)
					.getChild(CommonConstants.REGION_DETAILS_NODE_NAME);
			ValueMap valueMap = sapResource.adaptTo(ValueMap.class);
			if (valueMap.containsKey(country.toUpperCase())) {
				JsonObject countryDetails = new JsonParser().parse(valueMap.get(country.toUpperCase(), String.class))
						.getAsJsonObject();
				if (countryDetails.has("isPhantom")) {
					isPhantom = countryDetails.get("isPhantom").getAsString() ;
				}
			}
		}
		return isPhantom;
	}

	public static String getTdsCloneIdFromHp(ValueMap baseHpMap) {
		JsonArray cloneArray = getCloneArray(baseHpMap);
		return getHpAttribute(CommonConstants.TDS_CLONE_NAME, cloneArray);
	}

	public static String getSpecificityFromHp(ValueMap baseHpMap) {
		String specificity = StringUtils.EMPTY;
		if(null != baseHpMap && null != baseHpMap.get(CommonConstants.SPECIFICITY)) {
			 specificity = baseHpMap.get(CommonConstants.SPECIFICITY, String.class);
		}
		return specificity;
	}

	public static String getHpAttribute(String tdsCloneId, JsonArray cloneArray) {
		String attributeValue = StringUtils.EMPTY;
		if (null != cloneArray) {
			for (JsonElement cloneElement : cloneArray) {
				JsonObject cloneJsontObject = cloneElement.getAsJsonObject();
				attributeValue = null != cloneJsontObject.get(tdsCloneId) ? cloneJsontObject.get(tdsCloneId).getAsString() : "";
			}
		}
		return attributeValue;
	}

	public static JsonArray getCloneArray(ValueMap baseHpMap) {
		JsonArray cloneArray = null;
		if (baseHpMap.containsKey(CommonConstants.CLONE)) {
			String clone = baseHpMap.get(CommonConstants.CLONE).toString();
			cloneArray = new JsonParser().parse(clone).getAsJsonArray();
		}
		return cloneArray;
	}

	public static Resource getBaseHpResourceFromLookUp(Resource variantResource) {
		Resource baseHpResource = null;
		if (null != variantResource) {
			Resource baseResource = variantResource.getParent();
			if (null != baseResource && null != baseResource.getChild(CommonConstants.HP)) {
				baseHpResource = baseResource.getChild(CommonConstants.HP);
			}
		}
		return baseHpResource;

	}
	
	/**
	 * Gets the hp Node Resource of the basePath.
	 *
	 * @param baseResource the basePath resource
	 * @return the baseHpResource
	 */
	public static Resource getBaseHpResource(Resource baseResource) {
		Resource baseHpResource = null;
			if (null != baseResource && null != baseResource.getChild(CommonConstants.HP)) {
				baseHpResource = baseResource.getChild(CommonConstants.HP);
			}
		return baseHpResource;
	}

	/**
	 * To title case.
	 *
	 * @param word the word
	 * @return the string
	 */
	public static String toTitleCase(String word) {
		return Stream.of(word.split(" ")).map(w -> w.toUpperCase().charAt(0) + w.toLowerCase().substring(1))
				.reduce((s, s2) -> s + " " + s2).orElse("");
	}

	/**
	 * String to integer.
	 *
	 * @param stringToConvert the string to convert
	 * @return the int
	 */
	public static int stringToInteger(String stringToConvert) {
		int number = 0;
		if (StringUtils.isNotBlank(stringToConvert)) {
			try {
				number = Integer.parseInt(stringToConvert);
			} catch (NumberFormatException e) {
				number = 0;
			}

		}
		return number;
	}

	/**
	 * Total time in minutes seconds.
	 *
	 * @param milliseconds the milliseconds
	 * @return the string
	 */
	public static String totalTimeInMinutesSeconds(long milliseconds) {
		long minutes = (milliseconds / 1000) / 60;
		long seconds = (milliseconds / 1000) % 60;
		return minutes + " minutes and " + seconds + " Seconds";
	}
	
	public static String HandleRTEAnchorLink(String html, ExternalizerService externalizerService,ResourceResolver resourceResolver, String urlFormate ) throws IOException {
		Reader reader = new StringReader(html);
		HTMLEditorKit.Parser parser = new ParserDelegator();
		HashMap<String, String> linkMap = new HashMap<String, String>();


		parser.parse(reader, new HTMLEditorKit.ParserCallback() {
			public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
				if (t == HTML.Tag.A) {
					Object link = a.getAttribute(HTML.Attribute.HREF);
					if (link != null) {

						String link_value = String.valueOf(link);
						if(link_value.startsWith("/content/bdb") && !link_value.contains("language-masters") ) {
							link_value = externalizerService.getFormattedUrl(link_value.startsWith("/content")?link_value.replace(".html", ""):link_value, resourceResolver);
						}else if(link_value.startsWith("/content/bdb/language-masters/en/")) {
							link_value = link_value.replace("language-masters/en",urlFormate);
							link_value = externalizerService.getFormattedUrl(link_value.startsWith("/content")?link_value.replace(".html", ""):link_value, resourceResolver);
						}
						else if(link_value.startsWith("/content/bdb/language-masters/ko/")) {
							link_value = link_value.replace("language-masters/ko",urlFormate);
							link_value = externalizerService.getFormattedUrl(link_value.startsWith("/content")?link_value.replace(".html", ""):link_value, resourceResolver);
						}
						else if(link_value.startsWith("/content/bdb/language-masters/ja/")) {
							link_value = link_value.replace("language-masters/ja",urlFormate);
							link_value = externalizerService.getFormattedUrl(link_value.startsWith("/content")?link_value.replace(".html", ""):link_value, resourceResolver);
						}
						else if(link_value.startsWith("/content/bdb/language-masters/zh/")) {
							link_value = link_value.replace("language-masters/zh",urlFormate);
							link_value = externalizerService.getFormattedUrl(link_value.startsWith("/content")?link_value.replace(".html", ""):link_value, resourceResolver);
						}
						else if(link_value.startsWith("/content/bdb/language-masters/en-eu/")) {
							link_value = link_value.replace("language-masters/en-eu",urlFormate);
							link_value = externalizerService.getFormattedUrl(link_value.startsWith("/content")?link_value.replace(".html", ""):link_value, resourceResolver);
						}
						linkMap.put(StringEscapeUtils.escapeHtml4(String.valueOf(link)), link_value);
					}
				}
			}
		}, true);

		reader.close();
		for (String i : linkMap.keySet()) {
			  html = html.replace(i, linkMap.get(i));
			}
		return html;
		
		
		}
	
	public static GenericList getGenericList(ResourceResolver resourceResolver, String path) {
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		GenericList genericList = null;
		if(null != pageManager) {
		    Page listPage = pageManager.getPage(path);
		    if(null != listPage) {
			    genericList = listPage.adaptTo(GenericList.class);
		    }
		}
		return genericList;
	}
	
	/**
	 * Gets the email domain list.
	 *
	 * @param currentPage the current page
	 * @param resourceResolver the resource resolver
	 * @return the email domain list
	 */
	public static String getEmailDomainList(Page currentPage, ResourceResolver resourceResolver) {
		List<String> publicEmailDomainsList = getEmailDomains();
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();

		if (null != currentPage && null != currentPage.getAbsoluteParent(2)) {
			String path = currentPage.getPath().concat(CommonConstants.SINGLE_SLASH)
					.concat(JcrConstants.JCR_CONTENT).concat(CommonConstants.SINGLE_SLASH)
					.concat(CommonConstants.EMAIL_DOMAINS);

			Resource emailDomainResource = resourceResolver.getResource(path);
			if (emailDomainResource != null) {
				Iterator<Resource> children = emailDomainResource.listChildren();
				while (children.hasNext()) {
					Resource child = children.next();
					ValueMap childValueMap = child.getValueMap();
					if (null != childValueMap.get(CommonConstants.DOMAINS_KEY)) {
						String emailDomain = childValueMap.get(CommonConstants.DOMAINS_KEY).toString();
						publicEmailDomainsList.remove(emailDomain);
					}
				}
				return gson.toJson(publicEmailDomainsList);
			}
		}
		return gson.toJson(publicEmailDomainsList);
	}

	//If List Not Authored, Setting Default Values for FE to read
	private static List <String> getEmailDomains(){
		return new ArrayList<>(Arrays.asList("@gmail", "@outlook", "@yahoo", "@hotmail", "@rediffmail", "@mac"));
	}

	public static String getRuoFromRegionDetails(Resource hpBaseResource, String country, String catalogNumber) {
		String regulatoryStatus = StringUtils.EMPTY;
		if (null != hpBaseResource && StringUtils.isNotBlank(catalogNumber) && null != hpBaseResource.getParent() && null != hpBaseResource.getParent().getChild(catalogNumber)) {
			Resource variantResource = hpBaseResource.getParent().getChild(catalogNumber);
			Resource regionDetails = variantResource.getChild("sap-cc") != null
					? variantResource.getChild("sap-cc").getChild("region-details")
					: null;
			if (null != regionDetails) {
				ValueMap rdValueMap = regionDetails.adaptTo(ValueMap.class);
				if (rdValueMap.containsKey(country.toUpperCase())) {
					String regionStrObject = rdValueMap.get(country.toUpperCase(), String.class);
					JsonObject regionObject = new Gson().fromJson(regionStrObject, JsonObject.class);
					if (null != regionObject && null != regionObject.get(CommonConstants.REGULATORY_STATUS)
							&& StringUtils.isNotBlank(regionObject.get(CommonConstants.REGULATORY_STATUS).toString())) {
						regulatoryStatus = regionObject.get(CommonConstants.REGULATORY_STATUS).getAsString();
					}
				}
			}
		}
		return regulatoryStatus;
	}
	
	/**
	 * Gets the read service resolver.
	 *
	 * @param resolverFactory the resolver factory
	 * @return the read service resolver
	 * @throws LoginException the login exception
	 */
	public static ResourceResolver getReadServiceResolver(ResourceResolverFactory resolverFactory) throws LoginException {
		LOG.debug("Inside getReadServiceResolver of CommonHelper");
		Map<String, Object> writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				"readService");
		return resolverFactory.getServiceResourceResolver(writeServiceAuth);
	}
	
	public static String getTranslatedRegulatoryStatus(String countryAttribute,ResourceResolver resourceResolver, BDBSearchEndpointService solrConfig) {
		String translatedValue = StringUtils.EMPTY;
		Resource regulatoryStausListRes= resourceResolver.getResource(solrConfig.getivdTranslationRegionsList() + CommonConstants.JCR_CONTENT +CommonConstants.SINGLE_SLASH +CommonConstants.LIST);
		if(null != regulatoryStausListRes) {
			Iterator<Resource> listItemRes= regulatoryStausListRes.listChildren();
			while (listItemRes.hasNext()) {
				Resource nextRes = listItemRes.next();
				ValueMap itemMap = nextRes.adaptTo(ValueMap.class);
				String jcrTitle = itemMap.get(JcrConstants.JCR_TITLE, String.class);
				if(StringUtils.isNotBlank(jcrTitle) && jcrTitle.equals(countryAttribute)) {
					translatedValue = itemMap.get("value", String.class);
				}
			}
		}
		return translatedValue;
	}
	
	public static List<String> getMediasImageFromBaseHp(String media, String thumbnail){
		ArrayList<String> productImagesList = new ArrayList<String>();
		if(StringUtils.isNotEmpty(media)) {
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(media).getAsJsonArray();
			
			if(null != array) {
				for(JsonElement element : array) {
					JsonObject obj = element.getAsJsonObject();
					if(null != obj) {
						String imageName = obj.get(SolrSearchConstants.IMAGE_NAME).getAsString();
						String imagetype = obj.get(SolrSearchConstants.IMAGE_TYPE).getAsString();
						String[] iamgeTypeArray = imagetype.split(",");
						for(String imageType : iamgeTypeArray) {
							if(imageType.equals(thumbnail)) {
								productImagesList.add(imageName +".png");	
							}
						}
					}
				}
				//String[] medias = new String[list.size()];
				//medias = list.toArray(medias);
			}
		}
		return productImagesList;
		
	}

	/**
	 * If the country selected is EU then it should be same as GB ( United Kingdom )
	 * @param country
	 * @return
	 */
    public static String isCountryEU(String country) {
		if(country.equalsIgnoreCase("eu")){
			country = "gb";
		}
		return country;
    }
    
	public static String HandleRTEAnchorLinkForIcm(String html, ExternalizerService externalizerService,ResourceResolver resourceResolver, String urlFormate ) throws IOException {
		Reader reader = new StringReader(html);
		HTMLEditorKit.Parser parser = new ParserDelegator();
		HashMap<String, String> linkMap = new HashMap<String, String>();


		parser.parse(reader, new HTMLEditorKit.ParserCallback() {
			public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
				if (t == HTML.Tag.A) {
					Object link = a.getAttribute(HTML.Attribute.HREF);
					if (link != null) {

						String link_value = String.valueOf(link);
						if(link_value.startsWith("/content/bdb") && !link_value.contains("language-masters") ) {
							link_value = externalizerService.getFormattedUrl(link_value.startsWith("/content")?link_value.replace(".html", ""):link_value, resourceResolver);
						}else if(link_value.startsWith("/content/bdb/language-masters/en/")) {
							link_value = link_value.replace("language-masters/en",urlFormate);
						}
						linkMap.put(StringEscapeUtils.escapeHtml4(String.valueOf(link)), link_value);
					}
				}
			}
		}, true);

		reader.close();
		for (String i : linkMap.keySet()) {
			  html = html.replace(i, linkMap.get(i));
			}
		return html;
		
		
		}
    public static List<String> getAllCountries(ResourceResolver resourceResolver, BDBApiEndpointService bdbApiEndpointService) throws LoginException {
		ArrayList<String> countriesList = new ArrayList<>();
		if (null != bdbApiEndpointService
				&& null != resourceResolver.getResource(bdbApiEndpointService.countryDropdownEndpoint())) {
			Resource genericListResource = resourceResolver
					.getResource(bdbApiEndpointService.countryDropdownEndpoint());
			Resource listResource = resourceResolver.getResource(genericListResource.getPath()
					+ CommonConstants.JCR_CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.LIST);
			if (listResource != null && listResource.hasChildren()) {
				Iterator<Resource> items = listResource.listChildren();
				while (items.hasNext()) {
					Resource itemResource = items.next();
					ValueMap valueMap = itemResource.getValueMap();
					String value = CommonHelper.getPropertyValue(valueMap, CommonConstants.VALUE);
					if (StringUtils.isNotEmpty(value.trim())) {
						countriesList.add(value);
					}
				}
			} else {
				LOG.debug("Either Generic List does not exist or it is empty.");
			}
		}
		return countriesList;
	}
    
    /**
     * Gets the product availability in region.
     *
     * @param productId the product id
     * @param country the country
     * @param resourceResolver the resource resolver
     * @return the product availability in region
     */
    public static boolean getProductAvailabilityInRegion(String productId, String country, ResourceResolver resourceResolver) {
    	Resource variantResource = getProductFromLookUp(resourceResolver, productId, CommonConstants.MATERIAL_NUMBER);
		if (null != variantResource) {
			Resource sapResource = variantResource.getChild(CommonConstants.SAP_CC_NODENAME)
					.getChild(CommonConstants.REGION_DETAILS_NODE_NAME);
			ValueMap valueMap = sapResource.adaptTo(ValueMap.class);
			if (valueMap.containsKey(country.toUpperCase())) {
				JsonObject countryDetails = new JsonParser().parse(valueMap.get(country.toUpperCase(), String.class))
						.getAsJsonObject();
				if (countryDetails.has("derivedProductStatus") && countryDetails.has("validityStartDate")) {
					String derivedProductStatus = countryDetails.get("derivedProductStatus").getAsString();
					if (derivedProductStatus.equals("DISPLAYONLY") || derivedProductStatus.equals("PURCHASEABLE")) {
						if (checkProductValidityOnDate(countryDetails.get("validityStartDate").getAsString())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Gets the product from look up.
	 *
	 * @param resourceResolver the resource resolver
	 * @param productId the product id
	 * @param productType the product type
	 * @return the product from look up
	 */
	public static Resource getProductFromLookUp(ResourceResolver resourceResolver, String productId, String productType) {
		String lookUpPath = getLookUpPathFromProductId(productId, productType);
		if (null != resourceResolver) {
			Resource lookUpResource = resourceResolver.getResource(lookUpPath);
			if (null != lookUpResource) {
				ValueMap valueMap = lookUpResource.adaptTo(ValueMap.class);

				if (valueMap.containsKey("catalogPath")) {
					// checking if lookup path has product path in property
					String catalogPath = valueMap.get("catalogPath").toString();
					return resourceResolver.getResource(catalogPath);
				}
			}
		}
		return null;
	}

	/**
	 * Gets the look up path from product id.
	 *
	 * @param productId the product id
	 * @param productType the product type
	 * @return the look up path from product id
	 */
	public static String getLookUpPathFromProductId(String productId, String productType) {
		String basePath = StringUtils.EMPTY;
		if (productType.equals(CommonConstants.MATERIAL_NUMBER)) {
			basePath = "/content/commerce/lookup/variant/";
		} else if (productType.equals(CommonConstants.BASE_MATERIAL_NUMBER)) {
			basePath = "/content/commerce/lookup/baseProduct/";
			productId = productId.replace("_base", "");
		}
		return getLookupPath(productId.toLowerCase(), basePath);
	}

	/**
	 * Gets the lookup path.
	 *
	 * @param productId the product id
	 * @param basePath the base path
	 * @return the lookup path
	 */
	public static String getLookupPath(String productId, String basePath) {
		StringBuilder builder = new StringBuilder(basePath);
		String finalPath;
		String temp = productId;
		/** Converting the lookup path in the below formats:
		* /content/commerce/lookup/variant/cyt-af/cyt-at4c
		* /content/commerce/lookup/variant/554f/554294 */
		if(temp.length() > 3) {
			builder.append(temp, 0, temp.length()-3).append("f").append(CommonConstants.SINGLE_SLASH);	
		} else {
			temp = "";
		}
		builder.append(productId);
		finalPath = builder.toString();
		return finalPath;
	}
    
	/**
	 * Gets the doc related to variants.
	 *
	 * @param resourceResolver the resource resolver
	 * @param variantList the variant list
	 * @return the doc related to variants
	 * @throws RepositoryException the repository exception
	 */
	public static List<String> getDocRelatedToVariants(ResourceResolver resourceResolver, List<String> variantList, BDBWorkflowConfigService bDBWorkflowConfigService) throws RepositoryException {
		LOG.debug("Entry getDocRelatedToVariants of CommonHelper");
		Map<String, String> uniqueDamCommerceBasePath = new HashMap<>();
		Set<String> uniqueDocPath = new HashSet<>();
		String matNumber;
		String pdfBaseDamPath;
		String pdfName;
		String baseDamPath;
		String variantBasePath;
		Session session = resourceResolver.adaptTo(Session.class);
		for (String variantPath : variantList) {
			matNumber = variantPath.substring(variantPath.lastIndexOf(CommonConstants.SINGLE_SLASH) + 1,
					variantPath.length());
			baseDamPath = variantPath.substring(0, variantPath.lastIndexOf(CommonConstants.SINGLE_SLASH))
					.replace(bDBWorkflowConfigService.getVarCommerceBasePath(), bDBWorkflowConfigService.getDamAssetBasePath());
			pdfBaseDamPath = baseDamPath.concat(CommonConstants.FORWARD_SLASH_PDF);
			variantBasePath = variantPath.substring(0, variantPath.lastIndexOf(CommonConstants.SINGLE_SLASH));
			pdfName = pdfBaseDamPath + CommonConstants.SINGLE_SLASH + matNumber + CommonConstants.DOT_PDF;
			
			if (session.nodeExists(pdfName)) {
				uniqueDocPath.add(pdfName);
			}
			uniqueDamCommerceBasePath.put(variantBasePath, pdfBaseDamPath);
		}
		updateUniqueDocPaths(resourceResolver, session, uniqueDocPath, uniqueDamCommerceBasePath);
		LOG.debug("Exit getDocRelatedToVariants of CommonHelper");
		return new ArrayList<>(uniqueDocPath);

	}

	/**
	 * Update unique doc paths.
	 *
	 * @param resourceResolver the resource resolver
	 * @param session the session
	 * @param uniqueDocPath the unique doc path
	 * @param uniqueDamCommerceBasePath the unique dam commerce base path
	 * @throws RepositoryException the repository exception
	 */
	public static void updateUniqueDocPaths(ResourceResolver resourceResolver, Session session,
			Set<String> uniqueDocPath, Map<String, String> uniqueDamCommerceBasePath) throws RepositoryException {
		String key;
		String value;
		String[] docPartIDs;
		String pfdName;
		LOG.debug("Entry updateUniqueDocPaths of CommonHelper");
		for (Map.Entry<String, String> entry : uniqueDamCommerceBasePath.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			docPartIDs = getDocPartIDs(resourceResolver, key);
			if (null != docPartIDs) {
				for (String docPartID : docPartIDs) {
					pfdName = value.concat(CommonConstants.SINGLE_SLASH).concat(docPartID.trim()).concat(CommonConstants.DOT_PDF);
					if (session.nodeExists(pfdName)) {
						uniqueDocPath.add(pfdName);
					}
				}
			}
		}
		LOG.debug("Exit updateUniqueDocPaths of CommonHelper");
	}
		

	/**
	 * Get the product title details from the selector
	 * @param request
	 * @return
	 */
	public static String getProductTitleDetails(SlingHttpServletRequest request) {
		RequestPathInfo requestPath = request.getRequestPathInfo();
		LOG.debug(" requestPath {}", requestPath);
		LOG.debug(" requestPath {}" + requestPath);
		String productTitleDetails = null;
		if (requestPath.getSelectors().length != 0) {
			String[] selectors = requestPath.getSelectors();
			LOG.debug(" selectors {}", selectors);
			LOG.debug(" selectors {}" + selectors.length);
			if (selectors.length == 2) {
				productTitleDetails = selectors[0];
			}
		}
		LOG.debug(" Product Varient {}", productTitleDetails);
		return productTitleDetails;
	}

	/**
	 * Get the Region And Language From Country List
	 * 
	 * @param country
	 * @param resourceResolver
	 * @param solrConfig
	 * @return
	 */
	public static String getRegionAndLanFromCountryList(String country, ResourceResolver resourceResolver,
			BDBSearchEndpointService solrConfig) {
		String regionPath = StringUtils.EMPTY;
		LOG.debug("Entry getRegionAndLanFromCountryList of CommonHelper");
		if (null != resourceResolver.getResource(solrConfig.countryToRegionAndLanguge())) {
			Resource countryRegionsListRes = resourceResolver.getResource(solrConfig.countryToRegionAndLanguge()
					+ CommonConstants.JCR_CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.LIST);
			if (null != countryRegionsListRes) {
				Iterator<Resource> listItemRes = countryRegionsListRes.listChildren();
				while (listItemRes.hasNext()) {
					Resource nextRes = listItemRes.next();
					ValueMap itemMap = nextRes.adaptTo(ValueMap.class);
					String listCountryValue = itemMap.get(CommonConstants.VALUE, String.class);
					if (StringUtils.isNotBlank(listCountryValue) && listCountryValue.equalsIgnoreCase(country)) {
						regionPath = itemMap.get(JcrConstants.JCR_TITLE, String.class);
					}
				}
			}
		}
		LOG.debug("Exit getRegionAndLanFromCountryList of CommonHelper");
		return regionPath;
	}

	/**
	 * Gets the doc part I ds.
	 *
	 * @param resolver        the resolver
	 * @param productBasePath the product base path
	 * @return the doc part I ds
	 */
	public static String[] getDocPartIDs(ResourceResolver resolver, String productBasePath) {
		LOG.debug("Entry getDocPartIDs of CommonHelper");
		Resource basePathResource = resolver.getResource(productBasePath);
		String[] docPartIDs = null;
		if (null != basePathResource) {
			Resource getHpResource = basePathResource.getChild(CommonConstants.HP_NODE);
			if (null != getHpResource) {
				ValueMap valueMap = getHpResource.adaptTo(ValueMap.class);
				docPartIDs = getDocPartIDsFromMap(valueMap);
			}
		}
		LOG.debug("Exit getDocPartIDs of CommonHelper");
		return docPartIDs;
	}

	/**
	 * Gets the docPartIDs from map.
	 *
	 * @param valueMap the value map
	 * @return the docPartIDs from map
	 */
	public static String[] getDocPartIDsFromMap(ValueMap valueMap) {
		String[] docPartIDs = null;
		if (valueMap.containsKey(CommonConstants.DOC_PART_IDS_CAP)) {
			String docPartIdsValue = valueMap.get(CommonConstants.DOC_PART_IDS_CAP, String.class);
			if (StringUtils.isNotBlank(docPartIdsValue)) {
				docPartIDs = docPartIdsValue.split("\\s*[|]\\s*");
			}

		}
		return docPartIDs;
	}

	/**
	 * Checking if the PDF is available in docPartIDs.
	 *
	 * @param docPartIdsValue the doc part ids value
	 * @param pdfName         the pdf name
	 * @return true, if successful
	 */
	public static boolean availableInDocpartIds(String docPartIdsValue, String pdfName) {
		List<String> docpartIds = Arrays.asList(docPartIdsValue.split("\\s*[|]\\s*"));
		return docpartIds.contains(pdfName);
	}

	/**
	 * Convert array to set.
	 *
	 * @param array the array
	 * @return the sets the
	 */
	public static Set<String> convertArrayToSet(String[] array) {
		// Create an empty Set
		Set<String> set = new HashSet<>();
		// Iterate through the array
		for (String t : array) {
			// Add each element into the set
			set.add(t + ".pdf");
		}
		// Return the converted Set
		return set;
	}

	/**
	 * Gets the pdf resources.
	 *
	 * @param basePdfPath    the base pdf path
	 * @param pdfType        the pdf type
	 * @param resolver       the resolver
	 * @param locale         the locale
	 * @param fileNamesOfPdf the file names of pdf
	 * @return the pdf resources
	 */
	public static List<String> getPdfResources(String basePdfPath, String pdfType, ResourceResolver resolver,
			String locale, Set<String> fileNamesOfPdf, String emeaCountriesListPath) {
		ArrayList<String> pdfList = new ArrayList<>();
		LOG.debug("Entry getPdfResources of CommonHelper");
		locale = null != locale ? (CommonConstants.REGION_TAG + locale.toLowerCase()) : StringUtils.EMPTY;
		if (null != basePdfPath && null != pdfType && null != resolver) {
			Resource baseRes = resolver.getResource(basePdfPath);
			if (null != baseRes) {
				Iterator<Resource> listPdf = baseRes.listChildren();
				while (listPdf.hasNext()) {
					prepareListOfAvailableEligibleDocs(pdfType, locale, fileNamesOfPdf, pdfList, listPdf, resolver, emeaCountriesListPath);
				}
			}
		}
		LOG.debug("pdfList : {}", pdfList);
		LOG.debug("Exit getPdfResources of CommonHelper");
		return pdfList;
	}
	/**
	 * Gets the pdf resources.
	 *
	 * @param basePdfPath    the base pdf path
	 * @param pdfType        the pdf type
	 * @param resolver       the resolver
	 * @param locale         the locale
	 * @param fileNamesOfPdf the file names of pdf
	 * @return the pdf resources
	 */
	public static List<Resource> getMultiLangPdfResources(String basePdfPath, String pdfType, ResourceResolver resolver,
			String locale,  String emeaCountriesListPath,String catalogNumber) {
		ArrayList<Resource> pdfList = new ArrayList<>();
		LOG.debug("Entry getPdfResources of CommonHelper");
		locale = null != locale ? (CommonConstants.REGION_TAG + locale.toLowerCase()) : StringUtils.EMPTY;
		if (null != basePdfPath && null != pdfType && null != resolver) {
			Resource baseRes = resolver.getResource(basePdfPath);
			if (null != baseRes) {
				Iterator<Resource> listPdf = baseRes.listChildren();
				while (listPdf.hasNext()) {
					
					prepareListOfAvailableEligibleMultiLangDocs(pdfType, locale,  pdfList, listPdf, resolver, emeaCountriesListPath,catalogNumber);
				}
			}
		}
		LOG.debug("pdfList : {}", pdfList);
		LOG.debug("Exit getPdfResources of CommonHelper");
		return pdfList;
	}
	
	/**
	 * Prepare list of available eligible docs.
	 *
	 * @param pdfType        the pdf type
	 * @param locale         the locale
	 * @param fileNamesOfPdf the file names of pdf
	 * @param pdfList        the pdf list
	 * @param listPdf        the list pdf
	 */
	public static void prepareListOfAvailableEligibleDocs(String pdfType, String locale, Set<String> fileNamesOfPdf,
			List<String> pdfList, Iterator<Resource> listPdf, ResourceResolver resolver, String emeaCountriesListPath) {
		LOG.debug("Entry prepareListOfAvailableEligibleDocs of CommonHelper");
		Resource pdfRes = listPdf.next();
		String pdfName = pdfRes.getName();
		Resource res = pdfRes.getChild(CommonConstants.METADATA_PATH_AS_CHILD);
		if (null != res) {
			ValueMap vm = res.adaptTo(ValueMap.class);
			if (vm.containsKey(CommonConstants.PDF_DOC_REGION) && vm.containsKey(CommonConstants.PDF_DOC_TYPE)) {
				addToListIfAvailable(pdfType, locale, fileNamesOfPdf, pdfList, pdfRes, pdfName, vm, resolver, emeaCountriesListPath);
			}
		}
		LOG.debug("Exit prepareListOfAvailableEligibleDocs of CommonHelper");
	}
	
	
	/**
	 * Prepare list of available eligible docs.
	 *
	 * @param pdfType        the pdf type
	 * @param locale         the locale
	 * @param fileNamesOfPdf the file names of pdf
	 * @param pdfList        the pdf list
	 * @param listPdf        the list pdf
	 */
	public static void prepareListOfAvailableEligibleMultiLangDocs(String pdfType, String locale, 
			List<Resource> pdfList, Iterator<Resource> listPdf, ResourceResolver resolver, String emeaCountriesListPath,String catalogNumber) {
		LOG.debug("Entry prepareListOfAvailableEligibleDocs of CommonHelper");
		Resource pdfRes = listPdf.next();
		String pdfName = pdfRes.getName();
		if(pdfName.startsWith(catalogNumber + "_")) {
		Resource res = pdfRes.getChild(CommonConstants.METADATA_PATH_AS_CHILD);
		if (null != res) {
			ValueMap vm = res.adaptTo(ValueMap.class);
			if (vm.containsKey(CommonConstants.PDF_DOC_REGION) && vm.containsKey(CommonConstants.PDF_DOC_TYPE)) {
				addToListIfAvailableMultiLang(pdfType, locale,  pdfList, pdfRes, pdfName, vm, resolver, emeaCountriesListPath);
			}
		}
		}
		LOG.debug("Exit prepareListOfAvailableEligibleDocs of CommonHelper");
	}
	/**
	 * Prepare list of apac and apac(english) region.
	 *
	 * @param region        the region
	 * return apac region countries list or apac(english) region countries list.
	 */
	public static ArrayList<String>getApacTagRegions(ResourceResolver resolver, String region) {
		String CONTENT_APAC_PATH = "/content/bdb/apac/";
		String CONTENT_CN_PATH = "/content/bdb/cn/";
		ArrayList<String>apac_en_RegionList = new ArrayList<String>();
		ArrayList<String>apacRegionList = new ArrayList<String>();
		ArrayList<String>apacCountriesList = new ArrayList<String>();
		
		String countryListPath = "/content/bdb/na/us/en-us/country-selector/jcr:content/root/countryselector/regionmultifield";
		Resource countryListResource = resolver.getResource(countryListPath);
		if (null != countryListResource && countryListResource.hasChildren()) {
			for (Resource childResource : countryListResource.getChildren()) {
				if (null != childResource && childResource.hasChildren()) {
					Resource countryResource = childResource.getChild("countrymultifield");
					if (null != countryResource && countryResource.hasChildren()) {
						for (Resource itemResource : countryResource.getChildren()) {
							ValueMap vm = itemResource.adaptTo(ValueMap.class);
							String countryUrl = vm.get("urlCountry", StringUtils.EMPTY);
							String[] urlSplits = countryUrl.split("/");
							String countryCode = urlSplits[urlSplits.length - 1].split("-")[1];
							String countryLang = urlSplits[urlSplits.length - 1].split("-")[0];
							if(StringUtils.contains(countryUrl, CONTENT_APAC_PATH) && StringUtils.contains(countryLang, CommonConstants.CONST_DEFAULT_LANGUAGE) ) {
								apac_en_RegionList.add(countryCode);
							
							}
							if(StringUtils.contains(countryUrl, CONTENT_APAC_PATH) || StringUtils.contains(countryUrl, CONTENT_CN_PATH)) {
								apacRegionList.add(countryCode);
							}
						}
					}
				}
			}
		}
		if(region.equals(CommonConstants.APAC_EN_REGION_TAG)) {
			apacCountriesList.addAll(apac_en_RegionList);
		}
		else {
			apacCountriesList.addAll(apacRegionList);
		}
		
		return apacCountriesList;
	}
	

	/**
	 * Adds the to list if available.
	 *
	 * @param pdfType        the pdf type
	 * @param locale         the locale
	 * @param fileNamesOfPdf the file names of pdf
	 * @param pdfList        the pdf list
	 * @param pdfRes         the pdf res
	 * @param pdfName        the pdf name
	 * @param vm             the vm
	 */
	public static void addToListIfAvailable(String pdfType, String locale, Set<String> fileNamesOfPdf,
			List<String> pdfList, Resource pdfRes, String pdfName, ValueMap vm, ResourceResolver resolver, String emeaCountriesListPath) {
		LOG.debug("Entry addToListIfAvailable of CommonHelper");
		String[] docRegion = vm.get(CommonConstants.PDF_DOC_REGION, String[].class);
		String docType = vm.get(CommonConstants.PDF_DOC_TYPE).toString().toLowerCase().replace(CommonConstants.SPACE,
				StringUtils.EMPTY);
		pdfType = pdfType.toLowerCase().replace(CommonConstants.SPACE, StringUtils.EMPTY);
		
		if (pdfType.equals(docType)) {
			boolean matchedRegion = false;
			boolean containsPdfName = false;
			for (String region : docRegion) {
				if (region.equals("bdb:regions/uk")) {
					region = "bdb:regions/gb";
				}
				Resource countryListResource = resolver.getResource(emeaCountriesListPath);
				if(region.equals("bdb:regions/emea") && null != countryListResource) {
		            Iterator<Resource> items = countryListResource.listChildren();
		            while (items.hasNext()) {
		                Resource countryResource = items.next();
		                if (null != countryResource) {
		                    ValueMap properties = countryResource.getValueMap();
		                    String countryLabel = CommonHelper.getPropertyValue(properties, JcrConstants.JCR_TITLE);
		                    String countryId = CommonHelper.getPropertyValue(properties, "value").toLowerCase();
		                    String countryTagName = "bdb:regions/"+countryId;
		                    if (StringUtils.isNotBlank(countryLabel) && StringUtils.isNotBlank(countryId)) {
		                    	if (locale.equals(countryTagName) && region.equals("bdb:regions/emea")) {
		        					matchedRegion = true;
		    		                break;
		        				}
		                    }
		                }
		            }
				}
				if(region.equals(CommonConstants.APAC_EN_REGION_TAG) || region.equals(CommonConstants.APAC_REGION_TAG)) {
					ArrayList<String>apacCountriesList = getApacTagRegions(resolver,region);
                    if(apacCountriesList.size()>0) {
                    	for(String countryCode:apacCountriesList) {
                    		String countryTagName = CommonConstants.REGION_TAG + countryCode;
	                    	if (StringUtils.isNotBlank(countryCode)) {
		                    	if (locale.equals(countryTagName)) {
		        					matchedRegion = true;
		    		                break;
		        				}
		                    }
	                    }
                    }
				}
				
				if (region.equals(locale) || region.equals("bdb:regions/global")) {
					matchedRegion = true;
					break;
				}
				
			}
			LOG.debug("fileNamesOfPdf of addToListIfAvailable method : {}", fileNamesOfPdf);
			LOG.debug("pdfName of addToListIfAvailable method : {}", pdfName);
			// Iterating the fileNamesOfPdf Set to check if the pdfName exist in the set
			// irrespective of the case. If the pdfName exists in set
			// then assigning true to the containsPdfName variable.
			Iterator<String> iterator = fileNamesOfPdf.iterator();
			while (iterator.hasNext()) {
				String element = iterator.next();
				if (element.equalsIgnoreCase(pdfName)) {
					containsPdfName = true;
					break;
				}
			}
			if (matchedRegion && containsPdfName) {
				pdfList.add(pdfRes.getPath());
			}

		}
		LOG.debug("pdfList of addToListIfAvailable of CommonHelper : {}",pdfList );
		LOG.debug("Exit addToListIfAvailable of CommonHelper");
	}
	
	/**
	 * Adds the to list if available.
	 *
	 * @param pdfType        the pdf type
	 * @param locale         the locale
	 * @param fileNamesOfPdf the file names of pdf
	 * @param pdfList        the pdf list
	 * @param pdfRes         the pdf res
	 * @param pdfName        the pdf name
	 * @param vm             the vm
	 */
	public static void addToListIfAvailableMultiLang(String pdfType, String locale, 
			List<Resource> pdfList, Resource pdfRes, String pdfName, ValueMap vm, ResourceResolver resolver, String emeaCountriesListPath) {
		LOG.debug("Entry addToListIfAvailable of CommonHelper");
		String[] docRegion = vm.get(CommonConstants.PDF_DOC_REGION, String[].class);
		String docType = vm.get(CommonConstants.PDF_DOC_TYPE).toString().toLowerCase().replace(CommonConstants.SPACE,
				StringUtils.EMPTY);
		pdfType = pdfType.toLowerCase().replace(CommonConstants.SPACE, StringUtils.EMPTY);
		
		if (pdfType.equals(docType)) {
			boolean matchedRegion = false;
			//boolean containsPdfName = false;
			for (String region : docRegion) {
				if (region.equals("bdb:regions/uk")) {
					region = "bdb:regions/gb";
				}
				Resource countryListResource = resolver.getResource(emeaCountriesListPath);
				if(region.equals("bdb:regions/emea") && null != countryListResource) {
		            Iterator<Resource> items = countryListResource.listChildren();
		            while (items.hasNext()) {
		                Resource countryResource = items.next();
		                if (null != countryResource) {
		                    ValueMap properties = countryResource.getValueMap();
		                    String countryLabel = CommonHelper.getPropertyValue(properties, JcrConstants.JCR_TITLE);
		                    String countryId = CommonHelper.getPropertyValue(properties, "value").toLowerCase();
		                    String countryTagName = "bdb:regions/"+countryId;
		                    if (StringUtils.isNotBlank(countryLabel) && StringUtils.isNotBlank(countryId)) {
		                    	if (locale.equals(countryTagName) && region.equals("bdb:regions/emea")) {
		        					matchedRegion = true;
		    		                break;
		        				}
		                    }
		                }
		            }
				}
				if(region.equals(CommonConstants.APAC_EN_REGION_TAG) || region.equals(CommonConstants.APAC_REGION_TAG)) {
					ArrayList<String>apacCountriesList = getApacTagRegions(resolver,region);
                    if(apacCountriesList.size()>0) {
                    	for(String countryCode:apacCountriesList) {
                    		String countryTagName = CommonConstants.REGION_TAG + countryCode;
	                    	if (StringUtils.isNotBlank(countryCode)) {
		                    	if (locale.equals(countryTagName)) {
		        					matchedRegion = true;
		    		                break;
		        				}
		                    }
	                    }
                    }
				}
				
				if (region.equals(locale) || region.equals("bdb:regions/global")) {
					matchedRegion = true;
					break;
				}
				
			}

			LOG.debug("pdfName of addToListIfAvailable method : {}", pdfName);
			/*
			 * // Iterating the fileNamesOfPdf Set to check if the pdfName exist in the set
			 * // irrespective of the case. If the pdfName exists in set // then assigning
			 * true to the containsPdfName variable. Iterator<String> iterator =
			 * fileNamesOfPdf.iterator(); while (iterator.hasNext()) { String element =
			 * iterator.next(); if (element.equalsIgnoreCase(pdfName)) { containsPdfName =
			 * true; break; } }
			 */
			if (matchedRegion) {
				pdfList.add(pdfRes);
			}

		}
		LOG.debug("pdfList of addToListIfAvailable of CommonHelper : {}",pdfList );
		LOG.debug("Exit addToListIfAvailable of CommonHelper");
	}

	/**
	 * Gets the preferred pdf resource on the basis of the priority like if two tds are available then most suitable should be returned.
	 *
	 * @param resourceResolver the resource resolver
	 * @param allPdfPaths      the all pdf paths
	 * @param locale           the locale
	 * @return the preferred pdf res
	 */
	public static PrefferedPdfInfo getPreferredPdfRes(ResourceResolver resourceResolver, List<String> allPdfPaths,
			String locale) {
		PrefferedPdfInfo prefferedPdfInfo = null;
		if (CollectionUtils.isNotEmpty(allPdfPaths)) {
			return getPreferredPdfResPath(resourceResolver, allPdfPaths, locale);
		}
		return prefferedPdfInfo;
	}

	/**
	 * Gets the preferred pdf res path on the basis of global tag availability and number of countries its tagged to.
	 *
	 * @param resourceResolver the resource resolver
	 * @param allPdfPaths      the all pdf paths
	 * @param locale           the locale
	 * @return the preferred pdf res path
	 */
	public static PrefferedPdfInfo getPreferredPdfResPath(ResourceResolver resourceResolver, List<String> allPdfPaths,
			String locale) {
		LOG.debug("Entry getPreferredPdfResPath of CommonHelper");
		PrefferedPdfInfo prefferedPdfInfo = new PrefferedPdfInfo();
		String preferredPdfResPath = allPdfPaths.get(0);
		int countryCount = getCountryCount(resourceResolver, preferredPdfResPath);
		int tempCount;
		String tempPreferredPdfResPath;
		for (int i = 1; i < allPdfPaths.size(); i++) {
			tempPreferredPdfResPath = allPdfPaths.get(i);
			tempCount = getCountryCount(resourceResolver, tempPreferredPdfResPath);
			if (chooseSuitableFromTwo(resourceResolver, preferredPdfResPath, tempPreferredPdfResPath, countryCount,
					tempCount, locale)) {
				preferredPdfResPath = tempPreferredPdfResPath;
				countryCount = tempCount;
			}
		}
		prefferedPdfInfo.setPreferredPdfResPath(preferredPdfResPath);
		if (countryCount == 28) {
			prefferedPdfInfo.setGlobal(true);
		} else if (countryCount < 28) {
			prefferedPdfInfo.setGlobal(false);
		} else {
			prefferedPdfInfo.setGlobal(!containsRegionTag(resourceResolver, preferredPdfResPath, locale));
		}
		LOG.debug("Preffered Pdf Info", prefferedPdfInfo);
		LOG.debug("Exit getPreferredPdfResPath of CommonHelper");
		return prefferedPdfInfo;
	}

	/**
	 * Choose suitable from two.
	 *
	 * @param resourceResolver    the resource resolver
	 * @param firstResourcePath   the first resource path
	 * @param secondResourcePath  the second resource path
	 * @param firstResourceCount  the first resource count
	 * @param secondResourceCount the second resource count
	 * @param locale              the locale
	 * @return true, if successful
	 */
	public static boolean chooseSuitableFromTwo(ResourceResolver resourceResolver, String firstResourcePath,
			String secondResourcePath, int firstResourceCount, int secondResourceCount, String locale) {
		LOG.debug("Entry chooseSuitableFromTwo of CommonHelper");
		boolean isSuitable = false;
		boolean containsLocaleTagFirstRes = containsRegionTag(resourceResolver, firstResourcePath, locale);
		boolean containsLocaleTagSecondRes = containsRegionTag(resourceResolver, secondResourcePath, locale);
		if ((containsLocaleTagSecondRes && containsLocaleTagFirstRes && (firstResourceCount > secondResourceCount))
				|| (containsLocaleTagSecondRes && (!containsLocaleTagFirstRes)) || (!containsLocaleTagSecondRes
						&& !containsLocaleTagFirstRes && firstResourceCount > secondResourceCount)) {
			isSuitable = true;
		}
		LOG.debug("isSuitable", isSuitable);
		LOG.debug("Exit chooseSuitableFromTwo of CommonHelper");
		return isSuitable;
	}

	/**
	 * Contains region tag.
	 *
	 * @param resourceResolver the resource resolver
	 * @param pdfPath          the pdf path
	 * @param locale           the locale
	 * @return true, if successful
	 */
	public static boolean containsRegionTag(ResourceResolver resourceResolver, String pdfPath, String locale) {
		LOG.debug("Entry containsRegionTag of CommonHelper");
		Resource pdfResource = resourceResolver.getResource(pdfPath);
		Resource pdfMetaDataResource = pdfResource.getChild(CommonConstants.METADATA_PATH_AS_CHILD);
		ValueMap vm = pdfMetaDataResource.adaptTo(ValueMap.class);
		locale = null != locale ? (CommonConstants.REGION_TAG + locale.toLowerCase()) : StringUtils.EMPTY;
		String[] docRegions = vm.get(CommonConstants.PDF_DOC_REGION, String[].class);
		boolean countryTagAvailable = false;
		for (String docRegion : docRegions) {
			if (StringUtils.equalsIgnoreCase(locale, docRegion)) {
				countryTagAvailable = true;
			}
		}
		LOG.debug("countryTagAvailable", countryTagAvailable);
		LOG.debug("Exit containsRegionTag of CommonHelper");
		return countryTagAvailable;
	}

	/**
	 * Gets the country count.
	 *
	 * @param resourceResolver the resource resolver
	 * @param pdfPath          the pdf path
	 * @return the country count
	 */
	public static int getCountryCount(ResourceResolver resourceResolver, String pdfPath) {
		LOG.debug("Entry getCountryCount of CommonHelper");
		int countryCount = 0;
		Resource pdfResource = resourceResolver.getResource(pdfPath);
		Resource pdfMetaDataResource = pdfResource.getChild(CommonConstants.METADATA_PATH_AS_CHILD);
		ValueMap vm = pdfMetaDataResource.adaptTo(ValueMap.class);
		String[] docRegions = vm.get(CommonConstants.PDF_DOC_REGION, String[].class);
		for (String docRegion : docRegions) {
			if (StringUtils.equalsIgnoreCase("bdb:regions/global", docRegion)) {
				countryCount += 28;
			} else {
				countryCount += 1;
			}
		}
		LOG.debug("Exit getCountryCount of CommonHelper");
		return countryCount;
	}

	/**
	 * Checks if is doc type matching.
	 *
	 * @param pdfType  the pdf type
	 * @param valueMap the value map
	 * @return true, if is doc type matching
	 */
	public static boolean isDocTypeMatching(String pdfType, ValueMap valueMap) {
		LOG.debug("Entry isDocTypeMatching of CommonHelper");
		String docType = valueMap.get(CommonConstants.PDF_DOC_TYPE, StringUtils.EMPTY);
		if (StringUtils.isNotBlank(docType)) {
			docType = docType.toLowerCase().replace(CommonConstants.SPACE, StringUtils.EMPTY);
			pdfType = pdfType.toLowerCase().replace(CommonConstants.SPACE, StringUtils.EMPTY);
			return StringUtils.equals(docType, pdfType);
		}
		LOG.debug("Exit isDocTypeMatching of CommonHelper");
		return false;
	}

	/**
	 * Activates the respective timezone as per country.
	 * @param countrySwitch - The Country name
	 * @param date          - The String date
	 * @return date         - The converted date
	 */
	public static String getZonedTime(String countrySwitch, String date) {
		if (countrySwitch != null && date != null) {
			Boolean isDayLightSaving = false;
			switch (countrySwitch) {
				case "ca":
					date = convertDate("Canada/Eastern", date) + "_EST";
					break;
				case "us":
					isDayLightSaving = checkDayLightSaving("US/Pacific");
					if(isDayLightSaving) {
						date = date + "[US/Pacific]" + "_PDT";
					} else {
						date = date + "[US/Pacific]" + "_PST";
					}
					break;
				case "in":
					date = convertDate("Asia/Calcutta", date) + "_IST";
					//date = date + "[Asia/Calcutta]" + "_IST";
					break;
				case "cn":
					date = convertDate("Asia/Hong_Kong", date) + "_HKT";
					break;
				case "jp":
					date = convertDate("Asia/Tokyo", date) + "_JST";
					break;
				case "kr":
					date = convertDate("Asia/Seoul", date) + "_KST";
					break;
				case "sg":
					date = convertDate("Asia/Singapore", date) + "_SGT";
					break;
				case "tw":
					date = convertDate("Asia/Taipei", date) + "_CST";
					break;
				case "au":
					date = convertDate("Australia/Sydney", date) + "_AEDT";
					break;
				case "nz":
					date = convertDate("Pacific/Auckland", date) + "_NZDT";
					break;
				case "br":
					date = convertDate("Brazil/East", date) + "_BRT";
					break;
				case "eu":
					date = convertDate("Africa/Johannesburg", date) + "_SAST";
					break;
				default:
					if(countrySwitch.equals("fi")) {
						date = convertDate("Europe/Helsinki", date) + "_CEST";
					} else if (countrySwitch.equals("gb") || countrySwitch.equals("ie") || countrySwitch.equals("pt")){
						date = convertDate("Europe/Dublin", date) + "_CEST";
					} else {
						date = convertDate("Europe/Berlin", date) + "_CEST";
					}
			}
		}
		return date;
	}

	/**
	 * Converts the Date&Time as per timezone.
	 * @param zone
	 * @param bannerDate
	 * @return countryDate
	 */
	public static String convertDate(String zone, String bannerDate) {
		String countryDate = StringUtils.EMPTY;
		SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		stf.setTimeZone(TimeZone .getTimeZone("PST"));
		Date countryTime;
		try {
			countryTime = stf.parse(bannerDate);
			Instant instant = countryTime.toInstant();
			ZoneId zoneId = ZoneId.of(zone);
			ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
			countryDate = zonedDateTime.toString();
		} catch (ParseException e) {
			LOG.error(e.getMessage());
		}
		return countryDate;
	}
	
	/**
	 * 
	 * @param zone
	 * @return
	 */
	private static Boolean checkDayLightSaving(String zone) {
		  TimeZone tz = TimeZone.getTimeZone(zone); 
		  boolean inDs = tz.inDaylightTime(new Date()); 
		  return inDs;
	}
	
	/**
	 * Activates the respective timezone as per country.
	 * @param countrySwitch - The Country name
	 * @return date         - The converted date
	 */
	public static String getRegionDateTime(String country) {
		String todaysDateAndTime = null;
		if (country != null) {
			switch (country) {
				case "ca":
					todaysDateAndTime = getRegionCurrentDateAndTime("Canada/Eastern");
					break;
				case "us":
					todaysDateAndTime = getRegionCurrentDateAndTime("US/Pacific");
					break;
				case "in":
					todaysDateAndTime = getRegionCurrentDateAndTime("Asia/Calcutta");
					break;
				case "cn":
					todaysDateAndTime = getRegionCurrentDateAndTime("Asia/Hong_Kong");
					break;
				case "jp":
					todaysDateAndTime = getRegionCurrentDateAndTime("Asia/Tokyo");
					break;
				case "kr":
					todaysDateAndTime = getRegionCurrentDateAndTime("Asia/Seoul");
					break;
				case "sg":
					todaysDateAndTime = getRegionCurrentDateAndTime("Asia/Singapore");
					break;
				case "tw":
					todaysDateAndTime = getRegionCurrentDateAndTime("Asia/Taipei");
					break;
				case "au":
					todaysDateAndTime = getRegionCurrentDateAndTime("Australia/Sydney");
					break;
				case "nz":
					todaysDateAndTime = getRegionCurrentDateAndTime("Pacific/Auckland");
					break;
				case "br":
					todaysDateAndTime = getRegionCurrentDateAndTime("Brazil/East");
					break;
				case "eu":
					todaysDateAndTime = getRegionCurrentDateAndTime("Africa/Johannesburg");
					break;
				default:
					todaysDateAndTime = getRegionCurrentDateAndTime("Europe/Athens");
			}
		}
		return todaysDateAndTime;
	}
	
	/**
	 * 
	 * @param zone
	 * @return date time
	 */
	private static String getRegionCurrentDateAndTime(String zone) {
		  ZoneId zoneId = ZoneId.of(zone);
          ZonedDateTime lt = ZonedDateTime.now(zoneId);
		  return lt.toString();
	}
	
	/**
	 * 
	 * @param redirectUrl
	 * @param countryCode
	 * @return boolean
	 */
	public static boolean isSameCountryUrl(String redirectUrl, String countryCode) {
		Boolean isSameCountry = false;
		if(StringUtils.isNotEmpty(redirectUrl) && StringUtils.isNotEmpty(countryCode)) {
			countryCode = "-"+countryCode.toLowerCase();
			String[] stringArray = redirectUrl.split("bdbiosciences.com/");
			String contentUrl = "";
			if(StringUtils.isNotEmpty(stringArray[1])) {
				contentUrl = stringArray[1];
				LOG.debug("content url in isSameCountryUrl : "+stringArray[1]);
				if(contentUrl.contains("/")) {
					String countryDetails = contentUrl.split("\\/")[0];
					isSameCountry = countryDetails.contains(countryCode) ? true : false;
				}
			}
		}
		return isSameCountry;
	}
	
	/**
	 * 
	 * @param materialNo
	 * @param country
	 * @param resourceResolver
	 * @return hpNodeResource
	 */
	public static Resource getHpNodeResource(String materialNo, String country, ResourceResolver resourceResolver, SolrClient server) {
		LOG.debug("Inside getHpNodeResource ------------->{}",materialNo);
		String hpNodePath;
		Resource resource = null;
		if (StringUtils.isNotBlank(materialNo)) {
			QueryResponse queryResponse;
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.addFilterQuery(SolrSearchConstants.DOCTYPE_T_PRODUCT);
			solrQuery.setParam("fl", SolrSearchConstants.HP_NODE_PATH_T);
			/** We will get results only with exact match in case of cytognos products
			 *  from solr search because of its case-sensitive nature
			 **/
			solrQuery.setQuery("uniqueIdentifier:".concat("\""+materialNo+"\""));
			try {
				queryResponse = server.query(solrQuery);
				SolrDocumentList sitesSolrDocs = queryResponse.getResults();
				if (!sitesSolrDocs.isEmpty()) {
					SolrDocument solrDocument = sitesSolrDocs.get(0);
					hpNodePath = solrDocument.get(SolrSearchConstants.HP_NODE_PATH_T).toString();
					LOG.debug("hpNodePath ------------->{}",hpNodePath);
					resource = resourceResolver.getResource(hpNodePath);
				}
			} catch (SolrServerException | IOException  | HttpSolrClient.RemoteSolrException e) {
				LOG.error("Error in getting HP node path {0}", e);
			}
		}
		return resource;
	}
	
	/**
	 * 
	 * @param materialNo
	 * @param country
	 * @param resourceResolver
	 * @return hpNodeResource
	 */
	public static String getIdFromSolr(String materialNo, String country, ResourceResolver resourceResolver, SolrClient server) {
		LOG.debug("Inside getHpNodeResource ------------->{}",materialNo);
		String facetId = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(materialNo)) {
			QueryResponse queryResponse;
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.addFilterQuery(SolrSearchConstants.DOCTYPE_T_PRODUCT);
			solrQuery.setParam("fl", SolrSearchConstants.SOLRDOC_FIELD_ID);
			/** We will get results only with exact match in case of cytognos products
			 *  from solr search because of its case-sensitive nature
			 **/
			solrQuery.setQuery("uniqueIdentifier:".concat("\""+materialNo+"\""));
			try {
				queryResponse = server.query(solrQuery);
				SolrDocumentList sitesSolrDocs = queryResponse.getResults();
				if (!sitesSolrDocs.isEmpty()) {
					SolrDocument solrDocument = sitesSolrDocs.get(0);
					facetId = solrDocument.get(SolrSearchConstants.SOLRDOC_FIELD_ID).toString();
					LOG.debug("hpNodePath ------------->{}",facetId);
				}
			} catch (SolrServerException | IOException  | HttpSolrClient.RemoteSolrException e) {
				LOG.error("Error in getting HP node path {0}", e);
			}
		}
		return facetId;
	}
}
