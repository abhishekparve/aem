
package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class GlobalErrorMessagesModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class GlobalErrorMessagesModelTest {

	/**
	 * The globalErrorMessagesModel.
	 */
	@InjectMocks
	GlobalErrorMessagesModel globalErrorMessagesModel;

	/** The resource. */
	@Mock
	Resource resource;

	/** The error message. */
	@Mock
	ApiErrorMessagesModel errorMessage;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		PrivateAccessor.setField(globalErrorMessagesModel, "rulesHeading", "rulesHeading");
		PrivateAccessor.setField(globalErrorMessagesModel, "lengthRuleLabel", "lengthRuleLabel");
		PrivateAccessor.setField(globalErrorMessagesModel, "caseRuleLabel", "caseRuleLabel");
		PrivateAccessor.setField(globalErrorMessagesModel, "numericRuleLabel", "numericRuleLabel");
		PrivateAccessor.setField(globalErrorMessagesModel, "symbolRuleLabel", "symbolRuleLabel");
		PrivateAccessor.setField(globalErrorMessagesModel, "noSpacesLabel", "noSpacesLabel");
		PrivateAccessor.setField(globalErrorMessagesModel, "passwordError", "passwordError");
		PrivateAccessor.setField(globalErrorMessagesModel, "passwordFLNameError", "passwordFLNameError");
		PrivateAccessor.setField(globalErrorMessagesModel, "passwordPatternError", "passwordPatternError");
		PrivateAccessor.setField(globalErrorMessagesModel, "confPasswordError", "confPasswordError");
		PrivateAccessor.setField(globalErrorMessagesModel, "confPasswordNotMatchError", "confPasswordNotMatchError");
		PrivateAccessor.setField(globalErrorMessagesModel, "mandatoryFieldError", "mandatoryFieldError");
		PrivateAccessor.setField(globalErrorMessagesModel, "phoneNumberLabelError", "phoneNumberLabelError");
		PrivateAccessor.setField(globalErrorMessagesModel, "currentPwdRequiredError", "currentPwdRequiredError");
		PrivateAccessor.setField(globalErrorMessagesModel, "firstNameError", "firstNameError");
		PrivateAccessor.setField(globalErrorMessagesModel, "lastNameError", "lastNameError");
		PrivateAccessor.setField(globalErrorMessagesModel, "phoneNumberValidationText", "phoneNumberValidationText");
		PrivateAccessor.setField(globalErrorMessagesModel, "phoneNumPatternError", "phoneNumPatternError");

	}

	/**
	 * Test init.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testInit() throws NoSuchFieldException {
		List<Resource> apiErrorMessagesMultiField = new ArrayList<>();
		apiErrorMessagesMultiField.add(resource);

		PrivateAccessor.setField(globalErrorMessagesModel, "apiErrorMessagesMultiField", apiErrorMessagesMultiField);

		when(resource.adaptTo(ApiErrorMessagesModel.class)).thenReturn(errorMessage);

		globalErrorMessagesModel.init();
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(globalErrorMessagesModel.getRulesHeading());
		assertNotNull(globalErrorMessagesModel.getLengthRuleLabel());
		assertNotNull(globalErrorMessagesModel.getCaseRuleLabel());
		assertNotNull(globalErrorMessagesModel.getNumericRuleLabel());
		assertNotNull(globalErrorMessagesModel.getSymbolRuleLabel());
		assertNotNull(globalErrorMessagesModel.getPasswordError());
		assertNotNull(globalErrorMessagesModel.getPasswordFLNameError());
		assertNotNull(globalErrorMessagesModel.getPasswordPatternError());
		assertNotNull(globalErrorMessagesModel.getConfPasswordError());
		assertNotNull(globalErrorMessagesModel.getConfPasswordNotMatchError());
		assertNotNull(globalErrorMessagesModel.getMandatoryFieldError());
		assertNotNull(globalErrorMessagesModel.getPhoneNumberLabelError());
		assertNotNull(globalErrorMessagesModel.getFirstNameError());
		assertNotNull(globalErrorMessagesModel.getLastNameError());
		assertNotNull(globalErrorMessagesModel.getPhoneNumberValidationText());
		assertNotNull(globalErrorMessagesModel.getPhoneNumPatternError());
		assertNotNull(globalErrorMessagesModel.getCurrentPwdRequiredError());
		assertNotNull(globalErrorMessagesModel.getErrorMessages());
	}

	/**
	 * Test fields.
	 */
	@Test
	void testFields() {
		assertEquals("rulesHeading", globalErrorMessagesModel.getRulesHeading());
		assertEquals("lengthRuleLabel", globalErrorMessagesModel.getLengthRuleLabel());
		assertEquals("caseRuleLabel", globalErrorMessagesModel.getCaseRuleLabel());
		assertEquals("numericRuleLabel", globalErrorMessagesModel.getNumericRuleLabel());
		assertEquals("symbolRuleLabel", globalErrorMessagesModel.getSymbolRuleLabel());
		assertEquals("noSpacesLabel", globalErrorMessagesModel.getNoSpacesLabel());
		assertEquals("passwordError", globalErrorMessagesModel.getPasswordError());
		assertEquals("passwordFLNameError", globalErrorMessagesModel.getPasswordFLNameError());
		assertEquals("passwordPatternError", globalErrorMessagesModel.getPasswordPatternError());
		assertEquals("confPasswordError", globalErrorMessagesModel.getConfPasswordError());
		assertEquals("confPasswordNotMatchError", globalErrorMessagesModel.getConfPasswordNotMatchError());
		assertEquals("mandatoryFieldError", globalErrorMessagesModel.getMandatoryFieldError());
		assertEquals("phoneNumberLabelError", globalErrorMessagesModel.getPhoneNumberLabelError());
		assertEquals("currentPwdRequiredError", globalErrorMessagesModel.getCurrentPwdRequiredError());
		assertEquals("firstNameError", globalErrorMessagesModel.getFirstNameError());
		assertEquals("lastNameError", globalErrorMessagesModel.getLastNameError());
		assertEquals("phoneNumberValidationText", globalErrorMessagesModel.getPhoneNumberValidationText());
		assertEquals("phoneNumPatternError", globalErrorMessagesModel.getPhoneNumPatternError());

	}
}
