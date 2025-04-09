package com.bdb.aem.core.services.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.SiteMapService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationStatus;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.day.crx.JcrConstants;

@Component(service = SiteMapService.class, immediate = true)
public class SiteMapServiceImpl implements SiteMapService {

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(SiteMapServiceImpl.class);

    @Reference
    private Replicator replicator;
    
    @Reference
    private ExternalizerService externalizerService;
    
    @Reference
    private SolrSearchService solrSearchService;

    private static final String NS = "http://www.sitemaps.org/schemas/sitemap/0.9";
    private static final String XHTML_NS ="http://www.w3.org/1999/xhtml";
    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    private static final String XML = ".xml";
    private static final String LOC = "loc";
    private static final String P_SITEMAP_INDEX_NAME = "sitemap_index.xml";
    private static final String P_SITEMAP_NAME = "sitemap";
    private static final String LASTMOD = "lastmod";
    private static final String XHTML = "xhtml";
    private static final String URLSET = "urlset";
    private static final String CHANGE_FREQ="changefreq";
    private static final String PRIORITY="priority";
    private static final String JCR_PRIORITY="pagePriority";
    private static final String JCR_CHANGE_FREQ="changeFrequency";
    private static final String APPLICATION_XML="application/xml";
    private static final String SOLR_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";



    @Override
    public void generateSiteMap(String[] paths, ResourceResolver resolver, final int P_DEFAULT_LIMIT)
			throws LoginException, RepositoryException, XMLStreamException {
		Session session = null;
		
		if (resolver == null) {
			return;
		}
		for (String path : paths) {
			Resource resource = resolver.resolve(path);
			PageManager pageManager = resolver.adaptTo(PageManager.class);
			Page page = null != pageManager ? pageManager.getContainingPage(resource) : null;
			List<String> pageQueue = new ArrayList<>();
			if(null != page){
                listAndAddChildren(pageQueue, page);
                listAndAddPDPs(pageQueue, path);
                if (!pageQueue.isEmpty()) {
                    int noOfSiteMap = getNoofSiteMap(pageQueue, P_DEFAULT_LIMIT);
                    session = resolver.adaptTo(Session.class);
                    writeToSiteMapIndex(resource, pageQueue, resolver, session, noOfSiteMap, pageManager,P_DEFAULT_LIMIT);
                }
            }
		}
		
	}

    private void listAndAddPDPs(List<String> pageQueue, String path) {
    	
    	String country = StringUtils.substringAfterLast(path, "-");
    	HttpSolrClient solr = solrSearchService.solrClient(country);
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(SOLR_DATE_FORMAT);
		LocalDateTime now = LocalDateTime.now();
		
    	SolrQuery solrQuery = new SolrQuery();
    	solrQuery.setQuery("docType_t:product");
    	solrQuery.addFilterQuery("isAvailable_t:true");
    	solrQuery.addFilterQuery("validityStartDate_dt:[* TO ".concat(dtf.format(now)).concat("]"));
    	solrQuery.addField("id");
    	SolrQuery finalSolrQuery = new SolrQuery();
    	finalSolrQuery.setQuery("docType_t:product");
    	finalSolrQuery.addFilterQuery("isAvailable_t:true");
    	finalSolrQuery.addFilterQuery("validityStartDate_dt:[* TO ".concat(dtf.format(now)).concat("]"));
    	finalSolrQuery.addField("id");
    	
    	try {
			QueryResponse sitesQueryResponse = solr.query(solrQuery);
			SolrDocumentList solrDocs = sitesQueryResponse.getResults();
			long numFound = solrDocs.getNumFound();
			finalSolrQuery.setRows((int) numFound);
			QueryResponse finalSitesQueryResponse = solr.query(finalSolrQuery);
			SolrDocumentList finalSolrDocs = finalSitesQueryResponse.getResults();
			Iterator<SolrDocument> iterator = finalSolrDocs.iterator();
			
			while (iterator.hasNext()) {
				SolrDocument solrDocument = iterator.next();
			    Iterator<Entry<String, Object>> itr = solrDocument.iterator();
			    while(itr.hasNext()) {
			    	Entry<String, Object> map = itr.next();
			    	pageQueue.add((String) map.getValue());
			    }
		}
		} catch (SolrServerException | IOException e) {
			logger.error("Error occurred in generating SiteMap from serviceImpl", e);
		}
    	
    }

