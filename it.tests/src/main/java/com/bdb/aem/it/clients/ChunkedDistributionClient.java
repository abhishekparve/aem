package com.bdb.aem.it.clients;

import static org.apache.http.HttpStatus.SC_OK;

import java.net.URI;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.testing.clients.ClientException;
import org.apache.sling.testing.clients.SlingClientConfig;
import org.apache.sling.testing.clients.SlingHttpResponse;
import org.apache.sling.testing.clients.util.FormEntityBuilder;
import org.apache.sling.testing.clients.util.HttpUtils;

import com.adobe.cq.testing.client.CQClient;

/**
 * <p>
 * Testing client to support the BDB Chunked Distribution functionality.
 * </p>
 * 
 * @author ronbanerjee
 *
 */
public class ChunkedDistributionClient extends CQClient {

	/**
	 * @see CQClient
	 */
	public ChunkedDistributionClient(CloseableHttpClient http, SlingClientConfig config) throws ClientException {
		super(http, config);
	}

	protected static final String SLING_DISTRIBUTION_TREE_PATH = "/libs/sling/distribution/tree";
	
	/**
	 * @see CQClient
	 */
	public ChunkedDistributionClient(URI serverUrl, String user, String password) throws ClientException {
		super(serverUrl, user, password);
	}

	/**
	 * Distributes (publish) a node.
	 *
	 * @param nodePath       path of the node to activate
	 * @param expectedStatus list of expected HTTP status to be returned, if not
	 *                       set, 200 is assumed.
	 *
	 * @return the response
	 * @throws ClientException if something fails during the request/response cycle
	 */
	public SlingHttpResponse distribute(String nodePath, String chunkSize, String mode, int... expectedStatus)
			throws ClientException {
		return doPost(SLING_DISTRIBUTION_TREE_PATH,
				FormEntityBuilder.create().addParameter("path", nodePath).addParameter("mode", mode)
						.addParameter("chunkSize", chunkSize).build(),
				HttpUtils.getExpectedStatus(SC_OK, expectedStatus));
	}
}
