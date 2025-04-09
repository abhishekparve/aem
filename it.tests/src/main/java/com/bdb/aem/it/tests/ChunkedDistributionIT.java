package com.bdb.aem.it.tests;

import java.util.concurrent.TimeoutException;

import org.apache.sling.testing.clients.ClientException;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import com.adobe.cq.testing.client.CQClient;
import com.adobe.cq.testing.junit.rules.CQAuthorPublishClassRule;
import com.bdb.aem.it.clients.ChunkedDistributionClient;

/**
 * <p>
 * Integration Test to validate BDB Chunked Distribution Functionality.
 * </p>
 * 
 * @author ronbanerjee
 *
 */
public class ChunkedDistributionIT {

	private static final String NODE_PATH = "/content/commerce/products/bdb/products/blood-collection/blood-collection-tubes";

	private static final String MODE = "AllNodes";

	private static final String CHUNK_SIZE = "50";

	@ClassRule
	public static final CQAuthorPublishClassRule cqBaseClassRule = new CQAuthorPublishClassRule();

	static  CQClient cqAdminAuthor;

	static CQClient cqAdminPublish;

	static ChunkedDistributionClient distClient;

	/**
	 * Creates Admin client for author, publish, replication before executing the
	 * test.
	 * 
	 * @throws ClientException if there is any client exception
	 */
	@BeforeClass
	public static void setup() throws ClientException {
		cqAdminAuthor = cqBaseClassRule.authorRule.getAdminClient(CQClient.class);
		cqAdminPublish = cqBaseClassRule.publishRule.getAdminClient(CQClient.class);
		distClient = cqAdminAuthor.adaptTo(ChunkedDistributionClient.class);
	}

	/**
	 * Tests the Chunked Distribution.
	 * 
	 * 
	 * @throws ClientException      if there is a client exception
	 * @throws TimeoutException     if there is a time out exception
	 * @throws InterruptedException if there is an interrupted exception
	 */
	@Test
	public void distributePage() throws ClientException, TimeoutException, InterruptedException {

		// Uncomment the below line, if there is a new path being created during
		// functional test. For this case NODE_PATH should already exist in publish
		// cqAdminPublish.doGet(NODE_PATH, 403, 404);
		distClient.distribute(NODE_PATH, CHUNK_SIZE, MODE, 200);

	}
}
