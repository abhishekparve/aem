package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

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

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class CategoryInfoModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class CategoryInfoModelTest {

    /** The aem context. */
    private final AemContext aemContext = new AemContext();

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

    /** The bdb api endpoint service. */
    @Mock
    BDBApiEndpointService bdbApiEndpointService;

    /** The category info model. */
    @InjectMocks
    CategoryInfoModel categoryInfoModel;
    
    @Mock
	Resource currentResource;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(categoryInfoModel, "imagePath", "imagePath");
		PrivateAccessor.setField(categoryInfoModel, "imagePathMobile", "imagePathMobile");
		PrivateAccessor.setField(categoryInfoModel, "sectionTitle", "sectionTitle");
		PrivateAccessor.setField(categoryInfoModel, "title", "title");
		PrivateAccessor.setField(categoryInfoModel, "subTitle", "subTitle");
		PrivateAccessor.setField(categoryInfoModel, "description", "description");
		PrivateAccessor.setField(categoryInfoModel, "urlCta", "urlCta");
		PrivateAccessor.setField(categoryInfoModel, "labelCta", "labelCta");
		PrivateAccessor.setField(categoryInfoModel, "altText", "altText");
		PrivateAccessor.setField(categoryInfoModel, "urlCtaAdd", "urlCtaAdd");
		PrivateAccessor.setField(categoryInfoModel, "labelCtaAdd", "labelCtaAdd");
		PrivateAccessor.setField(categoryInfoModel, "imageCaption", "imageCaption");
		PrivateAccessor.setField(categoryInfoModel, "backgroundImg", "backgroundImg");
		PrivateAccessor.setField(categoryInfoModel, "mobileBackgroundImg", "mobileBackgroundImg");
		PrivateAccessor.setField(categoryInfoModel, "modalImgFlag", "true");
		PrivateAccessor.setField(categoryInfoModel, "imageEnlargeSize", "imageEnlargeSize");
		PrivateAccessor.setField(categoryInfoModel, "imagePathModal", "imagePathModal");
		PrivateAccessor.setField(categoryInfoModel, "imageModalTitleText", "imageModalTitleText");
		PrivateAccessor.setField(categoryInfoModel, "imageAltModalText", "imageAltModalText");
		PrivateAccessor.setField(categoryInfoModel, "imageDescText", "imageDescText");
		CategoryInfoCLPDetailsModel categoryInfoCLPDetailsModel = new CategoryInfoCLPDetailsModel() ;
		PrivateAccessor.setField(categoryInfoCLPDetailsModel, "linkLabel", "linkLabel");
		PrivateAccessor.setField(categoryInfoCLPDetailsModel, "linkUrl", "linkUrl"); 
		ArrayList<CategoryInfoCLPDetailsModel> CLPLinksList = new ArrayList<>();
		CLPLinksList.add(categoryInfoCLPDetailsModel);
		PrivateAccessor.setField(categoryInfoModel, "links",CLPLinksList); 
		lenient().when(currentResource.getName()).thenReturn("CategoryInfoModel");
		lenient().when(currentResource.getParent()).thenReturn(currentResource);
		
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
    	lenient().when(externalizerService.getFormattedUrl("linkUrl", resourceResolver)).thenReturn("/content/page.html");
    	lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("imagePathMobile", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("urlCta", resourceResolver)).thenReturn("/content/page.html");
    	lenient().when(externalizerService.getFormattedUrl("urlCtaAdd", resourceResolver)).thenReturn("/content/page.html");
    	lenient().when(externalizerService.getFormattedUrl("backgroundImg", resourceResolver)).thenReturn("/content/page.html");
    	lenient().when(externalizerService.getFormattedUrl("mobileBackgroundImg", resourceResolver)).thenReturn("/content/page.html");
    	lenient().when(externalizerService.getFormattedUrl("imagePathModal", resourceResolver)).thenReturn("/content/page.html");
    	
        assertNotNull(categoryInfoModel.getAltText());
        assertNotNull(categoryInfoModel.getDescription());
        assertNotNull(categoryInfoModel.getImagePath());
        assertNotNull(categoryInfoModel.getImagePathMobile());
        assertNotNull(categoryInfoModel.getLabelCta());
        assertNotNull(categoryInfoModel.getLabelCtaAdd());
        assertNotNull(categoryInfoModel.getLinks());
        assertNotNull(categoryInfoModel.getSectionTitle());
        assertNotNull(categoryInfoModel.getSubTitle());
        assertNotNull(categoryInfoModel.getTitle());
        assertNotNull(categoryInfoModel.getUrlCta());
        assertNotNull(categoryInfoModel.getUrlCtaAdd());
        assertNotNull(categoryInfoModel.getImageCaption());
        assertNotNull(categoryInfoModel.getArticleId());
        assertNotNull(categoryInfoModel.getBackgroundImg());
        assertNotNull(categoryInfoModel.getModalImgFlag());
        assertNotNull(categoryInfoModel.getMobileBackgroundImg());
        assertNotNull(categoryInfoModel.getImageEnlargeSize());
        assertNotNull(categoryInfoModel.getImagePathModal());
        assertNotNull(categoryInfoModel.getImageModalTitleText());
        assertNotNull(categoryInfoModel.getImageAltModalText());
        assertNotNull(categoryInfoModel.getImageDescText());
    }
    
    /**
     * Test brightcove ids.
     */
    @Test
    void testBrightcoveIds() {
    	lenient().when(bdbApiEndpointService.brightcovePlayerId()).thenReturn("/content/dam/image-mob.png");
    	lenient().when(bdbApiEndpointService.brightcoveAccountId()).thenReturn("/content/dam/image-mob.png");
    	
        assertNotNull(categoryInfoModel.getBrightcoveAccountId());
        assertNotNull(categoryInfoModel.getBrightcovePlayerId());
    }
    
    /**
     * Test else conditions.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
    void testElseConditions() throws NoSuchFieldException {
    	PrivateAccessor.setField(categoryInfoModel, "sectionTitle", "");
		PrivateAccessor.setField(categoryInfoModel, "title", "title");
		PrivateAccessor.setField(categoryInfoModel, "modalImgFlag", "false");
    	
		assertNotNull(categoryInfoModel.getArticleId());
        assertNotNull(categoryInfoModel.getModalImgFlag());
    }
    
    /**
     * Test init.
     *
     * @throws LoginException the login exception
     */
    @Test
    void testInit() throws LoginException{
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        categoryInfoModel.init();
    }
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		categoryInfoModel.init();
	}
}
