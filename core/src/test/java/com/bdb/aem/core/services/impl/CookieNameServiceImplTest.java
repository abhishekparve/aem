package com.bdb.aem.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class CookieNameServiceImplTest.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class CookieNameServiceImplTest {
	
	/** The bdbendpoint impl obj. */
	@InjectMocks
	private CookieNameServiceImpl cookieNameServiceImplObj = new CookieNameServiceImpl();
	
	@Mock
	CookieNameServiceImpl.Configuration config;
	
	/** The guid cookie exp date. */
	private int GUID_COOKIE_EXP_DATE = 30;
	
	/** The anonymous user id. */
	private String ANONYMOUS_USER_ID = "anonymous";
	
	/** The current user id. */
	private String CURRENT_USER_ID = "current";
	
	/**
	 * Sets the up.
	 * @throws NoSuchFieldException 
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		PrivateAccessor.setField(cookieNameServiceImplObj, "anonymousUserId",ANONYMOUS_USER_ID);
		PrivateAccessor.setField(cookieNameServiceImplObj, "currentUserId",CURRENT_USER_ID);
		PrivateAccessor.setField(cookieNameServiceImplObj, "guIDCookieExpDate",GUID_COOKIE_EXP_DATE);
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertEquals(ANONYMOUS_USER_ID, cookieNameServiceImplObj.getAnonymousUserId());
		assertEquals(CURRENT_USER_ID, cookieNameServiceImplObj.getCurrentUserId());
		assertEquals(GUID_COOKIE_EXP_DATE, cookieNameServiceImplObj.getGUIDCookieExpDate());
	}
	
	@Test
	void testActivate() {
		cookieNameServiceImplObj.activate(config);
	}
	
	
}