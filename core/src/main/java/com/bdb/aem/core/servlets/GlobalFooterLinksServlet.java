package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Component(name = "GlobalFooterServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Get country region servlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + GlobalFooterLinksServlet.RESOURCE_TYPE,
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "us",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "ca",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "gb",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "de",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "dk",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "fi",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "es",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "it",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "fr",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "nl",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "lu",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "pt",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "ch",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "ie",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "at",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "pl",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "se",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "no",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "be",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "eu",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "jp",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "au",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "in",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "kr",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "tw",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "sg",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "nz",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "cn",
        ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "br",
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON
})
public class GlobalFooterLinksServlet extends SlingAllMethodsServlet {

	/**
     * The bdb api endpoint service.
     */
    @Reference
    private transient BDBApiEndpointService bdbApiEndpointService;
    
    /** The externalizer service. */
    @Reference
    private transient ExternalizerService externalizerService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1617227505348386613L;

	/** The logger. */
	public static final Logger LOGGER = LoggerFactory.getLogger(GlobalFooterLinksServlet.class);

	public static final String XF_PATH = "/content/experience-fragments/bdb";
	
	public static final String GLOBAL_FOOTER_PATH  = "/global-footer/master/jcr:content/root/globalfooter";

	public static final String RESOURCE_TYPE = "bdb/get-footer-links";
	

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		
		LOGGER.debug("Inside doGet of GlobalFooterServlet");
		String selectorName = null;
		String runMode = bdbApiEndpointService.getCustomRunMode();

