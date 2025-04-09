package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;

import java.util.Iterator;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.util.CommonConstants;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class TDSServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TDSServiceImplTest {
	
	/** The test service. */
	@InjectMocks
	TDSServiceImpl testService;
	
	/** The session. */
	@Mock
	Session session;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The res. */
	@Mock
	Resource skuBaseRes, baseRes, pdfRes, res;
	
	/** The vm. */
	@Mock
	ValueMap skuBaseVM, vm;
	
	/** The object new. */
	@Mock
	Object object, objectNew;
	
	/** The list pdf. */
	@Mock
	Iterator<Resource> listPdf;
	
	/** The country code. */
	String countryCode = "US";
	
	/** The catalog number. */
	String catalogNumber = "123456";
	
	/** The doc region. */
	String[] docRegion = {"bdb:regions/us", "bdb:regions/eu"};
	
	/** The Constant VAR_PRODUCT_FOLDER. */
	private static final String VAR_PRODUCT_FOLDER = "products/";
	
	/** The Constant DOC_PART_IDS. */
	private static final String DOC_PART_IDS =  "docPartIDs";
	
	/** The Constant PIPE. */
	private static final String PIPE ="\\|";

	/**
	 * Test get published tds pdf.
	 */
	@Test
	void testGetPublishedTdsPdf() {
		lenient().when(skuBaseRes.getPath()).thenReturn(CommonConstants.COMMERCE_PATH+VAR_PRODUCT_FOLDER+"reagents/cell-preparation-separation-reagents/551411_base");
		lenient().when(skuBaseVM.containsKey(DOC_PART_IDS)).thenReturn(true);
		lenient().when(skuBaseVM.get(DOC_PART_IDS)).thenReturn(object);
		lenient().when(object.toString()).thenReturn("12345 \\| 12340");
		lenient().when(resourceResolver.getResource("/content/dam/bdb/products/global/reagents/cell-preparation-separation-reagents/551411_base/pdf")).thenReturn(baseRes);
		lenient().when(baseRes.listChildren()).thenReturn(listPdf);
		lenient().when(listPdf.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(listPdf.next()).thenReturn(pdfRes);
		lenient().when(pdfRes.getName()).thenReturn("12345 \\.pdf");
		lenient().when(pdfRes.getChild(CommonConstants.METADATA_PATH_AS_CHILD)).thenReturn(res);
		lenient().when(res.adaptTo(ValueMap.class)).thenReturn(vm);
		lenient().when(vm.containsKey(CommonConstants.PDF_DOC_REGION)).thenReturn(true);
		lenient().when(vm.containsKey(CommonConstants.PDF_DOC_TYPE)).thenReturn(true);
		lenient().when(vm.get(CommonConstants.PDF_DOC_REGION, String[].class)).thenReturn(docRegion);
		lenient().when(vm.get(CommonConstants.PDF_DOC_TYPE)).thenReturn(objectNew);
		lenient().when(objectNew.toString()).thenReturn("technicaldatasheet(TDS)");
		lenient().when(pdfRes.getPath()).thenReturn("FinalPath");
		testService.getPublishedTdsPdf(skuBaseRes, skuBaseVM, resourceResolver, countryCode, catalogNumber);
	}

	/**
	 * Test get published tds pdf variation.
	 */
	@Test
	void testGetPublishedTdsPdfVariation() {
		lenient().when(skuBaseRes.getPath()).thenReturn(CommonConstants.COMMERCE_PATH+VAR_PRODUCT_FOLDER+"reagents/cell-preparation-separation-reagents/551411_base");
		//skuBaseVM.put(DOC_PART_IDS, "someId");
		lenient().when(skuBaseVM.containsKey(DOC_PART_IDS)).thenReturn(true);
		lenient().when(skuBaseVM.get("docPartIDs", String.class)).thenReturn("docId");
		lenient().when(object.toString()).thenReturn("12345");
		lenient().when(resourceResolver.getResource("/content/dam/bdb/products/global/reagents/cell-preparation-separation-reagents/551411_base/pdf")).thenReturn(baseRes);
		lenient().when(baseRes.listChildren()).thenReturn(listPdf);
		lenient().when(listPdf.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(listPdf.next()).thenReturn(pdfRes);
		lenient().when(pdfRes.getName()).thenReturn("12345 \\.pdf");
		lenient().when(pdfRes.getChild(CommonConstants.METADATA_PATH_AS_CHILD)).thenReturn(res);
		lenient().when(res.adaptTo(ValueMap.class)).thenReturn(vm);
		lenient().when(vm.containsKey(CommonConstants.PDF_DOC_REGION)).thenReturn(true);
		lenient().when(vm.containsKey(CommonConstants.PDF_DOC_TYPE)).thenReturn(true);
		lenient().when(vm.get(CommonConstants.PDF_DOC_REGION, String[].class)).thenReturn(docRegion);
		lenient().when(vm.get(CommonConstants.PDF_DOC_TYPE)).thenReturn(objectNew);
		lenient().when(objectNew.toString()).thenReturn("technicaldatasheet(TDS)");
		lenient().when(pdfRes.getPath()).thenReturn("FinalPath");
		testService.getPublishedTdsPdf(skuBaseRes, skuBaseVM, resourceResolver, countryCode, catalogNumber);
	}
}
