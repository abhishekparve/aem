package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.Resource;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.wcm.api.components.ComponentContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import static org.mockito.Mockito.lenient;


import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * Junit for TechnicalToolsTest Model.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class TechnicalToolsTest {

	/** The technical tools model. */
	@InjectMocks
	TechnicalToolsModel technicalToolsModel;

	@Mock
	Rendition rendition;

	//@Mock
	//InputStream inputStream;

	/** Mock SlingHttpServletRequest. */

	@Mock
	SlingHttpServletRequest request;

	/** Mock RequestPathInfo. */

	@Mock
	RequestPathInfo requestPath;

	/** Mock Common Helper. */

	@Mock
	CommonHelper commonHelper;

	/** The resource HP. */

	Resource resourceHP;

	/** Resource resolver. */

	@Mock
	ResourceResolver resourceResolver;

	/** Mock ResourceResolverFactory. */

	@Mock
	ExternalizerService externalizerService;

	/** Mock ResourceResolverFactory. */

	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The solr search service. */

	@Mock
	SolrSearchService solrSearchService;

	/** The context. */

	@Mock
	ComponentContext context;

	@Mock
	XSSFSheet sheet;

	@Mock
	XSSFRow row;
	
	@Mock
	Resource resource;
	
	@Mock
	Asset asset;

	@Mock
	Cell cell;

	@Mock
	Iterator<Row> iterator;

	@Mock
	DocumentBuilderFactory docFactory;

	@Mock
	DocumentBuilder docBuilder;
	
	@Mock
	XSSFWorkbook xssfWorkbook;
	
	@Mock
	ValueMap valueMap;
	
	@Mock
	InputStream inputStream;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	public void setUp() throws Exception {

		Map<String, String> hpNodePropertyProperties = new HashMap<String, String>();
		hpNodePropertyProperties.put("bdFormat", "[{\r\n" + "	\"dyeName\": \"Biotin\"\r\n" + "}]");
		hpNodePropertyProperties.put("searchResultPagePath", "searchPage");
		PrivateAccessor.setField(technicalToolsModel, "request", request);
		PrivateAccessor.setField(technicalToolsModel, "solrSearchService", solrSearchService);
		PrivateAccessor.setField(technicalToolsModel, "dyeNameExcelPath", "content/bdb/abc.xlsx");
		PrivateAccessor.setField(technicalToolsModel, "displaySpectrumViewerTool", false);
		final String[] input2 = new String[] { "myString1", "340336" };
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(input2);
	}

	/**
	 * test init.
	 *
	 * @throws LoginException the login exception
	 */


	@Test
	public void testInit() throws Exception {

		String[] selectors = new String[1];
		selectors[0] = "123456_base";
		selectors[0] = "123456";
		Map<String, Object> writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				"writeService");
//		lenient().when(resourceResolverFactory.getServiceResourceResolver(writeServiceAuth)).thenReturn(resourceResolver);
		lenient().when(request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)).thenReturn("content/bdb/abc.xlsx");
		lenient().when(solrSearchService.getHpNodeResource("340336", "", resourceResolver)).thenReturn(resourceHP);
		lenient().when(resourceResolver.getResource("content/bdb/abc.xlsx")).thenReturn(resource);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(resource.adaptTo(Asset.class)).thenReturn(asset);
		lenient().when(rendition.getStream()).thenReturn(inputStream);
		lenient().when(asset.getMimeType()).thenReturn("application/vnd.ms-excel-unequal");
		
		//inputStream = ((this.getClass()).getClassLoader()).getResourceAsStream("com/bdb/aem/core/models/technical-tools.xlsx");
		//lenient().when(new XSSFWorkbook(inputStream)).thenReturn(xssfWorkbook);
		lenient().when(asset.getOriginal()).thenReturn(rendition);
		lenient().when(rendition.getStream()).thenReturn(inputStream);
		lenient().when(xssfWorkbook.getSheetAt(0)).thenReturn(sheet);
		technicalToolsModel.init();

	}

	/**
	 * Test get format detail json.
	 */

	@Test
	public void testGetFormatDetailJson() {
		String jsonString = "{\r\n" + "	\"dyeName\": \"bicton\"\r\n" + "}";
		JsonElement formatDetailsElement = new JsonParser().parse(jsonString).getAsJsonObject();
		technicalToolsModel.getFormatDetailJson(formatDetailsElement);
	}

	/**
	 * test getIcon1.
	 */


	@Test
	public void testGetIcon1() {

		lenient().when(externalizerService.getFormattedUrl("icon1",
				resourceResolver)).thenReturn("icon1");
		assertNull(technicalToolsModel.getIcon1());

	}

	/**
	 * test getIcon2.
	 */


	@Test
	public void testGetIcon2() {
		assertNull(technicalToolsModel.getIcon2());
	}

	/**
	 * test getIcon3.
	 */


	@Test
	public void testGetIcon3() {
		assertNull(technicalToolsModel.getIcon3());
	}

	/**
	 * test getLink1.
	 */


	@Test
	public void testGetLink1() {
		assertNull(technicalToolsModel.getLink1());
	}

	/**
	 * test getLink2.
	 */


	@Test
	public void testGetLink2() {
		assertNull(technicalToolsModel.getLink2());
	}

	/**
	 * test getLink3.
	 */


	@Test
	public void testGetLink3() {
		assertNull(technicalToolsModel.getLink3());
	}

	/**
	 * test getText1.
	 */


	@Test
	public void testGetText1() {
		assertNull(technicalToolsModel.getText1());
	}

	/**
	 * test getText2.
	 */


	@Test
	public void testGetText2() {
		assertNull(technicalToolsModel.getText2());
	}

	/**
	 * test getText3.
	 */

	@Test
	public void testGetText3() {
		assertNull(technicalToolsModel.getText3());
	}

	/**
	 * Test get display spectrum viewer tool.
	 */

	@Test
	public void testGetDisplaySpectrumViewerTool() {
		assertNotNull(technicalToolsModel.getDisplaySpectrumViewerTool());
	}
}

