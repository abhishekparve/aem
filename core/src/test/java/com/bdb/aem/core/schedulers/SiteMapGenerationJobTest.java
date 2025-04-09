package com.bdb.aem.core.schedulers;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import com.bdb.aem.core.services.BDBApiEndpointService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import com.bdb.aem.core.schedulers.SiteMapGenerationJob.Config;
import com.bdb.aem.core.services.SiteMapService;
import com.bdb.aem.core.util.CommonHelper;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class SiteMapGenerationJobTest.
 */

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SiteMapGenerationJobTest {

	/** The site map generation job. */
	@InjectMocks
	private SiteMapGenerationJob siteMapGenerationJob;

	/** The config. */
	@Mock
	Config config;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The site map service. */
	@Mock
	SiteMapService siteMapService;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The logger. */
	@Mock
	Logger logger;

	/** The sling settings service. */
	@Mock
	SlingSettingsService slingSettingsService;

	/** The scheduler. */
	@Mock
	Scheduler scheduler;
	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	/**
	 * Test activate.
	 */
	@Test
	void testActivate() {
		ScheduleOptions sopts = Mockito.mock(ScheduleOptions.class);
		String[] paths = {};
		when(config.schedulerName()).thenReturn("scheduler");
		when(config.isEnabled()).thenReturn(true);
		when(config.contentPaths()).thenReturn(paths);
		when(config.countryPaths()).thenReturn(paths);
		when(config.regionPaths()).thenReturn(paths);
		when(config.regionPaths()).thenReturn(paths);
		when(config.scheduler_expression()).thenReturn("0 0 */12 ? * *");
		when(scheduler.EXPR("0 0 */12 ? * *")).thenReturn(sopts);

		siteMapGenerationJob.activate(config);
	}

	/**
	 * Test author run.
	 *
	 * @throws LoginException the login exception
	 * @throws Exception      the exception
	 */
	@Test
	void testAuthorRun() throws LoginException, Exception {
		String[] paths = { "ab", "bc" };
		PrivateAccessor.setField(siteMapGenerationJob, "paths", paths);
		when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		siteMapGenerationJob.run();
	}

	/**
	 * Test null run.
	 *
	 * @throws LoginException the login exception
	 * @throws Exception      the exception
	 */
	@Test
	void testNullRun() throws LoginException, Exception {

		String[] paths = { "ab", "bc" };
		PrivateAccessor.setField(siteMapGenerationJob, "paths", paths);
		PrivateAccessor.setField(siteMapGenerationJob, "isEnabled", true);
		when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		when(CommonHelper.getServiceResolverReplication(resolverFactory)).thenReturn(resourceResolver);
		siteMapGenerationJob.run();
	}

	/**
	 * Test run login exception.
	 *
	 * @throws LoginException the login exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testRunLoginException() throws LoginException, NoSuchFieldException {
		String[] paths = { "ab", "bc" };
		PrivateAccessor.setField(siteMapGenerationJob, "paths", paths);
		PrivateAccessor.setField(siteMapGenerationJob, "isEnabled", true);
		when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		lenient().when(CommonHelper.getServiceResolverReplication(resolverFactory)).thenThrow(LoginException.class);
		siteMapGenerationJob.run();
	}

	/**
	 * Test deactivate.
	 */
	@Test
	void testDeactivate() {
		siteMapGenerationJob.deactivate(config);
	}
}
