package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * Test Class Clone Comparison Model Test.
 * <p>
 * Junit Test for {@link CloneComparisonModel}
 * </p>
 * 
 * @author ronbanerjee
 *
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class CloneComparisonModelTest {

	@InjectMocks
	private CloneComparisonModel cloneComparisonModel;

	@InjectMocks
	private CloneComparisonCloneModel cloneModel;

	@InjectMocks
	private CloneComparisonAntigenModel antigenModel;

	@Mock
	private ExternalizerService externalizer;

	@Mock
	private ResourceResolver resolver;

	@Mock
	private Resource resource;

	/**
	 * Set up method.
	 * <p>
	 * Sets the parameters before the test function is called.
	 * </p>
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		lenient().when(externalizer.getFormattedUrl("/content/dam/bdb/icon", resolver))
				.thenReturn("http://localhost/content/dam/bdb/icon");
		lenient().when(resource.getResourceResolver()).thenReturn(resolver);
		// The mocks for CloneComparisonCloneModel
		PrivateAccessor.setField(cloneModel, "targetLabel", "Target Label");
		PrivateAccessor.setField(cloneModel, "moleculeLabel", "Molecule Label");
		PrivateAccessor.setField(cloneModel, "aliasesLabel", "Aliases Label");
		PrivateAccessor.setField(cloneModel, "noOfClonesLabel", "No of Clones");
		PrivateAccessor.setField(cloneModel, "cloneNameLabel", "Clone Name");
		PrivateAccessor.setField(cloneModel, "reactivityLabel", "Reactivity");
		PrivateAccessor.setField(cloneModel, "applicationsLabel", "Applications");
		PrivateAccessor.setField(cloneModel, "fixationLabel", "Fixations");
		PrivateAccessor.setField(cloneModel, "isotypeLabel", "Isotype");
		PrivateAccessor.setField(cloneModel, "regulatoryStatusLabel", "Regulatory Status");
		PrivateAccessor.setField(cloneModel, "hldaLabel", "HLDA Label");
		PrivateAccessor.setField(cloneModel, "referencesLabel", "References");
		PrivateAccessor.setField(cloneModel, "availableFormatsLabel", "Available Formats");
		PrivateAccessor.setField(cloneModel, "viewProductsLabel", "View Prodcuts");
		PrivateAccessor.setField(cloneModel, "editLabel", "Edit");
		PrivateAccessor.setField(cloneModel, "targetLabel", "Target Label");
		PrivateAccessor.setField(cloneModel, "backToSelectionIcon", "/content/dam/bdb/icon");
		PrivateAccessor.setField(cloneModel, "backToSelectionIconAlt", "Alt");

		// The Mocks for CloneComparisonAntigenModel
		PrivateAccessor.setField(antigenModel, "title", "Title");
		PrivateAccessor.setField(antigenModel, "description", "Description");
		PrivateAccessor.setField(antigenModel, "imagePath", "/content/dam/bdb/img");
		PrivateAccessor.setField(antigenModel, "imageAltText", "Alt Text");
		PrivateAccessor.setField(antigenModel, "plusIconPath", "/content/dam/bdb/icon");
		PrivateAccessor.setField(antigenModel, "minusIconPath", "/content/dam/bdb/icon");
		PrivateAccessor.setField(antigenModel, "plusIconAlt", "Alt Text");
		PrivateAccessor.setField(antigenModel, "minusIconAlt", "Alt Text");
		PrivateAccessor.setField(antigenModel, "targetMoleculeLabel", "Target");
		PrivateAccessor.setField(antigenModel, "chooseReactivityLabel", "Reactivity");
		PrivateAccessor.setField(antigenModel, "sortByLabel", "Sort By");
		PrivateAccessor.setField(antigenModel, "filterKeywordsLabel", "Filter Keywords");
		PrivateAccessor.setField(antigenModel, "filterKeywordsMessageLabel", "Filter Keyword message");
		PrivateAccessor.setField(antigenModel, "searchPlaceholderLabel", "Placeholder");
		PrivateAccessor.setField(antigenModel, "antigenNameLabel", "Name");
		PrivateAccessor.setField(antigenModel, "geneNameLabel", "Gene");
		PrivateAccessor.setField(antigenModel, "distributionLabel", "Distribution");
		PrivateAccessor.setField(antigenModel, "formatsLabel", "Formats");
		PrivateAccessor.setField(antigenModel, "viewClonesLabel", "Clones");
		PrivateAccessor.setField(antigenModel, "aliasLabel", "alias");
		PrivateAccessor.setField(antigenModel, "functionLabel", "functions");
		PrivateAccessor.setField(antigenModel, "availableFormatsLabel", "Formats");
		PrivateAccessor.setField(antigenModel, "descriptionLabel", "Description");
		PrivateAccessor.setField(antigenModel, "mWlabel", "mW");
		PrivateAccessor.setField(antigenModel, "linkCatalogLabel", "Catalog");
		PrivateAccessor.setField(antigenModel, "featurePanelLabel", "Feature Panel");
		PrivateAccessor.setField(antigenModel, "noResultsTitle", "No Results");
		PrivateAccessor.setField(antigenModel, "noResultsIcon", "/content/dam/bdb/img");
		PrivateAccessor.setField(antigenModel, "noResultsIconAlt", "Alt");
		PrivateAccessor.setField(antigenModel, "publicationLabel", "Label");
		PrivateAccessor.setField(antigenModel, "loadMoreCtaLabel", "Load More");
		PrivateAccessor.setField(antigenModel, "searchPageUrl", "Search Page");
		PrivateAccessor.setField(antigenModel, "dyeNamePlaceholder", "Dye Name");
		PrivateAccessor.setField(antigenModel, "linkCatalogPlaceholder", "Link to Catalog");
		PrivateAccessor.setField(antigenModel, "linkToCatalogText", "Link to catalog label");

		// The Mocks for CloneComparisonModel
		PrivateAccessor.setField(cloneComparisonModel, "clones", cloneModel);
		PrivateAccessor.setField(cloneComparisonModel, "antigens", antigenModel);
	}

	/**
	 * Tests the Getters.
	 * <p>
	 * Tests the Getters from {@link CloneComparisonCloneModel}
	 * </p>
	 */
	@Test
	void testGetters() {
		assertNotNull(cloneModel.getTargetLabel());
		assertNotNull(cloneModel.getMoleculeLabel());
		assertNotNull(cloneModel.getAliasesLabel());
		assertNotNull(cloneModel.getNoOfClonesLabel());
		assertNotNull(cloneModel.getCloneNameLabel());
		assertNotNull(cloneModel.getReactivityLabel());
		assertNotNull(cloneModel.getReactivityLabel());
		assertNotNull(cloneModel.getApplicationLabel());
		assertNotNull(cloneModel.getFixationLabel());
		assertNotNull(cloneModel.getIsotypeLabel());
		assertNotNull(cloneModel.getRegulatoryStatusLabel());
		assertNotNull(cloneModel.getHldaLabel());
		assertNotNull(cloneModel.getReferencesLabel());
		assertNotNull(cloneModel.getAvailableFormatsLabel());
		assertNotNull(cloneModel.getViewProductsLabel());
		assertNotNull(cloneModel.getEditLabel());
		assertNotNull(cloneComparisonModel.getClones());
		assertNotNull(cloneModel.getBackToSelectionIcon());
		assertNotNull(cloneModel.getBackToSelectionIconAlt());

	}

	/**
	 * Tests the Getters.
	 * <p>
	 * Tests the Getters from {@link CloneComparisonAntigenModel}
	 * </p>
	 */
	@Test
	void testGetters1() {
		assertNotNull(antigenModel.getTitle());
		assertNotNull(antigenModel.getDescription());
		assertNotNull(antigenModel.getImagePath());
		assertNotNull(antigenModel.getImageAltText());
		assertNotNull(antigenModel.getPlusIconPath());
		assertNotNull(antigenModel.getPlusIconAlt());
		assertNotNull(antigenModel.getMinusIconAlt());
		assertNotNull(antigenModel.getMinusIconPath());
		assertNotNull(antigenModel.getTargetMoleculeLabel());
		assertNotNull(antigenModel.getChooseReactivityLabel());
		assertNotNull(antigenModel.getSortByLabel());
		assertNotNull(antigenModel.getFeaturePanelLabel());
		assertNotNull(antigenModel.getFilterKeywordsLabel());
		assertNotNull(antigenModel.getFilterKeywordsMessageLabel());
		assertNotNull(antigenModel.getSearchPlaceholderLabel());
		assertNotNull(antigenModel.getAntigenNameLabel());
		assertNotNull(antigenModel.getGeneNameLabel());
		assertNotNull(antigenModel.getDistributionLabel());
		assertNotNull(antigenModel.getFormatsLabel());
		assertNotNull(antigenModel.getViewClonesLabel());
		assertNotNull(antigenModel.getAliasLabel());
		assertNotNull(antigenModel.getViewClonesLabel());
		assertNotNull(antigenModel.getFunctionLabel());
		assertNotNull(antigenModel.getDescription());
		assertNotNull(antigenModel.getDescriptionLabel());
		assertNotNull(cloneComparisonModel.getAntigens());

	}
}
