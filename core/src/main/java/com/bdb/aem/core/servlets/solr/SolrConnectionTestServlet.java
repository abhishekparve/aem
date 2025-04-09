package com.bdb.aem.core.servlets.solr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.servlets.TilesDataServlet;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.impl.BaseRequestImpl;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.util.CommonConstants;

@SuppressWarnings("CQRules:CQBP-75")
@Component(service = Servlet.class, configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true, property = {
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + SolrConnectionTestServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_GET })
public class SolrConnectionTestServlet extends SlingAllMethodsServlet {
	private static final Logger LOG = LoggerFactory.getLogger(SolrConnectionTestServlet.class);
	public static final String RESOURCE_TYPE = "bdb/test-solar";
	private static final long serialVersionUID = 1L;

	@Reference
	private transient RestClient restClient;
	
	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		String output = StringUtils.EMPTY;
		String type = request.getParameter("case");
		String instanceId = StringUtils.isNotEmpty(request.getParameter("instance-id"))?request.getParameter("instance-id"):"stgsearch";
		try {
			if(StringUtils.equalsIgnoreCase(type, "solr-without-proxy")) {
				SolrClient client = new HttpSolrClient.Builder("https://"+instanceId+".bdbiosciences.com/solr/bdbio-us").build();
				SolrQuery queryToBeExecuted = new SolrQuery();
				queryToBeExecuted.setQuery("materialNumber_t:561649");
				QueryResponse queryResponse =client.query(queryToBeExecuted);
				output = String.valueOf(queryResponse.getResults().getNumFound());
			} else if(StringUtils.equalsIgnoreCase(type, "solr-with-proxy")) {
				SolrClient client = new HttpSolrClient.Builder("https://"+instanceId+".bdbiosciences.com/solr/bdbio-us").withHttpClient(restClient.getHttpClient()).build();
				SolrQuery queryToBeExecuted = new SolrQuery();
				queryToBeExecuted.setQuery("materialNumber_t:561649");
				QueryResponse queryResponse =client.query(queryToBeExecuted);
				output = String.valueOf(queryResponse.getResults().getNumFound());
			} else if(StringUtils.equalsIgnoreCase(type, "ip-with-proxy")) {
				final Map<String, String> requestHeaderMap = new HashMap<>();
				final BaseRequest baseRequest = new BaseRequestImpl("https://ifconfig.me/ip", HttpMethodType.GET, "");
				BaseResponse baseResponse = restClient.sendRequest(baseRequest, requestHeaderMap);
				if (!baseResponse.hasError()) {
					output = baseResponse.getResponseData().getData();
				} else {
					output = "has error";
				}
			} else {
				output = "stop dreaming and give proper parameter";
			}
		} catch (AemInternalServerErrorException  | SolrServerException e) {
			LOG.error("Exception in doGet method {}" , e.getMessage());
		}
		response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
		response.getWriter().println(output);
	}

}
