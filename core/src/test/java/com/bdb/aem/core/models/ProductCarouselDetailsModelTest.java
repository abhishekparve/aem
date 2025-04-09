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
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.components.ComponentContext;

import junitx.util.PrivateAccessor;

/**
 * The Class ProductCarouselDetailsModelTest.
 */
@ExtendWith({ MockitoExtension.class })
public class ProductCarouselDetailsModelTest {

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;
	
	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The context. */
	@Mock
	ComponentContext context;
	
	/** The resource. */
	@Mock
	Resource resource;
	
	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;
	
	/** The current page. */
	@Mock
	Page currentPage;

	/** The page manager. */
	@Mock
	PageManager pageManager;

	/** The product carousel details model. */
	@InjectMocks
	ProductCarouselDetailsModel productCarouselDetailsModel;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(productCarouselDetailsModel, "imagePath", "imagePath");
		PrivateAccessor.setField(productCarouselDetailsModel, "altText", "altText");
		PrivateAccessor.setField(productCarouselDetailsModel, "productLabel", "productLabel");
		PrivateAccessor.setField(productCarouselDetailsModel, "productUrl", "productUrl");
		PrivateAccessor.setField(productCarouselDetailsModel, "linkType", "linkType");
	}

	/**
	 * Test all getters.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testAllGetters() throws NoSuchFieldException {
		lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver))
				.thenReturn("/content/dam/image.png");
		lenient().when(externalizerService.getFormattedUrl("productUrl", resourceResolver))
				.thenReturn("/content/page.html");
		assertNotNull(productCarouselDetailsModel.getImagePath());
		assertNotNull(productCarouselDetailsModel.getAltText());
		assertNotNull(productCarouselDetailsModel.getProductLabel());
		assertNotNull(productCarouselDetailsModel.getProductUrl());
		assertNotNull(productCarouselDetailsModel.getLinkType());
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		lenient().when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
		lenient().when(externalizerService.getFormattedUrl("pdpProductUrl", resourceResolver))
.thenReturn("/content/page.html");
		
		productCarouselDetailsModel.init();
	}
	
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		productCarouselDetailsModel.init();
	}

	/**
	 * Test map link url static.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testMapLinkUrlStatic() throws NoSuchFieldException {
		PrivateAccessor.setField(productCarouselDetailsModel, "linkType", "static");
		lenient().when(externalizerService.getFormattedUrl("productUrl", resourceResolver))
				.thenReturn("/content/dam/image.png");
		productCarouselDetailsModel.getProductUrl();
	}

	/**
	 * Test map link url PLP.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testMapLinkUrlPLP() throws NoSuchFieldException {
		PrivateAccessor.setField(productCarouselDetailsModel, "linkType", "plp");
		lenient().when(externalizerService.getFormattedUrl("productUrl", resourceResolver))
				.thenReturn("/content/dam/image.png");
		productCarouselDetailsModel.getProductUrl();
	}
	
	/**
	 * Test map link url PDP.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testMapLinkUrlPDP() throws NoSuchFieldException {
		PrivateAccessor.setField(productCarouselDetailsModel, "linkType", "pdp");
		lenient().when(externalizerService.getFormattedUrl("productUrl", resourceResolver))
				.thenReturn("/content/dam/image.png");
		productCarouselDetailsModel.getProductUrl();
	}

}