        	try {
        		ResourceResolver resourceResolver = request.getResourceResolver();
         		String[] selectors = request.getRequestPathInfo().getSelectors();
         		// Getting the country code from selector
    			if (selectors.length > 0) {
    				selectorName = selectors[0].toString();
    			}
    			// Getting the country path of the global footer experience-fragment
    			String countryXfPath = CommonHelper.getRegionSpecificXf(selectorName, resourceResolver);
    			String xfFooterPath = countryXfPath + GLOBAL_FOOTER_PATH;
    			
    			// Extracting Json for categories links for Global Footer XF
    			Resource itemResource = resourceResolver.getResource(xfFooterPath + "/categories");
    			
    			ObjectMapper mapperCat = new ObjectMapper();
    			ObjectNode categoryJson = mapperCat.createObjectNode();
    			ObjectNode globalFooterJson = mapperCat.createObjectNode();
    			ArrayNode catChildArray = mapperCat.createArrayNode();
        		Iterator<Resource> newChildNodes = itemResource.listChildren();

        		// Looping though each child of category node to get the title key and appending its value to columnName
        		LOGGER.debug("Extracting the category node of XF");
                int i = 1;
        		while (newChildNodes.hasNext()) {
    				Resource newChildNode = newChildNodes.next();
    				ValueMap subChildProperties = newChildNode.getValueMap();
    				ObjectNode itemObj = mapperCat.createObjectNode();
    				String columnName = "columnName" + i;
    				String title = subChildProperties.get("title","");
    				itemObj.put(columnName, title);

    				Iterator<Resource> subCatNodes = newChildNode.listChildren();
    			    
	    			while(subCatNodes.hasNext()) {
	        			Resource subCatNode = subCatNodes.next();
	        			// Iterating the child node of subcategories for extracting title and url property
	        			Iterator<Resource> subCategoryNodes = subCatNode.listChildren();
	    			    ArrayNode subCategoryChildArray = mapperCat.createArrayNode();
	    			    while(subCategoryNodes.hasNext()) {
		        			Resource subCategoryNode = subCategoryNodes.next();
		        			ValueMap subCategoryChildProperties = subCategoryNode.getValueMap();
		        			ObjectNode subCategory = mapperCat.createObjectNode();
		        			String subTitle = subCategoryChildProperties.get("title","");
		        			String url = subCategoryChildProperties.get("url","");
		        				subCategory.put("title",subTitle);
		        				if (url.contains("bd.com")) {
				        			subCategory.put("url", externalizerService.getFormattedUrl(url, resourceResolver));
		        				} else {
				        			subCategory.put("url",CommonHelper.getShortUrlXf(externalizerService.getFormattedUrl(url, resourceResolver), countryXfPath, resourceResolver, runMode));
		        				}
			        			subCategoryChildArray.add(subCategory);
	        			
	        		  }

	    			    itemObj.set("Links", subCategoryChildArray);
	    			    catChildArray.add(itemObj);

        		}
	    			
	    			categoryJson.set("Categories", catChildArray);
                    i++;
        		}
        		LOGGER.debug("Extracted the json of category node");
        		
        		// Extracting the json for Social Links of Global footer XF
        		ObjectMapper mapperSocial = new ObjectMapper();
        		ObjectNode socialJson = mapperSocial.createObjectNode();
        		ObjectNode socialObject = mapperSocial.createObjectNode();
        		Resource socialResource = resourceResolver.getResource(xfFooterPath + "/social");
        		ArrayNode socialChildArray = mapperSocial.createArrayNode();
        		ArrayNode socialArray = mapperSocial.createArrayNode();
        		
        		LOGGER.debug("Extracting the json of social node");
        		
        		// iterating through child nodes of social node to get the title and url
        		Iterator<Resource> socialChildNodes = socialResource.listChildren();
        		while (socialChildNodes.hasNext()) {
        			Resource socialChildNode = socialChildNodes.next();
        			ObjectNode socialObj = mapperSocial.createObjectNode(); //{}
        			ValueMap socialChildProperties = socialChildNode.getValueMap();
        			String socialAlt = socialChildProperties.get("socialAlt","");
        			String socialURL = socialChildProperties.get("socialURL","");
        			socialObj.put("title",socialAlt);
        			socialObj.put("url", externalizerService.getFormattedUrl(socialURL, resourceResolver));
        			socialChildArray.add(socialObj);
        		}
        		    socialObject.set("Links", socialChildArray);
        		    socialArray.add(socialObject);
        		    socialJson.set("Social", socialArray);
        		    
        			LOGGER.debug("Extracted the json of social node");

        		    // Extracting the json for Footer Links of Global Footer XF
        		    ObjectMapper mapperFooter = new ObjectMapper();
            		ObjectNode footerLinksJson = mapperFooter.createObjectNode();
            		ObjectNode footerLinksObject = mapperFooter.createObjectNode();
            		Resource footerLinksResource = resourceResolver.getResource(xfFooterPath + "/footerLinks");
            		ArrayNode footerLinksChildArray = mapperFooter.createArrayNode();
            		ArrayNode footerLinksArray = mapperFooter.createArrayNode();
            		 LOGGER.debug("Extracting the json of footerLinks node");
            		// iterating through child nodes of footerLinks node to get the title and url
            		Iterator<Resource> footerLinksNodes = footerLinksResource.listChildren();
            		while (footerLinksNodes.hasNext()) {
            			Resource footerLinksNode = footerLinksNodes.next();
            			ObjectNode footerLinksObj = mapperFooter.createObjectNode(); //{}
            			ValueMap footerLinksChildProperties = footerLinksNode.getValueMap();
            			String linkLabel = footerLinksChildProperties.get("linkLabel","");
            			String linkURL = footerLinksChildProperties.get("linkURL","");
            			footerLinksObj.put("title",linkLabel);
            			footerLinksObj.put("url", CommonHelper.getShortUrlXf(externalizerService.getFormattedUrl(linkURL, resourceResolver), countryXfPath, resourceResolver, runMode));
            			footerLinksChildArray.add(footerLinksObj);

            		}
            		    footerLinksObject.set("Links", footerLinksChildArray);
            		    footerLinksArray.add(footerLinksObject);
            		    footerLinksJson.set("Footer Links", footerLinksArray);
            		    LOGGER.debug("Extracted the json of footerLinks node");

        			    // combining all the three json
            		    globalFooterJson.setAll(categoryJson);
            		    globalFooterJson.setAll(socialJson);
            		    globalFooterJson.setAll(footerLinksJson);
            		    // Set the content type of the response to "application/json"
            		    response.setContentType("application/json");
            		    response.getWriter().write(mapperSocial.writerWithDefaultPrettyPrinter().writeValueAsString(globalFooterJson));
            		    LOGGER.debug("Exit of doGet Method of GlobalFooterServlet");
			
                			
        	     } catch (NullPointerException e) {
        		LOGGER.error("Error: "+ e.getMessage());
        	}
        	}
}