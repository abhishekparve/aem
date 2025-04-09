package com.bdb.aem.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.BeadlotLinkOnPdpModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrUtils;
import com.day.cq.wcm.api.Page;

/**
 * The Class BeadlotLinkOnPdpModelImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class , Resource.class }, adapters = {
		BeadlotLinkOnPdpModel.class }, resourceType = {
				BeadlotLinkOnPdpModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BeadlotLinkOnPdpModelImpl implements BeadlotLinkOnPdpModel{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BeadlotLinkOnPdpModelImpl.class);
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/beadlotLinkOnPdp/v1/beadlotLinkOnPdp";
	
	protected static final String QUERY_KEY = "searchTerm";
	protected static final String CATEGORY_KEY = "category";

	/** The header text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String headerText;

	/** The no beadlot text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String noBeadlotText;
	
	/** The view all text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String viewAllText;

	/** The landing page url. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String landingPageUrl;

	/** The request. */
	@Inject
	private SlingHttpServletRequest request;

	/** The resolver factory. */
	@Inject
	ResourceResolverFactory resolverFactory;
	
	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
	
	/** The SolrSearchService. */
	@Inject
	SolrSearchService solrSearchService;
	
	@Inject
	Page currentPage;

	/** The title of beadlot file. */
	private String titleOfBeadlotFile;

	/** The has beadlot. */
	private boolean hasBeadlot;
	
	/** The productVarient. */
	private String productVarient;
	
	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		LOGGER.debug("Inside BeadlotLinkOnPdpModel Init");
		productVarient = CommonHelper.getSelectorDetails(request);
		try {
			ResourceResolver resourceResolver = request.getResourceResolver();
			if (StringUtils.isNotBlank(productVarient) && StringUtils.isNotBlank(landingPageUrl)) {
				landingPageUrl = externalizerService.getFormattedUrl(landingPageUrl, resourceResolver);
				landingPageUrl = CommonHelper.getShortUrl(landingPageUrl, currentPage, resourceResolver, bdbApiEndpointService.getCustomRunMode());
				Resource hpVariantResource = null;
				if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH) && null != request.getAttribute("catalogNumber")) {
					String productVarHPPath = request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString();
					hpVariantResource = SolrUtils.getVariantHpResource(resourceResolver.getResource(productVarHPPath),
							request.getAttribute("catalogNumber").toString());
				}
				setTitleOfBeadlotFile(hpVariantResource);
			}
		} finally {
			LOGGER.debug("Initiated succesfully");
		} 
		LOGGER.debug("End of BeadlotLinkOnPdpModel Init");
	}

	/**
	 * Sets the title of beadlot file.
	 *
	 * @param hpResource the new title of beadlot file
	 */
	public void setTitleOfBeadlotFile(Resource hpResource) {
		LOGGER.debug("Inside setTitleOfBeadlotFile BeadlotLinkOnPdpModel");
		if (null != hpResource) {
			LOGGER.debug("Path of the Hp node : {}", hpResource.getPath());
			ValueMap properties = hpResource.adaptTo(ValueMap.class);
			if (MapUtils.isNotEmpty(properties)) {
				String hasBeadlotFiles = properties.get("hasBeadlotFiles", StringUtils.EMPTY);
				if (StringUtils.isNotBlank(hasBeadlotFiles)) {
					hasBeadlot = true;
					titleOfBeadlotFile = properties.get("beadlotTitle", StringUtils.EMPTY);
				}
			}
		}
		LOGGER.debug("Inside setTitleOfBeadlotFile BeadlotLinkOnPdpModel");
	}

	/**
	 * Gets the header text.
	 *
	 * @return the header text
	 */
	@Override
	public String getHeaderText() {
		return headerText;
	}
	
	/**
	 * Gets the view all text.
	 *
	 * @return the view all text
	 */
	@Override
	public String getViewAllText() {
		return viewAllText;
	}

	/**
	 * Gets the no beadlot text.
	 *
	 * @return the no beadlot text
	 */
	@Override
	public String getNoBeadlotText() {
		return noBeadlotText;
	}

	/**
	 * Gets the landing page url.
	 *
	 * @return the landing page url
	 */
	@Override
	public String getLandingPageUrl() {
//		this has been commented for demo purpose might need to uncomment it again hence not removing only commenting 
		/*
		 * if (hasBeadlot) { StringBuilder builder = new StringBuilder(landingPageUrl);
		 * return
		 * builder.append(CommonConstants.INTERROGATION).append(QUERY_KEY).append(
		 * CommonConstants.EQUAL).append(productVarient).append(CommonConstants.AND).
		 * append(CATEGORY_KEY).append(CommonConstants.EQUAL).append(titleOfBeadlotFile)
		 * .toString(); }
		 */
		return landingPageUrl;
	}

	/**
	 * Gets the title of beadlot file.
	 *
	 * @return the title of beadlot file
	 */
	@Override
	public String getTitleOfBeadlotFile() {
		return titleOfBeadlotFile;
	}

	/**
	 * Gets the checks for beadlot.
	 *
	 * @return the checks for beadlot
	 */
	@Override
	public boolean getHasBeadlot() {
		return hasBeadlot;
	}

}
