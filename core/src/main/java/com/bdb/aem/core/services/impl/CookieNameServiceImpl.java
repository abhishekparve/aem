package com.bdb.aem.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.bdb.aem.core.services.CookieNameService;

/**
 * The Class CookieNameServiceImpl.
 */
@Component(immediate = true, service = CookieNameService.class)
@Designate(ocd = CookieNameServiceImpl.Configuration.class)
public class CookieNameServiceImpl implements CookieNameService {

	/** The gu ID cookie exp date. */
	private int guIDCookieExpDate;

	/** The anonymous user id. */
	private String anonymousUserId;

	/** The current user id. */
	private String currentUserId;

	/**
	 * Activate.
	 *
	 * @param config the config
	 */
	@Activate
	@Modified
	protected final void activate(Configuration config) {
		this.guIDCookieExpDate = config.guIDCookieExpDate();
		this.anonymousUserId = config.anonymousUserId();
		this.currentUserId = config.currentUserId();
	}

	/**
	 * Deactivate.
	 */
	@Deactivate
	protected void deactivate() {
		// DoNothing
	}

	/**
	 * Gets the GUID cookie exp date.
	 *
	 * @return the GUID cookie exp date
	 */
	@Override
	public int getGUIDCookieExpDate() {
		return guIDCookieExpDate;
	}

	/**
	 * Gets the anonymous user id.
	 *
	 * @return the anonymous user id
	 */
	@Override
	public String getAnonymousUserId() {
		return anonymousUserId;
	}

	/**
	 * Current user id.
	 *
	 * @return the string
	 */
	@Override
	public String getCurrentUserId() {
		return currentUserId;
	}

	/**
	 * The Interface Configuration.
	 */
	@ObjectClassDefinition(name = "Cookies Value Configuration")
	public @interface Configuration {

		/**
		 * Gu ID cookie exp date.
		 *
		 * @return the int
		 */
		@AttributeDefinition(name = "GUIDCookieExpDate", description = "Cookie Expiration Date", type = AttributeType.INTEGER)
		int guIDCookieExpDate() default 30;

		/**
		 * Anonymous user id.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "anonymousUserId", description = "Anonymous User Id", type = AttributeType.STRING)
		public String anonymousUserId() default "anonymous";

		/**
		 * Current user id.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "currentUserId", description = "Current User Id", type = AttributeType.STRING)
		public String currentUserId() default "current";

	}

}
