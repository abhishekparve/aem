package com.bdb.aem.core.services.tools.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.BaseSlingModel;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class DefaultComponentServiceImplTest {
	
	@InjectMocks
	DefaultComponentServiceImpl defaultComponentServiceImpl;
	
	@Mock
	BaseSlingModel slingModel;

	@Test
	void test() {
		defaultComponentServiceImpl.updateModel(slingModel);
	}
}