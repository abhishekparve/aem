package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

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

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;

/**
 * The Class AnchorModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AnchorModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(AnchorModel.class);

	/** The category select. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String categorySelect;

	/** The search placeholder. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String searchPlaceholder;

	/** The page tab variarion. */
	@Inject
	@Via("resource")
	@Default(values = "false")
	private String pageTabVariarion;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The resolver factory. */
	@Inject
	ResourceResolverFactory resolverFactory;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
	
	/** The current resource. */
	@Inject
	Resource currentResource;

	/** The anchor list. */
	private List<Map<String, String>> anchorList;

	/** The anchor placeholder. */
	private String anchorPlaceholder = "";
	

	/** The anchor Title. */
	@Inject
	@Via("resource")
	@Default(values = "On This Page")
	private String title;

	/** The search page path. */
	String searchPagePath;

	/** The category tag name. */
	String categoryTagName;

	/** The Constant ANCHOR_IDENTIFIER. */
	protected static final String ANCHOR_IDENTIFIER = "enableAnchorIdentifier";
	
	Map<String, String> clpAnchorMap = null;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		try (ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory)){
			logger.debug("AnchorModel - Init method started");
			anchorList = createAnchorList(resourceResolver);
			String categoryTagProperty = CommonHelper.getProductCategory(currentPage);
			categoryTagName = getTag(resourceResolver, categoryTagProperty);
			searchPagePath = CommonHelper.getSearchResultPagePath(currentPage);
			if(StringUtils.isNotEmpty(searchPagePath)) {
				searchPagePath = externalizerService.getFormattedUrl(searchPagePath, resourceResolver)
				.concat("?searchKey=&productType_t::").concat(categoryTagName + "=" + categoryTagName);
			}
		} catch (LoginException | RepositoryException e) {
			logger.error("LoginException {}", e.getMessage());
		} 
	}

	
	
	/**
	 * Creates the anchor list.
	 *
	 * @param resourceResolver the resource resolver
	 * @return the map
	 * @throws RepositoryException the repository exception
	 */
	public List<Map<String, String>> createAnchorList(ResourceResolver resourceResolver) throws RepositoryException {List<Resource> resources = getComponentNodes(currentPage.getPath(), resourceResolver);
	List<Map<String, String>> clpAnchorList = new ArrayList<Map<String,String>>();
	if (!resources.isEmpty()) {
		for (Resource resource : resources) {
			clpAnchorMap = new HashMap<String,String>();
			Node node = resource.adaptTo(Node.class);
			String sectionTitle = node.hasProperty(CommonConstants.SECTION_TITLE) ? node.getProperty(CommonConstants.SECTION_TITLE).getString() : "";
			String title = node.hasProperty(CommonConstants.TITLE_LABEL) ? node.getProperty(CommonConstants.TITLE_LABEL).getString() : "";
			sectionTitle = StringUtils.isNotEmpty(sectionTitle) ? sectionTitle : title;
			String anchorIdDescription = node.hasProperty(CommonConstants.ANCHOR_ID_DESCRIPTION) ? node.getProperty(CommonConstants.ANCHOR_ID_DESCRIPTION).getString().replaceAll("\\<.*?\\>", "") : "";
			if(StringUtils.isNotEmpty(anchorIdDescription) && StringUtils.isNotEmpty(sectionTitle)) {
				anchorIdDescription = ": " +anchorIdDescription;
			}
			String anchorResourceLink = resource.getParent().getName().toString() + "-" + resource.getName().toString();
			clpAnchorMap.put(CommonConstants.SECTION_TITLE, sectionTitle);
			clpAnchorMap.put(CommonConstants.ANCHOR_ID_DESCRIPTION, anchorIdDescription);
			clpAnchorMap.put(CommonConstants.ANCHOR_RESOURCE_LIN, anchorResourceLink);
			clpAnchorList.add(clpAnchorMap);
		}
	}
	return clpAnchorList;}
	
	/**
	 * Gets the component nodes.
	 *
	 * @param pagePath the page path
	 * @param resourceResolver the resource resolver
	 * @return the component nodes
	 */
	public List<Resource> getComponentNodes(String pagePath, ResourceResolver resourceResolver) {
		List<Resource> resources = new ArrayList<>();
		String clpParentPath = currentResource.getParent().getPath();
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(pagePath) && null != resourceResolver) {
			Resource pageContentResource;

			if (pageTabVariarion.equalsIgnoreCase(CommonConstants.STRING_TRUE) && StringUtils.isNotEmpty(clpParentPath)) {
				pageContentResource = resourceResolver.getResource(clpParentPath);
			} else {
				pageContentResource = resourceResolver.getResource(pagePath.concat(CommonConstants.JCR_ROOT));
			}
			
			if (pageContentResource != null) {
				Iterator<Resource> children = pageContentResource.listChildren();
				while (children.hasNext()) {
					Resource child = children.next();
					ValueMap childValueMap = child.getValueMap();
					if (null != childValueMap.get(ANCHOR_IDENTIFIER)
							&& childValueMap.get(ANCHOR_IDENTIFIER).equals(CommonConstants.STRING_TRUE)) {
						resources.add(child);
					}
				}
			}
		}
		return resources;
	}
	
	/**
	 * Gets the tag.
	 *
	 * @param resourceResolver the resource resolver
	 * @param cqTagProperty the cq tag property
	 * @return the tag
	 */
	public String getTag(ResourceResolver resourceResolver, String cqTagProperty) {
		if (null != cqTagProperty) {
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
			Tag tag = tagManager.resolve(cqTagProperty);
			logger.debug("Tag selector resource Tag {}", tag);
			return (null != tag && StringUtils.isNotBlank(tag.getTitle())) ? tag.getTitle() : StringUtils.EMPTY;
		} else {
			return StringUtils.EMPTY;
		}
	}
	
	
	/**
	 * Gets the anchor list.
	 *
	 * @return the anchor list
	 */
	public List<Map<String, String>> getAnchorList() {
		if (anchorList.isEmpty())
			return new ArrayList<Map<String, String>>();
		return anchorList;
	}

	/**
	 * Gets the anchor placeholder.
	 *
	 * @return the anchor placeholder
	 */
	public String getAnchorPlaceholder() {
		return anchorPlaceholder;
	}
	
	/**
	 * Gets the anchor Title.
	 *
	 * @return the anchor Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the category select.
	 *
	 * @return the category select
	 */
	public String getCategorySelect() {
		return categorySelect;
	}

	/**
	 * Gets the search placeholder.
	 *
	 * @return the search placeholder
	 */
	public String getSearchPlaceholder() {
		return searchPlaceholder;
	}

	/**
	 * Gets the search page path.
	 *
	 * @return the search page path
	 */
	public String getSearchPagePath() {
		return searchPagePath;
	}
	
	/**
	 * @return the clpAnchorMap
	 */
	public Map<String, String> getClpAnchorMap() {
		return clpAnchorMap;
	}

}