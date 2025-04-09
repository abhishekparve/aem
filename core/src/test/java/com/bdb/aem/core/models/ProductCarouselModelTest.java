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

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class ProductCarouselModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ProductCarouselModelTest {

    /** The aem context. */
    private final AemContext aemContext = new AemContext();

    /** Mock ResourceResolverFactory. */
    @Mock
    ExternalizerService externalizerService;

    /** Resourceresolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /**  The ComponentContext context *. */
    @Mock
    ComponentContext context;

    
    /** The product carousel model. */
    @InjectMocks
    ProductCarouselModel productCarouselModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(productCarouselModel, "title", "title");
    	ProductCarouselDetailsModel productCarouselDetailsModel = new ProductCarouselDetailsModel();
    	PrivateAccessor.setField(productCarouselDetailsModel, "imagePath", "imagePath");
		PrivateAccessor.setField(productCarouselDetailsModel, "altText", "altText"); 
		PrivateAccessor.setField(productCarouselDetailsModel, "productLabel", "productLabel");
		PrivateAccessor.setField(productCarouselDetailsModel, "productUrl", "productUrl"); 
		ArrayList<ProductCarouselDetailsModel> productsList = new ArrayList<>();
		productsList.add(productCarouselDetailsModel);
		PrivateAccessor.setField(productCarouselModel, "products",productsList); 
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
    	lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("productUrl", resourceResolver)).thenReturn("/content/page.html");
    	assertNotNull(productCarouselModel.getTitle());
        assertNotNull(productCarouselModel.getProducts());        
    }

    /**
     * Test init.
     *
     * @throws LoginException the login exception
     */
    @Test
    void testInit() throws LoginException{
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
    }
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
	}
}
