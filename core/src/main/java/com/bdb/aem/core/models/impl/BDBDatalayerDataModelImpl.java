package com.bdb.aem.core.models.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.license.ProductInfoProvider;
import com.adobe.granite.license.ProductInfoService;
import com.bdb.aem.core.models.BDBDatalayerDataModel;
import com.bdb.aem.core.models.EventBlogModel;
import com.bdb.aem.core.models.PDPSessionModel;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.google.common.net.HttpHeaders;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, adapters = BDBDatalayerDataModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BDBDatalayerDataModelImpl implements BDBDatalayerDataModel {

	Logger logger = LoggerFactory.getLogger(BDBDatalayerDataModelImpl.class);

	private static final String FALLBACK_SITE_SECTION = "NA";

	// LEVEL of HOME
	private static final int HOME_LEVEL = 4;
	
	//Previous page value in case of user coming from CIAM.
	private static final String BDB_SSO_HOME = "bdb|accounts|account|login|";

	/**
	 * The ProductInfoProvider service.
	 */
	@OSGiService
	private ProductInfoProvider productInfoProvider;

	@OSGiService
	private ProductInfoService productInfoService;

	@OSGiService
	private SlingSettingsService slingSettingsService;
	
	@ScriptVariable
	private Page currentPage;

	@Self
	private SlingHttpServletRequest request;
	
	@Self
	private PDPSessionModel pdpSessionModel;

	@ScriptVariable
	protected ValueMap pageProperties;

	private String path;

	private String pathQueryString;

	private String intCampaign;

	private String extCampaign;

	private String name;

	private String title;
	
	private String contentPageType;

	private String template;

	private String siteSection;

	private String pageType;

	private String region;

	private String replicationDate;

	private String platform;
	
	private Boolean isBlogPage;
	
	private String userloginStatus;

	private String blogTopicFilterUsed;

	private String blogDateFilterUsed;
	
	private String originalPublishDate;
	
	private String blogArticlePublishDate;
	
	private String blogArticleTitle;
	
	private String clientIpAddress; 
	
	private ResourceResolver resourceResolver;

	@PostConstruct
	protected void init() {
		resourceResolver = request.getResourceResolver();
		path = request.getRequestURL().toString().split("/content")[0]+resourceResolver.map(request.getRequestURI());
		if (null == currentPage || currentPage.getName().equals(CommonConstants.ERROR_404) || currentPage.getName().equals(CommonConstants.ERROR_500)) {
			pageType = CommonConstants.ERROR;
		}
		if (null != request.getCookie(CommonConstants.PUNCH_OUT)) {
			platform = CommonConstants.PUNCH_OUT;
		} else {
			platform = CommonConstants.WEB;
		}
		if (null != request.getCookie(CommonConstants.SIGNED_IN_USER)) {
			userloginStatus = CommonConstants.LOGGED_IN;
		} else {
			userloginStatus = CommonConstants.NOT_LOGGED_IN; 
			
		}
		region = CommonHelper.getCountry(currentPage);
		if (StringUtils.isNotBlank(request.getQueryString())) {
			pathQueryString = path + CommonConstants.INTERROGATION + request.getQueryString();
		} else {
			pathQueryString = path;
		}
		if (null != currentPage) {
			name = getPageName(currentPage.getPath());
			title = currentPage.getTitle();
			
			InheritanceValueMap inVM = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			contentPageType = inVM.getInherited(CommonConstants.CONTENT_PAGE_TYPE, String.class);
			
			if(currentPage.getName().equalsIgnoreCase(CommonConstants.URL_PDP)) {
				contentPageType = CommonConstants.URL_PDP;
			}
			
			if(null == contentPageType) {
				contentPageType = CommonConstants.CONTENT_PAGE ;
			}
			
			if (null != currentPage.getContentResource().getValueMap().get(CommonConstants.CQ_LAST_REPLICATED)) {
				replicationDate = currentPage.getContentResource().getValueMap()
						.get(CommonConstants.CQ_LAST_REPLICATED, Date.class).toString();
			} else if (null != currentPage.getContentResource().getValueMap().get(CommonConstants.CQ_LAST_MODIFIED)) {
				replicationDate = currentPage.getContentResource().getValueMap()
						.get(CommonConstants.CQ_LAST_MODIFIED, Date.class).toString();
			}

			originalPublishDate = currentPage.getContentResource().getValueMap()
					.get(CommonConstants.JCR_CREATED, Date.class).toString();
			if (null != currentPage.getTemplate()) {
				template = currentPage.getTemplate().getName();
			}
		}
		setSiteSections();
		setCampaignIds(request);
		setBlogDetails();
		setClientIpAddress(request);
		logger.debug("Ip Address {}",clientIpAddress);
	}

	
	/**
	 * Generate the Client IP Address
	 * @param path
	 * @return clientIpAddress
	 */
	public void setClientIpAddress(SlingHttpServletRequest request) {
	    String xForwardedForHeader = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
	    if (xForwardedForHeader == null) {
	    	
	    	clientIpAddress = request.getRemoteAddr();
	    	logger.debug("Xforward as null Ip Address {}",clientIpAddress);
	    } else {
	      
	    	clientIpAddress = new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
	    	logger.debug("Xforward have value Ip Address {}",clientIpAddress);
	    }
	}
	
	public String getClientIpAddress() {
		
		return clientIpAddress;
		
	}
	/**
	 * Generate the page name based upon the agreed BDB-Adobe Page Naming Convention
	 * @param path
	 * @return pageName
	 */
	public String getPageName(String path) {
		String[] pagePath = path.split(CommonConstants.SLASH, 7);
		String finalPagePath = pagePath[pagePath.length - 1];
		String[] pathSubStrings = finalPagePath.split(CommonConstants.SLASH);
		StringBuilder baseName = new StringBuilder();
		baseName.append(CommonConstants.BDB);
		int length = pathSubStrings.length > 4 ? pathSubStrings.length : 4;
		for (int i = 0; i < length; i++) {
			if (i < 4) {
				baseName.append(CommonConstants.PIPE);
				if (i < pathSubStrings.length) {
					baseName.append(pathSubStrings[i]);
				}
			} else {
				baseName.append(CommonConstants.CARET);
				if (StringUtils.equalsIgnoreCase(pathSubStrings[i], CommonConstants.URL_PDP)) {
					String pdpName = StringUtils.isNotBlank(pdpSessionModel.getProductName())
							? pdpSessionModel.getProductName()
							: CommonConstants.URL_PDP;
					baseName.append(pdpName);
				} else {
					baseName.append(pathSubStrings[i]);
				}
			}
		}
		return baseName.toString();

	}
	
	@Override
	public String getPreviousPage() {
		if (StringUtils.isNotEmpty(request.getHeader(CommonConstants.REFERER))) {
			String referer = request.getHeader(CommonConstants.REFERER);
			if (StringUtils.containsIgnoreCase(referer, "sso.bdbiosciences.com")
					|| StringUtils.containsIgnoreCase(referer, "b2clogin.com")
					|| StringUtils.containsIgnoreCase(referer, "login.bd.com")) {
				return BDB_SSO_HOME;
			}
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Set the Blog details needed for Adobe data layer.
	 */
	private void setBlogDetails() {
		Resource res = request.getResource();
		Iterator<Resource> itemsList = res.listChildren();
		while (itemsList.hasNext()) {
			Resource rootRes = itemsList.next();
			if (null != rootRes) {
				Iterator<Resource> rootresList = rootRes.listChildren();
				while (rootresList.hasNext()) {
					Resource rootResItem = rootresList.next();
					if (rootResItem.getResourceType().equals("bdb-aem/proxy/components/content/eventblogDetails")) {
						EventBlogModel eventBlogModel = rootResItem.adaptTo(EventBlogModel.class);
						blogArticleTitle = eventBlogModel.getBannerTitleBlog();
						blogTopicFilterUsed = eventBlogModel.getBlogTopic1().replace(" ", "_");
						blogArticlePublishDate = getDateAndTime(eventBlogModel.getBlogDate(), "MMMM dd yyyy");
						blogDateFilterUsed = getDateAndTime(eventBlogModel.getBlogDate(), "MMM_yyyy");
						if (StringUtils.isNotBlank(blogDateFilterUsed) || StringUtils.isNotBlank(blogArticleTitle)) {
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the date and time.
	 *
	 * @param strDateTime    the date
	 * @param dateTimeFormat the date time format
	 * @return the date and time
	 */
	public String getDateAndTime(String strDateTime, String dateTimeFormat) {
		return Optional.ofNullable(strDateTime).filter(s -> !s.equals("")).map(s -> {
			try {
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(s);
			} catch (ParseException e) {
				logger.error("ParseException {}", e.getMessage());
			}
			return StringUtils.EMPTY;
		}).map(d -> new SimpleDateFormat(dateTimeFormat).format(d)).orElse("");
	}

	@Override
	public String getVersion() {
		return productInfoService.getLicense().getProductVersion();
	}

	@Override
	public String getAuthorId() {
		Session session = resourceResolver.adaptTo(Session.class);
		return session.getUserID();
	}

	@Override
	public String checkAuthorVsPublish() {
		String runModes = slingSettingsService.getRunModes().toString();
		if (StringUtils.containsIgnoreCase(runModes, CommonConstants.AUTHOR)) {
			return CommonConstants.AUTHOR;
		} else {
			return CommonConstants.PUBLISH;
		}
	}

	@Override
	public String getRunModes() {
		String server = request.getServerName();
		if (StringUtils.containsIgnoreCase(server, CommonConstants.DEV)) {
			return CommonConstants.DEV;
		} else if (StringUtils.containsIgnoreCase(server, CommonConstants.QA)) {
			return CommonConstants.QA;
		} else if (StringUtils.containsIgnoreCase(server, CommonConstants.STG)) {
			return CommonConstants.RUNMODE_STAGE;
		} else if (!StringUtils.containsIgnoreCase(server, "adobeaemcloud.com")) {
			return CommonConstants.RUNMODE_PROD;
		}
		return StringUtils.EMPTY;
	}

	@Override
	public String getTimestamp() {
		return new SimpleDateFormat(CommonConstants.TIMESTAMP).format(new java.util.Date());
	}
	
	@Override
	public String getScrollDepth() {
		return null != request.getCookie(CommonConstants.SCROLL_DEPTH)
				? CommonHelper.getSpecificCookie(request, CommonConstants.SCROLL_DEPTH)
				: StringUtils.EMPTY;
	}
	
	@Override
	public String getCcv2UserID() {
		return null != request.getCookie(CommonConstants.USER_DETAILS_UID)
				? CommonHelper.getSpecificCookie(request, CommonConstants.USER_DETAILS_UID)
				: StringUtils.EMPTY;
	}
	
	

	/**
	 * Method to set Site sections
	 */
	private void setSiteSections() {
		siteSection = FALLBACK_SITE_SECTION;
		final List<String> siteSections = new ArrayList<String>();
		getSiteSections(siteSections, currentPage);
		Collections.reverse(siteSections);
		if (siteSections.size() >= 1) {
			siteSection = siteSections.get(0);
		}
		if (siteSections.size() >= 2) {
			siteSection = siteSections.get(1);
		}
		if (siteSections.size() >= 3) {
			siteSection = siteSections.get(2);
		}
	}

	/**
	 * 
	 * @param siteSections
	 * @param page
	 */
	private void getSiteSections(List<String> siteSections, Page page) {
		if (page == null) {
			return;
		}
		if (page.getDepth() <= HOME_LEVEL) {
			return;
		}
		if (page.getDepth() > HOME_LEVEL) {
			siteSections.add(page.getTitle());
			getSiteSections(siteSections, page.getParent());
		}
	}

	/**
	 * Set the internal and external campaign id's from the url.
	 * 
	 * @param request
	 */
	private void setCampaignIds(SlingHttpServletRequest request) {
		StringBuilder baseName = new StringBuilder();
		String queryParams = request.getQueryString();
		if (StringUtils.containsIgnoreCase(queryParams, CommonConstants.INTCID)) {
			intCampaign = request.getParameter(CommonConstants.INTCID);
		}
		if (StringUtils.containsIgnoreCase(queryParams, "utm")) {
			if (StringUtils.containsIgnoreCase(queryParams, CommonConstants.UTM_SOURCE)) {
				baseName.append(request.getParameter(CommonConstants.UTM_SOURCE));
			}
			baseName.append(CommonConstants.PIPE);
			if (StringUtils.containsIgnoreCase(queryParams, CommonConstants.UTM_MEDIUM)) {
				baseName.append(request.getParameter(CommonConstants.UTM_MEDIUM));
			}
			baseName.append(CommonConstants.PIPE);
			if (StringUtils.containsIgnoreCase(queryParams, CommonConstants.UTM_CAMPAIGN)) {
				baseName.append(request.getParameter(CommonConstants.UTM_CAMPAIGN));
			}
			baseName.append(CommonConstants.PIPE);
			if (StringUtils.containsIgnoreCase(queryParams, CommonConstants.UTM_CONTENT)) {
				baseName.append(request.getParameter(CommonConstants.UTM_CONTENT));
			}
			baseName.append(CommonConstants.PIPE);
			if (StringUtils.containsIgnoreCase(queryParams, CommonConstants.UTM_TERM)) {
				baseName.append(request.getParameter(CommonConstants.UTM_TERM));
			}
			extCampaign = baseName.toString();
		}}

	@Override
	public Boolean isBlogPage() {
		if (StringUtils.containsIgnoreCase(path, "blog") && !StringUtils.containsIgnoreCase(path, "scheduleType")) {
			isBlogPage = Boolean.TRUE;
		}
		return isBlogPage;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getPathQueryString() {
		return pathQueryString;
	}
	
	@Override
	public String getUserloginStatus() {
		return userloginStatus;
	}

	@Override
	public String getPlatform() {
		return platform;
	}

	@Override
	public String getReplicationDate() {
		return replicationDate;
	}
	
	@Override
	public String getOriginalPublishDate() {
		return originalPublishDate;
	}

	@Override
	public String getBlogTopicFilterUsed() {
		return blogTopicFilterUsed;
	}
	
	@Override
	public String getBlogArticleTitle() {
		return blogArticleTitle;
	}


	@Override
	public String getBlogDateFilterUsed() {
		return blogDateFilterUsed;
	}
		
	@Override
	public String getBlogArticlePublishDate() {
		return blogArticlePublishDate;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getIntCampaign() {
		return intCampaign;
	}

	@Override
	public String getExtCampaign() {
		return extCampaign;
	}

	@Override
	public String getTemplate() {
		return template;
	}

	@Override
	public String getSiteSection() {
		return siteSection;
	}

	@Override
	public String getPageType() {
		return pageType;
	}

	@Override
	public String getContentPageType() {
		return contentPageType;
	}

}
