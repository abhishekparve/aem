package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class CategoryInfoCLPDetailsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class CategoryInfoCLPDetailsModelTest {

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The resource. */
	@Mock
	Resource resource;

	/** The context. */
	@Mock
	ComponentContext context;
	
	/** The current page. */
	@Mock
	Page currentPage;

	/** The page manager. */
	@Mock
	PageManager pageManager;

	/** The category info CLP details model. */
	@InjectMocks
	CategoryInfoCLPDetailsModel categoryInfoCLPDetailsModel;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(categoryInfoCLPDetailsModel, "linkLabel", "linkLabel");
		PrivateAccessor.setField(categoryInfoCLPDetailsModel, "linkUrl", "linkUrl");
		PrivateAccessor.setField(categoryInfoCLPDetailsModel, "openNewTab", "openNewTab");
		PrivateAccessor.setField(categoryInfoCLPDetailsModel, "eventName", "eventName");
	}

	/**
	 * Test all getters.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testAllGetters() throws NoSuchFieldException {
		PrivateAccessor.setField(categoryInfoCLPDetailsModel, "linkType", "linkType");
		lenient().when(externalizerService.getFormattedUrl("linkUrl", resourceResolver))
				.thenReturn("/content/dam/image.png");
		assertNotNull(categoryInfoCLPDetailsModel.getLinkLabel());
		assertNotNull(categoryInfoCLPDetailsModel.getLinkUrl());
		assertNotNull(categoryInfoCLPDetailsModel.getLinkType());
		assertNotNull(categoryInfoCLPDetailsModel.getOpenNewTab());
		assertNotNull(categoryInfoCLPDetailsModel.getEventName());

	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		lenient().when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
		lenient().when(externalizerService.getFormattedUrl("pdpLinkUrlVal", resourceResolver))
.thenReturn("/content/page.html");
		categoryInfoCLPDetailsModel.init();
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		categoryInfoCLPDetailsModel.init();
	}

	/**
	 * Test map link url static.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testMapLinkUrlStatic() throws NoSuchFieldException {
		PrivateAccessor.setField(categoryInfoCLPDetailsModel, "linkType", "static");
		lenient().when(externalizerService.getFormattedUrl("linkUrl", resourceResolver))
				.thenReturn("/content/dam/image.png");
		categoryInfoCLPDetailsModel.getLinkUrl();
	}

	/**
	 * Test map link url PLP.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testMapLinkUrlPLP() throws NoSuchFieldException {
		PrivateAccessor.setField(categoryInfoCLPDetailsModel, "linkType", "plp");
		lenient().when(externalizerService.getFormattedUrl("", resourceResolver)).thenReturn("/content/dam/image.png");
		categoryInfoCLPDetailsModel.getLinkUrl();
	}

	/**
	 * Test map link url PDP.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testMapLinkUrlPDP() throws NoSuchFieldException {
		PrivateAccessor.setField(categoryInfoCLPDetailsModel, "linkType", "pdp");
		lenient().when(externalizerService.getFormattedUrl("", resourceResolver)).thenReturn("/content/dam/image.png");
		categoryInfoCLPDetailsModel.getLinkUrl();
	}

}
