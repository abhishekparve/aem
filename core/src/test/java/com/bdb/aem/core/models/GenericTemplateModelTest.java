package com.bdb.aem.core.models;

import static com.bdb.aem.core.util.CommonConstants.IS_PRINT_PDF;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.LoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class GenericTemplateModelTest {

	@InjectMocks
	GenericTemplateModel genericTemplateModel;

	@Mock
	SlingHttpServletRequest request;
	
	@Mock
	RequestParameter param;

	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(genericTemplateModel, "request", request);
	}

	@Test
	void testInit() throws LoginException {
		lenient().when(request.getRequestParameter(IS_PRINT_PDF)).thenReturn(param);
		lenient().when(param.toString()).thenReturn("true");
		genericTemplateModel.init();
	}

}
