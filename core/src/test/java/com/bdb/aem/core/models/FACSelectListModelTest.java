package com.bdb.aem.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.tools.impl.FACSelectListServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FACSelectListModelTest {

	@InjectMocks
	FACSelectListModel facSelectListModel;

	/** Resourceresolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	@Mock
	Resource resource;
	@Mock
	List<FACReportsModel> reportsList;

	@Mock
	FACSelectListServiceImpl facSelectService;

	@Mock
	List<FACListNavigation> navigation;

	@Test
	void testAllGetters() throws Exception {
		PrivateAccessor.setField(facSelectListModel, "reportsList", reportsList);
		PrivateAccessor.setField(facSelectListModel, "navigation", navigation);
		facSelectListModel.getReportsList();
		facSelectListModel.getNavigation();
	}

	@Test
	void testAllGettersNull() {
		facSelectListModel.getDataService();
		facSelectListModel.getReportsList();
		facSelectListModel.getNavigation();
		facSelectListModel.getFacDataEndpoint();
	}


}