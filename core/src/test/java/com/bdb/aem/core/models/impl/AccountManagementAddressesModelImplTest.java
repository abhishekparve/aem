package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class AccountManagementAddressesModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccountManagementAddressesModelImplTest {
	
	/** The value page path. */
	String VALUE_PAGE_PATH = "/content/bdb/testPage";

	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

	/** The value template. */
	String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

	/** The value page title. */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";

	/** The title. */
	String title = "title";

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The i 18 n. */
	@Mock
	I18n i18n;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The bdb api endpoint service. */
	@InjectMocks
	BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();

	/** The addresses model test impl. */
	@InjectMocks
	private AccountManagementAddressesModelImpl addressesModelTestImpl;

	/** The context. */
	private AemContext context;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {		
		context.addModelsForClasses(AccountManagementAddressesModelImplTest.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("country", "US");
		pageProperties.put("region", "US");
		pageProperties.put("hybrisSiteId", "bdbUS");

		Map<String, String> addressesProperties = new HashMap<>();
		addressesProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/accountmanagement");

		addressesModelTestImpl = new AccountManagementAddressesModelImpl();

		PrivateAccessor.setField(addressesModelTestImpl, "addressesHeader", "addressesHeader");
		PrivateAccessor.setField(addressesModelTestImpl, "confirmButtonLabel", "confirmButtonLabel");
		PrivateAccessor.setField(addressesModelTestImpl, "reactivationSuccessText", "reactivationSuccessText");
		PrivateAccessor.setField(addressesModelTestImpl, "addrFormOkayLabel", "addrFormOkayLabel");
		PrivateAccessor.setField(addressesModelTestImpl, "shipToNumberLabelText", "shipToNumberLabelText");
		PrivateAccessor.setField(addressesModelTestImpl, "enterNicknameLabel", "enterNicknameLabel");
		
		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "updateFavoriteAddressEndpoint","/occ/{{site}}/updateFavoriteAddress");
		PrivateAccessor.setField(bdbApiEndpointService, "defaultAddressEndpoint", "/occ/{{site}}/defaultAddress");
		PrivateAccessor.setField(bdbApiEndpointService, "updateNicknameEndpoint", "/occ/{{site}}/updateNickname");
		PrivateAccessor.setField(bdbApiEndpointService, "createAddressEndpoint", "/occ/{{site}}/createAddress");
		PrivateAccessor.setField(bdbApiEndpointService, "reactivateUserEndpoint", "/occ/{{site}}/reactivate");
		PrivateAccessor.setField(bdbApiEndpointService, "uploadRuoCertificateEndpoint", "/occ/{{site}}/uploadRuoCertificate");
		PrivateAccessor.setField(addressesModelTestImpl, "currentPage", currentPage);
		PrivateAccessor.setField(addressesModelTestImpl, "request", request);
		PrivateAccessor.setField(addressesModelTestImpl, "bdbApiEndpointService", bdbApiEndpointService);
		addressesModelTestImpl.init();
		PrivateAccessor.setField(addressesModelTestImpl, "hybrisSiteId", "bdbUS");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		addressesModelTestImpl.init();
	}
	
	/**
	 * Gets the user acc addr labels.
	 *
	 * @return the user acc addr labels
	 */
	@Test
	void getUserAccAddrLabels() {
		assertNotNull(addressesModelTestImpl.getUserAccAddrLabels());
	}
	
	/**
	 * Gets the user acc addr config.
	 *
	 * @return the user acc addr config
	 */
	@Test
	void getUserAccAddrConfig() {
		assertNotNull(addressesModelTestImpl.getUserAccAddrConfig());
	}
	
	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	@Test
	void getHybrisSiteId() {
		assertNotNull(addressesModelTestImpl.getHybrisSiteId());
	}
}
