package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * JUnit for AccMgmtOrdersModelImpl.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class AccMgmtOrdersModelImplTest {
	
	/**  Mock AccMgmtOrdersModelImpl. */
	@InjectMocks
	AccMgmtOrdersModelImpl accMgmtOrdersModelImpl;

	/**
	 * Sets things up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		accMgmtOrdersModelImpl = new AccMgmtOrdersModelImpl();
		PrivateAccessor.setField(accMgmtOrdersModelImpl,"ordersHeader","A");
		PrivateAccessor.setField(accMgmtOrdersModelImpl,"declineOrder","declineOrder");
		PrivateAccessor.setField(accMgmtOrdersModelImpl,"approveOrder","approveOrder");
		PrivateAccessor.setField(accMgmtOrdersModelImpl,"amountSummaryText","amountSummaryText");
		PrivateAccessor.setField(accMgmtOrdersModelImpl,"additionalCommentsText","additionalCommentsText");
		PrivateAccessor.setField(accMgmtOrdersModelImpl,"cancelCtaLabel","cancelCtaLabel");
		PrivateAccessor.setField(accMgmtOrdersModelImpl,"approveCtaLabel","approveCtaLabel");
		PrivateAccessor.setField(accMgmtOrdersModelImpl,"declineCtaLabel","declineCtaLabel");
		PrivateAccessor.setField(accMgmtOrdersModelImpl,"maximumCharacterText","maximumCharacterText");
		PrivateAccessor.setField(accMgmtOrdersModelImpl,"productsLabel","productsLabel");
		accMgmtOrdersModelImpl.init();
	}

	/**
	 * Test method.
	 */
	@Test
	void testOrdersLabels() {

		assertEquals(createOrdersJson(),accMgmtOrdersModelImpl.getOrdersLabels());
	}
	
	/**
	 * This method generates the expected output of getOrdersLabels() method
	 * @return
	 */
	private String createOrdersJson() {
		return "{\"ordersHeader\":\"A\",\"declineOrder\":\"declineOrder\",\"approveOrder\":\"approveOrder\",\"amountSummaryText\":\"amountSummaryText\",\"additionalCommentsText\":\"additionalCommentsText\",\"cancelCtaLabel\":\"cancelCtaLabel\",\"approveCtaLabel\":\"approveCtaLabel\",\"declineCtaLabel\":\"declineCtaLabel\",\"maximumCharacterText\":\"maximumCharacterText\",\"productsLabel\":\"productsLabel\"}";
	}
}
