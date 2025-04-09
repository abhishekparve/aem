package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.GlobalErrorMessagesModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class AccountManagementCertificationsModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccountManagementCertificationsModelImplTest {

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

	/** The error model. */
	@Mock
	GlobalErrorMessagesModel errorModel;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

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

	/** The account certifications model. */
	@InjectMocks
	private AccountManagementCertificationsModelImpl accountCertificationsModel;

	/** The file type multifield. */
	private List<Resource> fileTypeMultifield;
	
	/** The file type resource. */
	private Resource fileTypeResource;
	
	/** The context. */
	private AemContext context;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Map<String, String> fileTypeProperty = new HashMap<>();		
		fileTypeProperty.put("id", "001");
		fileTypeProperty.put("label", "Test");
		fileTypeResource = context.create().resource("/root/account/certificate", fileTypeProperty);
		fileTypeMultifield = Arrays.asList(fileTypeResource);
		
		context.addModelsForClasses(AccountManagementCertificationsModelImpl.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("country", "US");
		pageProperties.put("region", "US");
		pageProperties.put("hybrisSiteId", "bdbUS");

		Map<String, String> certificationsProperties = new HashMap<>();
		certificationsProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE,
				"bdb-aem/components/content/accountmanagement/v1/accountmanagement");

		accountCertificationsModel = new AccountManagementCertificationsModelImpl();

		PrivateAccessor.setField(accountCertificationsModel, "certificationsHeader", "certificationsHeader");

		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "getListOfUserCertificationsEndpoint","/occ/{{site}}/listofusercertifications");
		PrivateAccessor.setField(bdbApiEndpointService, "deleteCertificateEndpoint", "/occ/{{site}}/deletecertificate");
		PrivateAccessor.setField(bdbApiEndpointService, "uploadRuoCertificateEndpoint", "/occ/{{site}}/tax");
		PrivateAccessor.setField(bdbApiEndpointService, "getAddressesEndpoint", "/occ/{{site}}/addresses");
		PrivateAccessor.setField(accountCertificationsModel, "currentPage", currentPage);
		PrivateAccessor.setField(accountCertificationsModel, "request", request);
		PrivateAccessor.setField(accountCertificationsModel, "bdbApiEndpointService", bdbApiEndpointService);
		PrivateAccessor.setField(accountCertificationsModel, "fileTypeMultiField", fileTypeMultifield);
		accountCertificationsModel.init();
		PrivateAccessor.setField(accountCertificationsModel, "hybrisSiteId", "bdbUS");

	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		accountCertificationsModel.init();
	}

	/**
	 * Test get certifications labels.
	 */
	@Test
	void testGetCertificationsLabels() {
		assertNotNull(accountCertificationsModel.getCertificationsLabels());
	}

	/**
	 * Test get certificates config.
	 */
	@Test
	void testGetCertificatesConfig() {
		assertNotNull(accountCertificationsModel.getCertificatesConfig());
	}

	/**
	 * Test get upload certification labels.
	 */
	@Test
	void testGetUploadCertificationLabels() {
		assertNotNull(accountCertificationsModel.getUploadCertificationLabels());
	}

	/**
	 * Test get upload certification config.
	 */
	@Test
	void testGetUploadCertificationConfig() {
		assertNotNull(accountCertificationsModel.getUploadCertificationConfig());
	}

	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	@Test
	void getHybrisSiteId() {
		assertNotNull(accountCertificationsModel.getHybrisSiteId());
	}
}