	/**
     * @param pageQueue
     * @param parentPage
     */
    private void listAndAddChildren(List<String> pageQueue, Page parentPage) {
        if (null != parentPage && null != parentPage.listChildren()) {
            pageQueue.add(parentPage.getPath());
            for (Iterator<Page> children = parentPage.listChildren(new PageFilter(), true); children.hasNext();) {
                Page childPage = children.next();
                if(!checkIfHideInNav(childPage) && checkIfActivated(childPage)) {
                    pageQueue.add(childPage.getPath());
                }
            }
        }
    }

    /**
     * @param page
     */
    private boolean checkIfHideInNav(Page page) {
        boolean hideInNav = false;
        String hideInNavProp = page.getProperties().get(CommonConstants.CONST_HIDEINNAV, String.class);
        if(CommonConstants.STRING_TRUE.equalsIgnoreCase(hideInNavProp)) {
            hideInNav = true;
        }
        return hideInNav;
    }
    
    /**
	 * @param pageQueue
	 * @return
	 */
	private int getNoofSiteMap(List<String> pageQueue, final int P_DEFAULT_LIMIT) {
		int noOfSiteMap;
		if (pageQueue.size() % P_DEFAULT_LIMIT == 0) {
			noOfSiteMap = pageQueue.size() / P_DEFAULT_LIMIT;
		} else {
			noOfSiteMap = pageQueue.size() / P_DEFAULT_LIMIT + 1;
		}
		return noOfSiteMap;
	}

    /**
     * @param page
     */
    private boolean checkIfActivated(Page page) {
        boolean isActivated = false;
        String lastReplicationAction = page.getProperties().get(
                ReplicationStatus.NODE_PROPERTY_LAST_REPLICATION_ACTION, String.class);
        if(CommonConstants.REP_STATUS_ACTIVATE.equalsIgnoreCase(lastReplicationAction)) {
            isActivated = true;
        }
        return isActivated;
    }

    /**
     * @param resource
     * @param pageQueue
     * @param lastSiteMap 
     * @param count 
     * @param noOfSiteMap 
     * @param pageManager 
     */
    private void writeToSiteMapIndex(Resource resource, List<String> pageQueue, ResourceResolver resolver, Session session, int noOfSiteMap, PageManager pageManager, final int P_DEFAULT_LIMIT)
			throws XMLStreamException, RepositoryException {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		StringWriter resultIndex = new StringWriter();
		XMLStreamWriter stream = outputFactory.createXMLStreamWriter(resultIndex);
		stream.writeStartDocument(CommonConstants.UTF_ENCODING, "1.0");
		stream.writeStartElement(StringUtils.EMPTY, "sitemapindex", NS);
		stream.writeNamespace(StringUtils.EMPTY, NS);
		int count = 0;
		for (int k = 0; k < noOfSiteMap; k++) {
			Boolean lastSiteMap = count == noOfSiteMap - 1;
			StringWriter result = getXMLStream(pageQueue, resolver, count, lastSiteMap, resource, pageManager,P_DEFAULT_LIMIT);
			writeSiteMapToJCR(resource, result, P_SITEMAP_NAME + count + XML, session);
			stream.writeStartElement(NS, P_SITEMAP_NAME);
			String loc = externalizerService.getFormattedUrlForPublish((resource.getPath() + CommonConstants.SINGLE_SLASH + P_SITEMAP_NAME + count + XML),resolver);
			writeElement(stream, LOC, StringUtils.substringBeforeLast(loc, CommonConstants.DOT_HTML));
			addLastModToIndex(resource, stream, resolver, count);
			count++;
			stream.writeEndElement();
		}
		stream.writeEndElement();
		stream.writeEndDocument();
		createSiteMapIndexFile(resource, session, resultIndex);
	}
    
