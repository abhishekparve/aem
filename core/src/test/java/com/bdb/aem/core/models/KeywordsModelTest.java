package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })

class KeywordsModelTest {

	@InjectMocks
	KeywordsModel keywordsModel;
	
	private String keyword;
	private String urlRedirect;
	
	@Mock
	Resource resource;
	
	@BeforeEach
	void setup() throws NoSuchFieldException {
		PrivateAccessor.setField(keywordsModel, "keyword", "keyword");
		PrivateAccessor.setField(keywordsModel, "urlRedirect", "urlRedirect");
	}
	@Test
	void testInit() {
		keywordsModel.init();
	}
	
	@Test
	void testGetters() {
		assertNotNull(keywordsModel.getKeyword());
		assertNotNull(keywordsModel.getUrlRedirect());
		keywordsModel.getKeywordOptions();
	}

}
