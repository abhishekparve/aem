package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonHelper;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class RuoModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class RuoModelTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RuoModelTest.class);
	/** The aem context. */
	private final AemContext aemContext = new AemContext();
	
	/** The ruo model. */
	@InjectMocks
	RuoModel ruoModel;
	
	/** The ruo item resource. */
	@Mock
	private Resource ruoResource, ruoItemResource;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Resourceresolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;
	
	/** The value map. */
	@Mock
	ValueMap valueMap;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(TechnicalToolsModel.class);
		aemContext.load().json("/com/bdb/aem/core/models/RuoModelTest.json", "/content");
		ruoResource = aemContext.currentResource("/content/ruoFields");
		ruoItemResource = aemContext.currentResource("/content/ruoFields/item0");
		PrivateAccessor.setField(ruoModel, "ruoFields",ruoResource);
		PrivateAccessor.setField(ruoModel, "ruoLegendtitle","ruoLegendtitle");
		PrivateAccessor.setField(ruoModel, "ruoDescription","ruoDescription");
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit(){
		lenient().when(resourceResolver.getResource("/content/ruoFields/item0")).thenReturn(ruoItemResource);
		ruoModel.init();
	}
	
	/**
	 * Test get title.
	 */
	@Test
	void testGetTitle(){
		assertNotNull(ruoModel.getRuoLegendtitle());
	}
	
	/**
	 * Test get description.
	 */
	@Test
	void testGetDescription(){
		assertNotNull(ruoModel.getRuoDescription());
	}
	
	/**
	 * Test get ruo map.
	 */
	@Test
	void testGetRuoMap(){
		assertNotNull(ruoModel.getRuoMap());
	}
	
}
