package com.bdb.aem.it.clients;

import static org.apache.http.HttpStatus.SC_OK;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.testing.clients.ClientException;
import org.apache.sling.testing.clients.SlingClientConfig;
import org.apache.sling.testing.clients.SlingHttpResponse;
import org.apache.sling.testing.clients.util.HttpUtils;
import org.apache.sling.testing.clients.util.ResourceUtil;
import org.junit.Assert;

import com.adobe.cq.testing.client.CQClient;

/**
 * <p>
 * Testing Client for Apache SOLR.
 * </p>
 * 
 * @author ronbanerjee
 *
 */
public class SolrQueryClient extends CQClient {
	/**
	 * @see CQClient
	 */
	public SolrQueryClient(CloseableHttpClient http, SlingClientConfig config) throws ClientException {
		super(http, config);
	}

	/**
	 * @see CQClient
	 */
	public SolrQueryClient(URI serverUrl, String user, String password) throws ClientException {
		super(serverUrl, user, password);
	}

	/**
	 * Hit a query to Apache SOLR.
	 * 
	 * @param expectedStatus the expected status
	 * @return the Sling Http Response
	 * @throws ClientException              if the client could not be created
	 * @throws UnsupportedEncodingException if the encoding is not a supported
	 *                                      encoding type
	 */
	public SlingHttpResponse doSolrSearch(int... expectedStatus) throws ClientException, UnsupportedEncodingException {
		String query = StringUtils.EMPTY;
		try {
			query = IOUtils.toString(
					ResourceUtil.getResourceAsStream("/com/bdb/aem/testing/junit/clients/solr/us.json"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			Assert.fail("IOException occurred");
		}
		return doPost("/content/bdb/paths/fetch-data.json", new StringEntity(query),
				HttpUtils.getExpectedStatus(SC_OK, expectedStatus));
	}

}