    /**
     * @param resource
     * @param pageQueue
     * @param lastSiteMap 
     * @param count 
     * @param noOfSiteMap 
     */
    private void writeToMasterSiteMapIndex(Resource resource, List<String> pageQueue, ResourceResolver resolver, Session session)
			throws XMLStreamException, RepositoryException {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		StringWriter resultIndex = new StringWriter();
		XMLStreamWriter stream = outputFactory.createXMLStreamWriter(resultIndex);
		stream.writeStartDocument(CommonConstants.UTF_ENCODING, "1.0");
		stream.writeStartElement(StringUtils.EMPTY, "sitemapindex", NS);
		stream.writeNamespace(StringUtils.EMPTY, NS);
		for (String pagePath : pageQueue) {
			stream.writeStartElement(NS, P_SITEMAP_NAME);
			String loc = externalizerService.getFormattedUrlForPublish(pagePath,resolver);
			//loc = StringUtils.replace(loc, resource.getPath(), "");
			writeElement(stream, LOC, StringUtils.substringBeforeLast(loc, CommonConstants.DOT_HTML));
			addLastModToIndex(pagePath, stream, resolver);
			stream.writeEndElement();
		}
		stream.writeEndElement();
		stream.writeEndDocument();
		createSiteMapIndexFile(resource, session, resultIndex);
	}
    

    /**
     * This method writes the sitemap file to JCR
     * @param res
     * @param result
     * @param siteMapName
     * @param session
     */
    public void writeSiteMapToJCR(Resource res, StringWriter result, String siteMapName, Session session)
            throws RepositoryException {
        Node node = res.adaptTo(Node.class);
        InputStream is = new ByteArrayInputStream(result.toString().getBytes(StandardCharsets.UTF_8));
        ValueFactory valueFactory = session.getValueFactory();
        Binary contentValue = valueFactory.createBinary(is);
        Node fileNode;
        Node resNode;
        if (null != node) {
            if (node.hasNode(siteMapName)) {
                fileNode = node.getNode(siteMapName);
                resNode = fileNode.getNode(JcrConstants.JCR_CONTENT);
            } else {
                fileNode = node.addNode(siteMapName, JcrConstants.NT_FILE);
                resNode = fileNode.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
            }
            fileNode.addMixin(JcrConstants.MIX_REFERENCEABLE);
            resNode.setProperty(JcrConstants.JCR_MIMETYPE, APPLICATION_XML);
            resNode.setProperty(JcrConstants.JCR_DATA, contentValue);
            Calendar lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(lastModified.getTimeInMillis());
            resNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
            session.save();
            activateContent(node.getPath() + CommonConstants.SINGLE_SLASH + siteMapName, session, replicator);
            session.refresh(true);
        }
    }

    /**
     *
     * @param pageQueue
     * @param lastSiteMap 
     * @param count 
     * @param resource 
     * @param pageManager 
     * @return lastSiteMap
     * @throws XMLStreamException
     */
    public StringWriter getXMLStream(List<String> pageQueue, ResourceResolver resolver, int count, Boolean lastSiteMap, Resource resource, PageManager pageManager,final int P_DEFAULT_LIMIT) throws XMLStreamException{
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        StringWriter result = new StringWriter();
        XMLStreamWriter stream = outputFactory.createXMLStreamWriter(result);
        stream.writeStartDocument(CommonConstants.UTF_ENCODING, "1.0");
        stream.writeStartElement(StringUtils.EMPTY, URLSET, NS);
        stream.writeNamespace(StringUtils.EMPTY, NS);
        stream.writeNamespace(XHTML, XHTML_NS);
        int limit = P_DEFAULT_LIMIT;
		if (pageQueue.size() < P_DEFAULT_LIMIT) {
			limit = pageQueue.size();
		} else if (Boolean.TRUE.equals(lastSiteMap)) {
			limit = pageQueue.size() - P_DEFAULT_LIMIT * count;
		}
		for (int j = 0; j < limit; j++) {
            String currentPagePath = pageQueue.get(count * P_DEFAULT_LIMIT + j);
            if(StringUtils.contains(currentPagePath, "pdp.")) {
            	populateDynamicXML(stream, currentPagePath, resolver,resource,pageManager);
            }else {
            	Page currentPage = pageManager.getPage(currentPagePath);
            	populateXML(stream, currentPage, resolver);
            }
        }
        stream.writeEndElement();
        stream.writeEndDocument();
        return result;
    }

