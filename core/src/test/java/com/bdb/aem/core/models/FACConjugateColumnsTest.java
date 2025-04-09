package com.bdb.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FACConjugateColumnsTest {

	@InjectMocks
	FACConjugateColumns facConjugateColumns;

	@Test
	void testAllGettersNull() {
		facConjugateColumns.getColHeading();
		facConjugateColumns.getImage();
		facConjugateColumns.getTableData();
	}

	@Test
	void testAllGetters() throws Exception {
		PrivateAccessor.setField(facConjugateColumns, "colHeading", "colHeading");
		PrivateAccessor.setField(facConjugateColumns, "image", "image");
		PrivateAccessor.setField(facConjugateColumns, "tableData", "tableData");
		facConjugateColumns.getColHeading();
		facConjugateColumns.getImage();
		facConjugateColumns.getTableData();
	}
}
