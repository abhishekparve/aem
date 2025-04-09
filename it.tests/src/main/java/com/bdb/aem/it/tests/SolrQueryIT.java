package com.bdb.aem.it.tests;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.http.ParseException;
import org.apache.sling.testing.clients.ClientException;
import org.apache.sling.testing.clients.SlingHttpResponse;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import com.adobe.cq.testing.client.CQClient;
import com.adobe.cq.testing.junit.rules.CQAuthorPublishClassRule;
import com.bdb.aem.it.clients.SolrQueryClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * <p>
 * Integration Test to validate the Solr Queries.
 * </p>
 * 
 * @author ronbanerjee
 *
 */
public class SolrQueryIT {

	@ClassRule
	public static final CQAuthorPublishClassRule cqBaseClassRule = new CQAuthorPublishClassRule();

	static CQClient cqAdminAuthor;

	static SolrQueryClient solrClient;

	/**
	 * Creates Admin client for author, SOLR before executing the test.
	 * 
	 * @throws ClientException
	 */
	@BeforeClass
	public static void setup() throws ClientException {
		cqAdminAuthor = cqBaseClassRule.authorRule.getAdminClient(CQClient.class);
		solrClient = cqAdminAuthor.adaptTo(SolrQueryClient.class);
	}

	/**
	 * Tests the Solr Query Functionality.
	 * 
	 * @throws ClientException      if there is any Client Exception
	 * @throws TimeoutException     if there is any Time out Exception
	 * @throws InterruptedException if there is any Interrupted Exception
	 * @throws IOException          if there is any IOException
	 * @throws ParseException       if there is any Parsing Exception
	 */
	@Test
	public void testSolrQuery()
			throws ClientException, TimeoutException, InterruptedException, ParseException, IOException {

		SlingHttpResponse r = solrClient.doSolrSearch(200);
		String res = r.getContent();
		JsonObject jobj = new JsonParser().parse(res).getAsJsonObject();

		JsonObject j = jobj != null ? jobj.getAsJsonObject("response") : null;
		int results = j != null && j.get("totalResults") != null ? j.get("totalResults").getAsInt() : 0;
		if (results == 0) {
			throw new ClientException("Results count for Solr Response is 0.");
		}
	}
}
