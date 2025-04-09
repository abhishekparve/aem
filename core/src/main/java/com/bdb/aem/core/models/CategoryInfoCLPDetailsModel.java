package com.bdb.aem.core.models;

import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;

/**
 * The Class CategoryInfoCLPDetailsModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class , Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CategoryInfoCLPDetailsModel {
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(CategoryInfoCLPDetailsModel.class);

	/** The Constant LINK_TYPE_STATIC. */
	private static final String LINK_TYPE_STATIC = "static";

	/** The Constant LINK_TYPE_PDP. */
	private static final String LINK_TYPE_PDP = "pdp";

	/** The Constant LINK_TYPE_PLP. */
	private static final String LINK_TYPE_PLP = "plp";

	/** The resource. */
	@Inject
	private Resource resource;
	
	/** The open new tab. */
	@Inject
	private String openNewTab;

	/** The link label. */
	@Inject
	private String linkLabel;

	/** The link type. */
	@Inject
	private String linkType;

	/** The link url. */
	@Inject
	private String linkUrl;

	/** The pdp link url. */
	@Inject
	private String pdpLinkUrl;

	/** The plp link url. */
	@Inject
	private String plpLinkUrl;
	
	/** The Event Name. */
	@Inject
	private String eventName;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The resolver factory. */
	@Inject
	ResourceResolverFactory resolverFactory;
	
	/** The solr search service. */
	@Inject
	SolrSearchService solrSearchService;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The static link url. */
	private String staticLinkUrl;
	
	/** The pdp link url val. */
	private String pdpLinkUrlVal;
	
	/** The plp link url val. */
	private String plpLinkUrlVal;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		try (ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory)){
			staticLinkUrl = externalizerService.getFormattedUrl(linkUrl, resourceResolver);
			
			String country = CommonHelper.getCountry(currentPage);
			pdpLinkUrlVal =  externalizerService.getFormattedUrl(
					CommonHelper.getPdpUrlFromTag(pdpLinkUrl, country, resource, resourceResolver, solrSearchService), resourceResolver);

			plpLinkUrlVal = Optional.ofNullable(plpLinkUrl).map(u -> externalizerService.getFormattedUrl(u, resourceResolver))
					.map(e -> e.concat("?searchKey=" + getLinkLabel())).orElse(StringUtils.EMPTY);
		} catch (LoginException e) {
			logger.error("LoginException {}", e.getMessage());
		} 
	}
	
	
	/**
	 * Gets the open new tab.
	 *
	 * @return the open new tab
	 */
	public String getOpenNewTab() {
		return openNewTab;
	}

	/**
	 * Gets the link label.
	 *
	 * @return the link label
	 */
	public String getLinkLabel() {
		return linkLabel;
	}

	/**
	 * Gets the link type.
	 *
	 * @return the link type
	 */
	public String getLinkType() {
		return linkType;
	}

	/**
	 * Gets the link url.
	 *
	 * @return the link url
	 */
	public String getLinkUrl() {

		return Optional.ofNullable(linkType).filter(StringUtils::isNotBlank).map(l -> mapLinkUrl(l))
				.orElse(StringUtils.EMPTY);

	}

	/**
	 * Map link url to correct method.
	 *
	 * @param linkUrl the link url
	 * @return the string
	 */
	private String mapLinkUrl(String linkUrl) {
		if (linkUrl.equals(LINK_TYPE_STATIC)) {
			return getStaticLinkUrl();
		} else if (linkUrl.equals(LINK_TYPE_PLP)) {
			return getPdpLinkUrlVal();
		} else if (linkUrl.equals(LINK_TYPE_PDP)) {
			return getPlpLinkUrlVal();
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Gets the static link url.
	 *
	 * @return the static link url
	 */
	public String getStaticLinkUrl() {
		return staticLinkUrl; 
	}

	/**
	 * Gets the pdp link url value.
	 *
	 * @return the pdp link url value
	 */
	public String getPdpLinkUrlVal() {
		return pdpLinkUrlVal;
	}

	/**
	 * Gets the plp link url value.
	 *
	 * @return the plp link url value
	 */
	public String getPlpLinkUrlVal() {
		return plpLinkUrlVal;
	}
	
	public String getEventName() {
		return eventName;
	}

}
