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

import com.bdb.aem.core.services.tools.impl.DefaultComponentServiceImpl;
import com.bdb.aem.core.services.tools.impl.DefaultDataServiceImpl;
import com.day.cq.wcm.api.Page;

import junitx.util.PrivateAccessor;

/**
 * The Class BaseSlingModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class BaseSlingModelTest {
	
	/** The base sling model. */
	@InjectMocks
	BaseSlingModel baseSlingModel;
	
	/** The resource. */
	@Mock
	Resource resource;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The page. */
	@Mock
	Page page;
	
	/** The default data service. */
	DefaultDataServiceImpl defaultDataService;
	
	/** The default component service. */
	DefaultComponentServiceImpl defaultComponentService;
	
	/** The component json. */
	String componentJson;

	/**
	 * Setup.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setup() throws NoSuchFieldException {
		defaultDataService = new DefaultDataServiceImpl();
		defaultComponentService = new DefaultComponentServiceImpl();
		componentJson = "componentJson";
		PrivateAccessor.setField(baseSlingModel, "defaultDataService", defaultDataService);
		PrivateAccessor.setField(baseSlingModel, "defaultComponentService", defaultComponentService);
		PrivateAccessor.setField(baseSlingModel, "resource", resource);
		PrivateAccessor.setField(baseSlingModel, "page", page);
		PrivateAccessor.setField(baseSlingModel, "componentJson", componentJson);
	}
	
	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		baseSlingModel.init();
	}

	/**
	 * Test get resource.
	 */
	@Test
	void testGetResource() {
		assertNotNull(baseSlingModel.getResource());
	}
	
	/**
	 * Test get current page.
	 */
	@Test
	void testGetCurrentPage() {
		assertNotNull(baseSlingModel.getCurrentPage());
	}

	/**
	 * Test get component service.
	 */
	@Test
	void testGetComponentService() {
		assertNotNull(baseSlingModel.getComponentService());
	}

	/**
	 * Test get component json.
	 */
	@Test
	void testGetComponentJson() {
		assertEquals("componentJson", baseSlingModel.getComponentJson());
	}

	/**
	 * Test get resolver.
	 */
	@Test
	void testGetResolver() {
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		assertNotNull(baseSlingModel.getResolver());
	}
}