    private void populateDynamicXML(XMLStreamWriter stream, String currentPagePath, ResourceResolver resolver, Resource resource, PageManager pageManager) throws XMLStreamException {
    	String formattedURL = externalizerService.getFormattedUrlForPublish(resource.getPath()+currentPagePath, resolver);
    	String stringToReplace = resource.getPath().substring(0, resource.getPath().lastIndexOf('/'));
    	formattedURL = formattedURL.replace(stringToReplace, CommonConstants.BDB_XF_RESOURCE_TYPE);
    	formattedURL = formattedURL.replace("pdp.", CommonConstants.BDB_XF_RESOURCE_TYPE);
    	formattedURL = formattedURL.replace(".html", CommonConstants.BDB_XF_RESOURCE_TYPE);
    	write(formattedURL, stream, resolver);
    	stream.writeEndElement();
	}

	/**
     * @param stream
     * @param currentPage
     * @throws XMLStreamException
     */
    private void populateXML(XMLStreamWriter stream, Page currentPage, ResourceResolver resolver)
            throws XMLStreamException {
    	String formattedURL = externalizerService.getFormattedUrlForPublish(currentPage.getPath(), resolver);
    	String strToRemove = CommonConstants.CONST_BDB_ROOT_PATH+CommonConstants.SINGLE_SLASH+CommonHelper.getRegion(currentPage)+CommonConstants.SINGLE_SLASH+CommonHelper.getCountry(currentPage);
    	formattedURL = formattedURL.replace(strToRemove, CommonConstants.BDB_XF_RESOURCE_TYPE);
    	formattedURL = formattedURL.replace(".html", CommonConstants.BDB_XF_RESOURCE_TYPE);
        write(formattedURL, stream, resolver);
        Calendar cal = currentPage.getLastModified();
        if (cal != null) {
            writeElement(stream, LASTMOD, DATE_FORMAT.format(cal));
        }
        ValueMap pageProperties=currentPage.getProperties();
        String priority=pageProperties.get(JCR_PRIORITY, StringUtils.EMPTY);
        if (!StringUtils.equals(priority, StringUtils.EMPTY)) {
            writeElement(stream, PRIORITY, priority);
        }
        String changeFreq=pageProperties.get(JCR_CHANGE_FREQ, StringUtils.EMPTY);
        if (!StringUtils.equals(changeFreq, StringUtils.EMPTY)) {
            writeElement(stream, CHANGE_FREQ, changeFreq);
        }
        stream.writeEndElement();
    }

    /**
     * @param pagePath
     * @param stream
     * @throws XMLStreamException
     */
    private void write(String pagePath, XMLStreamWriter stream, ResourceResolver resolver)
            throws XMLStreamException {
        stream.writeStartElement(NS, "url");
        List<String> pathList = new ArrayList<>();
        pathList.add(pagePath);
        writeElement(stream, LOC, pagePath);
    }

    /**
     * @param stream
     * @param elementName
     * @param text
     * @throws XMLStreamException
     */
    private void writeElement(final XMLStreamWriter stream, final String elementName, final String text)
            throws XMLStreamException {
        stream.writeStartElement(NS, elementName);
        stream.writeCharacters(text);
        stream.writeEndElement();
    }

    /**
     * @param resource
     * @param stream
     * @param count 
     * @throws XMLStreamException
     */
    private void addLastModToIndex(Resource resource, XMLStreamWriter stream, ResourceResolver resolver, int count)
            throws XMLStreamException {
        Resource resourceSiteMap = resolver.resolve(resource.getPath() + CommonConstants.SINGLE_SLASH + P_SITEMAP_NAME + count + XML + CommonConstants.SINGLE_SLASH + JcrConstants.JCR_CONTENT);
        Calendar cal = (Calendar) resourceSiteMap.getValueMap().get(JcrConstants.JCR_LASTMODIFIED);
        if (cal != null) {
            writeElement(stream, LASTMOD, DATE_FORMAT.format(cal));
        }
    }
    
