package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.bean.CountryLocaleBean;
import com.bdb.aem.core.bean.GlobalNavigationInformationBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class GlobalHeaderNavigationModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class GlobalHeaderNavigationModelTest {

	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

	/** The value template. */
	String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

	/** The value page title. */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";

	/** The global header navigation model. */
	@InjectMocks
	private GlobalHeaderNavigationModel globalHeaderNavigationModel;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The resource. */
	@Mock
	Resource resource, root, rootchild, nextResource, parent;

	/** The node. */
	@Mock
	Node node, rootNode, parentnode;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The in VM. */
	@Mock
	InheritanceValueMap inVM;

	/** The node iterator. */
	@Mock
	NodeIterator nodeIterator;

	/** The resource iterator. */
	@Mock
	Iterator<Resource> resourceIterator;

	/** The context. */
	@Mock
	ComponentContext context;

	/** The value map. */
	@Mock
	ValueMap valueMap;

	/** The property. */
	@Mock
	Property property;
	/** The Object. */
	@Mock
	Object object;
	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;
	/** The hp node resource. */
	@Mock
	Resource hpNodeResource, genericListResource1;
	/** The dropdown items. */
	private List<CountryLocaleBean> dropdownItems;

	/** The navigation items. */
	private List<GlobalNavigationInformationBean> navigationItems;

	/** The country name. */
	String countryName = "countryName";

	/** The language name. */
	String languageName;

	/** The current country-language name. */
	String currentCL;

	/** The compare current country-language name. */
	String compareCurrentCL;

	/** The region value. */
	String regionValue;

	/** The country value. */
	String countryValue = "countryValue";

	/** The language value. */
	String languageValue;
	String path = "/content/bdb";
	int index = 0;
	/** The node. */
	/** fieldMultifield mock value for multifield */
	List<Resource> fieldMultifield = new ArrayList<Resource>();
	List<GlobalNavigationInformationBean> navigation = new ArrayList<>();
	List<CountryLocaleBean> countryobj = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		GlobalNavigationInformationBean globalnavitem = new GlobalNavigationInformationBean();
		List<GlobalNavigationInformationBean> navigation = new ArrayList<>();
		PrivateAccessor.setField(globalnavitem, "modelImageDesc", "modelImageDescs");
		PrivateAccessor.setField(globalnavitem, "modelImageLink", "modelImageLink");
		PrivateAccessor.setField(globalnavitem, "productLabel", "productLabel");
		PrivateAccessor.setField(globalnavitem, "modelImagePath", "modelImagePath");
		PrivateAccessor.setField(globalnavitem, "modelImageAltText", "modelImageAltText");
		PrivateAccessor.setField(globalnavitem, "modelImageTitle", "modelImageTitle");
		PrivateAccessor.setField(globalnavitem, "modelImageCTALabel", "modelImageCTALabel");
		PrivateAccessor.setField(globalnavitem, "redirectPath", "redirectPath");
		PrivateAccessor.setField(globalnavitem, "productLabelTrimmed", "productLabelTrimmed");
		navigation.add(globalnavitem);
		lenient().when(resource.adaptTo(GlobalNavigationInformationBean.class)).thenReturn(globalnavitem);
		PrivateAccessor.setField(globalHeaderNavigationModel, "navigationItems", navigation);
		CountryLocaleBean countrydropdownItems = new CountryLocaleBean();
		List<CountryLocaleBean> countryobj = new ArrayList<>();
		PrivateAccessor.setField(countrydropdownItems, "countryName", "countryName");
		PrivateAccessor.setField(countrydropdownItems, "localeName", "localeName");
		countryobj.add(countrydropdownItems);
		lenient().when(resource.adaptTo(CountryLocaleBean.class)).thenReturn(countrydropdownItems);
		PrivateAccessor.setField(globalHeaderNavigationModel, "dropdownItems", countryobj);
		lenient().when(node.hasProperty(CommonConstants.CONST_IMAGE_DESC)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_IMAGE_DESC)).thenReturn(property);
		lenient().when(node.hasProperty(CommonConstants.CONST_IMAGE_LINK)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_IMAGE_LINK)).thenReturn(property);
		lenient().when(node.hasProperty(CommonConstants.CONST_IMAGE_PATH)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_IMAGE_PATH)).thenReturn(property);
		lenient().when(node.hasProperty(CommonConstants.CONST_IMAGE_TITLE)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_IMAGE_TITLE)).thenReturn(property);
		lenient().when(node.hasProperty(CommonConstants.CONST_IMAGE_ALT)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_IMAGE_ALT)).thenReturn(property);
		lenient().when(node.hasProperty(CommonConstants.CONST_IMAGE_CTA_LABEL)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_IMAGE_CTA_LABEL)).thenReturn(property);
	}

	@Test
	void testGetters() throws Exception {

		assertNotNull(globalHeaderNavigationModel.getNavigationItems());
		assertNotNull(globalHeaderNavigationModel.getDropdownItems());

	}

	@Test
	void testConstructModelElseRedirect() throws Exception {
		lenient().when(node.getNodes()).thenReturn(nodeIterator);
		lenient().when(nodeIterator.hasNext()).thenReturn(true, false);
		lenient().when(nodeIterator.nextNode()).thenReturn(node);
		lenient().when(node.getProperty(JcrConstants.JCR_PRIMARYTYPE)).thenReturn(property);
		lenient().when(property.getString()).thenReturn("cq:Page");
		lenient().when(node.hasNode(JcrConstants.JCR_CONTENT)).thenReturn(true);
		lenient().when(node.getNode(JcrConstants.JCR_CONTENT)).thenReturn(node);
		lenient().when(node.hasProperty(CommonConstants.CONST_REDIRECT_LINK)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_REDIRECT_LINK)).thenReturn(property);
		lenient().when(node.hasProperty(CommonConstants.CONST_HIDEINNAV)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_HIDEINNAV)).thenReturn(property);
		globalHeaderNavigationModel.constructModel(node, index, navigation, resourceResolver);
	}

	@Test
	void testConstructModelElseIf() throws Exception {
		lenient().when(node.getNodes()).thenReturn(nodeIterator);
		lenient().when(nodeIterator.hasNext()).thenReturn(true, false);
		lenient().when(nodeIterator.nextNode()).thenReturn(node);
		lenient().when(node.getProperty(JcrConstants.JCR_PRIMARYTYPE)).thenReturn(property);
		lenient().when(property.getString()).thenReturn("cq:Page");
		lenient().when(node.hasNode(JcrConstants.JCR_CONTENT)).thenReturn(true);
		lenient().when(node.getNode(JcrConstants.JCR_CONTENT)).thenReturn(node);
		lenient().when(node.hasProperty(CommonConstants.CONST_REDIRECT_LINK)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_REDIRECT_LINK)).thenReturn(property);
		lenient().when(node.hasProperty(JcrConstants.JCR_TITLE)).thenReturn(true);
		lenient().when(node.getProperty(JcrConstants.JCR_TITLE)).thenReturn(property);
		globalHeaderNavigationModel.constructModel(node, index, navigation, resourceResolver);
	}

	@Test
	void testConstructModelElse() throws Exception {
		lenient().when(node.getNodes()).thenReturn(nodeIterator);
		lenient().when(nodeIterator.hasNext()).thenReturn(true, false);
		lenient().when(nodeIterator.nextNode()).thenReturn(node);
		lenient().when(node.getProperty(JcrConstants.JCR_PRIMARYTYPE)).thenReturn(property);
		lenient().when(property.getString()).thenReturn("cq:Page");
		lenient().when(node.hasNode(JcrConstants.JCR_CONTENT)).thenReturn(true);
		lenient().when(node.getNode(JcrConstants.JCR_CONTENT)).thenReturn(node);
		lenient().when(node.hasProperty(CommonConstants.CONST_REDIRECT_LINK)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_REDIRECT_LINK)).thenReturn(property);
		lenient().when(node.hasProperty("pageTitle")).thenReturn(true);
		lenient().when(node.getProperty("pageTitle")).thenReturn(property);
		globalHeaderNavigationModel.constructModel(node, index, navigation, resourceResolver);
	}

	@Test
	void testConstructModel() throws Exception {
		lenient().when(node.getNodes()).thenReturn(nodeIterator);
		lenient().when(nodeIterator.hasNext()).thenReturn(true, false);
		lenient().when(nodeIterator.nextNode()).thenReturn(node);
		lenient().when(node.getProperty(JcrConstants.JCR_PRIMARYTYPE)).thenReturn(property);
		lenient().when(property.getString()).thenReturn("cq:Page");
		lenient().when(node.hasNode(JcrConstants.JCR_CONTENT)).thenReturn(true);
		lenient().when(node.getNode(JcrConstants.JCR_CONTENT)).thenReturn(node);
		lenient().when(node.hasProperty(CommonConstants.CONST_REDIRECT_LINK)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.CONST_REDIRECT_LINK)).thenReturn(property);
		lenient().when(node.hasProperty("navTitle")).thenReturn(true);
		lenient().when(node.getProperty("navTitle")).thenReturn(property);
		globalHeaderNavigationModel.constructModel(node, index, navigation, resourceResolver);
	}

	@Test
	void testConstructDropdown() throws Exception {
		List<CountryLocaleBean> dropdownList = new ArrayList<>();
		lenient().when(node.getNodes()).thenReturn(nodeIterator);
		lenient().when(nodeIterator.hasNext()).thenReturn(true, false);
		lenient().when(nodeIterator.nextNode()).thenReturn(node);
		lenient().when(node.getProperty(JcrConstants.JCR_PRIMARYTYPE)).thenReturn(property);
		lenient().when(property.getString()).thenReturn("cq:Page");
		lenient().when(node.getNode(JcrConstants.JCR_CONTENT)).thenReturn(node);
		lenient().when(node.hasProperty(CommonConstants.LANGUAGE)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.LANGUAGE)).thenReturn(property);
		lenient().when(bdbApiEndpointService.languageDropdownEndpoint()).thenReturn(VALUE_PAGE_RESOURCE_TYPE);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(genericListResource1);
		globalHeaderNavigationModel.constructDropdown(node, dropdownList, resourceResolver);
	}

	@Test
	void testNull() throws Exception {
		GlobalNavigationInformationBean globalnullnavitem = new GlobalNavigationInformationBean();
		List<GlobalNavigationInformationBean> navigation = new ArrayList<>();
		PrivateAccessor.setField(globalnullnavitem, "modelImageDesc", null);
		PrivateAccessor.setField(globalnullnavitem, "modelImageLink", null);
		PrivateAccessor.setField(globalnullnavitem, "productLabel", null);
		PrivateAccessor.setField(globalnullnavitem, "modelImagePath", null);
		PrivateAccessor.setField(globalnullnavitem, "modelImageAltText", null);
		PrivateAccessor.setField(globalnullnavitem, "modelImageTitle", null);
		PrivateAccessor.setField(globalnullnavitem, "modelImageCTALabel", null);
		PrivateAccessor.setField(globalnullnavitem, "redirectPath", null);
		PrivateAccessor.setField(globalnullnavitem, "productLabelTrimmed", null);
		navigation.add(globalnullnavitem);
		lenient().when(resource.adaptTo(GlobalNavigationInformationBean.class)).thenReturn(null);
		PrivateAccessor.setField(globalHeaderNavigationModel, "navigationItems", navigation);
		CountryLocaleBean countrydropdownnullItems = new CountryLocaleBean();
		List<CountryLocaleBean> countryobj = new ArrayList<>();
		PrivateAccessor.setField(countrydropdownnullItems, "countryName", null);
		PrivateAccessor.setField(countrydropdownnullItems, "localeName", null);
		countryobj.add(countrydropdownnullItems);
		lenient().when(resource.adaptTo(CountryLocaleBean.class)).thenReturn(null);
		PrivateAccessor.setField(globalHeaderNavigationModel, "dropdownItems", null);
		assertNull(globalHeaderNavigationModel.getNavigationItems().get(0).getModelImageDesc());
		assertNull(globalHeaderNavigationModel.getNavigationItems().get(0).getModelImageLink());
		assertNull(globalHeaderNavigationModel.getNavigationItems().get(0).getProductLabel());
		assertNull(globalHeaderNavigationModel.getNavigationItems().get(0).getModelImagePath());
		assertNull(globalHeaderNavigationModel.getNavigationItems().get(0).getModelImageAltText());
		assertNull(globalHeaderNavigationModel.getNavigationItems().get(0).getModelImageCTALabel());
		assertNull(globalHeaderNavigationModel.getNavigationItems().get(0).getRedirectPath());
		assertNull(globalHeaderNavigationModel.getCountryName());
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(genericListResource1);
		lenient().when(genericListResource1.adaptTo(Node.class)).thenReturn(node);
		lenient().when(node.getNodes()).thenReturn(nodeIterator);
		lenient().when(nodeIterator.hasNext()).thenReturn(true, false);
		lenient().when(nodeIterator.nextNode()).thenReturn(node);
		lenient().when(node.getProperty(JcrConstants.JCR_PRIMARYTYPE)).thenReturn(property);
		lenient().when(property.getString()).thenReturn("cq:Page");
		lenient().when(node.getNode(JcrConstants.JCR_CONTENT)).thenReturn(node);
		lenient().when(node.hasProperty(CommonConstants.LANGUAGE)).thenReturn(true);
		lenient().when(node.getProperty(CommonConstants.LANGUAGE)).thenReturn(property);
		lenient().when(bdbApiEndpointService.languageDropdownEndpoint()).thenReturn(VALUE_PAGE_RESOURCE_TYPE);
		globalHeaderNavigationModel.init();
	}

	@Test
	void testMethodInit() throws LoginException, RepositoryException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource(bdbApiEndpointService.getCountryStateDropdownEndpoint()))
				.thenReturn(resource);
		globalHeaderNavigationModel.getCountryLanguageCombo(countryName, languageName);
		globalHeaderNavigationModel.init();
	}

	@Test
	void testConstructModal() throws LoginException, RepositoryException {
		lenient().when(resourceResolver.getResource(path.toString())).thenReturn(resource);
		lenient().when(resource.adaptTo(Node.class)).thenReturn(node);
		lenient().when(rootNode.getNodes()).thenReturn(nodeIterator);
		lenient().when(parent.getParent()).thenReturn(hpNodeResource);
		lenient().when(nodeIterator.nextNode()).thenReturn(node);
		lenient().when(rootchild.listChildren()).thenReturn(resourceIterator);
		lenient().when(resourceIterator.hasNext()).thenReturn(true, false);
		lenient().when(resourceIterator.next()).thenReturn(nextResource);
		lenient().when(nodeIterator.hasNext()).thenReturn(true);
		lenient().when(nodeIterator.nextNode()).thenReturn(parentnode);
		globalHeaderNavigationModel.init();

	}

	@Test
	void testLoginException() throws LoginException, RepositoryException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenThrow(LoginException.class);
		// lenient().when(resourceResolver.getResource(bdbApiEndpointService.getCountryStateDropdownEndpoint())).thenReturn(resource);
		// lenient().when(CommonHelper.getGenericList(genericListResource1,
		// resourceResolver)).thenReturn(countryName);
		globalHeaderNavigationModel.init();
	}

	/**
	 * Test get drop down items.
	 *
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testGetDropDownItems() throws LoginException, RepositoryException {
		globalHeaderNavigationModel.getDropdownItems();
	}

	/**
	 * Test get navigation items.
	 *
	 * @throws RepositoryException the repository exception
	 * @throws LoginException      the login exception
	 */
	@Test
	void testGetNavigationItems() throws RepositoryException, LoginException {
		globalHeaderNavigationModel.getNavigationItems();
	}

	/**
	 * Test get current page.
	 */
	@Test
	void testGetCurrentPage() {
		globalHeaderNavigationModel.getCurrentPage();
	}

	/**
	 * Test get country name.
	 */
	@Test
	void testGetCountryName() {
		globalHeaderNavigationModel.getCountryName();
	}

	/**
	 * Test get language name.
	 */
	@Test
	void testGetLanguageName() {
		globalHeaderNavigationModel.getLanguageName();
	}

	/**
	 * Test get current CL.
	 */
	@Test
	void testGetCurrentCL() {
		globalHeaderNavigationModel.getCurrentCL();
	}

	/**
	 * Test get compare current CL.
	 */
	@Test
	void testGetCompareCurrentCL() {
		globalHeaderNavigationModel.getCompareCurrentCL();
	}

	/**
	 * Test get region value.
	 */
	@Test
	void testGetRegionValue() {
		globalHeaderNavigationModel.getRegionValue();
	}

	/**
	 * Test get country value.
	 */
	@Test
	void testGetCountryValue() {
		globalHeaderNavigationModel.getCountryValue();
	}

	/**
	 * Test get language value.
	 */
	@Test
	void testGetLanguageValue() {
		globalHeaderNavigationModel.getLanguageValue();
	}
}
