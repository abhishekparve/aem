
package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.when;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.replication.ReplicationException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class BeadLotStructureUpdateHelperServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BeadLotStructureUpdateHelperServiceImplTest {

	/** The bead lot structure update helper service impl. */
	@InjectMocks
	BeadLotStructureUpdateHelperServiceImpl beadLotStructureUpdateHelperServiceImpl;

	/** The json object. */
	private JsonObject jsonObject;
	
	/** The service resolver. */
	@Mock
	ResourceResolver serviceResolver;

	/** The session. */
	@Mock
	Session session;
	
	/** The resource. */
	@Mock
	Resource resource;
	
	/** The node. */
	@Mock
	private Node node;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	void setUp() throws Exception {
		String input = "{\r\n" + "	\"beadlots\": [{\r\n" + "		\"partNumber\": \"650625\",\r\n"
				+ "		\"documentDescription\": \"BeadlotJsonBean beadlotJsonBean\",\r\n"
				+ "		\"beadlotFile\": {\r\n" + "			\"documentNumber\": \"650625-18771\",\r\n"
				+ "			\"releaseDate\": \"05/08/2020\",\r\n"
				+ "			\"batchExpiryDate\": \"31/08/2020\",\r\n" + "			\"documentPart\": \"EN\",\r\n"
				+ "			\"documentVersion\": \"\",\r\n" + "			\"documentType\": \"ZSP\",\r\n"
				+ "			\"status\": \"Released\",\r\n" + "			\"softwareVersion\": \"v7\",\r\n"
				+ "			\"regStatus\": \"IVD\"\r\n" + "		}\r\n" + "	}]\r\n" + "}";

		jsonObject = new JsonParser().parse(input).getAsJsonObject();
	}

	/**
	 * Test get beadlot json as list.
	 *
	 * @throws RepositoryException the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	void testGetBeadlotJsonAsList() throws RepositoryException, ReplicationException {
		beadLotStructureUpdateHelperServiceImpl.getBeadlotJsonAsList(jsonObject);
	}
	
	/**
	 * Test create node.
	 *
	 * @throws RepositoryException the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	void testCreateNode() throws RepositoryException, ReplicationException {
		when(serviceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		beadLotStructureUpdateHelperServiceImpl.createNode("", serviceResolver, session, "");
	}
}