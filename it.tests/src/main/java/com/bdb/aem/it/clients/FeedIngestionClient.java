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
 * Testing client to support the BDB Feed Ingestion functionality.
 * </p>
 * 
 * @author ronbanerjee
 *
 */
public class FeedIngestionClient extends CQClient {
	
	/**
	 * @see CQClient
	 */
	public FeedIngestionClient(CloseableHttpClient http, SlingClientConfig config) throws ClientException {
		super(http, config);
	}
	
	/**
	 * @see CQClient
	 */
	public FeedIngestionClient(URI serverUrl, String user, String password) throws ClientException {
		super(serverUrl, user, password);
	}


	/**
	 * Ingests HP Feed.
	 * 
	 * @param expectedStatus the expected status
	 * @return the Sling Http Response
	 * @throws ClientException if the client could not be created
	 * @throws UnsupportedEncodingException if the encoding is not a supported encoding type
	 */
	public SlingHttpResponse ingestHPFeed(int... expectedStatus) throws ClientException, UnsupportedEncodingException {
		String hpFeed = StringUtils.EMPTY;
		try {
			hpFeed = IOUtils.toString(ResourceUtil.getResourceAsStream("/com/bdb/aem/testing/junit/clients/feed-ingestion/hp.json"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			Assert.fail("IOException occurred");
		}
		return doPost("/bin/catalogUpdatePathServlet.hp.html", new StringEntity(hpFeed),
				HttpUtils.getExpectedStatus(SC_OK, expectedStatus));
	}
	
	/**
	 * Ingests CCV2 Feed.
	 * 
	 * @param expectedStatus the expected status
	 * @return the Sling Http Response
	 * @throws ClientException if the client could not be created
	 * @throws UnsupportedEncodingException if the encoding is not a supported encoding type
	 */
	public SlingHttpResponse ingestCCV2Feed(int... expectedStatus) throws UnsupportedEncodingException, ClientException {
		String ccv2Feed = StringUtils.EMPTY;
		try {
			ccv2Feed = IOUtils.toString(ResourceUtil.getResourceAsStream("/com/bdb/aem/testing/junit/clients/feed-ingestion/ccv2Feed.json"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			Assert.fail("IOException occurred");
		}
		return doPost("/bin/catalogUpdatePathServlet.sap.html", new StringEntity(ccv2Feed),
				HttpUtils.getExpectedStatus(SC_OK, expectedStatus));
	}

}
