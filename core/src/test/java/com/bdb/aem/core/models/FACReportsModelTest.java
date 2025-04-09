package com.bdb.aem.core.models;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Junit test for
 * {@link FACReportsModel}
 * {@link FACListNavigation}
 * {@link FACGatingImages}
 * {@link SpeciesModel}
 * {@link FluorochromesModel}
 * {@link FacListSubNavModel}
 * {@link FACGatingImages}
 * 
 * 
 * @author ronbanerjee
 *
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FACReportsModelTest {

	@InjectMocks
	private FACReportsModel facReportsModel;

	@InjectMocks
	private SpeciesModel speciesModel;

	@InjectMocks
	private FluorochromesModel flModel;

	@InjectMocks
	private FACGatingImages gIm;

	@InjectMocks
	private FACListNavigation navigation;

	@InjectMocks
	private FacListSubNavModel subNav;

	@InjectMocks
	private FACGatingImages fsGating;

	@Mock
	private ResourceResolver resourceResolver;

	@Mock
	private Resource resource;

	@Mock
	private ExternalizerService externalizer;

	@Mock
	private PageManager PageManager;
	
	@Mock
	private TagManager tagManager;
	
	@Mock
	private Tag tag;

	@Mock
	private Page page;

	/**
	 * Sets up.
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {

		// The Specificity
		Map<String, Serializable> specificity = new HashMap<>();
		PrivateAccessor.setField(facReportsModel, "specificity", specificity);

		// The Species
		List<Map<String, Serializable>> species = new ArrayList<>();
		PrivateAccessor.setField(facReportsModel, "species", species);

		// The conjugates
		List<LinkedHashMap<String, String>> conjugates = new ArrayList<>();
		PrivateAccessor.setField(facReportsModel, "conjugates", conjugates);

		// The FacListSubNavModel
		PrivateAccessor.setField(subNav, "label", "label");
		PrivateAccessor.setField(subNav, "path", "/content/bdb/na/us/en-us/facselect");
		PrivateAccessor.setField(subNav, "iconUrl", "/content/dam/bdb/na/us/en-us/facselect.jpg");
		PrivateAccessor.setField(subNav, "description", "description");

		List<FacListSubNavModel> subNavList = new ArrayList<>();
		subNavList.add(subNav);

		// The FACListNavigation
		PrivateAccessor.setField(navigation, "label", "label");
		PrivateAccessor.setField(navigation, "path", "/content/bdb/na/us/en-us/facselect");
		PrivateAccessor.setField(navigation, "target", "_blank");
		PrivateAccessor.setField(navigation, "subNavigation", subNavList);

		List<FACListNavigation> navList = new ArrayList<>();
		navList.add(navigation);

		PrivateAccessor.setField(facReportsModel, "navigation", navList);

		// The Species Model
		PrivateAccessor.setField(speciesModel, "speciesLabel", "species");
		PrivateAccessor.setField(speciesModel, "data", "true");
		PrivateAccessor.setField(speciesModel, "reagent", "true");
		PrivateAccessor.setField(speciesModel, "notSuggested", "true");

		List<SpeciesModel> speciesList = new ArrayList<>();
		speciesList.add(speciesModel);
		PrivateAccessor.setField(facReportsModel, "selectedSpecies", speciesList);

		// The Fluorochromes Model
		PrivateAccessor.setField(flModel, "fname", "fname");
		PrivateAccessor.setField(flModel, "data", "true");
		PrivateAccessor.setField(flModel, "reagent", "true");
		PrivateAccessor.setField(flModel, "tableValue", "1.345");
		PrivateAccessor.setField(flModel, "notSuggested", "true");

		List<FluorochromesModel> flModelList = new ArrayList<>();
		flModelList.add(flModel);
		PrivateAccessor.setField(facReportsModel, "selectedFluorochromes", flModelList);

		PrivateAccessor.setField(fsGating, "imageLabel", "");
		PrivateAccessor.setField(fsGating, "imageUrl", "");
		PrivateAccessor.setField(fsGating, "notesHeading", "");
		PrivateAccessor.setField(fsGating, "notesDescription", "");
		PrivateAccessor.setField(fsGating, "permLabel", "");
		PrivateAccessor.setField(fsGating, "permUrl", "");
		PrivateAccessor.setField(fsGating, "valueText", "");
		PrivateAccessor.setField(fsGating, "valueHref", "");

		List<FACGatingImages> fsGatingList = new ArrayList<>();

		fsGatingList.add(fsGating);

		PrivateAccessor.setField(facReportsModel, "entrezLinks", fsGatingList);

		PrivateAccessor.setField(facReportsModel, "displaySource", "displaySource");
		PrivateAccessor.setField(facReportsModel, "protocol", "protocol");
		PrivateAccessor.setField(facReportsModel, "protocolAltText", "protocolAltText");
		PrivateAccessor.setField(facReportsModel, "clone", "clone");
		PrivateAccessor.setField(facReportsModel, "source", "source");
		PrivateAccessor.setField(facReportsModel, "target", "target");
		PrivateAccessor.setField(facReportsModel, "displaySource", "displaySource");

		lenient().when(facReportsModel.getResolver()).thenReturn(resourceResolver);
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(PageManager.class)).thenReturn(PageManager);
		lenient().when(PageManager.getContainingPage(resource)).thenReturn(page);
		lenient().when(page.getPath()).thenReturn("/content/bdb/na/us/en-us/facselect");
		lenient().when(externalizer.getFormattedUrl("/content/bdb/na/us/en-us/facselect", resourceResolver))
				.thenReturn("https://localhost/content/bdb/na/us/en-us/facselect");
		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(tagManager.resolve("/content/cq:tags/facselect")).thenReturn(tag);
	}

	/**
	 * Tests getters for {@link FacListSubNavModel}
	 */
	@Test
	public void testGetters1() {
		assertNotNull(subNav.getDescription());
		assertNotNull(subNav.getLabel());
		assertNotNull(subNav.getPath());
		assertNotNull(subNav.getIconUrl());

	}

	/**
	 * Tests getters for {@link FACListNavigation}
	 */
	@Test
	public void testGetters2() {
		assertNotNull(navigation.getLabel());
		assertNotNull(navigation.getPath());
		assertNotNull(navigation.getTarget());
		assertNotNull(navigation.getSubNavigation());
	}

	/**
	 * Tests getters for {@link SpeciesModel}
	 */
	@Test
	public void testGetters3() {
		assertNotNull(speciesModel.getSpeciesLabel());
		assertNotNull(speciesModel.getData());
		assertNotNull(speciesModel.getReagent());
		assertNotNull(speciesModel.getNotSuggested());
	}

	/**
	 * Tests getters for {@link FluorochromesModel}
	 */
	@Test
	public void testGetters4() {
		assertNotNull(flModel.getData());
		assertNotNull(flModel.getReagent());
		assertNotNull(flModel.getNotSuggested());
		assertNotNull(flModel.getFname());
		assertNotNull(flModel.getTableValue());
		
	}
	
	/**
	 * Tests getters for {@link FACGatingImages}
	 */
	@Test
	public void testGetters5() {
		assertNotNull(fsGating.getImageLabel());
		assertNotNull(fsGating.getImageUrl());
		assertNotNull(fsGating.getNotesDescription());
		assertNotNull(fsGating.getNotesHeading());
		assertNotNull(fsGating.getPermLabel());
		assertNotNull(fsGating.getPermUrl());
		assertNotNull(fsGating.getValueHref());
		assertNotNull(fsGating.getValueText());
		
	}


	/**
	 * Tests getters for {@link FACReportsModel}
	 */
	@Test
	void testAllGetters6() {

		assertNotNull(facReportsModel.getProtocol());
		assertNotNull(facReportsModel.getSource());
		assertNotNull(facReportsModel.getTarget());
		assertNotNull(facReportsModel.getClone());
		assertNotNull(facReportsModel.getSpecies());
		assertNotNull(facReportsModel.getFluorochromes());
		assertNotNull(facReportsModel.getSelectedFluorochromes());
		assertNotNull(facReportsModel.getSpecificity());
		assertNotNull(facReportsModel.getSelectedSpecies());
		assertNotNull(facReportsModel.getPath());
		assertNotNull(facReportsModel.getNavigation());
		assertNotNull(facReportsModel.getReactiveSpecies());
		assertNotNull(facReportsModel.getConjugates());
		assertNotNull(facReportsModel.getMappingList());
		assertNotNull(facReportsModel.getEntrezLinks());
		assertNotNull(facReportsModel.getSourceAltText());
		assertNotNull(facReportsModel.getDisplaySource());
	}
	
	
}
