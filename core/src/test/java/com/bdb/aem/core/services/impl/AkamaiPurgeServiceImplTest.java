package com.bdb.aem.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.akamai.ccu.v3.DispatcherDetailsBean;
import com.bdb.aem.core.services.PageCollectionService;
import com.bdb.aem.core.services.impl.AkamaiPurgeServiceImpl.Configuration;
import com.day.cq.replication.ReplicationAction;

import junitx.util.PrivateAccessor;

/**
 * The Class AkamaiPurgeServiceImplTest.
 */
@ExtendWith({ MockitoExtension.class })
class AkamaiPurgeServiceImplTest {

	/** The akamai purge service impl. */
	@InjectMocks
	AkamaiPurgeServiceImpl akamaiPurgeServiceImpl;

	/** The page collection service. */
	@Mock
	PageCollectionService pageCollectionService;

	/** The replication action. */
	@Mock
	ReplicationAction replicationAction;

	/** The resolver. */
	@Mock
	ResourceResolver resolver;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;
	
	/** The page iterator. */
	@Mock
	Iterator<DispatcherDetailsBean> pageIterator;

	/** The listpage. */
	private List<String> listpage;

	/** The write service auth. */
	private Map<String, Object> writeServiceAuth;

	/** The list dispatcher details bean. */
	private List<DispatcherDetailsBean> listDispatcherDetailsBean;

	/** The dispatcher details bean. */
	@Mock
	DispatcherDetailsBean dispatcherDetailsBean;
	
	/** The response. */
	@Mock
	HttpResponse response;

	/** The country. */
	private String COUNTRY = "US";

	/** The content path. */
	private String CONTENT_PATH = "content/page";
	
	/** The config. */
	@Mock
	Configuration config;

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException, IOException, LoginException {

		writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "readService");
		listpage = new ArrayList<>();
		listDispatcherDetailsBean = new ArrayList<>();

		PrivateAccessor.setField(akamaiPurgeServiceImpl, "accessTokenProperty",
				"akab-ohjbd5pm5bksxx54-eapushtlgg5lorvp");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "akamaiTypeProperty", "arl");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "clientSecretProperty",
				"/P1k566DM1DsdHtIye4ahYr3F/EMkp0okwFV/n5ar+0=");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "clientTokenProperty",
				"akab-4ufo6vcxbylnja67-wqxhd4p57vv2ysrw");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "connectionTimeoutProperty", "5000");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "enableDispatcherInvalidationProperty", "");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "hostHeaderProperty",
				"akab-huufnq4mpbubvbh4-tdvz6t62itwftumw.purge.akamaiapis.net");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "openAuthServiceURLProperty",
				"https://akab-huufnq4mpbubvbh4-tdvz6t62itwftumw.purge.akamaiapis.net");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "socketTimeoutProperty", "5000");
		
		lenient().when(resolverFactory.getServiceResourceResolver(writeServiceAuth)).thenReturn(resolver);
		lenient().when(replicationAction.getPath()).thenReturn("/content/experience-fragments");

	}

	/**
	 * Test purge page.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testPurgePage() throws NoSuchFieldException {
		
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "ccuapitypeProperty", "V2");
		assertNotNull(akamaiPurgeServiceImpl.purgePage(replicationAction));
	}

	/**
	 * Test post CCUV 3 call.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws LoginException the login exception
	 */
	@Test
	void testPostCCUV3Call() throws NoSuchFieldException, LoginException {
		listpage.add("replicationPage");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "ccuapitypeProperty", "V1");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "akamaiActionProperty", "invalidate");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "akamaiDomainProperty", "production");

		when(pageCollectionService.getReplicationPages("/content/experience-fragments", resolver)).thenReturn(listpage);
		when(pageCollectionService.getShortUrls(listpage, resolver, true, true)).thenReturn(listpage);
		assertNotNull(akamaiPurgeServiceImpl.purgePage(replicationAction));
	}

	/**
	 * Test post CCUV 3 call for else.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws LoginException the login exception
	 */
	@Test
	void testPostCCUV3CallForElse() throws NoSuchFieldException, LoginException {

		String pageName = "replicationPagesnfd/dfdlfkwqqwwwwwweeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeesssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaannnnnnnnnnnnnnnnnnnnnnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvlllllllllllllllllllllllllllllllllllllllllllldssssssdkzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzcccccccccccccccccccssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssskkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkjasksajfnjaksfdnjksadfhjsadfsddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddjjjjjjjjjjjjjjhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhgggggggggggggggggggggggggggggrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsjnfajlksjdklasjdksajkdlaslndzdnnnnnnnnnnnnnnnsndsdjnsjnjsndsndsknnaklsnajjsnjsadjdsssssssjsjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjsajjhdhsshsahgdssjhajhsddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddsssssssssssssssssssssssssssssssssssssaaaaaaaaaaaaaaaaaaaaaaaaajjjjjjjjjjjjjjjjjjjjjjaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaadsjdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddsjssssssssssssssssssssssssssssssssssssjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrdddddddddddddddddddddddd";
		listpage.add(pageName);
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "ccuapitypeProperty", "V1");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "akamaiActionProperty", "validate");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "akamaiDomainProperty", "production");

		when(pageCollectionService.getReplicationPages("/content/experience-fragments", resolver)).thenReturn(listpage);
		when(pageCollectionService.getShortUrls(listpage, resolver, true, true)).thenReturn(listpage);

		assertNotNull(akamaiPurgeServiceImpl.purgePage(replicationAction));
	}
	
	/**
	 * Test post CCUV 3 call for invalid URL.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws LoginException the login exception
	 */
	@Test
	void testPostCCUV3CallForInvalidURL() throws NoSuchFieldException, LoginException {
		listpage.add("replicationPage");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "ccuapitypeProperty", "V1");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "akamaiActionProperty", "invalidate");
		PrivateAccessor.setField(akamaiPurgeServiceImpl, "akamaiDomainProperty", "productionInvalid");

		when(pageCollectionService.getReplicationPages("/content/experience-fragments", resolver)).thenReturn(listpage);
		when(pageCollectionService.getShortUrls(listpage, resolver, true, true)).thenReturn(listpage);
		assertNotNull(akamaiPurgeServiceImpl.purgePage(replicationAction));
	}

	/**
	 * Test process dispatcher flush.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testProcessDispatcherFlush() throws NoSuchFieldException, ClientProtocolException, IOException {
	
		PrivateAccessor.setField(dispatcherDetailsBean, "dispatcherDomain", "production");
		PrivateAccessor.setField(dispatcherDetailsBean, "user", "jhon");
		PrivateAccessor.setField(dispatcherDetailsBean, "password", "abc@123");

		listDispatcherDetailsBean.add(dispatcherDetailsBean);
		
		when(dispatcherDetailsBean.getDispatcherDomain()).thenReturn("dispatcherDomain");
		akamaiPurgeServiceImpl.processDispatcherFlush(CONTENT_PATH, listDispatcherDetailsBean, COUNTRY);
	}
	
	/**
	 * Test activate.
	 */
	@Test
	void testActivate() {
		akamaiPurgeServiceImpl.activate(config);
	}
	
	
}
