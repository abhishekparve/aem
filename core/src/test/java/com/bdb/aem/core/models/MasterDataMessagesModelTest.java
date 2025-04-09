package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class MasterDataMessagesModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class MasterDataMessagesModelTest {

	/** The master data messages test model. */
	@InjectMocks
	MasterDataMessagesModel masterDataMessagesTestModel;
	
	/** The certification multi field. */
	private List<Resource> certificationMultiField;
	
	/** The order source multi field. */
	private List<Resource> orderSourceMultiField;
	
	/** The order status multi field. */
	private List<Resource> orderStatusMultiField;
	
	/** The user dashboard messages multifield. */
	private List<Resource> userDashboardMessagesMultifield;
	
	/** The certifications type multi field. */
	private List<Resource> certificationsTypeMultiField;
	
	/** The order approval promotions multifield. */
	private List<Resource> orderApprovalPromotionsMultifield;
	
	/** The context. */
	private AemContext context;
	
	/** The certification resource. */
	private Resource certificationResource;
	
	/** The order source resource. */
	private Resource orderSourceResource;
	
	/** The order status resource. */
	private Resource orderStatusResource;
	
	/** The user dashboard messages resource. */
	private Resource userDashboardMessagesResource;
	
	/** The certifications type resource. */
	private Resource certificationsTypeResource;
	
	/** The promotions resource. */
	private Resource promotionsResource;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Map<String, String> certificationProperty = new HashMap<>();		
		certificationProperty.put("errorCode", "CODE_1");
		certificationProperty.put("errorMessage", "DESCRIPTION1");
		certificationResource = context.create().resource("/root/master/certification", certificationProperty);
		
		Map<String, String> orderSourceProperty = new HashMap<>();		
		orderSourceProperty.put("errorCode", "CODE_2");
		orderSourceProperty.put("errorMessage", "DESCRIPTION");		
		orderSourceResource = context.create().resource("/root/master/orderSource", orderSourceProperty);
		
		Map<String, String> orderStatusProperty = new HashMap<>();
		orderStatusProperty.put("errorCode", "CODE_3");
		orderStatusProperty.put("errorMessage", "DESCRIPTION3");
		orderStatusResource = context.create().resource("/root/master/orderStatus", orderStatusProperty);
		
		Map<String, String> userDashboardMessagesProperty = new HashMap<>();
		userDashboardMessagesProperty.put("errorCode", "CODE_4");
		userDashboardMessagesProperty.put("errorMessage", "DESCRIPTION4");
		userDashboardMessagesResource = context.create().resource("/root/master/userDashboardMessages", userDashboardMessagesProperty);
		
		Map<String, String> certificationsTypeProperty = new HashMap<>();
		certificationsTypeProperty.put("errorCode", "CODE_5");
		certificationsTypeProperty.put("errorMessage", "DESCRIPTION5");
		certificationsTypeResource = context.create().resource("/root/master/certificationsTypeMessages", certificationsTypeProperty);
		
		Map<String, String> promotionsProperty = new HashMap<>();
		promotionsProperty.put("errorCode", "CODE_6");
		promotionsProperty.put("errorMessage", "DESCRIPTION6");
		promotionsResource = context.create().resource("/root/master/promotionsMessages", promotionsProperty);
		
		certificationMultiField = Arrays.asList(certificationResource);
		orderSourceMultiField = Arrays.asList(orderSourceResource);
		orderStatusMultiField = Arrays.asList(orderStatusResource);
		userDashboardMessagesMultifield = Arrays.asList(userDashboardMessagesResource);		
		certificationsTypeMultiField = Arrays.asList(certificationsTypeResource);
		orderApprovalPromotionsMultifield = Arrays.asList(promotionsResource);
		
		PrivateAccessor.setField(masterDataMessagesTestModel, "certificationMultiField", certificationMultiField);
		PrivateAccessor.setField(masterDataMessagesTestModel, "orderSourceMultiField", orderSourceMultiField);
		PrivateAccessor.setField(masterDataMessagesTestModel, "orderStatusMultiField", orderStatusMultiField);
		PrivateAccessor.setField(masterDataMessagesTestModel, "userDashboardMessagesMultifield", userDashboardMessagesMultifield);
		PrivateAccessor.setField(masterDataMessagesTestModel, "certificationsTypeMultiField", certificationsTypeMultiField);
		PrivateAccessor.setField(masterDataMessagesTestModel, "orderApprovalPromotionsMultifield", orderApprovalPromotionsMultifield);
		
		PrivateAccessor.setField(masterDataMessagesTestModel, "abandonedCartLabel", "abandonedCartLabel");
		PrivateAccessor.setField(masterDataMessagesTestModel, "blogLabel", "blogLabel");
		PrivateAccessor.setField(masterDataMessagesTestModel, "newsLetterLabel", "newsLetterLabel");
		PrivateAccessor.setField(masterDataMessagesTestModel, "tutorialLabel", "tutorialLabel");
		PrivateAccessor.setField(masterDataMessagesTestModel, "webinarLabel", "webinarLabel");
		masterDataMessagesTestModel.init();
	}

	/**
	 * Test the Getters.
	 */
	@Test
	void test() {
		assertEquals(masterDataMessagesTestModel.getAbandonedCartLabel(), "abandonedCartLabel");
		assertNotNull(masterDataMessagesTestModel.getBlogLabel());
		assertNotNull(masterDataMessagesTestModel.getNewsLetterLabel());
		assertNotNull(masterDataMessagesTestModel.getTutorialLabel());
		assertNotNull(masterDataMessagesTestModel.getWebinarLabel());
		assertNotNull(masterDataMessagesTestModel.getCertificationMessages());
		assertNotNull(masterDataMessagesTestModel.getOrderSourceMessages());
		assertNotNull(masterDataMessagesTestModel.getOrderStatusMessages());
		assertNotNull(masterDataMessagesTestModel.getUserDashboardMessages());
		assertNotNull(masterDataMessagesTestModel.getCertificationsType());
		assertNotNull(masterDataMessagesTestModel.getPromotionsCodeDescription());
		masterDataMessagesTestModel.getDeliverOptionMessages();
		masterDataMessagesTestModel.getOrderInquiryTypesOptions();
		masterDataMessagesTestModel.getQuoteStatusMessages();
	}
}
