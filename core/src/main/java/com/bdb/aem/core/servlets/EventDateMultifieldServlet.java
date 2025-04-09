package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
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
 * Servlet to update event Start Date and End Date for all event pages
 */
@Component(service = Servlet.class, immediate = true, configurationPolicy = ConfigurationPolicy.OPTIONAL,
		property = {Constants.SERVICE_DESCRIPTION + "=" + "Update Event start and end Dates",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_PATHS + CommonConstants.EQUAL + "/bin/updateEventDates"
})
public class EventDateMultifieldServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 5917205935317376773L;

	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(EventDateMultifieldServlet.class);

	@Reference
	transient QueryBuilder queryBuilder;

	/** The replicator. */
	@Reference
	transient  Replicator replicator;
	
	String PRIMARY_TYPE= "nt:unstructured";
	String RESOURCE_TYPE = "sling:resourceType";
	String EVENT_PAGE_ROOT_PATH = "bdb-aem/proxy/components/content/eventblogDetails";
	String PROPERTY_NAME = "selection";
	String PROPERTY_VALUE = "event";

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

		ResourceResolver resourceResolver = request.getResourceResolver();
		String DOCUMENTS_ROOT_PATH = request.getParameter("eventsBasePath");		
		Session session = resourceResolver.adaptTo(Session.class);
		SearchResult results = getAllEventPages(DOCUMENTS_ROOT_PATH, session);
		String EVENT_START_DATE = "eventStartDate";
		String EVENT_END_DATE = "eventEndDate";
		String EVENT_DATE_AND_TIME_LABEL = "eventDateAndTimeLabel";
		String eventStartDate = "";
		String eventEndDate = "";
		int count = 0;
		try {
			if (null != results) {
				for (Hit hit : results.getHits()) {
					Resource eventResource;
					String MULTIFIELD_EVENT_START_DATE = "multifieldEventStartDate";
					String MULTIFIELD_EVENT_END_DATE = "multifieldEventEndDate";
					eventResource = hit.getResource();
					if (null != eventResource) {
						ValueMap properties = eventResource.adaptTo(ValueMap.class);
						eventStartDate = (properties.containsKey(EVENT_START_DATE)) ? properties.get(EVENT_START_DATE, String.class) : "";
						eventEndDate = (properties.containsKey(EVENT_END_DATE)) ? properties.get(EVENT_END_DATE, String.class) : "";
						Node eventNode = eventResource.adaptTo(Node.class);
						if(!eventNode.hasNode(EVENT_DATE_AND_TIME_LABEL) && null != eventStartDate && null != eventEndDate){
							Node multifieldItemNode = eventNode.addNode(EVENT_DATE_AND_TIME_LABEL).addNode("item0");
							multifieldItemNode.setProperty(MULTIFIELD_EVENT_START_DATE, eventStartDate);
							multifieldItemNode.setProperty(MULTIFIELD_EVENT_END_DATE, eventEndDate);
							resourceResolver.commit();
							count++;
							replicator.replicate(session,ReplicationActionType.ACTIVATE, multifieldItemNode.getPath());
						}
						
						
					}
				}
			}
		}
		catch (RepositoryException | ReplicationException e) {
			logger.error("Exception in updating date for Event Pages:", e);
		} finally {
			response.getWriter().println("Successfully updated Nodes for event start date and end date: "+count);
			response.getWriter().println("Total no of event Pages : "+results.getHits().size());
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
	public SearchResult getAllEventPages(String resourcePath, Session session) {
			
		Map<String, String> params = new HashMap<>();
		params.put(SolrSearchConstants.QUERY_BUILDER_PATH, resourcePath);
		params.put(SolrSearchConstants.QUERY_BUILDER_TYPE, PRIMARY_TYPE);
		params.put(SolrSearchConstants.QUERY_BUILDER_1_PROPERTY, RESOURCE_TYPE);
		params.put(SolrSearchConstants.QUERY_BUILDER_1_PROPERTY_VALUE, EVENT_PAGE_ROOT_PATH);
		params.put(SolrSearchConstants.QUERY_BUILDER_2_PROPERTY, PROPERTY_NAME);
		params.put(SolrSearchConstants.QUERY_BUILDER_2_PROPERTY_VALUE, PROPERTY_VALUE);
		params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");

		Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);
		
		return query.getResult();
	}
}
