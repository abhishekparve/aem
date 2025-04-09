package com.bdb.aem.core.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class HPProductSolrIndexBeanTest.
 */
class HPProductSolrIndexBeanTest {
	
	/** The h P product solr index bean. */
	HPProductSolrIndexBean hPProductSolrIndexBean;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		hPProductSolrIndexBean = new HPProductSolrIndexBean();
		hPProductSolrIndexBean.setAlternativeName("String");
		hPProductSolrIndexBean.setApplications("String");
		hPProductSolrIndexBean.setBarcodeSequence("String");
		hPProductSolrIndexBean.setBdFormat("String");
		hPProductSolrIndexBean.setBrand("String");
		hPProductSolrIndexBean.setCellType("String");
		hPProductSolrIndexBean.setClone("String");
		hPProductSolrIndexBean.setConjugate("String");
		hPProductSolrIndexBean.setEntrezGeneId("String");
		hPProductSolrIndexBean.setHostSpecies("String");
		hPProductSolrIndexBean.setImmunogen("String");
		hPProductSolrIndexBean.setIsotype("String");
		hPProductSolrIndexBean.setLaserLine("String");
		hPProductSolrIndexBean.setNewProducts("String");
		hPProductSolrIndexBean.setPostTranslationalModification("String");
		hPProductSolrIndexBean.setProductName("String");
		hPProductSolrIndexBean.setProductType("String");
		hPProductSolrIndexBean.setProductUrl("String");
		hPProductSolrIndexBean.setReactivity("String");
		hPProductSolrIndexBean.setRecombinant("String");
		hPProductSolrIndexBean.setRegulatoryStatus("String");
		hPProductSolrIndexBean.setResearchArea("String");
		hPProductSolrIndexBean.setSequenceId("String");
		hPProductSolrIndexBean.setStorageBuffer("String");
		hPProductSolrIndexBean.setTarget("String");
		hPProductSolrIndexBean.setTargetSpecies("String");
		hPProductSolrIndexBean.setVolPerTest("String");
	}
	
	/**
	 * Test.
	 */
	@Test
	void test() {
		assertEquals("String", hPProductSolrIndexBean.getAlternativeName());
		assertEquals("String", hPProductSolrIndexBean.getApplications());
		assertEquals("String", hPProductSolrIndexBean.getBarcodeSequence());
		assertEquals("String", hPProductSolrIndexBean.getBdFormat());
		assertEquals("String", hPProductSolrIndexBean.getBrand());
		assertEquals("String", hPProductSolrIndexBean.getCellType());
		assertEquals("String", hPProductSolrIndexBean.getClone());
		assertEquals("String", hPProductSolrIndexBean.getConjugate());
		assertEquals("String", hPProductSolrIndexBean.getEntrezGeneId());
		assertEquals("String", hPProductSolrIndexBean.getHostSpecies());
		assertEquals("String", hPProductSolrIndexBean.getImmunogen());
		assertEquals("String", hPProductSolrIndexBean.getIsotype());
		assertEquals("String", hPProductSolrIndexBean.getLaserLine());
		assertEquals("String", hPProductSolrIndexBean.getNewProducts());
		assertEquals("String", hPProductSolrIndexBean.getPostTranslationalModification());
		assertEquals("String", hPProductSolrIndexBean.getProductName());
		assertEquals("String", hPProductSolrIndexBean.getProductType());
		assertEquals("String", hPProductSolrIndexBean.getProductUrl());
		assertEquals("String", hPProductSolrIndexBean.getReactivity());
		assertEquals("String", hPProductSolrIndexBean.getRecombinant());
		assertEquals("String", hPProductSolrIndexBean.getRegulatoryStatus());
		assertEquals("String", hPProductSolrIndexBean.getResearchArea());
		assertEquals("String", hPProductSolrIndexBean.getSequenceId());
		assertEquals("String", hPProductSolrIndexBean.getStorageBuffer());
		assertEquals("String", hPProductSolrIndexBean.getTarget());
		assertEquals("String", hPProductSolrIndexBean.getTargetSpecies());
		assertEquals("String", hPProductSolrIndexBean.getVolPerTest());
	}
}
