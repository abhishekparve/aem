package com.bdb.aem.core.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.io.input.BoundedInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ MockitoExtension.class })
public class SDSDocumentSearchServiceImplTest {
	@InjectMocks
	SDSDocumentSearchServiceImpl sdsDocumentSearchServiceImpl;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock Resource */
	@Mock
	Resource languageResource, resource, languageItem;
	/** The request. */
	@Mock
	SlingHttpServletRequest requests;
	@Mock
	ExternalizerService externalizerService;
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	@Mock
	HttpGet getMethod;
	/** The response entity. */
	@Mock
	HttpEntity responseEntity;
	@Mock
	BoundedInputStream boundedInput;
	@Mock
	Iterator<Resource> languageList;
	@Mock
	HttpResponse response;
	@Mock
	HttpPost httpRequest;
	RequestConfig requestConfig;
	@Mock
	CloseableHttpClient httpClient;
	@Mock
	CloseableHttpResponse httpResponse;
	@Mock
	HttpEntity httpEntity;
	@Mock
	ValueMap valueMap;
	String fileID = "fileID";
	String countryCode = "countryCode";
	String materialNumber = "12345";

	@Test
	void testSearchSDSDocument() throws RepositoryException, LoginException, IOException {
		String sdsEndpoint = "sdsEndpoint";
		lenient().when(resourceResolver.getResource(CommonConstants.LANGUAGE_GENERIC_LIST_PATH))
				.thenReturn(languageResource);
		lenient().when(bdbApiEndpointService.getSdsEndpointUser()).thenReturn("SdsEndpointUser");
		lenient().when(bdbApiEndpointService.getSdsEndpointPassword()).thenReturn("SdsEndpointPassword");
		lenient().when(bdbApiEndpointService.getSdsPdfDownloadEndpoint()).thenReturn("SdsPdfDownloadEndpoint");
		lenient().when(bdbApiEndpointService.getSdsPdfSearchEndpoint()).thenReturn("SdsPdfSearchEndpoint");
		sdsDocumentSearchServiceImpl.searchSDSDocument(countryCode, materialNumber);
	}

	@Test
	void testdownloadSDSDocument() throws RepositoryException, LoginException, IOException, AemInternalServerErrorException {
		JsonObject inputJson = new JsonObject();
		String sdsEndpoint = "sdsEndpoint";
		lenient().when(bdbApiEndpointService.getSdsEndpointUser()).thenReturn("SdsEndpointUser");
		lenient().when(bdbApiEndpointService.getSdsEndpointPassword()).thenReturn("SdsEndpointPassword");
		lenient().when(bdbApiEndpointService.getSdsPdfDownloadEndpoint()).thenReturn("SdsPdfDownloadEndpoint");
		Assertions.assertThrows(AemInternalServerErrorException.class, () -> {
			sdsDocumentSearchServiceImpl.downloadSDSDocument(fileID, requestConfig);
		});
		
	}

	@Test
	void testDeactivate() throws RepositoryException, LoginException, IOException {
		sdsDocumentSearchServiceImpl.deactivate();
	}

	@Test
	void testGetSearchAttributes() throws RepositoryException, LoginException, IOException {
		List<Resource> resourceList = new ArrayList<>();
		resourceList.add(resource);
		JsonObject searchAttributes = new JsonObject();
		String languageCode = "languageCode";
		String langCode = "langCode";
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.get(CommonConstants.DATA_VALUE, String.class)).thenReturn(languageCode);
		sdsDocumentSearchServiceImpl.getSearchAttributes(resourceList, languageCode, searchAttributes);
	}

	@Test
	void testGetLanguageResourceList() throws RepositoryException, LoginException {
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		List<Resource> resourceList = new ArrayList<>();
		resourceList.add(resource);
		lenient().when(languageResource.hasChildren()).thenReturn(true);
		lenient().when(languageResource.listChildren()).thenReturn(languageList);
		lenient().when(languageList.hasNext()).thenReturn(true, false);
		lenient().when(languageList.next()).thenReturn(languageItem);
		sdsDocumentSearchServiceImpl.getLanguageResourceList(languageResource, resourceList);
	}
}
