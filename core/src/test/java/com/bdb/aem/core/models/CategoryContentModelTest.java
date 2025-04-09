package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

import org.apache.sling.api.resource.LoginException;
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

import com.bdb.aem.core.bean.CategoryContentBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class CategoryContentModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CategoryContentModelTest {

	/** The aem context. */
	private final AemContext aemContext = new AemContext();

	/** The category content model. */
	@InjectMocks
	CategoryContentModel categoryContentModel;

	/** The column item resource. */
	@Mock
	private Resource columnFields, columnItemResource;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The value map. */
	@Mock
	ValueMap valueMap;
	
	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

    /** The bdb api endpoint service. */
    @Mock
    BDBApiEndpointService bdbApiEndpointService;
    
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(TechnicalToolsModel.class);
		aemContext.load().json("/com/bdb/aem/core/models/CategoryContentTest.json", "/content");
		columnFields = aemContext.currentResource("/content/columnFields");
		columnItemResource = aemContext.currentResource("/content/columnFields/item0");
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		PrivateAccessor.setField(categoryContentModel, "columnFields", columnFields);
		
		PrivateAccessor.setField(categoryContentModel, "sectionLabel", "sectionLabel");
		PrivateAccessor.setField(categoryContentModel, "title", "title");
		PrivateAccessor.setField(categoryContentModel, "description", "description");
		PrivateAccessor.setField(categoryContentModel, "iconAlignment", "iconAlignment");
		PrivateAccessor.setField(categoryContentModel, "videoImage", "no");
		PrivateAccessor.setField(categoryContentModel, "brightcoveVideoIdCategoryContent", "brightcoveVideoIdCategoryContent");
		PrivateAccessor.setField(categoryContentModel, "videoImageDescription", "videoImageDescription");
		PrivateAccessor.setField(categoryContentModel, "vidImageAlt", "vidImageAlt");
		PrivateAccessor.setField(categoryContentModel, "videoImagePath", "/content/dam/bdb/arrow.png");
		PrivateAccessor.setField(categoryContentModel, "imageLink", "/content/dam/bdb/arrow.png");
		
		CategoryContentBean categoryContentBean = new CategoryContentBean() ;
		PrivateAccessor.setField(categoryContentBean, "iconHeading", "iconHeading");
		ArrayList<CategoryContentBean> contentBeanList = new ArrayList<>();
		contentBeanList.add(categoryContentBean);
		PrivateAccessor.setField(categoryContentModel, "categoryContentList",contentBeanList); 
	}
    
    /**
     * Test brightcove ids.
     */
    @Test
    void testBrightcoveIds() {
    	lenient().when(bdbApiEndpointService.brightcovePlayerId()).thenReturn("/content/dam/image-mob.png");
    	lenient().when(bdbApiEndpointService.brightcoveAccountId()).thenReturn("/content/dam/image-mob.png");
    	
        assertNotNull(categoryContentModel.getBrightcoveAccountId());
        assertNotNull(categoryContentModel.getBrightcovePlayerId());
    }
	
	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters(){
		lenient().when(externalizerService.getFormattedUrl("videoImagePath", resourceResolver)).thenReturn("/content/page.html");
    	lenient().when(externalizerService.getFormattedUrl("imageLink", resourceResolver)).thenReturn("/content/dam/image.png");
    	
		assertNotNull(categoryContentModel.getSectionLabel());
		assertNotNull(categoryContentModel.getTitle());
		assertNotNull(categoryContentModel.getDescription());
		assertNotNull(categoryContentModel.getIconAlignment());
		assertNotNull(categoryContentModel.getVideoImage());
		assertNotNull(categoryContentModel.getBrightcoveVideoIdCategoryContent());
		assertNotNull(categoryContentModel.getVideoImageDescription());
		assertNotNull(categoryContentModel.getVidImageAlt());
		assertNotNull(categoryContentModel.getVideoImagePath());
		assertNotNull(categoryContentModel.getImageLink());
		assertNotNull(categoryContentModel.getCategoryContentList());
	}
	
	/**
	 * Test else conditions.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testElseConditions() throws NoSuchFieldException{
		PrivateAccessor.setField(categoryContentModel, "videoImagePath", "");
		PrivateAccessor.setField(categoryContentModel, "videoImage", "yes");
		PrivateAccessor.setField(categoryContentModel, "imageLink", "");
		
		assertNotNull(categoryContentModel.getVideoImage());;
		assertNotNull(categoryContentModel.getVideoImagePath());
		assertNotNull(categoryContentModel.getImageLink());
		
		assertNotNull(resourceResolver);
		lenient().when(resourceResolver.getResource("/content/columnFields/item0")).thenReturn(columnItemResource);
		categoryContentModel.init();
	}
    
	/**
	 * Test init.
	 */
	@Test
	void testInit(){
		assertNotNull(resourceResolver);
		lenient().when(resourceResolver.getResource("/content/columnFields/item0")).thenReturn(columnItemResource);
		categoryContentModel.init();
	}
}
