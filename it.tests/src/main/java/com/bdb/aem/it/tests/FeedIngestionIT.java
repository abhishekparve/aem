package com.bdb.aem.it.tests;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import org.apache.sling.testing.clients.ClientException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import com.adobe.cq.testing.client.CQClient;
import com.adobe.cq.testing.junit.rules.CQAuthorPublishClassRule;
import com.bdb.aem.it.clients.DeleteProductClient;
import com.bdb.aem.it.clients.FeedIngestionClient;

/**
 * <p>
 * Functional Test to validate the BDB Feed Ingestion Functionality. This
 * validates a) HP Feed b) CCV2 Feed.
 * </p>
 * 
 * @author ronbanerjee
 *
 */
public class FeedIngestionIT {

	@ClassRule
	public static final CQAuthorPublishClassRule cqBaseClassRule = new CQAuthorPublishClassRule();

	static CQClient cqAdminAuthor;

	static FeedIngestionClient feedClient;

	static DeleteProductClient deletionClient;

	private static final String HP_NODE_PATH = "/content/commerce/products/bdb/functional-test/000xxx/0000xx/000000_base";

	private static final String TEST_PRODUCT_FOLDER ="/content/commerce/products/bdb/functional-test";
	
	private static final String TEST_TAG_FOLDER ="/content/cq:tags/bdb/functional-test";


	/**
	 * Creates Admin client for author, publish, replication before executing the
	 * test.
	 * 
	 * 
	 * @throws ClientException
	 */
	@BeforeClass
	public static void setup() throws ClientException {
		cqAdminAuthor = cqBaseClassRule.authorRule.getAdminClient(CQClient.class);
		feedClient = cqAdminAuthor.adaptTo(FeedIngestionClient.class);
		deletionClient = cqAdminAuthor.adaptTo(DeleteProductClient.class);
	}
	
	
	@AfterClass
	public static void after() throws ClientException, TimeoutException, InterruptedException {

		cqAdminAuthor.deletePath(TEST_PRODUCT_FOLDER, 200);
		cqAdminAuthor.deletePath(TEST_TAG_FOLDER, 200);
	}

	/**
	 * Tests the HP Feed Ingestion Functionality.
	 * 
	 * @throws ClientException              if there is any Client Exception
	 * @throws TimeoutException             if there is any Time out Exception
	 * @throws InterruptedException         if there is any Interrupted Exception
	 * @throws UnsupportedEncodingException if there is any Unsupported Encoding
	 *                                      Exception
	 */
	@Test
	public void runHpFeed()
			throws ClientException, TimeoutException, InterruptedException, UnsupportedEncodingException {

		cqAdminAuthor.doGet(HP_NODE_PATH, 403, 404);
		feedClient.ingestHPFeed(200);
		cqAdminAuthor.waitExists(HP_NODE_PATH, 10000, 1000);

	}

	/**
	 * Tests the CCv2 Feed Ingestion Functionality.
	 * 
	 * @throws ClientException              if there is any Client Exception
	 * @throws TimeoutException             if there is any Time out Exception
	 * @throws InterruptedException         if there is any Interrupted Exception
	 * @throws UnsupportedEncodingException if there is any Unsupported Encoding
	 *                                      Exception
	 */
	@Test
	public void runCCV2Feed()
			throws ClientException, TimeoutException, InterruptedException, UnsupportedEncodingException {

		feedClient.ingestCCV2Feed(200);
	}

	/**
	 * Deletes the variant node, so as to eliminate all traces from the instance.
	 * 
	 * @throws UnsupportedEncodingException if there is an unsupported encoding
	 * @throws ClientException              if there is a client exception
	 */
	@Test
	public void deleteVariantNode() throws UnsupportedEncodingException, ClientException {
		deletionClient.deleteVariant("000000", 200);

	}

	/**
	 * Deletes the base node, so as to eliminate all traces from the instance.
	 * 
	 * @throws UnsupportedEncodingException if there is an unsupported encoding
	 * @throws ClientException              if there is a client exception
	 */
	@Test
	public void deleteBaseNode() throws UnsupportedEncodingException, ClientException {
		deletionClient.deleteBase("000000", 200);

	}

	/**
	 *  Deletes/Un-index the test data from Apache SOLR.
	 * 
	 * @throws UnsupportedEncodingException if there is an unsupported encoding exception
	 * @throws ClientException if there is a client exception
	 */
	@Test
	public void deleteProductFromSolr() throws UnsupportedEncodingException, ClientException {
		deletionClient.deleteProductFromSolr("000000", 200);

	}

	
	
}
