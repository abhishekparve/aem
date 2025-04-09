
package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.event.jobs.JobManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.bean.BeadlotJsonBean;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.BeadLotStructureUpdateHelperService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.replication.ReplicationException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class BeadLotStructureUpdateServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BeadLotStructureUpdateServiceImplTest {

	/** The bead lot structure update service impl. */
	@InjectMocks
	BeadLotStructureUpdateServiceImpl beadLotStructureUpdateServiceImpl;

	/** The solr search service. */
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;

	/** The service resolver. */
	@Mock
	ResourceResolver serviceResolver;

	/** The job manager. */
	@Mock
	JobManager jobManager;

	/** The workflow helper service. */
	@Mock
	WorkflowHelperService workflowHelperService;

	/** The session. */
	@Mock
	Session session;

	/** The resource. */
	@Mock
	Resource resource;
	
	/** The bead lot structure update helper service. */
	@Mock
	BeadLotStructureUpdateHelperService beadLotStructureUpdateHelperService;
	@Mock
	BDBSearchEndpointService searchConfig;
	/** The node. */
	@Mock
	private Node node;
	@Mock
	ValueMap valueMap;

	/** The json object. */
	private JsonObject jsonObject;

	/** The beadlot json beans. */
	private List<BeadlotJsonBean> beadlotJsonBeans;

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
		beadlotJsonBeans = new ArrayList<>();
		BeadlotJsonBean beadlotJsonBean = new BeadlotJsonBean();
		beadlotJsonBean.setDocumentDescription("BeadlotJsonBean beadlotJsonBean");
		beadlotJsonBean.setPartNumber("650625");
		beadlotJsonBean.setBeadlotFile(getBeadlotMap());
		beadlotJsonBeans.add(beadlotJsonBean);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(serviceResolver, "650625",
				CommonConstants.MATERIAL_NUMBER)).thenReturn(resource);
		lenient().when(resource.getPath()).thenReturn("/content/commerce");
		lenient().when(serviceResolver.getResource(resource.getPath() + "/hp")).thenReturn(resource);
		lenient().when(resource.adaptTo(Node.class)).thenReturn(node);
		lenient().when(beadLotStructureUpdateHelperService.createNode(Mockito.anyString(), Mockito.any(), Mockito.any(),
				Mockito.anyString())).thenReturn(node);
		lenient().when(beadLotStructureUpdateHelperService.getBeadlotJsonAsList(jsonObject))
				.thenReturn(beadlotJsonBeans);
		lenient().when(searchConfig.getCatalogStructureNodeType()).thenReturn("nodeType");
	}

	/**
	 * Test create beadlot structure.
	 *
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Test
	void testCreateBeadlotStructure() throws RepositoryException, ReplicationException {
		
		beadLotStructureUpdateServiceImpl.createIfNotExist("/content/commerce/products/bdb/beadlots", session,
				serviceResolver);
		beadLotStructureUpdateServiceImpl.createBeadlotStructure(serviceResolver, jsonObject, session);

	}

	/**
	 * Test else create beadlot structure.
	 *
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 * @throws RepositoryException             the repository exception
	 * @throws ReplicationException            the replication exception
	 */
	@Test
	void testElseCreateBeadlotStructure()
			throws AemInternalServerErrorException, RepositoryException, ReplicationException {

		lenient().when(session.nodeExists(Mockito.anyString())).thenReturn(true);
		lenient().when(serviceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		lenient().when(node.getSession()).thenReturn(session);
		
		Set<String> set = new HashSet<>();
		set.add("123456");
		beadLotStructureUpdateServiceImpl.getErrorSkus(set);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(valueMap.get("slingJobEnabler")).thenReturn("false");
		beadLotStructureUpdateServiceImpl.createBeadlotStructure(serviceResolver, jsonObject, session);

	}

	@Test
	void testGetResponse() throws AemInternalServerErrorException, RepositoryException, ReplicationException {
		Set<String> setMissingPartNumber = new HashSet<String>() {
			{
				add("setMissingPartNumber");
			}
		};
		Set<String> setFoundPartNumber = new HashSet<String>() {
			{
				add("setFoundPartNumber");
			}
		};
		beadLotStructureUpdateServiceImpl.getResponse(setMissingPartNumber, setFoundPartNumber);
	}

	@Test
	void testGetResponseEmpty() throws AemInternalServerErrorException, RepositoryException, ReplicationException {
		Set<String> setMissingPartNumber = new HashSet<String>() {
			{

			}
		};
		Set<String> setFoundPartNumber = new HashSet<String>() {
			{

			}
		};
		beadLotStructureUpdateServiceImpl.getResponse(setMissingPartNumber, setFoundPartNumber);
	}

	@Test
	void testSetFoundEmpty() throws AemInternalServerErrorException, RepositoryException, ReplicationException {
		Set<String> setMissingPartNumber = new HashSet<String>() {
			{
				add("setMissingPartNumber");
			}
		};
		Set<String> setFoundPartNumber = new HashSet<String>() {
			{

			}
		};
		beadLotStructureUpdateServiceImpl.getResponse(setMissingPartNumber, setFoundPartNumber);
	}

	/**
	 * Gets the beadlot map.
	 *
	 * @return the beadlot map
	 */
	private Map<String, String> getBeadlotMap() {
		Map<String, String> beadLotFileMap = new HashMap<>();
		beadLotFileMap.put("documentNumber", "650625-18771");
		beadLotFileMap.put("releaseDate", "05/08/2020");
		beadLotFileMap.put("batchExpiryDate", "31/08/2020");
		beadLotFileMap.put("documentPart", "EN");
		beadLotFileMap.put("documentVersion", "");
		beadLotFileMap.put("documentType", "ZSP");
		beadLotFileMap.put("status", "Released");
		beadLotFileMap.put("softwareVersion", "v7");
		beadLotFileMap.put("regStatus", "IVD");
		return beadLotFileMap;
	}
}