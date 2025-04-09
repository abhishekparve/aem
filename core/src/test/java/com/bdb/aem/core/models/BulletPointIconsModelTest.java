package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class BulletPointIconsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BulletPointIconsModelTest {

	/** The bullet point icons model. */
	@InjectMocks
	BulletPointIconsModel bulletPointIconsModel;

	/** The label. */
	private final String LABEL = "label";

	/** The path. */
	private final String PATH = "PATH";

	/** The bullet point resource. */
	private Resource bulletPointResource;

	/** The context aem. */
	private AemContext contextAem;
	@Mock
	Resource resource;
	@Mock
	LabelImagePathAltModel labelImagePathAltModel;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Map<String, String> bulletPointProperty = new HashMap<>();
		bulletPointProperty.put("label", LABEL);
		bulletPointProperty.put("path", PATH);
		bulletPointProperty.put("altText", LABEL);
		List<Resource> bulletMultifield = new ArrayList<Resource>();
		bulletMultifield.add(resource);
		contextAem.addModelsForClasses(BulletPointIconsModel.class);
		PrivateAccessor.setField(bulletPointIconsModel, "bulletMultifield", bulletMultifield);

	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		when(resource.adaptTo(LabelImagePathAltModel.class)).thenReturn(labelImagePathAltModel);
		when(labelImagePathAltModel.getLabel()).thenReturn(LABEL);
		bulletPointIconsModel.init();
	}

	@Test
	void testGetDescriptionMultifield() {
		assertNull(bulletPointIconsModel.getDescriptionMultifield());
	}

	/**
	 * Test get bullet point icon list.
	 */
	@Test
	void testGetBulletPointIconList() {
		assertNotNull(bulletPointIconsModel.getBulletPointIconList());

	}
}
