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

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class ApplicationsAndSolutionsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ApplicationsAndSolutionsModelTest {

	
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
    
    @Mock
    Resource currentResource;

    /** The applications and solutions model. */
    @InjectMocks
    ApplicationsAndSolutionsModel applicationsAndSolutionsModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(applicationsAndSolutionsModel, "sectionTitle", "sectionTitle");
		PrivateAccessor.setField(applicationsAndSolutionsModel, "anchorIdentifier", "anchorIdentifier");  
		PrivateAccessor.setField(applicationsAndSolutionsModel, "subTitle", "subTitle");  
		PrivateAccessor.setField(applicationsAndSolutionsModel, "title", "title");  
		PrivateAccessor.setField(applicationsAndSolutionsModel, "backgroundColor", "backgroundColor");
		PrivateAccessor.setField(applicationsAndSolutionsModel, "contentBackgroundColor", "contentBackgroundColor");
		ApplicationsAndSolutionsContentModel applicationsAndSolutionsContent = new ApplicationsAndSolutionsContentModel() ;
		PrivateAccessor.setField(applicationsAndSolutionsContent, "contentTitle", "contentTitle");
		PrivateAccessor.setField(applicationsAndSolutionsContent, "contentDescription", "contentDescription"); 
		ArrayList<ApplicationsAndSolutionsContentModel> applicationsAndSolutionsList = new ArrayList<>();
		applicationsAndSolutionsList.add(applicationsAndSolutionsContent);
		PrivateAccessor.setField(applicationsAndSolutionsModel, "slides",applicationsAndSolutionsList);
		lenient().when(currentResource.getName()).thenReturn("ApplicationsAndSolutionsModel");
		lenient().when(currentResource.getParent()).thenReturn(currentResource);
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
    	assertNotNull(applicationsAndSolutionsModel.getAnchorIdentifier());
        assertNotNull(applicationsAndSolutionsModel.getBackgroundColor());
        assertNotNull(applicationsAndSolutionsModel.getContentBackgroundColor());
        assertNotNull(applicationsAndSolutionsModel.getSectionTitle());
        assertNotNull(applicationsAndSolutionsModel.getSlides());
        assertNotNull(applicationsAndSolutionsModel.getSubTitle());
        assertNotNull(applicationsAndSolutionsModel.getTitle());
        assertNotNull(applicationsAndSolutionsModel.getArticleId());
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
