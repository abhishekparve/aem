package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.bdb.aem.core.pojo.RowPojo;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.replication.ReplicationException;

/**
 * The Class CreateExcelNodeServiceImplTest.
 */
@ExtendWith({ MockitoExtension.class })
class CreateExcelNodeServiceImplTest {

	/** The process product document. */
	@InjectMocks
	CreateExcelNodeServiceImpl createExcelNodeServiceImpl;

	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The session. */
	@Mock
	private Session session;

	/** The metadata excel resource. */
	@Mock
	Resource metadataExcelResource;

	/** The query param. */
	Map<String, String> queryParam;

	/** The asset. */
	@Mock
	Asset asset;

	/** The rendition. */
	@Mock
	Rendition rendition;

	/** The input stream. */
	@Mock
	InputStream inputStream;

	/** The row iterator. */
	@Mock
	Iterator<Row> rowIterator;

	/** The row. */
	@Mock
	Row row;

	/** The cell. */
	@Mock
	Cell cell;

	/** The base node. */
	@Mock
	Node baseNode;

	/** The created node. */
	@Mock
	Node createdNode;

	/** The workflow helper service. */
	@Mock
	WorkflowHelperService workflowHelperService;

	/** The workbook. */
	@Mock
	XSSFWorkbook workbook;
	@Mock
	List<RowPojo> deltaRows;
	@Mock
	XSSFSheet sheet;
	@Mock
	XSSFRow rows;
	
	/** The file path. */
	String filePath = "/content/path/excel";

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		queryParam = new HashMap<>();
		queryParam.put("filePath", filePath);
	}

	/**
	 * Process doc metadata excel test.
	 *
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void processDocMetadataExcelTest()
			throws WorkflowException, LoginException, RepositoryException, ReplicationException, IOException {
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(metadataExcelResource);
		when(metadataExcelResource.adaptTo(Asset.class)).thenReturn(asset);
		when(asset.getMimeType()).thenReturn("test");
		createExcelNodeServiceImpl.processDocMetadataExcel(session, resourceResolver, queryParam);
	}

	/**
	 * Excel row iterator test.
	 *
	 * @throws WorkflowException    the workflow exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void excelRowIteratorTest()
			throws WorkflowException, LoginException, RepositoryException, ReplicationException, IOException {
		when(rowIterator.hasNext()).thenReturn(true).thenReturn(false);
		when(rowIterator.next()).thenReturn(row);
		when(row.getCell(Mockito.anyInt())).thenReturn(cell);
		when(cell.toString()).thenReturn("612706").thenReturn("612706").thenReturn("612706.pdf")
				.thenReturn("Technical data sheet (TDS)").thenReturn("eu | us");

		when(session.getNode(Mockito.anyString())).thenReturn(baseNode);
		when(baseNode.addNode(Mockito.anyString(), Mockito.anyString())).thenReturn(createdNode);
		createExcelNodeServiceImpl.excelRowIterator(rowIterator, session, resourceResolver, filePath);
		createExcelNodeServiceImpl.createWorkbook(workbook);
		createExcelNodeServiceImpl.createRows(sheet, deltaRows);
	}
	
	/**
	 * Creates the node name test.
	 *
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void createNodeNameTest() throws RepositoryException {
		when(session.nodeExists(Mockito.anyString())).thenReturn(true).thenReturn(false);
		createExcelNodeServiceImpl.createNodeName(session, "abc-3", 0);
	}
}