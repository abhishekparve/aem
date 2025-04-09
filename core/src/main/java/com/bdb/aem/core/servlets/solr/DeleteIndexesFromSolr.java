package com.bdb.aem.core.servlets.solr;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.servlets.BaseServlet;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Class DeleteIndexesFromSolr.
 *
 * @author This servlet deletes all the indexes from the configured Solr server
 */
@SuppressWarnings("CQRules:CQBP-75")
@Component(service = Servlet.class, configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
		property = {Constants.SERVICE_DESCRIPTION + "=" + "Solr Delete Index",
				ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
				ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + DeleteIndexesFromSolr.RESOURCE_TYPE

		})
public class DeleteIndexesFromSolr extends BaseServlet {

	public static final String RESOURCE_TYPE = "bdb/admin/delete-all-indexes";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(DeleteIndexesFromSolr.class);

	/** The solr config. */
	@Reference
	transient BDBSearchEndpointService solrConfig;

	/** The Solr search service. */
	@Reference
	transient SolrSearchService solrSearchService;

	/**
	 * Do post.
	 *
	 * @param reqest   the reqest
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		try {
			String docType = getRequestObjectString(request);
			if (StringUtils.isNotBlank(docType)) {
				ArrayList<HttpSolrClient> solrClients = (ArrayList<HttpSolrClient>) solrSearchService
						.getAllSolrClients();
				for (HttpSolrClient server : solrClients) {
					server.deleteByQuery(docType);
					server.commit();
					server.close();
				}
			}
			response.getWriter().write("Deleted all the indexes from solr server");
		} catch (SolrServerException | LoginException e) {
			LOG.error("Exception due to", e);
			response.getWriter().write("Exception in deleting all the indexes from solr server");
		}

	}
}
