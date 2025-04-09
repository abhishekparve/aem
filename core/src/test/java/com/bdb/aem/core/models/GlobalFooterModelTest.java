package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;

import junitx.util.PrivateAccessor;

/**
 * The Class GlobalFooterModelTest.
 */
@ExtendWith({ MockitoExtension.class })
public class GlobalFooterModelTest {

	/** The global footer model. */
	@InjectMocks
	GlobalFooterModel globalFooterModel;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The context. */
	@Mock
	ComponentContext context;

	/** The global footer category model. */
	private GlobalFooterCategoryModel globalFooterCategoryModel;

	/** The global footer links model. */
	private GlobalFooterLinksModel globalFooterLinksModel;

	/** The global footer social model. */
	private GlobalFooterSocialModel globalFooterSocialModel;
	
	/** The global footer subcategory model. */
	private GlobalFooterSubcategoryModel globalFooterSubcategoryModel;

	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		globalFooterSocialModel = new GlobalFooterSocialModel();
		PrivateAccessor.setField(globalFooterSocialModel, "socialAlt", "Social Alt Text");
		ArrayList<GlobalFooterSocialModel> footerSocialList = new ArrayList<>();
		footerSocialList.add(globalFooterSocialModel);
		PrivateAccessor.setField(globalFooterModel, "social", footerSocialList);

		globalFooterLinksModel = new GlobalFooterLinksModel();
		PrivateAccessor.setField(globalFooterLinksModel, "linkURL", "/content/bdb/URL");
		ArrayList<GlobalFooterLinksModel> footerLinksList = new ArrayList<>();
		footerLinksList.add(globalFooterLinksModel);
		PrivateAccessor.setField(globalFooterModel, "footerLinks", footerLinksList);

		globalFooterCategoryModel = new GlobalFooterCategoryModel();
		PrivateAccessor.setField(globalFooterCategoryModel, "title", "Category Title");
		ArrayList<GlobalFooterCategoryModel> footerCategoryList = new ArrayList<>();
		footerCategoryList.add(globalFooterCategoryModel);
		PrivateAccessor.setField(globalFooterModel, "categories", footerCategoryList);
		
		globalFooterSubcategoryModel = new GlobalFooterSubcategoryModel();
		PrivateAccessor.setField(globalFooterSubcategoryModel, "url", "/content/bdb/URL");
		ArrayList<GlobalFooterSubcategoryModel> footerSubCategoryList = new ArrayList<>();
		footerSubCategoryList.add(globalFooterSubcategoryModel);
		PrivateAccessor.setField(globalFooterCategoryModel, "subcategories", footerSubCategoryList);
		
		PrivateAccessor.setField(globalFooterModel, "urlLogo", "urlLogo");
		PrivateAccessor.setField(globalFooterModel, "urlIcon", "urlIcon");
		PrivateAccessor.setField(globalFooterModel, "embededFormCode", "embededFormCode");
		PrivateAccessor.setField(globalFooterModel, "loadInModal", "loadInModal");
		PrivateAccessor.setField(globalFooterModel, "subCatName", "subCatName");

	}
	
	
	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
        lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		globalFooterModel.init();
	}
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenThrow(LoginException.class);
		globalFooterModel.init();
	}


	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		
		lenient().when(externalizerService.getFormattedUrl("urlLogo", resourceResolver)).thenReturn("/content/dam/logo.png");
		lenient().when(externalizerService.getFormattedUrl("urlIcon", resourceResolver)).thenReturn("/content/dam/icon.png");

		assertNotNull(globalFooterModel.getSocial());
		assertNotNull(globalFooterModel.getSocial().get(0));
		assertNotNull(globalFooterModel.getFooterLinks());
		assertNotNull(globalFooterModel.getFooterLinks().get(0));
		assertNotNull(globalFooterModel.getCategories());
		assertNotNull(globalFooterModel.getCategories().get(0));
		
		assertNotNull(globalFooterModel.getUrlLogo());
		assertNotNull(globalFooterModel.getUrlIcon());
		assertNotNull(globalFooterModel.getEmbededFormCode());
		assertNotNull(globalFooterModel.isLoadInModal());
		assertNotNull(globalFooterModel.getSubCatName());
		assertNotNull(globalFooterModel.getFooterLinksSize());
	}
	
	/**
	 * Test null values.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testNullValues() throws NoSuchFieldException {
		
		ArrayList<GlobalFooterSocialModel> footerSocialList = new ArrayList<>();
		ArrayList<GlobalFooterLinksModel> footerLinksList = new ArrayList<>();
		ArrayList<GlobalFooterCategoryModel> footerCategoryList = new ArrayList<>();
		
		PrivateAccessor.setField(globalFooterModel, "social", footerSocialList);
		PrivateAccessor.setField(globalFooterModel, "footerLinks", footerLinksList);
		PrivateAccessor.setField(globalFooterModel, "categories", footerCategoryList);
		PrivateAccessor.setField(globalFooterModel, "embededFormCode", "");
		PrivateAccessor.setField(globalFooterModel, "loadInModal", "");
		PrivateAccessor.setField(globalFooterModel, "subCatName", "");
		
		assertNotNull(globalFooterModel.getSocial());
		assertNotNull(globalFooterModel.getFooterLinks());
		assertNotNull(globalFooterModel.getCategories());
		assertNotNull(globalFooterModel.getEmbededFormCode());
		assertNotNull(globalFooterModel.isLoadInModal());
		assertNotNull(globalFooterModel.getSubCatName());
		assertNotNull(globalFooterModel.getFooterLinksSize());

	}

}
