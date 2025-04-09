package com.bdb.aem.core.services;

/**
 * The Interface CookieNameService.
 */
public interface CookieNameService {

	/**
	 * Gets the GUID cookie exp date.
	 *
	 * @return the GUID cookie exp date
	 */
	int getGUIDCookieExpDate();
	
	/**
	 * Gets the anonymous user id.
	 *
	 * @return the anonymous user id
	 */
	String getAnonymousUserId();
	
	/**
	 * Current user id.
	 *
	 * @return the string
	 */
	String getCurrentUserId();
}
