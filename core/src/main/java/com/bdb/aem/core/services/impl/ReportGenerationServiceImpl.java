package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.bean.AssetBean;
import com.bdb.aem.core.config.ReportGenerateConfig;
import com.bdb.aem.core.pojo.MediaItem;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ReportGenerationService;
import com.bdb.aem.core.services.SendEmailService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.bdb.aem.core.util.WorkflowHelper;
import com.day.cq.search.Query;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component(immediate = true, service = ReportGenerationService.class)
@Designate(ocd = ReportGenerateConfig.class)
public class ReportGenerationServiceImpl implements ReportGenerationService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportGenerationServiceImpl.class);

	private String emailTemplate;

	private String fromEmail;

	private String subject;

	private String message;
	
	private String commerceContentPath;

	@Reference
	private List<AssetBean> assetBean;

	@Reference
	private ResourceResolverFactory resolverFactory;

	@Reference
	private SendEmailService sendEmailService;

	@Reference
	private SendEmailService emailService;

	private String reportRecipients;

	@Reference
	BDBSearchEndpointService searchConfig;

	@Reference
	private SolrSearchService solrSearchService;

	/**
	 * Activate method.
	 *
	 * @param config
	 */
	@Activate
	@Modified
	public void activate(ReportGenerateConfig config) {
		LOG.debug("ReportGenerationServiceImpl Activate()");
		this.fromEmail = config.fromEmail();
		this.reportRecipients = config.reportRecipientUserGroup();
		this.commerceContentPath = config.commerceContentPath();
		this.subject = config.reportRecipientUserGroup();
		this.message = config.reportRecipientUserGroup();
		this.emailTemplate = config.resourceMissingEmailTemplate();
	}

	/**
	 * Fetch Reports for missing Assets.
	 */
	@Override
	public void fetchReportData() {
		LOG.debug("Entering ReportGenerationServiceImpl fetchReportData()");
		ResourceResolver resolver = null;
		try {
			resolver = CommonHelper.getReadServiceResolver(resolverFactory);
			sendReport(resolver);
		} catch (LoginException e) {
			LOG.error("LoginException: Error in ReportGenerationServiceImpl.fetchReportData {}",
					e.getCause().getMessage());
		} catch (RepositoryException e) {
			LOG.error("RepositoryException: Error in ReportGenerationServiceImpl.fetchReportData {}",
					e.getCause().getMessage());
		} catch (IOException e) {
			LOG.error("IOException: Error in ReportGenerationServiceImpl.fetchReportData {}",
					e.getCause().getMessage());
		} catch (MessagingException e) {
			LOG.error("MessagingException: Error in ReportGenerationServiceImpl.fetchReportData {}",
					e.getCause().getMessage());
		} catch (EmailException e) {
			LOG.error("EmailException: Error in ReportGenerationServiceImpl.fetchReportData {}",
					e.getCause().getMessage());
		} finally {
			if (null != resolver && resolver.isLive()) {
				resolver.close();
			}
		}
		LOG.debug("Exiting ReportGenerationServiceImpl fetchReportData()");
	}

	/**
	 * Method to call the document generation for missing assets and and sending to
	 * respective group email.
	 *
	 * @param resourceResolver for the resource resolver
	 * @throws RepositoryException the repository exception
	 * @throws IOException         for the file operations
	 * @throws MessagingException  the repository exception
	 * @throws EmailException
	 */
	private void sendReport(ResourceResolver resourceResolver)
			throws RepositoryException, IOException, MessagingException, EmailException {
		LOG.debug("Entering ReportGenerationServiceImpl sendReport()");
		String finalStr = buildSolrQuery(resourceResolver);
		sendDocument(resourceResolver, finalStr);
		LOG.debug("Exiting ReportGenerationServiceImpl sendReport() {}");
	}

	/**
	 * Method to query repository retrieve nodes for specific properties.
	 *
	 * @param resourceResolver for the resource resolver
	 * @throws RepositoryException the repository exception
	 */
	private String buildSolrQuery(ResourceResolver resourceResolver) {
		LOG.debug("Entering ReportGenerationServiceImpl buildSolrQuery()");
		Map<String, String> params = new HashMap<>();
		params.put(SolrSearchConstants.QUERY_BUILDER_PATH, commerceContentPath);
		params.put(SolrSearchConstants.QUERY_BUILDER_TYPE, searchConfig.getCatalogStructureNodeType());
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY, SolrSearchConstants.IS_VARIANT);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY_VALUE, "true");
		params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");
		Query query = CommonHelper.getQuery(resourceResolver, params);
		SearchResult resultSet = query.getResult();
		LOG.debug("Resultset hits {}", resultSet.getHits().size());
		SolrQuery solrQuery = new SolrQuery();
		HttpSolrClient server = solrSearchService.solrClient("us");
		solrQuery.addFilterQuery("thumbnailImage_t:" + "\"/content/dam/bdb/general/placeholder/placeholder.png\"");
		solrQuery.addField("materialNumber_t");
		solrQuery.addField("hpNodePath_t");
		solrQuery.setQuery("*:*");
		solrQuery.setRows(resultSet.getHits().size());
		String returnStr = StringUtils.EMPTY;
		try {
			QueryResponse sitesQueryResponse = server.query(solrQuery);
			SolrDocumentList sitesSolrDocs = sitesQueryResponse.getResults();
			returnStr = getHits(resourceResolver,sitesSolrDocs);
		} catch (SolrServerException | IOException e) {
			LOG.error("Error in ReportGenerationServiceImpl.buildSolrQuery {}",
					e.getCause().getMessage());
		}
		return returnStr;
	}

	/**
	 * Method to process JCR query results to filter missing assets
	 *
	 * @param resourceResolver for the resource resolver
	 * @param hits             of individual nodes returned from commerce media
	 *                         data.
	 * @param assets           of assets for the capturing missing assets and their
	 *                         properties.
	 * @throws RepositoryException the repository exception
	 */
	private String getHits(ResourceResolver resourceResolver, SolrDocumentList sitesSolrDocs) {
		StringBuilder finalString = new StringBuilder();
		finalString.append("Product/Material Number,Product Path").append(CommonConstants.NEWLINE);
		Iterator<SolrDocument> iterator = sitesSolrDocs.iterator();
		JsonArray facetArray = new JsonArray();
		while(iterator.hasNext()) {
			SolrDocument solrDocument = iterator.next();
			Iterator<Map.Entry<String, Object>> itr = solrDocument.iterator();
			JsonObject facetObject = new JsonObject();
			while (itr.hasNext()) {
				Map.Entry<String, Object> map = itr.next();
				facetObject.add(map.getKey(), new Gson().toJsonTree(map.getValue()));
			}
			facetArray.add(facetObject);
		}
		generateCSVFile(facetArray, finalString);
		LOG.debug("Exiting ReportGenerationServiceImpl");
		return finalString.toString();
	}

	// Remove this method during code push
	@Override
	public List<AssetBean> fetchTestReportData() {
		LOG.debug("Entering ReportGenerationServiceImpl fetchTestReportData()");
		fetchReportData();
		LOG.debug("Exiting ReportGenerationServiceImpl fetchTestReportData()");
		return new ArrayList<>(assetBean);
	}

	/**
	 * Method to generate CVS file.
	 *
	 * @param assets List of missing assets
	 * @return File CVS File containing list of missing files.
	 */
	private StringBuilder generateCSVFile(JsonArray facetArray, StringBuilder finalString) {
		LOG.debug("Entering ReportGenerationServiceImpl generateCSVFile()");
		for(JsonElement obj : facetArray) {
			JsonObject jobj = (JsonObject)obj;
			finalString.append(jobj.get("materialNumber_t"));
			finalString.append(CommonConstants.COMMA);
			finalString.append(jobj.get("hpNodePath_t"));
			finalString.append(CommonConstants.NEWLINE);
			 
		}
		return finalString;
	}

	/**
	 * Method to call email service and confirm message.
	 *
	 * @param resourceResolver for the resource resolver
	 */
	private void sendDocument(ResourceResolver resourceResolver, String attachment)
			throws IOException, RepositoryException, MessagingException, EmailException {
		LOG.debug("Entering ReportGenerationServiceImpl sendDocument()");
		List<String> failureList = sendEmail(attachment, resourceResolver);
		if (null == failureList || failureList.isEmpty()) {
			LOG.debug("Email sent successfully to the recipients ");
		} else {
			LOG.error("Error sending email to {}", failureList);
		}
		LOG.debug("Exiting ReportGenerationServiceImpl sendDocument()");
	}

	/**
	 * Method to call email service.
	 *
	 * @param attachment       CVS file with list of missing assets.
	 * @param resourceResolver for the resource resolver
	 */
	private List<String> sendEmail(String attachment, ResourceResolver resourceResolver)
			throws RepositoryException, IOException, MessagingException, EmailException {
		LOG.debug("Entering ReportGenerationServiceImpl sendEmail() {}", reportRecipients);
		Map<String, String> emailParams = new HashMap<>();
		emailParams.put("from", fromEmail);
		emailParams.put("subject", subject);
		emailParams.put("message", message);
		Resource templateRsrc = resourceResolver.getResource(emailTemplate);
		List<String> users = new ArrayList<>(
				(WorkflowHelper.getRecipientsDetails(resourceResolver, Arrays.asList(reportRecipients)).keySet()));
		String[] recipientArray = users.toArray(new String[users.size()]);
		// Get the Excel Report as Email attachment Map<String, DataSource>
		// Get the Excel Report as Email attachment
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime currentDate = LocalDateTime.now();
		Map<String, DataSource> attachments = new HashMap<>();
		attachments.put("BDB-missing-image-list-" + dtf.format(currentDate) + ".csv",
				new ByteArrayDataSource(attachment, "text/csv"));
		LOG.debug("Exiting ReportGenerationServiceImpl sendEmail()");
		return emailService.sendEmailWithAttachment(templateRsrc, emailParams, attachments, recipientArray, subject);
	}
}
