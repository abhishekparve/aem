package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import junitx.util.PrivateAccessor;

/**
 * The Class CloneComparisonAntigenModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class CloneComparisonAntigenModelTest {

	/** The clone comparison antigen model. */
	@InjectMocks
	CloneComparisonAntigenModel cloneComparisonAntigenModel;
	
	/** The resource. */
	@Mock
	Resource resource;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The externalizer. */
	@Mock
	ExternalizerService externalizer;
	
	/**
	 * Setup.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setup() throws NoSuchFieldException {
		PrivateAccessor.setField(cloneComparisonAntigenModel, "title", "title");
		PrivateAccessor.setField(cloneComparisonAntigenModel, "description", "description");
		PrivateAccessor.setField(cloneComparisonAntigenModel, "imagePath", "imagePath");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "imageAltText", "imageAltText");
		PrivateAccessor.setField(cloneComparisonAntigenModel, "plusIconPath", "plusIconPath");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "minusIconPath", "minusIconPath");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "plusIconAlt", "plusIconAlt");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "minusIconAlt", "minusIconAlt");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "targetMoleculeLabel", "targetMoleculeLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "chooseReactivityLabel", "chooseReactivityLabel");
		PrivateAccessor.setField(cloneComparisonAntigenModel, "sortByLabel", "sortByLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "filterKeywordsLabel", "filterKeywordsLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "filterKeywordsMessageLabel", "filterKeywordsMessageLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "searchPlaceholderLabel", "searchPlaceholderLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "antigenNameLabel", "antigenNameLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "geneNameLabel", "geneNameLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "distributionLabel", "distributionLabel");
		PrivateAccessor.setField(cloneComparisonAntigenModel, "formatsLabel", "formatsLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "viewClonesLabel", "viewClonesLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "aliasLabel", "aliasLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "functionLabel", "functionLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "availableFormatsLabel", "availableFormatsLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "descriptionLabel", "descriptionLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "mWlabel", "mWlabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "linkCatalogLabel", "linkCatalogLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "featurePanelLabel", "featurePanelLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "noResultsTitle", "noResultsTitle");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "noResultsIcon", "noResultsIcon");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "noResultsIconAlt", "noResultsIconAlt");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "publicationLabel", "publicationLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "loadMoreCtaLabel", "loadMoreCtaLabel");	
		PrivateAccessor.setField(cloneComparisonAntigenModel, "searchPageUrl", "searchPageUrl");
		PrivateAccessor.setField(cloneComparisonAntigenModel, "entrezgeneIdLabel", "entrezgeneIdLabel");
		cloneComparisonAntigenModel.setLinkToCatalogPlaceholder("linkCatalogPlaceholder");
		cloneComparisonAntigenModel.setDyeNamePlaceholder("dyeNamePlaceholder");
		PrivateAccessor.setField(cloneComparisonAntigenModel, "linkToCatalogText", "linkToCatalogText");
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertEquals("title", cloneComparisonAntigenModel.getTitle());
		assertEquals("description", cloneComparisonAntigenModel.getDescription());
		assertEquals("imagePath", cloneComparisonAntigenModel.getImagePath());	
		assertEquals("imageAltText", cloneComparisonAntigenModel.getImageAltText());	
		assertEquals("plusIconPath", cloneComparisonAntigenModel.getPlusIconPath());	
		assertEquals("minusIconPath", cloneComparisonAntigenModel.getMinusIconPath());
		assertEquals("plusIconAlt", cloneComparisonAntigenModel.getPlusIconAlt());	
		assertEquals("minusIconAlt", cloneComparisonAntigenModel.getMinusIconAlt());
		assertEquals("targetMoleculeLabel", cloneComparisonAntigenModel.getTargetMoleculeLabel());
		assertEquals("chooseReactivityLabel", cloneComparisonAntigenModel.getChooseReactivityLabel());
		assertEquals("sortByLabel", cloneComparisonAntigenModel.getSortByLabel());
		assertEquals("filterKeywordsLabel", cloneComparisonAntigenModel.getFilterKeywordsLabel());
		assertEquals("filterKeywordsMessageLabel", cloneComparisonAntigenModel.getFilterKeywordsMessageLabel());
		assertEquals("searchPlaceholderLabel", cloneComparisonAntigenModel.getSearchPlaceholderLabel());
		assertEquals("antigenNameLabel", cloneComparisonAntigenModel.getAntigenNameLabel());
		assertEquals("geneNameLabel", cloneComparisonAntigenModel.getGeneNameLabel());
		assertEquals("distributionLabel", cloneComparisonAntigenModel.getDistributionLabel());
		assertEquals("formatsLabel", cloneComparisonAntigenModel.getFormatsLabel());
		assertEquals("viewClonesLabel", cloneComparisonAntigenModel.getViewClonesLabel());
		assertEquals("aliasLabel", cloneComparisonAntigenModel.getAliasLabel());
		assertEquals("functionLabel", cloneComparisonAntigenModel.getFunctionLabel());	
		assertEquals("availableFormatsLabel", cloneComparisonAntigenModel.getAvailableFormatsLabel());	
		assertEquals("descriptionLabel", cloneComparisonAntigenModel.getDescriptionLabel());	
		assertEquals("mWlabel", cloneComparisonAntigenModel.getmWlabel());
		assertEquals("linkCatalogLabel", cloneComparisonAntigenModel.getLinkCatalogLabel());	
		assertEquals("featurePanelLabel", cloneComparisonAntigenModel.getFeaturePanelLabel());	
		assertNotNull(cloneComparisonAntigenModel.getNoResults());
		assertEquals("publicationLabel", cloneComparisonAntigenModel.getPublicationLabel());	
		assertEquals("loadMoreCtaLabel", cloneComparisonAntigenModel.getLoadMoreCtaLabel());
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(externalizer.getFormattedUrl("searchPageUrl", resourceResolver)).thenReturn("FormattedURL");
		assertEquals("FormattedURL", cloneComparisonAntigenModel.getSearchPageUrl());
		assertEquals("entrezgeneIdLabel", cloneComparisonAntigenModel.getEntrezgeneIdLabel());
		assertEquals("linkCatalogPlaceholder", cloneComparisonAntigenModel.getLinkCatalogPlaceholder());
		assertEquals("dyeNamePlaceholder", cloneComparisonAntigenModel.getDyeNamePlaceholder());
		assertEquals("linkToCatalogText", cloneComparisonAntigenModel.getLinkToCatalogText());
	}
}
