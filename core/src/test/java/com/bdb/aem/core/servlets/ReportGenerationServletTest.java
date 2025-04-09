package com.bdb.aem.core.servlets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.bean.AssetBean;
import com.bdb.aem.core.services.ReportGenerationService;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ReportGenerationServletTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ReportGenerationServletTest {
	
	/** The request. */
	@Mock
	SlingHttpServletRequest request;
	
	/** The response. */
	@Mock
	SlingHttpServletResponse response;
	
	/** The report generation servlet. */
	@InjectMocks
	ReportGenerationServlet reportGenerationServlet;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The session. */
	@Mock
	Session session;
	
	/** The report generation service. */
	@Mock
	ReportGenerationService reportGenerationService;
	
	/** The print writer. */
	@Mock
	PrintWriter printWriter;
	
	/** The resource resolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;
    
    /** The asset beans. */
    List<AssetBean> assetBeans;
    
    /** The items. */
    AssetBean item1, item2;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	 void setUp() throws Exception {
		
		item1=new AssetBean();
		item2=new AssetBean();
		
		PrivateAccessor.setField(item1, "assetPaths", "assetPaths");
		PrivateAccessor.setField(item2, "assetPaths", "assetPaths");
		
		assetBeans = Arrays.asList(item1, item2);
		
		lenient().when(reportGenerationService.fetchTestReportData()).thenReturn(assetBeans);
		lenient().when(response.getWriter()).thenReturn(printWriter);
		
	 }
	 
	/**
	 * Test do post.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testdoPost() throws IOException {
		assertNotNull(request);
		reportGenerationServlet.doPost(request, response);
	}
	
	/**
	 * Test exception.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testException() throws IOException {
		lenient().when(reportGenerationService.fetchTestReportData()).thenReturn(assetBeans);
		lenient().when(response.getWriter()).thenThrow(IOException.class);
		reportGenerationServlet.doPost(request, response);
	}

}
