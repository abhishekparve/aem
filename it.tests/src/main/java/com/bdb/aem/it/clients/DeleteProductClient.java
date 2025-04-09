package com.bdb.aem.it.clients;

import static org.apache.http.HttpStatus.SC_OK;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.testing.clients.ClientException;
import org.apache.sling.testing.clients.SlingClientConfig;
import org.apache.sling.testing.clients.SlingHttpResponse;
import org.apache.sling.testing.clients.util.HttpUtils;

import com.adobe.cq.testing.client.CQClient;
import com.bdb.aem.core.servlets.DeleteFaultyProducts;

/**
 * <p>
 * Testing client to De-activate, Delete and Unindex the product based on
 * material number.
 * </p>
 * 
 * @author ronbanerjee
 *
 */
public class DeleteProductClient extends CQClient {

	/**
	 * @see CQClient
	 */
	public DeleteProductClient(CloseableHttpClient http, SlingClientConfig config) throws ClientException {
		super(http, config);
	}

	/**
	 * @see CQClient
	 */
	public DeleteProductClient(URI serverUrl, String user, String password) throws ClientException {
		super(serverUrl, user, password);
	}

	/**
	 * Deletes variant node for {@code materialNumber}.
	 * 
	 * @see DeleteFaultyProducts
	 * 
	 * @param expectedStatus the expected status
	 * @return the Sling HTTP Response
	 * @throws ClientException              if the client could not be created
	 * @throws UnsupportedEncodingException if the encoding is not a supported
	 *                                      encoding type
	 */
	public SlingHttpResponse deleteVariant(String materialNumber, int... expectedStatus)
			throws ClientException, UnsupportedEncodingException {

		return doPost("/content/bdb/paths/admin/delete-faulty-products.variant.html", new StringEntity(materialNumber),
				HttpUtils.getExpectedStatus(SC_OK, expectedStatus));
	}

	/**
	 * Deletes Base Node for {@code materialNumber}.
	 * 
	 * @see DeleteFaultyProducts
	 * 
	 * @param expectedStatus the expected status
	 * @return the Sling HTTP Response
	 * @throws ClientException              if the client could not be created
	 * @throws UnsupportedEncodingException if the encoding is not a supported
	 *                                      encoding type
	 */
	public SlingHttpResponse deleteBase(String materialNumber, int... expectedStatus)
			throws UnsupportedEncodingException, ClientException {

		return doPost("/content/bdb/paths/admin/delete-faulty-products.base.html", new StringEntity(materialNumber),
				HttpUtils.getExpectedStatus(SC_OK, expectedStatus));
	}

	/**
	 * Deletes the product with {@code materialNumber} from Apache SOLR.
	 * 
	 * @see DeleteFaultyProducts
	 * 
	 * @param materialNumber the material number which is un-indexed from Apache SOLR
	 * @param expectedStatus the expected status for this request
	 * @return the HTTP response
	 * @throws UnsupportedEncodingException
	 * @throws ClientException
	 */
	public SlingHttpResponse deleteProductFromSolr(String materialNumber, int... expectedStatus)
			throws UnsupportedEncodingException, ClientException {

		return doPost("/content/bdb/paths/admin/delete-faulty-products.solr.html", new StringEntity(materialNumber),
				HttpUtils.getExpectedStatus(SC_OK, expectedStatus));
	}
}
