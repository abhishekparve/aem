package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

/**
 * Servlet to update metadata description for all PDF documents
 */
@Component(service = Servlet.class, immediate = true, configurationPolicy = ConfigurationPolicy.OPTIONAL,
		property = {Constants.SERVICE_DESCRIPTION + "=" + "Update All PDF Documents Description",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_PATHS + CommonConstants.EQUAL + "/bin/updateMetadataDescription"
})
public class UpdateMetadataDescriptionServlet extends SlingAllMethodsServlet {

	
	private static final long serialVersionUID = -6007528978474149150L;

	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(UpdateMetadataDescriptionServlet.class);

	@Reference
	transient QueryBuilder queryBuilder;

	/** The replicator. */
	@Reference
	transient  Replicator replicator;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

		ResourceResolver resourceResolver = request.getResourceResolver();
		String DOCUMENTS_ROOT_PATH = request.getParameter("pdfBasePath");
		//String DOCUMENTS_ROOT_PATH = "/content/dam/bdb/products/global";
		String PDF_METADATA_PATH = "jcr:content/metadata";
		String DC_DESCRIPTION = "";
		Session session = resourceResolver.adaptTo(Session.class);
		SearchResult results = getAllPdfs(DOCUMENTS_ROOT_PATH, session);
		String docTitle = "";
		String pdfType = "";
		String pdfxDocType = "";
		String docSku = "";
		int updateDescriptionCount = 0;
		int updatePdfxDoctypeCount = 0;
		try {
			if (null != results) {
				for (Hit hit : results.getHits()) {
					Resource pdfResource;
					pdfResource = hit.getResource();
					if (null != pdfResource && pdfResource.hasChildren()
							&& null != pdfResource.getChild(PDF_METADATA_PATH)) {
						Resource metadataResource = pdfResource.getChild(PDF_METADATA_PATH);
						ModifiableValueMap properties = metadataResource.adaptTo(ModifiableValueMap.class);
						
						/*
						 * This code is used to update description of pdf document.
						 */
						docTitle = (properties.containsKey(SolrSearchConstants.DOC_TITLE)) ? properties.get(SolrSearchConstants.DOC_TITLE, String.class) : "";
						pdfType = (properties.containsKey(SolrSearchConstants.DOCUMENT_TYPE)) ? properties.get(SolrSearchConstants.DOCUMENT_TYPE, String.class) : "";
						if(pdfType.contains("TDS") || pdfType.contains("Technical Data Sheet")) {
							pdfType = "TDS";			
						}
						if(pdfType.contains("IFU") || pdfType.contains("Instruction for use")) {
							pdfType = "IFU";			
						}
						docSku = (properties.containsKey(SolrSearchConstants.DOC_SKU)) ? properties.get(SolrSearchConstants.DOC_SKU, String.class) : "";
						DC_DESCRIPTION = (properties.containsKey("dc:description")) ? properties.get("dc:description", String.class) : "";
						String defaultDescription = docTitle + " - "+ pdfType + " "+ docSku;
						if(pdfType.equals("TDS") || pdfType.equals("IFU")) {
							if (!defaultDescription.equals(DC_DESCRIPTION)) {
								updateDescriptionCount++;
								properties.put("dc:description", defaultDescription);
							}
						}
						
						
						/*
						 * This code is to update pdfx:DocType metadata property same as docType.
						 */
						pdfType = (properties.containsKey(SolrSearchConstants.DOCUMENT_TYPE)) ? properties.get(SolrSearchConstants.DOCUMENT_TYPE, String.class) : "";
						pdfxDocType = (properties.containsKey(SolrSearchConstants.PRODUCT_PDFX_DOC_TYPE)) ? properties.get(SolrSearchConstants.PRODUCT_PDFX_DOC_TYPE, String.class) : "";
						if(pdfType.equals("Technical data sheet (TDS)") || pdfType.equals("Instruction for use (IFU)")) {
							if(!pdfxDocType.equals(pdfType)) {
								updatePdfxDoctypeCount++;
								properties.put(SolrSearchConstants.PRODUCT_PDFX_DOC_TYPE, pdfType);
								
							}
						}
						resourceResolver.commit();
						replicator.replicate(session,ReplicationActionType.ACTIVATE, pdfResource.getPath());
					}
				}
			}
		}
		catch (RepositoryException | ReplicationException e) {
			logger.error("Exception in updating pdf Description/pdfxDocType for pdfs:", e);
		} finally {
			response.getWriter().println("Successfully updated pdf Description for "+updateDescriptionCount+ " PDFs!");
			response.getWriter().println("Successfully updated pdfxDocType for "+updatePdfxDoctypeCount+ " PDFs!");
		}
		
		if (session.isLive()) {
			session.logout();
		}
	}
	
	/**
	 * 
	 * @param resourcePath
	 * @param session
	 * @return
	 */
	public SearchResult getAllPdfs(String resourcePath, Session session) {
			
		Map<String, String> params = new HashMap<>();
		params.put(SolrSearchConstants.QUERY_BUILDER_PATH, resourcePath);
		params.put(SolrSearchConstants.QUERY_BUILDER_TYPE, SolrSearchConstants.JCR_PRIMARY_TYPE_DAM_ASSETS);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY, SolrSearchConstants.JCR_CONTENT_METADATA_DC_FORMAT);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY_VALUE, SolrSearchConstants.PDF_FILE_FORMAT);
		params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");

		Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);
		
		return query.getResult();
	}
}