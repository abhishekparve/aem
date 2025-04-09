package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.tools.impl.FACSelectConjugateServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FACSelectConjugateModelTest {
	@InjectMocks
	FACSelectConjugateModel facSelectConjugateModel;
	/** Resource Resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	@Mock
	Resource resource;
	@Mock
	FACSelectConjugateServiceImpl facSelectService;
	@Mock
	List<FACConjugateRows> rows;
	@Mock
	Map<String, Serializable> statisticsImageTable;
	@Mock
	 ExternalizerService externalizer;
	@Test
	void testAllGettersNull() {
		facSelectConjugateModel.getConjugateRows();
		facSelectConjugateModel.getDataService();
		facSelectConjugateModel.getStatisticsImageTable();
		facSelectConjugateModel.getGatingHierarchyURL();
		facSelectConjugateModel.getViewProductURL();
		facSelectConjugateModel.setStatisticsImageTable(null);
	}

	@Test
	void testAllGetters() throws Exception {
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		PrivateAccessor.setField(facSelectConjugateModel, "statisticsImageTable", statisticsImageTable);
		PrivateAccessor.setField(facSelectConjugateModel, "gatingHierarchyURL", "gatingHierarchyURL");
		PrivateAccessor.setField(facSelectConjugateModel, "viewProductURL", "viewProductURL");
		PrivateAccessor.setField(facSelectConjugateModel, "rows", rows);
		facSelectConjugateModel.getConjugateRows();
		facSelectConjugateModel.getStatisticsImageTable();
		facSelectConjugateModel.getGatingHierarchyURL();
		facSelectConjugateModel.getViewProductURL();
		facSelectConjugateModel.setStatisticsImageTable(statisticsImageTable);
	}
}
