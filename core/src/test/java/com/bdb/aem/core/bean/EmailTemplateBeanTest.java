package com.bdb.aem.core.bean;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class EmailTemplateBeanTest.
 */
class EmailTemplateBeanTest {
	
	/** The email template bean. */
	EmailTemplateBean emailTemplateBean;
	
	/** The email no participants bean. */
	EmailTemplateBean emailNoParticipantsBean;
	
	/** The input param test. */
	Map<String, Object> inputParamTest = new HashMap<String, Object>();
	
	/** The participants test. */
	List<String> participantsTest = new ArrayList<String>();
	
	/** The participants null. */
	List<String> participantsNull;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		inputParamTest.put("key", "value");
		participantsTest.add("Item1");
		emailTemplateBean = new EmailTemplateBean();
		emailTemplateBean.setEmailSubject("Subject");
		emailTemplateBean.setInputParams(inputParamTest);
		emailTemplateBean.setParticipants(participantsTest);
		emailTemplateBean.setPayload("Payload");
		emailTemplateBean.setRecipientUser("RecipientUser");
		emailTemplateBean.setTemplateSelector("TemplateSelector");
		emailNoParticipantsBean = new EmailTemplateBean();
		emailNoParticipantsBean.setParticipants(participantsNull);
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		assertEquals("Subject", emailTemplateBean.getEmailSubject());
		assertEquals(inputParamTest, emailTemplateBean.getInputParams());
		assertEquals(participantsTest, emailTemplateBean.getParticipants());
		assertEquals("Payload", emailTemplateBean.getPayload());
		assertEquals("RecipientUser", emailTemplateBean.getRecipientUser());
		assertEquals("TemplateSelector", emailTemplateBean.getTemplateSelector());
		assertEquals(Collections.emptyList(), emailNoParticipantsBean.getParticipants());
	}
}
