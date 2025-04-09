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
 * The Class SignUpIndustryModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SignUpIndustryModelTest {
		
	/** The sign up industry model. */
	@InjectMocks
	SignUpIndustryModel signUpIndustryModel;
		
	/** The role multifield. */
	private List<Resource> roleMultifield;
	
	/** The interest multifield. */
	private List<Resource> interestMultifield;
	
	/** The context. */
	private AemContext context;
	
	/** The role resource. */
	private Resource roleResource;
	
	/** The interest resource. */
	private Resource interestResource;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		Map<String, String> roleProperty = new HashMap<>();		
		roleProperty.put("code", "001001");
		roleProperty.put("label", "Pharma");
		roleResource = context.create().resource("/root/aof/role", roleProperty);
		
		Map<String, String> interestProperty = new HashMap<>();		
		interestProperty.put("code", "010001");
		interestProperty.put("label", "Cryogenics");		
		interestResource = context.create().resource("/root/aof/interest", interestProperty);
		
		roleMultifield = Arrays.asList(roleResource);
		interestMultifield = Arrays.asList(interestResource);		
		
		PrivateAccessor.setField(signUpIndustryModel, "label", "industryLabels");
		PrivateAccessor.setField(signUpIndustryModel, "code", "industryCode");
		PrivateAccessor.setField(signUpIndustryModel, "interestMultifield", interestMultifield);
		PrivateAccessor.setField(signUpIndustryModel, "roleMultifield", roleMultifield);
		signUpIndustryModel.init();
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(signUpIndustryModel.getIndustryLabel());
		assertNotNull(signUpIndustryModel.getIndustryCode());
		assertNotNull(signUpIndustryModel.getRoleList());
		assertNotNull(signUpIndustryModel.getInterestList());		
	}
}