    /**
     * @param resource
     * @param stream
     * @param count 
     * @throws XMLStreamException
     */
    private void addLastModToIndex(String pagePath, XMLStreamWriter stream, ResourceResolver resolver)
            throws XMLStreamException {
        Resource resourceSiteMap = resolver.resolve(pagePath+ CommonConstants.SINGLE_SLASH + JcrConstants.JCR_CONTENT);
        Calendar cal = (Calendar) resourceSiteMap.getValueMap().get(JcrConstants.JCR_LASTMODIFIED);
        if (cal != null) {
            writeElement(stream, LASTMOD, DATE_FORMAT.format(cal));
        }
    }
    
    
    /**
     * @throws RepositoryException 
     * @throws XMLStreamException 
     * 
     */
	@Override
	public void generateMasterIndexSiteMap(String[] countryPaths, ResourceResolver resolver)
			throws XMLStreamException, RepositoryException {
		Session session = null;
		if (resolver == null) {
			return;
		}
		for (String path : countryPaths) {
			Resource resource = resolver.resolve(path);
			PageManager pageManager = resolver.adaptTo(PageManager.class);
			Page page = null != pageManager ? pageManager.getContainingPage(resource) : null;
			List<String> pageQueue = new ArrayList<>();
			if (null != page && null != page.listChildren()) {
				Iterator<Page> children = page.listChildren();
				while (children.hasNext()) {
					Page childPage = children.next();
					if (null != childPage) {
						String siteMapIndexPath = childPage.getPath()
								+ (CommonConstants.SINGLE_SLASH + P_SITEMAP_INDEX_NAME);
						if(resolver.getResource(siteMapIndexPath) != null) {
							pageQueue.add(siteMapIndexPath);
						}
					}
				}
			}
			if (!pageQueue.isEmpty()) {
				session = resolver.adaptTo(Session.class);
				writeToMasterSiteMapIndex(resource, pageQueue, resolver, session);
			}
		}
		
		
	}

    /**
     * @param parentResource
     * @param session
     * @param resultIndex
     */
    public void createSiteMapIndexFile(Resource parentResource, Session session, StringWriter resultIndex)
            throws RepositoryException {
        Node node = parentResource.adaptTo(Node.class);
        InputStream is = new ByteArrayInputStream(resultIndex.toString().getBytes(StandardCharsets.UTF_8));
        ValueFactory valueFactory = session.getValueFactory();
        Node fileNode;
        Node resNode;
        if (null != node) {
            if (node.hasNode(P_SITEMAP_INDEX_NAME)) {
                fileNode = node.getNode(P_SITEMAP_INDEX_NAME);
                resNode = fileNode.getNode(JcrConstants.JCR_CONTENT);
            } else {
                fileNode = node.addNode(P_SITEMAP_INDEX_NAME, JcrConstants.NT_FILE);
                resNode = fileNode.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
            }
            fileNode.addMixin(JcrConstants.MIX_REFERENCEABLE);
            resNode.setProperty(JcrConstants.JCR_MIMETYPE, APPLICATION_XML);
            Calendar lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(lastModified.getTimeInMillis());
            resNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
            Binary contentValue = valueFactory.createBinary(is);
            resNode.setProperty(JcrConstants.JCR_DATA, contentValue);
            session.save();
            activateContent(node.getPath() + CommonConstants.SINGLE_SLASH + P_SITEMAP_INDEX_NAME,
                    session, replicator);
        }
    }

    /**
     * @param contentPath
     * @param session
     * @param replicator
     */
    private void activateContent(String contentPath, Session session, Replicator replicator){
        try {
            replicator.replicate(session, ReplicationActionType.ACTIVATE, contentPath);
        } catch(ReplicationException e) {
            logger.error("Error in Replication", e);
        }
    }


}
