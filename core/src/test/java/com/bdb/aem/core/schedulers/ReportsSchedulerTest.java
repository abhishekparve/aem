package com.bdb.aem.core.schedulers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.sling.commons.scheduler.Scheduler;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.mockito.Mock;
import org.slf4j.Logger;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.jcr.RepositoryException;

import static org.mockito.Mockito.lenient;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import com.bdb.aem.core.config.ReportsSchedulerConfiguration;
import com.bdb.aem.core.services.ReportGenerationService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
/**
 * The Class ReportsSchedulerTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ReportsSchedulerTest {
	/** The ReportsScheduler job. */
	@InjectMocks
	private ReportsScheduler reportsScheduler;
	@Mock
	private Scheduler scheduler;
	/** The config. */
	@Mock
	ReportsSchedulerConfiguration config;

	/** The sling servlet request. */
	@Mock
	SlingHttpServletRequest request;
	@Mock
	private ReportGenerationService reportGenerationService;
	@Mock
	ScheduleOptions sopt;
	private AemContext context;
	int schedulerID;
	String schedulerName = "schedulerName";
	/**
	 * Test activate.
	 */
	@Test
  void testActivate() throws Exception {
		lenient().when(config.schdulerName()).thenReturn(schedulerName);
		schedulerID= schedulerName.hashCode();
		reportsScheduler.activate(config);
	}
	/**
	 * Test modify.
	 */
	@Test
	public void testModified() throws Exception {
		lenient().when(config.schdulerName()).thenReturn(schedulerName);
		schedulerID= schedulerName.hashCode();
		reportsScheduler.modified(config);
	}
	/**
	 * Test run method.
	 */
	@Test
	public void testRun() throws Exception ,IOException ,RepositoryException , JSONException , LoginException  {
		Set<String> runmodes = 	new HashSet<>(Arrays.asList("author", "b"));
		reportsScheduler.run();
	}
	

}
