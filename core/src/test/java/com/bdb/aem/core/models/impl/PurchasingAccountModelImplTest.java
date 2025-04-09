package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingConstants;
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
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class PurchasingAccountModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PurchasingAccountModelImplTest {

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

	/**
	 * The current page.
	 */
	@Mock
	Page currentPage;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The bdb api endpoint service. */
	@InjectMocks
	BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();

	/** The purchasing account model. */
	@InjectMocks
	private PurchasingAccountModelImpl purchasingAccountModel;

	/** The context. */
	private AemContext context;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(PurchasingAccountModelImpl.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);

		Map<String, String> purchaseProperties = new HashMap<>();
		purchaseProperties.put("creditCard", "Credit Card");
		purchaseProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE,
				"bdb-aem/components/content/purchasingaccount/v1/purchasingaccount");

		purchasingAccountModel = new PurchasingAccountModelImpl();
		
		PrivateAccessor.setField(purchasingAccountModel, "downloadCreditNoteUrl", "downloadCreditNoteUrl");
		PrivateAccessor.setField(purchasingAccountModel, "purchaseAccountConfirmationImage", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "purchaseAccountConfirmationTitle", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "purchaseAccountConfirmationPendingTitle", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "purchaseAccountConfirmationSuccessTitle", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "purchaseAccountCompletedContent", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "purchaseAccountPendingContent" , "Value");
		PrivateAccessor.setField(purchasingAccountModel, "purchaseAccountRewardsCTALabel", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "purchaseAccountContinueCTALabel", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "purchaseAccountContinueCTALink", "Value");
		
		PrivateAccessor.setField(purchasingAccountModel, "joinRewardsTitle", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "joinRewardsSubtitle", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "notHealthCareProfessionalLabel", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "notGovtEmployeeLabel", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "notProhibitedGiftsAccept", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "rewardsTnCLabel", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "joinBdRewardsCTALabel", "Value");
		PrivateAccessor.setField(purchasingAccountModel, "joinBdRewardsCTALink", "Value");

		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "purchasingAccountRegistrationEndpoint", "/occ/{{site}}/purchasingaccount");
		PrivateAccessor.setField(bdbApiEndpointService, "uploadTaxCertificateEndpoint", "/occ/{{site}}/tax");
		PrivateAccessor.setField(purchasingAccountModel, "bdbApiEndpointService", bdbApiEndpointService);
		PrivateAccessor.setField(bdbApiEndpointService, "getGrantsForCustomerEndpoint", "/occ/{{site}}/getGrantsForCustomer");
		PrivateAccessor.setField(bdbApiEndpointService, "orderHistoryForGrantsEndpoint", "/occ/{{site}}/orderHistoryForGrants");
		PrivateAccessor.setField(bdbApiEndpointService, "getDistributorsOptionsEndpoint", "/occ/{{site}}/distributor");
		PrivateAccessor.setField(bdbApiEndpointService, "postSmartCartRegisterEndpoint", "/occ/{{site}}/postSmartCartRegisterEndpoint");
		purchasingAccountModel.init();
	}

	/**
	 * Test init.
	 */
	@Test
	void init() {
		purchasingAccountModel.init();
	}

	/**
	 *  Test get purchase account labels.
	 *
	 * @return the purchase account labels
	 */
	@Test
	void getPurchaseAccountLabels() {
		assertNotNull(purchasingAccountModel.getPurchaseAccountLabels());
	}

	/**
	 * Gets the purchase account config.
	 *
	 * @return the purchase account config
	 */
	@Test
	void getPurchaseAccountConfig() {
		assertNotNull(purchasingAccountModel.getPurchaseAccountConfig());
	}

	/**
	 * Test get purchase account confirmation labels.
	 *
	 * @return the purchase account confirmation labels
	 */
	@Test
	void getPurchaseAccountConfirmationLabels() {
		assertNotNull(purchasingAccountModel.getPurchaseAccountConfirmationLabels());
	}

	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	@Test
	void getHybrisSiteId() {
		assertNotNull(purchasingAccountModel.getHybrisSiteId());
	}
}