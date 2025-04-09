package com.bdb.aem.it.tests;

import java.util.concurrent.TimeoutException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.testing.clients.ClientException;
import org.apache.sling.testing.clients.SlingClientConfig;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.adobe.cq.testing.client.CQClient;
import com.adobe.cq.testing.client.ReplicationClient;
import com.adobe.cq.testing.junit.rules.CQAuthorPublishClassRule;
import com.adobe.cq.testing.junit.rules.Page;
import com.bdb.aem.it.rules.BDBSamplePage;

/**
 * <p>
 * Integration Test to create a page and replicate it.
 * </p>
 * 
 * @author ronbanerjee
 *
 */
public class ReplicatePageIT {


	@ClassRule
	public static final CQAuthorPublishClassRule cqBaseClassRule = new CQAuthorPublishClassRule();

	static  CQClient cqAdminAuthor;

	static  CQClient cqAdminPublish;

	static  ReplicationClient repClient;

	/**
	 * BDBPage will create a test page and it will make sure that the page is
	 * removed at the end of every test execution. By removing the page at the end
	 * of the test execution, you are not going to leave any clutter on the instance
	 * under test.
	 * 
	 * @see Page#AbstractSlingClient(CloseableHttpClient, SlingClientConfig)
	 */
	@Rule
	public Page page = new BDBSamplePage(cqBaseClassRule.authorRule, "bdb-fn-test-page");

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
		cqAdminPublish = cqBaseClassRule.publishRule.getAdminClient(CQClient.class);
		repClient = cqAdminAuthor.adaptTo(ReplicationClient.class);
	}

	/**
	 * Tests the replication functionality.
	 * 
	 * @throws ClientException      if there is any Client Exception
	 * @throws TimeoutException     if there is any Timeout Exception
	 * @throws InterruptedException if there is any Interrupted Exception
	 */
	@Test
	public void replicatePage() throws ClientException, TimeoutException, InterruptedException {

		String pagePath = page.getPath();

		cqAdminPublish.doGet(pagePath, 403, 404);

		repClient.activate(pagePath, 200);
		cqAdminPublish.waitExists(pagePath, 10000, 1000);

		repClient.deactivate(pagePath, 200);
		cqAdminPublish.doGet(pagePath, 403, 404);

		// Deletes the folders/nodes created for this test, so as not to keep any
		// traces on the instance.
		cqAdminAuthor.deletePath(pagePath, 200);
	}
}
